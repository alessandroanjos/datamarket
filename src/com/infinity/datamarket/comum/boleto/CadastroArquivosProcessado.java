package com.infinity.datamarket.comum.boleto;

import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.CadastroControleId;
import com.infinity.datamarket.comum.util.Controle;
import com.infinity.datamarket.comum.util.IRepositorio;

public class CadastroArquivosProcessado extends Cadastro{
	
	private static CadastroArquivosProcessado instancia;
	
//	private static final Class CLASSE = ArquivosProcessado.class;
	
	public static CadastroArquivosProcessado getInstancia(){
		if (instancia == null){
			instancia = new CadastroArquivosProcessado();			
		}
		return instancia;
	}

	private CadastroBoleto getCadastroBoleto(){
		return CadastroBoleto.getInstancia();
	}
	
	public IRepositorioArquivosProcessado getRepositorio() {
		// TODO Auto-generated method stub
		return (IRepositorioArquivosProcessado) super.getRepositorio(IRepositorio.REPOSITORIO_ARQUIVO_PROCESSADO);
	}
	
	public void inserir(ArquivoProcessado ArquivosProcessado) throws AppException{
		
		Controle controle = CadastroControleId.getInstancia().getControle(ArquivoProcessado.class);
		ArquivosProcessado.setId(controle.getValor());
		Collection<PagamentoBoleto> coll = ArquivosProcessado.getPagamentosBoleto();
		if (coll != null) {
			Iterator<PagamentoBoleto> it = coll.iterator();
			while(it.hasNext()){
				PagamentoBoleto pb = it.next();
				pb.getPk().setIdArquivoProcessado(controle.getValor());

				Boleto bol = getCadastroBoleto().baixarBoleto(pb.getIdBoleto());
				pb.setBoleto(bol);
			}
		}
		getRepositorio().inserir(ArquivosProcessado);
	}
	
	public void alterar(ArquivoProcessado ArquivosProcessado) throws AppException{
		getRepositorio().alterar(ArquivosProcessado);
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
}