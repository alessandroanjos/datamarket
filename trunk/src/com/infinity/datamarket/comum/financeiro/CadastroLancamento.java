package com.infinity.datamarket.comum.financeiro;

import java.util.Collection;

import com.infinity.datamarket.comum.financeiro.Lancamento;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

public class CadastroLancamento extends Cadastro{
	private static CadastroLancamento instancia;
	private static Class CLASSE = Lancamento.class;
	private CadastroLancamento(){}
	public static CadastroLancamento getInstancia(){
		if (instancia == null){
			instancia = new CadastroLancamento();
		}
		return instancia;
	}

	public Lancamento consultarPorId(Long id) throws AppException{
		return (Lancamento) getRepositorio().findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	public void inserir(Lancamento lancamento) throws AppException{
		getRepositorio().insert(lancamento);
	}

	public void alterar(Lancamento lancamento) throws AppException{
		getRepositorio().update(lancamento);
	}

	public void excluir(Lancamento lancamento) throws AppException{
		getRepositorio().remove(lancamento);
	}

}
