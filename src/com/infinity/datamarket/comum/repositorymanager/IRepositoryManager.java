/*
 * Created on 03/11/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.infinity.datamarket.comum.repositorymanager;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.infinity.datamarket.comum.util.AppException;

/**
 * @author wagner.medeiros
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface IRepositoryManager {

	public void update(Serializable _object) throws AppException;
	public void merge(Serializable _object) throws AppException;
	public void insert(Serializable _object) throws AppException;
	public void insertOrUpdate(Serializable _object) throws AppException;
	public void remove(Serializable _object) throws AppException;
	public void removeById(Class _class, Object _id) throws AppException;
	public Serializable findById(Class _class, Object _id) throws AppException;
	public Collection findAll(Class _class)throws AppException;
	public List filter(IPropertyFilter _filter, boolean _precise) throws AppException;

}
