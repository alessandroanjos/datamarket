package com.infinity.datamarket.av.op;

import com.infinity.datamarket.av.gui.telas.TelaAVInicial;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Constantes;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;

public class OpAVIniciaSeparacao extends OpAVEncerraPedido {

	public static String  MENSAGEM_INICIAL  = OpAVIniciaPedido.MENSAGEM_INICIAL;

	public static String OP_DESC_PEDIDO = OpAVIniciaPedido.OP_DESC_PEDIDO;
	public static String OP_DESC_SEPARACAO = OpAVIniciaPedido.OP_DESC_SEPARACAO;
	public static String OP_DESC_DEVOLUCAO = OpAVIniciaPedido.OP_DESC_DEVOLUCAO;
	
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		try{

			gerenciadorPerifericos.getCmos().gravar(CMOS.OPERACAO_ATUAL, CMOS.OPERACAO_SEPARACAO);

			super.exec(gerenciadorPerifericos, param);

			gerenciadorPerifericos.getDisplay().setMensagem(OpAVIniciaPedido.MENSAGEM_INICIAL);

			gerenciadorPerifericos.getCmos().gravar(CMOS.OPERACAO_ATUAL, CMOS.OPERACAO_SEPARACAO);

			TelaAVInicial tela = (TelaAVInicial) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_AV_INICIAL);
			tela.setCampoOperacao(OP_DESC_SEPARACAO);
			gerenciadorPerifericos.atualizaTela(tela);

			return ALTERNATIVA_1;

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