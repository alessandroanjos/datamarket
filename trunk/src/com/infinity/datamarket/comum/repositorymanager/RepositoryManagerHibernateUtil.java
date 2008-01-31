/*
 * Created on 01/12/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.infinity.datamarket.comum.repositorymanager;

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



    public static final SessionFactory sessionFactory;
    public static final ThreadLocal session = new ThreadLocal();
    public static final ThreadLocal transation = new ThreadLocal();


    public static Session currentSession() throws HibernateException{
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

    public static void beginTrasaction()
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

    public static void commitTransation()
        throws HibernateException
    {
        Transaction t = (Transaction)transation.get();
        try
        {
            if(t != null && !t.wasCommitted() && !t.wasRolledBack())
            {
                t.commit();
                transation.set(null);
                closeSession();
            }
        }
        catch(HibernateException e)
        {
            rollbackTransation();
            throw new HibernateException(e);
        }
    }

    public static void rollbackTransation()
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
            closeSession();
        }
    }

    public static void closeSession()
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

    public static void closeSession(Session s)
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

    public static Session newSession()
        throws HibernateException
    {
        Session s = sessionFactory.openSession();
        return s;
    }

    public static void closeQuerySession()
        throws HibernateException
    {
        Session s = (Session)session.get();
        session.set(null);
        if(s != null)
        {
            s.close();
        }
    }

    static
    {
        try
        {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        catch(HibernateException ex)
        {
        	ex.printStackTrace();
            throw new RuntimeException("Configuration Problem: " + ex.getMessage(), ex);
        }
        catch(Throwable ex)
        {
        	ex.printStackTrace();
            throw new RuntimeException("Configuration Problem: " + ex.getMessage(), ex);
        }
    }
}

