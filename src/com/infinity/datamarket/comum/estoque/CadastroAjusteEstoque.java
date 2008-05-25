package com.infinity.datamarket.comum.estoque;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

public class CadastroAjusteEstoque extends Cadastro{
	
	private static CadastroAjusteEstoque instancia;
	private static Class CLASSE = AjusteEstoque.class;
	private CadastroAjusteEstoque(){}
	public static CadastroAjusteEstoque getInstancia(){
		if (instancia == null){
			instancia = new CadastroAjusteEstoque();
		}
		return instancia;
	}

	public EntradaProduto consultarPorId(Long id) throws AppException{
		return (EntradaProduto) getRepositorio().findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	public void inserir(AjusteEstoque ajuste) throws AppException{
		getRepositorio().insert(ajuste);
	}
	
}
