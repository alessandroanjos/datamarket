package com.infinity.datamarket.pdv.gerenciadorperifericos.components;

	import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

	/**
	 * Documento utilizado para tratar um campo numérico em um {@link JTextField}
	 *
	 * @author Henrique Spindola (<a href="mailto:henrique.spindola@csi.com.br">henrique.spindola@csi.com.br</a>)
	 *
	 */
	public class NumberDocument extends PlainDocument {

		private static final long serialVersionUID = 5844727332166134705L;
		private static final Locale LOCALE_PORTUGUES_BRASIL = new Locale("pt", "BR");
		private int tamanhoMaximo;
		private NumberFormat numberFormat;
		
		
		public NumberDocument(int tamanhoMaximo){
			this.tamanhoMaximo = tamanhoMaximo;
		}
				
		public void insertString(final int offs, final String str,
				final AttributeSet a) throws BadLocationException {

			try{
				Long.parseLong(str);
			}catch(NumberFormatException e){
				return;
			}
			try {
				// tamanho maximo do campo
				if (getLength() < tamanhoMaximo) {
					String textoCampo = getText(0, getLength());

					NumberFormat nf = getNumberFormat();

					final long newNumber = Long.parseLong(str);
					remove(0, getLength());
					super.insertString(0,textoCampo+nf.format(newNumber), a);
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}

		public void remove(int offs, int len) throws BadLocationException {
			String textoCampo = getText(0, getLength());
			if (!"".equals(textoCampo)) {
				super.remove(offs, len);
			}
		}

		public NumberFormat getNumberFormat() {
			if (numberFormat == null) {
				numberFormat =
					DecimalFormat.getIntegerInstance(LOCALE_PORTUGUES_BRASIL);
				numberFormat.setGroupingUsed(true);
				numberFormat.setMaximumFractionDigits(0);
				numberFormat.setMaximumIntegerDigits(13);
			}
			return numberFormat;
		}
	}