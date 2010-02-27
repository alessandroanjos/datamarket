package com.infinity.datamarket.av.op;

import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.util.MensagensAV;

public class OpAVBloqueiaAV extends OpAVIniciaAV{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		int retorno  = super.exec(gerenciadorPerifericos, param);
		
    	gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Av Fechado"));
		
		return retorno;
	}
}
