package com.infinity.datamarket.pdv.mic;


import java.math.BigDecimal;
import java.util.Iterator;

import com.infinity.datamarket.comum.transacao.EventoItemRegistrado;
import com.infinity.datamarket.comum.transacao.EventoTransacao;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.gui.telas.TelaVenda;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class MicExibeTelaCancela extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		EventoItemRegistrado item = (EventoItemRegistrado) gerenciadorPerifericos.getCmos().ler(CMOS.ITEM_CANCELADO);

		TelaVenda tela = (TelaVenda) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_VENDA);

		tela.inicioTextoCupom();

		TransacaoVenda transVenda = (TransacaoVenda) gerenciadorPerifericos.getCmos().ler(CMOS.TRANSACAO_VENDA_ATUAL);
		BigDecimal valTotal = new BigDecimal(0);
		if (transVenda != null && transVenda.getEventosTransacao() != null){
			Iterator i = transVenda.getEventosTransacao().iterator();
			while(i.hasNext()){
				EventoTransacao ev = (EventoTransacao) i.next();
				if (ev instanceof EventoItemRegistrado){
					EventoItemRegistrado eir = (EventoItemRegistrado) ev;
					BigDecimal precoUnitario = eir.getPreco().divide(eir.getQuantidade(), BigDecimal.ROUND_UNNECESSARY);
					BigDecimal descontoUnitario = eir.getDesconto().divide(eir.getQuantidade(), BigDecimal.ROUND_UNNECESSARY);
					if (item.getPk().equals(eir.getPk())){
						tela.setDescricao("Canc "+ eir.getProdutoItemRegistrado().getDescricaoCompleta());
						tela.setQuantidade(eir.getQuantidade());
						tela.setValorUnitario(precoUnitario.add(descontoUnitario));
						tela.setDesconto(eir.getDesconto());
						tela.setValorTotal(eir.getPreco());
					}
					if (eir.getSituacao().equals(EventoItemRegistrado.ATIVO)){
						valTotal = valTotal.add(eir.getPreco());
					}
					tela.addItem(eir.getPk().getNumeroEvento(), eir.getProdutoItemRegistrado().getDescricaoCompleta(), eir.getQuantidade(), precoUnitario, eir.getDesconto(),eir.getSituacao());
				}
			}
		}
		tela.setValorSubTotal(valTotal);
		gerenciadorPerifericos.atualizaTela(tela);
		gerenciadorPerifericos.getCmos().gravar(CMOS.TOTAL, valTotal);
		gerenciadorPerifericos.getCmos().gravar(CMOS.SUB_TOTAL, valTotal);

		return ALTERNATIVA_1;


	}
}