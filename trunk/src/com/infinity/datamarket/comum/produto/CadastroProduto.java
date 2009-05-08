package com.infinity.datamarket.comum.produto;

import java.util.Collection;
import java.util.List;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;
import com.infinity.datamarket.comum.util.ObjetoInexistenteException;

public class CadastroProduto extends Cadastro{
	private static CadastroProduto instancia;
	private CadastroProduto(){}
	public static CadastroProduto getInstancia(){
		if (instancia == null){
			instancia = new CadastroProduto();
		}
		return instancia;
	}
	
	
	public IRepositorioProduto getRepositorio() {
		// TODO Auto-generated method stub
		return (IRepositorioProduto) super.getRepositorio(IRepositorio.REPOSITORIO_PRODUTO);
	}

	public Produto consultarPorPK(Long id) throws AppException{
		return (Produto) getRepositorio().consultarPorPK(id);
	}

	public Produto consultarPorCodigoExterno(String codigo) throws AppException{
		return getRepositorio().consultarPorCodigoExterno(codigo);
		
	}

	public Collection consultarPorFiltro(IPropertyFilter filter, boolean preciso) throws AppException{
		return getRepositorio().consultarPorFiltro(filter, preciso);
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}
	public void inserir(Produto produto) throws AppException{
		getRepositorio().inserir(produto);
	}
	
	public void alterar(Produto produto) throws AppException{
		getRepositorio().alterar(produto);
	}
	
	public void excluir(Produto produto) throws AppException{
		getRepositorio().excluir(produto);
	}

	public Collection consultaProdutosPorSecao(String idLoja) throws AppException{
		return null;
	}

}
