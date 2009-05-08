/**
 * 
 */
package com.infinity.datamarket.comum.usuario;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;

/**
 * @author jonas
 *
 */
public class CadastroPerfil extends Cadastro {
	private static CadastroPerfil instancia;
	
	private CadastroPerfil(){}
	public static CadastroPerfil getInstancia(){
		if (instancia == null){
			instancia = new CadastroPerfil();
		}
		return instancia;
	}
	
	public IRepositorioPerfil getRepositorio(){
		return (IRepositorioPerfil) getRepositorio(IRepositorio.REPOSITORIO_PERFIL);
	}

	public Perfil consultarPorId(Long id) throws AppException{
		return getRepositorio().consultarPorId(id);
	}
	
	public void inserir(Perfil perfil) throws AppException{
		getRepositorio().inserir(perfil);
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	
	public Perfil consultarPorPK(Long id) throws AppException{
		return getRepositorio().consultarPorPK(id);
	}
	
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}
	
	public void alterar(Perfil perfil) throws AppException{
		getRepositorio().alterar(perfil);
	}
	
	public void excluir(Perfil perfil) throws AppException{		
		getRepositorio().excluir(perfil);
	}
	
	public Collection consultarPerfisPorPerfilSuperior(Perfil perfil) throws AppException {
			return getRepositorio().consultarPerfisPorPerfilSuperior(perfil);
	}
}
