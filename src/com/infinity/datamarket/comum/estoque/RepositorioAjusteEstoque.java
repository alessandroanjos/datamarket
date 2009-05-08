package com.infinity.datamarket.comum.estoque;

import java.util.Collection;

import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;
import com.sun.mail.iap.Response;

public class RepositorioAjusteEstoque extends Repositorio implements IRepositorioAjusteEstoque{
	
	private static RepositorioAjusteEstoque instancia;
	private static Class CLASSE = AjusteEstoque.class;
	private RepositorioAjusteEstoque(){}
	public static RepositorioAjusteEstoque getInstancia(){
		if (instancia == null){
			instancia = new RepositorioAjusteEstoque();
		}
		return instancia;
	}

	public AjusteEstoque consultarPorId(Long id) throws AppException{
		return (AjusteEstoque) findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}
	public void inserir(AjusteEstoque ajuste) throws AppException{
		insert(ajuste);
	}

}
