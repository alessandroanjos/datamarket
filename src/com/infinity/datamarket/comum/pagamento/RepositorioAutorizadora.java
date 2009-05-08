package com.infinity.datamarket.comum.pagamento;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioAutorizadora extends Repositorio implements IRepositorioAutorizadora{
	private static RepositorioAutorizadora instancia;
	private static Class CLASSE = Autorizadora.class;
	private RepositorioAutorizadora(){}
	public static RepositorioAutorizadora getInstancia(){
		if (instancia == null){
			instancia = new RepositorioAutorizadora();
		}
		return instancia;
	}

	public Autorizadora consultarPorId(Long id) throws AppException{
		return (Autorizadora) findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}
	public void inserir(Autorizadora autorizadora) throws AppException{
		insert(autorizadora);
		inserirDadoLote(autorizadora);
	}
	
	public void alterar(Autorizadora autorizadora) throws AppException{
		update(autorizadora);
		alterarDadoLote(autorizadora);
	}
	
	public void excluir(Autorizadora autorizadora) throws AppException{
		remove(autorizadora);
		excluirDadoLote(autorizadora);
	}

}
