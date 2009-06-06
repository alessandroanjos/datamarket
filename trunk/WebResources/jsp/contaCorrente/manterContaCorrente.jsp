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
					<h:outputText value="#{msgs.manterContaCorrente}"></h:outputText>
				</strong>
			</div>				
		</div>
		<h:form id="frmManterContaCorrente">
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
										<h:outputLabel styleClass="desc" value="Loja"></h:outputLabel>
										<h:selectOneMenu id="idLoja" style="width: 200px;" value="#{contaCorrenteBB.idLoja}"> 
											<f:selectItems id="lojasSelectItems" value="#{contaCorrenteBB.lojas}" />   
										</h:selectOneMenu>
									</div>	
									<br />
									<br />
									<div>
										<h:outputLabel styleClass="desc" value="Banco"></h:outputLabel>
										<h:selectOneMenu id="idBanco" styleClass="field select"
											value="#{contaCorrenteBB.idBanco}" style="width: 200px;">		
											<f:selectItems id="bancoSelectItems" value="#{contaCorrenteBB.bancos}" />
										</h:selectOneMenu>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Agencia*"></h:outputLabel>
										<h:inputText styleClass="field text" id="idAgencia" maxlength="10" size="10" required="false"
											value="#{contaCorrenteBB.idAgencia}">
											<f:validateLength maximum="10" />
										</h:inputText>
									
									</div>

								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Numero*"></h:outputLabel>
										<h:inputText styleClass="field text" id="numero" maxlength="10" size="10" required="false"
											value="#{contaCorrenteBB.numero}">
											<f:validateLength maximum="10" />
										</h:inputText>
									
									</div>
										
								</li>								
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Nome*"></h:outputLabel>
										<h:inputText styleClass="field text" id="nome" maxlength="20" size="20" required="false"
											value="#{contaCorrenteBB.nome}">
											<f:validateLength maximum="20" />
										</h:inputText>
									
									</div>
									
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Saldo*"></h:outputLabel>
										<h:inputText styleClass="field text" id="saldo" maxlength="10" size="10" dir="rtl"
											value="#{contaCorrenteBB.saldo}" required="false" readonly="true"
											onkeydown="return(BackSpace(this,event));"  onkeypress="return(MascaraMoeda(this,'','.',event));" >
											<f:validateLength maximum="10" />
											<f:validateDoubleRange  minimum="0.00" maximum="9999999.99"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
										
									</div>
									
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Ativa"></h:outputLabel>
										<h:selectOneRadio  styleClass="field select"  id="situacao"  required="true"
											value="#{contaCorrenteBB.situacao}"  layout="lineDirection" rendered="true">
										    <f:selectItem itemLabel="Sim" itemValue="S" />
										    <f:selectItem itemLabel="Não" itemValue="N"/>
										</h:selectOneRadio>
										
									</div>
									
								</li>
								<li class="buttons">
								    <h:commandButton styleClass="btTxt" immediate="true" id="botaoVoltar" action="#{contaCorrenteBB.voltarConsulta}" value="Voltar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoAlterar" action="#{contaCorrenteBB.alterar}" value="Alterar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoExcluir" action="#{contaCorrenteBB.excluir}" value="Excluir"></h:commandButton>									
								</li>
							</ul>
						</div>
						<div class="clear"></div>
					</div>
		</h:form>
	  </body>	
	</f:view>
</html>
		