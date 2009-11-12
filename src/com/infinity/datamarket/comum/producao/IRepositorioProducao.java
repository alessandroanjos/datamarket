package com.infinity.datamarket.comum.producao;

import java.util.Collection;

import com.infinity.datamarket.comum.pagamento.Autorizadora;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioProducao extends IRepositorio{
	public Producao consultarPorId(Long id) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public Collection consultarTodos() throws AppException;
	public void inserir(Producao producao) throws AppException;
	public void alterar(Producao producao) throws AppException;
	public void excluir(Producao producao) throws AppException;
	public Integer consultarMaiorNumeroLote() throws AppException;
}
