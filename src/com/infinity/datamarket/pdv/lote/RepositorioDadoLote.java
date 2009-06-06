package com.infinity.datamarket.pdv.lote;

import java.io.Serializable;

import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioDadoLote extends Repositorio implements IRepositorioDadoLote{
	private static RepositorioDadoLote instancia;
	
	private RepositorioDadoLote(){}
	
	public static RepositorioDadoLote getInstancia(){
		if (instancia == null){
			instancia = new RepositorioDadoLote();
		}
		return instancia;
	}
	public void inserir(Serializable obj) throws AppException{
		insert(obj);
	}
	
	public void alterar(Serializable obj) throws AppException{
		merge(obj);
	}
	
	public void excluir(Serializable obj) throws AppException{
		remove(obj);
	}

}
