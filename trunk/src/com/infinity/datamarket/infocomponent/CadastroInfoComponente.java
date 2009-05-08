package com.infinity.datamarket.infocomponent;


import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;


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
	
	
	public IRepositorioInfoComponente getRepositorio() {		
		return (IRepositorioInfoComponente) super.getRepositorio(IRepositorio.REPOSITORIO_INFO_COMPONENTE);
	}

	public InfoComponent consultarPorId(String id) throws AppException{
		return getRepositorio().consultarPorId(id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	
	public void alterar(InfoComponent infoComponente) throws AppException{
		getRepositorio().alterar(infoComponente);
	}
	
	public void inserir(InfoComponent infoComponente) throws AppException{
		getRepositorio().inserir(infoComponente);
	}
	

}
