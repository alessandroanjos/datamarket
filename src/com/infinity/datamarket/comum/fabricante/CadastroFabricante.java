/**
 * 
 */
package com.infinity.datamarket.comum.fabricante;

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
public class CadastroFabricante extends Cadastro {
	private static CadastroFabricante instancia;
	private static Class CLASSE = Fabricante.class;
	private CadastroFabricante(){}
	public static CadastroFabricante getInstancia(){
		if (instancia == null){
			instancia = new CadastroFabricante();
		}
		return instancia;
	}
	public Fabricante consultarPorId(Long id) throws AppException{
		return (Fabricante) getRepositorio().findById(CLASSE, id);
	}
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	public void inserir(Fabricante fabricante) throws AppException{
		getRepositorio().insert(fabricante);
		inserirDadoLote(fabricante);
		
	}
	public void alterar(Fabricante fabricante) throws AppException{
		getRepositorio().update(fabricante);
		alterarDadoLote(fabricante);
	}
	public void excluir(Fabricante fabricante) throws AppException{
		getRepositorio().remove(fabricante);
		excluirDadoLote(fabricante);
	}	
}
