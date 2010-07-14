package com.infinity.datamarket.pdv;

import java.util.Date;
import java.util.Properties;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.componente.Componente;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.util.Parametro;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.comum.util.Util;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOSArquivo;
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

		Fachada.getInstancia().atualizarParametro(new Parametro("DIR_PADRAO_RECIBOS", Util.getDirCorrente()));

		GerenciadorPerifericos ger = GerenciadorPerifericos.getInstancia();
//		ti.jProgressBar1.setValue(50);

		Estado est = contr.getEstado(new Long(1));
		
		TelaMenssagem t = (TelaMenssagem) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_MENSAGEM);
    	t.setMenssagem("Caixa Fechado");
		Maquina maquina = Maquina.getInstancia(est, new Date(), ger, contr, t, "Caixa Fechado");
//		ti.jProgressBar1.setValue(75);
		ThreadEnviaTransacao t1 = new ThreadEnviaTransacao();
		t1.start();
		maquina.iniciar();
		RepositoryManagerHibernateUtil.getInstancia().closeSession();
//		ti.jProgressBar1.setValue(100);
//		ti.setVisible(false);
	}
	
	
}
