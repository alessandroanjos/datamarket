package com.infinity.datamarket.comum.util;

import java.util.Hashtable;
import java.util.ResourceBundle;

import com.infinity.datamarket.pdv.gui.telas.Tela;
import com.infinity.datamarket.pdv.maquinaestados.Mic;

public class ServiceLocator {
	private Hashtable pool;
	private static ServiceLocator instancia;
	ResourceBundle rs;
	private ServiceLocator() {
		pool = new Hashtable();
		rs = ResourceBundle.getBundle("JNDI");
	}

	public static synchronized ServiceLocator getInstancia(){
		if (instancia == null){
			instancia = new ServiceLocator();
		}
		return instancia;
	}

	private synchronized Object getObject(String strClass){
		if (pool.containsKey(strClass)){
			return pool.get(strClass);
		}else{
			try{
				Class c = Class.forName(strClass);
				Object objeto = c.newInstance();
				pool.put(strClass, objeto);
				return objeto;
			}catch(ClassNotFoundException e){
				throw new SistemaException(e);
			}catch(IllegalAccessException e){
				throw new SistemaException(e);
			}catch(InstantiationException e){
				throw new SistemaException(e);
			}
		}
	}

	public Mic getMic(String strMic){
		return (Mic) getObject(strMic);
	}
	
	public IRepositorio getRepositoprio(String strRepositorio){
		return (IRepositorio) getObject(strRepositorio);
	}

	public Tela getTela(String tela){
		String strTela = rs.getString(tela);
		return (Tela) getObject(strTela);
	}
}
