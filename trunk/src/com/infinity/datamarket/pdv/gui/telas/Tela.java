package com.infinity.datamarket.pdv.gui.telas;

import java.io.Serializable;

import javax.swing.JPanel;

import com.infinity.datamarket.comum.util.ConcentradorParametro;

public class Tela implements Serializable{
	private JPanel painel;

	public Tela(){
		this.painel = new JPanel();
		this.painel.setBounds(0,0,800,600);
		this.painel.setLayout(null);
	}

	public JPanel getPainel(){
		return this.painel;
	}

	static{

		LOGO_CLIENTE = ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.LOGO_CLIENTE).getValor();

	}
	public static final String LOGO_CLIENTE;
}
