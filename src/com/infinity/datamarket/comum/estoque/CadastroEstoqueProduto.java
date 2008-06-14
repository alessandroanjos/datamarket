package com.infinity.datamarket.comum.estoque;

import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

public class CadastroEstoqueProduto extends Cadastro{
	
	private static CadastroEstoqueProduto instancia;
	private static Class CLASSE = EstoqueProduto.class;
	private CadastroEstoqueProduto(){}
	public static CadastroEstoqueProduto getInstancia(){
		if (instancia == null){
			instancia = new CadastroEstoqueProduto();
		}
		return instancia;
	}

	public EstoqueProduto consultarPorId(EstoqueProdutoPK pk) throws AppException{
		return (EstoqueProduto) getRepositorio().findById(CLASSE, pk);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	public void inserir(EstoqueProduto ep) throws AppException{
		getRepositorio().insert(ep);
	}
	public void alterar(EstoqueProduto ep) throws AppException{
		getRepositorio().update(ep);
	}	

}
