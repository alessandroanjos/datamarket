package com.infinity.datamarket.pdv.mic;

import java.math.BigDecimal;
import java.util.Iterator;

import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.produto.TipoProduto;
import com.infinity.datamarket.comum.transacao.EventoItemPagamento;
import com.infinity.datamarket.comum.transacao.EventoItemRegistrado;
import com.infinity.datamarket.comum.transacao.EventoTransacao;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.ImpressoraFiscal;
import com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.ImpressoraFiscalException;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;

public class MicFinalizaCupom extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		TransacaoVenda transVenda = (TransacaoVenda) gerenciadorPerifericos.getCmos().ler(CMOS.TRANSACAO_VENDA_ATUAL);
		try{
			gerenciadorPerifericos.getImpressoraFiscal().iniciaFechamentoCupom(null, null, null);
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
			
		}catch(ImpressoraFiscalException e){
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
