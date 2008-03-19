/*
 * Created on 03/11/2006
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
public class ObjectExistentException extends AppException {

	public ObjectExistentException(Exception e) {
		super(e);
	}

	public ObjectExistentException(String msn, Exception e) {
		super(msn, e);
	}

	public ObjectExistentException(String msn) {
		super(msn);
	}

}
