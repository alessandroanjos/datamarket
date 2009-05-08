package com.infinity.datamarket.comum.usuario;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioLoja extends IRepositorio{
	
	public Loja consultarPorId(Long id) throws AppException;
	public void inserir(Loja loja) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public Loja consultarPorPK(Long id) throws AppException;
	public Collection consultarTodos() throws AppException;
	public void alterar(Loja loja) throws AppException;
	public void excluir(Loja loja) throws AppException;
}
