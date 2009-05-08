package com.infinity.datamarket.comum.financeiro;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioBaixaLancamento extends Repositorio implements IRepositorioBaixaLancamento{
	
	private static RepositorioBaixaLancamento instancia;
	private static Class CLASSE = BaixaLancamento.class;
	private RepositorioBaixaLancamento(){}
	
	public static RepositorioBaixaLancamento getInstancia(){
		if (instancia == null){
			instancia = new RepositorioBaixaLancamento();
		}
		return instancia;
	}

	public BaixaLancamento consultarPorId(Long id) throws AppException{
		return (BaixaLancamento) findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}
	public void inserir(BaixaLancamento baixaLancamento) throws AppException{
		insert(baixaLancamento);
	}

	public void alterar(BaixaLancamento baixaLancamento) throws AppException{
		update(baixaLancamento);
	}

	public void excluir(BaixaLancamento baixaLancamento) throws AppException{
		remove(baixaLancamento);
	}

}
