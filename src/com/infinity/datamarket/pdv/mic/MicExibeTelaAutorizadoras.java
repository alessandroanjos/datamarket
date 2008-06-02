package com.infinity.datamarket.pdv.mic;


import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import com.infinity.datamarket.comum.pagamento.Autorizadora;
import com.infinity.datamarket.comum.pagamento.FormaRecebimento;
import com.infinity.datamarket.comum.pagamento.PlanoPagamento;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Constantes;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.gui.telas.TelaAutorizadoras;
import com.infinity.datamarket.pdv.gui.telas.TelaPlanos;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class MicExibeTelaAutorizadoras extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		TelaAutorizadoras tela = (TelaAutorizadoras) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_AUTORIZADORAS);

		FormaRecebimento forma = (FormaRecebimento) gerenciadorPerifericos.getCmos().ler(CMOS.FORMA_RECEBIMENTO_ATUAL);
		
		Collection autorizadoras;
		try {
			PropertyFilter filter = new PropertyFilter();
			filter.setTheClass(Autorizadora.class);
			filter.addProperty("situacao", Constantes.STATUS_ATIVO);
			autorizadoras = getFachadaPDV().consultarAutorizadora(filter);
		} catch (AppException e) {
			e.printStackTrace();
			gerenciadorPerifericos.getDisplay().setMensagem("Não Ha Cartões Válidos");
			try {
				gerenciadorPerifericos.esperaVolta();
			} catch (AppException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return ALTERNATIVA_2;
		}
		if (autorizadoras == null || autorizadoras.size() == 0){
			gerenciadorPerifericos.getDisplay().setMensagem("Não Ha Cartões Válidos");
			try {
				gerenciadorPerifericos.esperaVolta();
			} catch (AppException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return ALTERNATIVA_2;
		}
		
		tela.limparCartoes();
		tela.setForma(forma.getDescricao());

		Iterator i = autorizadoras.iterator();
		
		while(i.hasNext()){
			Autorizadora a = (Autorizadora) i.next();
			tela.addCartao(a.getId().intValue(), a.getDescricao());
		}

		gerenciadorPerifericos.atualizaTela(tela);
		return ALTERNATIVA_1;
	}
	
}