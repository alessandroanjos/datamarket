package com.infinity.datamarket.comum.autorizadora;

import java.util.Collection;

import com.infinity.datamarket.comum.cliente.CadastroCliente;
import com.infinity.datamarket.comum.cliente.Cliente;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

public class CadastroAutorizadora extends Cadastro {
	private static CadastroAutorizadora instancia;
	private static Class CLASSE = Autorizadora.class;
	private CadastroAutorizadora(){}
	
	public static CadastroAutorizadora getInstancia(){
		if (instancia == null){
			instancia = new CadastroAutorizadora();
		}
		return instancia;
	}
	
	public Autorizadora consultarPorId(Long id) throws AppException{
		return (Autorizadora) getRepositorio().findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	
	public Autorizadora consultarPorPK(Long id) throws AppException{
		return (Autorizadora) getRepositorio().findById(CLASSE, id);
	}
	
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	public void inserir(Autorizadora autorizadora) throws AppException{
		getRepositorio().insert(autorizadora);
	}
	
	public void alterar(Autorizadora autorizadora) throws AppException{
		getRepositorio().update(autorizadora);
	}
	
	public void excluir(Autorizadora autorizadora) throws AppException{
		getRepositorio().remove(autorizadora);
	}
}
