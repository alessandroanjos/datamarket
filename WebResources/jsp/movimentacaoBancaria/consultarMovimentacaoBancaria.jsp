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
	<body>
	<div id="outer">
		<div id="topoGeral">
			<div id="tituloPaginaGeral">
				<strong>
					<h:outputText value="#{msgs.consultarMovimentacaoBancaria}"></h:outputText>
				</strong>
			</div>				
		</div>		

		<h:form id="frmConsultarMovimentacaoBancaria" binding="#{movimentacaoBancariaBB.init}">
				
				<div id="content">
				
						<div id="primarioContentContainer">
							<fieldset>
								<legend>Opções de filtro:</legend>
								<ul>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Conta*"></h:outputLabel>
											<h:selectOneMenu id="idContaConsulta" styleClass="field select"
												value="#{movimentacaoBancariaBB.idContaConsulta}"
												style="width: 400px;">
												<f:selectItems id="contasSelectItems"
													value="#{movimentacaoBancariaBB.contas}" />
											</h:selectOneMenu>
											<h:message for="idContaConsulta" styleClass="msgErro" />
										</div>
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Periodo"></h:outputLabel>
											<h:inputText readonly="false" maxlength="10" size="10"
												styleClass="field text data"
												value="#{movimentacaoBancariaBB.dataInicio}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }"
												id="dataInicio">
												<f:convertDateTime timeZone="GMT-3"/>
											</h:inputText>
										</div>
										<div>	
											<h:inputText readonly="false" maxlength="10" size="10"
												styleClass="field text data"
												value="#{movimentacaoBancariaBB.dataFinal}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }"
												id="dataFinal">
												<f:convertDateTime timeZone="GMT-3"/>
											</h:inputText>
										</div>
									</li>
								</ul>
							</fieldset>	
							<div class="listagem">
								<t:dataTable id="componentes" value="#{movimentacaoBancariaBB.movimentacaoBancarias}"
									var="movimentacaoBancaria" rowClasses="rowA,rowB" width="90%" renderedIfEmpty="false">
									<h:column>
										<f:facet name="header">
											<h:outputText value="Código" /> 
										</f:facet>
										<h:commandLink value="#{movimentacaoBancaria.id}" action="#{movimentacaoBancariaBB.consultar}">
											<f:param name="id" value="#{movimentacaoBancaria.id}"/>						
										</h:commandLink>										 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Tipo" /> 
										</f:facet>
										<h:outputText value="#{movimentacaoBancaria.tipo}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Numero" /> 
										</f:facet>
										<h:commandLink value="#{movimentacaoBancaria.numero}" action="#{movimentacaoBancariaBB.consultar}">
											<f:param name="id" value="#{movimentacaoBancaria.id}"/>						
										</h:commandLink>
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Data" />
										</f:facet>
										<h:commandLink value="#{movimentacaoBancaria.data}" action="#{movimentacaoBancariaBB.consultar}">
											<f:param name="id" value="#{movimentacaoBancaria.id}"/>						
										</h:commandLink>
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Valor" /> 
										</f:facet>
										<h:commandLink value="#{movimentacaoBancaria.valor}" action="#{movimentacaoBancariaBB.consultar}">
											<f:param name="id" value="#{movimentacaoBancaria.id}"/>						
										</h:commandLink>
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Forma" /> 
										</f:facet>
										<h:commandLink value="#{movimentacaoBancaria.forma.descricao}" action="#{movimentacaoBancariaBB.consultar}">
											<f:param name="id" value="#{movimentacaoBancaria.id}"/>						
										</h:commandLink>
									</h:column>

								</t:dataTable>	
										<div>
											<h:messages rendered="#{not movimentacaoBancariaBB.existeRegistros}" errorClass="msgSistemaErro" infoClass="msgSistemaSucesso" globalOnly="true" showDetail="true"/>
										</div>
							</div>
							<ul>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" action="#{movimentacaoBancariaBB.resetBB}" id="botaoLimpar" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoConsultar" action="#{movimentacaoBancariaBB.consultar}" value="Consultar"></h:commandButton>
								</li>						
							</ul>
						</div>
						<div class="clear"></div>
					</div>
		</h:form>
	  </body>	
	</f:view>
</html>