package com.infinity.datamarket.enterprise.gui.baixaLancamento;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.hibernate.collection.PersistentSet;

import com.infinity.datamarket.comum.banco.Banco;
import com.infinity.datamarket.comum.cliente.Cliente;
import com.infinity.datamarket.comum.conta.ContaCorrente;
import com.infinity.datamarket.comum.financeiro.BaixaLancamento;
import com.infinity.datamarket.comum.financeiro.BaixaLancamentoPK;
import com.infinity.datamarket.comum.financeiro.GrupoLancamento;
import com.infinity.datamarket.comum.financeiro.Lancamento;
import com.infinity.datamarket.comum.fornecedor.Fornecedor;
import com.infinity.datamarket.comum.pagamento.ConstantesFormaRecebimento;
import com.infinity.datamarket.comum.pagamento.FormaRecebimento;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter.IntervalObject;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Util;
import com.infinity.datamarket.enterprise.gui.login.LoginBackBean;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

public class BaixaLancamentoBackBean extends BackBean {

	Logger logger = Logger.getLogger(BaixaLancamentoBackBean.class);

	private String id;
	private Lancamento lancamento;
	private String idLoja;
	private String idGrupo;
	private String idFornecedor;
	private Long idEntradaProduto;
	private String idSituacao;
	private String descricao;
	private String tipoLancamento;
	private Date dataLancamento = new Date(System.currentTimeMillis());
	private Date dataVencimento;
	private Date dataPagamento;
	private String numeroDocumento;
	private String observacao;
	private String descricaoSituacao;
	private BigDecimal valor;

	private String idContaCorrente;
	private String idFormaRecebimento;
	private String idBanco;
	private String agencia;
	private String numeroConta;
	private String numeroCheque;
	private String idTipoPessoa;
	private String cpfCnpj;
	private Date dataCheque;
	private String numeroDocumentoItemBaixa;	
	private String detalheFormaRecebimento;

	private BigDecimal valorItem;
	private BigDecimal valorAcrescimo;
	private BigDecimal valorDesconto;
	private BigDecimal valorTotalItem;
	private BigDecimal valorTotalPago;

	private Date dataInicial;
	private Date dataFinal;	
	
	private SelectItem[] listaTipoPessoa;
	private SelectItem[] listaSituacao;
	private SelectItem[] contasCorrentes;
	private SelectItem[] bancos;		
	private SelectItem[] fornecedores;	
	SelectItem[] formas,grupos;
	
	Collection lancamentos;
	
	private List<BaixaLancamento> itensBaixaLancamento = new ArrayList<BaixaLancamento>();
	private List<BaixaLancamento> itensBaixaLancamentoExcluidos = new ArrayList<BaixaLancamento>();
	
	private static long sequencialItemBaixaLancamento = 0;
	
	public List<BaixaLancamento> getItensBaixaLancamento() {
		return itensBaixaLancamento;
	}

	public void setItensBaixaLancamento(List<BaixaLancamento> itensBaixaLancamento) {
		this.itensBaixaLancamento = itensBaixaLancamento;
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

	public Date getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(Date dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
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

	public String getDescricaoSituacao() {
		return descricaoSituacao;
	}

	public void setDescricaoSituacao(String descricaoSituacao) {
		this.descricaoSituacao = descricaoSituacao;
	}

	public void setFormas(SelectItem[] formas) {
		this.formas = formas;
	}

	public void setFornecedores(SelectItem[] fornecedores) {
		this.fornecedores = fornecedores;
	}

	public void setGrupos(SelectItem[] grupos) {
		this.grupos = grupos;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getIdEntradaProduto() {
		return idEntradaProduto;
	}

	public void setIdEntradaProduto(Long idEntradaProduto) {
		this.idEntradaProduto = idEntradaProduto;
	}

	public String getIdFornecedor() {
		return idFornecedor;
	}

	public void setIdFornecedor(String idFornecedor) {
		this.idFornecedor = idFornecedor;
	}

	public String getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getIdLoja() {
		return idLoja;
	}

	public void setIdLoja(String idLoja) {
		this.idLoja = idLoja;
	}

	public String getIdSituacao() {
		return idSituacao;
	}

	public void setIdSituacao(String idSituacao) {
		this.idSituacao = idSituacao;
	}

	public Collection getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(Collection lancamentos) {
		this.lancamentos = lancamentos;
	}

	public SelectItem[] getListaSituacao() {
		SelectItem[] lista = new SelectItem[6];
		lista[0] = new SelectItem("T", "Todos");
		lista[1] = new SelectItem("A", "Aberto");
		lista[2] = new SelectItem("P", "Pago Parcial");
		lista[3] = new SelectItem("F", "Finalizado");
		lista[4] = new SelectItem("C", "Cancelado");
		lista[5] = new SelectItem("D", "Pendente");
		if(this.getIdSituacao() == null || this.getIdSituacao().equals("")){
			this.setIdSituacao("T");
		}
		return lista;
	}

	public void setListaSituacao(SelectItem[] listaSituacao) {
		this.listaSituacao = listaSituacao;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
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

	public BigDecimal getValorItem() {
		return valorItem;
	}

	public void setValorItem(BigDecimal valor) {
		this.valorItem = valor;
	}
	
	public String retornaDescricaoSituacao(String situacao){
		String descricao = "";
		
		if(situacao.equals("A")){
			descricao = "ABERTO";
		}else if(situacao.equals("P")){
			descricao = "PAGO PARCIAL";
		}else if(situacao.equals("F")){
			descricao = "FINALIZADO";
		}else if(situacao.equals("C")){
			descricao = "CANCELADO";
		}else if(situacao.equals("D")){
			descricao = "PENDENTE";
		}		
		return descricao;
	}
	
//	 Lojas 
	private Set<Loja> carregarLojas() {
		
//		List<Loja> lojas = null;
//		try {
//			lojas = (ArrayList<Loja>)getFachada().consultarTodosLoja();
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
	
	public SelectItem[] getLojas(){
		SelectItem[] arrayLojas = null;
		try {
			Set<Loja> lojas = carregarLojas();
			arrayLojas = new SelectItem[lojas.size()+1];
			int i = 0;
			arrayLojas[i++] = new SelectItem("0", "");
			for(Loja lojaTmp : lojas){
				SelectItem item = new SelectItem(lojaTmp.getId().toString(), lojaTmp.getNome());
				arrayLojas[i++] = item;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "   ");
			getContextoApp().addMessage(null, msg);
		}
		return arrayLojas;
	}
	
	// Formas
	private List<FormaRecebimento> carregarFormas() {
		
		List<FormaRecebimento> formas = null;
		
		try {
			PropertyFilter filter = new PropertyFilter();

			filter.setTheClass(FormaRecebimento.class);
			if(this.getTipoLancamento().equals(Lancamento.CREDITO)){
				filter.addProperty("validaRecebimentoConta", "S");	
			}else if(this.getTipoLancamento().equals(Lancamento.DEBITO)){
				filter.addProperty("validaPagamentoConta", "S");	
			}		
			
			formas = (ArrayList<FormaRecebimento>)getFachada().consultarFormaRecebimento(filter);
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return formas;
	}

	public SelectItem[] getFormas() {
		SelectItem[] arrayFormas = null;
		try {
			List<FormaRecebimento> formas = carregarFormas();
			arrayFormas = new SelectItem[formas.size()+1];
			int i = 0;
			arrayFormas[i++] = new SelectItem("0", "");
			for(FormaRecebimento formaTmp : formas){
				SelectItem item = new SelectItem(formaTmp.getId().toString(), formaTmp.getDescricao());
				arrayFormas[i++] = item;
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return arrayFormas;

	}

//	 Fornecedores
	private List<Fornecedor> carregarFornecedores() {
		
		List<Fornecedor> fornecedores = null;
		try {
			fornecedores = (ArrayList<Fornecedor>)getFachada().consultarTodosFornecedores();
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return fornecedores;
	}

	public SelectItem[] getFornecedores() {
		SelectItem[] arrayFornecedores = null;
		try {
			List<Fornecedor> fornecedores = carregarFornecedores();
			arrayFornecedores = new SelectItem[fornecedores.size()+1];
			int i = 0;
			arrayFornecedores[i++] = new SelectItem("0", "");
			for(Fornecedor formaTmp : fornecedores){
				SelectItem item = new SelectItem(formaTmp.getId().toString(), formaTmp.getTipoPessoa().equals(Fornecedor.PESSOA_FISICA)?formaTmp.getNomeFornecedor():formaTmp.getNomeFantasia());
				arrayFornecedores[i++] = item;
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return arrayFornecedores;

	}

//	Grupos
	private List<GrupoLancamento> carregarGrupos() {
		
		List<GrupoLancamento> grupos = null;
		try {
			grupos = (ArrayList<GrupoLancamento>)getFachada().consultarTodosGrupoLancamentos();
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return grupos;
	}

	public SelectItem[] getGrupos() {
		SelectItem[] arrayGrupos = null;
		try {
			List<GrupoLancamento> formas = carregarGrupos();
			arrayGrupos = new SelectItem[formas.size()+1];
			int i = 0;
			arrayGrupos[i++] = new SelectItem("0", "");
			for(GrupoLancamento grupoTmp : formas){
				SelectItem item = new SelectItem(grupoTmp.getId().toString(), grupoTmp.getDescricao());
				arrayGrupos[i++] = item;
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return arrayGrupos;
	}
	
	public void resetBB(){
		this.setId(null);
		this.setDescricao(null);
		this.setTipoLancamento(Lancamento.DEBITO);
		this.setDataLancamento(new Date(System.currentTimeMillis()));
		this.setDataPagamento(null);
		this.setDataVencimento(null);
		this.setIdGrupo("0");
		this.setIdEntradaProduto(null);
		this.setIdLoja("0");
		this.setIdFornecedor("0");
		this.setNumeroDocumento(null);
		this.setObservacao(null);
		this.setValorItem(null);
		this.setDataInicial(null);
		this.setDataFinal(null);
		this.setIdSituacao("T");
		this.setDescricaoSituacao(retornaDescricaoSituacao(this.getIdSituacao()));
		this.setItensBaixaLancamento(null);
		this.setItensBaixaLancamentoExcluidos(null);
	}
	
	public String consultar(){
		try{
			FacesContext context = FacesContext.getCurrentInstance();
			Map params = context.getExternalContext().getRequestParameterMap();
			String param = (String)  params.get("id");
			if (param != null && !"".equals(param)){
				setId(param);
			}
			if (getId() != null && !"".equals(getId())){
				Lancamento lancamento = getFachada().consultarLancamentoPorId(new Long(getId()));
				preencheBackBean(lancamento);
				if(this.getTipoLancamento().equals(Lancamento.DEBITO)){
					return "proximaContaAPagar";	
				}else if(this.getTipoLancamento().equals(Lancamento.CREDITO)){
					return "proximaContaAReceber";
				}				
			}else if (this.getIdLoja() != null || this.getIdGrupo() != null ||
					this.getIdFornecedor() != null || this.getDescricao() != null ||
					(this.getDataInicial() != null && this.getDataFinal() != null) || this.getTipoLancamento() != null){
				 
				PropertyFilter filter = new PropertyFilter();

				filter.setTheClass(Lancamento.class);
				
				if(this.idLoja != null && !this.idLoja.equals("0")){
					Loja loja = new Loja();
					loja.setId(new Long(this.idLoja));
					filter.addProperty("loja", loja);	
				}
				
				if(this.idFornecedor != null && !this.idFornecedor.equals("0")){
					Fornecedor fornecedor = new Fornecedor();
					fornecedor.setId(new Long(this.idFornecedor));
					filter.addProperty("fornecedor", fornecedor);	
				}
				
				if(this.idGrupo != null && !this.idGrupo.equals("0")){
					GrupoLancamento grupo = new GrupoLancamento();
					grupo.setId(new Long(this.idGrupo));
					filter.addProperty("grupo", grupo);	
				}
				
				if(this.descricao != null && !this.descricao.equals("")){
					filter.addProperty("descricao", this.descricao);
				}
				
				filter.addProperty("tipoLancamento", this.tipoLancamento);
				
				if (this.getDataInicial() != null && this.getDataFinal() != null){	
					if(this.getDataInicial().after(this.getDataFinal())){
						throw new Exception("A Data Final deve ser maior que a Data Inicial.");
					}
					
					filter.addPropertyInterval("dataVencimento", this.getDataInicial(), IntervalObject.MAIOR_IGUAL);
					Date dataFinal = new Date(this.getDataFinal().getTime());
					
					dataFinal.setDate(dataFinal.getDate()+1);
					filter.addPropertyInterval("dataVencimento", dataFinal, IntervalObject.MENOR_IGUAL);
				}
				
				if(this.getIdSituacao() != null && !this.getIdSituacao().equals("T")){
					filter.addProperty("situacao", this.getIdSituacao());	
				}
				
				Collection col = getFachada().consultarLancamento(filter);
				if (col == null || col.size() == 0){
					setExisteRegistros(false);
					this.setLancamentos(col);
					
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					getContextoApp().addMessage(null, msg);
				}else if (col != null){
					if(col.size() == 1){
						preencheBackBean((Lancamento)col.iterator().next());
						if(this.getTipoLancamento().equals(Lancamento.DEBITO)){
							return "proximaContaAPagar";	
						}else if(this.getTipoLancamento().equals(Lancamento.CREDITO)){
							return "proximaContaAReceber";
						}
					}else{
						setExisteRegistros(true);
						this.setLancamentos(col);
					}
				}
			}else{
				Collection c = getFachada().consultarTodasLancamentos();
				if(c != null && c.size() > 0){
					setExisteRegistros(true);
					setLancamentos(c);
				}else{
					setExisteRegistros(false);
					setLancamentos(null);
					
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					getContextoApp().addMessage(null, msg);
				}
			}
		}catch(ObjectNotFoundException e){
			setExisteRegistros(false);
			this.setLancamentos(null);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Nenhum Registro Encontrado", "");
			getContextoApp().addMessage(null, msg);
		}catch(Exception e){
			setExisteRegistros(false);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return "mesma";
	}
	
	public void preencheBackBean(Lancamento lancamento){
		this.setLancamento(lancamento);
		this.setId(lancamento.getId().toString());
		this.setDescricao(lancamento.getDescricao());
		this.setTipoLancamento(lancamento.getTipoLancamento());
		this.setDataLancamento(lancamento.getDataLancamento());
		this.setDataPagamento(lancamento.getDataPagamento());
		this.setDataVencimento(lancamento.getDataVencimento());
		this.setIdGrupo(lancamento.getGrupo().getId().toString());
		this.setIdEntradaProduto(lancamento.getIdEntradaProduto());
		this.setIdLoja(lancamento.getLoja().getId().toString());
		this.setNumeroDocumento(lancamento.getNumeroDocumento());
		this.setObservacao(lancamento.getObservacao());
		this.setValor(lancamento.getValor());
		this.setIdSituacao(lancamento.getSituacao());
		this.setDescricaoSituacao(retornaDescricaoSituacao(this.getIdSituacao()));
		
		if(lancamento.getFornecedor() != null){
			this.setIdFornecedor(lancamento.getFornecedor().getId().toString());	
		}
		
		this.setValorTotalPago(BigDecimal.ZERO.setScale(2));
		
		if(lancamento.getItensPagamento() != null){
//			if(this.getItensBaixaLancamento() == null){
				this.setItensBaixaLancamento(new ArrayList<BaixaLancamento>());
//			}
			
			Iterator<BaixaLancamento> it = lancamento.getItensPagamento().iterator();
			while(it.hasNext()){
				BaixaLancamento obj = it.next();

				if(obj.getFormaRecebimento().getId().toString().equals(ConstantesFormaRecebimento.CHEQUE)){
					String detalheForma = obj.getBanco().getId() + "/" + obj.getAgencia() + "/" + obj.getNumeroConta() + "/" + 
										  obj.getNumeroCheque() + "/ \n" + Util.formataCpfCnpj(obj.getCpfCnpjCheque()) + "/" + obj.getNumeroDocumento() + "/" + 
							              Util.retornaDataFormatoDDMMYYYY(obj.getDataCheque());
					obj.setDetalheFormaRecebimento(detalheForma);
				}
				
				this.getItensBaixaLancamento().add(obj);
				
				if(obj.getSituacao().equals(BaixaLancamento.ATIVO)){
					this.setValorTotalPago(this.getValorTotalPago().add(obj.getValorTotalItem()).setScale(2));
				}
			}
			
			sequencialItemBaixaLancamento = retornaMaxCodigoItem();
		}
		resetCamposItemBaixaLancamento();
	}
	
	public long retornaMaxCodigoItem(){
		long codigo = 0;
		
		if(this.getItensBaixaLancamento() != null){
			for (Iterator iter = this.getItensBaixaLancamento().iterator(); iter.hasNext();) {
				BaixaLancamento item = (BaixaLancamento) iter.next();
				if(item.getPk().getId().longValue() > codigo){
					codigo = item.getPk().getId().longValue();
				}
			}
		}
		
		return ++codigo;
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
	
	public void validarItemBaixaLancamento(BaixaLancamento itemBaixaLanc) throws AppException{

		if(this.getIdContaCorrente() == null || this.getIdContaCorrente().equals("0")){
			throw new AppException("O Campo Conta Corrente é Obrigatório!");
		}

		if(this.getIdFormaRecebimento() == null || this.getIdFormaRecebimento().equals("0")){
			throw new AppException("O Campo Forma de Recebimento é Obrigatório!");
		}

		if(this.getValorItem() == null || this.getValorItem().setScale(2).equals(BigDecimal.ZERO.setScale(2))){
			throw new AppException("O Campo Valor deve ser maior que zero (0)!");
		}
		
		if(this.getValorDesconto() == null || this.getValorDesconto().setScale(2).compareTo(this.getValorItem().setScale(2)) > 0){
			throw new AppException("O Campo Valor Desconto não deve ser maior que o Campo Valor!");
		}
		
		if(this.getValorTotalItem().setScale(2).compareTo(BigDecimal.ZERO.setScale(2)) <= 0){
			throw new AppException("O Campo Valor Total Item não pode ser menor ou igual a zero (0)!");
		}
		
		if(this.getIdFormaRecebimento().equals(ConstantesFormaRecebimento.CHEQUE.toString())){
			if(this.getIdBanco() == null || this.getIdBanco().equals("0")){
				throw new AppException("O Campo Banco é Obrigatório!");
			}			
			if(this.getAgencia() == null || this.getAgencia().equals("")){
				throw new AppException("O Campo Agência é Obrigatório!");
			}
			if(this.getNumeroConta() == null || this.getNumeroConta().equals("")){
				throw new AppException("O Campo Número Conta é Obrigatório!");
			}
			if(this.getNumeroCheque() == null || this.getNumeroCheque().equals("")){
				throw new AppException("O Campo Número Cheque é Obrigatório!");
			}
			if(this.getIdTipoPessoa() == null || this.getIdTipoPessoa().equals("")){
				throw new AppException("O Campo Tipo Pessoa é Obrigatório!");
			}
			if(this.getCpfCnpj() == null || this.getCpfCnpj().equals("")){
				throw new AppException("O Campo CPF/CNPJ é Obrigatório!");
			}
			if(this.getDataCheque() == null || this.getDataCheque().equals("")){
				throw new AppException("O Campo Data Cheque é Obrigatório!");
			}
		}
	}
	
	public void resetCamposItemBaixaLancamento(){
		this.setIdFormaRecebimento("0");
		this.setIdContaCorrente("0");
		this.setValorItem(BigDecimal.ZERO.setScale(2));
		this.setValorAcrescimo(BigDecimal.ZERO.setScale(2));
		this.setValorDesconto(BigDecimal.ZERO.setScale(2));
		this.setValorTotalItem(BigDecimal.ZERO.setScale(2));
		this.setIdBanco("0");
		this.setAgencia("");
		this.setNumeroConta("");
		this.setNumeroCheque("");
		this.setNumeroDocumentoItemBaixa("");
		this.setDataCheque(getDataSistema());
		this.setIdTipoPessoa(Cliente.PESSOA_FISICA);
		this.setCpfCnpj("");
	}
	
	public void inserirItemBaixaLancamento(){
		logger.info("inserirItemBaixaLancamento - INICIO");
		try {
			if(this.getItensBaixaLancamento() == null){
				this.setItensBaixaLancamento(new ArrayList<BaixaLancamento>());
				sequencialItemBaixaLancamento = 0;
			}else{
				sequencialItemBaixaLancamento = retornaMaxCodigoItem();
			}
			
			logger.info("inserirItemBaixaLancamento - sequencialItemBaixaLancamento --> "+sequencialItemBaixaLancamento);
			
			BaixaLancamento itemBaixaLanc = new BaixaLancamento();
			
			BaixaLancamentoPK itemPk = new BaixaLancamentoPK(new Long(sequencialItemBaixaLancamento), this.getLancamento().getId());
			itemBaixaLanc.setPk(itemPk);
			
//			Loja loja = new Loja();
//			loja.setId(new Long(this.getIdLoja()));
//			itemBaixaLanc.setLoja(loja);
//			
//			logger.info("inserirItemBaixaLancamento - idLoja --> "+this.getIdLoja());
			
			FormaRecebimento formaReceb = new FormaRecebimento();
			formaReceb.setId(new Long(this.getIdFormaRecebimento()));
			itemBaixaLanc.setFormaRecebimento(getFachada().consultarFormaRecebimentoPorId(formaReceb.getId()));
			
			logger.info("inserirItemBaixaLancamento - idFormaRecebimento --> "+this.getIdFormaRecebimento());
			
			ContaCorrente contaCorrente = new ContaCorrente();
			contaCorrente.setId(new Long(this.getIdContaCorrente()));
			itemBaixaLanc.setContaCorrente(getFachada().consultarContaCorrentePorID(contaCorrente.getId().toString()));
			
			logger.info("inserirItemBaixaLancamento - idContaCorrente --> "+this.getIdContaCorrente());
			
			itemBaixaLanc.setValor(this.getValorItem().setScale(2));
			itemBaixaLanc.setValorAcrescimo(this.getValorAcrescimo().setScale(2));
			itemBaixaLanc.setValorDesconto(this.getValorDesconto().setScale(2));
			
			logger.info("inserirItemBaixaLancamento - valor --> "+this.getValorItem().setScale(2));
			logger.info("inserirItemBaixaLancamento - valorDesconto --> "+this.getValorDesconto().setScale(2));
			logger.info("inserirItemBaixaLancamento - valorAcrescimo --> "+this.getValorAcrescimo().setScale(2));
			
			this.setValorTotalItem(this.getValorItem().add(this.getValorAcrescimo()).subtract(this.getValorDesconto()).setScale(2));
			
			itemBaixaLanc.setValorTotalItem(this.getValorTotalItem().setScale(2));
			
			logger.info("inserirItemBaixaLancamento - valorTotalItem --> "+itemBaixaLanc.getValorTotalItem().setScale(2));
			
			if(this.getValorTotalPago() == null){
				this.setValorTotalPago(BigDecimal.ZERO.setScale(2));
			}
			
			if(this.getIdFormaRecebimento().equals(ConstantesFormaRecebimento.CHEQUE.toString())){
				logger.info("inserirItemBaixaLancamento - forma cheque - inicio");
				Banco banco = new Banco();
				banco.setId(new Long(this.getIdBanco()));
				itemBaixaLanc.setBanco(banco);
				itemBaixaLanc.setAgencia(this.getAgencia());
				itemBaixaLanc.setNumeroConta(this.getNumeroConta());
				itemBaixaLanc.setNumeroCheque(this.getNumeroCheque());
				itemBaixaLanc.setTipoPessoaCheque(this.getIdTipoPessoa());
				itemBaixaLanc.setCpfCnpjCheque(this.getCpfCnpj().replace(".","").replace("-","").replace("/",""));
				itemBaixaLanc.setNumeroDocumento(this.getNumeroDocumentoItemBaixa());
				itemBaixaLanc.setDataCheque(this.getDataCheque());
				
				logger.info("inserirItemBaixaLancamento - banco --> "+this.getIdBanco());
				logger.info("inserirItemBaixaLancamento - agencia --> "+this.getAgencia());
				logger.info("inserirItemBaixaLancamento - numeroConta --> "+this.getNumeroConta());
				logger.info("inserirItemBaixaLancamento - numeroCheque --> "+this.getNumeroCheque());
				logger.info("inserirItemBaixaLancamento - tipoPessoa --> "+this.getIdTipoPessoa());
				logger.info("inserirItemBaixaLancamento - cpfCnpj --> "+this.getCpfCnpj());
				logger.info("inserirItemBaixaLancamento - numeroDocumentoCheque --> "+this.getNumeroDocumentoItemBaixa());
				logger.info("inserirItemBaixaLancamento - dataCheque --> "+Util.retornaDataFormatoDDMMYYYY(this.getDataCheque()));				
				
				String detalheForma = this.getIdBanco() + "/" + this.getAgencia() + "/" + this.getNumeroConta() + "/" + 
				                      this.getNumeroCheque() + "/ \n" + this.getCpfCnpj() + "/" + this.getNumeroDocumentoItemBaixa() + "/" + 
				                      Util.retornaDataFormatoDDMMYYYY(this.getDataCheque());
				itemBaixaLanc.setDetalheFormaRecebimento(detalheForma);
				logger.info("inserirItemBaixaLancamento - detalheForma --> "+detalheForma);
				detalheForma = null;
				logger.info("inserirItemBaixaLancamento - forma cheque - fim");                      
			}
			
			itemBaixaLanc.setItemLancadoCtaCorrente("N");
			logger.info("inserirItemBaixaLancamento - itemLancadoCtaCorrente --> "+itemBaixaLanc.getItemLancadoCtaCorrente());
			itemBaixaLanc.setSituacao(BaixaLancamento.ATIVO);
			logger.info("inserirItemBaixaLancamento - situacaoItem --> "+itemBaixaLanc.getSituacao());
			
			itemBaixaLanc.setDataBaixa(getDataSistema());
			logger.info("inserirItemBaixaLancamento - dataCheque --> "+Util.retornaDataFormatoDDMMYYYY(itemBaixaLanc.getDataBaixa()));
			
			logger.info("inserirItemBaixaLancamento - validando Item");
			validarItemBaixaLancamento(itemBaixaLanc);
			logger.info("inserirItemBaixaLancamento - validou Item");
			
			logger.info("inserirItemBaixaLancamento - valorTotalPago antes --> "+this.getValorTotalPago().setScale(2));
			
			this.setValorTotalPago(this.getValorTotalPago().add(itemBaixaLanc.getValorTotalItem()).setScale(2));
			
			logger.info("inserirItemBaixaLancamento - valorTotalPago depois --> "+this.getValorTotalPago().setScale(2));
			
			if(this.getValorTotalPago().setScale(2).compareTo(this.getLancamento().getValor().setScale(2)) > 0){
				this.setValorTotalPago(this.getValorTotalPago().subtract(itemBaixaLanc.getValorTotalItem()).setScale(2));
				throw new AppException("O Valor Total Pago não pode ser maior que o Valor do Lançamento!");
			}

			logger.info("inserirItemBaixaLancamento - inserindo Item na lista");
			this.getItensBaixaLancamento().add(itemBaixaLanc);	
			logger.info("inserirItemBaixaLancamento - inseriu Item na lista");
			
			logger.info("inserirItemBaixaLancamento - limpando os campos");
			resetCamposItemBaixaLancamento();
			logger.info("inserirItemBaixaLancamento - limpou os campos");
		} catch (AppException e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			getContextoApp().addMessage(null, msg);
		}
		logger.info("inserirItemBaixaLancamento - FIM");
	}
	
	public void removerItemBaixaLancamento(ActionEvent event){
		logger.info("removerItemBaixaLancamento - INICIO");
		
		if(this.getItensBaixaLancamentoExcluidos() == null){
			this.setItensBaixaLancamentoExcluidos(new ArrayList<BaixaLancamento>());
		}
		
        UIParameter component = (UIParameter) event.getComponent().findComponent("idExcluirItemBaixaLancamento");
        Long param = (Long) component.getValue(); 
        
        logger.info("removerItemBaixaLancamento - idExcluirItemBaixaLancamento --> "+param.longValue());

		Iterator i = this.getItensBaixaLancamento().iterator();
		while(i.hasNext()){
			BaixaLancamento itemBaixaLanc = (BaixaLancamento) i.next();
//			BaixaLancamentoPK itemPk = new BaixaLancamentoPK(param, this.getLancamento().getId());

			if (itemBaixaLanc.getPk().getId().equals(param)){
				
				this.getItensBaixaLancamento().remove(itemBaixaLanc);
				logger.info("removerItemBaixaLancamento - valorTotalPago antes --> "+this.getValorTotalPago().setScale(2));
				this.setValorTotalPago(this.getValorTotalPago().subtract(itemBaixaLanc.getValorTotalItem()));
				logger.info("removerItemBaixaLancamento - valorTotalPago depois --> "+this.getValorTotalPago().setScale(2));
				itemBaixaLanc.setSituacao(BaixaLancamento.CANCELADO);
				
				logger.info("removerItemBaixaLancamento - item removido --> "+itemBaixaLanc.getPk().getId());
				
				this.getItensBaixaLancamentoExcluidos().add(itemBaixaLanc);
				break;
			}
		}
		logger.info("removerItemBaixaLancamento - FIM");
	}
	
	public String baixarLancamento(){
		logger.info("baixarLancamento - INICIO");
		try {		
			Lancamento lancamento = this.getLancamento();
			
			Set<BaixaLancamento> c1 = new HashSet<BaixaLancamento>();
			
			for (Iterator iter = this.getItensBaixaLancamento().iterator(); iter.hasNext();) {
				BaixaLancamento element = (BaixaLancamento) iter.next();
				c1.add(element);
			}
			
			lancamento.setItensPagamento(c1);
			if(this.getItensBaixaLancamentoExcluidos() != null){
				Set<BaixaLancamento> c2 = new HashSet<BaixaLancamento>();
				for (Iterator iter = this.getItensBaixaLancamentoExcluidos().iterator(); iter.hasNext();) {
					BaixaLancamento element = (BaixaLancamento) iter.next();
					c2.add(element);
				}
				lancamento.setItensPagamentoExcluidos(c2);
			}
			
			if(lancamento.getValor().setScale(2).compareTo(this.getValorTotalPago().setScale(2)) > 0){
				lancamento.setSituacao(Lancamento.PAGTO_PARCIAL);
			}else if(lancamento.getValor().setScale(2).compareTo(this.getValorTotalPago().setScale(2)) == 0){
				lancamento.setSituacao(Lancamento.FINALIZADO);
			} 
			logger.info("baixarLancamento - tipo do Lancamento para baixa --> "+lancamento.getTipoLancamento());
			logger.info("baixarLancamento - nova situacao do lancamento --> "+lancamento.getSituacao() + " - " + retornaDescricaoSituacao(lancamento.getSituacao()));
			lancamento.setDataPagamento(getDataSistema());
			logger.info("baixarLancamento - data do Pagamento --> "+Util.retornaDataFormatoDDMMYYYY(getDataSistema()));

			getFachada().baixarLancamento(lancamento);
			logger.info("baixarLancamento - lancamento dado baixa com sucesso!");
			
			Iterator<BaixaLancamento> it0 = lancamento.getItensPagamento().iterator();
			while(it0.hasNext()){
				BaixaLancamento itemBaixaLanc = it0.next();
				itemBaixaLanc.setItemLancadoCtaCorrente("S");
			}
			logger.info("baixarLancamento - atualizar status dos itens lancados em conta corrente.");
			getFachada().alterarLancamento(lancamento);
			logger.info("baixarLancamento - atualizou status dos itens lancados em conta corrente.!");
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			resetBB();
		} catch (AppException e) {
			logger.info("baixarLancamento - AppException --> "+e.getMessage());
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			getContextoApp().addMessage(null, msg);
		} catch (Exception e) {
			logger.info("baixarLancamento - Exception --> "+e.getMessage());
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		logger.info("baixarLancamento - FIM");
		return "mesma";
	}

	public String cancelarBaixaLancamento(){
		logger.info("cancelarBaixaLancamento - INICIO");
		try {		
			Lancamento lancamento = this.getLancamento();

			Set<BaixaLancamento> c1 = new HashSet<BaixaLancamento>();
			
			for (Iterator iter = this.getItensBaixaLancamento().iterator(); iter.hasNext();) {
				BaixaLancamento element = (BaixaLancamento) iter.next();
				c1.add(element);
			}
			
			lancamento.setItensPagamento(c1);
			if(this.getItensBaixaLancamentoExcluidos() != null){
				Set<BaixaLancamento> c2 = new HashSet<BaixaLancamento>();
				for (Iterator iter = this.getItensBaixaLancamentoExcluidos().iterator(); iter.hasNext();) {
					BaixaLancamento element = (BaixaLancamento) iter.next();
					c2.add(element);
				}
				lancamento.setItensPagamentoExcluidos(c2);
			}
			lancamento.setSituacao(Lancamento.CANCELADO); 

			logger.info("cancelarBaixaLancamento - tipo do Lancamento para baixa --> "+lancamento.getTipoLancamento());
			logger.info("cancelarBaixaLancamento - nova situacao do lancamento --> "+lancamento.getSituacao() + " - " + retornaDescricaoSituacao(lancamento.getSituacao()));

			lancamento.setDataPagamento(getDataSistema());
			logger.info("baixarLancamento - data do Pagamento --> "+Util.retornaDataFormatoDDMMYYYY(getDataSistema()));
			
			getFachada().baixarLancamento(lancamento);
			logger.info("cancelarBaixaLancamento - lancamento cancelado com sucesso!");
			
			Iterator<BaixaLancamento> it0 = lancamento.getItensPagamento().iterator();
			while(it0.hasNext()){
				BaixaLancamento itemBaixaLanc = it0.next();
				itemBaixaLanc.setSituacao("C");
			}
			logger.info("cancelarBaixaLancamento - atualizar status dos itens lancados em conta corrente.");
			getFachada().alterarLancamento(lancamento);
			logger.info("cancelarBaixaLancamento - atualizou status dos itens lancados em conta corrente.!");
			
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			resetBB();
		} catch (AppException e) {
			logger.info("cancelarBaixaLancamento - AppException --> "+e.getMessage());
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			getContextoApp().addMessage(null, msg);
		} catch (Exception e) {
			logger.info("cancelarBaixaLancamento - Exception --> "+e.getMessage());
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		logger.info("cancelarBaixaLancamento - FIM");
		return "mesma";
	}

	public String getIdFormaRecebimento() {
		return idFormaRecebimento;
	}

	public void setIdFormaRecebimento(String idFormaRecebimento) {
		this.idFormaRecebimento = idFormaRecebimento;
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

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getIdBanco() {
		return idBanco;
	}

	public void setIdBanco(String idBanco) {
		this.idBanco = idBanco;
	}

	public String getIdTipoPessoa() {
		return idTipoPessoa;
	}

	public void setIdTipoPessoa(String idTipoPessoa) {
		this.idTipoPessoa = idTipoPessoa;
	}

	public SelectItem[] getListaTipoPessoa() {
		SelectItem[] lista = new SelectItem[2];
		lista[0] = new SelectItem(Cliente.PESSOA_FISICA, "Física");
		lista[1] = new SelectItem(Cliente.PESSOA_JURIDICA, "Jurídica");
		if(this.getIdTipoPessoa() == null || this.getIdTipoPessoa().equals("")){
			this.setIdTipoPessoa(Cliente.PESSOA_FISICA);
		}
		return lista;
	}

	public void setListaTipoPessoa(SelectItem[] listaTipoPessoa) {
		this.listaTipoPessoa = listaTipoPessoa;
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

	public BigDecimal getValorTotalPago() {
		return valorTotalPago;
	}

	public void setValorTotalPago(BigDecimal valorTotalPago) {
		this.valorTotalPago = valorTotalPago;
	}

//Contas Corrente
	private List<ContaCorrente> carregarContasCorrente() {
		
		List<ContaCorrente> contas = null;
		try {
			IPropertyFilter filtro = new PropertyFilter();
			filtro.setTheClass(ContaCorrente.class);
			filtro.addProperty("situacao", "S");
			contas = (ArrayList<ContaCorrente>)getFachada().consultarContaCorrente(filtro);
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return contas;
	}
	
	public SelectItem[] getContasCorrentes(){
		SelectItem[] arrayContasCorrente = null;
		try {
			List<ContaCorrente> contas = carregarContasCorrente();
			arrayContasCorrente = new SelectItem[contas.size()+1];
			int i = 0;
			arrayContasCorrente[i++] = new SelectItem("0", "");
			for(ContaCorrente contaTmp : contas){
				SelectItem item = new SelectItem(contaTmp.getId().toString(), contaTmp.getNome());
				arrayContasCorrente[i++] = item;
			}
			if(this.getIdContaCorrente() == null){
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "   ");
			getContextoApp().addMessage(null, msg);
		}
		return arrayContasCorrente;
	}

//	public SelectItem[] getContasCorrentes(){
//		SelectItem[] obj = new SelectItem[1];
//		obj[0] = new SelectItem("1", "CONTA CORRENTE PADRAO");
//		return obj;
//	}
	
	public void setContasCorrentes(SelectItem[] contasCorrentes) {
		this.contasCorrentes = contasCorrentes;
	}

	public String getIdContaCorrente() {
		return idContaCorrente;
	}

	public void setIdContaCorrente(String idContaCorrente) {
		this.idContaCorrente = idContaCorrente;
	}

	public Date getDataCheque() {
		return dataCheque;
	}

	public void setDataCheque(Date dataCheque) {
		this.dataCheque = dataCheque;
	}

	public Lancamento getLancamento() {
		return lancamento;
	}

	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}

	public List<BaixaLancamento> getItensBaixaLancamentoExcluidos() {
		return itensBaixaLancamentoExcluidos;
	}

	public void setItensBaixaLancamentoExcluidos(
			List<BaixaLancamento> itensBaixaLancamentoExcluidos) {
		this.itensBaixaLancamentoExcluidos = itensBaixaLancamentoExcluidos;
	}

	public String getNumeroDocumentoItemBaixa() {
		return numeroDocumentoItemBaixa;
	}

	public void setNumeroDocumentoItemBaixa(String numeroDocumentoItemBaixa) {
		this.numeroDocumentoItemBaixa = numeroDocumentoItemBaixa;
	}

	public String getDetalheFormaRecebimento() {
		return detalheFormaRecebimento;
	}

	public void setDetalheFormaRecebimento(String detalheFormaRecebimento) {
		this.detalheFormaRecebimento = detalheFormaRecebimento;
	}
	
//	 Bancos
	private List<Banco> carregarBancos() {
		
		List<Banco> bancos = null;
		try {
			IPropertyFilter filter = new PropertyFilter();
			filter.setTheClass(Banco.class);
			filter.addProperty("situacao", "A");
			bancos = (ArrayList<Banco>)getFachada().consultarBanco(filter);
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return bancos;
	}
	
	public SelectItem[] getBancos(){
		SelectItem[] arrayBancos = null;
		try {
			List<Banco> bancos = carregarBancos();
			arrayBancos = new SelectItem[bancos.size()+1];
			int i = 0;
			arrayBancos[i++] = new SelectItem("0", "");
			for(Banco bancoTmp : bancos){
				SelectItem item = new SelectItem(bancoTmp.getId().toString(), bancoTmp.getDescricao());
				arrayBancos[i++] = item;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "   ");
			getContextoApp().addMessage(null, msg);
		}
		return arrayBancos;
	}
	
	
//	public SelectItem[] getBancos(){
//		SelectItem[] obj = new SelectItem[1];
//		obj[0] = new SelectItem("001", "BANCO DO BRASIL");
//		return obj;
//	}

	public void setBancos(SelectItem[] bancos) {
		this.bancos = bancos;
	}
	
	public void setInit(HtmlForm init) {
		FacesContext context = FacesContext.getCurrentInstance();
		Map params = context.getExternalContext().getRequestParameterMap();            
		String param = (String)  params.get(ACAO);

		System.out.println((String)  params.get("acaoLocal"));
		if (param != null){
			resetBB();
			if(VALOR_ACAO.equals(param)){
				resetBB();
				setLancamentos(null);
			}
		}
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
}