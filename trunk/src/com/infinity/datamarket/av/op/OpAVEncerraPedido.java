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

public class OpAVEncerraPedido extends Mic{
	
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		try {
			gerenciadorPerifericos.getDisplay().setMensagem("Canc. Pedido? [ENTRA] [ESC]");
			EntradaDisplay entrada;

			entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 0);

			if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){

				TelaAVInicial tela = (TelaAVInicial) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_AV_INICIAL);
				tela.setCampoCodigoProduto("");
				tela.setCampoDescricaoProduto("");
				tela.setCampoValor("");
				tela.setCampoQuantidade("");
				tela.setCampoDesconto("");
				tela.zerarTabela();
				tela.setCampoTotal("");
				

				tela.setCampoOperacao("");
				tela.setCampoCliente("");
				gerenciadorPerifericos.atualizaTela(tela);

				gerenciadorPerifericos.getCmos().gravar(CMOS.PRODUTO_ATUAL,null);
				gerenciadorPerifericos.getCmos().gravar(CMOS.QUANTIDADE_ITEM,null);
				gerenciadorPerifericos.getCmos().gravar(CMOS.DESCONTO,null);
				gerenciadorPerifericos.getCmos().gravar(CMOS.VALOR_ITEM_PEDIDO,null);
				gerenciadorPerifericos.getCmos().gravar(CMOS.CLIENTE_AV,null);

				gerenciadorPerifericos.getDisplay().setMensagem(OpAVIniciaPedido.MENSAGEM_INICIAL);


				return ALTERNATIVA_2;
			}else{

				gerenciadorPerifericos.getDisplay().setMensagem("Codigo do Produto");
				return ALTERNATIVA_1;
			}
		} catch (AppException e) {
			e.printStackTrace();
			return ALTERNATIVA_2;
		}
	}
}