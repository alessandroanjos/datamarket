

/*
 * TelaInicial.java
 *
 * Created on 24 de Janeiro de 2007, 13:28
 */

package com.infinity.datamarket.pdv.gui.telas;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

/**
 *
 * @author  wagner.medeiros
 */
public class TelaPlanos extends Tela{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3067546726606964988L;
	private JPanel painelCentral;
	private JPanel painelTop;
	private JLabel imgLogo;

    public TelaPlanos() {
        initComponents();
    }


    private void initComponents() {



        painelCentral = new JPanel();
        painelCentral.setLayout(null);
        painelCentral.setBackground(new java.awt.Color(232, 232, 0));
        painelCentral.setBounds(0,100,800, 400);

        cupom = new JTextPaneCupom();
    	cupom.setFont(new java.awt.Font("Courier New",Font.PLAIN+Font.BOLD,32));
    	cupom.setBounds(10, 10, 770, 365);
    	cupom.setBackground(new java.awt.Color(255,255,255));
    	cupom.setFocusable(false);
    	cupom.setEditable(false);
    	cupom.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 5));
    	painelCentral.add(cupom);

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


    private JTextPane cupom;
    private JTextField descricao;
    private JTextField quantidade;
    private JTextField val_unitario;
    private JTextField desconto;
    private JTextField val_total;
    private JTextField sub_total;


    private JLabel lb_cupom;
    private JLabel lb_descricao;
    private JLabel lb_quantidade;
    private JLabel lb_val_unitario;
    private JLabel lb_desconto;
    private JLabel lb_val_total;
    private JLabel lb_sub_total;
    private static final int TAM_LINHA_CUPOM = 42;

	public void addPlano(int codigo, String descricao){

		this.cupom.setText(this.cupom.getText()+codigo+" - "+descricao+"\n");

	}

	public void limparPlanos(){

		this.cupom.setText("");

	}




	public static void main(String[] a){
		JFrame f = new JFrame();
		TelaPlanos t = new TelaPlanos();
		f.setSize(800, 600);
		t.addPlano(1, "A vista");
		t.addPlano(2, "2x sem juros");
		t.addPlano(3, "3x sem juros");
		f.getContentPane().add(t.painelCentral);
		f.setVisible(true);
	}

}
