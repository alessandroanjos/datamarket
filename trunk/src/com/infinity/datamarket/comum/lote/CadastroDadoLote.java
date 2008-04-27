package com.infinity.datamarket.comum.lote;

import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

public class CadastroDadoLote extends Cadastro{
	
	private static CadastroDadoLote instancia;
	private static Class CLASSE = DadoLote.class;
	private CadastroDadoLote(){}
	public static CadastroDadoLote getInstancia(){
		if (instancia == null){
			instancia = new CadastroDadoLote();
		}
		return instancia;
	}

	public DadoLote consultarPorId(Long id) throws AppException{
		return (DadoLote) getRepositorio().findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	public void inserir(DadoLote dadoLote) throws AppException{
		getRepositorio().insert(dadoLote);
	}
	
	public void excluir(DadoLote dadoLote) throws AppException{
		getRepositorio().remove(dadoLote);
	}	

}
