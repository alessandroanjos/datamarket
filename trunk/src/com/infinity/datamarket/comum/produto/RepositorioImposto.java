package com.infinity.datamarket.comum.produto;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioImposto extends Repositorio implements IRepositorioImposto{
	
	private static RepositorioImposto instancia;
	
	private static final Class CLASSE = Imposto.class;
	
	public static RepositorioImposto getInstancia(){
		if (instancia == null){
			instancia = new RepositorioImposto();			
		}
		return instancia;
	}
	
	public void inserir(Imposto imposto) throws AppException{
		insert(imposto);
		inserirDadoLote(imposto);
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	
	public Imposto consultarPorPK(Long id) throws AppException{
		return (Imposto) findById(CLASSE, id);
	}
	
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}
	
	public void alterar(Imposto imposto) throws AppException{
		update(imposto);
		alterarDadoLote(imposto);
	}
	
	public void excluir(Imposto imposto) throws AppException{
		remove(imposto);
		excluirDadoLote(imposto);
	}
}
