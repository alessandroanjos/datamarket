package com.infinity.datamarket.pdv.mic;


import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class MicDecideVoltaSobreposicao extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		TransacaoVenda transVenda = (TransacaoVenda) gerenciadorPerifericos.getCmos().ler(CMOS.TRANSACAO_VENDA_ATUAL);
		if (transVenda == null || transVenda.getEventosTransacao() == null){
			return ALTERNATIVA_1;
		}else{
			return ALTERNATIVA_2;
		}
	}
	
}