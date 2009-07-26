package com.infinity.datamarket.pdv.op;

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

public class OpSolicitaConsultaProdutoDesconto extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		String codigo=null;
		try{
			gerenciadorPerifericos.getDisplay().setMensagem("Desc: Codigo do Produto");
			EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 6);
			if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
				codigo = entrada.getDado();
			}else{
				return ALTERNATIVA_2;
			}
		}catch(Exception e){
			return ALTERNATIVA_2;
		}

		Produto prod = null;

		try{
			prod = getFachadaPDV().consultarProdutoPorCodigoExterno(codigo);
		}catch (ObjetoInexistenteException e) {
			try{
				gerenciadorPerifericos.getDisplay().setMensagem("Produto não Encontrado");
				gerenciadorPerifericos.esperaVolta();
			}catch (AppException ex) {
				return ALTERNATIVA_2;
			}
			return ALTERNATIVA_2;
		}catch (AppException e) {
			return ALTERNATIVA_2;
		}

		gerenciadorPerifericos.getCmos().gravar(CMOS.PRODUTO_ATUAL, prod);

		return ALTERNATIVA_1;
	}

}
