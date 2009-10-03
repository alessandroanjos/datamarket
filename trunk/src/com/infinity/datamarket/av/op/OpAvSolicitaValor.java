package com.infinity.datamarket.av.op;

import java.math.BigDecimal;

import com.infinity.datamarket.av.gui.telas.TelaAVInicial;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ObjetoInexistenteException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;

public class OpAvSolicitaValor extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		try{
			Produto prod = (Produto)gerenciadorPerifericos.getCmos().ler(CMOS.PRODUTO_ATUAL);
			
			if (prod.getPrecoPromocional().doubleValue() != 0) {
				gerenciadorPerifericos.getCmos().gravar(CMOS.VALOR_ITEM_PEDIDO, prod.getPrecoPromocional());

				TelaAVInicial tela = (TelaAVInicial) gerenciadorPerifericos.getCmos().ler(CMOS.TELA_ATUAL);
				tela.setCampoValor("R$ " + prod.getPrecoPromocional());
				gerenciadorPerifericos.atualizaTela(tela);

				return ALTERNATIVA_1;

			} else if (prod.getPrecoPadrao().doubleValue() != 0) {
				gerenciadorPerifericos.getCmos().gravar(CMOS.VALOR_ITEM_PEDIDO, prod.getPrecoPadrao());

				TelaAVInicial tela = (TelaAVInicial) gerenciadorPerifericos.getCmos().ler(CMOS.TELA_ATUAL);
				tela.setCampoValor("R$ " + prod.getPrecoPadrao());
				gerenciadorPerifericos.atualizaTela(tela);

				return ALTERNATIVA_1;

			} else {
				while (true) {	
					gerenciadorPerifericos.getDisplay().setMensagem("Digite o Valor");
					EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_MONETARIA, 6);
					if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
						if (!"".equals(entrada.getDado())) {
							BigDecimal valorItem = new BigDecimal(entrada.getDado());
							if (valorItem.doubleValue() == 0 ) {
								gerenciadorPerifericos.getDisplay().setMensagem("Valor Invalido [Volta]");
								gerenciadorPerifericos.esperaVolta();
							} else {
								gerenciadorPerifericos.getCmos().gravar(CMOS.VALOR_ITEM_PEDIDO, valorItem);

								TelaAVInicial tela = (TelaAVInicial) gerenciadorPerifericos.getCmos().ler(CMOS.TELA_ATUAL);
								tela.setCampoValor("R$ " + valorItem);
								gerenciadorPerifericos.atualizaTela(tela);

								return ALTERNATIVA_1;
							}
						} else {
							gerenciadorPerifericos.getDisplay().setMensagem("Valor Invalido [Volta]");
							gerenciadorPerifericos.esperaVolta();
						}
					} else {
						return ALTERNATIVA_2;
					}
				}
				
			}
		}catch (ObjetoInexistenteException e) {
			gerenciadorPerifericos.getDisplay().setMensagem("Produto não Encontrado");
			try {
				gerenciadorPerifericos.esperaVolta();
			} catch (AppException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return ALTERNATIVA_2;
		}catch (AppException e) {
			return ALTERNATIVA_2;
		}
	}
}
