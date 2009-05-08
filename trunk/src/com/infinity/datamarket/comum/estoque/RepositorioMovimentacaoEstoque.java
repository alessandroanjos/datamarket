package com.infinity.datamarket.comum.estoque;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioMovimentacaoEstoque extends Repositorio implements IRepositorioMovimentacaoEstoque{
private static RepositorioMovimentacaoEstoque instancia;
	
	private static Class CLASSE = MovimentacaoEstoque.class;
	
	private RepositorioMovimentacaoEstoque(){
		
	}
	
	public static RepositorioMovimentacaoEstoque getInstancia(){
		if (instancia == null){
			instancia = new RepositorioMovimentacaoEstoque();
		}
		return instancia;
	}
	
	
	public MovimentacaoEstoque consultarPorId(Long id) throws AppException{
		return (MovimentacaoEstoque) findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}
	public void inserir(MovimentacaoEstoque movimentacaoEstoque) throws AppException{
		insert(movimentacaoEstoque);
		
	}
	
	public void alterar(MovimentacaoEstoque movimentacaoEstoque) throws AppException{
		update(movimentacaoEstoque);
	}
	public void excluir(MovimentacaoEstoque movimentacaoEstoque) throws AppException{
		remove(movimentacaoEstoque);
	}
	
}
