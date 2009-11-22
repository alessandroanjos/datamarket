package com.infinity.datamarket.pdv.op;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.util.ConcentradorParametro;
import com.infinity.datamarket.comum.util.Parametro;
import com.infinity.datamarket.comum.util.Util;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpImprimeMovimentoDia extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		
		Parametro parametro = ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.IMPRIME_MOVIMENTO_DIA);
		if (parametro != null && Boolean.valueOf(parametro.getValor()).booleanValue()){
		
			gerenciadorPerifericos.getDisplay().setMensagem("Aguarde...");
							
			try {
				Date dataMovimento = (Date) gerenciadorPerifericos.getCmos().ler(CMOS.DATA_MOVIMENTO);
				ByteArrayOutputStream out = (ByteArrayOutputStream) getFachadaPDV().gerarRelatorioAnaliticoVendas(gerenciadorPerifericos.getCodigoLoja(), dataMovimento, dataMovimento);
//				String caminho = Fachada.getInstancia().consultarParametro("DIR_PADRAO_RECIBOS").getValor();//"c:\\pdv\\temp\\";
				String caminho = Util.getDirCorrente();
				File dir = new File(caminho);
				if (!dir.exists()){
					dir.mkdir();
				}
				String nomeArquivo = caminho+"MOVIMENTO_VENDA_"+ dataMovimento.getTime() +".pdf";						
				FileOutputStream f = new FileOutputStream(nomeArquivo);
				f.write(out.toByteArray());
				f.flush();
				f.close();
				Runtime.getRuntime().exec("cmd /c"+nomeArquivo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		return ALTERNATIVA_1;
	}

}
