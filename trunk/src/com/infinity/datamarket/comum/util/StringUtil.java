package com.infinity.datamarket.comum.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe utilitária com métodos para manipulação de Strings.
 *
 * @author Carlos Figueira
 * @author Emerson Polesi (<a href="mailto:polesi@csi.com.br">polesi@csi.com.br</a>)
 */
public final class StringUtil {

	/**
	 * Indica truncamento.
	 */
	public static final int TRUNCAMENTO = 1;

	/**
	 * Constante usada para indicar arredondamento.
	 */
	public static final int ARREDONDA = 2;

	/**
	 * Constante usada para indicar que a string deve ser completada
	 * à esquerda.
	 */
	public static final int ESQUERDA = 3;

	/**
	 * Constante usada para indicar que a string deve ser completada
	 * à direita.
	 */
	public static final int DIREITA = 4;

	/**
	 * Construtor da classe, para impedir que ela seja instanciada.
	 */
	private StringUtil() {}

	/**
	 * Substitui os caracters passados em um conjunto por outro
	 *
	 * @param str a string da qual se deseja substituir os caracteres
	 *        conjunto o conjunto de caracteres que podem ser substituidos
	 *        caracter o caracter pelo qual sera substituido
	 *
	 * @return a string com as substituições
	 */
	private static String replaceChar(String str, String conjunto, String caracter) {
		Pattern p = Pattern.compile(conjunto, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		StringBuffer sb = new StringBuffer();

		while (m.find()) {
			m.appendReplacement(sb, caracter);
		}

		m.appendTail(sb);

		return sb.toString();
	}

	/**
	 * Remove a acentuação de uma string.
	 *
	 * @param str a string da qual se deseja remover os acentos.
	 *
	 * @return a string sem os acentos
	 */
	public static String removeAcentuacao(String str) {
		String retorno = str;

		retorno = replaceChar(retorno, "[áâãà]", "a");
		retorno = replaceChar(retorno, "[éêè]" , "e");
		retorno = replaceChar(retorno, "[íîì]" , "i");
		retorno = replaceChar(retorno, "[óôõò]", "o");
		retorno = replaceChar(retorno, "[úûüù]", "u");
		retorno = replaceChar(retorno, "[ç]"   , "c");

		return retorno;
	}

	/**
	 * Remove os espaços à esquerda e à direita da string.
	 *
	 * @param str a string da qual se deseja remover os espaços laterais.
	 *
	 * @return a string sem os espaços à direita e à esquerda.
	 */
	public static String allTrim(String str) {
		return str.trim();
	}
	/**
	 * Converte uma string em um array de bytes.
	 * A string deve possuir apenas caracteres ASCII não extendidos (0-127).
	 *
	 * @param str a string a ser convertida para um array de bytes.
	 *
	 * @return o array de bytes correspondente aos caracteres da string passada
	 *		como parâmetro.
	 */
	public static byte[] asciiToByteArray(String str) {
		char[] aux = str.toCharArray();
		byte[] result = new byte[aux.length];
		for (int i = 0; i < aux.length; i++) {
			result[i] = (byte) aux[i];
		}
		return result;
	}
	/**
	 * Converte um array de bytes em uma string com os caracteres
	 * correspondentes aos bytes deste array.
	 *
	 * @param byte[] O array de bytes a ser convertido em string.
	 *
	 * @return a string correspondente ao array de bytes.
	 */
	public static String byteToString(byte[] b) {
		return new String(b);
	}
	/**
	 * Centraliza uma string numa área de tamanho determinado.
	 * Caso o comprimento da string seja maior ou igual ao tamanho especificado
	 * da área, a string retornada será igual à passada como parâmetro.
	 * Caso o comprimento da string seja menor do que o tamanho especificado da
	 * área, serão inseridos espaços à esquerda e à direita da mesma de forma
	 * que ela fique centralizada nesta área.
	 *
	 * @param str a string a ser centralizada.
	 * @param tamanho o tamanho da área na qual a string ficará centralizada.
	 *
	 * @return a string centralizada numa área do tamanho especificado.
	 */
	public static String centraliza(String str, int tamanho) {
		int falta = tamanho - str.length();
		if (falta >= 0) {
			if (falta % 2 == 1) {
				str = str + " ";
				falta--;
			}
			while (falta > 0) {
				str = " " + str + " ";
				falta -= 2;
			}
		}
		return str;
	}
	/**
	 * Completa uma string à esquerda ou à direita com um determinado
	 * caractere até um tamanho especificado. Caso a string indicada tenha
	 * comprimento maior do que o tamanho especificado, a string retornada
	 * será igual à passada como parâmetro.
	 *
	 * @param str a string a ser completada com espaços.
	 * @param tamanho o tamanho da string com o complemento.
	 * @param complemento o caractere a ser usado para completar a string.
	 * @param lado <code>StringUtil.ESQUERDA</code> indica que o complemento
	 *		será feito à esquerda. <code>StringUtil.DIREITA</code> indica que
	 *		o complemento será feito à direita.
	 *
	 * @return a string com complemento.
	 */
	public static String completaString(String texto, int tamanho,
			char complemento, int lado) {
		return completaString(texto, tamanho, complemento, lado == ESQUERDA);
	}
	/**
	 * Completa uma string à esquerda ou à direita com um determinado
	 * caractere até um tamanho especificado. Caso a string indicada tenha
	 * comprimento maior do que o tamanho especificado, a string retornada
	 * será igual à passada como parâmetro.
	 *
	 * @param str a string a ser completada com espaços.
	 * @param tamanho o tamanho da string com o complemento.
	 * @param complemento o caractere a ser usado para completar a string.
	 * @param esquerda <code>true</code> indica que o complemento será feito à
	 *		esquerda. <code>false</code> indica que o complemento será feito à
	 *		direita.
	 *
	 * @return a string com complemento.
	 */
	public static String completaString(String texto, int tamanho,
			char complemento, boolean esquerda) {
		while (texto.length() < tamanho) {
			if (esquerda) {
				texto = complemento + texto;
			} else {
				texto = texto + complemento;
			}
		}
		return texto;
	}

	/**
	 * Recebe duas strings e insere entre elas uma quantidade n de um
	 * caracter específico de forma que a quantida de caracteres da
	 * string resultante seja igual ao tamanho passado. Se o tamanho
	 * passado for menor ou igual que as duas strings concatenadas, então
	 * a string resultante será as duas strings concatenadas.
	 * @param texto1 o texto que será alinhado a esquerda.
	 * @param texto2 o texto que será alinhado a direita.
	 * @param tamanho o tamanho da string com o complemento.
	 * @param complemento o caractere a ser usado para completar a string.
	 * @return a string com complemento.
	 */
	public static String completaStringCentro(String texto1, String texto2,
        	int tamanho,char complemento){
	    String retorno = texto1;
	    if (tamanho <= (texto1 + texto2).length()) {
            retorno = texto1 + texto2;
        } else {
            int espacoRestante = tamanho - texto1.length();
    	    retorno = retorno + completaString(texto2,espacoRestante,complemento,true);
        }
	    return retorno;
	}


	/**
	 * Cria uma string preenchida com cópias do caractere especificado.
	 * Exemplo: copia('*',5) retornará "*****".
	 *
	 * @param caractere o caractere que preencherá toda a string.
	 * @param quantidade o comprimento da string resultante.
	 *
	 * @return uma string preenchida com o caractere indicado.
	 */
	public static String copia(char caractere, int quantidade) {
		char[] c = new char[quantidade];
		for (int i = 0; i < quantidade; i++) {
			c[i] = caractere;
		}
		return new String(c);
	}
	/**
	 * Cria uma string com várias ocorrências de uma string especificada.
	 * Exemplo: copia("ABc",3) retornará "ABcABcABc".
	 *
	 * @param str a string a ser copiada.
	 * @param quantidade a quantidade de ocorrências da string passada como
	 *		parâmetro na nova string.
	 *
	 * @return uma string preenchida com cópias da string indicada.
	 */
	public static String copia(String str, int quantidade) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < quantidade; i++) {
			sb.append(str);
		}
		return sb.toString();
	}
	/**
	 * Conta a quantidade de ocorrências de um caractere dentro de uma string.
	 *
	 * @param c o caractere a ser procurado na string.
	 * @param s a string a qual será verificada a quantidade de ocorrências do
	 *		caractere.
	 *
	 * @return O número de vezes que o caractere ocorre na string. Caso a
	 *		string não contenha o caractere. será retornado zero.
	 */
	public static int count(char c, String s) {
		int retorno = 0;

		for (int i = 0 ; i < s.length() ; i++){
			if (s.charAt(i) == c) {
				retorno++;
			}
		}

		return retorno;
	}


	/**
	 * Duplica a ocorrência de um determinado caractere dentro de uma string.
	 * Exemplo: duplicaChar('#',"-#-##") resultará em "-##-####".
	 *
	 * @param caracter o caractere a ser duplicado.
	 * @param texto a string a qual se deseja duplicar a ocorrência do
	 *		caractere.
	 *
	 * @return Uma string que tem as ocorrências do caractere na string
	 *		especificada duplicadas. Caso a string especificada não contenha
	 *		ocorrência alguma do caratere, a string resultante será igual à
	 *		passada como parâmetro.
	 */
	public static String duplicaChar(char caracter, String texto) {
		try {
			int i=0;
			while (i < texto.length()) {
				if (texto.charAt(i++) == caracter) {
					if (i != texto.length()) {
						texto = texto.substring(0,i) +
								texto.substring(i-1,texto.length());
					} else {
						texto = texto + caracter;
					}
					i++;
				}
			}
		} catch(Throwable e) {
			e.printStackTrace();
		}

		return texto;
	}
	/**
	 * Remove os espaços em branco à esquerda da string.
	 * Ver java.lang.Character.isWhiteSpace() para mais informações sobre
	 * quais os caracteres são considerados espaços em branco.
	 *
	 * @param str a string da qual serão removidos os espaços em branco à
	 *		esquerda.
	 *
	 * @return a string com os espaços em branco removidos.
	 */
	public static String lTrim(String str) {
		String result = "";

		int i;
		for (i = 0; i < str.length(); i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				break;
			}
		}
		for (; i < str.length(); i++) {
			result = result + str.charAt(i);
		}
		return result;
	}

	/**
	 * Cria a representação formatada em string de um número inteiro.
	 * A representação corresponderá ao número formatado com ponto decimal e
	 * separador de milhares.
	 * Exemplo: numeroToString(12345,2,0,",",".",ARREDONDA)
	 * resultará em "12.345,00".
	 *
	 * @param numero o número a ser formatado.
	 * @param decimais o número de casas decimais que deve constar na
	 *		representação em string a ser criada.
	 * @param tamanho o tamanho que deve ter a string resultante (será
	 *		completada com zeros). Para que a string não seja completada com
	 *		zeros, pode-se passar o valor zero neste parâmetro.
	 * @param separadorDecimal o identificador que representa o separador
	 *		decimal. Caso este parâmetro seja uma string vazia, não haverá
	 *		separação da parte inteira da parte fracionária.
	 * @param separadorMilhares o identificador que representa o separador
	 *		de milhares do número.
	 * @param modoTruncamento não utilizado, passar sempre zero.
	 *
	 * @return a representação em string do número formatado.
	 */
	public static String numeroToString(int numero, int decimais,
						int tamanho, String separadorDecimal,
						String separadorMilhares, int modoTruncamento) {
		return numeroToString(numero, decimais, tamanho, separadorDecimal,
			separadorMilhares, modoTruncamento == TRUNCAMENTO);
	}
	/**
	 * Cria a representação formatada em string de um número inteiro.
	 * A representação corresponderá ao número formatado com ponto decimal e
	 * separador de milhares.
	 * Exemplo: numeroToString(12345,2,0,",",".",true) resultará em "12.345,00".
	 *
	 * @param numero o número a ser formatado.
	 * @param decimais o número de casas decimais que deve constar na
	 *		representação em string a ser criada.
	 * @param tamanho o tamanho que deve ter a string resultante (será
	 *		completada com zeros). Para que a string não seja completada com
	 *		zeros, pode-se passar o valor zero neste parâmetro.
	 * @param separadorDecimal o identificador que representa o separador
	 *		decimal. Caso este parâmetro seja uma string vazia, não haverá
	 *		separação da parte inteira da parte fracionária.
	 * @param separadorMilhares o identificador que representa o separador
	 *		de milhares do número.
	 * @param truncar não utilizado, passar sempre <code>false</code>).
	 *
	 * @return a representação em string do número formatado.
	 */
	public static String numeroToString(int numero, int decimais,
						int tamanho, String separadorDecimal,
						String separadorMilhares, boolean truncar) {
		long multiplicador = 1;
		boolean negativo = numero < 0.0;
		if (negativo) {
			numero = -numero;
		}
		for (int i = 0; i < decimais; i++) {
			multiplicador *= 10;
		}
		numero *= multiplicador;
		long num;
		if (truncar) {
			num = Math.round(Math.floor(numero));
		} else {
			num = Math.round(numero);
		}
		long frac = num % multiplicador;
		String strFrac = completaString("" + frac, decimais, '0', ESQUERDA);
		if (strFrac.length() > decimais) {
			strFrac = strFrac.substring(0, decimais);
		}
		num /= multiplicador;
		String result = "";
		int tamMilhar = 3 + separadorMilhares.length();
		do {
			String auxMilhar = (num % 1000) + separadorMilhares;
			if (num >= 1000) {
				while (auxMilhar.length() < tamMilhar) {
					auxMilhar = "0" + auxMilhar;
				}
			}
			result = auxMilhar + result;
			num /= 1000;
		} while (num > 0);
		result = result.substring(0, result.length() - separadorMilhares.length());
		result = result + separadorDecimal + strFrac;
		result = completaString(result, (negativo ? tamanho - 1 : tamanho), '0', ESQUERDA);
		if (negativo) {
			result = "-" + result;
		}
		return result;
	}
	/**
	 * Cria a representação formatada em string de um BigDecimal.
	 * A representação corresponderá ao número formatado com ponto decimal e
	 * separador de milhares.
	 * Exemplo: numeroToString(new BigDecimal("12345.317"),2,0,",",".",
	 * ARREDONDA) resultará em "12.345,32".
	 *
	 * @param numero o número a ser formatado.
	 * @param decimais o número de casas decimais que deve constar na
	 *		representação em string a ser criada.
	 * @param tamanho o tamanho que deve ter a string resultante (será
	 *		completada com zeros). Para que a string não seja completada com
	 *		zeros, pode-se passar o valor zero neste parâmetro.
	 * @param separadorDecimal o identificador que representa o separador
	 *		decimal. Caso este parâmetro seja uma string vazia, não haverá
	 *		separação da parte inteira da parte fracionária.
	 * @param separadorMilhares o identificador que representa o separador
	 *		de milhares do número.
	 * @param modoTruncamento o modo de truncamento a ser aplicado no número
	 *		para que a representação do mesmo possua o número de casas decimais
	 *		especificado (ver as constantes de truncamento desta classe).
	 *
	 * @return a representação em string do número formatado.
	 */
	public static String numeroToString(BigDecimal numero, int decimais,
						int tamanho, String separadorDecimal,
						String separadorMilhares, int modoTruncamento) {
		return numeroToString(numero, decimais, tamanho, separadorDecimal,
			separadorMilhares, modoTruncamento == TRUNCAMENTO);
	}
	/**
	 * Cria a representação formatada em string de um BigDecimal.
	 * A representação corresponderá ao número formatado com ponto decimal e
	 * separador de milhares.
	 * Exemplo: numeroToString(new BigDecimal("12345.32"),2,0,",",".",false)
	 * resultará em "12.345,32".
	 *
	 * @param numero o número a ser formatado.
	 * @param decimais o número de casas decimais que deve constar na
	 *		representação em string a ser criada.
	 * @param tamanho o tamanho que deve ter a string resultante (será
	 *		completada com zeros). Para que a string não seja completada com
	 *		zeros, pode-se passar o valor zero neste parâmetro.
	 * @param separadorDecimal o identificador que representa o separador
	 *		decimal. Caso este parâmetro seja uma string vazia, não haverá
	 *		separação da parte inteira da parte fracionária.
	 * @param separadorMilhares o identificador que representa o separador
	 *		de milhares do número.
	 * @param truncar indica se o número deve ser truncado (<code>true</code)
	 *		ou arredondado para o mais próximo (<code>false</code) para	manter
	 *		o número de casas decimais especificado.
	 *
	 * @return a representação em string do número formatado.
	 */
	public static String numeroToString(BigDecimal numero, int decimais,
						int tamanho, String separadorDecimal,
						String separadorMilhares, boolean truncar) {
		long multiplicador = 1;
		boolean negativo = numero.compareTo(new BigDecimal(0)) < 0;
		if (negativo) {
			numero = numero.negate();
		}
		for (int i = 0; i < decimais; i++) {
			multiplicador *= 10; // multiplica por 10
		}
		long num;
		if (truncar) {
			num = numero.movePointRight(decimais).longValue();
		} else {
			num = numero.setScale(decimais,BigDecimal.ROUND_UP).movePointRight(decimais).longValue();
		}
		long frac = num % multiplicador;
		String strFrac = completaString("" + frac, decimais, '0', ESQUERDA);
		if (strFrac.length() > decimais) {
			strFrac = strFrac.substring(0, decimais);
		}
		num /= multiplicador;
		String result = "";
		int tamMilhar = 3 + separadorMilhares.length();
		do {
			String auxMilhar = (num % 1000) + separadorMilhares;
			if (num >= 1000) {
				while (auxMilhar.length() < tamMilhar) {
					auxMilhar = "0" + auxMilhar;
				}
			}
			result = auxMilhar + result;
			num /= 1000;
		} while (num > 0);
		result = result.substring(0, result.length() - separadorMilhares.length());
		result = result + separadorDecimal + strFrac;
		result = completaString(result, (negativo ? tamanho - 1 : tamanho), '0', ESQUERDA);
		if (negativo) {
			result = "-" + result;
		}
		return result;
	}
	/**
	 * Remove todas as ocorrências de um determinado caractere de dentro de uma
	 * string.
	 * Exemplo: removeChar("A-B-C",'-') resultará em "ABC".
	 *
	 * @param str a string que terá todas as ocorrências do caractere removidas.
	 * @param ch o caracter a ser removido da string.
	 *
	 * @return A string sem ocorrências do caractere especificado.
	 */
	public static String removeChar(String str, char ch) {
		String aux = str;
		int iaux = aux.indexOf(""+ch);
		while (iaux != -1){
			aux = aux.substring(0,iaux)+aux.substring(iaux+1);
			iaux = aux.indexOf(""+ch);
		}
		return aux;
	}
	/**
	 * Inverte os caracteres de uma dada string.
	 * Exemplo: reverse("ABC") resultará em "CBA").
	 *
	 * @param str a string que terá seus caracteres invertidos.
	 *
	 * @return uma string com o conteúdo revertido da string passada como
	 *		parâmetro.
	 */
	public static String reverse(String str) {
		return (new StringBuffer(str)).reverse().toString();
	}

	/**
	 * Remove os espaços em branco à direita da string.
	 * Ver java.lang.Character.isWhiteSpace() para mais informações sobre
	 * quais os caracteres são considerados espaços em branco.
	 *
	 * @param str a string da qual serão removidos os espaços em branco à
	 *		direita.
	 *
	 * @return a string com os espaços em branco removidos.
	 */
	public static String rTrim(String str) {
	    if (str.equals(""))
	        return "";
	    int pos = str.length() - 1;
	    while (pos >= 0 && str.charAt(pos) == ' ') pos--;
	    return (pos < 0) ? "" : str.substring(0, pos + 1);
	}

	/**
	 * Formata textos com regras flexíveis de formatação, semelhantes às da
	 * função <code><i>sprintf</i></code> da linguagem C.
	 *
	 * A string de formatação controla como será realizada a formatação dos
	 * argumentos e pode conter dois tipos de informação: caracteres comuns e
	 * especificadores de formato.
	 * Caracteres comuns são simplesmente copiados para a string resultante do
	 * processo de formatação.
	 * Especificadores de formato buscam argumentos no array de argumentos e
	 * aplicam formatação a eles.
	 * Cada especificador de formato possui a seguinte estrutura:
	 *
	 *   % [flags] [compr] [.prec] tipo
	 *
	 * Cada especificador de formato inicia com o caractere '%' (percentual).
	 * Após o % vem o seguinte, nesta ordem:
	 *
	 *	  Componente ¦ O que este componente controla ou especifica
	 *	 ------------+----------------------------------------------
	 *	  [flags]    ¦(Opcional) Flags complementares
	 *	  [compr]    ¦(Opcional) Especificador de comprimento
	 *	  [.prec]    ¦(Opcional) Especificador de precisão
	 *	  tipo       ¦(Requerido) Tipo de argumento a ser utilizado
	 *
	 * As flags complementares especificam justificação e sinal de números.
	 *
	 *	  Flag ¦ O que significa
	 *	 ------+--------------------------------------------------------------
	 *	    -  ¦ Justifica o resultado à esquerda, completando à direita com
	 *         | espaços. Se não for especificado, justifica o resultado à
	 *         | direita, completando com zeros ou espaços à esquerda.
	 *	    +  ¦ Números sempre aparecerão com um sinal de + ou - à frente.
	 *
	 * O Especificador de comprimento define o comprimento mínimo de um valor
	 * no resultado da formatação
	 *
	 * Compr pode ser especificado de duas formas distintas:
	 *   - diretamente, através de um número decimal
	 *   - indiretamente, através de um asterisco
	 *
	 * Se for usado um asterisco como especificador de comprimento, o valor do
	 * próximo argumento no array de argumentos especifica o comprimento mínimo
	 * do valor no resultado da formatação.
	 *
	 * Caso o comprimento não seja especificado ou seja menor do que o
	 * comprimento do resultado gerado pela formatação, o resultado não será
	 * truncado.
	 *
	 *	  [compr] ¦ Como o resultado da formatação é afetado
	 *	 ---------+--------------------------------------------------------
	 *	    n     ¦ Pelo menos n caracteres aparecerão no resultado da
	 *            | formatação. Se o valor do argumento possuir menos que n
	 *            | caracteres, o resultado da formatação será preenchido com
	 *            | brancos (preenchido à direita se a flag '-' tiver sido
	 *            | especificada ou à esquerda caso contrário).
	 *	    0n    ¦ Pelo menos n caracteres aparecerão no resultado da
	 *            | formatação. Se o valor do argumento possuir menos que n
	 *            | caracteres, o resultado da formatação será preenchido com
	 *            | à esquerda com zeros.
	 *	    *     ¦ O array de argumentos fornecerá o valor do especificador de
	 *            | comprimento, o qual deve preceder o argumento a ser
	 *            | efetivamente formatado.
	 *
	 * O especificador de precisão define o número máximo de caracteres (ou
	 * o número mínimo de casas decimais) que aparecerão no resultado da
	 * formatação.
	 * Um especificador de precisão sempre inicia com um ponto '.' para
	 * separá-lo de um possível especificador de comprimento.
	 *
	 * A precisão é especificada em uma de duas formas (seguindo o mesmo padrão
	 * do comprimento):
	 *   - diretamente, através de um número decimal
	 *   - indiretamente, através de um asterisco
	 *
	 *   -----------------------------------------------------------------------
	 *	  [.prec] ¦ Como o resultado da formatação é afetado
	 *	 ---------+-------------------------------------------------------------
	 *    (nenhum)| Precisão default.
	 *		.0    ¦ Para os tipos d e x: precisão default.
	 *			  ¦ Para o tipo f: ponto decimal não aparecerá no resultado.
	 *		.n    ¦ n caracteres ou n casas decimais aparecerão no resultado.
	 *	     *    ¦ O array de argumentos fornecerá o valor do especificador de
	 *            | precisão, o qual deve preceder o argumento a ser
	 *            | efetivamente formatado.
	 *
	 * O especificador de precisão não afetará o resultado da formatação se
	 * uma das seguintes condições forem satisfeitas:
	 *   - precisão 0 foi especificada
	 *   - o tipo do argumento a ser formatado é um inteiro (tipo d, x ou X).
	 *   - o valor do argumento a ser formatado é zero.
	 *
	 * Os tipos válidos para argumentos de formatação são:
	 *
	 *	------------------------------------------------------------------------
	 *	Tipo¦ Requisito para o objeto argumento
	 *	----+-------------------------------------------------------------------
	 *	 d  ¦ Argumento numérico inteiro longo. O objeto deve implementar o
	 *      | método longValue() que retorna um <code>long</code> com o valor.
	 *      | O resultado da formatação será apresentado em decimal.
	 *      |
	 *	 x  ¦ Argumento numérico inteiro. O objeto deve implementar o método
	 *   X  | intValue() que retorna um <code>int</code> com o valor.
	 *      | O resultado da formatação será apresentado em hexadecimal.
	 *      | A diferença entre x e X é que X apresenta os dígitos hexadecimais
	 *      | alfabéticos em letras maiúsculas.
	 *	    ¦
	 *	 f  ¦ Argumento numérico de ponto flutuante. O objeto deve implementar
	 *      | o método doubleValue() que retorna um <code>double</code> com o
	 *      | valor.
	 *	    ¦
	 *	 c  ¦ Argumento caractere. O objeto deve implementar o método
	 *      | charValue() que retorna um <code>Character</code> com o valor.
	 *	    ¦
	 *	 s  ¦ Argumento string. O objeto deve ser um String.
	 *
	 * O especificador de precisão .n pode afetar o resultado da formatação de
	 * formas distintas, dependendo do tipo do argumento sendo formatado:
	 *
	 *	 -----------------------------------------------------------------------
	 *	 Tipo¦ Efeito do .n na formatação
	 *	 ----+------------------------------------------------------------------
	 *	  d  ¦ Especifica que pelo menos n dígitos aparecerão no resultado da
	 *	  x  ¦ formatação.
	 *	  X  ¦  - Se o argumento a ser formatado possuir menos do que n
	 *       |    dígitos, o resultado é preenchido à esquerda com zeros.
	 *	     ¦  - Se o argumento a ser formatado possuir mais do que n
	 *       |    dígitos, o resultado não será truncado.
	 *	     ¦
	 *	  f  ¦ Especifica que n caracteres aparecerão no resultado da
	 *       | formatação após o ponto decimal (com arredondamento).
	 *	     ¦
	 *	  c  ¦ Não afeta o resultado.
	 *	     ¦
	 *	  s  ¦ Especifica que não mais do que n caracteres aparecerão no
	 *       | resultado da formatação.
	 *
	 * Exemplos de uso:
	 *
	 * sprintf("Data = %02d/%02d/%04d",
	 * new Object[] { new Long(7), new Long(6), new Long(1971) } )
	 * resultará em "Data = 07/06/1971".
	 *
	 * sprintf("String = %.4s", new Object[] { "TESTANDO" } )
	 * resultará em "TEST".
	 *
	 * sprintf("%s %5.2f", new Object[] { "Valor =", new Double(23.16) } )
	 * resultará em "Valor =    23.16".
	 *
	 *
	 * @param texto a string de formatação (com as regras de formatação).
	 * @param args os argumentos para as regras de formatação (sequências %)
	 * 		encontradas no texto passado como parâmetro. A quantidade de
	 *		elementos deste array deve ser igual à quantidade de regras no
	 *		texto.
	 *
	 * @return o texto formatado de acordo com as regras especificadas.
	 */
	public static String sprintf(String texto, Object[] args) {
		StringBuffer sb = new StringBuffer();
		int arg = 0;
		int i = 0;
		while (i < texto.length()) {
			char c = texto.charAt(i);
			if (c != '%') {
				sb.append(c);
				i++;
			} else if (texto.charAt(i+1) == '%') {
				sb.append('%');
				i++;
			} else {
				boolean flagPlus = false, flagMinus = false;
				int width = -1;
				boolean flagZeroEsquerda = false;
				int precision = -1;
				boolean flagLong = false;
				char type = ' ';
				i++;
				char aux = texto.charAt(i);
				if (aux == '+') {
					flagPlus = true;
					i++;
					aux = texto.charAt(i);
				} else if (aux == '-') {
					flagMinus = true;
					i++;
					aux = texto.charAt(i);
				}
				if (Character.isDigit(aux)) {    // Busca tamanho do resultado
					int num = 0;
					if (aux == '0') {
						flagZeroEsquerda = true;
					}
					while (Character.isDigit(aux)) {
						num = 10 * num + ((int) aux) - ((int) '0');
						aux = texto.charAt(++i);
					}
					width = num;
				}
				if (aux == '*') {  // O tamanho é dado como argumento.
					width = ((Integer) (args[arg++])).intValue();
					aux = texto.charAt(++i);
				}
				if (aux == '.') {   // Ai vem a precisão...
					aux = texto.charAt(++i);
					if (aux == '*') { // a precisão é dada como argumento.
						precision = ((Integer) args[arg++]).intValue();
						aux = texto.charAt(++i);
					} else if (Character.isDigit(aux)) {
						int num = 0;
						while (Character.isDigit(aux)) {
							num = 10 * num + ((int) aux) - ((int) '0');
							aux = texto.charAt(++i);
						}
						precision = num;
					} else {
						precision = 0;
					}
				}
				if (aux == 'l' || aux == 'L') {
					flagLong = true;
					aux = texto.charAt(++i);
				}
				type = aux;
				i++;
				switch (type) {
					case 'd' :
					{
						long num;
						try {
							num = ((Number) args[arg]).longValue();
						} catch (ClassCastException e) {
							num = ((BigDecimal) args[arg]).longValue();
						}
						arg++;
						if (precision == -1) {
							precision = 1;
						}
						if (num == 0 && precision == 0) {
							continue;
						}
						boolean negativo = num < 0;
						if (negativo) {
							num = -num;
						}
						String sinal = (negativo ? "-" : (flagPlus ? "+" : ""));
						String str = "" + num;
						while (str.length() < precision) {
							str = "0" + str;
						}
						width -= sinal.length();

						while (str.length() < width) {
							if (flagMinus) {
								str = str + " ";
							} else if (flagZeroEsquerda) {
								str = "0" + str;
							} else {
								str = " " + str;
							}
						}
						str = sinal + str;
						sb.append(str);
						break;
					}
					case 'f' :
					{
						double num;
						try {
							num = ((Number) args[arg]).doubleValue();
						} catch (ClassCastException e) {
							num = ((BigDecimal) args[arg]).doubleValue();
						}
						arg++;
						if (precision == -1) {
							precision = 6;
						}
						boolean negativo = (num < 0.0);
						if (negativo) {
							num = -num;
						}
						String sinal = (negativo ? "-" : (flagPlus ? "+" : ""));
						for (int j = 0; j < precision; j++) {
							num *= 10.0;
						}
						num += 0.5; // arredondando...
						for (int j = 0; j < precision; j++) {
							num /= 10.0;
						}
						String str = "" + Math.round(Math.floor(num));
						if (precision != 0) {
							str = str + ".";
							double dAux = num - Math.floor(num);
							for (int j = 0; j < precision; j++) {
								dAux *= 10;
								long n = Math.round(Math.floor(dAux));
								str = str + n;
								dAux = dAux - n;
							}
						}
						width -= sinal.length();

						while (str.length() < width) {
							if (flagMinus) {
								str = str + " ";
							} else if (flagZeroEsquerda) {
								str = "0" + str;
							} else {
								str = " " + str;
							}
						}
						str = sinal + str;
						sb.append(str);
						break;
					}
					case 's' :
					{
						String str = (String) args[arg++];
						if (precision != -1 && str.length() > precision) {
							str = str.substring(0, precision);
						}
						while (str.length() < width) {
							if (flagMinus) {
								str = str + " ";
							} else {
								str = " " + str;
							}
						}
						sb.append(str);
						break;
					}
					case 'x' :
					case 'X' :
					{
						boolean flagMaiusc = type == 'X';
						int letraBase = (int) (flagMaiusc ? 'A' : 'a');
						int num = ((Integer) args[arg++]).intValue();
						if (precision == -1) {
							precision = 1;
						}
						if (num == 0 && precision == 0) {
							continue;
						}
						if (num < 0) continue;
						String str = (num == 0) ? "0" : "";
						while (num > 0) {
							int n16 = num % 16;
							if (n16 < 10) {
								str = n16 + str;
							} else {
								str = ((char) (letraBase + n16 - 10)) + str;
							}
							num /= 16;
						}
						while (str.length() < precision) {
							str = "0" + str;
						}
						while (str.length() < width) {
							if (flagMinus) {
								str = str + " ";
							} else {
								str = (flagZeroEsquerda ? "0" : " ") + str;
							}
						}
						sb.append(str);
						break;
					}
					case 'c' :
					{
						Object obj = args[arg++];
						char caractere;
						if (obj instanceof Character) {
							caractere = ((Character) obj).charValue();
						} else { // if (obj instanceof Number)
							try {
								caractere = (char) ((Number) obj).intValue();
							} catch (ClassCastException e) {
								caractere =
										(char) ((BigDecimal) obj).intValue();
							}
						}
						sb.append(caractere);
						break;
					}
				}
			}
		}
		return sb.toString();
	}
	/**
	 * Troca todas as ocorrências de um determinado caractere numa string por
	 * outro caractere.
	 * Exemplo: trocaChar("A_B_C",'_','+') resultará em "A+B+C".
	 *
	 * @param str a string que terá seus caracteres trocados.
	 * @param antigo o caractere que será substituído.
	 * @param novo o caractere que substituirá o caractere antigo.
	 *
	 * @return uma string com os caracteres substituídos.
	 */
	public static String trocaChar(String str, char antigo, char novo) {
		StringBuffer sb = new StringBuffer(str);
		for (int i = 0; i < str.length(); i++) {
			if (sb.charAt(i) == antigo) {
				sb.setCharAt(i, novo);
			}
		}
		return sb.toString();
	}
	/**
	 * Tira os zeros à esquerda de uma string que representa um número inteiro.
	 *
	 * @param str O string da qual será retirada os zeros à esquerda.
	 *
	 * @return O string sem zeros à esquerda.
	 */
	public static String zerosEsquerda(String str) {
		BigInteger i = new BigInteger(str);
		return i.toString();
	}


	/**
	 * Função que recebe uma string e a devolve em partes em forma de um array.
	 * @param string A string que será repartida.
	 * @param delimitador O caracter ou expressão que se tomará como base para separação
	 * das partes da String passada.
	 * @return Um array de string contendo as partes da string. Se um dos parâmetros for
	 *  nulo ou se a string passada for vazia então será retornado um array de dimensão 1
	 *  com conteúdo vazio.
	 * @author jorge.strobel
	 */
	public static String[] split(String string ,String delimitador){
	    String[] conteudo;
	    if ((string != null && !string.equals("")) && delimitador != null) {
	        StringTokenizer strToken = new StringTokenizer(string ,delimitador);
		    conteudo = new String[strToken.countTokens()];
		    for (int i = 0; i < conteudo.length; i++) {
	            conteudo[i] = strToken.nextToken();
	        }
        }else{
            conteudo = new String[1];
            conteudo[0] = "";
        }
	    return conteudo;
	}

/**
 * Completa um long com zeros a esquerda e retorna String
 * correspondente formatada.
 *
 * @param numOri o long a ter zeros a esquerda completados.
 * @param tam o tamanho total da String a ser retornada
 *
 * @return a string formatada.
 */
public static String completeNumberZERO(long numOri,int tam) {

	String strOri = String.valueOf(numOri);
	return wrappedCompleteChar(strOri,tam,'0',true);
}

/**
 * Completa uma String com brancos a direita e retorna a String
 * correspondente formatada.
 *
 * @param strOri a String a ter brancos a direita completados.
 * @param tam o tamanho total da String a ser retornada
 *
 * @return a string formatada.
 */
public static String completeStringCHAR(String strOri,int tam) {

	if (strOri != null) {
		return wrappedCompleteChar(strOri,tam,' ',false);
	} else {
		return wrappedCompleteChar("null",tam,' ',false);
	}
}

/**
 * Completa uma Stirng com zeros a esquerda e retorna a String
 * correspondente formatada.
 *
 * @param strOri a String a ter zeros a esquerda completados.
 * @param tam o tamanho total da String a ser retornada
 *
 * @return a string formatada.
 */
public static String completeStringZERO(String strOri,int tam) {

	if (strOri != null) {
		return wrappedCompleteChar(strOri,tam,'0',true);
	} else {
		return wrappedCompleteChar("null",tam,'0',true);
	}
}

/**
 * Retorna uma String formatada (com ponto decimal) que representa um BigDecimal.
 * Se total for menor ou igual a dec, é retornada uma String branco.
 * Se number igual a null, retorna a String "null".
 *
 * @param number o BigDecimal a ser formatado.
 * @param total a quantidade total de casas inteiras e casas decimais.
 * @param dec a quantidade de casas decimais.
 *
 * @return a string formatada.
 */
public static String csiBigDecToString(BigDecimal number,int total,int dec) {

	if (number != null) {
		if (total > dec) {
			StringTokenizer st = new StringTokenizer(number.toString(),".");
			String parteInteira = st.nextToken();
			String parteDecimal = "";
			while (st.hasMoreTokens()) {
				parteDecimal = st.nextToken();
				int pdLength = parteDecimal.length();
				parteDecimal = parteDecimal.substring(0,(pdLength>=dec)?(dec):(pdLength));
			}
			parteDecimal = StringUtil.completaString(parteDecimal,dec,'0',false);
			int tamInteira = total - dec;
			parteInteira = ((parteInteira.length() <= tamInteira)?StringUtil.completaString(parteInteira,
				tamInteira,'0',true):parteInteira.substring(0,tamInteira));
			return (parteInteira+"."+parteDecimal);
		} else {
			return "";
		}
	} else {
		return "null";
	}
}

/**
 * Verifica se uma string é um valor numérico ou não
 * @param string
 * @return Retorna true se a string é um valor e false caso contrário.
 */
public static boolean eNumerico(String string){
    boolean retorno = true;
    if(string == null || string.equals("")){
        retorno = false;
    } else {
        for (int i = 0; i < string.length(); i++) {
            if (! Character.isDigit(string.charAt(i))) {
                retorno = false;
                break;
            }
        }
    }

    return retorno;
}


private static String wrappedCompleteChar(String strOri,int tam,char cs,boolean left) {

	return ((strOri.length() < tam)?
		StringUtil.completaString(strOri,tam,cs,left):
		strOri.substring(0,tam));
}

/**
 * Esse método está sendo usado na MicImpComprovantesFinanciamento
 * @param valor
 * @param moedaSing
 * @param moedaPlu
 * @return
 * @throws Exception
 */
public static String obterExtensoValorMonetario(BigDecimal valor, String moedaSing, String moedaPlu)
throws Exception
{
if(valor == null || moedaSing == null || moedaPlu == null)
{
    throw new Exception();
}
if(valor.compareTo(new BigDecimal(0)) < 0 )
{
    throw new Exception();
}
String nF = formatarValor(valor, 2, ',', '.');
byte numForm[] = justificarTexto(nF, 14, false, '*').getBytes();
String numExten = "";
boolean grupo[] = new boolean[4];
int posit = 0;
int pi = 0;
for(int ct1 = 0; ct1 < 4; ct1++)
{
    String tmpExten = "";
    pi = ct1 != 3 ? 0 : 1;
    for(int ct2 = pi; ct2 < 3; ct2++)
    {
        switch(ct2)
        {
        default:
            break;

        case 0: // '\0'
            switch(numForm[posit])
            {
            case 49: // '1'
                if(numForm[posit + 1] == 48 && numForm[posit + 2] == 48)
                {
                    tmpExten = "CEM ";
                } else
                {
                    tmpExten = "CENTO ";
                }
                break;

            case 50: // '2'
                tmpExten = "DUZ";
                break;

            case 51: // '3'
                tmpExten = "TREZ";
                break;

            case 52: // '4'
                tmpExten = "QUATROC";
                break;

            case 53: // '5'
                tmpExten = "QUINH";
                break;

            case 54: // '6'
                tmpExten = "SEISC";
                break;

            case 55: // '7'
                tmpExten = "SETEC";
                break;

            case 56: // '8'
                tmpExten = "OITOC";
                break;

            case 57: // '9'
                tmpExten = "NOVEC";
                break;
            }
            if(numForm[posit] > 49)
            {
                tmpExten = tmpExten + "ENTOS ";
            }
            break;

        case 1: // '\001'
            if(numForm[posit] > 48 && tmpExten.length() > 0)
            {
                tmpExten = tmpExten + "E ";
            }
            switch(numForm[posit])
            {
            case 49: // '1'
                switch(numForm[posit + 1])
                {
                case 48: // '0'
                    tmpExten = tmpExten + "DEZ ";
                    break;

                case 49: // '1'
                    tmpExten = tmpExten + "ONZE ";
                    break;

                case 50: // '2'
                    tmpExten = tmpExten + "DOZE ";
                    break;

                case 51: // '3'
                    tmpExten = tmpExten + "TREZE ";
                    break;

                case 52: // '4'
                    tmpExten = tmpExten + "QUATORZE ";
                    break;

                case 53: // '5'
                    tmpExten = tmpExten + "QUINZE ";
                    break;

                case 54: // '6'
                    tmpExten = tmpExten + "DEZESSEIS ";
                    break;

                case 55: // '7'
                    tmpExten = tmpExten + "DEZESSETE ";
                    break;

                case 56: // '8'
                    tmpExten = tmpExten + "DEZOITO ";
                    break;

                case 57: // '9'
                    tmpExten = tmpExten + "DEZENOVE ";
                    break;
                }
                break;

            case 50: // '2'
                tmpExten = tmpExten + "VINTE ";
                break;

            case 51: // '3'
                tmpExten = tmpExten + "TRINTA ";
                break;

            case 52: // '4'
                tmpExten = tmpExten + "QUAR";
                break;

            case 53: // '5'
                tmpExten = tmpExten + "CINQU";
                break;

            case 54: // '6'
                tmpExten = tmpExten + "SESS";
                break;

            case 55: // '7'
                tmpExten = tmpExten + "SET";
                break;

            case 56: // '8'
                tmpExten = tmpExten + "OIT";
                break;

            case 57: // '9'
                tmpExten = tmpExten + "NOV";
                break;
            }
            if(numForm[posit] > 51)
            {
                tmpExten = tmpExten + "ENTA ";
            }
            break;

        case 2: // '\002'
            if(numForm[posit - 1] == 49)
            {
                break;
            }
            if(numForm[posit] > 48 && tmpExten.length() > 0)
            {
                tmpExten = tmpExten + "E ";
            }
            switch(numForm[posit])
            {
            case 42: // '*'
            case 43: // '+'
            case 44: // ','
            case 45: // '-'
            case 46: // '.'
            case 47: // '/'
            case 48: // '0'
            default:
                break;

            case 49: // '1'
                if(numExten.length() == 0 && tmpExten.length() == 0)
                {
                    tmpExten = tmpExten + "H";
                }
                tmpExten = tmpExten + "UM ";
                break;

            case 50: // '2'
                tmpExten = tmpExten + "DOIS ";
                break;

            case 51: // '3'
                tmpExten = tmpExten + "TRES ";
                break;

            case 52: // '4'
                tmpExten = tmpExten + "QUATRO ";
                break;

            case 53: // '5'
                tmpExten = tmpExten + "CINCO ";
                break;

            case 54: // '6'
                tmpExten = tmpExten + "SEIS ";
                break;

            case 55: // '7'
                tmpExten = tmpExten + "SETE ";
                break;

            case 56: // '8'
                tmpExten = tmpExten + "OITO ";
                break;

            case 57: // '9'
                tmpExten = tmpExten + "NOVE ";
                break;
            }
            break;
        }
        posit++;
    }

    grupo[ct1] = tmpExten.length() != 0;
    posit++;
    if(grupo[ct1])
    {
        switch(ct1)
        {
        case 0: // '\0'
            tmpExten = tmpExten + "MILH";
            if(tmpExten.charAt(0) == 'U' || tmpExten.charAt(0) == 'H')
            {
                tmpExten = tmpExten + "AO ";
            } else
            {
                tmpExten = tmpExten + "OES ";
            }
            break;

        case 1: // '\001'
            tmpExten = tmpExten + "MIL ";
            break;

        case 2: // '\002'
            if((tmpExten.charAt(0) == 'U' || tmpExten.charAt(0) == 'H') && numExten.length() == 0)
            {
                tmpExten = tmpExten + moedaSing;
            } else
            {
                tmpExten = tmpExten + moedaPlu;
            }
            break;

        case 3: // '\003'
            if(numExten.length() != 0)
            {
                numExten = numExten + " E ";
            }
            tmpExten = tmpExten + "CENTAVO";
            if(tmpExten.charAt(0) != 'U' && tmpExten.charAt(0) != 'H')
            {
                tmpExten = tmpExten + "S";
            }
            break;
        }
        if(ct1 == 2 && numExten.length() != 0 && grupo[1] && (numForm[posit - 4] == 48 || numForm[posit - 4] > 48 && numForm[posit - 3] == 48 && numForm[posit - 2] == 48))
        {
            numExten = numExten + "E ";
        }
        numExten = numExten + tmpExten;
    } else
    if(ct1 == 2)
    {
        if(grupo[0] && !grupo[1] && !grupo[2])
        {
            numExten = numExten + "DE ";
        }
        if(grupo[0] || grupo[1] || grupo[2])
        {
            numExten = numExten + moedaPlu;
            numExten = numExten + " ";
        }
    }
}

return numExten.toUpperCase();
}

/**
 * Esse método está sendo usado na MicImpComprovantesFinanciamento
 * @param valor
 * @param nDec
 * @param sDec
 * @param sMil
 * @return
 * @throws Exception
 */
public static String formatarValor(BigDecimal valor, int nDec, char sDec, char sMil)
throws Exception
{
if(valor == null)
{
    throw new Exception();
}
boolean negativo = valor.compareTo(new BigDecimal(0)) < 0;
if(negativo)
{
    valor = valor.negate();
}
valor = valor.setScale(nDec, 1);
String val = valor.toString();
int pos = val.indexOf('.');
String depoisVirg = "";
if(pos > 0 && val.length() > pos + 1)
{
    depoisVirg = sDec + val.substring(pos + 1);
    val = val.substring(0, pos);
}
String result = "";
for(; val.length() > 3; val = val.substring(0, val.length() - 3))
{
    result = sMil + val.substring(val.length() - 3) + result;
}

result = (negativo ? "-" : "") + val + result + depoisVirg;
return result;
}

/**
 * Esse método está sendo usado na MicImpComprovantesFinanciamento
 * @param texto
 * @param tamanho
 * @param esquerda
 * @param preenchimento
 * @return
 */
public static String justificarTexto(String texto, int tamanho, boolean esquerda, char preenchimento)
{
int tamanhoTexto = texto.length();
int diferenca = tamanho - tamanhoTexto;
if(diferenca <= 0)
{
    return texto.substring(0, tamanho);
}
byte bRet[] = new byte[tamanho];
int inicio = 0;
if(tamanhoTexto > 0)
{
    if(esquerda)
    {
        System.arraycopy(texto.getBytes(), 0, bRet, 0, tamanhoTexto);
        inicio = tamanhoTexto;
    } else
    {
        System.arraycopy(texto.getBytes(), 0, bRet, diferenca, tamanhoTexto);
        inicio = 0;
    }
}
for(int i = 0; i < diferenca; i++)
{
    bRet[inicio + i] = (byte)preenchimento;
}

String retorno = new String(bRet);
return retorno;
}

}