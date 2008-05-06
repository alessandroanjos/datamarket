

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
import javax.swing.JTextPane;

import com.infinity.datamarket.comum.util.StringUtil;

/**
 *
 * @author  wagner.medeiros
 */
public class TelaTotal extends TelaCupom{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -7511489337433445282L;


	public TelaTotal() {
        initComponents();
    }


    private void initComponents() {

    	lb_total = new JLabel("Total");
    	lb_total.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
    	lb_total.setBounds(370,0, 200, 20);
    	painelCentral.add(lb_total);

    	total = new JTextField();
    	total.setFont(new java.awt.Font("Courier New",Font.BOLD,28));
    	total.setText("R$                 2,56");
    	total.setBounds(370, 25, 413, 60);
    	total.setBackground(new java.awt.Color(255, 255, 255));
    	total.setFocusable(false);
    	total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
    	total.setEditable(false);
    	total.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 5));
    	painelCentral.add(total);

    	lb_aReceber = new JLabel("A Receber");
    	lb_aReceber.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
    	lb_aReceber.setBounds(370,90, 200, 20);
    	painelCentral.add(lb_aReceber);

    	aReceber = new JTextField();
    	aReceber.setFont(new java.awt.Font("Courier New",Font.BOLD,28));
    	aReceber.setText("R$                 2,56");
    	aReceber.setBounds(370, 115, 413, 60);
    	aReceber.setBackground(new java.awt.Color(255, 255, 255));
    	aReceber.setFocusable(false);
    	aReceber.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
    	aReceber.setEditable(false);
    	aReceber.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 5));
    	painelCentral.add(aReceber);

    	lb_recebido = new JLabel("Recebido");
    	lb_recebido.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
    	lb_recebido.setBounds(370,180, 200, 20);
    	painelCentral.add(lb_recebido);


    	recebido = new javax.swing.JTextPane();
    	recebido.setFont(new java.awt.Font("Courier New",Font.BOLD,28));
    	recebido.setBounds(370, 205, 413, 185);
    	recebido.setBackground(new java.awt.Color(255,255,255));
    	recebido.setFocusable(false);
    	recebido.setEditable(false);
    	recebido.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 5));
     	painelCentral.add(recebido);

    }


    private JTextField total;
    private JTextField aReceber;
    private JTextPane recebido;


    private JLabel lb_total;
    private JLabel lb_aReceber;
    private JLabel lb_recebido;

	
	public void setTotalTela(BigDecimal val){
		this.total.setText(StringUtil.numeroToString(val, 2, 0, ",", ".", true));
	}

	public void inicioTextoRecebido(){
		this.recebido.setText("");
	}
	
	public void setAReceber(BigDecimal val){
		this.aReceber.setText(StringUtil.numeroToString(val, 2, 0, ",", ".", true));
	}
	
	public void addRecebimento(String forma, BigDecimal valor){
		super.addRecebimento(forma, valor);
		String linha = StringUtil.completaStringCentro(forma, StringUtil.numeroToString(valor, 2, 0, ",", ".", true), 23, ' ');
		this.recebido.setText(this.recebido.getText()+linha+"\n");
	}

	public static void main(String[] a){
		JFrame f = new JFrame();
		TelaTotal t = new TelaTotal();
		f.setSize(800, 600);
		//f.getContentPane().add(t.painelTop);
		f.getContentPane().add(t.painelCentral);
		f.setVisible(true);
	}

}
