<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="https://ajax4jsf.dev.java.net/ajax" prefix="a4j"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
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
		<script type="text/javascript" src="/EnterpriseServer/js/jquery.js"></script>
		<script type="text/javascript" src="/EnterpriseServer/js/global.js"></script>

		<script type="text/javascript">

window.onload = function(){ inicializar() };

function inicializar() {
	$("input.field, select.field").each(function(i){
		$(this).focus(function() {this.style.backgroundColor = "#eff6ff"});
		$(this).blur(function() {this.style.backgroundColor = "white"});
	});
}


</script>
<script>
<!--
function returnDay(day){
var day_of_week = "";
switch (parseInt(day)){
case parseInt("1"): day_of_week = "Domingo";break;
case parseInt("2"): day_of_week = "Segunda-Feira";break;
case parseInt("3"): day_of_week = "Terça-Feira";break;
case parseInt("4"): day_of_week = "Quarta-Feira";break;
case parseInt("5"): day_of_week = "Quinta-Feira";break;
case parseInt("6"): day_of_week = "Sexta-Feira";break;
case parseInt("7"): day_of_week = "Sábado";break;
}
return day_of_week;
}
var today_date= new Date()
var month=today_date.getMonth()+1
var today=today_date.getDate()
var year=today_date.getYear()
var day=returnDay(today_date.getDay()+1)

//--> </script>
	</head>
<f:view>
		<t:stylesheet path="/css/form.css"></t:stylesheet>
		<t:stylesheet path="/css/default.css"></t:stylesheet>
		<body>
		<div id="outer">
					<h:form>
					<div id="topo">
						<h1>&nbsp;</h1>
						<h2>&nbsp;</h2>
						<div id="tituloPagina"><strong>Manter Tipos de Produtos</strong></div>
						<div id="logoCliente"><img src="/EnterpriseServer/images/logoCliente.gif" alt="Magia dos Pães" title="Magia dos Pães" /></div>
					</div>
					<div id="header">
						<div class="centro">
							<div id="loginUser">Usuário: <strong><h:outputText id="usuario" value="#{loginBB.usuarioLogado.nome}"></h:outputText></strong> &nbsp;&nbsp;&nbsp;&#8212;&nbsp;&nbsp;&nbsp; <script>document.write(day)</script>,  <script>document.write(today+"/"+month+"/"+year)</script></div>
							<div id="breadcrumb"><strong>Infinity</strong> - DataMarket - Enterprise Server</div>
						</div>
					</div>
					<div id="content">
						<div id="primarioContentContainerInternas">
							<ul>
								<li class="normal">
									<div>
										<h:messages errorClass="msgSistemaErro" infoClass="msgSistemaSucesso" globalOnly="true" showDetail="true"/>
									</div>
								</li>
							
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Código*"></h:outputLabel>
										<h:inputText styleClass="field text" id="id" maxlength="2"
											value="#{tipoProdutoBB.id}" size="2" disabled="true" required="true">
											<f:validateLength maximum="2" />
											<f:validator validatorId="LongValidator" />
										</h:inputText>
										<h:message for="id" styleClass="msgErro" />
									</div>
								</li>
								<li class="normal">
									<div>
										<label class="desc"><h:outputText value="Nome*"></h:outputText></label>
										<h:inputText styleClass="field text" id="nome" maxlength="50"
											size="50" value="#{tipoProdutoBB.descricao}" required="true">
											<f:validateLength maximum="50" />
										</h:inputText>
										<h:message for="nome" styleClass="msgErro" />
									</div>
								</li>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" id="botaoAlterar" action="#{tipoProdutoBB.alterar}" value="Alterar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoExcluir" action="#{tipoProdutoBB.excluir}" value="Excluir"></h:commandButton>
								</li>
							</ul>
						</div>
						<div class="clear"></div>
					</div>
				</h:form>
				<div id="footer">
					<p>
						Todos os direitos reservados &copy; 2008 |
						<a href="http://www.infinity.com.br" target="_blank">www.infinity.com.br</a>
					</p>
				</div>
			</div>
		</body>
	</f:view>
</html>