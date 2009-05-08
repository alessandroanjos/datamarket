package com.infinity.datamarket.comum.financeiro;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioLancamento extends Repositorio implements IRepositorioLancamento{
	private static RepositorioLancamento instancia;
	private static Class CLASSE = Lancamento.class;
	private RepositorioLancamento(){}
	public static RepositorioLancamento getInstancia(){
		if (instancia == null){
			instancia = new RepositorioLancamento();
		}
		return instancia;
	}

	public Lancamento consultarPorId(Long id) throws AppException{
		return (Lancamento) findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}
	public void inserir(Lancamento lancamento) throws AppException{
		insert(lancamento);
	}
	
	public void alterar(Lancamento lancamento) throws AppException{
		update(lancamento);
	}

	public void excluir(Lancamento lancamento) throws AppException{
		remove(lancamento);
	}

}
