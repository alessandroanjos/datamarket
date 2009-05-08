package com.infinity.datamarket.comum.funcionalidade;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioFuncionalidade extends Repositorio implements IRepositorioFuncionalidade{

	private static RepositorioFuncionalidade instancia;
	private static Class CLASSE = Funcionalidade.class;
	private RepositorioFuncionalidade(){}
	public static RepositorioFuncionalidade getInstancia(){
		if (instancia == null){
			instancia = new RepositorioFuncionalidade();
		}
		return instancia;
	}

	public Funcionalidade consultarPorId(Long id) throws AppException{
		return (Funcionalidade) findById(CLASSE, id);
	}
	
	public void inserir(Funcionalidade funcionalidade) throws AppException{
		insert(funcionalidade);
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	
	public Funcionalidade consultarPorPK(Long id) throws AppException{
		return (Funcionalidade) findById(CLASSE, id);
	}
	
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}
	
	public void alterar(Funcionalidade funcionalidade) throws AppException{
		update(funcionalidade);
	}
	
	public void excluir(Funcionalidade funcionalidade) throws AppException{
		remove(funcionalidade);
	}

	
}
