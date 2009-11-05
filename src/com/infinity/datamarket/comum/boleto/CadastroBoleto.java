package com.infinity.datamarket.comum.boleto;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import com.infinity.datamarket.comum.financeiro.BaixaLancamento;
import com.infinity.datamarket.comum.financeiro.CadastroLancamento;
import com.infinity.datamarket.comum.financeiro.Lancamento;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.CadastroControleId;
import com.infinity.datamarket.comum.util.Controle;
import com.infinity.datamarket.comum.util.IRepositorio;

public class CadastroBoleto extends Cadastro{
	
	private static CadastroBoleto instancia;
	
//	private static final Class CLASSE = Boleto.class;
	
	public static CadastroBoleto getInstancia(){
		if (instancia == null){
			instancia = new CadastroBoleto();			
		}
		return instancia;
	}
	
	private CadastroLancamento getCadastroLancamento(){
		return CadastroLancamento.getInstancia();
	}
	
	public IRepositorioBoleto getRepositorio() {
		// TODO Auto-generated method stub
		return (IRepositorioBoleto) super.getRepositorio(IRepositorio.REPOSITORIO_BOLETO);
	}
	
	public void inserir(Boleto Boleto) throws AppException{
		Controle controle = CadastroControleId.getInstancia().getControle(Boleto.class);
		
		Boleto.setId(controle.getValor());
		getRepositorio().inserir(Boleto);
	}
	
	public void alterar(Boleto Boleto) throws AppException{
		getRepositorio().alterar(Boleto);
	}
	
	public Boleto baixarBoleto(Long idBoleto){
	
		try {
			Boleto bol = consultarID(idBoleto);			
			if (bol == null) {
				return null;
			}
			bol.setStatus(Boleto.PAGO);
			this.alterar(bol);
			//BAIXAR O LANCAMENTO
			PropertyFilter filter = new PropertyFilter();
			filter.setTheClass(Lancamento.class);
			filter.addProperty("boleto.id", idBoleto);

			try {
				Collection coll = getCadastroLancamento().consultar(filter);
				if (coll != null && !coll.isEmpty()) {
					Lancamento lancamento = (Lancamento)coll.iterator().next();
					lancamento.setSituacao(Lancamento.FINALIZADO);
					lancamento.setDataPagamento(new Date());
					getCadastroLancamento().baixar(lancamento);
			
					Iterator<BaixaLancamento> it0 = lancamento.getItensPagamento().iterator();
					while(it0.hasNext()){
						BaixaLancamento itemBaixaLanc = it0.next();
						itemBaixaLanc.setItemLancadoCtaCorrente("S");
						itemBaixaLanc.setBoleto(bol);
					}
					getCadastroLancamento().alterar(lancamento);
				}
			} catch (AppException e) {
				// TODO: handle exception
			}
//
//			Lancamento lancamento = this.getLancamento();
//			
//			Set<BaixaLancamento> c1 = new HashSet<BaixaLancamento>();
//			
//			for (Iterator iter = this.getItensBaixaLancamento().iterator(); iter.hasNext();) {
//				BaixaLancamento element = (BaixaLancamento) iter.next();
//				c1.add(element);
//			}
//			
//			lancamento.setItensPagamento(c1);
//			if(this.getItensBaixaLancamentoExcluidos() != null){
//				Set<BaixaLancamento> c2 = new HashSet<BaixaLancamento>();
//				for (Iterator iter = this.getItensBaixaLancamentoExcluidos().iterator(); iter.hasNext();) {
//					BaixaLancamento element = (BaixaLancamento) iter.next();
//					c2.add(element);
//				}
//				lancamento.setItensPagamentoExcluidos(c2);
//			}
//			
//			if(lancamento.getValor().setScale(2).compareTo(this.getValorTotalPago().setScale(2)) > 0){
//				lancamento.setSituacao(Lancamento.PAGTO_PARCIAL);
//			}else if(lancamento.getValor().setScale(2).compareTo(this.getValorTotalPago().setScale(2)) == 0){
//				lancamento.setSituacao(Lancamento.FINALIZADO);
//			} 
//			logger.info("baixarLancamento - tipo do Lancamento para baixa --> "+lancamento.getTipoLancamento());
//			logger.info("baixarLancamento - nova situacao do lancamento --> "+lancamento.getSituacao() + " - " + retornaDescricaoSituacao(lancamento.getSituacao()));
//			lancamento.setDataPagamento(getDataSistema());
//			logger.info("baixarLancamento - data do Pagamento --> "+Util.retornaDataFormatoDDMMYYYY(getDataSistema()));
//
//			getFachada().baixarLancamento(lancamento);
//			logger.info("baixarLancamento - lancamento dado baixa com sucesso!");
//			
//			Iterator<BaixaLancamento> it0 = lancamento.getItensPagamento().iterator();
//			while(it0.hasNext()){
//				BaixaLancamento itemBaixaLanc = it0.next();
//				itemBaixaLanc.setItemLancadoCtaCorrente("S");
//			}
//			logger.info("baixarLancamento - atualizar status dos itens lancados em conta corrente.");
//			getFachada().alterarLancamento(lancamento);
//			logger.info("baixarLancamento - atualizou status dos itens lancados em conta corrente.!");
			
			
			return bol;
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return null;
	}
	
	public Boleto consultarID(Long idboleto)  throws AppException {
		PropertyFilter filter = new PropertyFilter();
		filter.setTheClass(Boleto.class);
		filter.addProperty("id", idboleto);
		

		Collection coll = consultar(filter);
		if (coll != null) {
			Iterator it = coll.iterator();
			if (it.hasNext()) {
				return (Boleto)it.next();
			}
		}
		
		return null;
	}
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
}