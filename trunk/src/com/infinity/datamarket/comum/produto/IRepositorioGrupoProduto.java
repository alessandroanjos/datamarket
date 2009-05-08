package com.infinity.datamarket.comum.produto;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioGrupoProduto extends IRepositorio{
	public void inserir(GrupoProduto grupoProduto) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public GrupoProduto consultarPorPK(Long id) throws AppException;
	public Collection consultarTodos() throws AppException;
	public void alterar(GrupoProduto grupoProduto) throws AppException;
	public void excluir(GrupoProduto grupoProduto) throws AppException;
		
}
