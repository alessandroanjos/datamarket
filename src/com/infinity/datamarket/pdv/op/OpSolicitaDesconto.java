package com.infinity.datamarket.pdv.op;

import java.math.BigDecimal;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.componente.Componente;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.util.ConcentradorParametro;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;

public class OpSolicitaDesconto extends Mic{



	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		try{
			Usuario usu = (Usuario) gerenciadorPerifericos.getCmos().ler(CMOS.AUTORIZADOR_ATUAL);
			if (usu.getPerfil().getPercentualDesconto().compareTo(BigDecimal.ZERO) > 0){
			
				String retornoTipo = "0";
				while(!retornoTipo.equals("1") && !retornoTipo.equals("2")){
					gerenciadorPerifericos.getDisplay().setMensagem("Desc : 1-Percentual 2-Valor");
					EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 1);
					if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
						retornoTipo = entrada.getDado();
					}else{
						return ALTERNATIVA_2;
					}
				}
	
				Produto prod = (Produto) gerenciadorPerifericos.getCmos().ler(CMOS.PRODUTO_ATUAL);
				BigDecimal desc = null;
				BigDecimal preco = null;
				if (prod.getPrecoPromocional() != null){
					desc = prod.getPrecoPadrao().subtract(prod.getPrecoPromocional());
					preco = prod.getPrecoPromocional();
				}else{
					preco = prod.getPrecoPadrao();
				}
				
				BigDecimal precoOriginal = preco;
	
				String percentual = null;
				if (retornoTipo.equals("1")){
					gerenciadorPerifericos.getDisplay().setMensagem("Desconto Percentual");
					EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{10,27},Display.MASCARA_PERCENTUAL, 5);
					if (entrada.getTeclaFinalizadora() == 10){
						percentual = entrada.getDado();
						BigDecimal perc = new BigDecimal(percentual);
						BigDecimal precoDesc = preco.multiply(perc);
						precoDesc = precoDesc.divide(new BigDecimal(100), BigDecimal.ROUND_DOWN);
						precoDesc = precoDesc.setScale(2,BigDecimal.ROUND_DOWN);
						preco = preco.subtract(precoDesc);
						preco = preco.setScale(2,BigDecimal.ROUND_DOWN);
					}else{
						return ALTERNATIVA_2;
					}
				}
	
				String valor = null;
				if (retornoTipo.equals("2")){
					gerenciadorPerifericos.getDisplay().setMensagem("Desconto Valor");
					EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{10,27},Display.MASCARA_MONETARIA, 5);
					if (entrada.getTeclaFinalizadora() == 10){
						valor = entrada.getDado();
						BigDecimal val = new BigDecimal(valor);
						preco = preco.subtract(val);
					}else{
						return ALTERNATIVA_2;
					}
				}
				if (new BigDecimal(0).compareTo(preco) > 0){
					gerenciadorPerifericos.getDisplay().setMensagem("Desconto Inválido");
					gerenciadorPerifericos.esperaVolta();
					return ALTERNATIVA_2;
				}
				
				//calcula o valor de desconto maximo para o perfil do usuario

//				BigDecimal qtdProduto = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.QUANTIDADE_ITEM); 
				BigDecimal descontoItem = precoOriginal.subtract(preco);//.multiply(qtdProduto).setScale(2,BigDecimal.ROUND_DOWN);
//				descontoItem = descontoItem.setScale(2,BigDecimal.ROUND_DOWN);
				gerenciadorPerifericos.getCmos().gravar(CMOS.DESCONTO, descontoItem);
				
				BigDecimal perc = usu.getPerfil().getPercentualDesconto();
				BigDecimal precoDesc = precoOriginal.multiply(perc);
				precoDesc = precoDesc.divide(new BigDecimal(100), BigDecimal.ROUND_DOWN);
				precoDesc = precoDesc.setScale(2,BigDecimal.ROUND_DOWN);
				precoOriginal = precoOriginal.subtract(precoDesc);
				precoOriginal = precoOriginal.setScale(2,BigDecimal.ROUND_DOWN);
				
				if (precoOriginal.compareTo(preco) > 0){
					gerenciadorPerifericos.getDisplay().setMensagem("Desconto não permitido");
					gerenciadorPerifericos.esperaVolta();
					return ALTERNATIVA_2;
				}
			
		        int idComponente = ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.COMPONENTE).getValorInteiro();
		        Componente componente = Fachada.getInstancia().consultarComponentePorId(new Long(idComponente));

				if (componente.getTipoComponente() == Componente.TIPO_COMPONENTE_PDV) {
					prod.setPrecoPromocional(preco);
				}
				gerenciadorPerifericos.getCmos().gravar(CMOS.PRODUTO_ATUAL, prod);
				
			}else{
				gerenciadorPerifericos.getDisplay().setMensagem("Desconto não permitido");
				gerenciadorPerifericos.esperaVolta();
				return ALTERNATIVA_2;
			}
	
		}catch(Exception e){
			e.printStackTrace();
			return ALTERNATIVA_2;
		}





		return ALTERNATIVA_1;
	}
}
