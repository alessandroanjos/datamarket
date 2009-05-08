package com.infinity.datamarket.comum.usuario;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioPerfil extends IRepositorio{

	public Perfil consultarPorId(Long id) throws AppException;
	public void inserir(Perfil perfil) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public Perfil consultarPorPK(Long id) throws AppException;
	public Collection consultarTodos() throws AppException;
	public void alterar(Perfil perfil) throws AppException;
	public void excluir(Perfil perfil) throws AppException;
	public Collection consultarPerfisPorPerfilSuperior(Perfil perfil) throws AppException ;
	
}
