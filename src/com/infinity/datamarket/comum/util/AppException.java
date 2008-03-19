/*
 * Created on 01/11/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.infinity.datamarket.comum.util;

/**
 * @author wagner.medeiros
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AppException extends Exception{
	public AppException(Exception e){
		super(e);
	}
	public AppException(String msn, Exception e){
		super(msn,e);
	}
	public AppException(String msn){
		super(msn);
	}
}
