package com.infinity.datamarket.comum.estoque;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.financeiro.CadastroGrupoLancamento;
import com.infinity.datamarket.comum.financeiro.GrupoLancamento;
import com.infinity.datamarket.comum.financeiro.IRepositorioLancamento;
import com.infinity.datamarket.comum.financeiro.Lancamento;
import com.infinity.datamarket.comum.produto.CadastroProduto;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.CadastroControleId;
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

	public IRepositorioLancamento getRepositorioLancamento() {
		// TODO Auto-generated method stub
		return (IRepositorioLancamento) super.getRepositorio(IRepositorio.REPOSITORIO_LANCAMENTO);
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
			try{
				BigDecimal valorUnitario = pep.getPrecoUnitario();
				Produto produto = pep.getPk().getProduto();
				produto.setPrecoCompra(valorUnitario);
				CadastroProduto.getInstancia().alterar(produto);
			}catch(ObjectNotFoundException e){
				System.out.println("NÃO FOI ALTERADO O VALOR DO PRODUTO");
			}
		}
		Lancamento lancamento = new Lancamento();
		lancamento.setId(CadastroControleId.getInstancia().getControle(Lancamento.class).getValor());
		lancamento.setDescricao("ENTRADA DE PRODUTOS");
		lancamento.setIdEntradaProduto(entradaProduto.getId());
		lancamento.setDataLancamento(entradaProduto.getDataEntrada());
		lancamento.setDataVencimento(entradaProduto.getDataVencimento());
		lancamento.setFornecedor(entradaProduto.getFornecedor());
		lancamento.setGrupo(CadastroGrupoLancamento.getInstancia().consultarPorId(GrupoLancamento.GRUPO_ENTRADA_PRODUTO));
		lancamento.setLoja(entradaProduto.getEstoque().getPk().getLoja());
		lancamento.setNumeroDocumento(entradaProduto.getNumeroNota());
		lancamento.setObservacao("ENTRADA DE PRODUTOS EM ESTOQUE");
		lancamento.setSituacao(Lancamento.ABERTO);
		lancamento.setTipoLancamento(Lancamento.DEBITO);
		lancamento.setValor(entradaProduto.getValor());
		getRepositorioLancamento().inserir(lancamento);
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
			try{
				BigDecimal valorUnitario = pep.getPrecoUnitario();
				Produto produto = pep.getPk().getProduto();
				produto.setPrecoCompra(valorUnitario);
				CadastroProduto.getInstancia().alterar(produto);
			}catch(ObjectNotFoundException e){
				System.out.println("NÃO FOI ALTERADO O VALOR DO PRODUTO");
			}
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
