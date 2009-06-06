package com.infinity.datamarket.enterprise.gui.conta;

/**
 * 
 */
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.infinity.datamarket.comum.conta.ContaCorrente;
import com.infinity.datamarket.comum.conta.MovimentacaoBancaria;
import com.infinity.datamarket.comum.pagamento.FormaRecebimento;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter.IntervalObject;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Constantes;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

/**
 * @author alessandro
 * 
 */
public class MovimentacaoBancariaBackBean extends BackBean {
	private String id;
	private String idConta;
	private String idForma;
	private String tipo;
	private String numero;
	private Date data;
	private BigDecimal valor;
	private FormaRecebimento forma;
	  
	private Collection<ContaCorrente> contas;
	private Collection<MovimentacaoBancaria> movimentacaoBancarias;
	private MovimentacaoBancaria movimentacaoBancaria;
	
	private String idContaConsulta;
	private Date dataInicio;
	private Date dataFinal;
	
	private BigDecimal saldoAnterior;
	private BigDecimal saldoAtual;
	
	public BigDecimal getSaldoAnterior() {
		return saldoAnterior;
	}

	public void setSaldoAnterior(BigDecimal saldoAnterior) {
		this.saldoAnterior = saldoAnterior;
	}

	public BigDecimal getSaldoAtual() {
		return saldoAtual;
	}

	public void setSaldoAtual(BigDecimal saldoAtual) {
		this.saldoAtual = saldoAtual;
	}

	public String voltarConsulta() {
		consultar();
		return "voltar";
	}

	public String voltarMenu() {
		resetBB();
		return "voltar";
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	  

	public String inserir() {
		try {
			validarMovimentacaoBancaria();
			MovimentacaoBancaria movimentacaoBancaria = new MovimentacaoBancaria();
			if (getId()==null) movimentacaoBancaria.setId(getIdInc(MovimentacaoBancaria.class));
			
			ContaCorrente conta = getFachada().consultarContaCorrentePorID(this.idConta);
			
			movimentacaoBancaria.setConta(conta);
			movimentacaoBancaria.setTipo(this.tipo);
			movimentacaoBancaria.setNumero(this.numero);
			movimentacaoBancaria.setData(this.data);
			movimentacaoBancaria.setValor(this.valor);
			
			FormaRecebimento forma = getFachada().consultarFormaRecebimentoPorId(new Long(this.idForma));

			movimentacaoBancaria.setForma(forma);
			
			getFachada().inserirMovimentacaoBancaria(movimentacaoBancaria);
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

	public String consultar() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			PropertyFilter filter = new PropertyFilter();
			filter.setTheClass(MovimentacaoBancaria.class);
			if (getIdContaConsulta() != null && !"".equals(getIdContaConsulta())){				
				filter.addProperty("conta.id", new Long(getIdContaConsulta()));
			}
			if (getDataInicio() != null && !getDataInicio().equals("") && getDataFinal() != null && !getDataFinal().equals("")) {
				filter.addPropertyInterval("data",getDataInicio(), IntervalObject.MAIOR_IGUAL);
				
				Date dataFinal = new Date(getDataFinal().getTime());
				
				dataFinal.setDate(dataFinal.getDate()+1);
				
				filter.addPropertyInterval("data",dataFinal, IntervalObject.MENOR_IGUAL);
			}
			Collection col = getFachada().consultarMovimentacaoBancaria(filter);
			
			if (col == null || col.size() == 0){
				setMovimentacaoBancarias(null);
				FacesContext ctx = FacesContext.getCurrentInstance();
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Nenhum Registro Encontrado", "");
				ctx.addMessage(null, msg);	
				setExisteRegistros(false);
			}else if (col != null){
				setExisteRegistros(true);
				setMovimentacaoBancarias(col);
				Set<MovimentacaoBancaria> colMovBanc = new TreeSet<MovimentacaoBancaria>();
				Iterator it = col.iterator();
				while(it.hasNext()){
					MovimentacaoBancaria movimentacao = (MovimentacaoBancaria) it.next();
					colMovBanc.add(movimentacao);					
				}
				MovimentacaoBancaria[] arrayMovBanc = (MovimentacaoBancaria[])colMovBanc.toArray();
				this.setSaldoAnterior(arrayMovBanc[0].getSaldoAnteriorConta());
				this.setSaldoAtual(arrayMovBanc[arrayMovBanc.length-1].getSaldoAnteriorConta());
			}
		}catch(ObjectNotFoundException e){
			setMovimentacaoBancarias(null);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Nenhum Registro Encontrado", "");
			context.addMessage(null, msg);
			setExisteRegistros(false);
		}catch(Exception e){
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			context.addMessage(null, msg);
			setExisteRegistros(false);
		}
		return "mesma";
	}

	public String alterar() {
		try {
			
			validarMovimentacaoBancaria();
			
			MovimentacaoBancaria movimentacaoBancaria = new MovimentacaoBancaria();

			movimentacaoBancaria.setId(new Long(this.id));
			
			ContaCorrente conta = getFachada().consultarContaCorrentePorID(this.idConta);
			
			movimentacaoBancaria.setConta(conta);
			movimentacaoBancaria.setTipo(this.tipo);
			movimentacaoBancaria.setNumero(this.numero);
			movimentacaoBancaria.setData(this.data);
			movimentacaoBancaria.setValor(this.valor);
			
			FormaRecebimento forma = getFachada().consultarFormaRecebimentoPorId(new Long(this.idForma));

			movimentacaoBancaria.setForma(forma);
			getFachada().alterarMovimentacaoBancaria(movimentacaoBancaria);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
		} catch (AppException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
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

	public String excluir() {
		try {
			
			validarMovimentacaoBancaria();
			
			MovimentacaoBancaria movimentacaoBancaria = new MovimentacaoBancaria();

			movimentacaoBancaria.setId(new Long(this.id));
			
			ContaCorrente conta = getFachada().consultarContaCorrentePorID(this.idConta);
			
			movimentacaoBancaria.setConta(conta);
			movimentacaoBancaria.setTipo(this.tipo);
			movimentacaoBancaria.setNumero(this.numero);
			movimentacaoBancaria.setData(this.data);
			movimentacaoBancaria.setValor(this.valor);
			
			FormaRecebimento forma = getFachada().consultarFormaRecebimentoPorId(new Long(this.idForma));

			movimentacaoBancaria.setForma(forma);
			
			getFachada().excluirMovimentacaoBancaria(movimentacaoBancaria);
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
	
	public void validarMovimentacaoBancaria() throws AppException {
		if(this.idConta == null || this.idConta.equals("0") || this.idConta.equals("")){
			throw new AppException("O Campo conta é obrigatório!");
		}
		if(this.numero == null || this.numero.equals("0") || this.numero.equals("")){
			throw new AppException("O Campo Numero é obrigatório!");
		}
		if(this.data == null || this.data.equals("")){
			throw new AppException("O Campo data é obrigatório!");
		}
		if(this.valor == null || this.valor.equals("0") || this.valor.longValue() <= 0 || this.valor.equals("")){
			throw new AppException("O Campo valor é obrigatório ou maior que zero!");
		}
		if(this.idForma == null || this.idForma.equals("0") || this.idForma.equals("")){
			throw new AppException("O Campo forma é obrigatório!");
		}
	}

	public void resetBB() {
		this.setId(null);
		this.setIdConta(null);
		this.setTipo(Constantes.TIPO_OPERACAO_CREDITO);
		this.setNumero(null);
		this.setData(null);
		this.setValor(null);
		this.setIdForma(null);
	}

	public void resetConsultaBB() {
		this.setIdContaConsulta(null);
		this.setDataInicio(null);
		this.setDataFinal(null);
	}
//	Contas
	private List<ContaCorrente> carregarContas() {
		
		List<ContaCorrente> contas = null;
		try {
			contas = (ArrayList<ContaCorrente>)getFachada().consultarTodosContaCorrente();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return contas;
	}

	public SelectItem[] getContas() {
		SelectItem[] arrayContas = null;
		try {
			List<ContaCorrente> contas = carregarContas();
			arrayContas = new SelectItem[contas.size()+1];
			int i = 0;
			arrayContas[i++] = new SelectItem("0", "");
			for(ContaCorrente contaTmp : contas){
				SelectItem item = new SelectItem(contaTmp.getId().toString(), contaTmp.getBanco().getDescricao()+ " - " + contaTmp.getIdAgencia()+" - "+ contaTmp.getNumero()+" - "+contaTmp.getNome());
				arrayContas[i++] = item;
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return arrayContas;

	}
	
//	 Formas
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
				setMovimentacaoBancarias(null);
			}
		}
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public FormaRecebimento getForma() {
		return forma;
	}

	public void setForma(FormaRecebimento forma) {
		this.forma = forma;
	}

	public String getIdConta() {
		return idConta;
	}

	public void setIdConta(String idConta) {
		this.idConta = idConta;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Collection<MovimentacaoBancaria> getMovimentacaoBancarias() {
		return movimentacaoBancarias;
	}

	public void setMovimentacaoBancarias(
			Collection<MovimentacaoBancaria> movimentacaoBancarias) {
		this.movimentacaoBancarias = movimentacaoBancarias;
	}

	public String getIdForma() {
		return idForma;
	}

	public void setIdForma(String idForma) {
		this.idForma = idForma;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getIdContaConsulta() {
		return idContaConsulta;
	}

	public void setIdContaConsulta(String idContaConsulta) {
		this.idContaConsulta = idContaConsulta;
	}

	public MovimentacaoBancaria getMovimentacaoBancaria() {
		return movimentacaoBancaria;
	}

	public void setMovimentacaoBancaria(MovimentacaoBancaria movimentacaoBancaria) {
		this.setId(movimentacaoBancaria.getId().toString());
		this.setIdConta(movimentacaoBancaria.getConta().getId().toString());
		this.setTipo(movimentacaoBancaria.getTipo());
		this.setNumero(movimentacaoBancaria.getNumero());
		this.setData(movimentacaoBancaria.getData());
		this.setValor(movimentacaoBancaria.getValor());
		this.setIdForma(movimentacaoBancaria.getForma().getId().toString());
	}


	

}
