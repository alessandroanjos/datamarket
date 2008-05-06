package com.infinity.datamarket.infocomponent;


import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;


public class CadastroInfoComponente extends Cadastro {
	private static CadastroInfoComponente instancia;
	private static Class CLASSE = InfoComponent.class;
	private CadastroInfoComponente(){}
	public static CadastroInfoComponente getInstancia(){
		if (instancia == null){
			instancia = new CadastroInfoComponente();
		}
		return instancia;
	}

	public InfoComponent consultarPorId(String id) throws AppException{
		return (InfoComponent) getRepositorio().findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	
	public void alterar(InfoComponent infoComponente) throws AppException{
		getRepositorio().update(infoComponente);
	}
	
	public void inserir(InfoComponent infoComponente) throws AppException{
		getRepositorio().insert(infoComponente);
	}
	

}
