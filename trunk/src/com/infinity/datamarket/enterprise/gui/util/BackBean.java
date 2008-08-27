package com.infinity.datamarket.enterprise.gui.util;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

import javax.faces.component.html.HtmlForm;

import com.infinity.datamarket.comum.Fachada;

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
	
	public String formataCpfCnpj(String cpfCnpf){
		String result = "";
		String temp = "";
		//formata cpf xx.xxx.xxx-xx
		if(cpfCnpf.length() <= 11){
			for(int i = cpfCnpf.length(); i < 11;i++){
				temp = temp + "0";
			}
			temp = temp.concat(cpfCnpf);
			result = temp.substring(0,3) + "." + temp.substring(3, 6) + "." + temp.substring(6, 9) + "-" + temp.substring(9);
		}else{
			//forma cnpj xx.xxx.xxx/xxxx-xx
			for(int i = cpfCnpf.length(); i < 14;i++){
				temp = temp + "0";
			}
			temp = temp.concat(cpfCnpf);
			result = temp.substring(0,2) + "." + temp.substring(2, 5) + "." + temp.substring(5, 8) + "/" + temp.substring(8, 12) + "-" + temp.substring(12);
		}		
		return result;
	}
}