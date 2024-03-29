package com.infinity.datamarket.av.op;

import java.math.BigDecimal;

import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpAvSolicitaDescontoValorProduto extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
//
//		try{
//
//			TelaAVInicial tela = (TelaAVInicial) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_AV_INICIAL);
//			tela.setCampoDesconto("");
//			gerenciadorPerifericos.atualizaTela(tela);
//
//			Produto produto = (Produto)gerenciadorPerifericos.getCmos().ler(CMOS.PRODUTO_ATUAL);
//			BigDecimal quantidade = (BigDecimal)gerenciadorPerifericos.getCmos().ler(CMOS.QUANTIDADE_ITEM);
//			BigDecimal precoUnitario = (BigDecimal)gerenciadorPerifericos.getCmos().ler(CMOS.VALOR_ITEM_PEDIDO);
//
			BigDecimal desconto = BigDecimal.ZERO;
//			BigDecimal descontoInicial = BigDecimal.ZERO;
//			if (produto.getPrecoPadrao().compareTo(BigDecimal.ZERO) != 0 && produto.getPrecoPromocional() != null && produto.getPrecoPromocional().compareTo(BigDecimal.ZERO) > 0) {
//				desconto = produto.getPrecoPadrao().subtract(produto.getPrecoPromocional()).multiply(quantidade);
//			}
//			descontoInicial = desconto;
//			
//			while (true) {	
//				if (descontoInicial.doubleValue() > 0) {
//					gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Pre�o Desconto Item R$ " + descontoInicial.doubleValue());	
//				} else {
//					gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Pre�o Desconto Item ");
//				}
//				EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_MONETARIA, 6);
//				if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
//					if ("".equals(entrada.getDado())) {
//						desconto = descontoInicial;
//						break;
//					} else {
//						desconto = new BigDecimal(entrada.getDado());
//						if (desconto.doubleValue() < 0 ) {
//							gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Desconto Invalido [Volta]");
//							gerenciadorPerifericos.esperaVolta();
//						} else {
//							break;
//						}
//					}
//				} else {
//					return ALTERNATIVA_2;
//				}
//			}
//
			gerenciadorPerifericos.getCmos().gravar(CMOS.DESCONTO, desconto);
//	
//		}catch (ObjetoInexistenteException e) {
//			gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Produto n�o Encontrado");
//			try {
//				gerenciadorPerifericos.esperaVolta();
//			} catch (AppException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			return ALTERNATIVA_2;
//		}catch (AppException e) {
//			return ALTERNATIVA_2;
//		}
//	
		
		return ALTERNATIVA_1;
	}
}
