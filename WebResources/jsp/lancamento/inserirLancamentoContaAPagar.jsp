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
					<h:outputText value="#{msgs.inserirContaAPagar}"></h:outputText>
				</strong>
			</div>				
		</div>
		<h:form id="frmInserirLancamentoContaAPagar" binding="#{lancamentoBB.init}">
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
										<h:outputLabel styleClass="desc" value="Loja*"></h:outputLabel>
										<h:selectOneMenu id="idLoja" styleClass="field select" style="width: 200px;"
											value="#{lancamentoBB.idLoja}" required="false">   
												  <f:selectItems id="lojaSelectItems" 
												  value="#{lancamentoBB.lojas}" />   
										</h:selectOneMenu> 
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Data*"></h:outputLabel>
										<h:inputText readonly="false" maxlength="10" size="10"
											styleClass="field text data"
											value="#{lancamentoBB.dataLancamento}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }"
											id="dataLancamento">
											<f:convertDateTime timeZone="GMT-3"/>
										</h:inputText>
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Fornecedor"></h:outputLabel>
										<h:selectOneMenu id="idFornecedor" styleClass="field select" style="width: 200px;"
											value="#{lancamentoBB.idFornecedor}" required="false">   
												  <f:selectItems id="fornecedorSelectItems" 
												  value="#{lancamentoBB.fornecedores}" />   
										</h:selectOneMenu> 
									</div>
								</li>
								<li class="normal">	
									<div>
										<h:outputLabel styleClass="desc" value="Grupo de Lançamento*"></h:outputLabel>
										<h:selectOneMenu id="idGrupo" styleClass="field select" style="width: 200px;"
											value="#{lancamentoBB.idGrupo}" required="false">   
												  <f:selectItems id="formaSelectItems" 
												  value="#{lancamentoBB.grupos}" />   
										</h:selectOneMenu> 
									</div>								
									<div>
										<h:outputLabel styleClass="desc" value="Descrição*"></h:outputLabel>
										<h:inputText styleClass="field text" id="descricao" maxlength="50" size="50" required="false"
											value="#{lancamentoBB.descricao}">
											<f:validateLength maximum="50" />
										</h:inputText>
									</div>
								</li>								
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Número Documento*"></h:outputLabel>
										<h:inputText styleClass="field text" id="numeroDocumento" maxlength="50" size="50" required="false"
											value="#{lancamentoBB.numeroDocumento}">
											<f:validateLength maximum="50" />
										</h:inputText>
									</div>								
									<div>
										<h:outputLabel styleClass="desc" value="Vencimento*"></h:outputLabel>
										<h:inputText readonly="false" maxlength="10" size="10"
											styleClass="field text data"
											value="#{lancamentoBB.dataVencimento}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }"
											id="dataVencimento">
											<f:convertDateTime timeZone="GMT-3"/>
										</h:inputText>
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Valor*"></h:outputLabel>
										<h:inputText styleClass="field monetario" id="valor" maxlength="10"
											size="11" value="#{lancamentoBB.valor}" dir="rtl"									
											onkeydown="return(BackSpace(this,event));"  onkeypress="return(MascaraMoeda(this,'','.',event));" 
											required="false">
											<f:validateLength maximum="10" />
											<f:validateDoubleRange minimum="0.00" maximum="9999999.99" />
											<f:validator validatorId="BigDecimalValidator" />
										</h:inputText>
									</div>								
								</li>	
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Observação"></h:outputLabel>
										<h:inputTextarea styleClass="field text" id="observacao" style="width: 100%;" cols="100" rows="3" value="#{lancamentoBB.observacao}">
											<f:validateLength maximum="250" />
										</h:inputTextarea>
									</div>
								</li>	
								<li class="buttons">
									<h:commandButton styleClass="btTxt" action="#{lancamentoBB.resetBB}" id="botaoLimpar" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoInserir" action="#{lancamentoBB.inserirContaAPagar}" value="Inserir"></h:commandButton>
								</li>
							</ul>
						</div>
						<div class="clear"></div>
					</div>
		</h:form>
	  </body>	
	</f:view>
</html>
		