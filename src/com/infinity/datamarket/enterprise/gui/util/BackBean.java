package com.infinity.datamarket.enterprise.gui.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

import javax.faces.component.html.HtmlForm;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.usuario.Usuario;

public class BackBean {
	
	public static final String INSERIR = "I";
	public static final String ALTERAR = "A";
	public static final String CONSULTAR = "C";
	public static final String EXCLUIR   = "E";
	
	public static final String ATIVO = "S";
	public static final String INATIVO   = "N";
	
	public static final String ACAO = "acao";
	
	public static final String VALOR_ACAO = "init";
	
	public static Properties mensagens;
	
	public static String codigoUsuarioLogado;
	
	public HtmlForm init;
	
	Date dataSistema;
	TimeZone timeZone;
	
	public Fachada getFachada(){
		return Fachada.getInstancia();
	}
	
	public static String retornaTituloPagina(String chave){
		String valor = "";
		
		try {
			if(mensagens == null){
				mensagens.load(BackBean.class.getResourceAsStream("/resources/mensagens.properties"));
			}
			valor = mensagens.getProperty(chave);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return valor;
	}
	
	
	Boolean existeRegistros;

	public Boolean getExisteRegistros() {
		return existeRegistros;
	}

	public void setExisteRegistros(Boolean existeRegistros) {
		this.existeRegistros = existeRegistros;
	}
	
	public Date getDataSistema(){
		return new Date(System.currentTimeMillis());
	}

	public void setDataSistema(Date dataSistema){
		this.dataSistema = dataSistema;
	}

	public TimeZone getTimeZone() {
		return TimeZone.getDefault();
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	/**
	 * @return the codigoUsuarioLogado
	 */
	public static String getCodigoUsuarioLogado() {
		return codigoUsuarioLogado;
	}

	/**
	 * @param codigoUsuarioLogado the codigoUsuarioLogado to set
	 */
	public static void setCodigoUsuarioLogado(String codigoUsuarioLogado) {
		BackBean.codigoUsuarioLogado = codigoUsuarioLogado;
	}

	/**
	 * @return the init
	 */
	public HtmlForm getInit() {
		return init;
	}

	/**
	 * @param init the init to set
	 */
	public void setInit(HtmlForm init) {
		this.init = init;
	}
}