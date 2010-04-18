package com.infinity.datamarket.enterprise.gui.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URLConnection;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Hibernate;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.operacao.ConstantesOperacao;
import com.infinity.datamarket.comum.operacao.Operacao;
import com.infinity.datamarket.comum.operacao.OperacaoPedido;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;

public class ConsultarOperacoesServlet extends HttpServlet {

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			
			InputStream obj = request.getInputStream();
			ObjectInputStream input = new ObjectInputStream(obj);
			Object object = input.readObject();
			String status = (String) (object + "");
			input.close();

			
			PropertyFilter filter = new PropertyFilter();
			filter.setTheClass(OperacaoPedido.class);
			filter.addProperty("tipo", ConstantesOperacao.OPERACAO_PEDIDO);
			filter.addProperty("status",  Integer.parseInt(status));
			Collection col = Fachada.getInstancia().consultarOperacao(filter);
			for (Iterator iter = col.iterator(); iter.hasNext();) {
				OperacaoPedido element = (OperacaoPedido) iter.next();
				Hibernate.initialize(element.getEventosOperacao());
			}
			ObjectOutputStream ouptu = new ObjectOutputStream(response.getOutputStream());
			ouptu.writeObject(col);
			ouptu.close();

		} catch (Exception e) {
			e.printStackTrace();
			ObjectOutputStream out = new ObjectOutputStream(response.getOutputStream());
			out.writeObject(e);// 
			out.close();

		}
	}
}