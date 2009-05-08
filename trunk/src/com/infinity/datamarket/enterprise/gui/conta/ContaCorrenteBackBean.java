package com.infinity.datamarket.enterprise.gui.conta;
/**
 * 
 */
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.infinity.datamarket.comum.banco.Banco;
import com.infinity.datamarket.comum.conta.ContaCorrente;
import com.infinity.datamarket.comum.estoque.EntradaProduto;
import com.infinity.datamarket.comum.produto.Produto;
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
public class ContaCorrenteBackBean extends BackBean {
	private String id;

	private String idAgencia;
	private String numero;
	private String nome;
	private BigDecimal saldo;
	private String situacao;
	private ContaCorrente contaCorrente;
	private String idBanco;
	private Banco banco;
	private Collection<Banco> bancos;
	private Collection<ContaCorrente> contaCorrentes;
	
	private String idConsulta;
	private String idAgenciaConsulta;
	private String numeroConsulta;
	private String nomeConsulta;
	
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
			validarContaCorrente();
			ContaCorrente contaCorrente = new ContaCorrente();
			
			contaCorrente.setIdAgencia(this.idAgencia);
			contaCorrente.setNome(this.nome);
			contaCorrente.setNumero(this.numero);
			contaCorrente.setSaldo(this.saldo);
			contaCorrente.setSituacao(this.situacao);
			contaCorrente.setBanco(this.banco);
			if (getId()==null) contaCorrente.setId(getIdInc(ContaCorrente.class));
			getFachada().inserirContaCorrente(contaCorrente);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Opera��o Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Forma de recebimento j� Existente!", "");
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
		try {
				FacesContext context = FacesContext.getCurrentInstance();
				Map params = context.getExternalContext().getRequestParameterMap();            
				String param = (String)  params.get("id");
				if (param != null && !"".equals(param)){
					setId(param);
				}			
				if (getId() != null && !"".equals(getId())){
					ContaCorrente contaCorrente = getFachada().consultarContaCorrentePorID(getId());
					setContaCorrente(contaCorrente);
					resetConsultaBB();
					return "proxima";
				}
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(ContaCorrente.class);
				if (getIdAgenciaConsulta() != null && !"".equals(getIdAgenciaConsulta())){				
					filter.addProperty("idAgencia", getIdAgenciaConsulta());
				}
				if (getNumeroConsulta() != null && !"".equals(getNumeroConsulta())){				
					filter.addProperty("numero", getNumeroConsulta());
				}
				if (getNomeConsulta() != null && !"".equals(getNomeConsulta())){				
					filter.addProperty("nome", getNomeConsulta());
				}
				Collection col = getFachada().consultarContaCorrente(filter);
				if (col == null || col.size() == 0){
					setContaCorrentes(null);
					FacesContext ctx = FacesContext.getCurrentInstance();
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					ctx.addMessage(null, msg);	
					setExisteRegistros(false);
				}else if (col != null){
					if(col.size() == 1){
						ContaCorrente contaCorrente = (ContaCorrente)col.iterator().next();
						setContaCorrente(contaCorrente);
						resetConsultaBB();
						return "proxima";
					}else{
						setExisteRegistros(true);
						setContaCorrentes(col);
					}
				}
			}catch(ObjectNotFoundException e){
				setContaCorrentes(null);
				FacesContext ctx = FacesContext.getCurrentInstance();
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Nenhum Registro Encontrado", "");
				ctx.addMessage(null, msg);
				setExisteRegistros(false);
			}catch(Exception e){
				e.printStackTrace();
				FacesContext ctx = FacesContext.getCurrentInstance();
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Erro de Sistema!", "");
				ctx.addMessage(null, msg);
				setExisteRegistros(false);
			}
			resetConsultaBB();
			return "mesma";
	}

	public String alterar() {
		try {
			
			validarContaCorrente();
			
			ContaCorrente contaCorrente = new ContaCorrente();

			contaCorrente.setId(new Long(this.id));
			contaCorrente.setNumero(this.numero);
			contaCorrente.setIdAgencia(this.idAgencia);
			contaCorrente.setNome(this.nome);
			contaCorrente.setNumero(this.numero);
			contaCorrente.setSaldo(this.saldo);
			contaCorrente.setSituacao(this.situacao);
			Banco banco = getFachada().consultarBancoPorID(this.idBanco);
			contaCorrente.setBanco(banco);

			getFachada().alterarContaCorrente(contaCorrente);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Opera��o Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
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

	public String excluir() {
		try {
			ContaCorrente contaCorrente = new ContaCorrente();

			contaCorrente.setId(new Long(this.id));
			contaCorrente.setNumero(this.numero);
			contaCorrente.setIdAgencia(this.idAgencia);
			contaCorrente.setNome(this.nome);
			contaCorrente.setNumero(this.numero);
			contaCorrente.setSaldo(this.saldo);
			contaCorrente.setSituacao(this.situacao);
			contaCorrente.setBanco(this.banco);
			
			getFachada().excluirContaCorrente(contaCorrente);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Opera��o Realizada com Sucesso!", "");
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

	public void resetBB() {
		this.setId(null);
		this.setIdAgencia(null);
		this.setNumero(null);
		this.setNome(null);
		this.setSaldo(null);
		this.setSituacao(null);
		this.setBanco(null);
		this.setSituacao(Constantes.SIM);
		this.setIdBanco(null);
	}
	
	public void resetConsultaBB() {
		this.setIdConsulta(null);
		this.setIdAgenciaConsulta(null);
		this.setNumeroConsulta(null);
		this.setNomeConsulta(null);
	}
	public void validarContaCorrente() throws AppException {
		if(this.idAgencia == null || this.idAgencia.equals("0") || this.idAgencia.equals("")){
			throw new AppException("O Campo Ag�ncia � obrigat�rio!");
		}
		if(this.numero == null || this.numero.equals("0") || this.numero.equals("")){
			throw new AppException("O Campo Numero � obrigat�rio!");
		}
		if(this.nome == null || this.nome.equals("")){
			throw new AppException("O Campo Nome � obrigat�rio!");
		}
		if(this.saldo == null || this.saldo.equals("0") || this.saldo.equals("")){
			throw new AppException("O Campo Saldo � obrigat�rio!");
		}
	}
//	Bancos
	private List<Banco> carregarBancos() {
		
		List<Banco> bancos = null;
		try {
			bancos = (ArrayList<Banco>)getFachada().consultarTodosBancos();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return bancos;
	}

	public SelectItem[] getBancos() {
		SelectItem[] arrayBancos = null;
		try {
			List<Banco> bancos = carregarBancos();
			arrayBancos = new SelectItem[bancos.size()+1];
			int i = 0;
			arrayBancos[i++] = new SelectItem("0", "");
			for(Banco grupoTmp : bancos){
				SelectItem item = new SelectItem(grupoTmp.getId().toString(), grupoTmp.getDescricao());
				arrayBancos[i++] = item;
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return arrayBancos;

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
				setContaCorrentes(null);
			}
		}
	}


	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Collection getContaCorrentes() {
		return contaCorrentes;
	}

	public void setContaCorrentes(Collection contaCorrentes) {
		this.contaCorrentes = contaCorrentes;
	}

	public ContaCorrente getContaCorrente() {
		return contaCorrente;
	}

	public void setContaCorrente(ContaCorrente contaCorrente) {
		this.setId(contaCorrente.getId().toString());
		this.setNumero(contaCorrente.getNumero());
		this.setIdAgencia(contaCorrente.getIdAgencia());
		this.setNome(contaCorrente.getNome());
		this.setNumero(contaCorrente.getNumero());
		this.setSaldo(contaCorrente.getSaldo());
		this.setSituacao(contaCorrente.getSituacao());
		this.setBanco(contaCorrente.getBanco());
		if (contaCorrente.getBanco() != null) {
		    this.setIdBanco(contaCorrente.getBanco().getId().toString());
		} else {
			this.setIdBanco(null);
		}
	}

	public String getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(String idAgencia) {
		this.idAgencia = idAgencia;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getIdBanco() {
		return idBanco;
	}

	public void setIdBanco(String idBanco) {
		this.idBanco = idBanco;
	}

	public String getIdAgenciaConsulta() {
		return idAgenciaConsulta;
	}

	public void setIdAgenciaConsulta(String idAgenciaConsulta) {
		this.idAgenciaConsulta = idAgenciaConsulta;
	}

	public String getIdConsulta() {
		return idConsulta;
	}

	public void setIdConsulta(String idConsulta) {
		this.idConsulta = idConsulta;
	}

	public String getNomeConsulta() {
		return nomeConsulta;
	}

	public void setNomeConsulta(String nomeConsulta) {
		this.nomeConsulta = nomeConsulta;
	}

	public String getNumeroConsulta() {
		return numeroConsulta;
	}

	public void setNumeroConsulta(String numeroConsulta) {
		this.numeroConsulta = numeroConsulta;
	}

}