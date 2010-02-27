package com.infinity.datamarket.av.op;

import com.infinity.datamarket.av.gui.telas.TelaAVInicial;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.op.OpSolicitaConsultaProdutoDesconto;
import com.infinity.datamarket.pdv.util.MensagensAV;

public class OpAvSolicitaConsultaProdutoDesconto extends OpSolicitaConsultaProdutoDesconto {
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		TelaAVInicial tela = (TelaAVInicial) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_AV_INICIAL);
		tela.setCampoQuantidade("");
		tela.setCampoCodigoProduto("");
		tela.setCampoDescricaoProduto("");
		tela.setCampoDesconto("");
		tela.setCampoValor("");
		gerenciadorPerifericos.atualizaTela(tela);

		int retorno = super.exec(gerenciadorPerifericos, param);
		if(retorno == ALTERNATIVA_2) {
			return ALTERNATIVA_2;
		}
		Produto prod = (Produto)gerenciadorPerifericos.getCmos().ler(CMOS.PRODUTO_ATUAL);

		tela.setCampoCodigoProduto(prod.getCodigoExterno());
		tela.setCampoDescricaoProduto(prod.getDescricaoCompleta());
		gerenciadorPerifericos.atualizaTela(tela);
		
		gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Codigo do Produto"));
		gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Digite a Quantidade"));
		
		
		return retorno;
		
	}
}
