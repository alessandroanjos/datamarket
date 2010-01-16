package com.infinity.datamarket.av.op;

import java.math.BigDecimal;

import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpAvValidarProdutoSeparacao extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		// validar se o produto está dentro da separacao
		// validar se o produto ainda pode ser adicionado a separacao
		// quantidade necessaria para separacao
		gerenciadorPerifericos.getCmos().gravar(CMOS.QUANTIDADE_ITEM_NECESSARIO_PARA_SEPARACAO, BigDecimal.ONE);
		
		return ALTERNATIVA_1;
	}
}