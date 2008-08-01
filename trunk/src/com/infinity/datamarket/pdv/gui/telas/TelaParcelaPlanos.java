

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

import com.infinity.datamarket.comum.pagamento.PlanoPagamento;
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
        painelCentral.setBackground(backGround);
        painelCentral.setBounds(0,100,800, 400);
        
        
        
        forma = new JTextField();
        forma.setFont(new java.awt.Font("Courier New",Font.PLAIN+Font.BOLD,48));
        forma.setBounds(10, 10, 770, 80);
        forma.setBackground(new java.awt.Color(255,255,255));
        forma.setFocusable(false);
        forma.setEditable(false);
        forma.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 5));
    	painelCentral.add(forma);
    	
    	lbEntrada = new JLabel("Entrada");
    	lbEntrada.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
    	lbEntrada.setBounds(10,90, 200, 20);
    	painelCentral.add(lbEntrada);
    	
    	
    	entrada = new JTextField();
    	entrada.setFont(new java.awt.Font("Courier New",Font.PLAIN+Font.BOLD,32));
    	entrada.setBounds(10, 110, 350, 50);
    	entrada.setBackground(new java.awt.Color(255,255,255));
    	entrada.setFocusable(false);
    	entrada.setEditable(false);
    	entrada.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
    	entrada.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 5));
    	painelCentral.add(entrada);
    	
    	lbTotal = new JLabel("Valor Total");
    	lbTotal.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
    	lbTotal.setBounds(370,90, 200, 20);
    	painelCentral.add(lbTotal);
    	
    	
    	total = new JTextField();
    	total.setFont(new java.awt.Font("Courier New",Font.PLAIN+Font.BOLD,32));
    	total.setBounds(370, 110, 410, 50);
    	total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
    	total.setBackground(new java.awt.Color(255,255,255));
    	total.setFocusable(false);
    	total.setEditable(false);
    	total.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 5));
    	painelCentral.add(total);
        
    	
    	
    	lbCodigo = new JLabel("Parcela");
    	lbCodigo.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
    	lbCodigo.setBounds(10,160, 200, 20);
    	painelCentral.add(lbCodigo);
    	
    	lbDescricao = new JLabel("Valor");
    	lbDescricao.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
    	lbDescricao.setBounds(90,160, 200, 20);
    	painelCentral.add(lbDescricao);
    	
    	lbData = new JLabel("Data");
    	lbData.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
    	lbData.setBounds(320,160, 200, 20);
    	painelCentral.add(lbData);
    	
        planos = new JTextPaneCupom();
    	planos.setFont(new java.awt.Font("Courier New",Font.PLAIN+Font.BOLD,32));
    	planos.setBounds(10, 180, 770, 210);
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
    private JTextField entrada;
    private JTextField total;
    private JLabel lbEntrada;
    private JLabel lbCodigo;
    private JLabel lbDescricao;
    private JLabel lbData;
    private JLabel lbTotal;

	public void addParcela(int codigo, BigDecimal valor, Date data){
		
		Calendar c  = new GregorianCalendar();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		df.setCalendar(c);
		
		String sData = data==null?"":df.format(data);
		
		this.planos.setText(this.planos.getText()+codigo+"   "+StringUtil.completaString(StringUtil.numeroToString(valor, 2, 0, ",", ".", true),10,' ',true)+"  "+sData+"\n");

	}
	
	public void setEntrada(BigDecimal entrada){

		this.entrada.setText(StringUtil.numeroToString(entrada, 2, 0, ",", ".", true));

	}
	
	public void setValorTotal(BigDecimal total){

		this.total.setText(StringUtil.numeroToString(total, 2, 0, ",", ".", true));

	}

	
	public void setPlanoFroma(PlanoPagamento p){

		this.forma.setText(p.getDescricao() + " " +p.getForma().getDescricao());

	}

//	public void setPlanoFroma(String g){
//
//		this.forma.setText(g);
//
//	}
	

	public void limparParcelas(){

		this.planos.setText("");

	}




	public static void main(String[] a){
		JFrame f = new JFrame();
		TelaParcelaPlanos t = new TelaParcelaPlanos();
		f.setSize(800, 600);
//		t.setPlanoFroma("1 + 2 s/juros cheque-pre");
		t.setEntrada(new BigDecimal(1200.00));
		t.setValorTotal(new BigDecimal(112200.00));
		t.addParcela(1, new BigDecimal(100000.00),new Date());
		t.addParcela(2, new BigDecimal(10000.00),new Date());
		t.addParcela(3, new BigDecimal(1000.00),new Date());
		t.addParcela(4, new BigDecimal(100.00),new Date());
		f.getContentPane().add(t.painelCentral);
		f.setVisible(true);
	}

}
