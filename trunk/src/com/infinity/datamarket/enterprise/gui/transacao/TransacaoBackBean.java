package com.infinity.datamarket.enterprise.gui.transacao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import com.infinity.datamarket.comum.cliente.Cliente;
import com.infinity.datamarket.comum.componente.Componente;
import com.infinity.datamarket.comum.pagamento.ConstantesFormaRecebimento;
import com.infinity.datamarket.comum.pagamento.FormaRecebimento;
import com.infinity.datamarket.comum.pagamento.PlanoPagamento;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter.IntervalObject;
import com.infinity.datamarket.comum.transacao.ConstantesEventoTransacao;
import com.infinity.datamarket.comum.transacao.EventoItemPagamento;
import com.infinity.datamarket.comum.transacao.EventoItemPagamentoCartaoOff;
import com.infinity.datamarket.comum.transacao.EventoItemPagamentoCartaoProprio;
import com.infinity.datamarket.comum.transacao.EventoItemPagamentoCheque;
import com.infinity.datamarket.comum.transacao.EventoItemPagamentoChequePredatado;
import com.infinity.datamarket.comum.transacao.EventoItemRegistrado;
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

public class TransacaoBackBean extends BackBean {
	private static int sequencialItem = 0;
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
	private List<EventoItemPagamento> itensPagamento = new ArrayList<EventoItemPagamento>();
	
	
	// aba pagamento
	private BigDecimal valorFormaPagamento;
	private String idFormaPagamento;
	//cheques
	//a vista
	private String codigoBanco;
	private String codigoAgencia;
	private String numeroConta;
	private String numeroCheque;
	private String cmc7;
	//a prazo
	private Date dataVencimento;
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
	// rodape
	private BigDecimal valorSubTotalCupom; //soma de todos os itens
	private BigDecimal valorTotalCupom; //valorSubTotalCupom - descontoCupom - valorTroco
	private BigDecimal descontoCupom;
	private BigDecimal valorTroco;
	private String idFormaTroco;	
	
	private BigDecimal valorComissaoVendedor;
	private Date dataInicial;
	private Date dataFinal;
	private String idCliente;
	
	private SelectItem[] lojas;
	private SelectItem[] componentes;
	private SelectItem[] formasPagamento;
	private SelectItem[] formasTroco;
	private SelectItem[] usuariosOperadores;
	private SelectItem[] usuariosVendedores;
	private SelectItem[] clientes;	
	
	private Collection transacoes;
	
	String abaCorrente;

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
	
	private List<Cliente> carregarClientes() {		
		List<Cliente> clientes = null;
		try {
			clientes = (ArrayList<Cliente>)getFachada().consultarTodosClientes();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return clientes;
	}
	
	public SelectItem[] getClientes() {
		SelectItem[] arrayClientes = null;
		try {
			List<Cliente> clientes = carregarClientes();
			arrayClientes = new SelectItem[clientes.size()+1];
			int i = 0;
			arrayClientes[i++] = new SelectItem("0","");
			for(Cliente clientesTmp : clientes){
				SelectItem item = new SelectItem(clientesTmp.getId().toString(), 
						clientesTmp.getTipoPessoa().equals(Cliente.PESSOA_FISICA)?clientesTmp.getNomeCliente():clientesTmp.getNomeFantasia());
				arrayClientes[i++] = item;
			}
			if(this.getIdCliente() == null || (this.getIdCliente() != null && this.getIdCliente().equals("0"))){
				this.setIdCliente((String)arrayClientes[0].getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return arrayClientes;
	}
	public void setClientes(SelectItem[] clientes) {
		this.clientes = clientes;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
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

	public String getCmc7() {
		return cmc7;
	}

	public void setCmc7(String cmc7) {
		this.cmc7 = cmc7;
	}

	public String getCodigoAgencia() {
		return codigoAgencia;
	}

	public void setCodigoAgencia(String codigoAgencia) {
		this.codigoAgencia = codigoAgencia;
	}

	public String getCodigoBanco() {
		return codigoBanco;
	}

	public void setCodigoBanco(String codigoBanco) {
		this.codigoBanco = codigoBanco;
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
		consultar();
		return "voltar";
	}
	public String voltarMenu(){
		resetBB();
		return "voltar";
	}

	public void resetBB(){
		this.setId(null);
		this.setIdLoja(null);
		this.setLojas(null);
		this.setIdComponente(null);
		this.setComponentes(null);
		this.setIdCliente(null);
		this.setClientes(null);
		this.setIdVendedor(null);
		this.setIdOperador(null);
		this.setUsuariosVendedores(null);
		this.setUsuariosOperadores(null);
		this.setIdFormaTroco(null);
		this.setIdFormaPagamento(null);
		this.setFormasTroco(null);
		this.setFormasPagamento(null);
		this.setDataTransacao(null);
		this.setNsuTransacao(null);
		this.setNumeroCupom(null);
		this.setCodigoProduto(null);
		this.setDescricaoProduto(null);
		this.setPrecoVenda(null);
		this.setQuantidade(null);
		this.setDescontoItem(null);
		this.setValorFormaPagamento(null);
		this.setCodigoBanco(null);
		this.setCodigoAgencia(null);
		this.setNumeroConta(null);
		this.setNumeroCheque(null);
		this.setNumeroCheque(null);
		this.setCmc7(null);
		this.setDataVencimento(null);
		this.setNumeroCartao(null);
		this.setCodigoAutorizadora(null);
		this.setCodigoAutorizacao(null);
		this.setQuantidadeParcelasCartao(null);
		this.setCpfCnpjCliente(null);
		this.setItensTransacao(null);
		this.setItensPagamento(null);
	}
	
	public String consultar(){
		try{
			FacesContext context = FacesContext.getCurrentInstance();
			Map params = context.getExternalContext().getRequestParameterMap();            
			TransacaoPK param = (TransacaoPK)  params.get("id");
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
				if (this.getIdLoja() != null && !this.getIdLoja().equals("0")){	
					filter.addProperty("pk.loja", this.getIdLoja());
				}
				if (this.getIdComponente() != null && !this.getIdComponente().equals("0")){	
					filter.addProperty("pk.componente", this.getIdComponente());
				}
				if (this.getNumeroCupom() != null && !this.getNumeroCupom().equals("")){
					filter.addProperty("pk.numeroCupom", this.getNumeroCupom());
				}
				if (this.getDataInicial() != null && this.getDataFinal() != null){	
					if(this.getDataInicial().after(this.getDataFinal())){
						throw new Exception("A Data Final deve ser maior que a Data Inicial.");
					}
					
					filter.addPropertyInterval("pk.dataTransacao", this.getDataInicial(), IntervalObject.MAIOR_IGUAL);
					filter.addPropertyInterval("pk.dataTransacao", this.getDataFinal(), IntervalObject.MENOR_IGUAL);
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
		if(transacao.getCpfCnpjCliente() != null){
			PropertyFilter filter = new PropertyFilter();
			filter.setTheClass(Cliente.class);
			filter.addProperty("cpfCnpj", transacao.getCpfCnpjCliente());
			Cliente cliente = null;
			try {
				cliente = (Cliente)getFachada().consultarCliente(filter);
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(cliente != null){
				this.setIdCliente(cliente.getId().toString());	
			}else{
				this.setIdCliente("0");
			}			
		}
		this.setIdOperador(transacao.getCodigoUsuarioOperador());
		this.setIdVendedor(transacao.getCodigoUsuarioVendedor());

//		this.setItensTransacao(transacao.getEventosTransacao());#JONAS#
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
			throw new Exception("É necessário informar a Loja.");
		}
		if(this.getIdComponente() == null || (this.getIdComponente() != null && this.getIdComponente().equals("0"))){
			throw new Exception("É necessário informar o Componente.");
		}
		if(this.getDataTransacao() == null || (this.getDataTransacao() != null && this.getDataTransacao().equals("0"))){
			throw new Exception("É necessário informar a Data da Transação");
		}
		if(this.getNsuTransacao() == null || (this.getNsuTransacao() != null && this.getNsuTransacao().equals(""))){
			throw new Exception("É necessário informar o Número da Transação.");
		}
		if(this.getNumeroCupom() == null || (this.getNumeroCupom() != null && this.getNumeroCupom().equals(""))){
			throw new Exception("É necessário informar o Número do Cupom.");
		}
	}
	
	public Produto validarItemRegistrado() throws Exception{
		Produto produto = null;
		if(this.getCodigoProduto() == null || (this.getCodigoProduto() != null && this.getCodigoProduto().equals("0"))){
			throw new Exception("É necessário informar um produto.");
		}else{
			produto = getFachada().consultarProdutoPorPK(new Long(this.getCodigoProduto()));
			if(produto == null){
				throw new Exception("O Produto informado é inválido!");
			}
		}
		if(this.getPrecoVenda() == null || (this.getPrecoVenda() != null && this.getPrecoVenda().compareTo(BigDecimal.ZERO.setScale(2)) <= 0)){
			throw new Exception("O Preço unitário do produto informado é inválido!");
		}
		if(this.getQuantidade() == null || (this.getQuantidade() != null && this.getQuantidade().compareTo(BigDecimal.ZERO.setScale(3)) <= 0)){
			throw new Exception("A Quantidade informada é inválida!");
		}
		if(this.getDescontoItem() != null && (this.getDescontoItem().compareTo(BigDecimal.ZERO.setScale(2)) < 0 && this.getDescontoItem().compareTo(new BigDecimal("99.99")) > 0)){
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
			produtoItemRegistrado.getPk().setNumeroEvento(++sequencialItem);
			
			if(produto == null){
				produto = getFachada().consultarProdutoPorPK(new Long(this.getCodigoProduto()));	
			}
			
			produtoItemRegistrado.setCodigoExterno(produto.getCodigoExterno());
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
			
			if(this.getItensTransacao() == null){
				this.setItensTransacao(new ArrayList<EventoItemRegistrado>());
			}
		
			this.getItensTransacao().add(eventoItemRegistrado);
			
			this.setValorSubTotalCupom(this.getValorSubTotalCupom().add(valorTotalItem));
			
			this.setCodigoProduto("");
			this.setDescricaoProduto("");
			this.setPrecoVenda(new BigDecimal("0.00"));
			this.setQuantidade(new BigDecimal("1.000"));
			this.setDescontoItem(new BigDecimal("0.00"));
			this.setValorItem(new BigDecimal("0.00"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return "mesma";
	}

	public String removerItemTransacao(){
		FacesContext context = FacesContext.getCurrentInstance();
		Map params = context.getExternalContext().getRequestParameterMap();  
		String param = (String)  params.get("idExcluirItemRegistrado");

		Iterator i = this.getItensTransacao().iterator();
		while(i.hasNext()){
			EventoItemRegistrado itemTransacao = (EventoItemRegistrado) i.next();
			if (itemTransacao.getPk().getNumeroEvento() == Integer.parseInt(param)){
				this.getItensTransacao().remove(itemTransacao);
				this.setValorSubTotalCupom(this.getValorSubTotalCupom().subtract(itemTransacao.getPreco()));
				break;
			}
		}
		return "mesma";
	}
	
	public String inserirItemPagamento(){
		if(this.getItensPagamento() == null){
			this.setItensPagamento(new ArrayList<EventoItemPagamento>());
		}
		try {
			EventoTransacaoPK eventoPk = new EventoTransacaoPK();
			eventoPk.setLoja(Integer.parseInt(this.getIdLoja()));
			eventoPk.setComponente(Integer.parseInt(this.getIdComponente()));
			eventoPk.setDataTransacao(this.getDataTransacao());
			eventoPk.setNumeroTransacao(this.getNsuTransacao());
			if(this.getIdFormaPagamento().equals(ConstantesFormaRecebimento.DINHEIRO.toString())){
				EventoItemPagamento evItemPagamento = new EventoItemPagamento();				
				evItemPagamento.setPk(eventoPk);
				evItemPagamento.setTipoEvento(ConstantesEventoTransacao.EVENTO_ITEM_PAGAMENTO);
				
				evItemPagamento.setCodigoForma(ConstantesFormaRecebimento.DINHEIRO.intValue());			
				FormaRecebimento forma = getFachada().consultarFormaRecebimentoPorId(ConstantesFormaRecebimento.DINHEIRO);
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
			}else if(this.getIdFormaPagamento().equals(ConstantesFormaRecebimento.CHEQUE.toString())){
				EventoItemPagamentoCheque evItemPagamentoCheque = new EventoItemPagamentoCheque();				
				evItemPagamentoCheque.setPk(eventoPk);
				evItemPagamentoCheque.setTipoEvento(ConstantesEventoTransacao.EVENTO_ITEM_PAGAMENTO);	
				
				evItemPagamentoCheque.setCodigoForma(ConstantesFormaRecebimento.CHEQUE.intValue());			
				FormaRecebimento forma = getFachada().consultarFormaRecebimentoPorId(ConstantesFormaRecebimento.CHEQUE);
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
				if(this.getCmc7() != null && !this.getCmc7().equals("")){
					evItemPagamentoCheque.setNumeroChequeLido(this.getCmc7());
				}else{
					evItemPagamentoCheque.setBanco(this.getCodigoBanco());
					evItemPagamentoCheque.setAgencia(this.getCodigoAgencia());
					evItemPagamentoCheque.setConta(this.getNumeroConta());
					evItemPagamentoCheque.setNumeroCheque(this.getNumeroCheque());
					evItemPagamentoCheque.setCPFCNPJ(this.getCpfCnpjCliente());
				}
				this.getItensPagamento().add(evItemPagamentoCheque);
			}else if(this.getIdFormaPagamento().equals(ConstantesFormaRecebimento.CHEQUE_PRE.toString())){
				EventoItemPagamentoChequePredatado evItemPagamentoChequePreDatado = new EventoItemPagamentoChequePredatado();				
				evItemPagamentoChequePreDatado.setPk(eventoPk);
				evItemPagamentoChequePreDatado.setTipoEvento(ConstantesEventoTransacao.EVENTO_ITEM_PAGAMENTO);
				
				evItemPagamentoChequePreDatado.setCodigoForma(ConstantesFormaRecebimento.CHEQUE_PRE.intValue());			
				FormaRecebimento forma = getFachada().consultarFormaRecebimentoPorId(ConstantesFormaRecebimento.CHEQUE_PRE);
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
			}else if(this.getIdFormaPagamento().equals(ConstantesFormaRecebimento.CARTAO_OFF.toString())){
				EventoItemPagamentoCartaoOff evItemPagamentoCartaoOff = new EventoItemPagamentoCartaoOff();				
				evItemPagamentoCartaoOff.setPk(eventoPk);
				evItemPagamentoCartaoOff.setTipoEvento(ConstantesEventoTransacao.EVENTO_ITEM_PAGAMENTO);	
				
				evItemPagamentoCartaoOff.setCodigoForma(ConstantesFormaRecebimento.CARTAO_OFF.intValue());
				FormaRecebimento forma = getFachada().consultarFormaRecebimentoPorId(ConstantesFormaRecebimento.CARTAO_OFF);
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
			}else if(this.getIdFormaPagamento().equals(ConstantesFormaRecebimento.CARTAO_PROPRIO.toString())){
				EventoItemPagamentoCartaoProprio evItemPagamentoCartaoProprio = new EventoItemPagamentoCartaoProprio();				
				evItemPagamentoCartaoProprio.setPk(eventoPk);
				evItemPagamentoCartaoProprio.setTipoEvento(ConstantesEventoTransacao.EVENTO_ITEM_PAGAMENTO);	
				
				evItemPagamentoCartaoProprio.setCodigoForma(ConstantesFormaRecebimento.CARTAO_PROPRIO.intValue());
				FormaRecebimento forma = getFachada().consultarFormaRecebimentoPorId(ConstantesFormaRecebimento.CARTAO_PROPRIO);
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
				
				this.getItensPagamento().add(evItemPagamentoCartaoProprio);
			}
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "mesma";
	}

	public String removerItemPagamento(){
		FacesContext context = FacesContext.getCurrentInstance();
		Map params = context.getExternalContext().getRequestParameterMap();  
		String param = (String)  params.get("idExcluirItemPagamento");

		Iterator i = this.getItensPagamento().iterator();
		while(i.hasNext()){
			EventoItemPagamento itemPagamento = (EventoItemPagamento) i.next();
			if (itemPagamento.getPk().getNumeroEvento() == Integer.parseInt(param)){
				this.getItensTransacao().remove(itemPagamento);
			}
		}
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
		if(this.getCmc7() != null && !this.getCmc7().equals("")){
			parcela.setNumeroChequeLido(this.getCmc7());
		}else{
			parcela.setBanco(this.getCodigoBanco());
			parcela.setAgencia(this.getCodigoAgencia());
			parcela.setConta(this.getNumeroConta());
			parcela.setNumeroCheque(this.getNumeroCheque());
			parcela.setCPFCNPJ(this.getCpfCnpjCliente());
		}
		parcela.setData(this.getDataVencimento());
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
	
	public BigDecimal calculaComissaoVendedor(String idVendedor, BigDecimal valorTotalCupom){
		BigDecimal comissao = BigDecimal.ZERO;
		try {
			Vendedor vendedor = (Vendedor)getFachada().consultarUsuarioPorId(new Long(idVendedor));
			comissao = valorTotalCupom.multiply(vendedor.getComissao() != null ? vendedor.getComissao(): BigDecimal.ZERO).
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
			
			if(this.getItensTransacao().size() == 0){
				throw new AppException("É necessário inserir os itens.");
			}else if(this.getItensPagamento().size() == 0){
				throw new AppException("É necessário informar as formas de pagamento.");
			}
			
			TransacaoVenda transVenda = new TransacaoVenda();
			TransacaoPK transacaoPk = new TransacaoPK();
			transacaoPk.setLoja(new Integer(this.getIdLoja()).intValue());
			transacaoPk.setComponente(new Integer(this.getIdComponente()).intValue());
			transacaoPk.setNumeroTransacao(new Integer(this.getNsuTransacao()).intValue());
			transacaoPk.setDataTransacao(this.getDataTransacao());
			transVenda.setPk(transacaoPk);
			
			transVenda.setNumeroCupom(this.getNumeroCupom());
			
			if(this.getIdCliente() != null && !this.getIdCliente().equals("0")){
				Cliente cli = (Cliente)getFachada().consultarClientePorPK(new Long(this.getIdCliente()));
				transVenda.setCpfCnpjCliente(cli.getCpfCnpj());
			}
			
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

			Iterator itItensPagamento = this.getItensPagamento().iterator();
			while(itItensPagamento.hasNext()){
				conj.add((EventoItemPagamento)itItensPagamento.next());
			}

			transVenda.setEventosTransacao(conj);
			
			transVenda.setDataHoraInicio(new Date(System.currentTimeMillis()));
			transVenda.setDataHoraFim(new Date(System.currentTimeMillis()));
			
			getFachada().inserirTransacao(transVenda);
			
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Transação de Venda já Existente!", "");
			ctx.addMessage(null, msg);
		} catch (AppException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			ctx.addMessage(null, msg);
		} catch (Exception e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		resetBB();
		return "mesma";
	}
	
	public String alterar(){
		return "mesma";
	}
	
	public String excluir(){
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
            	this.setValorItem(this.getPrecoVenda().
            			multiply(this.getQuantidade()==null?BigDecimal.ONE:this.getQuantidade()).
            			subtract(this.getDescontoItem()==null?BigDecimal.ZERO:this.getDescontoItem()));
            }else{
            	this.setValorItem(new BigDecimal("0.00"));
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

}