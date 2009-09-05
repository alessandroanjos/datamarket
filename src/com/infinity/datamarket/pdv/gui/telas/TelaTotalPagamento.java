

/*
 * TelaInicial.java
 *
 * Created on 24 de Janeiro de 2007, 13:28
 */

package com.infinity.datamarket.pdv.gui.telas;

import java.awt.Font;
import java.io.File;
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
public class TelaTotalPagamento extends Tela{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -7511489337433445282L;

	private JPanel painelCentral;
	private JPanel painelTop;
	private JLabel imgLogo;
	

	public TelaTotalPagamento() {
        initComponents();
    }


    private void initComponents() {
    	
    	painelCentral = new JPanel();
        painelCentral.setLayout(null);
        painelCentral.setBackground(backGround);
        painelCentral.setBounds(0,100,800, 400);

        lb_total = new JLabel("Total");
    	lb_total.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
    	lb_total.setBounds(435,0, 200, 20);
    	painelCentral.add(lb_total);

    	total = new JTextField();
    	total.setFont(new java.awt.Font("Courier New",Font.BOLD,32));
    	total.setText("R$                 2,56");
    	total.setBounds(435, 25, 350, 70);
    	total.setBackground(new java.awt.Color(255, 255, 255));
    	total.setFocusable(false);
    	total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
    	total.setEditable(false);
    	total.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 5));
    	painelCentral.add(total);

    	lb_aReceber = new JLabel("A Receber");
    	lb_aReceber.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
    	lb_aReceber.setBounds(435,100, 200, 20);
    	painelCentral.add(lb_aReceber);

    	aReceber = new JTextField();
    	aReceber.setFont(new java.awt.Font("Courier New",Font.BOLD,32));
    	aReceber.setText("R$                 2,56");
    	aReceber.setBounds(435, 125, 350, 70);
    	aReceber.setBackground(new java.awt.Color(255, 255, 255));
    	aReceber.setFocusable(false);
    	aReceber.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
    	aReceber.setEditable(false);
    	aReceber.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 5));
    	painelCentral.add(aReceber);

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
        if(new File(LOGO_CLIENTE).exists()) {
            imgLogo = new JLabel(new javax.swing.ImageIcon(LOGO_CLIENTE));
            imgLogo.setBounds(5, 10, 340, 83);
            painelTop.add(imgLogo);
        }
        painelTop.setLayout(null);

        getPainel().add(painelTop);


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
		String linha = StringUtil.completaStringCentro(forma, StringUtil.numeroToString(valor, 2, 0, ",", ".", true), 23, ' ');
		this.recebido.setText(this.recebido.getText()+linha+"\n");
	}

	public static void main(String[] a){
		JFrame f = new JFrame();
		TelaTotalPagamento t = new TelaTotalPagamento();
		f.setSize(800, 600);
		t.setAReceber(new BigDecimal(200.90).setScale(2,BigDecimal.ROUND_UP));
		t.setTotalTela(new BigDecimal(200.90).setScale(2,BigDecimal.ROUND_UP));
		t.addRecebimento("DINHEIRO", new BigDecimal(200.90).setScale(2,BigDecimal.ROUND_UP));
		f.getContentPane().add(t.painelCentral);
		f.setVisible(true);
	}

}
