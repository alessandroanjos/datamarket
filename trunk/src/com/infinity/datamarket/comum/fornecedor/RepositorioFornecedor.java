package com.infinity.datamarket.comum.fornecedor;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioFornecedor extends Repositorio implements IRepositorioFornecedor{
	
	private static RepositorioFornecedor instancia;
	private static Class CLASSE = Fornecedor.class;
	private RepositorioFornecedor(){}
	public static RepositorioFornecedor getInstancia(){
		if (instancia == null){
			instancia = new RepositorioFornecedor();
		}
		return instancia;
	}
	public Fornecedor consultarPorId(Long id) throws AppException{
		return (Fornecedor) findById(CLASSE, id);
	}
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}
	public void inserir(Fornecedor fornecedor) throws AppException{
		insert(fornecedor);
		inserirDadoLote(fornecedor);		
	}
	public void alterar(Fornecedor fornecedor) throws AppException{
		update(fornecedor);
		alterarDadoLote(fornecedor);
	}
	public void excluir(Fornecedor fornecedor) throws AppException{
		remove(fornecedor);
		excluirDadoLote(fornecedor);
	}

}
