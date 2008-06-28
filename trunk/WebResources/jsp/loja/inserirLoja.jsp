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
			<script type="text/javascript" src="/EnterpriseServer/js/funcoes.js"></script>
			<t:stylesheet path="/css/default.css"></t:stylesheet>
			<t:stylesheet path="/css/form.css"></t:stylesheet>
      	</head>
		<body>			
			<div id="outer">
				<div id="topoGeral">
					<div id="tituloPaginaGeral">
						<strong>
							<h:outputText value="#{msgs.inserirLoja}"></h:outputText>
						</strong>
					</div>				
				</div>
				<div id="content">
					<div id="primarioContentContainerInternas">
						<h:form id="frmInserirLoja">
							<ul>
								<li class="normal">
									<div>
										<h:messages errorClass="msgSistemaErro" infoClass="msgSistemaSucesso" globalOnly="true" showDetail="true"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Código*"></h:outputLabel>
										<h:inputText styleClass="field text ativo" id="id" maxlength="2" onkeypress="return SoNumero(event);"
											value="#{lojaBB.id}" size="2" required="true">
											<f:validateLength maximum="2" />
											<f:validator validatorId="LongValidator" />
										</h:inputText>
									</div>
									<h:message for="id" styleClass="msgErro" />
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Nome*"></h:outputLabel>
										<h:inputText styleClass="field text" id="nome" maxlength="50"
											size="50" value="#{lojaBB.nome}" required="true">
											<f:validateLength maximum="50" />
										</h:inputText>
									</div>
									<h:message for="nome" styleClass="msgErro" />
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Número IP*"></h:outputLabel>
										<h:inputText styleClass="field text" id="ip" maxlength="15"
											size="15" value="#{lojaBB.numeroIp}" required="true" onblur="if (!verificaIP(this.value)) this.value = '';">
											<f:validateLength maximum="15" />
										</h:inputText>
									</div>		
									<h:message for="ip" styleClass="msgErro" />
								</li>
								<li class="normal">															
									<div>
										<h:outputLabel styleClass="desc" value="Porta*"></h:outputLabel>
										<h:inputText styleClass="field text inteiro" id="numeroPorta" maxlength="4" onkeypress="return SoNumero(event);"
											size="4" value="#{lojaBB.numeroPorta}" required="true">
											<f:validateLength maximum="4" />
										</h:inputText>
									</div>
									<h:message for="numeroPorta" styleClass="msgErro" />
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Estoque Atual*"></h:outputLabel>
										<h:selectOneMenu id="idEstoque" styleClass="field select"
											value="#{lojaBB.idEstoqueAtual}" style="width: 200px;">		
											<f:selectItems id="estoqueSelectItems" value="#{lojaBB.estoques}" />
										</h:selectOneMenu>
										<h:message for="idEstoque" styleClass="msgErro" />
									</div>
								</li>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" immediate="true" id="botaoLimpar" type="reset" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoInserir" action="#{lojaBB.inserir}" value="Inserir"></h:commandButton>
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
		