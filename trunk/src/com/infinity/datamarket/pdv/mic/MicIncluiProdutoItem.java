package com.infinity.datamarket.pdv.mic;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.produto.TipoProduto;
import com.infinity.datamarket.comum.transacao.ConstantesEventoTransacao;
import com.infinity.datamarket.comum.transacao.EventoItemRegistrado;
import com.infinity.datamarket.comum.transacao.EventoTransacaoPK;
import com.infinity.datamarket.comum.transacao.ProdutoItemRegistrado;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ConjuntoEventoTransacao;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;

public class MicIncluiProdutoItem extends Mic {

	public int exec(GerenciadorPerifericos gerenciadorPerifericos,
			ParametroMacroOperacao param) {

		Produto produto = (Produto) gerenciadorPerifericos.getCmos().ler(
				CMOS.PRODUTO_ATUAL);
		TransacaoVenda transVenda = (TransacaoVenda) gerenciadorPerifericos
				.getCmos().ler(CMOS.TRANSACAO_VENDA_ATUAL);

		EventoTransacaoPK pk = new EventoTransacaoPK(transVenda.getPk()
				.getLoja(), transVenda.getPk().getComponente(), transVenda
				.getPk().getNumeroTransacao(), transVenda.getPk()
				.getDataTransacao());

		BigDecimal quantidade = (BigDecimal) gerenciadorPerifericos.getCmos()
				.ler(CMOS.QUANTIDADE_ITEM);

		if (produto.getTipo().getId().equals(TipoProduto.UNIDADE_VARIAVEL)) {
			try {
				gerenciadorPerifericos.getDisplay().setMensagem(
						produto.getUnidade().getDescricaoDisplay());
				EntradaDisplay entrada = gerenciadorPerifericos.lerDados(
						new int[] { Tecla.CODIGO_ENTER, Tecla.CODIGO_VOLTA },
						Display.MASCARA_QUANTIDADE, 8);
				if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER) {
					quantidade = new BigDecimal(entrada.getDado());
				} else {
					if (transVenda.getEventosTransacao() != null
							&& transVenda.getEventosTransacao().size() > 0) {
						return ALTERNATIVA_3;
					}
					return ALTERNATIVA_2;
				}
				if (quantidade.compareTo(BigDecimal.ZERO) == 0) {
					gerenciadorPerifericos.getDisplay().setMensagem(
							"Quantidade Inválida");
					gerenciadorPerifericos.esperaVolta();
					if (transVenda.getEventosTransacao() != null
							&& transVenda.getEventosTransacao().size() > 0) {
						return ALTERNATIVA_3;
					}
					return ALTERNATIVA_2;

				}
			} catch (AppException e) {
				return ALTERNATIVA_2;
			}
		}

		BigDecimal preco = new BigDecimal(0);
		BigDecimal lucro = null;
		BigDecimal precoUnitario = new BigDecimal(0);
		BigDecimal desconto = new BigDecimal(0);
		if (produto.getPrecoPadrao().compareTo(BigDecimal.ZERO) == 0) {
			try {
				gerenciadorPerifericos.getDisplay().setMensagem(
						"Digite o Preço");
				EntradaDisplay entrada = gerenciadorPerifericos.lerDados(
						new int[] { Tecla.CODIGO_ENTER, Tecla.CODIGO_VOLTA },
						Display.MASCARA_MONETARIA, 10);
				if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER) {
					precoUnitario = new BigDecimal(entrada.getDado());
					preco = precoUnitario.multiply(quantidade);
				} else {
					if (transVenda.getEventosTransacao() != null
							&& transVenda.getEventosTransacao().size() > 0) {
						return ALTERNATIVA_3;
					}
					return ALTERNATIVA_2;
				}
			} catch (AppException e) {
				return ALTERNATIVA_2;
			}
		} else {
			if (produto.getPrecoPromocional() != null) {
				precoUnitario = produto.getPrecoPromocional();
				preco = produto.getPrecoPromocional().multiply(quantidade);
				desconto = produto.getPrecoPadrao().subtract(
						produto.getPrecoPromocional()).multiply(quantidade);
			} else {
				precoUnitario = produto.getPrecoPadrao();
				preco = produto.getPrecoPadrao().multiply(quantidade);
			}
		}

		// caso o preco calculado seja igual a zero, é transanformado para um
		// centavo.

		if (preco.setScale(2, BigDecimal.ROUND_DOWN).compareTo(BigDecimal.ZERO) == 0) {
			try {
				gerenciadorPerifericos.getDisplay().setMensagem(
						"Quantidade Inválida");
				gerenciadorPerifericos.esperaVolta();
				if (transVenda.getEventosTransacao() != null
						&& transVenda.getEventosTransacao().size() > 0) {
					return ALTERNATIVA_3;
				}
				return ALTERNATIVA_2;
			} catch (AppException e) {
				return ALTERNATIVA_2;
			}
		}

		ProdutoItemRegistrado produtoItemRegistrado = new ProdutoItemRegistrado(
				pk, produto.getId().intValue(), produto.getCodigoExterno(),
				produto.getDescricaoCompleta(), produto.getPrecoPadrao(),
				precoUnitario, produto.getImposto().getImpostoImpressora(), produto.getImposto().getPercentual());

		produtoItemRegistrado.setTipoProduto(produto.getTipo().getId());
		
		if (produto.getPrecoCompra() != null){			
			lucro = preco.subtract(produto.getPrecoCompra().multiply(quantidade));
		}
		
		EventoItemRegistrado eventoItemRegistrado = new EventoItemRegistrado(
				pk, ConstantesEventoTransacao.EVENTO_ITEM_REGISTRADO,
				new Date(), quantidade, desconto, preco, lucro,produtoItemRegistrado);

		Collection eventos = transVenda.getEventosTransacao();

		if (eventos == null) {
			eventos = new ConjuntoEventoTransacao();
			transVenda.setEventosTransacao(eventos);
		}

		eventos.add(eventoItemRegistrado);
		
		gerenciadorPerifericos.getCmos().gravar(CMOS.ITEM_REGISTRADO,
				eventoItemRegistrado);

		gerenciadorPerifericos.getCmos().gravar(CMOS.TRANSACAO_VENDA_ATUAL,
				transVenda);
		gerenciadorPerifericos.getCmos().gravar(CMOS.QUANTIDADE_ITEM,
				BigDecimal.ONE);

		return ALTERNATIVA_1;
	}
}
