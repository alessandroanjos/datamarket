<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="https://ajax4jsf.dev.java.net/ajax" prefix="a4j"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<f:view>
	<f:loadBundle basename="resources.mensagens" var="msgs"/>
	<head>

		<title><h:outputText value="#{msgs.tituloPaginas}"></h:outputText></title>

		<meta http-equiv="pragma" content="no-cache"/><link rel="icon" xhref="favicon.ico" type="image/x-icon" /><link rel="shortcut icon" xhref="favicon.ico" type="image/x-icon" />
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
		
		<script type="text/javascript" src="/EnterpriseServer/js/jquery.js"></script>
		<script type="text/javascript" src="/EnterpriseServer/js/global.js"></script>
		<t:stylesheet path="/css/default.css"></t:stylesheet>
		<t:stylesheet path="/css/form.css"></t:stylesheet>		

	</head>
	<body onload="exibirMensagemErro();">
	<div id="outer">
		<div id="topoGeral">
			<div id="tituloPaginaGeral">
				<strong>
					<h:outputText value="#{msgs.manterAjusteEstoque}"></h:outputText>
				</strong>
			</div>				
		</div>
		<h:form id="frmManterAjusteEstoque">
			<div id="content">
				<div id="primarioContentContainerInternas" >
					<ul>
						<li class="normal">
							<div>
								<%@ include file="/jsp/mensagem_erro.jsp"%> <!--  h  messages errorClass="msgSistemaErro"
									infoClass="msgSistemaSucesso" globalOnly="true"
									showDetail="true" /> -->
							</div>
						</li>
						<li class="normal detalhes">
							<div>
								<h:outputLabel styleClass="desc" value="C�digo:"></h:outputLabel>
								<h:outputText value="#{ajusteEstoqueBB.ajusteEstoque.id}"></h:outputText>
							</div>
						</li>
						<li class="normal detalhes">	
							<div>
								<h:outputLabel styleClass="desc" value="Estoque:"></h:outputLabel>
								<h:outputText value="#{ajusteEstoqueBB.ajusteEstoque.estoque.pk.id}"></h:outputText>
								<h:outputText value="#{ajusteEstoqueBB.ajusteEstoque.estoque.descricao}">- </h:outputText>
							</div>
						</li>
						<li class="normal detalhes">
							<div>
								<h:outputLabel styleClass="desc" value="C�digo Produto:"></h:outputLabel>
								<h:outputText value="#{ajusteEstoqueBB.idProduto}"></h:outputText>
							</div>
						</li>
						<li class="normal detalhes">	
							<div>
								<h:outputLabel styleClass="desc" value="Descri��o:"></h:outputLabel>
								<h:outputText value="#{ajusteEstoqueBB.ajusteEstoque.produto.descricaoCompleta}"></h:outputText>
								
							</div>
							
						</li>
						<li class="normal detalhes">	
							<div>
								<h:outputLabel styleClass="desc" value="Qtd. Antes:"></h:outputLabel>
								<h:outputText value="#{ajusteEstoqueBB.quantidadeAntes}"></h:outputText>								
							</div>
						</li>
						<li class="normal detalhes">								
							<div>
								<h:outputLabel styleClass="desc" value="Qtd. Depois:"></h:outputLabel>
								<h:outputText value="#{ajusteEstoqueBB.quantidadeDepois}"></h:outputText>								
							</div>
						</li>	

						<li class="buttons">
							<h:commandButton styleClass="btTxt" id="botaoVoltar"
								action="#{ajusteEstoqueBB.voltarConsulta}" value="Voltar"></h:commandButton>
						</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			
		</h:form>
	 </body>			
	</f:view>
</html>
