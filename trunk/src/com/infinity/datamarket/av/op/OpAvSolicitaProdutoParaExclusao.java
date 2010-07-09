package com.infinity.datamarket.av.op;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.infinity.datamarket.comum.operacao.EventoOperacaoItemRegistrado;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;
import com.infinity.datamarket.pdv.util.MensagensAV;

public class OpAvSolicitaProdutoParaExclusao extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		try {
			gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Cod. Item para Exluir"));
			EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 13);
			if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){

				List<EventoOperacaoItemRegistrado> coll = (List<EventoOperacaoItemRegistrado>)gerenciadorPerifericos.getCmos().ler(CMOS.COLL_EVENTO_OPERACAO_ITEM_REGISTRADO_PEDIDO);
				if (coll == null || coll.size() ==0) {
					gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Sem Item para excluir"));
					gerenciadorPerifericos.esperaVolta();
					return ALTERNATIVA_2;
				}
				boolean tem = false;
				EventoOperacaoItemRegistrado eventoParaExcluir =null;
				Iterator<EventoOperacaoItemRegistrado> it = coll.iterator();
				while(it.hasNext()) {
					EventoOperacaoItemRegistrado evento = it.next();
					if (evento.getProdutoOperacaoItemRegistrado().getCodigoExterno().equals(entrada.getDado())) {
						tem = true;
						eventoParaExcluir = evento ;
						break;
					}
				}
				if (!tem) {
					gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Sem Produto no Pedido"));
					gerenciadorPerifericos.esperaVolta();
					return ALTERNATIVA_2;
				}
				
				coll.remove(eventoParaExcluir);

				return ALTERNATIVA_1;
	
			} else {
				return ALTERNATIVA_2;
			}
	
		} catch (AppException e) {
			return ALTERNATIVA_2;
		}
	}
}