package com.infinity.datamarket.pdv.op;

import java.math.BigDecimal;

import com.infinity.datamarket.comum.transacao.ConstantesTransacao;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpInicioPagamento extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		gerenciadorPerifericos.getCmos().gravar(CMOS.TIPO_TRANSACAO, new Integer(ConstantesTransacao.TRANSACAO_PAGAMENTO));
		gerenciadorPerifericos.getCmos().gravar(CMOS.SUB_TOTAL, BigDecimal.ZERO);
		gerenciadorPerifericos.getCmos().gravar(CMOS.TOTAL, BigDecimal.ZERO);
		gerenciadorPerifericos.getCmos().gravar(CMOS.QUANTIDADE_ITEM, BigDecimal.ONE);
		gerenciadorPerifericos.getCmos().gravar(CMOS.FORMA_RECEBIMENTO_ATUAL, null);
		gerenciadorPerifericos.getCmos().gravar(CMOS.TRANSACAO_PAGAMENTO_ATUAL, null);
		gerenciadorPerifericos.getCmos().gravar(CMOS.VALOR_PAGAMENTO_ATUAL, BigDecimal.ZERO);
		gerenciadorPerifericos.getCmos().gravar(CMOS.VALOR_TROCO_ATUAL, BigDecimal.ZERO);
		gerenciadorPerifericos.getCmos().gravar(CMOS.VALOR_TROCO_ATUAL, BigDecimal.ZERO);
		gerenciadorPerifericos.getCmos().gravar(CMOS.DESCONTO,BigDecimal.ZERO);
		gerenciadorPerifericos.getCmos().gravar(CMOS.ACRESSIMO,BigDecimal.ZERO);
		gerenciadorPerifericos.getCmos().gravar(CMOS.DADOS_CONSULTA_CARTAO_PROPRIO,null);
		return ALTERNATIVA_1;
	}

}
