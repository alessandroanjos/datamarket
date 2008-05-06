package com.infinity.datamarket.pdv.mic;

import java.math.BigDecimal;

import com.infinity.datamarket.comum.util.StringUtil;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class MicExibeDisplayTotal extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		BigDecimal subTotal = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.SUB_TOTAL);
		gerenciadorPerifericos.getDisplay().setMensagem(StringUtil.completaStringCentro("A Receber",StringUtil.numeroToString(subTotal, 2, 0, ",", ".", true),27,' '));
		return ALTERNATIVA_1;
	}
}
