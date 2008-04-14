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
	<h:outputText id="titulo" styleClass="label" value="Home > Relatório de Produtos por Seção"></h:outputText>
	</div>
	<br>
	<div>
		<h:messages styleClass="errors" globalOnly="true" showDetail="true"/>
	</div>
		<h:panelGrid columns="3" id="formGrid" width="600px">
			<h:outputText styleClass="label" value="Loja*"></h:outputText>					
			<h:selectOneMenu id="idLoja" 
				value="#{relatorioProdutosPorSecaoBB.idLoja}" required="true">   
					  <f:selectItems id="lojaSelectItems" 
					  value="#{relatorioProdutosPorSecaoBB.lojas}" />   
			</h:selectOneMenu>  
			<h:message for="lojas" styleClass="errors"/>
			<h:outputText styleClass="label" value="Seção"></h:outputText>
			<t:div styleClass="div-auto-scroll"
				style="width: 100%; height: 200px;">
				<h:selectManyCheckbox id="idSecoesSelecionadas"
					layout="pageDirection" required="false" styleClass="label"
					value="#{relatorioProdutosPorSecaoBB.listaSecoesSelecionadas}">
					<f:selectItems value="#{relatorioProdutosPorSecaoBB.grupos}"/>
				</h:selectManyCheckbox>
			</t:div>		
			<h:message for="idSecoesSelecionadas" styleClass="errors"/>
		</h:panelGrid>
		<br>
		<h:panelGrid  columns="2" id="formGrid1">
			<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" immediate="true" styleClass="inputBtn" id="botaoVoltar" action="#{relatorioProdutosPorSecaoBB.voltarMenu}" value="Voltar"></h:commandButton>
			<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" styleClass="inputBtn" id="botaoRelatorio" action="#{relatorioProdutosPorSecaoBB.gerarRelatorioProdutosPorSecao}" value="Relatório" onclick="openReport('#{relatorioProdutosPorSecaoBB.nomeRelatorio}');"></h:commandButton>
		</h:panelGrid>
</h:form>	
</body>
</f:view>
</html>