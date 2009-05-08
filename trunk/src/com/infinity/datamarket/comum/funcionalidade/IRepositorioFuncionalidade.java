package com.infinity.datamarket.comum.funcionalidade;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;

public interface IRepositorioFuncionalidade {
	public Funcionalidade consultarPorId(Long id) throws AppException;
	public void inserir(Funcionalidade funcionalidade) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public Funcionalidade consultarPorPK(Long id) throws AppException;
	public Collection consultarTodos() throws AppException;
	public void alterar(Funcionalidade funcionalidade) throws AppException;
	public void excluir(Funcionalidade funcionalidade) throws AppException;
}
