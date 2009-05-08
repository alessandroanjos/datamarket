package com.infinity.datamarket.comum.produto;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioUnidade extends Repositorio implements IRepositorioUnidade{

	private static RepositorioUnidade instancia;
	
	private static final Class CLASSE = Unidade.class;
	
	public static RepositorioUnidade getInstancia(){
		if (instancia == null){
			instancia = new RepositorioUnidade();			
		}
		return instancia;
	}
	
	public void inserir(Unidade unidade) throws AppException{
		insert(unidade);
		inserirDadoLote(unidade);
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	
	public Unidade consultarPorPK(Long id) throws AppException{
		return (Unidade) findById(CLASSE, id);
	}
	
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}
	
	public void alterar(Unidade unidade) throws AppException{
		update(unidade);
		alterarDadoLote(unidade);
	}
	
	public void excluir(Unidade unidade) throws AppException{
		remove(unidade);
		excluirDadoLote(unidade);
	}
	
}
