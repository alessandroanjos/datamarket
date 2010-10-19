package com.infinity.datamarket.pdv.op;

import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.tef.ExcecaoTEF;
import com.infinity.datamarket.pdv.tef.GerenciadorTEF;
import com.infinity.datamarket.pdv.tef.RespostaOperacaoTEF;
import com.infinity.datamarket.pdv.tef.SolicitacaoOperacaoTEF;

public class OpConfirmaVendaTef extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		
//		try{
//
//		}catch(ExcecaoTEF e){
//			e.printStackTrace();
//			gerenciadorPerifericos.getDisplay().setMensagem("Erro de confirmacao TEF");
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
