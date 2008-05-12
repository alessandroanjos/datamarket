package com.infinity.datamarket.pdv.gui.telas.swing;

import java.awt.Dimension;
import java.awt.Toolkit;

public class TelaCargaDados extends javax.swing.JFrame implements Runnable {
    

    public TelaCargaDados(int tamanho, int altura) {
        initComponents(tamanho, altura);
    }
    
    private void initComponents(int tamanho, int altura) {
    	
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        getContentPane().setLayout(null);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 48));
        jLabel1.setText("Carregando Dados");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 20, 400, 70);
        jLabel1.getAccessibleContext().setAccessibleName("jLabelMensagem");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 48));
        jLabel2.setText("Aguarde...");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(90, 90, 240, 80);
        
        
        this.setTitle("Atenção");
        
        
//		Calcula a posição do frame a partir da resolucao usada
		int x = (tamanho - this.getWidth()) / 2;
		int y = (altura - this.getHeight()) / 2;

//		Define a posicao (centralizada)
		setLocation(x, y);
        
        setSize(430, 210);
        
        this.setVisible(true);
        
    }
    
    public static void main(String args[]) {
    	TelaCargaDados c = new TelaCargaDados(800,600);
    }
    
                         
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
	
    
    public void run() {
   
		
	}
    
    public void stop(){
    	
    	this.setVisible(false);
    	
    }
                       
    
}
