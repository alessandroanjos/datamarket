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
					<h:form id="frmManterFormaRecebimento">
					<div id="topo">
						<h1>&nbsp;</h1>
						<h2>&nbsp;</h2>
						<div id="tituloPagina"><strong>Manter Formas de Recebimento</strong></div>
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
										<h:inputText styleClass="field text ativo" readonly="true" id="id" maxlength="2"
											value="#{formaRecebimentoBB.id}" size="3" rendered="true">
											<f:validateLength maximum="2" />
											<f:validator validatorId="LongValidator"/>
										</h:inputText>
										<h:message for="id" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Descrição*"></h:outputLabel>
										<h:inputText styleClass="field text" id="descricao" maxlength="50" size="50" rendered="true"
											value="#{formaRecebimentoBB.descricao}">
											<f:validateLength maximum="50" />
										</h:inputText>
										<h:message for="descricao" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Recebimento na Impressora"></h:outputLabel>
										<h:inputText styleClass="field text" id="recebimentoImpressora" maxlength="50" size="50"
											value="#{formaRecebimentoBB.recebimentoImpressora}">
											<f:validateLength maximum="50" />
										</h:inputText>
										<h:message for="recebimentoImpressora" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Abre Gaveta*"></h:outputLabel>
										<h:selectOneRadio  styleClass="field radio"  id="abrirGaveta" 
											value="#{formaRecebimentoBB.abrirGaveta}"  layout="lineDirection" rendered="true">
										    <f:selectItem itemLabel="Sim" itemValue="S" />
										    <f:selectItem itemLabel="Não" itemValue="N"/>
										</h:selectOneRadio>
										<h:message for="abrirGaveta" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Valor Limite Sangria"></h:outputLabel>
										<h:inputText styleClass="field text" id="valorLimiteSangria" maxlength="10" size="10"
											value="#{formaRecebimentoBB.valorLimiteSangria}" dir="rtl" required="true" onkeypress="Formata('frmManterFormaRecebimento:valorLimiteSangria',9,2);">
											<f:validateLength maximum="10" />
											<f:validateDoubleRange  minimum="0.00" maximum="9999999.99"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
										<h:message for="valorLimiteSangria" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Inicio Validade"></h:outputLabel>
										<t:inputCalendar readonly="true" styleClass="field text" forceId="dataInicioValidade" 
											value="#{formaRecebimentoBB.dataInicioValidade}" id="dataInicioValidade" renderAsPopup="true" 
											popupDateFormat="dd/MM/yyyy"/>
										<h:message for="dataInicioValidade" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Final Validade"></h:outputLabel>
										<t:inputCalendar readonly="true" styleClass="field text" forceId="dataFimValidade" 
											value="#{formaRecebimentoBB.dataFimValidade}" id="dataFimValidade" renderAsPopup="true" 
											popupDateFormat="dd/MM/yyyy"/>
										<h:message for="dataFimValidade" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Valor Maximo Troco"></h:outputLabel>
										<h:inputText styleClass="field text" id="valorMaxTroco" maxlength="15" size="15"
											value="#{formaRecebimentoBB.valorMaxTroco}" required="true" dir="rtl" onkeypress="Formata('frmManterFormaRecebimento:valorMaxTroco',14,2);">
											<f:validateLength maximum="15" />
											<f:validateDoubleRange  minimum="0.00" maximum="9999999.99"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
										<h:message for="valorMaxTroco" styleClass="msgErro"/>		
									</div>
								</li>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" immediate="true" id="botaoLimpar" type="reset" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoAlterar" action="#{formaRecebimentoBB.alterar}" value="Alterar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoExcluir" action="#{formaRecebimentoBB.excluir}" value="Excluir"></h:commandButton>									
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
