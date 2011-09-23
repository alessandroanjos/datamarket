package com.infinity.datamarket.pdv.op;

import com.infinity.datamarket.comum.pagamento.ConstantesFormaRecebimento;
import com.infinity.datamarket.comum.pagamento.PlanoPagamento;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.tef.GerenciadorTEF;
import com.infinity.datamarket.pdv.tef.RespostaOperacaoTEF;
import com.infinity.datamarket.pdv.tef.SolicitacaoOperacaoTEF;

public class OpDesfazUltimaAutorizacaoCartao extends OpDesfazAutorizacaoCartao {

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		try{

			RespostaOperacaoTEF respostaTEF = (RespostaOperacaoTEF) gerenciadorPerifericos.getCmos().ler("respostaConfirmacaoSolicitacao");

			if (respostaTEF != null){
				gerenciadorPerifericos.getDisplay().setMensagem("Aguarde...");

					PlanoPagamento plano = respostaTEF.getPlano();
					
					
					if (respostaTEF != null) {
						
						SolicitacaoOperacaoTEF solicitacao = new SolicitacaoOperacaoTEF();
		
						solicitacao.setIdentificacao(respostaTEF.getIdentificacao());
						solicitacao.setNumeroCOO(respostaTEF.getNumeroCOO());
						solicitacao.setValorOperacao(respostaTEF.getValorOperacao());
						solicitacao.setNomeRede(respostaTEF.getNomeRede());
						solicitacao.setMoeda(respostaTEF.getMoeda());
						solicitacao.setNsuTEF(new Long(respostaTEF.getNsuTEF()));
						solicitacao.setDataHoraTransacao(respostaTEF.getDataHoraTransacao());
		
						boolean ehCheque = false;	
						if (plano != null && plano.getForma().getId().equals(ConstantesFormaRecebimento.CARTAO_OFF)){
							ehCheque = true;
							solicitacao.setCmc7(respostaTEF.getCmc7());

							solicitacao.setTipoPessoa(respostaTEF.getTipoPessoa());
							solicitacao.setDocumentoPessoa(respostaTEF.getDocumentoPessoa());
							solicitacao.setDataCheque(respostaTEF.getDataCheque());
							
							solicitacao.setBanco(respostaTEF.getBanco());
							solicitacao.setAgencia(respostaTEF.getAgencia());
							solicitacao.setDigitoAgencia(respostaTEF.getDigitoAgencia());
							solicitacao.setContaCorrente(respostaTEF.getContaCorrente());
							solicitacao.setDigitoContaCorrente(respostaTEF.getDigitoContaCorrente());
							solicitacao.setNumeroCheque(respostaTEF.getNumeroCheque());
							solicitacao.setDigitoCheque(respostaTEF.getDigitoCheque());
						}		
		
						RespostaOperacaoTEF  respostaCancelamentoTEF = GerenciadorTEF.getInstancia().getTef().cancelaOperacao(solicitacao, ehCheque);
					
						SolicitacaoOperacaoTEF solicitacaoConfirmacaoCancelamento = new SolicitacaoOperacaoTEF();
						//solicitacaoConfirmacaoCancelamento.setPlano(respostaTEF.getPlano());
						solicitacaoConfirmacaoCancelamento.setIdentificacao(respostaCancelamentoTEF.getIdentificacao());
						solicitacaoConfirmacaoCancelamento.setNumeroCOO(respostaCancelamentoTEF.getNumeroCOO());
						solicitacaoConfirmacaoCancelamento.setNomeRede(respostaCancelamentoTEF.getNomeRede());
						solicitacaoConfirmacaoCancelamento.setNsuTEF(respostaCancelamentoTEF.getNsuTEF());
						solicitacaoConfirmacaoCancelamento.setChaveFinalizacao(respostaCancelamentoTEF.getChaveFinalizacao());

						RespostaOperacaoTEF respostaConfirmacaoTEF = GerenciadorTEF.getInstancia().getTef().confirmaOperacao(solicitacaoConfirmacaoCancelamento);

					}				

			}	
		}catch(Exception e){
			e.printStackTrace();
			gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunica��o");
			try {
				gerenciadorPerifericos.esperaVolta();
			} catch (AppException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return ALTERNATIVA_2;
		}
		return ALTERNATIVA_1;
	}
}