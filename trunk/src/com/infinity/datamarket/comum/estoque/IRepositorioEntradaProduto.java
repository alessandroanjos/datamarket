package com.infinity.datamarket.comum.estoque;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioEntradaProduto extends IRepositorio{


	public EntradaProduto consultarPorId(Long id) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public Collection consultarTodos() throws AppException;
	public void inserir(EntradaProduto entradaProduto) throws AppException;
	public void alterar(EntradaProduto entradaProduto) throws AppException;
	public void excluir(EntradaProduto entradaProduto) throws AppException;
}
