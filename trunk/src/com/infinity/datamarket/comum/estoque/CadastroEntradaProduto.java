package com.infinity.datamarket.comum.estoque;

import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

public class CadastroEntradaProduto extends Cadastro{
	
	private static CadastroEntradaProduto instancia;
	private static Class CLASSE = EntradaProduto.class;
	private CadastroEntradaProduto(){}
	public static CadastroEntradaProduto getInstancia(){
		if (instancia == null){
			instancia = new CadastroEntradaProduto();
		}
		return instancia;
	}

	public EntradaProduto consultarPorId(Long id) throws AppException{
		return (EntradaProduto) getRepositorio().findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	public void inserir(EntradaProduto entradaProduto) throws AppException{
		getRepositorio().insert(entradaProduto);
		Collection col = entradaProduto.getProdutosEntrada();
		if (col==null)
			return;
		Iterator it = col.iterator();
		while(it.hasNext()){
			ProdutoEntradaProduto pep = (ProdutoEntradaProduto) it.next();
			EstoqueProdutoPK pk = new EstoqueProdutoPK();
			pk.setEstoque(pep.getEstoque());
			pk.setProduto(pep.getPk().getProduto());
			
			//consulta de estoque produto
			try {
				EstoqueProduto ep = (EstoqueProduto) getRepositorio().findById(EstoqueProduto.class, pk);
				ep.setQuantidade(ep.getQuantidade().add(pep.getQuantidade()));
				getRepositorio().update(ep);
			} catch (Exception e) {
				// TODO: handle exception
				EstoqueProduto ep = new EstoqueProduto();
				EstoqueProdutoPK pkEp = new EstoqueProdutoPK();
				pkEp.setEstoque(pk.getEstoque());
				pkEp.setProduto(pk.getProduto());
				ep.setPk(pkEp);
				ep.setQuantidade(pep.getQuantidade());
				getRepositorio().insert(ep);
			}
	
		}
	}
	public void alterar(EntradaProduto entradaProduto) throws AppException{
		getRepositorio().update(entradaProduto);
		Collection col = entradaProduto.getProdutosEntrada();
		Iterator it = col.iterator();
		while(it.hasNext()){
			ProdutoEntradaProduto pep = (ProdutoEntradaProduto) it.next();
			EstoqueProdutoPK pk = new EstoqueProdutoPK();
			pk.setEstoque(pep.getEstoque());
			pk.setProduto(pep.getPk().getProduto());
			
			//consulta de estoque produto
			EstoqueProduto ep = (EstoqueProduto) getRepositorio().findById(EstoqueProduto.class, pk);
			ep.setQuantidade(ep.getQuantidade().add(pep.getQuantidade()));
			getRepositorio().update(ep);
		}
	}
	public void excluir(EntradaProduto entradaProduto) throws AppException{
		getRepositorio().remove(entradaProduto);
		Collection col = entradaProduto.getProdutosEntrada();
		Iterator it = col.iterator();
		while(it.hasNext()){
			ProdutoEntradaProduto pep = (ProdutoEntradaProduto) it.next();
			EstoqueProdutoPK pk = new EstoqueProdutoPK();
			pk.setEstoque(pep.getEstoque());
			pk.setProduto(pep.getPk().getProduto());
			
			//consulta de estoque produto
			EstoqueProduto ep = (EstoqueProduto) getRepositorio().findById(EstoqueProduto.class, pk);
			ep.setQuantidade(ep.getQuantidade().subtract(pep.getQuantidade()));
			getRepositorio().update(ep);
		}
	}	

}
