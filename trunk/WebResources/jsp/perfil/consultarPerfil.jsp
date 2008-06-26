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

      	$("input.monetario").each(function(i){
      		$(this).mask("999.99",{placeholder:" "});
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
							<h:outputText value="#{msgs.consultarPerfil}"></h:outputText>
						</strong>
					</div>				
				</div>				
				<div id="content">				
					<div id="primarioContentContainerInternas">
						<h:form id="frmConsultarPerfil">
							<fieldset>
								<legend>Op��es de filtro:</legend>
								<ul>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="C�digo"></h:outputLabel>
											<h:inputText styleClass="field text ativo inteiro" id="id" maxlength="4"
												value="#{perfilBB.id}" size="4" required="false">
												<f:validateLength maximum="4" />
												<f:validator validatorId="LongValidator"/>
											</h:inputText>
											<h:message for="id" styleClass="msgErro" />
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Descri��o"></h:outputLabel>
											<h:inputText styleClass="field text" id="descricao" maxlength="50" size="50"
												value="#{perfilBB.descricao}" required="false">
												<f:validateLength maximum="50" />
											</h:inputText>
											<h:message for="descricao" styleClass="msgErro" />
										</div>
										<br />
										<br />
										<div>
											<h:outputLabel styleClass="desc" value="Perfil Superior"></h:outputLabel>
											<h:selectOneMenu id="perfis" styleClass="field select"
												value="#{perfilBB.idPerfilSuperior}" style="width: 200px;">   
													  <f:selectItems id="perfilSelectItems" 
													  value="#{perfilBB.perfis}" />   
											</h:selectOneMenu> 
											<h:message for="perfis" styleClass="msgErro" />
										</div>
									</li>
								</ul>
							</fieldset>	
							<div class="listagem">
								<t:dataTable value="#{perfilBB.listaPerfis}"
									var="perfil" rowClasses="rowA,rowB" width="100%" renderedIfEmpty="false">
									<h:column>
										<f:facet name="header">
											<h:outputText value="C�digo" /> 
										</f:facet>
										<h:outputText value="#{perfil.id}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Descri��o" />
										</f:facet>
										<h:commandLink value="#{perfil.descricao}" action="#{perfilBB.consultar}">
											<f:param name="id" value="#{perfil.id}"/>						
										</h:commandLink>
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Perfil Superior" />
										</f:facet>
										<h:outputText value="#{perfil.perfilSuperior.id} - #{perfil.perfilSuperior.descricao}" />
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Percentual de Desconto" />
										</f:facet>
										<h:outputText value="#{perfil.percentualDesconto}" />
									</h:column>									
								</t:dataTable>
								<ul>
									<li class="normal">
										<div>
											<h:messages rendered="#{not perfilBB.existeRegistros}" errorClass="msgSistemaErro" infoClass="msgSistemaSucesso" globalOnly="true" showDetail="true"/>
										</div>
									</li>
								</ul>
							</div>
							<ul>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" id="botaoLimpar" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoConsultar" action="#{perfilBB.consultar}" value="Consultar"></h:commandButton>
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