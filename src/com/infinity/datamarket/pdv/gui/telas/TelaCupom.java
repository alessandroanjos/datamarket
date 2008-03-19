

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
import javax.swing.JTextPane;

import com.infinity.datamarket.comum.transacao.EventoItemRegistrado;
import com.infinity.datamarket.comum.util.StringUtil;


/**
 *
 * @author  wagner.medeiros
 */
public class TelaCupom extends Tela{
	protected JPanel painelCentral;
	protected JPanel painelTop;
	protected JLabel imgLogo;

    public TelaCupom() {
        initComponents();
    }


    private void initComponents() {



        painelCentral = new JPanel();
        painelCentral.setLayout(null);
        painelCentral.setBackground(new java.awt.Color(232, 232, 0));
        painelCentral.setBounds(0,100,800, 400);

        lb_cupom = new JLabel("Cupom");
        lb_cupom.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
        lb_cupom.setBounds(10,0, 200, 20);
    	painelCentral.add(lb_cupom);

        cupom = new javax.swing.JTextPane();
    	cupom.setFont(new java.awt.Font("Courier New",Font.PLAIN,14));
    	cupom.setBounds(10, 25, 350, 365);
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
        imgLogo = new JLabel(new javax.swing.ImageIcon(LOGO_CLIENTE));
        imgLogo.setBounds(5, 10, 340, 83);
        painelTop.setLayout(null);
        painelTop.add(imgLogo);

        getPainel().add(painelTop);

    }


    private JTextPane cupom;
    private JLabel lb_cupom;
    

	public void addLinha(String linha){
		this.cupom.setText((this.cupom.getText().substring(this.cupom.getText().indexOf("\n")+1))+"\n"+linha);
	}

	public void addTextoCupom(String linha){
		this.cupom.setText(linha);
	}

	public void setTotal(BigDecimal val){
		String linha = StringUtil.completaString("",42,'-',true);
		this.cupom.setText((this.cupom.getText().substring(this.cupom.getText().indexOf("\n")+1))+"\n"+linha);
		linha = StringUtil.completaStringCentro("Total", StringUtil.numeroToString(val, 2, 0, ",", ".", true), 42, ' ');
		this.cupom.setText((this.cupom.getText().substring(this.cupom.getText().indexOf("\n")+1))+"\n"+linha);
	}

	public String getTextoCupom(){
		return this.cupom.getText();
	}

	public void addRecebimento(String forma, BigDecimal valor){
		String linha = StringUtil.completaStringCentro(forma, StringUtil.numeroToString(valor, 2, 0, ",", ".", true), 42, ' ');
		this.cupom.setText((this.cupom.getText().substring(this.cupom.getText().indexOf("\n")+1))+"\n"+linha);
	}
	
	public void addDesconto(BigDecimal valor, BigDecimal total){
		String linha = StringUtil.completaStringCentro("Desconto", "- "+StringUtil.numeroToString(valor, 2, 0, ",", ".", true), 42, ' ');
		this.cupom.setText((this.cupom.getText().substring(this.cupom.getText().indexOf("\n")+1))+"\n"+linha);
		linha = StringUtil.completaStringCentro("Total", StringUtil.numeroToString(total, 2, 0, ",", ".", true), 42, ' ');
		this.cupom.setText((this.cupom.getText().substring(this.cupom.getText().indexOf("\n")+1))+"\n"+linha);
	}

	public void inicioTextoCupom(){
		this.cupom.setText("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"+StringUtil.completaString("",15,' ',true)+"Cupom Fiscal"+StringUtil.completaString("",15,' ',true)+"\n");
	}

	 public void addItem(int index, String descricao, BigDecimal quantidade, BigDecimal valorUnitario,
				BigDecimal desconto, String situacao){
		String indice = StringUtil.completeNumberZERO(index, 3);
		String desc = StringUtil.completaString(descricao, 38, ' ',false);
		this.cupom.setText((this.cupom.getText().substring(this.cupom.getText().indexOf("\n")+1))+"\n"+indice+" "+desc);

		String qtd = StringUtil.numeroToString(quantidade, 3, 0, ",", ".", true);

		BigDecimal total = quantidade.multiply(valorUnitario);
		String val_unitario = null;
		String val_total = null;
		if (desconto !=  null && desconto.compareTo(new BigDecimal(0)) > 0){
			val_unitario = StringUtil.numeroToString(valorUnitario.add(desconto), 2, 0, ",", ".", true);
			val_total = StringUtil.numeroToString(total.add(desconto), 2, 0, ",", ".", true);
		}else{
			val_unitario = StringUtil.numeroToString(valorUnitario, 2, 0, ",", ".", true);
			val_total = StringUtil.numeroToString(total, 2, 0, ",", ".", true);
		}

		String p1 = StringUtil.completaString(qtd+ " x "+val_unitario,28,' ',false);
		String p2 = StringUtil.completaString(val_total,10,' ',true);


		this.cupom.setText((this.cupom.getText().substring(this.cupom.getText().indexOf("\n")+1))+"\n"+p1+" R$ "+p2);
		if (desconto !=  null && desconto.compareTo(new BigDecimal(0)) > 0){
			p1 = StringUtil.completaString("Desconto item "+indice,28,' ',false);
			p2 = StringUtil.completaString("-"+StringUtil.numeroToString(desconto, 2, 0, ",", ".", true),10,' ',true);
			this.cupom.setText((this.cupom.getText().substring(this.cupom.getText().indexOf("\n")+1))+"\n"+p1+" R$ "+p2);
		}
		if (situacao!= null && situacao.equals(EventoItemRegistrado.CANCELADO)){
			p1 = StringUtil.completaString("Cancelado item "+indice,28,' ',false);			
			p2 = StringUtil.completaString("-"+StringUtil.numeroToString(total, 2, 0, ",", ".", true),10,' ',true);
			this.cupom.setText((this.cupom.getText().substring(this.cupom.getText().indexOf("\n")+1))+"\n"+p1+" R$ "+p2);
		}
	 }
	 


	public static void main(String[] a){
		JFrame f = new JFrame();
		TelaCupom t = new TelaCupom();
		f.setSize(800, 600);
		//f.getContentPane().add(t.painelTop);
		f.getContentPane().add(t.painelCentral);
		f.setVisible(true);
	}

}
