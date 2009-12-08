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
	<body onload="exibirMensagemErro();">
	<div id="outer">
		<div id="topoGeral">
			<div id="tituloPaginaGeral">
				<strong>
					<h:outputText value="#{msgs.inserirPlanoPagamento}"></h:outputText>
				</strong>
			</div>				
		</div>
		<h:form id="frmInserirPlanoPagamento" binding="#{planoPagamentoBB.init}">
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
										<h:outputLabel styleClass="desc" value="C�digo*"></h:outputLabel>
										<h:inputText styleClass="field text" id="id" maxlength="3" 
											onfocus="this.select();" onclick="this.select();"
											onkeypress="return SoNumero(event);"
											value="#{planoPagamentoBB.id}" size="4" >
											<f:validateLength maximum="4" />
											<f:validator validatorId="LongValidator"/>
										</h:inputText>												
									</div>
										
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Descri��o*"></h:outputLabel>
										<h:inputText styleClass="field text" id="descricao" maxlength="50" size="50" 
											value="#{planoPagamentoBB.descricao}">
											<f:validateLength maximum="50" />
										</h:inputText>
										
									</div>
									
								</li>
								<li class="normal">						
									<div>
										<h:outputLabel styleClass="desc" value="Forma de Receb. Associada"></h:outputLabel>
										<h:selectOneMenu id="idForma" styleClass="field select" style="width: 190px;" 
											value="#{planoPagamentoBB.idForma}" > 
												  <f:selectItems id="formaSelectItems" 
												  value="#{planoPagamentoBB.formas}"  />   
										</h:selectOneMenu>
												
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Valor M�nimo"></h:outputLabel>
										<h:inputText styleClass="field text" id="valorMinimo" maxlength="10" size="10"
											value="#{planoPagamentoBB.valorMinimo}" dir="rtl" required="false" 
											onkeydown="return(BackSpace(this,event));"  onkeypress="return(MascaraMoeda(this,'','.',event));" >
											<f:validateLength maximum="10" />
											<f:validateDoubleRange  minimum="0.01" maximum="9999999.99"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
										
									</div>								
									<div>
										<h:outputLabel styleClass="desc" value="Valor M�ximo"></h:outputLabel>
										<h:inputText styleClass="field text" id="valorMaximo" maxlength="10" size="10"
											value="#{planoPagamentoBB.valorMaximo}" dir="rtl" required="false" 
											onkeydown="return(BackSpace(this,event));"  onkeypress="return(MascaraMoeda(this,'','.',event));" >
											<f:validateLength maximum="10" />
											<f:validateDoubleRange  minimum="0.01" maximum="9999999.99"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
										
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Perc. Desconto"></h:outputLabel>
										<h:inputText styleClass="field text" id="percentualDesconto" maxlength="5" size="5"
											value="#{planoPagamentoBB.percDesconto}" dir="rtl" required="false" 
											onkeydown="return(BackSpace(this,event));"  onkeypress="return(MascaraMoeda(this,'','.',event));" >
											<f:validateLength maximum="5" />
											<f:validateDoubleRange  minimum="0.00" maximum="100.00"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
										
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Perc. Acr�scimo"></h:outputLabel>
										<h:inputText styleClass="field text" id="percentualAcrescimo" maxlength="5" size="5"
											value="#{planoPagamentoBB.percAcrescimo}" dir="rtl" required="false" 
											onkeydown="return(BackSpace(this,event));"  onkeypress="return(MascaraMoeda(this,'','.',event));" >
											<f:validateLength maximum="5" />
											<f:validateDoubleRange  minimum="0.00" maximum="100.00"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
										
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Inicio Validade"></h:outputLabel>
										<h:inputText styleClass="field text" id="dataInicioValidade" maxlength="10" size="10" required="false"
											value="#{planoPagamentoBB.dataInicioValidade}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }">
											
										</h:inputText>
										
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Final Validade"></h:outputLabel>
										<h:inputText styleClass="field text" id="dataFimValidade" maxlength="10" size="10" required="false"
											value="#{planoPagamentoBB.dataFimValidade}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }">
										</h:inputText>
										
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Situa��o*"></h:outputLabel>
										<h:selectOneRadio  styleClass="field select" id="status" 
											value="#{planoPagamentoBB.status}" layout="lineDirection" >
										    <f:selectItems id="situacao" value="#{planoPagamentoBB.situacaoItens}" />							
										</h:selectOneRadio>
									</div>		
									
								</li>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" action="#{planoPagamentoBB.resetBB}" id="botaoLimpar" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoInserir"  action="#{planoPagamentoBB.inserir}" value="Inserir"></h:commandButton>
								</li>
							</ul>
						</div>
						<div class="clear"></div>
					</div>
					
		</h:form>
	  </body>
	</f:view>
</html>
		
