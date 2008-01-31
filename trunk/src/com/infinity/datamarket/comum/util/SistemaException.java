/*
 * Created on 23/04/2007
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
public class SistemaException extends RuntimeException{
	public SistemaException() {
		super();
    }


    public SistemaException(String message) {
    	super(message);
    }


    public SistemaException(String message, Throwable cause) {
        super(message, cause);
    }


    public SistemaException(Throwable cause) {
        super(cause);
    }
}
