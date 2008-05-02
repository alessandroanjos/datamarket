<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="https://ajax4jsf.dev.java.net/ajax" prefix="a4j"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

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
<f:view>  
<t:stylesheet path="/css/style.css"></t:stylesheet>
<body>
<h:form>
	<div>
	<h:outputText id="titulo" styleClass="label" value="Home > Liberação de Dados"></h:outputText>
	</div>
	<br>
	<div>
		<h:messages styleClass="errors" globalOnly="true" showDetail="true"/>
	</div>
		<h:panelGrid columns="3" id="formGrid">
		    <f:facet name="header">
				<h:outputText styleClass="tituloTabela" value="Liberação de Dados" />
			</f:facet>
			<h:outputText styleClass="label" value="Número da Liberação de Dados Atual"></h:outputText>					
			<h:inputText styleClass="inputText" id="numeroLote" maxlength="8"
				value="#{loteBB.numeroLote}" size="8" readonly="true" required="true">
				<f:validateLength maximum="8" />
				<f:validator validatorId="LongValidator"/>
			</h:inputText>
			<h:message for="numeroLote" styleClass="errors"/>
			<h:outputText styleClass="label" value="Quantidade de Registros para Liberar"></h:outputText>
			<h:inputText styleClass="inputText" id="qtdRegistros" maxlength="8" size="8"
				value="#{loteBB.qtdRegistros}" required="true" readonly="true">
				<f:validateLength maximum="8" />
			</h:inputText>			
			<h:message for="qtdRegistros" styleClass="errors"/>
			
		</h:panelGrid>
		<br>
		<h:panelGrid  columns="3" id="formGrid1">
			<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" immediate="true" styleClass="inputBtn" id="botaoVoltar" action="#{loteBB.voltarMenu}" value="Voltar"></h:commandButton>
			<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" immediate="true" styleClass="inputBtn" id="botaoConsultar" action="#{loteBB.consultarLote}" value="Consultar Próxima Liberação"></h:commandButton>
			<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" styleClass="inputBtn" id="botaoLiberar" action="#{loteBB.liberarLote}" value="Liberar Dados"></h:commandButton>
		</h:panelGrid>
</h:form>	
</body>
</f:view>
</html>