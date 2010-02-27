package com.infinity.datamarket.av.op;

import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Estado;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.util.MensagensAV;

public class OpAvExibeDisplayDescricaoEstado extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		Estado estAtual = (Estado)gerenciadorPerifericos.getCmos().ler(CMOS.ESTADO_ATUAL);
		
		gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, estAtual.getDescricao()));
		return ALTERNATIVA_1;
	}
}
