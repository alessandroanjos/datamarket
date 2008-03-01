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
	<h:outputText styleClass="label" id="titulo" value="Home > Produto"></h:outputText>
	</div>
	<br>
	<div>
		<h:messages styleClass="errors" globalOnly="true" showDetail="true"/>
	</div>
	<h:form>
		<h:panelGrid columns="3" id="formGrid">
		    <f:facet name="header">
				<h:outputText styleClass="tituloTabela"  value="Manter Produto" />
			</f:facet>
		    <h:outputText styleClass="label" value="Codigo*"></h:outputText>					
			<h:inputText styleClass="inputText" id="id" maxlength="2"
				value="#{produtoBB.id}" readonly="true"  required="true" size="2">
				<f:validateLength maximum="2" minimum="1"/>
				<f:validator validatorId="LongValidator"/>
			</h:inputText>
			<h:message for="id" styleClass="errors"/>
			<h:outputText styleClass="label" value="Descrição completa*"></h:outputText>
			<h:inputText styleClass="inputText" id="descricaoCompleta" maxlength="50" size="50"
				value="#{produtoBB.descricaoCompleta}" required="true">
				<f:validateLength maximum="50" />
			</h:inputText>			
			<h:message for="descricaoCompleta" styleClass="errors"/>
			
			<h:outputText styleClass="label" value="Descrição compacta*"></h:outputText>
			<h:inputText styleClass="inputText" id="descricaoCompacta" maxlength="50" size="50"
				value="#{produtoBB.descricaoCompacta}" required="true">
				<f:validateLength maximum="30" />
			</h:inputText>			
			<h:message for="descricaoCompacta" styleClass="errors"/>
			
			<h:outputText styleClass="label" value="Preço Padão*"></h:outputText>
			<h:inputText styleClass="inputText" id="precoPadrao" maxlength="9" size="12"
				value="#{produtoBB.precoPadrao}" required="true">
				<f:validateDoubleRange maximum="999999.99" minimum="0.01"/>
			</h:inputText>			
			<h:message for="precoPadrao" styleClass="errors"/>
			
			<h:outputText styleClass="label" value="Preço Promocional"></h:outputText>
			<h:inputText styleClass="inputText" id="precoPromocional" maxlength="9" size="12"
				value="#{produtoBB.precoPromocional}">
				<f:validateDoubleRange maximum="999999.99" minimum="0.01"/>
			</h:inputText>			
			<h:message for="precoPromocional" styleClass="errors"/>
			
			<h:outputText styleClass="label" value="Código Externo*"></h:outputText>
			<h:inputText styleClass="inputText" id="codigoExterno" maxlength="15" size="17"
				value="#{produtoBB.codigoExterno}" required="true">
				<f:validateLength maximum="15" />
			</h:inputText>			
			<h:message for="codigoExterno" styleClass="errors"/>
			
			<h:outputText styleClass="label" value="Código de Automação*"></h:outputText>
			<h:inputText styleClass="inputText" id="codigoAutomacao" maxlength="15" size="17"
				value="#{produtoBB.codigoAutomacao}" required="true">
				<f:validateLength maximum="15" />
			</h:inputText>			
			<h:message for="codigoAutomacao" styleClass="errors"/>
			
			<h:outputText styleClass="label" value="Tipo de Produto*"></h:outputText>
			<h:selectOneMenu id="tipos" styleClass="inputText"
						value="#{produtoBB.idTipoProduto}">		
				<f:selectItems id="tiposSelectItems" value="#{produtoBB.tipos}" />
			</h:selectOneMenu>
			<h:message for="idTipoProduto" styleClass="errors"/>
			
			<h:outputText styleClass="label" value="Grupo de Produto*"></h:outputText>
			<h:selectOneMenu id="grupos" styleClass="inputText"
						value="#{produtoBB.idGrupo}">		
				<f:selectItems id="gruposSelectItems" value="#{produtoBB.grupos}" />
			</h:selectOneMenu>
			<h:message for="idGrupo" styleClass="errors"/>
			
			<h:outputText styleClass="label" value="Unidade*"></h:outputText>
			<h:selectOneMenu id="undades" styleClass="inputText"
						value="#{produtoBB.idUnidade}">		
				<f:selectItems id="unidadeSelectItems" value="#{produtoBB.unidades}" />
			</h:selectOneMenu>
			<h:message for="idUnidades" styleClass="errors"/>
			
			<h:outputText styleClass="label" value="Imposto*"></h:outputText>
			<h:selectOneMenu id="impostos" styleClass="inputText"
						value="#{produtoBB.idImposto}">		
				<f:selectItems id="impostosSelectItems" value="#{produtoBB.impostos}" />
			</h:selectOneMenu>
			<h:message for="idImpostos" styleClass="errors"/>
			</h:panelGrid>
			<br>
			<h:panelGrid columns="3" id="formGrid1">
				<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" styleClass="inputBtn" id="botaoVoltar" immediate="true" action="#{produtoBB.voltarConsulta}" value="Voltar"></h:commandButton>
				<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" styleClass="inputBtn" id="botaoAlterar" action="#{produtoBB.alterar}" value="Alterar"></h:commandButton>
				<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" styleClass="inputBtn" id="botaoExcluir" action="#{produtoBB.excluir}" value="Excluir"></h:commandButton>
			</h:panelGrid>
		
	</h:form>
	
</body>
</html>
</f:view>