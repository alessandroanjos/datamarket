package com.infinity.datamarket.autorizador;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

public class CadastroAutorizacaoCartaoProprio extends Cadastro {
	private static CadastroAutorizacaoCartaoProprio instancia;
	private static Class CLASSE = AutorizacaoCartaoProprio.class;
	private CadastroAutorizacaoCartaoProprio(){}
	
	public static CadastroAutorizacaoCartaoProprio getInstancia(){
		if (instancia == null){
			instancia = new CadastroAutorizacaoCartaoProprio();
		}
		return instancia;
	}
	
	public AutorizacaoCartaoProprio consultarPorId(Long id) throws AppException{
		return (AutorizacaoCartaoProprio) getRepositorio().findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
		
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	public void inserir(AutorizacaoCartaoProprio autorizacao) throws AppException{
		getRepositorio().insert(autorizacao);
	}
	
	public void alterar(AutorizacaoCartaoProprio autorizacao) throws AppException{
		getRepositorio().update(autorizacao);
	}
	
	public void excluir(AutorizacaoCartaoProprio autorizacao) throws AppException{
		getRepositorio().remove(autorizacao);
	}
}
