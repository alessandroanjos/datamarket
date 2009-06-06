<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="https://ajax4jsf.dev.java.net/ajax" prefix="a4j"%>
<%@taglib uri="http://yui4jsf.sourceforge.net" prefix="yui"%>


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
					<h:outputText value="#{msgs.manterMovimentacaoBancaria}"></h:outputText>
				</strong>
			</div>				
		</div>
		<h:form id="frmManterMovimentacaoBancaria">
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
										<h:outputLabel styleClass="desc" value="Conta*"></h:outputLabel>
										<h:selectOneMenu id="idConta" styleClass="field select"
											value="#{movimentacaoBancariaBB.idConta}" style="width: 400px;">		
											<f:selectItems id="contasSelectItems" value="#{movimentacaoBancariaBB.contas}" />
										</h:selectOneMenu>
										
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Tipo"></h:outputLabel>
										<h:selectOneRadio  styleClass="field select"  id="tipo"  required="true"
											value="#{movimentacaoBancariaBB.tipo}"  layout="lineDirection" rendered="true">
										    <f:selectItem itemLabel="Cr�dito" itemValue="C"/>
										    <f:selectItem itemLabel="D�bito"  itemValue="D"/>
										</h:selectOneRadio>
										
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Numero*"></h:outputLabel>
										<h:inputText styleClass="field text" id="numero" maxlength="20" size="20" required="false"
											value="#{movimentacaoBancariaBB.numero}">
											<f:validateLength maximum="20" />
										</h:inputText>
									</div>
								</li>				
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Data*"></h:outputLabel>
										<h:inputText readonly="false" maxlength="10" size="10"
											styleClass="field text data"
											value="#{movimentacaoBancariaBB.data}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }"
											id="data">
											<f:convertDateTime timeZone="GMT-3"/>
										</h:inputText>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Valor*"></h:outputLabel>
										<h:inputText styleClass="field text" id="valor" maxlength="10" size="10" dir="rtl"
											value="#{movimentacaoBancariaBB.valor}" required="false" disabled="true" readonly="true"
											onkeydown="return(BackSpace(this,event));"  onkeypress="return(MascaraMoeda(this,'','.',event));" >
											<f:validateLength maximum="10" />
											<f:validateDoubleRange  minimum="0.00" maximum="9999999.99"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Forma"></h:outputLabel>
										<h:selectOneMenu id="idForma" styleClass="field select"
											value="#{movimentacaoBancariaBB.idForma}" style="width: 200px;">		
											<f:selectItems id="formasSelectItems" value="#{movimentacaoBancariaBB.formas}" />
										</h:selectOneMenu>
										
									</div>
								</li>	
								<li class="buttons">
								    <h:commandButton styleClass="btTxt" immediate="true" id="botaoVoltar" action="#{movimentacaoBancariaBB.voltarConsulta}" value="Voltar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoAlterar" action="#{movimentacaoBancariaBB.alterar}" value="Alterar"></h:commandButton>
									<!--<h:commandButton styleClass="btTxt" id="botaoExcluir" action="#{movimentacaoBancariaBB.excluir}" value="Excluir"></h:commandButton>-->									
								</li>
							</ul>
						</div>
						<div class="clear"></div>
					</div>
		</h:form>
	  </body>	
	</f:view>
</html>
		