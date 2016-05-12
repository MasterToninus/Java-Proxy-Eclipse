/**
 * 
 */
package it.csttech.miti;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;


/**
 * @author antoniomiti
 *
 */
public class ReflectionInspection {

	static public void probe(Class<?> c) {
			System.out.format("Class:%n  %s%n%n", c.getCanonicalName());
			System.out.format("Modifiers:%n  %s%n%n", Modifier.toString(c.getModifiers()));

			System.out.format("Declared Annotations:%n");
			Annotation[] decAnn = c.getDeclaredAnnotations();
			fancyArrayShow(decAnn);
			System.out.format("Annotations:%n");
			Annotation[] ann = c.getAnnotations();
			fancyArrayShow(decAnn);			

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

	private static void fancyArrayShow(Object[] array){
		if (array.length != 0) {
			for (Object a : array)
				System.out.format("  %s%n", a.toString());
			System.out.format("%n");
		} else {
			System.out.format("  -- None --%n%n");
		}		
	}
	
	private static void printAncestor(Class<?> c, List<Class> l) {
		Class<?> ancestor = c.getSuperclass();
		if (ancestor != null) {
			l.add(ancestor);
			printAncestor(ancestor, l);
		}
	}

}
