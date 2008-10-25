package com.infinity.datamarket.comum.operacao;

import java.util.Collection;
import java.util.Iterator;

import org.hibernate.Query;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.cliente.CadastroCliente;
import com.infinity.datamarket.comum.cliente.Cliente;

import com.infinity.datamarket.comum.estoque.CadastroEstoque;
import com.infinity.datamarket.comum.estoque.Estoque;
import com.infinity.datamarket.comum.estoque.EstoquePK;
import com.infinity.datamarket.comum.estoque.EstoqueProduto;
import com.infinity.datamarket.comum.estoque.EstoqueProdutoPK;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.usuario.CadastroLoja;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.ValidationException;

public class CadastroOperacao extends Cadastro{
	private static CadastroOperacao instancia;
	private CadastroOperacao(){}
	public static CadastroOperacao getInstancia(){
		if (instancia == null){
			instancia = new CadastroOperacao();
		}
		return instancia;
	}

	public void inserirES(Operacao operacao) throws AppException{		
		getRepositorio().insert(operacao);
		if (operacao instanceof OperacaoDevolucao){
			OperacaoDevolucao operacaoDevolucao = (OperacaoDevolucao) operacao;
			if(operacaoDevolucao.getStatus() != ConstantesOperacao.CANCELADO){
				Collection col = operacaoDevolucao.getEventosOperacao();
				if (col != null && col.size() > 0){
					Iterator i = col.iterator();
					while(i.hasNext()){
						EventoOperacao evt = (EventoOperacao) i.next();
						if (evt instanceof EventoOperacaoItemRegistrado){
							EventoOperacaoItemRegistrado evir = (EventoOperacaoItemRegistrado) evt;							
							Loja l = CadastroLoja.getInstancia().consultarPorId(new Long(evir.getPk().getLoja()));
							EstoqueProdutoPK pk = new EstoqueProdutoPK();
							EstoquePK epk = new EstoquePK();
							epk.setLoja(l);
							epk.setId(l.getIdEstoque());
							Estoque e = new Estoque();
							e.setPk(epk);
							pk.setEstoque(e);
							Produto p = new Produto();
							p.setId(new Long(evir.getProdutoOperacaoItemRegistrado().getIdProduto()));
							pk.setProduto(p);
							try{
								EstoqueProduto estoqueProduto = CadastroEstoque.getInstancia().consultarEstoqueProduto(pk);
								estoqueProduto.setQuantidade(estoqueProduto.getQuantidade().add(evir.getQuantidade()));
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
	
//	public void atualizarES(Transacao trans, Collection<EventoItemRegistrado> itensRegistradosRemovidos) throws AppException{
//		BigDecimal quantidade = BigDecimal.ONE;
//		atualizar(trans, itensRegistradosRemovidos);
//		if (trans instanceof TransacaoCancelamento){
//			TransacaoCancelamento transCanc = (TransacaoCancelamento) trans;
//			TransacaoPK pk = new TransacaoPK(transCanc.getLojaCancelada(),transCanc.getComponenteCancelado(), transCanc.getNumeroTransacaoCancelada(),transCanc.getDataTransacaoCancelada());
//			TransacaoVenda transVenda = (TransacaoVenda) consultarPorPK(pk);
//			transVenda.setSituacao(TransacaoVenda.CANCELADO);
//			atualizar(transVenda);
//		}else if (trans instanceof TransacaoVenda){
//			TransacaoVenda transVenda = (TransacaoVenda) trans;
//			if(transVenda.getSituacao().equals(TransacaoVenda.ATIVO)){
//				if(itensRegistradosRemovidos != null){
//					Iterator<EventoItemRegistrado> it = itensRegistradosRemovidos.iterator();
//					while(it.hasNext()){
//						EventoItemRegistrado evir = it.next();
//						Loja l = CadastroLoja.getInstancia().consultarPorId(new Long(evir.getPk().getLoja()));
//						EstoqueProdutoPK pk = new EstoqueProdutoPK();
//						EstoquePK epk = new EstoquePK();
//						epk.setLoja(l);
//						epk.setId(l.getIdEstoque());
//						Estoque e = new Estoque();
//						e.setPk(epk);
//						pk.setEstoque(e);
//						Produto p = new Produto();
//						p.setId(new Long(evir.getProdutoItemRegistrado().getIdProduto()));
//						pk.setProduto(p);
//						try{
//							EstoqueProduto estoqueProduto = CadastroEstoque.getInstancia().consultarEstoqueProduto(pk);
//							quantidade = estoqueProduto.getQuantidade().add(evir.getQuantidade());
//							estoqueProduto.setQuantidade(quantidade);
//							getRepositorio().update(estoqueProduto);
//						}catch(ObjectNotFoundException ex){
//							EstoqueProduto estoqueProduto = new EstoqueProduto();
//							estoqueProduto.setPk(pk);
//							estoqueProduto.setQuantidade(evir.getQuantidade());
//							getRepositorio().insert(estoqueProduto);
//						}catch(Exception ex){
//							System.out.println("Estoque da loja "+evir.getPk().getLoja()+" não foi atualizado");
//							ex.printStackTrace();
//						}
//					}
//				}
//				Collection col = transVenda.getEventosTransacao();
//				if (col != null && col.size() > 0){
//					Iterator i = col.iterator();
//					while(i.hasNext()){
//						EventoTransacao evt = (EventoTransacao) i.next();
//						if (evt instanceof EventoItemRegistrado){
//							EventoItemRegistrado evir = (EventoItemRegistrado) evt;
//							if(!evir.getAcao().equals(EventoItemRegistrado.ITEM_NAO_ALTERADO)){								
//								if (evir.getSituacao().equals(EventoItemRegistrado.ATIVO)){
//									Loja l = CadastroLoja.getInstancia().consultarPorId(new Long(evir.getPk().getLoja()));
//									EstoqueProdutoPK pk = new EstoqueProdutoPK();
//									EstoquePK epk = new EstoquePK();
//									epk.setLoja(l);
//									epk.setId(l.getIdEstoque());
//									Estoque e = new Estoque();
//									e.setPk(epk);
//									pk.setEstoque(e);
//									Produto p = new Produto();
//									p.setId(new Long(evir.getProdutoItemRegistrado().getIdProduto()));
//									pk.setProduto(p);
//									try{
//										EstoqueProduto estoqueProduto = CadastroEstoque.getInstancia().consultarEstoqueProduto(pk);
//										if(evir.getAcao().equals(EventoItemRegistrado.ITEM_INSERIDO)){
//											quantidade = estoqueProduto.getQuantidade().subtract(evir.getQuantidade());										
//										}else if(evir.getAcao().equals(EventoItemRegistrado.ITEM_EXCLUIDO)){
//											quantidade = estoqueProduto.getQuantidade().add(evir.getQuantidade());
//										}
//										estoqueProduto.setQuantidade(quantidade);
//										getRepositorio().update(estoqueProduto);
//									}catch(ObjectNotFoundException ex){
//										EstoqueProduto estoqueProduto = new EstoqueProduto();
//										estoqueProduto.setPk(pk);
//										estoqueProduto.setQuantidade(evir.getQuantidade().negate());
//										getRepositorio().insert(estoqueProduto);
//									}catch(Exception ex){
//										System.out.println("Estoque da loja "+evir.getPk().getLoja()+" não foi atualizado");
//										ex.printStackTrace();
//									}
//								}
//							}
//						}
//					}
//				}
//			}else if(transVenda.getSituacao().equals(TransacaoVenda.CANCELADO)){
//				Collection col = transVenda.getEventosTransacao();
//				if (col != null && col.size() > 0){
//					Iterator i = col.iterator();
//					while(i.hasNext()){
//						EventoTransacao evt = (EventoTransacao) i.next();
//						if (evt instanceof EventoItemRegistrado){
//							EventoItemRegistrado evir = (EventoItemRegistrado) evt;
//							if (evir.getSituacao().equals(EventoItemRegistrado.ATIVO)){
//								Loja l = CadastroLoja.getInstancia().consultarPorId(new Long(evir.getPk().getLoja()));
//								EstoqueProdutoPK pk = new EstoqueProdutoPK();
//								EstoquePK epk = new EstoquePK();
//								epk.setLoja(l);
//								epk.setId(l.getIdEstoque());
//								Estoque e = new Estoque();
//								e.setPk(epk);
//								pk.setEstoque(e);
//								Produto p = new Produto();
//								p.setId(new Long(evir.getProdutoItemRegistrado().getIdProduto()));
//								pk.setProduto(p);
//								try{
//									EstoqueProduto estoqueProduto = CadastroEstoque.getInstancia().consultarEstoqueProduto(pk);
//									quantidade = estoqueProduto.getQuantidade().add(evir.getQuantidade());
//									estoqueProduto.setQuantidade(quantidade);
//									getRepositorio().update(estoqueProduto);
//								}catch(ObjectNotFoundException ex){
//									EstoqueProduto estoqueProduto = new EstoqueProduto();
//									estoqueProduto.setPk(pk);
//									estoqueProduto.setQuantidade(evir.getQuantidade());
//									getRepositorio().insert(estoqueProduto);
//								}catch(Exception ex){
//									System.out.println("Estoque da loja "+evir.getPk().getLoja()+" não foi atualizado");
//									ex.printStackTrace();
//								}
//							}
//						}
//					}
//				}
//			}
//		}
//	}

	public Operacao consultarPorPK(OperacaoPK pk) throws AppException{
		Operacao operacao = (Operacao) getRepositorio().findById(Operacao.class, pk);
		return operacao;
	}
		
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	
	public void alterarStatus(OperacaoPK pk, int status) throws AppException{		
		if (status == ConstantesOperacao.ABERTO || status == ConstantesOperacao.CANCELADO||
				status == ConstantesOperacao.FECHADO || status == ConstantesOperacao.EM_PROCESSAMENTO){
			Operacao operacao = (Operacao) getRepositorio().findById(Operacao.class, pk);
			operacao.setStatus(status);
			getRepositorio().update(operacao);
		}else{
			throw new ValidationException("Status de Operação Inválido");
		}
	}

	public Integer retornaMaxIdOperacaoPorLoja(OperacaoPK pk) throws AppException{
		Integer retorno = new Integer(0);
		StringBuffer sb = new StringBuffer();
		sb.append("select max(operacao.pk.id) from Operacao operacao where loja = " + pk.getLoja());
		Query query = RepositoryManagerHibernateUtil.currentSession().createQuery(sb.toString());
		Integer qretorno = (Integer)query.uniqueResult(); 		
		if (qretorno != null){
			retorno = qretorno + 1;	
		}	
		retorno++;
		return retorno;
	}
	
}
