/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navaile.junitreflect;

import java.lang.reflect.*;
import java.util.*;

/**
 * Class parser was designed to be used in conjunction with JUnit. It allows
 * unit tests of private methods and access to private fields.
 * 
 * @param <C> class to be parsed
 * @author navaile
 */
public class ClassParser<C> {
	
	private C instance;
	private final HashMap<String, Method> methodMap = new HashMap<>();
	private final HashMap<String, Field> fieldMap = new HashMap<>();
	
	/**
	 * Sets C instance, instance must be set on every new instance.
	 * 
	 * @param clazz class
	 */
	public void setInstance(C clazz) {

		this.instance = clazz;

		methodMap.clear();
		for(Method method: clazz.getClass().getDeclaredMethods())
			methodMap.put(method.getName(), method);

		fieldMap.clear();
		for(Field field: clazz.getClass().getDeclaredFields())
			fieldMap.put(field.getName(), field);
	}
	
	/**
	 * Class instance.
	 * 
	 * @return class instance
	 */
	protected C getInstance() {
		return instance;
	}
	
	/**
	 * Retrieves method names.
	 * 
	 * @return a String array of method names
	 */
	protected String[] getMethodNames() {
		return (String[]) methodMap.keySet().toArray(new String[methodMap.size()]);
	}
	
	/**
	 * Retrieves a class method and makes it accessible.
	 * 
	 * @param methodName name of method, case sensitive
	 * @return class method, null if method does not exist
	 */
	protected Method getMethod(String methodName) {
		final Method m = methodMap.get(methodName);
			m.setAccessible(true);
		return m;
	}
	
	/**
	 * Retrieves a list of methods.
	 * 
	 * @param mod method Modifier (e.g. Modifier.PRIVATE)
	 * @return set of methods that met the modifier
	 */
	protected Set<Method> getMethods(int[] mod) {
		
		final Set<Method> set = new HashSet<>(methodMap.size());
		
		final Method[] methodArr = methodMap.values().toArray(new Method[methodMap.size()]);
		
		for (Method method : methodArr) {
			for(int i = 0; i < mod.length; i++) {
				if((mod[i] & method.getModifiers()) != 0)	set.add(method);
			}
		}
		
		return set;
	}
	
	/**
	 * Retrieves field names.
	 * 
	 * @return a String array of field names
	 */
	protected String[] getFieldNames() {
		return (String[]) fieldMap.keySet().toArray(new String[fieldMap.size()]);
	}

	/**
	 * Retrieves a class field.
	 * 
	 * @param fieldName name of field, case sensitive
	 * @return field, null if field does not exist
	 */
	protected Field getField(String fieldName) {
		return fieldMap.get(fieldName);
	}

	/**
	 * Retrieves a class field.
	 * 
	 * @param clazz return class type
	 * @param fieldName field name
	 * @return value, cast to clazz<T> if instance of clazz<T>, else type Object
	 * @throws java.lang.IllegalAccessException
	 * @throws java.lang.InstantiationException
	 */
	protected <T> T getField(Class<T> clazz, String fieldName) throws 
			ClassCastException, IllegalAccessException, InstantiationException {

		Field field = getField(fieldName);
			field.setAccessible(true);
		
		return clazz.cast(field.get(getInstance()));
	}
	
	/**
	 * Sets specific field to value provided, final field will not be modified.
	 * 
	 * @param fieldName field string name
	 * @param value new value, must by of the same class type
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException 
	 */
	public void setField(String fieldName, Object value) throws
			NoSuchFieldException, IllegalAccessException, ClassCastException {

		Field field = getField(fieldName);
			
		if(Modifier.isFinal(field.getModifiers())) {
			throw new IllegalAccessException(fieldName + " field is final.");
		}
		else if(Modifier.isStatic(field.getModifiers())) {
			setStaticField(instance.getClass(), fieldName, value);
		}
		else {
			field.setAccessible(true);
			field.set(getInstance(), value);
		}
	}
	
	/**
	 * Set static field to new value, final field will not be modified.
	 * 
	 * @param clazz class name that contains the static field
	 * @param fieldName field name
	 * @param newValue new value, must be of same type
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException 
	 */
	public static void setStaticField(Class clazz, String fieldName, Object newValue)
			throws NoSuchFieldException, IllegalAccessException {

		Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);

		field.set(null, newValue);
    }
	
	/**
	 * Invoke method.
	 * 
	 * @param <T>
	 * @param clazz method return type T
	 * @param methodName method name
	 * @param args the arguments used for the method call
	 * @return the result of dispatching the method represented by this object on obj with parameters args
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws ClassCastException
	 */
	protected <T extends Object> T T_method_args(Class<T> clazz, String methodName, Object... args) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, ClassCastException {

		final Object obj = getMethod(methodName).invoke(getInstance(), args);
		return clazz.cast(obj);
	}
	
	/**
	 * Invoke method with arguments but no return value.
	 * 
	 * @param methodName method name
	 * @param args the arguments used for the method call
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws ClassCastException
	 */
	protected void method_args(String methodName, Object... args) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, ClassCastException {

		getMethod(methodName).invoke(getInstance(), args);
	}
	
	/**
	 * Invoke method with no arguments and no return value.
	 * 
	 * @param methodName method name
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws ClassCastException 
	 */
	protected void method(String methodName) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, ClassCastException {
		
		getMethod(methodName).invoke(getInstance(), null);
	}

	/**
	 * Builds an Object array using the provided arguments.
	 * 
	 * @param args method arguments
	 * @return Object[] that contains the provided arguments
	 */
	protected Object[] args(Object... args) {
		
		ArrayList list = new ArrayList(args.length);
			list.addAll(Arrays.asList(args));
		
		return list.toArray();
	}
}
