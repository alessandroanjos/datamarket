package com.infinity.datamarket.comum.componente;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioComponente extends Repositorio implements IRepositorioComponente{
	
	private static RepositorioComponente instancia;
	private static Class CLASSE = Componente.class;
	private RepositorioComponente(){}
	public static RepositorioComponente getInstancia(){
		if (instancia == null){
			instancia = new RepositorioComponente();
		}
		return instancia;
	}

	public Componente consultarPorId(Long id) throws AppException{
		return (Componente) findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}
	public void inserir(Componente componente) throws AppException{
		insert(componente);
	}
	
	public void alterar(Componente componente) throws AppException{
		update(componente);
	}
	
	public void excluir(Componente componente) throws AppException{
		remove(componente);
	}
}
