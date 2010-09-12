package com.infinity.datamarket.pdv.op;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.autorizador.AutorizacaoException;
import com.infinity.datamarket.autorizador.AutorizadorServerRemote;
import com.infinity.datamarket.autorizador.DadosAutorizacaoCartaoProprio;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.StringUtil;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.ImpressoraFiscalException;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.tef.RespostaOperacaoTEF;
import com.infinity.datamarket.pdv.util.ServerConfig;
import com.infinity.datamarket.pdv.util.ServiceLocator;

public class OpImprimeConfirmaAutorizacaoCartao extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		
		try{

			RespostaOperacaoTEF  respostaTEF  = (RespostaOperacaoTEF )gerenciadorPerifericos.getCmos().ler("respostaSolicitacao");
			if (respostaTEF != null) {
				imprimeComprovante(respostaTEF.getLinhasComprovantePrincipal(), gerenciadorPerifericos);
			}
			
		}catch(ImpressoraFiscalException e){
			e.printStackTrace();
			gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
			try {
				gerenciadorPerifericos.esperaVolta();
			} catch (AppException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return ALTERNATIVA_2;

		}catch(Exception e){
			e.printStackTrace();
			gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
			try {
				gerenciadorPerifericos.esperaVolta();
			} catch (AppException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return ALTERNATIVA_2;
		}
		return ALTERNATIVA_1;
	}
	
	private void imprimeComprovante(String[] linhas, GerenciadorPerifericos ger) throws ImpressoraFiscalException{
		StringBuffer buffer = new StringBuffer();
		System.out.println("Imprime cupom");
		for (int i = 0; i < linhas.length; i++) {
			System.out.println(linhas[i]);
			buffer.append(StringUtil.centraliza("CARTÃO PRÓPRIO", 48));
		}
		System.out.println("FIM - Imprime cupom");

		ger.getImpressoraFiscal().imprimeRelatorioGerencial(buffer.toString());
		ger.getImpressoraFiscal().finalizaRelatorioGerencial();
	}
	
}
