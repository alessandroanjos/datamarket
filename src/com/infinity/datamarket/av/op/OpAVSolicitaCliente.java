package com.infinity.datamarket.av.op;

import java.util.Collection;

import com.infinity.datamarket.av.gui.telas.TelaAVInicial;
import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.cliente.Cliente;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;

public class OpAVSolicitaCliente extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		while (true) {
				try{
				gerenciadorPerifericos.getDisplay().setMensagem("CPF/CNPJ Cliente - 41850351449");
				EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 14);
				String cpfCnpj = null;
				if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
					cpfCnpj = entrada.getDado();
					if (cpfCnpj.equals("")) {
						TelaAVInicial tela = (TelaAVInicial) gerenciadorPerifericos.getCmos().ler(CMOS.TELA_ATUAL);
						tela.setCampoCliente("Sem Cliente");
						gerenciadorPerifericos.atualizaTela(tela);
						
						gerenciadorPerifericos.getDisplay().setMensagem("Codigo do Produto");
						return ALTERNATIVA_1;
					}
					
					try {
						if (new Integer(cpfCnpj).intValue() == 0) {
							TelaAVInicial tela = (TelaAVInicial) gerenciadorPerifericos.getCmos().ler(CMOS.TELA_ATUAL);
							tela.setCampoCliente("Sem Cliente");
							gerenciadorPerifericos.atualizaTela(tela);
							gerenciadorPerifericos.getDisplay().setMensagem("Codigo do Produto");
							return ALTERNATIVA_1;
						}
					} catch (Exception e) {}
				} else if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_VOLTA){
					return ALTERNATIVA_2;
				}

				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(Cliente.class);
				filter.addProperty("cpfCnpj", cpfCnpj); // 41850351449
				Collection clientes = null;
				try {
					clientes = Fachada.getInstancia().consultarCliente(filter);
				} catch (AppException e) {
					
					gerenciadorPerifericos.getDisplay().setMensagem("Cliente Invalido [VOLAT]");
					gerenciadorPerifericos.esperaVolta();
					continue;
				}
				if (clientes == null || clientes.size() == 0){
					gerenciadorPerifericos.getDisplay().setMensagem("Cliente Invalido [VOLAT]");
					gerenciadorPerifericos.esperaVolta();
					continue;
				}
				Cliente cli = (Cliente) clientes.iterator().next();

				TelaAVInicial tela = (TelaAVInicial) gerenciadorPerifericos.getCmos().ler(CMOS.TELA_ATUAL);
				tela.setCampoCliente(cli.getNomeCliente());
				gerenciadorPerifericos.atualizaTela(tela);

				gerenciadorPerifericos.getCmos().gravar(CMOS.CLIENTE_AV, cli);

				gerenciadorPerifericos.getDisplay().setMensagem("Codigo do Produto");

				return ALTERNATIVA_1;

			}catch(Exception e){
				
				gerenciadorPerifericos.getDisplay().setMensagem("Cliente Invalido [VOLAT]");
				try {
					gerenciadorPerifericos.esperaVolta();	
				} catch (Exception ee) {
					// TODO: handle exception
				}
				continue;
			}
		}
	}
}
