package com.infinity.datamarket.pdv.op;

import java.util.Date;

import com.infinity.datamarket.comum.transacao.TransacaoPagamento;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpIncluiTransacaoPagamento extends Mic{



	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
	
		TransacaoPagamento transPagamento = (TransacaoPagamento) gerenciadorPerifericos.getCmos().ler(CMOS.TRANSACAO_PAGAMENTO_ATUAL);
		transPagamento.setDataHoraFim(new Date());
	
		try{
			getFachadaPDV().inserirTransacao(transPagamento);
			gerenciadorPerifericos.getCmos().gravar(CMOS.CHAVE_ULTIMA_TRANSACAO, transPagamento.getPk());
		}catch(AppException e){
			e.printStackTrace();
		}

		return ALTERNATIVA_1;
	}
}
