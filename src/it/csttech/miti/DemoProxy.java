/**
 * 
 */
package it.csttech.miti;

import java.lang.reflect.Proxy;

/**
 * 
 * Twin concepts: proxy and invocation handler:
 * Method invocations on an instance of a dynamic proxy class are dispatched to a single method in the instance's invocation handler, and they are encoded with a java.lang.reflect.Method object identifying the method that was invoked and an array of type Object containing the arguments.
 * 
 * Cosa sono i Proxy:
 * 	A dynamic proxy class is a class that implements a list of interfaces specified at runtime such that a method invocation through one of the interfaces on an instance of the class will be encoded and dispatched to another object through a uniform interface.
 * 
 * @author antoniomiti
 * @see <a href="http://tutorials.jenkov.com/java-reflection/dynamic-proxies.html">link1<a>
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/reflection/proxy.html">link2<a>
 * @see <a href="http://www.jugancona.it/vqwiki/jsp/Wiki%3FDesign+patterns+in+pillole:+il+%22mediatore%22,+il+proxy+pattern.html">link3<a>
 *
 */
public class DemoProxy {


	/**
	 * @param args
	 */
	public static void main(String[] args) {

        
		System.out.println("Case 1: create a proxy with Proxy.newProxyInstance()");
		/* Each class constructed by these factory methods is a public final subclass of Proxyreferred to as a proxy class. 
		 * We refer to an instance of one of these dynamically constructed proxies as a proxy instance. 
		 * We call the interfaces that the proxy class implements in this way proxied interfaces. 
		 * A proxy instance is assignment compatible with all of its proxied interfaces.
		*/

		TargetInterface realSubject = new TargetImplementation();
		//This call implicitly creates the proxy class, which can be retrieved with getProxyClass.
		TargetInterface proxedSubject = (TargetInterface) Proxy.newProxyInstance(
				realSubject.getClass().getClassLoader(),
				realSubject.getClass().getInterfaces(),
				new TargetInvocationHandler(realSubject));
		realSubject.method();
		proxedSubject.method();
		/*
		 * You create dynamic proxies using the Proxy.newProxyInstance() method. The newProxyInstance() methods takes 3 parameters:
		 * 	- The ClassLoader that is to "load" the dynamic proxy class.
		 * 	- An array of interfaces to implement.
		 * 	- An InvocationHandler to forward all methods calls on the proxy to.
		 * 
		 * 
		 * MyInterface proxy = (MyInterface) Proxy.newProxyInstance(
                            MyInterface.class.getClassLoader(),
                            new Class[] { MyInterface.class },
                            handler);
		 */
             

		System.out.println("\n\nCase 2: using a custom made proxy class \"ProxyTarget\" encapsulating the direct call of Proxy methods");
		TargetInterface t = (TargetInterface) ProxyTarget.createProxy(new TargetImplementation());
        //this will call the invoke method of the ProxyTarget class automatically and transparently 
        t.method();

        
		System.out.println("\n\nCase 3: Further testing of the Proxy object");	
		Class<?> clazz = proxedSubject.getClass();
		
		System.out.println( "proxedSubject.getClass() = " + clazz);
		System.out.println( "proxedSubject.toString() = " + proxedSubject.toString());       
		System.out.println( "realSubject.getClass() = " + realSubject.getClass());       
		System.out.println( "realSubject.toString() = " + realSubject.toString());       
        
        //Conclusion. every invocation on the proxedSubject variable pass through the invoke method. except for the .getClass()
		//http://stackoverflow.com/questions/19633534/what-is-com-sun-proxy-proxy

		
		System.out.println("\n\nCase 4: Probing Proxy Class");
		System.out.println("*-------------------------*");
		ReflectionInspection.probeClassDeep(proxedSubject.getClass());
		System.out.println("*-------------------------*");
		ReflectionInspection.probeClassDeep(realSubject.getClass());

		System.out.println("\n\nCase 5: Probing Proxy object");
		//Proxy è una classe costruita a runtime. l'unico modo per capire come è fatta è di esplorarla con la reflection
		//https://docs.oracle.com/javase/tutorial/reflect/member/fieldValues.html
		//TODO Passo l'istanza e esamino nel dettaglio gli attributi (cosa contengono) e i metodi
		//TODO Guardare anche cosa contengono gli attributi che vengono erediti
		//TODO elencare anche i metodi ereditati
		//		ReflectionInspection.deepProbe(proxedSubject.getClass());
		System.out.println("****************\nRealSubject");		
		ReflectionInspection.probeInstance(proxedSubject);
		System.out.println("****************\nRealSubject");	
		ReflectionInspection.probeInstance(realSubject);

		
		System.out.println("\n\nCase 6: Proxy Creation inside main class constructor");
		
		System.out.println("\n\nCase 7: Probing Proxy Methods");
		System.out.println("*-------------------------*");
		ReflectionInspection.probeClassMethods(proxedSubject.getClass());
		System.out.println("*-------------------------*");
		ReflectionInspection.probeClassMethods(realSubject.getClass());
		System.out.println("\n *-------------------------* \n");
		
		//ReflectionInspection.probeClassMethods();
		new DemoProxy();
		
	}
	
	/**
	 * 
	 */
	public DemoProxy() {
		System.out.println("*** Entering *" + this.getClass().getName() + "* Costructor***");

		TargetInterface realSubject = new TargetImplementation();
		//This call implicitly creates the proxy class, which can be retrieved with getProxyClass.
		TargetInterface proxedSubject = (TargetInterface) Proxy.newProxyInstance(
				realSubject.getClass().getClassLoader(),
				realSubject.getClass().getInterfaces(),
				new TargetInvocationHandler(realSubject));
		proxedSubject.method();
		
		System.out.println("*** Exting *" + this.getClass().getName() + "* Costructor***");		
	}
	


}
