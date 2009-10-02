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

public class OpAVSolicitaOperacao extends Mic{

	public static String OP_DESC_PEDIDO = "Pedido";
	public static String OP_DESC_DEVOLUCAO = "Devolucao";

	public static int OP_CODIGO_PEDIDO = 1;
	public static int OP_CODIGO_DEVOLUCAO = 2;

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		try{

				gerenciadorPerifericos.getDisplay().setMensagem("Digite Operacao [1-P;2-D]");
				EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 1);
				int opcao = 0;
				if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
					String op = entrada.getDado();
					if (op.equals("")) {
						gerenciadorPerifericos.getDisplay().setMensagem("Operacao Invalida");
						gerenciadorPerifericos.esperaVolta();
						return ALTERNATIVA_2;
					}
					
					try {
						opcao = new Integer(op).intValue();
						if (opcao == 0) {
							gerenciadorPerifericos.getDisplay().setMensagem("Operacao Invalida");
							gerenciadorPerifericos.esperaVolta();
							return ALTERNATIVA_2;
						}
					} catch (Exception e) {
						gerenciadorPerifericos.getDisplay().setMensagem("Operacao Invalida");
						gerenciadorPerifericos.esperaVolta();
						return ALTERNATIVA_2;
					}
				} else if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_VOLTA){
					gerenciadorPerifericos.getDisplay().setMensagem("Operacao Invalida");
					gerenciadorPerifericos.esperaVolta();
					return ALTERNATIVA_2;
				}

				String descOperacao = "";
				if (opcao == 1) {
					descOperacao = OP_DESC_PEDIDO;
					gerenciadorPerifericos.getCmos().gravar(CMOS.OPERADOR_ATUAL, CMOS.OPERACAO_PEDIDO);
				} else if (opcao == 2) {
					descOperacao = OP_DESC_DEVOLUCAO;
					gerenciadorPerifericos.getCmos().gravar(CMOS.OPERADOR_ATUAL, CMOS.OPERACAO_DEVOLUCAO);
				}

				TelaAVInicial tela = (TelaAVInicial) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_AV_INICIAL);
				tela.setCampoOperacao(descOperacao);
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
