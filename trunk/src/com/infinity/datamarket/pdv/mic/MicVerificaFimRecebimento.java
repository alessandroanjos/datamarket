package com.infinity.datamarket.pdv.mic;

import java.math.BigDecimal;

import com.infinity.datamarket.comum.transacao.ConstantesTransacao;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class MicVerificaFimRecebimento extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		BigDecimal valorPagamento = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.VALOR_PAGAMENTO_ATUAL);
		BigDecimal subTotal = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.SUB_TOTAL);

		if (valorPagamento.compareTo(subTotal) > 0){
			BigDecimal troco = valorPagamento.subtract(subTotal);
//			gerenciadorPerifericos.getCmos().gravar(CMOS.SUB_TOTAL,new BigDecimal(0));
			gerenciadorPerifericos.getCmos().gravar(CMOS.VALOR_TROCO_ATUAL,troco);
			return verificaTipoTransacaoFim(gerenciadorPerifericos);
		}else if (valorPagamento.compareTo(subTotal) < 0){
			BigDecimal newSubTotal = subTotal.subtract(valorPagamento);
			gerenciadorPerifericos.getCmos().gravar(CMOS.SUB_TOTAL,newSubTotal);
			gerenciadorPerifericos.getCmos().gravar(CMOS.VALOR_TROCO_ATUAL,new BigDecimal(0));
			return verificaTipoTransacaoContinua(gerenciadorPerifericos);
		}else{
//			gerenciadorPerifericos.getCmos().gravar(CMOS.SUB_TOTAL,new BigDecimal(0));
			gerenciadorPerifericos.getCmos().gravar(CMOS.VALOR_TROCO_ATUAL,new BigDecimal(0));
			return verificaTipoTransacaoFim(gerenciadorPerifericos);
		}
	}
	
	private int verificaTipoTransacaoFim(GerenciadorPerifericos gerenciadorPerifericos){
		Integer tipoTransacao = (Integer) gerenciadorPerifericos.getCmos().ler(CMOS.TIPO_TRANSACAO);
		if (tipoTransacao.intValue() == ConstantesTransacao.TRANSACAO_VENDA){
			return ALTERNATIVA_1;
		}else {
			return ALTERNATIVA_3;
		}
	}
	
	private int verificaTipoTransacaoContinua(GerenciadorPerifericos gerenciadorPerifericos){
		Integer tipoTransacao = (Integer) gerenciadorPerifericos.getCmos().ler(CMOS.TIPO_TRANSACAO);
		if (tipoTransacao.intValue() == ConstantesTransacao.TRANSACAO_VENDA){
			return ALTERNATIVA_2;
		}else {
			return ALTERNATIVA_4;
		}
	}

}
