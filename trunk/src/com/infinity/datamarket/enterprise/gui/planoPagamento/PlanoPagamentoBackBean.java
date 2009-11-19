package com.infinity.datamarket.enterprise.gui.planoPagamento;

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

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import com.infinity.datamarket.comum.pagamento.FormaRecebimento;
import com.infinity.datamarket.comum.pagamento.PlanoPagamento;
import com.infinity.datamarket.comum.pagamento.PlanoPagamentoAPrazo;
import com.infinity.datamarket.comum.pagamento.PlanoPagamentoAVista;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Constantes;
import com.infinity.datamarket.enterprise.gui.planoPagamentoAPrazo.PlanoPagamentoAPrazoBackBean;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

/**
 * @author alessandro
 *
 */
public class PlanoPagamentoBackBean extends BackBean {
	String id;
	String descricao;
	String status;
	BigDecimal valorMinimo;
	BigDecimal valorMaximo;
	BigDecimal percDesconto;
	BigDecimal percAcrescimo;
	Date dataInicioValidade;
	Date dataFimValidade;
	String idForma;
	SelectItem[] formas;
	SelectItem[] situacaoItens;
	Collection planos;
	
//	public PlanoPagamentoBackBean(){
//		resetBB();
//	}
	
	public String voltarConsulta(){
		consultar();
		return "voltar";
	}
	public String voltarMenu(){
		resetBB();
		return "voltar";
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

	public void validarPlanoPagamento() throws AppException{
		
		if(this.getId() == null || this.getId().equals("")){
			throw new AppException("É necessário informar o Código.");
		}

		if(this.getDescricao() == null || this.getDescricao().equals("")){
			throw new AppException("É necessário informar uma Descrição.");
		}
		
		if(this.getDataInicioValidade() == null || this.getDataInicioValidade().equals("")){
			throw new AppException("É necessário informar uma Data de Início de Validade.");
		}
		
		if(this.getDataFimValidade() == null || this.getDataFimValidade().equals("")){
			throw new AppException("É necessário informar uma Data Final de Validade.");
		}
		
		if(this.getDataInicioValidade().compareTo(this.getDataFimValidade()) >= 0){
			throw new AppException("A Data Final de Validade deve ser maior que a Data de Início de Validade.");
		}
		
		if(this.getIdForma() == null || this.getIdForma().equals("")){
			throw new AppException("É necessário informar uma Forma de Recebimento Associada.");
		}
	}

	public String inserir(){
		try {			
			
			validarPlanoPagamento();
			
			PlanoPagamento planoPagamento = new PlanoPagamentoAVista();
			preenchePlanoPagamento(planoPagamento, INSERIR);
			getFachada().inserirPlanoPagamento(planoPagamento);
			
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
	
		
	public String consultar(){
		try{
			FacesContext context = FacesContext.getCurrentInstance();
			Map params = context.getExternalContext().getRequestParameterMap();            
			String param = (String)  params.get("id");
			if (param != null && !"".equals(param)){
				setId(param);
			}
			if (getId() != null && !"".equals(getId())){
				PlanoPagamento planoPagamento = getFachada().consultarPlanoPagamentoPorId(new Long(getId()));
				this.setId(planoPagamento.getId().toString());
				this.setDescricao(planoPagamento.getDescricao());
				this.setStatus(planoPagamento.getStatus());
				this.setValorMaximo(planoPagamento.getValorMaximo());
				this.setValorMinimo(planoPagamento.getValorMinimo());
				this.setPercAcrescimo(planoPagamento.getPercAcrescimo());
				this.setPercDesconto(planoPagamento.getPercDesconto());
				this.setDataInicioValidade(planoPagamento.getDataInicioValidade());
				this.setDataFimValidade(planoPagamento.getDataFimValidade());
		        this.setIdForma(planoPagamento.getForma().getId().toString());
		        
		        if (planoPagamento instanceof PlanoPagamentoAPrazo && this instanceof PlanoPagamentoAPrazoBackBean) {
		        	PlanoPagamentoAPrazoBackBean ppapbb = (PlanoPagamentoAPrazoBackBean) this;
					if (((PlanoPagamentoAPrazo)planoPagamento).getParcelasFixasVariadas() != null) {
						ppapbb.setParcelasFixasVariadas(((PlanoPagamentoAPrazo)planoPagamento).getParcelasFixasVariadas());
					} else {
						ppapbb.setParcelasFixasVariadas(Constantes.PARCELAS_FIXAS);
					}

					if (((PlanoPagamentoAPrazo)planoPagamento).getDatasParcelasVariadasInformadaSistemaOuUsuario() != null) {
						ppapbb.setParcelasVariadasDatasAutomaticas(((PlanoPagamentoAPrazo)planoPagamento).getDatasParcelasVariadasInformadaSistemaOuUsuario());
					} else {
						ppapbb.setParcelasVariadasDatasAutomaticas(Constantes.DATAS_INFORMADA_PELO_SISTEMA);
					}
				} else if (this instanceof PlanoPagamentoAPrazoBackBean) {
					PlanoPagamentoAPrazoBackBean ppapbb = (PlanoPagamentoAPrazoBackBean) this;
					ppapbb.setParcelasVariadasDatasAutomaticas(Constantes.DATAS_INFORMADA_PELO_SISTEMA);
					ppapbb.setParcelasFixasVariadas(Constantes.PARCELAS_FIXAS);
				}
				
				return "proxima";
			}else if (getDescricao() != null && !"".equals(getDescricao())){
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(PlanoPagamentoAVista.class);
				filter.addProperty("descricao", getDescricao());
				Collection col = getFachada().consultarPlanoPagamento(filter);
				if (col == null || col.size() == 0){
					setExisteRegistros(false);
					this.setPlanos(col);
					
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					getContextoApp().addMessage(null, msg);					
				}else if (col != null){
					if(col.size() == 1){
						PlanoPagamento planoPagamento = getFachada().consultarPlanoPagamentoPorId(new Long(getId()));
						this.setId(planoPagamento.getId().toString());
						this.setDescricao(planoPagamento.getDescricao());
						this.setStatus(planoPagamento.getStatus());
						this.setValorMaximo(planoPagamento.getValorMaximo());
						this.setValorMinimo(planoPagamento.getValorMinimo());
						this.setPercDesconto(planoPagamento.getPercDesconto());
						this.setPercAcrescimo(planoPagamento.getPercAcrescimo());
						this.setDataInicioValidade(planoPagamento.getDataInicioValidade());
						this.setDataFimValidade(planoPagamento.getDataFimValidade());
				        this.setIdForma(planoPagamento.getForma().getId().toString());
				        
				        if (planoPagamento instanceof PlanoPagamentoAPrazo && this instanceof PlanoPagamentoAPrazoBackBean) {
				        	PlanoPagamentoAPrazoBackBean ppapbb = (PlanoPagamentoAPrazoBackBean) this;
							if (((PlanoPagamentoAPrazo)planoPagamento).getParcelasFixasVariadas() != null) {
								ppapbb.setParcelasFixasVariadas(((PlanoPagamentoAPrazo)planoPagamento).getParcelasFixasVariadas());
							} else {
								ppapbb.setParcelasFixasVariadas(Constantes.PARCELAS_FIXAS);
							}

							if (((PlanoPagamentoAPrazo)planoPagamento).getDatasParcelasVariadasInformadaSistemaOuUsuario() != null) {
								ppapbb.setParcelasVariadasDatasAutomaticas(((PlanoPagamentoAPrazo)planoPagamento).getDatasParcelasVariadasInformadaSistemaOuUsuario());
							} else {
								ppapbb.setParcelasVariadasDatasAutomaticas(Constantes.DATAS_INFORMADA_PELO_SISTEMA);
							}
						} else if (this instanceof PlanoPagamentoAPrazoBackBean) {
							PlanoPagamentoAPrazoBackBean ppapbb = (PlanoPagamentoAPrazoBackBean) this;
							ppapbb.setParcelasVariadasDatasAutomaticas(Constantes.DATAS_INFORMADA_PELO_SISTEMA);
							ppapbb.setParcelasFixasVariadas(Constantes.PARCELAS_FIXAS);
						}

						return "proxima";
					}else{
						setExisteRegistros(true);
						this.setPlanos(col);
					}
				}
			}else{
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(PlanoPagamentoAVista.class);
				Collection c = getFachada().consultarPlanoPagamento(filter);
				if(c != null && c.size() > 0){
					setExisteRegistros(true);
				}else{
					setExisteRegistros(false);
				}
				setPlanos(c);
			}
		}catch(ObjectNotFoundException e){
			setExisteRegistros(false);
			this.setPlanos(null);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Nenhum Registro Encontrado", "");
			getContextoApp().addMessage(null, msg);			
		}catch(Exception e){
			setExisteRegistros(false);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
//		this.setId(null);
//		this.setDescricao(null);
		this.setStatus(null);
		this.setValorMaximo(null);
		this.setValorMinimo(null);
		this.setPercDesconto(null);
		this.setPercAcrescimo(null);
		this.setDataInicioValidade(null);
		this.setDataFimValidade(null);
		this.setIdForma(null);
		return "mesma";
	}
	
	public String alterar(){
		try {
			
			validarPlanoPagamento();
			
			PlanoPagamento planoPagamento = new PlanoPagamentoAVista();
			
			preenchePlanoPagamento(planoPagamento, ALTERAR);
			
			getFachada().alterarPlanoPagamento(planoPagamento);
			
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
	
	public String excluir(){
		try {
			PlanoPagamento planoPagamento = new PlanoPagamentoAVista();
			
			planoPagamento.setId(new Long(this.id));
			planoPagamento.setDescricao(this.descricao);
			planoPagamento.setStatus(this.status);
			planoPagamento.setValorMaximo(this.valorMaximo);
			planoPagamento.setValorMinimo(this.valorMinimo);
			planoPagamento.setPercAcrescimo(this.percAcrescimo);
			planoPagamento.setPercDesconto(this.percDesconto);
			planoPagamento.setDataInicioValidade(this.dataInicioValidade);
			planoPagamento.setDataFimValidade(this.dataFimValidade);

			FormaRecebimento forma = new FormaRecebimento();
			forma.setId(new Long(this.idForma));
			planoPagamento.setForma(forma);
			
			getFachada().excluirPlanoPagamento(planoPagamento);
			
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			resetBB();
		} catch (Exception e) {
			if (e.getCause().getCause() instanceof HibernateException) {
				if (e.getCause().getCause().getCause() instanceof ConstraintViolationException) {
					
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Existe informções relcionadas com o plano que deseja excluir!", "");
					getContextoApp().addMessage(null, msg);
				}
			} else {
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Erro de Sistema", "");
				getContextoApp().addMessage(null, msg);
				e.printStackTrace();
			}
		}
		return "mesma";
	}
	
	public void resetBB(){
		this.setId(null);
		this.setDescricao(null);
		this.setStatus(null);
		this.setValorMaximo(null);
		this.setValorMinimo(null);
		this.setPercDesconto(null);
		this.setPercAcrescimo(null);
		this.setDataInicioValidade(null);
		this.setDataFimValidade(null);
		this.setIdForma(null);
	}
	
	public SelectItem[] getSituacaoItens() {
		SelectItem[] situacaoItens = new SelectItem[]{new SelectItem(Constantes.STATUS_ATIVO,"Ativo"),
                new SelectItem(Constantes.STATUS_INATIVO,"Inativo")};
		if(getStatus() == null){
			setStatus(Constantes.STATUS_ATIVO);
		}
		return situacaoItens;
	}
	protected List<FormaRecebimento> carregarFormas() {
		
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
			
			if(this.getIdForma() == null || this.getIdForma().equals("") || this.getIdForma().equals("0")){
				this.setIdForma((String) arrayFormas[0].getValue());				
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return arrayFormas;
	}
	public String getIdForma() {
		return idForma;
	}
	public void setIdForma(String idForma) {
		this.idForma = idForma;
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
	public BigDecimal getPercAcrescimo() {
		return percAcrescimo;
	}
	public void setPercAcrescimo(BigDecimal percAcrescimo) {
		this.percAcrescimo = percAcrescimo;
	}
	public BigDecimal getPercDesconto() {
		return percDesconto;
	}
	public void setPercDesconto(BigDecimal percDesconto) {
		this.percDesconto = percDesconto;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public BigDecimal getValorMaximo() {
		return valorMaximo;
	}
	public void setValorMaximo(BigDecimal valorMaximo) {
		this.valorMaximo = valorMaximo;
	}
	public BigDecimal getValorMinimo() {
		return valorMinimo;
	}
	public void setValorMinimo(BigDecimal valorMinimo) {
		this.valorMinimo = valorMinimo;
	}
	public void setFormas(SelectItem[] formas) {
		this.formas = formas;
	}
	public void setSituacaoItens(SelectItem[] situacaoItens) {
		this.situacaoItens = situacaoItens;
	}
	
	public void preenchePlanoPagamento(PlanoPagamento plano, String acao) throws AppException{
//		PlanoPagamento planoPagamento = null;
//		
//		if(plano instanceof PlanoPagamentoAPrazo){
//			planoPagamento = (PlanoPagamentoAPrazo)plano;
//		}else if(plano instanceof PlanoPagamentoAVista){
//			planoPagamento = (PlanoPagamentoAVista)plano;
//		}else{
//			planoPagamento = plano;
//		}

		plano.setId(new Long(getId()));
		plano.setDescricao(this.descricao);
		plano.setStatus(this.status);
		plano.setValorMaximo(this.valorMaximo);
		plano.setValorMinimo(this.valorMinimo);
		plano.setPercAcrescimo(this.percAcrescimo);
		plano.setPercDesconto(this.percDesconto);
		plano.setDataInicioValidade(this.dataInicioValidade);
		plano.setDataFimValidade(this.dataFimValidade);
		if(this.getIdForma() != null && !this.getIdForma().equals("0")){
			FormaRecebimento forma = new FormaRecebimento();
			forma.setId(new Long(this.getIdForma()));
			plano.setForma(forma);
		}else{
			throw new AppException("É obrigatório selecionar uma Forma de Recebimento Associada.");
		}
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
				setPlanos(null);
			}
		}
	}
}
