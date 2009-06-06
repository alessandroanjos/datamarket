package com.infinity.datamarket.pdv.gerenciadorperifericos.components;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPasswordField;

public class LimitedPasswordField extends JPasswordField{
	private byte maxLength=0;
	private int mascara=2;
	private int[] rangeNumber = new int[]{0x30,0x31,0x32,0x33,0x34,0x35,0x36,0x37,0x38,0x39,0x60,0x61,0x62,0x63,0x64,0x65,0x66,0x67,0x68,0x69};
	private int[] rangeCotrole = new int[]{'\n','\b',0x1B};


	public static final int NUMERICO = 0;
	public static final int ALFABETICO = 1;
	public static final int ALFANUMERICO = 2;

	public void paint(Graphics g) {
		try{
			super.paint(g);
		}catch (Exception e){

		}
	}

	public LimitedPasswordField(int maxLength){
		super();
		this.maxLength= (byte)maxLength;
		this.addKeyListener(new LimitedKeyListener());
	}

	public void setMaxLength(int maxLength){
		this.maxLength= (byte)maxLength;
		update();
	}

	public void setMascara(int mascara){
		this.mascara = mascara;
	}

	private void update(){
		if (getText().length()>maxLength){
			setText(getText().substring(0,maxLength));
			setCaretPosition(maxLength);
		}
	}

	public void setText(String arg0){
		super.setText(arg0);
		update();
	}

	public void paste(){
		super.paste();
		update();
	}

	private boolean ehValorNumerico(int key){
		for (int i = 0;i < rangeNumber.length;i++){
			if (rangeNumber[i] == key){
				return true;
			}
		}
		return false;
	}

	private boolean ehValorControle(int key){
		for (int i = 0;i < rangeCotrole.length;i++){
			if (rangeCotrole[i] == key){
				return true;
			}
		}
		return false;
	}

	//Classes Internas
	private class LimitedKeyListener extends KeyAdapter{
		private boolean backspace= false;
		private boolean trava=false;

		public void keyPressed(KeyEvent e){
			if (!ehValorControle(e.getKeyCode())){
				if (mascara == NUMERICO){
					trava = !ehValorNumerico(e.getKeyCode());
				}
			}
			backspace=(e.getKeyCode()==8);
		}

		public void keyTyped(KeyEvent e){
			if ((!backspace	&& getText().length()>maxLength-1) || trava){
				e.consume();
			}
		}
	}
}
