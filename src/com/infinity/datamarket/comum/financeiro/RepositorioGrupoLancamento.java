package com.infinity.datamarket.comum.financeiro;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
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
		PropertyFilter filter = new PropertyFilter();
		filter.setTheClass(CLASSE);
		if(id.equals(GrupoLancamento.GRUPO_VENDA) || id.equals(GrupoLancamento.GRUPO_ENTRADA_PRODUTO)){
			filter.addProperty("tipoRegistro", GrupoLancamento.REGISTRO_SISTEMA);
		}else{
			filter.addProperty("tipoRegistro", GrupoLancamento.REGISTRO_USUARIO);	
		}
		filter.addProperty("id", id);
		return (GrupoLancamento) filterUniqueResult(filter);
	}	

	public Collection consultar(IPropertyFilter filter) throws AppException{
		filter.addProperty("tipoRegistro", GrupoLancamento.REGISTRO_USUARIO);		
		return filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		PropertyFilter filter = new PropertyFilter();
		filter.setTheClass(CLASSE);
		filter.addProperty("tipoRegistro", GrupoLancamento.REGISTRO_USUARIO);		
		return filter(filter,false);
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
