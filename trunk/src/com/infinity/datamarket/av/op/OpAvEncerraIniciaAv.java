package com.infinity.datamarket.av.op;

import com.infinity.datamarket.av.gui.telas.TelaAVInicial;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;
import com.infinity.datamarket.pdv.util.MensagensAV;


public class OpAvEncerraIniciaAv extends OpAVIniciaAV{

	public static String  MENSAGEM_INICIAL  = "AV Fechado [ENTER]";

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		try{

			
			gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Fecha AV? [ENTRA] [ESC]"));
			EntradaDisplay entrada;

			entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 0);

			if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
				super.exec(gerenciadorPerifericos,param);
				gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, MENSAGEM_INICIAL ));

				return ALTERNATIVA_1;
			}else{
				gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Digite Operacao [P;D;E]"));
				
				return ALTERNATIVA_2;
			}


		}catch(Exception e){
			gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Erro"));
			try{
				gerenciadorPerifericos.esperaVolta();
			}catch(AppException ex){

			}
			return ALTERNATIVA_2;
		}
	}

}
