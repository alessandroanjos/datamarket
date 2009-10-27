/*
 * TelaInicial.java
 *
 * Created on 24 de Janeiro de 2007, 13:28
 */

package com.infinity.datamarket.av.gui.telas;

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
    private javax.swing.JLabel labelLoja;
    private javax.swing.JLabel labelTotal;
    private javax.swing.JLabel labelValor;
    private javax.swing.JLabel labelUsuario;
    private javax.swing.JLabel labelComponente;
    private javax.swing.JLabel labelCliente;
    private javax.swing.JLabel labelOperacao;
    private javax.swing.JLabel labelCodigoProduto;
    private javax.swing.JLabel labelQuantidade;
    private javax.swing.JLabel labelDesconto;
    private javax.swing.JTextField campoCliente;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField campoComponente;
    private javax.swing.JTextField campoDesconto;
    private javax.swing.JTextField campoLoja;
    private javax.swing.JTextField campoUsuario;
    private javax.swing.JTextField campoOperacao;
    private javax.swing.JTextField campoCodigoProduto;
    private javax.swing.JTextField campoDescricaoProduto;
    private javax.swing.JTextField campoQuantidade;
    private javax.swing.JTextField campoValor;
    private javax.swing.JTextField campoTotal;
    
    public TelaAVInicial() {
        initComponents();
    }


    private void initComponents() {
    	menssagem = new javax.swing.JLabel();
         labelLoja = new javax.swing.JLabel();
         campoComponente = new javax.swing.JTextField();
         campoLoja = new javax.swing.JTextField();
         labelUsuario = new javax.swing.JLabel();
         labelComponente = new javax.swing.JLabel();
         jScrollPane1 = new javax.swing.JScrollPane();
         jTable1 = new javax.swing.JTable();
         campoUsuario = new javax.swing.JTextField();
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
         campoTotal = new javax.swing.JTextField();

         getPainel().add(labelLoja);
         labelLoja.setText("Loja");
         labelLoja.setFont(new java.awt.Font("Courier New", 1, 24));
         labelLoja.setForeground(new java.awt.Color(255, 255, 255));
         labelLoja.setBounds(10, 20, 60, 20);

         getPainel().add(campoLoja);
         campoLoja.setFont(new java.awt.Font("Courier New", 1, 24));
         campoLoja.setBounds(70, 20, 60, 25);

         getPainel().add(labelComponente);
         labelComponente.setText("Componente");
         labelComponente.setFont(new java.awt.Font("Courier New", 1, 24));
         labelComponente.setForeground(new java.awt.Color(255, 255, 255));
         labelComponente.setBounds(160, 20, 150, 20);

         getPainel().add(campoComponente);
         campoComponente.setFont(new java.awt.Font("Courier New", 1, 24));
         campoComponente.setBounds(305, 20, 100, 25);

         getPainel().add(labelUsuario);
         labelUsuario.setText("Usu\u00e1rio");
         labelUsuario.setFont(new java.awt.Font("Courier New", 1, 24));
         labelUsuario.setForeground(new java.awt.Color(255, 255, 255));
         labelUsuario.setBounds(430, 20, 120, 20);

         getPainel().add(campoUsuario);
         campoUsuario.setFont(new java.awt.Font("Courier New", 1, 24));
         campoUsuario.setBounds(535, 20, 250, 25);


         
// Segunda linha
         
         getPainel().add(labelOperacao);
         labelOperacao.setText("Opera\u00e7\u00e3o");
         labelOperacao.setFont(new java.awt.Font("Courier New", 1, 24));
         labelOperacao.setForeground(new java.awt.Color(255, 255, 255));
         labelOperacao.setBounds(10, 60, 120, 20);

         getPainel().add(campoOperacao);
         campoOperacao.setFont(new java.awt.Font("Courier New", 1, 24));
         campoOperacao.setBounds(125, 60, 200, 25);

         getPainel().add(labelCliente);
         labelCliente.setText("Cliente");
         labelCliente.setFont(new java.awt.Font("Courier New", 1, 24));
         labelCliente.setForeground(new java.awt.Color(255, 255, 255));
         labelCliente.setBounds(355, 60, 120, 20);

         getPainel().add(campoCliente);
         campoCliente.setFont(new java.awt.Font("Courier New", 1, 24));
         campoCliente.setBounds(465, 60, 320, 25);


         // Terceira linha 
         getPainel().add(labelCodigoProduto);
         labelCodigoProduto.setText("Produto");
         labelCodigoProduto.setFont(new java.awt.Font("Courier New", 1, 24));
         labelCodigoProduto.setForeground(new java.awt.Color(255, 255, 255));
         labelCodigoProduto.setBounds(10, 100, 120, 20);

         getPainel().add(campoCodigoProduto);
         campoCodigoProduto.setFont(new java.awt.Font("Courier New", 1, 24));
         campoCodigoProduto.setBounds(115, 100, 100, 25);

         getPainel().add(campoDescricaoProduto);
         campoDescricaoProduto.setBackground(new java.awt.Color(236, 233, 216));
         campoDescricaoProduto.setFont(new java.awt.Font("Courier New", 1, 20));
         campoDescricaoProduto.setBounds(215, 100, 570, 25);


         // quarta linha 
         getPainel().add(labelValor);
         labelValor.setText("Valor");         
         labelValor.setFont(new java.awt.Font("Courier New", 1, 24));
         labelValor.setForeground(new java.awt.Color(255, 255, 255));
         labelValor.setBounds(10, 140, 140, 20);

         getPainel().add(campoValor);
         campoValor.setFont(new java.awt.Font("Courier New", 1, 24));
         campoValor.setBounds(85, 140, 180, 25);

         getPainel().add(labelQuantidade);
         labelQuantidade.setText("Quantidade");         
         labelQuantidade.setFont(new java.awt.Font("Courier New", 1, 24));
         labelQuantidade.setForeground(new java.awt.Color(255, 255, 255));
         labelQuantidade.setBounds(270, 140, 140, 20);

         getPainel().add(campoQuantidade);
         campoQuantidade.setFont(new java.awt.Font("Courier New", 1, 24));
         campoQuantidade.setBounds(420, 140, 120, 25);

         getPainel().add(labelDesconto);
         labelDesconto.setText("Desconto");
         labelDesconto.setFont(new java.awt.Font("Courier New", 1, 24));
         labelDesconto.setForeground(new java.awt.Color(255, 255, 255));
         labelDesconto.setBounds(545, 140, 120, 20);

         getPainel().add(campoDesconto);
         campoDesconto.setFont(new java.awt.Font("Courier New", 1, 24));
         campoDesconto.setBounds(665, 140, 120, 25);

         // grid
         getPainel().add(jScrollPane1);
         jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
         jScrollPane1.setBorder(null);
         jScrollPane1.setFont(new java.awt.Font("Tahoma", 0, 14));
         jTable1.setFont(new java.awt.Font("Tahoma", 0, 14));
         this.zerarTabela();
         jTable1.setGridColor(new java.awt.Color(255, 255, 255));
         jTable1.setPreferredSize(new java.awt.Dimension(800, 217));
         jTable1.setSelectionBackground(new java.awt.Color(255, 255, 255));
         jScrollPane1.setViewportView(jTable1);
         jScrollPane1.setBounds(0, 180, 800, 240);

         //total
         getPainel().add(labelTotal);
         labelTotal.setText("Total");
         labelTotal.setFont(new java.awt.Font("Courier New", 1, 24));
         labelTotal.setForeground(new java.awt.Color(255, 255, 255));
         labelTotal.setBounds(545, 430, 120, 20);

         getPainel().add(campoTotal);
         campoTotal.setFont(new java.awt.Font("Courier New", 1, 24));
         campoTotal.setBounds(625, 430, 160, 25);

         
         
         getPainel().setBackground(new java.awt.Color(0, 0, 100));
         
         campoComponente.setFocusable(false);
         campoDesconto.setFocusable(false);
         campoValor.setFocusable(false);
         campoTotal.setFocusable(false);
         campoLoja.setFocusable(false);
         campoUsuario.setFocusable(false);
         campoOperacao.setFocusable(false);
         campoCodigoProduto.setFocusable(false);
         campoDescricaoProduto.setFocusable(false);
         campoQuantidade.setFocusable(false);
         jTable1.setFocusable(false);

    }
        private javax.swing.JLabel menssagem;

	public void setMenssagem(String menssagem){
		this.menssagem.setText(menssagem);
	}
	
	 public void setCampoComponente(String testo) {
		campoComponente.setText(testo);
	}

	public void setCampoDesconto(String testo) {
		campoDesconto.setText(testo);
	}

	public void setCampoLoja(String testo) {
		campoLoja.setText(testo);
	}

	public void setCampoUsuario(String testo) {
		campoUsuario.setText(testo);
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
            new String [] {"Produto", "Valor", "Quantidade", "Desconto", "Total"}
        ){
        
        public boolean isCellEditable(int rowIndex, int columnIndex)
        {
            return false;
        }
        });

	}
	
	public void adicionarRegistroTabela(String produto, String valor, String quantidade, String descont, String total){

		javax.swing.table.DefaultTableModel table = (javax.swing.table.DefaultTableModel)jTable1.getModel();
		
	     Object[] linha = {produto, valor, quantidade, descont, total};    
	     table.insertRow(0, linha);   

        
	}
	
}