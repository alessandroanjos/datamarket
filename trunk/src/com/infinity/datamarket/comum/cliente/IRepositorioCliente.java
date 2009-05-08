package com.infinity.datamarket.comum.cliente;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioCliente extends IRepositorio{

	public Cliente consultarPorId(Long id) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public Cliente consultarPorPK(Long id) throws AppException;
	public Collection consultarTodos() throws AppException;
	public void inserir(Cliente cliente) throws AppException;
	public void alterar(Cliente cliente) throws AppException;
	public void excluir(Cliente cliente) throws AppException;
	
}
