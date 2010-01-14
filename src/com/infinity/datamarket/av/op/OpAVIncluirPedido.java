package com.infinity.datamarket.av.op;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.operacao.ConstantesOperacao;
import com.infinity.datamarket.comum.operacao.EventoOperacaoItemRegistrado;
import com.infinity.datamarket.comum.operacao.Operacao;
import com.infinity.datamarket.comum.operacao.OperacaoPedido;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpAVIncluirPedido extends Mic {
//
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){		

		try{

			OperacaoPedido pedido = (OperacaoPedido)gerenciadorPerifericos.getCmos().ler(CMOS.OPERACAO_PEDIDO);

			List<EventoOperacaoItemRegistrado> collEvent = new ArrayList<EventoOperacaoItemRegistrado>();

			Collection collEventos = pedido.getEventosOperacao();
			Iterator it = collEventos.iterator();
			while (it.hasNext()) {
				EventoOperacaoItemRegistrado evento = (EventoOperacaoItemRegistrado)it.next();
				collEvent.add(evento);
			}

			gerenciadorPerifericos.getCmos().gravar(CMOS.COLL_EVENTO_OPERACAO_ITEM_REGISTRADO_PEDIDO,collEvent);

//			try{
//				URL urlCon = new URL("http://" +
//						ServerConfig.HOST_SERVIDOR_ES +
//						":" +
//						ServerConfig.PORTA_SERVIDOR_ES +
//						"/" +
//						ServerConfig.CONTEXTO_SERVIDOR_ES +
//						"/" +
//						ServerConfig.ATUALIZA_OPERACAO_EM_SEPARACAO + "?idLoja=" + pedido.getPk().getLoja() + "&idPedido="+pedido.getPk().getId());
//				URLConnection huc1 = urlCon.openConnection();
//		
//				huc1.setAllowUserInteraction(true);						
//				
//				ObjectInputStream input = new ObjectInputStream(huc1.getInputStream());
//				Object obj = input.readObject();
//				if (obj instanceof String && "OK".equalsIgnoreCase(((String)obj))) {
//
//				} else  if (obj instanceof Exception){
//					((Exception)obj).printStackTrace();
//					
//					gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
//					gerenciadorPerifericos.esperaVolta();
//					return ALTERNATIVA_2;		
//				}
//			}catch(Exception e){
//				e.printStackTrace();
//				gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
//				try {
//					gerenciadorPerifericos.esperaVolta();
//				} catch (AppException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				return ALTERNATIVA_2;
//			}
			
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