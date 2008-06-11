package com.infinity.datamarket.comum.estoque;

import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.ObjetoInexistenteException;

public class CadastroMovimentacaoEstoque extends Cadastro{

	private static CadastroMovimentacaoEstoque instancia;
	
	private static Class CLASSE = MovimentacaoEstoque.class;
	
	private CadastroMovimentacaoEstoque(){
		
	}
	
	public static CadastroMovimentacaoEstoque getInstancia(){
		if (instancia == null){
			instancia = new CadastroMovimentacaoEstoque();
		}
		return instancia;
	}
	
	
	public MovimentacaoEstoque consultarPorId(Long id) throws AppException{
		return (MovimentacaoEstoque) getRepositorio().findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	public void inserir(MovimentacaoEstoque movimentacaoEstoque) throws AppException{
		getRepositorio().insert(movimentacaoEstoque);
		Collection col = movimentacaoEstoque.getProdutosMovimentacao();
		if (col==null)
			return;
		Iterator it = col.iterator();
		while(it.hasNext()){
		
			ProdutoMovimentacaoEstoque pme = (ProdutoMovimentacaoEstoque) it.next();
			ProdutoMovimentacaoEstoquePK pk = pme.getPk();
			pk.setId(movimentacaoEstoque.getId());
			getRepositorio().insert(pme);
			
			EstoqueProdutoPK pkEp = new EstoqueProdutoPK();
			pkEp.setEstoque(movimentacaoEstoque.getEstoqueEntrada());
			pkEp.setProduto(pme.getProduto());
			
			//adiciona saldo produto para o novo estoque
			try {
				EstoqueProduto ep = (EstoqueProduto) getRepositorio().findById(EstoqueProduto.class, pkEp);
				ep.setQuantidade(ep.getQuantidade().add(pme.getQuantidade()));
				getRepositorio().update(ep);
			} catch (ObjectNotFoundException e) {
				// TODO: handle exception
				EstoqueProduto ep = new EstoqueProduto();
				ep.setPk(pkEp);
				ep.setQuantidade(pme.getQuantidade());
				getRepositorio().insert(ep);
			}
			
			pkEp = new EstoqueProdutoPK();
			pkEp.setEstoque(movimentacaoEstoque.getEstoqueSaida());
			pkEp.setProduto(pme.getProduto());
			//retira saldo produto para o novo estoque
			try {
				EstoqueProduto ep = (EstoqueProduto) getRepositorio().findById(EstoqueProduto.class, pkEp);
				ep.setQuantidade(ep.getQuantidade().subtract(pme.getQuantidade()));
				getRepositorio().update(ep);
			} catch (ObjectNotFoundException e) {
				// TODO: handle exception
			}
			
		}
	}
	
	public void alterar(MovimentacaoEstoque movimentacaoEstoque) throws AppException{
		getRepositorio().update(movimentacaoEstoque);
	}
	public void excluir(MovimentacaoEstoque movimentacaoEstoque) throws AppException{
		getRepositorio().remove(movimentacaoEstoque);
	}
	
	
	
}
