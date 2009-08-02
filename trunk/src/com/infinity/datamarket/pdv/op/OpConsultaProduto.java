package com.infinity.datamarket.pdv.op;

import java.math.BigDecimal;

import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ObjetoInexistenteException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpConsultaProduto extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		String codigo = param.getParam();
		Produto prod = null;
		if (codigo == null || codigo.equals("") ){
			return ALTERNATIVA_2;
		}
		BigDecimal valor = BigDecimal.ZERO;
		String cod = new String(codigo);
		boolean flag = false;
		if (cod.length() == 13 && cod.startsWith("2")){
			codigo = new Integer(cod.substring(1, 5)).toString();
			valor = new BigDecimal(cod.substring(5, cod.length()-3)+"."+cod.substring(cod.length()-3, cod.length()));
			flag = true;
		}
		try{
			prod = getFachadaPDV().consultarProdutoPorCodigoExterno(codigo);
			if (flag){
				BigDecimal valorUnitario = null;
				if (prod.getPrecoPromocional() != null && prod.getPrecoPromocional().compareTo(BigDecimal.ZERO) > 0){
					valorUnitario = prod.getPrecoPromocional();
				}else{
					valorUnitario = prod.getPrecoPadrao();
				}
				BigDecimal quantidade = valor.divide(valorUnitario,BigDecimal.ROUND_DOWN);
				gerenciadorPerifericos.getCmos().gravar(CMOS.QUANTIDADE_ITEM, quantidade);
			}
		}catch (ObjetoInexistenteException e) {
			gerenciadorPerifericos.getDisplay().setMensagem("Produto n�o Encontrado");
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