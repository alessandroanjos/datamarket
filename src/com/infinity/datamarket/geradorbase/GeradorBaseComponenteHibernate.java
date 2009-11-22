package com.infinity.datamarket.geradorbase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.Collection;
import java.util.Iterator;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.util.Util;
import com.infinity.datamarket.pdv.maquinaestados.LeitorMaquinaEstadoXML;

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

		String diretorioDestino = Util.getDirCorrente() + "\\banco\\"+loja;
		String diretorioOrigem = Util.getDirCorrente() + "\\db\\template";
		File f = new File(diretorioDestino);
		if (!f.exists()){
			f.mkdirs();
		}else{
			File[] arquivos = f.listFiles();
			for (int i = 0; i < arquivos.length; i++) {
				if (arquivos[i].isFile()) {
					arquivos[i].delete();
				}
			}
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

		Configuration cfg = new Configuration();
		
		URL url = LeitorMaquinaEstadoXML.class.getClassLoader().getResource(RepositoryManagerHibernateUtil.HIBERNATE_PDV);
		String arq = url.toString();
		arq = arq.substring("file:\\".length(), arq.length());
		File file = new File(arq);

		sessionFactory = cfg.configure(file).
			setProperty("hibernate.connection.url","jdbc:h2:" + diretorioDestino + "\\pdv").buildSessionFactory();
		
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