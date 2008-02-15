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
	<h:outputText styleClass="label" id="titulo" value="Home > Imposto"></h:outputText>
	</div>
	<br>
	<div>
		<h:messages styleClass="errors" globalOnly="true" showDetail="true"/>
	</div>
	<h:form>
		<h:panelGrid columns="3" id="formGrid">
		    <f:facet name="header">
				<h:outputText styleClass="tituloTabela"  value="Manter Imposto" />
			</f:facet>
		    <h:outputText styleClass="label" value="Codigo*"></h:outputText>					
			<h:inputText styleClass="inputText" id="id" maxlength="2"
				value="#{impostoBB.id}" readonly="true"  required="true" size="2">
				<f:validateLength maximum="2" minimum="1"/>
				<f:validator validatorId="LongValidator"/>
			</h:inputText>
			<h:message for="id" styleClass="errors"/>
			<h:outputText styleClass="label" value="Descrição*"></h:outputText>
			<h:inputText styleClass="inputText" id="descricao" maxlength="50" size="50"
				value="#{impostoBB.descricao}" required="true">
				<f:validateLength maximum="50" />
			</h:inputText>
			<h:message for="descricao" styleClass="errors"/>
			<h:outputText styleClass="label" value="Imposto Impressora*"></h:outputText>
			<h:inputText styleClass="inputText" id="impostoImpressora" maxlength="2" size="2"
				value="#{impostoBB.impostoImpressora}" required="true">
				<f:validateLength maximum="2" />
			</h:inputText>
			<h:message for="impostoImpressora" styleClass="errors"/>
			<h:outputText styleClass="label" value="Percentual*"></h:outputText>
			<h:inputText styleClass="inputText" id="percentual" maxlength="5" size="5"
				value="#{impostoBB.percentual}" required="true">
				<f:validateLength maximum="5" />
				<f:validateDoubleRange minimum="00.01" maximum="99.99"/>
			</h:inputText>
			<h:message for="percentual" styleClass="errors"/>
			</h:panelGrid>
			<br>
			<h:panelGrid columns="3" id="formGrid1">
				<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" styleClass="inputBtn" id="botaoVoltar" immediate="true" action="#{impostoBB.voltarConsulta}" value="Voltar"></h:commandButton>
				<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" styleClass="inputBtn" id="botaoAlterar" action="#{impostoBB.alterar}" value="Alterar"></h:commandButton>
				<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" styleClass="inputBtn" id="botaoExcluir" action="#{impostoBB.excluir}" value="Excluir"></h:commandButton>
			</h:panelGrid>
		
	</h:form>
	
</body>
</html>
</f:view>