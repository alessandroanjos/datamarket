package com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.virtual;

import java.math.BigDecimal;
import java.util.Collection;

import com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.ImpressoraFiscal;
import com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.ImpressoraFiscalException;

public class ImpressoraFiscalVirtual implements ImpressoraFiscal{

	public void cancelaCupom() throws ImpressoraFiscalException {
		// TODO Auto-generated method stub
		
	}

	public void cancelaItem(String indice) throws ImpressoraFiscalException {
		// TODO Auto-generated method stub
		
	}

	public void efetuaPagamento(BigDecimal valor, String forma) throws ImpressoraFiscalException {
		// TODO Auto-generated method stub
		
	}

	public void fechaCupom(String forma, BigDecimal desconto, BigDecimal acressimo, String tipoDescontoAcressimo, BigDecimal valorTotal, String mensagem) throws ImpressoraFiscalException {
		// TODO Auto-generated method stub
		
	}

	public void fechamentoX() throws ImpressoraFiscalException {
		// TODO Auto-generated method stub
		
	}

	public void fechamentoZ() throws ImpressoraFiscalException {
		// TODO Auto-generated method stub
		
	}

	public void finalizaRelatorioGerencial() throws ImpressoraFiscalException {
		// TODO Auto-generated method stub
		
	}

	public BigDecimal getGT() throws ImpressoraFiscalException {
		// TODO Auto-generated method stub
		return BigDecimal.ZERO;
	}

	public long getNumeroCupom() throws ImpressoraFiscalException {
		// TODO Auto-generated method stub
		return 0;
	}

	public void imprimeRelatorioGerencial(String texto) throws ImpressoraFiscalException {
		// TODO Auto-generated method stub
		
	}

	public void iniciaFechamentoCupom(BigDecimal desconto, BigDecimal acressimo) throws ImpressoraFiscalException {
		// TODO Auto-generated method stub
		
	}

	public void inicioCupomFiscal(String cpf_cnpj) throws ImpressoraFiscalException {
		// TODO Auto-generated method stub
		
	}

	public void inicioDia() throws ImpressoraFiscalException {
		// TODO Auto-generated method stub
		
	}

	public void inicioOperador(BigDecimal valor, String forma) throws ImpressoraFiscalException {
		// TODO Auto-generated method stub
		
	}

	public void leituraX() throws ImpressoraFiscalException {
		// TODO Auto-generated method stub
		
	}

	public void sangria(BigDecimal valor, String forma) throws ImpressoraFiscalException {
		// TODO Auto-generated method stub
		
	}

	public void suprimento(BigDecimal valor, String forma) throws ImpressoraFiscalException {
		// TODO Auto-generated method stub
		
	}

	public void terminaFechaCupom(String mensagem) throws ImpressoraFiscalException {
		// TODO Auto-generated method stub
		
	}

	public void vendeItem(String codigo, String descricao, String aliquota, String tipoUnidade, BigDecimal quantidade, String unidade, BigDecimal valor, String tipoDesconto, BigDecimal desconto) throws ImpressoraFiscalException {
		// TODO Auto-generated method stub
		
	}

	public void addAliquota(BigDecimal aliquota) throws ImpressoraFiscalException {
		// TODO Auto-generated method stub
		
	}

	public void addTotalizador(String totalizaor, int indice) throws ImpressoraFiscalException {
		// TODO Auto-generated method stub
		
	}

	public Collection getAliqoutas() throws ImpressoraFiscalException {
		// TODO Auto-generated method stub
		return null;
	}

}
