package com.infinity.datamarket.comum.financeiro;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioBaixaLancamento extends IRepositorio{

	public BaixaLancamento consultarPorId(Long id) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public Collection consultarTodos() throws AppException;
	public void inserir(BaixaLancamento baixaLancamento) throws AppException;
	public void alterar(BaixaLancamento baixaLancamento) throws AppException;
	public void excluir(BaixaLancamento baixaLancamento) throws AppException;
	
}
