package com.infinity.datamarket.geradorbase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Collection;
import java.util.Iterator;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.infinity.datamarket.comum.componente.Componente;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.util.Util;

public class GeradorBaseComponenteHibernate extends GeradorBaseComponente{
	
	private static GeradorBaseComponenteHibernate instancia;
	private SessionFactory sessionFactory;
	private GeradorBaseComponenteHibernate(){					
		
	}
	
	public static GeradorBaseComponenteHibernate getInstancia(){
		if (instancia == null){
			instancia = new GeradorBaseComponenteHibernate();			
		}
		return instancia;
	}
	
	
	
	private void geradorBaseGenerico(Collection objetos) throws Exception{
		if (objetos != null){
			Transaction trans =  null;
			Session s = null;
			try{
				Iterator i = objetos.iterator();
				s = sessionFactory.openSession();
				trans =  s.beginTransaction();
				while(i.hasNext()){
					Object obj = i.next();							
					s.save(obj);
				}
				trans.commit();				
			}catch (Exception e) {
				if (trans != null){
					trans.rollback();
					throw e;
				}
			}finally{
				if (s != null){
					s.close();
				}				
			}			
		}
	}
	
	public void finaliza()  throws Exception{
		sessionFactory.close();
	}

	
	public void inicio(Long loja, Componente componente) throws Exception {

		String diretorioDestino = Util.getDirDestinoCargaBaseLojaComponente(loja, componente.getId());
		String diretorioOrigemTemplante = Util.getDirTemplateCargaBase();
		File f = new File(diretorioDestino);
		if (!f.exists()){
			f.mkdirs();
		}else{
			Util.apagarArquivos(f);
		}

		Util.copiarArquivos(diretorioOrigemTemplante, diretorioDestino); 

		Util.alterarNomeBanco(diretorioDestino, "pdv", "av");

		String nomeBanco = "pdv";
		if (componente.getTipoComponente() == Componente.TIPO_COMPONENTE_AV) {
			nomeBanco = "av";
		}
		Configuration cfg = new Configuration();
		
		String arq = Util.getDirCorrente() + "/WEB-INF/classes/" + RepositoryManagerHibernateUtil.HIBERNATE_PDV;
		File file = new File(arq);

		sessionFactory = cfg.configure(file).
			setProperty("hibernate.connection.url","jdbc:h2:" + diretorioDestino + "/" + nomeBanco).buildSessionFactory();
		
	}

	protected void geraBaseProsutos(Collection col) throws Exception {
		geradorBaseGenerico(col);
		
	}

	protected void geraBaseTotalizadoresNaoFiscais(Collection col) throws Exception {
		geradorBaseGenerico(col);
		
	}

	protected void geraBaseUsuarios(Collection col) throws Exception {
		geradorBaseGenerico(col);
		
	}
	
	protected void geraBaseImposto(Collection col) throws Exception {
		geradorBaseGenerico(col);
		
	}

	protected void geraBaseFabricante(Collection col) throws Exception {
		geradorBaseGenerico(col);
		
	}

	protected void geraBaseGrupoProduto(Collection col) throws Exception {
		geradorBaseGenerico(col);
		
	}

	protected void geraBaseTipoProduto(Collection col) throws Exception {
		geradorBaseGenerico(col);
		
	}


	protected void geraBaseParametros(Collection col)throws Exception {
		geradorBaseGenerico(col);
		
	}
	
	protected void geraBaseUnidade(Collection col) throws Exception {
		geradorBaseGenerico(col);
		
	}

	protected void geraBaseLojas(Collection col)
	throws Exception{
		geradorBaseGenerico(col);
	}
	
	public void geraBaseAcumuladorNaoFiscal(Collection objetos) throws Exception{
		geradorBaseGenerico(objetos);
	}
	
	public void geraBaseAutorizadora(Collection objetos) throws Exception{
		geradorBaseGenerico(objetos);
	}
	public void geraBaseComponente(Collection objetos) throws Exception{
		geradorBaseGenerico(objetos);
	}
	
	protected void geraBaseFormaRecebimento(Collection objetos) throws Exception {
		geradorBaseGenerico(objetos);
	}
	protected void geraBaseLoja(Collection objetos) throws Exception {
		geradorBaseGenerico(objetos);
	}
	protected void geraBasePlanos(Collection objetos) throws Exception {
		geradorBaseGenerico(objetos);
	}
	
	protected void geraBasePerfil(Collection objetos) throws Exception {
		geradorBaseGenerico(objetos);
	}
	
	protected void geraBaseMacroOperacao(Collection objetos) throws Exception{
		geradorBaseGenerico(objetos);
	}


	protected void geraBaseBancos(Collection objetos) throws Exception{
		geradorBaseGenerico(objetos);
	}


	protected void geraBaseContaCorrente(Collection objetos) throws Exception{
		geradorBaseGenerico(objetos);
	}
}