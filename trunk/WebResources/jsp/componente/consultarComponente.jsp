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
			<script type="text/javascript" src="/EnterpriseServer/js/funcoes.js"></script>
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
      		$(this).mask("9999",{placeholder:" "});
      	});

      }

      </script>
		</head>
		<body>
			<div id="outer">
				<div id="topoGeral">
					<div id="tituloPaginaGeral">
						<strong>
							<h:outputText value="#{msgs.consultarComponente}"></h:outputText>
						</strong>
					</div>				
				</div>				
				<div id="content">
					<div id="primarioContentContainerInternas">
						<h:form id="frmConsultarComponente">
							<fieldset>
								<legend>Opções de filtro:</legend>
								<ul>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Código"></h:outputLabel>
											<h:inputText styleClass="field text inteiro ativo" id="id" maxlength="4"
												value="#{componenteBB.id}" size="4" required="false">
												<f:validateLength maximum="4" />
												<f:validator validatorId="LongValidator"/>
											</h:inputText>
											<h:message for="id" styleClass="msgErro" />
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Descrição"></h:outputLabel>
											<h:inputText styleClass="field text" id="descricao" maxlength="50" size="50"
												value="#{componenteBB.descricao}">
												<f:validateLength maximum="50" />
											</h:inputText>
											<h:message for="descricao" styleClass="msgErro" />
										</div>
									</li>
								</ul>
							</fieldset>	
							<div class="listagem">
								<t:dataTable id="componentes" value="#{componenteBB.componentes}"
									var="componente" rowClasses="rowA,rowB" width="100%">
									<h:column>
										<f:facet name="header">
											<h:outputText value="Código" /> 
										</f:facet>
										<h:outputText value="#{componente.id}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Descrição" />
										</f:facet>
										<h:commandLink value="#{componente.descricao}" action="#{componenteBB.consultar}">
											<f:param name="id" value="#{componente.id}"/>						
										</h:commandLink>
									</h:column>
								</t:dataTable>
							</div>
							<ul>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" id="botaoLimpar" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoConsultar" action="#{componenteBB.consultar}" value="Consultar"></h:commandButton>
								</li>						
							</ul>
						</h:form>
					</div>
					<div class="clear"></div>
				</div>
			</div>
		</body>
	</f:view>
</html>