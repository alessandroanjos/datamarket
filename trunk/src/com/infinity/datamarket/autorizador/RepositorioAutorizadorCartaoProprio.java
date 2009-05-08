package com.infinity.datamarket.autorizador;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioAutorizadorCartaoProprio extends Repositorio implements IRepositorioAutorizacaoCartaoProprio{
	private static RepositorioAutorizadorCartaoProprio instancia;
	private static Class CLASSE = AutorizacaoCartaoProprio.class;
	private RepositorioAutorizadorCartaoProprio(){}
	
	public static RepositorioAutorizadorCartaoProprio getInstancia(){
		if (instancia == null){
			instancia = new RepositorioAutorizadorCartaoProprio();
		}
		return instancia;
	}
	
	
	public AutorizacaoCartaoProprio consultarPorId(Long id) throws AppException{
		return (AutorizacaoCartaoProprio) findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
		
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}
	public void inserir(AutorizacaoCartaoProprio autorizacao) throws AppException{
		insert(autorizacao);
	}
	
	public void alterar(AutorizacaoCartaoProprio autorizacao) throws AppException{
		update(autorizacao);
	}
	
	public void excluir(AutorizacaoCartaoProprio autorizacao) throws AppException{
		remove(autorizacao);
	}
}
