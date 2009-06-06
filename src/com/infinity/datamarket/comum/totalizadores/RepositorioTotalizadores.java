package com.infinity.datamarket.comum.totalizadores;

import java.util.Collection;

import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioTotalizadores extends Repositorio implements IRepositorioTotalizadores{
	
	private static RepositorioTotalizadores instancia;
	private RepositorioTotalizadores(){}
	public static RepositorioTotalizadores getInstancia(){
		if (instancia == null){
			instancia = new RepositorioTotalizadores();
		}
		return instancia;
	}

	public void atualizar(TotalizadorNaoFiscal tot) throws AppException{
		update(tot);
	}


	public TotalizadorNaoFiscal consultarTotalizadorPorPK(Long totalizador) throws AppException{
		return (TotalizadorNaoFiscal) findById(TotalizadorNaoFiscal.class, totalizador);
	}

	public Collection consultarTodos() throws AppException{
		return findAll(TotalizadorNaoFiscal.class);		
	}


}
