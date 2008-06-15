package com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal;

import java.math.BigDecimal;

public interface ImpressoraFiscal {
	
	public static final String DESCONTO_PERCENTUAL = "%";
	public static final String DESCONTO_VALOR = "$";
	
	public void inicioDia() throws ImpressoraFiscalException;
	public void inicioOperador(BigDecimal valor, String forma) throws ImpressoraFiscalException;
	public void fechamentoX() throws ImpressoraFiscalException;
	public void fechamentoZ() throws ImpressoraFiscalException;
	public void leituraX() throws ImpressoraFiscalException;
	public void sangria(BigDecimal valor, String forma) throws ImpressoraFiscalException;
	public void suprimento(BigDecimal valor, String forma) throws ImpressoraFiscalException;
	public void inicioCupomFiscal(String cpf_cnpj) throws ImpressoraFiscalException;
	public void vendeItem(String codigo , String descricao,String aliquota, String tipoUnidade, BigDecimal quantidade, String unidade,BigDecimal valor,
						  String tipoDesconto, BigDecimal desconto) throws ImpressoraFiscalException;
	public void cancelaItem(String indice) throws ImpressoraFiscalException;
	public void cancelaCupom() throws ImpressoraFiscalException;
	public void efetuaPagamento(BigDecimal valor, String forma) throws ImpressoraFiscalException;
	public void fechaCupom(String forma, BigDecimal desconto, BigDecimal acressimo,String tipoDescontoAcressimo,BigDecimal valorTotal, String mensagem) throws ImpressoraFiscalException;
	public void terminaFechaCupom(String mensagem) throws ImpressoraFiscalException;
	public void iniciaFechamentoCupom(BigDecimal desconto, BigDecimal acressimo) throws ImpressoraFiscalException;
	public void imprimeRelatorioGerencial(String texto) throws ImpressoraFiscalException;
	public void finalizaRelatorioGerencial() throws ImpressoraFiscalException;
	public BigDecimal getGT() throws ImpressoraFiscalException;
	public long getNumeroCupom() throws ImpressoraFiscalException;
}
