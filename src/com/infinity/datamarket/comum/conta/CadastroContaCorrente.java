package com.infinity.datamarket.comum.conta;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

public class CadastroContaCorrente extends Cadastro{
	private static CadastroContaCorrente instancia;
	private static Class CLASSE = ContaCorrente.class;
	private CadastroContaCorrente(){}
	public static CadastroContaCorrente getInstancia(){
		if (instancia == null){
			instancia = new CadastroContaCorrente();
		}
		return instancia;
	}

	public ContaCorrente consultarPorId(Long id) throws AppException{
		return (ContaCorrente) getRepositorio().findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	public void inserir(ContaCorrente contaCorrente) throws AppException{
		getRepositorio().insert(contaCorrente);
	}
	
	public void alterar(ContaCorrente contaCorrente) throws AppException{
		getRepositorio().update(contaCorrente);
	}
	
	public void excluir(ContaCorrente contaCorrente) throws AppException{
		getRepositorio().remove(contaCorrente);
	}

}
