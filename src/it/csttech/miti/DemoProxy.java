/**
 * 
 */
package it.csttech.miti;

import java.lang.reflect.Proxy;

/**
 * 
 * Concetti Gemelli: proxy and invocation handler:
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
	 * 
	 */
	public DemoProxy() {
		System.out.println("ciauBau");
	}

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
		TargetInterface proxy = (TargetInterface) Proxy.newProxyInstance(
				realSubject.getClass().getClassLoader(),
				realSubject.getClass().getInterfaces(),
				new TargetInvocationHandler(realSubject));
		proxy.method();
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

        
        
        
        
	}

}
