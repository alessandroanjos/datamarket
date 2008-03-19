package com.infinity.datamarket.comum.componente;


import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;


public class CadastroComponente extends Cadastro {
	private static CadastroComponente instancia;
	private static Class CLASSE = Componente.class;
	private CadastroComponente(){}
	public static CadastroComponente getInstancia(){
		if (instancia == null){
			instancia = new CadastroComponente();
		}
		return instancia;
	}

	public Componente consultarPorId(Long id) throws AppException{
		return (Componente) getRepositorio().findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	public void inserir(Componente componente) throws AppException{
		getRepositorio().insert(componente);
	}
	
	public void alterar(Componente componente) throws AppException{
		getRepositorio().update(componente);
	}
	
	public void excluir(Componente componente) throws AppException{
		getRepositorio().remove(componente);
	}

}
