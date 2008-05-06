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

	 public IRepositorio getRepositorio(){
	 	return RepositorioHI.getInstancia();
	}
	 
	private void manterDadoLote(Serializable obj, String operacao) throws AppException{
		Parametro p = (Parametro) getRepositorio().findById(Parametro.class, "LOTE");
		int numeroProximoLote = Integer.parseInt(p.getValor()) + 1;
		DadoLote dado = new DadoLote();
		dado.setLote(numeroProximoLote);
		dado.setDado(obj);
		dado.setOperacao(operacao);
		getRepositorio().insert(dado);
	}
	
	public void inserirDadoLote(Serializable obj) throws AppException{
		manterDadoLote(obj, DadoLote.INSERIR);
	}
	
	public void alterarDadoLote(Serializable obj) throws AppException{
		manterDadoLote(obj, DadoLote.ALTERAR);
	}
	
	public void excluirDadoLote(Serializable obj) throws AppException{
		manterDadoLote(obj, DadoLote.EXCLUIR);
	}
	

}
