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
		
		<t:stylesheet path="/EnterpriseServer/css/default.css"></t:stylesheet>
		<t:stylesheet path="/EnterpriseServer/css/form.css"></t:stylesheet>
	</head>

		<h:form id="frmConsultarClientePagamento">
				<f:subview id="subTopo" rendered="true">
					<jsp:include page="/jsp/topo.jsp?tituloPagina=#{msgs.consultarClientePagamento}&user=#{loginBB.usuarioLogado.nome}"></jsp:include>	
				</f:subview>					
				<div id="content">
				
						<div id="primarioContentContainerInternas">
							<ul>
								<li class="normal">
									<div>
										<h:messages errorClass="msgSistemaErro" infoClass="msgSistemaSucesso" globalOnly="true" showDetail="true"/>
									</div>
								</li>
							</ul>
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
											value="#{clientePagamentoBB.dataInicial}" onkeypress="return SoNumero();" onkeydown="FormataData('frmConsultarClientePagamento:dataInicial');">
											
										</h:inputText>
										<h:message for="dataInicial" styleClass="msgErro"/>
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Data Final"></h:outputLabel>
										<h:inputText styleClass="field text" id="dataFinal" maxlength="10" size="10"
											value="#{clientePagamentoBB.dataFinal}" onkeypress="return SoNumero();" onkeydown="FormataData('frmConsultarClientePagamento:dataFinal');">
											
										</h:inputText>
										<h:message for="dataFinal" styleClass="msgErro"/>
									</div>					
									</li>
								</ul>
							</fieldset>	
							<div class="listagem">
								<t:dataTable value="#{clientePagamentoBB.clientesPagamentos}"
									var="clientePagamento" rowClasses="rowA,rowB" width="100%">
									<h:column>
										<f:facet name="header">
											<h:outputText value="Código" /> 
										</f:facet>
										<h:outputText value="#{clientePagamento.cliente.id}" /> 
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
										<h:outputText value="#{cliente.cpfCnpj}" /> 
									</h:column>		
									<h:column>
										<f:facet name="header">
											<h:outputText value="Data Pagamento" /> 
										</f:facet>
										<h:outputText value="#{clientePagamento.dataPagamento}" /> 
									</h:column>		
									<h:column>
										<f:facet name="header">
											<h:outputText value="Valor Pago" /> 
										</f:facet>
										<h:outputText value="#{clientePagamento.valorPagamento}" /> 
									</h:column>							
								</t:dataTable>
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
					<jsp:include page="/jsp/rodape.jsp"></jsp:include>

				</div>
		</h:form>
	</f:view>
</html>