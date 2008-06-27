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
		<script type="text/javascript" src="/EnterpriseServer/js/funcoes.js"></script>
		<t:stylesheet path="/css/default.css"></t:stylesheet>
		<t:stylesheet path="/css/form.css"></t:stylesheet>
		
	</head>
	<body>
	<div id="outer">
		<div id="topoGeral">
			<div id="tituloPaginaGeral">
				<strong>
					<h:outputText value="#{msgs.inserirClientePagamento}"></h:outputText>
				</strong>
			</div>				
		</div>	
					
				<div id="content">					
					<div id="primarioContentContainerInternas">
<!-- xxxxxxxxxxxxxxx -->					
		<h:form id="frmInserirClientePagamento">
<!-- xxxxxxxxxxxxxxx -->					
						<ul>
							<li class="normal">
								<div>
									<h:messages errorClass="msgSistemaErro" infoClass="msgSistemaSucesso" globalOnly="true" showDetail="true"/>
								</div>
							</li>
							<li class="normal">
								<div>
									<h:outputLabel styleClass="desc" value="Cliente"></h:outputLabel>
									<h:selectOneMenu id="clientes" styleClass="field select" onchange="submit();" immediate="true"
										value="#{clientePagamentoBB.idCliente}" required="true" style="width: 200px;" valueChangeListener="#{clientePagamentoBB.recuperaDadosCliente}">   
										  <f:selectItems id="clienteSelectItems" value="#{clientePagamentoBB.clientes}" />   
									</h:selectOneMenu>
									<h:message for="clientes" styleClass="msgErro" />
								</div>
								</li>
							<li class="normal">
								<div>
									<h:outputLabel styleClass="desc" value="Limite Compras"></h:outputLabel>
									<h:inputText styleClass="field text" id="valorLimiteCompras" maxlength="10" size="10" readonly="true"
										value="#{clientePagamentoBB.cliente.valorLimiteCompras}">
									</h:inputText>
								</div>
								<div>
									<h:outputLabel styleClass="desc" value="Limite Disponível"></h:outputLabel>									
									<h:inputText styleClass="field text" id="valorLimiteDisponivel" maxlength="10" size="10" readonly="true"
										value="#{clientePagamentoBB.cliente.valorLimiteDisponivel}">
									</h:inputText>
								</div>
								<div>
									<h:outputLabel styleClass="desc" value="Valor Débito"></h:outputLabel>									
									<h:inputText styleClass="field text" id="valorDebito" maxlength="10" size="10" readonly="true"
										value="#{clientePagamentoBB.cliente.valorLimiteCompras - clientePagamentoBB.cliente.valorLimiteDisponivel}">
									</h:inputText>
								</div>
							</li>																						
							<li class="normal">
								<div>
									<h:outputLabel styleClass="desc" value="Data Pagamento"></h:outputLabel>
									<h:inputText styleClass="field text" id="dataPagamento" maxlength="10" size="10"
										value="#{clientePagamentoBB.dataPagamento}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) this.value = ''"
										required="false">
									</h:inputText>
									<h:message for="dataPagamento" styleClass="msgErro"/>
								</div>							
								<div>
									<h:outputLabel styleClass="desc" value="Forma de Recebimento"></h:outputLabel>
									<h:selectOneMenu id="formas" styleClass="field select"
										value="#{clientePagamentoBB.idFormaRecebimento}" required="false" style="width: 200px;">   
										  <f:selectItems id="formaSelectItems" value="#{clientePagamentoBB.formas}" />   
									</h:selectOneMenu>
									<h:message for="formas" styleClass="msgErro" />
								</div>
							<div>
									<h:outputLabel styleClass="desc" value="Valor Pagamento"></h:outputLabel>
									<h:inputText styleClass="text field" dir="rtl" id="valorPagamento" maxlength="10" size="10" 
										value="#{clientePagamentoBB.valorPagamento}" required="true" onkeypress="Formata('frmInserirClientePagamento:valorPagamento',9,2);">
										<f:validateLength maximum="10" />	
										<f:validateDoubleRange  minimum="0.00" maximum="9999999.99"/>										
										<f:validator validatorId="BigDecimalValidator" />
									</h:inputText>
									<h:message for="valorPagamento" styleClass="msgErro" />							
								</div>
							</li>									
						</ul>						
						<ul>
							<li class="buttons">
								<h:commandButton styleClass="btTxt" immediate="true" id="botaoLimpar" type="reset" value="Limpar"></h:commandButton>
								<h:commandButton styleClass="btTxt" id="botaoInserir" action="#{clientePagamentoBB.inserir}" value="Inserir"></h:commandButton>
							</li>
						</ul>
<!-- xxxxxxxxxxxxxxx -->					
		</h:form>		
<!-- xxxxxxxxxxxxxxx -->					
          </div>
					<div class="clear"></div>
				</div>

	  </body>
	</f:view>
</html>