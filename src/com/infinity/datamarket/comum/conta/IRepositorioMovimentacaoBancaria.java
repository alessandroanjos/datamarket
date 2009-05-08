package com.infinity.datamarket.comum.conta;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioMovimentacaoBancaria extends IRepositorio{

	public MovimentacaoBancaria consultarPorId(Long id) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public Collection consultarTodos() throws AppException;
	public void inserir(MovimentacaoBancaria movimentacaoBancaria) throws AppException;
	
	public void alterar(MovimentacaoBancaria movimentacaoBancaria) throws AppException;
	public void excluir(MovimentacaoBancaria movimentacaoBancaria) throws AppException;
	
}
