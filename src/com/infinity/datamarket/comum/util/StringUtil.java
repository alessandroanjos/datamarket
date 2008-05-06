package com.infinity.datamarket.comum.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe utilit�ria com m�todos para manipula��o de Strings.
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
	 * � esquerda.
	 */
	public static final int ESQUERDA = 3;

	/**
	 * Constante usada para indicar que a string deve ser completada
	 * � direita.
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
	 * @return a string com as substitui��es
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
	 * Remove a acentua��o de uma string.
	 *
	 * @param str a string da qual se deseja remover os acentos.
	 *
	 * @return a string sem os acentos
	 */
	public static String removeAcentuacao(String str) {
		String retorno = str;

		retorno = replaceChar(retorno, "[����]", "a");
		retorno = replaceChar(retorno, "[���]" , "e");
		retorno = replaceChar(retorno, "[���]" , "i");
		retorno = replaceChar(retorno, "[����]", "o");
		retorno = replaceChar(retorno, "[����]", "u");
		retorno = replaceChar(retorno, "[�]"   , "c");

		return retorno;
	}

	/**
	 * Remove os espa�os � esquerda e � direita da string.
	 *
	 * @param str a string da qual se deseja remover os espa�os laterais.
	 *
	 * @return a string sem os espa�os � direita e � esquerda.
	 */
	public static String allTrim(String str) {
		return str.trim();
	}
	/**
	 * Converte uma string em um array de bytes.
	 * A string deve possuir apenas caracteres ASCII n�o extendidos (0-127).
	 *
	 * @param str a string a ser convertida para um array de bytes.
	 *
	 * @return o array de bytes correspondente aos caracteres da string passada
	 *		como par�metro.
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
	 * Centraliza uma string numa �rea de tamanho determinado.
	 * Caso o comprimento da string seja maior ou igual ao tamanho especificado
	 * da �rea, a string retornada ser� igual � passada como par�metro.
	 * Caso o comprimento da string seja menor do que o tamanho especificado da
	 * �rea, ser�o inseridos espa�os � esquerda e � direita da mesma de forma
	 * que ela fique centralizada nesta �rea.
	 *
	 * @param str a string a ser centralizada.
	 * @param tamanho o tamanho da �rea na qual a string ficar� centralizada.
	 *
	 * @return a string centralizada numa �rea do tamanho especificado.
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
	 * Completa uma string � esquerda ou � direita com um determinado
	 * caractere at� um tamanho especificado. Caso a string indicada tenha
	 * comprimento maior do que o tamanho especificado, a string retornada
	 * ser� igual � passada como par�metro.
	 *
	 * @param str a string a ser completada com espa�os.
	 * @param tamanho o tamanho da string com o complemento.
	 * @param complemento o caractere a ser usado para completar a string.
	 * @param lado <code>StringUtil.ESQUERDA</code> indica que o complemento
	 *		ser� feito � esquerda. <code>StringUtil.DIREITA</code> indica que
	 *		o complemento ser� feito � direita.
	 *
	 * @return a string com complemento.
	 */
	public static String completaString(String texto, int tamanho,
			char complemento, int lado) {
		return completaString(texto, tamanho, complemento, lado == ESQUERDA);
	}
	/**
	 * Completa uma string � esquerda ou � direita com um determinado
	 * caractere at� um tamanho especificado. Caso a string indicada tenha
	 * comprimento maior do que o tamanho especificado, a string retornada
	 * ser� igual � passada como par�metro.
	 *
	 * @param str a string a ser completada com espa�os.
	 * @param tamanho o tamanho da string com o complemento.
	 * @param complemento o caractere a ser usado para completar a string.
	 * @param esquerda <code>true</code> indica que o complemento ser� feito �
	 *		esquerda. <code>false</code> indica que o complemento ser� feito �
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
	 * caracter espec�fico de forma que a quantida de caracteres da
	 * string resultante seja igual ao tamanho passado. Se o tamanho
	 * passado for menor ou igual que as duas strings concatenadas, ent�o
	 * a string resultante ser� as duas strings concatenadas.
	 * @param texto1 o texto que ser� alinhado a esquerda.
	 * @param texto2 o texto que ser� alinhado a direita.
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
	 * Cria uma string preenchida com c�pias do caractere especificado.
	 * Exemplo: copia('*',5) retornar� "*****".
	 *
	 * @param caractere o caractere que preencher� toda a string.
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
	 * Cria uma string com v�rias ocorr�ncias de uma string especificada.
	 * Exemplo: copia("ABc",3) retornar� "ABcABcABc".
	 *
	 * @param str a string a ser copiada.
	 * @param quantidade a quantidade de ocorr�ncias da string passada como
	 *		par�metro na nova string.
	 *
	 * @return uma string preenchida com c�pias da string indicada.
	 */
	public static String copia(String str, int quantidade) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < quantidade; i++) {
			sb.append(str);
		}
		return sb.toString();
	}
	/**
	 * Conta a quantidade de ocorr�ncias de um caractere dentro de uma string.
	 *
	 * @param c o caractere a ser procurado na string.
	 * @param s a string a qual ser� verificada a quantidade de ocorr�ncias do
	 *		caractere.
	 *
	 * @return O n�mero de vezes que o caractere ocorre na string. Caso a
	 *		string n�o contenha o caractere. ser� retornado zero.
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
	 * Duplica a ocorr�ncia de um determinado caractere dentro de uma string.
	 * Exemplo: duplicaChar('#',"-#-##") resultar� em "-##-####".
	 *
	 * @param caracter o caractere a ser duplicado.
	 * @param texto a string a qual se deseja duplicar a ocorr�ncia do
	 *		caractere.
	 *
	 * @return Uma string que tem as ocorr�ncias do caractere na string
	 *		especificada duplicadas. Caso a string especificada n�o contenha
	 *		ocorr�ncia alguma do caratere, a string resultante ser� igual �
	 *		passada como par�metro.
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
	 * Remove os espa�os em branco � esquerda da string.
	 * Ver java.lang.Character.isWhiteSpace() para mais informa��es sobre
	 * quais os caracteres s�o considerados espa�os em branco.
	 *
	 * @param str a string da qual ser�o removidos os espa�os em branco �
	 *		esquerda.
	 *
	 * @return a string com os espa�os em branco removidos.
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
	 * Cria a representa��o formatada em string de um n�mero inteiro.
	 * A representa��o corresponder� ao n�mero formatado com ponto decimal e
	 * separador de milhares.
	 * Exemplo: numeroToString(12345,2,0,",",".",ARREDONDA)
	 * resultar� em "12.345,00".
	 *
	 * @param numero o n�mero a ser formatado.
	 * @param decimais o n�mero de casas decimais que deve constar na
	 *		representa��o em string a ser criada.
	 * @param tamanho o tamanho que deve ter a string resultante (ser�
	 *		completada com zeros). Para que a string n�o seja completada com
	 *		zeros, pode-se passar o valor zero neste par�metro.
	 * @param separadorDecimal o identificador que representa o separador
	 *		decimal. Caso este par�metro seja uma string vazia, n�o haver�
	 *		separa��o da parte inteira da parte fracion�ria.
	 * @param separadorMilhares o identificador que representa o separador
	 *		de milhares do n�mero.
	 * @param modoTruncamento n�o utilizado, passar sempre zero.
	 *
	 * @return a representa��o em string do n�mero formatado.
	 */
	public static String numeroToString(int numero, int decimais,
						int tamanho, String separadorDecimal,
						String separadorMilhares, int modoTruncamento) {
		return numeroToString(numero, decimais, tamanho, separadorDecimal,
			separadorMilhares, modoTruncamento == TRUNCAMENTO);
	}
	/**
	 * Cria a representa��o formatada em string de um n�mero inteiro.
	 * A representa��o corresponder� ao n�mero formatado com ponto decimal e
	 * separador de milhares.
	 * Exemplo: numeroToString(12345,2,0,",",".",true) resultar� em "12.345,00".
	 *
	 * @param numero o n�mero a ser formatado.
	 * @param decimais o n�mero de casas decimais que deve constar na
	 *		representa��o em string a ser criada.
	 * @param tamanho o tamanho que deve ter a string resultante (ser�
	 *		completada com zeros). Para que a string n�o seja completada com
	 *		zeros, pode-se passar o valor zero neste par�metro.
	 * @param separadorDecimal o identificador que representa o separador
	 *		decimal. Caso este par�metro seja uma string vazia, n�o haver�
	 *		separa��o da parte inteira da parte fracion�ria.
	 * @param separadorMilhares o identificador que representa o separador
	 *		de milhares do n�mero.
	 * @param truncar n�o utilizado, passar sempre <code>false</code>).
	 *
	 * @return a representa��o em string do n�mero formatado.
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
	 * Cria a representa��o formatada em string de um BigDecimal.
	 * A representa��o corresponder� ao n�mero formatado com ponto decimal e
	 * separador de milhares.
	 * Exemplo: numeroToString(new BigDecimal("12345.317"),2,0,",",".",
	 * ARREDONDA) resultar� em "12.345,32".
	 *
	 * @param numero o n�mero a ser formatado.
	 * @param decimais o n�mero de casas decimais que deve constar na
	 *		representa��o em string a ser criada.
	 * @param tamanho o tamanho que deve ter a string resultante (ser�
	 *		completada com zeros). Para que a string n�o seja completada com
	 *		zeros, pode-se passar o valor zero neste par�metro.
	 * @param separadorDecimal o identificador que representa o separador
	 *		decimal. Caso este par�metro seja uma string vazia, n�o haver�
	 *		separa��o da parte inteira da parte fracion�ria.
	 * @param separadorMilhares o identificador que representa o separador
	 *		de milhares do n�mero.
	 * @param modoTruncamento o modo de truncamento a ser aplicado no n�mero
	 *		para que a representa��o do mesmo possua o n�mero de casas decimais
	 *		especificado (ver as constantes de truncamento desta classe).
	 *
	 * @return a representa��o em string do n�mero formatado.
	 */
	public static String numeroToString(BigDecimal numero, int decimais,
						int tamanho, String separadorDecimal,
						String separadorMilhares, int modoTruncamento) {
		return numeroToString(numero, decimais, tamanho, separadorDecimal,
			separadorMilhares, modoTruncamento == TRUNCAMENTO);
	}
	/**
	 * Cria a representa��o formatada em string de um BigDecimal.
	 * A representa��o corresponder� ao n�mero formatado com ponto decimal e
	 * separador de milhares.
	 * Exemplo: numeroToString(new BigDecimal("12345.32"),2,0,",",".",false)
	 * resultar� em "12.345,32".
	 *
	 * @param numero o n�mero a ser formatado.
	 * @param decimais o n�mero de casas decimais que deve constar na
	 *		representa��o em string a ser criada.
	 * @param tamanho o tamanho que deve ter a string resultante (ser�
	 *		completada com zeros). Para que a string n�o seja completada com
	 *		zeros, pode-se passar o valor zero neste par�metro.
	 * @param separadorDecimal o identificador que representa o separador
	 *		decimal. Caso este par�metro seja uma string vazia, n�o haver�
	 *		separa��o da parte inteira da parte fracion�ria.
	 * @param separadorMilhares o identificador que representa o separador
	 *		de milhares do n�mero.
	 * @param truncar indica se o n�mero deve ser truncado (<code>true</code)
	 *		ou arredondado para o mais pr�ximo (<code>false</code) para	manter
	 *		o n�mero de casas decimais especificado.
	 *
	 * @return a representa��o em string do n�mero formatado.
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
	 * Remove todas as ocorr�ncias de um determinado caractere de dentro de uma
	 * string.
	 * Exemplo: removeChar("A-B-C",'-') resultar� em "ABC".
	 *
	 * @param str a string que ter� todas as ocorr�ncias do caractere removidas.
	 * @param ch o caracter a ser removido da string.
	 *
	 * @return A string sem ocorr�ncias do caractere especificado.
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
	 * Exemplo: reverse("ABC") resultar� em "CBA").
	 *
	 * @param str a string que ter� seus caracteres invertidos.
	 *
	 * @return uma string com o conte�do revertido da string passada como
	 *		par�metro.
	 */
	public static String reverse(String str) {
		return (new StringBuffer(str)).reverse().toString();
	}

	/**
	 * Remove os espa�os em branco � direita da string.
	 * Ver java.lang.Character.isWhiteSpace() para mais informa��es sobre
	 * quais os caracteres s�o considerados espa�os em branco.
	 *
	 * @param str a string da qual ser�o removidos os espa�os em branco �
	 *		direita.
	 *
	 * @return a string com os espa�os em branco removidos.
	 */
	public static String rTrim(String str) {
	    if (str.equals(""))
	        return "";
	    int pos = str.length() - 1;
	    while (pos >= 0 && str.charAt(pos) == ' ') pos--;
	    return (pos < 0) ? "" : str.substring(0, pos + 1);
	}

	/**
	 * Formata textos com regras flex�veis de formata��o, semelhantes �s da
	 * fun��o <code><i>sprintf</i></code> da linguagem C.
	 *
	 * A string de formata��o controla como ser� realizada a formata��o dos
	 * argumentos e pode conter dois tipos de informa��o: caracteres comuns e
	 * especificadores de formato.
	 * Caracteres comuns s�o simplesmente copiados para a string resultante do
	 * processo de formata��o.
	 * Especificadores de formato buscam argumentos no array de argumentos e
	 * aplicam formata��o a eles.
	 * Cada especificador de formato possui a seguinte estrutura:
	 *
	 *   % [flags] [compr] [.prec] tipo
	 *
	 * Cada especificador de formato inicia com o caractere '%' (percentual).
	 * Ap�s o % vem o seguinte, nesta ordem:
	 *
	 *	  Componente � O que este componente controla ou especifica
	 *	 ------------+----------------------------------------------
	 *	  [flags]    �(Opcional) Flags complementares
	 *	  [compr]    �(Opcional) Especificador de comprimento
	 *	  [.prec]    �(Opcional) Especificador de precis�o
	 *	  tipo       �(Requerido) Tipo de argumento a ser utilizado
	 *
	 * As flags complementares especificam justifica��o e sinal de n�meros.
	 *
	 *	  Flag � O que significa
	 *	 ------+--------------------------------------------------------------
	 *	    -  � Justifica o resultado � esquerda, completando � direita com
	 *         | espa�os. Se n�o for especificado, justifica o resultado �
	 *         | direita, completando com zeros ou espa�os � esquerda.
	 *	    +  � N�meros sempre aparecer�o com um sinal de + ou - � frente.
	 *
	 * O Especificador de comprimento define o comprimento m�nimo de um valor
	 * no resultado da formata��o
	 *
	 * Compr pode ser especificado de duas formas distintas:
	 *   - diretamente, atrav�s de um n�mero decimal
	 *   - indiretamente, atrav�s de um asterisco
	 *
	 * Se for usado um asterisco como especificador de comprimento, o valor do
	 * pr�ximo argumento no array de argumentos especifica o comprimento m�nimo
	 * do valor no resultado da formata��o.
	 *
	 * Caso o comprimento n�o seja especificado ou seja menor do que o
	 * comprimento do resultado gerado pela formata��o, o resultado n�o ser�
	 * truncado.
	 *
	 *	  [compr] � Como o resultado da formata��o � afetado
	 *	 ---------+--------------------------------------------------------
	 *	    n     � Pelo menos n caracteres aparecer�o no resultado da
	 *            | formata��o. Se o valor do argumento possuir menos que n
	 *            | caracteres, o resultado da formata��o ser� preenchido com
	 *            | brancos (preenchido � direita se a flag '-' tiver sido
	 *            | especificada ou � esquerda caso contr�rio).
	 *	    0n    � Pelo menos n caracteres aparecer�o no resultado da
	 *            | formata��o. Se o valor do argumento possuir menos que n
	 *            | caracteres, o resultado da formata��o ser� preenchido com
	 *            | � esquerda com zeros.
	 *	    *     � O array de argumentos fornecer� o valor do especificador de
	 *            | comprimento, o qual deve preceder o argumento a ser
	 *            | efetivamente formatado.
	 *
	 * O especificador de precis�o define o n�mero m�ximo de caracteres (ou
	 * o n�mero m�nimo de casas decimais) que aparecer�o no resultado da
	 * formata��o.
	 * Um especificador de precis�o sempre inicia com um ponto '.' para
	 * separ�-lo de um poss�vel especificador de comprimento.
	 *
	 * A precis�o � especificada em uma de duas formas (seguindo o mesmo padr�o
	 * do comprimento):
	 *   - diretamente, atrav�s de um n�mero decimal
	 *   - indiretamente, atrav�s de um asterisco
	 *
	 *   -----------------------------------------------------------------------
	 *	  [.prec] � Como o resultado da formata��o � afetado
	 *	 ---------+-------------------------------------------------------------
	 *    (nenhum)| Precis�o default.
	 *		.0    � Para os tipos d e x: precis�o default.
	 *			  � Para o tipo f: ponto decimal n�o aparecer� no resultado.
	 *		.n    � n caracteres ou n casas decimais aparecer�o no resultado.
	 *	     *    � O array de argumentos fornecer� o valor do especificador de
	 *            | precis�o, o qual deve preceder o argumento a ser
	 *            | efetivamente formatado.
	 *
	 * O especificador de precis�o n�o afetar� o resultado da formata��o se
	 * uma das seguintes condi��es forem satisfeitas:
	 *   - precis�o 0 foi especificada
	 *   - o tipo do argumento a ser formatado � um inteiro (tipo d, x ou X).
	 *   - o valor do argumento a ser formatado � zero.
	 *
	 * Os tipos v�lidos para argumentos de formata��o s�o:
	 *
	 *	------------------------------------------------------------------------
	 *	Tipo� Requisito para o objeto argumento
	 *	----+-------------------------------------------------------------------
	 *	 d  � Argumento num�rico inteiro longo. O objeto deve implementar o
	 *      | m�todo longValue() que retorna um <code>long</code> com o valor.
	 *      | O resultado da formata��o ser� apresentado em decimal.
	 *      |
	 *	 x  � Argumento num�rico inteiro. O objeto deve implementar o m�todo
	 *   X  | intValue() que retorna um <code>int</code> com o valor.
	 *      | O resultado da formata��o ser� apresentado em hexadecimal.
	 *      | A diferen�a entre x e X � que X apresenta os d�gitos hexadecimais
	 *      | alfab�ticos em letras mai�sculas.
	 *	    �
	 *	 f  � Argumento num�rico de ponto flutuante. O objeto deve implementar
	 *      | o m�todo doubleValue() que retorna um <code>double</code> com o
	 *      | valor.
	 *	    �
	 *	 c  � Argumento caractere. O objeto deve implementar o m�todo
	 *      | charValue() que retorna um <code>Character</code> com o valor.
	 *	    �
	 *	 s  � Argumento string. O objeto deve ser um String.
	 *
	 * O especificador de precis�o .n pode afetar o resultado da formata��o de
	 * formas distintas, dependendo do tipo do argumento sendo formatado:
	 *
	 *	 -----------------------------------------------------------------------
	 *	 Tipo� Efeito do .n na formata��o
	 *	 ----+------------------------------------------------------------------
	 *	  d  � Especifica que pelo menos n d�gitos aparecer�o no resultado da
	 *	  x  � formata��o.
	 *	  X  �  - Se o argumento a ser formatado possuir menos do que n
	 *       |    d�gitos, o resultado � preenchido � esquerda com zeros.
	 *	     �  - Se o argumento a ser formatado possuir mais do que n
	 *       |    d�gitos, o resultado n�o ser� truncado.
	 *	     �
	 *	  f  � Especifica que n caracteres aparecer�o no resultado da
	 *       | formata��o ap�s o ponto decimal (com arredondamento).
	 *	     �
	 *	  c  � N�o afeta o resultado.
	 *	     �
	 *	  s  � Especifica que n�o mais do que n caracteres aparecer�o no
	 *       | resultado da formata��o.
	 *
	 * Exemplos de uso:
	 *
	 * sprintf("Data = %02d/%02d/%04d",
	 * new Object[] { new Long(7), new Long(6), new Long(1971) } )
	 * resultar� em "Data = 07/06/1971".
	 *
	 * sprintf("String = %.4s", new Object[] { "TESTANDO" } )
	 * resultar� em "TEST".
	 *
	 * sprintf("%s %5.2f", new Object[] { "Valor =", new Double(23.16) } )
	 * resultar� em "Valor =    23.16".
	 *
	 *
	 * @param texto a string de formata��o (com as regras de formata��o).
	 * @param args os argumentos para as regras de formata��o (sequ�ncias %)
	 * 		encontradas no texto passado como par�metro. A quantidade de
	 *		elementos deste array deve ser igual � quantidade de regras no
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
				if (aux == '*') {  // O tamanho � dado como argumento.
					width = ((Integer) (args[arg++])).intValue();
					aux = texto.charAt(++i);
				}
				if (aux == '.') {   // Ai vem a precis�o...
					aux = texto.charAt(++i);
					if (aux == '*') { // a precis�o � dada como argumento.
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
	 * Troca todas as ocorr�ncias de um determinado caractere numa string por
	 * outro caractere.
	 * Exemplo: trocaChar("A_B_C",'_','+') resultar� em "A+B+C".
	 *
	 * @param str a string que ter� seus caracteres trocados.
	 * @param antigo o caractere que ser� substitu�do.
	 * @param novo o caractere que substituir� o caractere antigo.
	 *
	 * @return uma string com os caracteres substitu�dos.
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
	 * Tira os zeros � esquerda de uma string que representa um n�mero inteiro.
	 *
	 * @param str O string da qual ser� retirada os zeros � esquerda.
	 *
	 * @return O string sem zeros � esquerda.
	 */
	public static String zerosEsquerda(String str) {
		BigInteger i = new BigInteger(str);
		return i.toString();
	}


	/**
	 * Fun��o que recebe uma string e a devolve em partes em forma de um array.
	 * @param string A string que ser� repartida.
	 * @param delimitador O caracter ou express�o que se tomar� como base para separa��o
	 * das partes da String passada.
	 * @return Um array de string contendo as partes da string. Se um dos par�metros for
	 *  nulo ou se a string passada for vazia ent�o ser� retornado um array de dimens�o 1
	 *  com conte�do vazio.
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
 * Se total for menor ou igual a dec, � retornada uma String branco.
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
 * Verifica se uma string � um valor num�rico ou n�o
 * @param string
 * @return Retorna true se a string � um valor e false caso contr�rio.
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
 * Esse m�todo est� sendo usado na MicImpComprovantesFinanciamento
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
 * Esse m�todo est� sendo usado na MicImpComprovantesFinanciamento
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
 * Esse m�todo est� sendo usado na MicImpComprovantesFinanciamento
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