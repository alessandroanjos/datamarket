package com.infinity.datamarket.av.op;

import java.math.BigDecimal;

import com.infinity.datamarket.av.gui.telas.TelaAVInicial;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.produto.TipoProduto;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpAvConsultaQuantidadeProdutoSeparacao extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){


		// se solicitar a quantidade tem que validar se nao utrapassou da necessidade
		// gerenciadorPerifericos.getCmos().ler(CMOS.QUANTIDADE_ITEM_NECESSARIO_PARA_SEPARACAO)

		TelaAVInicial tela = (TelaAVInicial) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_AV_INICIAL);
		tela.setCampoQuantidade("");
		tela.setCampoDesconto("");
		gerenciadorPerifericos.atualizaTela(tela);

		Produto produto = (Produto)gerenciadorPerifericos.getCmos().ler(CMOS.PRODUTO_ATUAL);

		BigDecimal quantidade = BigDecimal.ZERO;
		
		if (produto.getTipo().getId().equals(TipoProduto.UNIDADE_VARIAVEL)) {
			gerenciadorPerifericos.getCmos().gravar(CMOS.QUANTIDADE_ITEM, gerenciadorPerifericos.getCmos().ler(CMOS.QUANTIDADE_ITEM_NECESSARIO_PARA_SEPARACAO) );
		} else {
			gerenciadorPerifericos.getCmos().gravar(CMOS.QUANTIDADE_ITEM, BigDecimal.ONE);
		}
	
		return ALTERNATIVA_1;
	}
}
