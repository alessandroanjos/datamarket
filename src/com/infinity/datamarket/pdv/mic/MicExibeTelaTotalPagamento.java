package com.infinity.datamarket.pdv.mic;


import java.math.BigDecimal;
import java.util.Iterator;

import com.infinity.datamarket.comum.pagamento.FormaRecebimento;
import com.infinity.datamarket.comum.transacao.EventoItemPagamento;
import com.infinity.datamarket.comum.transacao.EventoItemRegistrado;
import com.infinity.datamarket.comum.transacao.EventoTransacao;
import com.infinity.datamarket.comum.transacao.TransacaoPagamento;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.gui.telas.TelaTotal;
import com.infinity.datamarket.pdv.gui.telas.TelaTotalPagamento;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class MicExibeTelaTotalPagamento extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		
		TransacaoPagamento transPagamento = (TransacaoPagamento) gerenciadorPerifericos.getCmos().ler(CMOS.TRANSACAO_PAGAMENTO_ATUAL);
		
		TelaTotalPagamento tela = (TelaTotalPagamento) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_TOTAL_PAGAMENTO);

		tela.inicioTextoRecebido();

		BigDecimal subTotal = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.SUB_TOTAL);
		
		BigDecimal total = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.TOTAL);
		
		tela.setTotalTela(total);
		tela.setAReceber(subTotal);
		
		if (transPagamento != null && transPagamento.getEventosTransacao() != null){
			Iterator i = transPagamento.getEventosTransacao().iterator();
			while(i.hasNext()){
				EventoTransacao ev = (EventoTransacao) i.next();
				if (ev instanceof EventoItemPagamento){
					EventoItemPagamento eip = (EventoItemPagamento) ev;
	
					BigDecimal valorPagamento = eip.getValorBruto();
					if (eip.getValorAcrescimo() != null){
						valorPagamento = valorPagamento.add(eip.getValorAcrescimo());
					}
					if (eip.getValorDesconto() != null){
						valorPagamento = valorPagamento.subtract(eip.getValorDesconto());
					}
					try{
						FormaRecebimento forma = getFachadaPDV().consultarFormaRecebimentoPorId(new Long(eip.getCodigoForma()));
						tela.addRecebimento(forma.getDescricao(), valorPagamento);
					}catch(AppException e){
					}
				}
			}

		}

		gerenciadorPerifericos.atualizaTela(tela);

		return ALTERNATIVA_1;
	}
}