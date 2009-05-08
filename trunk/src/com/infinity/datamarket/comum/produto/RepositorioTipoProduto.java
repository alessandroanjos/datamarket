package com.infinity.datamarket.comum.produto;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioTipoProduto extends Repositorio implements IRepositorioTipoProduto{

private static RepositorioTipoProduto instancia;
	
	private static final Class CLASSE = TipoProduto.class;
	
	public static RepositorioTipoProduto getInstancia(){
		if (instancia == null){
			instancia = new RepositorioTipoProduto();			
		}
		return instancia;
	}
	
	public void inserir(TipoProduto tipoProduto) throws AppException{
		insert(tipoProduto);
		inserirDadoLote(tipoProduto);
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	
	public TipoProduto consultarPorPK(Long id) throws AppException{
		return (TipoProduto) findById(CLASSE, id);
	}
	
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}
	
	public void alterar(TipoProduto tipoProsuto) throws AppException{
		update(tipoProsuto);
		alterarDadoLote(tipoProsuto);
	}
	
	public void excluir(TipoProduto tipoProsuto) throws AppException{
		remove(tipoProsuto);
		excluirDadoLote(tipoProsuto);
	}
	
}
