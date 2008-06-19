<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="https://ajax4jsf.dev.java.net/ajax" prefix="a4j"%>
<%@taglib uri="http://yui4jsf.sourceforge.net" prefix="yui"%>


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
		<script type="text/javascript" src="/EnterpriseServer/js/global.js"></script>
		<script type="text/javascript" src="/EnterpriseServer/js/funcoes.js"></script>
		<t:stylesheet path="/css/default.css"></t:stylesheet>
		<t:stylesheet path="/css/form.css"></t:stylesheet>	
	</head>
	<body>
	<div id="outer">
		<div id="topoGeral">
			<div id="tituloPaginaGeral">
				<strong>
					<h:outputText value="#{msgs.manterAutorizadora}"></h:outputText>
				</strong>
			</div>				
		</div>
		<h:form id="frmManterAutorizadora">
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
											value="#{autorizadoraBB.id}" size="3" required="true" readonly="true">
											<f:validateLength maximum="2" />
											<f:validator validatorId="LongValidator"/>
										</h:inputText>
										<h:message for="id" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Descrição*"></h:outputLabel>
										<h:inputText styleClass="field text" id="descricao" maxlength="50" size="50" required="true"
											value="#{autorizadoraBB.descricao}">
											<f:validateLength maximum="50" />
										</h:inputText>
										<h:message for="descricao" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Situação"></h:outputLabel>
										<h:selectOneRadio  styleClass="field select"  id="situacao" 
											value="#{autorizadoraBB.situacao}"  layout="lineDirection" required="true">
										    <f:selectItem itemLabel="Sim" itemValue="S" />
										    <f:selectItem itemLabel="Não" itemValue="N"/>
										</h:selectOneRadio>
										<h:message for="situacao" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Desagil"></h:outputLabel>
										<h:inputText styleClass="field text" id="desagil" maxlength="6" size="6"
											value="#{autorizadoraBB.desagil}" required="true" onkeypress="Formata('frmInserirFormaRecebimento:valorLimiteSangria',9,2);">
											<f:validateLength maximum="6" />
											<f:validateDoubleRange  minimum="0.00" maximum="999.99"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
										<h:message for="desagil" styleClass="msgErro"/>
									</div>
								</li>
								<li class="buttons">
								    <h:commandButton styleClass="btTxt" id="botaoVoltar" action="#{autorizadoraBB.voltarConsulta}" value="Voltar"></h:commandButton>
									<h:commandButton styleClass="btTxt" immediate="true" id="botaoLimpar" type="reset" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoAlterar" action="#{autorizadoraBB.alterar}" value="Alterar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoExcluir" action="#{autorizadoraBB.excluir}" value="Excluir"></h:commandButton>									
								</li>
							</ul>
						</div>
						<div class="clear"></div>
					</div>
		</h:form>
	  </body>	
	</f:view>
</html>
		