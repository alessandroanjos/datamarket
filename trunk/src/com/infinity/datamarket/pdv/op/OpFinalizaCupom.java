package com.infinity.datamarket.pdv.op;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.transacao.EventoItemPagamento;
import com.infinity.datamarket.comum.transacao.EventoTransacao;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.ImpressoraFiscalException;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpFinalizaCupom extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		gerenciadorPerifericos.getDisplay().setMensagem("Aguarde...");
		TransacaoVenda transVenda = (TransacaoVenda) gerenciadorPerifericos.getCmos().ler(CMOS.TRANSACAO_VENDA_ATUAL);
		try{
			gerenciadorPerifericos.getImpressoraFiscal().iniciaFechamentoCupom(transVenda.getDescontoCupom(), null);
			if (transVenda != null && transVenda.getEventosTransacao() != null){
				Iterator i = transVenda.getEventosTransacao().iterator();
				while(i.hasNext()){
					EventoTransacao ev = (EventoTransacao) i.next();
					if (ev instanceof EventoItemPagamento){
						EventoItemPagamento eip = (EventoItemPagamento) ev;
						gerenciadorPerifericos.getImpressoraFiscal().efetuaPagamento(eip.getValorBruto().subtract(eip.getValorDesconto()),eip.getFormaImpressora());
					}
				}
			}
			gerenciadorPerifericos.getImpressoraFiscal().terminaFechaCupom("Obrigado. Volte Sempre!");
			gerenciadorPerifericos.getCmos().gravar(CMOS.SUB_TOTAL,BigDecimal.ZERO);
		}catch(ImpressoraFiscalException e){
			
			EventoItemPagamento itemPagamento = (EventoItemPagamento) gerenciadorPerifericos.getCmos().ler(CMOS.ITEM_PAGAMENTO);
			Collection c = transVenda.getEventosTransacao();
			c.remove(itemPagamento);
			
			gerenciadorPerifericos.getCmos().gravar(CMOS.TRANSACAO_VENDA_ATUAL,transVenda);
			gerenciadorPerifericos.getDisplay().setMensagem(e.getMessage());
			try{
				gerenciadorPerifericos.esperaVolta();
			}catch(Exception ex){
				return ALTERNATIVA_2;
			}
			return ALTERNATIVA_2;
		}
		return ALTERNATIVA_1;
	}

}
