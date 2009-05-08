package com.infinity.datamarket.comum.estoque;

import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.Constantes;
import com.infinity.datamarket.comum.util.IRepositorio;

public class CadastroEntradaProduto extends Cadastro{
	
	private static CadastroEntradaProduto instancia;
	
	private CadastroEntradaProduto(){}
	public static CadastroEntradaProduto getInstancia(){
		if (instancia == null){
			instancia = new CadastroEntradaProduto();
		}
		return instancia;
	}
	
	
	
	public IRepositorioEntradaProduto getRepositorio() {
		// TODO Auto-generated method stub
		return (IRepositorioEntradaProduto) super.getRepositorio(IRepositorio.REPOSITORIO_ENTRADA_PRODUTO);
	}
	
	public IRepositorioEstoqueProduto getRepositorioEstoqueProduto() {
		// TODO Auto-generated method stub
		return (IRepositorioEstoqueProduto) super.getRepositorio(IRepositorio.REPOSITORIO_ESTOQUE_PRODUTO);
	}

	public EntradaProduto consultarPorId(Long id) throws AppException{
		return (EntradaProduto) getRepositorio().consultarPorId(id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}
	public void inserir(EntradaProduto entradaProduto) throws AppException{
		getRepositorio().inserir(entradaProduto);
		Collection col = entradaProduto.getProdutosEntrada();
		if (col==null)
			return;
		Iterator it = col.iterator();
		while(it.hasNext()){
			ProdutoEntradaProduto pep = (ProdutoEntradaProduto) it.next();
			EstoqueProdutoPK pk = new EstoqueProdutoPK();
			pk.setEstoque(entradaProduto.getEstoque());
			pk.setProduto(pep.getPk().getProduto());
			pep.getPk().setId(entradaProduto.getId());
			//consulta de estoque produto
			try {
				EstoqueProduto ep = (EstoqueProduto) getRepositorioEstoqueProduto().consultarPorId(pk);
				ep.setQuantidade(ep.getQuantidade().add(pep.getQuantidade()));
				getRepositorioEstoqueProduto().alterar(ep);
			} catch (ObjectNotFoundException e) {
				// TODO: handle exception
				EstoqueProduto ep = new EstoqueProduto();
				EstoqueProdutoPK pkEp = new EstoqueProdutoPK();
				pkEp.setEstoque(pk.getEstoque());
				pkEp.setProduto(pk.getProduto());
				ep.setPk(pkEp);
				ep.setQuantidade(pep.getQuantidade());
				getRepositorioEstoqueProduto().inserir(ep);
			}
	
		}
	}
	
	public void alterar(EntradaProduto entradaProduto) throws AppException{
		getRepositorio().alterar(entradaProduto);
		Collection col = entradaProduto.getProdutosEntrada();
		Iterator it = col.iterator();
		while(it.hasNext()){
			ProdutoEntradaProduto pep = (ProdutoEntradaProduto) it.next();
			EstoqueProdutoPK pk = new EstoqueProdutoPK();
			pk.setEstoque(entradaProduto.getEstoque());
			pk.setProduto(pep.getPk().getProduto());
			
			//consulta de estoque produto
			EstoqueProduto ep = (EstoqueProduto) getRepositorioEstoqueProduto().consultarPorId(pk);
			if(entradaProduto.getStatus().equals(Constantes.STATUS_ATIVO)){
				ep.setQuantidade(ep.getQuantidade().add(pep.getQuantidade()));	
			}else if(entradaProduto.getStatus().equals(Constantes.STATUS_CANCELADO)){
				ep.setQuantidade(ep.getQuantidade().subtract(pep.getQuantidade()));
			}			
			getRepositorioEstoqueProduto().alterar(ep);
		}
	}
	
	public void excluir(EntradaProduto entradaProduto) throws AppException{
		getRepositorio().excluir(entradaProduto);
		Collection col = entradaProduto.getProdutosEntrada();
		Iterator it = col.iterator();
		while(it.hasNext()){
			ProdutoEntradaProduto pep = (ProdutoEntradaProduto) it.next();
			EstoqueProdutoPK pk = new EstoqueProdutoPK();
			pk.setEstoque(entradaProduto.getEstoque());
			pk.setProduto(pep.getPk().getProduto());
			
			//consulta de estoque produto
			EstoqueProduto ep = (EstoqueProduto) getRepositorioEstoqueProduto().consultarPorId(pk);
			ep.setQuantidade(ep.getQuantidade().subtract(pep.getQuantidade()));
			getRepositorioEstoqueProduto().alterar(ep);
		}
	}	

}
