/*
 * Created on 16/11/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.infinity.datamarket.comum.util;

import java.io.Serializable;

import com.infinity.datamarket.comum.lote.DadoLote;


/**
 * @author wagner.medeiros
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Cadastro {

	public IRepositorio getRepositorio(String repositorio){
		return ServiceLocator.getInstancia().getRepositoprio(repositorio);
	}
	
}
