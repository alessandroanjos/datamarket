package com.infinity.datamarket.pdv.mic;

import net.sf.jasperreports.view.JasperViewer;

import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ConcentradorParametro;
import com.infinity.datamarket.comum.util.Parametro;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;

public class MicImprimeRecibo extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		
		Parametro parametro = ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.IMPRIME_RECIBO);
		if (Boolean.valueOf(parametro.getValor()).booleanValue()){
			try{
				gerenciadorPerifericos.getDisplay().setMensagem("Deseja Imprimir Recibo?");
				EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 0);
				String idUsu = null;
				if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
				
				
					gerenciadorPerifericos.getDisplay().setMensagem("Aguarde...");
					
					TransacaoVenda transVenda = (TransacaoVenda) gerenciadorPerifericos.getCmos().ler(CMOS.TRANSACAO_VENDA_ATUAL);
					
					JasperViewer viewer;
					try {
						viewer = getFachadaPDV().gerarReciboVenda(transVenda);
						viewer.show();
					} catch (AppException e) {
						e.printStackTrace();
					}
					
				}
				
			} catch (AppException e) {
				e.printStackTrace();
			}
		}
		return ALTERNATIVA_1;
	}

}
