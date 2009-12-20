package com.infinity.datamarket.av.op;

import java.math.BigDecimal;

import com.infinity.datamarket.av.gui.telas.TelaAVInicial;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.produto.TipoProduto;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ObjetoInexistenteException;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;

public class OpAvConsultaQuantidadeProduto extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		try{

			TelaAVInicial tela = (TelaAVInicial) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_AV_INICIAL);
			tela.setCampoQuantidade("");
			tela.setCampoDesconto("");
			gerenciadorPerifericos.atualizaTela(tela);

			Produto produto = (Produto)gerenciadorPerifericos.getCmos().ler(CMOS.PRODUTO_ATUAL);

			BigDecimal quantidade = BigDecimal.ZERO;
			
			if (produto.getTipo().getId().equals(TipoProduto.UNIDADE_VARIAVEL)) {
				try {
					gerenciadorPerifericos.getDisplay().setMensagem(produto.getUnidade().getDescricaoDisplay());
					EntradaDisplay entrada = gerenciadorPerifericos.lerDados( new int[] { Tecla.CODIGO_ENTER, Tecla.CODIGO_VOLTA }, Display.MASCARA_QUANTIDADE, 8);
					if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER) {
						quantidade = new BigDecimal(entrada.getDado());
					} else {
						if (produto.getPrecoPadrao().compareTo(BigDecimal.ZERO) == 0) {

							return ALTERNATIVA_2;

						} else {
							gerenciadorPerifericos.getDisplay().setMensagem("Codigo do Produto");
							
							tela.setCampoQuantidade("");
							tela.setCampoCodigoProduto("");
							tela.setCampoDescricaoProduto("");
							tela.setCampoDesconto("");
							tela.setCampoValor("");
							gerenciadorPerifericos.atualizaTela(tela);

							return ALTERNATIVA_3;
						}
						
					}
					if (quantidade.doubleValue() <= 0) {
						gerenciadorPerifericos.getDisplay().setMensagem("Quantidade Inválida");
						gerenciadorPerifericos.esperaVolta();
						return ALTERNATIVA_2;
					}
				} catch (AppException e) {
					return ALTERNATIVA_2;
				}
			} else {
				gerenciadorPerifericos.getDisplay().setMensagem("Digite a Quantidade");
				EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 4);
				if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
					quantidade = new BigDecimal(entrada.getDado());
				}else{
					return ALTERNATIVA_2;
				}
				if ("".equals(entrada.getDado())) {
					quantidade = new BigDecimal("1");
				}
				if (quantidade.doubleValue() <= 0) {
					gerenciadorPerifericos.getDisplay().setMensagem("Quantidade Inválida");
					gerenciadorPerifericos.esperaVolta();
					return ALTERNATIVA_2;
				}
			}

			tela.setCampoQuantidade("" + quantidade);
			gerenciadorPerifericos.atualizaTela(tela);

			gerenciadorPerifericos.getCmos().gravar(CMOS.QUANTIDADE_ITEM, quantidade);

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
	
		return ALTERNATIVA_1;
	}
}
