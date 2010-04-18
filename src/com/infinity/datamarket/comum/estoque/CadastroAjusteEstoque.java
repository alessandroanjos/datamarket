package com.infinity.datamarket.comum.estoque;

import java.util.Collection;

import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;

public class CadastroAjusteEstoque extends Cadastro{
	
	private static CadastroAjusteEstoque instancia;
	private CadastroAjusteEstoque(){}
	public static CadastroAjusteEstoque getInstancia(){
		if (instancia == null){
			instancia = new CadastroAjusteEstoque();
		}
		return instancia;
	}
	
	
	public IRepositorioAjusteEstoque getRepositorio() {
		return (IRepositorioAjusteEstoque) super.getRepositorio(IRepositorio.REPOSITORIO_AJUSTE_ESTOQUE);
	}
	
	public IRepositorioEstoqueProduto getRepositorioEstoqueProduto() {
		return (IRepositorioEstoqueProduto) super.getRepositorio(IRepositorio.REPOSITORIO_ESTOQUE_PRODUTO);
	}

	public AjusteEstoque consultarPorId(Long id) throws AppException{
		return (AjusteEstoque) getRepositorio().consultarPorId(id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}
	public void inserir(AjusteEstoque ajuste) throws AppException{
		getRepositorio().inserir(ajuste);
		Produto produto = ajuste.getProduto();
		if (produto==null)
			return;

			EstoqueProdutoPK pk = new EstoqueProdutoPK();
			pk.setEstoque(ajuste.getEstoque());
			pk.setProduto(produto);
			
			//consulta de estoque produto
			try {
				EstoqueProduto ep = (EstoqueProduto) getRepositorioEstoqueProduto().consultarPorId(pk);
				ep.subtrairQuantidade(ajuste.getQuantidadeAntes(), null);
				ep.adicionarQuantidade(ajuste.getQuantidadeDepois(),null);				
				getRepositorioEstoqueProduto().alterar(ep);
			} catch (Exception e) {
				// TODO: handle exception
				EstoqueProduto ep = new EstoqueProduto();
				EstoqueProdutoPK pkEp = new EstoqueProdutoPK();
				pkEp.setEstoque(pk.getEstoque());
				pkEp.setProduto(pk.getProduto());
				ep.setPk(pkEp);
				ep.adicionarQuantidade(ajuste.getQuantidadeDepois(),null);
				getRepositorioEstoqueProduto().inserir(ep);
			}
	}
	
}
