package com.infinity.datamarket.av.op;

import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.util.MensagensAV;

public class OpAVLeituraTipoOperacoa extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		
		gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Produto"));
		
		return ALTERNATIVA_1;
	}
}
