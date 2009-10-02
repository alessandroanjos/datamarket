package com.infinity.datamarket.av.op;

import java.util.Collection;

import com.infinity.datamarket.av.gui.telas.TelaAVInicial;
import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.cliente.Cliente;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;

public class OpAVSolicitaCliente extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		try{

			while (true) {
				gerenciadorPerifericos.getDisplay().setMensagem("CPF/CNPJ Cliente - 41850351449");
				EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 14);
				String cpfCnpj = null;
				if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
					cpfCnpj = entrada.getDado();
					if (cpfCnpj.equals("")) {
						return ALTERNATIVA_1;
					}
					
					try {
						if (new Integer(cpfCnpj).intValue() == 0) {
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
					gerenciadorPerifericos.getDisplay().setMensagem("ERRO");
				}
				if (clientes == null || clientes.size() == 0){
					gerenciadorPerifericos.getDisplay().setMensagem("Cliente Invalido");
				}
				Cliente cli = (Cliente) clientes.iterator().next();

				TelaAVInicial tela = (TelaAVInicial) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_AV_INICIAL);
				tela.setCampoCliente(cli.getNomeCliente());
				gerenciadorPerifericos.atualizaTela(tela);

				gerenciadorPerifericos.getCmos().gravar(CMOS.CLIENTE_AV, cli);

				return ALTERNATIVA_1;
			}

		}catch(Exception e){
			gerenciadorPerifericos.getDisplay().setMensagem("Erro");
			try{
				gerenciadorPerifericos.esperaVolta();
			}catch(AppException ex){

			}
			return ALTERNATIVA_2;
		}
	}
}
