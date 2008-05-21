package com.infinity.datamarket.pdv.mic;

import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.pagamento.DadosCheque;
import com.infinity.datamarket.comum.pagamento.DadosChequePredatado;
import com.infinity.datamarket.comum.util.Util;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;

public class MicSolicitaDadosChequePredatado extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		
		String CPFCNPJ = null;
		String numeroChequeLido = null;
		String banco = null;
		String agencia = null;
		String conta = null;
		String numeroCheque = null;
		
		Collection dadosCheque = (Collection) gerenciadorPerifericos.getCmos().ler(CMOS.DADOS_CHEQUE_PRE);

		int qtdCheques = dadosCheque.size();
		
		int count = 0;
		
		Iterator i = dadosCheque.iterator();
		
		a: while(i.hasNext()){
			
			DadosChequePredatado dados = (DadosChequePredatado) i.next();
			count++;
			String strPre = count+"/"+qtdCheques+" ";
			try{
				while(CPFCNPJ == null || "".equals(CPFCNPJ)){
					gerenciadorPerifericos.getDisplay().setMensagem(strPre+"CPF/CNPJ");
					EntradaDisplay entrada1 = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 13);
					if (entrada1.getTeclaFinalizadora() == 10){
						CPFCNPJ = entrada1.getDado();
						if (!"".equals(CPFCNPJ)){
							if (Util.validacpf(CPFCNPJ) || Util.validaCnpj(CPFCNPJ)){
								while(numeroChequeLido == null){
									gerenciadorPerifericos.getDisplay().setMensagem(strPre+"Passe o Cheque");
									EntradaDisplay entrada2 = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 15);
									if (entrada2.getTeclaFinalizadora() == 10){
										numeroChequeLido = entrada2.getDado();
										if ("".equals(numeroChequeLido)){
											while(banco == null || "".equals(banco)){
												gerenciadorPerifericos.getDisplay().setMensagem(strPre+"Banco");
												EntradaDisplay entrada3 = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 3);
												if (entrada3.getTeclaFinalizadora() == 10){
													banco = entrada3.getDado();
													if (!"".equals(banco)){
														while(agencia == null || "".equals(agencia)){
															gerenciadorPerifericos.getDisplay().setMensagem(strPre+"Agencia");
															EntradaDisplay entrada4 = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 5);
															if (entrada4.getTeclaFinalizadora() == 10){
																agencia = entrada4.getDado();
																if (!"".equals(agencia)){
																	while(conta == null || "".equals(conta)){
																		gerenciadorPerifericos.getDisplay().setMensagem(strPre+"Conta");
																		EntradaDisplay entrada5 = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 5);
																		if (entrada5.getTeclaFinalizadora() == 10){
																			conta = entrada5.getDado();
																			if (!"".equals(conta)){
																				while(numeroCheque == null || "".equals(numeroCheque)){
																					gerenciadorPerifericos.getDisplay().setMensagem(strPre+"Numero Cheque");
																					EntradaDisplay entrada6 = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 8);
																					if (entrada6.getTeclaFinalizadora() == 10){
																						numeroCheque = entrada6.getDado();
																						if (!"".equals(numeroCheque)){																							
																							dados.setCPFCNPJ(CPFCNPJ);
																							dados.setAgencia(agencia);
																							dados.setBanco(banco);
																							dados.setConta(conta);
																							dados.setNumeroCheque(numeroCheque);	
																							CPFCNPJ = null;
																							numeroChequeLido = null;
																							banco = null;
																							agencia = null;
																							conta = null;
																							numeroCheque = null;
																							continue a;
																						}
																					}else{
																						conta = null;
																						break;
																					}
																				}
																			}
																		}else{
																			agencia = null;
																			break;
																		}
																	}
																}
															}else{
																banco = null;
																break;
															}
														}
													}
												}else{
													numeroChequeLido = null;
													break;
												}
											}
										}else{																
											dados.setCPFCNPJ(CPFCNPJ);
											dados.setNumeroChequeLido(numeroChequeLido);	
											CPFCNPJ = null;
											numeroChequeLido = null;
											banco = null;
											agencia = null;
											conta = null;
											numeroCheque = null;
											continue a;
										}
									}else{
										CPFCNPJ = null;
										break;
									}
								}
							}else{
								CPFCNPJ = null;
								gerenciadorPerifericos.getDisplay().setMensagem("CPF/CNPJ Inválido");
								gerenciadorPerifericos.esperaVolta();
							}
						}
					}else{
						if (count == 1){
							return ALTERNATIVA_2;
						}else{
							i = dadosCheque.iterator();
							count=0;
							continue a;
						}
					}
				}
			}catch(Exception e){
				return ALTERNATIVA_2;
			}
		}
		gerenciadorPerifericos.getCmos().gravar(CMOS.DADOS_CHEQUE,dadosCheque);
		return ALTERNATIVA_1;
	}
}
