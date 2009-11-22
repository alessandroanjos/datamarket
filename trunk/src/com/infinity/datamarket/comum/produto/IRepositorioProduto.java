package com.infinity.datamarket.comum.produto;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioProduto extends IRepositorio{
	
	public Produto consultarPorPK(Long id) throws AppException;
	public Produto consultarPorCodigoExterno(String codigo) throws AppException;
	public Collection consultarPorFiltro(IPropertyFilter filter, boolean preciso) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public Collection consultarTodos() throws AppException;
	public Collection consultarTodosLoja(long idLoja) throws AppException;
	public void inserir(Produto produto) throws AppException;
	
	public void alterar(Produto produto) throws AppException;
	public void alterar(Produto produto, Collection<Composicao> itensComposicaoRemovidos) throws AppException;
	
	public void excluir(Produto produto) throws AppException;
	public Collection consultarProdutosPorFiltro(Produto produto, String idLoja) throws AppException;

}
