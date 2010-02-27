package com.infinity.datamarket.av.op;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.infinity.datamarket.av.gui.telas.TelaAVInicial;
import com.infinity.datamarket.comum.componente.Componente;
import com.infinity.datamarket.comum.operacao.ConstantesEventoOperacao;
import com.infinity.datamarket.comum.operacao.EventoOperacaoItemRegistrado;
import com.infinity.datamarket.comum.operacao.EventoOperacaoPK;
import com.infinity.datamarket.comum.operacao.ProdutoOperacaoItemRegistrado;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOSArquivo;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.util.MensagensAV;

public class OpAvAdicionaItemPedido extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		Logger logger = Logger.getLogger(OpAvAdicionaItemPedido.class);

		try {

	        Loja loja  = (Loja)gerenciadorPerifericos.getCmos().ler(CMOSArquivo.LOJA);

	        Componente componente = (Componente)gerenciadorPerifericos.getCmos().ler(CMOSArquivo.COMPONENTE);

	        gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "EMBRANCO"));
			Produto produto = (Produto)gerenciadorPerifericos.getCmos().ler(CMOS.PRODUTO_ATUAL);
			BigDecimal quantidade = (BigDecimal)gerenciadorPerifericos.getCmos().ler(CMOS.QUANTIDADE_ITEM);
			BigDecimal descontoItem = (BigDecimal)gerenciadorPerifericos.getCmos().ler(CMOS.DESCONTO);
			BigDecimal valItem = (BigDecimal)gerenciadorPerifericos.getCmos().ler(CMOS.VALOR_ITEM_PEDIDO);
			BigDecimal valTotalPedido = (BigDecimal)gerenciadorPerifericos.getCmos().ler(CMOS.VALOR_TOTAL_PEDIDO);
			BigDecimal descontoTotalPedido = (BigDecimal)gerenciadorPerifericos.getCmos().ler(CMOS.VALOR_TOTAL_DESCONTO_PEDIDO);
			BigDecimal valorTotalItem = valItem.subtract(descontoItem).multiply(quantidade).setScale(2, BigDecimal.ROUND_DOWN);
			if (descontoItem == null) {
				descontoItem = BigDecimal.ZERO;
			}
			if (valTotalPedido == null) {
				valTotalPedido = BigDecimal.ZERO;
			}
			if (descontoTotalPedido == null) {
				descontoTotalPedido = BigDecimal.ZERO;
			}
			if (valorTotalItem == null && valorTotalItem.doubleValue() <= 0 ) {

				gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Valor Total Invalido"));
				gerenciadorPerifericos.esperaVolta();

				return ALTERNATIVA_2;
			}

			boolean tem = false;
			List<EventoOperacaoItemRegistrado> coll = (List<EventoOperacaoItemRegistrado>)gerenciadorPerifericos.getCmos().ler(CMOS.COLL_EVENTO_OPERACAO_ITEM_REGISTRADO_PEDIDO);
			if (coll == null ) {
				coll = new ArrayList<EventoOperacaoItemRegistrado>();
			}
			EventoOperacaoItemRegistrado evento = null;
			Iterator<EventoOperacaoItemRegistrado> it = coll.iterator();
			while(it.hasNext()) {
				evento = it.next();
				if (evento.getProdutoOperacaoItemRegistrado().getCodigoExterno().equals(produto.getCodigoExterno())) {
					tem = true;
					break;
				}
			}
			if (tem && evento.getDesconto().equals(descontoItem) &&  evento.getProdutoOperacaoItemRegistrado().getPrecoPraticado().equals(valItem)) {
				evento.setPreco(evento.getPreco().add(valorTotalItem));
				evento.setQuantidade(evento.getQuantidade().add(quantidade));
			} else {

				
//				String descricaoProduto = produto.getDescricaoCompacta(); 
//				String descricaoValorItem = "R$ " + valItem.toString();
//				String descricaoDescontoItem = "R$ " + descontoItem.toString();
//				String descricaoValorTotalItem = "R$ " + valorTotalItem.toString();
	
				valTotalPedido = valTotalPedido.add(valItem.subtract(descontoItem).multiply(quantidade));
				valTotalPedido = valTotalPedido.setScale(2, BigDecimal.ROUND_DOWN);
	
				descontoTotalPedido = descontoTotalPedido.add(descontoItem.multiply(quantidade));
				descontoTotalPedido = descontoTotalPedido.setScale(2, BigDecimal.ROUND_DOWN);

	//			TelaAVInicial tela = (TelaAVInicial) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_AV_INICIAL);
	//			tela.adicionarRegistroTabela(descricaoProduto, descricaoValorItem, quantidade + "", descricaoDescontoItem, descricaoValorTotalItem);
	//			tela.setCampoTotal("R$ " + valTotalPedido);
	//			tela.setCampoTotalDesconto("R$ " + descontoTotalPedido);
	//			gerenciadorPerifericos.atualizaTela(tela);
	
				gerenciadorPerifericos.getCmos().gravar(CMOS.PRODUTO_ATUAL,null);
				gerenciadorPerifericos.getCmos().gravar(CMOS.QUANTIDADE_ITEM,null);
				gerenciadorPerifericos.getCmos().gravar(CMOS.DESCONTO,null);
				gerenciadorPerifericos.getCmos().gravar(CMOS.VALOR_TOTAL_PEDIDO, valTotalPedido);
				gerenciadorPerifericos.getCmos().gravar(CMOS.VALOR_TOTAL_DESCONTO_PEDIDO, descontoTotalPedido);
	
				ProdutoOperacaoItemRegistrado prodOpItemReg = new ProdutoOperacaoItemRegistrado();
				
				prodOpItemReg.setPk(new EventoOperacaoPK());
				prodOpItemReg.getPk().setLoja(loja.getId().intValue());
				int numeroEevento = coll.size();
				prodOpItemReg.getPk().setNumeroEvento(numeroEevento++);

				prodOpItemReg.setIdProduto(produto.getId().intValue());
				logger.info("inserirItemPedido:: prodOpItemReg.getIdProduto()--> "+prodOpItemReg.getIdProduto());
				prodOpItemReg.setCodigoExterno(produto.getCodigoExterno());
				logger.info("inserirItemPedido:: prodOpItemReg.getCodigoExterno()--> "+prodOpItemReg.getCodigoExterno());
				prodOpItemReg.setDescricaoCompleta(produto.getDescricaoCompleta());			
				logger.info("inserirItemPedido:: prodOpItemReg.getDescricaoCompleta()--> "+prodOpItemReg.getDescricaoCompleta());
				prodOpItemReg.setImpostoImpressora(produto.getImposto().getImpostoImpressora());
				logger.info("inserirItemPedido:: prodOpItemReg.getImpostoImpressora()--> "+prodOpItemReg.getImpostoImpressora());
				prodOpItemReg.setPercentual(produto.getImposto().getPercentual());
				logger.info("inserirItemPedido:: prodOpItemReg.getPercentual()--> "+prodOpItemReg.getPercentual());
				prodOpItemReg.setPrecoPadrao(produto.getPrecoPadrao());
				logger.info("inserirItemPedido:: prodOpItemReg.getPrecoPadrao()--> "+prodOpItemReg.getPrecoPadrao());
				
				prodOpItemReg.setPrecoPraticado(valItem);
				logger.info("inserirItemPedido:: prodOpItemReg.getPrecoPraticado()--> "+prodOpItemReg.getPrecoPraticado());
				prodOpItemReg.setTipoProduto(produto.getTipo().getId());
				logger.info("inserirItemPedido:: prodOpItemReg.getTipoProduto()--> "+prodOpItemReg.getTipoProduto());
				prodOpItemReg.setUnidade(produto.getUnidade().getAbreviacao());
				logger.info("inserirItemPedido:: prodOpItemReg.getUnidade()--> "+prodOpItemReg.getUnidade());
	
				BigDecimal lucro = null;
				if (produto.getPrecoCompra() != null){
					logger.info("inserirItemPedido:: produto.getPrecoCompra()--> "+produto.getPrecoCompra());
					lucro = valorTotalItem.subtract(produto.getPrecoCompra().multiply(quantidade));
				}
				logger.info("inserirItemPedido:: lucro--> "+lucro);
	
				EventoOperacaoItemRegistrado evOpItemReg = 
					new EventoOperacaoItemRegistrado(prodOpItemReg.getPk(),
							  						 ConstantesEventoOperacao.EVENTO_OPERACAO_ITEM_REGISTRADO,
							  						 new Date(),
							  						 valorTotalItem,
							  						 quantidade,
							  						 descontoItem,
							  						 lucro,
							  						 prodOpItemReg);
				
				evOpItemReg.setAcao(EventoOperacaoItemRegistrado.ITEM_INSERIDO);
	
				coll.add(evOpItemReg);
				
				gerenciadorPerifericos.getCmos().gravar(CMOS.COLL_EVENTO_OPERACAO_ITEM_REGISTRADO_PEDIDO,coll);
			
			}

			return ALTERNATIVA_1;

		} catch (Exception e) {

			logger.info("inserirItemPedido:: ERRO --> " + e.getMessage());
			e.printStackTrace();

			return ALTERNATIVA_2;
		}
	}
}
