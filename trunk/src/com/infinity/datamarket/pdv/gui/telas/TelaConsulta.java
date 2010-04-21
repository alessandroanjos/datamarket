

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

import com.infinity.datamarket.comum.util.StringUtil;

/**
 *
 * @author  wagner.medeiros
 */
public class TelaConsulta extends TelaComMenuPDV{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3067546726606964988L;
	private JPanel painelCentral;
	private JPanel painelTop;
	private JLabel imgLogo;


	public JPanel getPanel( ) {
		return painelTop;
	}

    public TelaConsulta() {
        initComponents();
    }


    private void initComponents() {



        painelCentral = new JPanel();
        painelCentral.setLayout(null);
        painelCentral.setBackground(backGround);
        painelCentral.setBounds(0,100,800, 400);
        
        
        lbProduto = new JLabel("Descrição");
        lbProduto.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
        lbProduto.setBounds(10,0, 200, 20);
    	painelCentral.add(lbProduto);
    	
        produto = new JTextField();
        produto.setFont(new java.awt.Font("Courier New",Font.PLAIN+Font.BOLD,48));
        produto.setBounds(10, 20, 770, 80);
        produto.setBackground(new java.awt.Color(255,255,255));
        produto.setFocusable(false);
        produto.setEditable(false);
        produto.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 5));
    	painelCentral.add(produto);
        
    	lbPrecoPadrao = new JLabel("Preço Padrão");
    	lbPrecoPadrao.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
    	lbPrecoPadrao.setBounds(10,110, 200, 20);
    	painelCentral.add(lbPrecoPadrao);
    	
    	
    	precoPadrao = new JTextField();
    	precoPadrao.setFont(new java.awt.Font("Courier New",Font.PLAIN+Font.BOLD,40));
    	precoPadrao.setBounds(10, 130, 250, 70);
    	precoPadrao.setBackground(new java.awt.Color(255,255,255));
    	precoPadrao.setFocusable(false);
    	precoPadrao.setEditable(false);
    	precoPadrao.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 5));
    	painelCentral.add(precoPadrao);
    	
    	lbPrecoPromocional = new JLabel("Preço Promocional");
    	lbPrecoPromocional.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
    	lbPrecoPromocional.setBounds(530,110, 200, 20);
    	painelCentral.add(lbPrecoPromocional);
    	
    	precoPromocional = new JTextField();
    	precoPromocional.setFont(new java.awt.Font("Courier New",Font.PLAIN+Font.BOLD,40));
    	precoPromocional.setBounds(530, 130, 250, 70);
    	precoPromocional.setBackground(new java.awt.Color(255,255,255));
    	precoPromocional.setFocusable(false);
    	precoPromocional.setEditable(false);
    	precoPromocional.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 5));
    	painelCentral.add(precoPromocional);
    	
    	lbDesconto = new JLabel("Desconto");
    	lbDesconto.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
    	lbDesconto.setBounds(270,110, 200, 20);
    	painelCentral.add(lbDesconto);
    	
    	desconto = new JTextField();
    	desconto.setFont(new java.awt.Font("Courier New",Font.PLAIN+Font.BOLD,40));
    	desconto.setBounds(270, 130, 250, 70);
    	desconto.setBackground(new java.awt.Color(255,255,255));
    	desconto.setFocusable(false);
    	desconto.setEditable(false);
    	desconto.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 5));
    	painelCentral.add(desconto);
      
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


    private JTextField produto;
    private JTextField precoPadrao;
    private JTextField precoPromocional;
    private JTextField desconto;
    private JLabel lbProduto;
    private JLabel lbPrecoPadrao;
    private JLabel lbPrecoPromocional;
    private JLabel lbDesconto;

		
	public void setProduto(String descricao){

		this.produto.setText(descricao);

	}

	public void setPrecoPromocional(BigDecimal valor){

		this.precoPromocional.setText(StringUtil.numeroToString(valor, 2, 0, ",", ".", true));

	}

	public void setPrecoPadrao(BigDecimal valor){

		this.precoPadrao.setText(StringUtil.numeroToString(valor, 2, 0, ",", ".", true));

	}

	public void setDesconto(BigDecimal valor){

		this.desconto.setText(StringUtil.numeroToString(valor, 2, 0, ",", ".", true));

	}


	



	public static void main(String[] a){
		JFrame f = new JFrame();
		TelaConsulta t = new TelaConsulta();
		f.setSize(800, 600);
		t.setProduto("Sabão em barra Recife");
		t.setPrecoPadrao(new BigDecimal("999999.99"));
		t.setDesconto(new BigDecimal("999999.99"));
		t.setPrecoPromocional(new BigDecimal("999999.99"));
		f.getContentPane().add(t.painelCentral);
		f.setVisible(true);
	}

}
