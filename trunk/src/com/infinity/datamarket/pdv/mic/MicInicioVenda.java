package com.infinity.datamarket.pdv.mic;

import java.math.BigDecimal;

import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class MicInicioVenda extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		gerenciadorPerifericos.getCmos().gravar(CMOS.SUB_TOTAL, new BigDecimal(0));
		gerenciadorPerifericos.getCmos().gravar(CMOS.TOTAL, new BigDecimal(0));
		gerenciadorPerifericos.getCmos().gravar(CMOS.QUANTIDADE_ITEM, new BigDecimal(1));
		gerenciadorPerifericos.getCmos().gravar(CMOS.FORMA_RECEBIMENTO_ATUAL, null);
		gerenciadorPerifericos.getCmos().gravar(CMOS.TRANSACAO_VENDA_ATUAL, null);
		gerenciadorPerifericos.getCmos().gravar(CMOS.VALOR_PAGAMENTO_ATUAL, new BigDecimal(0));
		gerenciadorPerifericos.getCmos().gravar(CMOS.VALOR_TROCO_ATUAL, new BigDecimal(0));
		gerenciadorPerifericos.getCmos().gravar(CMOS.VALOR_TROCO_ATUAL, new BigDecimal(0));
		return ALTERNATIVA_1;
	}

}