package com.infinity.datamarket.infocomponent;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioInfoComponente extends Repositorio implements IRepositorioInfoComponente{
	private static RepositorioInfoComponente instancia;
	private static Class CLASSE = InfoComponent.class;
	private RepositorioInfoComponente(){}
	public static RepositorioInfoComponente getInstancia(){
		if (instancia == null){
			instancia = new RepositorioInfoComponente();
		}
		return instancia;
	}

	public InfoComponent consultarPorId(String id) throws AppException{
		return (InfoComponent) findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	
	public void alterar(InfoComponent infoComponente) throws AppException{
		update(infoComponente);
	}
	
	public void inserir(InfoComponent infoComponente) throws AppException{
		insert(infoComponente);
	}
	

}
