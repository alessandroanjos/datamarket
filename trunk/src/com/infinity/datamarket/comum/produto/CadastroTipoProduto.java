package com.infinity.datamarket.comum.produto;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

public class CadastroTipoProduto extends Cadastro{
	
	private static CadastroTipoProduto instancia;
	
	private static final Class CLASSE = TipoProduto.class;
	
	public static CadastroTipoProduto getInstancia(){
		if (instancia == null){
			instancia = new CadastroTipoProduto();			
		}
		return instancia;
	}
	
	public void inserir(TipoProduto tipoProduto) throws AppException{
		getRepositorio().insert(tipoProduto);
		inserirDadoLote(tipoProduto);
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	
	public TipoProduto consultarPorPK(Long id) throws AppException{
		return (TipoProduto) getRepositorio().findById(CLASSE, id);
	}
	
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	
	public void alterar(TipoProduto tipoProsuto) throws AppException{
		getRepositorio().update(tipoProsuto);
		alterarDadoLote(tipoProsuto);
	}
	
	public void excluir(TipoProduto tipoProsuto) throws AppException{
		getRepositorio().remove(tipoProsuto);
		excluirDadoLote(tipoProsuto);
	}
}
