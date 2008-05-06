package com.infinity.datamarket.pdv.mic;

import java.math.BigDecimal;

import com.infinity.datamarket.comum.util.StringUtil;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class MicExibeDisplayMultiplicacao extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		BigDecimal quantidade = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.QUANTIDADE_ITEM);
		gerenciadorPerifericos.getDisplay().setMensagem(StringUtil.numeroToString(quantidade, 3, 0, ",", ".", true)+" x "+"Código Produto");
		return ALTERNATIVA_1;
	}
}
