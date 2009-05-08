package com.infinity.datamarket.comum.estoque;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioEstoqueProduto extends IRepositorio{

	public EstoqueProduto consultarPorId(EstoqueProdutoPK pk) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public Collection consultarTodos() throws AppException;
	public void inserir(EstoqueProduto ep) throws AppException;
	public void alterar(EstoqueProduto ep) throws AppException;
	
}
