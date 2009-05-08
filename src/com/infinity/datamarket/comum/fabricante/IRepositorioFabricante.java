package com.infinity.datamarket.comum.fabricante;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioFabricante extends IRepositorio{
	
	public Fabricante consultarPorId(Long id) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public Collection consultarTodos() throws AppException;
	public void inserir(Fabricante fabricante) throws AppException;
	public void alterar(Fabricante fabricante) throws AppException;
	public void excluir(Fabricante fabricante) throws AppException;

}
