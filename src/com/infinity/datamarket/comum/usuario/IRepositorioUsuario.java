package com.infinity.datamarket.comum.usuario;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioUsuario extends IRepositorio{
	
	public Usuario consultarPorId(Long id) throws AppException;
	public void inserir(Usuario usuario) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public Usuario consultarPorPK(Long id) throws AppException;
	public Collection consultarTodos() throws AppException;
	public void alterar(Usuario usuario) throws AppException;
	public void excluir(Usuario usuario) throws AppException;
	public Usuario consultarPorId_IdMacro(Long id) throws AppException;
	
}
