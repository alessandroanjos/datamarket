package com.infinity.datamarket.comum.producao;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.estoque.CadastroEstoqueProduto;
import com.infinity.datamarket.comum.estoque.EstoqueProduto;
import com.infinity.datamarket.comum.estoque.EstoqueProdutoPK;
import com.infinity.datamarket.comum.produto.CadastroProduto;
import com.infinity.datamarket.comum.produto.Composicao;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;

public class CadastroProducao extends Cadastro{
	private static CadastroProducao instancia;
	
	private CadastroProducao(){}
	public static CadastroProducao getInstancia(){
		if (instancia == null){
			instancia = new CadastroProducao();
		}
		return instancia;
	}
	

	public IRepositorioProducao getRepositorio() {
	
		return (IRepositorioProducao) super.getRepositorio(IRepositorio.REPOSITORIO_PRODUCAO);
	}

	public Producao consultarPorId(Long id) throws AppException{
		return (Producao) getRepositorio().consultarPorId(id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}
	public void inserir(Producao producao) throws AppException{
		getRepositorio().inserir(producao);
		EstoqueProdutoPK estoqueProdutoPkDestino = new EstoqueProdutoPK();
		estoqueProdutoPkDestino.setProduto(producao.getProduto());
		estoqueProdutoPkDestino.setEstoque(producao.getEstoque());				
		
		CadastroEstoqueProduto cadEstoqueProduto = CadastroEstoqueProduto.getInstancia();
		try{
			EstoqueProduto estProdDestino = cadEstoqueProduto.consultarPorId(estoqueProdutoPkDestino);
			estProdDestino.setQuantidade(estProdDestino.getQuantidade().add(producao.getQuantidade()));
			cadEstoqueProduto.alterar(estProdDestino);
			
			EstoqueProduto estProdOrigem = null;
			Collection c = CadastroProduto.getInstancia().consultarPorPK(producao.getProduto().getId()).getComposicao();
			Iterator it = c.iterator();
			while(it.hasNext()){
				Composicao comp = (Composicao)it.next();
				if(comp != null){
					EstoqueProdutoPK estoqueProdutoPkOrigem = new EstoqueProdutoPK();
					try {						
						estoqueProdutoPkOrigem.setProduto(comp.getPk().getProduto());
						estoqueProdutoPkOrigem.setEstoque(producao.getEstoque());
						estProdOrigem = cadEstoqueProduto.consultarPorId(estoqueProdutoPkOrigem);
						estProdOrigem.setQuantidade(estProdOrigem.getQuantidade().subtract(producao.getQuantidade().multiply(comp.getQuantidade())));
						cadEstoqueProduto.alterar(estProdOrigem);
					} catch (ObjectNotFoundException e) {
						estProdOrigem = new EstoqueProduto();
						estProdOrigem.setPk(estoqueProdutoPkOrigem);
						estProdOrigem.setQuantidade(producao.getQuantidade().multiply(comp.getQuantidade()).negate());
						cadEstoqueProduto.inserir(estProdOrigem);
					}
				}
			}			
		} catch (ObjectNotFoundException e) {			
			EstoqueProduto ep = new EstoqueProduto();
			ep.setPk(estoqueProdutoPkDestino);
			ep.setQuantidade(producao.getQuantidade());
			cadEstoqueProduto.inserir(ep);
			
			EstoqueProduto estProdOrigem = null;
			Collection c = CadastroProduto.getInstancia().consultarPorPK(producao.getProduto().getId()).getComposicao();
			Iterator it = c.iterator();
			while(it.hasNext()){
				Composicao comp = (Composicao)it.next();
				if(comp != null){
					EstoqueProdutoPK estoqueProdutoPkOrigem = new EstoqueProdutoPK();
					try {						
						estoqueProdutoPkOrigem.setProduto(comp.getPk().getProduto());
						estoqueProdutoPkOrigem.setEstoque(producao.getEstoque());
						estProdOrigem = cadEstoqueProduto.consultarPorId(estoqueProdutoPkOrigem);
						estProdOrigem.setQuantidade(estProdOrigem.getQuantidade().subtract(producao.getQuantidade().multiply(comp.getQuantidade())));
						cadEstoqueProduto.alterar(estProdOrigem);
					} catch (ObjectNotFoundException e1) {
						estProdOrigem = new EstoqueProduto();
						estProdOrigem.setPk(estoqueProdutoPkOrigem);
						estProdOrigem.setQuantidade(producao.getQuantidade().multiply(comp.getQuantidade()).negate());
						cadEstoqueProduto.inserir(estProdOrigem);
					}
				}
			}
		}
	}
	
	public void alterar(Producao producao, BigDecimal quantidade) throws AppException{
		
		BigDecimal quantidadeCalculada = quantidade.subtract(producao.getQuantidade()); 
		producao.setQuantidade(quantidade);
		
		getRepositorio().alterar(producao);
		
		EstoqueProdutoPK pk = new EstoqueProdutoPK();
		pk.setProduto(producao.getProduto());
		pk.setEstoque(producao.getEstoque());				
		
		
		try{
			EstoqueProduto estProd = CadastroEstoqueProduto.getInstancia().consultarPorId(pk);
			BigDecimal qtd = estProd.getQuantidade().add(quantidadeCalculada);			
			estProd.setQuantidade(qtd);
			CadastroEstoqueProduto.getInstancia().alterar(estProd);
		} catch (ObjectNotFoundException e) {			
			e.printStackTrace();
		}
	}
	
	public void excluir(Producao producao) throws AppException{
		getRepositorio().excluir(producao);
		
		EstoqueProdutoPK pk = new EstoqueProdutoPK();
		pk.setProduto(producao.getProduto());
		pk.setEstoque(producao.getEstoque());				
		
		
		try{
			EstoqueProduto estProd = CadastroEstoqueProduto.getInstancia().consultarPorId(pk);
			BigDecimal qtd = estProd.getQuantidade().subtract(producao.getQuantidade());			
			estProd.setQuantidade(qtd);
			CadastroEstoqueProduto.getInstancia().alterar(estProd);
		} catch (ObjectNotFoundException e) {			
			e.printStackTrace();
		}
	}
	public Integer consultarMaiorNumeroLote() throws AppException{
		Integer lote = getRepositorio().consultarMaiorNumeroLote();
		if (lote == null){
			lote = new Integer(1);
		}else{
			lote = new Integer(lote.intValue()+1);
		}
		return lote;
	}

}
