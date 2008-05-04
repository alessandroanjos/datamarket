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

		<h:form id="frmInserirGrupoProduto">
				<f:subview id="subTopo" rendered="true">
					<jsp:include page="/jsp/topo.jsp?tituloPagina=#{msgs.inserirGrupoProduto}&user=#{loginBB.usuarioLogado.nome}"></jsp:include>	
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
										<h:outputLabel styleClass="desc" value="Número da Liberação de Dados Atual"></h:outputLabel>
										<h:inputText styleClass="field text ativo" id="numeroLote" maxlength="8"
											value="#{loteBB.numeroLote}" size="8" required="true">
											<f:validateLength maximum="8" />
											<f:validator validatorId="LongValidator" />
										</h:inputText>
										<h:message for="numeroLote" styleClass="msgErro" />
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Quantidade de Registros para Liberar"></h:outputLabel>
										<h:inputText styleClass="field text" id="qtdRegistros" maxlength="8"
											size="8" value="#{loteBB.qtdRegistros}" required="true">
											<f:validateLength maximum="50" />
										</h:inputText>
										<h:message for="qtdRegistros" styleClass="msgErro" />
									</div>
								</li>								
								<li class="buttons">
									<h:commandButton styleClass="btTxt" id="botaoConsultar" action="#{loteBB.consultarLote}" value="Consultar Próxima Liberação"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoLiberar" action="#{loteBB.liberarLote}" value="Liberar Dados"></h:commandButton>
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