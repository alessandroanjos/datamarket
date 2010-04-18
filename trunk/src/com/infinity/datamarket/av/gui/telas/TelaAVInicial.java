/*
 * TelaInicial.java
 *
 * Created on 24 de Janeiro de 2007, 13:28
 */

package com.infinity.datamarket.av.gui.telas;

import java.awt.Font;
import java.io.File;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.infinity.datamarket.pdv.gui.telas.Tela;

/**
 *
 * @author  tosck
 */
public class TelaAVInicial extends Tela{
	/**
	 * 
	 */
	private static final long serialVersionUID = -631336578969656451L;
    // Variables declaration - do not modify                     
    
    private javax.swing.JLabel labelTotal;
    private javax.swing.JLabel labelTotalDesconto;
    private javax.swing.JLabel labelValor;    
    
    private javax.swing.JLabel labelCliente;
    private javax.swing.JLabel labelOperacao;
    private javax.swing.JLabel labelCodigoProduto;
    private javax.swing.JLabel labelQuantidade;
    private javax.swing.JLabel labelDesconto;
    private javax.swing.JTextField campoCliente;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField campoDesconto;    
    private javax.swing.JTextField campoOperacao;
    private javax.swing.JTextField campoCodigoProduto;
    private javax.swing.JTextField campoDescricaoProduto;
    private javax.swing.JTextField campoQuantidade;
    private javax.swing.JTextField campoValor;
    private javax.swing.JTextField campoTotal;
    private javax.swing.JTextField campoTotalDesconto;

    public TelaAVInicial() {
        initComponents();
    }


    private void initComponents() {
    	
    	painelCentral = new JPanel();
        painelCentral.setLayout(null);
        painelCentral.setBackground(backGround);
        painelCentral.setBounds(0,0,800, 500);
        
       

        
        //imgLogo = new JLabel(new javax.swing.ImageIcon("C:\\eclipse3.2\\workspace\\MaquinaEstados\\bin\\logo.bmp"));
        if (new File(LOGO_CLIENTE).exists()){
	        imgLogo = new JLabel(new javax.swing.ImageIcon(LOGO_CLIENTE));
	        imgLogo.setBounds(5, 10, 340, 90);
	        painelCentral.add(imgLogo);
        }
        
        

   
    	         
                  
         jScrollPane1 = new javax.swing.JScrollPane();
         jTable1 = new javax.swing.JTable();
         
         labelCliente = new javax.swing.JLabel();
         labelOperacao = new javax.swing.JLabel();
         campoOperacao = new javax.swing.JTextField();
         labelCodigoProduto = new javax.swing.JLabel();
         campoCodigoProduto = new javax.swing.JTextField();
         campoDescricaoProduto = new javax.swing.JTextField();
         campoQuantidade = new javax.swing.JTextField();
         labelQuantidade = new javax.swing.JLabel();
         labelDesconto = new javax.swing.JLabel();
         campoDesconto = new javax.swing.JTextField();
         campoCliente = new javax.swing.JTextField();
         labelValor = new javax.swing.JLabel();
         campoValor = new javax.swing.JTextField();
         labelTotal = new javax.swing.JLabel();
         labelTotalDesconto = new javax.swing.JLabel();
         campoTotal = new javax.swing.JTextField();
         campoTotalDesconto = new javax.swing.JTextField();
                  
        


         
// Segunda linha
         
         painelCentral.add(labelOperacao);
         labelOperacao.setText("Opera\u00e7\u00e3o");         
         labelOperacao.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
         labelOperacao.setBounds(360, 5, 120, 20);

         painelCentral.add(campoOperacao);
         campoOperacao.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 2));
         campoOperacao.setFont(new java.awt.Font("Courier New", 1, 24));
         campoOperacao.setBounds(360, 25, 300, 25);         
         campoOperacao.setFocusable(false);

         painelCentral.add(labelCliente);
         labelCliente.setText("Cliente");
         labelCliente.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
         labelCliente.setBounds(360, 55, 120, 20);

         painelCentral.add(campoCliente);
         campoCliente.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 2));
         campoCliente.setFont(new java.awt.Font("Courier New", 1, 24));
         campoCliente.setBounds(360, 75, 420, 25);         
         campoCliente.setFocusable(false);


         // Terceira linha 
         painelCentral.add(labelCodigoProduto);
         labelCodigoProduto.setText("Produto");         
         labelCodigoProduto.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
         labelCodigoProduto.setBounds(10, 105, 120, 20);

         painelCentral.add(campoCodigoProduto);
         campoCodigoProduto.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 2));
         campoCodigoProduto.setFont(new java.awt.Font("Courier New", 1, 24));
         campoCodigoProduto.setBounds(10, 125, 200, 25);
         campoCodigoProduto.setText("");
         campoCodigoProduto.setFocusable(false);

         painelCentral.add(campoDescricaoProduto);
         campoDescricaoProduto.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 2));
         campoDescricaoProduto.setFont(new java.awt.Font("Courier New", 1, 24));
         campoDescricaoProduto.setBounds(220, 125, 560, 25);
         campoDescricaoProduto.setFocusable(false);


         // quarta linha 
         painelCentral.add(labelValor);
         labelValor.setText("Valor");
         labelValor.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
         labelValor.setBounds(10, 155, 120, 20);

         
         painelCentral.add(campoValor);
         campoValor.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 2));
         campoValor.setFont(new java.awt.Font("Courier New", 1, 24));
         campoValor.setBounds(10, 175, 150, 25);
         campoValor.setFocusable(false);
         campoValor.setHorizontalAlignment(JTextField.RIGHT);

         painelCentral.add(labelQuantidade);
         labelQuantidade.setText("Quantidade");
         labelQuantidade.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
         labelQuantidade.setBounds(175, 155, 120, 20);

         painelCentral.add(campoQuantidade);
         campoQuantidade.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 2));
         campoQuantidade.setFont(new java.awt.Font("Courier New", 1, 24));
         campoQuantidade.setBounds(175, 175, 150, 25);
         campoQuantidade.setFocusable(false);
         campoQuantidade.setHorizontalAlignment(JTextField.RIGHT);

         painelCentral.add(labelDesconto);
         labelDesconto.setText("Desconto");
         labelDesconto.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
         labelDesconto.setBounds(340, 155, 120, 20);

         painelCentral.add(campoDesconto);
         campoDesconto.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 2));
         campoDesconto.setFont(new java.awt.Font("Courier New", 1, 24));
         campoDesconto.setBounds(340, 175, 150, 25);
         campoDesconto.setFocusable(false);
         campoDesconto.setHorizontalAlignment(JTextField.RIGHT);

         // grid
         painelCentral.add(jScrollPane1);
         jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
         jScrollPane1.setBorder(null);
         jScrollPane1.setFont(new java.awt.Font("Courier New", 1, 14));
         jTable1.setFont(new java.awt.Font("Courier New", 1, 14));
         this.zerarTabela();
         
//         jTable1.setModel(new javax.swing.table.DefaultTableModel(
//               new Object [][] {},
//               new String [] {"Seq","Produto", "Valor", "Quantidade", "Desconto Item", "Total"}
//           ){
//           
//           public boolean isCellEditable(int rowIndex, int columnIndex)
//           {
//               return false;
//           }
//           });
         
         jTable1.setGridColor(new java.awt.Color(255, 255, 255));
         jTable1.setPreferredSize(new java.awt.Dimension(790, 190));
         jTable1.setSelectionBackground(new java.awt.Color(255, 255, 255));

         NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.getDefault());
         FormatRenderer r = new FormatRenderer(formatter);
         r.setHorizontalAlignment(SwingConstants.RIGHT);
         
//         FormatRenderer r1 = new FormatRenderer(formatter);
//         r.setHorizontalAlignment(SwingConstants.RIGHT);
         
         jTable1.getColumnModel().getColumn(0).setPreferredWidth(100);
         jTable1.getColumnModel().getColumn(0).setResizable(false);
         
         jTable1.getColumnModel().getColumn(1).setPreferredWidth(280);
         jTable1.getColumnModel().getColumn(1).setResizable(false);

         jTable1.getColumnModel().getColumn(2).setPreferredWidth(100);
         jTable1.getColumnModel().getColumn(2).setResizable(false);
         jTable1.getColumnModel().getColumn(2).setCellRenderer(r);

         jTable1.getColumnModel().getColumn(3).setPreferredWidth(108);
         jTable1.getColumnModel().getColumn(3).setResizable(false);

         jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);
         jTable1.getColumnModel().getColumn(4).setResizable(false);
         jTable1.getColumnModel().getColumn(4).setCellRenderer(r);

         jTable1.getColumnModel().getColumn(5).setPreferredWidth(100);
         jTable1.getColumnModel().getColumn(5).setResizable(false);
         jTable1.getColumnModel().getColumn(5).setCellRenderer(r);
         
         jTable1.getTableHeader().setReorderingAllowed(false);
         jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
         
         jScrollPane1.setViewportView(jTable1);
         jScrollPane1.setBounds(0, 210, 793, 210);

         //Total Desconto
         painelCentral.add(labelTotalDesconto);
         labelTotalDesconto.setText("Total");
         labelTotalDesconto.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
         labelTotalDesconto.setBounds(580, 460, 160, 20);
         

         painelCentral.add(campoTotalDesconto);
         campoTotalDesconto.setFont(new java.awt.Font("Courier New", 1, 24));
         campoTotalDesconto.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 2));         
         campoTotalDesconto.setBounds(625, 430, 160, 25);
         campoTotalDesconto.setHorizontalAlignment(JTextField.RIGHT);

         //total Desconto
         painelCentral.add(labelTotal);
         labelTotal.setText("Desconto");
         labelTotal.setFont(new java.awt.Font("MS Sans Serif",Font.BOLD,16));
         labelTotal.setBounds(545, 430, 120, 20);

         painelCentral.add(campoTotal);
         campoTotal.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 2));
         campoTotal.setFont(new java.awt.Font("Courier New", 1, 24));         
         campoTotal.setBounds(625, 460, 160, 25);
         campoTotal.setHorizontalAlignment(JTextField.RIGHT);
         
         
         campoDesconto.setFocusable(false);
         campoValor.setFocusable(false);
         campoTotal.setFocusable(false);
         campoTotalDesconto.setFocusable(false);                  
         campoOperacao.setFocusable(false);
         campoCodigoProduto.setFocusable(false);
         campoDescricaoProduto.setFocusable(false);
         campoQuantidade.setFocusable(false);
         jTable1.setFocusable(false);
         
         
         getPainel().add(painelCentral);

    }
    	
	 

	public void setCampoDesconto(String testo) {
		campoDesconto.setText(testo);
	}
		

	public void setCampoOperacao(String testo) {
		campoOperacao.setText(testo);
	}

	public void setCampoCodigoProduto(String testo) {
		campoCodigoProduto.setText(testo);
	}

	public void setCampoDescricaoProduto(String testo) {
		campoDescricaoProduto.setText(testo);
	}

	public void setCampoQuantidade(String testo) {
		campoQuantidade.setText(testo);
	}

	public void setCampoTotal(String testo) {
		campoTotal.setText(testo);
	}

	public void setCampoValor(String testo) {
		campoValor.setText(testo);
	}


	public void setCampoCliente(String campoCliente) {
		this.campoCliente.setText(campoCliente);
	}
	
	public void zerarTabela(){
        
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"Cod","Produto", "Valor", "Quantidade", "Desconto Item", "Total"}
        ){
        
        public boolean isCellEditable(int rowIndex, int columnIndex)
        {
            return false;
        }
        });
                
		
//		for (int i = 0; i < jTable1.getModel().getRowCount(); i++) {
//			((DefaultTableModel)jTable1.getModel()).removeRow(i);			
//		} 

	}
	
	public void zerarTabelaSeparacao(){
        
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"Código","Produto", "Valor", "Quantidade", "Desconto no Item", "Total", "Separação"}
        ){
        
        public boolean isCellEditable(int rowIndex, int columnIndex)
        {
            return false;
        }
        });

	}
	
	public void adicionarRegistroTabela(String seq,String produto, String valor, String quantidade, String descont, String total){

		javax.swing.table.DefaultTableModel table = (javax.swing.table.DefaultTableModel)jTable1.getModel();
		
	     Object[] linha = {seq,produto, valor, quantidade, descont, total};    
	     table.insertRow(0, linha);   

        
	}
	
	public void adicionarRegistroTabelaSeparacao(String codigoExterno, String produto, String valor, String quantidade, String descont, String total, String totalSeparacao){

		javax.swing.table.DefaultTableModel table = (javax.swing.table.DefaultTableModel)jTable1.getModel();
		
	     Object[] linha = {codigoExterno,produto, valor, quantidade, descont, total,totalSeparacao};    
	     table.insertRow(0, linha);   

        
	}


	public javax.swing.JTextField getCampoTotalDesconto() {
		return campoTotalDesconto;
	}


	public void setCampoTotalDesconto(String campoTotalDesconto) {
		this.campoTotalDesconto.setText(campoTotalDesconto);
	}
	
	public static void main(String[] args) {
		TelaAVInicial t = new TelaAVInicial();
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(f.DISPOSE_ON_CLOSE);
		f.add(t.getPainel());
		f.setSize(800, 600);
		f.setVisible(true);
		t.adicionarRegistroTabela("001","AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA","9.999.999,99","9.999.999,999","9.999.999,99","9.999.999,99");
	}
	
	private JPanel painelCentral;
	private JPanel painelTop;
	private JLabel imgLogo;

	class FormatRenderer extends DefaultTableCellRenderer {
	    private Format formatter;
	 
	    public FormatRenderer(Format formatter) {
	        if (formatter==null)
	            throw new NullPointerException();
	        this.formatter = formatter;
	    }
	 
	    protected void setValue(Object obj) {
	        setText(obj==null? "" : formatter.format(obj));
	    }
	}
   
	
}