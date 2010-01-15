package com.infinity.datamarket.av.op;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.infinity.datamarket.comum.operacao.EventoOperacaoItemRegistrado;
import com.infinity.datamarket.comum.operacao.EventoOperacaoItemRegistradoSeparacao;
import com.infinity.datamarket.comum.operacao.OperacaoPedido;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpAvRecalcularPedidoSeparados extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		OperacaoPedido pedido = (OperacaoPedido)gerenciadorPerifericos.getCmos().ler(CMOS.OPERACAO_PEDIDO);

		BigDecimal valorTotal = BigDecimal.ZERO;
		BigDecimal valorTotalDesconto = BigDecimal.ZERO;

		Collection collItensPedidoSeparados = (List<EventoOperacaoItemRegistrado>)gerenciadorPerifericos.getCmos().ler(CMOS.COLL_EVENTO_OPERACAO_ITEM_REGISTRADO_PEDIDO);
		if (collItensPedidoSeparados != null && !collItensPedidoSeparados.isEmpty()) {
			Iterator it = collItensPedidoSeparados.iterator();
			while(it.hasNext()) {
				EventoOperacaoItemRegistrado evento = null;
				Object obj = (Object) it.next();
				if (obj instanceof EventoOperacaoItemRegistradoSeparacao) {
					evento = ((EventoOperacaoItemRegistradoSeparacao)obj).getEventoOperacaoItemRegistrado();
				} else {
					evento = (EventoOperacaoItemRegistrado)obj;
				}

				valorTotal = valorTotal.add(evento.getPreco());
				valorTotalDesconto = valorTotalDesconto.add(evento.getDesconto());
			}
		}

		pedido.setValor(valorTotal);
		
		pedido.setDesconto(valorTotalDesconto);

		
		// caso tenha alterado alguma quantidade ou tenha removido algum produto 
		
		//recalcular
		
		return ALTERNATIVA_1;
	}
}