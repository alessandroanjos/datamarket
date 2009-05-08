package com.infinity.datamarket.infocomponent;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioInfoComponente extends IRepositorio{
	public InfoComponent consultarPorId(String id) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public void alterar(InfoComponent infoComponente) throws AppException;
	public void inserir(InfoComponent infoComponente) throws AppException;
}
