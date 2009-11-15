package com.infinity.datamarket.enterprise.gui.servlets;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.infinity.datamarket.lote.LoteServer;

public class TransmissorLoteServlet extends HttpServlet {

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String numeroLote = request.getParameter("numeroLote");
			int numoLote = Integer.parseInt(numeroLote);

			String numeroLoja = request.getParameter("idLoja");
			int idLoja = Integer.parseInt(numeroLoja);

			Collection coll = new LoteServer().getLote(numoLote,idLoja);

			ObjectOutputStream ouptu = new ObjectOutputStream(response.getOutputStream());
			ouptu.writeObject(coll);
			ouptu.close();

		} catch (Exception e) {
			ObjectOutputStream out = new ObjectOutputStream(response.getOutputStream());
			out.writeObject(e);// 
			out.close();

		}
	}
}