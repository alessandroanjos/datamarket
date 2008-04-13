package com.infinity.datamarket.report;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LerXML {

    private static final String QUEBRA_NIVEL1 = "$P{quebraNivel1}";
    private static final String VALOR_QUEBRA_NIVEL1 = "$F{numeroLoja}";

    private static final String QUEBRA_NIVEL2 = "$P{quebraNivel2}";
    private static final String VALOR_QUEBRA_NIVEL2 = "$F{dataTesouraria}";

    public static String alterarXML(String nomeTemplate, String[] token, String[] valoresToken){

        //nome do novo arquivo
        String novoArquivo = null;

        //setando o nome do arquivo
        String nomeArquivoTemplate = nomeTemplate;

        FileReader fr = null;
        BufferedReader br = null;

        FileWriter fw = null;
        BufferedWriter bw = null;

        try {
        	
        	System.out.println("LerXML::alterarXML:: nome arquivo lido=" + nomeArquivoTemplate + ConstantsReport.EXTENSAO_JRXML);
            fr = new FileReader(nomeArquivoTemplate + ConstantsReport.EXTENSAO_JRXML);
            br = new BufferedReader(fr);            

            System.out.println("LerXML::alterarXML:: nome arquivo com alteracoes=" + nomeArquivoTemplate + "Editado" + ConstantsReport.EXTENSAO_JRXML);
            novoArquivo = nomeArquivoTemplate + "Editado" + ConstantsReport.EXTENSAO_JRXML;            

            fw = new FileWriter(novoArquivo);
			bw = new BufferedWriter(fw);

            String line;
            line = br.readLine();

            boolean jaEscreveu;

            while (line != null) {
                jaEscreveu = false;

                if (!line.equals("")) {
//                    System.out.println(line);
                    //caso exista o tokin
                    for(int i=0; i<token.length; i++){
                        if (line.indexOf(token[i]) > 0) {
                            int indiceQuebraNivel1 = line.indexOf(token[i]);
                            bw.write(
                                    line.substring(0, indiceQuebraNivel1)
                                    + valoresToken[i]
                                    + line.substring(
                                            (indiceQuebraNivel1 + token[i].length()),
                                            line.length()));
            				bw.newLine();
            				i = token.length + 2 ;

            				jaEscreveu = true;
                        }
                    }//fim do for
                }

                if( ! jaEscreveu){
                    bw.write(line);
    				bw.newLine();
                }

                line = br.readLine();
            }//fim do while

        } catch (FileNotFoundException e) {
        	System.out.println(e);
        } catch (IOException e1) {
        	System.out.println(e1);
        }finally{

            try {
                br.close();
                fr.close();

                bw.close();
                fw.close();
            } catch (IOException e2) {
            	System.out.println(e2);
            }

        }
        return nomeTemplate + "Editado";
    }


    public static void main(String[] args){

        //setando o nome do arquivo
        String nomeArquivoTemplate = "C:\\projetoTesouraria\\TesourariaJava\\"
                + ConstantsReport.CAMINHO_TEMPLATES_JRXML
                + "relacaoTransacaoTesouraria_groupGenerico";

        FileReader fr = null;
        BufferedReader br = null;

        FileWriter fw = null;
        BufferedWriter bw = null;

        try {

            fr = new FileReader(nomeArquivoTemplate + ConstantsReport.EXTENSAO_JRXML);
            br = new BufferedReader(fr);

            fw = new FileWriter(nomeArquivoTemplate + "Novo" + ConstantsReport.EXTENSAO_JRXML);
			bw = new BufferedWriter(fw);

            String line;
            line = br.readLine();
            while (line != null) {
                if (!line.equals("")) {
//                    System.out.println(line);
                    //caso exista o tokin
                    if (line.indexOf(QUEBRA_NIVEL1) > 0) { //System.out.println(line.substring(indiceQuebraNivel1, (indiceQuebraNivel1 + QUEBRA_NIVEL1.length())));                        System.out.print(line.substring(0, indiceQuebraNivel1 ) + " -->TOKEN<--");                        System.out.println(line.substring((indiceQuebraNivel1 + QUEBRA_NIVEL1.length()), line.length()));
                        int indiceQuebraNivel1 = line.indexOf(QUEBRA_NIVEL1);
                        bw.write(
                                line.substring(0, indiceQuebraNivel1)
                                + VALOR_QUEBRA_NIVEL1
                                + line.substring(
                                        (indiceQuebraNivel1 + QUEBRA_NIVEL1.length()),
                                        line.length()));
        				bw.newLine();

                    }else if(line.indexOf(QUEBRA_NIVEL2) > 0){
                        int indiceQuebraNivel2 = line.indexOf(QUEBRA_NIVEL2);
                        bw.write(
                                line.substring(0, indiceQuebraNivel2)
                                + VALOR_QUEBRA_NIVEL2
                                + line.substring(
                                        (indiceQuebraNivel2 + QUEBRA_NIVEL2.length()),
                                        line.length()));
        				bw.newLine();

                    }else{
                        bw.write(line);
                        bw.newLine();
                    }

                    line = br.readLine();
                }
            }

        } catch (FileNotFoundException e) {
        	System.out.println(e);
        } catch (IOException e1) {
        	System.out.println(e1);
        }finally{

            try {
                br.close();
                fr.close();

                bw.close();
                fw.close();
            } catch (IOException e2) {
            	System.out.println(e2);
            }

        }

    }
}