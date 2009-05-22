package com.infinity.datamarket.comum.financeiro;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import com.infinity.datamarket.comum.conta.CadastroContaCorrente;
import com.infinity.datamarket.comum.conta.CadastroMovimentacaoBancaria;
import com.infinity.datamarket.comum.conta.ContaCorrente;
import com.infinity.datamarket.comum.conta.MovimentacaoBancaria;
import com.infinity.datamarket.comum.conta.RepositorioContaCorrente;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.ConcentradorControleId;
import com.infinity.datamarket.comum.util.Controle;
import com.infinity.datamarket.comum.util.IRepositorio;
import com.infinity.datamarket.comum.util.IRepositorioControleId;

public class CadastroLancamento extends Cadastro{
	private static CadastroLancamento instancia;
	
	private CadastroLancamento(){}
	public static CadastroLancamento getInstancia(){
		if (instancia == null){
			instancia = new CadastroLancamento();
		}
		return instancia;
	}

	
	public IRepositorioLancamento getRepositorio() {
		// TODO Auto-generated method stub
		return (IRepositorioLancamento) super.getRepositorio(IRepositorio.REPOSITORIO_LANCAMENTO);
	}
	
	public Lancamento consultarPorId(Long id) throws AppException{
		return getRepositorio().consultarPorId(id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}
	public void inserir(Lancamento lancamento) throws AppException{
		getRepositorio().inserir(lancamento);
	}
	
	public IRepositorioControleId getRepositorioControleId() {
		return (IRepositorioControleId) super.getRepositorio(IRepositorio.REPOSITORIO_CONTROLE_ID);
	}

	public void baixar(Lancamento lancamento) throws AppException{
		getRepositorio().alterar(lancamento);
		if(lancamento.getSituacao().equals(Lancamento.PAGTO_PARCIAL) || lancamento.getSituacao().equals(Lancamento.FINALIZADO)){
			Iterator<BaixaLancamento> it = lancamento.getItensPagamento().iterator();
			while(it.hasNext()){
				BaixaLancamento itemBaixaLanc = it.next();
				if(itemBaixaLanc.getItemLancadoCtaCorrente().equals("N") && itemBaixaLanc.getSituacao().equals(BaixaLancamento.ATIVO)){				
					if(lancamento.getTipoLancamento().equals(Lancamento.CREDITO)){ // conta a receber
						// inserir em conta corrente como CREDITO
						lancarItemBaixaLancamentoContaCorrente(lancamento, itemBaixaLanc, Lancamento.CREDITO);
					}else if(lancamento.getTipoLancamento().equals(Lancamento.DEBITO)){ // conta a pagar
						// inserir em conta corrente como DEBITO
						lancarItemBaixaLancamentoContaCorrente(lancamento, itemBaixaLanc, Lancamento.DEBITO);
					}
				}
			}
			if(lancamento.getItensPagamentoExcluidos() != null){
				Iterator<BaixaLancamento> it2 = lancamento.getItensPagamentoExcluidos().iterator();
				while(it2.hasNext()){
					BaixaLancamento itemBaixaLancExc = it2.next();
					if(itemBaixaLancExc.getItemLancadoCtaCorrente().equals("S") && itemBaixaLancExc.getSituacao().equals(BaixaLancamento.CANCELADO)){				
						if(lancamento.getTipoLancamento().equals(Lancamento.CREDITO)){ // conta a receber
							// inserir em conta corrente como DEBITO
							lancarItemBaixaLancamentoContaCorrente(lancamento, itemBaixaLancExc, Lancamento.DEBITO);	
						}else if(lancamento.getTipoLancamento().equals(Lancamento.DEBITO)){ // conta a pagar
							// inserir em conta corrente como CREDITO
							lancarItemBaixaLancamentoContaCorrente(lancamento, itemBaixaLancExc, Lancamento.CREDITO);
						}
					}
				}
			}
		}else if(lancamento.getSituacao().equals(Lancamento.CANCELADO)){
			Iterator<BaixaLancamento> it = lancamento.getItensPagamento().iterator();
			while(it.hasNext()){
				BaixaLancamento itemBaixaLanc = it.next();				
				if(lancamento.getTipoLancamento().equals(Lancamento.CREDITO)){ // conta a receber
					// inserir em conta corrente como DEBITO
					lancarItemBaixaLancamentoContaCorrente(lancamento, itemBaixaLanc, Lancamento.DEBITO);
				}else if(lancamento.getTipoLancamento().equals(Lancamento.DEBITO)){ // conta a pagar
					// inserir em conta corrente como CREDITO
					lancarItemBaixaLancamentoContaCorrente(lancamento, itemBaixaLanc, Lancamento.CREDITO);
				}
			}
		}
		Set<ContaCorrente> hashContas = CadastroMovimentacaoBancaria.getInstancia().getHashContas();
		
		if(hashContas.size() > 0 ){
			RepositoryManagerHibernateUtil.beginTrasaction();
		}
		
		Iterator<ContaCorrente> it = hashContas.iterator();
		
		
		while (it.hasNext()){
			ContaCorrente cTmp = (ContaCorrente)it.next();
			ContaCorrente c = CadastroContaCorrente.getInstancia().consultarPorId(cTmp.getId());
			c.setSaldo(cTmp.getSaldo());
			RepositorioContaCorrente repCtaCorrente = (RepositorioContaCorrente)super.getRepositorio(IRepositorio.REPOSITORIO_CONTA_CORRENTE);
			repCtaCorrente.alterar(c);			
		}
		
		if(hashContas.size() > 0){
			RepositoryManagerHibernateUtil.commitTransation();
		}
		CadastroMovimentacaoBancaria.getInstancia().setHashContas(new TreeSet<ContaCorrente>());
	}
	
	public void lancarItemBaixaLancamentoContaCorrente(Lancamento lancamento, BaixaLancamento itemBaixaLanc, String tipoLancamento) throws AppException{

		MovimentacaoBancaria movimentacao = new MovimentacaoBancaria();
		Controle retorno = null;
		try {
			retorno = (Controle) getRepositorioControleId().getControle(MovimentacaoBancaria.class);
		}catch(ObjectNotFoundException e){
		}
		if (retorno == null) {
			retorno = new Controle();
			retorno.setChave(MovimentacaoBancaria.class.getSimpleName());
			retorno.setValor(new ConcentradorControleId().retornaMaxId(MovimentacaoBancaria.class)+1);
			getRepositorioControleId().inserirControle(retorno);
		} else {
			retorno.setValor(retorno.getValor()+1);
			getRepositorioControleId().atualizarControle(retorno);
		}

		movimentacao.setId(retorno.getValor());
		movimentacao.setConta(itemBaixaLanc.getContaCorrente());
		movimentacao.setForma(itemBaixaLanc.getFormaRecebimento());
		movimentacao.setData(lancamento.getDataPagamento());
		movimentacao.setValor(itemBaixaLanc.getValorTotalItem().setScale(2));
		movimentacao.setTipo(tipoLancamento);
		movimentacao.setNumero(itemBaixaLanc.getNumeroDocumento());
		
		CadastroMovimentacaoBancaria.getInstancia().inserir(movimentacao);
	}
	
	public void alterar(Lancamento lancamento) throws AppException{
		getRepositorio().alterar(lancamento);
	}

	public void excluir(Lancamento lancamento) throws AppException{
		getRepositorio().excluir(lancamento);
	}

}
