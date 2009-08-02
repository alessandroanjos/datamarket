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
		<script type="text/javascript" src="/EnterpriseServer/js/funcoes.js"></script>
		
		<t:stylesheet path="/css/default.css"></t:stylesheet>
		<t:stylesheet path="/css/form.css"></t:stylesheet>
	</head>
	<body onload="exibirMensagemErro();">
	<div id="outer">
		<div id="topoGeral">
			<div id="tituloPaginaGeral">
				<strong>
					<h:outputText value="#{msgs.consultarContaCorrente}"></h:outputText>
				</strong>
			</div>				
		</div>		

		<h:form id="frmConsultarContaCorrente" binding="#{contaCorrenteBB.init}">
				
				<div id="content">
				
						<div id="primarioContentContainer">
							<fieldset>
								<legend>Opções de filtro:</legend>
								<ul>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Loja"></h:outputLabel>
											<h:selectOneMenu id="idLoja" style="width: 200px;" value="#{contaCorrenteBB.idLoja}"> 
												<f:selectItems id="lojasSelectItems" value="#{contaCorrenteBB.lojas}" />   
											</h:selectOneMenu>
										</div>	
										<br />
										<br />
										<div>
											<h:outputLabel styleClass="desc" value="Banco"></h:outputLabel>
											<h:selectOneMenu id="idBanco" styleClass="field select"
												value="#{contaCorrenteBB.idBanco}" style="width: 200px;">		
												<f:selectItems id="bancoSelectItems" value="#{contaCorrenteBB.bancos}" />
											</h:selectOneMenu>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Agência"></h:outputLabel>
											<h:inputText styleClass="field text" id="idAgenciaConsulta"
												maxlength="10" size="10" required="false"
												value="#{contaCorrenteBB.idAgenciaConsulta}">
												<f:validateLength maximum="10" />
											</h:inputText>

										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Numero"></h:outputLabel>
											<h:inputText styleClass="field text" id="numeroConsulta"
												maxlength="10" size="10" required="false"
												value="#{contaCorrenteBB.numeroConsulta}">
												<f:validateLength maximum="10" />
											</h:inputText>

										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Nome"></h:outputLabel>
											<h:inputText styleClass="field text" id="nomeConsulta" maxlength="20"
												size="20" required="false" value="#{contaCorrenteBB.nomeConsulta}">
												<f:validateLength maximum="20" />
											</h:inputText>

										</div>
									</li>
								</ul>
							</fieldset>	
							<div class="listagem">
								<t:dataTable id="componentes" value="#{contaCorrenteBB.contaCorrentes}"
									var="contaCorrente" rowClasses="rowA,rowB" width="90%" renderedIfEmpty="false">
									<h:column>
										<f:facet name="header">
											<h:outputText value="Código" /> 
										</f:facet>
										<h:commandLink value="#{contaCorrente.id}" action="#{contaCorrenteBB.consultar}">
											<f:param name="id" value="#{contaCorrente.id}"/>						
										</h:commandLink>										 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Banco" /> 
										</f:facet>
										<h:outputText value="#{contaCorrente.banco.descricao}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Agência" /> 
										</f:facet>
										<h:commandLink value="#{contaCorrente.idAgencia}" action="#{contaCorrenteBB.consultar}">
											<f:param name="id" value="#{contaCorrente.id}"/>						
										</h:commandLink>
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Numero" /> 
										</f:facet>
										<h:commandLink value="#{contaCorrente.numero}" action="#{contaCorrenteBB.consultar}">
											<f:param name="id" value="#{contaCorrente.id}"/>						
										</h:commandLink>
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Nome" />
										</f:facet>
										<h:commandLink value="#{contaCorrente.nome}" action="#{contaCorrenteBB.consultar}">
											<f:param name="id" value="#{contaCorrente.id}"/>						
										</h:commandLink>
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Saldo" /> 
										</f:facet>
										<h:commandLink value="#{contaCorrente.saldo}" action="#{contaCorrenteBB.consultar}">
											<f:param name="id" value="#{contaCorrente.id}"/>						
										</h:commandLink>										
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Ativa" /> 
										</f:facet>
										<h:commandLink value="#{contaCorrente.situacao}" action="#{contaCorrenteBB.consultar}">
											<f:param name="id" value="#{contaCorrente.id}"/>						
										</h:commandLink>
									</h:column>

								</t:dataTable>	
										<div>
											<%@ include file="/jsp/mensagem_erro.jsp"%> <!--  h  messages rendered="#{not contaCorrenteBB.existeRegistros}" errorClass="msgSistemaErro" infoClass="msgSistemaSucesso" globalOnly="true" showDetail="true" /> -->
										</div>
							</div>
							<ul>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" action="#{contaCorrenteBB.resetBB}" id="botaoLimpar" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoConsultar" action="#{contaCorrenteBB.consultar}" value="Consultar"></h:commandButton>
								</li>						
							</ul>
						</div>
						<div class="clear"></div>
					</div>
		</h:form>
	  </body>	
	</f:view>
</html>