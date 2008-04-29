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
	</head>
	<f:view>
		<h:form id="frmInserirLoja">
				<f:loadBundle basename="resources.mensagens" var="msgs"/>
				<f:subview id="subTopo" rendered="true">
					<jsp:include page="/jsp/topo.jsp?tituloPagina=#{msgs.inserirLoja}&user=#{loginBB.usuarioLogado.nome}"></jsp:include>	
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
											value="#{lojaBB.id}" size="2" required="true">
											<f:validateLength maximum="2" />
											<f:validator validatorId="LongValidator" />
										</h:inputText>
										<h:message for="id" styleClass="msgErro" />
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Nome*"></h:outputLabel>
										<h:inputText styleClass="field text" id="nome" maxlength="50"
											size="50" value="#{lojaBB.nome}" required="true">
											<f:validateLength maximum="50" />
										</h:inputText>
										<h:message for="nome" styleClass="msgErro" />
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Número IP*"></h:outputLabel>
										<h:inputText styleClass="field text" id="ip" maxlength="15"
											size="15" value="#{lojaBB.numeroIp}" required="true">
											<f:validateLength maximum="15" />
										</h:inputText>
										<h:message for="ip" styleClass="msgErro" />
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Porta*"></h:outputLabel>
										<h:inputText styleClass="field text" id="numeroPorta" maxlength="4"
											size="4" value="#{lojaBB.numeroPorta}" required="true">
											<f:validateLength maximum="4" />
										</h:inputText>
										<h:message for="numeroPorta" styleClass="msgErro" />
									</div>
								</li>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" immediate="true" id="botaoLimpar" type="reset" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoInserir" action="#{lojaBB.inserir}" value="Inserir"></h:commandButton>
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
		