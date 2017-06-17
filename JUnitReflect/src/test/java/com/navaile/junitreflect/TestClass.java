/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navaile.junitreflect;

/**
 *
 * @author navaile
 */
public class TestClass {
	
	private static String STAT_FINAL = "original private static String STAT_FINAL";
	private final String FINAL = "original private final String FINAL";
	private String INST_VAR = "original private String INST_VAR";
	private final String FINAL_FINAL = "FINAL_FINAL";
	
	public TestClass() {}
	
	public String methTest(String test) {
		return "method test: " + test;
	}
	
	public void methTest_1() {
	}
	
	private static int add(int one, int two) {
		return one + two;
	}
}
