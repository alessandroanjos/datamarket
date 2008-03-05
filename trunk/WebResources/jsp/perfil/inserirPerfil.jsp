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
	<script type="text/javascript" language="javascript" src="/EnterpriseServer/js/funcoes.js"></script> 
</head>


<f:view>  
<t:stylesheet path="/css/style.css"></t:stylesheet>
<body>
<h:form id="frmInserirPerfil">
	<div>
	<h:outputText id="titulo" styleClass="label" value="Home > Perfil"></h:outputText>
	</div>
	<br>
	<div>
		<h:messages styleClass="errors" globalOnly="true" showDetail="true"/>
	</div>
		<h:panelGrid columns="3" id="formGrid">
		    <f:facet name="header">
				<h:outputText styleClass="tituloTabela" value="Inserir Perfil" />
			</f:facet>
			<h:outputText styleClass="label" value="Codigo*"></h:outputText>					
			<h:inputText styleClass="inputText" id="id" maxlength="2"
				value="#{perfilBB.id}" size="2" required="true">
				<f:validateLength maximum="2" />
				<f:validator validatorId="LongValidator"/>
			</h:inputText>
			<h:message for="id" styleClass="errors"/>
			<h:outputText styleClass="label" value="Descrição*"></h:outputText>
			<h:inputText styleClass="inputText" id="descricao" maxlength="20" size="20"
				value="#{perfilBB.descricao}" required="true">
				<f:validateLength maximum="20" />
			</h:inputText>			
			<h:message for="descricao" styleClass="errors"/>
			<h:outputText styleClass="label" value="Perfil Superior*"></h:outputText>
			<h:selectOneMenu id="perfis" 
				value="#{perfilBB.idPerfilSuperior}">   
					  <f:selectItems id="perfilSuperiorSelectItems" 
					  value="#{perfilBB.perfis}" />   
			</h:selectOneMenu>  
			<h:message for="perfilSuperior" styleClass="errors"/>
			<h:outputText styleClass="label" value="Percentual de Desconto*"></h:outputText>
			<h:inputText styleClass="inputTextRight" id="percentualDesconto" maxlength="6" size="6"
				value="#{perfilBB.percentualDesconto}" required="true" onkeypress="Formata('frmInserirPerfil:percentualDesconto',5,2);">
				<f:validateLength maximum="6" />
				<f:validateDoubleRange  minimum="0.00" maximum="100.00"/>
				<f:validator validatorId="BigDecimalValidator"/>
			</h:inputText>			
			<h:message for="percentualDesconto" showSummary="true" styleClass="errors"/>
		</h:panelGrid>
		<br>
		<h:panelGrid columns="2" id="formGrid2" style="width: 600px;">
			<h:outputText styleClass="tituloTabela-left" value="Operações Associadas" />
			<h:outputText styleClass="tituloTabela-left" value="Funcionalidades Associadas" />
			<t:div styleClass="div-auto-scroll" style="width: 100%; height: 300px;">
				
				<h:selectManyCheckbox id="idListaOperacoesAssociadas" layout="pageDirection" required="true" styleClass="label"
					value="#{perfilBB.listaOperacoesAssociadas}" >
						<f:selectItems value="#{perfilBB.operacoes}"/>
				</h:selectManyCheckbox>
			</t:div>

			<t:div styleClass="div-auto-scroll" style="width: 100%; height: 300px;">
				<h:selectManyCheckbox id="idListaFuncionalidadesAssociadas" layout="pageDirection" required="true" styleClass="label"
					value="#{perfilBB.listaFuncionalidadesAssociadas}" >
						<f:selectItems value="#{perfilBB.funcionalidades}"/>
				</h:selectManyCheckbox>
			</t:div>		
		</h:panelGrid>
		<br>
		<h:panelGrid  columns="3" id="formGrid1">
			<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" immediate="true" styleClass="inputBtn" id="botaoVoltar" action="#{perfilBB.voltarMenu}" value="Voltar"></h:commandButton>
			<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" immediate="true" styleClass="inputBtn" id="botaoLimpar" type="reset" value="Limpar"></h:commandButton>
			<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" styleClass="inputBtn" id="botaoInserir" action="#{perfilBB.inserir}" value="Inserir"></h:commandButton>
		</h:panelGrid>
</h:form>	
</body>
</f:view>
</html>