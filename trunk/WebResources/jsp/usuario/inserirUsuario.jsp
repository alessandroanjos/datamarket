<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="https://ajax4jsf.dev.java.net/ajax" prefix="a4j"%>
<%@taglib uri="http://yui4jsf.sourceforge.net" prefix="yui"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 4.01 Transitional//EN">
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
		<script type="text/javascript" src="/EnterpriseServer/js/funcoes.js"></script>
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
					<h:form id="frmInserirUsuario">
					<div id="topo">
						<h1>&nbsp;</h1>
						<h2>&nbsp;</h2>
						<div id="tituloPagina"><strong>Inserir Usuário</strong></div>
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
										<h:inputText styleClass="field text ativo" id="id" maxlength="2"
											value="#{usuarioBB.id}" size="3" required="true">
											<f:validateLength maximum="2" />
											<f:validator validatorId="LongValidator"/>
										</h:inputText>
										<h:message for="id" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Nome*"></h:outputLabel>
										<h:inputText styleClass="field text" id="nome" maxlength="50" size="50" required="true"
											value="#{usuarioBB.nome}">
											<f:validateLength maximum="50" />
										</h:inputText>
										<h:message for="nome" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Senha*"></h:outputLabel>
										<h:inputSecret styleClass="field text" id="senha" redisplay="true" maxlength="20" size="25"
											value="#{usuarioBB.senha}" required="true">
											<f:validateLength maximum="20" />
											<f:validator validatorId="LongValidator"/>
										</h:inputSecret>
										<h:message for="senha" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Perfil*"></h:outputLabel>
										<h:selectOneMenu id="perfis" styleClass="field select"
											value="#{usuarioBB.idPerfil}" required="true">   
												  <f:selectItems id="perfilSelectItems" 
												  value="#{usuarioBB.perfis}" />   
										</h:selectOneMenu>
										<h:message for="perfis" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Vendedor*"></h:outputLabel>
										<h:selectOneRadio styleClass="field radio" id="vendedor" required="true" rendered="true" value="#{usuarioBB.vendedor}" layout="lineDirection">
										    <f:selectItem itemLabel="Sim" itemValue="S"/>
										    <f:selectItem itemLabel="Não" itemValue="N"/>
										</h:selectOneRadio>
										<h:message for="vendedor" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Valor Comissão"></h:outputLabel>
										<h:inputText styleClass="field text" id="comissao" maxlength="5" size="5"
											value="#{usuarioBB.comissao}" required="true" dir="rtl" onkeypress="Formata('frmInserirUsuario:comissao',5,2);">
											<f:validateLength maximum="5" />
											<f:validateDoubleRange  minimum="0.00" maximum="100.00"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
										<h:message for="comissao" styleClass="msgErro"/>		
									</div>
								</li>
								<li class="normal">
									<div style="width: 100%;">
										<h:outputLabel styleClass="desc" value="Lojas Associadas"></h:outputLabel>
										<t:div styleClass="listagem" style="width: 100%; height: 150px;">
											<h:selectManyCheckbox id="idListaLojasAssociadas" layout="pageDirection" required="false" styleClass="field checkbox"
												value="#{usuarioBB.listaLojasAssociadas}" >
													<f:selectItems value="#{usuarioBB.lojas}"/>
											</h:selectManyCheckbox>
										</t:div>
										<h:message for="idListaLojasAssociadas" styleClass="msgErro"/>
									</div>
								</li>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" immediate="true" id="botaoLimpar" type="reset" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoInserir" action="#{usuarioBB.inserir}" value="Inserir"></h:commandButton>
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
