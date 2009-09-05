

/*
 * TelaInicial.java
 *
 * Created on 24 de Janeiro de 2007, 13:28
 */

package com.infinity.datamarket.pdv.gui.telas;

import java.awt.Font;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

/**
 *
 * @author  wagner.medeiros
 */
public class TelaAutorizadoras extends Tela{

	/**
	 * 
	 */
	private static final long serialVersionUID = -344274509511594817L;
	private JPanel painelCentral;
	private JPanel painelTop;
	private JLabel imgLogo;

    public TelaAutorizadoras() {
        initComponents();
    }


    private void initComponents() {



        painelCentral = new JPanel();
        painelCentral.setLayout(null);
        painelCentral.setBackground(backGround);
        painelCentral.setBounds(0,100,800, 400);
        
        
        lbForma = new JLabel("Forma");
        lbForma.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
        lbForma.setBounds(10,0, 200, 20);
    	painelCentral.add(lbForma);
    	
        forma = new JTextField();
        forma.setFont(new java.awt.Font("Courier New",Font.PLAIN+Font.BOLD,48));
        forma.setBounds(10, 20, 770, 80);
        forma.setBackground(new java.awt.Color(255,255,255));
        forma.setFocusable(false);
        forma.setEditable(false);
        forma.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 5));
    	painelCentral.add(forma);
        
    	lbCodigo = new JLabel("Código");
    	lbCodigo.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
    	lbCodigo.setBounds(10,100, 200, 20);
    	painelCentral.add(lbCodigo);
    	
    	lbDescricao = new JLabel("Cartão");
    	lbDescricao.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
    	lbDescricao.setBounds(110,100, 200, 20);
    	painelCentral.add(lbDescricao);
    	
        planos = new JTextPaneCupom();
    	planos.setFont(new java.awt.Font("Courier New",Font.PLAIN+Font.BOLD,32));
    	planos.setBounds(10, 120, 770, 255);
    	planos.setBackground(new java.awt.Color(255,255,255));
    	planos.setFocusable(false);
    	planos.setEditable(false);
    	planos.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 5));
    	painelCentral.add(planos);

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


    private JTextPane planos;
    private JTextField forma;
    private JLabel lbForma;
    private JLabel lbCodigo;
    private JLabel lbDescricao;

	public void addCartao(int codigo, String descricao){

		this.planos.setText(this.planos.getText()+codigo+"    "+descricao+"\n");

	}
	
	public void setForma(String descricao){

		this.forma.setText(descricao);

	}
	

	public void limparCartoes(){

		this.planos.setText("");

	}




	public static void main(String[] a){
		JFrame f = new JFrame();
		TelaAutorizadoras t = new TelaAutorizadoras();
		f.setSize(800, 600);
		t.setForma("CARTÃO OFF");
		t.addCartao(1, "VISA");
		t.addCartao(2, "MASTERCARD");
		t.addCartao(3, "HIPERCARD");
		f.getContentPane().add(t.painelCentral);
		f.setVisible(true);
	}

}
