<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="https://ajax4jsf.dev.java.net/ajax" prefix="a4j"%>
<%@taglib uri="http://yui4jsf.sourceforge.net" prefix="yui"%>


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
	</head>
	<f:view>
			<h:form id="frmInserirUsuario">
				<f:loadBundle basename="resources.mensagens" var="msgs"/>
				<f:subview id="subTopo" rendered="true">
					<jsp:include page="/jsp/topo.jsp?tituloPagina=#{msgs.inserirUsuario}&user=#{loginBB.usuarioLogado.nome}"></jsp:include>	
				</f:subview>	
					<div id="content">
						<div id="primarioContentContainerInternas">
							<ul>
								<li class="normal">
									<div>
										<h:messages errorClass="msgSistemaErro" infoClass="msgSistemaSucesso" globalOnly="true" showDetail="true"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Código*"></h:outputLabel>
										<h:inputText styleClass="field text ativo" id="id" maxlength="2"
											value="#{usuarioBB.id}" size="3" required="true">
											<f:validateLength maximum="2" />
											<f:validator validatorId="LongValidator"/>
										</h:inputText>
										<h:message for="id" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Nome*"></h:outputLabel>
										<h:inputText styleClass="field text" id="nome" maxlength="50" size="50" required="true"
											value="#{usuarioBB.nome}">
											<f:validateLength maximum="50" />
										</h:inputText>
										<h:message for="nome" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Senha*"></h:outputLabel>
										<h:inputSecret styleClass="field text" id="senha" redisplay="true" maxlength="20" size="25"
											value="#{usuarioBB.senha}" required="true">
											<f:validateLength maximum="20" />
											<f:validator validatorId="LongValidator"/>
										</h:inputSecret>
										<h:message for="senha" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Perfil*"></h:outputLabel>
										<h:selectOneMenu id="perfis" styleClass="field select"
											value="#{usuarioBB.idPerfil}" required="true">   
												  <f:selectItems id="perfilSelectItems" 
												  value="#{usuarioBB.perfis}" />   
										</h:selectOneMenu>
										<h:message for="perfis" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Vendedor*"></h:outputLabel>
										<h:selectOneRadio styleClass="field select" id="vendedor" required="true" rendered="true" value="#{usuarioBB.vendedor}" layout="lineDirection">
										    <f:selectItem itemLabel="Sim" itemValue="S"/>
										    <f:selectItem itemLabel="Não" itemValue="N"/>
										</h:selectOneRadio>
										<h:message for="vendedor" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Valor Comissão"></h:outputLabel>
										<h:inputText styleClass="field text" id="comissao" maxlength="5" size="5"
											value="#{usuarioBB.comissao}" required="true" dir="rtl" onkeypress="Formata('frmInserirUsuario:comissao',5,2);">
											<f:validateLength maximum="5" />
											<f:validateDoubleRange  minimum="0.00" maximum="100.00"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
										<h:message for="comissao" styleClass="msgErro"/>		
									</div>
								</li>
								<li class="normal">
									<div style="width: 100%;">
										<h:outputLabel styleClass="desc" value="Lojas Associadas"></h:outputLabel>
										<t:div styleClass="listagem" style="width: 100%; height: 150px;">
											<h:selectManyCheckbox id="idListaLojasAssociadas" layout="pageDirection" required="false" styleClass="field select"
												value="#{usuarioBB.listaLojasAssociadas}" >
													<f:selectItems value="#{usuarioBB.lojas}"/>
											</h:selectManyCheckbox>
										</t:div>
										<h:message for="idListaLojasAssociadas" styleClass="msgErro"/>
									</div>
								</li>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" immediate="true" id="botaoLimpar" type="reset" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoInserir" action="#{usuarioBB.inserir}" value="Inserir"></h:commandButton>
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
