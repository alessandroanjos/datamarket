

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

/**
 *
 * @author  wagner.medeiros
 */
public class TelaConsultaCartaoProprio extends Tela{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3067546726606964988L;
	private JPanel painelCentral;
	private JPanel painelTop;
	private JLabel imgLogo;

    public TelaConsultaCartaoProprio() {
        initComponents();
    }


    private void initComponents() {



        painelCentral = new JPanel();
        painelCentral.setLayout(null);
        painelCentral.setBackground(backGround);
        painelCentral.setBounds(0,100,800, 400);
        
        
        lbNome = new JLabel("Cliente");
        lbNome.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
        lbNome.setBounds(10,50, 200, 20);
    	painelCentral.add(lbNome);
    	
        nome = new JTextField();
        nome.setFont(new java.awt.Font("Courier New",Font.PLAIN+Font.BOLD,48));
        nome.setBounds(10, 70, 770, 80);
        nome.setBackground(new java.awt.Color(255,255,255));
        nome.setFocusable(false);
        nome.setEditable(false);
        nome.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 5));
    	painelCentral.add(nome);
        
    	
    	
    	lbDescricao = new JLabel("Valor");
    	lbDescricao.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
    	lbDescricao.setBounds(10,200, 200, 20);
    	painelCentral.add(lbDescricao);
    	
    	valor = new JTextField();
    	valor.setFont(new java.awt.Font("Courier New",Font.PLAIN+Font.BOLD,48));
    	valor.setBounds(10, 220, 770, 80);
    	valor.setBackground(new java.awt.Color(255,255,255));
    	valor.setFocusable(false);
    	valor.setHorizontalAlignment(JTextField.RIGHT);
    	valor.setEditable(false);
    	valor.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 5));
    	painelCentral.add(valor);

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


    private JTextField valor;
    private JTextField nome;
    private JLabel lbNome;
    private JLabel lbDescricao;

	public void setValor(BigDecimal valor){

		this.valor.setText("R$  "+valor.toString());

	}
	
	public void setNome(String descricao){

		this.nome.setText(descricao);

	}
	



	public static void main(String[] a){
		JFrame f = new JFrame();
		TelaConsultaCartaoProprio t = new TelaConsultaCartaoProprio();
		f.setSize(800, 600);
		t.setNome("Wagner de Medeiros Melo");
		t.setValor(new BigDecimal(200.98).setScale(2,BigDecimal.ROUND_UP));
		f.getContentPane().add(t.painelCentral);
		f.setVisible(true);
	}

}
