package com.infinity.datamarket.av.op;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.infinity.datamarket.av.gui.telas.TelaAVInicial;
import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.operacao.ConstantesOperacao;
import com.infinity.datamarket.comum.operacao.EventoOperacaoItemRegistrado;
import com.infinity.datamarket.comum.operacao.EventoOperacaoItemRegistradoSeparacao;
import com.infinity.datamarket.comum.operacao.Operacao;
import com.infinity.datamarket.comum.operacao.OperacaoPedido;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpAVIncluirPedidoEmSeparacao extends Mic {
//
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){		

		try{

			double quantidadeSeparada = 0d;
			
			OperacaoPedido pedido = (OperacaoPedido)gerenciadorPerifericos.getCmos().ler(CMOS.OPERACAO_PEDIDO);

			if (pedido.getCliente() != null && pedido.getCliente().getNomeCliente() !=null ) {
				TelaAVInicial tela = (TelaAVInicial) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_AV_INICIAL);
				tela.setCampoCliente(pedido.getCliente().getNomeCliente());
				gerenciadorPerifericos.atualizaTela(tela);

			}
			List<EventoOperacaoItemRegistrado> collEvent = new ArrayList<EventoOperacaoItemRegistrado>();

			Collection collEventos = pedido.getEventosOperacao();
			Iterator it = collEventos.iterator();
			boolean primeiro = true;
			while (it.hasNext()) {
				Object obj = it.next();
				
				EventoOperacaoItemRegistrado evento = (EventoOperacaoItemRegistrado)obj;

				BigDecimal quantidadeInicial = BigDecimal.ZERO;
				
				EventoOperacaoItemRegistradoSeparacao eventoItemRegistradoSeparacao = new EventoOperacaoItemRegistradoSeparacao(evento, quantidadeInicial);
				
				collEvent.add(eventoItemRegistradoSeparacao);
			}

			gerenciadorPerifericos.getCmos().gravar(CMOS.COLL_EVENTO_OPERACAO_ITEM_REGISTRADO_PEDIDO,collEvent);
			
			PropertyFilter filter = new PropertyFilter();
			filter.setTheClass(Operacao.class);
			filter.addProperty("pk.id", pedido.getPk().getId());
			filter.addProperty("pk.loja", pedido.getPk().getLoja());
			
			Collection coll = Fachada.getInstancia().consultarOperacao(filter);
			
			if (coll != null && coll.size() != 0) {
				OperacaoPedido pedidoJaCadastrado = (OperacaoPedido) coll.iterator().next(); 
				getFachadaPDV().excluirOperacao(pedidoJaCadastrado);	
			}

			pedido.setStatus(ConstantesOperacao.EM_SEPARACAO);
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