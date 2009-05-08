package com.infinity.datamarket.comum.produto;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;

public interface IRepositorioTipoProduto {

	public void inserir(TipoProduto tipoProduto) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public TipoProduto consultarPorPK(Long id) throws AppException;
	public Collection consultarTodos() throws AppException;
	public void alterar(TipoProduto tipoProsuto) throws AppException;
	public void excluir(TipoProduto tipoProsuto) throws AppException;
	
}
