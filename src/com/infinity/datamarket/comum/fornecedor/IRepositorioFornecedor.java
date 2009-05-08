package com.infinity.datamarket.comum.fornecedor;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioFornecedor extends IRepositorio{
	public Fornecedor consultarPorId(Long id) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public Collection consultarTodos() throws AppException;
	public void inserir(Fornecedor fornecedor) throws AppException;
	public void alterar(Fornecedor fornecedor) throws AppException;
	public void excluir(Fornecedor fornecedor) throws AppException;
}
