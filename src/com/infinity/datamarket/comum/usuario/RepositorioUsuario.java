package com.infinity.datamarket.comum.usuario;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioUsuario extends Repositorio implements IRepositorioUsuario{
	private static RepositorioUsuario instancia;
	private static Class CLASSE = Usuario.class;
	private RepositorioUsuario(){}
	
	public static RepositorioUsuario getInstancia(){
		if (instancia == null){
			instancia = new RepositorioUsuario();
		}
		return instancia;
	}

	public Usuario consultarPorId(Long id) throws AppException{
		return (Usuario) findById(CLASSE, id);
	}

	public void inserir(Usuario usuario) throws AppException{
		insert(usuario);
		inserirDadoLote(usuario);
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	
	public Usuario consultarPorPK(Long id) throws AppException{
		return (Usuario) findById(CLASSE, id);
	}
	
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}
	
	public void alterar(Usuario usuario) throws AppException{
		update(usuario);
		alterarDadoLote(usuario);
	}
	
	public void excluir(Usuario usuario) throws AppException{
		remove(usuario);
		excluirDadoLote(usuario);
	}

	public Usuario consultarPorId_IdMacro(Long id) throws AppException{
		return (Usuario) findById(CLASSE, id);		
	}
	
}
