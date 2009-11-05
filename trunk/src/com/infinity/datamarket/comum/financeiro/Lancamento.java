package com.infinity.datamarket.comum.financeiro;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.infinity.datamarket.comum.boleto.Boleto;
import com.infinity.datamarket.comum.fornecedor.Fornecedor;
import com.infinity.datamarket.comum.transacao.ClienteTransacao;
import com.infinity.datamarket.comum.transacao.Transacao;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.util.Persistente;

public class Lancamento extends Persistente{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4821537507052249091L;
	
	public static final String CREDITO = "C";
	public static final String DEBITO = "D";
	
	public static final String ABERTO = "A";
	public static final String PAGTO_PARCIAL = "P";
	public static final String FINALIZADO = "F";
	public static final String CANCELADO = "C";
	public static final String PENDENTE = "D";
	
	private Loja loja;
	private Date dataLancamento;
	private Date dataVencimento;
	private Date dataPagamento;
	private String numeroDocumento;
	private String descricao;
	private String tipoLancamento;
	private BigDecimal valor;
	private String observacao;
	private GrupoLancamento grupo;
	private Long idEntradaProduto;
	private Fornecedor fornecedor;
	private String situacao;
	private ClienteTransacao cliente;
	private Transacao transacao;

	private Collection<BaixaLancamento> itensPagamento;
	private Collection<BaixaLancamento> itensPagamentoExcluidos;

	public Transacao getTransacao() {
		return transacao;
	}

	public void setTransacao(Transacao transacao) {
		this.transacao = transacao;
	}

	public Collection<BaixaLancamento> getItensPagamento() {
		return itensPagamento;
	}

	public void setItensPagamento(Collection<BaixaLancamento> itensPagamento) {
		this.itensPagamento = itensPagamento;
	}
	
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	public Date getDataLancamento() {
		return dataLancamento;
	}
	public void setDataLancamento(Date dataLancamento) {
		this.dataLancamento = dataLancamento;
	}
	public Date getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public GrupoLancamento getGrupo() {
		return grupo;
	}
	public void setGrupo(GrupoLancamento grupo) {
		this.grupo = grupo;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getTipoLancamento() {
		return tipoLancamento;
	}
	public void setTipoLancamento(String tipoLancamento) {
		this.tipoLancamento = tipoLancamento;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public Date getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public Loja getLoja() {
		return loja;
	}
	public void setLoja(Loja loja) {
		this.loja = loja;
	}
	public Long getIdEntradaProduto() {
		return idEntradaProduto;
	}
	public void setIdEntradaProduto(Long idEntradaProduto) {
		this.idEntradaProduto = idEntradaProduto;
	}
	
	public int hashCode() {
		return getId().hashCode();
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Lancamento other = (Lancamento) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

	public Collection<BaixaLancamento> getItensPagamentoExcluidos() {
		return itensPagamentoExcluidos;
	}

	public void setItensPagamentoExcluidos(
			Collection<BaixaLancamento> itensPagamentoExcluidos) {
		this.itensPagamentoExcluidos = itensPagamentoExcluidos;
	}

	public ClienteTransacao getCliente() {
		return cliente;
	}

	public void setCliente(ClienteTransacao cliente) {
		this.cliente = cliente;
	}
}
