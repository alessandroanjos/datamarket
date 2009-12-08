package com.infinity.datamarket.av.op;

import java.math.BigDecimal;

import com.infinity.datamarket.av.gui.telas.TelaAVInicial;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpAvAdicionaItemPedido extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		try {

			gerenciadorPerifericos.getDisplay().setMensagem(" ");
			Produto prod = (Produto)gerenciadorPerifericos.getCmos().ler(CMOS.PRODUTO_ATUAL);
			BigDecimal quantidade = (BigDecimal)gerenciadorPerifericos.getCmos().ler(CMOS.QUANTIDADE_ITEM);
			BigDecimal desc = (BigDecimal)gerenciadorPerifericos.getCmos().ler(CMOS.DESCONTO);
			BigDecimal val = (BigDecimal)gerenciadorPerifericos.getCmos().ler(CMOS.VALOR_ITEM_PEDIDO);
			if (desc == null) {
				desc  = BigDecimal.ZERO;
			}
			BigDecimal valTotal = (BigDecimal)gerenciadorPerifericos.getCmos().ler(CMOS.VALOR_TOTAL_PEDIDO);
			if (valTotal == null) {
				valTotal = BigDecimal.ZERO;
				gerenciadorPerifericos.getCmos().gravar(CMOS.VALOR_TOTAL_PEDIDO, valTotal);
			}
	
			String produto = prod.getDescricaoCompacta(); 
			String valor = "R$ " + val.toString();
			String descont = "R$ " + desc.toString();
			String total = "R$ " + val.multiply(quantidade).subtract(desc).toString();
			
			valTotal = valTotal.add(val.multiply(quantidade).subtract(desc));
	
			TelaAVInicial tela = (TelaAVInicial) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_AV_INICIAL);
			tela.adicionarRegistroTabela(produto, valor, quantidade + "", descont, total);
			tela.setCampoTotal("R$ " + valTotal);
			gerenciadorPerifericos.atualizaTela(tela);
	
			gerenciadorPerifericos.getCmos().gravar(CMOS.PRODUTO_ATUAL,null);
			gerenciadorPerifericos.getCmos().gravar(CMOS.QUANTIDADE_ITEM,null);
			gerenciadorPerifericos.getCmos().gravar(CMOS.DESCONTO,null);
			gerenciadorPerifericos.getCmos().gravar(CMOS.VALOR_TOTAL_PEDIDO, valTotal);
		
			return ALTERNATIVA_1;

		
		} catch (Exception e) {
			return ALTERNATIVA_2;
		}
	}
}
