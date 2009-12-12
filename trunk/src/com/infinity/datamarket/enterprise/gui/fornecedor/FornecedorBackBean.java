package com.infinity.datamarket.enterprise.gui.fornecedor;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.infinity.datamarket.comum.fornecedor.Fornecedor;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Util;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

public class FornecedorBackBean extends BackBean {
	
	public FornecedorBackBean(){
//		resetBB();
		setDataCadastro(new Date(System.currentTimeMillis()));
	}
	
	String id;
	String nomeFornecedor;
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
	Date dataCadastro = new Date(System.currentTimeMillis());
	
	SelectItem[] listaTipoPessoa;
	
	SelectItem[] listaUf;
	
	Collection fornecedores;

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

	public String getIdTipoPessoa() {
		return idTipoPessoa;
	}

	public void setIdTipoPessoa(String idTipoPessoa) {
		this.idTipoPessoa = idTipoPessoa;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		System.out.println(dataCadastro);
		this.dataCadastro = dataCadastro;
	}

	public void resetBB(){
		this.setId(null);
		this.setIdTipoPessoa(Fornecedor.PESSOA_FISICA);
		this.setNomeFornecedor(null);
		this.setNomeFantasia(null);
//		this.setDataCadastro(null);
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
		//this.setDataCadastro(null);
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
				Fornecedor fornecedor = getFachada().consultaFornecedorPorId(new Long(getId()));
				this.setId(fornecedor.getId().toString());
				this.setNomeFornecedor(fornecedor.getNomeFornecedor());
				this.setIdTipoPessoa(fornecedor.getTipoPessoa());
				this.setCpfCnpj(Util.formataCpfCnpj(fornecedor.getCpfCnpj()));
				this.setRazaoSocial(fornecedor.getRazaoSocial());
				this.setNomeFantasia(fornecedor.getNomeFantasia());
				this.setInscricaoEstadual(fornecedor.getInscricaoEstadual());
				this.setInscricaoMunicipal(fornecedor.getInscricaoMunicipal());
				this.setLogradouro(fornecedor.getLogradouro());
				this.setNumero(fornecedor.getNumero());
				this.setComplemento(fornecedor.getComplemento());
				this.setBairro(fornecedor.getBairro());
				this.setCidade(fornecedor.getCidade());
				this.setEstado(fornecedor.getEstado());
				this.setCep(fornecedor.getCep());
				this.setFoneResidencial(fornecedor.getFoneResidencial());
				this.setFoneComercial(fornecedor.getFoneComercial());
				this.setFoneCelular(fornecedor.getFoneCelular());
				this.setPessoaContato(fornecedor.getPessoaContato());
				this.setFoneContato(fornecedor.getFoneContato());
				this.setDataCadastro(fornecedor.getDataCadastro());
				setDataSistema(this.getDataCadastro());
				
				return "proxima";
			}else if ((this.getNomeFornecedor() != null && !"".equals(this.getNomeFornecedor()))
					|| (this.getCpfCnpj() != null && !"".equals(this.getCpfCnpj()))
					|| (this.getIdTipoPessoa() != null && !"".equals(this.getIdTipoPessoa()))){
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(Fornecedor.class);
				filter.addProperty("tipoPessoa", this.getIdTipoPessoa());
				if(this.getIdTipoPessoa().equals(Fornecedor.PESSOA_FISICA)){
					filter.addProperty("nomeFornecedor", this.getNomeFornecedor());	
				}else{
					filter.addProperty("razaoSocial", this.getNomeFornecedor());
				}
				if(this.getCpfCnpj() != null && !"".equals(this.getCpfCnpj())){
					filter.addProperty("cpfCnpj", this.getCpfCnpj());	
				}
				Collection col = getFachada().consultarFornecedor(filter);
				if (col == null || col.size() == 0){
					setExisteRegistros(false);
					this.setFornecedores(col);
					
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					getContextoApp().addMessage(null, msg);					
				}else if (col != null){
					if(col.size() == 1){
						Fornecedor fornecedor = (Fornecedor)col.iterator().next();
						this.setId(fornecedor.getId().toString());
						this.setNomeFornecedor(fornecedor.getNomeFornecedor());
						this.setIdTipoPessoa(fornecedor.getTipoPessoa());
						this.setCpfCnpj(Util.formataCpfCnpj(fornecedor.getCpfCnpj()));
						this.setRazaoSocial(fornecedor.getRazaoSocial());
						this.setNomeFantasia(fornecedor.getNomeFantasia());
						this.setInscricaoEstadual(fornecedor.getInscricaoEstadual());
						this.setInscricaoMunicipal(fornecedor.getInscricaoMunicipal());
						this.setLogradouro(fornecedor.getLogradouro());
						this.setNumero(fornecedor.getNumero());
						this.setComplemento(fornecedor.getComplemento());
						this.setBairro(fornecedor.getBairro());
						this.setCidade(fornecedor.getCidade());
						this.setEstado(fornecedor.getEstado());
						this.setCep(fornecedor.getCep());
						this.setFoneResidencial(fornecedor.getFoneResidencial());
						this.setFoneComercial(fornecedor.getFoneComercial());
						this.setFoneCelular(fornecedor.getFoneCelular());
						this.setPessoaContato(fornecedor.getPessoaContato());
						this.setFoneContato(fornecedor.getFoneContato());
						this.setDataCadastro(fornecedor.getDataCadastro());
						setDataSistema(this.getDataCadastro());
						return "proxima";
					}else{
						setExisteRegistros(true);
						this.setFornecedores(col);
					}
				}
			}else{
				Collection c = getFachada().consultarTodosFornecedores();
				if(c != null && c.size() > 0){
					setExisteRegistros(true);
					this.setFornecedores(c);	
				}else{
					setExisteRegistros(false);
					this.setFornecedores(null);
				}
				
			}
		}catch(ObjectNotFoundException e){
			setExisteRegistros(false);
			this.setFornecedores(null);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Nenhum Registro Encontrado", "");
			getContextoApp().addMessage(null, msg);			
		}catch(Exception e){
			setExisteRegistros(false);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return "mesma";
	}

	public String inserir(){
		try {
			
			validarCampos(this.getIdTipoPessoa());
			
			Fornecedor fornecedor = preencheFornecedor(INSERIR);
			
			getFachada().inserirFornecedor(fornecedor);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Fornecedor já Existente!", "");
			getContextoApp().addMessage(null, msg);
		} catch (AppException app){
			app.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					app.getMessage(), "");
			getContextoApp().addMessage(null, msg);
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return "mesma";
	}
	
	public String alterar(){
		try {	
			validarCampos(this.getIdTipoPessoa());
			
			Fornecedor fornecedor = preencheFornecedor(ALTERAR);			
								
			getFachada().alterarFornecedor(fornecedor);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			resetBB();
			this.setFornecedores(null);
		} catch (AppException app){
			app.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					app.getMessage(), "");
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
			Fornecedor fornecedor = preencheFornecedor(EXCLUIR);
			
			getFachada().excluirFornecedor(fornecedor);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			resetBB();
			this.setFornecedores(null);
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return "mesma";
	}
	
	public Fornecedor preencheFornecedor(String acao){
		
		Fornecedor fornecedor = new Fornecedor();
		
		if (acao.equals(BackBean.INSERIR)) {
			if (getId()==null) fornecedor.setId(getIdInc(Fornecedor.class));
		} else {
			fornecedor.setId(new Long(this.getId()));
		}
		fornecedor.setNomeFornecedor(this.getNomeFornecedor());
		fornecedor.setTipoPessoa(this.getIdTipoPessoa());
		cpfCnpj = this.getCpfCnpj().trim().replace(".", "").replace("-", "").replace("/", "");
		fornecedor.setCpfCnpj(this.getCpfCnpj());
		fornecedor.setRazaoSocial(this.getRazaoSocial());
		fornecedor.setNomeFantasia(this.getNomeFantasia());
		fornecedor.setInscricaoEstadual(this.getInscricaoEstadual());
		fornecedor.setInscricaoMunicipal(this.getInscricaoMunicipal());
		fornecedor.setLogradouro(this.getLogradouro());
		fornecedor.setNumero(this.getNumero());
		fornecedor.setComplemento(this.getComplemento());
		fornecedor.setBairro(this.getBairro());
		fornecedor.setCidade(this.getCidade());
		fornecedor.setEstado(this.getEstado());
		fornecedor.setCep(this.getCep());
		fornecedor.setFoneResidencial(this.getFoneResidencial());
		fornecedor.setFoneComercial(this.getFoneComercial());
		fornecedor.setFoneCelular(this.getFoneCelular());
		fornecedor.setPessoaContato(this.getPessoaContato());
		fornecedor.setFoneContato(this.getFoneContato());
		fornecedor.setDataCadastro(this.getDataSistema());
		if(this.getIdTipoPessoa().equals(Fornecedor.PESSOA_FISICA)){
			fornecedor.setRazaoSocial(null);
			fornecedor.setNomeFantasia(null);
			fornecedor.setInscricaoEstadual(null);
			fornecedor.setInscricaoMunicipal(null);

		}else if(this.getIdTipoPessoa().equals(Fornecedor.PESSOA_FISICA)){
			fornecedor.setNomeFornecedor(null);
		}
		return fornecedor;
	}

	/**
	 * @return the fornecedores
	 */
	public Collection getFornecedores() {
		return fornecedores;
	}

	/**
	 * @param fornecedores the fornecedores to set
	 */
	public void setFornecedores(Collection fornecedores) {
		this.fornecedores = fornecedores;
	}

	/**
	 * @return the nomeFornecedor
	 */
	public String getNomeFornecedor() {
		return nomeFornecedor;
	}

	/**
	 * @param nomeFornecedor the nomeFornecedor to set
	 */
	public void setNomeFornecedor(String nomeFornecedor) {
		this.nomeFornecedor = nomeFornecedor;
	}

	/**
	 * @return the foneResidencial
	 */
	public String getFoneResidencial() {
		return foneResidencial;
	}

	/**
	 * @param foneResidencial the foneResidencial to set
	 */
	public void setFoneResidencial(String foneResidencial) {
		this.foneResidencial = foneResidencial;
	}

	public SelectItem[] getListaTipoPessoa() {
		SelectItem[] lista = new SelectItem[2];
		lista[0] = new SelectItem("F", "Física");
		lista[1] = new SelectItem("J", "Jurídica");
		if(this.getIdTipoPessoa() == null || this.getIdTipoPessoa().equals("")){
			this.setIdTipoPessoa(Fornecedor.PESSOA_FISICA);
		}
		return lista;
	}

	public void setListaTipoPessoa(SelectItem[] listaTipoPessoa) {
		this.listaTipoPessoa = listaTipoPessoa;
	}
	public void setListaUf(SelectItem[] listaUf) {
		this.listaUf = listaUf;
	}
	
	public SelectItem[] getListaUf() {
		return Util.getUFs();
	}
	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}
	
	public void validarCampos(String tipoPessoa) throws AppException{
		if(tipoPessoa.equals(Fornecedor.PESSOA_FISICA)){
			if(this.getNomeFornecedor() == null || this.getNomeFornecedor().equals("")){
				throw new AppException("O Nome do Fornecedor é obrigatório.");
			}
		}else if(tipoPessoa.equals(Fornecedor.PESSOA_JURIDICA)){
			if(this.getRazaoSocial() == null || this.getRazaoSocial().equals("")){
				throw new AppException("O campo Razão Social é obrigatório.");
			}
			if(this.getNomeFantasia() == null || this.getNomeFantasia().equals("")){
				throw new AppException("O campo Nome Fantasia é obrigatório.");
			}
//			if(this.getInscricaoEstadual() == null || this.getInscricaoEstadual().equals("")){
//				throw new AppException("O campo Inscrição Estadual é obrigatório.");
//			}
//			if(this.getInscricaoMunicipal() == null || this.getInscricaoMunicipal().equals("")){
//				throw new AppException("O campo Inscrição Municipal é obrigatório.");
//			}
		}		
	}
	
	public String voltarConsulta() {
		this.setId(null);
		this.setIdTipoPessoa(Fornecedor.PESSOA_FISICA);
		this.setNomeFornecedor(null);
		this.setCpfCnpj(null);
		consultar();
		return "voltar";
	}

	public String voltarMenu() {
		resetBB();
		return "voltar";
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
				setFornecedores(null);	
			}			
		}
	}
}