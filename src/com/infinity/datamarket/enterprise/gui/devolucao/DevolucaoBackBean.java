package com.infinity.datamarket.enterprise.gui.devolucao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.infinity.datamarket.comum.cliente.Cliente;
import com.infinity.datamarket.comum.fornecedor.Fornecedor;
import com.infinity.datamarket.comum.operacao.ConstantesEventoOperacao;
import com.infinity.datamarket.comum.operacao.ConstantesOperacao;
import com.infinity.datamarket.comum.operacao.EventoOperacaoItemRegistrado;
import com.infinity.datamarket.comum.operacao.EventoOperacaoPK;
import com.infinity.datamarket.comum.operacao.Operacao;
import com.infinity.datamarket.comum.operacao.OperacaoDevolucao;
import com.infinity.datamarket.comum.operacao.OperacaoPK;
import com.infinity.datamarket.comum.operacao.ProdutoOperacaoItemRegistrado;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter.IntervalObject;
import com.infinity.datamarket.comum.transacao.ClienteTransacao;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ConjuntoEventoOperacao;
import com.infinity.datamarket.comum.util.Util;
import com.infinity.datamarket.enterprise.gui.login.LoginBackBean;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

public class DevolucaoBackBean extends BackBean {
	
	private OperacaoDevolucao operacaoDevolucao;

	private static int sequencialProdutoOperacaoEventoRegistrado = 0;
	private OperacaoPK id;
	
	private SelectItem[] lojas;
	private String idLoja;
	private String idOperacaoDevolucao;
	
	private Date dataDevolucao;

	private String idTipoPessoa = new String(Fornecedor.PESSOA_FISICA);
	private SelectItem[] listaTipoPessoa;
	private String cpfCnpj;
	private String nomeCliente;
	private ClienteTransacao clienteTransacao;

	private String codigoProduto;
	private String descricaoProduto;
	private BigDecimal precoVenda;
	private BigDecimal quantidade;
	private BigDecimal valorItem;
	private List<EventoOperacaoItemRegistrado> eventosOperacao = new ArrayList<EventoOperacaoItemRegistrado>();
	
	private BigDecimal valorTotalDevolucao;

	private Date dataInicial;
	private Date dataFinal;

	private SelectItem[] listaSituacao;
	private String idSituacao = TransacaoVenda.ATIVO;
	
	private Collection devolucoes;

	public String getIdLoja() {
		return idLoja;
	}

	public void setIdLoja(String idLoja) {
		this.idLoja = idLoja;
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

	public Collection getDevolucoes() {
		return devolucoes;
	}

	public void setDevolucoes(Collection devolucoes) {
		this.devolucoes = devolucoes;
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

	public BigDecimal getValorTotalDevolucao() {
		return valorTotalDevolucao;
	}

	public void setValorTotalDevolucao(BigDecimal valorTotalDevolucao) {
		this.valorTotalDevolucao = valorTotalDevolucao;
	}
	
	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}	

	public BigDecimal getValorItem() {
		return valorItem;
	}

	public void setValorItem(BigDecimal valorItem) {
		this.valorItem = valorItem;
	}
	
	public String getIdTipoPessoa() {
		return idTipoPessoa;
	}

	public void setIdTipoPessoa(String idTipoPessoa) {
		this.idTipoPessoa = idTipoPessoa;
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

	public void setListaTipoPessoa(SelectItem[] listaTipoPessoa) {
		this.listaTipoPessoa = listaTipoPessoa;
	}

	public String getIdSituacao() {
		return idSituacao;
	}

	public void setIdSituacao(String idSituacao) {
		this.idSituacao = idSituacao;
	}
	
	public SelectItem[] getListaSituacao() {
		SelectItem[] listaSituacao = new SelectItem[2];
		listaSituacao[0] = new SelectItem(TransacaoVenda.ATIVO, "Ativa");
		listaSituacao[1] = new SelectItem(TransacaoVenda.CANCELADO, "Cancelada");
		if(this.getIdSituacao() == null || this.getIdSituacao().equals("")){
			this.setIdSituacao(TransacaoVenda.ATIVO);
		}
		return listaSituacao;
	}	

	public void setListaSituacao(SelectItem[] listaSituacao) {
		this.listaSituacao = listaSituacao;
	}
	
	public OperacaoDevolucao getOperacaoDevolucao() {
		return operacaoDevolucao;
	}

	public void setOperacaoDevolucao(OperacaoDevolucao operacaoDevolucao) {
		this.operacaoDevolucao = operacaoDevolucao;
	}
	
	public String getNomeCliente() {
		return nomeCliente;
//		return "nome do filho da puta do cliente...";
	}

	public void setNomeCliente(String nomeCliente) {
//		this.nomeCliente = nomeCliente;
	}
	
	public ClienteTransacao getClienteTransacao() {
		return clienteTransacao;
	}

	public void setClienteTransacao(ClienteTransacao clienteTransacao) {
		this.clienteTransacao = clienteTransacao;
	}
	
	public List<EventoOperacaoItemRegistrado> getEventosOperacao() {
		return eventosOperacao;
	}

	public void setEventosOperacao(
			List<EventoOperacaoItemRegistrado> eventosOperacao) {
		this.eventosOperacao = eventosOperacao;
	}
	
	public OperacaoPK getId() {
		return id;
	}

	public void setId(OperacaoPK id) {
		this.id = id;
	}
	
	public String voltarConsulta(){
		resetBB();
		return "voltar";
	}

	public String voltarMenu(){
		resetBB();
		return "voltar";
	}
	
	public String confirmarDevolucao(){
		resetBB();
		return "voltaInserir";
	}

	public void resetBB(){
		this.setIdLoja("0");
		this.setLojas(null);
		this.setDataDevolucao(null);
		this.setCodigoProduto(null);
		this.setDescricaoProduto(null);
		this.setPrecoVenda(new BigDecimal("0.00"));
		this.setQuantidade(new BigDecimal("1.000"));
		this.setValorTotalDevolucao(new BigDecimal("0.00"));
		this.setIdTipoPessoa(Cliente.PESSOA_FISICA);
		this.setCpfCnpj(null);
//		this.setNomeCliente(null);
		this.nomeCliente = null;
	}
	
	public String consultar(){
		try{
			FacesContext context = FacesContext.getCurrentInstance();
			Map params = context.getExternalContext().getRequestParameterMap();
			OperacaoPK param = null;
			if(params != null){				
				Integer loja = null;
				Integer id = null;
				if(params.get("operacao_loja") != null && !params.get("operacao_loja").equals("")){
					loja = new Integer(Integer.parseInt(params.get("operacao_loja").toString()));
				}
				if(params.get("operacao_id") != null && !params.get("operacao_id").equals("")){
					id = new Integer(Integer.parseInt(params.get("operacao_id").toString()));
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
				OperacaoDevolucao devolucao = (OperacaoDevolucao)getFachada().consultarOperacaoPorPK(getId());
				
				preencheBackBean(devolucao);
				
				return "proxima";
			} else if((this.getIdLoja() != null && !this.getIdLoja().equals("0"))
					|| (this.getIdOperacaoDevolucao() != null && !this.getIdOperacaoDevolucao().equals("0"))
					|| (this.getCpfCnpj() != null && !this.getCpfCnpj().equals(""))
					|| (this.getDataInicial() != null && this.getDataFinal() != null)){
				
				PropertyFilter filter = new PropertyFilter();
				
				OperacaoPK operacaoPk = new OperacaoPK();
				
				if (this.getIdLoja() != null && !this.getIdLoja().equals("0")){	
					operacaoPk.setLoja(Integer.parseInt(this.getIdLoja()));
				}
				
				filter.addProperty("pk", operacaoPk);

				if (this.getDataInicial() != null && this.getDataFinal() != null){	
					if(this.getDataInicial().after(this.getDataFinal())){
						throw new Exception("A Data Final deve ser maior que a Data Inicial.");
					}
					
					filter.addPropertyInterval("dataDevolucao", this.getDataInicial(), IntervalObject.MAIOR_IGUAL);
					this.getDataFinal().setDate(this.getDataFinal().getDate()+1);
					filter.addPropertyInterval("dataDevolucao", this.getDataFinal(), IntervalObject.MENOR_IGUAL);
				}
				
				if(this.getIdTipoPessoa() != null){
					if(this.getCpfCnpj() != null && !this.getCpfCnpj().equals("")){
						filter.setTheClass(TransacaoVenda.class);
						if(this.getIdTipoPessoa().equals(Cliente.PESSOA_FISICA)){
							filter.addProperty("cliente.tipoPessoa", Cliente.PESSOA_FISICA);
						}else{
							filter.addProperty("cliente.tipoPessoa", Cliente.PESSOA_JURIDICA);
						}
						filter.addProperty("cliente.cpfCnpj", this.getCpfCnpj().replace(".", "").replace("-", "").replace("/", ""));
					}else{
						filter.setTheClass(Operacao.class);	
					}						
				}else{
					filter.setTheClass(Operacao.class);
				}				

				this.nomeCliente = this.getNomeCliente();
				
				Collection col = getFachada().consultarOperacao(filter);
				
				if (col == null || col.size() == 0){
					setExisteRegistros(false);
					this.setDevolucoes(null);
					FacesContext ctx = FacesContext.getCurrentInstance();
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					ctx.addMessage(null, msg);					
				}else if (col != null){
					if(col.size() == 1){
						
						preencheBackBean((OperacaoDevolucao)col.iterator().next());

						return "proxima";
					}else{
						setExisteRegistros(true);
						this.setDevolucoes(col);
					}
				}
			}else{
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(TransacaoVenda.class);
				Collection c = getFachada().consultarOperacao(filter);
				if(c != null && c.size() > 0){
					this.setDevolucoes(c);
					setExisteRegistros(true);
				}else{
					this.setDevolucoes(null);
					setExisteRegistros(false);
				}
			}
		}catch(ObjectNotFoundException e){
			setExisteRegistros(false);
			this.setDevolucoes(null);
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
	
	public void preencheBackBean(OperacaoDevolucao devolucao){
		
	}
	
	public void validaDadosDevolucao() throws AppException{
		if(this.getIdLoja() == null || this.getIdLoja().equals("0")){
			throw new AppException("Selecione a Loja onde será feita a Devolução!");
		}
		
		if(this.getDataDevolucao() == null || this.getDataDevolucao().equals("")){
			throw new AppException("Informe a Data da Devolução!");
		}
		
		if(this.getCpfCnpj() == null || this.getCpfCnpj().equals("")){
			throw new AppException("Informe o CPF/CNPJ do Cliente.");
		}
	}
	
	public Produto validarItemRegistrado() throws AppException{
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
			throw new AppException("O Preço de Venda informado é inválido!");
		}
		if(this.getQuantidade() == null || (this.getQuantidade() != null && this.getQuantidade().compareTo(BigDecimal.ZERO.setScale(3)) <= 0)){
			throw new AppException("A Quantidade informada é inválida!");
		}
		return produto;
	}

	
	public void inserirProdutoDevolucao(){
		BigDecimal valorTotalItem = BigDecimal.ZERO;
		Produto produto = null;
		try {			
			produto = validarItemRegistrado();			
			
			ProdutoOperacaoItemRegistrado produtoOperacaoItemRegistrado = new ProdutoOperacaoItemRegistrado();
			
			produtoOperacaoItemRegistrado.setPk(new EventoOperacaoPK());
			produtoOperacaoItemRegistrado.getPk().setLoja(Integer.parseInt(this.getIdLoja()));
			produtoOperacaoItemRegistrado.getPk().setId(++sequencialProdutoOperacaoEventoRegistrado);
			
			if(produto == null){
				produto = getFachada().consultarProdutoPorPK(new Long(this.getCodigoProduto()));	
			}

			produtoOperacaoItemRegistrado.setCodigoExterno(produto.getCodigoExterno());
			produtoOperacaoItemRegistrado.setDescricaoCompleta(produto.getDescricaoCompleta());
			produtoOperacaoItemRegistrado.setIdProduto(produto.getId().intValue());
			produtoOperacaoItemRegistrado.setImpostoImpressora(null);
			produtoOperacaoItemRegistrado.setPercentual(null);
//			produtoOperacaoItemRegistrado.setPrecoPadrao(produto.getPrecoPadrao());
			produtoOperacaoItemRegistrado.setPrecoPadrao(this.getPrecoVenda());
			produtoOperacaoItemRegistrado.setPrecoPraticado(produto.getPrecoPadrao());
			
			valorTotalItem = this.getPrecoVenda().multiply(this.getQuantidade()).setScale(2, BigDecimal.ROUND_DOWN);
			
			EventoOperacaoItemRegistrado eventoOperacaoItemRegistrado = 
					new EventoOperacaoItemRegistrado(produtoOperacaoItemRegistrado.getPk(), 
											 ConstantesEventoOperacao.EVENTO_OPERACAO_ITEM_REGISTRADO,
											 new Date(), 
											 valorTotalItem,
											 this.getQuantidade(),
											 null,
											 null,
											 produtoOperacaoItemRegistrado);
			
			
			if(this.getEventosOperacao() == null){
				this.setEventosOperacao(new ArrayList<EventoOperacaoItemRegistrado>());
			}
		
			this.getEventosOperacao().add(eventoOperacaoItemRegistrado);
			
			this.setValorTotalDevolucao(this.getValorTotalDevolucao().add(valorTotalItem).setScale(2));
			
			this.setCodigoProduto("");
			this.setDescricaoProduto("");
			this.setPrecoVenda(new BigDecimal("000").movePointLeft(2));
			this.setQuantidade(new BigDecimal("1000").movePointLeft(3));
			this.setValorItem(new BigDecimal("000").movePointLeft(2));
		} catch (AppException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			ctx.addMessage(null, msg);
		}
	}
	
	public void removerProdutoDevolucao(ActionEvent event){
	    UIParameter component = (UIParameter) event.getComponent().findComponent("idExcluirProdutoDevolucao");
	    Integer param = (Integer) component.getValue(); 
	
		Iterator i = this.getEventosOperacao().iterator();
		while(i.hasNext()){
			EventoOperacaoItemRegistrado evOpItReg = (EventoOperacaoItemRegistrado) i.next();
			if (evOpItReg.getPk().getNumeroEvento() == param){
				this.getEventosOperacao().remove(evOpItReg);
				this.setValorTotalDevolucao(this.getValorTotalDevolucao().subtract(evOpItReg.getPreco()));
				break;
			}
		}
	}
	
	public OperacaoDevolucao preencheOperacaoDevolucao(String operacao) throws AppException{
		OperacaoDevolucao devolucao = new OperacaoDevolucao();
		OperacaoPK pk = new OperacaoPK();
		pk.setLoja(Integer.parseInt(this.getIdLoja()));
		pk.setId(getFachada().retornaMaxIdOperacaoPorLoja(pk).intValue());					
		devolucao.setPk(pk);
		
		devolucao.setData(this.getDataDevolucao());
		devolucao.setCliente(this.getClienteTransacao());
		devolucao.setStatus(ConstantesOperacao.ABERTO);
		devolucao.setTipo(ConstantesOperacao.OPERACAO_DEVOLUCAO);
		devolucao.setCodigoUsuarioOperador(LoginBackBean.getCodigoUsuarioLogado());
		devolucao.setValor(this.getValorTotalDevolucao());
		
		ConjuntoEventoOperacao ceo = new ConjuntoEventoOperacao();
		
		Iterator it = this.getEventosOperacao().iterator();
		while(it.hasNext()){
			EventoOperacaoItemRegistrado evOpItReg = (EventoOperacaoItemRegistrado)it.next();
			ceo.add(evOpItReg);
		}
		
		devolucao.setEventosOperacao(ceo);
		return devolucao;
	}
	
	public String inserir(){
		try {
			validaDadosDevolucao();
			
			OperacaoDevolucao devolucao = preencheOperacaoDevolucao(INSERIR);
			
			if(devolucao.getEventosOperacao() == null || devolucao.getEventosOperacao().size() == 0){
				throw new AppException("É necessário informar os itens da Devolução!");
			}
			
//			getFachada().inserirOperacaoDevolucao(devolucao);
			
			this.setOperacaoDevolucao(devolucao);
			this.setIdOperacaoDevolucao(String.valueOf(devolucao.getPk().getId()));
			resetBB();
		} catch (ObjectExistentException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Devolução já Existente!", "");
			ctx.addMessage(null, msg);
			return "mesma";
		} catch (AppException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			ctx.addMessage(null, msg);
			return "mesma";
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
			return "mesma";
		}		
		return "proximaOk";
	}
	
	public String alterar(){
		try {

			validaDadosDevolucao();
		
			OperacaoDevolucao devolucao = preencheOperacaoDevolucao(ALTERAR);
			
//			getFachada().alterarOperacaoDevolucao(devolucao);
			
			this.setOperacaoDevolucao(devolucao);
			
			resetBB();
		} catch (ObjectExistentException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Devolução já Existente!", "");
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
		return "proximaOk";
	}

	public String cancelar(){
		try {

			validaDadosDevolucao();
		
			OperacaoDevolucao devolucao = preencheOperacaoDevolucao(CANCELAR);
			
//			getFachada().alterarOperacaoDevolucao(devolucao);
			
			this.setOperacaoDevolucao(devolucao);
			
			resetBB();
		} catch (ObjectExistentException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Devolução já Existente!", "");
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
		return "proximaOk";
	}

	
	public void imprimirRecibo(){
		try {
			if(this.getOperacaoDevolucao() != null){
				FacesContext context = FacesContext.getCurrentInstance();
				HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();			
				ServletOutputStream servletOutputStream = response.getOutputStream();
//				getFachada().gerarReciboVenda(this.getOperacaoDevolucao(),servletOutputStream);			
				response.setContentType("application/pdf");
				response.setHeader("Content-disposition", "attachment;filename=ReciboOperacaoDevolucao" + System.currentTimeMillis() + ".pdf");
				context.responseComplete();
				servletOutputStream.flush();
				servletOutputStream.close();
			}else{
				FacesContext ctx = FacesContext.getCurrentInstance();
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Não existe Recibo para imprimir!", "");
				ctx.addMessage(null, msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param init the init to set
	 */
	public void setInit(HtmlForm init) {
		FacesContext context = FacesContext.getCurrentInstance();
		Map params = context.getExternalContext().getRequestParameterMap();            
		String param = (String)  params.get(ACAO);

		System.out.println((String)  params.get("acaoLocal"));
		if (param != null){
			resetBB();
			if(VALOR_ACAO.equals(param)){
				setDevolucoes(null);
			}
		}else if(params.get("acaoLocal") != null && ((String)params.get("acaoLocal")).equals("pesquisarProdutos")){
			try {
				Produto prod = getFachada().consultarProdutoPorPK(new Long((String)params.get("codigoProduto")));
				if(prod != null){
					this.setDescricaoProduto(prod.getDescricaoCompleta());
					this.setPrecoVenda(prod.getPrecoPadrao());					
				}
			} catch (Exception e) {				
				e.printStackTrace();			
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
//						this.setNomeCliente(clienteTransacao.getNomeCliente());
						this.nomeCliente = clienteTransacao.getNomeCliente();
					}else{
//						this.setNomeCliente(clienteTransacao.getRazaoSocial());
						this.nomeCliente = clienteTransacao.getRazaoSocial();
					}			
				}
			} catch (ObjectNotFoundException e){
				FacesContext ctx = FacesContext.getCurrentInstance();
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Cliente com o CPF/CNPF informado Inexistente no Cadastro!", "");
				ctx.addMessage(null, msg);
				this.cpfCnpj = (String)params.get("cpfCnpj");
			} catch (Exception e) {
				e.printStackTrace();			
			}
		}
	}

	public String getIdOperacaoDevolucao() {
		return Util.completaString(idOperacaoDevolucao, "0", 9, false);
	}

	public void setIdOperacaoDevolucao(String idOperacaoDevolucao) {
		this.idOperacaoDevolucao = idOperacaoDevolucao;
	}
}