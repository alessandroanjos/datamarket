package com.infinity.datamarket.comum.estoque;

import java.util.Collection;

import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

public class CadastroAjusteEstoque extends Cadastro{
	
	private static CadastroAjusteEstoque instancia;
	private static Class CLASSE = AjusteEstoque.class;
	private CadastroAjusteEstoque(){}
	public static CadastroAjusteEstoque getInstancia(){
		if (instancia == null){
			instancia = new CadastroAjusteEstoque();
		}
		return instancia;
	}

	public AjusteEstoque consultarPorId(Long id) throws AppException{
		return (AjusteEstoque) getRepositorio().findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	public void inserir(AjusteEstoque ajuste) throws AppException{
		getRepositorio().insert(ajuste);
		Produto produto = ajuste.getProduto();
		if (produto==null)
			return;

			EstoqueProdutoPK pk = new EstoqueProdutoPK();
			pk.setEstoque(ajuste.getEstoque());
			pk.setProduto(produto);
			
			//consulta de estoque produto
			try {
				EstoqueProduto ep = (EstoqueProduto) getRepositorio().findById(EstoqueProduto.class, pk);
				ep.setQuantidade(ajuste.getQuantidadeDepois());
				getRepositorio().update(ep);
			} catch (Exception e) {
				// TODO: handle exception
				EstoqueProduto ep = new EstoqueProduto();
				EstoqueProdutoPK pkEp = new EstoqueProdutoPK();
				pkEp.setEstoque(pk.getEstoque());
				pkEp.setProduto(pk.getProduto());
				ep.setPk(pkEp);
				ep.setQuantidade(ajuste.getQuantidadeDepois());
				getRepositorio().insert(ep);
			}
	}
	
}
