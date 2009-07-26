package com.infinity.datamarket.pdv.op;

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
import com.infinity.datamarket.pdv.util.ServerConfig;
import com.infinity.datamarket.pdv.util.ServiceLocator;

public class OpImprimeConfirmaAutorizacaoCartaoProprio extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		
		
		
		try{
			Collection c = (Collection) gerenciadorPerifericos.getCmos().ler(CMOS.DADOS_AUTORIZACOES_CARTAO_PROPRIO);
			
			if (c != null && c.size() > 0){
				gerenciadorPerifericos.getDisplay().setMensagem("Aguarde...");
				AutorizadorServerRemote remote = (AutorizadorServerRemote) ServiceLocator.getJNDIObject(ServerConfig.AUTORIZADOR_SERVER_JNDI);
				if (remote == null){
					gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
					gerenciadorPerifericos.esperaVolta();
					return ALTERNATIVA_2;
				}				
				Iterator i = c.iterator();
				while(i.hasNext()){
					DadosAutorizacaoCartaoProprio dados = (DadosAutorizacaoCartaoProprio) i.next();
					imprimeComprovante(dados, gerenciadorPerifericos);
					imprimeComprovante(dados, gerenciadorPerifericos);
				}
				i = c.iterator();
				while(i.hasNext()){
					DadosAutorizacaoCartaoProprio dados = (DadosAutorizacaoCartaoProprio) i.next();
					remote.confirmaTransacaoCartaoProprio(new Long(dados.getAutrizacao()));
				}
			}
			
			gerenciadorPerifericos.getCmos().gravar(CMOS.DADOS_AUTORIZACOES_CARTAO_PROPRIO,new ArrayList());
								
		}catch(AutorizacaoException e){
			gerenciadorPerifericos.getDisplay().setMensagem(e.getMessage());
			try {
				gerenciadorPerifericos.esperaVolta();
			} catch (AppException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return ALTERNATIVA_2;
		}catch(ImpressoraFiscalException e){
			gerenciadorPerifericos.getDisplay().setMensagem(e.getMessage());
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
	
	private void imprimeComprovante(DadosAutorizacaoCartaoProprio dados, GerenciadorPerifericos ger) throws ImpressoraFiscalException{
		StringBuffer buffer = new StringBuffer();
		buffer.append(StringUtil.centraliza("CARTÃO PRÓPRIO", 48));
		buffer.append(StringUtil.centraliza(" ", 48));
		buffer.append(StringUtil.completaStringCentro("Aut:", dados.getAutrizacao(),48, ' '));
		buffer.append(StringUtil.completaStringCentro("Valor:", StringUtil.numeroToString(dados.getValor(), 2, 0, ",", ".", true), 48, ' '));
		buffer.append(StringUtil.centraliza(" ", 48));
		buffer.append(StringUtil.centraliza("RECONHEÇO QUE PAGUEI A IMPORTÂNCIA ACIMA", 48));
		buffer.append(StringUtil.centraliza(" ", 48));
		buffer.append(StringUtil.centraliza(" ", 48));
		buffer.append(StringUtil.completaString("_", 48, '_',true));
		buffer.append(StringUtil.centraliza(dados.getNome(), 48));
		buffer.append(StringUtil.centraliza(" ", 48));
		
		
		ger.getImpressoraFiscal().imprimeRelatorioGerencial(buffer.toString());
		ger.getImpressoraFiscal().finalizaRelatorioGerencial();
	}
	
}
