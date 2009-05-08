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
		return (Produto) findById(CLASSE, id);
	}

	public Produto consultarPorCodigoExterno(String codigo) throws AppException{
		PropertyFilter filter = new PropertyFilter();
		filter.setTheClass(CLASSE);
		filter.addProperty("codigoExterno", codigo);
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
		List l = filter(filter, preciso);
		return l;
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
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
