package com.infinity.datamarket.comum.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ObjetoClonavel implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6498318513109918453L;

	
	public static Object clone(Object obj) {
		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bout);
			out.writeObject(obj);
			out.close();
			ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
			ObjectInputStream in = new ObjectInputStream(bin);
			Object ret = in.readObject();
			in.close();
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
