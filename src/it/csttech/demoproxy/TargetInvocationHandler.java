/**
 * 
 */
package it.csttech.demoproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author MasterToninus
 *
 */
public class TargetInvocationHandler implements InvocationHandler {

	private Object realSubject = null;
	
	/**
	 * 
	 */
	public TargetInvocationHandler(Object realSubject) {
		this.realSubject = realSubject;
	}

	/* (non-Javadoc)
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object result = null;
		try {
			// add function before redispatching
			System.out.println("  *" + this.getClass().getName() + "*: is going to call the method *" + method.getName() + "* of *" + realSubject.getClass() + "*");
			// redispatch the call
			result = method.invoke(realSubject, args);
			// add function after redispatching
			System.out.println("  *" + this.getClass().getName() + "*: invocation accomplished. ");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
		return result;
	}
	
}
