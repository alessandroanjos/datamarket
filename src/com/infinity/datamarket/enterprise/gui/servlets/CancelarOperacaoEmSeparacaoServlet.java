package com.infinity.datamarket.enterprise.gui.servlets;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.operacao.ConstantesOperacao;
import com.infinity.datamarket.comum.operacao.Operacao;
import com.infinity.datamarket.comum.operacao.OperacaoPedido;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;

public class CancelarOperacaoEmSeparacaoServlet extends HttpServlet {

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {

			String loja = request.getParameter("idLoja");
			String idPedido = request.getParameter("idPedido");
    		
			PropertyFilter filter = new PropertyFilter();
			filter.setTheClass(Operacao.class);
			filter.addProperty("pk.id", new Integer(idPedido));
			filter.addProperty("pk.loja", new Integer(loja));

			Collection coll = Fachada.getInstancia().consultarOperacao(filter);

			if (coll != null && coll.size() != 0) {
				OperacaoPedido pedidoJaCadastrado = (OperacaoPedido) coll.iterator().next();
				pedidoJaCadastrado.setStatus(ConstantesOperacao.ABERTO);
				Fachada.getInstancia().atualizaOperacao(pedidoJaCadastrado);	
			}

//			InputStream obj = request.getInputStream();
//			ObjectInputStream input = new ObjectInputStream(obj);
//			Object object = input.readObject();
//			Operacao operacao = (Operacao) object;
//			input.close();
//			
//			operacao.setStatus(ConstantesOperacao.ABERTO);
//			Fachada.getInstancia().inserirOperacaoES(operacao);
//
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