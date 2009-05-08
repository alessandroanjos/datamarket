package com.infinity.datamarket.comum.pagamento;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioAutorizadora extends IRepositorio{
	public Autorizadora consultarPorId(Long id) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public Collection consultarTodos() throws AppException;
	public void inserir(Autorizadora autorizadora) throws AppException;
	public void alterar(Autorizadora autorizadora) throws AppException;
	public void excluir(Autorizadora autorizadora) throws AppException;
}
