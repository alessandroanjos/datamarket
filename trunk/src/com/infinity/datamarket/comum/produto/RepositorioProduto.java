package com.infinity.datamarket.comum.produto;

import java.util.Collection;
import java.util.List;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ObjetoInexistenteException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioProduto extends Repositorio implements IRepositorioProduto{
	private static RepositorioProduto instancia;
	private static Class CLASSE = Produto.class;
	private RepositorioProduto(){}
	public static RepositorioProduto getInstancia(){
		if (instancia == null){
			instancia = new RepositorioProduto();
		}
		return instancia;
	}

	public Produto consultarPorPK(Long id) throws AppException{
		Produto produto = (Produto) findById(CLASSE, id);
		if (produto.getStatus().equals(produto.DESATIVADO)){
			throw new com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException("Produto Inexistente");
		}
		return produto;
	}

	public Produto consultarPorCodigoExterno(String codigo) throws AppException{
		PropertyFilter filter = new PropertyFilter();
		filter.setTheClass(CLASSE);
		filter.addProperty("codigoExterno", codigo);
		filter.addProperty("status", Produto.ATIVO);
		filter.setIgnoreCase(true);
		List l = filter(filter, true);
		if (l.size() > 0){
			return (Produto) l.iterator().next();
		}else{
			throw new ObjetoInexistenteException("Produto Inexistente");
		}
	}

	public Collection consultarPorFiltro(IPropertyFilter filter, boolean preciso) throws AppException{
		filter.setTheClass(CLASSE);
		filter.addProperty("status", Produto.ATIVO);
		List l = filter(filter, preciso);
		return l;
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		filter.addProperty("status", Produto.ATIVO);
		return filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		PropertyFilter filter = new PropertyFilter();
		filter.setTheClass(CLASSE);		
		return consultar(filter);
	}
	public void inserir(Produto produto) throws AppException{
		insert(produto);
		inserirDadoLote(produto);
	}
	
	public void alterar(Produto produto) throws AppException{
		update(produto);
		alterarDadoLote(produto);
	}
	
	public void excluir(Produto produto) throws AppException{
		remove(produto);
		excluirDadoLote(produto);
	}

}
