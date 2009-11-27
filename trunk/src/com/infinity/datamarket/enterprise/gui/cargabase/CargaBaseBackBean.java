package com.infinity.datamarket.enterprise.gui.cargabase;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;

import com.infinity.datamarket.comum.componente.Componente;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.enterprise.gui.util.BackBean;
import com.infinity.datamarket.geradorbase.GeradorBaseComponente;

public class CargaBaseBackBean extends BackBean{
	private String idComponente;
	private String idLoja;	
	
	public String getIdLoja() {
		return idLoja;
	}
	public void setIdLoja(String idLoja) {
		this.idLoja = idLoja;
	}

	protected List<Loja> carregarLojas() {
		
		List<Loja> lojas = null;
		try {
			lojas = (List<Loja>)getFachada().consultarTodosLoja();
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return lojas;
	}
	
	public SelectItem[] getLojas(){
		SelectItem[] arrayFormas = null;
		try {
			List<Loja> lojas = carregarLojas();
			arrayFormas = new SelectItem[lojas.size()];
			int i = 0;
			for(Loja loja : lojas){
				SelectItem item = new SelectItem(loja.getId().toString(), loja.getNome());
				arrayFormas[i++] = item;
			}
			
			if(this.getIdLoja() == null || this.getIdLoja().equals("") || this.getIdLoja().equals("0") && arrayFormas.length > 0){
				this.setIdLoja((String) arrayFormas[0].getValue());				
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return arrayFormas;
	}
	

	public String getIdComponente() {
		return idComponente;
	}
	public void setIdComponente(String idComponente) {
		this.idComponente = idComponente;
	}

	protected Collection carregarComponentes() {
		
		Collection Componentes = null;
		try {
			getLojas();
			
			Componentes = (Collection)getFachada().consultarTodosComponentes(new Long(this.getIdLoja()));
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return Componentes;
	}
	
	public SelectItem[] getComponentes(){
		SelectItem[] arrayFormas = null;
		try {
			Collection Componentes = carregarComponentes();
			arrayFormas = new SelectItem[Componentes.size()];
			int i = 0;
			Iterator it = Componentes.iterator();
			while(it.hasNext()) {
				Componente Componente = (Componente)it.next();
				SelectItem item = new SelectItem(Componente.getId().toString(), Componente.getDescricao());
				arrayFormas[i++] = item;
			}
			if(this.getIdComponente() == null || this.getIdComponente().equals("") || this.getIdComponente().equals("0") && arrayFormas.length > 0){
				this.setIdComponente((String) arrayFormas[0].getValue());				
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return arrayFormas;
	}

	public void resetBB(){
		this.idComponente = "";
		this.idLoja = "";
	}
	
	public String voltarMenu(){
		resetBB();
		return "voltar";
	}
	
	public String liberarCargaBase(){
		try {
			GeradorBaseComponente gerador = (GeradorBaseComponente) ServiceLocator.getInstancia().getObjectToIntancia(
							"com.infinity.datamarket.geradorbase.GeradorBaseComponenteHibernate");
			gerador.geraBase(new Long(getIdLoja()));

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Novo Banco Gerado com Sucesso", "");
			getContextoApp().addMessage(null, msg);
		} catch (Exception e) {

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de Sistema", "");
			getContextoApp().addMessage(null, msg);
			setIdLoja("");
			setIdComponente("");
		}
		return "mesma";
	}

	public String consultarComponentes(){
//		try {
//			Parametro param = ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.LOTE);
//			setNumeroLote(param.getValor());
//			
//			PropertyFilter filter = new PropertyFilter();
//			filter.setTheClass(DadoLote.class);
//			filter.addProperty("lote", Integer.parseInt(numeroLote) + 1);
//			Collection col = getFachada().consultarDadosLote(filter);
//			
//			setQtdRegistros(col.size()+"");
//		} catch (Exception e) {
//			
//			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
//					"Erro de Sistema", "");
//			getContextoApp().addMessage(null, msg);
//			setQtdRegistros("");
//			setNumeroLote("");
//		}
		return "mesma";
	}
}