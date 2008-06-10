package com.infinity.datamarket.pdv.mic;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import com.infinity.datamarket.comum.pagamento.ConstantesFormaRecebimento;
import com.infinity.datamarket.comum.pagamento.DadosCartaoOff;
import com.infinity.datamarket.comum.pagamento.DadosCartaoProprio;
import com.infinity.datamarket.comum.pagamento.DadosCheque;
import com.infinity.datamarket.comum.pagamento.DadosChequePredatado;
import com.infinity.datamarket.comum.pagamento.PlanoPagamento;
import com.infinity.datamarket.comum.transacao.ConstantesEventoTransacao;
import com.infinity.datamarket.comum.transacao.EventoItemPagamento;
import com.infinity.datamarket.comum.transacao.EventoItemPagamentoCartaoOff;
import com.infinity.datamarket.comum.transacao.EventoItemPagamentoCartaoProprio;
import com.infinity.datamarket.comum.transacao.EventoItemPagamentoCheque;
import com.infinity.datamarket.comum.transacao.EventoItemPagamentoChequePredatado;
import com.infinity.datamarket.comum.transacao.EventoTransacaoPK;
import com.infinity.datamarket.comum.transacao.ParcelaEventoItemPagamentoChequePredatado;
import com.infinity.datamarket.comum.transacao.ParcelaEventoItemPagamentoChequePredatadoPK;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.comum.util.ConjuntoEventoTransacao;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class MicProcessaPlano extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		BigDecimal valorPagamento = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.VALOR_PAGAMENTO_ATUAL);
		PlanoPagamento plano = (PlanoPagamento) gerenciadorPerifericos.getCmos().ler(CMOS.PLANO_PAGAMENTO_ATUAL);
		BigDecimal subTotal = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.SUB_TOTAL);

		if (valorPagamento.compareTo(subTotal) > 0){
			valorPagamento = subTotal;
		}

		TransacaoVenda transVenda = (TransacaoVenda) gerenciadorPerifericos.getCmos().ler(CMOS.TRANSACAO_VENDA_ATUAL);
		EventoTransacaoPK pk = new EventoTransacaoPK(transVenda.getPk().getLoja(),transVenda.getPk().getComponente(),transVenda.getPk().getNumeroTransacao(),transVenda.getPk().getDataTransacao());

		BigDecimal valorAcrescimo = null;
		BigDecimal valorDesconto = null;

		if (plano.getPercAcrescimo() != null && plano.getPercAcrescimo().compareTo(new BigDecimal(0)) == 0 ){
			BigDecimal fator = plano.getPercAcrescimo().divide(new BigDecimal(100), BigDecimal.ROUND_UNNECESSARY);
			valorAcrescimo = valorPagamento.multiply(fator);
		}
		if (plano.getPercDesconto() != null && plano.getPercDesconto().compareTo(new BigDecimal(0)) == 0 ){
			BigDecimal fator = plano.getPercAcrescimo().divide(new BigDecimal(100), BigDecimal.ROUND_UNNECESSARY);
			valorDesconto = valorPagamento.multiply(fator);
		}

		if (valorAcrescimo !=  null){
			valorPagamento = valorPagamento.add(valorAcrescimo);
		}
		if (valorDesconto !=  null){
			valorPagamento = valorPagamento.subtract(valorDesconto);
		}


		Date dataAtual = new Date();
		
		EventoItemPagamento eventoItemPagamento = null;
		
		Collection eventos = transVenda.getEventosTransacao();

		if (eventos == null){
			eventos = new ConjuntoEventoTransacao();
			transVenda.setEventosTransacao(eventos);
		}
		
		if (plano.getForma().getId().equals(ConstantesFormaRecebimento.CHEQUE)){
			DadosCheque dados = (DadosCheque) gerenciadorPerifericos.getCmos().ler(CMOS.DADOS_CHEQUE);
			eventoItemPagamento = new EventoItemPagamentoCheque(pk,ConstantesEventoTransacao.EVENTO_ITEM_PAGAMENTO,dataAtual,plano.getForma().getId().intValue(),plano.getId().intValue(),plano.getForma().getRecebimentoImpressora(),valorPagamento,valorDesconto,valorAcrescimo,
					dados.getCPFCNPJ(), dados.getNumeroChequeLido(), dados.getBanco(), dados.getAgencia(), dados.getConta(),dados.getNumeroCheque());
			eventos.add(eventoItemPagamento);
			gerenciadorPerifericos.getCmos().gravar(CMOS.ITEM_PAGAMENTO, eventoItemPagamento);
		}else if (plano.getForma().getId().equals(ConstantesFormaRecebimento.CHEQUE_PRE)){
			Collection dadosParcelas = (Collection) gerenciadorPerifericos.getCmos().ler(CMOS.DADOS_CHEQUE_PRE);
			EventoItemPagamentoChequePredatado eventoItemPagamentoChequePredatado = new EventoItemPagamentoChequePredatado(pk,ConstantesEventoTransacao.EVENTO_ITEM_PAGAMENTO,dataAtual,plano.getForma().getId().intValue(),plano.getId().intValue(),plano.getForma().getRecebimentoImpressora(),valorPagamento,valorDesconto,valorAcrescimo,null);
			eventos.add(eventoItemPagamentoChequePredatado);
			Collection parcelas = dadosToParcelas(dadosParcelas,eventoItemPagamentoChequePredatado.getPk());
			eventoItemPagamentoChequePredatado.setParcelas(parcelas);
			gerenciadorPerifericos.getCmos().gravar(CMOS.ITEM_PAGAMENTO, eventoItemPagamentoChequePredatado);
		}else if (plano.getForma().getId().equals(ConstantesFormaRecebimento.CARTAO_OFF)){
			DadosCartaoOff dados = (DadosCartaoOff) gerenciadorPerifericos.getCmos().ler(CMOS.DADOS_CARTAO_OFF);
			eventoItemPagamento = new EventoItemPagamentoCartaoOff(pk,ConstantesEventoTransacao.EVENTO_ITEM_PAGAMENTO,dataAtual,plano.getForma().getId().intValue(),plano.getId().intValue(),plano.getForma().getRecebimentoImpressora(),valorPagamento,valorDesconto,valorAcrescimo,
					dados.getNumeroCartao(), dados.getQuantidadeParcelas(), dados.getAutorizacao(), dados.getCodigoAutorizadora());
			eventos.add(eventoItemPagamento);
			gerenciadorPerifericos.getCmos().gravar(CMOS.ITEM_PAGAMENTO, eventoItemPagamento);
		}else if (plano.getForma().getId().equals(ConstantesFormaRecebimento.CARTAO_PROPRIO)){
			DadosCartaoProprio dados = (DadosCartaoProprio) gerenciadorPerifericos.getCmos().ler(CMOS.DADOS_CARTAO_PROPRIO);
			eventoItemPagamento = new EventoItemPagamentoCartaoProprio(pk,ConstantesEventoTransacao.EVENTO_ITEM_PAGAMENTO,dataAtual,plano.getForma().getId().intValue(),plano.getId().intValue(),plano.getForma().getRecebimentoImpressora(),valorPagamento,valorDesconto,valorAcrescimo,
					dados.getAutorizacao());
			eventos.add(eventoItemPagamento);
			gerenciadorPerifericos.getCmos().gravar(CMOS.ITEM_PAGAMENTO, eventoItemPagamento);
		}else{
			eventoItemPagamento = new EventoItemPagamento(pk,ConstantesEventoTransacao.EVENTO_ITEM_PAGAMENTO,dataAtual,plano.getForma().getId().intValue(),plano.getId().intValue(),plano.getForma().getRecebimentoImpressora(),valorPagamento,valorDesconto,valorAcrescimo);
			eventos.add(eventoItemPagamento);
			gerenciadorPerifericos.getCmos().gravar(CMOS.ITEM_PAGAMENTO, eventoItemPagamento);
		}
		
		gerenciadorPerifericos.getCmos().gravar(CMOS.TRANSACAO_VENDA_ATUAL, transVenda);

		return ALTERNATIVA_1;
	}
	
	private Collection dadosToParcelas(Collection dados, EventoTransacaoPK eventoPK){
		Collection retorno = new HashSet();
		Iterator i = dados.iterator();
		for(int cont = 1;i.hasNext();cont++){
			DadosChequePredatado dado = (DadosChequePredatado) i.next();
			ParcelaEventoItemPagamentoChequePredatadoPK pk = new ParcelaEventoItemPagamentoChequePredatadoPK(eventoPK.getLoja(),
					eventoPK.getComponente(),eventoPK.getNumeroTransacao(),eventoPK.getDataTransacao(), eventoPK.getNumeroEvento(),cont);
			ParcelaEventoItemPagamentoChequePredatado parcela = new ParcelaEventoItemPagamentoChequePredatado();
			parcela.setPk(pk);
			parcela.setAgencia(dado.getAgencia());
			parcela.setBanco(dado.getBanco());
			parcela.setConta(dado.getConta());
			parcela.setCPFCNPJ(dado.getCPFCNPJ());
			parcela.setData(dado.getData());
			parcela.setEntrada(dado.getEntrada());
			parcela.setNumeroCheque(dado.getNumeroCheque());
			parcela.setNumeroChequeLido(dado.getNumeroChequeLido());
			parcela.setValor(dado.getValor());
			retorno.add(parcela);
		}
		return retorno;
	}

}
