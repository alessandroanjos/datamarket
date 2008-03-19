package com.infinity.datamarket.pdv.mic;

import java.math.BigDecimal;

import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;

public class MicResuprimento extends Mic{



	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		try{
			gerenciadorPerifericos.getDisplay().setMensagem("Resuprimento");
			EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_MONETARIA, 5);
			if (entrada.getTeclaFinalizadora() == 10){
				String valor = entrada.getDado();
				BigDecimal val = new BigDecimal(valor);
				gerenciadorPerifericos.getCmos().gravar(CMOS.VALOR_RESUPRIMENTO, val);
			}else{
				return ALTERNATIVA_2;
			}

		}catch(Exception e){
			return ALTERNATIVA_2;
		}
		return ALTERNATIVA_1;
	}
}
