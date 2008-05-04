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
		
		<t:stylesheet path="/EnterpriseServer/css/default.css"></t:stylesheet>
		<t:stylesheet path="/EnterpriseServer/css/form.css"></t:stylesheet>
	</head>

		<h:form id="frmConsultarUsuario">
				<f:subview id="subTopo" rendered="true">
					<jsp:include page="/jsp/topo.jsp?tituloPagina=#{msgs.consultarUsuario}&user=#{loginBB.usuarioLogado.nome}"></jsp:include>	
				</f:subview>					
				<div id="content">
				
						<div id="primarioContentContainerInternas">
							<fieldset>
								<legend>Op��es de filtro:</legend>
								<ul>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="C�digo"></h:outputLabel>
											<h:inputText styleClass="field text ativo" id="id" maxlength="4"
												value="#{usuarioBB.id}" size="4" required="false">
												<f:validateLength maximum="4" />
												<f:validator validatorId="LongValidator"/>
											</h:inputText>
											<h:message for="id" styleClass="msgErro" />
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Nome"></h:outputLabel>
											<h:inputText styleClass="field text" id="nome" maxlength="50" size="50"
												value="#{usuarioBB.nome}">
												<f:validateLength maximum="50" />
											</h:inputText>
											<h:message for="nome" styleClass="msgErro" />
										</div>
										<br />
										<br />
										<div>
											<h:outputLabel styleClass="desc" value="Perfil"></h:outputLabel>
											<h:selectOneMenu id="perfis" styleClass="field select"
												value="#{usuarioBB.idPerfil}" required="true">   
													  <f:selectItems id="perfilSelectItems" 
													  value="#{usuarioBB.perfis}" />   
											</h:selectOneMenu> 
											<h:message for="perfis" styleClass="msgErro" />
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Vendedor"></h:outputLabel>
											<h:selectOneRadio styleClass="field select" id="vendedor" required="true" rendered="true" value="#{usuarioBB.vendedor}" layout="lineDirection">
											    <f:selectItem itemLabel="Sim" itemValue="S"/>
											    <f:selectItem itemLabel="N�o" itemValue="N"/>
											</h:selectOneRadio>
											<h:message for="vendedor" styleClass="msgErro" />
										</div>
									</li>
								</ul>
							</fieldset>	
							<div class="listagem">
								<t:dataTable value="#{usuarioBB.listaUsuarios}"
									var="usuario" rowClasses="rowA,rowB" width="100%">
									<h:column>
										<f:facet name="header">
											<h:outputText value="C�digo" /> 
										</f:facet>
										<h:outputText value="#{usuario.id}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Nome" />
										</f:facet>
										<h:commandLink value="#{usuario.nome}" action="#{usuarioBB.consultar}">
											<f:param name="id" value="#{usuario.id}"/>						
										</h:commandLink>
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Perfil" />
										</f:facet>
										<h:outputText value="#{usuario.perfil.id} - #{usuario.perfil.descricao}" />
									</h:column>									
								</t:dataTable>
							</div>
							<ul>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" id="botaoLimpar" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoConsultar" action="#{usuarioBB.consultar}" value="Consultar"></h:commandButton>
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