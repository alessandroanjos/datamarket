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
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import com.infinity.datamarket.comum.pagamento.ParcelaPlanoPagamentoChequePredatado;
import com.infinity.datamarket.comum.pagamento.ParcelaPlanoPagamentoChequePredatadoPK;
import com.infinity.datamarket.comum.pagamento.PlanoPagamentoChequePredatado;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Constantes;
import com.infinity.datamarket.enterprise.gui.planoPagamento.PlanoPagamentoBackBean;

/**
 * @author Jonas
 *
 */
public class PlanoPagamentoChequePreBackBean extends PlanoPagamentoBackBean {
	
//	public PlanoPagamentoChequePreBackBean(){
//		setPercentagemEntrada(BigDecimal.ZERO);
//	}

	String abaCorrente;
	
	BigDecimal percentualTotal = new BigDecimal(100.00).setScale(2);
	BigDecimal percentualRestante = BigDecimal.ZERO;
	
	BigDecimal percentagemEntrada = BigDecimal.ZERO;
	BigDecimal percentagemParcela = BigDecimal.ZERO;
	int quantidadeDias;
	int numParcela = 0;
	String dataProgramada;
	SelectItem[] dataProgramadaSimNao;
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
	
	public void resetBB(){
		super.resetBB();
//		this.setPercentagemEntrada(null);
		this.setDataProgramada(Constantes.NAO);
		this.setParcelas(null);
	}
	
	public void validarBackBean() throws AppException{
		if(this.getId() == null || this.getId().equals("")){
			throw new AppException("O campo Código é obrigatório.");
		}		
		if(this.getDescricao() == null || this.getDescricao().equals("")){
			throw new AppException("O campo Descrição é obrigatório.");
		}
		if(this.getIdForma() == null || this.getIdForma().equals("0")){
			throw new AppException("O campo Forma Associada é obrigatório.");
		}
		
		if(this.getValorMinimo() == null || this.getValorMinimo().compareTo(new BigDecimal("0")) <= 0){
			throw new AppException("O campo Valor Mínimo é obrigatório.");
		}
		
		if(this.getValorMaximo() == null || this.getValorMaximo().compareTo(new BigDecimal("0")) <= 0){
			throw new AppException("O campo Valor Máximo é obrigatório.");
		}
		
		/*if(this.getPercDesconto() == null || this.getPercDesconto().compareTo(new BigDecimal("0")) < 0){
			throw new Exception("O campo Percentual de Desconto é obrigatório.");
		}
		
		if(this.getPercAcrescimo() == null || this.getPercAcrescimo().compareTo(new BigDecimal("0")) < 0){
			throw new Exception("O campo Percentual de Acréscimo é obrigatório.");
		}*/

		if(this.getDataInicioValidade() == null || this.getDataInicioValidade().equals("")){
			throw new AppException("É necessário informar a Data Inicial de Validade.");
		}
		
		if(this.getDataFimValidade() == null || this.getDataFimValidade().equals("")){
			throw new AppException("É necessário informar a Data Final de Validade.");
		}
		
		if(this.getStatus() == null){
			throw new AppException("É necessário selecionar uma Situação.");
		}

		if(this.getParcelas() == null || (this.getParcelas() != null && this.getParcelas().size() == 0)){
			throw new AppException("É necessário ter pelo menos uma parcela.");
		}
	}
	
	public String inserir(){
		try {	
			
			
			setIdForma(Constantes.FORMA_CHEQUE_PRE);
			validarBackBean();
			
			PlanoPagamentoChequePredatado planoPre = new PlanoPagamentoChequePredatado();
			
			preenchePlanoPagamento(planoPre, INSERIR);
			
			planoPre.setPercentagemEntrada(this.getPercentagemEntrada());
		
			BigDecimal totalPercentagem = BigDecimal.ZERO;
			totalPercentagem = totalPercentagem.add(this.getPercentagemEntrada());
				
			Iterator it = this.getParcelas().iterator();
			
			while (it.hasNext()){
				ParcelaPlanoPagamentoChequePredatado parcela = (ParcelaPlanoPagamentoChequePredatado)it.next();
				parcela.getPk().setPlano(planoPre);
				totalPercentagem = totalPercentagem.add(parcela.getPercentagemParcela());
			}
			
			if(totalPercentagem.compareTo(new BigDecimal("100")) != 0){
				throw new Exception("O somatório do percentual das parcelas mais o percentual de entrada deve ser igual a 100%.");
			}
			
			planoPre.setParcelas(this.getParcelas());
			
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
		return "mesma";
	}
	
	public String alterar(){
		try {
			setIdForma(Constantes.FORMA_CHEQUE_PRE);
			validarBackBean();
			
			BigDecimal totalPercentagem = BigDecimal.ZERO;
			PlanoPagamentoChequePredatado planoPre = new PlanoPagamentoChequePredatado();
			
			preenchePlanoPagamento(planoPre, ALTERAR);
			
			planoPre.setPercentagemEntrada(this.getPercentagemEntrada());
			
			totalPercentagem = totalPercentagem.add(this.getPercentagemEntrada());
			
			if(this.getParcelas() == null || (this.getParcelas() != null && this.getParcelas().size() == 0)){
				throw new Exception("É necessário ter pelo menos uma parcela.");
			}
			
			Iterator it = this.getParcelas().iterator();
			
			while (it.hasNext()){
				ParcelaPlanoPagamentoChequePredatado parcela = (ParcelaPlanoPagamentoChequePredatado)it.next();
				parcela.getPk().setPlano(planoPre);
				totalPercentagem = totalPercentagem.add(parcela.getPercentagemParcela());
			}
			
			planoPre.setParcelas(this.getParcelas());
			
			if(totalPercentagem.compareTo(new BigDecimal("100")) != 0){
				throw new Exception("O somatório do percentual das parcelas mais o percentual de entrada deve ser igual a 100%.");
			}
			
			getFachada().alterarPlanoPagamento(planoPre);
			
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			this.resetBB();
			this.setPlanos(null);
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
		return "mesma";
	}

	public String excluir(){
		try {
			setIdForma(Constantes.FORMA_CHEQUE_PRE);
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
			if (e.getCause().getCause() instanceof HibernateException) {
				if (e.getCause().getCause().getCause() instanceof ConstraintViolationException) {
					FacesContext ctx = FacesContext.getCurrentInstance();
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Existe informções relcionadas com o plano que deseja excluir!", "");
					ctx.addMessage(null, msg);
				}
			} else {
				FacesContext ctx = FacesContext.getCurrentInstance();
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Erro de Sistema", "");
				ctx.addMessage(null, msg);
				e.printStackTrace();
			}

			

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
					setExisteRegistros(false);
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
						setExisteRegistros(true);
						this.setPlanos(col);
					}
				}
			}else{
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(PlanoPagamentoChequePredatado.class);
				Collection c = getFachada().consultarPlanoPagamento(filter);
				if(c != null && c.size() > 0){
					setExisteRegistros(true);
					setPlanos(c);
				}else{
					setExisteRegistros(false);
					setPlanos(null);
				}				
			}
		}catch(ObjectNotFoundException e){
			setExisteRegistros(false);
			this.setPlanos(null);
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
		this.setPercentagemEntrada(BigDecimal.ZERO);
		this.setParcelas(null);
		return "mesma";
	}

	
	public String inserirParcela(){
		try {
			if(this.getPercentagemEntrada() == null){
				this.setPercentagemEntrada(BigDecimal.ZERO);
			}
			if(this.getPercentagemParcela().equals(BigDecimal.ZERO)){
				throw new Exception("O Percentual da Parcela deve ser maior que 0 (Zero).");
			}
			if(this.getDataProgramada().equals(Constantes.SIM) && this.getQuantidadeDias() == 0){
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
			parcela.setDataProgramada(getDataProgramada());
			
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
			}else if((this.dataProgramada.equals(Constantes.SIM)) && (parcela.getQuantidadeDias() <= quantidadeDiasParcelaAnterior)){
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
		this.setPercentagemParcela(null);
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

	public String getAbaCorrente() {
		return abaCorrente;
	}

	public void setAbaCorrente(String abaCorrente) {
		this.abaCorrente = abaCorrente;
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

	public SelectItem[] getDataProgramadaSimNao() {
		SelectItem[] dataProgramadaSimNao = new SelectItem[]{new SelectItem(Constantes.SIM,"Sim"),
                new SelectItem(Constantes.NAO,"Não")};
		if(getDataProgramada() == null){
			setDataProgramada(Constantes.NAO);
		}
		return dataProgramadaSimNao;
	}
	/**
	 * @return the dataProgramada
	 */
	public String getDataProgramada() {
		return dataProgramada;
	}

	/**
	 * @param dataProgramada the dataProgramada to set
	 */
	public void setDataProgramada(String dataProgramada) {
		this.dataProgramada = dataProgramada;
	}
}