package com.infinity.datamarket.av.op;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import com.infinity.datamarket.comum.operacao.EventoOperacaoItemRegistrado;
import com.infinity.datamarket.comum.operacao.EventoOperacaoItemRegistradoSeparacao;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;

public class OpAvConfirmaFinalizacaoSeparacao extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		try {

			List<EventoOperacaoItemRegistrado> coll = (List<EventoOperacaoItemRegistrado>)gerenciadorPerifericos.getCmos().ler(CMOS.COLL_EVENTO_OPERACAO_ITEM_REGISTRADO_PEDIDO);
			if (coll == null || coll.size() ==0) {
				gerenciadorPerifericos.getDisplay().setMensagem("Sem Item no Pedido");
				gerenciadorPerifericos.esperaVolta();
				return ALTERNATIVA_2;
			} else {
				
				boolean temItemSeparado = false;
				boolean temProdutoNaoSeparado = false;
				
				Iterator<EventoOperacaoItemRegistrado> it  = coll.iterator();
				while (it.hasNext()) {
					EventoOperacaoItemRegistrado eoir = it.next();
					if (eoir instanceof EventoOperacaoItemRegistradoSeparacao) {
						EventoOperacaoItemRegistradoSeparacao ioirs = (EventoOperacaoItemRegistradoSeparacao) eoir;
						if ( ioirs.getQuantidadeSeparada()  != BigDecimal.ZERO) {
							temItemSeparado = true;	
						}
						if ( ioirs.getQuantidadeSeparada()  == BigDecimal.ZERO) {
							temProdutoNaoSeparado = true;	
						}
					}
				}

				if (!temItemSeparado) {
					gerenciadorPerifericos.getDisplay().setMensagem("Sem Prod. Separado [Voltar]");
					gerenciadorPerifericos.esperaVolta();
					gerenciadorPerifericos.getDisplay().setMensagem("Codigo do Produto");
					return ALTERNATIVA_2;
				}

			}

			gerenciadorPerifericos.getDisplay().setMensagem("Finalizar Separaca? [ENTER][ESC]");
			EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 0);
			if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
			
				return ALTERNATIVA_1;
	
			} else {
				
				return ALTERNATIVA_2;
			}
	
		} catch (AppException e) {
			return ALTERNATIVA_2;
		}

	}

}
