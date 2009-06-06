package com.infinity.datamarket.pdv.util;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ServiceLocator {

	private static Hashtable prop;
	
	static{
	
		prop = new Hashtable (); 
		prop.put (InitialContext.INITIAL_CONTEXT_FACTORY, ServerConfig.INITIAL_CONTEXT_FACTORY); 
		prop.put (InitialContext.PROVIDER_URL,ServerConfig.PROVIDER_URL);
	
	}
	
	public static Object getJNDIObject(String jndi){
		try{
			Context ctx = new InitialContext (prop); 
			return  ctx.lookup (jndi);
		}catch(NamingException e){
			System.out.println("Não encontrado o servidor: "+jndi);
		}catch (Throwable e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
