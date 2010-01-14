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

public class OpAVIniciaSeparacao extends OpAVEncerraPedido {

	public static String  MENSAGEM_INICIAL  = "Digite Operacao [P;D;E]";

	public static String OP_DESC_PEDIDO = "Pedido";
	public static String OP_DESC_SEPARACAO = "Separa��o";
	public static String OP_DESC_DEVOLUCAO = "Devolu��o";

	public static int OP_CODIGO_PEDIDO = 1;
	public static int OP_CODIGO_DEVOLUCAO = 2;

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		try{

			super.exec(gerenciadorPerifericos, param);

				TelaAVInicial tela = (TelaAVInicial) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_AV_INICIAL);
				tela.setCampoOperacao(OP_DESC_PEDIDO);
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