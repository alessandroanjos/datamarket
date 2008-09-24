package com.infinity.datamarket.pdv.mic;

import java.math.BigDecimal;

import com.infinity.datamarket.autorizador.DadosConsultaCartaoProprio;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;

public class MicSolicitaValorPagamentoCartaoProprio extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		try{
			DadosConsultaCartaoProprio dados = (DadosConsultaCartaoProprio) gerenciadorPerifericos.getCmos().ler(CMOS.DADOS_CONSULTA_CARTAO_PROPRIO);			
			a: while(true){
				gerenciadorPerifericos.getDisplay().setMensagem("Valor");
				EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_MONETARIA, 7);
				if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
					String valor = entrada.getDado();
					BigDecimal valorPagamento = new BigDecimal(valor);
					if (dados.getValorDebito().compareTo(valorPagamento) <= 0 || valorPagamento.compareTo(BigDecimal.ZERO) <= 0){
						gerenciadorPerifericos.getDisplay().setMensagem("Valor Inválido");
						gerenciadorPerifericos.esperaVolta();
						continue;
					}else{						
						b: while(true){
							gerenciadorPerifericos.getDisplay().setMensagem("Desconto");
							EntradaDisplay entrada2 = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_MONETARIA, 7);
							if (entrada2.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
								String desc = entrada2.getDado();
								BigDecimal valorDesconto = new BigDecimal(desc);
								if (dados.getValorDebito().compareTo(valorDesconto) <= 0 || valorPagamento.compareTo(valorDesconto) <= 0){
									gerenciadorPerifericos.getDisplay().setMensagem("Desconto Inválido");
									gerenciadorPerifericos.esperaVolta();
									continue b;
								}else{
									c: while(true){
										gerenciadorPerifericos.getDisplay().setMensagem("Acréssimo");
										EntradaDisplay entrada3 = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_MONETARIA, 7);
										if (entrada3.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
											String acressimo = entrada3.getDado();
											BigDecimal valorAcressimo = new BigDecimal(acressimo);
											gerenciadorPerifericos.getCmos().gravar(CMOS.DESCONTO,valorDesconto);
											gerenciadorPerifericos.getCmos().gravar(CMOS.ACRESSIMO,valorAcressimo);
											valorPagamento = valorPagamento.add(valorAcressimo).subtract(valorDesconto);
											gerenciadorPerifericos.getCmos().gravar(CMOS.SUB_TOTAL, valorPagamento);
											gerenciadorPerifericos.getCmos().gravar(CMOS.TOTAL, valorPagamento);
											return ALTERNATIVA_1;				
										}else{
											continue a;
										}
									}
									
								}				
							}else{
								continue a;
							}
						}
					}				
				}else{
					return ALTERNATIVA_2;
				}
			}			
		}catch(AppException e){
			return ALTERNATIVA_2;
		}
	}

}
