package com.infinity.datamarket.comum.clientepagamento;

import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioClientePagamento extends Repositorio implements IRepositorioClientePagamento{
	
	private static RepositorioClientePagamento instancia;
	private static Class CLASSE = ClientePagamento.class;
	private RepositorioClientePagamento(){}
	
	public static RepositorioClientePagamento getInstancia(){
		if (instancia == null){
			instancia = new RepositorioClientePagamento();
		}
		return instancia;
	}
	
	public ClientePagamento consultarPorId(Long id) throws AppException{
		return (ClientePagamento) findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	
	public ClientePagamento consultarPorPK(Long id) throws AppException{
		return (ClientePagamento) findById(CLASSE, id);
	}
	
	public Collection consultarTodos() throws AppException{
		PropertyFilter filter = new PropertyFilter();
		filter.setTheClass(CLASSE);
		filter.addOrderByProperty("dataPagamento", PropertyFilter.DESC);
		return filter(filter, false);

	}
	public void inserir(ClientePagamento clientePagamento) throws AppException{
		insert(clientePagamento);
	}
	
	public void alterar(ClientePagamento clientePagamento) throws AppException{
		update(clientePagamento);
	}
	
	public void excluir(ClientePagamento clientePagamento) throws AppException{
		remove(clientePagamento);
	}
	
}
