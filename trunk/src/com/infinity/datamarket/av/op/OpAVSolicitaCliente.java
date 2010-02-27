package com.infinity.datamarket.av.op;

import java.util.Collection;

import com.infinity.datamarket.av.gui.telas.TelaAVInicial;
import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.cliente.Cliente;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.transacao.ClienteTransacao;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ConcentradorParametro;
import com.infinity.datamarket.comum.util.Parametro;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.gui.telas.swing.TelaCadastroClientePDV;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;
import com.infinity.datamarket.pdv.op.OpSolicitaCliente;
import com.infinity.datamarket.pdv.util.MensagensAV;

public class OpAVSolicitaCliente extends OpSolicitaCliente {
	
public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		
		Parametro parametro = ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.SOLICITA_CLIENTE);
		if (parametro != null && parametro != null && Boolean.valueOf(parametro.getValor()).booleanValue()){
			try{
				gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Cadastrar Cliente?"));
				EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 0);
				String idUsu = null;
				if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){								
					gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Aguarde..."));
					TelaCadastroClientePDV tela = TelaCadastroClientePDV.iniciar(gerenciadorPerifericos.getWindow().getFrame());
					if (TelaCadastroClientePDV.retorno == TelaCadastroClientePDV.OK){
						ClienteTransacao cliente = tela.getClienteTransacao();
						
						gerenciadorPerifericos.getCmos().gravar(CMOS.CLIENTE_AV, cliente);
						
						TelaAVInicial telaAV = (TelaAVInicial) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_AV_INICIAL);
						telaAV.setCampoCliente(cliente.getNomeCliente());
						gerenciadorPerifericos.atualizaTela(telaAV);
						
						gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Codigo do Produto"));
						//
						return ALTERNATIVA_1;
					} else {
						TelaAVInicial telaAV = (TelaAVInicial) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_AV_INICIAL);
						telaAV.setCampoCliente("Sem Cliente");
						gerenciadorPerifericos.atualizaTela(telaAV);
						 
						gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Codigo do Produto"));
						return ALTERNATIVA_1;
					}
					
				} else {
					TelaAVInicial telaAV = (TelaAVInicial) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_AV_INICIAL);
					telaAV.setCampoCliente("Sem Cliente");
					gerenciadorPerifericos.atualizaTela(telaAV);
					
					gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Codigo do Produto"));
					return ALTERNATIVA_1;
				}
				
			} catch (AppException e) {
				e.printStackTrace();
			}
		}
		return ALTERNATIVA_1;
	}

//
//	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
//
//		while (true) {
//				try{
//				gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "CPF/CNPJ Cliente");
//				EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER},Display.MASCARA_NUMERICA, 14);
//				String cpfCnpj = null;
//				if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
//					cpfCnpj = entrada.getDado();
//					if (cpfCnpj.equals("")) {
//						TelaAVInicial tela = (TelaAVInicial) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_AV_INICIAL);
//						tela.setCampoCliente("Sem Cliente");
//						gerenciadorPerifericos.atualizaTela(tela);
//						
//						gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Codigo do Produto");
//						return ALTERNATIVA_1;
//					}
//					
//					try {
//						if (new Integer(cpfCnpj).intValue() == 0) {
//							TelaAVInicial tela = (TelaAVInicial) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_AV_INICIAL);
//							tela.setCampoCliente("Sem Cliente");
//							gerenciadorPerifericos.atualizaTela(tela);
//							gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Codigo do Produto");
//							return ALTERNATIVA_1;
//						}
//					} catch (Exception e) {}
//				} //else if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_VOLTA){
//					//return ALTERNATIVA_2;
//				//}
//
//				PropertyFilter filter = new PropertyFilter();
//				filter.setTheClass(Cliente.class);
//				filter.addProperty("cpfCnpj", cpfCnpj); // 41850351449
//				Collection clientes = null;
//				try {
//					clientes = Fachada.getInstancia().consultarCliente(filter);
//				} catch (AppException e) {
//					
//					gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Cliente Invalido [VOLAT]");
//					gerenciadorPerifericos.esperaVolta();
//					continue;
//				}
//				if (clientes == null || clientes.size() == 0){
//					gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Cliente Invalido [VOLAT]");
//					gerenciadorPerifericos.esperaVolta();
//					continue;
//				}
//				Cliente cli = (Cliente) clientes.iterator().next();
//
//				TelaAVInicial tela = (TelaAVInicial) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_AV_INICIAL);
//				tela.setCampoCliente(cli.getNomeCliente());
//				gerenciadorPerifericos.atualizaTela(tela);
//
//				gerenciadorPerifericos.getCmos().gravar(CMOS.CLIENTE_AV, cli);
//
//				gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Codigo do Produto");
//
//				return ALTERNATIVA_1;
//
//			}catch(Exception e){
//				
//				gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Cliente Invalido [VOLAT]");
//				try {
//					gerenciadorPerifericos.esperaVolta();	
//				} catch (Exception ee) {
//					// TODO: handle exception
//				}
//				continue;
//			}
//		}
//	}
}
