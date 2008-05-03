<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="https://ajax4jsf.dev.java.net/ajax" prefix="a4j"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

	<head>

		<title>INFINITY - DataMarket - Enterprise Server</title>

		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
		
		<script type="text/javascript" src="/EnterpriseServer/js/jquery.js"></script>
		<script type="text/javascript" src="/EnterpriseServer/js/global.js"></script>
		<t:stylesheet path="/EnterpriseServer/css/form.css"></t:stylesheet>
		<t:stylesheet path="/EnterpriseServer/css/default.css"></t:stylesheet>
		<t:stylesheet path="/EnterpriseServer/css/style.css"></t:stylesheet>
	</head>
	<f:view>
		

		<h:form id="frmManterPerfil">
				<f:loadBundle basename="resources.mensagens" var="msgs"/>
				<f:subview id="subTopo" rendered="true">
					<jsp:include page="/jsp/topo.jsp?tituloPagina=#{msgs.manterPerfil}&user=#{loginBB.usuarioLogado.nome}"></jsp:include>	
				</f:subview>					
				<div id="content">
					<div id="tabMenu">
						<ul>
							<li id="tabMenuDiv0" class="current" onclick="selecionaMenuTab(this.id)"><span><a href="#">Geral</a></span></li>
							<li id="tabMenuDiv1" onclick="selecionaMenuTab(this.id)"><span><a href="#">Opera��es</a></span></li>
							<li id="tabMenuDiv2" onclick="selecionaMenuTab(this.id)"><span><a href="#">Funcionalidades</a></span></li>
						</ul>
						<div class="clear"></div>
					</div>
					<div id="primarioContentContainerInternas">
						<div id="tabDiv0">
							<ul>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="C�digo*"></h:outputLabel>
										<h:inputText styleClass="field text ativo" readonly="true" id="id"
											maxlength="2" value="#{perfilBB.id}" size="2"
											required="true">
											<f:validateLength maximum="2" />
											<f:validator validatorId="LongValidator" />
										</h:inputText>
										<h:message for="id" styleClass="msgErro" />										
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Descri��o*"></h:outputLabel>
										<h:inputText styleClass="field text" id="descricao" maxlength="20" size="20" value="#{perfilBB.descricao}" required="true">
											<f:validateLength maximum="20" />
										</h:inputText>
										<h:message for="descricao" styleClass="msgErro" />									
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Perfil Superior"></h:outputLabel>
										<h:selectOneMenu id="perfis" styleClass="field select" value="#{perfilBB.idPerfilSuperior}">
											<f:selectItems id="perfilSuperiorSelectItems" value="#{perfilBB.perfis}" />
										</h:selectOneMenu>
										<h:message for="perfilSuperior" styleClass="msgErro" />	
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Percentual de Desconto*"></h:outputLabel>
										<h:inputText styleClass="text field" dir="rtl" id="percentualDesconto" maxlength="6" size="6" value="#{perfilBB.percentualDesconto}" required="true" onkeypress="Formata('frmManterPerfil:percentualDesconto',5,2);">
											<f:validateLength maximum="6" />
											<f:validateDoubleRange minimum="0.00" maximum="100.00" />
											<f:validator validatorId="BigDecimalValidator" />
										</h:inputText>
										<h:message for="percentualDesconto" styleClass="msgErro" />							
									</div>
								</li>
							</ul>
						</div>
						<div id="tabDiv1">
							<ul>
								<li class="normal">
									<div class="div-auto-scroll" style="width: 100%; height: 163px;">
										<h:selectManyCheckbox id="idListaOperacoesAssociadas" layout="pageDirection" required="false" styleClass="field select" value="#{perfilBB.listaOperacoesAssociadas}">
											<f:selectItems value="#{perfilBB.operacoes}" />
										</h:selectManyCheckbox>		
									</div>
								</li>
							</ul>
						</div>
						<div id="tabDiv2">
							<ul>
								<li class="normal">
									<div class="div-auto-scroll" style="width: 100%; height: 163px;">
										<t:tree2 id="tree" value="#{perfilBB.arvoreFuncionalidades}"
											clientSideToggle="true" varNodeToggler="t" var="node"
											showRootNode="false" preserveToggle="true">
											<f:facet name="root">
												<h:outputLabel value="#{node.description}"></h:outputLabel>
											</f:facet>
											<f:facet name="noRaiz">
												<h:panelGroup>
													<h:outputLabel value="#{node.description}"></h:outputLabel>
												</h:panelGroup>
											</f:facet>
											<f:facet name="no">
												<h:panelGroup styleClass="field select">
													<h:selectBooleanCheckbox value="#{node.checked}"/>
													<h:outputLabel value="#{node.description}" />
												</h:panelGroup>
											</f:facet>
										</t:tree2>			
									</div>
								</li>
							</ul>
						</div>
						<div id="primarioContentContainerInternas">
							<ul>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" id="botaoAlterar" action="#{perfilBB.alterar}" value="Alterar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoExcluir" action="#{perfilBB.excluir}" value="Excluir"></h:commandButton>
								</li>
							</ul>
						</div>
					</div>
					<div class="clear"></div>
				</div>
				<jsp:include page="/jsp/rodape.jsp"></jsp:include>
			</div>	
		</h:form>		
	</f:view>
</html>