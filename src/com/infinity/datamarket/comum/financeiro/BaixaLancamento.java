package com.infinity.datamarket.comum.financeiro;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.infinity.datamarket.comum.banco.Banco;
import com.infinity.datamarket.comum.conta.ContaCorrente;
import com.infinity.datamarket.comum.pagamento.FormaRecebimento;
import com.infinity.datamarket.comum.util.Util;

public class BaixaLancamento implements Serializable{

	private static final long serialVersionUID = -3258009781430050304L;
	
	public static final String ATIVO = "A";
	public static final String CANCELADO = "C";
	
	private BaixaLancamentoPK pk;
	private FormaRecebimento formaRecebimento;
	private ContaCorrente contaCorrente; 
	private BigDecimal valor;
	private BigDecimal valorDesconto;
	private BigDecimal valorAcrescimo;
	private BigDecimal valorTotalItem;
	private String numeroDocumento;
	private Banco banco;
	private String agencia;
	private String numeroConta;
	private String numeroCheque;
	private String tipoPessoaCheque;
	private String cpfCnpjCheque;
	private Date dataCheque;
	private String situacao;
	private String itemLancadoCtaCorrente;
	private String detalheFormaRecebimento;
	private Date dataBaixa;
	
	public Date getDataBaixa() {
		return dataBaixa;
	}
	public void setDataBaixa(Date dataBaixa) {
		this.dataBaixa = dataBaixa;
	}
	public String getDetalheFormaRecebimento() {
		return detalheFormaRecebimento;
	}
	public void setDetalheFormaRecebimento(String detalheFormaRecebimento) {
		this.detalheFormaRecebimento = detalheFormaRecebimento;
	}
	public String getItemLancadoCtaCorrente() {
		return itemLancadoCtaCorrente;
	}
	public void setItemLancadoCtaCorrente(String itemLancadoCtaCorrente) {
		this.itemLancadoCtaCorrente = itemLancadoCtaCorrente;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public Date getDataCheque() {
		return dataCheque;
	}
	public void setDataCheque(Date dataCheque) {
		this.dataCheque = dataCheque;
	}
	public String getAgencia() {
		return agencia;
	}
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}
	public Banco getBanco() {
		return banco;
	}
	public void setBanco(Banco banco) {
		this.banco = banco;
	}
	
	public FormaRecebimento getFormaRecebimento() {
		return formaRecebimento;
	}
	public void setFormaRecebimento(FormaRecebimento formaRecebimento) {
		this.formaRecebimento = formaRecebimento;
	}
	
	public String getNumeroCheque() {
		return numeroCheque;
	}
	public void setNumeroCheque(String numeroCheque) {
		this.numeroCheque = numeroCheque;
	}
	public String getNumeroConta() {
		return numeroConta;
	}
	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public String getTipoPessoaCheque() {
		return tipoPessoaCheque;
	}
	public void setTipoPessoaCheque(String tipoPessoaCheque) {
		this.tipoPessoaCheque = tipoPessoaCheque;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public BigDecimal getValorAcrescimo() {
		return valorAcrescimo;
	}
	public void setValorAcrescimo(BigDecimal valorAcrescimo) {
		this.valorAcrescimo = valorAcrescimo;
	}
	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}
	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}
	public BigDecimal getValorTotalItem() {
		return valorTotalItem;
	}
	public void setValorTotalItem(BigDecimal valorTotalItem) {
		this.valorTotalItem = valorTotalItem;
	}
	
	public int hashCode() {
		return getPk().hashCode();
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final BaixaLancamento other = (BaixaLancamento) obj;
		if (getPk() == null) {
			if (other.getPk() != null)
				return false;
		} else if (!getPk().equals(other.getPk()))
			return false;
		return true;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("id: "+getPk().getId().longValue());
		sb.append("\nlancamento: "+getPk().getIdLancamento().longValue());
		//sb.append("\nconta corrente: "+contaCorrente.getId().longValue() + " descricao: "+contaCorrente.getDescricao());
		sb.append("\nformaRecebimento: "+formaRecebimento.getId().longValue() + " descricao: "+formaRecebimento.getDescricao());
		sb.append("\nvalor: "+valor);
		sb.append("\nvalorDesconto: "+valorDesconto);
		sb.append("\nvalorAcrescimo: "+valorAcrescimo);
		sb.append("\nvalorTotalItem: "+valorTotalItem);
		sb.append("\nbanco: "+banco);
		sb.append("\nagencia: "+agencia);
		sb.append("\nnumeroConta: "+numeroConta);
		sb.append("\nnumeroCheque: "+numeroCheque);
		sb.append("\nnumeroDocumento: "+numeroDocumento);
		sb.append("\ndataCheque: "+Util.retornaDataFormatoDDMMYYYY(dataCheque));
		sb.append("\ntipoPessoaCheque: "+tipoPessoaCheque);
		sb.append("\ncpfCnpjCheque: "+cpfCnpjCheque);
		sb.append("\nsituacao: "+situacao);
		sb.append("\nitemLancadoCtaCorrente: "+itemLancadoCtaCorrente);
		sb.append("\ndetalheFormaRecebimento: "+detalheFormaRecebimento);
		return sb.toString();
	}
	public String getCpfCnpjCheque() {
		return cpfCnpjCheque;
	}
	public void setCpfCnpjCheque(String cpfCnpjCheque) {
		this.cpfCnpjCheque = cpfCnpjCheque;
	}
	
	public BaixaLancamentoPK getPk() {
		return pk;
	}
	public void setPk(BaixaLancamentoPK pk) {
		this.pk = pk;
	}
	public ContaCorrente getContaCorrente() {
		return contaCorrente;
	}
	public void setContaCorrente(ContaCorrente contaCorrente) {
		this.contaCorrente = contaCorrente;
	}
	
	public BaixaLancamento(){
		
	}
	
	public BaixaLancamento(BaixaLancamentoPK pk){
		setPk(pk);
	}
}