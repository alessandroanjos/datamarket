package com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.bematech;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.taglibs.standard.extra.spath.ASCII_CharStream;

import com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.ImpressoraFiscal;
import com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.ImpressoraFiscalException;
import com.sun.jna.Native;
import com.sun.mail.util.ASCIIUtility;

public class ImpressoraFiscalBematechMP2000 implements ImpressoraFiscal, Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1339103375970047661L;
	private static final String DESCONTO = "D";
	private static final String ACRESSIMO = "A";
	
	private static final String QUANTIDADE_INTEIRA = "I";
	private static final String QUANTIDADE_FRACIONADA = "F";
	
	private IComunicacaoImpressoraFiscalBematechMP2000 lib;
	
	public ImpressoraFiscalBematechMP2000() throws ImpressoraFiscalException{
		lib = (IComunicacaoImpressoraFiscalBematechMP2000) Native.loadLibrary("BemaFI32", IComunicacaoImpressoraFiscalBematechMP2000.class);
		int iRetorno = lib.Bematech_FI_NomeiaTotalizadorNaoSujeitoIcms(01, "SUPRIMETO");
		trataRetorno(iRetorno);
		
		iRetorno = lib.Bematech_FI_ProgramaAliquota("1800", 1);
		trataRetorno(iRetorno);
		
		iRetorno = lib.Bematech_FI_NomeiaTotalizadorNaoSujeitoIcms(02, "SANGRIA");
		trataRetorno(iRetorno);
	}
	
	private static void trataRetorno(int iRetorno) throws ImpressoraFiscalException{
		switch (iRetorno) {
		case 0:
			throw new ImpressoraFiscalException("ERRO IMPRESSORA 001");
		case -2:
			throw new ImpressoraFiscalException("ERRO IMPRESSORA 002");			
		case -5:
			throw new ImpressoraFiscalException("ERRO IMPRESSORA 003");		
		default:
			break;
		}
	}
	
	public void inicioDia() throws ImpressoraFiscalException {
		
		int iRetorno = lib.Bematech_FI_AberturaDoDia("0", "Dinheiro");
		trataRetorno(iRetorno);
		

	}
	
	public void inicioOperador(BigDecimal valor, String forma) throws ImpressoraFiscalException {
		
		suprimento(valor, forma);

		
	}
	
	public void suprimento(BigDecimal valor, String forma) throws ImpressoraFiscalException {
		
		int iRetorno = lib.Bematech_FI_RecebimentoNaoFiscal("01",valor.setScale(2).toString(), forma);
		trataRetorno(iRetorno);
		
	}
	
	public void sangria(BigDecimal valor, String forma) throws ImpressoraFiscalException {
		
		int iRetorno = lib.Bematech_FI_RecebimentoNaoFiscal("02",valor.setScale(2).toString(), forma);
		trataRetorno(iRetorno);
		
	}
	
	public void fechamentoX() throws ImpressoraFiscalException {
		
		int iRetorno = lib.Bematech_FI_FechamentoDoDia();
		trataRetorno(iRetorno);
	}
	
	public void fechamentoZ() throws ImpressoraFiscalException {
		
		int iRetorno = lib.Bematech_FI_FechamentoDoDia();
		trataRetorno(iRetorno);
	}
	
	public void leituraX() throws ImpressoraFiscalException{
		
		int iRetorno = lib.Bematech_FI_LeituraX();
		trataRetorno(iRetorno);
		
	}
	
	public void inicioCupomFiscal(String cpf_cnpj) throws ImpressoraFiscalException{
		int iRetorno = lib.Bematech_FI_AbreCupom(cpf_cnpj);
		trataRetorno(iRetorno);
	}
	
	public void vendeItem(String codigo , String descricao,String aliquota, String tipoUnidade, BigDecimal quantidade, String unidade, BigDecimal valor,
			  String tipoDesconto, BigDecimal desconto) throws ImpressoraFiscalException{
		int iRetorno = lib.Bematech_FI_UsaUnidadeMedida(unidade);
		trataRetorno(iRetorno);
		
		System.out.println(codigo+" | "+descricao+" | "+aliquota+" | "+tipoUnidade+" | "+quantidade.setScale(3).toString()+" | "+2+" | "+ valor.setScale(2).toString()+" | "+tipoDesconto+" | "+desconto.setScale(2).toString());
		
		iRetorno = lib.Bematech_FI_VendeItem(codigo, descricao, aliquota, tipoUnidade, tipoUnidade.equals(QUANTIDADE_FRACIONADA)?quantidade.setScale(3).toString():quantidade.setScale(0).toString(), 2, valor.setScale(2).toString(), tipoDesconto, desconto.setScale(2).toString());
		trataRetorno(iRetorno);
	}
	
	public void cancelaItem(String indice) throws ImpressoraFiscalException{
		int iRetorno = lib.Bematech_FI_CancelaItemGenerico(indice);
		trataRetorno(iRetorno);
	}
	
	public void cancelaCupom() throws ImpressoraFiscalException{
		int iRetorno = lib.Bematech_FI_CancelaCupom();
		trataRetorno(iRetorno);
	}
	
	public void efetuaPagamento(BigDecimal valor, String forma) throws ImpressoraFiscalException{
		
		System.out.println(forma +" "+valor.toString());
		
		int iRetorno = lib.Bematech_FI_EfetuaFormaPagamento(forma, valor.setScale(2,BigDecimal.ROUND_HALF_DOWN).toString());
		trataRetorno(iRetorno);
	}
	
	public void fechaCupom(String forma, BigDecimal desconto, BigDecimal acressimo,String tipoDescontoAcressimo,BigDecimal valorTotal, String mensagem) throws ImpressoraFiscalException{
		int iRetorno = 0;
		if (desconto!= null && desconto.compareTo(BigDecimal.ZERO) > 0){
			iRetorno = lib.Bematech_FI_FechaCupom(forma, DESCONTO, tipoDescontoAcressimo!=null?tipoDescontoAcressimo:"", desconto!= null?desconto.setScale(2).toString():"", valorTotal.setScale(2).toString(), mensagem);
		}else if (acressimo!= null && acressimo.compareTo(BigDecimal.ZERO) > 0){
			iRetorno = lib.Bematech_FI_FechaCupom(forma, ACRESSIMO, tipoDescontoAcressimo!=null?tipoDescontoAcressimo:"", desconto!= null?desconto.setScale(2).toString():"", valorTotal.setScale(2).toString(), mensagem);
		}else{
			iRetorno = lib.Bematech_FI_FechaCupom(forma, ACRESSIMO, ImpressoraFiscal.DESCONTO_VALOR, "0", valorTotal.setScale(2).toString(), mensagem);
		}
		trataRetorno(iRetorno);
	}
	
	public void terminaFechaCupom(String mensagem) throws ImpressoraFiscalException{
		int iRetorno = lib.Bematech_FI_TerminaFechamentoCupom(mensagem);
		trataRetorno(iRetorno);
	}
	
	public void iniciaFechamentoCupom(BigDecimal desconto, BigDecimal acressimo) throws ImpressoraFiscalException{
		int iRetorno = 0;
		if (desconto!= null && desconto.compareTo(BigDecimal.ZERO) > 0){
			iRetorno = lib.Bematech_FI_IniciaFechamentoCupom(DESCONTO, ImpressoraFiscal.DESCONTO_VALOR, desconto!= null?desconto.setScale(2).toString():"");
		}else if (acressimo!= null && acressimo.compareTo(BigDecimal.ZERO) > 0){
			iRetorno = lib.Bematech_FI_IniciaFechamentoCupom(ACRESSIMO, ImpressoraFiscal.DESCONTO_VALOR, desconto!= null?acressimo.setScale(2).toString():"");
		}else{
			iRetorno = lib.Bematech_FI_IniciaFechamentoCupom(ACRESSIMO, ImpressoraFiscal.DESCONTO_VALOR, "0");
		}
		trataRetorno(iRetorno);
	}
	
	public void imprimeRelatorioGerencial(String texto) throws ImpressoraFiscalException{
		int tamanho = texto.length();
		if (tamanho < 618){
			int iRetorno = lib.Bematech_FI_RelatorioGerencial(texto);
			trataRetorno(iRetorno);
		}else{
			String novoTexto = texto.substring(0,617);
			int iRetorno = lib.Bematech_FI_RelatorioGerencial(novoTexto);
			trataRetorno(iRetorno);
			imprimeRelatorioGerencial(texto.substring(618));
		}
		
	}
	
	public void finalizaRelatorioGerencial() throws ImpressoraFiscalException{
		int iRetorno = lib.Bematech_FI_FechaRelatorioGerencial();
		trataRetorno(iRetorno);
	}
	
	public BigDecimal getGT() throws ImpressoraFiscalException{
		byte[] t = new byte[18];
		
		int iRetorno = lib.Bematech_FI_GrandeTotal(t);
		trataRetorno(iRetorno);
		String sValor = "";
		for(int i = 0;i < t.length;i++){
			sValor = sValor + Character.getNumericValue(t[i]);
		}
		BigDecimal valor = new BigDecimal(sValor);
		
		return valor.divide(new BigDecimal(100));
	}
	
	public long getNumeroCupom() throws ImpressoraFiscalException{
		byte[] t = new byte[6];
		
		int iRetorno = lib.Bematech_FI_NumeroCupom(t);
		trataRetorno(iRetorno);
		String sValor = "";
		for(int i = 0;i < t.length;i++){
			sValor = sValor + Character.getNumericValue(t[i]);
		}
		return new Long(sValor).longValue();
	}
	


}
