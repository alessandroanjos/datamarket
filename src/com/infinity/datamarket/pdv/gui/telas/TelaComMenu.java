package com.infinity.datamarket.pdv.gui.telas;

import java.awt.Color;
import java.io.Serializable;

import javax.swing.JPanel;

import com.infinity.datamarket.comum.util.Util;

public abstract class TelaComMenu extends Tela {

	public abstract void adicionarLayoutMenu(String layout);

	public abstract void apagarLayoutMenu();

}