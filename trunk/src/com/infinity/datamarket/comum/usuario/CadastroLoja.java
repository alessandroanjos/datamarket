package com.infinity.datamarket.comum.usuario;

import java.util.Collection;

import com.infinity.datamarket.comum.produto.TipoProduto;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

public class CadastroLoja extends Cadastro{
	private static CadastroLoja instancia;
	private static Class CLASSE = Loja.class;
	private CadastroLoja(){}
	public static CadastroLoja getInstancia(){
		if (instancia == null){
			instancia = new CadastroLoja();
		}
		return instancia;
	}

	public Loja consultarPorId(Long id) throws AppException{
		return (Loja) getRepositorio().findById(CLASSE, id);
	}
	
	public void inserir(Loja loja) throws AppException{
		getRepositorio().insert(loja);
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	
	public Loja consultarPorPK(Long id) throws AppException{
		return (Loja) getRepositorio().findById(CLASSE, id);
	}
	
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	
	public void alterar(Loja loja) throws AppException{
		getRepositorio().update(loja);
	}
	
	public void excluir(Loja loja) throws AppException{
		getRepositorio().remove(loja);
	}
}
