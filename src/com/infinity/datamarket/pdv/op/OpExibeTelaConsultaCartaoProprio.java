package com.infinity.datamarket.pdv.op;


import com.infinity.datamarket.autorizador.DadosConsultaCartaoProprio;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.gui.telas.TelaConsultaCartaoProprio;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpExibeTelaConsultaCartaoProprio extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		TelaConsultaCartaoProprio tela = (TelaConsultaCartaoProprio) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_CONSULTA_CARTAO_PROPRIO);
		DadosConsultaCartaoProprio dados = (DadosConsultaCartaoProprio) gerenciadorPerifericos.getCmos().ler(CMOS.DADOS_CONSULTA_CARTAO_PROPRIO);
		tela.setNome(dados.getNome());
		tela.setValor(dados.getValorDebito());
		gerenciadorPerifericos.atualizaTela(tela);
		return ALTERNATIVA_1;
	}
}