package com.infinity.datamarket.comum.util;

import java.util.Collection;

public interface IRepositorioParametro extends IRepositorio{
	public Parametro getParametro(String chave) throws AppException;
	public Collection<Parametro> consultarTodosParametro() throws AppException;
	public void atualizarParametro(Parametro parametro) throws AppException;
}
