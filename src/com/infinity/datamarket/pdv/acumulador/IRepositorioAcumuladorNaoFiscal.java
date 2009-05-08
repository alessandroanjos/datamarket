package com.infinity.datamarket.pdv.acumulador;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioAcumuladorNaoFiscal extends IRepositorio{
	public AcumuladorNaoFiscal consultarPorId(Long id) throws AppException;

	public Collection consultar(IPropertyFilter filter) throws AppException;
	
	public Collection consultarTodos() throws AppException;
}
