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
	
</head>
  
<body>
	<div>
		<h:messages styleClass="errors" globalOnly="true" showDetail="true"/>
	</div>
	<h:form>
		<h:panelGrid border="0" cellspacing="0" cellpadding="0" align="center" columns="3" id="formGrid">
			<f:facet name="header">
				<h:outputText styleClass="tituloTabela" value="Login" />
			</f:facet>
			<h:outputText styleClass="label" value="ID*"></h:outputText>					
			<h:inputText styleClass="inputText" id="id" maxlength="6"
				value="#{loginBB.id}" required="true">
				<f:validateLength maximum="6" />
				<f:validator validatorId="LongValidator"/>
			</h:inputText>
			<h:message for="id" styleClass="errors"/>
			<h:outputText styleClass="label" value="Senha*"></h:outputText>
			<h:inputSecret styleClass="inputText" id="senha" redisplay="true" maxlength="6"
				value="#{loginBB.senha}" required="true">
				<f:validateLength maximum="10" />
				<f:validator validatorId="LongValidator"/>
			</h:inputSecret>
			<h:message for="senha" styleClass="errors"/>
		</h:panelGrid>
		<h:panelGrid columns="2" align="center">
			<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" styleClass="inputBtn" id="botaoLogin" action="#{loginBB.logar}" value="Login"></h:commandButton>
			<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" styleClass="inputBtn" id="botaoLimpar" type="reset" value="Limpar"></h:commandButton>
		</h:panelGrid>
	</h:form>
	
</body>
</html>
</f:view>