/*
 * Created on 01/11/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.infinity.datamarket.comum.repositorymanager;

import java.io.Serializable;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;

import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Persistente;

/**
 * @author wagner.medeiros
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RepositoryManagerBuffer extends RepositoryManagerHibernate{

	private Hashtable objects;
	private boolean getAll;

//	private static RepositoryManagerBuffer instance;
//
//	private RepositoryManagerBuffer(){
//
//	}
//
//	public static RepositoryManagerBuffer getInstance()
//    {
//        if(instance == null)
//        {
//            instance = new RepositoryManagerBuffer();
//        }
//        return instance;
//    }

	public void update(Persistente _object) throws AppException {
		if (_object == null){
			throw new InvalidObjectException("Invalid argument - "+_object);
		}
		super.update(_object);
		objects.put(_object.getId(),_object);
	}

	public void remove(Persistente _object) throws AppException {
		if (_object == null){
			throw new InvalidObjectException("Invalid argument - "+_object);
		}
		super.remove(_object);
		objects.remove(_object.getId());
	}



	 public Serializable findById(Class _class, Object _id)
     throws AppException
	 {
		 Serializable obj = (Persistente) objects.get(_id);
		if (obj != null){
			return obj;
		}else{
			obj = super.findById(_class, _id);
			objects.put(obj,obj);
			return obj;

		}
	}

	public Collection findAll(Class _class) throws AppException {
		if (getAll){
			return objects.values();
		}else{
			Collection allObjects = super.findAll(_class);
			Enumeration e = objects.elements();
			while(e.hasMoreElements()){
				ObjectId obj = (ObjectId) e.nextElement();
				objects.put(obj.getId(),obj);
			}
			getAll = true;
			return allObjects;
		}
	}
}
