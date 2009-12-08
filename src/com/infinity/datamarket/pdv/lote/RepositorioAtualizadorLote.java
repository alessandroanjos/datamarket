package com.infinity.datamarket.pdv.lote;

import java.io.Serializable;

import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioAtualizadorLote extends Repositorio implements IRepositorioAtualizadorLote{
	private static RepositorioAtualizadorLote instancia;
	
	private RepositorioAtualizadorLote(){}
	
	public static RepositorioAtualizadorLote getInstancia(){
		if (instancia == null){
			instancia = new RepositorioAtualizadorLote();
		}
		return instancia;
	}
	public void inserir(Serializable obj) throws AppException{
		insert(obj);
	}
	
	public void alterar(Serializable obj) throws AppException{
		merge(obj);
//		insertOrUpdate(obj);
	}
	
	public void excluir(Serializable obj) throws AppException{
		remove(obj);
	}

}
