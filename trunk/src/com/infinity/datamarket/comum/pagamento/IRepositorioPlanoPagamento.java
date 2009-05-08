package com.infinity.datamarket.comum.pagamento;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioPlanoPagamento extends IRepositorio{

	public PlanoPagamento consultarPorId(Long id) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public Collection consultarTodos() throws AppException;
	public void inserir(PlanoPagamento planoPagamento) throws AppException;	
	public void alterar(PlanoPagamento planoPagamento) throws AppException;
	public void excluir(PlanoPagamento planoPagamento) throws AppException;
	public Collection consultarTodosPreDatado() throws AppException;
	
}
