package com.infinity.datamarket.comum.clientepagamento;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioClientePagamento extends IRepositorio{
	
	public ClientePagamento consultarPorId(Long id) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public ClientePagamento consultarPorPK(Long id) throws AppException;
	public Collection consultarTodos() throws AppException;
	public void inserir(ClientePagamento clientePagamento) throws AppException;
	public void alterar(ClientePagamento clientePagamento) throws AppException;
	public void excluir(ClientePagamento clientePagamento) throws AppException;

}
