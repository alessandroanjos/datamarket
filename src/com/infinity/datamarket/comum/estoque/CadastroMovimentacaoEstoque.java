package com.infinity.datamarket.comum.estoque;

import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;

public class CadastroMovimentacaoEstoque extends Cadastro{

	private static CadastroMovimentacaoEstoque instancia;
	
//	private static Class CLASSE = MovimentacaoEstoque.class;
	
	private CadastroMovimentacaoEstoque(){
		
	}
	
	public static CadastroMovimentacaoEstoque getInstancia(){
		if (instancia == null){
			instancia = new CadastroMovimentacaoEstoque();
		}
		return instancia;
	}
	
	
	public IRepositorioMovimentacaoEstoque getRepositorio() {
		// TODO Auto-generated method stub
		return (IRepositorioMovimentacaoEstoque) super.getRepositorio(IRepositorio.REPOSITORIO_MOVIMENTACAO_ESTOQUE);
	}
	
	public IRepositorioEstoqueProduto getRepositorioEstoqueProduto() {
		// TODO Auto-generated method stub
		return (IRepositorioEstoqueProduto) super.getRepositorio(IRepositorio.REPOSITORIO_ESTOQUE_PRODUTO);
	}
	
	
	public MovimentacaoEstoque consultarPorId(Long id) throws AppException{
		return (MovimentacaoEstoque) getRepositorio().consultarPorId(id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}
	public void inserir(MovimentacaoEstoque movimentacaoEstoque) throws AppException{
		getRepositorio().inserir(movimentacaoEstoque);
		Collection col = movimentacaoEstoque.getProdutosMovimentacao();
		if (col==null)
			return;
		Iterator it = col.iterator();
		while(it.hasNext()){
		
			ProdutoMovimentacaoEstoque pme = (ProdutoMovimentacaoEstoque) it.next();
//			pme.getPk().setId(movimentacaoEstoque.getId());
//			ProdutoMovimentacaoEstoquePK pk = pme.getPk();
//			pk.setId(movimentacaoEstoque.getId());
//			getRepositorio().insert(pme);
			
			EstoqueProdutoPK pkEp = new EstoqueProdutoPK();
			pkEp.setEstoque(movimentacaoEstoque.getEstoqueEntrada());
			pkEp.setProduto(pme.getProduto());
			
			//adiciona saldo produto para o novo estoque
			try {
				EstoqueProduto ep = (EstoqueProduto) getRepositorioEstoqueProduto().consultarPorId(pkEp);
				ep.setQuantidade(ep.getQuantidade().add(pme.getQuantidade()));
				getRepositorioEstoqueProduto().alterar(ep);
			} catch (ObjectNotFoundException e) {
				// TODO: handle exception
				EstoqueProduto ep = new EstoqueProduto();
				ep.setPk(pkEp);
				ep.setQuantidade(pme.getQuantidade());
				getRepositorioEstoqueProduto().inserir(ep);
			}
			
			pkEp = new EstoqueProdutoPK();
			pkEp.setEstoque(movimentacaoEstoque.getEstoqueSaida());
			pkEp.setProduto(pme.getProduto());
			//retira saldo produto para o novo estoque
			try {
				EstoqueProduto ep = (EstoqueProduto) getRepositorioEstoqueProduto().consultarPorId(pkEp);
				ep.setQuantidade(ep.getQuantidade().subtract(pme.getQuantidade()));
				getRepositorioEstoqueProduto().alterar(ep);
			} catch (ObjectNotFoundException e) {
				// TODO: handle exception
			}
			
		}
	}
	
	public void alterar(MovimentacaoEstoque movimentacaoEstoque) throws AppException{
		getRepositorio().alterar(movimentacaoEstoque);
	}
	public void excluir(MovimentacaoEstoque movimentacaoEstoque) throws AppException{
		getRepositorio().excluir(movimentacaoEstoque);
	}
	
	public void cancelar(MovimentacaoEstoque movimentacaoEstoque) throws AppException{
		getRepositorio().alterar(movimentacaoEstoque);
		
		Collection col = movimentacaoEstoque.getProdutosMovimentacao();
		
		if (col==null)
			throw new AppException("Não foram encontrados os itens da movimentação de estoque selecionada.");
		
		Iterator it = col.iterator();
		while(it.hasNext()){		
			ProdutoMovimentacaoEstoque pme = (ProdutoMovimentacaoEstoque) it.next();
			
			EstoqueProdutoPK pkEp = new EstoqueProdutoPK();
			pkEp.setEstoque(movimentacaoEstoque.getEstoqueEntrada());
			pkEp.setProduto(pme.getProduto());
			
			//adiciona saldo produto para o novo estoque
			try {
				EstoqueProduto ep = (EstoqueProduto) getRepositorioEstoqueProduto().consultarPorId(pkEp);
				ep.setQuantidade(ep.getQuantidade().subtract(pme.getQuantidade()));
				getRepositorioEstoqueProduto().alterar(ep);
			} catch (ObjectNotFoundException e) {
				e.printStackTrace();
			}			
			
			pkEp = new EstoqueProdutoPK();
			pkEp.setEstoque(movimentacaoEstoque.getEstoqueSaida());
			pkEp.setProduto(pme.getProduto());
			//retira saldo produto para o novo estoque
			try {
				EstoqueProduto ep = (EstoqueProduto) getRepositorioEstoqueProduto().consultarPorId(pkEp);
				ep.setQuantidade(ep.getQuantidade().add(pme.getQuantidade()));
				getRepositorioEstoqueProduto().alterar(ep);
			} catch (ObjectNotFoundException e) {
				e.printStackTrace();
			}			
		}
	}	
}
