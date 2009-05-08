package com.infinity.datamarket.comum.usuario;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioLoja extends Repositorio implements IRepositorioLoja{
	
	private static RepositorioLoja instancia;
	private static Class CLASSE = Loja.class;
	private RepositorioLoja(){}
	public static RepositorioLoja getInstancia(){
		if (instancia == null){
			instancia = new RepositorioLoja();
		}
		return instancia;
	}

	public Loja consultarPorId(Long id) throws AppException{
		return (Loja) findById(CLASSE, id);
	}
	
	public void inserir(Loja loja) throws AppException{
		insert(loja);
		inserirDadoLote(loja);
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	
	public Loja consultarPorPK(Long id) throws AppException{
		return (Loja) findById(CLASSE, id);
	}
	
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}
	
	public void alterar(Loja loja) throws AppException{
		update(loja);
		alterarDadoLote(loja);
	}
	
	public void excluir(Loja loja) throws AppException{
		remove(loja);
		excluirDadoLote(loja);
	}


}
