package com.infinity.datamarket.pdv.op;

import com.infinity.datamarket.comum.transacao.TransacaoPagamento;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;


public class OpCancelaTransacaoPagamento extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		
		TransacaoPagamento transacao = (TransacaoPagamento) gerenciadorPerifericos.getCmos().ler(CMOS.TRANSACAO_PAGAMENTO_ATUAL);		
		transacao.setSituacao(TransacaoVenda.CANCELADO);		
		gerenciadorPerifericos.getCmos().gravar(CMOS.TRANSACAO_PAGAMENTO_ATUAL, transacao);

		return ALTERNATIVA_1;
	}

}
