/*
 * Created on 01/12/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.infinity.datamarket.comum.repositorymanager;

import java.util.Iterator;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * @author wagner.medeiros
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RepositoryManagerHibernateUtil {

	public static String HIBERNATE = "hibernate.cfg.xml";
	public static String HIBERNATE_PDV = "hibernate.cfg.pdv.xml";

    public  final SessionFactory sessionFactory;
    public  final ThreadLocal session = new ThreadLocal();
    public  final ThreadLocal transation = new ThreadLocal();
	private  final Configuration configuration;

	public static String arquivoHibernate;
	public static Properties properties;
	private static RepositoryManagerHibernateUtil instancia;
	private RepositoryManagerHibernateUtil () {
        try {
        	if (arquivoHibernate == null) {
        		arquivoHibernate = HIBERNATE;
        	}
			configuration = new Configuration().configure("/"+ arquivoHibernate);
			if (properties != null) {
				Iterator it = properties.keySet().iterator();
				while(it.hasNext()) {
					String key = it.next().toString();
					String valor = properties.getProperty(key);
					configuration.setProperty(key,valor);	
				}
			}
			sessionFactory = configuration.buildSessionFactory();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			throw new RuntimeException("Configuration Problem: "
					+ ex.getMessage(), ex);
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new RuntimeException("Configuration Problem: "
					+ ex.getMessage(), ex);
		}
	}

	public static RepositoryManagerHibernateUtil getInstancia() {
		if (instancia == null) {
			instancia = new RepositoryManagerHibernateUtil();
		}
		return instancia;
	}

	public  Configuration getConfiguration() throws HibernateException {
		 return configuration;
	}

    public  Session currentSession() throws HibernateException{
    	Session s = (Session)session.get();
        if(s == null || !s.isOpen())
        {
            s = sessionFactory.openSession();
            session.set(s);
        } else
        if(!s.isConnected())
        {
            s.reconnect();
        }
        return s;
    }

    public  void beginTrasaction()
        throws HibernateException
    {
        try{
	    	Transaction t = (Transaction)transation.get();
	        if(t == null)
	        {
	            t = currentSession().beginTransaction();
	            transation.set(t);
	        }
        }catch(Throwable e){
        	e.printStackTrace();
        }
    }

    public  void commitTransation()
        throws HibernateException
    {
        Transaction t = (Transaction)transation.get();
        try
        {
            if(t != null && !t.wasCommitted() && !t.wasRolledBack())
            {
                t.commit();
                transation.set(null);
//                closeSession();
            }
        }
        catch(HibernateException e)
        {
            rollbackTransation();
            throw new HibernateException(e);
        }
    }

    public  void rollbackTransation()
        throws HibernateException
    {
        Exception exception;
        Transaction t = (Transaction)transation.get();
        try
        {
            if(t != null && !t.wasCommitted() && !t.wasRolledBack())
            {
                t.rollback();
                transation.set(null);
            }
        }
        catch(HibernateException e)
        {
            throw new HibernateException(e);
        }
        finally
        {
//            closeSession();
        }
    }

    public  void closeSession()
        throws HibernateException
    {
        try{
	    	Session s = (Session)session.get();
	        session.set(null);
	        if(s != null)
	        {
	            s.close();
	        }
        }catch(Throwable e){
        	e.printStackTrace();
        }
    }

    public  void closeSession(Session s)
    throws HibernateException
{
    try{
        if(s != null)
        {
            s.close();
        }
    }catch(Throwable e){
    	e.printStackTrace();
    }
}

    public  Session newSession()
        throws HibernateException
    {
        Session s = sessionFactory.openSession();
        return s;
    }

    public  void closeQuerySession()
        throws HibernateException
    {
        Session s = (Session)session.get();
        session.set(null);
        if(s != null)
        {
            s.close();
        }
    }
}