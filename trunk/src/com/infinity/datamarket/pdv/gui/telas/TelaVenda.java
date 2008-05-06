

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
public class TelaVenda extends TelaCupom{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8683394242326153996L;




	public TelaVenda() {
        initComponents();
    }

	private void initComponents() {


    	lb_desconto = new JLabel("Descrição");
    	lb_desconto.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
    	lb_desconto.setBounds(370,0, 200, 20);
    	painelCentral.add(lb_desconto);

    	descricao = new JTextField();
    	descricao.setFont(new java.awt.Font("Courier New",Font.BOLD,28));
    	descricao.setText("NOVALGINA 200MG");
    	descricao.setBounds(370, 25, 413, 60);
    	descricao.setBackground(new java.awt.Color(255, 255, 255));
    	descricao.setFocusable(false);
    	descricao.setEditable(false);
    	descricao.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 5));
    	painelCentral.add(descricao);

    	lb_quantidade = new JLabel("Quantidade");
    	lb_quantidade.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
    	lb_quantidade.setBounds(370,90, 200, 20);
    	painelCentral.add(lb_quantidade);

    	quantidade = new JTextField();
    	quantidade.setFont(new java.awt.Font("Courier New",Font.BOLD,30));
    	quantidade.setText("1,000");
    	quantidade.setBounds(370, 115, 150, 60);
    	quantidade.setBackground(new java.awt.Color(255, 255, 255));
    	quantidade.setFocusable(false);
    	quantidade.setEditable(false);
    	quantidade.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
    	quantidade.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 5));
    	painelCentral.add(quantidade);

    	lb_val_unitario = new JLabel("Valor Unitário");
    	lb_val_unitario.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
    	lb_val_unitario.setBounds(560,90, 200, 20);
    	painelCentral.add(lb_val_unitario);

    	val_unitario = new JTextField();
    	val_unitario.setFont(new java.awt.Font("Courier New",Font.BOLD,30));
    	val_unitario.setText("2,56");
    	val_unitario.setBounds(560, 115, 223, 60);
    	val_unitario.setBackground(new java.awt.Color(255, 255, 255));
    	val_unitario.setFocusable(false);
    	val_unitario.setEditable(false);
    	val_unitario.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
    	val_unitario.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 5));
    	painelCentral.add(val_unitario);

    	lb_desconto = new JLabel("Desconto");
    	lb_desconto.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
    	lb_desconto.setBounds(370,180, 200, 20);
    	painelCentral.add(lb_desconto);

    	desconto = new JTextField();
    	desconto.setFont(new java.awt.Font("Courier New",Font.BOLD,30));
    	desconto.setText("0,00");
    	desconto.setBounds(370, 205, 150, 60);
    	desconto.setBackground(new java.awt.Color(255, 255, 255));
    	desconto.setFocusable(false);
    	desconto.setEditable(false);
    	desconto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
    	desconto.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 5));
    	painelCentral.add(desconto);

    	lb_val_total = new JLabel("Total Item");
    	lb_val_total.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
    	lb_val_total.setBounds(560,180, 200, 20);
    	painelCentral.add(lb_val_total);

    	val_total = new JTextField();
    	val_total.setFont(new java.awt.Font("Courier New",Font.BOLD,30));
    	val_total.setText("2,56");
    	val_total.setBounds(560, 205, 223, 60);
    	val_total.setBackground(new java.awt.Color(255, 255, 255));
    	val_total.setFocusable(false);
    	val_total.setEditable(false);
    	val_total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
    	val_total.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 5));
    	painelCentral.add(val_total);


    	lb_sub_total = new JLabel("Sub-Total");
    	lb_sub_total.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,32));
    	lb_sub_total.setBounds(370,280, 200, 30);
    	painelCentral.add(lb_sub_total);

    	sub_total = new JTextField();
    	sub_total.setFont(new java.awt.Font("Courier New",Font.BOLD,48));
    	sub_total.setText("2,56");
    	sub_total.setBounds(370, 320, 413, 70);
    	sub_total.setBackground(new java.awt.Color(255, 255, 255));
    	sub_total.setFocusable(false);
    	sub_total.setEditable(false);
    	sub_total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
    	sub_total.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 5));
    	painelCentral.add(sub_total);

       


        
    }


   
    private JTextField descricao;
    private JTextField quantidade;
    private JTextField val_unitario;
    private JTextField desconto;
    private JTextField val_total;
    private JTextField sub_total;



    private JLabel lb_descricao;
    private JLabel lb_quantidade;
    private JLabel lb_val_unitario;
    private JLabel lb_desconto;
    private JLabel lb_val_total;
    private JLabel lb_sub_total;
    private static final int TAM_LINHA_CUPOM = 42;



	
	public static void main(String[] a){
		JFrame f = new JFrame();
		TelaVenda t = new TelaVenda();
		f.setSize(800, 600);
		//f.getContentPane().add(t.painelTop);
		f.getContentPane().add(t.painelCentral);
		f.setVisible(true);
	}

	public void setDescricao(String desc){
		this.descricao.setText(desc);
	}

	public void setQuantidade(BigDecimal quant){
		this.quantidade.setText(StringUtil.numeroToString(quant, 3, 0, ",", ".", true));
	}

	public void setValorUnitario(BigDecimal val){
		this.val_unitario.setText(StringUtil.numeroToString(val, 2, 0, ",", ".", true));
	}

	public void setDesconto(BigDecimal desc){
		this.desconto.setText(StringUtil.numeroToString(desc, 2, 0, ",", ".", true));
	}

	public void setValorTotal(BigDecimal val){
		this.val_total.setText(StringUtil.numeroToString(val, 2, 0, ",", ".", true));
	}

	public void setValorSubTotal(BigDecimal val){
		this.sub_total.setText(StringUtil.numeroToString(val, 2, 0, ",", ".", true));
	}

}
