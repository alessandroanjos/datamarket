package com.infinity.datamarket.av.op;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import com.infinity.datamarket.comum.operacao.EventoOperacaoItemRegistrado;
import com.infinity.datamarket.comum.operacao.EventoOperacaoItemRegistradoSeparacao;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpAvValidarProdutoSeparacao extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		Produto prod = (Produto)gerenciadorPerifericos.getCmos().ler(CMOS.PRODUTO_ATUAL);

		BigDecimal quantidadeSeparada = BigDecimal.ZERO;
		BigDecimal quantidadeRestante = BigDecimal.ZERO;

		boolean tem = false;
		List<EventoOperacaoItemRegistrado> coll = (List<EventoOperacaoItemRegistrado>)gerenciadorPerifericos.getCmos().ler(CMOS.COLL_EVENTO_OPERACAO_ITEM_REGISTRADO_PEDIDO);
		Iterator<EventoOperacaoItemRegistrado> it = coll.iterator();
		while(it.hasNext()) {

			Object objeto = it.next();
			EventoOperacaoItemRegistrado evento = null;
			if (objeto instanceof EventoOperacaoItemRegistradoSeparacao) {
				EventoOperacaoItemRegistradoSeparacao eventoSeparacao = (EventoOperacaoItemRegistradoSeparacao) objeto;
				evento = eventoSeparacao.getEventoOperacaoItemRegistrado();
				quantidadeSeparada = eventoSeparacao.getQuantidadeSeparada();
			} else if (objeto instanceof EventoOperacaoItemRegistrado) {
				evento = (EventoOperacaoItemRegistrado) objeto;
			}
			
			if (evento.getProdutoOperacaoItemRegistrado().getIdProduto() == prod.getId() ||
					evento.getProdutoOperacaoItemRegistrado().getCodigoExterno().equals(prod.getCodigoExterno())
				) {
				
				quantidadeRestante = evento.getQuantidade().subtract(quantidadeSeparada);
				tem = true;
				gerenciadorPerifericos.getCmos().gravar(CMOS.QUANTIDADE_ITEM_NECESSARIO_PARA_SEPARACAO, quantidadeRestante);
			}	
		}
		
		if (!tem) {
			gerenciadorPerifericos.getDisplay().setMensagem("Pedido sem Produto");
			try {
				gerenciadorPerifericos.esperaVolta();
			} catch (AppException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			gerenciadorPerifericos.getDisplay().setMensagem("Codigo do Produto");
			return ALTERNATIVA_2;
			
		}
		
		if(quantidadeRestante.doubleValue() <= BigDecimal.ZERO.doubleValue()) {
			gerenciadorPerifericos.getDisplay().setMensagem("Produto ja Separado");
			try {
				gerenciadorPerifericos.esperaVolta();
			} catch (AppException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			gerenciadorPerifericos.getDisplay().setMensagem("Codigo do Produto");
			
			return ALTERNATIVA_2;
		}
 
		gerenciadorPerifericos.getCmos().gravar(CMOS.QUANTIDADE_ITEM_NECESSARIO_PARA_SEPARACAO, quantidadeRestante);
		
		return ALTERNATIVA_1;
	}
}