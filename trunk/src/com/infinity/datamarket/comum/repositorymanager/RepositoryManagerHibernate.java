/*
 * Created on 01/12/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.infinity.datamarket.comum.repositorymanager;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.exception.ConstraintViolationException;

import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Persistente;

/**
 * @author wagner.medeiros
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RepositoryManagerHibernate implements IRepositoryManager
{

//    private static RepositoryManagerHibernate instance;
//
//    private RepositoryManagerHibernate(){
//
//    }
//
//    public static RepositoryManagerHibernate getInstance()
//    {
//        if(instance == null)
//        {
//            instance = new RepositoryManagerHibernate();
//        }
//        return instance;
//    }

    public Collection findAll(Class _class)
        throws AppException
    {
        Session session = null;
        List col;
        try
        {
            String className = _class.getName();
            String hql = "from " + className;
            session = RepositoryManagerHibernateUtil.currentSession();
            Query query = session.createQuery(hql);
            col = query.list();
        }
        catch(HibernateException e)
        {
            throw new RepositoryControlerException(e);
        }
        return col;
    }

    public void insert(Serializable _obj)
        throws AppException
    {
    	try{
    		if (_obj instanceof Persistente){
        		findById(_obj.getClass(), ((Persistente)_obj).getId());
        		throw new ObjectExistentException("Object Existent");
        	}
    	}catch(com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException e){
    		
    	}catch(java.lang.IllegalArgumentException e){
    		
    	}catch(HibernateException ex)
        {
            throw new RepositoryControlerException(ex);
        }
    	
        try
        {        	
            Session session = null;
            session = RepositoryManagerHibernateUtil.currentSession();
            session.save(_obj);
        }
        catch(HibernateException ex)
        {
            throw new RepositoryControlerException(ex);
        }
    }
    
    public void insertOrUpdate(Serializable _obj)
    throws AppException{
    try
    {
        Session session = null;
        session = RepositoryManagerHibernateUtil.currentSession();
        session.save(_obj);
    }
    catch(HibernateException ex)
    {
    	throw new RepositoryControlerException(ex);
    }
}

    public void update(Serializable _obj)
        throws AppException
    {
        try
        {
            Session session = null;
            session = RepositoryManagerHibernateUtil.currentSession();
            session.update(_obj);
        }
        catch(HibernateException ex)
        {
            throw new RepositoryControlerException(ex);
        }
    }

    public void remove(Serializable _obj)
        throws AppException
    {
        try
        {
            Session session = null;
            session = RepositoryManagerHibernateUtil.currentSession();
            session.delete(_obj);
        }
        catch(HibernateException ex)
        {
            throw new RepositoryControlerException(ex);
        }
    }

    public void removeById(Class _class, Object _id)
        throws AppException
    {
        try
        {
        	Serializable obj = findById(_class, _id);
            remove(obj);
        }
        catch(Exception ex)
        {
            throw new RepositoryControlerException("Removing by id", ex);
        }
    }

    public Serializable findById(Class _class, Object _id)
        throws AppException
    {
        Object obj = null;
        Session session = null;
        try
        {
            session = RepositoryManagerHibernateUtil.currentSession();
            obj = session.load(_class, (Serializable)_id);
        }
        catch (ObjectNotFoundException e) {
        	throw new com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException(e);
    	}
        catch(HibernateException e)
        {
            throw new RepositoryControlerException(e);
        }
        return (Serializable)obj;
    }

    public List filter(IPropertyFilter _filter, boolean _precise)
        throws AppException
    {
        List list = null;
        Map attributes = _filter.getProperties();
        Collection nullAttributes = _filter.getPropertiesNull();
        Map intervalAttributes = _filter.getPropertiesInterval();
        Session session = null;
        try
        {
            session = RepositoryManagerHibernateUtil.currentSession();
            Criteria criteria = session.createCriteria(_filter.getTheClass());
            if(attributes != null)
            {
                Set keys = attributes.keySet();
                Iterator it = keys.iterator();
                Map criterias = new Hashtable();
                do
                {
                    if(!it.hasNext())
                    {
                        break;
                    }
                    String attributeName = (String)it.next();
                    Object attributeValue = attributes.get(attributeName);
                    if(attributeName.indexOf(".") != -1 && (!attributeName.endsWith(".id") || attributeName.indexOf(".") != attributeName.lastIndexOf(".")))
                    {
                        criterias = joinTables(criterias, criteria, attributeName, attributeValue, _filter.isIgnoreCase(), _precise);
                    } else
                    if((attributeValue instanceof String))
                    {
                        if(_precise)
                        {
                            if(_filter.isIgnoreCase())
                            {
                                criteria.add(Expression.ilike(attributeName, attributeValue));
                            } else
                            {
                                criteria.add(Expression.like(attributeName, attributeValue));
                            }
                        } else
                        if(_filter.isIgnoreCase())
                        {
                            attributeValue = "%" + attributeValue + "%";
                            criteria.add(Expression.ilike(attributeName, attributeValue));
                        } else
                        {
                            attributeValue = "%" + attributeValue + "%";
                            criteria.add(Expression.like(attributeName, attributeValue));
                        }
                    } else
                    if((attributeValue instanceof Number) && ((Number)attributeValue).shortValue() != 0)
                    {
                        criteria.add(Expression.eq(attributeName, attributeValue));
                    } else
                    if(attributeValue instanceof Date)
                    {
                        criteria.add(Expression.eq(attributeName, attributeValue));
                    } else
                    if(attributeValue instanceof Boolean)
                    {
                        criteria.add(Expression.eq(attributeName, attributeValue));
                    } else
                    if(attributeValue instanceof Boolean)
                    {
                        criteria.add(Expression.eq(attributeName, attributeValue));
                    }
                } while(true);
            }
            if(nullAttributes != null)
            {
                String attributeName;
                for(Iterator itNull = nullAttributes.iterator(); itNull.hasNext(); criteria.add(Expression.isNull(attributeName)))
                {
                    attributeName = (String)itNull.next();
                }

            }
            if(intervalAttributes != null)
            {
                Set intervals = intervalAttributes.keySet();
                Iterator itIntervals = intervals.iterator();
                do
                {
                    if(!itIntervals.hasNext())
                    {
                        break;
                    }
                    PropertyFilter.IntervalObject interval = (PropertyFilter.IntervalObject)itIntervals.next();
                    Object attributeValue = intervalAttributes.get(interval);
                    if(interval.getComparationType() == 3)
                    {
                        criteria.add(Expression.gt(interval.getPropertyName(), attributeValue));
                    } else
                    if(interval.getComparationType() == 4)
                    {
                        criteria.add(Expression.ge(interval.getPropertyName(), attributeValue));
                    } else
                    if(interval.getComparationType() == 1)
                    {
                        criteria.add(Expression.lt(interval.getPropertyName(), attributeValue));
                    } else
                    if(interval.getComparationType() == 2)
                    {
                        criteria.add(Expression.le(interval.getPropertyName(), attributeValue));
                    }
                    if(interval.getComparationType() == 5)
                    {
                        criteria.add(Expression.isNotNull(interval.getPropertyName()));
                       
                    }

                } while(true);
            }
            Map orders = _filter.getOrderBy();
            if(orders != null)
            {
                Set keys = orders.keySet();
                Iterator it = keys.iterator();
                do
                {
                    if(!it.hasNext())
                    {
                        break;
                    }
                    String attribute = (String)it.next();
                    Object order = _filter.getOrderBy().get(attribute);
                    if(order.equals("ASC"))
                    {
                        criteria.addOrder(Order.asc(attribute));
                    } else
                    if(order.equals("DESC"))
                    {
                        criteria.addOrder(Order.desc(attribute));
                    }
                } while(true);
            }
            list = criteria.list();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            throw new RepositoryControlerException("Creating the Filter", ex);
        }
        return list;
    }

    private Map joinTables(Map criterias, Criteria _cri, String _attributeName, Object _attributeValue, boolean _ignoreCase, boolean _precise)
        throws HibernateException
    {
        String root = _attributeName.substring(0, _attributeName.indexOf("."));
        String attribute = _attributeName.substring(_attributeName.indexOf(".") + 1);
        Criteria criteria;
        if(criterias.containsKey(root))
        {
            criteria = (Criteria)criterias.get(root);
        } else
        {
            criteria = _cri.createCriteria(root);
            criterias.put(root, criteria);
        }
        if(attribute.indexOf(".") != -1 && (!attribute.endsWith(".id") || attribute.indexOf(".") != attribute.lastIndexOf(".")))
        {
            criterias = joinTables(criterias, criteria, attribute, _attributeValue, _ignoreCase, _precise);
        } else
        if((_attributeValue instanceof String))
        {
            if(_precise)
            {
                if(_ignoreCase)
                {
                    criteria.add(Expression.ilike(attribute, _attributeValue));
                } else
                {
                    criteria.add(Expression.like(attribute, _attributeValue));
                }
            } else
            if(_ignoreCase)
            {
                _attributeValue = "%" + _attributeValue + "%";
                criteria.add(Expression.ilike(attribute, _attributeValue));
            } else
            {
                _attributeValue = "%" + _attributeValue + "%";
                criteria.add(Expression.like(attribute, _attributeValue));
            }
        } else
        if((_attributeValue instanceof Number) && ((Number)_attributeValue).shortValue() != 0)
        {
            criteria.add(Expression.eq(attribute, _attributeValue));
        } else
        if(_attributeValue instanceof Date)
        {
            criteria.add(Expression.eq(attribute, _attributeValue));
        } else
        if(_attributeValue instanceof Boolean)
        {
            criteria.add(Expression.eq(attribute, _attributeValue));
        } else
        if(_attributeValue instanceof Boolean)
        {
            criteria.add(Expression.eq(attribute, _attributeValue));
        }
        return criterias;
    }
}