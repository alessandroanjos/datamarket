

/*
 * TelaInicial.java
 *
 * Created on 24 de Janeiro de 2007, 13:28
 */

package com.infinity.datamarket.pdv.gui.telas;

import java.awt.Font;
import java.math.BigDecimal;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.infinity.datamarket.comum.util.StringUtil;

/**
 *
 * @author  wagner.medeiros
 */
public class TelaTroco extends TelaCupom{

    public TelaTroco() {
        initComponents();
    }


    private void initComponents() {



    	lb_troco = new JLabel("Troco");
    	lb_troco.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,30));
    	lb_troco.setBounds(370,120, 200,30);
    	painelCentral.add(lb_troco);

    	troco = new JTextField();
    	troco.setFont(new java.awt.Font("Courier New",Font.BOLD,42));
    	troco.setText("R$          0,00");
    	troco.setBounds(370, 160, 413, 90);
    	troco.setBackground(new java.awt.Color(255, 255, 255));
    	troco.setFocusable(false);
    	troco.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
    	troco.setEditable(false);
    	troco.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 5));
    	painelCentral.add(troco);


        getPainel().add(painelCentral);

    }


    private JTextField troco;
    private JLabel lb_troco;


	public void addTroco(BigDecimal valorTroco){
		this.troco.setText(StringUtil.numeroToString(valorTroco, 2, 0, ",", ".", true));
	}

	public static void main(String[] a){
		JFrame f = new JFrame();
		TelaTroco t = new TelaTroco();
		f.setSize(800, 600);
		//f.getContentPane().add(t.painelTop);
		f.getContentPane().add(t.painelCentral);
		f.setVisible(true);
	}

}
