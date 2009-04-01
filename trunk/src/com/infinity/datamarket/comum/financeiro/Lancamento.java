package com.infinity.datamarket.comum.financeiro;

import java.math.BigDecimal;
import java.util.Date;

import com.infinity.datamarket.comum.pagamento.FormaRecebimento;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.util.Persistente;

public class Lancamento extends Persistente{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4821537507052249091L;
	
	public static final String CREDITO = "C";
	public static final String DEBITO = "D";
	
	private Loja loja;
	private Date dataLancamento;
	private Date dataVencimento;
	private Date dataPagamento;
	private String numeroDocumento;
	private String descricao;
	private String tipoLancamento;
	private BigDecimal valor;
	private String observacao;
	private FormaRecebimento forma;
	private GrupoLancamento grupo;
	private Long idEntradaProduto;
	
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
	public FormaRecebimento getForma() {
		return forma;
	}
	public void setForma(FormaRecebimento forma) {
		this.forma = forma;
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
	
}
