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
	<h:outputText id="titulo" styleClass="label" value="Home > Produto"></h:outputText>
	</div>
	<br>
	<div>
		<h:messages styleClass="errors" globalOnly="true" showDetail="true"/>
	</div>
	<h:form>
		<h:panelGrid columns="3" id="formGrid">
		    <f:facet name="header">
				<h:outputText styleClass="tituloTabela" value="Consulta de Produto" />
			</f:facet>
		    <h:outputText styleClass="label" value="Código"></h:outputText>					
			<h:inputText styleClass="inputText" id="id" maxlength="2"
				value="#{produtoBB.id}" size="3">
				<f:validateLength maximum="2" />
				<f:validator validatorId="LongValidator"/>
			</h:inputText>
			<h:message for="id" styleClass="errors"/>
			<h:outputText styleClass="label" value="Descrição Completa"></h:outputText>
			<h:inputText styleClass="inputText" id="descricaoCompleta" maxlength="50" size="50"
				value="#{produtoBB.descricaoCompleta}">
				<f:validateLength maximum="50" />
			</h:inputText>
			<h:message for="descricao" styleClass="errors"/>
			<h:outputText styleClass="label" value="Tipo de Produto*"></h:outputText>
			<h:selectOneMenu id="tipos" styleClass="inputText"
						value="#{produtoBB.idTipoProduto}">		
				<f:selectItem id="tiposSelectItemsBranco" itemValue="" itemLabel=""/>		
				<f:selectItems id="tiposSelectItems" value="#{produtoBB.tiposConsulta}" />
			</h:selectOneMenu>
			<h:message for="idTipoProduto" styleClass="errors"/>
			
			<h:outputText styleClass="label" value="Grupo de Produto*"></h:outputText>
			<h:selectOneMenu id="grupos" styleClass="inputText"
						value="#{produtoBB.idGrupo}">		
				<f:selectItem id="gruposSelectItemsBranco" itemValue="" itemLabel=""/>		
				<f:selectItems id="gruposSelectItems" value="#{produtoBB.gruposConsulta}" />
			</h:selectOneMenu>
			<h:message for="idGrupo" styleClass="errors"/>
			
			<h:outputText styleClass="label" value="Unidade*"></h:outputText>
			<h:selectOneMenu id="undades" styleClass="inputText"
						value="#{produtoBB.idUnidade}">		
				<f:selectItem id="unidadeSelectItemsBranco" itemValue="" itemLabel=""/>		
				<f:selectItems id="unidadeSelectItems" value="#{produtoBB.unidadesConsulta}" />
			</h:selectOneMenu>
			<h:message for="idUnidades" styleClass="errors"/>
			
			<h:outputText styleClass="label" value="Imposto*"></h:outputText>
			<h:selectOneMenu id="impostos" styleClass="inputText"
						value="#{produtoBB.idImposto}">		
				<f:selectItem id="impostosSelectItemsBranco" itemValue="" itemLabel=""/>		
				<f:selectItems id="impostosSelectItems" value="#{produtoBB.impostosConsulta}" />
			</h:selectOneMenu>
			<h:message for="idImpostos" styleClass="errors"/>
		</h:panelGrid>
		<br>
		<h:panelGrid columns="3" id="formGrid1">	
			<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" styleClass="inputBtn" id="botaoVoltar" action="#{produtoBB.voltarMenu}" value="Voltar"></h:commandButton>
			<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" immediate="true" styleClass="inputBtn" id="botaoLimpar" type="reset" value="Limpar"></h:commandButton>
			<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" styleClass="inputBtn" id="botaoConsultar" action="#{produtoBB.consultar}" value="Consultar"></h:commandButton>
		</h:panelGrid>
		<p>
		<h:dataTable value="#{produtoBB.produtos}"
					var="produto">
					<f:facet name="header">
						<h:outputText styleClass="tituloTabela" value="Produtos" />
					</f:facet>					
					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="label"  value="Código" />
						</f:facet>
						<h:outputText styleClass="label" value="#{produto.id}" /> 
					</h:column>
					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="label"  value="Descrição Completa" />
						</f:facet>
						<h:commandLink styleClass="label" value="#{produto.descricaoCompleta}" action="#{produtoBB.consultar}">
							<f:param name="id" value="#{produto.id}"/>						
						</h:commandLink>
					</h:column>
					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="label"  value="Código Automação" />
						</f:facet>
						<h:outputText styleClass="label" value="#{produto.codigoAutomacao}" /> 
					</h:column>
					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="label"  value="Código Externo" />
						</f:facet>
						<h:outputText styleClass="label" value="#{produto.codigoExterno}" /> 
					</h:column>
					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="label"  value="Preço" />
						</f:facet>
						<h:outputText styleClass="label" value="#{produto.precoPadrao}" /> 
					</h:column>
					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="label"  value="Preço Promocional" />
						</f:facet>
						<h:outputText styleClass="label" value="#{produto.precoPromocional}" /> 
					</h:column>
					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="label"  value="Tipo de Produto" />
						</f:facet>
						<h:outputText styleClass="label" value="#{produto.tipo.descricao}" /> 
					</h:column>
					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="label"  value="Grupo de Produto" />
						</f:facet>
						<h:outputText styleClass="label" value="#{produto.grupo.descricao}" /> 
					</h:column>
					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="label"  value="Unidade" />
						</f:facet>
						<h:outputText styleClass="label" value="#{produto.unidade.descricao}" /> 
					</h:column>
					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="label"  value="Imposto" />
						</f:facet>
						<h:outputText styleClass="label" value="#{produto.imposto.descricao}" /> 
					</h:column>

				</h:dataTable>
	</h:form>
	
</body>
</html>
</f:view>