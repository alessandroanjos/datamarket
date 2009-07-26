package com.infinity.datamarket.pdv.op;

import java.math.BigDecimal;

import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.StringUtil;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpExibeDisplayTroco extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		try{
			BigDecimal troco = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.VALOR_TROCO_ATUAL);
			String linha = StringUtil.completaStringCentro("Troco", StringUtil.numeroToString(troco, 2, 0, ",", ".", true), 27, ' ');
			gerenciadorPerifericos.getDisplay().setMensagem(linha);
			gerenciadorPerifericos.esperaEntra();
			return ALTERNATIVA_1;
		}catch(AppException e){
			return ALTERNATIVA_2;
		}
	}
}
