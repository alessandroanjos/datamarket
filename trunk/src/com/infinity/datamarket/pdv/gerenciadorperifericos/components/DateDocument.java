package com.infinity.datamarket.pdv.gerenciadorperifericos.components;

	import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.MaskFormatter;
import javax.swing.text.PlainDocument;
import javax.swing.text.SimpleAttributeSet;

import com.infinity.datamarket.comum.util.StringUtil;

	/**
	 * Documento utilizado para tratar um campo numérico em um {@link JTextField}
	 *
	 * @author Henrique Spindola (<a href="mailto:henrique.spindola@csi.com.br">henrique.spindola@csi.com.br</a>)
	 *
	 */
	public class DateDocument extends PlainDocument {

		private static final long serialVersionUID = 5844727332166134705L;
		private static final Locale LOCALE_PORTUGUES_BRASIL = new Locale("pt", "BR");
		private static final int TAMANHO_MAXIMO_CAMPO = 8;
		private DateFormat dateFormat;
		public static final String mascara = "  /  /    ";

		public void insertString(final int offs, final String str,
				final AttributeSet a) throws BadLocationException {

//			if (str.startsWith(getNumberFormat().getCurrency().getSymbol())) {
//				try {
//					// tamanho maximo do campo
//					if (getLength() < TAMANHO_MAXIMO_CAMPO) {
//
//						NumberFormat nf = getNumberFormat();
//						//verificando se é numero
//						nf.parse(str).doubleValue();
//						remove(0, getLength());
//						super.insertString(0, str, a);
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			} else 
			if (str.equals(mascara)){
				super.insertString(0,mascara, a);
				return;
			}
			if ("0123456789".indexOf(str) < 0) {
				Toolkit.getDefaultToolkit().beep();
				return;
			} else {
				try {
					
					String textoCampo =
						getText(0, getLength()).replace(" ", "");
					textoCampo =
						textoCampo.replace("/", "");
					
					if (textoCampo.length() < TAMANHO_MAXIMO_CAMPO) {
						textoCampo = textoCampo + str;
						textoCampo = StringUtil.completaString(textoCampo,8, ' ', true);
					}					
					String dia =
						textoCampo.substring(0, 2);
					String mes =
						textoCampo.substring(2, 4);
					String ano =
						textoCampo.substring(4, 8);
					remove(0, getLength());
					super.insertString(0,dia+"/"+mes+"/"+ano, a);
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
					textoCampo =
						getText(0, getLength()).replace(" ", "");
					textoCampo =
						textoCampo.replace("/", "");
					if (textoCampo.length() > 0){
						textoCampo = textoCampo.substring(0, textoCampo.length()-1);
						textoCampo = StringUtil.completaString(textoCampo,8, ' ', true);					
						String dia =
							textoCampo.substring(0, 2);
						String mes =
							textoCampo.substring(2, 4);
						String ano =
							textoCampo.substring(4, 8);
						super.remove(0, getLength());
						super.insertString(0,dia+"/"+mes+"/"+ano, null);
					}
				}
			}
		}

		public DateFormat getDateFormat() {
			if (dateFormat == null) {
				dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM,LOCALE_PORTUGUES_BRASIL);
//				dateFormat.setGroupingUsed(true);
//				dateFormat.setMaximumFractionDigits(2);
//				dateFormat.setMaximumIntegerDigits(9);
			}
			return dateFormat;
		}
	}