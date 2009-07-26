package com.infinity.datamarket.pdv.op;

import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpErroSistema extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		try{
			gerenciadorPerifericos.getDisplay().setMensagem("Erro de Sistema  [VOLTA]");
			gerenciadorPerifericos.esperaVolta();
			gerenciadorPerifericos.getDisplay().setMensagem("");
		}catch (AppException e){
			return ALTERNATIVA_2;
		}
		return ALTERNATIVA_1;
	}
}
