/**
 * 
 */
package it.csttech.demoproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author JugAncona classe ProxyTarget che creerà il proxy della classe
 *         TargetImplementation (o di una qualsiasi implementazione di
 *         TargetInterface) e a cui delegheremo le chiamate dei metodi
 *         dell'interfaccia TargetInterface:
 *
 *         Create the proxy class and delegate the calls to the target class
 *
 *         two important tasks for any proxy are interface implementation and
 *         delegation.
 * 
 */
public class ProxyTarget implements InvocationHandler {

	public static Object createProxy(Object obj) {
		// create the proxy class according the instance of the target class
		return Proxy.newProxyInstance(
				obj.getClass().getClassLoader(), 
				obj.getClass().getInterfaces(),
				new ProxyTarget(obj));
		// attenzione all'invocazione del metodo statico newProxyInstance:
		// Il metodo fa ampio uso delle classi di reflection fornite da Java e
		// nell'ordine richiede il ClassLoader della classe target,
		// le interfacce da essa implementate, e un gestore delle chiamate ai
		// metodi di queste interfacce.
		// Quest'ultimo argomento in questo caso è la classe ProxyTarget stessa
		// che implementa l'interfaccia standard InvocationHandler.
		// Questa interfaccia dichiara solo il metodo Object invoke(Object
		// proxy, Method method, Object[] args) .

	}

	private Object target;

	private ProxyTarget(Object target) {
		this.target = target;
	}

	// Implements the method of InvocationHandler interface
	// This method will catch all the calls to the target class and redispatch
	// them
	public Object invoke(Object proxy, Method method, Object[] args) {
		Object result = null;
		try {
			// add function before redispatching
			System.out.println("  *" + this.getClass().getName() + "*: is going to call the method *" + method.getName() + "* of *" + target.getClass() + "*");
			// redispatch the call
			result = method.invoke(target, args);
			// add function after redispatching
			System.out.println("  *" + this.getClass().getName() + "*: invocation accomplished. ");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}

}
