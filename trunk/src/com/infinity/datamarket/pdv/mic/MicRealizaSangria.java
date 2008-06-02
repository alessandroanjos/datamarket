package com.infinity.datamarket.pdv.mic;

import java.math.BigDecimal;
import java.util.Iterator;

import com.infinity.datamarket.comum.pagamento.ConstantesFormaRecebimento;
import com.infinity.datamarket.comum.pagamento.FormaRecebimento;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.produto.TipoProduto;
import com.infinity.datamarket.comum.transacao.EventoItemPagamento;
import com.infinity.datamarket.comum.transacao.EventoItemRegistrado;
import com.infinity.datamarket.comum.transacao.EventoTransacao;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.ImpressoraFiscal;
import com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.ImpressoraFiscalException;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;

public class MicRealizaSangria extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		gerenciadorPerifericos.getDisplay().setMensagem("Aguarde...");
		BigDecimal valorSangria = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.VALOR_SANGRIA);
		try{
			FormaRecebimento forma = getFachadaPDV().consultarFormaRecebimentoPorId(ConstantesFormaRecebimento.DINHEIRO);
			
			gerenciadorPerifericos.getImpressoraFiscal().sangria(valorSangria, forma.getRecebimentoImpressora());
			
		}catch(ImpressoraFiscalException e){
			gerenciadorPerifericos.getDisplay().setMensagem(e.getMessage());
			try{
				gerenciadorPerifericos.esperaVolta();
			}catch(Exception ex){
				return ALTERNATIVA_2;
			}
			return ALTERNATIVA_2;
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ALTERNATIVA_1;
	}

}
