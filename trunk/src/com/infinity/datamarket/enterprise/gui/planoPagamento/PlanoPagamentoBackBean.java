package com.infinity.datamarket.enterprise.gui.planoPagamento;

/**
 * 
 */
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.infinity.datamarket.comum.pagamento.FormaRecebimento;
import com.infinity.datamarket.comum.pagamento.PlanoPagamento;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
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
	FormaRecebimento forma;
	Collection planos;
	
	public String voltarConsulta(){
		resetBB();
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


	public String inserir(){

		PlanoPagamento planoPagamento = new PlanoPagamento();
		planoPagamento.setId(new Long(this.id));
		planoPagamento.setDescricao(this.descricao);
		planoPagamento.setStatus(this.status);
		planoPagamento.setValorMaximo(this.valorMaximo);
		planoPagamento.setValorMinimo(this.valorMinimo);
		planoPagamento.setValorMinimo(this.valorMinimo);
		planoPagamento.setDataInicioValidade(this.dataInicioValidade);
		planoPagamento.setDataFimValidade(this.dataFimValidade);
        planoPagamento.setForma(this.forma);

		try {
			getFachada().inserirPlanoPagamento(planoPagamento);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Forma de recebimento já Existente!", "");
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
				planoPagamento.setId(new Long(this.id));
				planoPagamento.setDescricao(this.descricao);
				planoPagamento.setStatus(this.status);
				planoPagamento.setValorMaximo(this.valorMaximo);
				planoPagamento.setValorMinimo(this.valorMinimo);
				planoPagamento.setValorMinimo(this.valorMinimo);
				planoPagamento.setDataInicioValidade(this.dataInicioValidade);
				planoPagamento.setDataFimValidade(this.dataFimValidade);
		        planoPagamento.setForma(this.forma);
				
				return "proxima";
			}else if (getDescricao() != null && !"".equals(getDescricao())){
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(FormaRecebimento.class);
				filter.addProperty("descricao", getDescricao());
				Collection col = getFachada().consultarPlanoPagamento(filter);
				if (col == null || col.size() == 0){
					this.setPlanos(col);
					FacesContext ctx = FacesContext.getCurrentInstance();
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					ctx.addMessage(null, msg);					
				}else if (col != null){
					if(col.size() == 1){
						PlanoPagamento planoPagamento = getFachada().consultarPlanoPagamentoPorId(new Long(getId()));
						this.setId(planoPagamento.getId().toString());
						this.setDescricao(planoPagamento.getDescricao());
						this.setStatus(planoPagamento.getStatus());
						this.setValorMaximo(planoPagamento.getValorMaximo());
						this.setValorMinimo(planoPagamento.getValorMinimo());
						this.setValorMinimo(planoPagamento.getValorMinimo());
						this.setDataInicioValidade(planoPagamento.getDataInicioValidade());
						this.setDataFimValidade(planoPagamento.getDataFimValidade());
						this.setForma(planoPagamento.getForma());

						return "proxima";
					}else{
						this.setPlanos(col);
					}
				}
			}else{
				setPlanos(getFachada().consultarTodosPlanos());
			}
		}catch(ObjectNotFoundException e){
			this.setPlanos(null);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Nenhum Registro Encontrado", "");
			ctx.addMessage(null, msg);			
		}catch(Exception e){
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		this.setId(null);
		this.setDescricao(null);
		this.setStatus(null);
		this.setValorMaximo(null);
		this.setValorMinimo(null);
		this.setValorMinimo(null);
		this.setDataInicioValidade(null);
		this.setDataFimValidade(null);
		this.setForma(null);
		return "mesma";
	}
	
	public String alterar(){
		try {
			PlanoPagamento planoPagamento = new PlanoPagamento();
			
			planoPagamento.setId(new Long(this.id));
			planoPagamento.setDescricao(this.descricao);
			planoPagamento.setStatus(this.status);
			planoPagamento.setValorMaximo(this.valorMaximo);
			planoPagamento.setValorMinimo(this.valorMinimo);
			planoPagamento.setValorMinimo(this.valorMinimo);
			planoPagamento.setDataInicioValidade(this.dataInicioValidade);
			planoPagamento.setDataFimValidade(this.dataFimValidade);
	        planoPagamento.setForma(this.forma);

			getFachada().alterarPlanoPagamento(planoPagamento);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
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
			PlanoPagamento planoPagamento = new PlanoPagamento();
			
			planoPagamento.setId(new Long(this.id));
			planoPagamento.setDescricao(this.descricao);
			planoPagamento.setStatus(this.status);
			planoPagamento.setValorMaximo(this.valorMaximo);
			planoPagamento.setValorMinimo(this.valorMinimo);
			planoPagamento.setValorMinimo(this.valorMinimo);
			planoPagamento.setDataInicioValidade(this.dataInicioValidade);
			planoPagamento.setDataFimValidade(this.dataFimValidade);
	        planoPagamento.setForma(this.forma);
			getFachada().excluirPlanoPagamento(planoPagamento);
			
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		resetBB();
		return "mesma";
	}
	
	public String resetBB(){
		this.setId(null);
		this.setDescricao(null);
		
		this.setDataInicioValidade(null);
		this.setDataFimValidade(null);
		
		this.setPlanos(null);
		return "mesma";
	}
	
	public FormaRecebimento getForma() {
		return forma;
	}
	public void setForma(FormaRecebimento forma) {
		this.forma = forma;
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
	
}
