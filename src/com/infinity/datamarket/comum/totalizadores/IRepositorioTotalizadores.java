package com.infinity.datamarket.comum.totalizadores;

import java.util.Collection;

import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioTotalizadores extends IRepositorio{
	
	public void atualizar(TotalizadorNaoFiscal tot) throws AppException;
	
	public TotalizadorNaoFiscal consultarTotalizadorPorPK(Long totalizador) throws AppException;

	public Collection consultarTodos() throws AppException;

}
