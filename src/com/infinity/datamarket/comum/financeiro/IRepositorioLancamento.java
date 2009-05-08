package com.infinity.datamarket.comum.financeiro;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;

public interface IRepositorioLancamento {


	public Lancamento consultarPorId(Long id) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public Collection consultarTodos() throws AppException;
	public void inserir(Lancamento lancamento) throws AppException;
	public void alterar(Lancamento lancamento) throws AppException;
	public void excluir(Lancamento lancamento) throws AppException;
	
}
