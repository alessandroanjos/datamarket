package com.infinity.datamarket.comum.util;

public interface IRepositorioParametro extends IRepositorio{
	public Parametro getParametro(String chave) throws AppException;
	public void atualizarParametro(Parametro parametro) throws AppException;
}
