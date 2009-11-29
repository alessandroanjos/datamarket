package com.infinity.datamarket.enterprise.gui.servlets;

import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.infinity.datamarket.comum.util.Util;

/**
 * Apaga a nova cargabase quando for consumida
 * 
 * @author procenge
 */
public class ApagarNovaCargaBaseServlet extends HttpServlet {

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {

			String idLoja = request.getParameter("idLoja");
			String idComponente = request.getParameter("idComponente");
			Long codigoLoja = new Long(idLoja);
			Long codigoComponente =  new Long(idComponente);
			
			String diretorioCargaBase = Util.getDirDestinoCargaBaseLojaComponente(codigoLoja, codigoComponente);

			Util.apagarArquivos(diretorioCargaBase);
			
			ObjectOutputStream out = new ObjectOutputStream(response.getOutputStream());
			out.writeObject("OK");// 
			out.close();

		} catch (Exception e) {
			ObjectOutputStream out = new ObjectOutputStream(response.getOutputStream());
			out.writeObject(e);// 
			out.close();

		}
	}
}