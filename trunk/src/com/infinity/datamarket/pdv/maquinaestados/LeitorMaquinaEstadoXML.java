package com.infinity.datamarket.pdv.maquinaestados;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xerces.dom.DeferredElementImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class LeitorMaquinaEstadoXML {
	
	public static String CONST_maquinaEstado = "maquinaEstado";
	public static String CONST_listaMicroperacao = "listaMicroperacao";
	public static String CONST_listaEstado = "listaEstado";
	public static String CONST_listaMacroOperacao = "listaMacroOperacao";
	public static String CONST_listaTecla = "listaTecla";

	public static String CONST_operacao = "operacao";
	public static String CONST_estado = "estado";
	public static String CONST_tecla = "tecla";
	public static String CONST_saidaMicroOperacaoAssociada = "saidaMicroOperacaoAssociada";
	public static String CONST_microOperacaoAssociada = "microOperacaoAssociada";
	public static String CONST_macroOperacao = "macroOperacao";
	
	public static void main(String[] a ) {
		try {
			lerArquivoXM("fluxoPDV.xml"); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static MaquinaEstado lerArquivoXM(String arquivo) throws Exception {
		Map mapEstado = new HashMap();
		Map mapIDEstado = new HashMap();
		Map mapMicOperacao = new HashMap();
		Map mapTecla = new HashMap();
		Collection mapMacroOperacao = new ArrayList();
		
		try{
			URL url = LeitorMaquinaEstadoXML.class.getClassLoader().getResource(arquivo);
			String arq = url.toString();
			arq = arq.substring("file:\\".length(), arq.length());
			File file = new File(arq);
			DocumentBuilderFactory dbf  = DocumentBuilderFactory.newInstance();
			DocumentBuilder db 			= dbf.newDocumentBuilder();
			Document doc 				= db.parse(file);
			
			Element elem 	  = doc.getDocumentElement();
			String maquinaEstado = elem.getNodeName();
			if (!CONST_maquinaEstado.equals(maquinaEstado)) {
				throw new Exception("Erro ao dar o parse no xml de fluxos");
			}
//			pegando todos os filhos de maquinaEstado
			NodeList nodeList = elem.getChildNodes();
			//System.out.println("tamanho " + nodeList.getLength());

			//pegando o primerio filho
			Node listaMicroperacao = nodeList.item(0).getNextSibling();
			String nomeListaMicroperacao = listaMicroperacao.getNodeName();
			if (!CONST_listaMicroperacao.equals(nomeListaMicroperacao)) {
				throw new Exception("Erro ao dar o parse no xml de fluxos");
			}
			NodeList todasOperacao = listaMicroperacao.getChildNodes();
			for (int i = 0; i < todasOperacao.getLength(); i++) {
				if (todasOperacao.item(i) instanceof DeferredElementImpl) {
					Node operacao = todasOperacao.item(i);	
					String nomeOperacao = operacao.getNodeName();
					if (!CONST_operacao.equals(nomeOperacao)) {
						throw new Exception("Erro ao dar o parse no xml de fluxos");
					}
				
					NamedNodeMap atributosOperacao = operacao.getAttributes();
					String id = atributosOperacao.getNamedItem("id").getNodeValue();
					String classe = atributosOperacao.getNamedItem("classe").getNodeValue();
					
					//System.out.print(id);
					//System.out.print(" ");
					//System.out.println(classe);

					MicroOperacao obj = new MicroOperacao();
					obj.setId(new Long(mapMicOperacao.size() + 1));
					obj.setClasse(classe);
					
					if (mapMicOperacao.containsKey(id)) {
						throw new Exception("Já existe Operação com o código " + id);
					}
					mapMicOperacao.put(id, obj);
				}
			}
			
			Node listaEstado = nodeList.item(3);
			String nomeListaEstado = listaEstado.getNodeName();
			if (!CONST_listaEstado.equals(nomeListaEstado)) {
				throw new Exception("Erro ao dar o parse no xml de fluxos");
			}

			Map estadoTeclaMenu = new HashMap();
			
			NodeList todasEstados = listaEstado.getChildNodes();
			for (int i = 0; i < todasEstados.getLength(); i++) {
				if (todasEstados.item(i) instanceof DeferredElementImpl) {
					Node estado = todasEstados.item(i);	
					String nomeEstado = estado.getNodeName();
					if (!CONST_estado.equals(nomeEstado)) {
						throw new Exception("Erro ao dar o parse no xml de fluxos");
					}

					NamedNodeMap atributosEstados = estado.getAttributes();
					String codigoTeclaMenu = null;
						

					try {
						codigoTeclaMenu = atributosEstados.getNamedItem("teclaMenu").getNodeValue();	
					} catch (Exception e) { }

					String codigo = atributosEstados.getNamedItem("codigo").getNodeValue();
					String id = atributosEstados.getNamedItem("id").getNodeValue();
					String descricao = atributosEstados.getNamedItem("descricao").getNodeValue();
					String inputType = atributosEstados.getNamedItem("inputType").getNodeValue();
					String inputSize = atributosEstados.getNamedItem("inputSize").getNodeValue();

					if (codigoTeclaMenu != null) {
						estadoTeclaMenu.put(new Long(mapEstado.size() + 1),codigoTeclaMenu);
					}

					Estado obj = new Estado();
					obj.setId(new Long(id));
					obj.setDescricao(descricao);
					obj.setInputSize(Integer.parseInt(inputSize));
					obj.setInputType(Integer.parseInt(inputType));
					
					
					
					if (mapIDEstado.containsKey(obj.getId())) {
						throw new Exception("Já existe estado com o id " + codigo);
					}
					if (mapEstado.containsKey(codigo)) {
						throw new Exception("Já existe estado com o código " + codigo);
					}
					mapEstado.put(codigo, obj);
				}
			}
			
			Node listaTecla = nodeList.item(5);
			String nomeListaTecla = listaTecla.getNodeName();
			if (!CONST_listaTecla.equals(nomeListaTecla)) {
				throw new Exception("Erro ao dar o parse no xml de fluxos");
			}
			NodeList todasTeclas = listaTecla.getChildNodes();
			for (int i = 0; i < todasTeclas.getLength(); i++) {
				if (todasTeclas.item(i) instanceof DeferredElementImpl) {
					Node teclas = todasTeclas.item(i);	
					String nomeTeclas = teclas.getNodeName();
					if (!CONST_tecla.equals(nomeTeclas)) {
						throw new Exception("Erro ao dar o parse no xml de fluxos");
					}

					NamedNodeMap atributosTeclas = teclas.getAttributes();
					String codigo = atributosTeclas.getNamedItem("codigo").getNodeValue();
					String descricao = atributosTeclas.getNamedItem("descricao").getNodeValue();
					String codigoASCI = atributosTeclas.getNamedItem("codigoASCI").getNodeValue();

					Tecla obj = new Tecla();
					obj.setDescricao(descricao);
					obj.setId(new Long(mapTecla.size() + 1));
					obj.setCodigoASCI(Integer.parseInt(codigoASCI));

					if (mapMicOperacao.containsKey(codigo)) {
						throw new Exception("Já existe Tecla com o código " + codigo);
					}
					mapTecla.put(codigo, obj);
				}
			}
	
			// adicionado a tecla de menu ao estado
			Iterator it = mapEstado.values().iterator();
			while(it.hasNext()){
				Estado estado = (Estado)it.next();
				if (estadoTeclaMenu.containsKey(estado.getId())) {
					String codigoTeclado = (String)estadoTeclaMenu.get(estado.getId());
					if (codigoTeclado != null) {
						Tecla t = (Tecla)mapTecla.get(codigoTeclado);
						if (t != null) {
							estado.setTeclaMenu(t);
						} else {
							throw new Exception("Não existe a tecla " + codigoTeclado + " para o menu do estado " + estado.getDescricao());
						}
					}
				}
			}
			
			Node listaMacroOperacao = nodeList.item(7);
			String nomeListaMacroOperacao = listaMacroOperacao.getNodeName();
			if (!CONST_listaMacroOperacao.equals(nomeListaMacroOperacao)) {
				throw new Exception("Erro ao dar o parse no xml de fluxos");
			}
			Map idMacro = new HashMap();
			
			NodeList todasMacroOperacoes = listaMacroOperacao.getChildNodes();
			for (int i = 0; i < todasMacroOperacoes.getLength(); i++) {
				if (todasMacroOperacoes.item(i) instanceof DeferredElementImpl) {
					Node macroOperacao = todasMacroOperacoes.item(i);	
					String nomeMacroOperacao = macroOperacao.getNodeName();
					if (!CONST_macroOperacao.equals(nomeMacroOperacao)) {
						throw new Exception("Erro ao dar o parse no xml de fluxos");
					}

					NamedNodeMap atributosMacroOperacao = macroOperacao.getAttributes();
					String codigoEstadoAtual = atributosMacroOperacao.getNamedItem("estadoAtual").getNodeValue();
					String codigoProximoEstado = atributosMacroOperacao.getNamedItem("proximoEstado").getNodeValue();
					String codigoTecla = atributosMacroOperacao.getNamedItem("tecla").getNodeValue();
					String codigoFluxo = atributosMacroOperacao.getNamedItem("codigoMicroOperacaoAssociada").getNodeValue();
					String descricao = atributosMacroOperacao.getNamedItem("descricao").getNodeValue();
					if(atributosMacroOperacao.getNamedItem("id") == null) {
						throw new Exception("Não Existe id do macro " + descricao);
					}
					String id = atributosMacroOperacao.getNamedItem("id").getNodeValue();
					String tipoComponente = atributosMacroOperacao.getNamedItem("tipoComponente").getNodeValue();

					if (idMacro.containsKey(new Integer(id))) {
						String desc = (String)idMacro.get(new Integer(id));
						throw new Exception("Os macros " + desc + " e o " + descricao + " estão utilizando o mesmo id " + id );
					}
					idMacro.put(new Integer(id), descricao);

					if (mapEstado.get(codigoEstadoAtual) == null) {
						throw new Exception("Não Existe o estado " + codigoEstadoAtual);
					}
					if (mapEstado.get(codigoProximoEstado) == null) {
						throw new Exception("Não Existe o estado " + codigoProximoEstado);
					}
					if (mapTecla.get(codigoTecla) == null) {
						throw new Exception("Não Existe a tecla " + codigoTecla);
					}
					Tecla tecla = (Tecla)mapTecla.get(codigoTecla);
					Estado estadoAtual = (Estado)mapEstado.get(codigoEstadoAtual);
					estadoAtual.getFinalizadoras().add(tecla);
					Estado proximoEstado = (Estado)mapEstado.get(codigoProximoEstado);
					MicroOperacaoAssociada operacaoAssociada = getMicroOperacaoAssociada(macroOperacao, codigoFluxo, mapMicOperacao, descricao, new HashMap());
					if (operacaoAssociada == null) {
						throw new Exception("Não foi encontrado fluxo com o código " + codigoFluxo + " da macroperacao " + descricao);
					}

					MacroOperacao obj = new MacroOperacao();
					obj.setDescricao(descricao);
					obj.setEstadoAtual(estadoAtual);
					obj.setId(new Long(id));
					obj.setTipoComponente(Integer.parseInt(tipoComponente));
					obj.setMicroOperacaoInicial(operacaoAssociada);
					obj.setProximoEstado(proximoEstado);
					obj.setTecla(tecla);
					mapMacroOperacao.add(obj);
				}
			}
		} catch (Exception e) {
			throw new Exception(e);
		}

		return new MaquinaEstado(mapEstado.values(), mapMicOperacao.values(), mapTecla.values(), mapMacroOperacao);
	}

	private static MicroOperacaoAssociada getMicroOperacaoAssociada(Node macroOperacao, String codigoFluxoPesquisa,Map mapMicOperacao, String macroperacao, Map mapMicroOperacaoAssociada) throws Exception {
		if(mapMicroOperacaoAssociada.get(codigoFluxoPesquisa) != null){
			return (MicroOperacaoAssociada)mapMicroOperacaoAssociada.get(codigoFluxoPesquisa);
		}

		NodeList todasSaidasMicroOperacaoAssociada = macroOperacao.getChildNodes();
		for (int j = 0; j < todasSaidasMicroOperacaoAssociada.getLength(); j++) {
			if (todasSaidasMicroOperacaoAssociada.item(j) instanceof DeferredElementImpl) {
				Node saidaElementMicroOperacaoAssociada = todasSaidasMicroOperacaoAssociada.item(j);
				String nomeSaidaMicroOperacaoAssociada = saidaElementMicroOperacaoAssociada.getNodeName();
				if (!CONST_microOperacaoAssociada.equals(nomeSaidaMicroOperacaoAssociada)) {
					throw new Exception("Erro ao dar o parse no xml de fluxos");
				}

				NamedNodeMap atributosSaidaMicroOperacaoAssociada = saidaElementMicroOperacaoAssociada.getAttributes();
				String codigoFluxo = atributosSaidaMicroOperacaoAssociada.getNamedItem("codigoMicroOperacaoAssociada").getNodeValue();
				String operacaoFluxo = atributosSaidaMicroOperacaoAssociada.getNamedItem("operacao").getNodeValue();

				//System.out.print(codigoFluxo);
				//System.out.print(" ");
				//System.out.println(operacaoFluxo);
				if (codigoFluxo.equalsIgnoreCase(codigoFluxoPesquisa)) {
					if (mapMicOperacao.get(operacaoFluxo) == null) {
						throw new Exception("Não Existe a operacao " + operacaoFluxo);
					}

					MicroOperacao microOperacao = (MicroOperacao)mapMicOperacao.get(operacaoFluxo); 

					MicroOperacaoAssociada opAss = new MicroOperacaoAssociada();
					opAss.setMicroOperacao(microOperacao);
					opAss.setId(new Long(mapMicroOperacaoAssociada.size() + 1));
					mapMicroOperacaoAssociada.put(codigoFluxo, opAss);

					Collection collSaida = new ArrayList();
					NodeList todasSaidas = saidaElementMicroOperacaoAssociada.getChildNodes();
					for (int i = 0; i < todasSaidas.getLength(); i++) {
						if (todasSaidas.item(i) instanceof DeferredElementImpl) {
							Node saidaElement = todasSaidas.item(i);
							String descricaoSaidaElement = saidaElement.getNodeName();
							if (!CONST_saidaMicroOperacaoAssociada.equals(descricaoSaidaElement)) {
								throw new Exception("Erro ao dar o parse no xml de fluxos");
							}
							
							NamedNodeMap atributosSaida = saidaElement.getAttributes();
							String tipoSaida = atributosSaida.getNamedItem("tipoSaida").getNodeValue();
							String codigoFluxoDestino = atributosSaida.getNamedItem("codigoMicroOperacaoAssociadaDestino").getNodeValue();

							//System.out.print(tipoSaida);
							//System.out.print(" ");
							//System.out.println(codigoFluxoDestino);
							if (codigoFluxo.equalsIgnoreCase(codigoFluxoDestino)) {
								//throw new Exception("A saida " + tipoSaida + " do Fluxo " + codigoFluxo + " está para o mesmo fluxo, fooping infinito, da macroperacao " + macroperacao);
							}
							if (collSaida.contains(tipoSaida.toUpperCase())){
								throw new Exception("duas saidas com o mesmo código para o código do fluxo " + codigoFluxo+ " da macroperacao " + macroperacao);
							}
							collSaida.add(tipoSaida.toUpperCase());
							
							MicroOperacaoAssociada operacaoAssociada = getMicroOperacaoAssociada(macroOperacao, codigoFluxoDestino, mapMicOperacao, macroperacao, mapMicroOperacaoAssociada);
							if (operacaoAssociada == null) {
								throw new Exception("Não foi encontrado fluxo com o código " + codigoFluxoDestino + " da macroperacao " + macroperacao);
							}
							
							SaidaMicroOperacaoAssociada saida = new SaidaMicroOperacaoAssociada();
							saida.setSaida(Integer.parseInt(tipoSaida));
							saida.setMicroOperacaoAssociada(operacaoAssociada);
							saida.setId(new Long(opAss.getSaidas().size() + 1));
							opAss.getSaidas().add(saida);
						}
					}
					return opAss;
				}
			}
		}
		return null;
	}
}