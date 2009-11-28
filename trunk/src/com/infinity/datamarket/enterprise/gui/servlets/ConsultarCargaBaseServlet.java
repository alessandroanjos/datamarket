package com.infinity.datamarket.enterprise.gui.servlets;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.infinity.datamarket.comum.util.Util;

public class ConsultarCargaBaseServlet extends HttpServlet {

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			String idLoja = request.getParameter("idLoja");
			String idComponente = request.getParameter("idComponente");
			Long codigoLoja = new Long(idLoja);
			Long codigoComponente = new Long(0);
			
			if (codigoComponente != null) {
				codigoComponente = new Long(idComponente);
			}
			
			String diretorioCargaBase = Util.getDirDestinoCargaBaseLojaComponente(codigoLoja, codigoComponente);
			if (codigoComponente  == null ) {
				File[] arquivos = new File(Util.getDirDestinoCargaBaseLoja(codigoLoja)).listFiles();
				if (arquivos != null && arquivos.length >0 ) {
					for (int i = 0; i < arquivos.length; i++) {
						if (arquivos[i].isDirectory() && arquivos[i].listFiles() != null &&  arquivos[i].listFiles().length > 0 ) {
							diretorioCargaBase = arquivos[i].toString();
						}
					}
				} 
			}
			
			File f = new File(diretorioCargaBase);
			if (f.exists()) {
				File[] arquivos = f.listFiles();
				if (arquivos != null && arquivos.length >0 ) {
					response.setContentType("application/zip"); 
					if (idComponente != null) {
						response.setHeader("Content-Disposition","attachment; filename=cargaBase_Loja_" + idLoja + "_Componente_" + idComponente + "_.zip;"); 
					} else {
						response.setHeader("Content-Disposition","attachment; filename=cargaBase_Loja_" + idLoja + "_.zip;"); 				
					}
					ObjectOutputStream ouptu = new ObjectOutputStream(response.getOutputStream());
					ouptu.writeObject(Util.zipDir(diretorioCargaBase));
					ouptu.close();
				} else {
					ObjectOutputStream ouptu = new ObjectOutputStream(response.getOutputStream());
					ouptu.writeObject("SEM CARGA DE BASE");
					ouptu.close();
				}
			} else {
				ObjectOutputStream ouptu = new ObjectOutputStream(response.getOutputStream());
				ouptu.writeObject("SEM CARGA DE BASE");
				ouptu.close();
			}
		} catch (Exception e) {
			ObjectOutputStream out = new ObjectOutputStream(response.getOutputStream());
			out.writeObject(e);// 
			out.close();

		}
	}
}