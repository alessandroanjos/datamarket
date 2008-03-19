package com.infinity.datamarket.pdv.mic;

import java.util.Date;

import com.infinity.datamarket.comum.pagamento.PlanoPagamento;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;

public class MicSolicitaPlano extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		try{
			do{
				gerenciadorPerifericos.getDisplay().setMensagem("Selecione o Plano");
				EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 2);
				if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
					String valor = entrada.getDado();
					Long id = null;
					try{
						id = new Long(valor);
					}catch(NumberFormatException e){
						continue;
					}
					PlanoPagamento plano;
					try{
						plano= getFachadaPDV().consultarPlanoPagamentoPorId(id);
					}catch(ObjectNotFoundException e){
						gerenciadorPerifericos.getDisplay().setMensagem("Plano Inválido");
						gerenciadorPerifericos.esperaVolta();
						continue;
					}
					Date dataAtual = new Date();
					if (plano != null && (dataAtual.compareTo(plano.getDataInicioValidade()) < 0 || dataAtual.compareTo(plano.getDataFimValidade()) > 0)){
						gerenciadorPerifericos.getDisplay().setMensagem("Plano Inválido");
						gerenciadorPerifericos.esperaVolta();
						continue;
					}
					gerenciadorPerifericos.getCmos().gravar(CMOS.PLANO_PAGAMENTO_ATUAL, plano);
					return ALTERNATIVA_1;
				}else{
					return ALTERNATIVA_2;
				}
			}while(true);
		}catch(AppException e){
			return ALTERNATIVA_2;
		}
	}

}
