package com.infinity.datamarket.comum.banco;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

public class CadastroBanco extends Cadastro{
	private static CadastroBanco instancia;
	private static Class CLASSE = Banco.class;
	private CadastroBanco(){}
	public static CadastroBanco getInstancia(){
		if (instancia == null){
			instancia = new CadastroBanco();
		}
		return instancia;
	}

	public Banco consultarPorId(Long id) throws AppException{
		return (Banco) getRepositorio().findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	public void inserir(Banco banco) throws AppException{
		getRepositorio().insert(banco);
		inserirDadoLote(banco);
	}
	
	public void alterar(Banco banco) throws AppException{
		getRepositorio().update(banco);
		alterarDadoLote(banco);
	}
	
	public void excluir(Banco banco) throws AppException{
		getRepositorio().remove(banco);
		excluirDadoLote(banco);
	}

}
