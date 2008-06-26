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
							<h:outputText value="#{msgs.manterComponente}"></h:outputText>
						</strong>
					</div>				
				</div>
				<div id="content">
					<div id="primarioContentContainerInternas">
						<h:form id="frmInserirComponente">						
							<ul>
								<li class="normal">
									<div>
										<h:messages errorClass="msgSistemaErro" infoClass="msgSistemaSucesso" globalOnly="true" showDetail="true"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Código*"></h:outputLabel>
										<h:inputText styleClass="field text ativo" id="id" maxlength="4"
											value="#{componenteBB.id}" size="4" required="true" readonly="true">
											<f:validateLength maximum="4" />
											<f:validator validatorId="LongValidator"/>
										</h:inputText>
									</div>
									<h:message for="id" styleClass="msgErro" />
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Descrição*"></h:outputLabel>
										<h:inputText styleClass="field text" id="descricao" maxlength="50" size="50"
											value="#{componenteBB.descricao}" required="true">
											<f:validateLength maximum="50" />
										</h:inputText>
									</div>
									<h:message for="descricao" styleClass="msgErro" />
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Numero IP*"></h:outputLabel>
										<h:inputText styleClass="field text" id="ip" maxlength="15" size="15"
											value="#{componenteBB.ip}" required="true" onblur="if (!verificaIP(this.value)) this.value = '';">
											<f:validateLength maximum="15" />
										</h:inputText>
									</div>
									<h:message for="ip" styleClass="msgErro" />
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Porta*"></h:outputLabel>
										<h:inputText styleClass="field text inteiro" id="porta" maxlength="4" size="4"
											value="#{componenteBB.porta}" required="true">
											<f:validateLength maximum="4" />
										</h:inputText>
									</div>
									<h:message for="porta" styleClass="msgErro" />
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Loja*"></h:outputLabel>
										<h:selectOneMenu id="idLoja" styleClass="field select"
											value="#{componenteBB.idLoja}" required="true">   
												  <f:selectItems id="lojaSelectItems" 
												  value="#{componenteBB.lojas}" />   
										</h:selectOneMenu> 
									</div>
									<h:message for="idLoja" styleClass="msgErro" />
								</li>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" immediate="true" id="botaoVoltar" action="#{componenteBB.voltarConsulta}" value="Voltar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoAlterar" action="#{componenteBB.alterar}" value="Alterar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoExcluir" action="#{componenteBB.excluir}" value="Excluir"></h:commandButton>
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
		