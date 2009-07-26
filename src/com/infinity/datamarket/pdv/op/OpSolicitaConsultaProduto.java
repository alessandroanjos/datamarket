package com.infinity.datamarket.pdv.op;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.produto.TipoProduto;
import com.infinity.datamarket.comum.transacao.EventoItemRegistrado;
import com.infinity.datamarket.comum.transacao.EventoTransacao;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.StringUtil;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;

public class OpSolicitaConsultaProduto extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		String codigo=null;
		BigDecimal quantidade = null;
		try{

			try{
				quantidade = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.QUANTIDADE_ITEM);
			}catch (NumberFormatException e){
				quantidade = new BigDecimal(1);
			}
			if (quantidade.compareTo(BigDecimal.ONE) > 0){
				gerenciadorPerifericos.getDisplay().setMensagem("Canc: "+StringUtil.numeroToString(quantidade, 3, 0, ",", ".", true)+" x "+"Cod Produto");
			}else{
				gerenciadorPerifericos.getDisplay().setMensagem("Canc: Codigo do Produto");
			}
			EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 6);
			if (entrada.getTeclaFinalizadora() == 10){
				codigo = entrada.getDado();
			}else{
				return ALTERNATIVA_2;
			}
		}catch(Exception e){
			return ALTERNATIVA_2;
		}

		TransacaoVenda trans = (TransacaoVenda)gerenciadorPerifericos.getCmos().ler(CMOS.TRANSACAO_VENDA_ATUAL);

		Collection conj = trans.getEventosTransacao();

		if (validaCancelamento(conj)){
			Iterator i = conj.iterator();
			int qtdProdCanc = 0;
			boolean prodEncontrado = false;
			boolean qtdEncontrada = false;
			while(i.hasNext()){
				EventoTransacao evento = (EventoTransacao) i.next();
				if (evento instanceof EventoItemRegistrado){
					EventoItemRegistrado item = (EventoItemRegistrado) evento;
					if (item.getProdutoItemRegistrado().getCodigoExterno().equals(codigo) && item.getSituacao().equals(EventoItemRegistrado.ATIVO)){
						prodEncontrado = true;
						if (item.getProdutoItemRegistrado().getTipoProduto().equals(TipoProduto.UNIDADE_VARIAVEL)){
							try{
								gerenciadorPerifericos.getDisplay().setMensagem("Digite a quantidade");
								EntradaDisplay entradaQtd = gerenciadorPerifericos.lerDados(new int[] { Tecla.CODIGO_ENTER, Tecla.CODIGO_VOLTA },Display.MASCARA_QUANTIDADE, 8);
								if (entradaQtd.getTeclaFinalizadora() == Tecla.CODIGO_ENTER) {
									BigDecimal quantidadeTmp = new BigDecimal(entradaQtd.getDado());
									if (quantidadeTmp.compareTo(BigDecimal.ZERO) > 0){
										quantidade = quantidadeTmp;
									}else{
										gerenciadorPerifericos.getDisplay().setMensagem("Quantidade inválida");
										gerenciadorPerifericos.esperaVolta();
										return ALTERNATIVA_2;
									}
								}else{
									return ALTERNATIVA_2;
								}
							}catch(AppException e){
								return ALTERNATIVA_2;
							}
						}
						if (item.getQuantidade().equals(quantidade)){
							if (item.getProdutoItemRegistrado().getPrecoPadrao().compareTo(BigDecimal.ZERO) == 0){
								try{
									gerenciadorPerifericos.getDisplay().setMensagem("Digite o Preço");
									EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[] { Tecla.CODIGO_ENTER, Tecla.CODIGO_VOLTA },Display.MASCARA_MONETARIA, 10);
									if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER) {
										BigDecimal precoUnitario = new BigDecimal(entrada.getDado());
										if (precoUnitario.equals(item.getProdutoItemRegistrado().getPrecoPraticado())){
											gerenciadorPerifericos.getCmos().gravar(CMOS.ITEM_CANCELADO, item);
											item.setSituacao(EventoItemRegistrado.CANCELADO);
											qtdEncontrada = true;
											break;
										}
									}else{
										return ALTERNATIVA_2;
									}
								}catch(AppException e){
									return ALTERNATIVA_2;
								}
							}else{
 								gerenciadorPerifericos.getCmos().gravar(CMOS.ITEM_CANCELADO, item);
								item.setSituacao(EventoItemRegistrado.CANCELADO);
								qtdEncontrada = true;
								break;
							}
						}
					}
				}
			}
			if (prodEncontrado){
				if (!qtdEncontrada)
					try{
						gerenciadorPerifericos.getDisplay().setMensagem("Quantidade não vendida");
						gerenciadorPerifericos.esperaVolta();
						return ALTERNATIVA_2;
					}catch (AppException ex) {
						return ALTERNATIVA_2;
					}
			}else{
				try{
					gerenciadorPerifericos.getDisplay().setMensagem("Produto não vendido");
					gerenciadorPerifericos.esperaVolta();
					return ALTERNATIVA_2;
				}catch (AppException ex) {
					return ALTERNATIVA_2;
				}
			}
		}else{
			try{
				gerenciadorPerifericos.getDisplay().setMensagem("Cancelamento não Permitido");
				gerenciadorPerifericos.esperaVolta();
				return ALTERNATIVA_2;
			}catch (AppException ex) {
				return ALTERNATIVA_2;
			}
		}

		return ALTERNATIVA_1;
	}
	
	private boolean validaCancelamento(Collection c){
		if (c != null || c.size() > 1){
			Iterator i = c.iterator();
			int qtdCanc = 0;
			while(i.hasNext()){
				EventoTransacao evento = (EventoTransacao) i.next();
				if (evento instanceof EventoItemRegistrado){
					EventoItemRegistrado item = (EventoItemRegistrado) evento;
					if (item.getSituacao().equals(EventoItemRegistrado.CANCELADO)){
						qtdCanc++;
					}
				}
			}
			if (qtdCanc+1 == c.size()){
				return false;
			}
		}else{
			return false;
		}
		return true;
	}

}
