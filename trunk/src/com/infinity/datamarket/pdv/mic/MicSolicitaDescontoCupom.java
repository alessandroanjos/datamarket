package com.infinity.datamarket.pdv.mic;

import java.math.BigDecimal;
import java.util.Iterator;

import com.infinity.datamarket.comum.transacao.EventoItemPagamento;
import com.infinity.datamarket.comum.transacao.EventoItemRegistrado;
import com.infinity.datamarket.comum.transacao.EventoTransacao;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;

public class MicSolicitaDescontoCupom extends Mic{



	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		try{
			
			Usuario usu = (Usuario) gerenciadorPerifericos.getCmos().ler(CMOS.AUTORIZADOR_ATUAL);
			if (usu.getPerfil().getPercentualDesconto().compareTo(BigDecimal.ZERO) > 0){
			
			
				TransacaoVenda transacao = (TransacaoVenda) gerenciadorPerifericos.getCmos().ler(CMOS.TRANSACAO_VENDA_ATUAL);
				
				if (transacao.getDescontoCupom() != null && transacao.getDescontoCupom().compareTo(new BigDecimal(0)) > 0){
					gerenciadorPerifericos.getDisplay().setMensagem("Desconto já Efetuado");
					gerenciadorPerifericos.esperaVolta();
					return ALTERNATIVA_1;
				}
				
				if (transacao.getEventosTransacao() != null){				
					Iterator i = transacao.getEventosTransacao().iterator();
					while(i.hasNext()){
						if (i.next() instanceof EventoItemPagamento){
							gerenciadorPerifericos.getDisplay().setMensagem("Pagamento já Efetuado");
							gerenciadorPerifericos.esperaVolta();
							return ALTERNATIVA_1;
						}					
					}
				}
				
				
				String retornoTipo = "0";
				while(!retornoTipo.equals("1") && !retornoTipo.equals("2")){
					gerenciadorPerifericos.getDisplay().setMensagem("Desc : 1-Percentual 2-Valor");
					EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 6);
					if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
						retornoTipo = entrada.getDado();
					}else{
						return ALTERNATIVA_1;
					}
				}
	
				
				BigDecimal valor = new BigDecimal(0);
				if (transacao != null && transacao.getEventosTransacao() != null){
					Iterator i = transacao.getEventosTransacao().iterator();
					while(i.hasNext()){
						EventoTransacao ev = (EventoTransacao) i.next();
						if (ev instanceof EventoItemRegistrado){
							EventoItemRegistrado eir = (EventoItemRegistrado) ev;
							if (eir.getSituacao().equals(EventoItemRegistrado.ATIVO)){						
								valor = valor.add(eir.getPreco());
							}	
						}
					}
				}
				
				BigDecimal valorOriginal = new BigDecimal(valor.doubleValue());
				
				BigDecimal valorDesc =  null;
	
				String percentual = null;
				if (retornoTipo.equals("1")){
					gerenciadorPerifericos.getDisplay().setMensagem("Desconto Percentual");
					EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{10,27},Display.MASCARA_PERCENTUAL, 5);
					if (entrada.getTeclaFinalizadora() == 10){
						percentual = entrada.getDado();
						BigDecimal perc = new BigDecimal(percentual);
						valorDesc = valor.multiply(perc);
						valorDesc = valorDesc.divide(new BigDecimal(100), BigDecimal.ROUND_DOWN);
						valorDesc = valorDesc.setScale(2, BigDecimal.ROUND_DOWN);
						valor = valor.subtract(valorDesc);
						valor = valor.setScale(2, BigDecimal.ROUND_DOWN);
					}else{
						return ALTERNATIVA_1;
					}
				}
	
				if (retornoTipo.equals("2")){
					gerenciadorPerifericos.getDisplay().setMensagem("Desconto Valor");
					EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{10,27},Display.MASCARA_MONETARIA, 5);
					if (entrada.getTeclaFinalizadora() == 10){
						valorDesc = new BigDecimal(entrada.getDado());
						valor = valor.subtract(valorDesc);
					}else{
						return ALTERNATIVA_1;
					}
				}
				if (new BigDecimal(0).compareTo(valor) > 0){
					gerenciadorPerifericos.getDisplay().setMensagem("Desconto Inválido");
					gerenciadorPerifericos.esperaVolta();
					return ALTERNATIVA_1;
				}
				
				//calcula o valor de desconto maximo para o perfil do usuario
				
				
				
				BigDecimal perc = usu.getPerfil().getPercentualDesconto();
				BigDecimal precoDesc = valorOriginal.multiply(perc);
				precoDesc = precoDesc.divide(new BigDecimal(100), BigDecimal.ROUND_DOWN);
				precoDesc = precoDesc.setScale(2,BigDecimal.ROUND_DOWN);
				valorOriginal = valorOriginal.subtract(precoDesc);
				valorOriginal = valorOriginal.setScale(2,BigDecimal.ROUND_DOWN);
				
				if (valorOriginal.compareTo(valor) > 0){
					gerenciadorPerifericos.getDisplay().setMensagem("Desconto não permitido");
					gerenciadorPerifericos.esperaVolta();
					return ALTERNATIVA_1;
				}
			
			
				
				transacao.setDescontoCupom(valorDesc);
				gerenciadorPerifericos.getCmos().gravar(CMOS.TRANSACAO_VENDA_ATUAL, transacao);
				gerenciadorPerifericos.getCmos().gravar(CMOS.SUB_TOTAL, valor);
				gerenciadorPerifericos.getCmos().gravar(CMOS.TOTAL, valor);
				
			}else{
				gerenciadorPerifericos.getDisplay().setMensagem("Desconto não permitido");
				gerenciadorPerifericos.esperaVolta();
				return ALTERNATIVA_1;
			}
	
		}catch(Exception e){
			return ALTERNATIVA_1;
		}





		return ALTERNATIVA_1;
	}
}
