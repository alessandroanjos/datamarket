package com.infinity.datamarket.pdv.op;


import java.math.BigDecimal;
import java.util.Iterator;

import com.infinity.datamarket.comum.pagamento.FormaRecebimento;
import com.infinity.datamarket.comum.transacao.EventoItemPagamento;
import com.infinity.datamarket.comum.transacao.EventoTransacao;
import com.infinity.datamarket.comum.transacao.TransacaoPagamento;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.gui.telas.TelaTrocoPagamento;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpExibeTelaTrocoPagamento extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		TelaTrocoPagamento tela = (TelaTrocoPagamento) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_TROCO_PAGAMENTO);

		tela.inicioTextoRecebido();

		TransacaoPagamento transPagamento = (TransacaoPagamento) gerenciadorPerifericos.getCmos().ler(CMOS.TRANSACAO_PAGAMENTO_ATUAL);
		BigDecimal valTotal = new BigDecimal(0);
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

		BigDecimal troco = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.VALOR_TROCO_ATUAL);
		tela.setTroco(troco);
		gerenciadorPerifericos.atualizaTela(tela);
		return ALTERNATIVA_1;
	}
}