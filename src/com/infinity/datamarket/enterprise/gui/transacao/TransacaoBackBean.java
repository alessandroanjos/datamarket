package com.infinity.datamarket.enterprise.gui.transacao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import com.infinity.datamarket.autorizador.AutorizadorServerRemote;
import com.infinity.datamarket.autorizador.DadosAutorizacaoCartaoProprio;
import com.infinity.datamarket.comum.cliente.Cliente;
import com.infinity.datamarket.comum.componente.Componente;
import com.infinity.datamarket.comum.fornecedor.Fornecedor;
import com.infinity.datamarket.comum.pagamento.Autorizadora;
import com.infinity.datamarket.comum.pagamento.ConstantesFormaRecebimento;
import com.infinity.datamarket.comum.pagamento.FormaRecebimento;
import com.infinity.datamarket.comum.pagamento.PlanoPagamento;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter.IntervalObject;
import com.infinity.datamarket.comum.transacao.ClienteTransacao;
import com.infinity.datamarket.comum.transacao.ConstantesEventoTransacao;
import com.infinity.datamarket.comum.transacao.ConstantesTransacao;
import com.infinity.datamarket.comum.transacao.EventoItemPagamento;
import com.infinity.datamarket.comum.transacao.EventoItemPagamentoCartaoOff;
import com.infinity.datamarket.comum.transacao.EventoItemPagamentoCartaoProprio;
import com.infinity.datamarket.comum.transacao.EventoItemPagamentoCheque;
import com.infinity.datamarket.comum.transacao.EventoItemPagamentoChequePredatado;
import com.infinity.datamarket.comum.transacao.EventoItemRegistrado;
import com.infinity.datamarket.comum.transacao.EventoTransacao;
import com.infinity.datamarket.comum.transacao.EventoTransacaoPK;
import com.infinity.datamarket.comum.transacao.ParcelaEventoItemPagamentoChequePredatado;
import com.infinity.datamarket.comum.transacao.ParcelaEventoItemPagamentoChequePredatadoPK;
import com.infinity.datamarket.comum.transacao.ProdutoItemRegistrado;
import com.infinity.datamarket.comum.transacao.Transacao;
import com.infinity.datamarket.comum.transacao.TransacaoPK;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.usuario.Vendedor;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ConjuntoEventoTransacao;
import com.infinity.datamarket.comum.util.Constantes;
import com.infinity.datamarket.enterprise.gui.util.BackBean;
import com.infinity.datamarket.pdv.util.ServerConfig;
import com.infinity.datamarket.pdv.util.ServiceLocator;

public class TransacaoBackBean extends BackBean {

	private Hashtable<String, DadosAutorizacaoCartaoProprio> listaAutorizacaoCartaoProprio = new Hashtable<String, DadosAutorizacaoCartaoProprio>(); 
	private static int sequencialEventoTransacao = 0;
	private static int numeroParcelaChequePreDatado = 0;
	// aba principal
	private TransacaoPK id;
	private String idLoja;
	private String idComponente;
	private Integer nsuTransacao;
	private String numeroCupom;
	private String idVendedor;
	private String idOperador; // usuario logado
	private Date dataTransacao;
	// aba itens da transacao
	private String codigoProduto;
	private String descricaoProduto;
	private BigDecimal precoVenda;
	private BigDecimal quantidade;
	private BigDecimal descontoItem;
	private BigDecimal valorItem;
	private List<EventoItemRegistrado> itensTransacao = new ArrayList<EventoItemRegistrado>();
	private List<EventoItemRegistrado> itensTransacaoModificados = new ArrayList<EventoItemRegistrado>();
	private List<EventoItemPagamento> itensPagamento = new ArrayList<EventoItemPagamento>();
	
	private Date dataVencimento;
	// aba pagamento
	private BigDecimal valorFormaPagamento;
	private String idFormaPagamento;
	//cheques
	//a vista
	private String codigoBancoChqAvt;
	private String codigoAgenciaChqAvt;
	private String numeroContaChqAvt;
	private String numeroChequeChqAvt;
	private String cmc7ChqAvt;
	private String cpfCnpjClienteChqAvt;		
	//a prazo
	private String codigoBancoChqPrz;
	private String codigoAgenciaChqPrz;
	private String numeroContaChqPrz;
	private String numeroChequeChqPrz;
	private String cmc7ChqPrz;
	private String cpfCnpjClienteChqPrz;		
	private Date dataVencimentoChqPrz;
	private Set parcelasChequePreDatado;
	private BigDecimal valorEntradaChequePre;
	private boolean parcelaEntradaChequePre;
	// cartao debito/credito
	private String numeroCartao;
	private String codigoAutorizadora;
	private String codigoAutorizacao;
	private Integer quantidadeParcelasCartao;
	// cartao proprio
	private String cpfCnpjCliente;		
	private String idTipoPessoa = new String(Fornecedor.PESSOA_FISICA);
	private SelectItem[] listaTipoPessoa;
	// rodape
	private BigDecimal valorSubTotalCupom; //soma de todos os itens
	private BigDecimal valorTotalCupom; //valorSubTotalCupom - descontoCupom - valorTroco
	private BigDecimal descontoCupom;
	private BigDecimal valorTroco;
	private String idFormaTroco;	
	
	private BigDecimal valorComissaoVendedor;
	private Date dataInicial;
	private Date dataFinal;
	private BigDecimal valorTotalRecebido;
	private String idSituacao = TransacaoVenda.ATIVO;
	private SelectItem[] listaSituacao;
	
	private SelectItem[] lojas;
	private SelectItem[] componentes;
	private SelectItem[] formasPagamento;
	private SelectItem[] formasTroco;
	private SelectItem[] usuariosOperadores;
	private SelectItem[] usuariosVendedores;
	private SelectItem[] autorizadoras;	
	
	private Collection transacoes;
	
	String abaCorrente;
	
	private String idTipoPessoaCadastro;
	private String cpfCnpjClienteCadastro;
	private String nomeClienteCadastro;
	private String razaoSocialCadastro;
	private String inscricaoEstadualCadastro;
	private String inscricaoMunicipalCadastro;
	private String logradouroCadastro;
	private String numeroCadastro;
	private String complementoCadastro;
	private String bairroCadastro;
	private String cidadeCadastro;
	private String estadoCadastro;
	private String cepCadastro;
	private String foneResidencialCadastro;
	private String foneCelularCadastro;
	private String pessoaContatoCadastro;
	private String emailCadastro;
	private String foneContatoCadastro;
	private String referenciaComercialCadastro;
	String abaCadastroClienteCorrente;

	public String getAbaCorrente() {
		return abaCorrente;
	}

	public void setAbaCorrente(String abaCorrente) {
		this.abaCorrente = abaCorrente;
	}

	private List<Componente> carregarComponentes() {		
		List<Componente> componentes = null;
		try {
			componentes = (ArrayList<Componente>)getFachada().consultarTodosComponentes();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return componentes;
	}
	
	public SelectItem[] getComponentes() {
		SelectItem[] arrayComponentes = null;
		try {
			List<Componente> componentes = carregarComponentes();
			arrayComponentes = new SelectItem[componentes.size()];
			int i = 0;
			for(Componente componentesTmp : componentes){
				SelectItem item = new SelectItem(componentesTmp.getId().toString(), componentesTmp.getDescricao());
				arrayComponentes[i++] = item;
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return arrayComponentes;
	}

	public void setComponentes(SelectItem[] componentes) {
		this.componentes = componentes;
	}

	private List<FormaRecebimento> carregarFormas(IPropertyFilter filter) {		
		List<FormaRecebimento> formasTroco = null;
		try {
			formasTroco = (ArrayList<FormaRecebimento>)getFachada().consultarFormaRecebimento(filter);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return formasTroco;
	}
	
	public SelectItem[] getFormasPagamento() {
		SelectItem[] arrayFormas = null;
		try {
			PropertyFilter filter = new PropertyFilter();
			filter.setTheClass(FormaRecebimento.class);
			List<FormaRecebimento> formasPagamento = carregarFormas(filter);
			arrayFormas = new SelectItem[formasPagamento.size()];
			int i = 0;
			for(FormaRecebimento formasTmp : formasPagamento){
				SelectItem item = new SelectItem(formasTmp.getId().toString(), formasTmp.getDescricao());
				arrayFormas[i++] = item;
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return arrayFormas;
	}
	
	public SelectItem[] getFormasTroco() {
		SelectItem[] arrayFormas = null;
		try {
			PropertyFilter filter = new PropertyFilter();
			filter.setTheClass(FormaRecebimento.class);
//			filter.addProperty("formaTroco", null);
//			FormaRecebimento forma = new FormaRecebimento();
//			forma.setId(new Long(0));
			filter.addPropertyInterval("formaTroco", null, IntervalObject.NAO_NULO);
			
			List<FormaRecebimento> formasTroco = carregarFormas(filter);
			arrayFormas = new SelectItem[formasTroco.size()];
			int i = 0;
			for(FormaRecebimento formasTmp : formasTroco){
				SelectItem item = new SelectItem(formasTmp.getId().toString(), formasTmp.getDescricao());
				arrayFormas[i++] = item;
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return arrayFormas;
	}

	
	public void setFormasPagamento(SelectItem[] formasPagamento) {
		this.formasPagamento = formasPagamento;
	}

	public void setFormasTroco(SelectItem[] formasTroco) {
		this.formasTroco = formasTroco;
	}

	public String getIdComponente() {
		return idComponente;
	}

	public void setIdComponente(String idComponente) {
		this.idComponente = idComponente;
	}

	public String getIdFormaTroco() {
		return idFormaTroco;
	}

	public void setIdFormaTroco(String idFormaTroco) {
		this.idFormaTroco = idFormaTroco;
	}

	public String getIdLoja() {
		return idLoja;
	}

	public void setIdLoja(String idLoja) {
		this.idLoja = idLoja;
	}

	public String getIdOperador() {
		return idOperador;
	}

	public void setIdOperador(String idOperador) {
		this.idOperador = idOperador;
	}

	public String getIdVendedor() {
		return idVendedor;
	}

	public void setIdVendedor(String idVendedor) {
		this.idVendedor = idVendedor;
	}

	public List<EventoItemRegistrado> getItensTransacao() {
		return itensTransacao;
	}

	private List<Loja> carregarLojas() {		
		List<Loja> lojas = null;
		try {
			lojas = (ArrayList<Loja>)getFachada().consultarTodosLoja();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return lojas;
	}

	public SelectItem[] getLojas() {
		SelectItem[] arrayLojas = null;
		try {
			List<Loja> lojas = carregarLojas();
			arrayLojas = new SelectItem[lojas.size()];
			int i = 0;
			for(Loja lojasTmp : lojas){
				SelectItem item = new SelectItem(lojasTmp.getId().toString(), lojasTmp.getNome());
				arrayLojas[i++] = item;
			}
			if(this.getIdLoja() == null && arrayLojas.length > 0){
				this.setIdLoja(arrayLojas[0].getValue().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return arrayLojas;
	}

	public void setLojas(SelectItem[] lojas) {
		this.lojas = lojas;
	}

	public Integer getNsuTransacao() {
		return nsuTransacao;
	}

	public void setNsuTransacao(Integer nsuTransacao) {
		this.nsuTransacao = nsuTransacao;
	}

	public String getNumeroCupom() {
		return numeroCupom;
	}

	public void setNumeroCupom(String numeroCupom) {
		this.numeroCupom = numeroCupom;
	}

	public Collection getTransacoes() {
		return transacoes;
	}

	public void setTransacoes(Collection transacoes) {
		this.transacoes = transacoes;
	}
	
	public BigDecimal getValorTroco() {
		return valorTroco;
	}

	public void setValorTroco(BigDecimal valorTroco) {
		this.valorTroco = valorTroco;
	}
	
	public TransacaoPK getId() {
		return id;
	}

	public void setId(TransacaoPK id) {
		this.id = id;
	}

	public BigDecimal getValorComissaoVendedor() {
		return valorComissaoVendedor;
	}

	public void setValorComissaoVendedor(BigDecimal valorComissaoVendedor) {
		this.valorComissaoVendedor = valorComissaoVendedor;
	}

	private List<Usuario> carregarUsuarios(IPropertyFilter filter) {		
		List<Usuario> usuarios = null;
		try {
			usuarios = (ArrayList<Usuario>)getFachada().consultarUsuario(filter);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return usuarios;
	}

	public SelectItem[] getUsuariosVendedores() {
		SelectItem[] arrayUsuarios = null;
		try {
			Loja loja = null;
			if(this.getIdLoja() != null && !this.getIdLoja().equals("0")){
				loja = (Loja)getFachada().consultarLojaPorId(new Long(this.getIdLoja()));	
			}
			PropertyFilter filter = new PropertyFilter();
			filter.setTheClass(Vendedor.class);
			
			filter.addProperty("lojas", loja);
			List<Usuario> usuariosVendedores = carregarUsuarios(filter);
			arrayUsuarios = new SelectItem[usuariosVendedores.size()];
			int i = 0;
			for(Usuario usuariosTmp : usuariosVendedores){
				SelectItem item = new SelectItem(usuariosTmp.getId().toString(), usuariosTmp.getNome());
				arrayUsuarios[i++] = item;
			}
			if(this.getIdVendedor() == null && arrayUsuarios.length > 0){
				this.setIdVendedor(arrayUsuarios[0].getValue().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return arrayUsuarios;
	}
	
	public SelectItem[] getUsuariosOperadores() {
		SelectItem[] arrayUsuarios = null;
		try {
			Loja loja = null;
			if(this.getIdLoja() != null && !this.getIdLoja().equals("0")){
				loja = (Loja)getFachada().consultarLojaPorId(new Long(this.getIdLoja()));	
			}
			PropertyFilter filter = new PropertyFilter();
			filter.setTheClass(Usuario.class);
			filter.addProperty("lojas", loja);
			List<Usuario> usuariosOperadores = carregarUsuarios(filter);
			arrayUsuarios = new SelectItem[usuariosOperadores.size()];
			int i = 0;
			for(Usuario usuariosTmp : usuariosOperadores){
				SelectItem item = new SelectItem(usuariosTmp.getId().toString(), usuariosTmp.getNome());
				arrayUsuarios[i++] = item;
			}
			if(this.getIdOperador() == null && arrayUsuarios.length > 0){
				this.setIdOperador(arrayUsuarios[0].getValue().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return arrayUsuarios;
	}

	
	public void setUsuariosVendedores(SelectItem[] usuariosVendedores) {
		this.usuariosVendedores = usuariosVendedores;
	}

	public void setUsuariosOperadores(SelectItem[] usuariosOperadores) {
		this.usuariosOperadores = usuariosOperadores;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}
				
	public String getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(String codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public BigDecimal getDescontoItem() {
		return descontoItem;
	}

	public void setDescontoItem(BigDecimal descontoItem) {
		this.descontoItem = descontoItem;
	}

	public String getDescricaoProduto() {
		return descricaoProduto;
	}

	public void setDescricaoProduto(String descricaoProduto) {
		this.descricaoProduto = descricaoProduto;
	}

	public BigDecimal getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(BigDecimal precoVenda) {
		this.precoVenda = precoVenda;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getDescontoCupom() {
		return descontoCupom;
	}

	public void setDescontoCupom(BigDecimal descontoCupom) {
		this.descontoCupom = descontoCupom;
	}

	public BigDecimal getValorSubTotalCupom() {
		return valorSubTotalCupom;
	}

	public void setValorSubTotalCupom(BigDecimal valorSubTotalCupom) {
		this.valorSubTotalCupom = valorSubTotalCupom;
	}

	public BigDecimal getValorTotalCupom() {
		return valorTotalCupom;
	}

	public void setValorTotalCupom(BigDecimal valorTotalCupom) {
		this.valorTotalCupom = valorTotalCupom;
	}
	
	public String getCpfCnpjCliente() {
		return cpfCnpjCliente;
	}

	public void setCpfCnpjCliente(String cpfCnpjCliente) {
		this.cpfCnpjCliente = cpfCnpjCliente;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}	

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}
	
	public Date getDataTransacao() {
		return dataTransacao;
	}

	public void setDataTransacao(Date dataTransacao) {
		this.dataTransacao = dataTransacao;
	}
	
	public String getCodigoAutorizacao() {
		return codigoAutorizacao;
	}

	public void setCodigoAutorizacao(String codigoAutorizacao) {
		this.codigoAutorizacao = codigoAutorizacao;
	}

	public String getCodigoAutorizadora() {
		return codigoAutorizadora;
	}

	public void setCodigoAutorizadora(String codigoAutorizadora) {
		this.codigoAutorizadora = codigoAutorizadora;
	}

	public String getIdFormaPagamento() {
		return idFormaPagamento;
	}

	public void setIdFormaPagamento(String idFormaPagamento) {
		this.idFormaPagamento = idFormaPagamento;
	}

	public Integer getQuantidadeParcelasCartao() {
		return quantidadeParcelasCartao;
	}

	public void setQuantidadeParcelasCartao(Integer quantidadeParcelasCartao) {
		this.quantidadeParcelasCartao = quantidadeParcelasCartao;
	}

	public BigDecimal getValorFormaPagamento() {
		return valorFormaPagamento;
	}

	public void setValorFormaPagamento(BigDecimal valorFormaPagamento) {
		this.valorFormaPagamento = valorFormaPagamento;
	}

	public void setItensTransacao(List<EventoItemRegistrado> itensTransacao) {
		this.itensTransacao = itensTransacao;
	}
	
	public Set getParcelasChequePreDatado() {
		return parcelasChequePreDatado;
	}

	public void setParcelasChequePreDatado(Set parcelasChequePreDatado) {
		this.parcelasChequePreDatado = parcelasChequePreDatado;
	}

	public BigDecimal getValorEntradaChequePre() {
		return valorEntradaChequePre;
	}

	public void setValorEntradaChequePre(BigDecimal valorEntradaChequePre) {
		this.valorEntradaChequePre = valorEntradaChequePre;
	}
	
	public boolean isParcelaEntradaChequePre() {
		return parcelaEntradaChequePre;
	}

	public void setParcelaEntradaChequePre(boolean parcelaEntradaChequePre) {
		this.parcelaEntradaChequePre = parcelaEntradaChequePre;
	}
	
	public String voltarConsulta(){
		resetBB();
//		consultar();
		return "voltar";
	}
	public String voltarMenu(){
		resetBB();
		return "voltar";
	}

	public void resetBB(){
		this.setId(null);
		this.setIdLoja("0");
		this.setLojas(null);
		this.setIdComponente("0");
		this.setComponentes(null);
		this.setIdVendedor("0");
		this.setIdOperador("0");
		this.setUsuariosVendedores(null);
		this.setUsuariosOperadores(null);
		this.setIdFormaTroco("0");
		this.setIdFormaPagamento(null);
		this.setFormasTroco(null);
		this.setFormasPagamento(null);
		this.setDataTransacao(null);
		this.setNsuTransacao(null);
		this.setNumeroCupom(null);
		this.setCodigoProduto(null);
		this.setDescricaoProduto(null);
		this.setPrecoVenda(new BigDecimal("0.00"));
		this.setQuantidade(new BigDecimal("0.000"));
		this.setDescontoItem(new BigDecimal("0.00"));
		this.setValorFormaPagamento(new BigDecimal("0.00"));
		this.setIdTipoPessoa(Cliente.PESSOA_FISICA);
		this.setCpfCnpjClienteChqAvt(null);
		this.setCodigoBancoChqAvt(null);
		this.setCodigoAgenciaChqAvt(null);
		this.setNumeroContaChqAvt(null);
		this.setNumeroChequeChqAvt(null);
		this.setCmc7ChqAvt(null);
		this.setCodigoBancoChqPrz(null);
		this.setCodigoAgenciaChqPrz(null);
		this.setNumeroContaChqPrz(null);
		this.setNumeroChequeChqPrz(null);
		this.setCmc7ChqPrz(null);
		this.setDataVencimentoChqPrz(null);
		this.setCpfCnpjClienteChqPrz(null);
		this.setDataVencimento(null);
		this.setNumeroCartao(null);
		this.setCodigoAutorizadora(null);
		this.setCodigoAutorizacao(null);
		this.setQuantidadeParcelasCartao(null);
		this.setCpfCnpjCliente(null);
		this.setItensTransacao(null);
		this.setItensTransacaoModificados(null);
		this.setItensPagamento(null);
		this.setValorSubTotalCupom(new BigDecimal("0.00"));
		this.setValorTotalRecebido(new BigDecimal("0.00"));
		this.setDescontoCupom(new BigDecimal("0.00"));
		this.setValorTroco(new BigDecimal("0.00"));
		this.setValorTotalCupom(new BigDecimal("0.00"));
		this.setIdTipoPessoaCadastro(Cliente.PESSOA_FISICA);
		this.setCpfCnpjClienteCadastro(null);
		this.setNomeClienteCadastro(null);
		this.setRazaoSocialCadastro(null);
		this.setInscricaoEstadualCadastro(null);
		this.setInscricaoMunicipalCadastro(null);
		this.setEmailCadastro(null);
		this.setLogradouroCadastro(null);
		this.setNumeroCadastro(null);
		this.setComplementoCadastro(null);
		this.setBairroCadastro(null);
		this.setCidadeCadastro(null);
		this.setEstadoCadastro(null);
		this.setCepCadastro(null);
		this.setFoneResidencialCadastro(null);
		this.setFoneCelularCadastro(null);
		this.setPessoaContatoCadastro(null);
		this.setFoneContatoCadastro(null);
		this.setReferenciaComercialCadastro(null);
	}
	
	public String consultar(){
		try{
			FacesContext context = FacesContext.getCurrentInstance();
			Map params = context.getExternalContext().getRequestParameterMap();
			TransacaoPK param = null;
			if(params != null){
				param = new TransacaoPK();
				if(params.get("transacao_loja") != null && !params.get("transacao_loja").equals("")){
					param.setLoja(Integer.parseInt(params.get("transacao_loja").toString()));
				}
				if(params.get("transacao_componente") != null && !params.get("transacao_componente").equals("")){
					param.setComponente(Integer.parseInt(params.get("transacao_componente").toString()));
				}
				if(params.get("transacao_dataTransacao") != null && !params.get("transacao_dataTransacao").equals("")){
					DateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
					System.out.println(dt.parse((String)params.get("transacao_dataTransacao")));
					param.setDataTransacao(dt.parse((String)params.get("transacao_dataTransacao")));
				}
				if(params.get("transacao_numeroTransacao") != null && !params.get("transacao_numeroTransacao").equals("")){
					param.setNumeroTransacao(new Integer(params.get("transacao_numeroTransacao").toString()).intValue());
				}
			}
			if (param != null){
				setId(param);
			}
			if (getId() != null && getId().getLoja() != 0 && getId().getComponente() != 0 &&
					getId().getNumeroTransacao() != 0 && getId().getDataTransacao() != null){
				Transacao transacao = getFachada().consultarTransacaoPorPK(getId());
				
				preencheBackBean((TransacaoVenda)transacao);
				
				return "proxima";
			} else if((this.getIdLoja() != null && !this.getIdLoja().equals("0")) 
					|| (this.getIdComponente() != null && !this.getIdComponente().equals("0"))
					|| (this.getNumeroCupom() != null && !this.getNumeroCupom().equals(""))
					|| (this.getDataInicial() != null && this.getDataFinal() != null)){
				
				PropertyFilter filter = new PropertyFilter();
				
				TransacaoPK transPk = new TransacaoPK();
				
				if (this.getIdLoja() != null && !this.getIdLoja().equals("0")){	
					transPk.setLoja(Integer.parseInt(this.getIdLoja()));
//					filter.addProperty("pk.loja", this.getIdLoja());
				}
				if (this.getIdComponente() != null && !this.getIdComponente().equals("0")){	
					transPk.setComponente(Integer.parseInt(this.getIdComponente()));
//					filter.addProperty("pk.componente", this.getIdComponente());
				}
				if (this.getNsuTransacao() != null && !this.getNsuTransacao().equals("")){
					transPk.setNumeroTransacao(this.getNsuTransacao().intValue());
//					filter.addProperty("pk.numeroTransacao", this.getNsuTransacao());
				}
				
				filter.addProperty("pk", transPk);
				
				if (this.getDataInicial() != null && this.getDataFinal() != null){	
					if(this.getDataInicial().after(this.getDataFinal())){
						throw new Exception("A Data Final deve ser maior que a Data Inicial.");
					}
					
					filter.addPropertyInterval("pk.dataTransacao", this.getDataInicial(), IntervalObject.MAIOR_IGUAL);
					this.getDataFinal().setDate(this.getDataFinal().getDate()+1);
					filter.addPropertyInterval("pk.dataTransacao", this.getDataFinal(), IntervalObject.MENOR_IGUAL);
				}
				
				if(this.getIdSituacao() != null && !this.getIdSituacao().equals("0")){
					filter.addProperty("situacao", this.getIdSituacao());
				}
				
				filter.setTheClass(Transacao.class);

				Collection col = getFachada().consultarTransacao(filter);
				
				if (col == null || col.size() == 0){
					setExisteRegistros(false);
					this.setTransacoes(null);
					FacesContext ctx = FacesContext.getCurrentInstance();
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					ctx.addMessage(null, msg);					
				}else if (col != null){
					if(col.size() == 1){
						
						preencheBackBean((TransacaoVenda)col.iterator().next());

						return "proxima";
					}else{
						setExisteRegistros(true);
						setTransacoes(col);
					}
				}
			}else{
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(TransacaoVenda.class);
				Collection c = getFachada().consultarTransacao(filter);
				if(c != null && c.size() > 0){
					this.setTransacoes(c);
					setExisteRegistros(true);
				}else{
					this.setTransacoes(null);
					setExisteRegistros(false);
				}
			}
		}catch(ObjectNotFoundException e){
			setExisteRegistros(false);
			this.setTransacoes(null);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Nenhum Registro Encontrado", "");
			ctx.addMessage(null, msg);			
		}catch(Exception e){
			setExisteRegistros(false);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return "mesma";
	}
	
	public void preencheBackBean(TransacaoVenda transacao){
		this.setId(transacao.getPk());
		this.setIdLoja(new Integer(transacao.getPk().getLoja()).toString());
		this.setIdComponente(new Integer(transacao.getPk().getComponente()).toString());
		this.setDataTransacao(transacao.getPk().getDataTransacao());
		this.setNsuTransacao(transacao.getPk().getNumeroTransacao());
		this.setNumeroCupom(transacao.getNumeroCupom());

		this.setIdOperador(transacao.getCodigoUsuarioOperador());
		this.setIdVendedor(transacao.getCodigoUsuarioVendedor());

		if(this.getItensTransacao() == null){
			this.setItensTransacao(new ArrayList<EventoItemRegistrado>());
		}
		if(this.getItensPagamento() == null){
			this.setItensPagamento(new ArrayList<EventoItemPagamento>());
		}
		sequencialEventoTransacao = transacao.getEventosTransacao().size();
		Iterator it = transacao.getEventosTransacao().iterator();
		while(it.hasNext()){
			EventoTransacao evento = (EventoTransacao)it.next();
			if(evento != null){
				if(evento instanceof EventoItemRegistrado){
					EventoItemRegistrado ev = (EventoItemRegistrado)evento;
					ev.setAcao(EventoItemRegistrado.ITEM_NAO_ALTERADO);
					this.getItensTransacao().add(ev);									
				}else if(evento instanceof EventoItemPagamento){
					EventoItemPagamento ev = (EventoItemPagamento)evento;
					ev.setDescricaoForma(ConstantesFormaRecebimento.retornaDescricaoFormaRecebimento(ev.getCodigoForma()));	
					this.setValorTotalRecebido(this.getValorTotalRecebido().add(ev.getValorBruto()));
					this.getItensPagamento().add(ev);
				}
			}
		}

		this.setValorSubTotalCupom(transacao.getValorCupom().add(transacao.getValorTroco()));
		this.setDescontoCupom(transacao.getDescontoCupom());
		this.setValorTroco(transacao.getValorTroco());
		
		if(transacao.getFormaTroco() != null){
			this.setIdFormaTroco(transacao.getFormaTroco().getId().toString());	
		}else{
			this.setIdFormaTroco("0");	
		}			
		this.setValorTotalCupom(transacao.getValorCupom());
		
		this.setValorComissaoVendedor(transacao.getComissaoUsuarioVendedor());
	}
	
	public void validaCabecalhoTransacao() throws Exception{
		if(this.getIdLoja() == null || (this.getIdLoja() != null && this.getIdLoja().equals("0"))){
			this.setAbaCorrente("tabMenuDiv0");
			this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
			throw new Exception("É necessário informar a Loja.");
		}
		if(this.getIdComponente() == null || (this.getIdComponente() != null && this.getIdComponente().equals("0"))){
			this.setAbaCorrente("tabMenuDiv0");
			this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
			throw new Exception("É necessário informar o Componente.");
		}
		if(this.getDataTransacao() == null || (this.getDataTransacao() != null && this.getDataTransacao().equals("0"))){
			this.setAbaCorrente("tabMenuDiv0");
			this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
			throw new Exception("É necessário informar a Data da Transação");
		}
		if(this.getNsuTransacao() == null || (this.getNsuTransacao() != null && this.getNsuTransacao().equals(""))){
			this.setAbaCorrente("tabMenuDiv0");
			this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
			throw new Exception("É necessário informar o Número da Transação.");
		}
		if(this.getNumeroCupom() == null || (this.getNumeroCupom() != null && this.getNumeroCupom().equals(""))){
			this.setAbaCorrente("tabMenuDiv0");
			this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
			throw new Exception("É necessário informar o Número do Cupom.");
		}
	}
	
	public Produto validarItemRegistrado() throws Exception{
		Produto produto = null;
		if(this.getCodigoProduto() == null || (this.getCodigoProduto() != null && this.getCodigoProduto().equals("0"))){
			this.setAbaCorrente("tabMenuDiv1");
			this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
			throw new Exception("É necessário informar um produto.");
		}else{
			produto = getFachada().consultarProdutoPorPK(new Long(this.getCodigoProduto()));
			if(produto == null){
				this.setAbaCorrente("tabMenuDiv1");
				this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
				throw new Exception("O Produto informado é inválido!");
			}
		}
		if(this.getPrecoVenda() == null || (this.getPrecoVenda() != null && this.getPrecoVenda().compareTo(BigDecimal.ZERO.setScale(2)) <= 0)){
			this.setAbaCorrente("tabMenuDiv1");
			this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
			throw new Exception("O Preço unitário do produto informado é inválido!");
		}
		if(this.getQuantidade() == null || (this.getQuantidade() != null && this.getQuantidade().compareTo(BigDecimal.ZERO.setScale(3)) <= 0)){
			this.setAbaCorrente("tabMenuDiv1");
			this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
			throw new Exception("A Quantidade informada é inválida!");
		}
		if(this.getDescontoItem() != null && (this.getDescontoItem().compareTo(BigDecimal.ZERO.setScale(2)) < 0 && this.getDescontoItem().compareTo(new BigDecimal("99.99")) > 0)){
			this.setAbaCorrente("tabMenuDiv1");
			this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
			throw new Exception("O Desconto informado é inválido!");
		}
		return produto;
	}
	
	public String inserirItemTransacao(){
		BigDecimal valorTotalItem = new BigDecimal("0.00");
		Produto produto = null;
		try {			
			validaCabecalhoTransacao();
			produto = validarItemRegistrado();			
			
			ProdutoItemRegistrado produtoItemRegistrado = new ProdutoItemRegistrado();
			
			produtoItemRegistrado.setPk(new EventoTransacaoPK());
			produtoItemRegistrado.getPk().setLoja(Integer.parseInt(this.getIdLoja()));
			produtoItemRegistrado.getPk().setComponente(Integer.parseInt(this.getIdComponente()));
			produtoItemRegistrado.getPk().setNumeroTransacao(this.getNsuTransacao().intValue());
			produtoItemRegistrado.getPk().setDataTransacao(this.getDataTransacao());
			produtoItemRegistrado.getPk().setNumeroEvento(++sequencialEventoTransacao);
			
			if(produto == null){
				produto = getFachada().consultarProdutoPorPK(new Long(this.getCodigoProduto()));	
			}
			
			produtoItemRegistrado.setCodigoExterno(produto.getCodigoExterno());
			produtoItemRegistrado.setIdProduto(produto.getId().intValue());
			produtoItemRegistrado.setDescricaoCompleta(produto.getDescricaoCompleta());
			produtoItemRegistrado.setPrecoPadrao(produto.getPrecoPadrao());
			valorTotalItem = this.getPrecoVenda().multiply(this.getQuantidade()).
								subtract(this.getDescontoItem()).setScale(2, RoundingMode.HALF_DOWN);
			produtoItemRegistrado.setPrecoPraticado(this.getPrecoVenda());
			produtoItemRegistrado.setImpostoImpressora(produto.getImposto().getImpostoImpressora());
			produtoItemRegistrado.setPercentual(produto.getImposto().getPercentual());

			EventoItemRegistrado eventoItemRegistrado = 
					new EventoItemRegistrado(produtoItemRegistrado.getPk(), 
											 ConstantesEventoTransacao.EVENTO_ITEM_REGISTRADO,
											 new Date(), 
											 this.getQuantidade(), 
											 this.getDescontoItem(), 
											 valorTotalItem, 
											 produtoItemRegistrado);
			
//			if(this.getOperacao().equals("I")){
			eventoItemRegistrado.setAcao("I");
//			}else if(this.getOperacao().equals("A")){
//				eventoItemRegistrado.setAcao("A");
//			}
			
			if(this.getItensTransacao() == null){
				this.setItensTransacao(new ArrayList<EventoItemRegistrado>());
			}
		
			this.getItensTransacao().add(eventoItemRegistrado);
			
			this.setValorSubTotalCupom(this.getValorSubTotalCupom().add(valorTotalItem));
			
			this.setValorTotalCupom(this.getValorSubTotalCupom().subtract(this.getDescontoCupom()).subtract(this.getValorTroco()));
			
//			if(this.getValorTotalRecebido() != null && !this.getValorTotalRecebido().equals(BigDecimal.ZERO) && this.getValorTotalRecebido().compareTo(this.getValorSubTotalCupom()) > 0){
//				this.setValorTroco(this.getValorTotalRecebido().
//						subtract(this.getValorSubTotalCupom().
//								subtract((this.getDescontoCupom() != null && !this.getDescontoCupom().equals(BigDecimal.ZERO) ? this.getDescontoCupom(): BigDecimal.ZERO))));
//			}
//			if(this.getValorTotalRecebido() != null && !this.getValorTotalRecebido().equals(BigDecimal.ZERO) && this.getValorTotalRecebido().compareTo(this.getValorSubTotalCupom()) > 0){
//				this.setValorTroco(this.getValorTotalRecebido().
//						subtract(this.getValorSubTotalCupom().
//								subtract((this.getDescontoCupom() != null && !this.getDescontoCupom().equals(BigDecimal.ZERO) ? this.getDescontoCupom(): BigDecimal.ZERO))));
//				this.setValorTotalCupom(this.getValorTotalRecebido().
//						subtract(this.getValorSubTotalCupom().
//								subtract((this.getDescontoCupom() != null ? this.getDescontoCupom() : BigDecimal.ZERO).
//										subtract(this.getValorTroco()))));
//			}else{
//				this.setValorTroco(new BigDecimal("0.00"));
//				this.setValorTotalCupom(this.getValorSubTotalCupom().
//								subtract((this.getDescontoCupom() != null ? this.getDescontoCupom() : BigDecimal.ZERO).
//										subtract(this.getValorTroco())));
//			}
			atualizarTotais();
			this.setCodigoProduto("");
			this.setDescricaoProduto("");
			this.setPrecoVenda(new BigDecimal("0.00"));
			this.setQuantidade(new BigDecimal("1.000"));
			this.setDescontoItem(new BigDecimal("0.00"));
			this.setValorItem(new BigDecimal("0.00"));
			this.setAbaCorrente("tabMenuDiv1");
			this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return "mesma";
	}
	
	public void atualizarTotais(){
		BigDecimal valorLiquido = BigDecimal.ZERO;
		BigDecimal valorTroco = BigDecimal.ZERO;
//		calculo do valor total do cupom
//		if(this.getValorTotalRecebido().compareTo(this.getValorSubTotalCupom()) > 0){
//			valorLiquido = this.getValorTotalRecebido().subtract(this.getValorSubTotalCupom());
//		}else{
//			valorLiquido = this.getValorSubTotalCupom();
//		}
//		if(this.getDescontoCupom().compareTo(BigDecimal.ZERO) > 0){
//		   valorLiquido = valorLiquido.subtract(this.getDescontoCupom());
//		}
		valorLiquido = this.getValorSubTotalCupom().subtract(this.getDescontoCupom());
		this.setValorTotalCupom(valorLiquido);
		
		//calcula o valor do troco
		if(this.getValorTotalRecebido().compareTo(valorLiquido) > 0){
			valorTroco = this.getValorTotalRecebido().subtract(valorLiquido);
		}
		this.setValorTroco(valorTroco.setScale(2));
	}

	public String removerItemTransacao(){
		FacesContext context = FacesContext.getCurrentInstance();
		Map params = context.getExternalContext().getRequestParameterMap();  
		String param = (String)  params.get("idExcluirItemRegistrado");

		if(this.getItensTransacaoModificados() == null){
			this.setItensTransacaoModificados(new ArrayList<EventoItemRegistrado>());
		}
		
		Iterator i = this.getItensTransacao().iterator();
		while(i.hasNext()){
			EventoItemRegistrado itemTransacao = (EventoItemRegistrado) i.next();
			if (itemTransacao.getPk().getNumeroEvento() == Integer.parseInt(param)){
				this.getItensTransacao().remove(itemTransacao);
				this.setValorSubTotalCupom(this.getValorSubTotalCupom().subtract(itemTransacao.getPreco()));
//				this.setValorTotalCupom(this.getValorSubTotalCupom().subtract(this.getDescontoCupom()));
				//incluir na lista de itens alterados/excluidos
				itemTransacao.setAcao("E");
				this.getItensTransacaoModificados().add(itemTransacao);
				break;
			}
		}
//		if(this.getValorTotalRecebido() != null && !this.getValorTotalRecebido().equals(BigDecimal.ZERO) && this.getValorTotalRecebido().compareTo(this.getValorSubTotalCupom()) > 0){
//			this.setValorTroco(this.getValorTotalRecebido().
//					subtract(this.getValorSubTotalCupom().
//							subtract((this.getDescontoCupom() != null && !this.getDescontoCupom().equals(BigDecimal.ZERO) ? this.getDescontoCupom(): BigDecimal.ZERO))));
//			this.setValorTotalCupom(this.getValorTotalRecebido().
//					subtract(this.getValorSubTotalCupom().
//							subtract((this.getDescontoCupom() != null ? this.getDescontoCupom() : BigDecimal.ZERO).
//									subtract(this.getValorTroco()))));
//		}else{
//			this.setValorTroco(new BigDecimal("0.00"));
//			this.setValorTotalCupom(this.getValorSubTotalCupom().
//							subtract((this.getDescontoCupom() != null ? this.getDescontoCupom() : BigDecimal.ZERO).
//									subtract(this.getValorTroco())));
//		}
		atualizarTotais();
		this.setAbaCorrente("tabMenuDiv1");
		this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
		return "mesma";
	}
	
	public void validarInclusaoItemPagamento() throws AppException{
		if(this.getValorFormaPagamento() == null || (this.getValorFormaPagamento() != null && this.getValorFormaPagamento().equals(""))){
			throw new AppException("É necessário informar o Valor do Pagamento.");
		}							
		if(this.getIdFormaPagamento().equals(ConstantesFormaRecebimento.CHEQUE.toString())){
			if(this.getCodigoBancoChqAvt() == null && this.getCodigoAgenciaChqAvt() == null && this.getNumeroContaChqAvt() == null
					&& this.getNumeroChequeChqAvt() == null && this.getCpfCnpjClienteChqAvt() == null && this.getCmc7ChqAvt() == null){
				throw new AppException("É necessário informar os dados do Cheque.");	
			}else{
				if(this.getCmc7ChqAvt() == null){
					if(this.getCodigoBancoChqAvt() == null){
						throw new AppException("É necessário informar o Código do Banco.");
					}
					if(this.getCodigoAgenciaChqAvt() == null){
						throw new AppException("É necessário informar o Código da Agência.");
					}
					if(this.getNumeroContaChqAvt() == null){
						throw new AppException("É necessário informar o Número da Conta.");
					}
					if(this.getNumeroChequeChqAvt() == null){
						throw new AppException("É necessário informar o Número do Cheque.");
					}
					if(this.getCpfCnpjClienteChqAvt() == null){
						throw new AppException("É necessário informar o CPF/CNPJ do Cliente.");
					}
				}					
			}
		}else if(this.getIdFormaPagamento().equals(ConstantesFormaRecebimento.CHEQUE_PRE.toString())){
			if(this.getParcelasChequePreDatado() == null || (this.getParcelasChequePreDatado() != null && this.getParcelasChequePreDatado().size() <= 0)){
				throw new AppException("É necessário informar as Parcelas do Plano.");
			}
		}else if(this.getIdFormaPagamento().equals(ConstantesFormaRecebimento.CARTAO_OFF.toString())){
			if(this.getNumeroCartao() == null || (this.getNumeroCartao() != null && this.getNumeroCartao().equals(""))){
				throw new AppException("É necessário informar o Número do Cartão.");
			}
			if(this.getCodigoAutorizadora() == null || (this.getCodigoAutorizadora() != null && this.getCodigoAutorizadora().equals("0"))){
				throw new AppException("É necessário informar a Autorizadora.");
			}
			if(this.getCodigoAutorizacao() == null || (this.getCodigoAutorizacao() != null && this.getCodigoAutorizacao().equals(""))){
				throw new AppException("É necessário informar o Código da Autorização.");
			}
			if(this.getQuantidadeParcelasCartao() == null || (this.getQuantidadeParcelasCartao() != null && (this.getQuantidadeParcelasCartao().equals("") || this.getQuantidadeParcelasCartao().equals("0")))){
				throw new AppException("É necessário informar a Quantidade de Parcelas.");
			}
		}else if(this.getIdFormaPagamento().equals(ConstantesFormaRecebimento.CARTAO_PROPRIO.toString())){
			if(this.getCpfCnpjCliente() == null || (this.getCpfCnpjCliente() != null && this.getCpfCnpjCliente().equals(""))){
				throw new AppException("É necessário informar o CPF/CNPJ do Cliente.");
			}
		}		
	}
	
	public String inserirItemPagamento(){
		if(this.getItensPagamento() == null){
			this.setItensPagamento(new ArrayList<EventoItemPagamento>());
		}		
		try {			
			validarInclusaoItemPagamento();
			
			EventoTransacaoPK eventoPk = new EventoTransacaoPK();
			eventoPk.setLoja(Integer.parseInt(this.getIdLoja()));
			eventoPk.setComponente(Integer.parseInt(this.getIdComponente()));
			eventoPk.setDataTransacao(this.getDataTransacao());
			eventoPk.setNumeroTransacao(this.getNsuTransacao());
			eventoPk.setNumeroEvento(++sequencialEventoTransacao);
			if(this.getIdFormaPagamento().equals(ConstantesFormaRecebimento.DINHEIRO.toString())){
				EventoItemPagamento evItemPagamento = new EventoItemPagamento();				
				evItemPagamento.setPk(eventoPk);
				evItemPagamento.setTipoEvento(ConstantesEventoTransacao.EVENTO_ITEM_PAGAMENTO);
				
				evItemPagamento.setCodigoForma(ConstantesFormaRecebimento.DINHEIRO.intValue());			
				FormaRecebimento forma = getFachada().consultarFormaRecebimentoPorId(ConstantesFormaRecebimento.DINHEIRO);
				evItemPagamento.setDescricaoForma(forma.getDescricao());
				int codigoPlano = 0;
				Iterator it = forma.getPlanos().iterator();
				while (it.hasNext()){
					PlanoPagamento planoTmp = (PlanoPagamento)it.next();
					codigoPlano = planoTmp.getId().intValue();
					break;
				}				
				evItemPagamento.setCodigoPlano(codigoPlano);
				evItemPagamento.setDataHoraEvento(new Date(System.currentTimeMillis()));
				evItemPagamento.setFormaImpressora(forma.getRecebimentoImpressora());
				evItemPagamento.setValorAcrescimo(BigDecimal.ZERO);
				evItemPagamento.setValorDesconto(BigDecimal.ZERO);
				evItemPagamento.setValorBruto(this.getValorFormaPagamento());				
				this.getItensPagamento().add(evItemPagamento);
				this.setValorTotalRecebido(this.getValorTotalRecebido().add(evItemPagamento.getValorBruto()));
				//limpar campos
			}else if(this.getIdFormaPagamento().equals(ConstantesFormaRecebimento.CHEQUE.toString())){
				EventoItemPagamentoCheque evItemPagamentoCheque = new EventoItemPagamentoCheque();				
				evItemPagamentoCheque.setPk(eventoPk);
				evItemPagamentoCheque.setTipoEvento(ConstantesEventoTransacao.EVENTO_ITEM_PAGAMENTO);	
				
				evItemPagamentoCheque.setCodigoForma(ConstantesFormaRecebimento.CHEQUE.intValue());			
				FormaRecebimento forma = getFachada().consultarFormaRecebimentoPorId(ConstantesFormaRecebimento.CHEQUE);
				evItemPagamentoCheque.setDescricaoForma(forma.getDescricao());
				int codigoPlano = 0;
				Iterator it = forma.getPlanos().iterator();
				while (it.hasNext()){
					PlanoPagamento planoTmp = (PlanoPagamento)it.next();
					codigoPlano = planoTmp.getId().intValue();
					break;
				}				
				evItemPagamentoCheque.setCodigoPlano(codigoPlano);
				evItemPagamentoCheque.setDataHoraEvento(new Date(System.currentTimeMillis()));
				evItemPagamentoCheque.setFormaImpressora(forma.getRecebimentoImpressora());
				evItemPagamentoCheque.setValorAcrescimo(BigDecimal.ZERO);
				evItemPagamentoCheque.setValorDesconto(BigDecimal.ZERO);
				evItemPagamentoCheque.setValorBruto(this.getValorFormaPagamento());
				if(this.getCmc7ChqAvt() != null && !this.getCmc7ChqAvt().equals("")){
					evItemPagamentoCheque.setNumeroChequeLido(this.getCmc7ChqAvt());
				}else{
					evItemPagamentoCheque.setBanco(this.getCodigoBancoChqAvt());
					evItemPagamentoCheque.setAgencia(this.getCodigoAgenciaChqAvt());
					evItemPagamentoCheque.setConta(this.getNumeroContaChqAvt());
					evItemPagamentoCheque.setNumeroCheque(this.getNumeroChequeChqAvt());
					evItemPagamentoCheque.setCPFCNPJ(this.getCpfCnpjClienteChqAvt());
				}
				this.getItensPagamento().add(evItemPagamentoCheque);
				this.setValorTotalRecebido(this.getValorTotalRecebido().add(evItemPagamentoCheque.getValorBruto()));
				this.setCodigoBancoChqAvt(null);
				this.setCodigoAgenciaChqAvt(null);
				this.setNumeroContaChqAvt(null);
				this.setNumeroChequeChqAvt(null);
				this.setCpfCnpjClienteChqAvt(null);
				this.setCmc7ChqAvt(null);
				this.setIdTipoPessoa(Cliente.PESSOA_FISICA);
			}else if(this.getIdFormaPagamento().equals(ConstantesFormaRecebimento.CHEQUE_PRE.toString())){
				EventoItemPagamentoChequePredatado evItemPagamentoChequePreDatado = new EventoItemPagamentoChequePredatado();				
				evItemPagamentoChequePreDatado.setPk(eventoPk);
				evItemPagamentoChequePreDatado.setTipoEvento(ConstantesEventoTransacao.EVENTO_ITEM_PAGAMENTO);
				
				evItemPagamentoChequePreDatado.setCodigoForma(ConstantesFormaRecebimento.CHEQUE_PRE.intValue());			
				FormaRecebimento forma = getFachada().consultarFormaRecebimentoPorId(ConstantesFormaRecebimento.CHEQUE_PRE);
				evItemPagamentoChequePreDatado.setDescricaoForma(forma.getDescricao());
				int codigoPlano = 0;
				Iterator it = forma.getPlanos().iterator();
				while (it.hasNext()){
					PlanoPagamento planoTmp = (PlanoPagamento)it.next();
					codigoPlano = planoTmp.getId().intValue();
					break;
				}				
				evItemPagamentoChequePreDatado.setCodigoPlano(codigoPlano);
				evItemPagamentoChequePreDatado.setDataHoraEvento(new Date(System.currentTimeMillis()));
				evItemPagamentoChequePreDatado.setFormaImpressora(forma.getRecebimentoImpressora());
				evItemPagamentoChequePreDatado.setValorAcrescimo(BigDecimal.ZERO);
				evItemPagamentoChequePreDatado.setValorDesconto(BigDecimal.ZERO);
				evItemPagamentoChequePreDatado.setValorBruto(this.getValorFormaPagamento());
				
				evItemPagamentoChequePreDatado.setParcelas(this.getParcelasChequePreDatado());
				
				this.getItensPagamento().add(evItemPagamentoChequePreDatado);
				this.setValorTotalRecebido(this.getValorTotalRecebido().add(evItemPagamentoChequePreDatado.getValorBruto()));
				this.setCodigoBancoChqPrz(null);
				this.setCodigoAgenciaChqPrz(null);
				this.setNumeroContaChqPrz(null);
				this.setNumeroChequeChqPrz(null);
				this.setCpfCnpjClienteChqPrz(null);
				this.setCmc7ChqPrz(null);
				this.setIdTipoPessoa(Cliente.PESSOA_FISICA);
			}else if(this.getIdFormaPagamento().equals(ConstantesFormaRecebimento.CARTAO_OFF.toString())){
				EventoItemPagamentoCartaoOff evItemPagamentoCartaoOff = new EventoItemPagamentoCartaoOff();				
				evItemPagamentoCartaoOff.setPk(eventoPk);
				evItemPagamentoCartaoOff.setTipoEvento(ConstantesEventoTransacao.EVENTO_ITEM_PAGAMENTO);	
				
				evItemPagamentoCartaoOff.setCodigoForma(ConstantesFormaRecebimento.CARTAO_OFF.intValue());
				FormaRecebimento forma = getFachada().consultarFormaRecebimentoPorId(ConstantesFormaRecebimento.CARTAO_OFF);
				evItemPagamentoCartaoOff.setDescricaoForma(forma.getDescricao());
				int codigoPlano = 0;
				Iterator it = forma.getPlanos().iterator();
				while (it.hasNext()){
					PlanoPagamento planoTmp = (PlanoPagamento)it.next();
					codigoPlano = planoTmp.getId().intValue();
					break;
				}				
				evItemPagamentoCartaoOff.setCodigoPlano(codigoPlano);
				evItemPagamentoCartaoOff.setDataHoraEvento(new Date(System.currentTimeMillis()));
				evItemPagamentoCartaoOff.setFormaImpressora(forma.getRecebimentoImpressora());
				evItemPagamentoCartaoOff.setValorAcrescimo(BigDecimal.ZERO);
				evItemPagamentoCartaoOff.setValorDesconto(BigDecimal.ZERO);
				evItemPagamentoCartaoOff.setValorBruto(this.getValorFormaPagamento());
				evItemPagamentoCartaoOff.setNumeroCartao(this.getNumeroCartao());
				evItemPagamentoCartaoOff.setCodigoAutorizadora(this.getCodigoAutorizadora());
				evItemPagamentoCartaoOff.setAutorizacao(this.getCodigoAutorizacao());
				evItemPagamentoCartaoOff.setQuantidadeParcelas(this.getQuantidadeParcelasCartao());
				
				this.getItensPagamento().add(evItemPagamentoCartaoOff);
				this.setValorTotalRecebido(this.getValorTotalRecebido().add(evItemPagamentoCartaoOff.getValorBruto()));
				this.setNumeroCartao(null);
				this.setCodigoAutorizadora("0");
				this.setCodigoAutorizacao(null);
				this.setQuantidadeParcelasCartao(1);
			}else if(this.getIdFormaPagamento().equals(ConstantesFormaRecebimento.CARTAO_PROPRIO.toString())){
				EventoItemPagamentoCartaoProprio evItemPagamentoCartaoProprio = new EventoItemPagamentoCartaoProprio();				
				evItemPagamentoCartaoProprio.setPk(eventoPk);
				evItemPagamentoCartaoProprio.setTipoEvento(ConstantesEventoTransacao.EVENTO_ITEM_PAGAMENTO);	
				
				evItemPagamentoCartaoProprio.setCodigoForma(ConstantesFormaRecebimento.CARTAO_PROPRIO.intValue());
				FormaRecebimento forma = getFachada().consultarFormaRecebimentoPorId(ConstantesFormaRecebimento.CARTAO_PROPRIO);
				evItemPagamentoCartaoProprio.setDescricaoForma(forma.getDescricao());
				int codigoPlano = 0;
				Iterator it = forma.getPlanos().iterator();
				while (it.hasNext()){
					PlanoPagamento planoTmp = (PlanoPagamento)it.next();
					codigoPlano = planoTmp.getId().intValue();
					break;
				}				
				evItemPagamentoCartaoProprio.setCodigoPlano(codigoPlano);
				evItemPagamentoCartaoProprio.setDataHoraEvento(new Date(System.currentTimeMillis()));
				evItemPagamentoCartaoProprio.setFormaImpressora(forma.getRecebimentoImpressora());
				evItemPagamentoCartaoProprio.setValorAcrescimo(BigDecimal.ZERO);
				evItemPagamentoCartaoProprio.setValorDesconto(BigDecimal.ZERO);
				evItemPagamentoCartaoProprio.setValorBruto(this.getValorFormaPagamento());
				evItemPagamentoCartaoProprio.setAutorizacao(this.getCpfCnpjCliente());
				
				//consultar saldo/situacao do cliente				
				DadosAutorizacaoCartaoProprio dadosAutorizacao = buscaAutorizacaoCartaoProprio(this.getCpfCnpjCliente(), this.getValorFormaPagamento());				
				this.getListaAutorizacaoCartaoProprio().put(new Integer(eventoPk.getNumeroEvento()).toString(), dadosAutorizacao);
				
				this.getItensPagamento().add(evItemPagamentoCartaoProprio);
				this.setValorTotalRecebido(this.getValorTotalRecebido().add(evItemPagamentoCartaoProprio.getValorBruto()));
				this.setCpfCnpjCliente(null);
			}			
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			ctx.addMessage(null, msg);
		}
		this.setValorFormaPagamento(new BigDecimal("0.00"));
		this.setAbaCorrente("tabMenuDiv2");
		this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
		
		if(this.getValorTotalRecebido() != null && !this.getValorTotalRecebido().equals(BigDecimal.ZERO) && this.getValorTotalRecebido().compareTo(this.getValorSubTotalCupom()) > 0){
			this.setValorTroco(
					this.getValorTotalRecebido().
						subtract(this.getValorSubTotalCupom()).
							subtract((this.getDescontoCupom() != null && !this.getDescontoCupom().equals(BigDecimal.ZERO) ? this.getDescontoCupom(): BigDecimal.ZERO)));
		}
		
		BigDecimal valorTemp = BigDecimal.ZERO;
		
		if(this.getValorTotalRecebido().compareTo(this.getValorSubTotalCupom()) > 0){
			valorTemp = this.getValorTotalRecebido();
		}else{
			valorTemp = this.getValorSubTotalCupom();
		}
		
		this.setValorTotalCupom(valorTemp.
							subtract((this.getDescontoCupom() != null ? this.getDescontoCupom() : BigDecimal.ZERO)).
							subtract(this.getValorTroco()));
		return "mesma";
	}

	public DadosAutorizacaoCartaoProprio buscaAutorizacaoCartaoProprio(String cpfCnpj, BigDecimal valor){
		DadosAutorizacaoCartaoProprio dados = null;
		try {
			if((cpfCnpj != null && !cpfCnpj.equals("")) && (valor != null && !valor.equals(new BigDecimal("0.00")))){
				AutorizadorServerRemote obj = (AutorizadorServerRemote)ServiceLocator.getJNDIObject(ServerConfig.CLIENTE_SERVER_JNDI);
				dados = obj.autorizaTransacaoCartaoProprio(cpfCnpj, valor);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dados;
	}
	
	public void confirmaAutorizacaoCartaoProprio(Hashtable<String, DadosAutorizacaoCartaoProprio> listaAutorizacaoCartaoProprio){
		try {
			if(listaAutorizacaoCartaoProprio.size() > 0){
				AutorizadorServerRemote obj = (AutorizadorServerRemote)ServiceLocator.getJNDIObject(ServerConfig.CLIENTE_SERVER_JNDI);
				Enumeration<DadosAutorizacaoCartaoProprio> enumLista = listaAutorizacaoCartaoProprio.elements();
				while(enumLista.hasMoreElements()){
					DadosAutorizacaoCartaoProprio dados = (DadosAutorizacaoCartaoProprio) enumLista.nextElement();
					obj.confirmaTransacaoCartaoProprio(new Long(dados.getAutrizacao()));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void desfazAutorizacaoCartaoProprio(Hashtable<String, DadosAutorizacaoCartaoProprio> listaAutorizacaoCartaoProprio){
		try {
			if(listaAutorizacaoCartaoProprio.size() > 0){				
				AutorizadorServerRemote obj = (AutorizadorServerRemote)ServiceLocator.getJNDIObject(ServerConfig.CLIENTE_SERVER_JNDI);
				Enumeration<DadosAutorizacaoCartaoProprio> enumLista = listaAutorizacaoCartaoProprio.elements();
				while(enumLista.hasMoreElements()){
					DadosAutorizacaoCartaoProprio dados = (DadosAutorizacaoCartaoProprio) enumLista.nextElement();
					obj.desfazTransacaoCartaoProprio(new Long(dados.getAutrizacao()));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String removerItemPagamento(){
		FacesContext context = FacesContext.getCurrentInstance();
		Map params = context.getExternalContext().getRequestParameterMap();  
		String param = (String)  params.get("idExcluirItemPagamento");

		Iterator i = this.getItensPagamento().iterator();
		while(i.hasNext()){
			EventoItemPagamento itemPagamento = (EventoItemPagamento) i.next();
			if (itemPagamento.getPk().getNumeroEvento() == Integer.parseInt(param)){
				this.getItensPagamento().remove(itemPagamento);
				this.setValorTotalRecebido(this.getValorTotalRecebido().subtract(itemPagamento.getValorBruto()));
				break;
			}
		}
		if(this.getValorTotalRecebido() != null && !this.getValorTotalRecebido().equals(BigDecimal.ZERO) && this.getValorTotalRecebido().compareTo(this.getValorSubTotalCupom()) > 0){
			this.setValorTroco(this.getValorTotalRecebido().
					subtract(this.getValorSubTotalCupom()).
							subtract((this.getDescontoCupom() != null && !this.getDescontoCupom().equals(BigDecimal.ZERO) ? this.getDescontoCupom(): BigDecimal.ZERO)));
			this.setValorTotalCupom(this.getValorTotalRecebido().
					subtract(this.getValorSubTotalCupom()).
							subtract((this.getDescontoCupom() != null ? this.getDescontoCupom() : BigDecimal.ZERO)).
									subtract(this.getValorTroco()));
		}else{
			this.setValorTroco(new BigDecimal("0.00"));
			this.setValorTotalCupom(this.getValorSubTotalCupom().
							subtract((this.getDescontoCupom() != null ? this.getDescontoCupom() : BigDecimal.ZERO)).
									subtract(this.getValorTroco()));
		}		
		
		this.setAbaCorrente("tabMenuDiv2");
		this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
		return "mesma";
	}
	
	public String inserirParcelaItemPagamentoChequePreDatado(){
		ParcelaEventoItemPagamentoChequePredatadoPK pk = new ParcelaEventoItemPagamentoChequePredatadoPK();
		pk.setLoja(Integer.parseInt(this.getIdLoja()));
		pk.setComponente(Integer.parseInt(this.getIdComponente()));
		pk.setDataTransacao(this.getDataTransacao());
		pk.setNumeroTransacao(this.getNsuTransacao());
		pk.setNumeroParcela(++numeroParcelaChequePreDatado);
		ParcelaEventoItemPagamentoChequePredatado parcela = new ParcelaEventoItemPagamentoChequePredatado();
		parcela.setPk(pk);
		if(this.getCmc7ChqPrz() != null && !this.getCmc7ChqPrz().equals("")){
			parcela.setNumeroChequeLido(this.getCmc7ChqPrz());
		}else{
			parcela.setBanco(this.getCodigoBancoChqPrz());
			parcela.setAgencia(this.getCodigoAgenciaChqPrz());
			parcela.setConta(this.getNumeroContaChqPrz());
			parcela.setNumeroCheque(this.getNumeroChequeChqPrz());
			parcela.setCPFCNPJ(this.getCpfCnpjClienteChqPrz());
		}
		parcela.setData(this.getDataVencimentoChqPrz());
		if(this.isParcelaEntradaChequePre()){
			parcela.setEntrada(Constantes.SIM);	
		}else{
			parcela.setEntrada(Constantes.NAO);
		}				
		parcela.setValor(this.getValorFormaPagamento());
		if(this.getParcelasChequePreDatado() == null){
			this.setParcelasChequePreDatado(new HashSet());
		}
		this.getParcelasChequePreDatado().add(parcela);
		return "mesma";
	}
	
	public String removerParcelaItemPagamentoChequePreDatado(){
		FacesContext context = FacesContext.getCurrentInstance();
		Map params = context.getExternalContext().getRequestParameterMap();  
		String param = (String)  params.get("idExcluirParcelaChequePre");

		Iterator i = this.getParcelasChequePreDatado().iterator();
		while(i.hasNext()){
			ParcelaEventoItemPagamentoChequePredatado parcela = (ParcelaEventoItemPagamentoChequePredatado) i.next();
			if (parcela.getPk().getNumeroEvento() == Integer.parseInt(param)){
				this.getParcelasChequePreDatado().remove(parcela);
			}
		}
		if(this.getParcelasChequePreDatado() != null && this.getParcelasChequePreDatado().size() == 0){
			numeroParcelaChequePreDatado = 0;
		}
		return "mesma";
	}
	
	public BigDecimal calculaComissaoVendedor(String idVendedor, BigDecimal pValorTotalCupom){
		BigDecimal comissao = BigDecimal.ZERO;
		try {
			Vendedor vendedor = (Vendedor)getFachada().consultarUsuarioPorId(new Long(idVendedor));
			comissao = pValorTotalCupom.multiply(vendedor.getComissao() != null ? vendedor.getComissao(): BigDecimal.ZERO).
							setScale(2, BigDecimal.ROUND_DOWN).divide(new BigDecimal("100.00"), BigDecimal.ROUND_DOWN);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comissao;
	}

	public String inserir(){
		try {

			validaCabecalhoTransacao();
			
			if(this.getItensTransacao() == null || (this.getItensTransacao() != null && this.getItensTransacao().size() == 0)){
				this.setAbaCorrente("tabMenuDiv1");
				this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
				throw new AppException("É necessário inserir os itens da transação de venda.");
			}else if(this.getItensPagamento() == null || (this.getItensPagamento() != null && this.getItensPagamento().size() == 0)){
				this.setAbaCorrente("tabMenuDiv2");
				this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
				throw new AppException("É necessário informar as formas de pagamento.");
			}
			
			if(this.getValorTotalRecebido().compareTo(this.getValorTotalCupom()) < 0){
				this.setAbaCorrente("tabMenuDiv2");
				this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
				throw new AppException("O Valor Recebido deve ser maior ou igual ao Valor Total do Cupom.");
			}
			
			TransacaoVenda transVenda = new TransacaoVenda();
			TransacaoPK transacaoPk = new TransacaoPK();
			transacaoPk.setLoja(new Integer(this.getIdLoja()).intValue());
			transacaoPk.setComponente(new Integer(this.getIdComponente()).intValue());
			transacaoPk.setNumeroTransacao(new Integer(this.getNsuTransacao()).intValue());
			transacaoPk.setDataTransacao(this.getDataTransacao());
			transVenda.setPk(transacaoPk);
			transVenda.setSituacao(TransacaoVenda.ATIVO);
			transVenda.setTipoTransacao(ConstantesTransacao.TRANSACAO_VENDA);
			transVenda.setStatus(Transacao.PROCESSADO);
			
			transVenda.setNumeroCupom(this.getNumeroCupom());
					
			transVenda.setCliente(preencheCliente());
			
			if(this.getIdVendedor() != null && !this.getIdVendedor().equals("0")){
				transVenda.setCodigoUsuarioVendedor(this.getIdVendedor());
				//inserir valor da comissao do vendedor
				transVenda.setComissaoUsuarioVendedor(this.calculaComissaoVendedor(this.getIdVendedor(), this.getValorTotalCupom()));
			}else{
				transVenda.setComissaoUsuarioVendedor(BigDecimal.ZERO);
			}
			
			if(this.getIdOperador() != null && !this.getIdOperador().equals("0")){
				transVenda.setCodigoUsuarioOperador(this.getIdOperador());
			}
			
			transVenda.setDescontoCupom(this.getDescontoCupom());
			transVenda.setValorCupom(this.getValorTotalCupom());
			transVenda.setValorTroco(this.getValorTroco());
			transVenda.setFormaTroco((FormaRecebimento)getFachada().consultarFormaRecebimentoPorId(new Long(this.getIdFormaTroco())));
						
			ConjuntoEventoTransacao conj = new ConjuntoEventoTransacao();
			
			Collection<EventoTransacao> c = new ArrayList<EventoTransacao>();
			
//			Iterator itItensTransacao = this.getItensTransacao().iterator();
//			while(itItensTransacao.hasNext()){
//				conj.add((EventoItemRegistrado)itItensTransacao.next());
//			}
//
//			Iterator itItensPagamento = this.getItensPagamento().iterator();
//			while(itItensPagamento.hasNext()){
//				conj.add((EventoItemPagamento)itItensPagamento.next());
//			}

			Iterator itItensTransacao = this.getItensTransacao().iterator();
			while(itItensTransacao.hasNext()){
				c.add((EventoItemRegistrado)itItensTransacao.next());
			}

			Iterator itItensPagamento = this.getItensPagamento().iterator();
			while(itItensPagamento.hasNext()){
				c.add((EventoItemPagamento)itItensPagamento.next());
			}

			conj.addAll(c);
			
			transVenda.setEventosTransacao(conj);
			
			transVenda.setDataHoraInicio(new Date(System.currentTimeMillis()));
			transVenda.setDataHoraFim(new Date(System.currentTimeMillis()));
			
			getFachada().inserirTransacaoES(transVenda);
			
			this.confirmaAutorizacaoCartaoProprio(listaAutorizacaoCartaoProprio);
			
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
			this.setAbaCorrente("tabMenuDiv0");
			this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
		} catch (ObjectExistentException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Transação de Venda já Existente!", "");
			ctx.addMessage(null, msg);
			this.desfazAutorizacaoCartaoProprio(listaAutorizacaoCartaoProprio);
			this.setAbaCorrente("tabMenuDiv0");
			this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
		} catch (AppException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			ctx.addMessage(null, msg);
			this.desfazAutorizacaoCartaoProprio(listaAutorizacaoCartaoProprio);
			this.setAbaCorrente("tabMenuDiv0");
			this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
		} catch (Exception e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
			this.desfazAutorizacaoCartaoProprio(listaAutorizacaoCartaoProprio);
			this.setAbaCorrente("tabMenuDiv0");
			this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
		}
		resetBB();
		return "mesma";
	}
	
	public String alterar(){
		try {

			validaCabecalhoTransacao();
			
			if(this.getItensTransacao() == null || (this.getItensTransacao() != null && this.getItensTransacao().size() == 0)){
				this.setAbaCorrente("tabMenuDiv1");
				this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
				throw new AppException("É necessário inserir os itens da transação de venda.");
			}else if(this.getItensPagamento() == null || (this.getItensPagamento() != null && this.getItensPagamento().size() == 0)){
				this.setAbaCorrente("tabMenuDiv2");
				this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
				throw new AppException("É necessário informar as formas de pagamento.");
			}
			
			if(this.getValorTotalCupom().compareTo(this.getValorTotalRecebido()) > 0){
				this.setAbaCorrente("tabMenuDiv2");
				this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
				throw new AppException("O Valor Recebido deve ser maior ou igual ao Valor Total do Cupom.");
			}
			
			TransacaoVenda transVenda = new TransacaoVenda();
			TransacaoPK transacaoPk = new TransacaoPK();
			transacaoPk.setLoja(new Integer(this.getIdLoja()).intValue());
			transacaoPk.setComponente(new Integer(this.getIdComponente()).intValue());
			transacaoPk.setNumeroTransacao(new Integer(this.getNsuTransacao()).intValue());
			transacaoPk.setDataTransacao(this.getDataTransacao());
			transVenda.setPk(transacaoPk);
			transVenda.setSituacao(TransacaoVenda.ATIVO);
			transVenda.setTipoTransacao(ConstantesTransacao.TRANSACAO_VENDA);
			transVenda.setStatus(Transacao.PROCESSADO);
			
			transVenda.setNumeroCupom(this.getNumeroCupom());
					
			transVenda.setCliente(preencheCliente());
			
			if(this.getIdVendedor() != null && !this.getIdVendedor().equals("0")){
				transVenda.setCodigoUsuarioVendedor(this.getIdVendedor());
				//inserir valor da comissao do vendedor
				transVenda.setComissaoUsuarioVendedor(this.calculaComissaoVendedor(this.getIdVendedor(), this.getValorTotalCupom()));
			}else{
				transVenda.setComissaoUsuarioVendedor(BigDecimal.ZERO);
			}
			
			if(this.getIdOperador() != null && !this.getIdOperador().equals("0")){
				transVenda.setCodigoUsuarioOperador(this.getIdOperador());
			}
			
			transVenda.setDescontoCupom(this.getDescontoCupom());
			transVenda.setValorCupom(this.getValorTotalCupom());
			transVenda.setValorTroco(this.getValorTroco());
			transVenda.setFormaTroco((FormaRecebimento)getFachada().consultarFormaRecebimentoPorId(new Long(this.getIdFormaTroco())));
						
			ConjuntoEventoTransacao conj = new ConjuntoEventoTransacao();
			
//			if(this.getItensTransacao() != null){
//				Iterator itItensTransacao = this.getItensTransacao().iterator();
//				while(itItensTransacao.hasNext()){
//					conj.add((EventoItemRegistrado)itItensTransacao.next());
//				}
//			}
//			
//			if(this.getItensTransacaoModificados() != null){
//				Iterator itItensTransacao = this.getItensTransacaoModificados().iterator();
//				while(itItensTransacao.hasNext()){
//					conj.add((EventoItemRegistrado)itItensTransacao.next());
//				}
//			}
//
//			if(this.getItensPagamento() != null){
//				Iterator itItensPagamento = this.getItensPagamento().iterator();
//				while(itItensPagamento.hasNext()){
//					conj.add((EventoItemPagamento)itItensPagamento.next());
//				}
//			}
			
			Collection<EventoTransacao> c = new ArrayList<EventoTransacao>();

			if(this.getItensTransacao() != null){
				Iterator itItensTransacao = this.getItensTransacao().iterator();
				while(itItensTransacao.hasNext()){
					c.add((EventoItemRegistrado)itItensTransacao.next());
				}
			}
			
//			if(this.getItensTransacaoModificados() != null){
//				Iterator itItensTransacao = this.getItensTransacaoModificados().iterator();
//				while(itItensTransacao.hasNext()){
//					c.add((EventoItemRegistrado)itItensTransacao.next());
//				}
//			}

			if(this.getItensPagamento() != null){
				Iterator itItensPagamento = this.getItensPagamento().iterator();
				while(itItensPagamento.hasNext()){
					c.add((EventoItemPagamento)itItensPagamento.next());
				}
			}
			
			conj.addAll(c);

			transVenda.setEventosTransacao(conj);
//			transVenda.setEventosTransacao(new ConjuntoEventoTransacao());
			
			transVenda.setDataHoraInicio(new Date(System.currentTimeMillis()));
			transVenda.setDataHoraFim(new Date(System.currentTimeMillis()));
			
			getFachada().atualizarTransacaoES(transVenda, this.getItensTransacaoModificados());
			
			this.confirmaAutorizacaoCartaoProprio(listaAutorizacaoCartaoProprio);
			
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
			this.setAbaCorrente("tabMenuDiv0");
			this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
		} catch (ObjectExistentException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Transação de Venda já Existente!", "");
			ctx.addMessage(null, msg);
			this.desfazAutorizacaoCartaoProprio(listaAutorizacaoCartaoProprio);
			this.setAbaCorrente("tabMenuDiv0");
			this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
		} catch (AppException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			ctx.addMessage(null, msg);
			this.desfazAutorizacaoCartaoProprio(listaAutorizacaoCartaoProprio);
			this.setAbaCorrente("tabMenuDiv0");
			this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
		} catch (Exception e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
			this.desfazAutorizacaoCartaoProprio(listaAutorizacaoCartaoProprio);
			this.setAbaCorrente("tabMenuDiv0");
			this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
		}
		return "mesma";
	}
	
	public String cancelar(){
		try {

			validaCabecalhoTransacao();
			
//			if(this.getItensTransacao() == null || (this.getItensTransacao() != null && this.getItensTransacao().size() == 0)){
//				this.setAbaCorrente("tabMenuDiv1");
//				this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
//				throw new AppException("É necessário inserir os itens da transação de venda.");
//			}else if(this.getItensPagamento() == null || (this.getItensPagamento() != null && this.getItensPagamento().size() == 0)){
//				this.setAbaCorrente("tabMenuDiv2");
//				this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
//				throw new AppException("É necessário informar as formas de pagamento.");
//			}
//			
//			if(this.getValorTotalCupom().compareTo(this.getValorTotalRecebido()) < 0){
//				this.setAbaCorrente("tabMenuDiv2");
//				this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
//				throw new AppException("O Valor Recebido deve ser maior ou igual ao Valor Total do Cupom.");
//			}
			
			TransacaoVenda transVenda = new TransacaoVenda();
			TransacaoPK transacaoPk = new TransacaoPK();
			transacaoPk.setLoja(new Integer(this.getIdLoja()).intValue());
			transacaoPk.setComponente(new Integer(this.getIdComponente()).intValue());
			transacaoPk.setNumeroTransacao(new Integer(this.getNsuTransacao()).intValue());
			transacaoPk.setDataTransacao(this.getDataTransacao());
			transVenda.setPk(transacaoPk);
			transVenda.setSituacao(TransacaoVenda.CANCELADO);
			transVenda.setTipoTransacao(ConstantesTransacao.TRANSACAO_VENDA);
			transVenda.setStatus(Transacao.PROCESSADO);
			
			transVenda.setNumeroCupom(this.getNumeroCupom());
					
			transVenda.setCliente(preencheCliente());
			
			if(this.getIdVendedor() != null && !this.getIdVendedor().equals("0")){
				transVenda.setCodigoUsuarioVendedor(this.getIdVendedor());
				//inserir valor da comissao do vendedor
				transVenda.setComissaoUsuarioVendedor(this.calculaComissaoVendedor(this.getIdVendedor(), this.getValorTotalCupom()));
			}else{
				transVenda.setComissaoUsuarioVendedor(BigDecimal.ZERO);
			}
			
			if(this.getIdOperador() != null && !this.getIdOperador().equals("0")){
				transVenda.setCodigoUsuarioOperador(this.getIdOperador());
			}
			
			transVenda.setDescontoCupom(this.getDescontoCupom());
			transVenda.setValorCupom(this.getValorTotalCupom());
			transVenda.setValorTroco(this.getValorTroco());
			transVenda.setFormaTroco((FormaRecebimento)getFachada().consultarFormaRecebimentoPorId(new Long(this.getIdFormaTroco())));
						
			ConjuntoEventoTransacao conj = new ConjuntoEventoTransacao();
			
			Iterator itItensTransacao = this.getItensTransacao().iterator();
			while(itItensTransacao.hasNext()){
				conj.add((EventoItemRegistrado)itItensTransacao.next());
			}
			
			itItensTransacao = this.getItensTransacaoModificados().iterator();
			while(itItensTransacao.hasNext()){
				conj.add((EventoItemRegistrado)itItensTransacao.next());
			}

			Iterator itItensPagamento = this.getItensPagamento().iterator();
			while(itItensPagamento.hasNext()){
				conj.add((EventoItemPagamento)itItensPagamento.next());
			}

			transVenda.setEventosTransacao(conj);
			
			transVenda.setDataHoraInicio(new Date(System.currentTimeMillis()));
			transVenda.setDataHoraFim(new Date(System.currentTimeMillis()));
			
			getFachada().atualizarTransacaoES(transVenda, null);
			
			this.confirmaAutorizacaoCartaoProprio(listaAutorizacaoCartaoProprio);
			
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
			this.setAbaCorrente("tabMenuDiv0");
			this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
		} catch (ObjectExistentException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Transação de Venda já Existente!", "");
			ctx.addMessage(null, msg);
			this.desfazAutorizacaoCartaoProprio(listaAutorizacaoCartaoProprio);
			this.setAbaCorrente("tabMenuDiv0");
			this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
		} catch (AppException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			ctx.addMessage(null, msg);
			this.desfazAutorizacaoCartaoProprio(listaAutorizacaoCartaoProprio);
			this.setAbaCorrente("tabMenuDiv0");
			this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
		} catch (Exception e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
			this.desfazAutorizacaoCartaoProprio(listaAutorizacaoCartaoProprio);
			this.setAbaCorrente("tabMenuDiv0");
			this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
		}
		resetBB();
		return "mesma";
	}

	public BigDecimal getValorItem() {
		return valorItem;
	}

	public void setValorItem(BigDecimal valorItem) {
		this.valorItem = valorItem;
	}
	
	public void atualizaValorItem(ValueChangeEvent event){
        try {
        	UIInput texto = (UIInput) event.getSource();   
            String valor = String.valueOf(texto.getValue());
            if(!valor.equals("")){
            	this.setValorTotalCupom(new BigDecimal(valor).setScale(2));
//            	this.setValorItem(this.getPrecoVenda().
//            			multiply(this.getQuantidade()==null?BigDecimal.ONE:this.getQuantidade()).
//            			subtract(this.getDescontoItem()==null?BigDecimal.ZERO:this.getDescontoItem()));
//            }else{
//            	this.setValorItem(new BigDecimal("0.00"));
            }
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<EventoItemPagamento> getItensPagamento() {
		return itensPagamento;
	}

	public void setItensPagamento(List<EventoItemPagamento> itensPagamento) {
		this.itensPagamento = itensPagamento;
	}

	public String getCmc7ChqAvt() {
		return cmc7ChqAvt;
	}

	public void setCmc7ChqAvt(String cmc7ChqAvt) {
		this.cmc7ChqAvt = cmc7ChqAvt;
	}

	public String getCmc7ChqPrz() {
		return cmc7ChqPrz;
	}

	public void setCmc7ChqPrz(String cmc7ChqPrz) {
		this.cmc7ChqPrz = cmc7ChqPrz;
	}

	public String getCodigoAgenciaChqAvt() {
		return codigoAgenciaChqAvt;
	}

	public void setCodigoAgenciaChqAvt(String codigoAgenciaChqAvt) {
		this.codigoAgenciaChqAvt = codigoAgenciaChqAvt;
	}

	public String getCodigoAgenciaChqPrz() {
		return codigoAgenciaChqPrz;
	}

	public void setCodigoAgenciaChqPrz(String codigoAgenciaChqPrz) {
		this.codigoAgenciaChqPrz = codigoAgenciaChqPrz;
	}

	public String getCodigoBancoChqAvt() {
		return codigoBancoChqAvt;
	}

	public void setCodigoBancoChqAvt(String codigoBancoChqAvt) {
		this.codigoBancoChqAvt = codigoBancoChqAvt;
	}

	public String getCodigoBancoChqPrz() {
		return codigoBancoChqPrz;
	}

	public void setCodigoBancoChqPrz(String codigoBancoChqPrz) {
		this.codigoBancoChqPrz = codigoBancoChqPrz;
	}

	public Date getDataVencimentoChqPrz() {
		return dataVencimentoChqPrz;
	}

	public void setDataVencimentoChqPrz(Date dataVencimentoChqPrz) {
		this.dataVencimentoChqPrz = dataVencimentoChqPrz;
	}

	public String getNumeroChequeChqAvt() {
		return numeroChequeChqAvt;
	}

	public void setNumeroChequeChqAvt(String numeroChequeChqAvt) {
		this.numeroChequeChqAvt = numeroChequeChqAvt;
	}

	public String getNumeroChequeChqPrz() {
		return numeroChequeChqPrz;
	}

	public void setNumeroChequeChqPrz(String numeroChequeChqPrz) {
		this.numeroChequeChqPrz = numeroChequeChqPrz;
	}

	public String getNumeroContaChqAvt() {
		return numeroContaChqAvt;
	}

	public void setNumeroContaChqAvt(String numeroContaChqAvt) {
		this.numeroContaChqAvt = numeroContaChqAvt;
	}

	public String getNumeroContaChqPrz() {
		return numeroContaChqPrz;
	}

	public void setNumeroContaChqPrz(String numeroContaChqPrz) {
		this.numeroContaChqPrz = numeroContaChqPrz;
	}

	private List<Autorizadora> carregarAutorizadoras() {		
		List<Autorizadora> autorizadoras = null;
		try {
			autorizadoras = (ArrayList<Autorizadora>)getFachada().consultarTodasAutorizadoras();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return autorizadoras;
	}
	
	public SelectItem[] getAutorizadoras() {
		SelectItem[] arrayAutorizadoras = null;
		try {
			List<Autorizadora> autorizadoras = carregarAutorizadoras();
			arrayAutorizadoras = new SelectItem[autorizadoras.size()+1];
			int i = 0;
			arrayAutorizadoras[i++] = new SelectItem("0","");
			for(Autorizadora autorizadorasTmp : autorizadoras){
				SelectItem item = new SelectItem(autorizadorasTmp.getId().toString(), autorizadorasTmp.getDescricao());
				arrayAutorizadoras[i++] = item;
			}
			if(this.getCodigoAutorizadora() == null || (this.getCodigoAutorizadora() != null && this.getCodigoAutorizadora().equals("0"))){
				this.setCodigoAutorizadora((String)arrayAutorizadoras[0].getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return arrayAutorizadoras;
	}


	public void setAutorizadoras(SelectItem[] autorizadoras) {
		this.autorizadoras = autorizadoras;
	}

	public String getIdTipoPessoa() {
		return idTipoPessoa;
	}

	public void setIdTipoPessoa(String idTipoPessoa) {
		this.idTipoPessoa = idTipoPessoa;
	}

	public SelectItem[] getListaTipoPessoa() {
		SelectItem[] lista = new SelectItem[2];
		lista[0] = new SelectItem("F", "Física");
		lista[1] = new SelectItem("J", "Jurídica");
		if(this.getIdTipoPessoa() == null || this.getIdTipoPessoa().equals("")){
			this.setIdTipoPessoa(Cliente.PESSOA_FISICA);
		}
		if(this.getIdTipoPessoaCadastro() == null || this.getIdTipoPessoaCadastro().equals("")){
			this.setIdTipoPessoaCadastro(Cliente.PESSOA_FISICA);
		}
		return lista;
	}

	public void setListaTipoPessoa(SelectItem[] listaTipoPessoa) {
		this.listaTipoPessoa = listaTipoPessoa;
	}

	public BigDecimal getValorTotalRecebido() {
		return valorTotalRecebido;
	}

	public void setValorTotalRecebido(BigDecimal valorTotalRecebido) {
		this.valorTotalRecebido = valorTotalRecebido;
	}

	public String getCpfCnpjClienteChqAvt() {
		return cpfCnpjClienteChqAvt;
	}

	public void setCpfCnpjClienteChqAvt(String cpfCnpjClienteChqAvt) {
		this.cpfCnpjClienteChqAvt = cpfCnpjClienteChqAvt;
	}

	public String getCpfCnpjClienteChqPrz() {
		return cpfCnpjClienteChqPrz;
	}

	public void setCpfCnpjClienteChqPrz(String cpfCnpjClienteChqPrz) {
		this.cpfCnpjClienteChqPrz = cpfCnpjClienteChqPrz;
	}

	public Hashtable<String, DadosAutorizacaoCartaoProprio> getListaAutorizacaoCartaoProprio() {
		return listaAutorizacaoCartaoProprio;
	}

	public void setListaAutorizacaoCartaoProprio(
			Hashtable<String, DadosAutorizacaoCartaoProprio> listaAutorizacaoCartaoProprio) {
		this.listaAutorizacaoCartaoProprio = listaAutorizacaoCartaoProprio;
	}

	public String getAbaCadastroClienteCorrente() {
		return abaCadastroClienteCorrente;
	}

	public void setAbaCadastroClienteCorrente(String abaCadastroClienteCorrente) {
		this.abaCadastroClienteCorrente = abaCadastroClienteCorrente;
	}

	public String getBairroCadastro() {
		return bairroCadastro;
	}

	public void setBairroCadastro(String bairroCadastro) {
		this.bairroCadastro = bairroCadastro;
	}

	public String getCepCadastro() {
		return cepCadastro;
	}

	public void setCepCadastro(String cepCadastro) {
		this.cepCadastro = cepCadastro;
	}

	public String getCidadeCadastro() {
		return cidadeCadastro;
	}

	public void setCidadeCadastro(String cidadeCadastro) {
		this.cidadeCadastro = cidadeCadastro;
	}

	public String getComplementoCadastro() {
		return complementoCadastro;
	}

	public void setComplementoCadastro(String complementoCadastro) {
		this.complementoCadastro = complementoCadastro;
	}

	public String getCpfCnpjClienteCadastro() {
		return cpfCnpjClienteCadastro;
	}

	public void setCpfCnpjClienteCadastro(String cpfCnpjClienteCadastro) {
		this.cpfCnpjClienteCadastro = cpfCnpjClienteCadastro;
	}

	public String getEmailCadastro() {
		return emailCadastro;
	}

	public void setEmailCadastro(String emailCadastro) {
		this.emailCadastro = emailCadastro;
	}

	public String getEstadoCadastro() {
		return estadoCadastro;
	}

	public void setEstadoCadastro(String estadoCadastro) {
		this.estadoCadastro = estadoCadastro;
	}

	public String getFoneCelularCadastro() {
		return foneCelularCadastro;
	}

	public void setFoneCelularCadastro(String foneCelularCadastro) {
		this.foneCelularCadastro = foneCelularCadastro;
	}

	public String getFoneContatoCadastro() {
		return foneContatoCadastro;
	}

	public void setFoneContatoCadastro(String foneContatoCadastro) {
		this.foneContatoCadastro = foneContatoCadastro;
	}

	public String getFoneResidencialCadastro() {
		return foneResidencialCadastro;
	}

	public void setFoneResidencialCadastro(String foneResidencialCadastro) {
		this.foneResidencialCadastro = foneResidencialCadastro;
	}

	public String getIdTipoPessoaCadastro() {
		return idTipoPessoaCadastro;
	}

	public void setIdTipoPessoaCadastro(String idTipoPessoaCadastro) {
		this.idTipoPessoaCadastro = idTipoPessoaCadastro;
	}

	public String getInscricaoEstadualCadastro() {
		return inscricaoEstadualCadastro;
	}

	public void setInscricaoEstadualCadastro(String inscricaoEstadualCadastro) {
		this.inscricaoEstadualCadastro = inscricaoEstadualCadastro;
	}

	public String getInscricaoMunicipalCadastro() {
		return inscricaoMunicipalCadastro;
	}

	public void setInscricaoMunicipalCadastro(String inscricaoMunicipalCadastro) {
		this.inscricaoMunicipalCadastro = inscricaoMunicipalCadastro;
	}

	public String getLogradouroCadastro() {
		return logradouroCadastro;
	}

	public void setLogradouroCadastro(String logradouroCadastro) {
		this.logradouroCadastro = logradouroCadastro;
	}

	public String getNomeClienteCadastro() {
		return nomeClienteCadastro;
	}

	public void setNomeClienteCadastro(String nomeClienteCadastro) {
		this.nomeClienteCadastro = nomeClienteCadastro;
	}

	public String getNumeroCadastro() {
		return numeroCadastro;
	}

	public void setNumeroCadastro(String numeroCadastro) {
		this.numeroCadastro = numeroCadastro;
	}

	public String getPessoaContatoCadastro() {
		return pessoaContatoCadastro;
	}

	public void setPessoaContatoCadastro(String pessoaContatoCadastro) {
		this.pessoaContatoCadastro = pessoaContatoCadastro;
	}

	public String getRazaoSocialCadastro() {
		return razaoSocialCadastro;
	}

	public void setRazaoSocialCadastro(String razaoSocialCadastro) {
		this.razaoSocialCadastro = razaoSocialCadastro;
	}

	public String getReferenciaComercialCadastro() {
		return referenciaComercialCadastro;
	}

	public void setReferenciaComercialCadastro(String referenciaComercialCadastro) {
		this.referenciaComercialCadastro = referenciaComercialCadastro;
	}
	
	public String buscaClientePorCpfCnpj() throws Exception{
		try {
			String cpfCnpj = "";
			if(this.getCpfCnpjClienteCadastro() != null && !this.getCpfCnpjClienteCadastro().equals("")){
				cpfCnpj = this.getCpfCnpjClienteCadastro().trim().replace(".", "").replace("-", "");
			}else{
				FacesContext ctx = FacesContext.getCurrentInstance();
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Para consultar o Cliente é necessário informar o CPF/CNPJ.", "");
				ctx.addMessage(null, msg);
				this.setAbaCorrente("tabMenuDiv3");
				this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
				return "mesma";
			}
			ClienteTransacao cli = getFachada().consultarClienteTransacaoPorID(cpfCnpj);
			if(cli != null){
				this.setCpfCnpjClienteCadastro(cli.getCpfCnpj());
				this.setNomeClienteCadastro(cli.getNomeCliente());				
				this.setIdTipoPessoaCadastro(cli.getTipoPessoa());
				this.setRazaoSocialCadastro(cli.getRazaoSocial());
				this.setInscricaoEstadualCadastro(cli.getInscricaoEstadual());
				this.setInscricaoMunicipalCadastro(cli.getInscricaoMunicipal());
				this.setLogradouroCadastro(cli.getLogradouro());
				this.setNumeroCadastro(cli.getNumero());
				this.setComplementoCadastro(cli.getComplemento());
				this.setBairroCadastro(cli.getBairro());
				this.setCidadeCadastro(cli.getCidade());
				this.setEstadoCadastro(cli.getEstado());
				this.setCepCadastro(cli.getCep());
				this.setFoneResidencialCadastro(cli.getFone());
				this.setFoneCelularCadastro(cli.getCelular());
				this.setPessoaContatoCadastro(cli.getPessoaContato());
				this.setReferenciaComercialCadastro(cli.getReferenciaBancaria());
				this.setEmailCadastro(cli.getEmail());
				this.setAbaCorrente("tabMenuDiv3");
				this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
			}
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Cliente não encontrado para o CPF/CNPJ informado.", "");
			ctx.addMessage(null, msg);
			this.setAbaCorrente("tabMenuDiv3");
			this.setAbaCadastroClienteCorrente("tabMenuDivInterno0");
			return "mesma";
		}
		return "mesma";
	}
	
	public ClienteTransacao preencheCliente(){
		ClienteTransacao cli = null;
		if(this.getCpfCnpjClienteCadastro() != null && !this.getCpfCnpjClienteCadastro().equals("")){
			cli = new ClienteTransacao();
			cli.setCpfCnpj(this.getCpfCnpjClienteCadastro());
			cli.setDataCadastro(new Date(System.currentTimeMillis()));
			cli.setNomeCliente(this.getNomeClienteCadastro());
			if(this.getIdTipoPessoaCadastro().equals(Cliente.PESSOA_FISICA)){
				cli.setTipoPessoa(Cliente.PESSOA_FISICA);
			}else{
				cli.setTipoPessoa(Cliente.PESSOA_JURIDICA);
				cli.setRazaoSocial(this.getRazaoSocialCadastro());
				cli.setInscricaoEstadual(this.getInscricaoEstadualCadastro());
				cli.setInscricaoMunicipal(this.getInscricaoMunicipalCadastro());
			}
			cli.setLogradouro(this.getLogradouroCadastro());
			cli.setNumero(this.getNumeroCadastro());
			cli.setComplemento(this.getComplementoCadastro());
			cli.setBairro(this.getBairroCadastro());
			cli.setCidade(this.getCidadeCadastro());
			cli.setEstado(this.getEstadoCadastro());
			cli.setCep(this.getCepCadastro());
			cli.setFone(this.getFoneResidencialCadastro());
			cli.setCelular(this.getFoneCelularCadastro());
			cli.setPessoaContato(this.getPessoaContatoCadastro());
			cli.setReferenciaBancaria(this.getReferenciaComercialCadastro());
			cli.setEmail(this.getEmailCadastro());			
		}
		return cli;
	}

	public List<EventoItemRegistrado> getItensTransacaoModificados() {
		return itensTransacaoModificados;
	}

	public void setItensTransacaoModificados(
			List<EventoItemRegistrado> itensTransacaoModificados) {
		this.itensTransacaoModificados = itensTransacaoModificados;
	}
	
	public String getIdSituacao() {
		return idSituacao;
	}

	public void setIdSituacao(String idSituacao) {
		this.idSituacao = idSituacao;
	}

	public void setListaSituacao(SelectItem[] listaSituacao) {
		this.listaSituacao = listaSituacao;
	}

	public SelectItem[] getListaSituacao() {
		SelectItem[] lista = new SelectItem[2];
		lista[0] = new SelectItem(TransacaoVenda.ATIVO, "Ativa");
		lista[1] = new SelectItem(TransacaoVenda.CANCELADO, "Cancelada");
		if(this.getIdSituacao() == null || this.getIdSituacao().equals("")){
			this.setIdSituacao(TransacaoVenda.ATIVO);
		}
		return lista;
	}
	
	public static void main(String[] args) {
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd 00:00:00.000");
		try {
			System.out.println(f.parse("2008-08-20 00:00:00.000"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}