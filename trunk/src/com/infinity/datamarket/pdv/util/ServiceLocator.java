package com.infinity.datamarket.pdv.util;

import java.util.Hashtable;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.infinity.datamarket.transaction.TransactionServerRemote;

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
			System.out.println("Não encontrado o nome: "+jndi);
		}catch (Throwable e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
