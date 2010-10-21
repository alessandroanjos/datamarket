package com.infinity.datamarket.pdv.tef;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ResourceBundle;

import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.pdv.tef.paygo.TEFPayGo;

public class GerenciadorTEF {

	private static GerenciadorTEF instancia;
	
	private TEF tef;
	
	public TEF getTef() {
		return tef;
	}

	private GerenciadorTEF(){
		String nomeClasseTEF = "";
		nomeClasseTEF = ((ResourceBundle)ServiceLocator.getInstancia().getResource()).getString(TEF.INSTANCIA_TEF);
		tef = (TEF)ServiceLocator.getInstancia().getObjectToInstancia(nomeClasseTEF);
	}
	
	public static GerenciadorTEF getInstancia(){
		if(instancia == null){
			instancia = new GerenciadorTEF();
		}
		return instancia;
	}
	
	public static RespostaOperacaoTEF respostaTEF;
	
	public static void main(String[] args) {
		System.out.println("A");
		GerenciadorTEF.getInstancia().testeServicoAtivo();
		System.out.println("B");
		GerenciadorTEF.getInstancia().testeSolicitaOperacaoCartao(1);
		System.out.println("C");
		GerenciadorTEF.getInstancia().testeSolicitaOperacaoCartao(2);
		System.out.println("D");
		GerenciadorTEF.getInstancia().testeConfirmaOperacao(1);
		System.out.println("E");
		GerenciadorTEF.getInstancia().testeConfirmaOperacao(2);

	}
	
	private void testeSolicitaOperacaoAdministracao(){
		SolicitacaoOperacaoTEF solicitacao = new SolicitacaoOperacaoTEF();
		solicitacao.setIdentificacao(new Long(1));
		try {
			respostaTEF = GerenciadorTEF.getInstancia().getTef().solicitaOperacaoAdministracao(solicitacao);
			
			if(respostaTEF.getTextoEspecialOperador() != null){
				System.out.println(respostaTEF.getTextoEspecialOperador());
			}
				if(respostaTEF.getLinhasComprovantePrincipal().length == 0){
					System.out.println("Sem Comprovante a imprimir.");
				} else if(respostaTEF.getLinhasComprovantePrincipal().length > 0){
			
					System.out.println("Imprimindo Linhas Comprovante Principal");
					for (int i = 0; i < respostaTEF.getLinhasComprovantePrincipal().length; i++) {
						String linha = respostaTEF.getLinhasComprovantePrincipal()[i];
						System.out.println(linha);
						
					}
				}
			
			//GerenciadorTEF.getInstancia().testeConfirmaOperacao();
		} catch (ExcecaoTEF e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private void testeSolicitaOperacaoCartao(long identificador){
		SolicitacaoOperacaoTEF solicitacao = new SolicitacaoOperacaoTEF();
		solicitacao.setIdentificacao(identificador);
		solicitacao.setNumeroCOO(new Long(1));
		solicitacao.setValorOperacao(new BigDecimal("50.00"));
		solicitacao.setNomeRede(TEFPayGo.VISANET);
		solicitacao.setMoeda(TEFPayGo.MOEDA_REAL);
		try {
			respostaTEF = GerenciadorTEF.getInstancia().getTef().solicitaOperacaoCartao(solicitacao);
		} catch (ExcecaoTEF e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void testeServicoAtivo(){
		try {
			System.out.println(GerenciadorTEF.getInstancia().getTef().validarServicoAtivo());
		} catch (ExcecaoTEF e) {
			e.printStackTrace();
		}
	}
	
	private void testeSolicitaOperacaoCheque(){
		SolicitacaoOperacaoTEF solicitacao = new SolicitacaoOperacaoTEF();
		solicitacao.setIdentificacao(new Long(3));
		solicitacao.setNumeroCOO(new Long(2));
		solicitacao.setValorOperacao(new BigDecimal("60.00"));
		
		solicitacao.setCmc7("237320870070000625477509958276");
		
		solicitacao.setTipoPessoa(TEFPayGo.PESSOA_FISICA);
		solicitacao.setDocumentoPessoa(new Long("02882755430"));
		solicitacao.setDataCheque(new Date(System.currentTimeMillis()));
		
		solicitacao.setBanco("237");
		solicitacao.setAgencia("3208");
		solicitacao.setDigitoAgencia("5");
		solicitacao.setContaCorrente("0099582");
		solicitacao.setDigitoContaCorrente("7");
		solicitacao.setNumeroCheque("000062");
		solicitacao.setDigitoCheque("0");
		
		try {
			respostaTEF = GerenciadorTEF.getInstancia().getTef().solicitaOperacaoCheque(solicitacao);
		} catch (ExcecaoTEF e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void testeCancelaOperacao(boolean ehCheque){
		SolicitacaoOperacaoTEF solicitacao = new SolicitacaoOperacaoTEF();
		
		solicitacao.setIdentificacao(respostaTEF.getIdentificacao());
		solicitacao.setNumeroCOO(respostaTEF.getNumeroCOO());
		solicitacao.setValorOperacao(respostaTEF.getValorOperacao());
		solicitacao.setNomeRede(respostaTEF.getNomeRede());
		solicitacao.setMoeda(respostaTEF.getMoeda());
		solicitacao.setNsuTEF(new Long(respostaTEF.getNsuTEF()));
		solicitacao.setDataHoraTransacao(respostaTEF.getDataHoraTransacao());

		if(ehCheque){
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
		try {
			respostaTEF = GerenciadorTEF.getInstancia().getTef().cancelaOperacao(solicitacao, ehCheque);
		} catch (ExcecaoTEF e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void testeConfirmaOperacao(long identificacao){
		SolicitacaoOperacaoTEF solicitacao = new SolicitacaoOperacaoTEF();
		solicitacao.setIdentificacao(identificacao);
		solicitacao.setNumeroCOO(respostaTEF.getNumeroCOO());
		solicitacao.setNomeRede(respostaTEF.getNomeRede());
		solicitacao.setNsuTEF(respostaTEF.getNsuTEF());
		solicitacao.setChaveFinalizacao(respostaTEF.getChaveFinalizacao());
		try {
			respostaTEF = GerenciadorTEF.getInstancia().getTef().confirmaOperacao(solicitacao);
		} catch (ExcecaoTEF e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void testeDesfazOperacao(){
		SolicitacaoOperacaoTEF solicitacao = new SolicitacaoOperacaoTEF();
		solicitacao.setIdentificacao(respostaTEF.getIdentificacao());
		solicitacao.setNumeroCOO(respostaTEF.getNumeroCOO());
		solicitacao.setNomeRede(respostaTEF.getNomeRede());
		solicitacao.setNsuTEF(respostaTEF.getNsuTEF());
		solicitacao.setChaveFinalizacao(respostaTEF.getChaveFinalizacao());
		try {
			respostaTEF = GerenciadorTEF.getInstancia().getTef().desfazOperacao(solicitacao);
		} catch (ExcecaoTEF e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}