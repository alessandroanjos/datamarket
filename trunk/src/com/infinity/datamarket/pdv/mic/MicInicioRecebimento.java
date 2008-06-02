package com.infinity.datamarket.pdv.mic;

import java.math.BigDecimal;
import java.util.Date;

import com.infinity.datamarket.comum.pagamento.FormaRecebimento;
import com.infinity.datamarket.comum.pagamento.PlanoPagamento;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public abstract class MicInicioRecebimento extends Mic{
	protected int execRegrasFormaPlano(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param, Long idForma){
		try {
			String valor = param.getParam();
			BigDecimal valorPagamento = new BigDecimal(valor);

			if (valorPagamento.compareTo(new BigDecimal(0)) == 0){
				valorPagamento = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.SUB_TOTAL);
			}

			gerenciadorPerifericos.getCmos().gravar(CMOS.VALOR_PAGAMENTO_ATUAL,valorPagamento);

			FormaRecebimento forma = getFachadaPDV().consultarFormaRecebimentoPorId(idForma);
			
			if (forma.getPlanos() == null || (forma.getPlanos() != null && forma.getPlanos().size() == 0) ){
				gerenciadorPerifericos.getDisplay().setMensagem("Forma sem plano associado");
				try{
					gerenciadorPerifericos.esperaVolta();
					return ALTERNATIVA_2;
				}catch(AppException e){

				}
			}else if (forma.getPlanos().size() ==  1){
				PlanoPagamento plano = (PlanoPagamento) forma.getPlanos().iterator().next();
				gerenciadorPerifericos.getCmos().gravar(CMOS.PLANO_PAGAMENTO_ATUAL,plano);
				gerenciadorPerifericos.getCmos().gravar(CMOS.FORMA_RECEBIMENTO_ATUAL,forma);
				return ALTERNATIVA_3;
			}
			Date dataAtual = new Date();
			if (dataAtual.compareTo(forma.getDataInicioValidade()) < 0 || dataAtual.compareTo(forma.getDataFimValidade()) > 0){
				gerenciadorPerifericos.getDisplay().setMensagem("Forma fora da validade");
				try{
					gerenciadorPerifericos.esperaVolta();
					return ALTERNATIVA_2;
				}catch(AppException e){

				}
			}
			gerenciadorPerifericos.getCmos().gravar(CMOS.FORMA_RECEBIMENTO_ATUAL,forma);
		} catch (ObjectNotFoundException e) {
			gerenciadorPerifericos.getDisplay().setMensagem("Forma inválida");
			try{
				gerenciadorPerifericos.esperaVolta();
				return ALTERNATIVA_2;
			}catch(AppException ex){

			}
			return ALTERNATIVA_2;
		} catch (AppException e) {
			e.printStackTrace();
			return ALTERNATIVA_2;
		}

		return ALTERNATIVA_1;

	}
}
