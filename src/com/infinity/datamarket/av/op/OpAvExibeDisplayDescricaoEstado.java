package com.infinity.datamarket.av.op;

import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Estado;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpAvExibeDisplayDescricaoEstado extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		Estado estAtual = (Estado)gerenciadorPerifericos.getCmos().ler(CMOS.ESTADO_ATUAL);
		
		gerenciadorPerifericos.getDisplay().setMensagem(estAtual.getDescricao());
		return ALTERNATIVA_1;
	}
}
