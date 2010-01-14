package com.infinity.datamarket.av.op;

import com.infinity.datamarket.av.gui.telas.TelaAVInicial;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpAVEncerraPedido extends Mic{
	
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){


		TelaAVInicial tela = (TelaAVInicial) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_AV_INICIAL);
		tela.setCampoCodigoProduto("");
		tela.setCampoDescricaoProduto("");
		tela.setCampoValor("");
		tela.setCampoQuantidade("");
		tela.setCampoDesconto("");
		
		String operacao = (String)gerenciadorPerifericos.getCmos().ler(CMOS.OPERACAO_ATUAL);
		if (CMOS.OPERACAO_SEPARACAO.equals(operacao)) {
			tela.zerarTabelaSeparacao();
		} else {
			tela.zerarTabela();
		}
		
		tela.setCampoTotal("");
		tela.setCampoTotalDesconto("");

		tela.setCampoOperacao("");
		tela.setCampoCliente("");
		gerenciadorPerifericos.atualizaTela(tela);

		gerenciadorPerifericos.getCmos().gravar(CMOS.OPERACAO_ATUAL, null);

		gerenciadorPerifericos.getCmos().gravar(CMOS.PRODUTO_ATUAL,null);
		gerenciadorPerifericos.getCmos().gravar(CMOS.QUANTIDADE_ITEM,null);
		gerenciadorPerifericos.getCmos().gravar(CMOS.DESCONTO,null);
		gerenciadorPerifericos.getCmos().gravar(CMOS.VALOR_ITEM_PEDIDO,null);
		gerenciadorPerifericos.getCmos().gravar(CMOS.CLIENTE_AV,null);

		gerenciadorPerifericos.getCmos().gravar(CMOS.VALOR_TOTAL_PEDIDO,null);
		gerenciadorPerifericos.getCmos().gravar(CMOS.VALOR_TOTAL_DESCONTO_PEDIDO,null);

		gerenciadorPerifericos.getCmos().gravar(CMOS.OPERACAO_PEDIDO,null);
		gerenciadorPerifericos.getCmos().gravar(CMOS.VALOR_TOTAL_DESCONTO_PEDIDO,null);
		gerenciadorPerifericos.getCmos().gravar(CMOS.VALOR_TOTAL_DESCONTO_PEDIDO,null);
		gerenciadorPerifericos.getCmos().gravar(CMOS.VALOR_TOTAL_DESCONTO_PEDIDO,null);
		gerenciadorPerifericos.getCmos().gravar(CMOS.VALOR_TOTAL_DESCONTO_PEDIDO,null);

		gerenciadorPerifericos.getCmos().gravar(CMOS.COLL_EVENTO_OPERACAO_ITEM_REGISTRADO_PEDIDO,null);
		
		
		gerenciadorPerifericos.getDisplay().setMensagem(OpAVIniciaPedido.MENSAGEM_INICIAL);

		return ALTERNATIVA_1;
	}
}