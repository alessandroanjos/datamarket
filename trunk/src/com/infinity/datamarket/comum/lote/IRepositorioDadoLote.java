package com.infinity.datamarket.comum.lote;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioDadoLote extends IRepositorio{
	public DadoLote consultarPorId(Long id) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public Collection consultarTodos() throws AppException;
	public void inserir(DadoLote dadoLote) throws AppException;
	public void excluir(DadoLote dadoLote) throws AppException;

}
