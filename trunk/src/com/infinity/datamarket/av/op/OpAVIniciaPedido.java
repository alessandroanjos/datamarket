package com.infinity.datamarket.av.op;

import com.infinity.datamarket.av.gui.telas.TelaAVInicial;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;
import com.infinity.datamarket.pdv.util.MensagensAV;

public class OpAVIniciaPedido extends Mic{


	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		try{

			gerenciadorPerifericos.getCmos().gravar(CMOS.OPERACAO_ATUAL, CMOS.OPERACAO_PEDIDO);

			TelaAVInicial tela = (TelaAVInicial) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_AV_INICIAL);
			tela.setCampoOperacao("Pedido");
			gerenciadorPerifericos.atualizaTela(tela);

			return ALTERNATIVA_1;

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
