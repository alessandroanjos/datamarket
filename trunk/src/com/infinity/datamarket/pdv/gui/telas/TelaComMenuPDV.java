package com.infinity.datamarket.pdv.gui.telas;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.infinity.datamarket.comum.util.Util;

public abstract class TelaComMenuPDV extends TelaComMenu {

	   
	private ArrayList listaLayoutMenu = new ArrayList();
	
	public void adicionarLayoutMenu(String layout) {
		javax.swing.JLabel labelTotalDesconto = new javax.swing.JLabel();

		getPanel( ).add(labelTotalDesconto);
        labelTotalDesconto.setText(layout);
        //labelTotalDesconto.setBackground(new java.awt.Color(0, 0, 100));
        labelTotalDesconto.setForeground(new java.awt.Color(255, 255, 255));

        labelTotalDesconto.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,11));

		int colunaX  = (int)(listaLayoutMenu.size()/6);
        int colunaY  = (int)(listaLayoutMenu.size()%6);

		labelTotalDesconto.setBounds(400 + ((colunaX) * 225), 5 + ((colunaY)* 15), 220, 11);
		
		listaLayoutMenu.add(labelTotalDesconto);
		
		getPanel( ).repaint();
	}

	public void apagarLayoutMenu(){
		if (listaLayoutMenu == null) {
			listaLayoutMenu = new ArrayList();
		}
		for (int i = 0; i < listaLayoutMenu.size(); i++) {
			javax.swing.JLabel labelTotalDesconto = (javax.swing.JLabel)listaLayoutMenu.get(i);
			getPanel( ).remove(labelTotalDesconto);
		}
		listaLayoutMenu.clear();

		getPanel( ).repaint();
	}
	
	public abstract JPanel getPanel( );
	
}