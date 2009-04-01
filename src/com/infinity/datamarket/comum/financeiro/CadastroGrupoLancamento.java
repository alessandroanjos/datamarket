package com.infinity.datamarket.comum.financeiro;

import java.util.Collection;

import com.infinity.datamarket.comum.financeiro.GrupoLancamento;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

public class CadastroGrupoLancamento extends Cadastro{
	private static CadastroGrupoLancamento instancia;
	private static Class CLASSE = GrupoLancamento.class;
	private CadastroGrupoLancamento(){}
	public static CadastroGrupoLancamento getInstancia(){
		if (instancia == null){
			instancia = new CadastroGrupoLancamento();
		}
		return instancia;
	}

	public GrupoLancamento consultarPorId(Long id) throws AppException{
		return (GrupoLancamento) getRepositorio().findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	public void inserir(GrupoLancamento grupo) throws AppException{
		getRepositorio().insert(grupo);
		inserirDadoLote(grupo);
	}

	public void alterar(GrupoLancamento grupo) throws AppException{
		getRepositorio().update(grupo);
		alterarDadoLote(grupo);
	}

	public void excluir(GrupoLancamento grupo) throws AppException{
		getRepositorio().remove(grupo);
		excluirDadoLote(grupo);
	}

}
