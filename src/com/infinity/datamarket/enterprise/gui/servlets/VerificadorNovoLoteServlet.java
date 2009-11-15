package com.infinity.datamarket.enterprise.gui.servlets;

import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.infinity.datamarket.lote.LoteServer;

public class VerificadorNovoLoteServlet extends HttpServlet {

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String numeroLote = request.getParameter("numeroLote");
			int numoLote = Integer.parseInt(numeroLote);
			
			boolean novoLote = new LoteServer().verificaNovoLoteLiberado(numoLote);

			ObjectOutputStream ouptu = new ObjectOutputStream(response.getOutputStream());
			ouptu.writeObject(new Boolean(novoLote));
			ouptu.close();

		} catch (Exception e) {
			ObjectOutputStream out = new ObjectOutputStream(response.getOutputStream());
			out.writeObject(e);// 
			out.close();

		}
	}
}