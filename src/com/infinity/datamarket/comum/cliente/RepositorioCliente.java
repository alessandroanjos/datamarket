package com.infinity.datamarket.comum.cliente;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioCliente extends Repositorio implements IRepositorioCliente{
	private static RepositorioCliente instancia;
	private static Class CLASSE = Cliente.class;
	private RepositorioCliente(){}
	
	public static RepositorioCliente getInstancia(){
		if (instancia == null){
			instancia = new RepositorioCliente();
		}
		return instancia;
	}
	
	public Cliente consultarPorId(Long id) throws AppException{
		return (Cliente) findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	
	public Cliente consultarPorPK(Long id) throws AppException{
		return (Cliente) findById(CLASSE, id);
	}
	
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}
	public void inserir(Cliente cliente) throws AppException{
		insert(cliente);
	}
	
	public void alterar(Cliente cliente) throws AppException{
		update(cliente);
	}
		
	public void excluir(Cliente cliente) throws AppException{
		remove(cliente);
	}
}
