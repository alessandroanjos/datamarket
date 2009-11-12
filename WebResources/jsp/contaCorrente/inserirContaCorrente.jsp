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
	<body onload="exibirMensagemErro();">
	<div id="outer">
		<div id="topoGeral">
			<div id="tituloPaginaGeral">
				<strong>
					<h:outputText value="#{msgs.inserirContaCorrente}"></h:outputText>
				</strong>
			</div>				
		</div>
		<h:form id="frmInserirContaCorrente" binding="#{contaCorrenteBB.init}">
				<div id="content">
						<div id="primarioContentContainerInternas">
							<ul>
								<li class="normal">
									<div>
										<%@ include file="/jsp/mensagem_erro.jsp"%>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Loja"></h:outputLabel>
										<h:selectOneMenu id="idLoja" style="width: 200px;" value="#{contaCorrenteBB.idLoja}"> 
											<f:selectItems id="lojasSelectItems" value="#{contaCorrenteBB.lojas}" />   
										</h:selectOneMenu>
									</div>	
									<div>
										<h:outputLabel styleClass="desc" value="Nome*"></h:outputLabel>
										<h:inputText styleClass="field text" id="nome" maxlength="20" size="30" required="false"
											value="#{contaCorrenteBB.nome}">
											<f:validateLength maximum="20" />
										</h:inputText>
									
									</div>
									</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Banco*"></h:outputLabel>
										<h:selectOneMenu id="idBanco" styleClass="field select"
											value="#{contaCorrenteBB.idBanco}" style="width: 200px;">		
											<f:selectItems id="bancoSelectItems" value="#{contaCorrenteBB.bancos}" />
										</h:selectOneMenu>
									</div>
								
									<div>
										<h:outputLabel styleClass="desc" value="Agencia*"></h:outputLabel>
										<h:inputText styleClass="field text" id="idAgencia" maxlength="4" size="5" required="false"
											value="#{contaCorrenteBB.idAgencia}">
											<f:validateLength maximum="4" />
										</h:inputText>
									
									</div>

									<div>
										<h:outputLabel styleClass="desc" value="Numero*"></h:outputLabel>
										<h:inputText styleClass="field text" id="numero" maxlength="5" size="6" required="false"
											value="#{contaCorrenteBB.numero}">
											<f:validateLength maximum="5" />
										</h:inputText>
									
									</div>
							
									<div>
										<h:outputLabel styleClass="desc" value="Digito*"></h:outputLabel>
										<h:inputText styleClass="field text" id="digitoContaCorrente" maxlength="1" size="2" required="false"
											value="#{contaCorrenteBB.digitoContaCorrente}">
											<f:validateLength maximum="1" />
										</h:inputText>
									
									</div>
										
								</li>								
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Carteira*"></h:outputLabel>
										<h:inputText styleClass="field text" id="carteira" maxlength="3" size="4" required="false"
											value="#{contaCorrenteBB.carteira}">
											<f:validateLength maximum="3" />
										</h:inputText>
									
									</div>
										
								</li>								
								
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Saldo*"></h:outputLabel>
										<h:inputText styleClass="field text" id="saldo" maxlength="10" size="10" dir="rtl"
											value="#{contaCorrenteBB.saldo}" required="false" 
											onkeydown="return(BackSpace(this,event));"  onkeypress="return(MascaraMoeda(this,'','.',event));" >
											<f:validateLength maximum="10" />
											<f:validateDoubleRange  minimum="0.00" maximum="9999999.99"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
										
									</div>
									
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Primeira Mensagem do Boleto "></h:outputLabel>
										<h:inputText styleClass="field text" id="mensagemBoleto1" maxlength="100" size="30" required="false"
											value="#{contaCorrenteBB.mensagemBoleto1}">
											<f:validateLength maximum="100" />
										</h:inputText>
									</div>
								
									<div>
										<h:outputLabel styleClass="desc" value="Segunda Mensagem do Boleto "></h:outputLabel>
										<h:inputText styleClass="field text" id="mensagemBoleto2" maxlength="100" size="30" required="false"
											value="#{contaCorrenteBB.mensagemBoleto2}">
											<f:validateLength maximum="100" />
										</h:inputText>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Terceira Mensagem do Boleto "></h:outputLabel>
										<h:inputText styleClass="field text" id="mensagemBoleto3" maxlength="100" size="30" required="false"
											value="#{contaCorrenteBB.mensagemBoleto3}">
											<f:validateLength maximum="100" />
										</h:inputText>
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Quarta Mensagem do Boleto "></h:outputLabel>
										<h:inputText styleClass="field text" id="mensagemBoleto4" maxlength="100" size="30" required="false"
											value="#{contaCorrenteBB.mensagemBoleto4}">
											<f:validateLength maximum="100" />
										</h:inputText>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Ativa"></h:outputLabel>
										<h:selectOneRadio  styleClass="field select"  id="situacao"  
											value="#{contaCorrenteBB.situacao}"  layout="lineDirection" rendered="true">
										    <f:selectItem itemLabel="Sim" itemValue="S"/>
										    <f:selectItem itemLabel="N�o" itemValue="N"/>
										</h:selectOneRadio>
										
									</div>
									
								</li>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" action="#{contaCorrenteBB.resetBB}" id="botaoLimpar" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoInserir" action="#{contaCorrenteBB.inserir}" value="Inserir"></h:commandButton>
								</li>
							</ul>
						</div>
						<div class="clear"></div>
					</div>
		</h:form>
	  </body>	
	</f:view>
</html>
		