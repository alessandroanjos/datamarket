package com.infinity.datamarket.enterprise.gui.formaRecebimento;

/**
 * 
 */
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

import com.infinity.datamarket.comum.pagamento.FormaRecebimento;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Constantes;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

/**
 * @author alessandro
 * 
 */
public class FormaRecebimentoBackBean extends BackBean {
	private String id;

	private String descricao;

	private String recebimentoImpressora;

	private String abrirGaveta;
	
	private String validaPagamentoConta;
	
	private String validaRecebimentoConta;

	private BigDecimal valorLimiteSangria;

	private Date dataInicioValidade;

	private Date dataFimValidade;

	private BigDecimal valorMaxTroco;

	private Collection planos;

	private Collection formasRecebimentos;
	
	private String idFormaTroco;
	
//	public FormaRecebimentoBackBean(){
//		resetBB();
//	}
	
	SelectItem[] formas;

	public String getIdFormaTroco() {
		return idFormaTroco;
	}

	public void setIdFormaTroco(String idFormaTroco) {
		this.idFormaTroco = idFormaTroco;
	}

	public String voltarConsulta() {
		consultar();
		return "voltar";
	}

	public String voltarMenu() {
		resetBB();
		return "voltar";
	}

	public String getAbrirGaveta() {
		return abrirGaveta;
	}

	public void setAbrirGaveta(String abrirGaveta) {
		this.abrirGaveta = abrirGaveta;
	}

	public Date getDataFimValidade() {
		return dataFimValidade;
	}

	public void setDataFimValidade(Date dataFimValidade) {
		this.dataFimValidade = dataFimValidade;
	}

	public Date getDataInicioValidade() {
		return dataInicioValidade;
	}

	public void setDataInicioValidade(Date dataInicioValidade) {
		this.dataInicioValidade = dataInicioValidade;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Collection getPlanos() {
		return planos;
	}

	public void setPlanos(Collection planos) {
		this.planos = planos;
	}

	public String getRecebimentoImpressora() {
		return recebimentoImpressora;
	}

	public void setRecebimentoImpressora(String recebimentoImpressora) {
		this.recebimentoImpressora = recebimentoImpressora;
	}

	public BigDecimal getValorLimiteSangria() {
		return valorLimiteSangria;
	}

	public void setValorLimiteSangria(BigDecimal valorLimiteSangria) {
		this.valorLimiteSangria = valorLimiteSangria;
	}

	public BigDecimal getValorMaxTroco() {
		return valorMaxTroco;
	}

	public void setValorMaxTroco(BigDecimal valorMaxTroco) {
		this.valorMaxTroco = valorMaxTroco;
	}
	
	public void validarFormaRecebimento() throws AppException {
		if(this.getId() == null || this.getId().equals("")){
			throw new AppException("É necessário informar o Código.");
		}

		if(this.getDescricao() == null || this.getDescricao().equals("")){
			throw new AppException("É necessário informar uma Descrição.");
		}

		if(this.getRecebimentoImpressora() == null || this.getRecebimentoImpressora().equals("")){
			throw new AppException("É necessário informar a Descrição no Recebimento em Impressora.");
		}
		
		if(this.getValorMaxTroco() == null || (this.getValorMaxTroco() != null && this.getValorMaxTroco().setScale(2).equals(new BigDecimal("0.00")))){
			throw new AppException("É necessário informar um Valor máximo para Troco.");
		}

		if(this.getValorLimiteSangria() == null || (this.getValorLimiteSangria() != null && this.getValorLimiteSangria().setScale(2).equals(new BigDecimal("0.00")))){
			throw new AppException("É necessário informar um Valor Limite para Sangrias.");
		}

		if(this.getDataInicioValidade() == null || this.getDataInicioValidade().equals("")){
			throw new AppException("É necessário informar a Data de Início da Validade.");
		}

		if(this.getDataFimValidade() == null || this.getDataFimValidade().equals("")){
			throw new AppException("É necessário informar a Data Final da Validade.");
		}
		
		if(this.getDataInicioValidade().compareTo(this.getDataFimValidade()) >= 0){
			throw new AppException("A Data Final da Validade deve ser maior que a Data de Início da Validade.");
		}

//		if(this.getPlanos() == null || this.getPlanos().size() == 0){
//			throw new AppException("É necessário informar selecionar pelo menos um Plano.");
//		}

	}

	public String inserir() {
		try {
			
			validarFormaRecebimento();
			
			FormaRecebimento formaRecebimento = new FormaRecebimento();
			
			formaRecebimento.setId(new Long(this.id));
			formaRecebimento.setDescricao(this.descricao);
			formaRecebimento.setRecebimentoImpressora(this.recebimentoImpressora);
			formaRecebimento.setAbrirGaveta(this.abrirGaveta);
			formaRecebimento.setValorLimiteSangria(this.valorLimiteSangria);
			formaRecebimento.setDataInicioValidade(this.dataInicioValidade);
			formaRecebimento.setDataFimValidade(this.dataFimValidade);
			formaRecebimento.setValorMaxTroco(this.valorMaxTroco);
			formaRecebimento.setValidaPagamentoConta(validaPagamentoConta);
			formaRecebimento.setValidaRecebimentoConta(validaRecebimentoConta);
			if(this.getIdFormaTroco() != null && !this.getIdFormaTroco().equals("0")){
				FormaRecebimento formaTroco = getFachada().consultarFormaRecebimentoPorId(new Long(this.getIdFormaTroco()));
				formaRecebimento.setFormaTroco(formaTroco);	
			}else{
				formaRecebimento.setFormaTroco(null);
			}			
			formaRecebimento.setPlanos(this.planos);
			getFachada().inserirFormaRecebimento(formaRecebimento);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Forma de recebimento já Existente!", "");
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

	public String consultar() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			Map params = context.getExternalContext().getRequestParameterMap();
			String param = (String) params.get("id");
			if (param != null && !"".equals(param)) {
				setId(param);
			}
			if (getId() != null && !"".equals(getId())) {
				FormaRecebimento formaRecebimento = getFachada()
						.consultarFormaRecebimentoPorId(new Long(getId()));
				this.setId(formaRecebimento.getId().toString());
				this.setDescricao(formaRecebimento.getDescricao());
				this.setRecebimentoImpressora(formaRecebimento
						.getRecebimentoImpressora());
				this.setAbrirGaveta(formaRecebimento.getAbrirGaveta());
				this.setValorLimiteSangria(formaRecebimento
						.getValorLimiteSangria());
				this.setDataInicioValidade(formaRecebimento
						.getDataInicioValidade());
				this.setDataFimValidade(formaRecebimento.getDataFimValidade());
				this.setValorMaxTroco(formaRecebimento.getValorMaxTroco());
				this.setValidaPagamentoConta(formaRecebimento.getValidaPagamentoConta());
				this.setValidaRecebimentoConta(formaRecebimento.getValidaRecebimentoConta());
				if(formaRecebimento.getFormaTroco() != null){
					this.setIdFormaTroco(formaRecebimento.getFormaTroco().getId().toString());	
				}else{
					this.setIdFormaTroco("0");
				}
				
				this.setPlanos(formaRecebimento.getPlanos());

				return "proxima";
			} else if (getDescricao() != null && !"".equals(getDescricao())) {
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(FormaRecebimento.class);
				filter.addProperty("descricao", getDescricao());
				Collection col = getFachada().consultarFormaRecebimento(filter);
				if (col == null || col.size() == 0) {
					setExisteRegistros(false);
					this.setFormasRecebimentos(col);
					
					FacesMessage msg = new FacesMessage(
							FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					getContextoApp().addMessage(null, msg);
					this.setExisteRegistros(false);
				} else if (col != null) {
					if (col.size() == 1) {
						FormaRecebimento formaRecebimento = (FormaRecebimento) col
								.iterator().next();
						this.setId(formaRecebimento.getId().toString());
						this.setDescricao(formaRecebimento.getDescricao());
						this.setRecebimentoImpressora(formaRecebimento
								.getRecebimentoImpressora());
						this.setAbrirGaveta(formaRecebimento.getAbrirGaveta());
						this.setValorLimiteSangria(formaRecebimento
								.getValorLimiteSangria());
						this.setDataInicioValidade(formaRecebimento
								.getDataInicioValidade());
						this.setDataFimValidade(formaRecebimento
								.getDataFimValidade());
						this.setValorMaxTroco(formaRecebimento
								.getValorMaxTroco());
						if(formaRecebimento.getFormaTroco() != null){
							this.setIdFormaTroco(formaRecebimento.getFormaTroco().getId().toString());	
						}else{
							this.setIdFormaTroco("0");
						}
						this.setValidaPagamentoConta(formaRecebimento.getValidaPagamentoConta());
						this.setValidaRecebimentoConta(formaRecebimento.getValidaRecebimentoConta());

						this.setPlanos(formaRecebimento.getPlanos());
						return "proxima";
					} else {
						setExisteRegistros(true);
						this.setFormasRecebimentos(col);
					}
				}
			} else {
				Collection c = getFachada().consultarTodosFormaRecebimento();
				if (c != null && c.size() > 0){
					this.setExisteRegistros(true);
				}else{
					this.setExisteRegistros(false);
				}				
				setFormasRecebimentos(c);
			}
		} catch (ObjectNotFoundException e) {
			this.setExisteRegistros(false);
			this.setFormasRecebimentos(null);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Nenhum Registro Encontrado", "");
			getContextoApp().addMessage(null, msg);
		} catch (Exception e) {
			this.setExisteRegistros(false);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
//		this.setId(null);
//		this.setDescricao(null);
		this.setRecebimentoImpressora(null);
		this.setAbrirGaveta(null);
		this.setValidaPagamentoConta(Constantes.SIM);
		this.setValidaRecebimentoConta(Constantes.SIM);
		this.setValorLimiteSangria(null);
		this.setDataInicioValidade(null);
		this.setDataFimValidade(null);
		this.setValorMaxTroco(null);
		this.setIdFormaTroco("0");
		this.setPlanos(null);
		return "mesma";
	}

	public String alterar() {
		try {
			
			validarFormaRecebimento();
			
			FormaRecebimento formaRecebimento = new FormaRecebimento();

			formaRecebimento.setId(new Long(this.id));
			formaRecebimento.setDescricao(this.descricao);
			formaRecebimento.setRecebimentoImpressora(this.recebimentoImpressora);
			formaRecebimento.setAbrirGaveta(this.abrirGaveta);
			formaRecebimento.setValorLimiteSangria(this.valorLimiteSangria);
			formaRecebimento.setDataInicioValidade(this.dataInicioValidade);
			formaRecebimento.setDataFimValidade(this.dataFimValidade);
			formaRecebimento.setValorMaxTroco(this.valorMaxTroco);
			formaRecebimento.setValidaPagamentoConta(validaPagamentoConta);
			formaRecebimento.setValidaRecebimentoConta(validaRecebimentoConta);
			if(this.getIdFormaTroco() != null && !this.getIdFormaTroco().equals("0")){
				FormaRecebimento formaTroco = getFachada().consultarFormaRecebimentoPorId(new Long(this.getIdFormaTroco()));
				formaRecebimento.setFormaTroco(formaTroco);	
			}else{
				formaRecebimento.setFormaTroco(null);
			}	
			formaRecebimento.setPlanos(this.planos);

			getFachada().alterarFormaRecebimento(formaRecebimento);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			resetBB();
		} catch (AppException e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			getContextoApp().addMessage(null, msg);
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return "mesma";
	}

	public String excluir() {
		try {
			FormaRecebimento formaRecebimento = new FormaRecebimento();

			formaRecebimento.setId(new Long(this.id));
			formaRecebimento.setDescricao(this.descricao);
			formaRecebimento
					.setRecebimentoImpressora(this.recebimentoImpressora);
			formaRecebimento.setAbrirGaveta(this.abrirGaveta);
			formaRecebimento.setValorLimiteSangria(this.valorLimiteSangria);
			formaRecebimento.setDataInicioValidade(this.dataInicioValidade);
			formaRecebimento.setDataFimValidade(this.dataFimValidade);
			formaRecebimento.setValorMaxTroco(this.valorMaxTroco);
			formaRecebimento.setValidaPagamentoConta(validaPagamentoConta);
			formaRecebimento.setValidaRecebimentoConta(validaRecebimentoConta);
			if(this.getIdFormaTroco() != null && !this.getIdFormaTroco().equals("0")){
				FormaRecebimento formaTroco = getFachada().consultarFormaRecebimentoPorId(new Long(this.getIdFormaTroco()));
				formaRecebimento.setFormaTroco(formaTroco);	
			}else{
				formaRecebimento.setFormaTroco(null);
			}	
			formaRecebimento.setPlanos(this.planos);
			getFachada().excluirFormaRecebimento(formaRecebimento);
			
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

	public void resetBB() {
		this.setId(null);
		this.setDescricao(null);
		this.setRecebimentoImpressora(null);
		this.setAbrirGaveta(Constantes.SIM);
		this.setValorLimiteSangria(null);
		this.setDataInicioValidade(null);
		this.setDataFimValidade(null);
		this.setValorMaxTroco(null);
		this.setIdFormaTroco("0");
		this.setPlanos(null);
		this.setValidaPagamentoConta(Constantes.SIM);
		this.setValidaRecebimentoConta(Constantes.SIM);
	}

	public Collection getFormasRecebimentos() {
		return formasRecebimentos;
	}

	public void setFormasRecebimentos(Collection formasRecebimentos) {
		this.formasRecebimentos = formasRecebimentos;
	}
	
	public void setFormas(SelectItem[] formas) {
		this.formas = formas;
	}

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
	
	public SelectItem[] getFormas(){
		SelectItem[] arrayFormas = null;
		try {
			List<FormaRecebimento> formas = carregarFormas();
			arrayFormas = new SelectItem[formas.size()+1];
			int i = 0;
			SelectItem itemBranco = new SelectItem("0", "");
			arrayFormas[i++] = itemBranco;
			for(FormaRecebimento formaTmp : formas){
				SelectItem item = new SelectItem(formaTmp.getId().toString(), formaTmp.getDescricao());
				arrayFormas[i++] = item;
			}
			
			if(this.getIdFormaTroco() == null || this.getIdFormaTroco().equals("") || this.getIdFormaTroco().equals("0")){
				this.setIdFormaTroco((String) arrayFormas[0].getValue());				
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return arrayFormas;
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
				setFormasRecebimentos(null);
			}
		}
	}

	public String getValidaPagamentoConta() {
		return validaPagamentoConta;
	}

	public void setValidaPagamentoConta(String validaPagamentoConta) {
		this.validaPagamentoConta = validaPagamentoConta;
	}

	public String getValidaRecebimentoConta() {
		return validaRecebimentoConta;
	}

	public void setValidaRecebimentoConta(String validaRecebimentoConta) {
		this.validaRecebimentoConta = validaRecebimentoConta;
	}
}
