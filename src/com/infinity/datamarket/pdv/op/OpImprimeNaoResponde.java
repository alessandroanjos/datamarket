package com.infinity.datamarket.pdv.op;

import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.StringUtil;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.ImpressoraFiscalException;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.tef.RespostaOperacaoTEF;
import com.infinity.datamarket.pdv.util.MensagensPDV;

public class OpImprimeNaoResponde extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		
//		try{

		gerenciadorPerifericos.getDisplay().setMensagem(MensagensPDV.getMensagem(this, "IMPRSSORA NAO RESPONDE"));
		try{
			gerenciadorPerifericos.esperaVolta();
		}catch(AppException ex){}

//		}catch(Exception e){
//			e.printStackTrace();
//			gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
//			try {
//				gerenciadorPerifericos.esperaVolta();
//			} catch (AppException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			return ALTERNATIVA_2;
//		}
		return ALTERNATIVA_1;
	}
}
