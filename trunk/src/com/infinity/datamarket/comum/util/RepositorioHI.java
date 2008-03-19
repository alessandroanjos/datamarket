/*
 * Created on 07/11/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.infinity.datamarket.comum.util;

import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernate;

/**
 * @author wagner.medeiros
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RepositorioHI extends RepositoryManagerHibernate implements IRepositorio{

	private static RepositorioHI instancia;

	private RepositorioHI(){

	}

	public static RepositorioHI getInstancia(){
		if (instancia == null){
			instancia = new RepositorioHI();
		}
		return instancia;
	}
}
