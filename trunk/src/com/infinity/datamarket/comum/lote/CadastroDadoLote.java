package com.infinity.datamarket.comum.lote;

import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;

public class CadastroDadoLote extends Cadastro{
	
	private static CadastroDadoLote instancia;
	private static Class CLASSE = DadoLote.class;
	private CadastroDadoLote(){}
	public static CadastroDadoLote getInstancia(){
		if (instancia == null){
			instancia = new CadastroDadoLote();
		}
		return instancia;
	}
	
	
	public IRepositorioDadoLote getRepositorio() {
		return (IRepositorioDadoLote) super.getRepositorio(IRepositorio.REPOSITORIO_DADO_LOTE);
	}

	public DadoLote consultarPorId(Long id) throws AppException{
		return (DadoLote) getRepositorio().consultarPorId(id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}
	public void inserir(DadoLote dadoLote) throws AppException{
		getRepositorio().inserir(dadoLote);
	}
	
	public void excluir(DadoLote dadoLote) throws AppException{
		getRepositorio().excluir(dadoLote);
	}	

}
