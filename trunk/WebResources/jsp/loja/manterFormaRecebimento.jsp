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
	<h:outputText styleClass="label" id="titulo" value="Home > Forma de Recebimento"></h:outputText>
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
		    <h:outputText styleClass="label" value="Código"></h:outputText>					
			<h:inputText styleClass="inputText" id="id" maxlength="2"
				value="#{formaRecebimentoBB.id}" size="3">
				<f:validateLength maximum="2" />
				<f:validator validatorId="LongValidator"/>
			</h:inputText>
			<h:message for="id" styleClass="errors"/>
			<h:outputText styleClass="label" value="Descrição"></h:outputText>
			<h:inputText styleClass="inputText" id="descricao" maxlength="50" size="50"
				value="#{formaRecebimentoBB.descricao}">
				<f:validateLength maximum="50" />
			</h:inputText>
			<h:message for="descricao" styleClass="errors"/>
			<h:outputText styleClass="label" value="Recebimento na Impressora"></h:outputText>
			<h:inputText styleClass="inputText" id="recebimentoImpressora" maxlength="50" size="50"
				value="#{formaRecebimentoBB.recebimentoImpressora}">
				<f:validateLength maximum="50" />
			</h:inputText>
			<h:message for="recebimentoImpressora" styleClass="errors"/>
			<h:outputText styleClass="label" value="Abre Gaveta"></h:outputText>
			<h:selectOneRadio  styleClass="selectOneRadio" id="abrirGaveta" 
				value="#{formaRecebimentoBB.abrirGaveta}" layout="lineDirection">
			    <f:selectItem itemLabel="Sim" itemValue="S"/>
			    <f:selectItem itemLabel="Não" itemValue="N"/>
			</h:selectOneRadio>
			<h:message for="abrirGaveta" styleClass="errors"/>
			<h:outputText styleClass="label" value="Valor Limite Sangria"></h:outputText>
			<h:inputText styleClass="inputText" id="valorLimiteSangria" maxlength="15" size="15"
				value="#{formaRecebimentoBB.valorLimiteSangria}">
				<f:validateLength maximum="15" />
			</h:inputText>
			<h:message for="valorLimiteSangria" styleClass="errors"/>
			<h:outputText styleClass="label" value="Inicio Validade"></h:outputText>
			<h:inputText styleClass="inputText" id="dataInicioValidade" 
				value="#{formaRecebimentoBB.dataInicioValidade}">
				<f:convertDateTime type="DATE" pattern="MM/dd/yyyy" />
			</h:inputText>
			<h:message for="dataInicioValidade" styleClass="errors"/>
			<h:outputText styleClass="label" value="Final Validade"></h:outputText>
			<h:inputText styleClass="inputText" id="dataFimValidade" 
				value="#{formaRecebimentoBB.dataFimValidade}" >
				<f:convertDateTime type="DATE" pattern="MM/dd/yyyy" />
			</h:inputText>
			<h:message for="dataFimValidade" styleClass="errors"/>
			<h:outputText styleClass="label" value="Valor Maximo Troco"></h:outputText>
			<h:inputText styleClass="inputText" id="valorMaxTroco" maxlength="15" size="15"
				value="#{formaRecebimentoBB.valorMaxTroco}">
				<f:validateLength maximum="15" />
			</h:inputText>
			<h:message for="valorMaxTroco" styleClass="errors"/>
			
		</h:panelGrid>
			<br>
			<h:panelGrid columns="3" id="formGrid1">
				<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" styleClass="inputBtn" id="botaoVoltar" immediate="true" action="#{formaRecebimentoBB.voltarConsulta}" value="Voltar"></h:commandButton>
				<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" styleClass="inputBtn" id="botaoAlterar" action="#{formaRecebimentoBB.alterar}" value="Alterar"></h:commandButton>
				<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" styleClass="inputBtn" id="botaoExcluir" action="#{formaRecebimentoBB.excluir}" value="Excluir"></h:commandButton>
			</h:panelGrid>
		
	</h:form>
	
</body>
</html>
</f:view>