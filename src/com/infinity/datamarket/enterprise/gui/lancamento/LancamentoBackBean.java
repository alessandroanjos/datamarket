package com.infinity.datamarket.enterprise.gui.lancamento;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.hibernate.collection.PersistentSet;

import com.infinity.datamarket.comum.financeiro.GrupoLancamento;
import com.infinity.datamarket.comum.financeiro.Lancamento;
import com.infinity.datamarket.comum.fornecedor.Fornecedor;
import com.infinity.datamarket.comum.pagamento.FormaRecebimento;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter.IntervalObject;
import com.infinity.datamarket.comum.transacao.ClienteTransacao;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.enterprise.gui.login.LoginBackBean;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

public class LancamentoBackBean extends BackBean {
	
	Logger logger = Logger.getLogger(LancamentoBackBean.class);

	private String descricao;
	private String tipoLancamento;
	private String id;

	private String idLoja;
	private Date dataLancamento = new Date(System.currentTimeMillis());
	private Date dataVencimento;
	private Date dataPagamento;
	private String numeroDocumento;
	private BigDecimal valor;
	private String observacao;
	private String idGrupo;
	private Long idEntradaProduto;
	private String idFornecedor;
	private String idSituacao;
	private SelectItem[] listaSituacao;
	private String descricaoSituacao;
	
	private Date dataInicial;
	private Date dataFinal;
	
	SelectItem[] fornecedores;
	private String idCliente;
	
	Collection lancamentos,formas,grupos;
	

	public String voltarConsulta(){
		resetBB();
		consultar();
		return "voltar";
	}
	public String voltarMenu(){
		resetBB();
		return "voltar";
	}


	public String inserirContaAPagar(){
		logger.info("inserirContaAPagar - INICIO");
		try {
			validarLancamento();
			
			Lancamento lancamento = montaObjetoLancamento("I");

			lancamento.setTipoLancamento(Lancamento.DEBITO);

			getFachada().inserirLancamento(lancamento);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Conta à Pagar já Existente!", "");
			getContextoApp().addMessage(null, msg);
		} catch (AppException e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			getContextoApp().addMessage(null, msg);
		} catch (Exception e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		logger.info("inserirContaAPagar - FIM");
		return "mesma";
	}

	public String inserirContaAReceber(){
		
		try {
			validarLancamento();
			
			if(this.getIdCliente() == null || this.getIdCliente().equals("")){
				throw new AppException("O Campo Cliente é obrigatório!");
			}
			Lancamento lancamento = montaObjetoLancamento("I");
			
			ClienteTransacao cliente = new ClienteTransacao();
			cliente.setCpfCnpj(this.getIdCliente().replace('.', ' ').replace('-', ' ').replace('/', ' '));
			lancamento.setCliente(cliente);
			
			lancamento.setTipoLancamento(Lancamento.CREDITO);

			getFachada().inserirLancamento(lancamento);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Conta à Receber já Existente!", "");
			getContextoApp().addMessage(null, msg);
		} catch (AppException e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			getContextoApp().addMessage(null, msg);
		} catch (Exception e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return "mesma";
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
				
				Collection col = getFachada().consultarFormaRecebimento(filter);
				if (col == null || col.size() == 0){
					setExisteRegistros(false);
					this.setLancamentos(col);
					
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					getContextoApp().addMessage(null, msg);
				}else if (col != null){
					if(col.size() == 1){
						Lancamento lancamento = (Lancamento)col.iterator().next();
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
	
	public Lancamento montaObjetoLancamento(String operacao){
		Lancamento lancamento = new Lancamento();

		if(operacao.equals("I")){
			if (getId()==null) lancamento.setId(getIdInc(Lancamento.class));
			lancamento.setSituacao(Lancamento.ABERTO);
		}else if(operacao.equals("A")){
			lancamento.setId(new Long(this.id));
			lancamento.setSituacao(this.getIdSituacao());
		}		
		lancamento.setDescricao(this.descricao);
		lancamento.setDataLancamento(this.dataLancamento);
		lancamento.setDataPagamento(this.dataPagamento);
		lancamento.setDataVencimento(this.dataVencimento);
		lancamento.setIdEntradaProduto(this.idEntradaProduto);
		lancamento.setNumeroDocumento(this.numeroDocumento);
		lancamento.setObservacao(this.observacao);
		lancamento.setValor(this.valor);
		lancamento.setIdEntradaProduto(this.idEntradaProduto);
		lancamento.setCliente(null);

		Loja loja = new Loja();
		loja.setId(new Long(this.idLoja));
		lancamento.setLoja(loja);

		GrupoLancamento grupo = new GrupoLancamento();
		grupo.setId(new Long(this.idGrupo));
		lancamento.setGrupo(grupo);
		
		if(this.idFornecedor != null && !this.idFornecedor.equals("0")){
			Fornecedor fornecedor = new Fornecedor();
			fornecedor.setId(new Long(this.idFornecedor));
			lancamento.setFornecedor(fornecedor);
		}

		return lancamento;
	}

	public String alterarContaAPagar(){
		try {
			validarSituacaoLancamento("ALTERAR");
			validarLancamento();
			
			Lancamento lancamento = montaObjetoLancamento("A");

			lancamento.setTipoLancamento(Lancamento.DEBITO);

			getFachada().alterarLancamento(lancamento);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			resetBB();
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return "mesma";
	}

	public String alterarContaAReceber(){
		try {
			validarSituacaoLancamento("ALTERAR");
			validarLancamento();
			
			Lancamento lancamento = montaObjetoLancamento("A");

			lancamento.setTipoLancamento(Lancamento.CREDITO);

			getFachada().alterarLancamento(lancamento);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			resetBB();
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return "mesma";
	}

	public String cancelar(){
		try {
			validarSituacaoLancamento("CANCELAR");
			
			Lancamento lancamento = getFachada().consultarLancamentoPorId(new Long(this.id));
			
			lancamento.setSituacao(Lancamento.CANCELADO);
			
			getFachada().alterarLancamento(lancamento);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			resetBB();
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return "mesma";
	}
	
	// Lojas 
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
			formas = (ArrayList<FormaRecebimento>)getFachada().consultarTodosFormaRecebimento();
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
		this.setValor(null);
		this.setDataInicial(null);
		this.setDataFinal(null);
		this.setIdSituacao("T");
		this.setDescricaoSituacao(retornaDescricaoSituacao(this.getIdSituacao()));
	}


	/**
	 * @param init the init to set
	 */
	public void setInit(HtmlForm init) {
		FacesContext context = FacesContext.getCurrentInstance();
		Map params = context.getExternalContext().getRequestParameterMap();
		String param = (String)  params.get(ACAO);
		if (param != null){
			resetBB();
			if(VALOR_ACAO.equals(param)){
				setLancamentos(null);
			}
		}
	}
	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}
	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the Lancamentos
	 */
	public Collection getLancamentos() {
		return lancamentos;
	}
	/**
	 * @param s the s to set
	 */
	public void setLancamentos(Collection s) {
		this.lancamentos = s;
	}
	
	public String getTipoLancamento() {
		return tipoLancamento;
	}
	public void setTipoLancamento(String tipoLancamento) {
		this.tipoLancamento = tipoLancamento;
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
		
	public Long getIdEntradaProduto() {
		return idEntradaProduto;
	}
	public void setIdEntradaProduto(Long idEntradaProduto) {
		this.idEntradaProduto = idEntradaProduto;
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
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public String getIdLoja() {
		return idLoja;
	}
	public void setIdLoja(String idLoja) {
		this.idLoja = idLoja;
	}
	public String getIdGrupo() {
		return idGrupo;
	}
	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}
	
	public void validarLancamento() throws AppException {
		if(this.idLoja == null || this.idLoja.equals("0")){
			throw new AppException("O Campo Loja é obrigatório!");
		}
		if(this.dataLancamento == null || this.dataLancamento.equals("")){
			throw new AppException("O Campo Data é obrigatório!");
		}
		if(this.idGrupo == null || this.idGrupo.equals("0")){
			throw new AppException("O Campo Grupo de Lançamento é obrigatório!");
		}
		if(this.descricao == null || this.descricao.equals("")){
			throw new AppException("O Campo Descrição é obrigatório!");
		}
		if(this.numeroDocumento == null || this.numeroDocumento.equals("")){
			throw new AppException("O Campo Número do Documento é obrigatório!");
		}
		if(this.dataVencimento == null || this.dataVencimento.equals("")){
			throw new AppException("O Campo Vencimento é obrigatório!");
		}
		if(this.valor == null || this.valor.equals(BigDecimal.ZERO.setScale(2))){
			throw new AppException("O Campo Valor é obrigatório!");
		}
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
	
	public void validarSituacaoLancamento(String acao) throws AppException {
		if(acao.equals("CANCELAR")){
			if(this.getIdSituacao() != null && !this.getIdSituacao().equals("A")){
				throw new AppException("O Lançamento não pode ser Cancelado. A Situação atual é: " + retornaDescricaoSituacao(this.getIdSituacao()));
			}
		}else if(acao.equals("ALTERAR")){
			if(this.getIdSituacao() != null && (this.getIdSituacao().equals("F") || this.getIdSituacao().equals("C"))){
				throw new AppException("O Lançamento não pode ser Alterado. A Situação atual é: " + retornaDescricaoSituacao(this.getIdSituacao()));
			}
		}		
	}
	
	public String getIdFornecedor() {
		return idFornecedor;
	}
	public void setIdFornecedor(String idFornecedor) {
		this.idFornecedor = idFornecedor;
	}
	public void setFornecedores(SelectItem[] fornecedores) {
		this.fornecedores = fornecedores;
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
	public String getIdSituacao() {
		return idSituacao;
	}
	public void setIdSituacao(String idSituacao) {
		this.idSituacao = idSituacao;
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
	public String getDescricaoSituacao() {
		return descricaoSituacao;
	}
	public void setDescricaoSituacao(String descricaoSituacao) {
		this.descricaoSituacao = descricaoSituacao;
	}
	
//	 Clientes
	private List<ClienteTransacao> carregarClientes() {
		
		List<ClienteTransacao> clientes = null;
		try {
			IPropertyFilter filter = new PropertyFilter();
			filter.setTheClass(ClienteTransacao.class);
			clientes = (ArrayList<ClienteTransacao>)getFachada().consultarClienteTransacao(filter);
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return clientes;
	}

	public SelectItem[] getClientes() {
		SelectItem[] arrayClientes = null;
		try {
			List<ClienteTransacao> clientes = carregarClientes();
			arrayClientes = new SelectItem[clientes.size()+1];
			int i = 0;
			arrayClientes[i++] = new SelectItem("0", "");
			for(ClienteTransacao clienteTmp : clientes){
				SelectItem item = new SelectItem(clienteTmp.getCpfCnpj().toString(), clienteTmp.getNomeCliente());
				arrayClientes[i++] = item;
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return arrayClientes;

	}
	public String getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

}
