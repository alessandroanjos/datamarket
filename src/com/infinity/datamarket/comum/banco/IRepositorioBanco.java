package com.infinity.datamarket.comum.banco;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioBanco extends IRepositorio{
	public Banco consultarPorId(Long id) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public Collection consultarTodos() throws AppException;
	public void inserir(Banco banco) throws AppException;
	public void alterar(Banco banco) throws AppException;
	public void excluir(Banco banco) throws AppException;
}
