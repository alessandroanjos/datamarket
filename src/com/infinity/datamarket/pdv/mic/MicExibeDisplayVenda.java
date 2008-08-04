package com.infinity.datamarket.pdv.mic;

import java.math.BigDecimal;
import java.util.Iterator;

import com.infinity.datamarket.comum.transacao.EventoItemRegistrado;
import com.infinity.datamarket.comum.transacao.EventoTransacao;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.comum.util.StringUtil;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class MicExibeDisplayVenda extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		
		TransacaoVenda transVenda = (TransacaoVenda) gerenciadorPerifericos.getCmos().ler(CMOS.TRANSACAO_VENDA_ATUAL);
		
		if (transVenda != null && transVenda.getEventosTransacao() != null){
			Iterator i = transVenda.getEventosTransacao().iterator();
			while(i.hasNext()){
				EventoTransacao ev = (EventoTransacao) i.next();
				if (ev instanceof EventoItemRegistrado){
					EventoItemRegistrado eir = (EventoItemRegistrado) ev;
					String desc = eir.getProdutoItemRegistrado().getDescricaoCompleta();
					BigDecimal valor = eir.getPreco();
					
					String strPreco = StringUtil.numeroToString(valor, 2, 0, ",", ".", true);
					int tamPreco = strPreco.length();
					int tamDesc = desc.length();
					
					if (tamDesc > (27-tamPreco)){
						desc = desc.substring(0,27-tamPreco-1);
					}
					
					String linha = StringUtil.completaStringCentro(desc+" ",strPreco , 27, ' ');
					gerenciadorPerifericos.getDisplay().setMensagem(linha);
				}
			}
		}else{
			gerenciadorPerifericos.getDisplay().setMensagem("Código Produto");
		}
		return ALTERNATIVA_1;
	}
}
