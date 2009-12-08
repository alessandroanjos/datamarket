package com.infinity.datamarket.pdv.op;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import com.infinity.datamarket.comum.operacao.EventoOperacaoItemRegistrado;
import com.infinity.datamarket.comum.operacao.OperacaoPedido;
import com.infinity.datamarket.comum.produto.TipoProduto;
import com.infinity.datamarket.comum.transacao.ConstantesEventoTransacao;
import com.infinity.datamarket.comum.transacao.EventoItemRegistrado;
import com.infinity.datamarket.comum.transacao.EventoTransacaoPK;
import com.infinity.datamarket.comum.transacao.ProdutoItemRegistrado;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.comum.util.ConjuntoEventoTransacao;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.ImpressoraFiscal;
import com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.ImpressoraFiscalException;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpIncluiVendeItemOperacaoPedido extends Mic{
	
	
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		
		OperacaoPedido pedido = (OperacaoPedido) gerenciadorPerifericos.getCmos().ler(
				CMOS.OPERACAO_PEDIDO);
		TransacaoVenda transVenda = (TransacaoVenda) gerenciadorPerifericos
				.getCmos().ler(CMOS.TRANSACAO_VENDA_ATUAL);

		transVenda.setCliente(pedido.getCliente());
		
		Collection eventosOperacao = pedido.getEventosOperacao();
		
		gerenciadorPerifericos.getDisplay().setMensagem("Aguarde...");
		
		Iterator i = eventosOperacao.iterator();
		
		while(i.hasNext()){
			
			
			EventoOperacaoItemRegistrado eoir = (EventoOperacaoItemRegistrado) i.next();
	
			Collection eventos = transVenda.getEventosTransacao();
	
			if (eventos == null) {
				eventos = new ConjuntoEventoTransacao();
				transVenda.setEventosTransacao(eventos);
			}
			
			EventoItemRegistrado eir = eventoOperacaoItemRergistradoToEventoItemRegistrado(eoir, transVenda);
			
			try{
				BigDecimal precoUnitario = eir.getPreco().divide(eir.getQuantidade(), BigDecimal.ROUND_DOWN);
				BigDecimal descontoUnitario = eir.getDesconto().divide(eir.getQuantidade(), BigDecimal.ROUND_DOWN);
				gerenciadorPerifericos.getImpressoraFiscal().vendeItem(eir.getProdutoItemRegistrado().getCodigoExterno(),
																	   eir.getProdutoItemRegistrado().getDescricaoCompleta(),
																	   eir.getProdutoItemRegistrado().getPercentual().setScale(2).toString(),
																	   eir.getProdutoItemRegistrado().getTipoProduto().equals(TipoProduto.NORMAL)?"I":"F",
																	   eir.getQuantidade(),
																	   eir.getProdutoItemRegistrado().getUnidade(),
																	   precoUnitario.add(descontoUnitario),
																	   ImpressoraFiscal.DESCONTO_VALOR,
																	   descontoUnitario.multiply(eir.getQuantidade()));
				
				eventos.add(eir);
				
			}catch(ImpressoraFiscalException e){
				
				gerenciadorPerifericos.getDisplay().setMensagem(e.getMessage());
				try{
					gerenciadorPerifericos.esperaVolta();
				}catch(Exception ex){
					return ALTERNATIVA_2;
				}
				return ALTERNATIVA_2;
			}	
			
			
		}
		Collection pedidos = transVenda.getPedidos();
		
		if (pedidos == null || pedidos.size() > 0){
			pedidos = new HashSet();
		}
		
		pedido.setTransacaoVenda(transVenda);
		
		pedidos.add(pedido);
		
		transVenda.setPedidos(pedidos); 
		
		//adiciona a operacao para que seja fechada no ES ao final da transacao
		Collection c = (Collection) gerenciadorPerifericos.getCmos().ler(CMOS.PK_OPERACOES);
		c.add(pedido);
		gerenciadorPerifericos.getCmos().gravar(CMOS.PK_OPERACOES,c);
		
		gerenciadorPerifericos.getCmos().gravar(CMOS.TRANSACAO_VENDA_ATUAL,
				transVenda);		

		return ALTERNATIVA_1;
	}
	
	private EventoItemRegistrado eventoOperacaoItemRergistradoToEventoItemRegistrado(EventoOperacaoItemRegistrado eoir,TransacaoVenda transVenda){
		EventoTransacaoPK pk = new EventoTransacaoPK(transVenda.getPk()
				.getLoja(), transVenda.getPk().getComponente(), transVenda
				.getPk().getNumeroTransacao(), transVenda.getPk()
				.getDataTransacao());
		
		
		ProdutoItemRegistrado produtoItemRegistrado = new ProdutoItemRegistrado(
				pk, eoir.getProdutoOperacaoItemRegistrado().getIdProduto(), eoir.getProdutoOperacaoItemRegistrado().getCodigoExterno(),
				eoir.getProdutoOperacaoItemRegistrado().getDescricaoCompleta(), eoir.getProdutoOperacaoItemRegistrado().getPrecoPadrao(),
				eoir.getProdutoOperacaoItemRegistrado().getPrecoPraticado(), eoir.getProdutoOperacaoItemRegistrado().getImpostoImpressora(), 
				eoir.getProdutoOperacaoItemRegistrado().getPercentual(), eoir.getProdutoOperacaoItemRegistrado().getTipoProduto(), eoir.getProdutoOperacaoItemRegistrado().getUnidade());
		
		EventoItemRegistrado eventoItemRegistrado = new EventoItemRegistrado(
				pk, ConstantesEventoTransacao.EVENTO_ITEM_REGISTRADO,
				new Date(), eoir.getQuantidade(), eoir.getDesconto(), eoir.getPreco(), eoir.getLucro(),produtoItemRegistrado);
		
		return eventoItemRegistrado;
	}

}
