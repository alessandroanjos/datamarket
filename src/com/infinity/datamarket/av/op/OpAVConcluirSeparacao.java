package com.infinity.datamarket.av.op;

import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.util.MensagensAV;


public class OpAVConcluirSeparacao extends OpAVEncerraPedido {


	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Separacao Concluida"));
		try {
			gerenciadorPerifericos.esperaEntra();
		} catch (AppException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Digite Operacao [P;D;E]"));

		return ALTERNATIVA_1;
	}
}