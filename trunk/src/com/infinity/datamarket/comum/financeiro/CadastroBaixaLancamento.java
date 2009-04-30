package com.infinity.datamarket.comum.financeiro;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

public class CadastroBaixaLancamento extends Cadastro{

	private static CadastroBaixaLancamento instancia;
	private static Class CLASSE = BaixaLancamento.class;
	private CadastroBaixaLancamento(){}
	
	public static CadastroBaixaLancamento getInstancia(){
		if (instancia == null){
			instancia = new CadastroBaixaLancamento();
		}
		return instancia;
	}

	public BaixaLancamento consultarPorId(Long id) throws AppException{
		return (BaixaLancamento) getRepositorio().findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	public void inserir(BaixaLancamento baixaLancamento) throws AppException{
		getRepositorio().insert(baixaLancamento);
	}

	public void alterar(BaixaLancamento baixaLancamento) throws AppException{
		getRepositorio().update(baixaLancamento);
	}

	public void excluir(BaixaLancamento baixaLancamento) throws AppException{
		getRepositorio().remove(baixaLancamento);
	}
}