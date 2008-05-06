/*
 * Created on 01/11/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.infinity.datamarket.comum.repositorymanager;

import com.infinity.datamarket.comum.util.AppException;

/**
 * @author wagner.medeiros
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class InvalidObjectException extends AppException {

	public InvalidObjectException(Exception e) {
		super(e);
	}

	public InvalidObjectException(String msn, Exception e) {
		super(msn,e);
	}

	public InvalidObjectException(String msn) {
		super(msn);
	}

}
