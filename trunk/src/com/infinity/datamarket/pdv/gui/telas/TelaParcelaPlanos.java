

/*
 * TelaInicial.java
 *
 * Created on 24 de Janeiro de 2007, 13:28
 */

package com.infinity.datamarket.pdv.gui.telas;

import java.awt.Font;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
public class TelaParcelaPlanos extends Tela{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3067546726606964988L;
	private JPanel painelCentral;
	private JPanel painelTop;
	private JLabel imgLogo;

    public TelaParcelaPlanos() {
        initComponents();
    }


    private void initComponents() {



        painelCentral = new JPanel();
        painelCentral.setLayout(null);
        painelCentral.setBackground(new java.awt.Color(232, 232, 0));
        painelCentral.setBounds(0,100,800, 400);
        
        
        lbForma = new JLabel("Entrada");
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
        
    	lbCodigo = new JLabel("Parcela");
    	lbCodigo.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
    	lbCodigo.setBounds(10,100, 200, 20);
    	painelCentral.add(lbCodigo);
    	
    	lbDescricao = new JLabel("Valor");
    	lbDescricao.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
    	lbDescricao.setBounds(90,100, 200, 20);
    	painelCentral.add(lbDescricao);
    	
    	lbData = new JLabel("Data");
    	lbData.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
    	lbData.setBounds(320,100, 200, 20);
    	painelCentral.add(lbData);
    	
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
        imgLogo = new JLabel(new javax.swing.ImageIcon(LOGO_CLIENTE));
        imgLogo.setBounds(5, 10, 340, 83);
        painelTop.setLayout(null);
        painelTop.add(imgLogo);

        getPainel().add(painelTop);

    }


    private JTextPane planos;
    private JTextField forma;
    private JLabel lbForma;
    private JLabel lbCodigo;
    private JLabel lbDescricao;
    private JLabel lbData;

	public void addParcela(int codigo, BigDecimal valor, Date data){
		
		Calendar c  = new GregorianCalendar();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		df.setCalendar(c);
		
		this.planos.setText(this.planos.getText()+codigo+"   "+StringUtil.completaString(StringUtil.numeroToString(valor, 2, 0, ",", ".", true),10,' ',true)+"  "+df.format(data)+"\n");

	}
	
	public void setEntrada(BigDecimal entrada){

		this.forma.setText(StringUtil.numeroToString(entrada, 2, 0, ",", ".", true));

	}
	

	public void limparParcelas(){

		this.planos.setText("");

	}




	public static void main(String[] a){
		JFrame f = new JFrame();
		TelaParcelaPlanos t = new TelaParcelaPlanos();
		f.setSize(800, 600);
		t.setEntrada(new BigDecimal(1200.00));
		t.addParcela(1, new BigDecimal(100000.00),new Date());
		t.addParcela(2, new BigDecimal(10000.00),new Date());
		t.addParcela(3, new BigDecimal(1000.00),new Date());
		t.addParcela(4, new BigDecimal(100.00),new Date());
		f.getContentPane().add(t.painelCentral);
		f.setVisible(true);
	}

}
