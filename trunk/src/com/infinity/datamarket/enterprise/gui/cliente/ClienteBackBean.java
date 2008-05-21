package com.infinity.datamarket.enterprise.gui.cliente;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.infinity.datamarket.comum.cliente.Cliente;
import com.infinity.datamarket.comum.componente.Componente;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

public class ClienteBackBean extends BackBean {
	
	public ClienteBackBean(){
		resetBB();
	}
	
	String id;
	String nomeCliente;
	String tipoPessoa = new String(Cliente.PESSOA_FISICA);
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
//	BigDecimal valorLimiteDisponivel;
	Date dataNascimento;
	Date dataCadastro = new Date(System.currentTimeMillis());
	
	Collection clientes;

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

//	public BigDecimal getValorLimiteDisponivel() {
//		return valorLimiteDisponivel;
//	}
//
//	public void setValorLimiteDisponivel(BigDecimal valorLimiteDisponivel) {
//		this.valorLimiteDisponivel = valorLimiteDisponivel;
//	}
	
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

	public String resetBB(){
		this.setId(null);
		this.setNomeCliente(null);
		this.setTipoPessoa(Cliente.PESSOA_FISICA);
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
				Cliente cliente = getFachada().consultarClientePorPK(new Long(getId()));
				this.setId(cliente.getId().toString());
				this.setNomeCliente(cliente.getNomeCliente());
				this.setTipoPessoa(cliente.getTipoPessoa());
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
//				this.setValorLimiteDisponivel(cliente.getValorLimiteDisponivel());
				this.setDataCadastro(cliente.getDataCadastro());
				this.setDataNascimento(cliente.getDataNascimento());
				
				return "proxima";
			}else if ((this.getNomeCliente() != null && !"".equals(this.getNomeCliente()))
					|| (this.getCpfCnpj() != null && !"".equals(this.getCpfCnpj()))
					|| (this.getTipoPessoa() != null && !"".equals(this.getTipoPessoa()))){
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(Cliente.class);
				filter.addProperty("tipoPessoa", this.getTipoPessoa());
				if(this.getTipoPessoa().equals(Cliente.PESSOA_FISICA)){
					filter.addProperty("nomeCliente", this.getNomeCliente());	
				}else{
					filter.addProperty("razaoSocial", this.getNomeCliente());
				}
				if(this.getCpfCnpj() != null && !"".equals(this.getCpfCnpj())){
					filter.addProperty("cpfCnpj", this.getCpfCnpj());	
				}
				Collection col = getFachada().consultarCliente(filter);
				if (col == null || col.size() == 0){
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
						this.setTipoPessoa(cliente.getTipoPessoa());
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
//						this.setValorLimiteDisponivel(cliente.getValorLimiteDisponivel());
						this.setDataCadastro(cliente.getDataCadastro());
						this.setDataNascimento(cliente.getDataNascimento());
						
						return "proxima";
					}else{
						this.setClientes(col);
					}
				}
			}else{
				this.setClientes(getFachada().consultarTodosClientes());
			}
		}catch(ObjectNotFoundException e){
			this.setClientes(null);
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
		resetBB();
		return "mesma";
	}

	public String inserir(){
		try {
			Cliente cliente = preencheCliente("I");
			
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
		} catch (Exception e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		resetBB();
		return "mesma";
	}
	
	public String alterar(){
		try {		
			Cliente cliente = preencheCliente("A");			
								
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
			Cliente cliente = new Cliente();
			
			cliente.setId(new Long(this.getId()));
			
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
		resetBB();
		return "mesma";
	}
	
	public Cliente preencheCliente(String acao){
		Cliente cliente = new Cliente();
		
		cliente.setId(new Long(this.getId()));
		cliente.setNomeCliente(this.getNomeCliente());
		cliente.setTipoPessoa(this.getTipoPessoa());
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
//		cliente.setValorLimiteDisponivel(this.getValorLimiteDisponivel());
		cliente.setDataNascimento(this.getDataNascimento());
		if(acao.equals("I")){
			cliente.setDataCadastro(new Date(System.currentTimeMillis()));	
		}		
		return cliente;
	}
}