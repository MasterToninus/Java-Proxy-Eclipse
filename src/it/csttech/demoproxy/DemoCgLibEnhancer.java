/**
 * 
 */
package it.csttech.demoproxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.*;

/**
 * <p>
 * The Enhancer can be compared with the Java standard library's Proxy class
 * which was introduced in Java 1.3. The Enhancer dynamically creates a subclass
 * of a given type but intercepts all method calls. Other than with the Proxy
 * class, this works for both class and interface types.
 * </p>
 * 
 * <p>
 * Cglib provides 6 type of "invocation handler". (they are all subinterface of
 * callback)
 * <ul>
 * <li><a href="http://cglib.sourceforge.net/apidocs/net/sf/cglib/proxy/FixedValue.html">FixedValue : </a> callback that simply returns the value to return from the proxied method.
 * No information about what method is being called is available to the callback, 
 * and the type of the returned object must be compatible with the return type of the proxied method. 
 * This makes this callback primarily useful for forcing a particular method (through the use of a CallbackFilter to return a fixed value with little overhead.
 * <li><a href="http://cglib.sourceforge.net/apidocs/net/sf/cglib/proxy/InvocationHandler.html">Invocation Handler :</a>
 * InvocationHandler replacement (unavailable under JDK 1.2). 
 * This callback type is primarily for use by the Proxy class but may be used with Enhancer as well.
 * <li><a href="http://cglib.sourceforge.net/apidocs/index.html">FixedValue</a>
 * <li><a href="http://cglib.sourceforge.net/apidocs/index.html">FixedValue</a>
 * <li><a href="http://cglib.sourceforge.net/apidocs/index.html">FixedValue</a>
 * <li><a href="http://cglib.sourceforge.net/apidocs/index.html">FixedValue</a>
 * </ul>
 * </p>
 * 
 * @author MasterToninus
 * 
 * @see <a href="https://github.com/cglib/cglib/wiki/How-To"> CgLib Git </a>
 * @see <a href="https://dzone.com/articles/cglib-missing-manual"> dZone </a>
 * @see <a href="https://dzone.com/articles/dynamic-class-enhancement-with-cglib"> dZone </a>
 *
 */
public class DemoCgLibEnhancer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TargetInterface sample = new TargetImplementation();
		sample.method();
		try {
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(TargetImplementation.class);
			enhancer.setCallback(new InvocationHandler() {
				@Override
				public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					if (method.getDeclaringClass() != Object.class) {
						System.out.println("  -ç-  *" + this.getClass().getName() + "*: is going to call the method *"
								+ method.getName() + "* of *" + proxy.getClass() + "*");
						return null;
					} else {
						throw new RuntimeException("Do not know what to do.");
					}
				}
			});

			TargetImplementation proxy = (TargetImplementation) enhancer.create();
			proxy.method();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * http://cglib.sourceforge.net/apidocs/index.html
	 * 
	 *
	 */
	public class MyFixedValue implements FixedValue {
		@Override
		public Object loadObject() throws Exception {
			return "Hello cglib ( - FixedValue - )!";
		}
	}

	/**
	 * http://cglib.sourceforge.net/apidocs/index.html
	 *
	 */
	public class MyInvocationHandler implements InvocationHandler {
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			if (method.getDeclaringClass() != Object.class) {
				return "Hello cglib ( - with InvocationHandler - )!";
			} else {
				throw new RuntimeException("Do not know what to do.");
			}
		}
	}

}
