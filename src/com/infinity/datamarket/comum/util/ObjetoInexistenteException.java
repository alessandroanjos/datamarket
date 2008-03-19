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
public class ObjetoInexistenteException extends AppException {
	public ObjetoInexistenteException(Exception e){
		super(e);
	}
	public ObjetoInexistenteException(String msn, Exception e){
		super(msn,e);
	}
	public ObjetoInexistenteException(String msn){
		super(msn);
	}
}
