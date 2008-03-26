package com.infinity.datamarket.pdv.infocomponent;

import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;

public class ThreadEnviaInfoComponent extends Thread{
	private GerenciadorPerifericos gp;
	
	public ThreadEnviaInfoComponent(GerenciadorPerifericos gp){
		this.gp = gp;
	}
	
	public void run(){
		
	}
	
}
