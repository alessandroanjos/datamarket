package com.infinity.datamarket.pdv.op;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ConcentradorParametro;
import com.infinity.datamarket.comum.util.Parametro;
import com.infinity.datamarket.comum.util.Util;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;

public class OpImprimeRecibo extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		
		Parametro parametro = ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.IMPRIME_RECIBO);
		if (parametro != null && Boolean.valueOf(parametro.getValor()).booleanValue()){
			try{
				gerenciadorPerifericos.getDisplay().setMensagem("Deseja Imprimir Recibo?");
				EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 0);
				String idUsu = null;
				if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
				
					gerenciadorPerifericos.getDisplay().setMensagem("Aguarde...");
					
					TransacaoVenda transVenda = (TransacaoVenda) gerenciadorPerifericos.getCmos().ler(CMOS.TRANSACAO_VENDA_ATUAL);
					try {

						ByteArrayOutputStream out = new ByteArrayOutputStream();
						getFachadaPDV().gerarReciboVenda(transVenda, out);
						String caminho = Util.getDirCorrente() + "/";
						File dir = new File(caminho);
						if (!dir.exists()){
							dir.mkdir();
						}
						String nomeArquivo = caminho+"RECIBO_VENDA_"+ transVenda.getPk().getLoja()+transVenda.getPk().getComponente()+
						transVenda.getPk().getDataTransacao().getTime()+transVenda.getPk().getNumeroTransacao()+".pdf";						
						FileOutputStream f = new FileOutputStream(nomeArquivo);
						f.write(out.toByteArray());
						f.flush();
						f.close();
						Runtime.getRuntime().exec("cmd /c"+nomeArquivo);
					} catch (Exception e) {
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
