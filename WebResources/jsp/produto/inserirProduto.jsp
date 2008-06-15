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
					<h:outputText value="#{msgs.inserirProduto}"></h:outputText>
				</strong>
			</div>				
		</div>	
		<h:form id="frmInserirProduto">				
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
										<h:inputText styleClass="field text ativo" id="id" maxlength="4"
											value="#{produtoBB.id}" size="4" required="true">
											<f:validateLength maximum="4" />
											<f:validator validatorId="LongValidator" />
										</h:inputText>
										<h:message for="id" styleClass="msgErro" />
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Descrição Completa*"></h:outputLabel>
										<h:inputText styleClass="field text" id="descricaoCompleta" maxlength="50"
											size="50" value="#{produtoBB.descricaoCompleta}" required="true">
											<f:validateLength maximum="50" />
										</h:inputText>
										<h:message for="descricaoCompleta" styleClass="msgErro" />
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Descrição Compacta*"></h:outputLabel>
										<h:inputText styleClass="field text" id="descricaoCompacta" maxlength="30"
											size="30" value="#{produtoBB.descricaoCompacta}" required="true">
											<f:validateLength maximum="30" />
										</h:inputText>
										<h:message for="descricaoCompacta" styleClass="msgErro" />
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Preço Padão*"></h:outputLabel>
										<h:inputText styleClass="field text" id="precoPadrao" maxlength="9"
											size="12" value="#{produtoBB.precoPadrao}" required="true" dir="rtl" onkeypress="Formata('frmInserirProduto:precoPadrao',9,2);">
											<f:validateDoubleRange minimum="0.01" maximum="999999.99"/>
										</h:inputText>
										<h:message for="precoPadrao" styleClass="msgErro" />
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Preço Promocional"></h:outputLabel>
										<h:inputText styleClass="field text" id="precoPromocional" maxlength="9"
											size="12" value="#{produtoBB.precoPromocional}" required="false" dir="rtl" onkeypress="Formata('frmInserirProduto:precoPromocional',9,2);">
											<f:validateDoubleRange minimum="0.01" maximum="999999.99"/>
										</h:inputText>
										<h:message for="precoPromocional" styleClass="msgErro" />
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Código Externo*"></h:outputLabel>
										<h:inputText styleClass="field text" id="codigoExterno" maxlength="15"
											size="17" value="#{produtoBB.codigoExterno}" required="true">
											<f:validateLength maximum="15" />
										</h:inputText>
										<h:message for="codigoExterno" styleClass="msgErro" />
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Código Automação*"></h:outputLabel>
										<h:inputText styleClass="field text" id="codigoAutomacao" maxlength="15"
											size="17" value="#{produtoBB.codigoAutomacao}" required="true">
											<f:validateLength maximum="15" />
										</h:inputText>
										<h:message for="codigoAutomacao" styleClass="msgErro" />
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Tipo de Produto*"></h:outputLabel>
										<h:selectOneMenu id="tipos" styleClass="field select" style="width: 200px;"
											value="#{produtoBB.idTipoProduto}">		
											<f:selectItems id="tiposSelectItems" value="#{produtoBB.tipos}" />
										</h:selectOneMenu>
										<h:message for="idTipoProduto" styleClass="msgErro" />
									</div>								
									<div>
										<h:outputLabel styleClass="desc" value="Grupo de Produto*"></h:outputLabel>
										<h:selectOneMenu id="grupos" styleClass="field select" style="width: 200px;"
											value="#{produtoBB.idGrupo}">		
											<f:selectItems id="gruposSelectItems" value="#{produtoBB.grupos}" />
										</h:selectOneMenu>
										<h:message for="idGrupo" styleClass="msgErro" />
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Unidade*"></h:outputLabel>
										<h:selectOneMenu id="unidades" styleClass="field select" style="width: 200px;"
											value="#{produtoBB.idUnidade}">		
											<f:selectItems id="unidadeSelectItems" value="#{produtoBB.unidades}" />
										</h:selectOneMenu>
										<h:message for="idUnidade" styleClass="msgErro" />
									</div>								
									<div>
										<h:outputLabel styleClass="desc" value="Imposto*"></h:outputLabel>
										<h:selectOneMenu id="impostos" styleClass="field select" style="width: 200px;"
											value="#{produtoBB.idImposto}">		
											<f:selectItems id="impostosSelectItems" value="#{produtoBB.impostos}" />
										</h:selectOneMenu>
										<h:message for="idImposto" styleClass="msgErro" />
									</div>
								</li>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" immediate="true" id="botaoLimpar" type="reset" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoInserir" action="#{produtoBB.inserir}" value="Inserir"></h:commandButton>
								</li>
							</ul>
						</div>
						<div class="clear"></div>
					</div>
		</h:form>
	  </body>		
	</f:view>
</html>