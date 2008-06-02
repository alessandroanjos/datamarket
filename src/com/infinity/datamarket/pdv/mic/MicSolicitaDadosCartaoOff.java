package com.infinity.datamarket.pdv.mic;

import com.infinity.datamarket.comum.pagamento.DadosCartaoOff;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;

public class MicSolicitaDadosCartaoOff extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		
		String codigoAutorizadora = (String) gerenciadorPerifericos.getCmos().ler(CMOS.AUTORIZADORA_CARTAO_OFF);
		String numeroCartao = null;
		String quantidadeParcelas = null;
		String autorizacao = null;
		
		try{
			while(numeroCartao == null || "".equals(numeroCartao)){
				gerenciadorPerifericos.getDisplay().setMensagem("Número Cartão");
				EntradaDisplay entrada3 = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 20);
				if (entrada3.getTeclaFinalizadora() == 10){
					numeroCartao = entrada3.getDado();
					if (!"".equals(numeroCartao)){
						while(quantidadeParcelas == null || "".equals(quantidadeParcelas)){
							gerenciadorPerifericos.getDisplay().setMensagem("Parcelas");
							EntradaDisplay entrada4 = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 2);
							if (entrada4.getTeclaFinalizadora() == 10){
								quantidadeParcelas = entrada4.getDado();
								if (!"".equals(quantidadeParcelas)){
									while(autorizacao == null || "".equals(autorizacao)){
										gerenciadorPerifericos.getDisplay().setMensagem("Autorização");
										EntradaDisplay entrada5 = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 10);
										if (entrada5.getTeclaFinalizadora() == 10){
											autorizacao = entrada5.getDado();
											if (!"".equals(autorizacao)){
												DadosCartaoOff dados = new DadosCartaoOff();
												dados.setCodigoAutorizadora(codigoAutorizadora);
												dados.setAutorizacao(autorizacao);
												dados.setNumeroCartao(numeroCartao);
												dados.setQuantidadeParcelas(Integer.parseInt(quantidadeParcelas));
												gerenciadorPerifericos.getCmos().gravar(CMOS.DADOS_CARTAO_OFF,dados);
												return ALTERNATIVA_1;
											}
										}else{
											quantidadeParcelas = null;
											break;
										}
									}
								}
							}else{
								numeroCartao = null;
								break;
							}
						}
					}
				}else{
					numeroCartao = null;
					return ALTERNATIVA_2;
				}
			}
			return ALTERNATIVA_2;
		}catch(Exception e){
			return ALTERNATIVA_2;
		}
	}
}
