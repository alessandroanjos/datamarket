package com.infinity.datamarket.comum.estoque;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;

public interface IRepositorioMovimentacaoEstoque {
	public MovimentacaoEstoque consultarPorId(Long id) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public Collection consultarTodos() throws AppException;
	public void inserir(MovimentacaoEstoque movimentacaoEstoque) throws AppException;
	
	public void alterar(MovimentacaoEstoque movimentacaoEstoque) throws AppException;
	public void excluir(MovimentacaoEstoque movimentacaoEstoque) throws AppException;
	
}
