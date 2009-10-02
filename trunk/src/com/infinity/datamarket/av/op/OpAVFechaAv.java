package com.infinity.datamarket.av.op;

import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;


public class OpAVFechaAv extends OpAVIniciaAV{

	public static String  MENSAGEM_INICIAL  = "AV Fechado [ENTER]";
	            
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		try{

			gerenciadorPerifericos.getDisplay().setMensagem(MENSAGEM_INICIAL );

		}catch(Exception e){
			gerenciadorPerifericos.getDisplay().setMensagem("Erro");
			try{
				gerenciadorPerifericos.esperaVolta();
			}catch(AppException ex){

			}
			return ALTERNATIVA_2;
		}
		return ALTERNATIVA_2;
	}

}
