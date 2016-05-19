/**
 * 
 */
package it.csttech.demoproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Example: an invocation handler that block the access to some kind of method
 * 
 * @see <a href="http://syncor.blogspot.it/2013/09/getting-started-with-log4j-2-in-eclipse.html">link</a>
 *
 */
public class ProxyLogger {

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

	/**
	 * 
	 *
	 */
	static class NestedInvocationHandler implements InvocationHandler {
		private final Object original;
		private static Logger log = null ; //should be final

		public NestedInvocationHandler(Object original) {
			this.original = original;
			log =  LogManager.getLogger(original.getClass().getSimpleName());
		}

		public Object invoke(Object proxy, Method method, Object[] args)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			Object result;

			log.trace("Invoking Method " + method.getName());
			result = method.invoke(original, args);
			log.trace("Invocation accomplished.");

			return result;
		}
	}

}
