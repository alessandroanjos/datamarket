package com.infinity.datamarket.pdv.mic;


import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import com.infinity.datamarket.comum.pagamento.FormaRecebimento;
import com.infinity.datamarket.comum.pagamento.PlanoPagamento;
import com.infinity.datamarket.comum.util.Constantes;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.gui.telas.TelaPlanos;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class MicExibeTelaPlanos extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		TelaPlanos tela = (TelaPlanos) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_PLANOS);

		FormaRecebimento forma = (FormaRecebimento) gerenciadorPerifericos.getCmos().ler(CMOS.FORMA_RECEBIMENTO_ATUAL);
		tela.limparPlanos();
		tela.setForma(forma.getDescricao());
		Collection c = forma.getPlanos();
		Iterator i = c.iterator();
		Date dataAtual = new Date();
		while(i.hasNext()){
			PlanoPagamento plano = (PlanoPagamento) i.next();
			if (plano.getStatus().equals(Constantes.STATUS_ATIVO)){
				if (dataAtual.compareTo(plano.getDataInicioValidade()) >= 0 && dataAtual.compareTo(plano.getDataFimValidade()) <= 0){
					tela.addPlano(plano.getId().intValue(), plano.getDescricao());
				}
			}
		}

		gerenciadorPerifericos.atualizaTela(tela);
		return ALTERNATIVA_1;
	}
}