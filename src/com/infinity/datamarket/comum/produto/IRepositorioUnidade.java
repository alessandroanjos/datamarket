package com.infinity.datamarket.comum.produto;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioUnidade extends IRepositorio{

	public void inserir(Unidade unidade) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public Unidade consultarPorPK(Long id) throws AppException;
	public Collection consultarTodos() throws AppException;
	public void alterar(Unidade unidade) throws AppException;
	public void excluir(Unidade unidade) throws AppException;
	
}
