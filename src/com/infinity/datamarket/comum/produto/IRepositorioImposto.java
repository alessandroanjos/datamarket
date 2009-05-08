package com.infinity.datamarket.comum.produto;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioImposto extends IRepositorio{

	public void inserir(Imposto imposto) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public Imposto consultarPorPK(Long id) throws AppException;
	public Collection consultarTodos() throws AppException;
	public void alterar(Imposto imposto) throws AppException;
	public void excluir(Imposto imposto) throws AppException;
	
}
