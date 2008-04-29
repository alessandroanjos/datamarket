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
		

		<h:form id="frmManterPlanoPagamento">
				<f:loadBundle basename="resources.mensagens" var="msgs"/>
				<f:subview id="subTopo" rendered="true">
					<jsp:include page="/jsp/topo.jsp?tituloPagina=#{msgs.manterPlanoPagamento}&user=#{loginBB.usuarioLogado.nome}"></jsp:include>	
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
											value="#{planoPagamentoBB.id}" size="3" readonly="true" rendered="true">
											<f:validateLength maximum="2" />
											<f:validator validatorId="LongValidator"/>
										</h:inputText>
										<h:message for="id" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Descrição*"></h:outputLabel>
										<h:inputText styleClass="field text" id="descricao" maxlength="50" size="50" rendered="true"
											value="#{planoPagamentoBB.descricao}">
											<f:validateLength maximum="50" />
										</h:inputText>
										<h:message for="descricao" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Situação"></h:outputLabel>
										<h:selectOneRadio  styleClass="field radio" id="status" 
											value="#{planoPagamentoBB.status}" layout="lineDirection">
										    <f:selectItems id="situacao" value="#{planoPagamentoBB.situacaoItens}" />
							
										</h:selectOneRadio>
										<h:message for="status" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Valor Mínimo"></h:outputLabel>
										<h:inputText styleClass="field text" id="valorMinimo" maxlength="10" size="10"
											value="#{planoPagamentoBB.valorMinimo}" dir="rtl" required="true" onkeypress="Formata('frmManterPlanoPagamento:valorMinimo',9,2);">
											<f:validateLength maximum="10" />
											<f:validateDoubleRange  minimum="0.01" maximum="9999999.99"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
										<h:message for="valorMinimo" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Valor Máximo"></h:outputLabel>
										<h:inputText styleClass="field text" id="valorMaximo" maxlength="10" size="10"
											value="#{planoPagamentoBB.valorMaximo}" dir="rtl" required="true" onkeypress="Formata('frmManterPlanoPagamento:valorMaximo',9,2);">
											<f:validateLength maximum="10" />
											<f:validateDoubleRange  minimum="0.01" maximum="9999999.99"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
										<h:message for="valorMaximo" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Percentual Desconto"></h:outputLabel>
										<h:inputText styleClass="field text" id="percentualDesconto" maxlength="5" size="5"
											value="#{planoPagamentoBB.percDesconto}" dir="rtl" required="true" onkeypress="Formata('frmManterPlanoPagamento:percentualDesconto',5,2);">
											<f:validateLength maximum="5" />
											<f:validateDoubleRange  minimum="0.00" maximum="100.00"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
										<h:message for="percentualDesconto" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Percentual Acréscimo"></h:outputLabel>
										<h:inputText styleClass="field text" id="percentualAcrescimo" maxlength="5" size="5"
											value="#{planoPagamentoBB.percAcrescimo}" dir="rtl" required="true" onkeypress="Formata('frmManterPlanoPagamento:percentualAcrescimo',5,2);">
											<f:validateLength maximum="5" />
											<f:validateDoubleRange  minimum="0.00" maximum="100.00"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
										<h:message for="percentualAcrescimo" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Inicio Validade"></h:outputLabel>
										<t:inputCalendar readonly="true" styleClass="field text" forceId="dataInicioValidade" 
											value="#{planoPagamentoBB.dataInicioValidade}" id="dataInicioValidade" renderAsPopup="true" 
											popupDateFormat="dd/MM/yyyy"/>
										<h:message for="dataInicioValidade" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Final Validade"></h:outputLabel>
										<t:inputCalendar readonly="true" styleClass="field text" forceId="dataFimValidade" 
											value="#{planoPagamentoBB.dataFimValidade}" id="dataFimValidade" renderAsPopup="true" 
											popupDateFormat="dd/MM/yyyy"/>
										<h:message for="dataFimValidade" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Forma de Recebimento Associada"></h:outputLabel>
										<h:selectOneMenu id="idForma" 
											value="#{planoPagamentoBB.idForma}">   
												  <f:selectItems id="formaSelectItems" 
												  value="#{planoPagamentoBB.formas}" />   
										</h:selectOneMenu>
										<h:message for="idForma" styleClass="msgErro"/>		
									</div>
								</li>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" immediate="true" id="botaoLimpar" type="reset" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoAlterar" action="#{planoPagamentoBB.alterar}" value="Alterar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoExcluir" action="#{planoPagamentoBB.excluir}" value="Excluir"></h:commandButton>
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
		
