package com.infinity.datamarket.enterprise.gui.planoPagamentoChequePre;

/**
 * 
 */
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.infinity.datamarket.comum.pagamento.ParcelaPlanoPagamentoChequePredatado;
import com.infinity.datamarket.comum.pagamento.ParcelaPlanoPagamentoChequePredatadoPK;
import com.infinity.datamarket.comum.pagamento.PlanoPagamentoChequePredatado;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.enterprise.gui.planoPagamento.PlanoPagamentoBackBean;

/**
 * @author Jonas
 *
 */
public class PlanoPagamentoChequePreBackBean extends PlanoPagamentoBackBean {

	BigDecimal percentualTotal = new BigDecimal(100.00).setScale(2);
	BigDecimal percentualRestante = BigDecimal.ZERO;
	
	BigDecimal percentagemEntrada = BigDecimal.ZERO;
	BigDecimal percentagemParcela = BigDecimal.ZERO;
	int quantidadeDias;
	int numParcela = 0;
	SortedSet<ParcelaPlanoPagamentoChequePredatado> parcelas = new TreeSet<ParcelaPlanoPagamentoChequePredatado>(); 

	public SortedSet<ParcelaPlanoPagamentoChequePredatado> getParcelas() {
		return parcelas;
	}

	public void setParcelas(SortedSet<ParcelaPlanoPagamentoChequePredatado> parcelas) {
		this.parcelas = parcelas;
	}

	public BigDecimal getPercentagemEntrada() {
		return percentagemEntrada;
	}

	public void setPercentagemEntrada(BigDecimal percentagemEntrada) {
		this.percentagemEntrada = percentagemEntrada;
	}

	public int getNumParcela() {
		return numParcela;
	}

	public void setNumParcela(int numParcela) {
		this.numParcela = numParcela;
	}

	public BigDecimal getPercentagemParcela() {
		return percentagemParcela;
	}

	public void setPercentagemParcela(BigDecimal percentagemParcela) {
		this.percentagemParcela = percentagemParcela;
	}

	public int getQuantidadeDias() {
		return quantidadeDias;
	}

	public void setQuantidadeDias(int quantidadeDias) {
		this.quantidadeDias = quantidadeDias;
	}	
	
	public BigDecimal getPercentualRestante() {
		return percentualRestante;
	}

	public void setPercentualRestante(BigDecimal percentualRestante) {
		this.percentualRestante = percentualRestante;
	}

	public BigDecimal getPercentualTotal() {
		return percentualTotal;
	}

	public void setPercentualTotal(BigDecimal percentualTotal) {
		this.percentualTotal = percentualTotal;
	}
	
	public String voltarConsulta(){
		this.resetBB();
		return "voltar";
	}

	public String voltarMenu(){
		this.resetBB();
		return "voltar";
	}
	
	public String resetBB(){
		super.resetBB();
		this.setPercentagemEntrada(null);
		this.setParcelas(null);
		return "mesma";
	}
	
	public String inserir(){
		try {			
			BigDecimal totalPercentagem = BigDecimal.ZERO;
			PlanoPagamentoChequePredatado planoPre = new PlanoPagamentoChequePredatado();
			
			preenchePlanoPagamento(planoPre, INSERIR);
			
			planoPre.setPercentagemEntrada(this.getPercentagemEntrada());
			
			totalPercentagem = totalPercentagem.add(this.getPercentagemEntrada());
			
			Iterator it = this.getParcelas().iterator();
			
			while (it.hasNext()){
				ParcelaPlanoPagamentoChequePredatado parcela = (ParcelaPlanoPagamentoChequePredatado)it.next();
				parcela.getPk().setPlano(planoPre);
				totalPercentagem = totalPercentagem.add(parcela.getPercentagemParcela());
			}
			
			planoPre.setParcelas(this.getParcelas());
			
			if(totalPercentagem.compareTo(new BigDecimal(100)) > 0){
				throw new Exception("O somatório do percentual das parcelas mais \n o percentual de entrada não deve ultrapassar 100%.");
			}
			
			getFachada().inserirPlanoPagamento(planoPre);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			this.resetBB();
			this.setPlanos(null);
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
		this.resetBB();
		return "mesma";
	}
	
	public String alterar(){
		try {
			BigDecimal totalPercentagem = BigDecimal.ZERO;
			PlanoPagamentoChequePredatado planoPre = new PlanoPagamentoChequePredatado();
			
			preenchePlanoPagamento(planoPre, ALTERAR);
			
			planoPre.setPercentagemEntrada(this.getPercentagemEntrada());
			
			totalPercentagem = totalPercentagem.add(this.getPercentagemEntrada());
			
			Iterator it = this.getParcelas().iterator();
			
			while (it.hasNext()){
				ParcelaPlanoPagamentoChequePredatado parcela = (ParcelaPlanoPagamentoChequePredatado)it.next();
				parcela.getPk().setPlano(planoPre);
				totalPercentagem = totalPercentagem.add(parcela.getPercentagemParcela());
			}
			
			planoPre.setParcelas(this.getParcelas());
			
			if(totalPercentagem.compareTo(new BigDecimal(100)) > 0){
				throw new Exception("O somatório entre o perc. das parcelas e o perc. de entrada não deve ultrapassar 100%.");
			}
			
			getFachada().alterarPlanoPagamento(planoPre);
			
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			this.resetBB();
			this.setPlanos(null);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			ctx.addMessage(null, msg);
		}
		return "mesma";
	}

	public String excluir(){
		try {
			PlanoPagamentoChequePredatado planoPre = new PlanoPagamentoChequePredatado();
			
			preenchePlanoPagamento(planoPre, ALTERAR);
			
			planoPre.setPercentagemEntrada(this.getPercentagemEntrada());
			
			planoPre.setParcelas(null);
			
			getFachada().excluirPlanoPagamento(planoPre);
			
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			this.resetBB();
			this.setPlanos(null);
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
	
	public String consultar(){
		try{
			FacesContext context = FacesContext.getCurrentInstance();
			Map params = context.getExternalContext().getRequestParameterMap();            
			String param = (String)  params.get("id");
			if (param != null && !"".equals(param)){
				setId(param);
			}
			if (getId() != null && !"".equals(getId())){
				PlanoPagamentoChequePredatado planoPagamento = (PlanoPagamentoChequePredatado)getFachada().consultarPlanoPagamentoPorId(new Long(getId()));
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
		        
		        this.setPercentagemEntrada(planoPagamento.getPercentagemEntrada());
		        Iterator it = planoPagamento.getParcelas().iterator();
				
		        if(this.getParcelas() == null){
					this.setParcelas(new TreeSet<ParcelaPlanoPagamentoChequePredatado>());
				}

		        while(it.hasNext()){
		        	this.getParcelas().add((ParcelaPlanoPagamentoChequePredatado)it.next());
		        }		       
		        
				return "proxima";
			}else if (getDescricao() != null && !"".equals(getDescricao())){
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(PlanoPagamentoChequePredatado.class);
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
						PlanoPagamentoChequePredatado planoPagamento = (PlanoPagamentoChequePredatado)getFachada().consultarPlanoPagamentoPorId(new Long(getId()));
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
				        
				        this.setPercentagemEntrada(planoPagamento.getPercentagemEntrada());
				        Iterator it = planoPagamento.getParcelas().iterator();
						
				        if(this.getParcelas() == null){
							this.setParcelas(new TreeSet<ParcelaPlanoPagamentoChequePredatado>());
						}

				        while(it.hasNext()){
				        	this.getParcelas().add((ParcelaPlanoPagamentoChequePredatado)it.next());
				        }		       
						return "proxima";
					}else{
						this.setPlanos(col);
					}
				}
			}else{
				setPlanos(getFachada().consultarTodosPlanosChequePre());
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
		this.setPercDesconto(null);
		this.setPercAcrescimo(null);
		this.setDataInicioValidade(null);
		this.setDataFimValidade(null);
		this.setIdForma(null);
		this.setPercentagemEntrada(BigDecimal.ZERO);
		this.setParcelas(null);
		return "mesma";
	}

	
	public String inserirParcela(){
		try {			
			if(this.getPercentagemParcela().equals(BigDecimal.ZERO)){
				throw new Exception("O Percentual da Parcela deve ser maior que 0 (Zero).");
			}
			if(this.getQuantidadeDias() == 0){
				throw new Exception("A Quantiade de Dias deve ser maior que 0 (Zero).");
			}
			
			int quantidadeDiasParcelaAnterior = 0;
			BigDecimal percentualParcelasParcial = BigDecimal.ZERO;
			if(this.getPercentualRestante().equals(BigDecimal.ZERO)){
				this.setPercentualRestante(this.getPercentualTotal().subtract(this.getPercentagemEntrada()));
			}
			
			ParcelaPlanoPagamentoChequePredatado parcela = new ParcelaPlanoPagamentoChequePredatado();
			ParcelaPlanoPagamentoChequePredatadoPK parcelaPK = new ParcelaPlanoPagamentoChequePredatadoPK();
			
			
			parcelaPK.setPlano(null);
			
			parcela.setPk(parcelaPK);
			parcela.setPercentagemParcela(this.getPercentagemParcela());
			parcela.setQuantidadeDias(this.getQuantidadeDias());
			
			if(this.getParcelas() == null){
				this.setParcelas(new TreeSet<ParcelaPlanoPagamentoChequePredatado>());
			}
			
			Iterator<ParcelaPlanoPagamentoChequePredatado> itParcelas = this.getParcelas().iterator();
			
			while (itParcelas.hasNext()){
				ParcelaPlanoPagamentoChequePredatado parcelaTmp = itParcelas.next();
				if(parcelaTmp.getQuantidadeDias() > quantidadeDiasParcelaAnterior){
					quantidadeDiasParcelaAnterior = parcelaTmp.getQuantidadeDias();
					percentualParcelasParcial = percentualParcelasParcial.add(parcelaTmp.getPercentagemParcela());
				}							
			}
			
			percentualParcelasParcial = percentualParcelasParcial.add(parcela.getPercentagemParcela()).add(this.getPercentagemEntrada());
			
			if(percentualParcelasParcial.floatValue() > this.getPercentualTotal().floatValue()){
				// nao pode adicionar a parcela, pois, o percentual estoura os 100% disponiveis.
				throw new Exception("O percentual não deve ultrapassar 100%. O Percentual restante é "+this.getPercentualRestante().setScale(2).toString());
			}else if(parcela.getQuantidadeDias() <= quantidadeDiasParcelaAnterior){
				// a quantidade de dias da parcela nao pode ser inferior a da ultima parcela
				throw new Exception("A Quantidade de Dias da Parcela não deve ser menor ou igual a da parcela anterior.");
			}else{
				parcelaPK.setNumeroEntrada(++numParcela);
				this.getParcelas().add(parcela);
				this.setPercentualRestante(this.getPercentualRestante().subtract(parcela.getPercentagemParcela()));
			}		
		} catch (Exception e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			ctx.addMessage(null, msg);
		}
		this.setPercentagemParcela(BigDecimal.ZERO);
		this.setQuantidadeDias(0);
		return "mesma";
	}
	
	public String excluirParcela(){
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			Map params = context.getExternalContext().getRequestParameterMap();  
			String param = (String)  params.get("idExcluir");
			
			Iterator<ParcelaPlanoPagamentoChequePredatado> itParcelas = this.getParcelas().iterator();
			
			while (itParcelas.hasNext()){
				ParcelaPlanoPagamentoChequePredatado parcelaTmp = itParcelas.next();
				if(parcelaTmp.getPk().getNumeroEntrada() == Integer.parseInt(param)){
					this.setPercentualRestante(this.getPercentualRestante().add(parcelaTmp.getPercentagemParcela()));
					this.getParcelas().remove(parcelaTmp);
					break;
				}
			}
			if(this.getParcelas().size() == 0){
				numParcela = 0;
			}
		} catch (Exception e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao tentar excluir a Parcela!", "");
			ctx.addMessage(null, msg);
		}
		return "mesma";
	}
}