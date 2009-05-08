/**
 * 
 */
package com.infinity.datamarket.comum.funcionalidade;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;

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

	
	public IRepositorioFuncionalidade getRepositorio() {
		return (IRepositorioFuncionalidade) super.getRepositorio(IRepositorio.REPOSITORIO_FUNCIONALIDADE);
	}
	
	public Funcionalidade consultarPorId(Long id) throws AppException{
		return getRepositorio().consultarPorId(id);
	}
	
	public void inserir(Funcionalidade funcionalidade) throws AppException{
		getRepositorio().inserir(funcionalidade);
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	
	public Funcionalidade consultarPorPK(Long id) throws AppException{
		return (Funcionalidade) getRepositorio().consultarPorPK(id);
	}
	
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}
	
	public void alterar(Funcionalidade funcionalidade) throws AppException{
		getRepositorio().alterar(funcionalidade);
	}
	
	public void excluir(Funcionalidade funcionalidade) throws AppException{
		getRepositorio().excluir(funcionalidade);
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
