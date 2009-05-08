package com.infinity.datamarket.comum.banco;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioBanco extends Repositorio implements IRepositorioBanco{
	
	private static RepositorioBanco instancia;
	private static Class CLASSE = Banco.class;
	private RepositorioBanco(){}
	public static RepositorioBanco getInstancia(){
		if (instancia == null){
			instancia = new RepositorioBanco();
		}
		return instancia;
	}

	public Banco consultarPorId(Long id) throws AppException{
		return (Banco) findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}
	public void inserir(Banco banco) throws AppException{
		insert(banco);
		inserirDadoLote(banco);
	}
	
	public void alterar(Banco banco) throws AppException{
		update(banco);
		alterarDadoLote(banco);
	}
	
	public void excluir(Banco banco) throws AppException{
		remove(banco);
		excluirDadoLote(banco);
	}


}
