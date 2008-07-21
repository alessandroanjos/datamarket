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
					<h:outputText value="#{msgs.consultarMovimentacaoEstoque}"></h:outputText>
				</strong>
			</div>				
		</div>
		<h:form id="frmConsultarMovimentacaoEstoque">
								
				<div id="content">
				
						<div id="primarioContentContainerInternas">
							<fieldset>
								<legend>Opções de filtro:</legend>
								<ul>
									<li class="normal">
											<div>
												<h:outputLabel styleClass="desc" value="Data Inicial"></h:outputLabel>
												<t:inputText readonly="false" maxlength="10" size="10"
													styleClass="field text" forceId="dataInicio"
													onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }"
													value="#{movimentacaoEstoqueBB.dataInicio}"
													id="dataInicio" />
											</div>	
											<div>	
												<h:outputLabel styleClass="desc" value=" Data Final "></h:outputLabel>
												<t:inputText readonly="false" styleClass="field text"
													maxlength="10" size="10" forceId="dataFinal"
													onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }"
													value="#{movimentacaoEstoqueBB.dataFinal}" id="dataFinal" />
											</div>								
									</li>
								</ul>
							</fieldset>	
							<div class="listagem">
								<t:dataTable value="#{movimentacaoEstoqueBB.movimentacaoEstoque}"
									var="movimentacao" rowClasses="rowA,rowB" width="95%" renderedIfEmpty="false">
									<h:column>
										<f:facet name="header">
											<h:outputText value="N.Entrada" /> 
										</f:facet>
										<h:commandLink value="#{movimentacao.id}" action="#{movimentacaoEstoqueBB.consultar}">
											<f:param name="id" value="#{movimentacao.id}"/>						
										</h:commandLink>
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Dt.Movimentação" />
										</f:facet>
										<h:outputText value="#{movimentacao.dataMovimentacao}" />
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Usuário" />
										</f:facet>
										<h:outputText value="#{movimentacao.codigoUsuario}" /> 
									</h:column>
									
								</t:dataTable>
							</div>
							<ul>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" id="botaoLimpar" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoConsultar" action="#{movimentacaoEstoqueBB.consultar}" value="Consultar"></h:commandButton>
								</li>						
							</ul>
						</div>
						<div class="clear"></div>
					</div>
		</h:form>
	 </body>	
	</f:view>
</html>