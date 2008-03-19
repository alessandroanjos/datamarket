package com.infinity.datamarket.pdv.gerenciadorperifericos.components;

	import java.awt.Toolkit;
	import java.text.DecimalFormat;
	import java.text.NumberFormat;
	import java.util.Locale;

	import javax.swing.JTextField;
	import javax.swing.text.AttributeSet;
	import javax.swing.text.BadLocationException;
	import javax.swing.text.PlainDocument;
	import javax.swing.text.SimpleAttributeSet;

	/**
	 * Documento utilizado para tratar um campo numérico em um {@link JTextField}
	 *
	 * @author Henrique Spindola (<a href="mailto:henrique.spindola@csi.com.br">henrique.spindola@csi.com.br</a>)
	 *
	 */
	public class MoneyDocument extends PlainDocument {

		private static final long serialVersionUID = 5844727332166134705L;
		private static final Locale LOCALE_PORTUGUES_BRASIL = new Locale("pt", "BR");
		private static final int TAMANHO_MAXIMO_CAMPO = 17;
		private NumberFormat numberFormat;

		long valorAntigo = 0;

		public void insertString(final int offs, final String str,
				final AttributeSet a) throws BadLocationException {

			if (str.startsWith(getNumberFormat().getCurrency().getSymbol())) {
				try {
					// tamanho maximo do campo
					if (getLength() < TAMANHO_MAXIMO_CAMPO) {

						NumberFormat nf = getNumberFormat();
						//verificando se é numero
						nf.parse(str).doubleValue();
						remove(0, getLength());
						super.insertString(0, str, a);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if ("0123456789".indexOf(str) < 0) {
				Toolkit.getDefaultToolkit().beep();
				return;
			} else {
				try {
					// tamanho maximo do campo
					if (getLength() < TAMANHO_MAXIMO_CAMPO) {
						String textoCampo =
							getText(0, getLength()).replaceAll("\\,", "");

						NumberFormat nf = getNumberFormat();
						if (textoCampo != null && !"".equals(textoCampo)) {
							valorAntigo = nf.parse(textoCampo).longValue();
						} else {
							valorAntigo = 0;
						}

						final long newNumber =
							valorAntigo * 10 + (Long.parseLong(str));
						remove(0, getLength());
						super.insertString(0, nf.format(newNumber / 100.0), a);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		public void remove(int offs, int len) throws BadLocationException {
			String textoCampo = getText(0, getLength());
			if (!"".equals(textoCampo)) {
				if (offs == 0 && len == getLength()) {
					// utilizado para limpar tudo ao inserir
					super.remove(offs, len);
				} else {
					// tratando a manipulação da virgula/formatacao do campo.
					String stringTemp =
						String.valueOf(getText(0, getLength()).charAt(offs - 1));
					super.remove(offs - 2, 3);
					insertString(getLength(), stringTemp, new SimpleAttributeSet());
				}
			}
		}

		public NumberFormat getNumberFormat() {
			if (numberFormat == null) {
				numberFormat =
					DecimalFormat.getCurrencyInstance(LOCALE_PORTUGUES_BRASIL);
				numberFormat.setGroupingUsed(true);
				numberFormat.setMaximumFractionDigits(2);
				numberFormat.setMaximumIntegerDigits(9);
			}
			return numberFormat;
		}
	}