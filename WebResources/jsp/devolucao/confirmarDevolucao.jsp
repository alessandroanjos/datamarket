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
			<div id="content">
				<div id="primarioContentContainerInternas">
					<h:form id="frmConfirmarDevolucao">
						<div>
							<h:commandButton image="/images/tree/drop-yes.gif" alt="" styleClass="btTxt" />
							&nbsp;
							<h:outputText styleClass="textoGrande" id="mensagem" value="Devolu��o Realizada com Sucesso!!!"></h:outputText>
						</div>
						<br />
						<div style="align:center;">
							<h:outputText styleClass="textoGrande" id="numeroDevolucao" value="N�mero Devolu��o: #{devolucaoBB.idOperacaoDevolucao}"></h:outputText>
						</div>
						<ul>						
							<li class="buttons">
	 							<h:commandButton styleClass="btTxt" id="botaoImprimir" action="#{devolucaoBB.imprimirRecibo}" value="Imprimir"></h:commandButton>
								<h:commandButton styleClass="btTxt" id="botaoOK" action="#{devolucaoBB.confirmarDevolucao}" value="OK"></h:commandButton>
							</li>
						</ul>	
					</h:form>		
				</div>
				<div class="clear"></div>
				</div>	
			</div>	
		 </body>						
	</f:view>
</html>