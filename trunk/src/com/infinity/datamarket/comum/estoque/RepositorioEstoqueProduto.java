package com.infinity.datamarket.comum.estoque;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioEstoqueProduto extends Repositorio implements IRepositorioEstoqueProduto{
	
	private static RepositorioEstoqueProduto instancia;
	private static Class CLASSE = EstoqueProduto.class;
	private RepositorioEstoqueProduto(){}
	public static RepositorioEstoqueProduto getInstancia(){
		if (instancia == null){
			instancia = new RepositorioEstoqueProduto();
		}
		return instancia;
	}

	public EstoqueProduto consultarPorId(EstoqueProdutoPK pk) throws AppException{
		return (EstoqueProduto) findById(CLASSE, pk);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}
	public void inserir(EstoqueProduto ep) throws AppException{
		insert(ep);
	}
	public void alterar(EstoqueProduto ep) throws AppException{
		update(ep);
	}	


}
