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
		

		<h:form id="frmManterFormaRecebimento">
				<f:loadBundle basename="resources.mensagens" var="msgs"/>
				<f:subview id="subTopo" rendered="true">
					<jsp:include page="/jsp/topo.jsp?tituloPagina=#{msgs.manterFormaRecebimento}&user=#{loginBB.usuarioLogado.nome}"></jsp:include>	
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
										<h:inputText styleClass="field text ativo" readonly="true" id="id" maxlength="2"
											value="#{formaRecebimentoBB.id}" size="3" rendered="true">
											<f:validateLength maximum="2" />
											<f:validator validatorId="LongValidator"/>
										</h:inputText>
										<h:message for="id" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Descri��o*"></h:outputLabel>
										<h:inputText styleClass="field text" id="descricao" maxlength="50" size="50" rendered="true"
											value="#{formaRecebimentoBB.descricao}">
											<f:validateLength maximum="50" />
										</h:inputText>
										<h:message for="descricao" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Recebimento na Impressora"></h:outputLabel>
										<h:inputText styleClass="field text" id="recebimentoImpressora" maxlength="50" size="50"
											value="#{formaRecebimentoBB.recebimentoImpressora}">
											<f:validateLength maximum="50" />
										</h:inputText>
										<h:message for="recebimentoImpressora" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Abre Gaveta*"></h:outputLabel>
										<h:selectOneRadio  styleClass="field radio"  id="abrirGaveta" 
											value="#{formaRecebimentoBB.abrirGaveta}"  layout="lineDirection" rendered="true">
										    <f:selectItem itemLabel="Sim" itemValue="S" />
										    <f:selectItem itemLabel="N�o" itemValue="N"/>
										</h:selectOneRadio>
										<h:message for="abrirGaveta" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Valor Limite Sangria"></h:outputLabel>
										<h:inputText styleClass="field text" id="valorLimiteSangria" maxlength="10" size="10"
											value="#{formaRecebimentoBB.valorLimiteSangria}" dir="rtl" required="true" onkeypress="Formata('frmManterFormaRecebimento:valorLimiteSangria',9,2);">
											<f:validateLength maximum="10" />
											<f:validateDoubleRange  minimum="0.00" maximum="9999999.99"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
										<h:message for="valorLimiteSangria" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Inicio Validade"></h:outputLabel>
										<t:inputCalendar readonly="true" styleClass="field text" forceId="dataInicioValidade" 
											value="#{formaRecebimentoBB.dataInicioValidade}" id="dataInicioValidade" renderAsPopup="true" 
											popupDateFormat="dd/MM/yyyy"/>
										<h:message for="dataInicioValidade" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Final Validade"></h:outputLabel>
										<t:inputCalendar readonly="true" styleClass="field text" forceId="dataFimValidade" 
											value="#{formaRecebimentoBB.dataFimValidade}" id="dataFimValidade" renderAsPopup="true" 
											popupDateFormat="dd/MM/yyyy"/>
										<h:message for="dataFimValidade" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Valor Maximo Troco"></h:outputLabel>
										<h:inputText styleClass="field text" id="valorMaxTroco" maxlength="15" size="15"
											value="#{formaRecebimentoBB.valorMaxTroco}" required="true" dir="rtl" onkeypress="Formata('frmManterFormaRecebimento:valorMaxTroco',14,2);">
											<f:validateLength maximum="15" />
											<f:validateDoubleRange  minimum="0.00" maximum="9999999.99"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
										<h:message for="valorMaxTroco" styleClass="msgErro"/>		
									</div>
								</li>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" immediate="true" id="botaoLimpar" type="reset" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoAlterar" action="#{formaRecebimentoBB.alterar}" value="Alterar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoExcluir" action="#{formaRecebimentoBB.excluir}" value="Excluir"></h:commandButton>									
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
		