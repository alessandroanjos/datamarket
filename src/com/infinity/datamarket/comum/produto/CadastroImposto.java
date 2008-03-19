package com.infinity.datamarket.comum.produto;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

public class CadastroImposto extends Cadastro{
	
	private static CadastroImposto instancia;
	
	private static final Class CLASSE = Imposto.class;
	
	public static CadastroImposto getInstancia(){
		if (instancia == null){
			instancia = new CadastroImposto();			
		}
		return instancia;
	}
	
	public void inserir(Imposto imposto) throws AppException{
		getRepositorio().insert(imposto);
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	
	public Imposto consultarPorPK(Long id) throws AppException{
		return (Imposto) getRepositorio().findById(CLASSE, id);
	}
	
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	
	public void alterar(Imposto imposto) throws AppException{
		getRepositorio().update(imposto);
	}
	
	public void excluir(Imposto imposto) throws AppException{
		getRepositorio().remove(imposto);
	}
}
