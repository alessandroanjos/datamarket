/**
 * 
 */
package com.infinity.datamarket.comum.funcionalidade;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

/**
 * @author jonas
 *
 */
public class CadastroFuncionalidade extends Cadastro {
	private static CadastroFuncionalidade instancia;
	private static Class CLASSE = Funcionalidade.class;
	private CadastroFuncionalidade(){}
	public static CadastroFuncionalidade getInstancia(){
		if (instancia == null){
			instancia = new CadastroFuncionalidade();
		}
		return instancia;
	}

	public Funcionalidade consultarPorId(Long id) throws AppException{
		return (Funcionalidade) getRepositorio().findById(CLASSE, id);
	}
	
	public void inserir(Funcionalidade funcionalidade) throws AppException{
		getRepositorio().insert(funcionalidade);
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	
	public Funcionalidade consultarPorPK(Long id) throws AppException{
		return (Funcionalidade) getRepositorio().findById(CLASSE, id);
	}
	
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	
	public void alterar(Funcionalidade funcionalidade) throws AppException{
		getRepositorio().update(funcionalidade);
	}
	
	public void excluir(Funcionalidade funcionalidade) throws AppException{
		getRepositorio().remove(funcionalidade);
	}
	
	/**
	 * Metodo responsavel por trazer uma lista com as funcionalidades onde a funcionalidade passada
	 * como parâmetro é Funcionalidade Superior
	 * @param funcionalidadeSuperior
	 * @return
	 */
	public Collection consultarFuncionalidadesPorFuncionalidadeSuperior(Funcionalidade funcionalidade) throws AppException {
			
		Collection col = null;
		PropertyFilter filter = new PropertyFilter();
		filter.setTheClass(CLASSE);
		filter.addProperty("funcionalidadeSuperior.id", funcionalidade.getId());
		filter.addOrderByProperty("id", "asc");
		filter.addOrderByProperty("funcionalidadeSuperior.id", "asc");
		
		col = this.consultar(filter);
		
		return col;
	}
}
