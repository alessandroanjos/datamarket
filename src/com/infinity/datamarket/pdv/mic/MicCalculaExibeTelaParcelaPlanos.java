package com.infinity.datamarket.pdv.mic;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import com.infinity.datamarket.comum.pagamento.DadosChequePredatado;
import com.infinity.datamarket.comum.pagamento.ParcelaPlanoPagamentoChequePredatado;
import com.infinity.datamarket.comum.pagamento.PlanoPagamento;
import com.infinity.datamarket.comum.pagamento.PlanoPagamentoChequePredatado;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Constantes;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.gui.telas.TelaParcelaPlanos;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;

public class MicCalculaExibeTelaParcelaPlanos extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		TelaParcelaPlanos tela = (TelaParcelaPlanos) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_PARCELA_PLANOS);

		PlanoPagamento plano = (PlanoPagamento) gerenciadorPerifericos.getCmos().ler(CMOS.PLANO_PAGAMENTO_ATUAL);
		
		if (plano instanceof PlanoPagamentoChequePredatado){
			tela.limparParcelas();
			
			
			PlanoPagamentoChequePredatado planoPre = (PlanoPagamentoChequePredatado) plano;
			
			BigDecimal valorPagamento = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.VALOR_PAGAMENTO_ATUAL);
			
			BigDecimal percentualEntrada = planoPre.getPercentagemEntrada();
			
			BigDecimal valorEntrada = valorPagamento.multiply(percentualEntrada).divide(new BigDecimal(100),2,BigDecimal.ROUND_DOWN);  
			
			tela.setEntrada(valorEntrada);
			
			Iterator i = planoPre.getParcelas().iterator();
			
			int count = 1;
			
			Date dataAtual = new Date();
			
			Collection parcelas = new ArrayList();

			System.out.println(valorEntrada);
			
			if (valorEntrada.compareTo(BigDecimal.ZERO) > 0){
			
				DadosChequePredatado dadosCheque = new DadosChequePredatado();
				dadosCheque.setValor(valorEntrada);
				dadosCheque.setData(dataAtual);
				dadosCheque.setEntrada(Constantes.SIM);
				parcelas.add(dadosCheque);
			
			}
			Calendar cal = new GregorianCalendar();
			
			BigDecimal valorTotal = valorEntrada;
			
			
			
			
			
			while(i.hasNext()){
				ParcelaPlanoPagamentoChequePredatado parcela = (ParcelaPlanoPagamentoChequePredatado) i.next();
				
				BigDecimal percentualParcela = parcela.getPercentagemParcela();
				
				
				BigDecimal valorParcela =  null;
				
				if (planoPre.getParcelas().size() == count){
					valorParcela = valorPagamento.subtract(valorTotal);
				}else{
					valorParcela = valorPagamento.multiply(percentualParcela).divide(new BigDecimal(100),2,BigDecimal.ROUND_DOWN);
					valorTotal = valorTotal.add(valorParcela);
				}
				
				cal.setTime(dataAtual);
				
				cal.add(Calendar.DAY_OF_MONTH, parcela.getQuantidadeDias());
				
				Date data = cal.getTime(); 
				
				tela.addParcela(count, valorParcela, data);
				
				DadosChequePredatado dadoParcela = new DadosChequePredatado();
				
				dadoParcela.setValor(valorParcela);
				dadoParcela.setData(data);
				
				parcelas.add(dadoParcela);
				
				count++;
				
			}
			
			
			gerenciadorPerifericos.atualizaTela(tela);
			
			gerenciadorPerifericos.getDisplay().setMensagem("Confirma? [ENTRA][VOLTA]");
			try {
				EntradaDisplay entrada = gerenciadorPerifericos.getDisplay().lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA}, Display.MASCARA_NUMERICA, 0);
				if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
					gerenciadorPerifericos.getCmos().gravar(CMOS.DADOS_CHEQUE_PRE, parcelas);
					return ALTERNATIVA_1;
				}else if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_VOLTA){
					return ALTERNATIVA_2;
				}
			} catch (AppException e) {
				return ALTERNATIVA_2;
			}
			
		}else{
			gerenciadorPerifericos.getDisplay().setMensagem("Forma não Parcelável");
			try {
				gerenciadorPerifericos.esperaVolta();
				return ALTERNATIVA_2;
			} catch (AppException e) {
				return ALTERNATIVA_2;
			}
		}
		return ALTERNATIVA_2;
	}
}