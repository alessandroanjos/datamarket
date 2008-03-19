package com.infinity.datamarket.pdv.mic;


import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.gui.telas.TelaMenssagem;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class MicExibeTelaDisponivel extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		TelaMenssagem tela = (TelaMenssagem) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_MENSAGEM);
		tela.setMenssagem("Obrigado Pela PreferÍncia");
		gerenciadorPerifericos.atualizaTela(tela);
		return ALTERNATIVA_1;
	}
}