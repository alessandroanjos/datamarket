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
							<h:outputText value="#{msgs.consultarLoja}"></h:outputText>
						</strong>
					</div>				
				</div>	
				<div id="content">
					<div id="primarioContentContainer">
						<h:form id="frmConsultarLoja">
							<fieldset>
								<legend>Op��es de filtro:</legend>
								<ul>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="C�digo"></h:outputLabel>
											<h:inputText styleClass="field text ativo" id="id" maxlength="4"
												value="#{lojaBB.id}" size="4" required="false">
												<f:validateLength maximum="4" />
												<f:validator validatorId="LongValidator"/>
											</h:inputText>
											<h:message for="id" styleClass="msgErro" />
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Nome"></h:outputLabel>
											<h:inputText styleClass="field text" id="descricao" maxlength="50" size="50"
												value="#{lojaBB.nome}">
												<f:validateLength maximum="50" />
											</h:inputText>
											<h:message for="descricao" styleClass="msgErro" />
										</div>									
										<br />
										<br />	
										<div>
											<h:outputLabel styleClass="desc" value="N�mero IP"></h:outputLabel>
											<h:inputText styleClass="field text" id="numeroIp" maxlength="15" size="15"
												value="#{lojaBB.numeroIp}">
												<f:validateLength maximum="15" />
											</h:inputText>
											<h:message for="numeroIp" styleClass="msgErro" />
										</div>									
										<div>
											<h:outputLabel styleClass="desc" value="N�mero Porta"></h:outputLabel>
											<h:inputText styleClass="field text" id="numeroPorta" maxlength="4" size="4"
												value="#{lojaBB.numeroPorta}">
												<f:validateLength maximum="4" />
											</h:inputText>
											<h:message for="numeroPorta" styleClass="msgErro" />
										</div>
									</li>
								</ul>
							</fieldset>	
							<div class="listagem">
								<t:dataTable id="lojas" value="#{lojaBB.lojas}"
									var="loja" rowClasses="rowA,rowB" width="100%">
									<h:column>
										<f:facet name="header">
											<h:outputText value="C�digo" /> 
										</f:facet>
										<h:outputText value="#{loja.id}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Nome" />
										</f:facet>
										<h:commandLink value="#{loja.nome}" action="#{lojaBB.consultar}">
											<f:param name="id" value="#{loja.id}"/>						
										</h:commandLink>
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="N�mero IP" />
										</f:facet>
										<h:outputText value="#{loja.numeroIp}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="N�mero Porta" />
										</f:facet>
										<h:outputText value="#{loja.numeroPorta}" /> 
									</h:column>
								</t:dataTable>																
							</div>
							<ul>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" id="botaoLimpar" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoConsultar" action="#{lojaBB.consultar}" value="Consultar"></h:commandButton>
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
		