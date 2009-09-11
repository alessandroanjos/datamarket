package com.infinity.datamarket.av.op;

import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpAVBloqueiaAV extends OpAVIniciaAV{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		int retorno  = super.exec(gerenciadorPerifericos, param);
		
    	gerenciadorPerifericos.getDisplay().setMensagem("Av Fechado");
		
		return retorno;
	}
}
