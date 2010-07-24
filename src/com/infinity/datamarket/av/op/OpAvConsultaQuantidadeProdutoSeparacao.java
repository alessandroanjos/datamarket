package com.infinity.datamarket.av.op;

import java.math.BigDecimal;

import com.infinity.datamarket.av.gui.telas.TelaAVInicial;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.produto.TipoProduto;
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

public class OpAvConsultaQuantidadeProdutoSeparacao extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){


		// se solicitar a quantidade tem que validar se nao utrapassou da necessidade
		// gerenciadorPerifericos.getCmos().ler(CMOS.QUANTIDADE_ITEM_NECESSARIO_PARA_SEPARACAO)

		BigDecimal quantidadeNecessaria = (BigDecimal)gerenciadorPerifericos.getCmos().ler(CMOS.QUANTIDADE_ITEM_NECESSARIO_PARA_SEPARACAO);
		
		TelaAVInicial tela = (TelaAVInicial) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_AV_INICIAL);
		tela.setCampoQuantidade("");
		tela.setCampoDesconto("");
		gerenciadorPerifericos.atualizaTela(tela);

		Produto produto = (Produto)gerenciadorPerifericos.getCmos().ler(CMOS.PRODUTO_ATUAL);

		BigDecimal quantidade = BigDecimal.ZERO;
		
		if (produto.getTipo().getId().equals(TipoProduto.UNIDADE_VARIAVEL)) {
			gerenciadorPerifericos.getCmos().gravar(CMOS.QUANTIDADE_ITEM, quantidadeNecessaria);
		} else {
			try {
				EntradaDisplay entrada = gerenciadorPerifericos.lerDados( new int[] { Tecla.CODIGO_ENTER}, Display.MASCARA_NUMERICA, 8);
				while (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER) {
					quantidade = new BigDecimal(entrada.getDado());
					if (quantidade.doubleValue() ==  BigDecimal.ZERO.doubleValue()) {
						gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Quantidade Invalida [ESC]"));
						gerenciadorPerifericos.esperaVolta();
						gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Digite a Quantidade"));
						entrada = gerenciadorPerifericos.lerDados( new int[] { Tecla.CODIGO_ENTER}, Display.MASCARA_NUMERICA, 8);
					} else if (quantidadeNecessaria.doubleValue() < quantidade.doubleValue()) {
						gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Quantidade Superior [ESC]"));
						gerenciadorPerifericos.esperaVolta();
						gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Digite a Quantidade"));
						entrada = gerenciadorPerifericos.lerDados( new int[] { Tecla.CODIGO_ENTER}, Display.MASCARA_NUMERICA, 8);

					} else {
						break;
					}
				}
			} catch (AppException e) {
				return ALTERNATIVA_2;
			}
		}
		gerenciadorPerifericos.getCmos().gravar(CMOS.QUANTIDADE_ITEM, BigDecimal.ONE);
		
	
		return ALTERNATIVA_1;
	}
}
