package com.infinity.datamarket.pdv.op;

import java.math.BigDecimal;
import java.util.Collection;

import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.produto.TipoProduto;
import com.infinity.datamarket.comum.transacao.EventoItemRegistrado;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.ImpressoraFiscal;
import com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.ImpressoraFiscalException;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpVendeItem extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		EventoItemRegistrado itemRegistrado = (EventoItemRegistrado) gerenciadorPerifericos.getCmos().ler(CMOS.ITEM_REGISTRADO);
		Produto produto = (Produto) gerenciadorPerifericos.getCmos().ler(CMOS.PRODUTO_ATUAL);
		try{
			BigDecimal precoUnitario = itemRegistrado.getPreco().divide(itemRegistrado.getQuantidade(), BigDecimal.ROUND_DOWN);
			BigDecimal descontoUnitario = itemRegistrado.getDesconto().divide(itemRegistrado.getQuantidade(), BigDecimal.ROUND_DOWN);
			gerenciadorPerifericos.getImpressoraFiscal().vendeItem(produto.getCodigoExterno(),
																   produto.getDescricaoCompleta(),
																   produto.getImposto().getPercentual().setScale(2).toString(),
																   produto.getTipo().getId().equals(TipoProduto.NORMAL)?"I":"F",
																   itemRegistrado.getQuantidade(),
																   produto.getUnidade().getAbreviacao(),
																   precoUnitario.add(descontoUnitario),
																   ImpressoraFiscal.DESCONTO_VALOR,
																   descontoUnitario.multiply(itemRegistrado.getQuantidade()));
		}catch(ImpressoraFiscalException e){
			TransacaoVenda transVenda = (TransacaoVenda) gerenciadorPerifericos.getCmos().ler(CMOS.TRANSACAO_VENDA_ATUAL);
			Collection c = transVenda.getEventosTransacao();
			c.remove(itemRegistrado);
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
