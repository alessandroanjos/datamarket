package com.infinity.datamarket.pdv.mic;

import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.ImpressoraFiscalException;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class MicAberturaCupom extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		TransacaoVenda transVenda = (TransacaoVenda) gerenciadorPerifericos.getCmos().ler(CMOS.TRANSACAO_VENDA_ATUAL);
		if (transVenda.getEventosTransacao() == null || transVenda.getEventosTransacao().size() == 0){
			try{
				gerenciadorPerifericos.getDisplay().setMensagem("Aguarde...");
				gerenciadorPerifericos.getImpressoraFiscal().inicioCupomFiscal("");
			}catch(ImpressoraFiscalException e){
				gerenciadorPerifericos.getDisplay().setMensagem(e.getMessage());
				try{
					gerenciadorPerifericos.esperaVolta();
				}catch(Exception ex){
					return ALTERNATIVA_2;
				}
				return ALTERNATIVA_2;
			}
		}
		return ALTERNATIVA_1;
	}

}
