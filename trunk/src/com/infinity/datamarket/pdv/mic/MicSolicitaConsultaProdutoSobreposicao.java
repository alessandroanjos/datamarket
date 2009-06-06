package com.infinity.datamarket.pdv.mic;

import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ObjetoInexistenteException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;

public class MicSolicitaConsultaProdutoSobreposicao extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		String codigo=null;
		try{

			gerenciadorPerifericos.getDisplay().setMensagem("Cons: Codigo do Produto");
			
			EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 6);
			if (entrada.getTeclaFinalizadora() == 10){
				codigo = entrada.getDado();
			}else{
				return ALTERNATIVA_2;
			}
			
			try{
			
				Produto prod = getFachadaPDV().consultarProdutoPorCodigoExterno(codigo);
			
				gerenciadorPerifericos.getCmos().gravar(CMOS.PRODUTO_ATUAL, prod);
				
			}catch (ObjetoInexistenteException e) {
				
				gerenciadorPerifericos.getDisplay().setMensagem("Produto não Encontrado");
				try {
					gerenciadorPerifericos.esperaVolta();
				} catch (AppException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return ALTERNATIVA_2;
				
			}
			
		}catch(Exception e){
			return ALTERNATIVA_2;
		}
		return ALTERNATIVA_1;
			
	}

}
