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
							<h:outputText value="#{msgs.consultarEstoque}"></h:outputText>
						</strong>
					</div>				
				</div>
		<h:form id="frmConsultarEstoque" binding="#{estoqueBB.init}">
				<div id="content">
				
						<div id="primarioContentContainer">
							<fieldset>
								<legend>Opções de filtro:</legend>
								<ul>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Loja"></h:outputLabel>
											<h:selectOneMenu id="idLoja" style="width: 200px;" value="#{estoqueBB.idLoja}"> 
												<f:selectItems id="lojasSelectItems" value="#{estoqueBB.lojas}" />   
											</h:selectOneMenu>
										</div>
										<br />
										<br />
										<div>
											<h:outputLabel styleClass="desc" value="Código"></h:outputLabel>
											<h:inputText styleClass="field text" id="id" maxlength="2" 
											    onfocus="this.select();" onclick="this.select();"
											    onkeypress="return SoNumero(event);"
												value="#{estoqueBB.id}" size="2" required="false">
												<f:validateLength maximum="2" />
												<f:validator validatorId="LongValidator"/>
											</h:inputText>
											
										</div>
									
										<div>
											<h:outputLabel styleClass="desc" value="Descrição"></h:outputLabel>
											<h:inputText styleClass="field text" id="descricao" maxlength="50" size="50"
												value="#{estoqueBB.descricao}">
												<f:validateLength maximum="50" />
											</h:inputText>
											
										</div>
									</li>
								</ul>
							</fieldset>	
							<div class="listagem">
								<t:dataTable value="#{estoqueBB.estoques}"
									var="estoque" rowClasses="rowA,rowB" width="95%" renderedIfEmpty="false">
									<h:column>
										<f:facet name="header">
											<h:outputText value="Código" /> 
										</f:facet>
										<h:outputText value="#{estoque.pk.id}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Descrição" />
										</f:facet>
										<h:commandLink value="#{estoque.descricao}" action="#{estoqueBB.consultar}">
											<f:param name="id" value="#{estoque.pk.id}"/>
											<f:param name="loja" value="#{estoque.pk.loja.id}"/>						
										</h:commandLink>
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Loja" />
										</f:facet>
										<h:outputText value="#{estoque.pk.loja.nome}" /> 
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
									<h:commandButton styleClass="btTxt" action="#{estoqueBB.resetBB}" id="botaoLimpar" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoConsultar" action="#{estoqueBB.consultar}" value="Consultar"></h:commandButton>
								</li>						
							</ul>
						</div>
						<div class="clear"></div>
					</div>
					
		</h:form>
	  </body>
	</f:view>
</html>