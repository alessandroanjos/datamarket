package com.infinity.datamarket.pdv.mic;

import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ObjetoInexistenteException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class MicConsultaProduto extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		String codigo = param.getParam();
		Produto prod = null;
		if (codigo == null || codigo.equals("") ){
			return ALTERNATIVA_2;
		}
		try{
			prod = getFachadaPDV().consultarProdutoPorCodigoExterno(codigo);
		}catch (ObjetoInexistenteException e) {
			gerenciadorPerifericos.getDisplay().setMensagem("Produto não Encontrado");
			try {
				gerenciadorPerifericos.esperaVolta();
			} catch (AppException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return ALTERNATIVA_2;
		}catch (AppException e) {
			return ALTERNATIVA_2;
		}

		gerenciadorPerifericos.getCmos().gravar(CMOS.PRODUTO_ATUAL, prod);

		return ALTERNATIVA_1;
	}

}
