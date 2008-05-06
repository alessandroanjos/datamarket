package com.infinity.datamarket.pdv.gerenciadorperifericos.components;

	import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

	public class PercentTextField  extends JTextField implements FocusListener,
			KeyListener, MouseListener {

		private static final long serialVersionUID = -6462499418000255472L;

		/**
		 * Construtor vazio
		 */
		public PercentTextField() {
			setDocument(new PercentDocument());
			setHorizontalAlignment(SwingConstants.RIGHT);
			adicionaListeners();
			setText("0");
		}

		public void paint(Graphics g) {
			try{
				super.paint(g);
			}catch (Exception e){

			}
		}

		/**
		 * Construtor com valor inicial
		 */
		public PercentTextField(String valorInicial) {
			setDocument(new PercentDocument());
			setHorizontalAlignment(SwingConstants.RIGHT);
			adicionaListeners();
			setText(valorInicial);
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
		public double getValor(){
			double retorno = 0;

			try {
				PercentDocument doc = (PercentDocument) getDocument();
				retorno = doc.getNumberFormat().parse(getText(0, doc.getLength())).doubleValue();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return retorno;
		}

		/**
		 * Define um novo valor para o campo.
		 * @param novoValor o novo valor do campo.
		 */
		public void setValor(double novoValor){
			try {
				PercentDocument doc = (PercentDocument) getDocument();
				doc.getNumberFormat().parse(getText(0, doc.getLength())).doubleValue();
				setText(doc.getNumberFormat().format(novoValor));
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
				setText("0");
				atualizaCursor();
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
			PercentTextField b = new PercentTextField();
			p.getContentPane().add(b);
			p.setVisible(true);
		}
	}