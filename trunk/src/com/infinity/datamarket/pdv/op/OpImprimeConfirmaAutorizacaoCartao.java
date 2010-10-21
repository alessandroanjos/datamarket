package com.infinity.datamarket.pdv.op;

import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.StringUtil;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.ImpressoraFiscalException;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.tef.GerenciadorTEF;
import com.infinity.datamarket.pdv.tef.RespostaOperacaoTEF;
import com.infinity.datamarket.pdv.tef.SolicitacaoOperacaoTEF;

public class OpImprimeConfirmaAutorizacaoCartao extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		try{
			RespostaOperacaoTEF  respostaTEF  = (RespostaOperacaoTEF )gerenciadorPerifericos.getCmos().ler("respostaSolicitacao");
			if (respostaTEF != null) {
				imprimeComprovante(respostaTEF.getLinhasComprovantePrincipal(), gerenciadorPerifericos);
			}

		}catch(ImpressoraFiscalException e){
			e.printStackTrace();
			gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
			try {
				gerenciadorPerifericos.esperaVolta();
			} catch (AppException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return ALTERNATIVA_2;

		}catch(Exception e){
			e.printStackTrace();
			gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
			try {
				gerenciadorPerifericos.esperaVolta();
			} catch (AppException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return ALTERNATIVA_2;
		}
		return ALTERNATIVA_1;
	}
	
	private void imprimeComprovante(String[] linhas, GerenciadorPerifericos ger) throws ImpressoraFiscalException{
		ger.getImpressoraFiscal().iniciaComprovanteNaoFiscalVinculado();
		ger.getImpressoraFiscal().imprimeComprovanteNaoFiscalVinculado(linhas);
		ger.getImpressoraFiscal().finalizaComprovanteNaoFiscalVinculado();
	}
	
}
