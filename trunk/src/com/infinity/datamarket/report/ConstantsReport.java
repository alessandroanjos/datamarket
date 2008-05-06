package com.infinity.datamarket.report;

import java.io.File;

/**
 * Contem as constantes para as outras classes.
 * 
 * @author Edney Siqueira <a href="mailto:edney.siqueira@csi.com.br">edney.siqueira@csi.com.br
 */
public class ConstantsReport {

    public static final String CAMINHO_TEMPLATES_JRXML =
        File.separator + "templates" + File.separator;
    
    public static final String CAMINHO_TEMPLATES_JASPER =
    	CAMINHO_TEMPLATES_JRXML + "jasper" + File.separator;

    public static final String CAMINHO_TEMPORARIO =
        File.separator + "temp" + File.separator;

    public static final String EXTENSAO_JRXML = ".jrxml";    
    public static final String EXTENSAO_PDF = ".pdf";    
    public static final String EXTENSAO_JASPER = ".jasper";
    
    public static final int ARQ_PDF = 0;
    public static final int ARQ_HTML = 1;
    public static final int ARQ_JRXML = 2;
    public static final int ARQ_JASPER = 3;
    
    public static final int MAXIMA_QUANTIDADE_REGISTROS = 2500;

	public static String CAMINHO_RAIZ;

}
