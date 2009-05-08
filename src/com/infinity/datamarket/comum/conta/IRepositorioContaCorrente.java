package com.infinity.datamarket.comum.conta;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioContaCorrente extends IRepositorio{

	public ContaCorrente consultarPorId(Long id) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public Collection consultarTodos() throws AppException;
	public void inserir(ContaCorrente contaCorrente) throws AppException;
	public void alterar(ContaCorrente contaCorrente) throws AppException;
	public void excluir(ContaCorrente contaCorrente) throws AppException;
	
}
