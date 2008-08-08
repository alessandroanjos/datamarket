package com.infinity.datamarket.enterprise.gui.lote;

import java.util.Collection;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;

import com.infinity.datamarket.comum.lote.DadoLote;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.ConcentradorParametro;
import com.infinity.datamarket.comum.util.Parametro;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

public class LoteBackBean extends BackBean{

	private String numeroLote;
	private String qtdRegistros;
	public String getNumeroLote() {
		return numeroLote;
	}
	public void setNumeroLote(String numeroLote) {
		this.numeroLote = numeroLote;
	}
	public String getQtdRegistros() {
		return qtdRegistros;
	}
	public void setQtdRegistros(String qtdRegistros) {
		this.qtdRegistros = qtdRegistros;
	}
	
	
	public void resetBB(){
		this.numeroLote = "";
		this.qtdRegistros = "";
	}
	
	public String voltarMenu(){
		resetBB();
		return "voltar";
	}
	
	public String liberarLote(){
		if (this.numeroLote == null || this.qtdRegistros == null || this.numeroLote.equals("") || this.qtdRegistros.equals("")){
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Consulte a Próxima Liberação Antes de Liberar", "");
			ctx.addMessage(null, msg);
		}else if (Integer.parseInt(getQtdRegistros()) > 0){
			try {
				Parametro param = new Parametro();
				param.setChave(ConcentradorParametro.LOTE);
				int novoLote = Integer.parseInt(numeroLote )+1;
				param.setValorInteiro(novoLote);
				ConcentradorParametro.getInstancia().atualizarParametro(param);
				FacesContext ctx = FacesContext.getCurrentInstance();
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Operação Realizada com Sucesso!", "");
				ctx.addMessage(null, msg);
				setNumeroLote(novoLote+"");
				setQtdRegistros("0");
			} catch (Exception e) {
				FacesContext ctx = FacesContext.getCurrentInstance();
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Erro de Sistema", "");
				ctx.addMessage(null, msg);
			}
		}else{
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Não ha registros a serem liberados", "");
			ctx.addMessage(null, msg);
		}
		return "mesma";
	}
	
	public String consultarLote(){
		try {
			Parametro param = ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.LOTE);
			setNumeroLote(param.getValor());
			
			PropertyFilter filter = new PropertyFilter();
			filter.setTheClass(DadoLote.class);
			filter.addProperty("lote", Integer.parseInt(numeroLote) + 1);
			Collection col = getFachada().consultarDadosLote(filter);
			
			setQtdRegistros(col.size()+"");
		} catch (Exception e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema", "");
			ctx.addMessage(null, msg);
			setQtdRegistros("");
			setNumeroLote("");
		}
		return "mesma";
	}
}
