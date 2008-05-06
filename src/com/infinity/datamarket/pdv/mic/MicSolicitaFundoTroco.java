package com.infinity.datamarket.pdv.mic;

import java.math.BigDecimal;

import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;

public class MicSolicitaFundoTroco extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		try{
			gerenciadorPerifericos.getDisplay().setMensagem("Fundo Troco");
			EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_MONETARIA, 7);
			if (entrada.getTeclaFinalizadora() == 10){
				String valor = entrada.getDado();
				BigDecimal fundoTroco = new BigDecimal(valor);
				gerenciadorPerifericos.getCmos().gravar(CMOS.FUNDO_TROCO, fundoTroco);
				return ALTERNATIVA_1;
			}else{
				return ALTERNATIVA_2;
			}
		}catch(AppException e){
			return ALTERNATIVA_2;
		}
	}

}
