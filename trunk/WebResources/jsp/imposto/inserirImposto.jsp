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

		<h:form id="frmInserirImposto">
				<f:subview id="subTopo" rendered="true">
					<jsp:include page="/jsp/topo.jsp?tituloPagina=#{msgs.inserirImposto}&user=#{loginBB.usuarioLogado.nome}"></jsp:include>	
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
										<h:outputLabel styleClass="desc" value="C�digo*"></h:outputLabel>
										<h:inputText styleClass="field text ativo" id="id" maxlength="2"
											value="#{impostoBB.id}" size="2" required="true">
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
											size="50" value="#{impostoBB.descricao}" required="true">
											<f:validateLength maximum="50" />
										</h:inputText>
										<h:message for="nome" styleClass="msgErro" />
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Imposto Impressora*"></h:outputLabel>
										<h:inputText styleClass="field text" id="impostoImpressora" maxlength="2"
											size="2" value="#{impostoBB.impostoImpressora}" required="true">
											<f:validateLength maximum="2" />
										</h:inputText>
										<h:message for="impostoImpressora" styleClass="msgErro" />
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Percentual*"></h:outputLabel>
										<h:inputText styleClass="field text" id="percentual" maxlength="5"
											size="5" value="#{impostoBB.percentual}" required="true">
											<f:validateLength maximum="5" />
											<f:validateDoubleRange minimum="00.01" maximum="99.99"/>
										</h:inputText>
										<h:message for="percentual" styleClass="msgErro" />
									</div>
								</li>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" immediate="true" id="botaoLimpar" type="reset" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoInserir" action="#{impostoBB.inserir}" value="Inserir"></h:commandButton>
								</li>
							</ul>
						</div>
						<div class="clear"></div>
					</div>
					<jsp:include page="/jsp/rodape.jsp"></jsp:include>

		</h:form>
	</f:view>
</html>
		