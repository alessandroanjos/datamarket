package com.infinity.datamarket.comum.usuario;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioPerfil extends Repositorio implements IRepositorioPerfil{
	private static Class CLASSE = Perfil.class;
	private RepositorioPerfil(){
		
	}
	
	private static RepositorioPerfil instancia;
	
	public static RepositorioPerfil getInstancia(){
		if (instancia == null){
			instancia = new RepositorioPerfil();
		}
		return instancia;
	}
	
	
	public Perfil consultarPorId(Long id) throws AppException{
		return (Perfil) findById(CLASSE, id);
	}
	
	public void inserir(Perfil perfil) throws AppException{
		insert(perfil);
		inserirDadoLote(perfil);
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	
	public Perfil consultarPorPK(Long id) throws AppException{
		return (Perfil) findById(CLASSE, id);
	}
	
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}
	
	public void alterar(Perfil perfil) throws AppException{
		update(perfil);
		alterarDadoLote(perfil);
	}
	
	public void excluir(Perfil perfil) throws AppException{
		remove(perfil);
		excluirDadoLote(perfil);
	}
	
	public Collection consultarPerfisPorPerfilSuperior(Perfil perfil) throws AppException {
			
		Collection col = null;
		PropertyFilter filter = new PropertyFilter();
		filter.setTheClass(CLASSE);
		filter.addProperty("perfilSuperior.id", perfil.getId());
		filter.addOrderByProperty("perfilSuperior.id", PropertyFilter.ASC);
	
		col = this.consultar(filter);
		
		return col;
	}
	
	
}
