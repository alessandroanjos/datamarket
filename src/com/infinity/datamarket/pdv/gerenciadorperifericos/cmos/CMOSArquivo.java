package com.infinity.datamarket.pdv.gerenciadorperifericos.cmos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

import com.infinity.datamarket.comum.util.SistemaException;

public class CMOSArquivo implements CMOS,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 0L;
	private File cmos;
	private HashMap hash;


	

	public CMOSArquivo(){

		
		

	}
	
	public void setArquivo(String arquivo){
		cmos = new File(arquivo);
		hash = (HashMap) lerCmos();
	}

	

	public Object ler(String chave){
		return hash.get(chave);
	}

	private synchronized Object lerCmos(){
		Object result;
		try{
			synchronized (cmos) {
				if (cmos.length() != 0){
					byte[] b = new byte[(int) cmos.length()];
					FileInputStream i = new FileInputStream(cmos);
					i.read(b);
					ByteArrayInputStream barr = new ByteArrayInputStream(b);
					ObjectInputStream objInp = new ObjectInputStream(barr);
					result = objInp.readObject();
					objInp.close();
					i.close();
				}else{
					return new HashMap();
				}
			}
		}catch(Exception e){
			throw new SistemaException(e);
		}
		return result;
	}

	public void gravar(String chave, Object valor){
		hash.put(chave, valor);
//		Thread t = new Thread(){
//			public void run(){
				gravaCmos(hash);
//			} 
//		};
//		t.start();
		
	}

	private synchronized void gravaCmos(Object obj){
		try{
//			Map map = Collections.synchronizedMap((Map)obj);  
			synchronized (obj) {
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				ObjectOutputStream out = new ObjectOutputStream(bout);				
				out.writeObject(obj);
				out.close();
				byte[] bytes = bout.toByteArray();
				FileOutputStream fout = new FileOutputStream(cmos);
				fout.write(bytes);
				fout.close();
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new SistemaException(e);
		}
	}

}
