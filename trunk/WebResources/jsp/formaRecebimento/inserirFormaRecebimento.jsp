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
							<h:outputText value="#{msgs.inserirFormaRecebimento}"></h:outputText>
						</strong>
					</div>				
				</div>
		<h:form id="frmInserirFormaRecebimento" binding="#{formaRecebimentoBB.init}">
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
										<h:outputLabel styleClass="desc" value="Código*"></h:outputLabel>
										<h:inputText styleClass="field text ativo" id="id" maxlength="3" 
										    onfocus="this.select();" onclick="this.select();" 
										    onkeypress="return SoNumero(event);"
											value="#{formaRecebimentoBB.id}" size="3" >
											<f:validateLength maximum="3" />
											<f:validator validatorId="LongValidator"/>
										</h:inputText>												
								
										
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Descrição*"></h:outputLabel>
										<h:inputText styleClass="field text" id="descricao" maxlength="50" size="50" 
											value="#{formaRecebimentoBB.descricao}">
											<f:validateLength maximum="50" />
										</h:inputText>
									</div>
									
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Recebimento na Impressora*"></h:outputLabel>
										<h:inputText styleClass="field text" id="recebimentoImpressora" maxlength="50" size="50" required="false"
											value="#{formaRecebimentoBB.recebimentoImpressora}">
											<f:validateLength maximum="50" />
										</h:inputText>
									</div>
									
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Abre Gaveta*"></h:outputLabel>
										<h:selectOneRadio  styleClass="field select"  id="abrirGaveta" 
											value="#{formaRecebimentoBB.abrirGaveta}"  layout="lineDirection" >
										    <f:selectItem itemLabel="Sim" itemValue="S" />
										    <f:selectItem itemLabel="Não" itemValue="N"/>
										</h:selectOneRadio>
									</div>
									
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Válida em Recebimento de Contas*"></h:outputLabel>
										<h:selectOneRadio  styleClass="field select"  id="validaRecebimentoConta" 
											value="#{formaRecebimentoBB.validaRecebimentoConta}"  layout="lineDirection" >
										    <f:selectItem itemLabel="Sim" itemValue="S" />
										    <f:selectItem itemLabel="Não" itemValue="N"/>
										</h:selectOneRadio>
									</div>
									
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Válida em Pagamento de Contas*"></h:outputLabel>
										<h:selectOneRadio  styleClass="field select"  id="validaPagamentoConta" 
											value="#{formaRecebimentoBB.validaPagamentoConta}"  layout="lineDirection" >
										    <f:selectItem itemLabel="Sim" itemValue="S" />
										    <f:selectItem itemLabel="Não" itemValue="N"/>
										</h:selectOneRadio>
									</div>
									
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Valor Limite Sangria*"></h:outputLabel>
										
										<h:inputText styleClass="field text" id="valorLimiteSangria" maxlength="10" size="10"
											value="#{formaRecebimentoBB.valorLimiteSangria}" dir="rtl"  
											onkeydown="return(BackSpace(this,event));"  onkeypress="return(MascaraMoeda(this,'','.',event));" >
											<f:validateLength maximum="10" />
											<f:validateDoubleRange  minimum="0.00" maximum="9999999.99"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
										
									</div>
									
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Valor Maximo Troco*"></h:outputLabel>

										<h:inputText styleClass="field text" id="valorMaxTroco" maxlength="10" size="10"
											value="#{formaRecebimentoBB.valorMaxTroco}"  dir="rtl" 
											onkeydown="return(BackSpace(this,event));"  onkeypress="return(MascaraMoeda(this,'','.',event));" >
											<f:validateLength maximum="10" />
											<f:validateDoubleRange  minimum="0.00" maximum="9999999.99"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>												
									</div>
																		
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Inicio Validade"></h:outputLabel>
										<h:inputText styleClass="field text" id="dataInicioValidade" maxlength="10" size="10"
											value="#{formaRecebimentoBB.dataInicioValidade}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }">
										</h:inputText>
									</div>
									
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Final Validade"></h:outputLabel>
										<h:inputText styleClass="field text" id="dataFimValidade" maxlength="10" size="10"
											value="#{formaRecebimentoBB.dataFimValidade}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }">
										</h:inputText>
									</div>
																		
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Forma Troco"></h:outputLabel>
										<h:selectOneMenu id="perfis" styleClass="field select"
											value="#{formaRecebimentoBB.idFormaTroco}" required="false" style="width: 200px;">   
												  <f:selectItems id="perfilSelectItems" 
												  value="#{formaRecebimentoBB.formas}" />   
										</h:selectOneMenu>
									</div>
									
								</li>																
								<li class="buttons">
									<h:commandButton styleClass="btTxt" action="#{formaRecebimentoBB.resetBB}" id="botaoLimpar" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoInserir" action="#{formaRecebimentoBB.inserir}" value="Inserir"></h:commandButton>
								</li>
							</ul>
						</div>
						<div class="clear"></div>
					</div>
		</h:form>
	  </body>	
	</f:view>
	
</html>
		
