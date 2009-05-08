package com.infinity.datamarket.comum.componente;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioComponente extends IRepositorio{
	public Componente consultarPorId(Long id) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public Collection consultarTodos() throws AppException;
	public void inserir(Componente componente) throws AppException;
	public void alterar(Componente componente) throws AppException;
	public void excluir(Componente componente) throws AppException;
}
