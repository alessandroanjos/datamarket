package com.infinity.datamarket.comum.operacao;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioOperacao extends IRepositorio{

	public void alterar(Operacao operacao)throws AppException;
	public void alterar(Operacao operacao, Collection<EventoOperacaoItemRegistrado> itensPedidoRemovidos)throws AppException;
	public void inserir(Operacao operacao)throws AppException;
	public Operacao consultarPorPK(OperacaoPK pk) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public void excluir(Operacao operacao)throws AppException;
}
