package com.infinity.datamarket.comum.boleto;

import java.math.BigDecimal;
import java.util.Date;

import org.jboleto.JBoleto;

import com.infinity.datamarket.comum.conta.ContaCorrente;
import com.infinity.datamarket.comum.util.Persistente;

public class Boleto extends Persistente{

	public static int GERADO = 0;
	public static int ATIVO = 1;
	public static int CANCELADO = 2;
	public static int PAGO = 3;

	private static final long serialVersionUID = 4520193234234231246L;

	public static int TIPO_BANCO_ITAU = JBoleto.ITAU;
	public static int TIPO_BANCO_BANCO_DO_BRASIL = JBoleto.BANCO_DO_BRASIL;
	public static int TIPO_BANCO_BANCO_GENERICO = JBoleto.BANCO_GENERICO;
	public static int TIPO_BANCO_BANCO_REAL = JBoleto.BANCO_REAL;
	public static int TIPO_BANCO_BRADESCO = JBoleto.BRADESCO;
	public static int TIPO_BANCO_CAIXA_ECONOMICA = JBoleto.CAIXA_ECONOMICA;
	public static int TIPO_BANCO_HSBC = JBoleto.HSBC;
	public static int TIPO_BANCO_NOSSACAIXA = JBoleto.NOSSACAIXA;
	public static int TIPO_BANCO_SANTANDER = JBoleto.SANTANDER;
	public static int TIPO_BANCO_UNIBANCO = JBoleto.UNIBANCO;

	private Integer status = new Integer(GERADO);
	private Integer cliente;
	private ContaCorrente contaCorrente;

	private Integer tipoBanco = 0;
   	private Date dataProcessamento = new Date();
   	private String cedente = null;
   	private String nossoNumero = null;
   	private String instrucao1 = null;
   	private String instrucao2 = null;
   	private String instrucao3 = null;
   	private String instrucao4 = null;
   	private String agencia = null;
   	private String carteira = null;
   	private String numeroContaCorrente = null;
   	private String digitoContaCorrente = null;

	private Date dataVencimento = null;
   	private BigDecimal valor = null;

   	private String nomeCliente = null;
   	private String enderecoCliente = null;
   	private String bairroCliente = null;
   	private String cidadeCliente = null;
   	private String UFCliente = null;
   	private String cpfCnpj = null;
   	private String cepCliente = null;

   	private Integer loja = null;
   	private Integer componente = null;
   	private Integer usuario = null;

   	private ArquivosProcessado arquivosProcessado;

	public Boleto(Date dataVencimento, int tipoBanco, Date dataProcessamento, String cedente, String nossoNumero, String instrucao1, String instrucao2, String instrucao3, String instrucao4, String agencia, String carteira, String contaCorrente, String digitoContaCorrente, BigDecimal valor) {
		super();
		this.dataVencimento = dataVencimento;
		this.tipoBanco = tipoBanco;
		this.dataProcessamento = dataProcessamento;
		this.cedente = cedente;
		this.nossoNumero = nossoNumero;
		this.instrucao1 = instrucao1;
		this.instrucao2 = instrucao2;
		this.instrucao3 = instrucao3;
		this.instrucao4 = instrucao4;
		this.agencia = agencia;
		this.carteira = carteira;
		this.numeroContaCorrente = contaCorrente;
		this.digitoContaCorrente = digitoContaCorrente;
		this.valor = valor;
	}
	public Boleto(Date dataVencimento, Integer tipoBanco, Date dataProcessamento, String cedente, String nossoNumero, String instrucao1, String instrucao2, String instrucao3, String instrucao4, String agencia, String carteira, String contaCorrente, String digitoContaCorrente, BigDecimal valor, String nomeCliente, String enderecoCliente, String bairroCliente, String cidadeCliente, String cliente, String cpfCnpj, String cepCliente) {
		super();
		this.dataVencimento = dataVencimento;
		this.tipoBanco = tipoBanco;
		this.dataProcessamento = dataProcessamento;
		this.cedente = cedente;
		this.nossoNumero = nossoNumero;
		this.instrucao1 = instrucao1;
		this.instrucao2 = instrucao2;
		this.instrucao3 = instrucao3;
		this.instrucao4 = instrucao4;
		this.agencia = agencia;
		this.carteira = carteira;
		this.numeroContaCorrente = contaCorrente;
		this.digitoContaCorrente = digitoContaCorrente;
		this.valor = valor;
		this.nomeCliente = nomeCliente;
		this.enderecoCliente = enderecoCliente;
		this.bairroCliente = bairroCliente;
		this.cidadeCliente = cidadeCliente;
		UFCliente = cliente;
		this.cpfCnpj = cpfCnpj;
		this.cepCliente = cepCliente;
	}
	public Boleto(){}
	
	public Integer getCliente() {
		return cliente;
	}
	public void setCliente(Integer cliente) {
		this.cliente = cliente;
	}
	public Integer getLoja() {
		return loja;
	}
	public void setLoja(Integer loja) {
		this.loja = loja;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getAgencia() {
		return agencia;
	}
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}
	public String getCarteira() {
		return carteira;
	}
	public void setCarteira(String carteira) {
		this.carteira = carteira;
	}
	public String getCedente() {
		return cedente;
	}
	public void setCedente(String cedente) {
		this.cedente = cedente;
	}
	public String getNumeroContaCorrente() {
		return numeroContaCorrente;
	}
	public void setNumeroContaCorrente(String contaCorrente) {
		this.numeroContaCorrente = contaCorrente;
	}
	public Date getDataProcessamento() {
		return dataProcessamento;
	}
	public void setDataProcessamento(Date dataProcessamento) {
		this.dataProcessamento = dataProcessamento;
	}
	public Date getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	public String getDigitoContaCorrente() {
		return digitoContaCorrente;
	}
	public void setDigitoContaCorrente(String digitoContaCorrente) {
		this.digitoContaCorrente = digitoContaCorrente;
	}
	public String getInstrucao1() {
		return instrucao1;
	}
	public void setInstrucao1(String instrucao1) {
		this.instrucao1 = instrucao1;
	}
	public String getInstrucao2() {
		return instrucao2;
	}
	public void setInstrucao2(String instrucao2) {
		this.instrucao2 = instrucao2;
	}
	public String getInstrucao3() {
		return instrucao3;
	}
	public void setInstrucao3(String instrucao3) {
		this.instrucao3 = instrucao3;
	}
	public String getInstrucao4() {
		return instrucao4;
	}
	public void setInstrucao4(String instrucao4) {
		this.instrucao4 = instrucao4;
	}
	public String getNossoNumero() {
		return nossoNumero;
	}
	public void setNossoNumero(String nossoNumero) {
		this.nossoNumero = nossoNumero;
	}
	public Integer getTipoBanco() {
		return tipoBanco;
	}
	public void setTipoBanco(Integer tipoBanco) {
		this.tipoBanco = tipoBanco;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public String getBairroCliente() {
		return bairroCliente;
	}
	public void setBairroCliente(String bairroCliente) {
		this.bairroCliente = bairroCliente;
	}
	public String getCepCliente() {
		return cepCliente;
	}
	public void setCepCliente(String cepCliente) {
		this.cepCliente = cepCliente;
	}
	public String getCidadeCliente() {
		return cidadeCliente;
	}
	public void setCidadeCliente(String cidadeCliente) {
		this.cidadeCliente = cidadeCliente;
	}
	public String getCpfCnpj() {
		return cpfCnpj;
	}
	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}
	public String getEnderecoCliente() {
		return enderecoCliente;
	}
	public void setEnderecoCliente(String enderecoCliente) {
		this.enderecoCliente = enderecoCliente;
	}
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public String getUFCliente() {
		return UFCliente;
	}
	public void setUFCliente(String cliente) {
		UFCliente = cliente;
	}
	public ContaCorrente getContaCorrente() {
		return contaCorrente;
	}
	public void setContaCorrente(ContaCorrente contaCorrente) {
		this.contaCorrente = contaCorrente;
	}
	public Integer getComponente() {
		return componente;
	}
	public void setComponente(Integer componente) {
		this.componente = componente;
	}
	public Integer getUsuario() {
		return usuario;
	}
	public void setUsuario(Integer usuario) {
		this.usuario = usuario;
	}
	public ArquivosProcessado getArquivosProcessado() {
		return arquivosProcessado;
	}
	public void setArquivosProcessado(ArquivosProcessado arquivosProcessado) {
		this.arquivosProcessado = arquivosProcessado;
	}
	
}
