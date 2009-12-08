package com.infinity.datamarket.pdv.op;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import com.infinity.datamarket.comum.boleto.Boleto;
import com.infinity.datamarket.comum.transacao.BoletoEventoItemPagamentoBoleto;
import com.infinity.datamarket.comum.transacao.ClienteTransacao;
import com.infinity.datamarket.comum.transacao.EventoItemPagamentoBoleto;
import com.infinity.datamarket.comum.transacao.EventoTransacao;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Util;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.ImpressoraFiscalException;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpIncluiTransacaoVenda extends Mic{



	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		TransacaoVenda trans = (TransacaoVenda) gerenciadorPerifericos.getCmos().ler(CMOS.TRANSACAO_VENDA_ATUAL);

		BigDecimal troco = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.VALOR_TROCO_ATUAL);
		BigDecimal total = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.TOTAL);

		trans.setDataHoraFim(new Date());
		trans.setValorTroco(troco);
		String cupom = "1";
		try {
			cupom = gerenciadorPerifericos.getImpressoraFiscal().getNumeroCupom()+"";
		} catch (ImpressoraFiscalException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		trans.setNumeroCupom(cupom);
		trans.setValorCupom(total);
		try{
				
			if (trans.getCliente() != null) {
				ClienteTransacao cliente = trans.getCliente();
				Collection col = trans.getEventosTransacao();
				if (col != null && col.size() > 0){
					Iterator i = col.iterator();
					while(i.hasNext()){
						EventoTransacao evt = (EventoTransacao) i.next();
						if (evt instanceof EventoItemPagamentoBoleto){
							EventoItemPagamentoBoleto ev = (EventoItemPagamentoBoleto) evt;
							if (ev.getBoletos() != null) {
								Collection boletos = ev.getBoletos();
								Iterator it = boletos.iterator();
								while (it.hasNext() ) {
									BoletoEventoItemPagamentoBoleto boletoEIPB = (BoletoEventoItemPagamentoBoleto)it.next();
									Boleto boleto = (Boleto) boletoEIPB.getPk().getBoleto();
	
									if (boleto != null) {
										
										boleto.setNomeCliente(cliente.getNomeCliente());
										boleto.setEnderecoCliente(cliente.getLogradouro() + (cliente.getNumero() != null ? ", N� " + cliente.getNumero(): ", S/N"));
										boleto.setBairroCliente(cliente.getBairro());
										boleto.setCidadeCliente(cliente.getCidade());
										boleto.setUFCliente(cliente.getEstado());
										boleto.setCpfCnpj(Util.formataCpfCnpj(cliente.getCpfCnpj()));
										boleto.setCepCliente(Util.formataCEP(cliente.getCep()));
									}
								}
							}	
						}
					}
				}	
			}
		
			getFachadaPDV().inserirTransacao(trans);
			gerenciadorPerifericos.getCmos().gravar(CMOS.CHAVE_ULTIMA_TRANSACAO, trans.getPk());
		}catch(AppException e){
			e.printStackTrace();
		}

		return ALTERNATIVA_1;
	}
}
