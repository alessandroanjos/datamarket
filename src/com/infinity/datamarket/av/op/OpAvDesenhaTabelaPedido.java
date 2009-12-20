package com.infinity.datamarket.av.op;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import com.infinity.datamarket.av.gui.telas.TelaAVInicial;
import com.infinity.datamarket.comum.operacao.EventoOperacaoItemRegistrado;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;

public class OpAvDesenhaTabelaPedido extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		try{
			TelaAVInicial tela = (TelaAVInicial) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_AV_INICIAL);
			tela.zerarTabela();

			List<EventoOperacaoItemRegistrado> coll = (List<EventoOperacaoItemRegistrado>)gerenciadorPerifericos.getCmos().ler(CMOS.COLL_EVENTO_OPERACAO_ITEM_REGISTRADO_PEDIDO);
			if (coll == null || coll.size() ==0) {
				gerenciadorPerifericos.getDisplay().setMensagem("Sem Item para excluir");
				gerenciadorPerifericos.esperaVolta();

				tela.setCampoTotal("R$ 0.0");
				tela.setCampoTotalDesconto("R$ 0.0");

				gerenciadorPerifericos.atualizaTela(tela);

				return ALTERNATIVA_2;
			}

			BigDecimal valTotalPedido = BigDecimal.ZERO;
			BigDecimal descontoTotalPedido = BigDecimal.ZERO;

			Iterator<EventoOperacaoItemRegistrado> it = coll.iterator();
			while(it.hasNext()) {

				EventoOperacaoItemRegistrado evento = it.next();

				String descricaoProduto = evento.getProdutoOperacaoItemRegistrado().getDescricaoCompleta(); 
				String descricaoValorItem = "R$ " + evento.getProdutoOperacaoItemRegistrado().getPrecoPraticado();
				String descricaoDescontoItem = "R$ " + evento.getDesconto();
				String descricaoValorTotalItem = "R$ " + evento.getPreco();
				String quantidade = "R$ " + evento.getQuantidade();


				tela.adicionarRegistroTabela(descricaoProduto, descricaoValorItem, quantidade + "", descricaoDescontoItem, descricaoValorTotalItem);

				valTotalPedido = valTotalPedido.add(evento.getPreco());
				descontoTotalPedido = descontoTotalPedido.add(evento.getDesconto());
			}

			tela.setCampoTotal("R$ " + valTotalPedido);
			tela.setCampoTotalDesconto("R$ " + descontoTotalPedido);
			gerenciadorPerifericos.getCmos().gravar(CMOS.VALOR_TOTAL_PEDIDO, valTotalPedido);
			gerenciadorPerifericos.getCmos().gravar(CMOS.VALOR_TOTAL_DESCONTO_PEDIDO, descontoTotalPedido);

			gerenciadorPerifericos.atualizaTela(tela);

		}catch (AppException e) {
			return ALTERNATIVA_2;
		}

		return ALTERNATIVA_1;
	}
}