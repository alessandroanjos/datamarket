package com.infinity.datamarket.comum.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.faces.model.SelectItem;

public class Util {
	
	public static final String STRING_VAZIA = "";
	public static final String EXTENSAO_ZIP = ".zip";

	public static int comparaDatasSemHora(Date d1, Date d2){
		Calendar c = new GregorianCalendar();
		c.setTime(d1);
		int d1_dia = c.get(Calendar.DAY_OF_MONTH);
		int d1_mes = c.get(Calendar.MONTH);
		int d1_ano = c.get(Calendar.YEAR);
		Date data1 = new GregorianCalendar(d1_ano,d1_mes,d1_dia).getTime();
		
		c = new GregorianCalendar();
		c.setTime(d2);
		int d2_dia = c.get(Calendar.DAY_OF_MONTH);
		int d2_mes = c.get(Calendar.MONTH);
		int d2_ano = c.get(Calendar.YEAR);
		Date data2 = new GregorianCalendar(d2_ano,d2_mes,d2_dia).getTime();
		
		return data1.compareTo(data2);
	}
	
	public static String retornaDataFormatoDDMMYYYY(Date dataParametro){
		return new SimpleDateFormat("dd/MM/yyyy").format(dataParametro);
	}
	
	public static boolean validacpf(String strCpf){ // formato XXX.XXX.XXX-XX   
        if (! strCpf.substring(0,1).equals("")){   
            try{   
                boolean validado=true;   
                int     d1, d2;   
                int     digito1, digito2, resto;   
                int     digitoCPF;   
                String  nDigResult;   
                strCpf=strCpf.replace('.',' ');   
                strCpf=strCpf.replace('-',' ');   
                strCpf=strCpf.replaceAll(" ","");   
                d1 = d2 = 0;   
                digito1 = digito2 = resto = 0;   
                   
                for (int nCount = 1; nCount < strCpf.length() -1; nCount++) {   
                    digitoCPF = Integer.valueOf(strCpf.substring(nCount -1, nCount)).intValue();   
                       
                    //multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4 e assim por diante.   
                    d1 = d1 + ( 11 - nCount ) * digitoCPF;   
                       
                    //para o segundo digito repita o procedimento incluindo o primeiro digito calculado no passo anterior.   
                    d2 = d2 + ( 12 - nCount ) * digitoCPF;   
                };   
                   
                //Primeiro resto da divisão por 11.   
                resto = (d1 % 11);   
                   
                //Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.   
                if (resto < 2)   
                    digito1 = 0;   
                else  
                    digito1 = 11 - resto;   
                   
                d2 += 2 * digito1;   
                   
                //Segundo resto da divisão por 11.   
                resto = (d2 % 11);   
                   
                //Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.   
                if (resto < 2)   
                    digito2 = 0;   
                else  
                    digito2 = 11 - resto;   
                   
                //Digito verificador do CPF que está sendo validado.   
                String nDigVerific = strCpf.substring(strCpf.length()-2, strCpf.length());   
                   
                //Concatenando o primeiro resto com o segundo.   
                nDigResult = String.valueOf(digito1) + String.valueOf(digito2);   
                   
                //comparar o digito verificador do cpf com o primeiro resto + o segundo resto.   
                return nDigVerific.equals(nDigResult);   
            }catch (Exception e){   
                return false;   
            }   
        }else return false;   
    }   

	
	
	public  static boolean validaCnpj( String str_cnpj ) {   
        if (! str_cnpj.substring(0,1).equals("")){   
            try{   
                str_cnpj=str_cnpj.replace('.',' ');   
                str_cnpj=str_cnpj.replace('/',' ');   
                str_cnpj=str_cnpj.replace('-',' ');   
                str_cnpj=str_cnpj.replaceAll(" ","");   
                int soma = 0, aux, dig;   
                String cnpj_calc = str_cnpj.substring(0,12);   
                   
                if ( str_cnpj.length() != 14 )   
                    return false;   
                char[] chr_cnpj = str_cnpj.toCharArray();   
                /* Primeira parte */  
                for( int i = 0; i < 4; i++ )   
                    if ( chr_cnpj[i]-48 >=0 && chr_cnpj[i]-48 <=9 )   
                        soma += (chr_cnpj[i] - 48 ) * (6 - (i + 1)) ;   
                for( int i = 0; i < 8; i++ )   
                    if ( chr_cnpj[i+4]-48 >=0 && chr_cnpj[i+4]-48 <=9 )   
                        soma += (chr_cnpj[i+4] - 48 ) * (10 - (i + 1)) ;   
                dig = 11 - (soma % 11);   
                cnpj_calc += ( dig == 10 || dig == 11 ) ?   
                    "0" : Integer.toString(dig);   
                /* Segunda parte */  
                soma = 0;   
                for ( int i = 0; i < 5; i++ )   
                    if ( chr_cnpj[i]-48 >=0 && chr_cnpj[i]-48 <=9 )   
                        soma += (chr_cnpj[i] - 48 ) * (7 - (i + 1)) ;   
                for ( int i = 0; i < 8; i++ )   
                    if ( chr_cnpj[i+5]-48 >=0 && chr_cnpj[i+5]-48 <=9 )   
                        soma += (chr_cnpj[i+5] - 48 ) * (10 - (i + 1)) ;   
                dig = 11 - (soma % 11);   
                cnpj_calc += ( dig == 10 || dig == 11 ) ?   
                    "0" : Integer.toString(dig);   
                return str_cnpj.equals(cnpj_calc);   
            }catch (Exception e){      
                return false;   
            }   
        }else return false;              
    }   

	static String[][] listaUf = new String[][]{{"	AC	","Acre	"}	,
	{	"	AL	","Alagoas	"	}	,
	{	"	AP	","Amapá	"	}	,
	{	"	AM	","Amazonas"	}	,
	{	"	BA	","Bahia	"	}	,
	{	"	CE	","Ceará	"	}	,
	{	"	DF	","Distrito Federal	"	}	,
	{	"	ES	","Espírito Santo	"	}	,
	{	"	GO	","Goiás	"	}	,
	{	"	MA	","Maranhão	"	}	,
	{	"	MT	","Mato Grosso	"	}	,
	{	"	MS	","Mato Grosso do Sul"	}	,
	{	"	MG	","Minas Gerais	"	}	,
	{	"	PA	","Pará	"	}	,
	{	"	PB	","Paraíba	"	}	,
	{	"	PR	","Paraná	"	}	,
	{	"	PE	","Pernambuco"	}	,
	{	"	PI	","Piauí"	}	,
	{	"	RJ	","Rio de Janeiro	"	}	,
	{	"	RN	","Rio Grande do Norte	"	}	,
	{	"	RS	","Rio Grande do Sul	"	}	,
	{	"	RO	","Rondônia	"	}	,
	{	"	RR	","Roraima	"	}	,
	{	"	SC	","Santa Catarina	"	}	,
	{	"	SP	","São Paulo"	}	,
	{	"	SE	","Sergipe	"	}	,
	{	"	TO	","Tocantins"	}};
	
	public  static String[][] getListaUf() {
		return listaUf;
	}
	public  static SelectItem[] getListaSelectItemUf() {
		SelectItem[] listaUf = new SelectItem[Util.getListaUf().length+1];
		listaUf[0] = new SelectItem("","");
		for (int i = 1; i < listaUf.length; i++) {
			listaUf[i] = new SelectItem((String)Util.getListaUf()[i-1][0],
										(String)Util.getListaUf()[i-1][1]);
		}
		return listaUf;
	}
	
	public static String retornaMesPorExtenso(int mes){
		String[] mesExtenso = {"Janeiro","Fevereiro","Março","Abril",
				               "Maio","Junho","Julho","Agosto","Setembro",
				               "Outubro","Novembro","Dezembro"};
		
		return mesExtenso[mes];
	}
	
	public static String completaString(String string, String caracter,
			int quantidade, boolean esquerda) {
		if (string == null) {
			string = STRING_VAZIA;
		}
		if (quantidade > string.length()) {
			StringBuffer nova = new StringBuffer(quantidade);
			if(!esquerda){
				nova.append(string);
				quantidade -= string.length();
				for (int i = 0; i < quantidade; i++) {
					nova.append(caracter);
				}
			}else{
				quantidade -= string.length();
				for (int i = 0; i < quantidade; i++) {
					nova.append(caracter);
				}
				nova.append(string);	
			}
			string = nova.toString();
		} else {
			if(esquerda){
				string = string.substring(0, quantidade);	
			}else{
				string = string.substring(--quantidade);
			}
			
		}
		return string;
	}
	
	public static void main(String[] args) {
		Date d1 = new Date();
		try {
			Thread.currentThread().sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date d2 = new Date();
		System.out.println(comparaDatasSemHora(d1, d2));
	}
	
	public static void compactaArquivoZip(String[] arqs, String nomeZip) {
		
		String nomeFinalArquivo;

		// verifica o nome do arquivo zip
		if ((nomeZip == null) || (nomeZip.trim().equals(""))) {
		   return;
		}

		// verifica o conteúdo da lista de nomes de arquivos a serem
		// zipados
		if ((arqs == null) || (arqs.length < 1)) {
			return;
		}

		// cria o FileOutputStream com o nome do arquivo zip que deve ser criado
		FileOutputStream fout = null;
		try {
			nomeFinalArquivo = colocaExtensao(nomeZip);
			fout = new FileOutputStream(nomeFinalArquivo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ZipOutputStream zout = new ZipOutputStream(fout);
		for (int i = 0; i < arqs.length; ++i) {
			String arq = retiraDiretorio(arqs[i], File.separator);
			// garante que a entrada nao possui informacao de diretorio

			ZipEntry ze = new ZipEntry(arq);
			ze.setMethod(ZipEntry.DEFLATED);
			try {
				zout.putNextEntry(ze);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//Envia os dados para o arquivo
			RandomAccessFile inFile = null;
			try {
				inFile = new RandomAccessFile(arqs[i], "r");
			} catch (IOException e) {
				e.printStackTrace();
			}
			byte bb[] = new byte[1024];
			int amount;
			try {
				while ((amount = inFile.read(bb)) > 0) { // != -1
					zout.write(bb, 0, amount);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				if (zout != null) {
					try {
						zout.closeEntry();
					} catch (Exception e) {
						System.out.println(e);
					}
				}
				if (inFile != null) {
					try {
						inFile.close();
					} catch (Exception e) {
						System.out.println(e);
					}
				}
			}
		}
		if (zout != null) {
			try {
				zout.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}


	public static String retiraDiretorio(String arquivo, String barra) {
		int ultimaBarra = arquivo.lastIndexOf(barra);

		if (ultimaBarra != -1) {
			char[] newArquivo = new char[arquivo.length() - ultimaBarra-1];
			for (int i = ultimaBarra +1;  i  < arquivo.length(); i++) {
				newArquivo[i-ultimaBarra -1] = arquivo.charAt(i);
			}
			return new String(newArquivo);
		} else {
			return new String(arquivo);
		}

	}

	public static String colocaExtensao(String path) {
		String res = path;
		if (!res.toLowerCase().endsWith(EXTENSAO_ZIP)) {
			res = res + EXTENSAO_ZIP;
		}
		return res;
	}
}
