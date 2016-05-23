/**
 * 
 */
package it.csttech.demoproxy;

import java.lang.reflect.Method;

import it.csttech.toninus.ReflectionInspection;
import net.sf.cglib.proxy.Proxy;
import net.sf.cglib.proxy.InvocationHandler;

/**
 * 
 * <p>
 * dynamic classes built using the functionality of the standard Java library
 * suffers from an important limitation, as it can only create dynamic classes
 * that proxy interfaces. In order to provide services such as container-managed
 * transactions (as done by the Spring Framework) or transparent lazy fetching
 * of data (as done by Hibernate) it is necessary to create dynamic classes that
 * appear to be an instance of a concrete class. standard Java library. However,
 * it suffers from an important limitation, as it can only create dynamic
 * classes that proxy interfaces. In order to provide services such as
 * container-managed transactions (as done by the Spring Framework) or
 * transparent lazy fetching of data (as done by Hibernate) it is necessary to
 * create dynamic classes that appear to be an instance of a concrete class.
 * </p>
 * 
 * <p>
 * Proxying for a concrete class is more challenging, because while there can be
 * many classes that implement an interface, there is only one version of a
 * concrete class per class loader. In order to work around this, CGLib creates
 * a dynamic child class of the class being proxied. Of course, this only works
 * if the class is not final.
 * </p>
 * 
 * @author MasterToninus
 *
 */
public class DemoCgLibProxy {

	/**
	 * <a href=
	 * "http://cglib.sourceforge.net/apidocs/net/sf/cglib/proxy/Proxy.html">
	 * Link </a>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		TargetInterface sample = new TargetImplementation();
		sample.method();

		TargetInterface proxy = proxyFactory();
		proxy.method();

		ReflectionInspection.probeClassDeep(proxy.getClass());
		ReflectionInspection.probeInstance(proxy);

	}

	/**
	 * 
	 * @return
	 */
	public static TargetInterface proxyFactory() {
		return (TargetInterface) Proxy.newProxyInstance(TargetImplementation.class.getClassLoader(),
				TargetImplementation.class.getInterfaces(), new MyInvocationHandler(new TargetImplementation()));
	}

	/**
	 * http://cglib.sourceforge.net/apidocs/index.html
	 *
	 */
	public static class MyInvocationHandler implements InvocationHandler {

		private Object realSubject = null;

		/**
		 * 
		 */
		public MyInvocationHandler(Object realSubject) {
			this.realSubject = realSubject;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
		 * java.lang.reflect.Method, java.lang.Object[])
		 */
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			Object result = null;
			try {
				// add function before redispatching
				System.out.println("  *" + this.getClass().getSimpleName() + "*: is going to call the method *"
						+ method.getName() + "* of *" + realSubject.getClass().getSimpleName() + "*");
				// redispatch the call
				result = method.invoke(realSubject, args);
				// add function after redispatching
				System.out.println("  *" + this.getClass().getSimpleName() + "*: invocation accomplished. ");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return result;
		}

	}

}
