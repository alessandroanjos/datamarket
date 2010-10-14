package com.infinity.datamarket.pdv.op;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.autorizador.AutorizacaoException;
import com.infinity.datamarket.autorizador.DadosAutorizacaoCartaoProprio;
import com.infinity.datamarket.comum.pagamento.ConstantesFormaRecebimento;
import com.infinity.datamarket.comum.pagamento.PlanoPagamento;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.tef.ExcecaoTEF;
import com.infinity.datamarket.pdv.tef.GerenciadorTEF;
import com.infinity.datamarket.pdv.tef.RespostaOperacaoTEF;
import com.infinity.datamarket.pdv.tef.SolicitacaoOperacaoTEF;
import com.infinity.datamarket.pdv.util.ServerConfig;

public class OpDesfazUltimaAutorizacaoCartao extends OpDesfazAutorizacaoCartao {

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		try{
			Collection c = (Collection) gerenciadorPerifericos.getCmos().ler(CMOS.DADOS_AUTORIZACOES_CARTAO_PROPRIO);
			
			if (c != null && c.size() > 0){
				gerenciadorPerifericos.getDisplay().setMensagem("Aguarde...");

				Iterator i = c.iterator();
				while(i.hasNext()){
					RespostaOperacaoTEF  respostaTEF = (RespostaOperacaoTEF) i.next();
					
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
					}				

				}
			}	
		}catch(Exception e){
			e.printStackTrace();
			gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
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
