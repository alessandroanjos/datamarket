package com.infinity.datamarket.comum.cliente;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

public class CadastroCliente extends Cadastro {
	private static CadastroCliente instancia;
	private static Class CLASSE = Cliente.class;
	private CadastroCliente(){}
	
	public static CadastroCliente getInstancia(){
		if (instancia == null){
			instancia = new CadastroCliente();
		}
		return instancia;
	}
	
	public Cliente consultarPorId(Long id) throws AppException{
		return (Cliente) getRepositorio().findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	public void inserir(Cliente cliente) throws AppException{
		getRepositorio().insert(cliente);
	}
	
	public void alterar(Cliente cliente) throws AppException{
		getRepositorio().update(cliente);
	}
	
	public void excluir(Cliente cliente) throws AppException{
		getRepositorio().remove(cliente);
	}
}
