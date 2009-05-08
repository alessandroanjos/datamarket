package com.infinity.datamarket.comum.financeiro;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioGrupoLancamento extends IRepositorio{

	public GrupoLancamento consultarPorId(Long id) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public Collection consultarTodos() throws AppException;
	public void inserir(GrupoLancamento grupo) throws AppException;
	public void alterar(GrupoLancamento grupo) throws AppException;
	public void excluir(GrupoLancamento grupo) throws AppException;

}
