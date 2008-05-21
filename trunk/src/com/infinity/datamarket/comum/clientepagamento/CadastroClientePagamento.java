package com.infinity.datamarket.comum.clientepagamento;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

public class CadastroClientePagamento extends Cadastro {
	private static CadastroClientePagamento instancia;
	private static Class CLASSE = ClientePagamento.class;
	private CadastroClientePagamento(){}
	
	public static CadastroClientePagamento getInstancia(){
		if (instancia == null){
			instancia = new CadastroClientePagamento();
		}
		return instancia;
	}
	
	public ClientePagamento consultarPorId(Long id) throws AppException{
		return (ClientePagamento) getRepositorio().findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	
	public ClientePagamento consultarPorPK(Long id) throws AppException{
		return (ClientePagamento) getRepositorio().findById(CLASSE, id);
	}
	
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	public void inserir(ClientePagamento clientePagamento) throws AppException{
		getRepositorio().insert(clientePagamento);
	}
	
	public void alterar(ClientePagamento clientePagamento) throws AppException{
		getRepositorio().update(clientePagamento);
	}
	
	public void excluir(ClientePagamento clientePagamento) throws AppException{
		getRepositorio().remove(clientePagamento);
	}
}
