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

public class OpAVIniciaPedido extends Mic{

	public static String  MENSAGEM_INICIAL  = "Digite Operacao [1-P;2-D]";



	public static String OP_DESC_PEDIDO = "Pedido";
	public static String OP_DESC_DEVOLUCAO = "Devolucao";

	public static int OP_CODIGO_PEDIDO = 1;
	public static int OP_CODIGO_DEVOLUCAO = 2;

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		try{

				TelaAVInicial tela = (TelaAVInicial) gerenciadorPerifericos.getCmos().ler(CMOS.TELA_ATUAL);
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
