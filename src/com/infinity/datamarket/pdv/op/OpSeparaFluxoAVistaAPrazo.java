package com.infinity.datamarket.pdv.op;

import com.infinity.datamarket.comum.pagamento.PlanoPagamento;
import com.infinity.datamarket.comum.pagamento.PlanoPagamentoAPrazo;
import com.infinity.datamarket.comum.pagamento.PlanoPagamentoAVista;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpSeparaFluxoAVistaAPrazo extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		
		
//		try { 
//			
			PlanoPagamento plano = (PlanoPagamento) gerenciadorPerifericos.getCmos().ler(CMOS.PLANO_PAGAMENTO_ATUAL);
			if (plano  instanceof PlanoPagamentoAVista) {
				return ALTERNATIVA_1;
			} else if (plano  instanceof PlanoPagamentoAPrazo) {
				return ALTERNATIVA_3;
			} else {
				return ALTERNATIVA_2;
			}
//
//		} catch (AppException e) {
//			e.printStackTrace();
//			return ALTERNATIVA_2;
//		}
//
//		return ALTERNATIVA_1;

	}
}
