package com.infinity.datamarket.pdv.mic;

import java.math.BigDecimal;
import java.util.Date;

import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.ImpressoraFiscalException;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class MicIncluiTransacaoVenda extends Mic{



	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		TransacaoVenda trans = (TransacaoVenda) gerenciadorPerifericos.getCmos().ler(CMOS.TRANSACAO_VENDA_ATUAL);

		BigDecimal troco = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.VALOR_TROCO_ATUAL);
		BigDecimal total = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.TOTAL);

		trans.setDataHoraFim(new Date());
		trans.setValorTroco(troco);
		String cupom = "1";
		try {
			cupom = gerenciadorPerifericos.getImpressoraFiscal().getNumeroCupom()+"";
		} catch (ImpressoraFiscalException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		trans.setNumeroCupom(cupom);
		trans.setValorCupom(total);

		try{
			getFachadaPDV().inserirTransacao(trans);
			gerenciadorPerifericos.getCmos().gravar(CMOS.CHAVE_ULTIMA_TRANSACAO, trans.getPk());
		}catch(AppException e){
			e.printStackTrace();
		}

		return ALTERNATIVA_1;
	}
}
