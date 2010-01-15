package com.infinity.datamarket.av.op;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.operacao.ConstantesOperacao;
import com.infinity.datamarket.comum.operacao.EventoOperacaoItemRegistrado;
import com.infinity.datamarket.comum.operacao.EventoOperacaoItemRegistradoSeparacao;
import com.infinity.datamarket.comum.operacao.Operacao;
import com.infinity.datamarket.comum.operacao.OperacaoPedido;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ConjuntoEventoOperacao;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpAVSalvarPedidoSeparado extends Mic {

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){		

		OperacaoPedido pedido = (OperacaoPedido)gerenciadorPerifericos.getCmos().ler(CMOS.OPERACAO_PEDIDO);

		try{
			

			ConjuntoEventoOperacao listaEvento = new ConjuntoEventoOperacao();

			Collection collItensPedidoSeparados = (List<EventoOperacaoItemRegistrado>)gerenciadorPerifericos.getCmos().ler(CMOS.COLL_EVENTO_OPERACAO_ITEM_REGISTRADO_PEDIDO);
			if (collItensPedidoSeparados != null && !collItensPedidoSeparados.isEmpty()) {
				Iterator it = collItensPedidoSeparados.iterator();
				while(it.hasNext()) {
					EventoOperacaoItemRegistrado evento = null;
					Object obj = (Object) it.next();
					if (obj instanceof EventoOperacaoItemRegistradoSeparacao) {
						evento = ((EventoOperacaoItemRegistradoSeparacao)obj).getEventoOperacaoItemRegistrado();
					} else {
						evento = (EventoOperacaoItemRegistrado)obj;
					}

					evento.getProdutoOperacaoItemRegistrado().getPk().setNumeroEvento(evento.getPk().getNumeroEvento());
					evento.getProdutoOperacaoItemRegistrado().getPk().setLoja(evento.getPk().getLoja());
					evento.getProdutoOperacaoItemRegistrado().getPk().setId(evento.getPk().getId());

					listaEvento.add(evento);
				}
			}
			
			pedido.setEventosOperacao(listaEvento);
			
			PropertyFilter filter = new PropertyFilter();
			filter.setTheClass(Operacao.class);
			filter.addProperty("pk.id", pedido.getPk().getId());
			filter.addProperty("pk.loja", pedido.getPk().getLoja());
			
			Collection coll = Fachada.getInstancia().consultarOperacao(filter);
			
			if (coll != null && coll.size() != 0) {
				OperacaoPedido pedidoJaCadastrado = (OperacaoPedido) coll.iterator().next(); 

				RepositoryManagerHibernateUtil.getInstancia().currentSession().evict(pedidoJaCadastrado);

				getFachadaPDV().excluirOperacao(pedidoJaCadastrado);	
			}

			pedido.setStatus(ConstantesOperacao.SEPARADO);
			getFachadaPDV().inserirOperacaoES(pedido);

			return ALTERNATIVA_1;

		}catch(Exception e){

			e.printStackTrace();
			gerenciadorPerifericos.getDisplay().setMensagem("Erro");
			try {
				gerenciadorPerifericos.esperaVolta();
			} catch (AppException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return ALTERNATIVA_2;
		}
	}
}