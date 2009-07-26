package com.infinity.datamarket.pdv.op;

import java.math.BigDecimal;

import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.usuario.Vendedor;
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

public class OpSolicitaVendedor extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		Parametro parametro = ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.PEDE_VENDEDOR);
		if (Boolean.valueOf(parametro.getValor()).booleanValue()){
			try{
				gerenciadorPerifericos.getDisplay().setMensagem("Código do Vendedor");
				EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 6);
				String idUsu = null;
				if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
					idUsu = entrada.getDado();
					if ("".equals(idUsu)){
						gerenciadorPerifericos.getCmos().gravar(CMOS.VENDEDOR_ATUAL, null);
						return ALTERNATIVA_1;
					}else{
						try{
							Usuario u = getFachadaPDV().consultarUsuarioPorId(new Long(idUsu));
							if (u instanceof Vendedor){
								Vendedor vendedor = (Vendedor) u;
								Parametro atualizaComissaoVendedor = ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.ATUALIZA_COMISSAO_VENDEDOR);
								if (Boolean.valueOf(atualizaComissaoVendedor.getValor()).booleanValue()){
									gerenciadorPerifericos.getDisplay().setMensagem("Alterar Comissão Vendedor?");
									EntradaDisplay entradaConfirmaComissao = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 0);
									if (entradaConfirmaComissao.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
										gerenciadorPerifericos.getDisplay().setMensagem("Nova Comissão");
										EntradaDisplay entradaComissao = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_PERCENTUAL, 5);
										if (entradaComissao.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
											String percentual = entradaComissao.getDado();
											BigDecimal perc = new BigDecimal(percentual);											
											vendedor.setComissao(perc);																									
										}
									}
								}
								gerenciadorPerifericos.getCmos().gravar(CMOS.VENDEDOR_ATUAL, vendedor);
							}else{
								gerenciadorPerifericos.getDisplay().setMensagem("Usuario não é vendedor");
								gerenciadorPerifericos.esperaVolta();
								return ALTERNATIVA_2;
							}
						}catch(ObjectNotFoundException e){
							gerenciadorPerifericos.getDisplay().setMensagem("Usuário não encontrado");
							gerenciadorPerifericos.esperaVolta();
							return ALTERNATIVA_2;
						}
					}
					return ALTERNATIVA_1;
				}else{
					return ALTERNATIVA_2;
				}
			}catch(AppException e){
				return ALTERNATIVA_2;
			}
		}else{
			return ALTERNATIVA_1;
		}
	}

}
