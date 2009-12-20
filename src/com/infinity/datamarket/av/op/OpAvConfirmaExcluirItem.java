package com.infinity.datamarket.av.op;

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

public class OpAvConfirmaExcluirItem extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		try {

			List<EventoOperacaoItemRegistrado> coll = (List<EventoOperacaoItemRegistrado>)gerenciadorPerifericos.getCmos().ler(CMOS.COLL_EVENTO_OPERACAO_ITEM_REGISTRADO_PEDIDO);
			if (coll == null || coll.size() ==0) {
				gerenciadorPerifericos.getDisplay().setMensagem("Sem Item para excluir");
				gerenciadorPerifericos.esperaVolta();
				return ALTERNATIVA_2;
			}

//			gerenciadorPerifericos.getDisplay().setMensagem("Excluir Item? [ENTER][ESC]");
//			EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 0);
//			if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
			
				return ALTERNATIVA_1;
//	
//			} else {
//				
//				return ALTERNATIVA_2;
//			}
	
		} catch (AppException e) {
			return ALTERNATIVA_2;
		}

	}

}
