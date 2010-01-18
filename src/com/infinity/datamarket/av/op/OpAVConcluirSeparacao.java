package com.infinity.datamarket.av.op;

import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;


public class OpAVConcluirSeparacao extends OpAVEncerraPedido {


	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		gerenciadorPerifericos.getDisplay().setMensagem("Separacao Concluida");
		try {
			gerenciadorPerifericos.esperaEntra();
		} catch (AppException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		gerenciadorPerifericos.getDisplay().setMensagem(OpAVIniciaPedido.MENSAGEM_INICIAL);

		return ALTERNATIVA_1;
	}
}