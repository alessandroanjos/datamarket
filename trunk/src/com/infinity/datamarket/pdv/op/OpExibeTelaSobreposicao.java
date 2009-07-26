package com.infinity.datamarket.pdv.op;


import java.math.BigDecimal;

import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.gui.telas.TelaConsulta;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;

public class OpExibeTelaSobreposicao extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		TelaConsulta tela = (TelaConsulta) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_CONSULTA);

		Produto prod = (Produto) gerenciadorPerifericos.getCmos().ler(CMOS.PRODUTO_ATUAL);
		
		
		tela.setProduto(prod.getDescricaoCompacta());
		tela.setPrecoPadrao(prod.getPrecoPadrao());
		
		if (prod.getPrecoPromocional() != null){
			tela.setPrecoPromocional(prod.getPrecoPromocional());
			tela.setDesconto(prod.getPrecoPadrao().subtract(prod.getPrecoPromocional()));
		}else{
			tela.setPrecoPromocional(BigDecimal.ZERO);
			tela.setDesconto(BigDecimal.ZERO);
		}
		gerenciadorPerifericos.atualizaTela(tela);
		
		
		
		BigDecimal valor = BigDecimal.ZERO;
		
		try {
			while(valor.compareTo(BigDecimal.ZERO) == 0){
				gerenciadorPerifericos.getDisplay().setMensagem("Novo Preço");
				EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_MONETARIA, 10);
				if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
					valor = new BigDecimal(entrada.getDado());
					if (valor.compareTo(BigDecimal.ZERO) == 0){
						gerenciadorPerifericos.getDisplay().setMensagem("Preço Inválido");
						gerenciadorPerifericos.esperaVolta();
						continue;
					}			
				
					prod.setPrecoPromocional(null);
					prod.setPrecoPadrao(valor);
					
					gerenciadorPerifericos.getCmos().gravar(CMOS.PRODUTO_ATUAL,prod);
				}else{
					TransacaoVenda transVenda = (TransacaoVenda) gerenciadorPerifericos.getCmos().ler(CMOS.TRANSACAO_VENDA_ATUAL);
					if (transVenda == null || transVenda.getEventosTransacao() == null){
						return ALTERNATIVA_2;
					}else{
						return ALTERNATIVA_1;
					}
				}
			}
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ALTERNATIVA_3;
		
	}
	
}