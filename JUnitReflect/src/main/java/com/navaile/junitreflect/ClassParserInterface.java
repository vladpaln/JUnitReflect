/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navaile.junitreflect;

/**
 * JUnit class parser interface.
 * 
 * @param <C> class to be parsed
 * @author navaile
 */
public interface ClassParserInterface<C> {
	
	public void setInstance(C clazz);
}
