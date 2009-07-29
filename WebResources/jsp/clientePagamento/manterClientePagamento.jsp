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
					<h:outputText value="#{msgs.manterClientePagamento}"></h:outputText>
				</strong>
			</div>				
		</div>				
				<div class="jqmAlert" id="alerta"><div class="jqmAlertWindow"><div class="jqmAlertTitle clearfix"><a href="#" onclick="javascript:fecharAlerta();" class="jqmClose"><em>Fechar</em></a><h1>Alerta</h1></div><div class="jqmAlertContent" id="jqmAlertContent"></div><input type="button" value="Ok" onclick="javascript:fecharAlerta();" /></div></div><div id="content">					
					<div id="primarioContentContainerInternas">
<!-- xxxxxxxxxxxxxxx -->					
		<h:form id="frmManterClientePagamento">
<!-- xxxxxxxxxxxxxxx -->					
						<ul>
							<li class="normal">
								<div>
									<h:outputLabel styleClass="desc" value="Cliente"></h:outputLabel>
									<h:outputLabel styleClass="field text" value="#{clientePagamentoBB.cliente.cpfCnpj} - #{clientePagamentoBB.cliente.nomeCliente}"></h:outputLabel>									
								</div>
							</li>
							<br />																						
							<br />							
							<li class="normal">
								<div>
									<h:outputLabel styleClass="desc" value="Data do Pagamento"></h:outputLabel>
									<h:outputLabel styleClass="field text" value="#{clientePagamentoBB.dataPagamento}"></h:outputLabel>
								</div>							
								<div>
									<h:outputLabel styleClass="desc" value="Forma de Recebimento"></h:outputLabel>
									<h:outputLabel styleClass="field text" value="#{clientePagamentoBB.formaRecebimento.descricao}"></h:outputLabel>
								</div>
								<div>
									<h:outputLabel styleClass="desc" value="Valor do Pagamento"></h:outputLabel>
									<h:outputLabel styleClass="field text" value="#{clientePagamentoBB.valorPagamento}"></h:outputLabel>									
								</div>											
							</li>									
						</ul>						
						<ul>
							<li class="buttons">
								<h:commandButton styleClass="btTxt" immediate="true" id="botaoVoltar" action="#{clientePagamentoBB.voltarConsulta}" value="Voltar"></h:commandButton>
								<h:commandButton styleClass="btTxt" id="botaoImprimir" action="#{clientePagamentoBB.imprimirRecibo}" value="Imprimir"></h:commandButton>
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