package com.infinity.datamarket.pdv.gerenciadorperifericos.window;

import java.awt.Color;

import javax.swing.JFrame;

import com.infinity.datamarket.pdv.gui.telas.Tela;


public class WindowFrame implements Window{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5771050092104776814L;
	private JFrame frame;
	public WindowFrame(){
		frame = new JFrame("INFINITY - DATAMARKET - PDV");
		frame.setFocusable(false);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	public void setTamanho(int altura,int largura){
		this.frame.setBounds(0,0,altura,largura);
	}
	public void setCor(int r, int g, int b){
		this.frame.setBackground(new Color(r,g,b));
	}
	public void iniciar(){
		this.frame.setVisible(true);
	}
	public void atualizaTela(Tela tela){
		frame.getContentPane().removeAll();
		frame.getContentPane().add(tela.getPainel());
		frame.repaint();
		frame.requestFocusInWindow();
	}

	public JFrame getFrame() {
		return frame;
	}
	
	public void atualizaLote(int lote){
		frame.setTitle("INFINITY - DATAMARKET - PDV | DADOS "+lote);
	}

}
