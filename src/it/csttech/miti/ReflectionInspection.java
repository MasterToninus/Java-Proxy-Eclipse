/**
 * 
 */
package it.csttech.miti;

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

	static public void probeClass(Class<?> c) {
			System.out.println(separatorLine(65));	
			System.out.format("| %11s = %-47s |%n","MODIFIERS", Modifier.toString(c.getModifiers()) );
			System.out.format("| %11s = %-47s |%n","NAME", c.getSimpleName() );
			System.out.format("| %11s = %-47s |%n","PACKAGE", c.getPackage() );
			System.out.format("| %11s = %-47s |%n","ANNOTATIONS", c.getDeclaredAnnotations().length );
			System.out.format("| %11s = %-47s |%n","SUPERCLASS", c.getSuperclass() );
			System.out.format("| %11s = %-47s |%n","INTERFACES", Arrays.asList(c.getInterfaces()) );
			System.out.format("| %11s = %-47s |%n","SYNTHETIC", (c.isSynthetic())? "yes" : "no");
			System.out.println(separatorLine(65));					
	}

	/*
	 * Inspect the contents of the field  of the passed object
	 */
	static public void probeInstance(Object o) {
		System.out.format("*Instance of %s *%n",	o.getClass());
		//Deve dire tipo dei campi e valore contenuto in essi
		Class<?> clazz = o.getClass();

		Set<Field> allFields = new HashSet<Field>(Arrays.asList( clazz.getDeclaredFields()));
		allFields.addAll(Arrays.asList(clazz.getFields()));

		System.out.format(separatorLine(130) + "\n|%24s | %24s | %20s | = | [ %s ] %n" + separatorLine(130) +"\n" ,
				"MODIFIERS","TYPE","NAME","VALUE");
		for (Field f : allFields){
			//String type = f.getType().getName();
			//String name = f.getName();
			Object value;
			try {
				f.setAccessible(true);
				Class<?> c = f.getType();
				value = f.get(o);
				//System.out.println(f.toGenericString() + " = [" + value +"]");
				System.out.format("|%24s | %24s | %20s | = | [ %s ] %n",
						Modifier.toString( f.getModifiers() ),
						(c.isArray()) ? c.getComponentType()+"[]" : c.getName(),
						f.getName(),
						(c.isArray()) ?( objectArraytoList(value) ).toString() : value
						);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(separatorLine(130));	
	
	}
	
	/*
	 * Inspect the passed Method
	 */
	static public void probeMethod(Method m) {
		
	}

	
	/**
	 * Inspect the passed Field
	 * @param f
	 */
	static public String probeField(Field f){
		f.setAccessible(true);
		return String.format("| %8s |%24s | %24s | %20s | \t%s %n",
				f.getDeclaredAnnotations().length,
				Modifier.toString(f.getModifiers()),
				(f.getType().isArray()) ? f.getType().getComponentType()+"[]" : f.getType().getName(),
				f.getName(),
				f.getDeclaringClass()
				);
	}

	/**
	 * Inspect the passed Field
	 * @param f
	 */
	static public void probeFields(Class<?> c){
		Set<Field> allFields = new HashSet<Field>(Arrays.asList( c.getDeclaredFields()));
		allFields.addAll(Arrays.asList(c.getFields()));

		System.out.format("FIELDS :%n");
		if (allFields.size() != 0) {
			System.out.format(separatorLine(130) + "%n| %8s |%24s | %24s | %20s | \t%s %n" + separatorLine(130) + "\n",
					"ANNO.","MODIFIERS","TYPE","NAME","(Declaring CLASS)");	
			for(Field f : allFields)
				System.out.print(probeField(f));
			System.out.println(separatorLine(130));				
		} else {
			System.out.format("  -- None --%n");
		}	
		
	}
	
	static public String probeAnnotation(Annotation a){
		return String.format("| %8s | %s |%n",
				a.annotationType(),a
				);
		
	}
	
	static public void probeAnnotations(AnnotatedElement e){
		Set<Annotation> allAnnotations = new HashSet<Annotation>(Arrays.asList( e.getDeclaredAnnotations()));
		allAnnotations.addAll(Arrays.asList(e.getAnnotations()));

		System.out.format("ANNOTATIONS :%n");		
		if (allAnnotations.size() != 0) {
			System.out.format(separatorLine(130) + "%n| %8s | %s |%n" + separatorLine(130) + "\n",
					"TYPE","toString()"
					);
			
			for(Annotation a : allAnnotations)
				System.out.print(probeAnnotation(a));
			System.out.println(separatorLine(130));	
		} else {
			System.out.format("  -- None --%n");
		}	

	}
	
	private static List<Object> objectArraytoList(Object o){
		List<Object> list = new ArrayList<Object>();
		if ( o.getClass().isArray() && o != null ){
			for(int i = 0; i< Array.getLength(o); i++)
				list.add(Array.get(o,i));
		}
		return list;		
	}
	
	/**
	 * Quick print out of all the element of the passed array as toString()
	 * @param array
	 */
	private static void fancyArrayShow(Object[] array){
		if (array.length != 0) {
			for (Object a : array)
				System.out.format("  %s%n", a.toString());
		} else {
			System.out.format("  -- None --%n");
		}		
	}
	
	static public void printAncestor(Class<?> c, List<Class<?>> l) {
		Class<?> ancestor = c.getSuperclass();
		if (ancestor != null) {
			l.add(ancestor);
			printAncestor(ancestor, l);
		}
	}

	static public String probeInnerClass(Class<?> c) {
		return(	String.format("| %s | %s | %s | %s | %s | %s | %s |%n",
					Modifier.toString(c.getModifiers()),
					c.getSimpleName(),
					c.getPackage(),
					c.getEnclosingClass(),
					c.getDeclaredAnnotations().length,
					c.getSuperclass(),
					Arrays.asList(c.getInterfaces()),
					(c.isSynthetic())? "yes" : "no"));
	}

	static public void probeInnerClasses(Class<?> c) {
		Set<Class<?>> allClasses = new HashSet<Class<?>>(Arrays.asList( c.getDeclaredClasses()));
		allClasses.addAll(Arrays.asList(c.getClasses()));

		System.out.format("INNERCLASSES :%n");		
		if (allClasses.size() != 0) {
			System.out.format(separatorLine(130) + "%n| %s | %s | %s | %s | %s | %s | %s |%n" + separatorLine(130) + "\n",
					"MODIFIERS","NAME","PACKAGE","ENCLOSING CLASS","ANNO.","SUPERCLASS","INTERFACES");
			
			for(Class<?> a : allClasses)
				System.out.print(probeInnerClass(a));
			System.out.println(separatorLine(130));	
		} else {
			System.out.format("  -- None --%n");
		}			
		
	}
	
	static public String probeConstructor(Constructor<?> c) {
		return(	String.format("| %s | %s | %s | %s | %s | %s | %s |%n",
					Modifier.toString(c.getModifiers()),
					c.getName(),
					c.getDeclaringClass().getSimpleName(),
					c.getDeclaredAnnotations().length,
					Arrays.asList(c.getGenericParameterTypes()),
					Arrays.asList(c.getGenericExceptionTypes()),
					(c.isSynthetic())? "yes" : "no"));
	}

	static public void probeConstructors(Class<?> c) {
		Set<Constructor<?>> allConstructors = new HashSet<Constructor<?>>(Arrays.asList( c.getDeclaredConstructors()));
		allConstructors.addAll(Arrays.asList(c.getConstructors()));

		System.out.format("CONSTRUCTORS :%n");		
		if (allConstructors.size() != 0) {
			System.out.format(separatorLine(130) + "%n| %s | %s | %s | %s | %s | %s | %s |%n" + separatorLine(130) + "\n",
					"MODIFIERS","NAME","DECLARINGCLASS","ANNO.","PARAMETERS","EXCEPTIONS","SYNTHETIC");
			
			for(Constructor<?> a : allConstructors)
				System.out.print(probeConstructor(a));
			System.out.println(separatorLine(130));	
		} else {
			System.out.format("  -- None --%n");
		}			
		

	}
	
	
	static public void probeClassDeep(Class<?> c) {
		System.out.format("\nCLASS : %s %n", c.getCanonicalName());
		
		probeClass(c);

		probeAnnotations(c);
		probeFields(c);
		probeInnerClasses(c);
		


		System.out.format("Declared Methods:%n");
		Method[] decMet = c.getDeclaredMethods();
		fancyArrayShow(decMet);
		System.out.format("Methods:%n");
		Method[] met = c.getMethods();
		fancyArrayShow(met);
		
		probeConstructors(c);
		
}
	
	protected static String separatorLine(int size){
		final char[] array = new char[size];
		Arrays.fill(array, '-');
		array[0]='+';
		array[array.length -1]='+';
		return new String(array);
	}

	
	
}
