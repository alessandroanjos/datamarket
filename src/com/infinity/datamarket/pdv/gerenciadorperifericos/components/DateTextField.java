package com.infinity.datamarket.pdv.gerenciadorperifericos.components;

	import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.MaskFormatter;

	public class DateTextField  extends JTextField  implements FocusListener,
			KeyListener, MouseListener 
	{

		private static final long serialVersionUID = -6462499418000255472L;

		
		/**
		 * Construtor vazio
		 */
		
		
		public DateTextField() {    		   
			setDocument(new DateDocument());
			setHorizontalAlignment(SwingConstants.RIGHT);
			adicionaListeners();
			setText(DateDocument.mascara);
		}

		public void paint(Graphics g) {
			try{
				super.paint(g);
			}catch (Exception e){

			}
		}

		/**
		 * Adiciona os listeners no campo de texto.
		 */
		private void adicionaListeners() {
			addFocusListener(this);
			addKeyListener(this);
			addMouseListener(this);
		}

		/**
		 * Joga o cursor (caret) para a ultima posição.
		 *
		 */
		private void atualizaCursor() {
			setCaretPosition(getText().length());
		}

		/**
		 * Mérodo utilizado para resgatar o valor numérico do conteúdo do campo.
		 * @return double O valor numérico do campo
		 */
		public String getValor(){
			String valor = getText().replaceAll("/","");
			valor = valor.replaceAll(" ","");
			if (valor.length() != 8){
				return "";
			}
			return getText();
		}

		/**
		 * Define um novo valor para o campo.
		 * @param novoValor o novo valor do campo.
		 */
		public void setValor(Date novoValor){
			try {
				DateDocument doc = (DateDocument) getDocument();
				doc.getDateFormat().parse(getText(0, doc.getLength()));
				setText(doc.getDateFormat().format(novoValor));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * Tratamento de ações ao receber foco.
		 */
		public void focusGained(FocusEvent e) {
			if (e.getSource() == this) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						atualizaCursor();
						//selectAll();
					}
				});
			}
		}

		/**
		 * Tratamento de ações ao pressionar uma tecla.
		 */
		public void keyPressed(KeyEvent e) {
			if (e.getSource() == this) {

//				// teclas validas: Backspace, teclado numerico,
//				// "numeros" do teclado alpha
//				if ((e.getKeyCode() >= KeyEvent.VK_0 && e.getKeyCode() <= KeyEvent.VK_9)
//						|| (e.getKeyCode() >= 96 && e.getKeyCode() <= 105)
//						|| (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
//					//teclas válidas, proceder normalmente
//				} else {
//					// reter o evento
//					e.consume();
//				}

				//teclas invalidas: ctrl + v
				if ((e.getKeyCode() == KeyEvent.VK_V && e.isControlDown())) {
					e.consume();
				} else if(e.getKeyCode() == KeyEvent.VK_LEFT ||
						e.getKeyCode() == KeyEvent.VK_RIGHT){
					e.consume();
				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER && getText().length() < 10) {
					e.consume();
				}
			}
		}

		/**
		 * Tratamento de ações ao clicar no componente.
		 */
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() == this) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						atualizaCursor();
					}
				});
			}
		}

		public void focusLost(FocusEvent e) {}
		public void keyReleased(KeyEvent e) {
			if (e.getSource() == this && this.getText().trim().equals("")) {
				setText("");
				try{
					atualizaCursor();
				}catch(Exception ex){

				}
			}
		}
		public void keyTyped(KeyEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {	}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}

		public static void main(String[] a){
			JFrame p = new JFrame();
			p.setBounds(0,0,100,100);
			DateTextField b = new DateTextField();
			p.getContentPane().add(b);
			p.setVisible(true);
			
			
		}
	}