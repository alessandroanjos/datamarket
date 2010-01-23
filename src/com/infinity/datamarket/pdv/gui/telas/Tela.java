package com.infinity.datamarket.pdv.gui.telas;

import java.awt.Color;
import java.io.Serializable;

import javax.swing.JPanel;

import com.infinity.datamarket.comum.util.ConcentradorParametro;
import com.infinity.datamarket.comum.util.Util;

public class Tela implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1868225148299818388L;
	private JPanel painel;

	protected static final Color backGround = new Color(226, 252, 252);
	
	public Tela(){
		this.painel = new JPanel();
		this.painel.setBounds(0,0,800,600);
		this.painel.setLayout(null);
	}

	public JPanel getPainel(){
		return this.painel;
	}

	static{

		LOGO_CLIENTE = Util.getDirCorrente() + "/logo.png";
		//ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.LOGO_CLIENTE).getValor();

	}
	public static final String LOGO_CLIENTE;
	
	static{

		LOGO_INFINITY = Util.getDirCorrente() + "/logo_infinity.jpg";

	}
	public static final String LOGO_INFINITY;
}
