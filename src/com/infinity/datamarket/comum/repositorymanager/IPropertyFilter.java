/*
 * Created on 01/12/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.infinity.datamarket.comum.repositorymanager;

import java.util.Collection;
import java.util.Map;

/**
 * @author wagner.medeiros
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface IPropertyFilter
{

    public abstract void addPropertyInterval(String s, Object obj, int i);

    public abstract boolean isIgnoreCase();

    public abstract void setIgnoreCase(boolean flag);

    public abstract void addPropertyIsNull(String s);

    public abstract Class getTheClass();

    public abstract void setTheClass(Class class1);

    public abstract void addProperty(String s, Object obj);

    public abstract void addOrderByProperty(String s, String s1);

    public abstract Map getProperties();

    public abstract Map getPropertiesInterval();

    public abstract Collection getPropertiesNull();

    public abstract Map getOrderBy();
}