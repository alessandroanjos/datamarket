package com.infinity.datamarket.pdv.acumulador;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioAcumuladorNaoFiscal extends Repositorio implements IRepositorioAcumuladorNaoFiscal{
	private static RepositorioAcumuladorNaoFiscal instancia;
	private static Class CLASSE = AcumuladorNaoFiscal.class;
	private RepositorioAcumuladorNaoFiscal(){}
	public static RepositorioAcumuladorNaoFiscal getInstancia(){
		if (instancia == null){
			instancia = new RepositorioAcumuladorNaoFiscal();
		}
		return instancia;
	}

	public AcumuladorNaoFiscal consultarPorId(Long id) throws AppException{
		return (AcumuladorNaoFiscal) findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	
	
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}

}
