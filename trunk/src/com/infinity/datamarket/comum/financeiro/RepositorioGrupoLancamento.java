package com.infinity.datamarket.comum.financeiro;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioGrupoLancamento extends Repositorio implements IRepositorioGrupoLancamento{
	private static RepositorioGrupoLancamento instancia;
	private static Class CLASSE = GrupoLancamento.class;
	private RepositorioGrupoLancamento(){}
	public static RepositorioGrupoLancamento getInstancia(){
		if (instancia == null){
			instancia = new RepositorioGrupoLancamento();
		}
		return instancia;
	}

	public GrupoLancamento consultarPorId(Long id) throws AppException{
		return (GrupoLancamento) findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}
	public void inserir(GrupoLancamento grupo) throws AppException{
		insert(grupo);
		inserirDadoLote(grupo);
	}

	public void alterar(GrupoLancamento grupo) throws AppException{
		update(grupo);
		alterarDadoLote(grupo);
	}

	public void excluir(GrupoLancamento grupo) throws AppException{
		remove(grupo);
		excluirDadoLote(grupo);
	}

}
