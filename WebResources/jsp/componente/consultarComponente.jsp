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
			<script type="text/javascript" src="/EnterpriseServer/js/global.js"></script>
			<script type="text/javascript" src="/EnterpriseServer/js/funcoes.js"></script>
			<t:stylesheet path="/css/default.css"></t:stylesheet>
			<t:stylesheet path="/css/form.css"></t:stylesheet>     
		</head>
		<body onload="exibirMensagemErro();">
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
						<h:form id="frmConsultarComponente" binding="#{componenteBB.init}">
							<fieldset>
								<legend>Op��es de filtro:</legend>
								<ul>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Loja"></h:outputLabel>
											<h:selectOneMenu id="idLoja" style="width: 200px;" value="#{componenteBB.idLoja}"> 
												<f:selectItems id="lojasSelectItems" value="#{componenteBB.lojas}" />   
											</h:selectOneMenu>
										</div>
										<br />
										<br />
										<div>
											<h:outputLabel styleClass="desc" value="C�digo"></h:outputLabel>
											<h:inputText styleClass="field text" id="id" maxlength="4" onfocus="this.select();" onclick="this.select();" onkeypress="return SoNumero(event);"
												value="#{componenteBB.id}" size="4" required="false">
												<f:validateLength maximum="4" />
												<f:validator validatorId="LongValidator"/>
											</h:inputText>
											
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Descri��o"></h:outputLabel>
											<h:inputText styleClass="field text" id="descricao" maxlength="50" size="50"
												value="#{componenteBB.descricao}">
												<f:validateLength maximum="50" />
											</h:inputText>
											
										</div>
									</li>
								</ul>
							</fieldset>	
							<div class="listagem">
								<t:dataTable id="componentes" value="#{componenteBB.componentes}"
									var="componente" rowClasses="rowA,rowB" width="95%" renderedIfEmpty="false">
									<h:column>
										<f:facet name="header">
											<h:outputText value="C�digo" /> 
										</f:facet>
										<h:commandLink value="#{componente.id}" action="#{componenteBB.consultar}">
											<f:param name="id" value="#{componente.id}"/>						
										</h:commandLink>
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Descri��o" />
										</f:facet>
										<h:outputText value="#{componente.descricao}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Loja" />
										</f:facet>
										<h:outputText value="#{componente.loja.nome}" /> 
										</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Tipo Componente" />
										</f:facet>
										<h:outputText rendered="#{componente.tipoComponente == 1}" value="PDV" /> 
										<h:outputText rendered="#{componente.tipoComponente == 2}" value="AV" /> 
									</h:column>
								</t:dataTable>
								<ul>
									<li class="normal">
										<div>
											<%@ include file="/jsp/mensagem_erro.jsp"%>
										</div>
									</li>
								</ul>
							</div>
							<ul>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" action="#{componenteBB.resetBB}" id="botaoLimpar" value="Limpar"></h:commandButton>
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