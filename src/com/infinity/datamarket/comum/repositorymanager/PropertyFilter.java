package com.infinity.datamarket.comum.repositorymanager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class PropertyFilter implements IPropertyFilter{

	public class IntervalObject
	{

	    private int comparationType;
	    private String propertyName;
	    public static final int MENOR = 1;
	    public static final int MENOR_IGUAL = 2;
	    public static final int MAIOR = 3;
	    public static final int MAIOR_IGUAL = 4;
	    public static final int NAO_NULO = 5;
	    public static final int NULO = 6;
	    public static final int DIFERENTE = 7;

	    public int getComparationType()
	    {
	        return comparationType;
	    }

	    public void setComparationType(int _comparationType)
	    {
	        comparationType = _comparationType;
	    }

	    public String getPropertyName()
	    {
	        return propertyName;
	    }

	    public void setPropertyName(String _propertyName)
	    {
	        propertyName = _propertyName;
	    }

	    public IntervalObject(String _propertyName, int _comparationType)
	    {
	        setComparationType(_comparationType);
	        setPropertyName(_propertyName);
	    }
	}


	private boolean ignoreCase;
	public static final String ASC = "ASC";
	public static final String DESC = "DESC";
	private Map properties;
	private Map propertiesInterval;
	private Collection propertiesNull;
	private Class theClass;
	private Map orderBy;

	public PropertyFilter()
	{
	    ignoreCase = true;
	}

	public Class getTheClass()
	{
	    return theClass;
	}

	public void setTheClass(Class _class)
	{
	    theClass = _class;
	}

	public boolean isIgnoreCase()
	{
	    return ignoreCase;
	}

	public void setIgnoreCase(boolean ignoreCase)
	{
	    this.ignoreCase = ignoreCase;
	}

	public void addProperty(String _propertyName, Object _propertyValue)
	{
	    if(properties == null)
	    {
	        properties = new HashMap();
	    }
	    properties.put(_propertyName, _propertyValue);
	}

	public void addPropertyIsNull(String _propertyName)
	{
	    if(propertiesNull == null)
	    {
	        propertiesNull = new Vector();
	    }
	    propertiesNull.add(_propertyName);
	}

	public void addPropertyInterval(String _propertyName, Object _propertyValue, int _comparationType)
	{
	    if(propertiesInterval == null)
	    {
	        propertiesInterval = new HashMap();
	    }
	    IntervalObject interval = new IntervalObject(_propertyName, _comparationType);
	    propertiesInterval.put(interval, _propertyValue);
	}

	public void addOrderByProperty(String _propertyName, String _order)
	{
	    if(orderBy == null)
	    {
	        orderBy = new HashMap();
	    }
	    if(_order != null && (_order.equals(DESC) || _order.equals(ASC)))
	    {
	        orderBy.put(_propertyName, _order);
	    } else
	    {
	        orderBy.put(_propertyName, ASC);
	    }
	}

	public Map getProperties()
	{
	    return properties;
	}

	public Map getPropertiesInterval()
	{
	    return propertiesInterval;
	}

	public void setPropertiesInterval(Map propertiesInterval)
	{
	    this.propertiesInterval = propertiesInterval;
	}

	public Collection getPropertiesNull()
	{
	    return propertiesNull;
	}

	public Map getOrderBy()
	{
	    return orderBy;
	}
}