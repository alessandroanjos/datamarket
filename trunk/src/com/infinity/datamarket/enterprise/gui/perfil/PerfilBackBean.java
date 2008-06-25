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
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.myfaces.custom.tree2.TreeNode;
import org.apache.myfaces.custom.tree2.TreeNodeBase;
import org.apache.myfaces.custom.tree2.TreeNodeChecked;

import com.infinity.datamarket.comum.funcionalidade.Funcionalidade;
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
	ArrayList listaFuncionalidadesAssociadas;
		
	SelectItem[] perfis;
	SelectItem[] operacoes;
	SelectItem[] funcionalidades;
	TreeNode arvore;
	TreeNode arvoreFuncionalidades;

	SelectItem[] funcionalidadesSelecionadas;
	
//	public PerfilBackBean(){
//		resetBB();
//	}

	public ArrayList getListaOperacoesAssociadas() {
		return listaOperacoesAssociadas;
	}

	public void setListaOperacoesAssociadas(ArrayList listaOperacoesAssociadas) {
		this.listaOperacoesAssociadas = listaOperacoesAssociadas;
	}
	
	public ArrayList getListaFuncionalidadesAssociadas() {
		return listaFuncionalidadesAssociadas;
	}

	public void setListaFuncionalidadesAssociadas(ArrayList listaFuncionalidadesAssociadas) {
		this.listaFuncionalidadesAssociadas = listaFuncionalidadesAssociadas;
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
	
	public void setFuncionalidades(SelectItem[] funcionalidades){
		this.funcionalidades = funcionalidades;
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
			// Perfil
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
			
			// Macro-Operaçoes Associadas
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
			
			// Funcionalidades Associadas
			List<String> listaFuncSelecionadas = new ArrayList<String>();
			
		    this.processNodes(listaFuncSelecionadas, this.arvore.getChildren().iterator());
		    
		    if(listaFuncSelecionadas != null && listaFuncSelecionadas.size() > 0){
		    	List<String> arrayCodFuncionalidades = new ArrayList<String>();
				for (int i = 0; i < listaFuncSelecionadas.size(); i++) {
					String idFuncionalidade = (String)listaFuncSelecionadas.get(i);
					Funcionalidade funcionalidade = new Funcionalidade();
					funcionalidade.setId(new Long(idFuncionalidade));
					funcionalidade = getFachada().consultarFuncionalidadePorPK(funcionalidade.getId());
					arrayCodFuncionalidades.add(idFuncionalidade);
					montaListaCodigosFuncionalidades(funcionalidade, arrayCodFuncionalidades);
				}
				
				Set<Funcionalidade> funcionalidadesTmp = new HashSet<Funcionalidade>();
				for (int i = 0; i < arrayCodFuncionalidades.size(); i++) {
					String codFuncionalidade = arrayCodFuncionalidades.get(i);
					Funcionalidade funcionalidade = new Funcionalidade();
					funcionalidade.setId(new Long(codFuncionalidade));
					funcionalidade = getFachada().consultarFuncionalidadePorPK(funcionalidade.getId());
					funcionalidadesTmp.add(funcionalidade);
				}
				perfil.setFuncionalidades(funcionalidadesTmp);
			}else{
				perfil.setFuncionalidades(null);	
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

	public void montaListaCodigosFuncionalidades(Funcionalidade funcionalidade, List<String> listaCodFuncionalidades){
		
		if(funcionalidade.getFuncionalidadeSuperior() != null){
			if(!listaCodFuncionalidades.contains(funcionalidade.getFuncionalidadeSuperior().getId().toString())){
				listaCodFuncionalidades.add(funcionalidade.getFuncionalidadeSuperior().getId().toString());
				montaListaCodigosFuncionalidades(funcionalidade.getFuncionalidadeSuperior(), listaCodFuncionalidades);
			}
		}
	}

	
	public boolean verificaExisteFuncionalidade(Set<Funcionalidade> listaFuncionalidades){
		boolean result = true;
		
		return result;
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
				}else{
					this.setIdPerfilSuperior("0");
				}
				
				this.setPercentualDesconto(perfil.getPercentualDesconto());
				
				Set operacoesAssociadas = perfil.getOperacoes();
				List<String> listaTemp = new ArrayList<String>();
				
				for (Iterator iter = operacoesAssociadas.iterator(); iter
						.hasNext();) {
					MacroOperacao macroOperacaoAssociada = (MacroOperacao) iter.next();
					listaTemp.add(macroOperacaoAssociada.getId().toString());
				}
				this.setListaOperacoesAssociadas((ArrayList)listaTemp);

				Set funcionalidadesAssociadas = perfil.getFuncionalidades();
				List<String> listaFuncTemp = new ArrayList<String>();
				
				for (Iterator iter = funcionalidadesAssociadas.iterator(); iter
						.hasNext();) {
					Funcionalidade funcionalidadeAssociada = (Funcionalidade) iter.next();
					listaFuncTemp.add(funcionalidadeAssociada.getId().toString());
				}
				this.setListaFuncionalidadesAssociadas((ArrayList)listaFuncTemp);
								
				return "proxima";
			}else if ((getDescricao() != null && !getDescricao().equals("")) 
					|| (getIdPerfilSuperior() != null && !getIdPerfilSuperior().equals("0"))){
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(Perfil.class);
				if(!getDescricao().equals("")){
					filter.addProperty("descricao", getDescricao());	
				}
				if(!getIdPerfilSuperior().equals("0")){
					filter.addProperty("perfilSuperior.id", new Long(getIdPerfilSuperior()));
				}				
				Collection col = getFachada().consultarPerfil(filter);
				if (col == null || col.size() == 0){
					setExisteRegistros(false);
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

						Set operacoesAssociadas = perfil.getOperacoes();
						List<String> listaTemp = new ArrayList<String>();
						
						for (Iterator iter = operacoesAssociadas.iterator(); iter
								.hasNext();) {
							MacroOperacao macroOperacaoAssociada = (MacroOperacao) iter.next();
							listaTemp.add(macroOperacaoAssociada.getId().toString());
						}
						this.setListaOperacoesAssociadas((ArrayList)listaTemp);

						Set funcionalidadesAssociadas = perfil.getFuncionalidades();
						List<String> listaFuncTemp = new ArrayList<String>();
						
						for (Iterator iter = funcionalidadesAssociadas.iterator(); iter
								.hasNext();) {
							Funcionalidade funcionalidadeAssociada = (Funcionalidade) iter.next();
							listaFuncTemp.add(funcionalidadeAssociada.getId().toString());
						}
						this.setListaFuncionalidadesAssociadas((ArrayList)listaFuncTemp);
						return "proxima";
					}else{
						setExisteRegistros(true);
						setListaPerfis(col);
					}
				}
			}else{
				Collection c = getFachada().consultarTodosPerfil();
				if (c != null && c.size() > 0){
					setExisteRegistros(true);
				}else{
					setExisteRegistros(false);
				}
				setListaPerfis(c);
			}
		}catch(ObjectNotFoundException e){
			this.setPerfis(null);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Nenhum Registro Encontrado", "");
			ctx.addMessage(null, msg);			
			setExisteRegistros(false);
		}catch(Exception e){
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
			setExisteRegistros(false);
		}
//		this.setId(null);
//		this.setDescricao(null);
		this.setPerfilSuperior(null);
		this.setPercentualDesconto(null);
		this.setListaOperacoesAssociadas(null);
		this.setListaFuncionalidadesAssociadas(null);
//		this.setIdPerfilSuperior(null);
//		this.setPerfis(null);
		this.setOperacoes(null);
		this.setFuncionalidades(null);
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
			
			if(perfil.getPerfilSuperior() != null && perfil.getId().equals(perfil.getPerfilSuperior().getId())){
				throw new Exception("O Perfil não pode ter ele mesmo como Perfil Superior.");				
			}
			
			perfil.setPercentualDesconto(this.getPercentualDesconto());
						
			//Macro-Operações Associadas
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
			
//			 Funcionalidades Associadas
			List<String> listaFuncSelecionadas = new ArrayList<String>();
			
		    this.processNodes(listaFuncSelecionadas, this.arvore.getChildren().iterator());
		    
		    if(listaFuncSelecionadas != null && listaFuncSelecionadas.size() > 0){
		    	List<String> arrayCodFuncionalidades = new ArrayList<String>();
				for (int i = 0; i < listaFuncSelecionadas.size(); i++) {
					String idFuncionalidade = (String)listaFuncSelecionadas.get(i);
					Funcionalidade funcionalidade = new Funcionalidade();
					funcionalidade.setId(new Long(idFuncionalidade));
					funcionalidade = getFachada().consultarFuncionalidadePorPK(funcionalidade.getId());
					arrayCodFuncionalidades.add(idFuncionalidade);
					montaListaCodigosFuncionalidades(funcionalidade, arrayCodFuncionalidades);
				}
				
				Set<Funcionalidade> funcionalidadesTmp = new HashSet<Funcionalidade>();
				for (int i = 0; i < arrayCodFuncionalidades.size(); i++) {
					String codFuncionalidade = arrayCodFuncionalidades.get(i);
					Funcionalidade funcionalidade = new Funcionalidade();
					funcionalidade.setId(new Long(codFuncionalidade));
					funcionalidade = getFachada().consultarFuncionalidadePorPK(funcionalidade.getId());
					funcionalidadesTmp.add(funcionalidade);
				}
				perfil.setFuncionalidades(funcionalidadesTmp);
			}else{
				perfil.setFuncionalidades(null);	
			}
			
			getFachada().alterarPerfil(perfil);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", e.getMessage());
			ctx.addMessage(null, msg);
		}
		return "mesma";
	}
	
	public String excluir(){
		try {
			Perfil perfil = new Perfil();
			perfil.setId(new Long(this.getId()));
			
			perfil.setDescricao(this.getDescricao());

			if (!this.getIdPerfilSuperior().equals("0")) {
				Perfil perfilSuperior = getFachada().consultarPerfilPorPK(
						new Long(this.getIdPerfilSuperior()));
				perfil.setPerfilSuperior(perfilSuperior);
			} else {
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
			
			if(this.getListaFuncionalidadesAssociadas() != null && this.getListaFuncionalidadesAssociadas().size() > 0){
				ArrayList lista = this.getListaFuncionalidadesAssociadas();
				Set<Funcionalidade> macroOperacoesTmp = new HashSet<Funcionalidade>(); 
				for (int i = 0; i < lista.size(); i++) {
					String idFuncionalidade = (String)lista.get(i);
					Funcionalidade funcionalidade = new Funcionalidade();
					funcionalidade.setId(new Long(idFuncionalidade));
					funcionalidade = getFachada().consultarFuncionalidadePorPK(funcionalidade.getId());
					macroOperacoesTmp.add(funcionalidade);					
				}
				perfil.setFuncionalidades(macroOperacoesTmp);
			}else{
				perfil.setFuncionalidades(null);	
			}
			//verifica se o perfil eh perfil superior
			if(perfil.getPerfilSuperior() != null &&
					getFachada().consultarPerfisPorPerfilSuperior(perfil).size() > 0){
				throw new Exception("O Perfil selecionado não pode ser excluído. \n" +
						"Existem Perfis vinculados a este Perfil.");
			// verifica se o perfil possui usuarios associados
			}else if(getFachada().consultarUsuariosPorPerfil(perfil).size() > 0){
				throw new Exception("O Perfil selecionado não pode ser excluído. \n" +
						"Existem Usuários vinculados a este Perfil.");
			}
			
			getFachada().excluirPerfil(perfil);	
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", e.getMessage());
			ctx.addMessage(null, msg);
		}
		
		return "mesma";
	}
	
	public void resetBB(){
		this.setId(null);
		this.setDescricao(null);
		this.setPerfilSuperior(null);
		this.setPercentualDesconto(null);
		this.setListaOperacoesAssociadas(null);
		this.setListaFuncionalidadesAssociadas(null);
		this.setIdPerfilSuperior(null);
//		this.setListaPerfis(null);
		this.setPerfis(null);
		this.setOperacoes(null);
		this.setFuncionalidades(null);
	}
	
	public String voltarConsulta(){
//		resetBB();
		consultar();
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

	public SelectItem[] getFuncionalidades(){
		SelectItem[] arrayFuncionalidadesAssociadas = null;
		try {
			List<Funcionalidade> funcionalidades = carregarFuncionalidades();
			arrayFuncionalidadesAssociadas = new SelectItem[funcionalidades.size()];
			int i = 0;
			for(Funcionalidade funcionalidadesAssociadasTmp : funcionalidades){
				SelectItem item = new SelectItem(funcionalidadesAssociadasTmp.getId().toString(), funcionalidadesAssociadasTmp.getDescricao());
				arrayFuncionalidadesAssociadas[i++] = item;
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return arrayFuncionalidadesAssociadas;
	}
	
	private List<Funcionalidade> carregarFuncionalidades() {
		
		List<Funcionalidade> funcionalidades = null;
		try {
			funcionalidades = (ArrayList<Funcionalidade>)getFachada().consultarTodosFuncionalidade();
			setarFuncionalidadesFilhas(funcionalidades.iterator());
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return funcionalidades;
	}
	
	public void setarFuncionalidadesFilhas(Iterator<Funcionalidade> funcionalidades){
		try {
			while(funcionalidades.hasNext()){
				Funcionalidade funcTemp = (Funcionalidade) funcionalidades.next();
				Collection funcionalidadesFilhas = getFachada().consultarFuncionalidadesPorFuncionalidadeSuperior(funcTemp);

				if(funcionalidadesFilhas != null && funcionalidadesFilhas.size() > 0){
					setarFuncionalidadesFilhas(funcionalidadesFilhas.iterator());
				}
				
				funcTemp.setFuncionalidadesFilhas(funcionalidadesFilhas);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
	}
	
	/**
	 * @return the funcionalidadesSelecionadas
	 */
	public SelectItem[] getFuncionalidadesSelecionadas() {
		return funcionalidadesSelecionadas;
	}

	/**
	 * @param funcionalidadesSelecionadas the funcionalidadesSelecionadas to set
	 */
	public void setFuncionalidadesSelecionadas(SelectItem[] funcionalidadesSelecionadas) {
		this.funcionalidadesSelecionadas = funcionalidadesSelecionadas;
	}

	public TreeNode getArvoreFuncionalidades(){
		TreeNode tree = new TreeNodeBase("root","",false);
		TreeNodeBase treeFilhos = null;
		Iterator itFunc = carregarFuncionalidades().iterator();
		while(itFunc.hasNext()){
			Funcionalidade func = (Funcionalidade)itFunc.next();
			if(func.getFuncionalidadeSuperior() == null){
				treeFilhos = new TreeNodeBase("noRaiz", func.getDescricao(), func.getId().toString(), false);
				if(func.getFuncionalidadesFilhas() != null && func.getFuncionalidadesFilhas().size() > 0){
					montaArvore(func.getFuncionalidadesFilhas().iterator(), treeFilhos);	
					tree.getChildren().add(treeFilhos);	
				}
			}
		}		
		this.arvore = tree;		
		if(this.arvore != null && this.arvore.getChildren() != null && this.arvore.getChildren().size() > 0){
			if(this.getListaFuncionalidadesAssociadas() != null && this.getListaFuncionalidadesAssociadas().size() > 0){
				marcarFuncionalidadesAssociadas(this.getListaFuncionalidadesAssociadas(), this.arvore.getChildren().iterator());	
			}				
		}
		return tree;
	}

	public void montaArvore(Iterator itFuncionalidades, TreeNode tree){
		while(itFuncionalidades.hasNext()){			
			Funcionalidade func = (Funcionalidade)itFuncionalidades.next();			
			if(func != null){				
				TreeNode no = null;
				if(func.getFuncionalidadeSuperior() == null){
					no = new TreeNodeBase("noRaiz", func.getDescricao(), func.getId().toString(), false);
				}else{
					if(func.getUrl() == null || func.getUrl().equals("")){
						no = new TreeNodeBase("noRaiz", func.getDescricao(), func.getId().toString(), false);	
					}else{
						no = new TreeNodeChecked("no", func.getDescricao(), func.getId().toString(), false, false);	
					}															
				}
				if(func.getFuncionalidadesFilhas() != null && func.getFuncionalidadesFilhas().size() > 0){
					montaArvore(func.getFuncionalidadesFilhas().iterator(), no);
				}
				tree.getChildren().add(no);				
			}			
		}	
		
	}
	
	public void setArvoreFuncionalidades(TreeNode arvoreFuncionalidades){
		this.arvoreFuncionalidades = arvoreFuncionalidades;
	}

	public void processNodes(List<String> listaFuncSelecionadas, Iterator iterator){
		 for (Iterator iter = iterator; iter.hasNext();) {
			 Object obj = iter.next();
			 if(obj instanceof TreeNodeChecked){
				   TreeNodeChecked tree = (TreeNodeChecked) obj; 
				   if(tree.getChildren() != null && tree.getChildren().size() > 0){
					   processNodes(listaFuncSelecionadas, tree.getChildren().iterator());
					   if(tree.isChecked() == true) { 
					       listaFuncSelecionadas.add(tree.getIdentifier());
					   }   
				   }else if(tree.isChecked() == true) { 
				       listaFuncSelecionadas.add(tree.getIdentifier());
				   } 
			 }else if (obj instanceof TreeNodeBase){
				 TreeNodeBase tree = (TreeNodeBase) obj;
//				 listaFuncSelecionadas.add(tree.getIdentifier());
				 if(tree.getChildren() != null && tree.getChildren().size() > 0){
					 processNodes(listaFuncSelecionadas, tree.getChildren().iterator());
				 }
			 }
		 }
	}
	
	public void marcarFuncionalidadesAssociadas(List<String> listaFuncSelecionadas, Iterator iterator){
		 for (Iterator iter = iterator; iter.hasNext();) {
			 Object obj = iter.next();
			 if(obj instanceof TreeNodeChecked){
				   TreeNodeChecked tree = (TreeNodeChecked) obj; 
				   if(tree.getChildren() != null && tree.getChildren().size() > 0){
					   marcarFuncionalidadesAssociadas(listaFuncSelecionadas, tree.getChildren().iterator());
					   if(existeFuncionalidade(tree.getIdentifier())){
						   tree.setChecked(true);
					   }else{
						   tree.setChecked(false);
					   }
				   }else if(existeFuncionalidade(tree.getIdentifier())){
					   tree.setChecked(true);
				   }else{
					   tree.setChecked(false);
				   }
			 }else if (obj instanceof TreeNodeBase){
				 TreeNodeBase tree = (TreeNodeBase) obj;
				 if(tree.getChildren() != null && tree.getChildren().size() > 0){
					 marcarFuncionalidadesAssociadas(listaFuncSelecionadas, tree.getChildren().iterator());
				 }
			 }
		 }
	}
	
	public boolean existeFuncionalidade(String codigoNo){
		boolean existe = false;
		for (int i = 0; i < this.getListaFuncionalidadesAssociadas().size(); i++) {
			String codFuncionalidade = (String)this.getListaFuncionalidadesAssociadas().get(i);
			if(codFuncionalidade.equals(codigoNo)){
				existe = true;
				break;
			}
		}
		return existe;
	}

}