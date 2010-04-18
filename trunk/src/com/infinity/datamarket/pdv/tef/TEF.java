package com.infinity.datamarket.pdv.tef;

public interface TEF {
	public final static String INSTANCIA_TEF = "INSTANCIA_TEF";
	
	public RespostaOperacaoTEF solicitaOperacaoAdministracao(SolicitacaoOperacaoTEF solicitacaoOperacaoTEF) throws ExcecaoTEF;
	
	public RespostaOperacaoTEF solicitaOperacaoCheque(SolicitacaoOperacaoTEF solicitacaoOperacaoTEF) throws ExcecaoTEF;
	
	public RespostaOperacaoTEF solicitaOperacaoCartao(SolicitacaoOperacaoTEF solicitacaoOperacaoTEF) throws ExcecaoTEF;
	
	public RespostaOperacaoTEF confirmaOperacao(SolicitacaoOperacaoTEF solicitacaoOperacaoTEF) throws ExcecaoTEF;
	
	public RespostaOperacaoTEF cancelaOperacao(SolicitacaoOperacaoTEF solicitacaoOperacaoTEF, boolean operacaoCheque) throws ExcecaoTEF;
	
	public RespostaOperacaoTEF desfazOperacao(SolicitacaoOperacaoTEF solicitacaoOperacaoTEF) throws ExcecaoTEF;
}
