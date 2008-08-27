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
		}
	}
	
	public void inserirES(Transacao trans) throws AppException{		
		getRepositorio().insert(trans);
		if (trans instanceof TransacaoCancelamento){
			TransacaoCancelamento transCanc = (TransacaoCancelamento) trans;
			TransacaoPK pk = new TransacaoPK(transCanc.getLojaCancelada(),transCanc.getComponenteCancelado(), transCanc.getNumeroTransacaoCancelada(),transCanc.getDataTransacaoCancelada());
			TransacaoVenda transVenda = (TransacaoVenda) consultarPorPK(pk);
			transVenda.setSituacao(TransacaoVenda.CANCELADO);
			atualizar(transVenda);
		}else if (trans instanceof TransacaoVenda){
			TransacaoVenda transVenda = (TransacaoVenda) trans;
			if(transVenda.getSituacao().equals(TransacaoVenda.ATIVO)){
				Collection col = transVenda.getEventosTransacao();
				if (col != null && col.size() > 0){
					Iterator i = col.iterator();
					while(i.hasNext()){
						EventoTransacao evt = (EventoTransacao) i.next();
						if (evt instanceof EventoItemRegistrado){
							EventoItemRegistrado evir = (EventoItemRegistrado) evt;
							if (evir.getSituacao().equals(EventoItemRegistrado.ATIVO)){
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
									System.out.println("Estoque da loja "+evir.getPk().getLoja()+" não foi atualizado");
									ex.printStackTrace();
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void atualizarES(Transacao trans, Collection<EventoItemRegistrado> itensRegistradosRemovidos) throws AppException{
		BigDecimal quantidade = BigDecimal.ONE;
		atualizar(trans, itensRegistradosRemovidos);
		if (trans instanceof TransacaoCancelamento){
			TransacaoCancelamento transCanc = (TransacaoCancelamento) trans;
			TransacaoPK pk = new TransacaoPK(transCanc.getLojaCancelada(),transCanc.getComponenteCancelado(), transCanc.getNumeroTransacaoCancelada(),transCanc.getDataTransacaoCancelada());
			TransacaoVenda transVenda = (TransacaoVenda) consultarPorPK(pk);
			transVenda.setSituacao(TransacaoVenda.CANCELADO);
			atualizar(transVenda);
		}else if (trans instanceof TransacaoVenda){
			TransacaoVenda transVenda = (TransacaoVenda) trans;
			if(transVenda.getSituacao().equals(TransacaoVenda.ATIVO)){
				if(itensRegistradosRemovidos != null){
					Iterator<EventoItemRegistrado> it = itensRegistradosRemovidos.iterator();
					while(it.hasNext()){
						EventoItemRegistrado evir = it.next();
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
							quantidade = estoqueProduto.getQuantidade().add(evir.getQuantidade());
							estoqueProduto.setQuantidade(quantidade);
							getRepositorio().update(estoqueProduto);
						}catch(ObjectNotFoundException ex){
							EstoqueProduto estoqueProduto = new EstoqueProduto();
							estoqueProduto.setPk(pk);
							estoqueProduto.setQuantidade(evir.getQuantidade());
							getRepositorio().insert(estoqueProduto);
						}catch(Exception ex){
							System.out.println("Estoque da loja "+evir.getPk().getLoja()+" não foi atualizado");
							ex.printStackTrace();
						}
					}
				}
				Collection col = transVenda.getEventosTransacao();
				if (col != null && col.size() > 0){
					Iterator i = col.iterator();
					while(i.hasNext()){
						EventoTransacao evt = (EventoTransacao) i.next();
						if (evt instanceof EventoItemRegistrado){
							EventoItemRegistrado evir = (EventoItemRegistrado) evt;
							if(!evir.getAcao().equals(EventoItemRegistrado.ITEM_NAO_ALTERADO)){								
								if (evir.getSituacao().equals(EventoItemRegistrado.ATIVO)){
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
										if(evir.getAcao().equals(EventoItemRegistrado.ITEM_INSERIDO)){
											quantidade = estoqueProduto.getQuantidade().subtract(evir.getQuantidade());										
										}else if(evir.getAcao().equals(EventoItemRegistrado.ITEM_EXCLUIDO)){
											quantidade = estoqueProduto.getQuantidade().add(evir.getQuantidade());
										}
										estoqueProduto.setQuantidade(quantidade);
										getRepositorio().update(estoqueProduto);
									}catch(ObjectNotFoundException ex){
										EstoqueProduto estoqueProduto = new EstoqueProduto();
										estoqueProduto.setPk(pk);
										estoqueProduto.setQuantidade(evir.getQuantidade().negate());
										getRepositorio().insert(estoqueProduto);
									}catch(Exception ex){
										System.out.println("Estoque da loja "+evir.getPk().getLoja()+" não foi atualizado");
										ex.printStackTrace();
									}
								}
							}
						}
					}
				}
			}else if(transVenda.getSituacao().equals(TransacaoVenda.CANCELADO)){
				Collection col = transVenda.getEventosTransacao();
				if (col != null && col.size() > 0){
					Iterator i = col.iterator();
					while(i.hasNext()){
						EventoTransacao evt = (EventoTransacao) i.next();
						if (evt instanceof EventoItemRegistrado){
							EventoItemRegistrado evir = (EventoItemRegistrado) evt;
							if (evir.getSituacao().equals(EventoItemRegistrado.ATIVO)){
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
									quantidade = estoqueProduto.getQuantidade().add(evir.getQuantidade());
									estoqueProduto.setQuantidade(quantidade);
									getRepositorio().update(estoqueProduto);
								}catch(ObjectNotFoundException ex){
									EstoqueProduto estoqueProduto = new EstoqueProduto();
									estoqueProduto.setPk(pk);
									estoqueProduto.setQuantidade(evir.getQuantidade());
									getRepositorio().insert(estoqueProduto);
								}catch(Exception ex){
									System.out.println("Estoque da loja "+evir.getPk().getLoja()+" não foi atualizado");
									ex.printStackTrace();
								}
							}
						}
					}
				}
			}
		}
	}

	public Transacao consultarPorPK(TransacaoPK pk) throws AppException{
		Transacao trans = (Transacao) getRepositorio().findById(Transacao.class, pk);
		return trans;
	}
	
	private void atualizar(Transacao trans) throws AppException{
//		getRepositorio().evict(trans);
//		getRepositorio().insertOrUpdate(trans);
		getRepositorio().update(trans);
	}

	private void atualizar(Transacao trans, Collection<EventoItemRegistrado> itensRegistradosRemovidos) throws AppException{
//		getRepositorio().evict(trans);
//		getRepositorio().insertOrUpdate(trans);
		if(itensRegistradosRemovidos != null){
			Iterator<EventoItemRegistrado> it = itensRegistradosRemovidos.iterator();
			while(it.hasNext()){
				EventoTransacaoPK evTransPK = it.next().getPk();
				getRepositorio().removeById(EventoItemRegistrado.class, evTransPK);
//				getRepositorio().removeById(EventoTransacao.class, evTransPK);
			}
		}
		getRepositorio().update(trans);
	}

	public void atualizaTransacaoProcessada(Transacao trans) throws AppException{
		trans.setStatus(Transacao.PROCESSADO);
		atualizar(trans);
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}

	public void inserirCliente(ClienteTransacao cli) throws AppException{
		getRepositorio().insert(cli);
	}
	
	public void atualizarCliente(ClienteTransacao cli) throws AppException{
		getRepositorio().update(cli);
	}
	
	public ClienteTransacao consultarClienteTransacaoPorID(String id) throws AppException{
		return (ClienteTransacao) getRepositorio().findById(ClienteTransacao.class, id);
	}
	
}
