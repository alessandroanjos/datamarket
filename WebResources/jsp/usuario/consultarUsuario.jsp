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
							<h:outputText value="#{msgs.consultarUsuario}"></h:outputText>
						</strong>
					</div>				
				</div>	
				<h:form id="frmConsultarUsuario" binding="#{usuarioBB.init}">
					<div id="content">
						<div id="primarioContentContainerInternas">
							<fieldset>
								<legend>Opções de filtro:</legend>
								<ul>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Loja"></h:outputLabel>
											<h:selectOneMenu id="idLoja" style="width: 200px;" value="#{usuarioBB.idLoja}"> 
												<f:selectItems id="lojasSelectItems" value="#{usuarioBB.lojas}" />   
											</h:selectOneMenu>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Código"></h:outputLabel>
											<h:inputText styleClass="field text ativo" id="id" maxlength="4" 
												onfocus="this.select();" onclick="this.select();"
												onkeypress="return SoNumero(event);"
												value="#{usuarioBB.id}" size="4" required="false">
												<f:validateLength maximum="4" />
												<f:validator validatorId="LongValidator"/>
											</h:inputText>
											
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Nome"></h:outputLabel>
											<h:inputText styleClass="field text" id="nome" maxlength="50" size="50"
												value="#{usuarioBB.nome}">
												<f:validateLength maximum="50" />
											</h:inputText>
											
										</div>
										<br />
										<br />
										<div>
											<h:outputLabel styleClass="desc" value="Perfil"></h:outputLabel>
											<h:selectOneMenu id="perfis" styleClass="field select"
												value="#{usuarioBB.idPerfil}"  style="width: 200px;">   
													  <f:selectItems id="perfilSelectItems" 
													  value="#{usuarioBB.perfis}" />   
											</h:selectOneMenu>
											
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Vendedor"></h:outputLabel>
											<h:selectOneRadio  styleClass="field radio vendedor" id="vendedor" 
												value="#{usuarioBB.idTipoUsuario}" layout="lineDirection"  rendered="true">
											    <f:selectItems id="situacao" value="#{usuarioBB.tiposUsuario}" />
											</h:selectOneRadio>
											
										</div>
									</li>
								</ul>
							</fieldset>	
							<div class="listagem">
								<t:dataTable value="#{usuarioBB.listaUsuarios}"
									var="usuario" rowClasses="rowA,rowB" width="95%" renderedIfEmpty="false">
									<h:column>
										<f:facet name="header">
											<h:outputText value="Código" style="width: 60px;" /> 
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
								<ul>
									<li class="normal">
										<div>
											<%@ include file="/jsp/mensagem_erro.jsp"%> <!--  h  messages rendered="#{not usuarioBB.existeRegistros}" errorClass="msgSistemaErro" infoClass="msgSistemaSucesso" globalOnly="true" showDetail="true" /> -->
										</div>
									</li>
								</ul>
							</div>
							<ul>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" action="#{usuarioBB.resetBB}" id="botaoLimpar" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoConsultar" action="#{usuarioBB.consultar}" value="Consultar"></h:commandButton>
								</li>						
							</ul>
						</div>
						<div class="clear"></div>
					</div>
				</h:form>
			</div>
		</body>
	</f:view>
</html>				