package com.infinity.datamarket.comum.estoque;

import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

public class CadastroMovimentacaoEstoque extends Cadastro{

	private static CadastroMovimentacaoEstoque instancia;
	
	private static Class CLASSE = MovimentacaoEstoque.class;
	
	private CadastroMovimentacaoEstoque(){
		
	}
	
	public static CadastroMovimentacaoEstoque getInstancia(){
		if (instancia == null){
			instancia = new CadastroMovimentacaoEstoque();
		}
		return instancia;
	}
	
	
	public MovimentacaoEstoque consultarPorId(Long id) throws AppException{
		return (MovimentacaoEstoque) getRepositorio().findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	public void inserir(MovimentacaoEstoque movimentacaoEstoque) throws AppException{
		getRepositorio().insert(movimentacaoEstoque);
	}
	
	public void alterar(EntradaProduto entradaProduto) throws AppException{
		getRepositorio().update(entradaProduto);
	}
	public void excluir(EntradaProduto entradaProduto) throws AppException{
		getRepositorio().remove(entradaProduto);
	}
	
	
	
}
