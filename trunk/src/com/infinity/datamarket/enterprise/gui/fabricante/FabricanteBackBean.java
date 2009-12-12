package com.infinity.datamarket.enterprise.gui.fabricante;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.infinity.datamarket.comum.fabricante.Fabricante;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Util;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

public class FabricanteBackBean extends BackBean {
	
	public FabricanteBackBean(){
//		resetBB();
		setDataCadastro(new Date(System.currentTimeMillis()));
	}
	
	String id;
	String nomeFabricante;
	String idTipoPessoa = new String(Fabricante.PESSOA_FISICA);
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
	
	Collection fabricantes;

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
		this.setIdTipoPessoa(Fabricante.PESSOA_FISICA);
		this.setNomeFabricante(null);
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
				Fabricante Fabricante = getFachada().consultaFabricantePorId(new Long(getId()));
				this.setId(Fabricante.getId().toString());
				this.setNomeFabricante(Fabricante.getNomeFabricante());
				this.setIdTipoPessoa(Fabricante.getTipoPessoa());
				this.setCpfCnpj(Util.formataCpfCnpj(Fabricante.getCpfCnpj()));
				this.setRazaoSocial(Fabricante.getRazaoSocial());
				this.setNomeFantasia(Fabricante.getNomeFantasia());
				this.setInscricaoEstadual(Fabricante.getInscricaoEstadual());
				this.setInscricaoMunicipal(Fabricante.getInscricaoMunicipal());
				this.setLogradouro(Fabricante.getLogradouro());
				this.setNumero(Fabricante.getNumero());
				this.setComplemento(Fabricante.getComplemento());
				this.setBairro(Fabricante.getBairro());
				this.setCidade(Fabricante.getCidade());
				this.setEstado(Fabricante.getEstado());
				this.setCep(Fabricante.getCep());
				this.setFoneResidencial(Fabricante.getFoneResidencial());
				this.setFoneComercial(Fabricante.getFoneComercial());
				this.setFoneCelular(Fabricante.getFoneCelular());
				this.setPessoaContato(Fabricante.getPessoaContato());
				this.setFoneContato(Fabricante.getFoneContato());
				this.setDataCadastro(Fabricante.getDataCadastro());
				setDataSistema(this.getDataCadastro());
				
				return "proxima";
			}else if ((this.getNomeFabricante() != null && !"".equals(this.getNomeFabricante()))
					|| (this.getCpfCnpj() != null && !"".equals(this.getCpfCnpj()))
					|| (this.getIdTipoPessoa() != null && !"".equals(this.getIdTipoPessoa()))){
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(Fabricante.class);
				filter.addProperty("tipoPessoa", this.getIdTipoPessoa());
				if(this.getIdTipoPessoa().equals(Fabricante.PESSOA_FISICA)){
					filter.addProperty("nomeFabricante", this.getNomeFabricante());	
				}else{
					filter.addProperty("razaoSocial", this.getNomeFabricante());
				}
				if(this.getCpfCnpj() != null && !"".equals(this.getCpfCnpj())){
					filter.addProperty("cpfCnpj", this.getCpfCnpj().trim().replace(".", "").replace("-", "").replace("/", ""));	
				}
				Collection col = getFachada().consultarFabricante(filter);
				if (col == null || col.size() == 0){
					setExisteRegistros(false);
					this.setFabricantes(col);
					
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					getContextoApp().addMessage(null, msg);					
				}else if (col != null){
					if(col.size() == 1){
						Fabricante Fabricante = (Fabricante)col.iterator().next();
						this.setId(Fabricante.getId().toString());
						this.setNomeFabricante(Fabricante.getNomeFabricante());
						this.setIdTipoPessoa(Fabricante.getTipoPessoa());
						this.setCpfCnpj(Util.formataCpfCnpj(Fabricante.getCpfCnpj()));
						this.setRazaoSocial(Fabricante.getRazaoSocial());
						this.setNomeFantasia(Fabricante.getNomeFantasia());
						this.setInscricaoEstadual(Fabricante.getInscricaoEstadual());
						this.setInscricaoMunicipal(Fabricante.getInscricaoMunicipal());
						this.setLogradouro(Fabricante.getLogradouro());
						this.setNumero(Fabricante.getNumero());
						this.setComplemento(Fabricante.getComplemento());
						this.setBairro(Fabricante.getBairro());
						this.setCidade(Fabricante.getCidade());
						this.setEstado(Fabricante.getEstado());
						this.setCep(Fabricante.getCep());
						this.setFoneResidencial(Fabricante.getFoneResidencial());
						this.setFoneComercial(Fabricante.getFoneComercial());
						this.setFoneCelular(Fabricante.getFoneCelular());
						this.setPessoaContato(Fabricante.getPessoaContato());
						this.setFoneContato(Fabricante.getFoneContato());
						this.setDataCadastro(Fabricante.getDataCadastro());
						setDataSistema(this.getDataCadastro());
						return "proxima";
					}else{
						setExisteRegistros(true);
						this.setFabricantes(col);
					}
				}
			}else{
				Collection c = getFachada().consultarTodosFabricantees();
				if(c != null && c.size() > 0){
					setExisteRegistros(true);
					this.setFabricantes(c);	
				}else{
					setExisteRegistros(false);
					this.setFabricantes(null);
				}
				
			}
		}catch(ObjectNotFoundException e){
			setExisteRegistros(false);
			this.setFabricantes(null);
			
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
			
			Fabricante fabricante = preencheFabricante(INSERIR);
			
			getFachada().inserirFabricante(fabricante);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Fabricante já Existente!", "");
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
			
			Fabricante fabricante = preencheFabricante(ALTERAR);			
								
			getFachada().alterarFabricante(fabricante);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			resetBB();
			this.setFabricantes(null);
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
			Fabricante fabricante = preencheFabricante(EXCLUIR);
					
			getFachada().excluirFabricante(fabricante);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			resetBB();
			this.setFabricantes(null);
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return "mesma";
	}
	
	public Fabricante preencheFabricante(String acao){
		
		Fabricante fabricante = new Fabricante();
		
		if (acao.equals(BackBean.INSERIR)) {
			if (getId()==null) fabricante.setId(getIdInc(Fabricante.class));
		} else {
			fabricante.setId(new Long(this.getId()));
		}
		
		fabricante.setNomeFabricante(this.getNomeFabricante());
		fabricante.setTipoPessoa(this.getIdTipoPessoa());
		cpfCnpj = this.getCpfCnpj().trim().replace(".", "").replace("-", "").replace("/", "");
		fabricante.setCpfCnpj(this.getCpfCnpj());
		fabricante.setRazaoSocial(this.getRazaoSocial());
		fabricante.setNomeFantasia(this.getNomeFantasia());
		fabricante.setInscricaoEstadual(this.getInscricaoEstadual());
		fabricante.setInscricaoMunicipal(this.getInscricaoMunicipal());
		fabricante.setLogradouro(this.getLogradouro());
		fabricante.setNumero(this.getNumero());
		fabricante.setComplemento(this.getComplemento());
		fabricante.setBairro(this.getBairro());
		fabricante.setCidade(this.getCidade());
		fabricante.setEstado(this.getEstado());
		fabricante.setCep(this.getCep());
		fabricante.setFoneResidencial(this.getFoneResidencial());
		fabricante.setFoneComercial(this.getFoneComercial());
		fabricante.setFoneCelular(this.getFoneCelular());
		fabricante.setPessoaContato(this.getPessoaContato());
		fabricante.setFoneContato(this.getFoneContato());
		fabricante.setDataCadastro(this.getDataSistema());
		if(this.getIdTipoPessoa().equals(Fabricante.PESSOA_FISICA)){
			fabricante.setRazaoSocial(null);
			fabricante.setNomeFantasia(null);
			fabricante.setInscricaoEstadual(null);
			fabricante.setInscricaoMunicipal(null);

		}else if(this.getIdTipoPessoa().equals(Fabricante.PESSOA_FISICA)){
			fabricante.setNomeFabricante(null);
		}
		return fabricante;
	}

	/**
	 * @return the Fabricantees
	 */
	public Collection getFabricantes() {
		return fabricantes;
	}

	/**
	 * @param Fabricantees the Fabricantees to set
	 */
	public void setFabricantes(Collection fabricantes) {
		this.fabricantes = fabricantes;
	}

	/**
	 * @return the nomeFabricante
	 */
	public String getNomeFabricante() {
		return nomeFabricante;
	}

	/**
	 * @param nomeFabricante the nomeFabricante to set
	 */
	public void setNomeFabricante(String nomeFabricante) {
		this.nomeFabricante = nomeFabricante;
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
			this.setIdTipoPessoa(Fabricante.PESSOA_FISICA);
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
		if(tipoPessoa.equals(Fabricante.PESSOA_FISICA)){
			if(this.getNomeFabricante() == null || this.getNomeFabricante().equals("")){
				throw new AppException("O Nome do Fabricante é obrigatório.");
			}
		}else if(tipoPessoa.equals(Fabricante.PESSOA_JURIDICA)){
			if(this.getRazaoSocial() == null || this.getRazaoSocial().equals("")){
				throw new AppException("O campo Razão Social é obrigatório.");
			}
			if(this.getNomeFantasia() == null || this.getNomeFantasia().equals("")){
				throw new AppException("O campo Nome Fantasia é obrigatório.");
			}

		}		
	}
	
	public String voltarConsulta() {
		this.setId(null);
		this.setIdTipoPessoa(Fabricante.PESSOA_FISICA);
		this.setNomeFabricante(null);
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
				setFabricantes(null);	
			}			
		}
	}
}