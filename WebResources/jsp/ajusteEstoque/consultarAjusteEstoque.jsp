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
	<div id="outer">
		<div id="topoGeral">
			<div id="tituloPaginaGeral">
				<strong>
					<h:outputText value="#{msgs.consultarAjusteEstoque}"></h:outputText>
				</strong>
			</div>				
		</div>
		<h:form id="frmConsultarAjusteEstoque">
				
				<div id="content">
				
						<div id="primarioContentContainerInternas">
							<fieldset>
								<legend>Opções de filtro:</legend>
								<ul>
									<li class="normal">
											<div>
												<h:outputLabel styleClass="desc" value="Data Inicio"></h:outputLabel>
												<t:inputText readonly="false" maxlength="10" size="10"
													styleClass="field text" forceId="dataInicio"
													value="#{ajusteEstoqueBB.dataInicio}"
													onkeypress="FormataData('frmConsultarAjusteEstoque:dataInicio');"
													id="dataInicio" />
											</div>	
											<div>	
												<h:outputLabel styleClass="desc" value=" Data Final "></h:outputLabel>
												<t:inputText readonly="false" styleClass="field text"
													maxlength="10" size="10" forceId="dataFinal"
													onkeypress="FormataData('frmConsultarAjusteEstoque:dataFinal');"
													value="#{ajusteEstoqueBB.dataFinal}" id="dataFinal" />
											</div>
											<div>
												<h:outputLabel styleClass="desc" value="Estoque"></h:outputLabel>
												<h:selectOneMenu id="idEstoque" styleClass="field text"
													value="#{ajusteEstoqueBB.idEstoque}">
													<f:selectItems id="estoqueSelectItems"
														value="#{ajusteEstoqueBB.estoques}" />
												</h:selectOneMenu>
											</div>								
									</li>
								</ul>
							</fieldset>	
							<div class="listagem">
								<t:dataTable value="#{ajusteEstoqueBB.ajusteEstoques}"
									var="ajuste" rowClasses="rowA,rowB" width="100%">
									<h:column>
										<f:facet name="header">
											<h:outputText value="N.Ajuste" /> 
										</f:facet>
										<h:commandLink value="#{ajuste.id}" action="#{ajusteEstoqueBB.consultar}">
											<f:param name="id" value="#{ajuste.id}"/>						
										</h:commandLink>
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Data" />
										</f:facet>
										<h:outputText value="#{ajuste.data}" />
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Usuário" />
										</f:facet>
										<h:outputText value="#{ajuste.codigoUsuario}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Estoque" />
										</f:facet>
										<h:outputText value="#{ajuste.estoque.descricao}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Produto" />
										</f:facet>
										<h:outputText value="#{ajuste.produto.id}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="" />
										</f:facet>
										<h:outputText value="#{ajuste.produto.descricaoCompleta}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Qtd.Antes" />
										</f:facet>
										<h:outputText value="#{ajuste.quantidadeAntes}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Qtd.Depois" />
										</f:facet>
										<h:outputText value="#{ajuste.quantidadeDepois}" /> 
									</h:column>
									
								</t:dataTable>
							</div>
							<ul>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" id="botaoLimpar" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoConsultar" action="#{ajusteEstoqueBB.consultar}" value="Consultar"></h:commandButton>
								</li>						
							</ul>
						</div>
						<div class="clear"></div>
					</div>

		</h:form>
	</f:view>
</html>