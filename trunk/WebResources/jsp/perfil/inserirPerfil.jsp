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
							<h:outputText value="#{msgs.inserirPerfil}"></h:outputText>
						</strong>
					</div>				
				</div>
				<div id="content">
					<div class="tabMenu">
						<ul>
							<li id="tabMenuDiv0" class="current" onclick="selecionaMenuTab(this.id)"><span><a href="#">Geral</a></span></li>
							<li id="tabMenuDiv1" onclick="selecionaMenuTab(this.id)"><span><a href="#">Operações</a></span></li>
							<li id="tabMenuDiv2" onclick="selecionaMenuTab(this.id)"><span><a href="#">Funcionalidades</a></span></li>
						</ul>
						<div class="clear"></div>
					</div>
					<div id="primarioContentContainerInternas">
						<h:form id="frmInserirPerfil" binding="#{perfilBB.init}">
							<div id="tabDiv0">
								<ul>
									<li class="normal">
										<div>
											<h:messages errorClass="msgSistemaErro" infoClass="msgSistemaSucesso" globalOnly="true" showDetail="true"/>
										</div>
									</li>

									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Descrição*"></h:outputLabel>
											<h:inputText styleClass="field text" id="descricao" maxlength="20" size="20" value="#{perfilBB.descricao}" required="true">
												<f:validateLength maximum="20" />
											</h:inputText>
										</div>
										<h:message for="descricao" styleClass="msgErro" />									
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Perfil Superior"></h:outputLabel>
											<h:selectOneMenu id="perfis" styleClass="field select" value="#{perfilBB.idPerfilSuperior}" style="width: 200px;">
												<f:selectItems id="perfilSuperiorSelectItems" value="#{perfilBB.perfis}" />
											</h:selectOneMenu>
										</div>
										<h:message for="perfilSuperior" styleClass="msgErro" />	
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Perc. de Desconto*"></h:outputLabel>
											<h:inputText styleClass="field text" dir="rtl" id="percentualDesconto" maxlength="5" size="5" 
												onfocus="this.select();" onclick="this.select();"
												onkeypress="return(formataMoeda(this,'','.',2,event));"
                      							 value="#{perfilBB.percentualDesconto}" required="true">
												<f:validateLength maximum="5" />
												<f:validateDoubleRange minimum="0.00" maximum="99.99" />
												<f:validator validatorId="BigDecimalValidator" />
											</h:inputText>
										</div>
										<h:message for="percentualDesconto" styleClass="msgErro" />							
									</li>
								</ul>
								<br /><br /><br />
							</div>
							<div id="tabDiv1" style="display:none;">
								<ul>
									<li class="normal">
										<div class="div-auto-scroll" style="width:400px !important;">
											<h:selectManyCheckbox id="idListaOperacoesAssociadas" layout="pageDirection" required="false" value="#{perfilBB.listaOperacoesAssociadas}">
												<f:selectItems value="#{perfilBB.operacoes}" />
											</h:selectManyCheckbox>		
										</div>
									</li>
								</ul>
							</div>
							<div id="tabDiv2" style="display:none;">
								<ul>
									<li class="normal">
										<div class="div-auto-scroll tabelaTree" style="width:400px !important;">
											<t:tree2 id="tree" value="#{perfilBB.arvoreFuncionalidades}"
												clientSideToggle="true" varNodeToggler="t" var="node"
												showRootNode="false" preserveToggle="true">
												<f:facet name="root">
													<h:outputLabel value="#{node.description}"></h:outputLabel>
												</f:facet>
												<f:facet name="noRaiz">
													<h:panelGroup>
														<h:outputLabel styleClass="noRaiz" value="#{node.description}"></h:outputLabel>
													</h:panelGroup>
												</f:facet>
												<f:facet name="no">
													<h:panelGroup>
														<h:selectBooleanCheckbox value="#{node.checked}"/>
														<h:outputLabel value="#{node.description}" />
													</h:panelGroup>
												</f:facet>
											</t:tree2>			
										</div>
									</li>								
								</ul>
							</div>
							<ul>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" action="#{perfilBB.resetBB}" id="botaoLimpar" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoInserir" action="#{perfilBB.inserir}" value="Inserir"></h:commandButton>
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