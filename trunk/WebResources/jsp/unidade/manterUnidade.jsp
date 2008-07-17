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

		<meta http-equiv="pragma" content="no-cache"/><link rel="icon" xhref="favicon.ico" type="image/x-icon" /><link rel="shortcut icon" xhref="favicon.ico" type="image/x-icon" />
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

      }

      </script>

	</head>
	<body>
	<div id="outer">
		<div id="topoGeral">
			<div id="tituloPaginaGeral">
				<strong>
					<h:outputText value="#{msgs.manterUnidade}"></h:outputText>
				</strong>
			</div>				
		</div>	
		<h:form id="frmManterUnidade">
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
											value="#{unidadeBB.id}" size="2" required="true">
											<f:validateLength maximum="2" />
											<f:validator validatorId="LongValidator" />
										</h:inputText>
									</div>
									<h:message for="id" styleClass="msgErro" />
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Descrição*"></h:outputLabel>
										<h:inputText styleClass="field text" id="nome" maxlength="50"
											size="50" value="#{unidadeBB.descricao}" required="true">
											<f:validateLength maximum="50" />
										</h:inputText>
									</div>
									<h:message for="nome" styleClass="msgErro" />
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Descrição Compacta*"></h:outputLabel>
										<h:inputText styleClass="field text ativo" id="descricaoCompacta" maxlength="20"
											value="#{unidadeBB.descricaoCompacta}" size="20" required="true">
											<f:validateLength maximum="20" />
										</h:inputText>
									</div>
									<h:message for="descricaoCompacta" styleClass="msgErro" />
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Abreviação*"></h:outputLabel>
										<h:inputText styleClass="field text" id="abreviacao" maxlength="2"
											size="5" value="#{unidadeBB.abreviacao}" required="true">
											<f:validateLength maximum="2" />
										</h:inputText>
									</div>
									<h:message for="abreviacao" styleClass="msgErro" />
								</li>
								<li class="buttons">
							    <h:commandButton styleClass="btTxt" immediate="true" id="botaoVoltar" action="#{unidadeBB.voltarConsulta}" value="Voltar"></h:commandButton>								
									<h:commandButton styleClass="btTxt" immediate="true" id="botaoLimpar" type="reset" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoAlterar" action="#{unidadeBB.alterar}" value="Alterar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoExcluir" action="#{unidadeBB.excluir}" value="Excluir"></h:commandButton>
								</li>
							</ul>
						</div>
						<div class="clear"></div>
					</div>
		</h:form>
	  </body>	
	</f:view>
</html>