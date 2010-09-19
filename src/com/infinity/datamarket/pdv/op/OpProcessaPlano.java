package com.infinity.datamarket.pdv.op;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import com.infinity.datamarket.comum.boleto.Boleto;
import com.infinity.datamarket.comum.operacao.OperacaoDevolucao;
import com.infinity.datamarket.comum.pagamento.ConstantesFormaRecebimento;
import com.infinity.datamarket.comum.pagamento.DadosCartaoOff;
import com.infinity.datamarket.comum.pagamento.DadosCartaoProprio;
import com.infinity.datamarket.comum.pagamento.DadosCheque;
import com.infinity.datamarket.comum.pagamento.DadosChequePredatado;
import com.infinity.datamarket.comum.pagamento.PlanoPagamento;
import com.infinity.datamarket.comum.transacao.BoletoEventoItemPagamentoBoleto;
import com.infinity.datamarket.comum.transacao.BoletoEventoItemPagamentoBoletoPK;
import com.infinity.datamarket.comum.transacao.ConstantesEventoTransacao;
import com.infinity.datamarket.comum.transacao.ConstantesTransacao;
import com.infinity.datamarket.comum.transacao.EventoItemPagamento;
import com.infinity.datamarket.comum.transacao.EventoItemPagamentoBoleto;
import com.infinity.datamarket.comum.transacao.EventoItemPagamentoCartao;
import com.infinity.datamarket.comum.transacao.EventoItemPagamentoCartaoOff;
import com.infinity.datamarket.comum.transacao.EventoItemPagamentoCartaoProprio;
import com.infinity.datamarket.comum.transacao.EventoItemPagamentoCheque;
import com.infinity.datamarket.comum.transacao.EventoItemPagamentoChequePredatado;
import com.infinity.datamarket.comum.transacao.EventoItemPagamentoTroca;
import com.infinity.datamarket.comum.transacao.EventoTransacaoPK;
import com.infinity.datamarket.comum.transacao.ParcelaEventoItemPagamentoChequePredatado;
import com.infinity.datamarket.comum.transacao.ParcelaEventoItemPagamentoChequePredatadoPK;
import com.infinity.datamarket.comum.transacao.Transacao;
import com.infinity.datamarket.comum.transacao.TransacaoPagamento;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.comum.util.ConjuntoEventoTransacao;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.tef.RespostaOperacaoTEF;

public class OpProcessaPlano extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		BigDecimal valorPagamento = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.VALOR_PAGAMENTO_ATUAL);
		PlanoPagamento plano = (PlanoPagamento) gerenciadorPerifericos.getCmos().ler(CMOS.PLANO_PAGAMENTO_ATUAL);
		BigDecimal subTotal = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.SUB_TOTAL);

		if (valorPagamento.compareTo(subTotal) > 0){
			valorPagamento = subTotal;
		}
		Transacao transacao = null;
		Integer tipoTransacao = (Integer) gerenciadorPerifericos.getCmos().ler(CMOS.TIPO_TRANSACAO);
		if (tipoTransacao.intValue() == ConstantesTransacao.TRANSACAO_VENDA){
			transacao = (Transacao) gerenciadorPerifericos.getCmos().ler(CMOS.TRANSACAO_VENDA_ATUAL);
		}else {
			transacao = (Transacao) gerenciadorPerifericos.getCmos().ler(CMOS.TRANSACAO_PAGAMENTO_ATUAL);
		}

		EventoTransacaoPK pk = new EventoTransacaoPK(transacao.getPk().getLoja(),transacao.getPk().getComponente(),transacao.getPk().getNumeroTransacao(),transacao.getPk().getDataTransacao());

		BigDecimal valorAcrescimo = BigDecimal.ZERO;
		BigDecimal valorDesconto = BigDecimal.ZERO
		;

		if (plano != null && plano.getPercAcrescimo() != null && plano.getPercAcrescimo().compareTo(new BigDecimal(0)) == 0 ){
			BigDecimal fator = plano.getPercAcrescimo().divide(new BigDecimal(100), BigDecimal.ROUND_DOWN);
			valorAcrescimo = valorPagamento.multiply(fator);
		}
		if (plano != null && plano.getPercDesconto() != null && plano.getPercDesconto().compareTo(new BigDecimal(0)) == 0 ){
			BigDecimal fator = plano.getPercAcrescimo().divide(new BigDecimal(100), BigDecimal.ROUND_DOWN);
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

		Collection eventos = null;
		
		if (transacao instanceof TransacaoPagamento){
			eventos = ((TransacaoPagamento)transacao).getEventosTransacao();
		}else{
			eventos = ((TransacaoVenda)transacao).getEventosTransacao();
		}
	

		if (eventos == null){
			eventos = new ConjuntoEventoTransacao();
			if (transacao instanceof TransacaoPagamento){
				((TransacaoPagamento)transacao).setEventosTransacao(eventos);
			}else{
				((TransacaoVenda)transacao).setEventosTransacao(eventos);
			}
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
			eventoItemPagamento = eventoItemPagamentoChequePredatado;
			gerenciadorPerifericos.getCmos().gravar(CMOS.ITEM_PAGAMENTO, eventoItemPagamentoChequePredatado);
		}else if (plano.getForma().getId().equals(ConstantesFormaRecebimento.BOLETO)){
			Collection boletos = (Collection) gerenciadorPerifericos.getCmos().ler(CMOS.BOLETO);
			EventoItemPagamentoBoleto eventoItemPagamentoBoleto = new EventoItemPagamentoBoleto(pk,ConstantesEventoTransacao.EVENTO_ITEM_PAGAMENTO,dataAtual,plano.getForma().getId().intValue(),plano.getId().intValue(),plano.getForma().getRecebimentoImpressora(),valorPagamento,valorDesconto,valorAcrescimo);
			eventos.add(eventoItemPagamentoBoleto);
//			SortedSet boletosEIPB = new TreeSet();
			Collection boletosEIPB = new HashSet();
			if (boletos != null) {
				Iterator it = boletos.iterator();
				while (it.hasNext()) {
					Boleto boleto = (Boleto)it.next();
					
					BoletoEventoItemPagamentoBoletoPK bkEIPBPK = new  BoletoEventoItemPagamentoBoletoPK();
					bkEIPBPK.setBoleto(boleto);
					bkEIPBPK.setComponente(eventoItemPagamentoBoleto.getPk().getComponente());
					bkEIPBPK.setLoja(eventoItemPagamentoBoleto.getPk().getLoja());
					bkEIPBPK.setDataTransacao(eventoItemPagamentoBoleto.getPk().getDataTransacao());
					bkEIPBPK.setNumeroEvento(eventoItemPagamentoBoleto.getPk().getNumeroEvento());
					bkEIPBPK.setNumeroTransacao(eventoItemPagamentoBoleto.getPk().getNumeroTransacao());

					BoletoEventoItemPagamentoBoleto bEIPB = new  BoletoEventoItemPagamentoBoleto();
					bEIPB.setPk(bkEIPBPK);
					
					boletosEIPB.add(bEIPB);
				}
			}

			eventoItemPagamentoBoleto.setBoletos(boletosEIPB);
			eventoItemPagamento = eventoItemPagamentoBoleto;
			gerenciadorPerifericos.getCmos().gravar(CMOS.ITEM_PAGAMENTO, eventoItemPagamentoBoleto);
			//apaga o boleto
			gerenciadorPerifericos.getCmos().gravar(CMOS.BOLETO, null);
		}else if (plano.getForma().getId().equals(ConstantesFormaRecebimento.CARTAO_OFF)){
			DadosCartaoOff dados = (DadosCartaoOff) gerenciadorPerifericos.getCmos().ler(CMOS.DADOS_CARTAO_OFF);
			eventoItemPagamento = new EventoItemPagamentoCartaoOff(pk,ConstantesEventoTransacao.EVENTO_ITEM_PAGAMENTO,dataAtual,plano.getForma().getId().intValue(),plano.getId().intValue(),plano.getForma().getRecebimentoImpressora(),valorPagamento,valorDesconto,valorAcrescimo,
					dados.getNumeroCartao(), dados.getQuantidadeParcelas(), dados.getAutorizacao(), dados.getCodigoAutorizadora());
			eventos.add(eventoItemPagamento);
			gerenciadorPerifericos.getCmos().gravar(CMOS.ITEM_PAGAMENTO, eventoItemPagamento);
		}else if (plano.getForma().getId().equals(ConstantesFormaRecebimento.CARTAO)){
			RespostaOperacaoTEF respostaTEF = (RespostaOperacaoTEF)gerenciadorPerifericos.getCmos().ler("respostaSolicitacao");

			long identificacao = respostaTEF.getIdentificacao();
			long coo = respostaTEF.getNumeroCOO();
			BigDecimal valor = respostaTEF.getValorOperacao();
			byte moeda = respostaTEF.getMoeda();
			String status = respostaTEF.getStatusTransacao();
			String nomeRede = respostaTEF.getNomeRede();
			Integer tipoTransacaoCartao = respostaTEF.getTipoTransacao();
			long numeroTranscaoNSUTEF = respostaTEF.getNsuTEF();
			Integer codigoAutorizacao = respostaTEF.getCodigoAutorizacao();
			long numeroLoteTransacao = respostaTEF.getNumeroLoteTransacao();
			Date dataHoraTranscaoHost = respostaTEF.getDataHoraTransacaoHost();
			Date dataHoraTranscaoLocal = respostaTEF.getDataHoraTransacaoLocal();
			byte tipoParcelamento = respostaTEF.getTipoParcelamento();
			Integer quantidadeParcelas = respostaTEF.getQuantidadeParcelas() ;
			Collection conjuntoParcelas = respostaTEF.getConjuntoParcelas();
			//ParcelaTEF parcela = null;
			Date dataHoraTransacao = respostaTEF.getDataHoraTransacao() ;
			Date dataCartaoPreDatado = respostaTEF.getDataCartaoPreDatado();
			String chaveFinalizacao = respostaTEF.getChaveFinalizacao();
			String[] linhas = respostaTEF.getLinhasComprovantePrincipal();
			String textoEspecialOperador = respostaTEF.getTextoEspecialOperador();
			String textoEspecialCliente = respostaTEF.getTextoEspecialCliente();
			String nomeAdministrador = respostaTEF.getNomeAutorizadora();

			EventoItemPagamentoCartao eventoItemPagamentoCartao = new EventoItemPagamentoCartao
			(pk,
				ConstantesEventoTransacao.EVENTO_ITEM_PAGAMENTO,
				dataAtual,
				plano.getForma().getId().intValue(),
				plano.getId().intValue(),
				plano.getForma().getRecebimentoImpressora(),
				valorPagamento,
				valorDesconto,
				valorAcrescimo);

			eventoItemPagamentoCartao.setIdentificacao(identificacao) ;
			eventoItemPagamentoCartao.setCoo(coo);
			eventoItemPagamentoCartao.setValor(valor);
			if (status != null) 
				eventoItemPagamentoCartao.setStatus(new Short(status));
			eventoItemPagamentoCartao.setRede(nomeRede);
			if (tipoTransacaoCartao != null) 
				eventoItemPagamentoCartao.setTipoTransacao(new Short(tipoTransacaoCartao.shortValue()));

			eventoItemPagamentoCartao.setNumeroTransacaoNSU(numeroTranscaoNSUTEF);
			if (codigoAutorizacao != null) 
				eventoItemPagamentoCartao.setCodigoAutorizacao(new Long(codigoAutorizacao));
			eventoItemPagamentoCartao.setNumeroLoteTransacao(numeroLoteTransacao);
			eventoItemPagamentoCartao.setDataHoraTransacaoHost(dataHoraTranscaoHost);
			eventoItemPagamentoCartao.setDataHoraTransacaoLocal(dataHoraTranscaoLocal);
			eventoItemPagamentoCartao.setTipoParcelamento(new Short(tipoParcelamento));
			eventoItemPagamentoCartao.setQuantidadeParcelamento(quantidadeParcelas);
			eventoItemPagamentoCartao.setDataHoraTransacao(dataHoraTransacao);
			eventoItemPagamentoCartao.setDataCartaoPreDatado(dataCartaoPreDatado);
			eventoItemPagamentoCartao.setChaveFinalizacao(chaveFinalizacao);
			eventoItemPagamentoCartao.setNomeAdministrador(nomeAdministrador);


			eventos.add(eventoItemPagamento);
			gerenciadorPerifericos.getCmos().gravar(CMOS.ITEM_PAGAMENTO, eventoItemPagamento);
		}else if (plano.getForma().getId().equals(ConstantesFormaRecebimento.CARTAO_PROPRIO)){
			DadosCartaoProprio dados = (DadosCartaoProprio) gerenciadorPerifericos.getCmos().ler(CMOS.DADOS_CARTAO_PROPRIO);
			eventoItemPagamento = new EventoItemPagamentoCartaoProprio(pk,ConstantesEventoTransacao.EVENTO_ITEM_PAGAMENTO,dataAtual,plano.getForma().getId().intValue(),plano.getId().intValue(),plano.getForma().getRecebimentoImpressora(),valorPagamento,valorDesconto,valorAcrescimo,
					dados.getAutorizacao(),dados.getCPFCNPJ());
			eventos.add(eventoItemPagamento);
			gerenciadorPerifericos.getCmos().gravar(CMOS.ITEM_PAGAMENTO, eventoItemPagamento);
		}else if (plano.getForma().getId().equals(ConstantesFormaRecebimento.TROCA)){
			OperacaoDevolucao operacao = (OperacaoDevolucao) gerenciadorPerifericos.getCmos().ler(CMOS.OPERACAO_DEVOLUCAO);
			eventoItemPagamento = new EventoItemPagamentoTroca(pk,ConstantesEventoTransacao.EVENTO_ITEM_PAGAMENTO,dataAtual,plano.getForma().getId().intValue(),plano.getId().intValue(),plano.getForma().getRecebimentoImpressora(),valorPagamento,valorDesconto,valorAcrescimo,
					operacao.getPk().getId()+"");
			eventos.add(eventoItemPagamento);
			gerenciadorPerifericos.getCmos().gravar(CMOS.ITEM_PAGAMENTO, eventoItemPagamento);
		}else{
			eventoItemPagamento = new EventoItemPagamento(pk,ConstantesEventoTransacao.EVENTO_ITEM_PAGAMENTO,dataAtual,plano.getForma().getId().intValue(),plano.getId().intValue(),plano.getForma().getRecebimentoImpressora(),valorPagamento,valorDesconto,valorAcrescimo);
			eventos.add(eventoItemPagamento);
			
			gerenciadorPerifericos.getCmos().gravar(CMOS.ITEM_PAGAMENTO, eventoItemPagamento);
		}
		eventoItemPagamento.setDescricaoForma(plano.getForma().getDescricao()); 
		eventoItemPagamento.setDescricaoPlano(plano.getDescricao());
		
		if (transacao instanceof TransacaoPagamento){
			gerenciadorPerifericos.getCmos().gravar(CMOS.TRANSACAO_PAGAMENTO_ATUAL, transacao);
		}else{
			gerenciadorPerifericos.getCmos().gravar(CMOS.TRANSACAO_VENDA_ATUAL, transacao);
		}		

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
