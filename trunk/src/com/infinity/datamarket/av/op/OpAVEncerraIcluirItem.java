package com.infinity.datamarket.av.op;

import com.infinity.datamarket.av.gui.telas.TelaAVInicial;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpAVEncerraIcluirItem extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){


		TelaAVInicial tela = (TelaAVInicial) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_AV_INICIAL);
		tela.setCampoCodigoProduto("");
		tela.setCampoDescricaoProduto("");
		tela.setCampoQuantidade("");
		tela.setCampoDesconto("");
		gerenciadorPerifericos.atualizaTela(tela);
		
		gerenciadorPerifericos.getCmos().gravar(CMOS.PRODUTO_ATUAL, null);
		gerenciadorPerifericos.getDisplay().setMensagem("Codigo do Produto");
		
		return ALTERNATIVA_1;
	}

}
