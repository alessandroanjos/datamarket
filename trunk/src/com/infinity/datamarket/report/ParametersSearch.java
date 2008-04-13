package com.infinity.datamarket.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 * Consulta os valores dos parâmetros definido no template e gera um 
 * MAP com seus respectivos valores. Padrão I18N da CSI
 * 
 * @author Edney Siqueira <a href="mailto:edney.siqueira@csi.com.br">edney.siqueira@csi.com.br
 */
public class ParametersSearch {

    public static Map parametersSearchProperties(JasperDesign design){
	//      Pega todos os parametros do objeto design
        List listaParametros = design.getParametersList();
        //      Criação da Map, onde vai conter os parametros do relatorio
        Map parametros = new TreeMap();
        //      percorre a lista de parametros
        for (int i = 0; i < listaParametros.size(); i++) {
            //      pega o valor do nome do parametro
            String parametro = ((JRParameter) listaParametros.get(i)).getName();
            //      REL_*
            if (parametro.trim().substring(0, 3).equalsIgnoreCase("REL")) {
                //      preenche na lista os parametros chaves do properties. Padrao
                if(pesquisarChave(parametro) != null){
                    parametros.put(parametro, pesquisarChave(parametro));
//                              parametros.put(parametro,MensagensConsultas.getDescricao(parametro));
                }
            }
        }
        return parametros;
      }
    
    /**
     * @param design
     * @param parameters
     * @param parameters
     */
    public static void parametersSearchMap(JasperDesign design, Map lista, Map parameters) {
//        Pega todos os parametros do objeto design
        List listaParametros = design.getParametersList();
        
        //      percorre a lista de parametros
        for (int i = 0; i < listaParametros.size(); i++) {
            //      pega o valor do nome do parametro
            String parametro = ((JRParameter) listaParametros.get(i)).getName();
            //      REL_*
            if (parametro.trim().substring(0, 3).equalsIgnoreCase("REL")) {
                //      preenche na lista os parametros chaves do Map.
                if(parameters.get(parametro) != null){
                    lista.put(parametro, parameters.get(parametro));
                }
            }
        }
    }
    
    
    public static String pesquisarChave(String chave){
        
//      o arquivo encontra-se no mesmo diretório //da aplicação
        File file = new File("C:\\projetoTesouraria\\TesourariaWeb\\templates\\mensagensConsultas.properties");    
        Properties props = new Properties();
        FileInputStream fis = null;
        
        String valor = null; 
        
        try {
            fis = new FileInputStream(file);
            //lê os dados que estão no arquivo
            props.load(fis);
            valor = props.getProperty(chave);
            fis.close();
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        return valor;
    }

}
