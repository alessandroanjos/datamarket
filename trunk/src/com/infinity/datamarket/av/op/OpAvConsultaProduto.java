package com.infinity.datamarket.av.op;

import java.math.BigDecimal;

import com.infinity.datamarket.av.gui.telas.TelaAVInicial;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ObjetoInexistenteException;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpAvConsultaProduto extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		TelaAVInicial tela = (TelaAVInicial) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_AV_INICIAL);
		tela.setCampoQuantidade("");
		tela.setCampoCodigoProduto("");
		tela.setCampoDescricaoProduto("");
		tela.setCampoDesconto("");
		tela.setCampoValor("");
		gerenciadorPerifericos.atualizaTela(tela);
		
		if (param == null || param.getParam() == null ) {
			return ALTERNATIVA_2;
		}
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

		tela.setCampoCodigoProduto(codigo);
		tela.setCampoDescricaoProduto(prod.getDescricaoCompleta());
		tela.setCampoDescricaoProduto(prod.getDescricaoCompleta());
		gerenciadorPerifericos.atualizaTela(tela);
		
		gerenciadorPerifericos.getDisplay().setMensagem("Codigo do Produto");
		gerenciadorPerifericos.getDisplay().setMensagem("Digite a Quantidade");
		
		return ALTERNATIVA_1;
	}
}