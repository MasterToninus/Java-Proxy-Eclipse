/**
 * 
 */
package it.csttech.demoproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Example: an invocation handler that block the access to some kind of method
 *
 */
public class ProxyCensor {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		TargetInterface proxedSubject = (TargetInterface) Proxy.newProxyInstance(
				TargetImplementation.class.getClassLoader(), TargetImplementation.class.getInterfaces(),
				new NestedInvocationHandler(new TargetImplementation()));

		proxedSubject.method();

	}

	static class NestedInvocationHandler implements InvocationHandler {
		private final Object original;

		public NestedInvocationHandler(Object original) {
			this.original = original;
		}

		public Object invoke(Object proxy, Method method, Object[] args)
				throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			if (method.getName().contains("meth")) {
				System.err.println("Access denied to method : " + method);
			} else
				method.invoke(original, args);
			return null;
		}
	}

}
