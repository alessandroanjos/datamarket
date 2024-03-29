package com.infinity.datamarket.pdv.op;


import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import com.infinity.datamarket.comum.pagamento.DadosChequePredatado;
import com.infinity.datamarket.comum.pagamento.ParcelaPlanoPagamentoAPrazo;
import com.infinity.datamarket.comum.pagamento.PlanoPagamento;
import com.infinity.datamarket.comum.pagamento.PlanoPagamentoAPrazo;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Constantes;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.comum.util.Util;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.gui.telas.TelaParcelaPlanos;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;

public class OpCalculaExibeTelaParcelaPlanos extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
	
		
		TelaParcelaPlanos tela = (TelaParcelaPlanos) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_PARCELA_PLANOS);

		PlanoPagamento plano = (PlanoPagamento) gerenciadorPerifericos.getCmos().ler(CMOS.PLANO_PAGAMENTO_ATUAL);
		
		tela.setPlanoFroma(plano);
		
		if (plano instanceof PlanoPagamentoAPrazo){
			tela.limparParcelas();
			PlanoPagamentoAPrazo planoPre = (PlanoPagamentoAPrazo) plano;
			
			BigDecimal valorPagamento = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.VALOR_PAGAMENTO_ATUAL);

			BigDecimal percentualEntrada = planoPre.getPercentagemEntrada();

			BigDecimal valorEntrada = valorPagamento.multiply(percentualEntrada).divide(new BigDecimal(100),2,BigDecimal.ROUND_DOWN);  

			tela.setEntrada(valorEntrada);
						
			Date dataAtual = new Date();

			SortedSet parcelas = new TreeSet();

			BigDecimal valorTotalPago = valorEntrada;

			if (valorEntrada.compareTo(BigDecimal.ZERO) > 0){
				
				DadosChequePredatado dadosCheque = new DadosChequePredatado();
				dadosCheque.setValor(valorEntrada);
				dadosCheque.setData(dataAtual);
				dadosCheque.setEntrada(Constantes.SIM);
				parcelas.add(dadosCheque);
			
			}
			if (Constantes.PARCELAS_VARIADAS.equalsIgnoreCase(planoPre.getParcelasFixasVariadas())) {
				// caso seja variada, solicita do usuario a quantidade e gera as parcelas
				try {
					
					int quantidadeParcelas = 0;
					while (quantidadeParcelas == 0) {
						gerenciadorPerifericos.getDisplay().setMensagem("Quantidade de parcelas?");
						EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 2);
					
						if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
							String dataBoleto = entrada.getDado();
							quantidadeParcelas = Integer.parseInt(dataBoleto);
							if (quantidadeParcelas ==0) {
								gerenciadorPerifericos.getDisplay().setMensagem("Quantidade Inv�lida");
								gerenciadorPerifericos.esperaVolta();
								continue;
							}
						}else if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_VOLTA){
							return ALTERNATIVA_2;
						}
					}

					BigDecimal percentualParcela = new BigDecimal(((100 - valorEntrada.doubleValue()) / quantidadeParcelas));
					
					//gerar os dados de acorodo com a quantidade
					for (int i = 0; i < quantidadeParcelas; i++) {

						BigDecimal valorParcela =  null;
						
						if (quantidadeParcelas == i){
							valorParcela = valorPagamento.subtract(valorTotalPago);
						}else{
							valorParcela = valorPagamento.multiply(percentualParcela).divide(new BigDecimal(100),2,BigDecimal.ROUND_DOWN);
							valorTotalPago = valorTotalPago.add(valorParcela);
						}

						tela.addParcela(i+1, valorParcela, null);
					
						DadosChequePredatado dadoParcela = new DadosChequePredatado();
						dadoParcela.setValor(valorParcela);
						if (Constantes.DATAS_INFORMADA_PELO_SISTEMA.equalsIgnoreCase(planoPre.getDatasParcelasVariadasInformadaSistemaOuUsuario())) {
							dadoParcela.setData(Util.adicionarDia(dataAtual, 30 * i));
						} else {
							dadoParcela.setData(null);
						}
						parcelas.add(dadoParcela);

					}
				} catch (AppException e) {
					e.printStackTrace();
					return ALTERNATIVA_2;
				}
			} else {
				
				tela.setValorTotal(valorPagamento);

				int count = 1;
				
				System.out.println(valorEntrada);
				
				Calendar cal = new GregorianCalendar();
				
				
				Iterator i = planoPre.getParcelas().iterator();
				
				while(i.hasNext()){
					ParcelaPlanoPagamentoAPrazo parcela = (ParcelaPlanoPagamentoAPrazo) i.next();
					
					BigDecimal percentualParcela = parcela.getPercentagemParcela();
					
					
					BigDecimal valorParcela =  null;
					
					if (planoPre.getParcelas().size() == count){
						valorParcela = valorPagamento.subtract(valorTotalPago);
					}else{
						valorParcela = valorPagamento.multiply(percentualParcela).divide(new BigDecimal(100),2,BigDecimal.ROUND_DOWN);
						valorTotalPago = valorTotalPago.add(valorParcela);
					}
					
					Date data = null;
					
					if (parcela.getQuantidadeDias() != 0){
						
						cal.setTime(dataAtual);
					
						cal.add(Calendar.DAY_OF_MONTH, parcela.getQuantidadeDias());
						
						data = cal.getTime(); 
						
						tela.addParcela(count, valorParcela, data);
					}else{
						tela.addParcela(count, valorParcela, null);
					}
					
					DadosChequePredatado dadoParcela = new DadosChequePredatado();
					
					dadoParcela.setValor(valorParcela);
					dadoParcela.setData(data);
					
					parcelas.add(dadoParcela);
					
					count++;
					
				}
			}
			
			if (parcelas.size() > 0 ) {
				gerenciadorPerifericos.atualizaTela(tela);
				
				gerenciadorPerifericos.getDisplay().setMensagem("Confirma? [ENTRA] [ESC]");
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
			} else {
				gerenciadorPerifericos.getDisplay().setMensagem("Forma sem Parcela");
				try {
					gerenciadorPerifericos.esperaVolta();
					return ALTERNATIVA_2;
				} catch (AppException e) {
					return ALTERNATIVA_2;
				}
			}
		}else{
			gerenciadorPerifericos.getDisplay().setMensagem("Forma n�o Parcel�vel");
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