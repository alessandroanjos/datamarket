package com.infinity.datamarket.comum.transacao;

import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioTransacao extends IRepositorio{
	public void inserir(Transacao trans) throws AppException;
	public Transacao consultarPorPK(TransacaoPK pk) throws AppException;
	public void atualizar(Transacao trans) throws AppException;
	public void atualizar(Transacao trans, Collection<EventoItemRegistrado> itensRegistradosRemovidos) throws AppException;	
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public void inserirCliente(ClienteTransacao cli) throws AppException;
	public void atualizarCliente(ClienteTransacao cli) throws AppException;
	public ClienteTransacao consultarClienteTransacaoPorID(String id) throws AppException;
	public Collection consultarClienteTransacao(IPropertyFilter filter) throws AppException;
}
