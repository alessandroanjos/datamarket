package com.infinity.datamarket.pdv.gui.telas.swing;

import javax.swing.JFrame;

import com.infinity.datamarket.comum.util.ConcentradorParametro;

public class TelaInicial extends JFrame{

    public TelaInicial() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">
    private void initComponents() {
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        setSize(356, 190);
		setResizable(false);
        getContentPane().setLayout(null);

        getContentPane().add(jProgressBar1);
        jProgressBar1.setBounds(0, 140, 350, 20);

        jLabel1.setIcon(new javax.swing.ImageIcon(ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.LOGO_CLIENTE).getValor()));
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 350, 90);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jLabel2.setBounds(100, 110, 250, 40);

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Iniciando ...");

        getContentPane().add(jLabel2);

        getContentPane().setBackground(new java.awt.Color(0, 0, 102));

    }

    // Variables declaration - do not modify
    private javax.swing.JLabel jLabel1;
    public javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration

}


