package com.infinity.datamarket.enterprise.gui.lancamento;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.infinity.datamarket.comum.Fachada;

import com.infinity.datamarket.comum.financeiro.GrupoLancamento;
import com.infinity.datamarket.comum.financeiro.Lancamento;
import com.infinity.datamarket.comum.pagamento.FormaRecebimento;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

public class LancamentoBackBean extends BackBean {

	private String descricao;
	private String tipoLancamento;
	private String id;

	private String idLoja;
	private Loja loja;
	private Date dataLancamento = new Date(System.currentTimeMillis());
	private Date dataVencimento;
	private Date dataPagamento;
	private String numeroDocumento;
	private BigDecimal valor;
	private String observacao;
	private String idForma;
	private FormaRecebimento forma;
	private String idGrupo;
	private GrupoLancamento grupo;
	private Long idEntradaProduto;
	
	Collection lancamentos,formas,grupos;
	

	public String voltarConsulta(){
		consultar();
		return "voltar";
	}
	public String voltarMenu(){
		resetBB();
		return "voltar";
	}


	public String inserir(){
		Lancamento lancamento = new Lancamento();

		if (getId()==null) lancamento.setId(getIdInc(Lancamento.class));
		try {
			lancamento.setDescricao(this.descricao);
			lancamento.setTipoLancamento(this.tipoLancamento);
			lancamento.setDataLancamento(this.dataLancamento);
			lancamento.setDataPagamento(this.dataPagamento);
			lancamento.setDataVencimento(this.dataVencimento);
			
			Long idForma = new Long(this.idForma);
			FormaRecebimento forma = Fachada.getInstancia().consultarFormaRecebimentoPorId(idForma);
			lancamento.setForma(forma);
			
			Long idGrupo = new Long(this.idGrupo);			
			GrupoLancamento grupo = Fachada.getInstancia().consultarGrupoLancamentoPorId(idGrupo);
			lancamento.setGrupo(grupo);
			
			lancamento.setIdEntradaProduto(this.idEntradaProduto);
			
			Long idLoja = new Long(this.idLoja);
			Loja loja   = Fachada.getInstancia().consultarLojaPorId(idLoja);
			lancamento.setLoja(loja);
			
			lancamento.setNumeroDocumento(this.numeroDocumento);
			lancamento.setObservacao(this.observacao);
			lancamento.setValor(this.valor);

			getFachada().inserirLancamento(lancamento);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Lancamento já Existente!", "");
			ctx.addMessage(null, msg);
		} catch (Exception e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
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
				this.setIdForma(lancamento.getForma().getId().toString());
				this.setForma(lancamento.getForma());
				this.setIdGrupo(lancamento.getGrupo().getId().toString());
				this.setGrupo(lancamento.getGrupo());
				this.setIdEntradaProduto(lancamento.getIdEntradaProduto());
				this.setIdLoja(lancamento.getLoja().getId().toString());
				this.setLoja(lancamento.getLoja());
				this.setNumeroDocumento(lancamento.getNumeroDocumento());
				this.setObservacao(lancamento.getObservacao());
				this.setValor(lancamento.getValor());
				return "proxima";
			}else if (getDescricao() != null && !"".equals(getDescricao())){
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(Lancamento.class);
				filter.addProperty("descricao", getDescricao());
				Collection col = getFachada().consultarFormaRecebimento(filter);
				if (col == null || col.size() == 0){
					setExisteRegistros(false);
					this.setLancamentos(col);
					FacesContext ctx = FacesContext.getCurrentInstance();
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					ctx.addMessage(null, msg);
				}else if (col != null){
					if(col.size() == 1){
						Lancamento lancamento = getFachada().consultarLancamentoPorId(new Long(getId()));
						this.setId(lancamento.getId().toString());
						this.setDescricao(lancamento.getDescricao());
						this.setTipoLancamento(lancamento.getTipoLancamento());
						this.setDataLancamento(lancamento.getDataLancamento());
						this.setDataPagamento(lancamento.getDataPagamento());
						this.setDataVencimento(lancamento.getDataVencimento());
						this.setIdForma(lancamento.getForma().getId().toString());
						this.setForma(lancamento.getForma());
						this.setIdGrupo(lancamento.getGrupo().getId().toString());
						this.setGrupo(lancamento.getGrupo());
						this.setIdEntradaProduto(lancamento.getIdEntradaProduto());
						this.setIdLoja(lancamento.getLoja().getId().toString());
						this.setLoja(lancamento.getLoja());
						this.setNumeroDocumento(lancamento.getNumeroDocumento());
						this.setObservacao(lancamento.getObservacao());
						this.setValor(lancamento.getValor());
						return "proxima";
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
				}
			}
		}catch(ObjectNotFoundException e){
			setExisteRegistros(false);
			this.setLancamentos(null);
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

	public String alterar(){
		try {
			Lancamento lancamento = new Lancamento();

			lancamento.setId(new Long(this.id));
			lancamento.setDescricao(this.descricao);
			lancamento.setTipoLancamento(this.tipoLancamento);
			lancamento.setDataLancamento(this.dataLancamento);
			lancamento.setDataPagamento(this.dataPagamento);
			lancamento.setDataVencimento(this.dataVencimento);
			lancamento.setForma(this.forma);
			lancamento.setGrupo(this.grupo);
			lancamento.setIdEntradaProduto(this.idEntradaProduto);
			lancamento.setLoja(this.loja);
			lancamento.setNumeroDocumento(this.numeroDocumento);
			lancamento.setObservacao(this.observacao);
			lancamento.setValor(this.valor);

			getFachada().alterarLancamento(lancamento);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return "mesma";
	}

	public String excluir(){
		try {
			Lancamento lancamento = new Lancamento();

			lancamento.setId(new Long(this.id));
			lancamento.setDescricao(this.descricao);
			lancamento.setTipoLancamento(this.tipoLancamento);
			lancamento.setDataLancamento(this.dataLancamento);
			lancamento.setDataPagamento(this.dataPagamento);
			lancamento.setDataVencimento(this.dataVencimento);
			lancamento.setForma(this.forma);
			lancamento.setGrupo(this.grupo);
			lancamento.setIdEntradaProduto(this.idEntradaProduto);
			lancamento.setLoja(this.loja);
			lancamento.setNumeroDocumento(this.numeroDocumento);
			lancamento.setObservacao(this.observacao);
			lancamento.setValor(this.valor);

			getFachada().excluirLancamento(lancamento);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}

		return "mesma";
	}
	// Lojas 
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
	
	public SelectItem[] getLojas(){
		SelectItem[] arrayLojas = null;
		try {
			List<Loja> lojas = carregarLojas();
			arrayLojas = new SelectItem[lojas.size()+1];
			int i = 0;
			arrayLojas[i++] = new SelectItem("0", "");
			for(Loja lojaTmp : lojas){
				SelectItem item = new SelectItem(lojaTmp.getId().toString(), lojaTmp.getNome());
				arrayLojas[i++] = item;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "   ");
			ctx.addMessage(null, msg);
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
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
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
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return arrayFormas;

	}
	
//	Grupos
	private List<GrupoLancamento> carregarGrupos() {
		
		List<GrupoLancamento> grupos = null;
		try {
			grupos = (ArrayList<GrupoLancamento>)getFachada().consultarTodosGrupoLancamentos();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
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
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return arrayGrupos;

	}
	public void resetBB(){
		this.setId(null);
		this.setDescricao(null);
		this.setTipoLancamento(Lancamento.CREDITO);
		this.setDataLancamento(new Date(System.currentTimeMillis()));
		this.setDataPagamento(null);
		this.setDataVencimento(null);
		this.setIdForma(null);
		this.setForma(null);
		this.setIdGrupo(null);
		this.setGrupo(null);
		this.setIdEntradaProduto(null);
		this.setIdLoja(null);
		this.setLoja(null);
		this.setNumeroDocumento(null);
		this.setObservacao(null);
		this.setValor(null);
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
	public Long getIdEntradaProduto() {
		return idEntradaProduto;
	}
	public void setIdEntradaProduto(Long idEntradaProduto) {
		this.idEntradaProduto = idEntradaProduto;
	}
	public Loja getLoja() {
		return loja;
	}
	public void setLoja(Loja loja) {
		this.loja = loja;
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
	public String getIdForma() {
		return idForma;
	}
	public void setIdForma(String idForma) {
		this.idForma = idForma;
	}
	public String getIdGrupo() {
		return idGrupo;
	}
	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}
}
