package com.infinity.datamarket.comum.transacao;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.estoque.CadastroEstoque;
import com.infinity.datamarket.comum.estoque.Estoque;
import com.infinity.datamarket.comum.estoque.EstoquePK;
import com.infinity.datamarket.comum.estoque.EstoqueProduto;
import com.infinity.datamarket.comum.estoque.EstoqueProdutoPK;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.totalizadores.CadastroTotalizadores;
import com.infinity.datamarket.comum.totalizadores.ConstantesTotalizadoresNaoFiscais;
import com.infinity.datamarket.comum.usuario.CadastroLoja;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

public class CadastroTransacao extends Cadastro{
	private static CadastroTransacao instancia;
	private CadastroTransacao(){}
	public static CadastroTransacao getInstancia(){
		if (instancia == null){
			instancia = new CadastroTransacao();
		}
		return instancia;
	}

	private CadastroTotalizadores getCadastroTotalizadores(){
		return CadastroTotalizadores.getInstancia();
	}

	public void inserir(Transacao trans) throws AppException{
		getRepositorio().insert(trans);
		if (trans instanceof TransacaoResuprimento){
			TransacaoResuprimento transResuprimento = (TransacaoResuprimento) trans;
			BigDecimal valor = transResuprimento.getValor();
			getCadastroTotalizadores().incrementarTotalizador(ConstantesTotalizadoresNaoFiscais.SUPRIMENTO, valor);
		}else if (trans instanceof TransacaoSangria){
			TransacaoSangria transSangria = (TransacaoSangria) trans;
			BigDecimal valor = transSangria.getValor();
			getCadastroTotalizadores().incrementarTotalizador(ConstantesTotalizadoresNaoFiscais.SANGRIA, valor);
		}else if (trans instanceof TransacaoEntradaOperador){
			TransacaoEntradaOperador transEntradaOperador = (TransacaoEntradaOperador) trans;
			BigDecimal valor = transEntradaOperador.getFundoTroco();
			getCadastroTotalizadores().incrementarTotalizador(ConstantesTotalizadoresNaoFiscais.SUPRIMENTO, valor);
		}else if (trans instanceof TransacaoCancelamento){
			TransacaoCancelamento transCanc = (TransacaoCancelamento) trans;
			TransacaoPK pk = new TransacaoPK(transCanc.getLojaCancelada(),transCanc.getComponenteCancelado(), transCanc.getNumeroTransacaoCancelada(),transCanc.getDataTransacaoCancelada());
			TransacaoVenda transVenda = (TransacaoVenda) consultarPorPK(pk);
			transVenda.setSituacao(TransacaoVenda.CANCELADO);
			atualizar(transVenda);
		}else if (trans instanceof TransacaoVenda){
			TransacaoVenda transVenda = (TransacaoVenda) trans;
			Collection col = transVenda.getEventosTransacao();
			if (col != null && col.size() > 0){
				Iterator i = col.iterator();
				while(i.hasNext()){
					EventoTransacao evt = (EventoTransacao) i.next();
					if (evt instanceof EventoItemRegistrado){
						EventoItemRegistrado evir = (EventoItemRegistrado) evt;
						Loja l = CadastroLoja.getInstancia().consultarPorId(new Long(evir.getPk().getLoja()));
						EstoqueProdutoPK pk = new EstoqueProdutoPK();
						EstoquePK epk = new EstoquePK();
						epk.setLoja(l);
						epk.setId(l.getIdEstoque());
						Estoque e = new Estoque();
						e.setPk(epk);
						pk.setEstoque(e);
						Produto p = new Produto();
						p.setId(new Long(evir.getProdutoItemRegistrado().getIdProduto()));
						pk.setProduto(p);
						try{
							EstoqueProduto estoqueProduto = CadastroEstoque.getInstancia().consultarEstoqueProduto(pk);
							estoqueProduto.setQuantidade(estoqueProduto.getQuantidade().subtract(evir.getQuantidade()));
							getRepositorio().update(estoqueProduto);
						}catch(ObjectNotFoundException ex){
							EstoqueProduto estoqueProduto = new EstoqueProduto();
							estoqueProduto.setPk(pk);
							estoqueProduto.setQuantidade(evir.getQuantidade().negate());
							getRepositorio().insert(estoqueProduto);
						}catch(Exception ex){
							System.out.println("Estoque da loja "+evir.getPk().getLoja()+" n�o foi atualizado");
							ex.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	public Transacao consultarPorPK(TransacaoPK pk) throws AppException{
		return (Transacao) getRepositorio().findById(Transacao.class, pk);
	}
	
	private void atualizar(Transacao trans) throws AppException{
		getRepositorio().update(trans);
	}
	
	public void atualizaTransacaoProcessada(Transacao trans) throws AppException{
		trans.setStatus(Transacao.PROCESSADO);
		atualizar(trans);
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}



}