package com.infinity.datamarket.pdv.maquinaestados;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioMaquina extends IRepositorio{
	
	public Collection consultaTecla(IPropertyFilter filtro) throws AppException;
	public Tecla consultaTeclaPorId(Long id) throws AppException;
	public Collection consultaMacroOperacao(IPropertyFilter filtro) throws AppException;


}
