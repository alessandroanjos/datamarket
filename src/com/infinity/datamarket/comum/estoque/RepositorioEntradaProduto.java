package com.infinity.datamarket.comum.estoque;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioEntradaProduto extends Repositorio implements IRepositorioEntradaProduto{

	private static RepositorioEntradaProduto instancia;
	private static Class CLASSE = EntradaProduto.class;
	private RepositorioEntradaProduto(){}
	public static RepositorioEntradaProduto getInstancia(){
		if (instancia == null){
			instancia = new RepositorioEntradaProduto();
		}
		return instancia;
	}

	public EntradaProduto consultarPorId(Long id) throws AppException{
		return (EntradaProduto) findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}
	public void inserir(EntradaProduto entradaProduto) throws AppException{
		insert(entradaProduto);
	}
	
	public void alterar(EntradaProduto entradaProduto) throws AppException{
		update(entradaProduto);

	}
	
	public void excluir(EntradaProduto entradaProduto) throws AppException{
		remove(entradaProduto);
	}	

}
