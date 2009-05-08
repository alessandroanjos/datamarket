package com.infinity.datamarket.comum.estoque;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioEstoque extends IRepositorio{


	public Estoque consultarPorId(EstoquePK id) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public EstoqueProduto consultarEstoqueProduto(EstoqueProdutoPK id) throws AppException;
	public Collection consultarTodos() throws AppException;
	public void inserir(Estoque componente) throws AppException;
	public void alterar(Estoque componente) throws AppException;
	public void excluir(Estoque componente) throws AppException;
	public Collection consultarTodosPorLoja(String idLoja) throws AppException;
	
}
