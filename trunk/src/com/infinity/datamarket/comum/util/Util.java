package com.infinity.datamarket.comum.util;

import java.util.ArrayList;

import javax.faces.model.SelectItem;

public class Util {

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
                   
                //Primeiro resto da divis�o por 11.   
                resto = (d1 % 11);   
                   
                //Se o resultado for 0 ou 1 o digito � 0 caso contr�rio o digito � 11 menos o resultado anterior.   
                if (resto < 2)   
                    digito1 = 0;   
                else  
                    digito1 = 11 - resto;   
                   
                d2 += 2 * digito1;   
                   
                //Segundo resto da divis�o por 11.   
                resto = (d2 % 11);   
                   
                //Se o resultado for 0 ou 1 o digito � 0 caso contr�rio o digito � 11 menos o resultado anterior.   
                if (resto < 2)   
                    digito2 = 0;   
                else  
                    digito2 = 11 - resto;   
                   
                //Digito verificador do CPF que est� sendo validado.   
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
	{	"	AP	","Amap�	"	}	,
	{	"	AM	","Amazonas"	}	,
	{	"	BA	","Bahia	"	}	,
	{	"	CE	","Cear�	"	}	,
	{	"	DF	","Distrito Federal	"	}	,
	{	"	ES	","Esp�rito Santo	"	}	,
	{	"	GO	","Goi�s	"	}	,
	{	"	MA	","Maranh�o	"	}	,
	{	"	MT	","Mato Grosso	"	}	,
	{	"	MS	","Mato Grosso do Sul"	}	,
	{	"	MG	","Minas Gerais	"	}	,
	{	"	PA	","Par�	"	}	,
	{	"	PB	","Para�ba	"	}	,
	{	"	PR	","Paran�	"	}	,
	{	"	PE	","Pernambuco"	}	,
	{	"	PI	","Piau�"	}	,
	{	"	RJ	","Rio de Janeiro	"	}	,
	{	"	RN	","Rio Grande do Norte	"	}	,
	{	"	RS	","Rio Grande do Sul	"	}	,
	{	"	RO	","Rond�nia	"	}	,
	{	"	RR	","Roraima	"	}	,
	{	"	SC	","Santa Catarina	"	}	,
	{	"	SP	","S�o Paulo"	}	,
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
	
}
