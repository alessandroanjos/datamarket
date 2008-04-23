<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="https://ajax4jsf.dev.java.net/ajax" prefix="a4j"%>


<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>

		<title>INFINITY - DataMarket - Enterprise Server</title>

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
case parseInt("3"): day_of_week = "Ter�a-Feira";break;
case parseInt("4"): day_of_week = "Quarta-Feira";break;
case parseInt("5"): day_of_week = "Quinta-Feira";break;
case parseInt("6"): day_of_week = "Sexta-Feira";break;
case parseInt("7"): day_of_week = "S�bado";break;
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
						<div id="tituloPagina"><strong>Manter Unidade</strong></div>
						<div id="logoCliente"><img src="/EnterpriseServer/images/logoCliente.gif" alt="Magia dos P�es" title="Magia dos P�es" /></div>
					</div>
					<div id="header">
						<div class="centro">
							<div id="loginUser">Usu�rio: <strong><h:outputText id="usuario" value="#{loginBB.usuarioLogado.nome}"></h:outputText></strong> &nbsp;&nbsp;&nbsp;&#8212;&nbsp;&nbsp;&nbsp; <script>document.write(day)</script>,  <script>document.write(today+"/"+month+"/"+year)</script></div>
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
										<h:outputLabel styleClass="desc" value="C�digo*"></h:outputLabel>
										<h:inputText styleClass="field text ativo" readonly="true" id="id" maxlength="2"
											value="#{unidadeBB.id}" size="2" required="true">
											<f:validateLength maximum="2" />
											<f:validator validatorId="LongValidator" />
										</h:inputText>
										<h:message for="id" styleClass="msgErro" />
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Descri��o*"></h:outputLabel>
										<h:inputText styleClass="field text" id="nome" maxlength="50"
											size="50" value="#{unidadeBB.descricao}" required="true">
											<f:validateLength maximum="50" />
										</h:inputText>
										<h:message for="nome" styleClass="msgErro" />
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Descri��o Compacta*"></h:outputLabel>
										<h:inputText styleClass="field text ativo" id="descricaoCompacta" maxlength="50"
											value="#{unidadeBB.descricaoCompacta}" size="50" required="true">
											<f:validateLength maximum="2" />
											<f:validator validatorId="LongValidator" />
										</h:inputText>
										<h:message for="descricaoCompacta" styleClass="msgErro" />
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Abrevia��o*"></h:outputLabel>
										<h:inputText styleClass="field text" id="abreviacao" maxlength="50"
											size="50" value="#{unidadeBB.abreviacao}" required="true">
											<f:validateLength maximum="50" />
										</h:inputText>
										<h:message for="abreviacao" styleClass="msgErro" />
									</div>
								</li>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" immediate="true" id="botaoLimpar" type="reset" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoAlterar" action="#{unidadeBB.alterar}" value="Alterar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoExcluir" action="#{unidadeBB.excluir}" value="Excluir"></h:commandButton>
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