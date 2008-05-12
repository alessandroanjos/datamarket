package com.infinity.datamarket.lote;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.ejb.Stateless;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.lote.DadoLote;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ConcentradorParametro;

@Stateless
public class LoteServer implements LoteServerLocal, LoteServerRemote {
	public boolean verificaNovoLoteLiberado(int numeroLote){
		int numeroLoteAtual = ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.LOTE).getValorInteiro();
		if (numeroLoteAtual > numeroLote){
			return true;
		}
		return false;
	}
	public Collection getLote(int numeroLote, int numLoja) throws AppException{
		PropertyFilter filter = new PropertyFilter();
		filter.setTheClass(DadoLote.class);
		filter.addProperty("lote", numeroLote + 1);
		filter.addOrderByProperty("sequencial", filter.ASC);
		Collection col = Fachada.getInstancia().consultarDadosLote(filter);
		Collection retorno = new Vector();
		Iterator i = col.iterator();
		while(i.hasNext()){
			DadoLote dado = (DadoLote) i.next();
			Object obj = dado.getDado();
			System.out.println(obj);
			if (obj instanceof Produto){
				Produto prod = (Produto) obj;
				Collection lojas = prod.getLojas();
				Iterator iLojas = lojas.iterator();
				boolean contem = false;
				while(iLojas.hasNext()){
					Loja loja = (Loja) iLojas.next();
					if (loja.getId().equals(new Long(numLoja))){
						contem = true;
					}
				}
				if (contem){
					retorno.add(dado);
				}
			}else if (obj instanceof Usuario){
				Usuario usu = (Usuario) obj;
				Collection usuarios = usu.getLojas();
				Iterator iUsuarios = usuarios.iterator();
				boolean contem = false;
				while(iUsuarios.hasNext()){
					Usuario usuario = (Usuario) iUsuarios.next();
					if (usuario.getId().equals(new Long(numLoja))){
						contem = true;
					}
				}
				if (contem){
					retorno.add(dado);
				}
			}else{
				retorno.add(dado);
			}
		}
		return retorno;
		
	}
}
