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
	<body>
	<div id="outer">
		<div id="topoGeral">
			<div id="tituloPaginaGeral">
				<strong>
					<h:outputText value="#{msgs.consultarTransacao}"></h:outputText>
				</strong>
			</div>				
		</div>		

		<h:form id="frmConsultarTransacao">
				
				<div id="content">
				
						<div id="primarioContentContainer">
							<fieldset>
								<legend>Opções de filtro:</legend>
								<ul>								
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Loja"></h:outputLabel>
											<h:selectOneMenu id="idLoja" style="width: 200px;" value="#{transacaoBB.idLoja}"> 
												<f:selectItems id="lojasSelectItems" value="#{transacaoBB.lojas}" />   
											</h:selectOneMenu>
										</div>									
										<div>
											<h:outputLabel styleClass="desc" value="Componente"></h:outputLabel>
											<h:selectOneMenu id="idComponente" style="width: 200px;" value="#{transacaoBB.idComponente}"> 
												<f:selectItems id="componentesSelectItems" value="#{transacaoBB.componentes}" />   
											</h:selectOneMenu>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Número Transação"></h:outputLabel>
											<h:inputText styleClass="field text ativo" id="nsuTransacao" maxlength="6" onkeypress="return SoNumero(event);"
												value="#{transacaoBB.nsuTransacao}" size="6" required="false">
												<f:validateLength maximum="6" />
												<f:validator validatorId="LongValidator"/>
											</h:inputText>
										</div>	
										<br />															
										<br />
										<div>
											<h:outputLabel styleClass="desc" value="Data Início"></h:outputLabel>
											<h:inputText styleClass="field text" id="dataInicial" maxlength="10" size="10" required="false"
												value="#{transacaoBB.dataInicial}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }">
												<f:convertDateTime timeZone="GMT-3"/>
											</h:inputText>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Data Fim"></h:outputLabel>
											<h:inputText styleClass="field text" id="dataFinal" maxlength="10" size="10" required="false"
												value="#{transacaoBB.dataFinal}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }">
												<f:convertDateTime timeZone="GMT-3"/>
											</h:inputText>										
										</div>
										<div>	
											<h:outputLabel styleClass="desc" value="Situacao"></h:outputLabel>
											<h:selectOneMenu id="idSituacao" style="width: 200px;" value="#{transacaoBB.idSituacao}"> 
												<f:selectItems id="situacaoSelectItems" value="#{transacaoBB.listaSituacao}" />   
											</h:selectOneMenu>
										</div>
									</li>	
								</ul>
							</fieldset>	
							<div class="listagem">
								<t:dataTable id="transacoes" value="#{transacaoBB.transacoes}"
									var="transacao" rowClasses="rowA,rowB" width="90%" renderedIfEmpty="false">
									<h:column>
										<f:facet name="header">
											<h:outputText value="Número Transação" /> 
										</f:facet>
										<h:commandLink value="#{transacao.pk.numeroTransacao}" action="#{transacaoBB.consultar}">
											<f:param name="transacao_loja" value="#{transacao.pk.loja}"/>						
											<f:param name="transacao_componente" value="#{transacao.pk.componente}"/>
											<f:param name="transacao_dataTransacao" value="#{transacao.pk.dataTransacao}"/>
											<f:param name="transacao_numeroTransacao" value="#{transacao.pk.numeroTransacao}"/>
										</h:commandLink>										
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Loja" />
										</f:facet>
										<h:outputText value="#{transacao.pk.loja}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Componente" />
										</f:facet>
										<h:outputText value="#{transacao.pk.componente}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Data Transação" />
										</f:facet>
										<h:outputText value="#{transacao.pk.dataTransacao}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Valor" />
										</f:facet>
										<h:outputText value="#{transacao.valorCupom}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Situação" />
										</f:facet>
										<h:outputText value="#{transacao.situacao}" /> 
									</h:column>									
								</t:dataTable>	
								<div>
									<h:messages rendered="#{not transacaoBB.existeRegistros}" errorClass="msgSistemaErro" infoClass="msgSistemaSucesso" globalOnly="true" showDetail="true"/>
								</div>
							</div>
							<ul>
								<li class="buttons">
									<h:commandButton immediate="true" styleClass="btTxt" id="botaoLimpar" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoConsultar" action="#{transacaoBB.consultar}" value="Consultar"></h:commandButton>
								</li>						
							</ul>
						</div>
						<div class="clear"></div>
					</div>
		</h:form>
	  </body>	
	</f:view>
</html>