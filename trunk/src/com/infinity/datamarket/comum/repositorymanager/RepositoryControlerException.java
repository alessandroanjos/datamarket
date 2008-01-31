/*
 * Created on 01/12/2006
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
public class RepositoryControlerException extends AppException{

	    RepositoryControlerException(String _message)
	    {
	        super(_message);
	    }

	    RepositoryControlerException(String _message, Exception _exception)
	    {
	        super(_message, _exception);
	    }

	    RepositoryControlerException(Exception _exception)
	    {
	        super(_exception);
	    }
}
