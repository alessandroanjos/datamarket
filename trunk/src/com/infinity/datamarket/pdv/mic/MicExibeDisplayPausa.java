package com.infinity.datamarket.pdv.mic;

import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class MicExibeDisplayPausa extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		gerenciadorPerifericos.getDisplay().setMensagem("Pausa");
		return ALTERNATIVA_1;
	}
}
