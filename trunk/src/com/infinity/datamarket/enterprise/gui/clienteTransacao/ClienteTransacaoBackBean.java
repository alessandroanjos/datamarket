package com.infinity.datamarket.enterprise.gui.clienteTransacao;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.infinity.datamarket.comum.cliente.Cliente;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.transacao.ClienteTransacao;
import com.infinity.datamarket.comum.util.Util;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

public class ClienteTransacaoBackBean extends BackBean {
	
	ClienteTransacao clienteTransacao;
	String id;
	String cpfCnpj;
	String tipoPessoa;
	String idTipoPessoa;
	Date dataCadastro;
	String nomeCliente;
//	String nomeFantasia;
	String razaoSocial;
	String inscricaoEstadual;
	String inscricaoMunicipal;
	String logradouro;
	String numero;
	String complemento;
	String bairro;
	String cidade;
	String estado;
	String cep;
	String fone;
	String celular;
	String pessoaContato;
	String referenciaBancaria;
	String email;
	
	Collection clientesTransacao;
	
	SelectItem[] listaTipoPessoa;
	
	SelectItem[] listaUf;
	

	public void resetBB(){
		this.setId(null);
		this.setNomeCliente(null);
		this.setIdTipoPessoa(Cliente.PESSOA_FISICA);
		this.setCpfCnpj(null);
		this.setRazaoSocial(null);
//		this.setNomeFantasia(null);
		this.setInscricaoEstadual(null);
		this.setInscricaoMunicipal(null);
		this.setLogradouro(null);
		this.setNumero(null);
		this.setComplemento(null);
		this.setBairro(null);
		this.setCidade(null);
		this.setEstado(null);
		this.setCep(null);
		this.setFone(null);
		this.setCelular(null);
		this.setPessoaContato(null);
		this.setDataCadastro(new Date(System.currentTimeMillis()));
		this.setReferenciaBancaria(null);
		this.setEmail(null);
	}
	
	public void consultar(){
		try {
			PropertyFilter filter = new PropertyFilter();

			filter.setTheClass(ClienteTransacao.class);
			
			if(this.getCpfCnpj() != null && !"".equals(this.getCpfCnpj())){
				String cpfCnpj = this.getCpfCnpj();
				cpfCnpj = cpfCnpj.replace(".", "");
				cpfCnpj = cpfCnpj.replace("/", "");
				cpfCnpj = cpfCnpj.replace("-", "");
				filter.addProperty("cpfCnpj", cpfCnpj);	
			}
			
			filter.addProperty("tipoPessoa", this.getIdTipoPessoa());
			
			if(this.getIdTipoPessoa().equals(Cliente.PESSOA_FISICA)){
				filter.addProperty("nomeCliente", this.getNomeCliente());	
			}else{
				filter.addProperty("razaoSocial", this.getNomeCliente());
			}
			
			Collection col = getFachada().consultarClienteTransacao(filter);
			
			if (col == null || col.size() == 0){
				setExisteRegistros(false);
				this.setClientesTransacao(null);
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Nenhum Registro Encontrado", "");
				getContextoApp().addMessage(null, msg);					
			}else if (col != null){
				setExisteRegistros(true);
				Iterator it = col.iterator();
				while(it.hasNext()){
					ClienteTransacao cliTmp = (ClienteTransacao)it.next();
					if(cliTmp != null){
						cliTmp.setCpfCnpj(Util.formataCpfCnpj(cliTmp.getCpfCnpj()));
					}
				}
				this.setClientesTransacao(col);
			}
		} catch (ObjectNotFoundException e){
				setExisteRegistros(false);
			this.setClientesTransacao(null);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Nenhum Registro Encontrado", "");
			getContextoApp().addMessage(null, msg);			
		} catch (Exception e){
			setExisteRegistros(false);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
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
		return Util.getUFs();
	}

	public void setListaTipoPessoa(SelectItem[] listaTipoPessoa) {
		this.listaTipoPessoa = listaTipoPessoa;
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
				setClientesTransacao(null);
			}
		}
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
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

	public Collection getClientesTransacao() {
		return clientesTransacao;
	}

	public void setClientesTransacao(Collection clientesTransacao) {
		this.clientesTransacao = clientesTransacao;
	}

	public ClienteTransacao getClienteTransacao() {
		return clienteTransacao;
	}

	public void setClienteTransacao(ClienteTransacao clienteTransacao) {
		this.clienteTransacao = clienteTransacao;
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

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
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

	public String getReferenciaBancaria() {
		return referenciaBancaria;
	}

	public void setReferenciaBancaria(String referenciaBancaria) {
		this.referenciaBancaria = referenciaBancaria;
	}

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
//
//	public String getNomeFantasia() {
//		return nomeFantasia;
//	}
//
//	public void setNomeFantasia(String nomeFantasia) {
//		this.nomeFantasia = nomeFantasia;
//	}

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}
}