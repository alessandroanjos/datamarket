package com.infinity.datamarket.comum.boleto;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioArquivosProcessado extends IRepositorio{

	public void inserir(ArquivosProcessado ArquivosProcessado) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
//	public ArquivosProcessado consultarPorPK(Long id) throws AppException;
//	public Collection consultarTodos() throws AppException;
	public void alterar(ArquivosProcessado ArquivosProcessado) throws AppException;
//	public void excluir(ArquivosProcessado ArquivosProcessado) throws AppException;
	
}
