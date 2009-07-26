package com.infinity.datamarket.pdv.op;


import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.transacao.EventoItemRegistrado;
import com.infinity.datamarket.comum.transacao.EventoTransacao;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.ImpressoraFiscalException;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpCancelaItem extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		EventoItemRegistrado item = (EventoItemRegistrado) gerenciadorPerifericos.getCmos().ler(CMOS.ITEM_CANCELADO);

		try {
			gerenciadorPerifericos.getImpressoraFiscal().cancelaItem(item.getPk().getNumeroEvento()+"");
		} catch (ImpressoraFiscalException e) {
			
			TransacaoVenda trans = (TransacaoVenda)gerenciadorPerifericos.getCmos().ler(CMOS.TRANSACAO_VENDA_ATUAL);
			Collection conj = trans.getEventosTransacao();
			Iterator i = conj.iterator();
			while(i.hasNext()){
				EventoTransacao evento = (EventoTransacao) i.next();
				if (evento instanceof EventoItemRegistrado){
					EventoItemRegistrado it = (EventoItemRegistrado) evento;
					if (it.getPk().equals(item.getPk())){
						it.setSituacao(EventoItemRegistrado.ATIVO);
					}
				}
			}
			gerenciadorPerifericos.getCmos().gravar(CMOS.TRANSACAO_VENDA_ATUAL,trans);
			gerenciadorPerifericos.getDisplay().setMensagem(e.getMessage());
			try{
				gerenciadorPerifericos.esperaVolta();
			}catch(Exception ex){
				return ALTERNATIVA_2;
			}
			return ALTERNATIVA_2;
		}
		
		return ALTERNATIVA_1;


	}
}