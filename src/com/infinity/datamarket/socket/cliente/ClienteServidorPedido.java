package com.infinity.datamarket.socket.cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.infinity.datamarket.pdv.util.ServerConfig;

public class ClienteServidorPedido {

	public static void main(String[] a) {
		try {
			//System.out.println(getProximoNumero(1l, 1l));
			System.out.println(servidorAtivo());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static boolean servidorAtivo() throws Exception{
		try {
			int porta = Integer.parseInt(ServerConfig.PORTA_SERVIDOR_PEDIDO);
			String ip = ServerConfig.IP_SERVIDOR_PEDIDO;
				
			Socket socket = new Socket(ip, porta);
			socket.close();
		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			return false;
		}

		return true;
		
	}


	public static Long getProximoNumero(long loja, long componente) throws Exception {

		Long retorno = 0l;

		try {
			int porta = Integer.parseInt(ServerConfig.PORTA_SERVIDOR_PEDIDO);
			String ip = ServerConfig.IP_SERVIDOR_PEDIDO;
			
			Socket Socket = new Socket(ip, porta);

			Long[] codigos = {loja, componente};
			
			ObjectOutputStream ouptu = new ObjectOutputStream(Socket.getOutputStream());
			ouptu.writeObject(codigos);
			
			ObjectInputStream input = new ObjectInputStream(Socket.getInputStream());
			Object object = input.readObject();
			input.close();
			ouptu.close();

			retorno = Long.parseLong(object + "");
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.err.println("Host não encontrado");
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Host não encontrado");
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}

		return retorno;
	}
}
