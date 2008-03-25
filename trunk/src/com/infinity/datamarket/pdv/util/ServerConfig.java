package com.infinity.datamarket.pdv.util;

import java.util.ResourceBundle;

import javax.naming.InitialContext;

public class ServerConfig {
	
	public static final String INITIAL_CONTEXT_FACTORY;
	public static final String PROVIDER_URL;
	
	
	public static final String TRANSACTION_SERVER_JNDI;
	public static final int SLEEP;

	
	static{
		ResourceBundle rb = ResourceBundle.getBundle("ServerConfig");
		
		INITIAL_CONTEXT_FACTORY = rb.getString("INITIAL_CONTEXT_FACTORY"); 
		PROVIDER_URL = rb.getString("PROVIDER_URL");
		
		TRANSACTION_SERVER_JNDI = rb.getString("TRANSACTION_SERVER_JNDI");
		SLEEP = Integer.parseInt(rb.getString("SLEEP"));
		
	}
}
