package com.infinity.datamarket.enterprise.gui.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.operacao.OperacaoPK;

public class AlterarOperacaoServlet extends HttpServlet {

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			
			
			InputStream obj = request.getInputStream();
			ObjectInputStream input = new ObjectInputStream(obj);
			Object object = input.readObject();
			OperacaoPK info = (OperacaoPK) object;
			input.close();
			
			String status = request.getParameter("status");
			
			int statusInt = Integer.parseInt(status);
						
			Fachada.getInstancia().alterarStatusOperacao(info, statusInt);
			
			ObjectOutputStream ouptu = new ObjectOutputStream(response.getOutputStream());
			ouptu.writeObject("OK");
			ouptu.close();

		} catch (Exception e) {
			ObjectOutputStream out = new ObjectOutputStream(response.getOutputStream());
			out.writeObject(e);// 
			out.close();

		}
	}
}