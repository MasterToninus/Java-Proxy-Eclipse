/**
 * 
 */
package it.csttech.miti;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.List;
import static java.lang.System.out;
import java.lang.reflect.Type;


/**
 * @author antoniomiti
 *
 */
public class ReflectionInspection {

	static public void probeClass(Class<?> c) {
			System.out.format("Class:%n  %s%n%n", c.getCanonicalName());
			System.out.format("Modifiers:%n  %s%n%n", Modifier.toString(c.getModifiers()));

			System.out.format("Declared Annotations:%n");
			Annotation[] decAnn = c.getDeclaredAnnotations();
			fancyArrayShow(decAnn);
			System.out.format("Annotations:%n");
			Annotation[] ann = c.getAnnotations();
			fancyArrayShow(ann);			

			System.out.format("Implemented Interfaces:%n");
			Class<?>[] interfaces = c.getInterfaces();
			fancyArrayShow(interfaces);
			
			System.out.format("Direct SuperClass:%n  %s%n%n", c.getSuperclass());

			System.out.format("Declared Fields:%n");
			Field[] decFiel = c.getDeclaredFields();
			fancyArrayShow(decFiel);
			System.out.format("Fields:%n");
			Field[] fiel = c.getFields();
			fancyArrayShow(fiel);

			System.out.format("Declared Inner Classes:%n");
			Class<?>[] decInnClaz = c.getDeclaredClasses();
			fancyArrayShow(decInnClaz);
			System.out.format("Inner Classes:%n");
			Class<?>[] innClaz = c.getClasses();	
			fancyArrayShow(innClaz);			
			
	
			System.out.format("Declared Methods:%n");
			Method[] decMet = c.getDeclaredMethods();
			fancyArrayShow(decMet);
			System.out.format("Methods:%n");
			Method[] met = c.getMethods();
			fancyArrayShow(met);
			
			System.out.format("Declared Constructors:%n");
			Constructor<?>[] decCons = c.getDeclaredConstructors();
			fancyArrayShow(decCons);
			System.out.format("Constructors:%n");
			Constructor<?>[] cons = c.getConstructors();
			fancyArrayShow(cons);
			
	}

	/*
	 * Inspect the contents of the field  of the passed object
	 */
	static public void probeInstance(Object o) {
		
	}
	
	static public void probeClassMethods(Class<?> c) {
		
			out.println("Class: " + c.getName() + " methods:\n");
		    Method[] allMethods = c.getDeclaredMethods();
		    for (Method m : allMethods) {
		    //	if (!m.getName().equals(args[1])) {
		    //		continue;
		    //	}
		    	probeMethod(m);
			
		    }
	}
	
	
	/*
	 * Inspect the passed Method
	 */
	static public void probeMethod(Method m) {
		
		out.format("%s%n", m.toGenericString());
		out.format(fmt, "ReturnType", m.getReturnType());
		out.format(fmt, "GenericReturnType", m.getGenericReturnType());
		out.format(fmt, "Modifier", Modifier.toString(m.getModifiers()));
		
		Parameter[] params = m.getParameters();
		Type[] gpType = m.getGenericParameterTypes();
        for (int i = 0; i < params.length; i++) {
            printParameter(params[i]);
            out.format(fmt,"GenericParameterType", gpType[i]);
        }
        out.println("\n");
/*
		Class<?>[] pType  = m.getParameterTypes();
		Type[] gpType = m.getGenericParameterTypes();		//GenericsParamTypes?
		for (int i = 0; i < pType.length; i++) {
			out.format(fmt,"ParameterType", pType[i]);
			out.format(fmt,"GenericParameterType", gpType[i]);
		}*/

		Class<?>[] xType  = m.getExceptionTypes();
		Type[] gxType = m.getGenericExceptionTypes();
		for (int i = 0; i < xType.length; i++) {
		    out.format(fmt,"ExceptionType", xType[i]);
		    out.format(fmt,"GenericExceptionType", gxType[i]);
		}
		out.println("\n");
	}
	
	public static void printParameter(Parameter p) {
		out.format(fmt, "Parameter name", p.getName()); //why does it not work? literal? try method with params in TargetImpl.
        out.format(fmt, "Parameter class", p.getType());
        out.format(fmt, "Modifiers", Modifier.toString(p.getModifiers())); //ModifiersType of an argument??
        out.format(fmt, "Is implicit?", p.isImplicit());
        out.format(fmt, "Is name present?", p.isNamePresent());
        out.format(fmt, "Is synthetic?", p.isSynthetic());
    }

	/**
	 * Inspect the passed Constructor
	 * @param c
	 */
	static public void probeConstructor(Constructor<?> c) {
		
	}	
	
	/**
	 * Inspect the passed Field
	 * @param f
	 */
	static public void probeField(Field f){
		
	}

	/**
	 * Quick print out of all the element of the passed array as toString()
	 * @param array
	 */
	private static void fancyArrayShow(Object[] array){
		if (array.length != 0) {
			for (Object a : array)
				System.out.format("  %s%n", a.toString());
			System.out.format("%n");
		} else {
			System.out.format("  -- None --%n%n");
		}		
	}
	
	private static void printAncestor(Class<?> c, List<Class<?>> l) {
		Class<?> ancestor = c.getSuperclass();
		if (ancestor != null) {
			l.add(ancestor);
			printAncestor(ancestor, l);
		}
	}

	private static final String  fmt = "%24s: %s%n";
	
}
