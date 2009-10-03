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

			
		gerenciadorPerifericos.getDisplay().setMensagem(" ");
		Produto prod = (Produto)gerenciadorPerifericos.getCmos().ler(CMOS.PRODUTO_ATUAL);
		Integer quantidade = (Integer)gerenciadorPerifericos.getCmos().ler(CMOS.QUANTIDADE_ITEM);
		BigDecimal desc = (BigDecimal)gerenciadorPerifericos.getCmos().ler(CMOS.DESCONTO);
		BigDecimal val = (BigDecimal)gerenciadorPerifericos.getCmos().ler(CMOS.VALOR_ITEM);
		if (desc == null) {
			desc  = BigDecimal.ZERO;
		}
		
		String produto = prod.getDescricaoCompacta(); 
		String valor = "R$ " + val.toString();
		String descont = "R$ " + desc.toString();
		String total = "R$ " + val.multiply(new BigDecimal(quantidade)).subtract(desc).toString();
		
//		adicionarRegistroTabela(String produto, String valor, String quantidade, String descont, String total)
		TelaAVInicial tela = (TelaAVInicial) gerenciadorPerifericos.getCmos().ler(CMOS.TELA_ATUAL);
		tela.adicionarRegistroTabela(produto, valor, quantidade + "", descont, total);
		gerenciadorPerifericos.atualizaTela(tela);

		gerenciadorPerifericos.getCmos().gravar(CMOS.PRODUTO_ATUAL,null);
		gerenciadorPerifericos.getCmos().gravar(CMOS.QUANTIDADE_ITEM,null);
		gerenciadorPerifericos.getCmos().gravar(CMOS.DESCONTO,null);
	
		return ALTERNATIVA_1;
	}
}
