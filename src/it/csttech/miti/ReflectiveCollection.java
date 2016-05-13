/**
 * 
 */
package it.csttech.miti;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author antoniomiti
 *
 */
public class ReflectiveCollection {

	static public Set<Annotation> collectAnnotation(AnnotatedElement e){
		Set<Annotation> allAnnotations = new HashSet<Annotation>(Arrays.asList( e.getDeclaredAnnotations()));
		allAnnotations.addAll(Arrays.asList(e.getAnnotations()));
		return allAnnotations;
	}

	static public Set<Class<?>> collectInnerClasses(Class<?> c){
		Set<Class<?>> allClasses = new HashSet<Class<?>>(Arrays.asList( c.getDeclaredClasses()));
		allClasses.addAll(Arrays.asList(c.getClasses()));
		return allClasses;
	}	

	static public Set<Field> collectFields(Class<?> c){
		Set<Field> allFields = new HashSet<Field>(Arrays.asList( c.getDeclaredFields()));
		allFields.addAll(Arrays.asList(c.getFields()));
		return allFields;
	}	

	static public Set<Method> collectMethods(Class<?> c){
		Set<Method> allMethods = new HashSet<Method>(Arrays.asList( c.getDeclaredMethods()));
		allMethods.addAll(Arrays.asList(c.getMethods()));
		return allMethods;
	}		

	static public Set<Constructor<?>> collectConstructors(Class<?> c){
		Set<Constructor<?>> allConstructors = new HashSet<Constructor<?>>(Arrays.asList( c.getDeclaredConstructors()));
		allConstructors.addAll(Arrays.asList(c.getConstructors()));
		return allConstructors;
	}		
	
}
