package com.infinity.datamarket.comum.estoque;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;

public class CadastroEstoqueProduto extends Cadastro{
	
	private static CadastroEstoqueProduto instancia;
//	private static Class CLASSE = EstoqueProduto.class;
	private CadastroEstoqueProduto(){}
	public static CadastroEstoqueProduto getInstancia(){
		if (instancia == null){
			instancia = new CadastroEstoqueProduto();
		}
		return instancia;
	}

	
	public IRepositorioEstoqueProduto getRepositorio() {
		return (IRepositorioEstoqueProduto) super.getRepositorio(IRepositorio.REPOSITORIO_ESTOQUE_PRODUTO);
	}
	
	public EstoqueProduto consultarPorId(EstoqueProdutoPK pk) throws AppException{
		return (EstoqueProduto) getRepositorio().consultarPorId(pk);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}
	public void inserir(EstoqueProduto ep) throws AppException{
		getRepositorio().inserir(ep);
	}
	public void alterar(EstoqueProduto ep) throws AppException{
		getRepositorio().alterar(ep);
	}	

}
