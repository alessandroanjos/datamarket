package com.infinity.datamarket.av.op;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.infinity.datamarket.comum.operacao.EventoOperacaoItemRegistrado;
import com.infinity.datamarket.comum.operacao.EventoOperacaoItemRegistradoSeparacao;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpAvRetiraProdutosNaoSeparados extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

			boolean recalcularValorPedido = false;
			
			Collection collItensPedidoSeparadosParaRemover = new ArrayList();

			Collection collItensPedidoSeparados = (List<EventoOperacaoItemRegistrado>)gerenciadorPerifericos.getCmos().ler(CMOS.COLL_EVENTO_OPERACAO_ITEM_REGISTRADO_PEDIDO);

			Iterator<EventoOperacaoItemRegistrado> it = collItensPedidoSeparados.iterator();
			while(it.hasNext()) {
				BigDecimal quantidadeSeparada = BigDecimal.ZERO;
				EventoOperacaoItemRegistrado evento = null;
				Object obj = (Object) it.next();
				if (obj instanceof EventoOperacaoItemRegistradoSeparacao) {
					evento = ((EventoOperacaoItemRegistradoSeparacao)obj).getEventoOperacaoItemRegistrado();
					quantidadeSeparada = ((EventoOperacaoItemRegistradoSeparacao)obj).getQuantidadeSeparada();

				} else {
					evento = (EventoOperacaoItemRegistrado)obj;
				}
				if (quantidadeSeparada == BigDecimal.ZERO) {
					collItensPedidoSeparadosParaRemover.add(obj);
				} else {
					if (!quantidadeSeparada.equals(evento.getQuantidade())) {
						evento.setQuantidade(quantidadeSeparada);
						recalcularValorPedido = true;
					}
				}
			}

			it = collItensPedidoSeparadosParaRemover.iterator();
			while(it.hasNext()) {
				Object obj = (Object) it.next();
				collItensPedidoSeparados.remove(obj);
				recalcularValorPedido = true;
			}	
			
			if (recalcularValorPedido) {
				return ALTERNATIVA_2;
			}

			
		return ALTERNATIVA_1;
	}
}