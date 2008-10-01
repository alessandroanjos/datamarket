package com.infinity.datamarket.pdv.mic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.infinity.datamarket.comum.transacao.TransacaoPagamento;
import com.infinity.datamarket.comum.transacao.TransacaoPagamentoCartaoProprio;
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

public class MicImprimeReciboPagamento extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		
		Parametro parametro = ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.IMPRIME_RECIBO_PAGAMENTO);
		if (Boolean.valueOf(parametro.getValor()).booleanValue()){
			try{
				gerenciadorPerifericos.getDisplay().setMensagem("Deseja Imprimir Recibo?");
				EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 0);
				String idUsu = null;
				if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
				
				
					gerenciadorPerifericos.getDisplay().setMensagem("Aguarde...");
										
					TransacaoPagamento transPagamento = (TransacaoPagamento) gerenciadorPerifericos.getCmos().ler(CMOS.TRANSACAO_PAGAMENTO_ATUAL);
					
					if (transPagamento instanceof TransacaoPagamentoCartaoProprio){
						TransacaoPagamentoCartaoProprio transPagamentoCartaoProprio = (TransacaoPagamentoCartaoProprio) transPagamento; 
						try {
							OutputStream out = getFachadaPDV().gerarReciboPagamentoCliente(transPagamentoCartaoProprio);							
							String caminho = "c:\\pdv\\temp\\";
							File dir = new File(caminho);
							if (!dir.exists()){
								dir.mkdir();
							}
							String nomeArquivo = caminho+"RECIBO_PAGAMENTO_"+ transPagamento.getPk().getLoja()+transPagamento.getPk().getComponente()+
							transPagamento.getPk().getDataTransacao().getTime()+transPagamento.getPk().getNumeroTransacao()+".pdf";
							ByteArrayOutputStream bout = (ByteArrayOutputStream) out;
							FileOutputStream f = new FileOutputStream(nomeArquivo);
							f.write(bout.toByteArray());
							f.flush();
							f.close();
							Runtime.getRuntime().exec("cmd /c"+nomeArquivo);
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				}
				
			} catch (AppException e) {
				e.printStackTrace();
			}
		}
		return ALTERNATIVA_1;
	}

}
