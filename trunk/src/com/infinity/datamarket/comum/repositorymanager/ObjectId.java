/*
 * Created on 26/10/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.infinity.datamarket.comum.repositorymanager;


/**
 * @author wagner.medeiros
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ObjectId {
	private Long Id;

	public Long getId() {
		return Id;
	}
	public void setId(Long chave) {
		this.Id = chave;
	}

	public boolean equals(Object _obj){
		if (!(_obj instanceof ObjectId)){
			return false;
		}
		ObjectId obj = (ObjectId) _obj;
		if (!obj.Id.equals(this.Id)){
			return false;
		}
		return true;
	}
}
