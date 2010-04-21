package com.infinity.datamarket.enterprise.gui.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Hibernate;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.operacao.ConstantesOperacao;
import com.infinity.datamarket.comum.operacao.OperacaoPedido;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;

public class ConsultarOperacoesServlet extends HttpServlet {

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			
			Collection col = new ArrayList();

			InputStream obj = request.getInputStream();
			ObjectInputStream input = new ObjectInputStream(obj);
			Object object = input.readObject();
			input.close();
			if (object == null) {
				col.addAll(consultarOperacao(null));
			} else if (object instanceof Integer[] ) {
				Integer[] status = (Integer[]) object;
				for (int i = 0; i < status.length; i++) {
					col.addAll(consultarOperacao(status[i]));
				}
			} else if (object instanceof String[] ) {
				String[] status = (String[]) object;
				for (int i = 0; i < status.length; i++) {
					col.addAll(consultarOperacao(new Integer((status[i]  + ""))));
					
				}
			} else {
				String status = (String) object;
					col.addAll(consultarOperacao(new Integer((status + ""))));
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

	private Collection consultarOperacao(Integer status) throws AppException {
		PropertyFilter filter = new PropertyFilter();
		filter.setTheClass(OperacaoPedido.class);
		filter.addProperty("tipo", ConstantesOperacao.OPERACAO_PEDIDO);
		if (status != null) {
			filter.addProperty("status",  status);
		}
		Collection col = Fachada.getInstancia().consultarOperacao(filter);
		for (Iterator iter = col.iterator(); iter.hasNext();) {
			OperacaoPedido element = (OperacaoPedido) iter.next();
			Hibernate.initialize(element.getEventosOperacao());
		}
		return col;
	}
}