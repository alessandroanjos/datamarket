package com.infinity.datamarket.comum.producao;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

import org.hibernate.Hibernate;

import com.infinity.datamarket.comum.estoque.CadastroEstoqueProduto;
import com.infinity.datamarket.comum.estoque.Estoque;
import com.infinity.datamarket.comum.estoque.EstoqueProduto;
import com.infinity.datamarket.comum.estoque.EstoqueProdutoPK;
import com.infinity.datamarket.comum.produto.CadastroProduto;
import com.infinity.datamarket.comum.produto.Composicao;
import com.infinity.datamarket.comum.produto.Produto;
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
	public void inserir(Producao producao,boolean ajustaProduto) throws AppException{
		getRepositorio().inserir(producao);
		EstoqueProdutoPK estoqueProdutoPkDestino = new EstoqueProdutoPK();
		estoqueProdutoPkDestino.setProduto(producao.getProduto());
		estoqueProdutoPkDestino.setEstoque(producao.getEstoque());				
		
		CadastroEstoqueProduto cadEstoqueProduto = CadastroEstoqueProduto.getInstancia();
		try{
			EstoqueProduto estProdDestino = cadEstoqueProduto.consultarPorId(estoqueProdutoPkDestino);
			estProdDestino.adicionarQuantidade(producao.getQuantidade(),producao.getVencimento());
			cadEstoqueProduto.alterar(estProdDestino);
		} catch (ObjectNotFoundException e) {			
			EstoqueProduto ep = new EstoqueProduto();
			ep.setPk(estoqueProdutoPkDestino);
			ep.adicionarQuantidade(producao.getQuantidade(),producao.getVencimento());
			cadEstoqueProduto.inserir(ep);

		}
			
		EstoqueProduto estProdOrigem = null;
		Collection c = CadastroProduto.getInstancia().consultarPorPK(producao.getProduto().getId()).getComposicao();
		Iterator it = c.iterator();
		while(it.hasNext()){
			Composicao comp = (Composicao)it.next();
			if(comp != null){
				EstoqueProdutoPK estoqueProdutoPkOrigem = new EstoqueProdutoPK();
				if(comp.getPk().getProduto().getEnquadramento().equals(Produto.FABRICADO)){
					atualizarEstoqueProduto(producao.getEstoque(), estoqueProdutoPkOrigem, comp.getPk().getProduto(), producao.getQuantidade().multiply(comp.getQuantidade()) , false);
				} else {
					try {		
						estoqueProdutoPkOrigem.setProduto(comp.getPk().getProduto());
						estoqueProdutoPkOrigem.setEstoque(producao.getEstoque());
						estProdOrigem = cadEstoqueProduto.consultarPorId(estoqueProdutoPkOrigem);
						estProdOrigem.subtrairQuantidade(producao.getQuantidade().multiply(comp.getQuantidade()),null);
						cadEstoqueProduto.alterar(estProdOrigem);
					} catch (ObjectNotFoundException e) {
						estProdOrigem = new EstoqueProduto();
						estProdOrigem.setPk(estoqueProdutoPkOrigem);
						estProdOrigem.subtrairQuantidade(producao.getQuantidade().multiply(comp.getQuantidade()),null);
						cadEstoqueProduto.inserir(estProdOrigem);
					}
				}
			}
		}			
		if (ajustaProduto){
			Produto prod = CadastroProduto.getInstancia().consultarPorPK(producao.getProduto().getId());
			Hibernate.initialize(prod.getLojas());
			Hibernate.initialize(prod.getComposicao());
			prod.setPrecoPadrao(producao.getPrecoVenda());
			prod.setPrecoPromocional(producao.getPrecoVenda());
			CadastroProduto.getInstancia().alterar(prod);			
		}
		
	}
	
	public void atualizarEstoqueProduto(Estoque estoque, EstoqueProdutoPK estoqueProdutoPK, Produto produto, BigDecimal quantidade, boolean somaEstoque) throws AppException{
		EstoqueProduto estProdOrigem = null;
		Collection c = produto.getComposicao();
		Iterator it = c.iterator();
		while(it.hasNext()){
			Composicao comp = (Composicao)it.next();
			if(comp != null){
				EstoqueProdutoPK estoqueProdutoPkOrigem = new EstoqueProdutoPK();
				if(comp.getPk().getProduto().getEnquadramento().equals(Produto.FABRICADO)){
					atualizarEstoqueProduto(estoque, estoqueProdutoPK, comp.getPk().getProduto(), quantidade.multiply(comp.getQuantidade()), false);
				}else{
					try {		
						estoqueProdutoPkOrigem.setProduto(comp.getPk().getProduto());
						estoqueProdutoPkOrigem.setEstoque(estoque);
						estProdOrigem = CadastroEstoqueProduto.getInstancia().consultarPorId(estoqueProdutoPkOrigem);
						if(somaEstoque){
							estProdOrigem.adicionarQuantidade(quantidade.multiply(comp.getQuantidade()),null);
						}else{
							estProdOrigem.subtrairQuantidade(quantidade.multiply(comp.getQuantidade()),null);	
						}
						CadastroEstoqueProduto.getInstancia().alterar(estProdOrigem);
					} catch (ObjectNotFoundException e) {
						estProdOrigem = new EstoqueProduto();
						estProdOrigem.setPk(estoqueProdutoPkOrigem);
						if(somaEstoque){
							estProdOrigem.adicionarQuantidade(quantidade.multiply(comp.getQuantidade()),null);
						}else{
							estProdOrigem.subtrairQuantidade(quantidade.multiply(comp.getQuantidade()),null);	
						}
						CadastroEstoqueProduto.getInstancia().inserir(estProdOrigem);
					}
				}
			}
		}			
	}
	
	public void alterar(Producao producao, BigDecimal quantidade) throws AppException{
		BigDecimal quantidadeAnterior = new BigDecimal(producao.getQuantidade().toString());
		BigDecimal quantidadeCalculada = quantidade.subtract(producao.getQuantidade()); 
		producao.setQuantidade(quantidade);
		
		getRepositorio().alterar(producao);
		
		EstoqueProdutoPK pk = new EstoqueProdutoPK();
		pk.setProduto(producao.getProduto());
		pk.setEstoque(producao.getEstoque());				
		
		
		try{
			EstoqueProduto estProd = CadastroEstoqueProduto.getInstancia().consultarPorId(pk);					
			estProd.adicionarQuantidade(quantidadeCalculada, producao.getVencimento());
			CadastroEstoqueProduto.getInstancia().alterar(estProd);
			
			Collection c = producao.getProduto().getComposicao();
			if (c != null && c.size() > 0){
				Iterator it = c.iterator();
				while(it.hasNext()){
					Composicao comp = (Composicao)it.next();
					if(comp != null){
						EstoqueProduto estProdOrigem = null;
						EstoqueProdutoPK estoqueProdutoPkOrigem = new EstoqueProdutoPK();
						estoqueProdutoPkOrigem = new EstoqueProdutoPK();
						estoqueProdutoPkOrigem.setProduto(comp.getPk().getProduto());
						estoqueProdutoPkOrigem.setEstoque(producao.getEstoque());
						try {																					
							estProdOrigem = CadastroEstoqueProduto.getInstancia().consultarPorId(estoqueProdutoPkOrigem);
							
							estProdOrigem.adicionarQuantidade(quantidadeAnterior.multiply(comp.getQuantidade()), producao.getVencimento());
							//BigDecimal qtdRemove = estProdOrigem.getQuantidade().add(quantidadeAnterior.multiply(comp.getQuantidade()));
							
							estProdOrigem.subtrairQuantidade(producao.getQuantidade().multiply(comp.getQuantidade()),null);
							//BigDecimal qtdAdd = qtdRemove.subtract(producao.getQuantidade().multiply(comp.getQuantidade()));
																					
							CadastroEstoqueProduto.getInstancia().alterar(estProdOrigem);
						} catch (ObjectNotFoundException e1) {
							estProdOrigem = new EstoqueProduto();
							estProdOrigem.setPk(estoqueProdutoPkOrigem);
							estProdOrigem.subtrairQuantidade(producao.getQuantidade().multiply(comp.getQuantidade()),null);
							CadastroEstoqueProduto.getInstancia().inserir(estProdOrigem);
						}
					}
				}
			}
			
		} catch (ObjectNotFoundException e) {			
			e.printStackTrace();
		}
	}
	
	public void excluir(Producao producao) throws AppException{
		getRepositorio().excluir(producao);
		
		EstoqueProdutoPK pk = new EstoqueProdutoPK();
		pk.setProduto(producao.getProduto());
		pk.setEstoque(producao.getEstoque());				
		
		BigDecimal qtdProducao = producao.getQuantidade();
		
		try{
			EstoqueProduto estProd = CadastroEstoqueProduto.getInstancia().consultarPorId(pk);
			estProd.subtrairQuantidade(producao.getQuantidade(),null);						
			CadastroEstoqueProduto.getInstancia().alterar(estProd);
			Collection c = producao.getProduto().getComposicao();
			if (c != null && c.size() > 0){
				Iterator it = c.iterator();
				while(it.hasNext()){
					Composicao comp = (Composicao)it.next();
					EstoqueProduto estProdOrigem = null;
					EstoqueProdutoPK estoqueProdutoPkOrigem = new EstoqueProdutoPK();
					estoqueProdutoPkOrigem.setProduto(comp.getPk().getProduto());
					estoqueProdutoPkOrigem.setEstoque(producao.getEstoque());												
					if(comp != null){						
						try {
							estProdOrigem = CadastroEstoqueProduto.getInstancia().consultarPorId(estoqueProdutoPkOrigem);
							estProdOrigem.adicionarQuantidade(producao.getQuantidade().multiply(comp.getQuantidade()),producao.getVencimento());
							CadastroEstoqueProduto.getInstancia().alterar(estProdOrigem);
						} catch (ObjectNotFoundException e1) {
							estProdOrigem = new EstoqueProduto();
							estProdOrigem.setPk(estoqueProdutoPkOrigem);
							estProdOrigem.subtrairQuantidade(producao.getQuantidade().multiply(comp.getQuantidade()),null);
							CadastroEstoqueProduto.getInstancia().inserir(estProdOrigem);
						}
					}
				}
			}
			
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
