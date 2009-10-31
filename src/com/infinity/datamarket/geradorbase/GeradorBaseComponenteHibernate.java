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

import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;

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

	
	public void inicio(Long loja) throws Exception {
		
		File f = new File("\\pdv\\db\\"+loja);
		if (!f.exists()){
			f.mkdirs();
		}else{
			File[] arquivos = f.listFiles();
			for (int i = 0; i < arquivos.length; i++) {
				arquivos[i].delete();
			}
		}

		File fBanco = new File("\\pdv\\db\\template");
		File[] arquivos = fBanco.listFiles();
		for (int i = 0; i < arquivos.length; i++) {
			FileChannel oriChannel = new FileInputStream(arquivos[i]).getChannel();
			FileChannel destChannel = new FileOutputStream(new File(f.getPath()+File.separator+arquivos[i].getName())).getChannel();
			destChannel.transferFrom(oriChannel, 0, oriChannel.size());
			oriChannel.close();
			destChannel.close();
		}
		
		Configuration cfg = new Configuration();
//		.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect")		
//		.setProperty("hibernate.connection.driver_class","org.h2.Driver")
//		.setProperty("hibernate.connection.url","jdbc:h2:\\pdv\\db\\"+loja+"\\pdv")
//		.setProperty("hibernate.connection.username","sa")
//		.setProperty("hibernate.connection.password","sa")
//		.setProperty("hibernate.connection.pool_size","1");
		File fileCfg = new File("C:\\workspace\\datamarket\\src\\hibernate.cfg.pdv.xml");
		 
		sessionFactory = cfg.configure(fileCfg).
			setProperty("hibernate.connection.url","jdbc:h2:\\pdv\\db\\"+loja+"\\pdv").buildSessionFactory();
		
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

	protected void geraBaseUnidade(Collection col) throws Exception {
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

	
	

}
