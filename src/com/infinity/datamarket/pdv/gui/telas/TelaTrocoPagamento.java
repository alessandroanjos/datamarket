

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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import com.infinity.datamarket.comum.util.StringUtil;

/**
 *
 * @author  wagner.medeiros
 */
public class TelaTrocoPagamento extends Tela{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -7511489337433445282L;

	private JPanel painelCentral;
	private JPanel painelTop;
	private JLabel imgLogo;
	

	public TelaTrocoPagamento() {
        initComponents();
    }


    private void initComponents() {
    	
    	painelCentral = new JPanel();
        painelCentral.setLayout(null);
        painelCentral.setBackground(backGround);
        painelCentral.setBounds(0,100,800, 400);
    	
    	lb_troco = new JLabel("Troco");
    	lb_troco.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,30));
    	lb_troco.setBounds(435,130, 200, 30);
    	painelCentral.add(lb_troco);

    	troco = new JTextField();
    	troco.setFont(new java.awt.Font("Courier New",Font.BOLD,42));
    	troco.setText("R$                 2,56");
    	troco.setBounds(435, 165, 350, 90);
    	troco.setBackground(new java.awt.Color(255, 255, 255));
    	troco.setFocusable(false);
    	troco.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
    	troco.setEditable(false);
    	troco.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 5));
    	painelCentral.add(troco);

    	lb_recebido = new JLabel("Recebido");
    	lb_recebido.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
    	lb_recebido.setBounds(10,0, 200, 20);
    	painelCentral.add(lb_recebido);


    	recebido = new javax.swing.JTextPane();
    	recebido.setFont(new java.awt.Font("Courier New",Font.BOLD,28));
    	recebido.setBounds(10, 25, 413, 365);
    	recebido.setBackground(new java.awt.Color(255,255,255));
    	recebido.setFocusable(false);
    	recebido.setEditable(false);
    	recebido.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 5));
     	painelCentral.add(recebido);
     	
     	getPainel().add(painelCentral);
     	
     	painelTop = new JPanel();
        painelTop.setLayout(null);
        painelTop.setBackground(new java.awt.Color(0, 0, 100));
        painelTop.setBounds(0,0,800, 100);

        //imgLogo = new JLabel(new javax.swing.ImageIcon("C:\\eclipse3.2\\workspace\\MaquinaEstados\\bin\\logo.bmp"));
        imgLogo = new JLabel(new javax.swing.ImageIcon(LOGO_CLIENTE));
        imgLogo.setBounds(5, 10, 340, 83);
        painelTop.setLayout(null);
        painelTop.add(imgLogo);

        getPainel().add(painelTop);


    }


    private JTextField troco;
    private JTextPane recebido;


    
    private JLabel lb_troco;
    private JLabel lb_recebido;


	public void inicioTextoRecebido(){
		this.recebido.setText("");
	}
	
	public void setTroco(BigDecimal val){
		this.troco.setText(StringUtil.numeroToString(val, 2, 0, ",", ".", true));
	}
	
	public void addRecebimento(String forma, BigDecimal valor){
		String linha = StringUtil.completaStringCentro(forma, StringUtil.numeroToString(valor, 2, 0, ",", ".", true), 23, ' ');
		this.recebido.setText(this.recebido.getText()+linha+"\n");
	}

	public static void main(String[] a){
		JFrame f = new JFrame();
		TelaTrocoPagamento t = new TelaTrocoPagamento();
		f.setSize(800, 600);
		t.setTroco(new BigDecimal(200.90).setScale(2,BigDecimal.ROUND_UP));
		t.addRecebimento("DINHEIRO", new BigDecimal(200.90).setScale(2,BigDecimal.ROUND_UP));
		f.getContentPane().add(t.painelCentral);
		f.setVisible(true);
	}

}
