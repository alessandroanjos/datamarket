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
	<h:outputText id="titulo" styleClass="label" value="Home > Inserir Forma de Recebimento"></h:outputText>
	</div>
	<br>
	<div>
		<h:messages styleClass="errors" globalOnly="true" showDetail="true"/>
	</div>
		<h:panelGrid columns="3" id="formGrid">
		    <f:facet name="header">
				<h:outputText styleClass="tituloTabela" value="Inserir Plano de Pagamento" />
			</f:facet>
		    <h:outputText styleClass="label" value="Codigo*"></h:outputText>					
			<h:inputText styleClass="inputText" id="id" maxlength="2"
				value="#{planoPagamentoBB.id}" size="2" required="true">
				<f:validateLength maximum="2" />
				<f:validator validatorId="LongValidator"/>
			</h:inputText>
			<h:message for="id" styleClass="errors"/>
			<h:outputText styleClass="label" value="Descrição"></h:outputText>
			<h:inputText styleClass="inputText" id="descricao" maxlength="50" size="50"
				value="#{planoPagamentoBB.descricao}">
				<f:validateLength maximum="50" />
			</h:inputText>
			<h:message for="descricao" styleClass="errors"/>
			<h:outputText styleClass="label" value="Situação"></h:outputText>
			<h:selectOneRadio  styleClass="selectOneRadio" id="status" 
				value="#{planoPagamentoBB.status}" layout="lineDirection">
			    <f:selectItems id="situacao" value="#{planoPagamentoBB.situacaoItens}" />

			</h:selectOneRadio>
			<h:message for="status" styleClass="errors"/>
			<h:outputText styleClass="label" value="Valor Minimo"></h:outputText>
			<h:inputText styleClass="inputText" id="valorMinimo" maxlength="15" size="15"
				value="#{planoPagamentoBB.valorMinimo}">
				<f:convertNumber maxFractionDigits="2"/>
			</h:inputText>
			<h:message for="valorMinimo" styleClass="errors"/>
			<h:outputText styleClass="label" value="Valor Maximo"></h:outputText>
			<h:inputText styleClass="inputText" id="valorMaximo" maxlength="15" size="15"
				value="#{planoPagamentoBB.valorMaximo}">
				<f:convertNumber maxFractionDigits="2"
                       groupingUsed="true"
                       currencySymbol="R$"
                       maxIntegerDigits="15"
                       type="currency"/>
			</h:inputText>
			<h:message for="valorMaximo" styleClass="errors"/>
			<h:outputText styleClass="label" value="Percentual Desconto"></h:outputText>
			<h:inputText styleClass="inputText" id="percDesconto" maxlength="15" size="15"
				value="#{planoPagamentoBB.percDesconto}">
				<f:convertNumber maxFractionDigits="2"
                       groupingUsed="true"
                       currencySymbol=""
                       maxIntegerDigits="4"
                       type="currency"/>
			</h:inputText>
			<h:message for="valorMinimo" styleClass="errors"/>
			<h:outputText styleClass="label" value="Percentual Acrescimo"></h:outputText>
			<h:inputText styleClass="inputText" id="percAcrescimo" maxlength="15" size="15"
				value="#{planoPagamentoBB.percAcrescimo}">
				<f:convertNumber maxFractionDigits="2"
                       groupingUsed="true"
                       currencySymbol=""
                       maxIntegerDigits="4"
                       type="currency"/>
			</h:inputText>
			<h:message for="percAcrescimo" styleClass="errors"/>
			<h:outputText styleClass="label" value="Inicio Validade"></h:outputText>
			<t:inputCalendar readonly="true" styleClass="inputText" forceId="dataInicioValidade" value="#{planoPagamentoBB.dataInicioValidade}" id="dataInicioValidade" renderAsPopup="true"
					popupDateFormat="dd/MM/yyyy"/>
			<h:message for="dataInicioValidade" styleClass="errors"/>
					
			<h:outputText styleClass="label" value="Final Validade"></h:outputText>
			<t:inputCalendar readonly="true" styleClass="inputText" forceId="dataFimValidade" value="#{planoPagamentoBB.dataFimValidade}" id="dataFimValidade" renderAsPopup="true"
					popupDateFormat="dd/MM/yyyy"/>
			<h:message for="dataFimValidade" styleClass="errors"/>
			<h:outputText styleClass="label" value="Forma de Recebimento"></h:outputText>
			<h:selectOneMenu id="idForma" 
				value="#{planoPagamentoBB.idForma}">   
					  <f:selectItems id="formaSelectItems" 
					  value="#{planoPagamentoBB.formas}" />   
			</h:selectOneMenu>  
			<h:message for="idForma" styleClass="errors"/>
		</h:panelGrid>
		<br>
		<h:panelGrid  columns="3" id="formGrid1">
			<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" immediate="true" styleClass="inputBtn" id="botaoVoltar" action="#{planoPagamentoBB.voltarMenu}" value="Voltar"></h:commandButton>
			<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" immediate="true" styleClass="inputBtn" id="botaoLimpar" type="reset" value="Limpar"></h:commandButton>
			<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" styleClass="inputBtn" id="botaoInserir" action="#{planoPagamentoBB.inserir}" value="Inserir"></h:commandButton>
		</h:panelGrid>
</h:form>	
</body>
</f:view>
</html>