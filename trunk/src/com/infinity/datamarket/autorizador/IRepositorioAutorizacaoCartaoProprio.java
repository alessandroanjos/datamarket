package com.infinity.datamarket.autorizador;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioAutorizacaoCartaoProprio extends IRepositorio{
	public AutorizacaoCartaoProprio consultarPorId(Long id) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public Collection consultarTodos() throws AppException;
	public void inserir(AutorizacaoCartaoProprio autorizacao) throws AppException;
	public void alterar(AutorizacaoCartaoProprio autorizacao) throws AppException;
	public void excluir(AutorizacaoCartaoProprio autorizacao) throws AppException;
}
