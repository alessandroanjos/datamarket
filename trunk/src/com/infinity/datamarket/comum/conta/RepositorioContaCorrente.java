package com.infinity.datamarket.comum.conta;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioContaCorrente extends Repositorio implements IRepositorioContaCorrente{

	private static RepositorioContaCorrente instancia;
	private static Class CLASSE = ContaCorrente.class;
	private RepositorioContaCorrente(){}
	public static RepositorioContaCorrente getInstancia(){
		if (instancia == null){
			instancia = new RepositorioContaCorrente();
		}
		return instancia;
	}

	public ContaCorrente consultarPorId(Long id) throws AppException{
		return (ContaCorrente) findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}
	public void inserir(ContaCorrente contaCorrente) throws AppException{
		insert(contaCorrente);
		alterarDadoLote(contaCorrente);
	}
	
	public void alterar(ContaCorrente contaCorrente) throws AppException{
		update(contaCorrente);
		alterarDadoLote(contaCorrente);
	}
	
	public void excluir(ContaCorrente contaCorrente) throws AppException{
		remove(contaCorrente);
		alterarDadoLote(contaCorrente);
	}

	
}
