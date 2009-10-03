package com.infinity.datamarket.av.op;

import com.infinity.datamarket.av.gui.telas.TelaAVInicial;
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
			gerenciadorPerifericos.getDisplay().setMensagem("Digite a Quantidade");
	
			EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 4);
			String quantidade = null;
			if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
				quantidade = entrada.getDado();
			}else{
				return ALTERNATIVA_2;
			}
	
			if ("".equals(entrada.getDado()) || Integer.parseInt(quantidade) == 0) {
				quantidade = "1";
			}
	
			TelaAVInicial tela = (TelaAVInicial) gerenciadorPerifericos.getCmos().ler(CMOS.TELA_ATUAL);
			tela.setCampoQuantidade("" + quantidade);
			gerenciadorPerifericos.atualizaTela(tela);
	
	
			gerenciadorPerifericos.getCmos().gravar(CMOS.QUANTIDADE_ITEM, Integer.parseInt(quantidade));
	
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
