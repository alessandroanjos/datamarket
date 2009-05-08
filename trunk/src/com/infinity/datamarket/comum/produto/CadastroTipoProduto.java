package com.infinity.datamarket.comum.produto;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;

public class CadastroTipoProduto extends Cadastro{
	
	private static CadastroTipoProduto instancia;
	
	public static CadastroTipoProduto getInstancia(){
		if (instancia == null){
			instancia = new CadastroTipoProduto();			
		}
		return instancia;
	}
	
	
	public IRepositorioTipoProduto getRepositorio() {
		// TODO Auto-generated method stub
		return (IRepositorioTipoProduto) super.getRepositorio(IRepositorio.REPOSITORIO_TIPO_PRODUTO);
	}
	
	public void inserir(TipoProduto tipoProduto) throws AppException{
		getRepositorio().inserir(tipoProduto);
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	
	public TipoProduto consultarPorPK(Long id) throws AppException{
		return (TipoProduto) getRepositorio().consultarPorPK(id);
	}
	
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}
	
	public void alterar(TipoProduto tipoProsuto) throws AppException{
		getRepositorio().alterar(tipoProsuto);
	}
	
	public void excluir(TipoProduto tipoProsuto) throws AppException{
		getRepositorio().excluir(tipoProsuto);
	}
}
