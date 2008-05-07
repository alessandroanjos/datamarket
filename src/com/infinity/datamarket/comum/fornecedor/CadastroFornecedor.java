/**
 * 
 */
package com.infinity.datamarket.comum.fornecedor;

import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.estoque.CadastroEntradaProduto;
import com.infinity.datamarket.comum.estoque.EntradaProduto;
import com.infinity.datamarket.comum.estoque.EstoqueProduto;
import com.infinity.datamarket.comum.estoque.EstoqueProdutoPK;
import com.infinity.datamarket.comum.estoque.ProdutoEntradaProduto;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

/**
 * @author alessandro
 *
 */
public class CadastroFornecedor extends Cadastro {
	private static CadastroFornecedor instancia;
	private static Class CLASSE = Fornecedor.class;
	private CadastroFornecedor(){}
	public static CadastroFornecedor getInstancia(){
		if (instancia == null){
			instancia = new CadastroFornecedor();
		}
		return instancia;
	}
	public Fornecedor consultarPorId(Long id) throws AppException{
		return (Fornecedor) getRepositorio().findById(CLASSE, id);
	}
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	public void inserir(Fornecedor fornecedor) throws AppException{
		getRepositorio().insert(fornecedor);
		
	}
	public void alterar(Fornecedor fornecedor) throws AppException{
		getRepositorio().update(fornecedor);
	}
	public void excluir(Fornecedor fornecedor) throws AppException{
		getRepositorio().remove(fornecedor);
	}	
}
