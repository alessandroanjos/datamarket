package com.infinity.datamarket.comum.boleto;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioBoleto extends IRepositorio{

	public void inserir(Boleto Boleto) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
//	public Boleto consultarPorPK(Long id) throws AppException;
//	public Collection consultarTodos() throws AppException;
	public void alterar(Boleto Boleto) throws AppException;
//	public void excluir(Boleto Boleto) throws AppException;
	
}
