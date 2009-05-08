package com.infinity.datamarket.comum.lote;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioDadoLote extends Repositorio implements IRepositorioDadoLote{
	private static RepositorioDadoLote instancia;
	private static Class CLASSE = DadoLote.class;
	private RepositorioDadoLote(){}
	public static RepositorioDadoLote getInstancia(){
		if (instancia == null){
			instancia = new RepositorioDadoLote();
		}
		return instancia;
	}

	public DadoLote consultarPorId(Long id) throws AppException{
		return (DadoLote) findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}
	public void inserir(DadoLote dadoLote) throws AppException{
		insert(dadoLote);
	}
	
	public void excluir(DadoLote dadoLote) throws AppException{
		remove(dadoLote);
	}	

}
