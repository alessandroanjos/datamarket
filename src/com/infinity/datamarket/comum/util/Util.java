package com.infinity.datamarket.comum.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
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
	
	public static Date adicionarDia(Date d1, int quantidade){
		Calendar c = new GregorianCalendar();
		c.setTime(d1);
		
		c.add(Calendar.DAY_OF_MONTH, quantidade);

		return c.getTime();
	}
  
	public static Date subtrairDia(Date d1, int quantidade){
		Calendar c = new GregorianCalendar();
		c.setTime(d1);
		
		c.add(Calendar.DAY_OF_MONTH, quantidade);

		return c.getTime();
	}
  

	public static Date formatarStringParaData(String dataParametro) throws ParseException {
		DateFormat f = new SimpleDateFormat("dd/MM/yyyy");
		return f.parse(dataParametro);
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
		try 
		{ 
		    //create a ZipOutputStream to zip the data to 
		    ZipOutputStream zos = new  ZipOutputStream(new FileOutputStream("c:\\curDir.zip")); 
		    //assuming that there is a directory named inFolder (If there 
		    //isn't create one) in the same directory as the one the code   runs from, 
		    //call the zipDir method 
		    zipDir("C:\\Arquivos de programas\\jboss-4.0.5.GA\\server\\default\\deploy\\EnterpriseServer.war\\banco\\1\\1", zos); 
		    //close the stream 
		    zos.close(); 
		} 
		catch(Exception e) 
		{ 
		    //handle exception 
		} 
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
	
	private static String dirCorrente;

	// Inicializa os atributos estáticos da classe
	static {
		String arquivo = "Perifericos.properties";
		URL url = Util.class.getClassLoader().getResource(arquivo);
		if (url != null) {
			dirCorrente = url.toString();
			dirCorrente = dirCorrente.substring("file:\\".length(), dirCorrente.length());
			try {
				dirCorrente = dirCorrente.substring(0, dirCorrente.indexOf("WEB-INF"));
				dirCorrente = dirCorrente.substring(0, dirCorrente.length() - 1);
				// no caso do sistema operacional for o linux
				// coloca a para inicial
				int local = dirCorrente.indexOf(":");
				if (local == -1) {
					dirCorrente = "/" + dirCorrente;
				}
			} catch (Exception e) {
			}

			try {
				if (dirCorrente.endsWith(arquivo)) {
					dirCorrente = dirCorrente.substring(0,dirCorrente.length() - arquivo.length());
				}
				if (dirCorrente.endsWith("WebResources")) {
					dirCorrente = dirCorrente.substring(0,dirCorrente.length() - "WebResources".length());
				}
				if (dirCorrente.endsWith("\\")) {
					dirCorrente = dirCorrente.substring(0,dirCorrente.length() - 1);
				}
				if (dirCorrente.endsWith("/")) {
					dirCorrente = dirCorrente.substring(0,dirCorrente.length() - 1);
				}
				
				
				int tem = dirCorrente.indexOf("%20");
				while (tem != -1) {
					dirCorrente = dirCorrente.substring(0,tem) + " " + dirCorrente.substring(tem + 3,dirCorrente.length()); 
					
					tem = dirCorrente.indexOf("%20");
				}
				tem = dirCorrente.indexOf("/./");
				while (tem != -1) {
					dirCorrente = dirCorrente.substring(0,tem) + "/" + dirCorrente.substring(tem + 3,dirCorrente.length()); 
					
					tem = dirCorrente.indexOf("/./");
				}
			} catch (Exception e) {
			}
		} else {
			throw new RuntimeException( "O caminho do arquivo de configuração principal do sistema não foi encontrado: " + "Perifericos.properties");
		}
	}

	/**
	 * Metodo que retorna o diretorio corrente onde o sistema foi cadastrado  
	 * 
	 * @return
	 */
	public static String getDirCorrente() {
		return dirCorrente;
	}
	
	/**
	 * Retorna os bytes de um arquivo
	 * 
	 * @param arquivo
	 * @return
	 * @throws Exception
	 */
	public static byte[] getByteArquivo(String arquivo)   throws Exception {
	    FileInputStream input = new FileInputStream(arquivo);
	    
		byte[] dados = new byte[input.available()];
		input.read(dados);
		return (dados);
		
	}
	
	/**
	 * retorna os bytes de um arquivo zipado que tem dentro deles todos os arquivos do diretorio passado
	 * 
	 * @param dirOrigem
	 * @return
	 * @throws Exception
	 */
	public static byte[] zipDir(String dirOrigem)  throws Exception {
		String destino = getDirTemp() + "/" + getDirDataHora() + ".zip";

		ZipOutputStream zos = new  ZipOutputStream(new FileOutputStream(destino)); 
	    zipDir(dirOrigem, zos);
		zos.close();


	    byte[] b = getByteArquivo(destino);

	    try {
			new File(destino).delete();
		} catch (Exception e) {
			// TODO: handle exception
		}
	    return b;
	}	

	/**
	 * zipa uma pasta e salva no arquivo passado
	 * 
	 * @param dirOrigem
	 * @param dirDestino
	 * @throws Exception
	 */
	public static void zipDir(String dirOrigem, String dirDestino)  throws Exception {
		ZipOutputStream zos = new  ZipOutputStream(new FileOutputStream(dirDestino)); 
	    zipDir(dirOrigem, zos);
		zos.close();

	}	
	public static void zipDir(String dir2zip, ZipOutputStream zos) throws Exception {
			// create a new File object based on the directory we have to zip
			// File
			File zipDir = new File(dir2zip);
			// get a listing of the directory content
			String[] dirList = zipDir.list();
			byte[] readBuffer = new byte[2156];
			int bytesIn = 0;
			// loop through dirList, and zip the files
			for (int i = 0; i < dirList.length; i++) {
				File f = new File(zipDir, dirList[i]);
				if (f.isDirectory()) {
					// if the File object is a directory, call this
					// function again to add its content recursively
					String filePath = f.getPath();
					zipDir(filePath, zos);
					// loop again
					continue;
				}
				// if we reached here, the File object f was not a directory
				// create a FileInputStream on top of f
				FileInputStream fis = new FileInputStream(f);
				// create a new zip entry
				ZipEntry anEntry = new ZipEntry(f.getName());
				// place the zip entry in the ZipOutputStream object
				zos.putNextEntry(anEntry);
				// now write the content of the file to the ZipOutputStream
				while ((bytesIn = fis.read(readBuffer)) != -1) {
					zos.write(readBuffer, 0, bytesIn);
				}
				// close the Stream
				fis.close();
			}
	}

	public static void unZipDir(String arquivoZipado, String diretorio) throws IOException {
		Enumeration entries;
	    ZipFile zipFile;

	    try {
	      zipFile = new ZipFile(arquivoZipado);

	      entries = zipFile.entries();

	      while(entries.hasMoreElements()) {
	        ZipEntry entry = (ZipEntry)entries.nextElement();

	        if(entry.isDirectory()) {
	          // Assume directories are stored parents first then children.
	          System.err.println("Extracting directory: " + entry.getName());
	          // This is not robust, just for demonstration purposes.
	          (new File(entry.getName())).mkdir();
	          continue;
	        }

	        System.err.println("Extracting file: " + entry.getName());
	        copyInputStream(zipFile.getInputStream(entry),
	           new BufferedOutputStream(new FileOutputStream(new File(diretorio,entry.getName()))));
	      }

	      zipFile.close();
	    } catch (IOException ioe) {
	      System.err.println("Unhandled exception:");
	      ioe.printStackTrace();
	      return;
	    }
	  }
	/**
	 * 
	 * @param arquivoZipado
	 * @param diretorio
	 * @deprecated
	 * @throws IOException
	 */
	public static void unZipDir2(String arquivoZipado, String diretorio) throws IOException {
		 BufferedOutputStream dest = null;
		FileInputStream fis = new FileInputStream(arquivoZipado);
		ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
		ZipEntry entry;
		while ((entry = zis.getNextEntry()) != null) {
			System.out.println("Extracting: " + entry);
			int count;
			byte data[] = new byte[2048];
			// write the files to the disk
			FileOutputStream fos = new FileOutputStream(new File(diretorio,entry.getName()));
			dest = new BufferedOutputStream(fos, 2048);
			while ((count = zis.read(data, 0, 2048)) != -1) {
				dest.write(data, 0, count);
			}
			dest.flush();
			dest.close();
		}
		zis.close();
	}

	public static void apagarArquivos(String dir) {
		apagarArquivos(new File(dir));
	}
	

	public static void apagarArquivos(File dir) {
		File[] arquivos = dir.listFiles();
		for (int i = 0; i < arquivos.length; i++) {
			if (arquivos[i].isFile()) {
				arquivos[i].delete();
			}
		}
	}
	
	public static final void copyInputStream(InputStream in, OutputStream out)
	  throws IOException
	  {
	    byte[] buffer = new byte[1024];
	    int len;

	    while((len = in.read(buffer)) >= 0)
	      out.write(buffer, 0, len);

	    in.close();
	    out.close();
	  }


	public static void copiarArquivos(String diretorioOrigem, String diretorioDestino) throws IOException {
		File f = new File(diretorioDestino);
		try {
			f.mkdirs();
		} catch (Exception e) {
			// TODO: handle exception
		}
		File fBanco = new File(diretorioOrigem);
		File[] arquivos = fBanco.listFiles();
		for (int i = 0; i < arquivos.length; i++) {
			if (arquivos[i].isFile()) {
				FileChannel oriChannel = new FileInputStream(arquivos[i]).getChannel();
				FileChannel destChannel = new FileOutputStream(new File(f.getPath()+File.separator+arquivos[i].getName())).getChannel();
				destChannel.transferFrom(oriChannel, 0, oriChannel.size());
				oriChannel.close();
				destChannel.close();
			}
		}

	}
	public static void copiarTudo(String diretorioOrigem, String diretorioDestino) throws IOException {
		File f = new File(diretorioDestino);
		try {
			f.mkdirs();
		} catch (Exception e) {
			// TODO: handle exception
		}
		File fBanco = new File(diretorioOrigem);
		File[] arquivos = fBanco.listFiles();
		for (int i = 0; i < arquivos.length; i++) {
			if (arquivos[i].isFile()) {
				FileChannel oriChannel = new FileInputStream(arquivos[i]).getChannel();
				FileChannel destChannel = new FileOutputStream(new File(f.getPath()+File.separator+arquivos[i].getName())).getChannel();
				destChannel.transferFrom(oriChannel, 0, oriChannel.size());
				oriChannel.close();
				destChannel.close();
			}
		}

	}

	public static String getDirDestinoCargaBaseLojaComponente(Long loja, Long idComponente) {
		String dirTemp = Util.getDirCorrente() + "/banco/"+loja + "/" + idComponente;
		try {
			if(!new File(dirTemp).exists()) 
				new File(dirTemp).mkdirs();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return dirTemp; 
	}
	
	public static String getDirDataHora() {
		Date date = new Date();
		
		return date.getTime() + "";
	}
	
	public static String getDirBakpBasePDV() {
		String dirTemp = Util.getDirCorrente() + "\\bkpCargaBase";
		try {
			if(!new File(dirTemp).exists()) 
				new File(dirTemp).mkdirs();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return dirTemp;
	}
	public static String getDirBasePDV() {
		String dirTemp = Util.getDirCorrente() + "\\banco";
		try {
			if(!new File(dirTemp).exists()) 
				new File(dirTemp).mkdirs();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return dirTemp;
	}
	public static String getBasePDV() {
		String dirTemp = Util.getDirCorrente() + "\\banco\\pdv";
		try {
			if(!new File(dirTemp).exists()) 
				new File(dirTemp).mkdirs();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return dirTemp;
	}
	public static String getDirDestinoCargaBaseLoja(Long loja ) {
		String dirTemp = Util.getDirCorrente() + "/banco/"+loja  ;
		try {
			if(!new File(dirTemp).exists()) 
				new File(dirTemp).mkdirs();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return dirTemp;
	}
	public static String getDirTemp() {
		String dirTemp = Util.getDirCorrente() + "/temp";
		try {
			if(!new File(dirTemp).exists()) 
				new File(dirTemp).mkdirs();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return dirTemp;
	}
	
	public static String getDirTemplateCargaBase() {
		String dirTemp = Util.getDirCorrente() + "/bd/template";
		try {
			if(!new File(dirTemp).exists()) 
				new File(dirTemp).mkdirs();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return dirTemp;
	}

	public static String formataCpfCnpj(String cpfCnpf){
		String result = "";
		String temp = "";
		//formata cpf xx.xxx.xxx-xx
		if(cpfCnpf.length() <= 11){
			for(int i = cpfCnpf.length(); i < 11;i++){
				temp = temp + "0";
			}
			temp = temp.concat(cpfCnpf);
			result = temp.substring(0,3) + "." + temp.substring(3, 6) + "." + temp.substring(6, 9) + "-" + temp.substring(9);
		}else{
			//forma cnpj xx.xxx.xxx/xxxx-xx
			for(int i = cpfCnpf.length(); i < 14;i++){
				temp = temp + "0";
			}
			temp = temp.concat(cpfCnpf);
			result = temp.substring(0,2) + "." + temp.substring(2, 5) + "." + temp.substring(5, 8) + "/" + temp.substring(8, 12) + "-" + temp.substring(12);
		}		
		return result;
	}
	
	public static String formataCEP(String cep){
		String cepRetorno = cep;
		
		if(cep.length() == 8){
			cepRetorno = cep.substring(0, 2) + "." + cep.substring(2, 5) + "-" + cep.substring(5, 8); 
		}
		
		return cepRetorno;
	}

}
