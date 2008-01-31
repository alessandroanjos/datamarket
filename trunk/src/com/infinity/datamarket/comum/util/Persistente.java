/*
 * Created on 23/04/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.infinity.datamarket.comum.util;

import java.io.Serializable;


/**
 * @author wagner.medeiros
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Persistente implements Serializable{

	private Long id;

	public Persistente(){

	}

	public Persistente(Long id){
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public boolean equals(Object obj){
		if (!(obj instanceof Persistente)){
			return false;
		}else{
			Persistente per = (Persistente) obj;
			return (this.id.equals(per.id));
		}
	}
}
