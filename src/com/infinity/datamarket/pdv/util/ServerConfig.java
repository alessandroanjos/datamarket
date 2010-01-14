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
	public static final String SERVLET_TRANSMISSOR_LOTE;
	public static final String SERVLET_VERIFICADOR_NOVO_LOTE;
	public static final String SERVLET_CONSULTAR_NOVO_CARGA_BASE;
	public static final String SERVLET_VERIFICADOR_NOVA_CARGA_BASE;
	public static final String SERVLET_APAGAR_NOVA_CARGA_BASE;
	public static final String SERVLET_GERA_NOVA_CARGA_BASE;
	

	public static final String CONSULTAR_CLIENTE_TRANSACAO_SERVLET;//=ConsultarClienteTransacaoServlet.servlet
	public static final String SALVAR_INFO_COMPONENTE_SERVLET;//=SalvarInfoComponenteServlet.servlet
	public static final String CONSULTAR_OPERACA_SERVLET;//=ConsultarOperacaoServlet.servlet
	public static final String CONSULTAR_OPERACOES_SERVLET;//=ConsultarOperacoesServlet.servlet
	public static final String ALTERAR_OPERACA_SERVLET;//=AlterarOperacaoServlet.servlet
	public static final String AUTORIZAR_TRANSACAO_CARTAO_PROPRIO_SERVLET;//=AutorizarTransacaoCartaoProprioServlet.servlet
	public static final String CONFIRMAR_TRANSACAO_CARTAO_PROPRIO_SERVLET;//=ConfirmarTransacaoCartaoProprioServlet.servlet
	public static final String DESFAZER_TRANSACAO_CARTAO_PROPRIO_SERVLET;//=DesfazerTransacaoCartaoProprioServlet.servlet
	public static final String CONSULTAR_DEBITOS_CARTAO_PROPRIO_SERVLET;//=ConsultarDebitosCartaoProprioServlet.servlet
	public static final String RECEPTOR_OPERACAO_SERVLET;//=ConsultarDebitosCartaoProprioServlet.servlet
	public static final String ATUALIZA_OPERACAO_EM_SEPARACAO;//=ConsultarDebitosCartaoProprioServlet.servlet
	
	
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
		SERVLET_TRANSMISSOR_LOTE= rb.getString("SERVLET_TRANSMISSOR_LOTE");
		SERVLET_VERIFICADOR_NOVO_LOTE= rb.getString("SERVLET_VERIFICADOR_NOVO_LOTE");

		CONSULTAR_CLIENTE_TRANSACAO_SERVLET= rb.getString("CONSULTAR_CLIENTE_TRANSACAO_SERVLET");
		SALVAR_INFO_COMPONENTE_SERVLET= rb.getString("SALVAR_INFO_COMPONENTE_SERVLET");
		CONSULTAR_OPERACA_SERVLET= rb.getString("CONSULTAR_OPERACA_SERVLET");
		ALTERAR_OPERACA_SERVLET= rb.getString("ALTERAR_OPERACA_SERVLET");
		AUTORIZAR_TRANSACAO_CARTAO_PROPRIO_SERVLET= rb.getString("AUTORIZAR_TRANSACAO_CARTAO_PROPRIO_SERVLET");
		CONFIRMAR_TRANSACAO_CARTAO_PROPRIO_SERVLET= rb.getString("CONFIRMAR_TRANSACAO_CARTAO_PROPRIO_SERVLET");
		DESFAZER_TRANSACAO_CARTAO_PROPRIO_SERVLET= rb.getString("DESFAZER_TRANSACAO_CARTAO_PROPRIO_SERVLET");
		CONSULTAR_DEBITOS_CARTAO_PROPRIO_SERVLET= rb.getString("CONSULTAR_DEBITOS_CARTAO_PROPRIO_SERVLET");
		SERVLET_CONSULTAR_NOVO_CARGA_BASE= rb.getString("SERVLET_CONSULTAR_NOVO_CARGA_BASE");
		SERVLET_VERIFICADOR_NOVA_CARGA_BASE= rb.getString("SERVLET_VERIFICADOR_NOVA_CARGA_BASE");
		
		SERVLET_APAGAR_NOVA_CARGA_BASE= rb.getString("SERVLET_APAGAR_NOVA_CARGA_BASE");
		CONSULTAR_OPERACOES_SERVLET=rb.getString("CONSULTAR_OPERACOES_SERVLET");
		SERVLET_GERA_NOVA_CARGA_BASE=rb.getString("SERVLET_GERA_NOVA_CARGA_BASE");
		RECEPTOR_OPERACAO_SERVLET=rb.getString("RECEPTOR_OPERACAO_SERVLET");

		ATUALIZA_OPERACAO_EM_SEPARACAO=rb.getString("ATUALIZA_OPERACAO_EM_SEPARACAO");
	}
}