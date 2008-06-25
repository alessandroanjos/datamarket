<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="https://ajax4jsf.dev.java.net/ajax" prefix="a4j"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<f:view>
	<f:loadBundle basename="resources.mensagens" var="msgs"/>
	<head>

		<title><h:outputText value="#{msgs.tituloPaginas}"></h:outputText></title>

		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
		
		<script type="text/javascript" src="/EnterpriseServer/js/jquery.js"></script>
		<script type="text/javascript" src="/EnterpriseServer/js/jquery-maskedinput.js"></script>
		<script type="text/javascript" src="/EnterpriseServer/js/global.js"></script>
		<t:stylesheet path="/css/default.css"></t:stylesheet>
		<t:stylesheet path="/css/form.css"></t:stylesheet>
      <script type="text/javascript">

      window.onload = function(){ inicializar() };

      function inicializar() {

      	$("input.field, select.field").each(function(i){
      		$(this).focus(function() {this.style.backgroundColor = "#eff6ff"});
      		$(this).blur(function() {this.style.backgroundColor = ""});
      	});

      	$("input.inteiro").each(function(i){
      		$(this).mask("99",{placeholder:" "});
      	});

      }

      </script>

	</head>
	<body>
	<div id="outer">
		<div id="topoGeral">
			<div id="tituloPaginaGeral">
				<strong>
					<h:outputText value="#{msgs.inserirGrupoProduto}"></h:outputText>
				</strong>
			</div>				
		</div>		
		<h:form id="frmInserirGrupoProduto">
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
										<h:inputText styleClass="field text inteiro ativo" id="id" maxlength="2"
											value="#{grupoProdutoBB.id}" size="2" required="true">
											<f:validateLength maximum="2" />
											<f:validator validatorId="LongValidator" />
										</h:inputText>
									</div>
									<h:message for="id" styleClass="msgErro" />
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Nome*"></h:outputLabel>
										<h:inputText styleClass="field text" id="nome" maxlength="50"
											size="50" value="#{grupoProdutoBB.descricao}" required="true">
											<f:validateLength maximum="50" />
										</h:inputText>
									</div>
									<h:message for="nome" styleClass="msgErro" />
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Grupo Superior"></h:outputLabel>
										<h:selectOneMenu id="grupos" styleClass="field select"
											value="#{grupoProdutoBB.idSuperior}">		
											<f:selectItems id="gruposSelectItems" value="#{grupoProdutoBB.grupos}" />
										</h:selectOneMenu>
									</div>
									<h:message for="idSuperior" styleClass="msgErro" />
								</li>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" immediate="true" id="botaoLimpar" type="reset" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoInserir" action="#{grupoProdutoBB.inserir}" value="Inserir"></h:commandButton>
								</li>
							</ul>
						</div>
						<div class="clear"></div>
					</div>

		</h:form>
		</body>
	</f:view>
</html>
		