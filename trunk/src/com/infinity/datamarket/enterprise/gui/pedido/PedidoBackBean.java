package com.infinity.datamarket.enterprise.gui.pedido;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.collection.PersistentSet;

import com.infinity.datamarket.comum.cliente.Cliente;
import com.infinity.datamarket.comum.fornecedor.Fornecedor;
import com.infinity.datamarket.comum.operacao.ConstantesEventoOperacao;
import com.infinity.datamarket.comum.operacao.ConstantesOperacao;
import com.infinity.datamarket.comum.operacao.EventoOperacaoItemRegistrado;
import com.infinity.datamarket.comum.operacao.EventoOperacaoPK;
import com.infinity.datamarket.comum.operacao.OperacaoPK;
import com.infinity.datamarket.comum.operacao.OperacaoPedido;
import com.infinity.datamarket.comum.operacao.ProdutoOperacaoItemRegistrado;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter.IntervalObject;
import com.infinity.datamarket.comum.transacao.ClienteTransacao;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.usuario.Vendedor;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ConjuntoEventoOperacao;
import com.infinity.datamarket.comum.util.Util;
import com.infinity.datamarket.enterprise.gui.login.LoginBackBean;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

public class PedidoBackBean extends BackBean {
	
	Logger logger = Logger.getLogger(PedidoBackBean.class);
	
	private OperacaoPedido operacaoPedido;
	
	private OperacaoPK id;
	private static int sequencialEventoPedido = 0;	
	
	private SelectItem[] lojas;
	private String idLoja;
	private String idPedido;
	private Date dataPedido;
	private SelectItem[] usuariosVendedores;
	private String idVendedor;

	private String codigoProduto;
	private String descricaoProduto;
	private BigDecimal precoVenda;
	private BigDecimal quantidade;
	private BigDecimal descontoItem;
	private BigDecimal valorItem;
	private List<EventoOperacaoItemRegistrado> itensPedido = new ArrayList<EventoOperacaoItemRegistrado>();
	private List<EventoOperacaoItemRegistrado> itensPedidoModificados = new ArrayList<EventoOperacaoItemRegistrado>();

	
	private BigDecimal descontoPedido;
	private BigDecimal valorTotalPedido;
	private BigDecimal quantidadeTotal;

	private Date dataInicial;
	private Date dataFinal;

	private SelectItem[] listaSituacao;
	private String idSituacao = String.valueOf(ConstantesOperacao.ABERTO);
		
	private String idTipoPessoa = new String(Fornecedor.PESSOA_FISICA);
	private SelectItem[] listaTipoPessoa;
	private String cpfCnpjCliente;
	private String nomeCliente;
	private String razaoSocialCliente;
	private String inscricaoEstadualCliente;
	private String inscricaoMunicipalCliente;
	private String logradouroCliente;
	private String numeroCliente;
	private String complementoCliente;
	private String bairroCliente;
	private String cidadeCliente;
	private String estadoCliente;
	private String cepCliente;
	private String foneCliente;
	private String emailCliente;
	private String celularCliente;
	private String pessoaContatoCliente;
	private String logradouroClienteEntrega;
	private String numeroClienteEntrega;
	private String complementoClienteEntrega;
	private String bairroClienteEntrega;
	private String cidadeClienteEntrega;
	private String estadoClienteEntrega;
	private String cepClienteEntrega;
	private String pontoReferenciaEnderecoEntrega;
	
	private SelectItem[] listaUFs1;
	private SelectItem[] listaUFs2;
	
	private String abaCorrente;
	
	private boolean usaEnderecoParaEntrega;
	private String enderecoEntrega = "N";
	
	private ClienteTransacao clienteTransacao;
	
	private Collection pedidos;

	public ClienteTransacao getClienteTransacao() {
		return clienteTransacao;
	}

	public void setClienteTransacao(ClienteTransacao clienteTransacao) {
		this.clienteTransacao = clienteTransacao;
	}

	public String getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(String codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public String getCpfCnpjCliente() {
		return cpfCnpjCliente;
	}

	public void setCpfCnpjCliente(String cpfCnpjCliente) {
		this.cpfCnpjCliente = cpfCnpjCliente;
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

	public Date getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(Date dataPedido) {
		this.dataPedido = dataPedido;
	}

	public String getDescricaoProduto() {
		return descricaoProduto;
	}

	public void setDescricaoProduto(String descricaoProduto) {
		this.descricaoProduto = descricaoProduto;
	}

	public OperacaoPK getId() {
		return id;
	}

	public void setId(OperacaoPK id) {
		this.id = id;
	}

	public String getIdLoja() {
		return idLoja;
	}

	public void setIdLoja(String idLoja) {
		this.idLoja = idLoja;
	}

	public String getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(String idPedido) {
		this.idPedido = idPedido;
	}

	public String getIdSituacao() {
		return idSituacao;
	}

	public void setIdSituacao(String idSituacao) {
		this.idSituacao = idSituacao;
	}

	public String getIdTipoPessoa() {
		return idTipoPessoa;
	}

	public void setIdTipoPessoa(String idTipoPessoa) {
		this.idTipoPessoa = idTipoPessoa;
	}

	public void setListaSituacao(SelectItem[] listaSituacao) {
		this.listaSituacao = listaSituacao;
	}

	public void setListaTipoPessoa(SelectItem[] listaTipoPessoa) {
		this.listaTipoPessoa = listaTipoPessoa;
	}

	public void setLojas(SelectItem[] lojas) {
		this.lojas = lojas;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
//		this.nomeCliente = nomeCliente;
	}

	public OperacaoPedido getOperacaoPedido() {
		return operacaoPedido;
	}

	public void setOperacaoPedido(OperacaoPedido operacaoPedido) {
		this.operacaoPedido = operacaoPedido;
	}

	public Collection getPedidos() {
		return pedidos;
	}

	public void setPedidos(Collection pedidos) {
		this.pedidos = pedidos;
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

	public BigDecimal getValorItem() {
		return valorItem;
	}

	public void setValorItem(BigDecimal valorItem) {
		this.valorItem = valorItem;
	}

	public BigDecimal getValorTotalPedido() {
		return valorTotalPedido;
	}

	public void setValorTotalPedido(BigDecimal valorTotalPedido) {
		this.valorTotalPedido = valorTotalPedido;
	}
	
	private Set<Loja> carregarLojas() {	
		Set<Loja> lojas = null;
		try {
			lojas = (PersistentSet)LoginBackBean.getInstancia().getUsuario().getLojas();
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return lojas;
	}

	public SelectItem[] getLojas() {
		SelectItem[] arrayLojas = null;
		try {
			Set<Loja> lojas = carregarLojas();
			arrayLojas = new SelectItem[lojas.size()];
			int i = 0;
			for(Loja lojasTmp : lojas){
				SelectItem item = new SelectItem(lojasTmp.getId().toString(), lojasTmp.getNome());
				arrayLojas[i++] = item;
			}
			if((this.getIdLoja() == null || this.getIdLoja().equals("0")) && arrayLojas.length > 0){
				this.setIdLoja(arrayLojas[0].getValue().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return arrayLojas;
	}
	
	public SelectItem[] getListaTipoPessoa() {
		SelectItem[] listaTipoPessoa = new SelectItem[2];
		listaTipoPessoa[0] = new SelectItem("F", "Física");
		listaTipoPessoa[1] = new SelectItem("J", "Jurídica");
		if(this.getIdTipoPessoa() == null || this.getIdTipoPessoa().equals("")){
			this.setIdTipoPessoa(Cliente.PESSOA_FISICA);
		}
		return listaTipoPessoa;
	}

	public SelectItem[] getListaSituacao() {
		SelectItem[] listaSituacao = new SelectItem[5];
		listaSituacao[0] = new SelectItem("0", "Todos");
		listaSituacao[1] = new SelectItem(String.valueOf(ConstantesOperacao.ABERTO), "Novo");
		listaSituacao[2] = new SelectItem(String.valueOf(ConstantesOperacao.CANCELADO), "Cancelado");
		listaSituacao[3] = new SelectItem(String.valueOf(ConstantesOperacao.EM_PROCESSAMENTO), "Em Processamento");
		listaSituacao[4] = new SelectItem(String.valueOf(ConstantesOperacao.FECHADO), "Finalizado");
		if(this.getIdSituacao() == null || this.getIdSituacao().equals("")){
			this.setIdSituacao(String.valueOf(ConstantesOperacao.ABERTO));
		}
		return listaSituacao;
	}
	
	public BigDecimal getDescontoPedido() {
		return descontoPedido;
	}

	public void setDescontoPedido(BigDecimal descontoPedido) {
		this.descontoPedido = descontoPedido;
	}

	public String getIdVendedor() {
		return idVendedor;
	}

	public void setIdVendedor(String idVendedor) {
		this.idVendedor = idVendedor;
	}

	public void setUsuariosVendedores(SelectItem[] usuariosVendedores) {
		this.usuariosVendedores = usuariosVendedores;
	}
	
	private List<Usuario> carregarUsuarios(IPropertyFilter filter) {		
		List<Usuario> usuarios = null;
		try {
			usuarios = (ArrayList<Usuario>)getFachada().consultarUsuario(filter);
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return usuarios;
	}
	
	private List<Usuario> carregarUsuariosVendedores(Usuario usuario, boolean ehVendedor) {		
		List<Usuario> usuarios = null;
		try {
			usuarios = (ArrayList<Usuario>)getFachada().consultarUsuariosPorFiltro(usuario, idLoja, ehVendedor);
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
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
//			List<Usuario> usuariosOperadores = carregarUsuarios(filter);
			List<Usuario> usuariosOperadores = carregarUsuariosVendedores(new Vendedor(), true);
			arrayUsuarios = new SelectItem[usuariosOperadores.size()];
			int i = 0;
			for(Usuario usuariosTmp : usuariosOperadores){
				SelectItem item = new SelectItem(usuariosTmp.getId().toString(), usuariosTmp.getNome());
				arrayUsuarios[i++] = item;
			}
			if(this.getIdVendedor() == null && arrayUsuarios.length > 0){
				this.setIdVendedor(arrayUsuarios[0].getValue().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return arrayUsuarios;
	}
	
	public List<EventoOperacaoItemRegistrado> getItensPedidoModificados() {
		return itensPedidoModificados;
	}

	public void setItensPedidoModificados(
			List<EventoOperacaoItemRegistrado> itensPedidoModificados) {
		this.itensPedidoModificados = itensPedidoModificados;
	}
	
	public void imprimirRecibo(){
		try {
			if(this.getOperacaoPedido() != null){
				FacesContext context = FacesContext.getCurrentInstance();
				HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();			
				ServletOutputStream servletOutputStream = response.getOutputStream();
//				getFachada().gerarReciboOperacaoPedido(this.getOperacaoPedido(),servletOutputStream);			
				response.setContentType("application/pdf");
				response.setHeader("Content-disposition", "attachment;filename=ReciboOperacaoPedido" + System.currentTimeMillis() + ".pdf");
				context.responseComplete();
				servletOutputStream.flush();
				servletOutputStream.close();
			}else{
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Não existe Recibo para imprimir!", "");
				getContextoApp().addMessage(null, msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void carregarProximoIdPedido(ActionEvent event){
		if(this.getIdLoja() != null){
			OperacaoPK pk = new OperacaoPK();
			pk.setLoja(Integer.parseInt(this.getIdLoja()));
			try {
				this.setIdPedido(getFachada().retornaMaxIdOperacaoPorLoja(pk).toString());
			} catch (AppException e) {
				this.setIdPedido("1");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param init the init to set
	 */
	public void setInit(HtmlForm init) {
		FacesContext context = FacesContext.getCurrentInstance();
		Map params = context.getExternalContext().getRequestParameterMap();            
		String param = (String)  params.get(ACAO);
		String path = context.getExternalContext().getRequestServletPath();
		boolean buscaMaxIdOperacao = true;
		if(path.indexOf("consultar") != -1){
			buscaMaxIdOperacao = false;
		}
		if (param != null){
			resetBB();
			resetItemPedidoBB();
			getLojas();
			OperacaoPK pk = new OperacaoPK();
			if(this.getIdLoja() != null){
				pk.setLoja(Integer.parseInt(this.getIdLoja()));
				try {
					if(buscaMaxIdOperacao){
						this.setIdPedido(Util.completaString(getFachada().retornaMaxIdOperacaoPorLoja(pk).toString(), "0", 6, true));	
					}else{
						this.setIdPedido("");
					}
				} catch (AppException e) {
					this.setIdPedido("1");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(VALOR_ACAO.equals(param)){
				this.setAbaCorrente("tabMenuDiv0");
				setPedidos(null);
			}
		}else if(params.get("acaoLocal") != null && ((String)params.get("acaoLocal")).equals("pesquisarProdutos")){
			try {
				Produto prod = getFachada().consultarProdutoPorPK(new Long((String)params.get("codigoProduto")));
				if(prod != null){
					this.setDescricaoProduto(prod.getDescricaoCompleta());
					this.setPrecoVenda(prod.getPrecoPadrao());					
				}
				this.setAbaCorrente("tabMenuDiv0");
			} catch (Exception e) {				
				e.printStackTrace();			
				this.setAbaCorrente("tabMenuDiv0");
			}
		}else if(params.get("acaoLocal") != null && ((String)params.get("acaoLocal")).equals("pesquisarClientes")){
			try {
				String cpfCnpj = (String)params.get("cpfCnpj");
				cpfCnpj = cpfCnpj.replace(".", "");
				cpfCnpj = cpfCnpj.replace("/", "");
				cpfCnpj = cpfCnpj.replace("-", "");
				ClienteTransacao clienteTransacao = getFachada().consultarClienteTransacaoPorID(cpfCnpj);
				if(clienteTransacao != null){
					if(clienteTransacao.getTipoPessoa().equals(Cliente.PESSOA_FISICA)){
						this.nomeCliente = clienteTransacao.getNomeCliente();
					}else{
						this.nomeCliente = clienteTransacao.getRazaoSocial();
					}
					
					this.clienteTransacao = clienteTransacao;
					this.logradouroCliente = clienteTransacao.getLogradouro();
					this.numeroCliente = clienteTransacao.getNumero();
					this.complementoCliente = clienteTransacao.getComplemento();
					this.bairroCliente = clienteTransacao.getBairro();
					this.cidadeCliente = clienteTransacao.getCidade();
					this.estadoCliente = clienteTransacao.getEstado();
					this.cepCliente = clienteTransacao.getCep();
					this.foneCliente = clienteTransacao.getFone();
					this.celularCliente = clienteTransacao.getCelular();
					this.pessoaContatoCliente = clienteTransacao.getPessoaContato();
					this.usaEnderecoParaEntrega = false;
					
					this.limpaCamposEnderecoEntrega();
				}
				this.setAbaCorrente("tabMenuDiv1");
			} catch (ObjectNotFoundException e){
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Cliente com o CPF/CNPF informado Inexistente no Cadastro!", "");
				getContextoApp().addMessage(null, msg);
				this.cpfCnpjCliente = (String)params.get("cpfCnpj");
				this.setAbaCorrente("tabMenuDiv1");
			} catch (Exception e) {
				e.printStackTrace();	
				this.setAbaCorrente("tabMenuDiv1");
			}
		}
	}
	
	public void resetBB(){		
		this.setIdLoja(null);
		this.setIdPedido("");
		this.setDataPedido(new Date());
		this.setIdVendedor(null);

		this.setValorTotalPedido(BigDecimal.ZERO.setScale(2));
		this.setQuantidadeTotal(BigDecimal.ZERO.setScale(3));
		this.setDescontoPedido(BigDecimal.ZERO.setScale(2));
		
		this.setIdTipoPessoa(Cliente.PESSOA_FISICA);
		this.setClienteTransacao(null);
		this.usaEnderecoParaEntrega = false;
		
		getLojas();
		OperacaoPK pk = new OperacaoPK();
		if(this.getIdLoja() != null){
			pk.setLoja(Integer.parseInt(this.getIdLoja()));
			try {
				this.setIdPedido(Util.completaString(getFachada().retornaMaxIdOperacaoPorLoja(pk).toString(), "0", 6, true));
			} catch (AppException e) {
				this.setIdPedido("1");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.setItensPedido(new ArrayList<EventoOperacaoItemRegistrado>());
		
		this.limpaCamposEnderecoEntrega();
	}
	
	public void resetItemPedidoBB(){
		this.setCodigoProduto("");
		this.setDescricaoProduto("");
		this.setPrecoVenda(BigDecimal.ZERO.setScale(2));
		this.setQuantidade(BigDecimal.ZERO.setScale(3));
		this.setDescontoItem(BigDecimal.ZERO.setScale(2));
		this.setValorItem(BigDecimal.ZERO.setScale(2));
	}
	
	public String retornaDescricaoStatus(int status){
		String descricaoStatus = "Novo";
		switch (status) {
		case ConstantesOperacao.ABERTO:
			descricaoStatus = "Novo";
			break;
		case ConstantesOperacao.CANCELADO:
			descricaoStatus = "Cancelado";
			break;
		case ConstantesOperacao.EM_PROCESSAMENTO:
			descricaoStatus = "Em Processamento";
			break;
		case ConstantesOperacao.FECHADO:
			descricaoStatus = "Finalizado";
			break;		
		}
		return descricaoStatus;
	}
	
	public OperacaoPedido preencheOperacaoPedido(String operacao) throws AppException{
		OperacaoPedido pedido = new OperacaoPedido();
		OperacaoPK pk = new OperacaoPK();
		pk.setLoja(Integer.parseInt(this.getIdLoja()));
		pk.setId(Integer.parseInt(this.getIdPedido()));
		pedido.setPk(pk);
		
		pedido.setData(this.getDataPedido());
		
		if(this.getClienteTransacao() == null){
			ClienteTransacao cli = getFachada().consultarClienteTransacaoPorID(this.getCpfCnpjCliente().replace(".", "").replace("/", "").replace("-", ""));
			pedido.setCliente(cli);
		}else{
			pedido.setCliente(this.getClienteTransacao());	
		}
		if(!operacao.equals(ALTERAR)){
			pedido.setStatus(ConstantesOperacao.ABERTO);
		}else {
			pedido.setStatus(new Integer(this.getIdSituacao()).intValue());	
		}
		
		pedido.setTipo(ConstantesOperacao.OPERACAO_PEDIDO);
		
		pedido.setCodigoUsuarioOperador(LoginBackBean.getCodigoUsuarioLogado());
		
		pedido.setCodigoUsuarioVendedor(this.getIdVendedor());
		
		pedido.setValor(this.getValorTotalPedido());
		
		pedido.setDesconto(this.getDescontoPedido());
		
		ConjuntoEventoOperacao ceo = new ConjuntoEventoOperacao();
		
		Iterator it = this.getItensPedido().iterator();
		while(it.hasNext()){
			EventoOperacaoItemRegistrado evOpItReg = (EventoOperacaoItemRegistrado)it.next();
			evOpItReg.getPk().setId(pedido.getPk().getId());
			ceo.add(evOpItReg);
		}
		
		pedido.setEventosOperacao(ceo);
		if(this.usaEnderecoParaEntrega){
			pedido.setUsaEnderecoEntrega("S");
			pedido.setLogradouro(this.getLogradouroClienteEntrega());
			pedido.setNumero(this.getNumeroClienteEntrega());
			pedido.setComplemento(this.getComplementoClienteEntrega());
			pedido.setBairro(this.getBairroClienteEntrega());
			pedido.setCidade(this.getCidadeClienteEntrega());
			pedido.setEstado(this.getEstadoClienteEntrega());
			pedido.setCep(this.getCepClienteEntrega());
			pedido.setPontoReferencia(this.getPontoReferenciaEnderecoEntrega());
		}else{
			pedido.setUsaEnderecoEntrega("N");
			pedido.setLogradouro(this.getLogradouroCliente());
			pedido.setNumero(this.getNumeroCliente());
			pedido.setComplemento(this.getComplementoCliente());
			pedido.setBairro(this.getBairroCliente());
			pedido.setCidade(this.getCidadeCliente());
			pedido.setEstado(this.getEstadoCliente());
			pedido.setCep(this.getCepCliente());
			pedido.setPontoReferencia(this.getPontoReferenciaEnderecoEntrega());
		}
		
		return pedido;
	}

	public BigDecimal getDescontoItem() {
		return descontoItem;
	}

	public void setDescontoItem(BigDecimal descontoItem) {
		this.descontoItem = descontoItem;
	}

	public List<EventoOperacaoItemRegistrado> getItensPedido() {
		return itensPedido;
	}

	public void setItensPedido(List<EventoOperacaoItemRegistrado> itensPedido) {
		this.itensPedido = itensPedido;
	}

	public BigDecimal getQuantidadeTotal() {
		return quantidadeTotal;
	}

	public void setQuantidadeTotal(BigDecimal quantidadeTotal) {
		this.quantidadeTotal = quantidadeTotal;
	}
	
	public void validarPedido() throws AppException{
		if(this.getIdLoja() == null || (this.getIdLoja() != null && this.getIdLoja().equals("0"))){
			this.setAbaCorrente("tabMenuDiv0");
			throw new AppException("É necessário informar uma Loja.");
		}
		if(this.getDataPedido() == null || (this.getDataPedido() != null && this.getDataPedido().equals(""))){
			this.setAbaCorrente("tabMenuDiv0");
			throw new AppException("É necessário informar a Data do Pedido!");
		}else if(this.getDataPedido().after(new Date(System.currentTimeMillis()))){
			this.setAbaCorrente("tabMenuDiv0");
			throw new AppException("A Data do Pedido não pode ser maior que a Data Atual");
		}
		if(this.getIdPedido() == null || (this.getIdPedido() != null && this.getIdPedido().equals(""))){
			this.setAbaCorrente("tabMenuDiv0");
			throw new AppException("É necessário informar o Número do Pedido.");
		}
		if(this.getIdVendedor() == null || (this.getIdVendedor() != null && this.getIdVendedor().equals(""))){
			this.setAbaCorrente("tabMenuDiv0");
			throw new AppException("É necessário informar um Vendedor.");
		}
		if(this.getIdTipoPessoa() == null || (this.getIdTipoPessoa() != null && this.getIdTipoPessoa().equals(""))){
			this.setAbaCorrente("tabMenuDiv0");
			throw new AppException("É necessário informar um Tipo Pessoa para o Cliente.");
		}
		if(this.getCpfCnpjCliente() == null || (this.getCpfCnpjCliente() != null && this.getCpfCnpjCliente().equals(""))){
			this.setAbaCorrente("tabMenuDiv1");
			throw new AppException("É necessário informar o CPF/CNPJ do Cliente.");
		}
		if(this.getNomeCliente() == null || (this.getNomeCliente() != null && this.getNomeCliente().equals(""))){
			this.setAbaCorrente("tabMenuDiv1");
			throw new AppException("É necessário informar o Nome do Cliente.");
		}
		if(this.getItensPedido() == null || this.getItensPedido().size() == 0){
			this.setAbaCorrente("tabMenuDiv0");
			throw new AppException("É necessário adicionar Itens ao Pedido.");
		}
	}
	
	public String inserir(){
		try {
			getLogger().info("inserir - INICIO");
			getLogger().info("inserir:: vou validar o pedido");
			validarPedido();
			getLogger().info("inserir:: validei o pedido");
			getLogger().info("inserir:: vou preencher o objeto pedido");
			OperacaoPedido op = preencheOperacaoPedido(INSERIR);
			getLogger().info("inserir:: preenchi o objeto pedido");
			getLogger().info("inserir:: vou inserir o pedido");
			getFachada().inserirOperacaoES(op);
			getLogger().info("inserir:: inseri o pedido");
			getLogger().info("inserir:: vou setar o pedido para impressao do recibo");
			this.setOperacaoPedido(op);
			getLogger().info("inserir:: setei o pedido para impressao do recibo");
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			getLogger().info("inserir:: limpando o formulario");
			resetBB();
			getLogger().info("inserir:: limpei o formulario");
			this.setAbaCorrente("tabMenuDiv0");
			getLogger().info("inserir:: setei a aba [" + this.getAbaCorrente() + "]");
		} catch (ObjectExistentException e) {
			getLogger().error("inserir:: objeto existente", e);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Pedido de Venda já Existente!", "");
			getContextoApp().addMessage(null, msg);
			this.setAbaCorrente("tabMenuDiv0");
		} catch (AppException e) {
			getLogger().error("inserir:: erro geral", e);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			getContextoApp().addMessage(null, msg);
			this.setAbaCorrente("tabMenuDiv0");
		} catch (Exception e) {
			getLogger().error("inserir:: erro de sistema", e);
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
			this.setAbaCorrente("tabMenuDiv0");
		}		
		getLogger().info("inserir - FIM");
		return "mesma";
	}
	
	public String alterar(){
		try {
			getLogger().info("alterar - INICIO");
			getLogger().info("alterar:: vou validar o pedido");
			validarPedido();
			getLogger().info("alterar:: validei o pedido");
			getLogger().info("alterar:: vou preencher o objeto pedido");
			OperacaoPedido op = preencheOperacaoPedido(ALTERAR);
			getLogger().info("alterar:: preenchi o objeto pedido");
			getLogger().info("alterar:: vou alterar o pedido");
			getFachada().alterarOperacao(op, this.getItensPedidoModificados());
			getLogger().info("alterar:: alterei o pedido");
			getLogger().info("alterar:: vou setar o pedido para impressao do recibo");
			this.setOperacaoPedido(op);
			getLogger().info("alterar:: setei o pedido para impressao do recibo");			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			getLogger().info("alterar:: limpando o formulario");
			resetBB();
			getLogger().info("alterar:: limpei o formulario");
			this.setAbaCorrente("tabMenuDiv0");			
			getLogger().info("alterar:: setei a aba atual para [" + this.getAbaCorrente() + "]");
		} catch (ObjectNotFoundException e) {
			getLogger().error("alterar:: pedido nao encontrado", e);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Pedido de Venda não Existente no Cadastro!", "");
			getContextoApp().addMessage(null, msg);
		} catch (AppException e) {
			getLogger().error("alterar:: erro geral", e);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			getContextoApp().addMessage(null, msg);
		} catch (Exception e) {
			getLogger().error("alterar:: erro de sistema", e);
			e.printStackTrace();			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}		
		getLogger().info("alterar - FIM");
		return "mesma";
	}
	
	public String excluir(){
		try {
			getLogger().info("excluir - INICIO");
			OperacaoPedido op = new OperacaoPedido();
			getLogger().info("excluir:: vou setar o id do pedido para excluir");
			op.setPk(this.getId());
			getLogger().info("excluir:: setei o id do pedido para excluir --> loja: "+this.getId().getLoja() + " - id: " + this.getId().getId());
			getLogger().info("excluir:: vou excluir o pedido");
			getFachada().excluirOperacao(op);
			getLogger().info("excluir:: exclui o pedido");
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			getLogger().info("excluir:: vou limpar o formulario");
			resetBB();
			getLogger().info("excluir:: limpei o formulario");
		} catch (ObjectNotFoundException e) {
			getLogger().error("excluir:: pedido nao encontrado", e);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Pedido de Venda não Existente no Cadastro!", "");
			getContextoApp().addMessage(null, msg);
		} catch (AppException e) {
			getLogger().error("excluir:: erro geral", e);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			getContextoApp().addMessage(null, msg);
		} catch (Exception e) {
			getLogger().error("excluir:: erro de sistema", e);
			e.printStackTrace();			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}		
		getLogger().info("excluir - FIM");
		return "mesma";
	}
	
	public Produto validarItemPedido()  throws AppException{
		Produto produto = null;
		if(this.getCodigoProduto() == null || (this.getCodigoProduto() != null && this.getCodigoProduto().equals("0"))){
			throw new AppException("É necessário informar um produto.");
		}else{
			produto = getFachada().consultarProdutoPorPK(new Long(this.getCodigoProduto()));
			if(produto == null){
				throw new AppException("O Produto informado é inválido!");
			}
		}
		if(this.getPrecoVenda() == null || (this.getPrecoVenda() != null && this.getPrecoVenda().compareTo(BigDecimal.ZERO.setScale(2)) <= 0)){
			throw new AppException("O Preço unitário do produto informado é inválido!");
		}
		if(this.getQuantidade() == null || (this.getQuantidade() != null && this.getQuantidade().compareTo(BigDecimal.ZERO.setScale(3)) <= 0)){
			throw new AppException("A Quantidade informada é inválida!");
		}
		if(this.getDescontoItem() != null && (this.getDescontoItem().compareTo(BigDecimal.ZERO.setScale(2)) < 0 && this.getDescontoItem().compareTo(new BigDecimal("99.99")) > 0)){
			throw new AppException("O Desconto informado é inválido!");
		}
		return produto;

	}
	
	public void validaCabecalhoPedido() throws AppException{
		if(this.getIdLoja() == null || this.getIdLoja().equals("0")){
			throw new AppException("É necessário preencher o campo Loja!");
		}
		
		if(this.getIdPedido() == null || this.getIdPedido().equals("")){
			throw new AppException("É necessário preencher o campo Número Pedido!");
		}
		
		if(this.getDataPedido() == null || this.getDataPedido().equals("")){
			throw new AppException("É necessário preencher o campo Data Pedido!");
		}else if(this.getDataPedido().after(new Date(System.currentTimeMillis()))){
			throw new AppException("A Data do Pedido não pode ser maior que a Data Atual!");
		}
			
		if(this.getIdVendedor() == null || this.getIdVendedor().equals("0")){
			throw new AppException("É necessário preencher o campo Vendedor!");
		}
	}
	
	public void inserirItemPedido(){
		getLogger().info("inserirItemPedido - INICIO");
		BigDecimal valorTotalItem = new BigDecimal("0.00");
		Produto produto = null;
		try {
			getLogger().info("inserirItemPedido:: vou validar o cabecalho do pedido");
			validaCabecalhoPedido();
			getLogger().info("inserirItemPedido:: validei o cabecalho do pedido");
			getLogger().info("inserirItemPedido:: vou validar o item");
			produto = validarItemPedido();
			getLogger().info("inserirItemPedido:: validei o item");
			getLogger().info("inserirItemPedido:: produto--> "+produto);
			if(produto == null){
				produto = getFachada().consultarProdutoPorPK(new Long(this.getCodigoProduto()));
				getLogger().info("inserirItemPedido:: produto--> "+produto.getId());
			}
							
			ProdutoOperacaoItemRegistrado prodOpItemReg = new ProdutoOperacaoItemRegistrado();
			
			prodOpItemReg.setPk(new EventoOperacaoPK());
			prodOpItemReg.getPk().setLoja(Integer.parseInt(this.getIdLoja()));
			prodOpItemReg.getPk().setId(Integer.parseInt(this.getIdPedido()));
			prodOpItemReg.getPk().setNumeroEvento(++sequencialEventoPedido);
			getLogger().info("inserirItemPedido:: sequencial item--> "+sequencialEventoPedido);
			
			prodOpItemReg.setIdProduto(produto.getId().intValue());
			getLogger().info("inserirItemPedido:: prodOpItemReg.getIdProduto()--> "+prodOpItemReg.getIdProduto());
			prodOpItemReg.setCodigoExterno(produto.getCodigoExterno());
			getLogger().info("inserirItemPedido:: prodOpItemReg.getCodigoExterno()--> "+prodOpItemReg.getCodigoExterno());
			prodOpItemReg.setDescricaoCompleta(produto.getDescricaoCompleta());			
			getLogger().info("inserirItemPedido:: prodOpItemReg.getDescricaoCompleta()--> "+prodOpItemReg.getDescricaoCompleta());
			prodOpItemReg.setImpostoImpressora(produto.getImposto().getImpostoImpressora());
			getLogger().info("inserirItemPedido:: prodOpItemReg.getImpostoImpressora()--> "+prodOpItemReg.getImpostoImpressora());
			prodOpItemReg.setPercentual(produto.getImposto().getPercentual());
			getLogger().info("inserirItemPedido:: prodOpItemReg.getPercentual()--> "+prodOpItemReg.getPercentual());
			prodOpItemReg.setPrecoPadrao(produto.getPrecoPadrao());
			getLogger().info("inserirItemPedido:: prodOpItemReg.getPrecoPadrao()--> "+prodOpItemReg.getPrecoPadrao());
			
			valorTotalItem = this.getPrecoVenda().subtract(this.getDescontoItem()).
						multiply(this.getQuantidade()).setScale(2, RoundingMode.DOWN);
			getLogger().info("inserirItemPedido:: valorTotalItem--> "+valorTotalItem);
			
			prodOpItemReg.setPrecoPraticado(this.getPrecoVenda());
			getLogger().info("inserirItemPedido:: prodOpItemReg.getPrecoPraticado()--> "+prodOpItemReg.getPrecoPraticado());
			prodOpItemReg.setTipoProduto(produto.getTipo().getId());
			getLogger().info("inserirItemPedido:: prodOpItemReg.getTipoProduto()--> "+prodOpItemReg.getTipoProduto());
			prodOpItemReg.setUnidade(produto.getUnidade().getAbreviacao());
			getLogger().info("inserirItemPedido:: prodOpItemReg.getUnidade()--> "+prodOpItemReg.getUnidade());

			BigDecimal lucro = null;
			if (produto.getPrecoCompra() != null){
				getLogger().info("inserirItemPedido:: produto.getPrecoCompra()--> "+produto.getPrecoCompra());
				lucro = valorTotalItem.subtract(produto.getPrecoCompra().multiply(quantidade));
			}
			getLogger().info("inserirItemPedido:: lucro--> "+lucro);

			EventoOperacaoItemRegistrado evOpItemReg = 
				new EventoOperacaoItemRegistrado(prodOpItemReg.getPk(),
						  						 ConstantesEventoOperacao.EVENTO_OPERACAO_ITEM_REGISTRADO,
						  						 new Date(),
						  						 valorTotalItem,
						  						 this.getQuantidade(),
						  						 this.getDescontoItem(),
						  						 lucro,
						  						 prodOpItemReg);
			
			evOpItemReg.setAcao(EventoOperacaoItemRegistrado.ITEM_INSERIDO);
			
			if(this.getItensPedido() == null){
				this.setItensPedido(new ArrayList<EventoOperacaoItemRegistrado>());
			}
			getLogger().info("inserirItemPedido:: this.getItensPedido().size() antes--> "+this.getItensPedido().size());
			this.getItensPedido().add(evOpItemReg);
			getLogger().info("inserirItemPedido:: this.getItensPedido().size() antes--> "+this.getItensPedido().size());
			getLogger().info("inserirItemPedido:: vou atualizar os totais");
			atualizarTotais(valorTotalItem, this.getQuantidade());
			getLogger().info("inserirItemPedido:: atualizei os totais");
			getLogger().info("inserirItemPedido:: vou limpar os campos do item");
			resetItemPedidoBB();
			getLogger().info("inserirItemPedido:: limpei os campos do item");
			this.setAbaCorrente("tabMenuDiv0");
			getLogger().info("inserirItemPedido:: setei a aba atual para [" + this.getAbaCorrente() + "]");
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			getContextoApp().addMessage(null, msg);
			this.setAbaCorrente("tabMenuDiv0");
		}
		getLogger().info("inserirItemPedido - FIM");
	}
	
	public void atualizarTotais(BigDecimal valItem, BigDecimal qtd){
		getLogger().info("atualizarTotais - INICIO");
		getLogger().info("atualizarTotais - valorTotal antes--> "+this.getValorTotalPedido());
		this.setValorTotalPedido(this.getValorTotalPedido().add(valItem).subtract(this.getDescontoPedido()));
		getLogger().info("atualizarTotais - valorTotal agora--> "+this.getValorTotalPedido());
		getLogger().info("atualizarTotais - quantiadeTotal antes--> "+this.getQuantidadeTotal());
		this.setQuantidadeTotal(this.getQuantidadeTotal().add(qtd));
		getLogger().info("atualizarTotais - quantiadeTotal agora--> "+this.getQuantidadeTotal());
		getLogger().info("atualizarTotais - FIM");
	}
	
	public void setaUsaEnderecoEntrega(ValueChangeEvent event){
        Boolean param = (Boolean) event.getNewValue(); 

		this.usaEnderecoParaEntrega = param.booleanValue();
		if(this.usaEnderecoParaEntrega){
			this.enderecoEntrega = "S";
			this.logradouroClienteEntrega = this.logradouroCliente;
			this.numeroClienteEntrega = this.numeroCliente;
			this.complementoClienteEntrega = this.complementoCliente;
			this.bairroClienteEntrega = this.bairroCliente;
			this.cidadeClienteEntrega = this.cidadeCliente;
			this.estadoClienteEntrega = this.estadoCliente;
			this.cepClienteEntrega = this.cepCliente;
		}else{
			this.enderecoEntrega = "N";
			this.logradouroClienteEntrega = "";
			this.numeroClienteEntrega = "";
			this.complementoClienteEntrega = "";
			this.bairroClienteEntrega = "";
			this.cidadeClienteEntrega = "";
			this.estadoClienteEntrega = "";
			this.cepClienteEntrega = "";
			this.pontoReferenciaEnderecoEntrega = "";
		}
	}
	
	public void removerItemPedido(ActionEvent event){
		getLogger().info("removerItemPedido - INICIO");
        UIParameter component = (UIParameter) event.getComponent().findComponent("idExcluirItemPedido");
        Integer param = (Integer) component.getValue(); 
        getLogger().info("removerItemPedido - vou remover o item --> "+param);
		if(this.getItensPedidoModificados() == null){
			this.setItensPedidoModificados(new ArrayList<EventoOperacaoItemRegistrado>());
		}
		getLogger().info("removerItemPedido - this.getItensPedidoModificados().size() antes--> "+this.getItensPedidoModificados().size());
		getLogger().info("removerItemPedido - this.getItensPedido().size() antes--> "+this.getItensPedido().size());
		Iterator i = this.getItensPedido().iterator();
		while(i.hasNext()){
			EventoOperacaoItemRegistrado itemPedido = (EventoOperacaoItemRegistrado) i.next();
			if (itemPedido.getPk().getNumeroEvento() == param){
				this.getItensPedido().remove(itemPedido);
				//incluir na lista de itens alterados/excluidos
				itemPedido.setAcao(EventoOperacaoItemRegistrado.ITEM_EXCLUIDO);
				getLogger().info("removerItemPedido - vou remover o item");
				this.getItensPedidoModificados().add(itemPedido);
				getLogger().info("removerItemPedido - removi o item");
				getLogger().info("removerItemPedido - vou atualizar os totais");
				atualizarTotais(itemPedido.getPreco().negate(), itemPedido.getQuantidade().negate());
				getLogger().info("removerItemPedido - atualizei os totais");
				break;
			}
		}
		getLogger().info("removerItemPedido - this.getItensPedido().size() agora--> "+this.getItensPedido().size());
		getLogger().info("removerItemPedido - this.getItensPedidoModificados().size() agora--> "+this.getItensPedidoModificados().size());
		getLogger().info("removerItemPedido - FIM");
	}

	public String getBairroCliente() {
		return bairroCliente;
	}

	public void setBairroCliente(String bairroCliente) {
//		this.bairroCliente = bairroCliente;
	}

	public String getBairroClienteEntrega() {
		return bairroClienteEntrega;
	}

	public void setBairroClienteEntrega(String bairroClienteEntrega) {
//		this.bairroClienteEntrega = bairroClienteEntrega;
	}

	public String getCelularCliente() {
		return celularCliente;
	}

	public void setCelularCliente(String celularCliente) {
//		this.celularCliente = celularCliente;
	}

	public String getCepCliente() {
		return cepCliente;
	}

	public void setCepCliente(String cepCliente) {
//		this.cepCliente = cepCliente;
	}

	public String getCepClienteEntrega() {
		return cepClienteEntrega;
	}

	public void setCepClienteEntrega(String cepClienteEntrega) {
//		this.cepClienteEntrega = cepClienteEntrega;
	}

	public String getCidadeCliente() {
		return cidadeCliente;
	}

	public void setCidadeCliente(String cidadeCliente) {
//		this.cidadeCliente = cidadeCliente;
	}

	public String getCidadeClienteEntrega() {
		return cidadeClienteEntrega;
	}

	public void setCidadeClienteEntrega(String cidadeClienteEntrega) {
//		this.cidadeClienteEntrega = cidadeClienteEntrega;
	}

	public String getComplementoCliente() {
		return complementoCliente;
	}

	public void setComplementoCliente(String complementoCliente) {
//		this.complementoCliente = complementoCliente;
	}

	public String getComplementoClienteEntrega() {
		return complementoClienteEntrega;
	}

	public void setComplementoClienteEntrega(String complementoClienteEntrega) {
//		this.complementoClienteEntrega = complementoClienteEntrega;
	}

	public String getEmailCliente() {
		return emailCliente;
	}

	public void setEmailCliente(String emailCliente) {
//		this.emailCliente = emailCliente;
	}

	public String getEstadoCliente() {
		return estadoCliente;
	}

	public void setEstadoCliente(String estadoCliente) {
//		this.estadoCliente = estadoCliente;
	}

	public String getEstadoClienteEntrega() {
		return estadoClienteEntrega;
	}

	public void setEstadoClienteEntrega(String estadoClienteEntrega) {
//		this.estadoClienteEntrega = estadoClienteEntrega;
	}

	public String getFoneCliente() {
		return foneCliente;
	}

	public void setFoneCliente(String foneCliente) {
//		this.foneCliente = foneCliente;
	}

	public String getInscricaoEstadualCliente() {
		return inscricaoEstadualCliente;
	}

	public void setInscricaoEstadualCliente(String inscricaoEstadualCliente) {
//		this.inscricaoEstadualCliente = inscricaoEstadualCliente;
	}

	public String getInscricaoMunicipalCliente() {
		return inscricaoMunicipalCliente;
	}

	public void setInscricaoMunicipalCliente(String inscricaoMunicipalCliente) {
//		this.inscricaoMunicipalCliente = inscricaoMunicipalCliente;
	}

	public String getLogradouroCliente() {
		return logradouroCliente;
	}

	public void setLogradouroCliente(String logradouroCliente) {
//		this.logradouroCliente = logradouroCliente;
	}

	public String getLogradouroClienteEntrega() {
		return logradouroClienteEntrega;
	}

	public void setLogradouroClienteEntrega(String logradouroClienteEntrega) {
//		this.logradouroClienteEntrega = logradouroClienteEntrega;
	}

	public String getNumeroCliente() {
		return numeroCliente;
	}

	public void setNumeroCliente(String numeroCliente) {
//		this.numeroCliente = numeroCliente;
	}

	public String getNumeroClienteEntrega() {
		return numeroClienteEntrega;
	}

	public void setNumeroClienteEntrega(String numeroClienteEntrega) {
//		this.numeroClienteEntrega = numeroClienteEntrega;
	}

	public String getPessoaContatoCliente() {
		return pessoaContatoCliente;
	}

	public void setPessoaContatoCliente(String pessoaContatoCliente) {
//		this.pessoaContatoCliente = pessoaContatoCliente;
	}

	public String getPontoReferenciaEnderecoEntrega() {
		return pontoReferenciaEnderecoEntrega;
	}

	public void setPontoReferenciaEnderecoEntrega(
			String pontoReferenciaEnderecoEntrega) {
//		this.pontoReferenciaEnderecoEntrega = pontoReferenciaEnderecoEntrega;
	}

	public String getRazaoSocialCliente() {
		return razaoSocialCliente;
	}

	public void setRazaoSocialCliente(String razaoSocialCliente) {
//		this.razaoSocialCliente = razaoSocialCliente;
	}

	public void limpaCamposEnderecoEntrega(){
		this.logradouroClienteEntrega = null;
		this.numeroClienteEntrega = null;
		this.complementoClienteEntrega = null;
		this.bairroClienteEntrega = null;
		this.cidadeClienteEntrega = null;
		this.estadoClienteEntrega = null;
		this.cepClienteEntrega = null;
		this.pontoReferenciaEnderecoEntrega = null;
	}

	public String getAbaCorrente() {
		return abaCorrente;
	}

	public void setAbaCorrente(String abaCorrente) {
		this.abaCorrente = abaCorrente;
	}

	public boolean getUsaEnderecoParaEntrega() {
		return usaEnderecoParaEntrega;
	}

	public void setUsaEnderecoParaEntrega(boolean usaEnderecoParaEntrega) {
		this.usaEnderecoParaEntrega = usaEnderecoParaEntrega;
	}

	public SelectItem[] getListaUFs1() {
		return Util.getUFs();
	}

	public void setListaUFs1(SelectItem[] listaUFs1) {
		this.listaUFs1 = listaUFs1;
	}

	public SelectItem[] getListaUFs2() {
		return Util.getUFs();
	}

	public void setListaUFs2(SelectItem[] listaUFs2) {
		this.listaUFs2 = listaUFs2;
	}

	public String getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(String enderecoEntrega) {
//		this.enderecoEntrega = enderecoEntrega;
	}
	
	public void preencheBackBean(OperacaoPedido opPedido){
		this.setIdLoja(String.valueOf(opPedido.getPk().getLoja()));
		this.setIdPedido(Util.completaString(String.valueOf(opPedido.getPk().getId()),"0",6,true));
		this.dataPedido = opPedido.getData();
		this.idVendedor = opPedido.getCodigoUsuarioVendedor();
		if(this.getItensPedido() == null){
			this.setItensPedido(new ArrayList<EventoOperacaoItemRegistrado>());
		}
		BigDecimal qtdTotalTemp = BigDecimal.ZERO.setScale(3);
		if(opPedido.getEventosOperacao() != null){
			Iterator<EventoOperacaoItemRegistrado> it = opPedido.getEventosOperacao().iterator();
			while(it.hasNext()){
				EventoOperacaoItemRegistrado itemPedido = it.next();
				if(itemPedido != null){
					this.getItensPedido().add(itemPedido);
					qtdTotalTemp = qtdTotalTemp.add(itemPedido.getQuantidade());
				}
			}
		}
		this.quantidadeTotal = qtdTotalTemp.setScale(3);
		this.descontoPedido = opPedido.getDesconto().setScale(2);
		this.valorTotalPedido = opPedido.getValor().setScale(2);
		
		if(opPedido.getCliente() != null){
			ClienteTransacao cli = opPedido.getCliente();
			this.cpfCnpjCliente = Util.formataCpfCnpj(cli.getCpfCnpj());
			this.nomeCliente = cli.getNomeCliente();
			this.razaoSocialCliente = cli.getRazaoSocial();
			this.inscricaoEstadualCliente = cli.getInscricaoEstadual();
			this.inscricaoMunicipalCliente = cli.getInscricaoMunicipal();
			this.emailCliente = cli.getEmail();
			this.logradouroCliente = cli.getLogradouro();
			this.numeroCliente = cli.getNumero();
			this.complementoCliente = cli.getComplemento();
			this.bairroCliente = cli.getBairro();
			this.cidadeCliente = cli.getCidade();
			this.estadoCliente = cli.getEstado();
			this.cepCliente = cli.getCep();
			this.foneCliente = cli.getFone();
			this.celularCliente = cli.getCelular();
			this.pessoaContatoCliente = cli.getPessoaContato();
		}
		
		if(opPedido.getUsaEnderecoEntrega() != null && opPedido.getUsaEnderecoEntrega().equals("S")){
			this.usaEnderecoParaEntrega = true;
			this.enderecoEntrega = "S";
			this.logradouroClienteEntrega = opPedido.getLogradouro();
			this.numeroClienteEntrega = opPedido.getNumero();
			this.complementoClienteEntrega = opPedido.getComplemento();
			this.bairroClienteEntrega = opPedido.getBairro();
			this.cidadeClienteEntrega = opPedido.getCidade();
			this.estadoClienteEntrega = opPedido.getEstado();
			this.cepClienteEntrega = opPedido.getCep();
			this.pontoReferenciaEnderecoEntrega = opPedido.getPontoReferencia();
		}else{
			this.usaEnderecoParaEntrega = false;
			this.enderecoEntrega = "N";
			this.logradouroClienteEntrega = "";
			this.numeroClienteEntrega = "";
			this.complementoClienteEntrega = "";
			this.bairroClienteEntrega = "";
			this.cidadeClienteEntrega = "";
			this.estadoClienteEntrega = "";
			this.cepClienteEntrega = "";
			this.pontoReferenciaEnderecoEntrega = "";
		}
	}
	
	public String consultar(){
		try{
			FacesContext context = FacesContext.getCurrentInstance();
			Map params = context.getExternalContext().getRequestParameterMap();
			OperacaoPK param = null;
			if(params != null){				
				Integer loja = null;
				Integer id = null;
				if(params.get("pedido_loja") != null && !params.get("pedido_loja").equals("")){
					loja = new Integer(Integer.parseInt(params.get("pedido_loja").toString()));
				}
				if(params.get("pedido_id") != null && !params.get("pedido_id").equals("")){
					id = new Integer(Integer.parseInt(params.get("pedido_id").toString()));
				}
				if(loja != null && id != null){
					param = new OperacaoPK();	
					param.setLoja(loja);
					param.setId(id);
				}
			}
			if (param != null){
				setId(param);
			}
			if (getId() != null && getId().getLoja() != 0 && getId().getId() != 0){
				OperacaoPedido opPedido = (OperacaoPedido) getFachada().consultarOperacaoPorPK(getId());
				
				preencheBackBean(opPedido);
				
				return "proxima";
			} else if((this.getIdLoja() != null && !this.getIdLoja().equals("0")) 
					|| (this.getIdPedido() != null && !this.getIdPedido().equals(""))
					|| (this.getDataInicial() != null && this.getDataFinal() != null)){
				
				PropertyFilter filter = new PropertyFilter();
				
				filter.setTheClass(OperacaoPedido.class);
				
				OperacaoPK operacaoPk = new OperacaoPK();
				
				if (this.getIdLoja() != null && !this.getIdLoja().equals("0")){	
					operacaoPk.setLoja(Integer.parseInt(this.getIdLoja()));
					filter.addProperty("pk.loja", Integer.parseInt(this.getIdLoja()));
				}
				if (this.getIdPedido() != null && !this.getIdPedido().equals("")){	
					operacaoPk.setId(Integer.parseInt(this.getIdPedido()));
					filter.addProperty("pk.id", Integer.parseInt(this.getIdPedido()));
				}
				
				if (this.getDataInicial() != null && this.getDataFinal() != null){	
					if(this.getDataInicial().after(this.getDataFinal())){
						throw new Exception("A Data Final deve ser maior que a Data Inicial.");
					}
					
					filter.addPropertyInterval("data", this.getDataInicial(), IntervalObject.MAIOR_IGUAL);
					Date dataFinal = new Date(this.getDataFinal().getTime());					
					dataFinal.setDate(dataFinal.getDate()+1);
					filter.addPropertyInterval("data", dataFinal, IntervalObject.MENOR_IGUAL);
				}
				
				if(this.getIdSituacao() != null && !this.getIdSituacao().equals("0")){
					filter.addProperty("status", Integer.parseInt(this.getIdSituacao()));
				}
				
				if(this.getIdTipoPessoa() != null){
					if(this.getCpfCnpjCliente() != null && !this.getCpfCnpjCliente().equals("")){
						if(this.getIdTipoPessoa().equals(Cliente.PESSOA_FISICA)){
							filter.addProperty("cliente.tipoPessoa", Cliente.PESSOA_FISICA);
						}else{
							filter.addProperty("cliente.tipoPessoa", Cliente.PESSOA_JURIDICA);
						}
						filter.addProperty("cliente.cpfCnpj", this.getCpfCnpjCliente().replace(".", "").replace("-", "").replace("/", ""));
					}						
				}				

				this.nomeCliente = this.getNomeCliente();
				
				Collection col = getFachada().consultarOperacao(filter);
				
				if (col == null || col.size() == 0){
					setExisteRegistros(false);
					this.setPedidos(null);
					
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					getContextoApp().addMessage(null, msg);					
				}else if (col != null){
					if(col.size() == 1){
						
						preencheBackBean((OperacaoPedido)col.iterator().next());

						return "proxima";
					}else{
						setExisteRegistros(true);
						setPedidos(col);
					}
				}
			}else{
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(TransacaoVenda.class);
				Collection c = getFachada().consultarTransacao(filter);
				if(c != null && c.size() > 0){
					this.setPedidos(c);
					setExisteRegistros(true);
				}else{
					this.setPedidos(null);
					setExisteRegistros(false);
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					getContextoApp().addMessage(null, msg);			
				}
			}
		}catch(ObjectNotFoundException e){
			setExisteRegistros(false);
			this.setPedidos(null);			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Nenhum Registro Encontrado", "");
			getContextoApp().addMessage(null, msg);			
		}catch(Exception e){
			e.printStackTrace();
			setExisteRegistros(false);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return "mesma";
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	public String voltarConsulta(){
		resetBB();
		return "voltar";
	}

}