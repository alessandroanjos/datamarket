package com.infinity.datamarket.pdv.op;

import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;

public class OpConfirmaCancelamentoUltimoCupom extends Mic{



	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		try{
			gerenciadorPerifericos.getDisplay().setMensagem("Canc. Cupom? [ENTRA] [ESC]");
			EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 0);
			if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
				return ALTERNATIVA_1;
			}else{
				return ALTERNATIVA_2;
			}
		} catch (AppException e) {
			e.printStackTrace();
			return ALTERNATIVA_2;
		}
	}
}