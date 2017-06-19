
package com.navaile.junitreflect;

import java.lang.reflect.*;
import org.junit.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Class parser tests.
 *
 * @author navaile
 */
public class ClassParserTest extends ClassParser<TestClass> {
	
	private final TestClass testClass = new TestClass();
	
	@Before
	public void ini() {
		setInstance(testClass);
	}
	
	@Test
	public void methodCount() {
		Assert.assertTrue(getMethodNames().length == 3);
	}
	
	@Test
	public void getMethod() {
		Assert.assertTrue(getMethod("methTest_1").getName().equalsIgnoreCase("methTest_1"));
	}
	
	@Test
	public void getMethodTypes() {
		Assert.assertTrue(getMethods(new int[] {Modifier.PUBLIC}).size() == 2);
		Assert.assertTrue(getMethods(new int[] {Modifier.PRIVATE}).size() == 1);
		Assert.assertTrue(getMethods(new int[] {Modifier.PRIVATE, Modifier.STATIC}).size() == 1);
	}
	
	@Test
	public void getFieldNamesTest() {
		Assert.assertTrue(getFieldNames().length == 4);
	}
	
	@Test
	public void getFieldTest() {
		Assert.assertTrue(getField("STAT_FINAL").getName().equalsIgnoreCase("STAT_FINAL"));
	}
	
	@Test
	public void getFieldTypeTest() {
		
		try {

			Assert.assertTrue(getField(String.class, "STAT_FINAL") instanceof String);

		} catch (ClassCastException ex) {
			Assert.fail("Class cast exception.");
		} catch (IllegalAccessException ex) {
			Assert.fail("Illegal access exception.");
		} catch (InstantiationException ex) {
			Assert.fail("Instantiation exception.");
		}
	}
	
	@Test
	public void setFieldTest() {
		
		try {

			setField("INST_VAR", "new value");
			String str = getField(String.class, "INST_VAR");

			Assert.assertTrue(str.equalsIgnoreCase("new value"));

		} catch (NoSuchFieldException ex) {
			Assert.fail("No such field exception.");
		} catch (ClassCastException ex) {
			Assert.fail("Class cast exception.");
		} catch (IllegalAccessException ex) {
			Assert.fail("Illegal access exception.");
		} catch (InstantiationException ex) {
			Assert.fail("Instantiation exception.");
		}
	}
	
	@Test
	public void setStaticFieldTest() {
		
		try {

			setStaticField(TestClass.class, "STAT_FINAL", "new STAT_FINAL value");
			String str = getField(String.class, "STAT_FINAL");

			Assert.assertTrue(str.equalsIgnoreCase("new STAT_FINAL value"));

		} catch (NoSuchFieldException ex) {
			Assert.fail("No such field exception.");
		} catch (ClassCastException ex) {
			Assert.fail("Class cast exception.");
		} catch (IllegalAccessException ex) {
			Assert.fail("Illegal access exception.");
		} catch (InstantiationException ex) {
			Assert.fail("Instantiation exception.");
		}
	}

	@Test
	public void retMethodArgs() {
		
		try {
			Assert.assertTrue(T_method_args(Integer.class, "add", new Object[] {11, 27}) == 38);
		} catch (IllegalAccessException ex) {
			Assert.fail("Illegal access exception.");
		} catch (IllegalArgumentException ex) {
			Assert.fail("Illegal argument exception.");
		} catch (InvocationTargetException ex) {
			Assert.fail("Invocation target exception.");
		} catch (ClassCastException ex) {
			Assert.fail("Class cast exception.");
		}
	}

	@Test (expected = NullPointerException.class)
	public void methodArgs() {
		
		try {
			
			method_args("addww", new Object[] {11, 27});

		} catch (IllegalAccessException ex) {
			Assert.fail("Illegal access exception.");
		} catch (IllegalArgumentException ex) {
			Assert.fail("Illegal argument exception.");
		} catch (InvocationTargetException ex) {
			Assert.fail("Invocation target exception.");
		} catch (ClassCastException ex) {
			Assert.fail("Class cast exception.");
		}
	}

	@Test
	public void method() {
		try {
			method("methTest_1");
		} catch (IllegalAccessException ex) {
			Assert.fail("Illegal access exception.");
		} catch (IllegalArgumentException ex) {
			Assert.fail("Illegal argument exception.");
		} catch (InvocationTargetException ex) {
			Assert.fail("Invocation target exception.");
		} catch (ClassCastException ex) {
			Assert.fail("Class cast exception.");
		}
	}

	@Test
	public void argsTest() {
		Assert.assertTrue(args("one", "two", "three").length == 3);
	}
}
