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
	<h:outputText styleClass="label" id="titulo" value="Home > Usuário"></h:outputText>
	</div>
	<br>
	<div>
		<h:messages styleClass="errors" globalOnly="true" showDetail="true"/>
	</div>
	<h:form id="form">
		<h:panelGrid columns="3" id="formGrid">
		    <f:facet name="header">
				<h:outputText styleClass="tituloTabela" value="Manter Usuário" />
			</f:facet>
			<h:outputText styleClass="label" value="Codigo*"></h:outputText>					
			<h:inputText styleClass="inputText" id="id" maxlength="2"
				value="#{usuarioBB.id}" size="2" required="true">
				<f:validateLength maximum="2" />
				<f:validator validatorId="LongValidator"/>
			</h:inputText>
			<h:message for="id" styleClass="errors"/>
			<h:outputText styleClass="label" value="Nome*"></h:outputText>
			<h:inputText styleClass="inputText" id="nome" maxlength="50" size="50"
				value="#{usuarioBB.nome}" required="true">
				<f:validateLength maximum="50" />
			</h:inputText>			
			<h:message for="nome" styleClass="errors"/>
			<h:outputText styleClass="label" value="Senha*"></h:outputText>
			<h:inputSecret styleClass="inputText" id="senha" redisplay="true" maxlength="20" size="25"
				value="#{usuarioBB.senha}" required="true">
				<f:validateLength maximum="20" />
				<f:validator validatorId="LongValidator"/>
			</h:inputSecret>
			<h:message for="senha" styleClass="errors"/>
			<h:outputText styleClass="label" value="Perfil*"></h:outputText>
			<h:selectOneMenu id="perfis" 
				value="#{usuarioBB.idPerfil}" required="true">   
					  <f:selectItems id="perfilSelectItems" 
					  value="#{usuarioBB.perfis}" />   
			</h:selectOneMenu>  
			<h:message for="perfil" styleClass="errors"/>
		</h:panelGrid>
		<br>
		<h:panelGrid columns="5" id="formGrid2" style="width: 400px;">
			<f:facet name="header">
				<h:outputText styleClass="tituloTabela-left" value="Lojas Associadas" />
			</f:facet>			
			<t:div styleClass="div-auto-scroll" style="width: 100%; height: 300px;">
				<h:selectManyCheckbox id="idListaLojasAssociadas" layout="pageDirection" required="true" styleClass="label"
					value="#{usuarioBB.listaLojasAssociadas}" >
						<f:selectItems value="#{usuarioBB.lojas}"/>
				</h:selectManyCheckbox>
			</t:div>		
		</h:panelGrid>
		<br>
			<h:panelGrid columns="3" id="formGrid1">
				<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" styleClass="inputBtn" id="botaoVoltar" immediate="true" action="#{usuarioBB.voltarConsulta}" value="Voltar"></h:commandButton>
				<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" styleClass="inputBtn" id="botaoAlterar" action="#{usuarioBB.alterar}" value="Alterar"></h:commandButton>
				<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" styleClass="inputBtn" id="botaoExcluir" action="#{usuarioBB.excluir}" value="Excluir"></h:commandButton>
			</h:panelGrid>
	</h:form>
	
</body>
</html>
</f:view>