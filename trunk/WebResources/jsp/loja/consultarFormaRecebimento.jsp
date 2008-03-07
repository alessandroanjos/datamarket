<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="https://ajax4jsf.dev.java.net/ajax" prefix="a4j"%>

 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<f:view>
<t:stylesheet path="/css/style.css"></t:stylesheet>
<html>
<head>

	<title>INFINITY - DataMarket - Enterprise Server</title>
	
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>
  
<body>
	<div>
	<h:outputText id="titulo" styleClass="label" value="Home > Forma de Recebimento"></h:outputText>
	</div>
	<br>
	<div>
		<h:messages styleClass="errors" globalOnly="true" showDetail="true"/>
	</div>
	
	<h:form>
		<h:panelGrid columns="3" id="formGrid">
		    <f:facet name="header">
				<h:outputText styleClass="tituloTabela" value="Consulta Formas de Recebimentos" />
			</f:facet>
		    <h:outputText styleClass="label" value="Código*"></h:outputText>					
			<h:inputText styleClass="inputText" id="id" maxlength="2"
				value="#{formaRecebimentoBB.id}" size="3">
				<f:validateLength maximum="2" />
				<f:validator validatorId="LongValidator"/>
			</h:inputText>
			<h:message for="id" styleClass="errors"/>
			<h:outputText styleClass="label" value="Descrição*"></h:outputText>
			<h:inputText styleClass="inputText" id="descricao"  maxlength="50" size="50"
				value="#{formaRecebimentoBB.descricao}">
				<f:validateLength maximum="50" />
			</h:inputText>
			<h:message for="descricao" styleClass="errors"/>
			
			<h:message for="valorMaxTroco" styleClass="errors"/>
			
		</h:panelGrid>
		<br>
		
		<h:panelGrid columns="3" id="formGrid1">	
			<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" styleClass="inputBtn" id="botaoVoltar" action="#{formaRecebimentoBB.voltarMenu}" value="Voltar"></h:commandButton>
			<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" immediate="true" styleClass="inputBtn" id="botaoLimpar" type="reset" value="Limpar"></h:commandButton>
			<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" styleClass="inputBtn" id="botaoConsultar" action="#{formaRecebimentoBB.consultar}" value="Consultar"></h:commandButton>
		</h:panelGrid>
		<p>
		<h:dataTable value="#{formaRecebimentoBB.formasRecebimentos}"
					var="forma">
					<f:facet name="header">
						<h:outputText styleClass="tituloTabela" value="Formas de Recebimento" />
					</f:facet>					
					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="label"  value="Código" />
						</f:facet>
						<h:outputText styleClass="label" value="#{forma.id}" /> 
					</h:column>
					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="label"  value="Descrição" />
						</f:facet>
						<h:commandLink styleClass="label" value="#{forma.descricao}" action="#{formaRecebimentoBB.consultar}">
							<f:param name="id" value="#{forma.id}"/>						
						</h:commandLink>
					</h:column>
				</h:dataTable>
	</h:form>
	
</body>
</html>
</f:view>