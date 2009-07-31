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
	<body>
	<div id="outer">
		<div id="topoGeral">
			<div id="tituloPaginaGeral">
				<strong>
					<h:outputText value="#{msgs.consultarLancamento}"></h:outputText>
				</strong>
			</div>				
		</div>		

		<h:form id="frmConsultarLancamento" binding="#{lancamentoBB.init}">
				
				<div id="content">
				
						<div id="primarioContentContainer">
							<fieldset>
								<legend>Opções de filtro:</legend>
								<ul>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Loja"></h:outputLabel>
											<h:selectOneMenu id="idLoja" styleClass="field select" style="width: 200px;"
												value="#{lancamentoBB.idLoja}" required="false">   
													  <f:selectItems id="lojaSelectItems" 
													  value="#{lancamentoBB.lojas}" />   
											</h:selectOneMenu> 
										</div>									
										<div>
											<h:outputLabel styleClass="desc" value="Fornecedor"></h:outputLabel>
											<h:selectOneMenu id="idFornecedor" styleClass="field select" style="width: 200px;"
												value="#{lancamentoBB.idFornecedor}" required="false">   
													  <f:selectItems id="fornecedorSelectItems" 
													  value="#{lancamentoBB.fornecedores}" />   
											</h:selectOneMenu> 
										</div>
										<br />
										<br />
										<div>
											<h:outputLabel styleClass="desc" value="Grupo de Lançamento"></h:outputLabel>
											<h:selectOneMenu id="idGrupo" styleClass="field select" style="width: 200px;"
												value="#{lancamentoBB.idGrupo}" required="false">   
													  <f:selectItems id="formaSelectItems" 
													  value="#{lancamentoBB.grupos}" />   
											</h:selectOneMenu> 
										</div>								
										<div>
											<h:outputLabel styleClass="desc" value="Descrição"></h:outputLabel>
											<h:inputText styleClass="field text" id="descricao" maxlength="50" size="50" required="false"
												value="#{lancamentoBB.descricao}">
												<f:validateLength maximum="50" />
											</h:inputText>
										</div>
										<br />
										<br />
										<div>
											<h:outputLabel styleClass="desc" value="Data Início"></h:outputLabel>
											<h:inputText styleClass="field text" id="dataInicial" maxlength="10" size="10" required="false"
												value="#{lancamentoBB.dataInicial}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }">
												<f:convertDateTime timeZone="GMT-3"/>
											</h:inputText>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Data Fim"></h:outputLabel>
											<h:inputText styleClass="field text" id="dataFinal" maxlength="10" size="10" required="false"
												value="#{lancamentoBB.dataFinal}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }">
												<f:convertDateTime timeZone="GMT-3"/>
											</h:inputText>										
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Tipo Lançamento"></h:outputLabel>
											<h:selectOneRadio  styleClass="field select"  id="tipoLancamento"  
												value="#{lancamentoBB.tipoLancamento}"  layout="lineDirection" rendered="true">
											    <f:selectItem itemLabel="à Pagar" itemValue="D" />
											    <f:selectItem itemLabel="à Receber" itemValue="C"/>
											</h:selectOneRadio>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Situação"></h:outputLabel>
											<h:selectOneMenu id="idSituacao" styleClass="field select" style="width: 200px;"
												value="#{lancamentoBB.idSituacao}" required="false">   
													  <f:selectItems id="siuacaoSelectItems" 
													  value="#{lancamentoBB.listaSituacao}" />   
											</h:selectOneMenu> 
										</div>
									</li>
								</ul>
							</fieldset>	
							<div class="listagem">
								<t:dataTable id="lancamentos" value="#{lancamentoBB.lancamentos}"
									var="lancamento" rowClasses="rowA,rowB" width="90%" renderedIfEmpty="false">
									<h:column>
										<f:facet name="header">
											<h:outputText value="Código" /> 
										</f:facet>
										<h:commandLink value="#{lancamento.id}" action="#{lancamentoBB.consultar}">
											<f:param name="id" value="#{lancamento.id}"/>						
										</h:commandLink>
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Loja" />
										</f:facet>
										<h:outputText value="#{lancamento.loja.nome}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Fornecedor" />
										</f:facet>
										<h:outputText value="#{lancamento.fornecedor.nomeFantasia}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Grupo de Lcto." />
										</f:facet>
										<h:outputText value="#{lancamento.grupo.descricao}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Tipo Lançam." />
										</f:facet>
										<h:outputText rendered="#{lancamento.tipoLancamento == 'D'}" value="à Pagar" /> 
										<h:outputText rendered="#{lancamento.tipoLancamento == 'C'}" value="à Receber" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Valor" />
										</f:facet>
										<h:outputText value="#{lancamento.valor}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Data Vencimento" />
										</f:facet>
										<h:outputText value="#{lancamento.dataVencimento}" /> 
									</h:column>								
									<h:column>
										<f:facet name="header">
											<h:outputText value="Situação" />
										</f:facet>
										<h:outputText rendered="#{lancamento.situacao == 'A'}" value="Aberto" /> 
										<h:outputText rendered="#{lancamento.situacao == 'P'}" value="Pago Parcial" /> 
										<h:outputText rendered="#{lancamento.situacao == 'F'}" value="Finalizado" /> 
										<h:outputText rendered="#{lancamento.situacao == 'C'}" value="Cancelado" /> 
									</h:column>																
								</t:dataTable>	
								<div>
									<%@ include file="/jsp/mensagem_erro.jsp"%> <!--  h  messages rendered="#{not lancamentoBB.existeRegistros}" errorClass="msgSistemaErro" infoClass="msgSistemaSucesso" globalOnly="true" showDetail="true" /> -->
								</div>
							</div>
							<ul>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" action="#{lancamentoBB.resetBB}" id="botaoLimpar" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoConsultar" action="#{lancamentoBB.consultar}" value="Consultar"></h:commandButton>
								</li>						
							</ul>
						</div>
						<div class="clear"></div>
					</div>
		</h:form>
	  </body>	
	</f:view>
</html>