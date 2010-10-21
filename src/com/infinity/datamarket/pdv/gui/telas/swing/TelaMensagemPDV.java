package com.infinity.datamarket.pdv.gui.telas.swing;

import javax.swing.JOptionPane;


public class TelaMensagemPDV extends javax.swing.JFrame implements Runnable {
    

    public TelaMensagemPDV(int tamanho, int altura, String mensagem, boolean comBotao, long tempoExibicaoSegundos) {
        initComponents(tamanho, altura,mensagem, comBotao, tempoExibicaoSegundos);
    }


    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton jButton1;
	
    private void initComponents(int tamanho, int altura,String mensagem, boolean comBotao, long tempoExibicaoSegundos) {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 48));
        jLabel1.setText(mensagem);
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 20, 400, 70);
        jLabel1.getAccessibleContext().setAccessibleName("jLabelMensagem");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 48));
        jLabel2.setText("Aguarde...");
//        getContentPane().add(jLabel2);
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
//    	TelaMensagemPDV c = new TelaMensagemPDV(800,600,"Carregando Dados",true,3);
    
    	JOptionPane.showMessageDialog(null, "Carregando", "Atenção", JOptionPane.INFORMATION_MESSAGE);
    }
    
                         
    
    public void run() {
   
		
	}
    
    public void stop(){
    	
    	this.setVisible(false);
    	
    }
                       
    
}
