package com.infinity.datamarket.pdv.op;


import java.math.BigDecimal;
import java.util.Iterator;

import com.infinity.datamarket.comum.pagamento.FormaRecebimento;
import com.infinity.datamarket.comum.transacao.EventoItemPagamento;
import com.infinity.datamarket.comum.transacao.EventoItemRegistrado;
import com.infinity.datamarket.comum.transacao.EventoTransacao;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.gui.telas.TelaTotal;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpExibeTelaTotal extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		
		TransacaoVenda transVenda = (TransacaoVenda) gerenciadorPerifericos.getCmos().ler(CMOS.TRANSACAO_VENDA_ATUAL);
		
		if (transVenda.getEventosTransacao() == null || transVenda.getEventosTransacao().size()  == 0){
			return ALTERNATIVA_2;
		}
		
		TelaTotal tela = (TelaTotal) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_TOTAL);

		tela.inicioTextoCupom();
		tela.inicioTextoRecebido();

		BigDecimal subTotal = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.SUB_TOTAL);

		
		BigDecimal valTotal = new BigDecimal(0);
		if (transVenda != null && transVenda.getEventosTransacao() != null){
			Iterator i = transVenda.getEventosTransacao().iterator();
			while(i.hasNext()){
				EventoTransacao ev = (EventoTransacao) i.next();
				if (ev instanceof EventoItemRegistrado){
					EventoItemRegistrado eir = (EventoItemRegistrado) ev;
					if (eir.getSituacao().equals(EventoItemRegistrado.ATIVO)){						
						valTotal = valTotal.add(eir.getPreco());
					}	
					BigDecimal precoUnitario = eir.getPreco().divide(eir.getQuantidade(), BigDecimal.ROUND_UNNECESSARY);
					tela.addItem(eir.getPk().getNumeroEvento(), eir.getProdutoItemRegistrado().getDescricaoCompleta(), eir.getQuantidade(), precoUnitario, eir.getDesconto(), eir.getSituacao());
				}
			}
			
			tela.setTotal(valTotal);
			tela.setAReceber(subTotal);

			if (transVenda.getDescontoCupom() != null && transVenda.getDescontoCupom().compareTo(new BigDecimal(0)) > 0){
				valTotal = valTotal.subtract(transVenda.getDescontoCupom());
				tela.addDesconto(transVenda.getDescontoCupom(), valTotal);
			}
			
			tela.setTotalTela(valTotal);
			
			
			i = transVenda.getEventosTransacao().iterator();
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