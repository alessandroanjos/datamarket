/*
 * TelaInicial.java
 *
 * Created on 24 de Janeiro de 2007, 13:28
 */

package com.infinity.datamarket.av.gui.telas;

import com.infinity.datamarket.pdv.gui.telas.Tela;

/**
 *
 * @author  wagner.medeiros
 */
public class TelaAVInicial extends Tela{
	/**
	 * 
	 */
	private static final long serialVersionUID = -631336578969656451L;
    // Variables declaration - do not modify                     
    private javax.swing.JLabel labelLoja;
    private javax.swing.JLabel labelUsuario;
    private javax.swing.JLabel labelComponente;
    private javax.swing.JLabel labelSenha;
    private javax.swing.JLabel labelOperacao;
    private javax.swing.JLabel labelCodigoProduto;
    private javax.swing.JLabel labelQuantidade;
    private javax.swing.JLabel labelDesconto;
    private javax.swing.JPasswordField jPasswordField1;
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
    
    public TelaAVInicial() {
        initComponents();
    }


    private void initComponents() {
    	menssagem = new javax.swing.JLabel();
         labelLoja = new javax.swing.JLabel();
         campoComponente = new javax.swing.JTextField();
         campoComponente.setText("campoComponente");
         campoLoja = new javax.swing.JTextField();
         labelUsuario = new javax.swing.JLabel();
         labelComponente = new javax.swing.JLabel();
         jScrollPane1 = new javax.swing.JScrollPane();
         jTable1 = new javax.swing.JTable();
         campoUsuario = new javax.swing.JTextField();
         labelSenha = new javax.swing.JLabel();
         labelOperacao = new javax.swing.JLabel();
         campoOperacao = new javax.swing.JTextField();
         labelCodigoProduto = new javax.swing.JLabel();
         campoCodigoProduto = new javax.swing.JTextField();
         campoDescricaoProduto = new javax.swing.JTextField();
         campoQuantidade = new javax.swing.JTextField();
         labelQuantidade = new javax.swing.JLabel();
         labelDesconto = new javax.swing.JLabel();
         campoDesconto = new javax.swing.JTextField();
         jPasswordField1 = new javax.swing.JPasswordField();

         labelLoja.setText("Loja");
         labelLoja.setBounds(10, 20, 20, 14);

          getPainel().add(campoComponente);
         campoComponente.setBounds(210, 20, 110, 19);

          getPainel().add(campoLoja);
         campoLoja.setBounds(40, 20, 90, 19);

         labelUsuario.setText("Usu\u00e1rio");
          getPainel().add(labelUsuario);
         labelUsuario.setBounds(340, 20, 36, 14);

         labelComponente.setText("campoComponente");
          getPainel().add(labelComponente);
          labelComponente.setBounds(140, 20, 61, 20);

         jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
         jScrollPane1.setBorder(null);
         jScrollPane1.setFont(new java.awt.Font("Tahoma", 0, 14));
         jTable1.setFont(new java.awt.Font("Tahoma", 0, 14));
         jTable1.setFont(new java.awt.Font("Tahoma", 0, 14));
         jTable1.setModel(new javax.swing.table.DefaultTableModel(
             new Object [][] {
                 {"sdfasdf", "2,00", "2", "1,00", "3,00"},
                 {null, null, null, null, null},
                 {null, null, null, null, null},
                 {null, null, null, null, null}
             },
             new String [] {
                 "Produto", "Valor", "Quantidade", "Desconto", "Total"
             }
         ));
         jTable1.setGridColor(new java.awt.Color(255, 255, 255));
         jTable1.setPreferredSize(new java.awt.Dimension(800, 217));
         jTable1.setSelectionBackground(new java.awt.Color(255, 255, 255));
         jScrollPane1.setViewportView(jTable1);

          getPainel().add(jScrollPane1);
         jScrollPane1.setBounds(0, 140, 800, 240);

          getPainel().add(campoUsuario);
         campoUsuario.setBounds(390, 20, 140, 19);

         labelSenha.setText("senha");
          getPainel().add(labelSenha);
         labelSenha.setBounds(560, 20, 29, 14);

         labelOperacao.setText("Opera\u00e7\u00e3o");
          getPainel().add(labelOperacao);
         labelOperacao.setBounds(10, 80, 47, 14);

          getPainel().add(campoOperacao);
         campoOperacao.setBounds(60, 80, 120, 19);

         labelCodigoProduto.setText("Produto");
          getPainel().add(labelCodigoProduto);
         labelCodigoProduto.setBounds(190, 80, 50, 14);

          getPainel().add(campoCodigoProduto);
         campoCodigoProduto.setBounds(240, 80, 100, 19);

         campoDescricaoProduto.setBackground(new java.awt.Color(236, 233, 216));
          getPainel().add(campoDescricaoProduto);
         campoDescricaoProduto.setBounds(340, 80, 140, 19);

          getPainel().add(campoQuantidade);
         campoQuantidade.setBounds(560, 80, 60, 19);

         labelQuantidade.setText("Quantidade");
          getPainel().add(labelQuantidade);
         labelQuantidade.setBounds(500, 80, 60, 14);

         labelDesconto.setText("Desconto");
          getPainel().add(labelDesconto);
          labelDesconto.setBounds(630, 80, 60, 14);

          getPainel().add(campoDesconto);
         campoDesconto.setBounds(680, 80, 90, 19);

          getPainel().add(jPasswordField1);
         jPasswordField1.setBounds(600, 20, 110, 23);

         
         campoComponente.setFocusable(false);
         campoDesconto.setFocusable(false);
         campoLoja.setFocusable(false);
         campoUsuario.setFocusable(false);
         campoOperacao.setFocusable(false);
         campoCodigoProduto.setFocusable(false);
         campoDescricaoProduto.setFocusable(false);
         campoQuantidade.setFocusable(false);

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
}