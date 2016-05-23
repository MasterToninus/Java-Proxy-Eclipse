/**
 * 
 */
package it.csttech.toninus;


import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author antoniomiti
 *
 */
public class ReflectionInspection {

	/**
	 * 
	 * @param c
	 */
	static public void probeClass(Class<?> c) {
		System.out.println(separatorLine(65));
		System.out.format("| %11s = %-47s |%n", "MODIFIERS", Modifier.toString(c.getModifiers()));
		System.out.format("| %11s = %-47s |%n", "NAME", c.getSimpleName());
		System.out.format("| %11s = %-47s |%n", "PACKAGE", c.getPackage());
		System.out.format("| %11s = %-47s |%n", "ANNOTATIONS", c.getDeclaredAnnotations().length);
		System.out.format("| %11s = %-47s |%n", "SUPERCLASS", c.getSuperclass());
		System.out.format("| %11s = %-47s |%n", "INTERFACES", Arrays.asList(c.getInterfaces()));
		System.out.format("| %11s = %-47s |%n", "SYNTHETIC", (c.isSynthetic()) ? "yes" : "no");
		System.out.println(separatorLine(65));
	}

	/**
	 * 
	 * @param c
	 */
	static public void probeClassDeep(Class<?> c) {
		System.out.format("\nCLASS : %s %n", c.getCanonicalName());

		probeClass(c);
		probeAnnotations(c);
		probeFields(c);
		probeInnerClasses(c);
		probeMethods(c);
		probeConstructors(c);

	}

	/*
	 * Inspect the contents of the field of the passed object
	 */
	static public void probeInstance(Object o) {
		System.out.format("*Instance of %s *%n", o.getClass());
		// Deve dire tipo dei campi e valore contenuto in essi
		Class<?> clazz = o.getClass();

		Set<Field> allFields = getFields(clazz);

		System.out.format(separatorLine(130) + "\n|%24s | %24s | %20s | = | [ %s ] %n" + separatorLine(130) + "\n",
				"MODIFIERS", "TYPE", "NAME", "VALUE");
		for (Field f : allFields) {
			Object value;
			try {
				f.setAccessible(true);
				Class<?> c = f.getType();
				value = f.get(o);
				// System.out.println(f.toGenericString() + " = [" + value
				// +"]");
				System.out.format("|%24s | %24s | %20s | = | [ %s ] %n", 
						Modifier.toString(f.getModifiers()),
						(c.isArray()) ? c.getComponentType() + "[]" : c.getSimpleName(), 
						f.getName(),
						(c.isArray()) ? (objectArraytoList(value)).toString() : value);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		System.out.println(separatorLine(130));
	}

	/**
	 * support method, unwrap an array object in the list of its components.
	 * 
	 * @param o
	 * @return
	 */
	private static List<Object> objectArraytoList(Object o) {
		List<Object> list = new ArrayList<Object>();
		if ( o != null && o.getClass().isArray() )
			for (int i = 0; i < Array.getLength(o); i++)
				list.add(Array.get(o, i));
		return list;
	}

	private static String separatorLine(int size) {
		final char[] array = new char[size];
		Arrays.fill(array, '-');
		array[0] = '+';
		array[array.length - 1] = '+';
		return new String(array);
	}

	/**
	 * Get all the fields accessible (excluding the default package wise
	 * accessability
	 * 
	 * @param c
	 * @return
	 */
	protected static Set<Field> getFields(Class<?> c) {
		Set<Field> allFields = new HashSet<Field>(Arrays.asList(c.getDeclaredFields())); 
		/* get all declared fields independently from the modifiers */

		allFields.addAll(Arrays.asList(c.getFields())); 
		/* get all public fields declared and inherited */

		/* get all the protected inherited fields */
		Class<?> ancestor = c.getSuperclass();
		do {
			Field[] dummy = ancestor.getDeclaredFields();
			for (Field f : dummy)
				if (Modifier.isProtected(f.getModifiers()))
					allFields.add(f);
			ancestor = ancestor.getSuperclass();
		} while (ancestor != null);

		return allFields;
	}

	/**
	 * Inspect the passed Field
	 * 
	 * @param f
	 */
	static public String probeField(Field f) {
		f.setAccessible(true);
		return String.format("| %8s |%24s | %35s | %20s | \t%s %n", 
				f.getDeclaredAnnotations().length,
				Modifier.toString(f.getModifiers()),
				(f.getType().isArray()) ? f.getType().getComponentType() + "[]" : f.getType().getName(), 
				f.getName(),
				f.getDeclaringClass());
	}

	/**
	 * Inspect the passed Field
	 * 
	 * @param f
	 */
	static public void probeFields(Class<?> c) {
		Set<Field> allFields = getFields(c);

		System.out.format("FIELDS :%n");
		if (allFields.size() != 0) {
			System.out.format(separatorLine(130) + "%n| %8s |%24s | %35s | %20s | \t%s %n" + separatorLine(130) + "\n",
					"ANNO.", "MODIFIERS", "TYPE", "NAME", "(Declaring CLASS)");
			for (Field f : allFields)
				System.out.print(probeField(f));
			System.out.println(separatorLine(130));
		} else {
			System.out.format("  -- None --%n");
		}

	}

	protected static Set<Annotation> getAnnotations(AnnotatedElement e) {
		Set<Annotation> allAnnotations = new HashSet<Annotation>(Arrays.asList(e.getDeclaredAnnotations()));
		allAnnotations.addAll(Arrays.asList(e.getAnnotations()));
		return allAnnotations;
	}

	static public String probeAnnotation(Annotation a) {
		return String.format("| %8s | %s |%n", a.annotationType(), a);

	}

	static public void probeAnnotations(AnnotatedElement e) {
		Set<Annotation> allAnnotations = getAnnotations(e);

		System.out.format("ANNOTATIONS :%n");
		if (allAnnotations.size() != 0) {
			System.out.format(separatorLine(130) + "%n| %8s | %s |%n" + separatorLine(130) + "\n", "TYPE",
					"toString()");

			for (Annotation a : allAnnotations)
				System.out.print(probeAnnotation(a));
			System.out.println(separatorLine(130));
		} else {
			System.out.format("  -- None --%n");
		}

	}

	protected static Set<Class<?>> getInnerClasses(Class<?> c) {
		Set<Class<?>> allClasses = new HashSet<Class<?>>(Arrays.asList(c.getDeclaredClasses()));
		allClasses.addAll(Arrays.asList(c.getClasses()));

		Class<?> ancestor = c.getSuperclass();
		do {
			Class<?>[] dummy = ancestor.getDeclaredClasses();
			for (Class<?> k : dummy)
				if (Modifier.isProtected(k.getModifiers()))
					allClasses.add(k);
			ancestor = ancestor.getSuperclass();
		} while (ancestor != null);

		return allClasses;
	}

	static public String probeInnerClass(Class<?> c) {
		return (String.format("| %s | %s | %s | %s | %s | %s | %s |%n", Modifier.toString(c.getModifiers()),
				c.getSimpleName(), c.getPackage(), c.getEnclosingClass(), c.getDeclaredAnnotations().length,
				c.getSuperclass(), Arrays.asList(c.getInterfaces()), (c.isSynthetic()) ? "yes" : "no"));
	}

	static public void probeInnerClasses(Class<?> c) {
		Set<Class<?>> allClasses = getInnerClasses(c);

		System.out.format("INNERCLASSES :%n");
		if (allClasses.size() != 0) {
			System.out.format(
					separatorLine(130) + "%n| %s | %s | %s | %s | %s | %s | %s |%n" + separatorLine(130) + "\n",
					"MODIFIERS", "NAME", "PACKAGE", "ENCLOSING CLASS", "ANNO.", "SUPERCLASS", "INTERFACES");

			for (Class<?> a : allClasses)
				System.out.print(probeInnerClass(a));
			System.out.println(separatorLine(130));
		} else {
			System.out.format("  -- None --%n");
		}

	}

	/**
	 * 
	 * @param c
	 * @return
	 */
	protected static Set<Method> getMethods(Class<?> c) {
		Set<Method> allMethods = new HashSet<Method>(Arrays.asList(c.getDeclaredMethods()));
		allMethods.addAll(Arrays.asList(c.getMethods()));

		Class<?> ancestor = c.getSuperclass();
		do {
			Method[] dummy = ancestor.getDeclaredMethods();
			for (Method m : dummy)
				if (Modifier.isProtected(m.getModifiers()))
					allMethods.add(m);
			ancestor = ancestor.getSuperclass();
		} while (ancestor != null);

		return allMethods;
	}

	/**
	 * 
	 * @param m
	 * @return
	 */
	static public String probeMethod(Method m) {
		return (String.format("| %25s | %20s | %20s | %5s | %20s | %s | %s |%n", Modifier.toString(m.getModifiers()),
				m.getName(), m.getDeclaringClass().getSimpleName(), m.getDeclaredAnnotations().length,
				m.getGenericReturnType().getTypeName(), Arrays.asList(m.getGenericParameterTypes()),
				Arrays.asList(m.getGenericExceptionTypes()), (m.isSynthetic()) ? "yes" : "no"));
	}

	/**
	 * s
	 * 
	 * @param c
	 */
	static public void probeMethods(Class<?> c) {
		Set<Method> allMethods = getMethods(c);

		System.out.format("METHODS :%n");
		if (allMethods.size() != 0) {
			System.out.format(
					separatorLine(130) + "%n| %25s | %20s | %20s | %5s | %20s | %s | %s |%n" + separatorLine(130)
							+ "\n",
					"MODIFIERS", "NAME", "DECLARINGCLASS", "ANNO.", "RETURNTYPE", "PARAMETERS", "EXCEPTIONS",
					"SYNTHETIC");

			for (Method a : allMethods)
				System.out.print(probeMethod(a));
			System.out.println(separatorLine(130));
		} else {
			System.out.format("  -- None --%n");
		}
	}

	/**
	 * 
	 * @param c
	 * @return
	 */
	protected static Set<Constructor<?>> getConstructors(Class<?> c) {
		Set<Constructor<?>> allConstructors = new HashSet<Constructor<?>>(Arrays.asList(c.getDeclaredConstructors()));
		allConstructors.addAll(Arrays.asList(c.getConstructors()));
		return allConstructors;
	}

	/**
	 * 
	 * @param c
	 * @return
	 */
	static public String probeConstructor(Constructor<?> c) {
		return (String.format("| %25s | %35s | %20s | %5s | %20s | %s | %s |%n", Modifier.toString(c.getModifiers()),
				c.getName(), c.getDeclaringClass().getSimpleName(), c.getDeclaredAnnotations().length,
				Arrays.asList(c.getGenericParameterTypes()), Arrays.asList(c.getGenericExceptionTypes()),
				(c.isSynthetic()) ? "yes" : "no"));
	}

	/**
	 * 
	 * @param c
	 */
	static public void probeConstructors(Class<?> c) {
		Set<Constructor<?>> allConstructors = getConstructors(c);

		System.out.format("CONSTRUCTORS :%n");
		if (allConstructors.size() != 0) {
			System.out.format(
					separatorLine(130) + "%n| %25s | %35s | %20s | %5s | %20s | %s | %s |%n" + separatorLine(130)
							+ "\n",
					"MODIFIERS", "NAME", "DECLARINGCLASS", "ANNO.", "PARAMETERS", "EXCEPTIONS", "SYNTHETIC");

			for (Constructor<?> a : allConstructors)
				System.out.print(probeConstructor(a));
			System.out.println(separatorLine(130));
		} else {
			System.out.format("  -- None --%n");
		}

	}

}
