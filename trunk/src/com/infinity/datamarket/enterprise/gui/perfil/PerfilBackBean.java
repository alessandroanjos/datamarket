/**
 * 
 */
package com.infinity.datamarket.enterprise.gui.perfil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.usuario.Perfil;
import com.infinity.datamarket.enterprise.gui.util.BackBean;
import com.infinity.datamarket.pdv.maquinaestados.MacroOperacao;

/**
 * @author jonas
 *
 */
public class PerfilBackBean extends BackBean {
	
	String id; 
	String descricao;
	BigDecimal percentualDesconto;
	Perfil perfilSuperior;
	
	String idPerfilSuperior;
	
	Collection listaPerfis;
	ArrayList listaOperacoesAssociadas;
	
	SelectItem[] perfis;
	SelectItem[] operacoes;
	
	public ArrayList getListaOperacoesAssociadas() {
		return listaOperacoesAssociadas;
	}

	public void setListaOperacoesAssociadas(ArrayList listaOperacoesAssociadas) {
		this.listaOperacoesAssociadas = listaOperacoesAssociadas;
	}

	public Collection getListaPerfis() {
		return listaPerfis;
	}

	public void setListaPerfis(Collection listaPerfis) {
		this.listaPerfis = listaPerfis;
	}

	public String getIdPerfilSuperior() {
		return idPerfilSuperior;
	}

	public void setIdPerfilSuperior(String idPerfilSuperior) {
		this.idPerfilSuperior = idPerfilSuperior;
	}

	public void setPerfis(SelectItem[] perfis) {
		this.perfis = perfis;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setOperacoes(SelectItem[] operacoes){
		this.operacoes = operacoes;
	}

	public Perfil getPerfilSuperior() {
		return perfilSuperior;
	}

	public void setPerfilSuperior(Perfil perfilSuperior) {
		this.perfilSuperior = perfilSuperior;
	}

	public BigDecimal getPercentualDesconto() {
		return percentualDesconto;
	}

	public void setPercentualDesconto(BigDecimal percentualDesconto) {
		this.percentualDesconto = percentualDesconto;
	}
	
	public String inserir(){
		
		try {
			Perfil perfil = new Perfil();
			perfil.setId(new Long(this.getId()));
			perfil.setDescricao(this.getDescricao());
			
			if(!this.getIdPerfilSuperior().equals("0")){
				Perfil perfilSuperior= getFachada().consultarPerfilPorPK(new Long(this.getIdPerfilSuperior()));
				perfil.setPerfilSuperior(perfilSuperior);
			}else{
				perfil.setPerfilSuperior(null);
			}
			
			perfil.setPercentualDesconto(this.getPercentualDesconto());
			
			if(this.getListaOperacoesAssociadas() != null && this.getListaOperacoesAssociadas().size() > 0){
				ArrayList lista = this.getListaOperacoesAssociadas();
				Set<MacroOperacao> macroOperacoesTmp = new HashSet<MacroOperacao>(); 
				for (int i = 0; i < lista.size(); i++) {
					String idMacroOperacao = (String)lista.get(i);
					MacroOperacao macroOperacao = new MacroOperacao();
					macroOperacao.setId(new Long(idMacroOperacao));
					macroOperacao = getFachada().consultarMacroOperacaoPorPK(macroOperacao.getId());
					macroOperacoesTmp.add(macroOperacao);					
				}
				perfil.setOperacoes(macroOperacoesTmp);
			}else{
				perfil.setOperacoes(null);	
			}
			
			getFachada().inserirPerfil(perfil);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Perfil já Existente!", "");
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
	
		
	public String consultar(){
		try{
			FacesContext context = FacesContext.getCurrentInstance();
			Map params = context.getExternalContext().getRequestParameterMap();            
			String param = (String)  params.get("id");
			if (param != null && !"".equals(param)){
				setId(param);
			}
			if (getId() != null && !"".equals(getId())){
				Perfil perfil = getFachada().consultarPerfilPorPK(new Long(getId()));
				this.setId(perfil.getId().toString());
				this.setDescricao(perfil.getDescricao());
				this.setPerfilSuperior(perfil.getPerfilSuperior());
				
				if(this.getPerfilSuperior() != null){
					this.setIdPerfilSuperior(this.getPerfilSuperior().getId().toString());
//					this.idPerfilSuperior = this.getPerfilSuperior().getId().toString();
				}else{
					this.setIdPerfilSuperior("0");
//					this.idPerfilSuperior = "0";
				}
				
				this.setPercentualDesconto(perfil.getPercentualDesconto());
				
//				Set operacoesAssociadas = perfil.getOperacoes();
//				List<MacroOperacao> listaTemp = new ArrayList<MacroOperacao>();
//				
//				for (Iterator iter = operacoesAssociadas.iterator(); iter
//						.hasNext();) {
//					MacroOperacao macroOperacaoAssociada = (MacroOperacao) iter.next();
//					listaTemp.add(macroOperacaoAssociada);
//				}
//				this.setListaOperacoesAssociadas((ArrayList)listaTemp);
				
				return "proxima";
			}else if (getDescricao() != null && !"".equals(getDescricao())){
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(Perfil.class);
				filter.addProperty("descricao", getDescricao());
				Collection col = getFachada().consultarPerfil(filter);
				if (col == null || col.size() == 0){
					this.setListaPerfis(null);
					FacesContext ctx = FacesContext.getCurrentInstance();
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					ctx.addMessage(null, msg);					
				}else if (col != null){
					if(col.size() == 1){
						Perfil perfil = (Perfil)col.iterator().next();
						this.setId(perfil.getId().toString());
						this.setDescricao(perfil.getDescricao());
						Perfil perfilSuperior= getFachada().consultarPerfilPorPK(new Long(this.getIdPerfilSuperior()));
						this.setPerfilSuperior(perfilSuperior);
						if(this.getPerfilSuperior() != null){
							this.setIdPerfilSuperior(this.getPerfilSuperior().getId().toString());	
						}else{
							this.setIdPerfilSuperior("0");
						}
						this.setPercentualDesconto(perfil.getPercentualDesconto());

						Set operacoesAssociadas = (HashSet)perfil.getOperacoes();
						List<MacroOperacao> listaTemp = new ArrayList<MacroOperacao>();
						
						for (Iterator iter = operacoesAssociadas.iterator(); iter
								.hasNext();) {
							MacroOperacao macroOperacaoAssociada = (MacroOperacao) iter.next();
							listaTemp.add(macroOperacaoAssociada);
						}
						this.setListaOperacoesAssociadas((ArrayList)listaTemp);

						return "proxima";
					}else{
						setListaPerfis(col);
					}
				}
			}else{
				setListaPerfis(getFachada().consultarTodosPerfil());
			}
		}catch(ObjectNotFoundException e){
			this.setPerfis(null);
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
		this.setPerfilSuperior(null);
		this.setPercentualDesconto(null);
		this.setListaOperacoesAssociadas(null);
		this.setIdPerfilSuperior(null);
		return "mesma";
	}
	
	public String alterar(){
		try {
			Perfil perfil = new Perfil();
			perfil.setId(new Long(this.getId()));
			perfil.setDescricao(this.getDescricao());
			
			if(!this.getIdPerfilSuperior().equals("0")){
				Perfil perfilSuperior= getFachada().consultarPerfilPorPK(new Long(this.getIdPerfilSuperior()));
				perfil.setPerfilSuperior(perfilSuperior);
			}else{
				perfil.setPerfilSuperior(null);
			}
			
			perfil.setPercentualDesconto(this.getPercentualDesconto());
			
			if(this.getListaOperacoesAssociadas() != null && this.getListaOperacoesAssociadas().size() > 0){
				ArrayList lista = this.getListaOperacoesAssociadas();
				Set<MacroOperacao> macroOperacoesTmp = new HashSet<MacroOperacao>(); 
				for (int i = 0; i < lista.size(); i++) {
					String idMacroOperacao = (String)lista.get(i);
					MacroOperacao macroOperacao = new MacroOperacao();
					macroOperacao.setId(new Long(idMacroOperacao));
					macroOperacao = getFachada().consultarMacroOperacaoPorPK(macroOperacao.getId());
					macroOperacoesTmp.add(macroOperacao);					
				}
				perfil.setOperacoes(macroOperacoesTmp);
			}else{
				perfil.setOperacoes(null);	
			}

			getFachada().alterarPerfil(perfil);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
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
	
	public String excluir(){
		try {
			Perfil perfil = new Perfil();
			perfil.setId(new Long(this.getId()));
			perfil.setDescricao(this.getDescricao());

			if(!this.getIdPerfilSuperior().equals("0")){
				Perfil perfilSuperior= getFachada().consultarPerfilPorPK(new Long(this.getIdPerfilSuperior()));
				perfil.setPerfilSuperior(perfilSuperior);
			}else{
				perfil.setPerfilSuperior(null);
			}
			
			perfil.setPercentualDesconto(this.getPercentualDesconto());
			
			if(this.getListaOperacoesAssociadas() != null && this.getListaOperacoesAssociadas().size() > 0){
				ArrayList lista = this.getListaOperacoesAssociadas();
				Set<MacroOperacao> macroOperacoesTmp = new HashSet<MacroOperacao>(); 
				for (int i = 0; i < lista.size(); i++) {
					String idMacroOperacao = (String)lista.get(i);
					MacroOperacao macroOperacao = new MacroOperacao();
					macroOperacao.setId(new Long(idMacroOperacao));
					macroOperacao = getFachada().consultarMacroOperacaoPorPK(macroOperacao.getId());
					macroOperacoesTmp.add(macroOperacao);					
				}
				perfil.setOperacoes(macroOperacoesTmp);
			}else{
				perfil.setOperacoes(null);	
			}

			getFachada().excluirPerfil(perfil);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
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
	
	public String resetBB(){
		this.setId(null);
		this.setDescricao(null);
		this.setPerfilSuperior(null);
		this.setPercentualDesconto(null);
		this.setListaOperacoesAssociadas(null);
		this.setListaPerfis(null);
		this.setIdPerfilSuperior(null);
		return "mesma";
	}
	
	public String voltarConsulta(){
		resetBB();
		return "voltar";
	}
	public String voltarMenu(){
		resetBB();
		return "voltar";
	}
	
	private List<Perfil> carregarPerfis() {
		
		List<Perfil> perfis = null;
		try {
			perfis = (ArrayList<Perfil>)getFachada().consultarTodosPerfil();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return perfis;
	}
	
	public SelectItem[] getPerfis(){
		SelectItem[] arrayPerfis = null;
		try {
			List<Perfil> perfis = carregarPerfis();
			arrayPerfis = new SelectItem[perfis.size()+1];
			int i = 0;
			SelectItem itemBranco = new SelectItem("0", "");
			arrayPerfis[i++] = itemBranco;
			for(Perfil perfilTmp : perfis){
				SelectItem item = new SelectItem(perfilTmp.getId().toString(), perfilTmp.getDescricao());
				arrayPerfis[i++] = item;
			}
			
			if(this.getIdPerfilSuperior() == null || this.getIdPerfilSuperior().equals("") || this.getIdPerfilSuperior().equals("0")){
				this.setIdPerfilSuperior((String) arrayPerfis[0].getValue());				
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return arrayPerfis;
	}
	
	private List<MacroOperacao> carregarMacroOperacoes() {
		
		List<MacroOperacao> operacoes = null;
		try {
			operacoes = (ArrayList<MacroOperacao>)getFachada().consultarTodosMacroOperacao();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return operacoes;
	}
	
	public SelectItem[] getOperacoes(){
		SelectItem[] arrayOperacoesAssociadas = null;
		try {
			List<MacroOperacao> operacoes = carregarMacroOperacoes();
			arrayOperacoesAssociadas = new SelectItem[operacoes.size()];
			int i = 0;
			for(MacroOperacao operacoesAssociadasTmp : operacoes){
				SelectItem item = new SelectItem(operacoesAssociadasTmp.getId().toString(), operacoesAssociadasTmp.getDescricao());
				arrayOperacoesAssociadas[i++] = item;
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return arrayOperacoesAssociadas;
	}

}