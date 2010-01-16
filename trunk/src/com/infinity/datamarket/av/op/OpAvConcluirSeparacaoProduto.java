package com.infinity.datamarket.av.op;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import com.infinity.datamarket.comum.operacao.EventoOperacaoItemRegistrado;
import com.infinity.datamarket.comum.operacao.EventoOperacaoItemRegistradoSeparacao;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpAvConcluirSeparacaoProduto extends Mic{
	
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		
		BigDecimal quantidade = (BigDecimal)gerenciadorPerifericos.getCmos().ler(CMOS.QUANTIDADE_ITEM);
		
		Produto prod = (Produto)gerenciadorPerifericos.getCmos().ler(CMOS.PRODUTO_ATUAL);

		List<EventoOperacaoItemRegistrado> coll = (List<EventoOperacaoItemRegistrado>)gerenciadorPerifericos.getCmos().ler(CMOS.COLL_EVENTO_OPERACAO_ITEM_REGISTRADO_PEDIDO);

		Iterator<EventoOperacaoItemRegistrado> it = coll.iterator();
		while(it.hasNext()) {

			Object objeto = it.next();
			BigDecimal quantidadeSeparacao = BigDecimal.ZERO;
			EventoOperacaoItemRegistrado evento = null;
			if (objeto instanceof EventoOperacaoItemRegistradoSeparacao) {
				EventoOperacaoItemRegistradoSeparacao eventoSeparacao = (EventoOperacaoItemRegistradoSeparacao) objeto;
				evento = eventoSeparacao.getEventoOperacaoItemRegistrado();
				quantidadeSeparacao = eventoSeparacao.getQuantidadeSeparada();
				if (evento.getProdutoOperacaoItemRegistrado().getIdProduto() == prod.getId() ||
						evento.getProdutoOperacaoItemRegistrado().getCodigoExterno().equals(prod.getCodigoExterno())
					) {
					eventoSeparacao.setQuantidadeSeparada( eventoSeparacao.getQuantidadeSeparada().add(quantidade));
				}
			}

		}

		// adicionar a quantidade ao evento 
		return ALTERNATIVA_1;
	}
}
