package com.infinity.datamarket.enterprise.gui.planoPagamentoAPrazo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import com.infinity.datamarket.comum.pagamento.ConstantesFormaRecebimento;
import com.infinity.datamarket.comum.pagamento.FormaRecebimento;
import com.infinity.datamarket.comum.pagamento.ParcelaPlanoPagamentoAPrazo;
import com.infinity.datamarket.comum.pagamento.ParcelaPlanoPagamentoAPrazoPK;
import com.infinity.datamarket.comum.pagamento.PlanoPagamento;
import com.infinity.datamarket.comum.pagamento.PlanoPagamentoAPrazo;
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
public class PlanoPagamentoAPrazoBackBean extends PlanoPagamentoBackBean {
	
	String abaCorrente;
	
	BigDecimal percentualTotal = new BigDecimal(100.00).setScale(2);
	BigDecimal percentualRestante = BigDecimal.ZERO;
	
	BigDecimal percentagemEntrada = BigDecimal.ZERO;
	BigDecimal percentagemParcela = BigDecimal.ZERO;
	int quantidadeDias;
	int numParcela = 0;
	String dataProgramada;
	SelectItem[] dataProgramadaSimNao;
	SortedSet<ParcelaPlanoPagamentoAPrazo> parcelas = new TreeSet<ParcelaPlanoPagamentoAPrazo>();
	SortedSet<ParcelaPlanoPagamentoAPrazo> parcelasRemovidas = new TreeSet<ParcelaPlanoPagamentoAPrazo>();
	PlanoPagamentoAPrazo planoPagtoAPrazo = null;
	String parcelasFixasVariadas= null;
	SelectItem[] parcelasFixasVariadasItens;
	String parcelasVariadasDatasAutomaticas;
	SelectItem[] parcelasVariadasDatasAutomaticasItens;
	
	public String getParcelasVariadasDatasAutomaticas() {
		return parcelasVariadasDatasAutomaticas;
	}

	public void setParcelasVariadasDatasAutomaticas(
			String parcelasVariadasDatasAutomaticas) {
		this.parcelasVariadasDatasAutomaticas = parcelasVariadasDatasAutomaticas;
	}

	public String getParcelasFixasVariadas() {
		return parcelasFixasVariadas;
	}

	public void setParcelasFixasVariadas(String parcelasFixasVariadas) {
		this.parcelasFixasVariadas = parcelasFixasVariadas;
	}

	public SortedSet<ParcelaPlanoPagamentoAPrazo> getParcelasRemovidas() {
		return parcelasRemovidas;
	}

	public void setParcelasRemovidas(
			SortedSet<ParcelaPlanoPagamentoAPrazo> parcelasRemovidas) {
		this.parcelasRemovidas = parcelasRemovidas;
	}

	public SortedSet<ParcelaPlanoPagamentoAPrazo> getParcelas() {
		return parcelas;
	}

	public void setParcelas(SortedSet<ParcelaPlanoPagamentoAPrazo> parcelas) {
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
		this.setPercentagemEntrada(new BigDecimal("0.00"));
		this.setDataProgramada(Constantes.NAO);
		this.setParcelas(null);
		this.setAbaCorrente("tabDiv0");
		this.parcelasFixasVariadas = null;
		this.parcelasVariadasDatasAutomaticas = null;
		
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
		if(this.getIdForma() == null || this.getIdForma().equals("")){
			throw new AppException("É necessário informar uma Forma de Recebimento Associada.");
		}
	}
	
//	String parcelasVariadasDatasAutomaticas;
//	SelectItem[] parcelasVariadasDatasAutomaticasItens;

	
	public SelectItem[] getParcelasVariadasDatasAutomaticasItens() {
		SelectItem[] parcelasVariadasDatasAutomaticasItens = new SelectItem[]{new SelectItem(Constantes.DATAS_INFORMADA_PELO_SISTEMA,"Datas Infomrada pelo Sistema"),
                new SelectItem(Constantes.DATAS_INFORMADA_PELO_USUARIO,"Datas Informada pelo Usuário")};
		if(getParcelasVariadasDatasAutomaticas() == null){
			setParcelasVariadasDatasAutomaticas(Constantes.DATAS_INFORMADA_PELO_SISTEMA);
		}
		return parcelasVariadasDatasAutomaticasItens;
	}
	public void setParcelasVariadasDatasAutomaticasItens(SelectItem[] parcelasVariadasDatasAutomaticasItens) {
		this.parcelasVariadasDatasAutomaticasItens = parcelasVariadasDatasAutomaticasItens;
	}

	
	public SelectItem[] getParcelasFixasVariadasItens() {
		SelectItem[] parcelasFixasVariadasItens = new SelectItem[]{new SelectItem(Constantes.PARCELAS_FIXAS,"Parcelas Fixas"),
                new SelectItem(Constantes.PARCELAS_VARIADAS,"Parcelas Variadas")};
		if(getParcelasFixasVariadas() == null){
			setParcelasFixasVariadas(Constantes.PARCELAS_FIXAS);
		}
		return parcelasFixasVariadasItens;
	}
	public void setParcelasFixasVariadasItens(SelectItem[] parcelasFixasVariadasItens) {
		this.parcelasFixasVariadasItens = parcelasFixasVariadasItens;
	}
public String inserir(){
		try {	
			
			
			validarBackBean();
			PlanoPagamentoAPrazo planoPre = new PlanoPagamentoAPrazo();
			planoPre.setParcelasFixasVariadas(getParcelasFixasVariadas());
			planoPre.setDatasParcelasVariadasInformadaSistemaOuUsuario(getParcelasVariadasDatasAutomaticas());
			
			preenchePlanoPagamento(planoPre, INSERIR);

			((PlanoPagamentoAPrazo)planoPre).setPercentagemEntrada(this.getPercentagemEntrada());

			BigDecimal totalPercentagem = BigDecimal.ZERO;
			totalPercentagem = totalPercentagem.add(this.getPercentagemEntrada());

			if (getParcelasFixasVariadas().equalsIgnoreCase(Constantes.PARCELAS_FIXAS)) {
		
				Iterator it = this.getParcelas().iterator();
				
				while (it.hasNext()){
					ParcelaPlanoPagamentoAPrazo parcela = (ParcelaPlanoPagamentoAPrazo)it.next();
					parcela.getPk().setPlano((PlanoPagamentoAPrazo)planoPre);
					totalPercentagem = totalPercentagem.add(parcela.getPercentagemParcela());
				}
				
				if(totalPercentagem.compareTo(new BigDecimal("100")) != 0){
					throw new Exception("O somatório do percentual das parcelas mais o percentual de entrada deve ser igual a 100%.");
				}
				
				((PlanoPagamentoAPrazo)planoPre).setParcelas(this.getParcelas());
				
				getFachada().inserirPlanoPagamento(planoPre);
			} else {
				((PlanoPagamentoAPrazo)planoPre).setParcelas(null);
			}

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			this.resetBB();
			this.setPlanos(null);
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
	
	public String alterar(){
		try {
//			setIdForma(Constantes.FORMA_CHEQUE_PRE);
			
			validarBackBean();
			
			BigDecimal totalPercentagem = BigDecimal.ZERO;
			
			PlanoPagamentoAPrazo planoPre = new PlanoPagamentoAPrazo();//(PlanoPagamentoAPrazo)getFachada().consultarPlanoPagamentoPorId(new Long(this.getId()));//
			planoPre.setParcelasFixasVariadas(getParcelasFixasVariadas());
			planoPre.setDatasParcelasVariadasInformadaSistemaOuUsuario(getParcelasVariadasDatasAutomaticas());

			preenchePlanoPagamento(planoPre, ALTERAR);
			
			totalPercentagem = totalPercentagem.add(this.getPercentagemEntrada());
			
			((PlanoPagamentoAPrazo)planoPre).setPercentagemEntrada(this.getPercentagemEntrada());
		
			if (getParcelasFixasVariadas().equalsIgnoreCase(Constantes.PARCELAS_FIXAS)) {
	
				if(this.getParcelas() == null || (this.getParcelas() != null && this.getParcelas().size() == 0)){
					throw new Exception("É necessário ter pelo menos uma parcela.");
				}
				
				Iterator it = this.getParcelas().iterator();
	
				Set<ParcelaPlanoPagamentoAPrazo> conjParcelas = new TreeSet<ParcelaPlanoPagamentoAPrazo>();
				
				while (it.hasNext()){
					ParcelaPlanoPagamentoAPrazo parcela = (ParcelaPlanoPagamentoAPrazo)it.next();
					parcela.getPk().setPlano((PlanoPagamentoAPrazo)planoPre);
					conjParcelas.add(parcela);
					totalPercentagem = totalPercentagem.add(parcela.getPercentagemParcela());
				}

				((PlanoPagamentoAPrazo)planoPre).setParcelas(conjParcelas);
				

				if(totalPercentagem.compareTo(new BigDecimal("100")) != 0){
					throw new AppException("O somatório do percentual das parcelas mais o percentual de entrada deve ser igual a 100%.");
				}
				
			} else {
				((PlanoPagamentoAPrazo)planoPre).setParcelas(null);
			}

			getFachada().alterarPlanoPagamento(planoPre);

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			this.resetBB();
			this.setPlanos(null);
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

	public void preenchePlanoPagamento(PlanoPagamento plano, String acao) throws AppException{

		if (plano instanceof PlanoPagamentoAPrazo) {
			((PlanoPagamentoAPrazo)plano).setParcelasFixasVariadas(getParcelasFixasVariadas());
			((PlanoPagamentoAPrazo)plano).setDatasParcelasVariadasInformadaSistemaOuUsuario(getParcelasVariadasDatasAutomaticas());
		}
		super.preenchePlanoPagamento(plano, acao);
	}
	
	public String excluir(){
		try {
//			setIdForma(Constantes.FORMA_CHEQUE_PRE);
			PlanoPagamentoAPrazo planoPre = new PlanoPagamentoAPrazo();
			planoPre.setParcelasFixasVariadas(getParcelasFixasVariadas());
			planoPre.setDatasParcelasVariadasInformadaSistemaOuUsuario(getParcelasVariadasDatasAutomaticas());

			preenchePlanoPagamento(planoPre, ALTERAR);
			
			((PlanoPagamentoAPrazo)planoPre).setPercentagemEntrada(this.getPercentagemEntrada());
			
			((PlanoPagamentoAPrazo)planoPre).setParcelas(null);
			
			getFachada().excluirPlanoPagamento(planoPre);
			
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			this.resetBB();
			this.setPlanos(null);
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
				PlanoPagamentoAPrazo planoPagamento = (PlanoPagamentoAPrazo)getFachada().consultarPlanoPagamentoPorId(new Long(getId()));
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
		        
		        this.setPercentagemEntrada(planoPagamento.getPercentagemEntrada());
		        Iterator it = planoPagamento.getParcelas().iterator();
				
		        if(this.getParcelas() == null){
					this.setParcelas(new TreeSet<ParcelaPlanoPagamentoAPrazo>());
				}

		        while(it.hasNext()){
		        	this.getParcelas().add((ParcelaPlanoPagamentoAPrazo)it.next());
		        }		       
		        this.setPlanoPagtoAPrazo((PlanoPagamentoAPrazo)planoPagamento);
				return "proxima";
			}else if (getDescricao() != null && !"".equals(getDescricao())){
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(PlanoPagamentoAPrazo.class);
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
						PlanoPagamentoAPrazo planoPagamento = (PlanoPagamentoAPrazo)getFachada().consultarPlanoPagamentoPorId(new Long(getId()));
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
				        
				        this.setPercentagemEntrada(planoPagamento.getPercentagemEntrada());
				        Iterator it = planoPagamento.getParcelas().iterator();
						
				        if(this.getParcelas() == null){
							this.setParcelas(new TreeSet<ParcelaPlanoPagamentoAPrazo>());
						}

				        while(it.hasNext()){
				        	this.getParcelas().add((ParcelaPlanoPagamentoAPrazo)it.next());
				        }		       
						return "proxima";
					}else{
						setExisteRegistros(true);
						this.setPlanos(col);
					}
				}
			}else{
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(PlanoPagamentoAPrazo.class);
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
			
			ParcelaPlanoPagamentoAPrazo parcela = new ParcelaPlanoPagamentoAPrazo();
			ParcelaPlanoPagamentoAPrazoPK parcelaPK = new ParcelaPlanoPagamentoAPrazoPK();
						
			parcelaPK.setPlano(null);
			
			parcela.setPk(parcelaPK);
			parcela.setPercentagemParcela(this.getPercentagemParcela());
			parcela.setQuantidadeDias(this.getQuantidadeDias());
			parcela.setDataProgramada(getDataProgramada());
			
			if(this.getParcelas() == null){
				this.setParcelas(new TreeSet<ParcelaPlanoPagamentoAPrazo>());
			}
			
			Iterator<ParcelaPlanoPagamentoAPrazo> itParcelas = this.getParcelas().iterator();
			
			while (itParcelas.hasNext()){
				ParcelaPlanoPagamentoAPrazo parcelaTmp = itParcelas.next();
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
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			getContextoApp().addMessage(null, msg);
		}
		this.setPercentagemParcela(new BigDecimal("0.00"));
		this.setQuantidadeDias(0);
		return "mesma";
	}
	
	public String excluirParcela(){
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			Map params = context.getExternalContext().getRequestParameterMap();  
			String param = (String)  params.get("idExcluir");
			
			Iterator<ParcelaPlanoPagamentoAPrazo> itParcelas = this.getParcelas().iterator();
			
			while (itParcelas.hasNext()){
				ParcelaPlanoPagamentoAPrazo parcelaTmp = itParcelas.next();
				if(parcelaTmp.getPk().getNumeroEntrada() == Integer.parseInt(param)){
					this.setPercentualRestante(this.getPercentualRestante().add(parcelaTmp.getPercentagemParcela()));
					this.getParcelas().remove(parcelaTmp);
					parcelaTmp.getPk().setPlano(this.getPlanoPagtoAPrazo());
					this.getParcelasRemovidas().add(parcelaTmp);
					break;
				}
			}
			if(this.getParcelas().size() == 0){
				numParcela = 0;
			}
		} catch (Exception e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao tentar excluir a Parcela!", "");
			getContextoApp().addMessage(null, msg);
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

	public PlanoPagamentoAPrazo getPlanoPagtoAPrazo() {
		return planoPagtoAPrazo;
	}

	public void setPlanoPagtoAPrazo(PlanoPagamentoAPrazo planoPagtoAPrazo) {
		this.planoPagtoAPrazo = planoPagtoAPrazo;
	}

	public List<FormaRecebimento> carregarFormas() {
		
		List<FormaRecebimento> formas = new ArrayList<FormaRecebimento>();
		try {

			FormaRecebimento frBoleto = getFachada().consultarFormaRecebimentoPorId(ConstantesFormaRecebimento.BOLETO);
			formas.add(frBoleto);
			FormaRecebimento frChequePre = getFachada().consultarFormaRecebimentoPorId(ConstantesFormaRecebimento.CHEQUE_PRE);
			formas.add(frChequePre);

		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return formas;
	}
}