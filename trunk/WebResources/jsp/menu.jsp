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
	<div>
	<h:outputText styleClass="label" id="titulo" value="Home"></h:outputText>
	</div>
	<br>
	<h:form>
	<h:commandLink styleClass="label" value="Inserir Tipo de Produto" action="inserirTipoProduto"></h:commandLink>
	<br>
	<h:commandLink styleClass="label" value="Manter Tipo de Produto" action="manterTipoProduto"></h:commandLink>
	<br>
	<h:commandLink styleClass="label" value="Inserir Unidade" action="inserirUnidade"></h:commandLink>
	<br>
	<h:commandLink styleClass="label" value="Manter Unidade" action="manterUnidade"></h:commandLink>
	<br>
	<h:commandLink styleClass="label" value="Inserir Imposto" action="inserirImposto"></h:commandLink>
	<br>
	<h:commandLink styleClass="label" value="Manter Imposto" action="manterImposto"></h:commandLink>
	<br>
	<h:commandLink styleClass="label" value="Inserir Loja" action="inserirLoja"></h:commandLink>
	<br>
	<h:commandLink styleClass="label" value="Manter Loja" action="manterLoja"></h:commandLink>
	<br>
	<h:commandLink styleClass="label" value="Inserir Grupo Produto" action="inserirGrupoProduto"></h:commandLink>
	<br>
	<h:commandLink styleClass="label" value="Manter Grupo Produto" action="manterGrupoProduto"></h:commandLink>
	<br>
	<h:commandLink styleClass="label" value="Inserir Perfil" action="inserirPerfil"></h:commandLink>
	<br>
	<h:commandLink styleClass="label" value="Manter Perfil" action="manterPerfil"></h:commandLink>
	<br>
	<h:commandLink styleClass="label" value="Inserir Produto" action="inserirProduto"></h:commandLink>
	<br>	
	<h:commandLink styleClass="label" value="Manter Produto" action="manterProduto"></h:commandLink>
	<br>
	<h:commandLink styleClass="label" value="Inserir Forma de Recebimento" action="inserirFormaRecebimento"></h:commandLink>
	<br>
	<h:commandLink styleClass="label" value="Manter Forma de Recebimento" action="manterFormaRecebimento"></h:commandLink>
	<br>
	<h:commandLink styleClass="label" value="Inserir Plano de Pagamento" action="inserirPlanoPagamento"></h:commandLink>
	<br>
	<h:commandLink styleClass="label" value="Manter Plano de Pagamento" action="manterPlanoPagamento"></h:commandLink>
	<br>
	<h:commandLink styleClass="label" value="Inserir Componente" action="inserirComponente"></h:commandLink>
	<br>
	<h:commandLink styleClass="label" value="Manter Componente" action="manterComponente"></h:commandLink>
	<br>

	<br>
	<h:commandLink styleClass="label" value="Logout" action="#{loginBB.logout}"></h:commandLink>
	</h:form>
</body>
</f:view>
</html>