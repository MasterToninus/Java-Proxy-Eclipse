package it.csttech.dragorsone;

import static java.lang.System.out;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

import it.csttech.toninus.ReflectionInspection;

public class DeepInspection extends ReflectionInspection {

	private static final String fmt = "%24s: %s%n";

	static public void probeClassMethodsDeep(Class<?> c) {

		out.println("CLASS: " + c.getName() + " METHODS:\n");
		Method[] allMethods = c.getDeclaredMethods();
		for (Method m : allMethods) {
			// if (!m.getName().equals(args[1])) {
			// continue;
			// }
			probeMethodDeep(m);

		}
	}

	/*
	 * Inspect the passed Method
	 */
	static public void probeMethodDeep(Method m) {

		out.format("%s%n", m.toGenericString());
		out.format(fmt, "ReturnType", m.getReturnType());
		out.format(fmt, "GenericReturnType", m.getGenericReturnType());
		out.format(fmt, "Modifier", Modifier.toString(m.getModifiers()));

		Parameter[] params = m.getParameters();
		Type[] gpType = m.getGenericParameterTypes();
		for (int i = 0; i < params.length; i++) {
			printParameter(params[i]);
			out.format(fmt, "GenericParameterType", gpType[i]);
		}
		out.println("\n");
		/*
		 * Class<?>[] pType = m.getParameterTypes(); Type[] gpType =
		 * m.getGenericParameterTypes(); //GenericsParamTypes? for (int i = 0; i
		 * < pType.length; i++) { out.format(fmt,"ParameterType", pType[i]);
		 * out.format(fmt,"GenericParameterType", gpType[i]); }
		 */

		Class<?>[] xType = m.getExceptionTypes();
		Type[] gxType = m.getGenericExceptionTypes();
		for (int i = 0; i < xType.length; i++) {
			out.format(fmt, "ExceptionType", xType[i]);
			out.format(fmt, "GenericExceptionType", gxType[i]);
		}
		out.println("\n");
	}

	public static void printParameter(Parameter p) {
		out.format(fmt, "Parameter name", p.getName()); 
		/*why does it not work? literal? try method with params in  TargetImpl.*/
		out.format(fmt, "Parameter class", p.getType());
		out.format(fmt, "Modifiers", Modifier.toString(p.getModifiers())); 
		/* ModifiersType  of an argument??*/
		out.format(fmt, "Is implicit?", p.isImplicit());
		out.format(fmt, "Is name present?", p.isNamePresent());
		out.format(fmt, "Is synthetic?", p.isSynthetic());
	}
	
}
