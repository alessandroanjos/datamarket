package com.infinity.datamarket.pdv.mic;

import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;


public class MicVoltaVenda extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		TransacaoVenda transVenda = (TransacaoVenda) gerenciadorPerifericos.getCmos().ler(CMOS.TRANSACAO_VENDA_ATUAL);

		if(transVenda.getEventosTransacao() != null && transVenda.getEventosTransacao().size() > 0){
			return ALTERNATIVA_2;
		}
		
		gerenciadorPerifericos.decrementaNumeroTransacao();

		return ALTERNATIVA_1;
	}

}
