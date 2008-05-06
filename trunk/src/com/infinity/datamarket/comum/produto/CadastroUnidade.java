package com.infinity.datamarket.comum.produto;

import java.util.Collection;

import com.infinity.datamarket.comum.lote.DadoLote;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

public class CadastroUnidade extends Cadastro{
	
	private static CadastroUnidade instancia;
	
	private static final Class CLASSE = Unidade.class;
	
	public static CadastroUnidade getInstancia(){
		if (instancia == null){
			instancia = new CadastroUnidade();			
		}
		return instancia;
	}
	
	public void inserir(Unidade unidade) throws AppException{
		getRepositorio().insert(unidade);
		inserirDadoLote(unidade);
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	
	public Unidade consultarPorPK(Long id) throws AppException{
		return (Unidade) getRepositorio().findById(CLASSE, id);
	}
	
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	
	public void alterar(Unidade unidade) throws AppException{
		getRepositorio().update(unidade);
		alterarDadoLote(unidade);
	}
	
	public void excluir(Unidade unidade) throws AppException{
		getRepositorio().remove(unidade);
		excluirDadoLote(unidade);
	}
}
