package com.infinity.datamarket.enterprise.gui.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Hibernate;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.operacao.Operacao;
import com.infinity.datamarket.comum.operacao.OperacaoDevolucao;
import com.infinity.datamarket.comum.operacao.OperacaoPK;
import com.infinity.datamarket.comum.operacao.OperacaoPedido;

public class ConsultarOperacaoServlet extends HttpServlet {

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			InputStream obj = request.getInputStream();
			ObjectInputStream input = new ObjectInputStream(obj);
			Object object = input.readObject();
			OperacaoPK info = (OperacaoPK) object;
			input.close();
			
			Operacao operacao = Fachada.getInstancia().consultarOperacaoPorPK(info);
			if (operacao instanceof OperacaoPedido){
				Hibernate.initialize(((OperacaoPedido)operacao).getEventosOperacao());
			}else if (operacao instanceof OperacaoDevolucao){
				Hibernate.initialize(((OperacaoPedido)operacao).getEventosOperacao());
			}
			
			ObjectOutputStream ouptu = new ObjectOutputStream(response.getOutputStream());
			ouptu.writeObject(operacao);
			ouptu.close();

		} catch (Exception e) {
			ObjectOutputStream out = new ObjectOutputStream(response.getOutputStream());
			out.writeObject(e);// 
			out.close();

		}
	}
}