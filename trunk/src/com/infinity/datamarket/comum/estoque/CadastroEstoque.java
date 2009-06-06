/**
 * 
 */
package com.infinity.datamarket.comum.estoque;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;

/**
 * @author alessandro
 *
 */
public class CadastroEstoque extends Cadastro {
	private static CadastroEstoque instancia;

	private CadastroEstoque(){}
	public static CadastroEstoque getInstancia(){
		if (instancia == null){
			instancia = new CadastroEstoque();
		}
		return instancia;
	}
	
	
	public IRepositorioEstoque getRepositorio() {
		// TODO Auto-generated method stub
		return (IRepositorioEstoque) super.getRepositorio(IRepositorio.REPOSITORIO_ESTOQUE);
	}

	public Estoque consultarPorId(EstoquePK id) throws AppException{
		return (Estoque) getRepositorio().consultarPorId(id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
		
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}
	public void inserir(Estoque componente) throws AppException{
		getRepositorio().inserir(componente);
	}
	
	public void alterar(Estoque componente) throws AppException{
		getRepositorio().alterar(componente);
	}
	
	public void excluir(Estoque componente) throws AppException{
		getRepositorio().excluir(componente);
	}
	
	public Collection consultarTodosPorLoja(String idLoja) throws AppException{
		return getRepositorio().consultarTodosPorLoja(idLoja);
	}
}
