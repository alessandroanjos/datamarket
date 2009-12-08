package com.infinity.datamarket.pdv;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

import com.infinity.datamarket.comum.componente.Componente;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.comum.util.Util;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.gui.telas.TelaMenssagem;
import com.infinity.datamarket.pdv.maquinaestados.ControladorMaquinaEstado;
import com.infinity.datamarket.pdv.maquinaestados.Estado;
import com.infinity.datamarket.pdv.maquinaestados.LeitorMaquinaEstadoXML;
import com.infinity.datamarket.pdv.maquinaestados.Maquina;
import com.infinity.datamarket.pdv.transacao.ThreadEnviaTransacao;


public class StartUpPDV {
	public static void main(String[] a) throws Exception{
		ControladorMaquinaEstado contr = LeitorMaquinaEstadoXML.lerArquivoXM("fluxoPDV.xml");

		String diretorioH2 = Util.getBasePDV(Componente.TIPO_COMPONENTE_PDV);

		Properties propreties = new Properties();
	    propreties.put("hibernate.connection.url", "jdbc:h2:" + diretorioH2);

		RepositoryManagerHibernateUtil.arquivoHibernate = RepositoryManagerHibernateUtil.HIBERNATE_PDV;
		RepositoryManagerHibernateUtil.properties = propreties;
		RepositoryManagerHibernateUtil.getInstancia().currentSession();
		GerenciadorPerifericos ger = GerenciadorPerifericos.getInstancia();
//		ti.jProgressBar1.setValue(50);
		Estado est = new Estado();
		est.setDescricao("FECHADO");
		est.setId(new Long(1));
		est.setInputSize(0);
		est.setInputType(0);
		TelaMenssagem t = (TelaMenssagem) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_MENSAGEM);
    	t.setMenssagem("Caixa Fechado");
		Maquina maquina = Maquina.getInstancia(est, new Date(), ger, contr, t, "Caixa Fechadoo");
//		ti.jProgressBar1.setValue(75);
		ThreadEnviaTransacao t1 = new ThreadEnviaTransacao();
		t1.start();
		maquina.iniciar();
		RepositoryManagerHibernateUtil.getInstancia().closeSession();
//		ti.jProgressBar1.setValue(100);
//		ti.setVisible(false);
	}
	
	
}
