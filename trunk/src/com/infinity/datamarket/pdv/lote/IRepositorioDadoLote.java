package com.infinity.datamarket.pdv.lote;

import java.io.Serializable;

import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioDadoLote extends IRepositorio{

	public void inserir(Serializable obj) throws AppException;	
	public void alterar(Serializable obj) throws AppException;	
	public void excluir(Serializable obj) throws AppException;
}
