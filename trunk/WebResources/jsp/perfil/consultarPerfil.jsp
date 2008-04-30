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
		
		<t:stylesheet path="/EnterpriseServer/css/default.css"></t:stylesheet>
		<t:stylesheet path="/EnterpriseServer/css/form.css"></t:stylesheet>
	</head>
		
	<f:view>
		<h:form id="frmConsultarPerfil">
				<f:subview id="subTopo" rendered="true">
					<f:loadBundle basename="resources.mensagens" var="msgs"/>
					<jsp:include page="/jsp/topo.jsp?tituloPagina=#{msgs.consultarPerfil}&user=#{loginBB.usuarioLogado.nome}"></jsp:include>	
				</f:subview>					
				<div id="content">
				
						<div id="primarioContentContainer">
							<fieldset>
								<legend>Opções de filtro:</legend>
								<ul>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Código"></h:outputLabel>
											<h:inputText styleClass="field text ativo" id="id" maxlength="4"
												value="#{perfilBB.id}" size="4" required="false">
												<f:validateLength maximum="4" />
												<f:validator validatorId="LongValidator"/>
											</h:inputText>
											<h:message for="id" styleClass="msgErro" />
										</div>
									</li>
									<li>
										<div>
											<h:outputLabel styleClass="desc" value="Descrição"></h:outputLabel>
											<h:inputText styleClass="field text" id="descricao" maxlength="50" size="50"
												value="#{perfilBB.descricao}">
												<f:validateLength maximum="50" />
											</h:inputText>
											<h:message for="descricao" styleClass="msgErro" />
										</div>
									</li>
									<li>
										<div>
											<h:outputLabel styleClass="desc" value="Número IP"></h:outputLabel>
											<h:selectOneMenu id="perfis"  styleClass="field select"
												value="#{perfilBB.idPerfilSuperior}">   
													  <f:selectItems id="perfilSuperiorSelectItems" 
													  value="#{perfilBB.perfis}" />   
											</h:selectOneMenu>
											<h:message for="perfilSuperior" styleClass="msgErro" />
										</div>
									</li>
									<li>
										<div>
											<h:outputLabel styleClass="desc" value="Percentual Desconto"></h:outputLabel>
											<h:inputText styleClass="field text" id="percentualDesconto" maxlength="6" size="6"
												value="#{perfilBB.percentualDesconto}">
												<f:validateLength maximum="6" />
												<f:validator validatorId="BigDecimalValidator"/>
											</h:inputText>
											<h:message for="percentualDesconto" styleClass="msgErro" />
										</div>
									</li>
								</ul>
							</fieldset>	
							<div class="listagem">
								<t:dataTable id="perfis" value="#{perfilBB.perfis}"
									var="perfil" rowClasses="rowA,rowB" width="100%">
									<h:column>
										<f:facet name="header">
											<h:outputText value="Código" /> 
										</f:facet>
										<h:outputText value="#{perfil.id}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Descrição" />
										</f:facet>
										<h:commandLink value="#{perfil.descricao}" action="#{perfilBB.consultar}">
											<f:param name="id" value="#{loja.id}"/>						
										</h:commandLink>
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Perfil Superior" />
										</f:facet>
										<h:outputText value="#{perfil.perfilSuperior.id} - #{perfil.perfilSuperior.descricao}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Percentual de Desconto" />
										</f:facet>
										<h:outputText value="#{perfil.percentualDesconto}" /> 
									</h:column>
								</t:dataTable>																
							</div>
							<ul>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" id="botaoLimpar" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoConsultar" action="#{perfilBB.consultar}" value="Consultar"></h:commandButton>
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