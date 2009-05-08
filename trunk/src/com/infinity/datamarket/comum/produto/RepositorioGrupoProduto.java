package com.infinity.datamarket.comum.produto;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioGrupoProduto extends Repositorio implements IRepositorioGrupoProduto{
	
private static RepositorioGrupoProduto instancia;
	
	private static final Class CLASSE = GrupoProduto.class;
	
	public static RepositorioGrupoProduto getInstancia(){
		if (instancia == null){
			instancia = new RepositorioGrupoProduto();			
		}
		return instancia;
	}
	
	public void inserir(GrupoProduto grupoProduto) throws AppException{		
		insert(grupoProduto);
		inserirDadoLote(grupoProduto);
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	
	public GrupoProduto consultarPorPK(Long id) throws AppException{
		return (GrupoProduto) findById(CLASSE, id);
	}
	
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}
	
	public void alterar(GrupoProduto grupoProduto) throws AppException{
		
		update(grupoProduto);
		alterarDadoLote(grupoProduto);
				
	}
	
	public void excluir(GrupoProduto grupoProduto) throws AppException{
		remove(grupoProduto);
		excluirDadoLote(grupoProduto);
	}


}
