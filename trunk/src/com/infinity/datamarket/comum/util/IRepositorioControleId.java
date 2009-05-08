package com.infinity.datamarket.comum.util;


public interface IRepositorioControleId extends IRepositorio{
	
	public Long retornaMaxId(Class classe);

	public Controle getControle(Class classe) throws AppException;
	
	public void atualizarControle(Controle controle) throws AppException;

	public void inserirControle(Controle controle) throws AppException;

}
