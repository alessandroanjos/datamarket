package com.infinity.datamarket.pdv;

import java.util.Date;

import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.maquinaestados.Estado;
import com.infinity.datamarket.pdv.maquinaestados.Maquina;
import com.infinity.datamarket.pdv.transacao.ThreadEnviaTransacao;


public class StartUpPDV {
	public static void main(String[] a){
//		TelaInicial ti = new TelaInicial();
//
//		Toolkit toolkit = Toolkit.getDefaultToolkit();
//		Dimension screenSize = toolkit.getScreenSize();
//
////		Calcula a posição do frame a partir da resolucao usada
//		int x = (screenSize.width - ti.getWidth()) / 2;
//		int y = (screenSize.height - ti.getHeight()) / 2;
//
////		Define a posicao (centralizada)
//		ti.setLocation(x, y);
//
//
//		ti.setVisible(true);
//		ti.jProgressBar1.setValue(25);
		RepositoryManagerHibernateUtil.currentSession();
		GerenciadorPerifericos ger = GerenciadorPerifericos.getInstancia();
//		ti.jProgressBar1.setValue(50);
		Estado est = new Estado();
		est.setDescricao("FECHADO");
		est.setId(new Long(1));
		est.setInputSize(0);
		est.setInputType(0);
		Maquina maquina = Maquina.getInstancia(est, new Date(), ger);
//		ti.jProgressBar1.setValue(75);
		ThreadEnviaTransacao t1 = new ThreadEnviaTransacao();
		t1.start();
		maquina.iniciar();
		RepositoryManagerHibernateUtil.closeSession();
//		ti.jProgressBar1.setValue(100);
//		ti.setVisible(false);
	}
}
