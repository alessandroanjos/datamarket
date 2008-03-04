/**
 * 
 */
package com.infinity.datamarket.comum.usuario;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

/**
 * @author jonas
 *
 */
public class CadastroPerfil extends Cadastro {
	private static CadastroPerfil instancia;
	private static Class CLASSE = Perfil.class;
	private CadastroPerfil(){}
	public static CadastroPerfil getInstancia(){
		if (instancia == null){
			instancia = new CadastroPerfil();
		}
		return instancia;
	}

	public Perfil consultarPorId(Long id) throws AppException{
		return (Perfil) getRepositorio().findById(CLASSE, id);
	}
	
	public void inserir(Perfil perfil) throws AppException{
		getRepositorio().insert(perfil);
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	
	public Perfil consultarPorPK(Long id) throws AppException{
		return (Perfil) getRepositorio().findById(CLASSE, id);
	}
	
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	
	public void alterar(Perfil perfil) throws AppException{
		getRepositorio().update(perfil);
	}
	
	public void excluir(Perfil perfil) throws AppException{
		getRepositorio().remove(perfil);
	}
	
	/**
	 * Metodo responsavel por trazer uma lista com os perfis onde o perfil passado
	 * como parâmetro é Perfil Superior
	 * @param perfilSuperior
	 * @return
	 */
	public Collection consultarPerfisPorPerfilSuperior(Perfil perfil) throws AppException {
			
		Collection col = null;
		PropertyFilter filter = new PropertyFilter();
		filter.setTheClass(CLASSE);
		filter.addProperty("perfilSuperior.id", perfil.getId());
		
		col = this.consultar(filter);
		
		return col;
	}
}
