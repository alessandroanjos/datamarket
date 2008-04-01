<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="https://ajax4jsf.dev.java.net/ajax" prefix="a4j"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
	<html>
		<head>

			<title>INFINITY - DataMarket - Enterprise Server</title>

			<meta http-equiv="pragma" content="no-cache">
			<meta http-equiv="cache-control" content="no-cache">
			<meta http-equiv="expires" content="0">
			<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
			<meta http-equiv="description" content="This is my page">
			
		<script type="text/javascript" language="javascript" src="/EnterpriseServer/js/funcoes.js"></script>
	</head>
		<f:view>
			<t:stylesheet path="/css/style.css"></t:stylesheet>
			<body onload="trocaAba('frmManterPerfil',1,3)">
				<h:form id="frmManterPerfil">
					<div>
						<h:outputText id="titulo" styleClass="label" value="Home > Perfil"></h:outputText>
					</div>
					<br>
					<div>
						<h:messages styleClass="errors" globalOnly="true" showDetail="true" />
					</div>
					<h:panelGrid columns="1" id="formGrid">
						<f:facet name="header">
							<h:outputText styleClass="tituloTabela" value="Manter Perfil" />
						</f:facet>
						<h:panelGrid columns="1" id="formGeral" border="1" width="450">
							<f:facet name="header">
								<h:panelGrid columns="3" styleClass="fundo-cinza-medio" width="450">
									<h:column>
										<h:outputText id="aba1" style="width=100%;" onclick="trocaAba('frmManterPerfil',1,3)" styleClass="aba-cinza-claro" value="Perfil" />
									</h:column>
									<h:column>
										<h:outputText id="aba2" style="width=100%;" onclick="trocaAba('frmManterPerfil',2,3)" styleClass="aba-cinza-claro" value="Operações Associadas" />
									</h:column>
									<h:column>
										<h:outputText id="aba3" style="width=100%;" onclick="trocaAba('frmManterPerfil',3,3)" styleClass="aba-cinza-claro" value="Funcionalidades Associadas" />
									</h:column>
								</h:panelGrid>
							</f:facet>
							<h:panelGrid columns="1" id="formDetalhes">
								<t:div id="Layer1" styleClass="abas-pagto-rec" style="position:relative" style="width: 450px; height: 200px;">
									<h:panelGrid columns="3" id="formGridPerfil">
										<h:outputText styleClass="label" value="Codigo*"></h:outputText>
										<h:inputText styleClass="inputText" id="id" maxlength="2" value="#{perfilBB.id}" size="2" required="true">
											<f:validateLength maximum="2" />
											<f:validator validatorId="LongValidator" />
										</h:inputText>
										<h:message for="id" styleClass="errors" />
										<h:outputText styleClass="label" value="Descrição*"></h:outputText>
										<h:inputText styleClass="inputText" id="descricao" maxlength="20" size="20" value="#{perfilBB.descricao}" required="true">
											<f:validateLength maximum="20" />
										</h:inputText>
										<h:message for="descricao" styleClass="errors" />
										<h:outputText styleClass="label" value="Perfil Superior"></h:outputText>
										<h:selectOneMenu id="perfis" value="#{perfilBB.idPerfilSuperior}">
											<f:selectItems id="perfilSuperiorSelectItems" value="#{perfilBB.perfis}" />
										</h:selectOneMenu>
										<h:message for="perfilSuperior" styleClass="errors" />
										<h:outputText styleClass="label" value="Percentual de Desconto*"></h:outputText>
										<h:inputText styleClass="inputTextRight" id="percentualDesconto" maxlength="6" size="6" value="#{perfilBB.percentualDesconto}" required="true" onkeypress="Formata('frmManterPerfil:percentualDesconto',5,2);">
											<f:validateLength maximum="6" />
											<f:validateDoubleRange minimum="0.00" maximum="100.00" />
											<f:validator validatorId="BigDecimalValidator" />
										</h:inputText>
										<h:message for="percentualDesconto" showSummary="false" styleClass="errors" />
									</h:panelGrid>
								</t:div>
								<t:div id="Layer2" styleClass="abas-pagto-rec" style="position:relative" style="width: 450px; height: 200px;">
									<t:div styleClass="div-auto-scroll" style="width: 100%; height: 200px;">
										<h:selectManyCheckbox id="idListaOperacoesAssociadas" layout="pageDirection" required="true" styleClass="label" value="#{perfilBB.listaOperacoesAssociadas}">
											<f:selectItems value="#{perfilBB.operacoes}" />
										</h:selectManyCheckbox>
									</t:div>
								</t:div>
								<t:div id="Layer3" styleClass="abas-pagto-rec" style="position:relative" style="width: 450px; height: 200px;">
									<t:div styleClass="div-auto-scroll" style="width: 100%; height: 200px;">
										<h:panelGrid columns="1" id="formGridTeste">
											<t:tree2 id="tree" value="#{perfilBB.arvoreFuncionalidades}"
												clientSideToggle="true" varNodeToggler="t" var="node"
												showRootNode="false" preserveToggle="true">
												<f:facet name="root">
													<h:outputText value="#{node.description}" styleClass="label"></h:outputText>
												</f:facet>
												<f:facet name="noRaiz">
													<h:panelGroup>
														<h:outputText value="#{node.description}" styleClass="label"></h:outputText>
													</h:panelGroup>
												</f:facet>
												<f:facet name="no">
													<h:panelGroup>
														<h:selectBooleanCheckbox value="#{node.checked}" />
														<h:outputText value="#{node.description}" styleClass="label" />
													</h:panelGroup>
												</f:facet>
											</t:tree2>
										</h:panelGrid>
									</t:div>
								</t:div>
							</h:panelGrid>
						</h:panelGrid>
					</h:panelGrid>
					<br>
					<h:panelGrid columns="3" id="formGrid1">
						<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" styleClass="inputBtn" id="botaoVoltar" immediate="true" action="#{perfilBB.voltarConsulta}" value="Voltar"></h:commandButton>
						<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" styleClass="inputBtn" id="botaoAlterar" action="#{perfilBB.alterar}" value="Alterar"></h:commandButton>
						<h:commandButton onmouseover="this.className='inputBtnhov'" onmouseout="this.className='inputBtn'" styleClass="inputBtn" id="botaoExcluir" action="#{perfilBB.excluir}" value="Excluir"></h:commandButton>
					</h:panelGrid>
				</h:form>
		</f:view>
	</html>