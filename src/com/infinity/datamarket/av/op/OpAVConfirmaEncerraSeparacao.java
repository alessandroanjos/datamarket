package com.infinity.datamarket.av.op;

import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;


public class OpAVConfirmaEncerraSeparacao extends Mic {

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		try{
			
			gerenciadorPerifericos.getDisplay().setMensagem("Cancelar Separa��o? ");
			EntradaDisplay entrada;

			entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 0);

			if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
				gerenciadorPerifericos.getDisplay().setMensagem("Digite Operacao [P;D;E]");

				return ALTERNATIVA_1;
			}else{
				gerenciadorPerifericos.getDisplay().setMensagem("Digite Produto");
				
				return ALTERNATIVA_2;
			}


		}catch(Exception e){
			gerenciadorPerifericos.getDisplay().setMensagem("Erro");
			try{
				gerenciadorPerifericos.esperaVolta();
			}catch(AppException ex){

			}
			return ALTERNATIVA_2;
		}
	}

	
}