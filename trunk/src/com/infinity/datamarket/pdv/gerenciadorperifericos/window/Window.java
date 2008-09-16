package com.infinity.datamarket.pdv.gerenciadorperifericos.window;

import java.io.Serializable;

import javax.swing.JFrame;

import com.infinity.datamarket.pdv.gui.telas.Tela;

public interface Window extends Serializable {
	public void setTamanho(int altura,int largura);
	public void setCor(int r, int g, int b);
	public void iniciar();
	public void atualizaTela(Tela tela);
	public JFrame getFrame();
	public void atualizaLote(int lote);
}
