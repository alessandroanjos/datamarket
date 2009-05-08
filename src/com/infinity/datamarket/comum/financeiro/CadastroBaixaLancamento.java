package com.infinity.datamarket.comum.financeiro;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;

public class CadastroBaixaLancamento extends Cadastro{

	private static CadastroBaixaLancamento instancia;
	private CadastroBaixaLancamento(){}
	
	public static CadastroBaixaLancamento getInstancia(){
		if (instancia == null){
			instancia = new CadastroBaixaLancamento();
		}
		return instancia;
	}
	
	
	public IRepositorioBaixaLancamento getRepositorio() {
		return (IRepositorioBaixaLancamento) super.getRepositorio(IRepositorio.REPOSITORIO_BAIXA_LANCAMENTO);
	}

	public BaixaLancamento consultarPorId(Long id) throws AppException{
		return (BaixaLancamento) getRepositorio().consultarPorId(id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}
	public void inserir(BaixaLancamento baixaLancamento) throws AppException{
		getRepositorio().inserir(baixaLancamento);
	}

	public void alterar(BaixaLancamento baixaLancamento) throws AppException{
		getRepositorio().alterar(baixaLancamento);
	}

	public void excluir(BaixaLancamento baixaLancamento) throws AppException{
		getRepositorio().excluir(baixaLancamento);
	}
}