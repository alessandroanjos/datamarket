package com.infinity.datamarket.pdv.op;


import java.math.BigDecimal;
import java.util.Iterator;

import com.infinity.datamarket.comum.transacao.EventoItemPagamento;
import com.infinity.datamarket.comum.transacao.EventoItemRegistrado;
import com.infinity.datamarket.comum.transacao.EventoTransacao;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpVoltaRecebimento extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		TransacaoVenda transVenda = (TransacaoVenda) gerenciadorPerifericos.getCmos().ler(CMOS.TRANSACAO_VENDA_ATUAL);
		boolean existeRecebimento =  false;
		if (transVenda != null && transVenda.getEventosTransacao() != null){
			Iterator i = transVenda.getEventosTransacao().iterator();
			i = transVenda.getEventosTransacao().iterator();
			BigDecimal valTotal = new BigDecimal(0);
			while(i.hasNext()){
				EventoTransacao ev = (EventoTransacao) i.next();
				if (ev instanceof EventoItemRegistrado){
					EventoItemRegistrado eir = (EventoItemRegistrado) ev;
					if (eir.getSituacao().equals(EventoItemRegistrado.ATIVO)){						
						valTotal = valTotal.add(eir.getPreco());
					}									
				}
				if (ev instanceof EventoItemPagamento){
					existeRecebimento = true;
					i.remove();
				}
			}
			
			
			if (transVenda.getDescontoCupom() != null && transVenda.getDescontoCupom().compareTo(new BigDecimal(0)) > 0){
				valTotal = valTotal.subtract(transVenda.getDescontoCupom());
			}

			gerenciadorPerifericos.getCmos().gravar(CMOS.SUB_TOTAL, valTotal);
			
		}
		
		
		if (!existeRecebimento){
			transVenda.setDescontoCupom(null);
			return ALTERNATIVA_2;
		}
		return ALTERNATIVA_1;
	}
}