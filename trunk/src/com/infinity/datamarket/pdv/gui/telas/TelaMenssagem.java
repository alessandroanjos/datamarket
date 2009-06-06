/*
 * TelaInicial.java
 *
 * Created on 24 de Janeiro de 2007, 13:28
 */

package com.infinity.datamarket.pdv.gui.telas;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author  wagner.medeiros
 */
public class TelaMenssagem extends Tela{
	/**
	 * 
	 */
	private static final long serialVersionUID = -631336578969656451L;
	private JPanel painelCentral;
	private JPanel painelTop;
	private JLabel imgLogo;

    public TelaMenssagem() {
        initComponents();
    }


    private void initComponents() {
    	menssagem = new javax.swing.JLabel();
        menssagem.setFont(new java.awt.Font("Arial", 1, 36));
        menssagem.setText("");
        menssagem.setBounds(20, 20, 590, 40);
        painelCentral = new JPanel();
        painelCentral.setLayout(null);
        painelCentral.setBackground(backGround);
        painelCentral.setBounds(0,100,800, 400);
        painelCentral.add(menssagem);
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
        private javax.swing.JLabel menssagem;

	public void setMenssagem(String menssagem){
		this.menssagem.setText(menssagem);
	}

}
