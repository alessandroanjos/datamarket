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
					<h:outputText value="#{msgs.consultarClientePagamento}"></h:outputText>
				</strong>
			</div>				
		</div>
		<h:form id="frmConsultarClientePagamento" binding="#{clientePagamentoBB.init}">
				
				<div id="content">
				
						<div id="primarioContentContainerInternas">
							<div>
								<h:messages errorClass="msgSistemaErro" infoClass="msgSistemaSucesso" globalOnly="true" showDetail="true"/>
							</div>
							<fieldset>
								<legend>Opções de filtro:</legend>
								<ul>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Cliente"></h:outputLabel>
											<h:selectOneMenu id="clientes" styleClass="field select"
												value="#{clientePagamentoBB.idCliente}" required="false" style="width: 200px;">   
												  <f:selectItems id="clienteSelectItems" value="#{clientePagamentoBB.clientes}" />   
											</h:selectOneMenu>
											<h:message for="clientes" styleClass="msgErro" />
										</div>
									<br />
									<br />
										<div>
										<h:outputLabel styleClass="desc" value="Data Inicial"></h:outputLabel>
										<h:inputText styleClass="field text" id="dataInicial" maxlength="10" size="10"
											value="#{clientePagamentoBB.dataInicial}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }">
											<f:convertDateTime timeZone="GMT-3"/>
										</h:inputText>
										<h:message for="dataInicial" styleClass="msgErro"/>
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Data Final"></h:outputLabel>
										<h:inputText styleClass="field text" id="dataFinal" maxlength="10" size="10"
											value="#{clientePagamentoBB.dataFinal}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }">
											<f:convertDateTime timeZone="GMT-3"/>
										</h:inputText>
										<h:message for="dataFinal" styleClass="msgErro"/>
									</div>					
									</li>
								</ul>
							</fieldset>	
							<div class="listagem">
								<t:dataTable value="#{clientePagamentoBB.clientesPagamentos}"
									var="clientePagamento" rowClasses="rowA,rowB" width="95%" renderedIfEmpty="false">
									<h:column>
										<f:facet name="header">
											<h:outputText value="Código" /> 
										</f:facet>
										<h:outputText value="#{clientePagamento.id}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Nome Cliente" />
										</f:facet>
										<h:commandLink value="#{clientePagamento.cliente.nomeCliente}" action="#{clientePagamentoBB.consultar}">
											<f:param name="id" value="#{clientePagamento.id}"/>						
										</h:commandLink>
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Tipo Pessoa" /> 
										</f:facet>
										<h:outputText value="#{clientePagamento.cliente.tipoPessoa}" /> 
									</h:column>	
									<h:column>
										<f:facet name="header">
											<h:outputText value="CPF/CNPJ" /> 
										</f:facet>
										<h:outputText value="#{clientePagamento.cliente.cpfCnpj}" /> 
									</h:column>		
									<h:column>
										<f:facet name="header">
											<h:outputText value="Data Pagamento" /> 
										</f:facet>
										<h:outputText value="#{clientePagamento.dataPagamento}" /> 
									</h:column>		
									<h:column>
										<f:facet name="header">
											<h:outputText value="Valor Pagamento"/> 
										</f:facet>
										<h:outputText value="#{clientePagamento.valorPagamento}" dir="rtl" /> 
									</h:column>							
								</t:dataTable>
								<!-- <ul>
									<li class="normal">
										<div>
											<h:messages rendered="#{not formaRecebimentoBB.existeRegistros}" errorClass="msgSistemaErro" infoClass="msgSistemaSucesso" globalOnly="true" showDetail="true"/>
										</div>
									</li>
								</ul>
								 -->
							</div>
							<ul>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" id="botaoLimpar" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoConsultar" action="#{clientePagamentoBB.consultar}" value="Consultar"></h:commandButton>
								</li>						
							</ul>
						</div>
						<div class="clear"></div>
					</div>
		</h:form>
	  </body>
	</f:view>
</html>