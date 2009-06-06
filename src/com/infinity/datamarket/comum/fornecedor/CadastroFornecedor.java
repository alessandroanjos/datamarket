/**
 * 
 */
package com.infinity.datamarket.comum.fornecedor;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;

/**
 * @author alessandro
 *
 */
public class CadastroFornecedor extends Cadastro{
	private static CadastroFornecedor instancia;
	private CadastroFornecedor(){}
	public static CadastroFornecedor getInstancia(){
		if (instancia == null){
			instancia = new CadastroFornecedor();
		}
		return instancia;
	}
	
	
	public IRepositorioFornecedor getRepositorio() {
		return (IRepositorioFornecedor) super.getRepositorio(IRepositorio.REPOSITORIO_FORNECEDOR);
	}
	
	public Fornecedor consultarPorId(Long id) throws AppException{
		return (Fornecedor) getRepositorio().consultarPorId(id);
	}
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}
	public void inserir(Fornecedor fornecedor) throws AppException{
		getRepositorio().inserir(fornecedor);
		
	}
	public void alterar(Fornecedor fornecedor) throws AppException{
		getRepositorio().alterar(fornecedor);
	}
	public void excluir(Fornecedor fornecedor) throws AppException{
		getRepositorio().excluir(fornecedor);
	}	
}
