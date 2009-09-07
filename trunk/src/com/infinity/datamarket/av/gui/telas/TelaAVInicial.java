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
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    
    public TelaAVInicial() {
        initComponents();
    }


    private void initComponents() {
    	menssagem = new javax.swing.JLabel();
    	 jFrame1 = new javax.swing.JFrame();
         jLabel1 = new javax.swing.JLabel();
         jTextField1 = new javax.swing.JTextField();
         jTextField2 = new javax.swing.JTextField();
         jLabel2 = new javax.swing.JLabel();
         jLabel3 = new javax.swing.JLabel();
         jScrollPane1 = new javax.swing.JScrollPane();
         jTable1 = new javax.swing.JTable();
         jTextField3 = new javax.swing.JTextField();
         jLabel4 = new javax.swing.JLabel();
         jLabel5 = new javax.swing.JLabel();
         jTextField6 = new javax.swing.JTextField();
         jLabel6 = new javax.swing.JLabel();
         jTextField7 = new javax.swing.JTextField();
         jTextField8 = new javax.swing.JTextField();
         jTextField9 = new javax.swing.JTextField();
         jLabel7 = new javax.swing.JLabel();
         jLabel8 = new javax.swing.JLabel();
         jTextField10 = new javax.swing.JTextField();
         jPasswordField1 = new javax.swing.JPasswordField();

         jLabel1.setText("Loja");
         jLabel1.setBounds(10, 20, 20, 14);

          getPainel().add(jTextField1);
         jTextField1.setBounds(210, 20, 110, 19);

          getPainel().add(jTextField2);
         jTextField2.setBounds(40, 20, 90, 19);

         jLabel2.setText("Usu\u00e1rio");
          getPainel().add(jLabel2);
         jLabel2.setBounds(340, 20, 36, 14);

         jLabel3.setText("Componente");
          getPainel().add(jLabel3);
         jLabel3.setBounds(140, 20, 61, 20);

         jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
         jScrollPane1.setBorder(null);
         jScrollPane1.setFont(new java.awt.Font("Tahoma", 0, 14));
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

          getPainel().add(jTextField3);
         jTextField3.setBounds(390, 20, 140, 19);

         jLabel4.setText("senha");
          getPainel().add(jLabel4);
         jLabel4.setBounds(560, 20, 29, 14);

         jLabel5.setText("Opera\u00e7\u00e3o");
          getPainel().add(jLabel5);
         jLabel5.setBounds(10, 80, 47, 14);

          getPainel().add(jTextField6);
         jTextField6.setBounds(60, 80, 120, 19);

         jLabel6.setText("Produto");
          getPainel().add(jLabel6);
         jLabel6.setBounds(190, 80, 50, 14);

          getPainel().add(jTextField7);
         jTextField7.setBounds(240, 80, 100, 19);

         jTextField8.setBackground(new java.awt.Color(236, 233, 216));
          getPainel().add(jTextField8);
         jTextField8.setBounds(340, 80, 140, 19);

          getPainel().add(jTextField9);
         jTextField9.setBounds(560, 80, 60, 19);

         jLabel7.setText("Quantidade");
          getPainel().add(jLabel7);
         jLabel7.setBounds(500, 80, 60, 14);

         jLabel8.setText("Desconto");
          getPainel().add(jLabel8);
         jLabel8.setBounds(630, 80, 60, 14);

          getPainel().add(jTextField10);
         jTextField10.setBounds(680, 80, 90, 19);

          getPainel().add(jPasswordField1);
         jPasswordField1.setBounds(600, 20, 110, 23);

    }
        private javax.swing.JLabel menssagem;

	public void setMenssagem(String menssagem){
		this.menssagem.setText(menssagem);
	}

}
