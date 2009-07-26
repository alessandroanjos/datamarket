package com.infinity.datamarket.pdv.op;

import java.math.BigDecimal;

import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpMultiplicacao extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		try{
			BigDecimal i = new BigDecimal(param.getParam());
			gerenciadorPerifericos.getCmos().gravar(CMOS.QUANTIDADE_ITEM, i);
		}catch(Exception e){

		}
		return ALTERNATIVA_1;
	}

}
