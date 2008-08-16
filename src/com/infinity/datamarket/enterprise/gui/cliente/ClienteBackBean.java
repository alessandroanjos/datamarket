package com.infinity.datamarket.enterprise.gui.cliente;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import com.infinity.datamarket.comum.cliente.Cliente;
import com.infinity.datamarket.comum.fornecedor.Fornecedor;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.SistemaException;
import com.infinity.datamarket.comum.util.Util;
import com.infinity.datamarket.enterprise.gui.util.BackBean;
import com.sun.jimi.core.encoder.jpg.util;

public class ClienteBackBean extends BackBean {
	
	public ClienteBackBean(){
		resetBB();
		setDataCadastro(new Date(System.currentTimeMillis()));
	}
	
	String id;
	String nomeCliente;
	String idTipoPessoa = new String(Fornecedor.PESSOA_FISICA);
	String tipoPessoa;
	String cpfCnpj;
	String razaoSocial;
	String nomeFantasia;
	String inscricaoEstadual;
	String inscricaoMunicipal;
	String logradouro;
	String numero;
	String complemento;
	String bairro;
	String cidade;
	String estado;
	String cep;
	String foneResidencial;
	String foneComercial;
	String foneCelular;
	String pessoaContato;
	String foneContato;
	BigDecimal valorLimiteCompras;
	BigDecimal valorLimiteDisponivel;
	Date dataNascimento;
	Date dataCadastro = new Date(System.currentTimeMillis());
	String referenciaComercial;
	
	Collection clientes;
	
	SelectItem[] listaTipoPessoa;
	
	SelectItem[] listaUf;
	
	public String getIdTipoPessoa() {
		return idTipoPessoa;
	}

	public void setIdTipoPessoa(String idTipoPessoa) {
		this.idTipoPessoa = idTipoPessoa;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public Collection getClientes() {
		return clientes;
	}

	public void setClientes(Collection clientes) {
		this.clientes = clientes;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFoneCelular() {
		return foneCelular;
	}

	public void setFoneCelular(String foneCelular) {
		this.foneCelular = foneCelular;
	}

	public String getFoneComercial() {
		return foneComercial;
	}

	public void setFoneComercial(String foneComercial) {
		this.foneComercial = foneComercial;
	}

	public String getFoneContato() {
		return foneContato;
	}

	public void setFoneContato(String foneContato) {
		this.foneContato = foneContato;
	}

	public String getFoneResidencial() {
		return foneResidencial;
	}

	public void setFoneResidencial(String foneResidencial) {
		this.foneResidencial = foneResidencial;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public String getInscricaoMunicipal() {
		return inscricaoMunicipal;
	}

	public void setInscricaoMunicipal(String inscricaoMunicipal) {
		this.inscricaoMunicipal = inscricaoMunicipal;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getPessoaContato() {
		return pessoaContato;
	}

	public void setPessoaContato(String pessoaContato) {
		this.pessoaContato = pessoaContato;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public BigDecimal getValorLimiteCompras() {
		return valorLimiteCompras;
	}

	public void setValorLimiteCompras(BigDecimal valorLimiteCompras) {
		this.valorLimiteCompras = valorLimiteCompras;
	}

	public BigDecimal getValorLimiteDisponivel() {
		return valorLimiteDisponivel;
	}

	public void setValorLimiteDisponivel(BigDecimal valorLimiteDisponivel) {
		this.valorLimiteDisponivel = valorLimiteDisponivel;
	}
	
	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public void resetBB(){
		this.setId(null);
		this.setNomeCliente(null);
		this.setIdTipoPessoa(Cliente.PESSOA_FISICA);
		this.setCpfCnpj(null);
		this.setRazaoSocial(null);
		this.setNomeFantasia(null);
		this.setInscricaoEstadual(null);
		this.setInscricaoMunicipal(null);
		this.setLogradouro(null);
		this.setNumero(null);
		this.setComplemento(null);
		this.setBairro(null);
		this.setCidade(null);
		this.setEstado(null);
		this.setCep(null);
		this.setFoneResidencial(null);
		this.setFoneComercial(null);
		this.setFoneCelular(null);
		this.setPessoaContato(null);
		this.setFoneContato(null);
		this.setValorLimiteCompras(null);
//		this.setValorLimiteDisponivel(null);
		this.setDataCadastro(new Date(System.currentTimeMillis()));
		this.setDataNascimento(null);
		this.setReferenciaComercial(null);
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
				Cliente cliente = getFachada().consultarClientePorPK(new Long(getId()));
				this.setId(cliente.getId().toString());
				this.setNomeCliente(cliente.getNomeCliente());
				this.setIdTipoPessoa(cliente.getTipoPessoa());
				this.setCpfCnpj(cliente.getCpfCnpj());
				this.setRazaoSocial(cliente.getRazaoSocial());
				this.setNomeFantasia(cliente.getNomeFantasia());
				this.setInscricaoEstadual(cliente.getInscricaoEstadual());
				this.setInscricaoMunicipal(cliente.getInscricaoMunicipal());
				this.setLogradouro(cliente.getLogradouro());
				this.setNumero(cliente.getNumero());
				this.setComplemento(cliente.getComplemento());
				this.setBairro(cliente.getBairro());
				this.setCidade(cliente.getCidade());
				this.setEstado(cliente.getEstado());
				this.setCep(cliente.getCep());
				this.setFoneResidencial(cliente.getFoneResidencial());
				this.setFoneComercial(cliente.getFoneComercial());
				this.setFoneCelular(cliente.getFoneCelular());
				this.setPessoaContato(cliente.getPessoaContato());
				this.setFoneContato(cliente.getFoneContato());
				this.setValorLimiteCompras(cliente.getValorLimiteCompras());
				this.setValorLimiteDisponivel(cliente.getValorLimiteDisponivel());
				this.setDataCadastro(cliente.getDataCadastro());
				this.setDataNascimento(cliente.getDataNascimento());
				this.setReferenciaComercial(cliente.getReferenciaComercial());
				
				return "proxima";
			}else if ((this.getNomeCliente() != null && !"".equals(this.getNomeCliente()))
					|| (this.getCpfCnpj() != null && !"".equals(this.getCpfCnpj()))
					|| (this.getIdTipoPessoa() != null && !"".equals(this.getIdTipoPessoa()))){
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(Cliente.class);
				filter.addProperty("tipoPessoa", this.getIdTipoPessoa());
				if(this.getIdTipoPessoa().equals(Cliente.PESSOA_FISICA)){
					filter.addProperty("nomeCliente", this.getNomeCliente());	
				}else{
					filter.addProperty("razaoSocial", this.getNomeCliente());
				}
				if(this.getCpfCnpj() != null && !"".equals(this.getCpfCnpj())){
					filter.addProperty("cpfCnpj", this.getCpfCnpj());	
				}
				Collection col = getFachada().consultarCliente(filter);
				if (col == null || col.size() == 0){
					setExisteRegistros(false);
					this.setClientes(col);
					FacesContext ctx = FacesContext.getCurrentInstance();
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					ctx.addMessage(null, msg);					
				}else if (col != null){
					if(col.size() == 1){
						Cliente cliente = (Cliente)col.iterator().next();
						this.setId(cliente.getId().toString());
						this.setNomeCliente(cliente.getNomeCliente());
						this.setIdTipoPessoa(cliente.getTipoPessoa());
						this.setCpfCnpj(cliente.getCpfCnpj());
						this.setRazaoSocial(cliente.getRazaoSocial());
						this.setNomeFantasia(cliente.getNomeFantasia());
						this.setInscricaoEstadual(cliente.getInscricaoEstadual());
						this.setInscricaoMunicipal(cliente.getInscricaoMunicipal());
						this.setLogradouro(cliente.getLogradouro());
						this.setNumero(cliente.getNumero());
						this.setComplemento(cliente.getComplemento());
						this.setBairro(cliente.getBairro());
						this.setCidade(cliente.getCidade());
						this.setEstado(cliente.getEstado());
						this.setCep(cliente.getCep());
						this.setFoneResidencial(cliente.getFoneResidencial());
						this.setFoneComercial(cliente.getFoneComercial());
						this.setFoneCelular(cliente.getFoneCelular());
						this.setPessoaContato(cliente.getPessoaContato());
						this.setFoneContato(cliente.getFoneContato());
						this.setValorLimiteCompras(cliente.getValorLimiteCompras());
						this.setValorLimiteDisponivel(cliente.getValorLimiteDisponivel());
						this.setDataCadastro(cliente.getDataCadastro());
						this.setDataNascimento(cliente.getDataNascimento());
						this.setReferenciaComercial(cliente.getReferenciaComercial());
						
						return "proxima";
					}else{
						setExisteRegistros(true);
						this.setClientes(col);
					}
				}
			}else{
				Collection c = getFachada().consultarTodosClientes();
				if(c != null && c.size() > 0){
					setExisteRegistros(true);
					this.setClientes(c);	
				}else{
					setExisteRegistros(false);
					this.setClientes(null);
				}
				
			}
		}catch(ObjectNotFoundException e){
			setExisteRegistros(false);
			this.setClientes(null);
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
//		resetBB();
		return "mesma";
	}

	public String inserir(){
		try {
			
			validarCampos(this.getIdTipoPessoa());
			
			Cliente cliente = preencheCliente(INSERIR);
			
			getFachada().inserirCliente(cliente);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Cliente já Existente!", "");
			ctx.addMessage(null, msg);
		} catch (SistemaException e) {
			if (e.getCause()!=null 
					&& e.getCause().getCause()!=null 
					&& e.getCause().getCause().getCause() != null 
					&& e.getCause().getCause().getCause() instanceof ConstraintViolationException) {
				FacesContext ctx = FacesContext.getCurrentInstance();
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Cliente já cadastrado!", "");
				ctx.addMessage(null, msg);
			} else {
				FacesContext ctx = FacesContext.getCurrentInstance();
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Erro de Sistema!", "");
				ctx.addMessage(null, msg);
			}
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
			
			validarCampos(this.getIdTipoPessoa());
			
			Cliente cliente = preencheCliente(ALTERAR);			
								
			getFachada().alterarCliente(cliente);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
			this.setClientes(null);
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
			Cliente cliente = preencheCliente(EXCLUIR);
			
			cliente.setId(new Long(this.getId()));
			
			getFachada().excluirCliente(cliente);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
			this.setClientes(null);
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
	
	public Cliente preencheCliente(String acao){
		Cliente cliente = new Cliente();
		

		cliente.setNomeCliente(this.getNomeCliente());
		cliente.setTipoPessoa(this.getIdTipoPessoa());
		cliente.setCpfCnpj(this.getCpfCnpj());
		cliente.setRazaoSocial(this.getRazaoSocial());
		cliente.setNomeFantasia(this.getNomeFantasia());
		cliente.setInscricaoEstadual(this.getInscricaoEstadual());
		cliente.setInscricaoMunicipal(this.getInscricaoMunicipal());
		cliente.setLogradouro(this.getLogradouro());
		cliente.setNumero(this.getNumero());
		cliente.setComplemento(this.getComplemento());
		cliente.setBairro(this.getBairro());
		cliente.setCidade(this.getCidade());
		cliente.setEstado(this.getEstado());
		cliente.setCep(this.getCep());
		cliente.setFoneResidencial(this.getFoneResidencial());
		cliente.setFoneComercial(this.getFoneComercial());
		cliente.setFoneCelular(this.getFoneCelular());
		cliente.setPessoaContato(this.getPessoaContato());
		cliente.setFoneContato(this.getFoneContato());
		cliente.setValorLimiteCompras(this.getValorLimiteCompras());
		cliente.setDataNascimento(this.getDataNascimento());
		cliente.setReferenciaComercial(this.getReferenciaComercial());
		
		if(acao.equals(INSERIR)){
			cliente.setDataCadastro(new Date(System.currentTimeMillis()));
			if(this.getValorLimiteCompras() == null){
				cliente.setValorLimiteDisponivel(new BigDecimal("0"));
				cliente.setValorLimiteCompras(new BigDecimal("0"));
			}	
		}else if(acao.equals(ALTERAR)){
			cliente.setId(new Long(getId()));
			if(this.getIdTipoPessoa().equals(Cliente.PESSOA_FISICA)){
				cliente.setRazaoSocial(null);
				cliente.setNomeFantasia(null);
				cliente.setInscricaoEstadual(null);
				cliente.setInscricaoMunicipal(null);

			}else if(this.getIdTipoPessoa().equals(Cliente.PESSOA_FISICA)){
				cliente.setNomeCliente(null);
				cliente.setDataNascimento(null);
			}
			cliente.setValorLimiteDisponivel(this.getValorLimiteDisponivel());
		}
		return cliente;
	}
	
	public String voltarConsulta(){
		consultar();
		return "voltar";
	}
	public String voltarMenu(){
		resetBB();
		return "voltar";
	}
	
	public SelectItem[] getListaTipoPessoa() {
		SelectItem[] lista = new SelectItem[2];
		lista[0] = new SelectItem("F", "Física");
		lista[1] = new SelectItem("J", "Jurídica");
		if(this.getIdTipoPessoa() == null || this.getIdTipoPessoa().equals("")){
			this.setIdTipoPessoa(Cliente.PESSOA_FISICA);
		}
		return lista;
	}

	public void setListaUf(SelectItem[] listaUf) {
		this.listaUf = listaUf;
	}
	
	public SelectItem[] getListaUf() {
		return Util.getListaSelectItemUf();
	}

	public void setListaTipoPessoa(SelectItem[] listaTipoPessoa) {
		this.listaTipoPessoa = listaTipoPessoa;
	}
	
	public void validarCampos(String tipoPessoa) throws AppException{
		if(tipoPessoa.equals(Fornecedor.PESSOA_FISICA)){
			if(this.getNomeCliente() == null || this.getNomeCliente().equals("")){
				throw new AppException("O Nome do Cliente é obrigatório.");
			}
		}else if(tipoPessoa.equals(Fornecedor.PESSOA_JURIDICA)){
			if(this.getRazaoSocial() == null || this.getRazaoSocial().equals("")){
				throw new AppException("O campo Razão Social é obrigatório.");
			}
			if(this.getNomeFantasia() == null || this.getNomeFantasia().equals("")){
				throw new AppException("O campo Nome Fantasia é obrigatório.");
			}
			if(this.getInscricaoEstadual() == null || this.getInscricaoEstadual().equals("")){
				throw new AppException("O campo Inscrição Estadual é obrigatório.");
			}
			if(this.getInscricaoMunicipal() == null || this.getInscricaoMunicipal().equals("")){
				throw new AppException("O campo Inscrição Municipal é obrigatório.");
			}
		}		
	}

	public String getReferenciaComercial() {
		return referenciaComercial;
	}

	public void setReferenciaComercial(String referenciaComercial) {
		this.referenciaComercial = referenciaComercial;
	}
	/**
	 * @param init the init to set
	 */
	public void setInit(HtmlForm init) {
		FacesContext context = FacesContext.getCurrentInstance();
		Map params = context.getExternalContext().getRequestParameterMap();            
		String param = (String)  params.get(ACAO);
		if (param != null && VALOR_ACAO.equals(param)){
			resetBB();
			setClientes(null);
		}
	}
}