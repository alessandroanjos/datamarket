package com.infinity.datamarket.enterprise.gui.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.operacao.Operacao;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;

public class ReceptorOperacaoServlet extends HttpServlet {

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			InputStream obj = request.getInputStream();
			ObjectInputStream input = new ObjectInputStream(obj);
			Object object = input.readObject();
			Operacao operacao = (Operacao) object;
			input.close();

			// quando ele � criando no AV ele adiciona 
			// quando ele � separado ele atualiza
//			if (Fachada.getInstancia().existeOperacao(operacao.getPk().getLoja(), operacao.getPk().getId())) {
			
			PropertyFilter filter = new PropertyFilter();
			filter.setTheClass(Operacao.class);
			filter.addProperty("pk.id", operacao.getPk().getId());
			filter.addProperty("pk.loja", operacao.getPk().getLoja());
			
			Collection coll = Fachada.getInstancia().consultarOperacao(filter);
			
			if (coll != null && coll.size() != 0) {
				Operacao opvelho = (Operacao) coll.iterator().next(); 
				
				RepositoryManagerHibernateUtil.getInstancia().currentSession().evict(opvelho);

				Fachada.getInstancia().excluirOperacao(opvelho);	
			
//
//				Fachada.getInstancia().atualizaOperacao(operacao);
//			} else {
//				Fachada.getInstancia().inserirOperacaoES(operacao);
			}

			Fachada.getInstancia().inserirOperacaoES(operacao);

			ObjectOutputStream ouptu = new ObjectOutputStream(response.getOutputStream());
			ouptu.writeObject("OK");
			ouptu.close();


		} catch (Exception e) {
			e.printStackTrace();
			ObjectOutputStream out = new ObjectOutputStream(response.getOutputStream());
			out.writeObject(e);// 
			out.close();

		}
	}
}