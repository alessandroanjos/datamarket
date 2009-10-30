package com.infinity.datamarket.comum.conta;

import java.math.BigDecimal;

import com.infinity.datamarket.comum.banco.Banco;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.util.Persistente;

public class ContaCorrente extends Persistente implements Comparable{

	private static final long serialVersionUID = 6474664301311452427L;
	private String carteira ;
	private String digitoContaCorrente;
	private String idAgencia;
	private String numero;
	private String nome;
	private BigDecimal saldo;
	private String situacao;
	private Banco banco;
	private Loja loja;
	private String mensagemBoleto1;
	private String mensagemBoleto2;
	private String mensagemBoleto3;
	private String mensagemBoleto4;

	public String getMensagemBoleto1() {
		return mensagemBoleto1;
	}
	public void setMensagemBoleto1(String mensagemBoleto1) {
		this.mensagemBoleto1 = mensagemBoleto1;
	}
	public String getMensagemBoleto2() {
		return mensagemBoleto2;
	}
	public void setMensagemBoleto2(String mensagemBoleto2) {
		this.mensagemBoleto2 = mensagemBoleto2;
	}
	public String getMensagemBoleto3() {
		return mensagemBoleto3;
	}
	public void setMensagemBoleto3(String mensagemBoleto3) {
		this.mensagemBoleto3 = mensagemBoleto3;
	}
	public String getMensagemBoleto4() {
		return mensagemBoleto4;
	}
	public void setMensagemBoleto4(String mensagemBoleto4) {
		this.mensagemBoleto4 = mensagemBoleto4;
	}
	public String getCarteira() {
		return carteira;
	}
	public void setCarteira(String carteira) {
		this.carteira = carteira;
	}
	public String getDigitoContaCorrente() {
		return digitoContaCorrente;
	}
	public void setDigitoContaCorrente(String digitoContaCorrente) {
		this.digitoContaCorrente = digitoContaCorrente;
	}
	public Loja getLoja() {
		return loja;
	}
	public void setLoja(Loja loja) {
		this.loja = loja;
	}
	public Banco getBanco() {
		return banco;
	}
	public void setBanco(Banco banco) {
		this.banco = banco;
	}
	public String getIdAgencia() {
		return idAgencia;
	}
	public void setIdAgencia(String idAgencia) {
		this.idAgencia = idAgencia;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public BigDecimal getSaldo() {
		return saldo;
	}
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public ContaCorrente(){
		
	}
	@Override
	public int hashCode() {
		StringBuffer sb = new StringBuffer();
		sb.append(getId());
		sb.append(banco);
		sb.append(idAgencia);
		sb.append(nome);
		sb.append(numero);
		sb.append(saldo);
		sb.append(situacao);
		sb.append(loja.getId());
		return sb.toString().hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ContaCorrente other = (ContaCorrente) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}
	public int compareTo(Object conta) {
		return getId().compareTo(((ContaCorrente)conta).getId());
	}
}