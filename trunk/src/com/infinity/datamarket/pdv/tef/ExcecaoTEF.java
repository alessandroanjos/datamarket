package com.infinity.datamarket.pdv.tef;

public class ExcecaoTEF extends Exception{
	
	public ExcecaoTEF(Exception e){
		super(e);
	}
	public ExcecaoTEF(String msg, Exception e){
		super(msg,e);
	}
	public ExcecaoTEF(String msg){
		super(msg);
	}
}
