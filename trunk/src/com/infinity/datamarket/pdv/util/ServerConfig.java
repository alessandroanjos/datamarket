package com.infinity.datamarket.pdv.util;

import java.util.ResourceBundle;

public class ServerConfig {
	
	public static final String INITIAL_CONTEXT_FACTORY;
	public static final String PROVIDER_URL;
	
	
	public static final String TRANSACTION_SERVER_JNDI;
	public static final String CLIENTE_SERVER_JNDI;
	public static final String AUTORIZADOR_SERVER_JNDI;
	public static final String LOTE_SERVER_JNDI;
	public static final String OPERACAO_SERVER_JNDI;
	public static final int SLEEP;
	
	public static final String QUEUE_INFO_COMPONENT_SERVER_JNDI;
	public static final String CONNECTION_FACTORY;
	public static final String HOST_SERVIDOR_ES;
	public static final String PORTA_SERVIDOR_ES;

	public static final String CONTEXTO_SERVIDOR_ES;
	public static final String SERVLET_GERADOR_BOLETO;
	public static final String SERVLET_RECEPTOR_TRANSACAO;

	
	
	static{
		ResourceBundle rb = ResourceBundle.getBundle("ServerConfig");
		
		INITIAL_CONTEXT_FACTORY = rb.getString("INITIAL_CONTEXT_FACTORY"); 
		PROVIDER_URL = rb.getString("PROVIDER_URL");
		
		TRANSACTION_SERVER_JNDI = rb.getString("TRANSACTION_SERVER_JNDI");
		LOTE_SERVER_JNDI = rb.getString("LOTE_SERVER_JNDI");
		AUTORIZADOR_SERVER_JNDI = rb.getString("AUTORIZADOR_SERVER_JNDI");
		CLIENTE_SERVER_JNDI = rb.getString("CLIENTE_SERVER_JNDI");
		OPERACAO_SERVER_JNDI = rb.getString("OPERACAO_SERVER_JNDI");
		
		SLEEP = Integer.parseInt(rb.getString("SLEEP"));
		
		QUEUE_INFO_COMPONENT_SERVER_JNDI = rb.getString("QUEUE_INFO_COMPONENT_SERVER_JNDI");
		CONNECTION_FACTORY = rb.getString("CONNECTION_FACTORY");

		HOST_SERVIDOR_ES = rb.getString("HOST_SERVIDOR_ES");
		PORTA_SERVIDOR_ES = rb.getString("PORTA_SERVIDOR_ES");


		CONTEXTO_SERVIDOR_ES= rb.getString("CONTEXTO_SERVIDOR_ES");
		SERVLET_GERADOR_BOLETO= rb.getString("SERVLET_GERADOR_BOLETO");
		SERVLET_RECEPTOR_TRANSACAO= rb.getString("SERVLET_RECEPTOR_TRANSACAO");
		
		
}
}
