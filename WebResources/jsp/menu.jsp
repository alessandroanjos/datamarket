<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t" %>
<%@ taglib uri="https://ajax4jsf.dev.java.net/ajax" prefix="a4j" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!--
	Design e Engenharia de Interface
	por Marco Antônio (marcaopimentel@gmail.com)
	Disponibilizado para Infinity DataMarket
-->
<html xmlns="http://www.w3.org/1999/xhtml">
	<f:view>
		<f:loadBundle basename="resources.mensagens" var="msgs"/>
		<head>
			<title><h:outputText value="#{msgs.tituloPaginas}"></h:outputText></title>
			<script type="text/javascript" src="/EnterpriseServer/js/jquery.js"></script>
			<script type="text/javascript" src="/EnterpriseServer/js/global.js"></script>
			<script type="text/javascript" src="/EnterpriseServer/js/funcoes.js"></script>
			<t:stylesheet path="css/default.css" enabledOnUserRole="true"></t:stylesheet>
			<t:stylesheet path="css/form.css" enabledOnUserRole="true"></t:stylesheet>	
		</head>


	<h:form id="menu">
	
		<f:subview id="topo">
			<jsp:include page="/jsp/topo.jsp?user=#{loginBB.usuarioLogado.nome}" flush="true"></jsp:include>
		</f:subview>
		
		<div id="content" style="margin-top:0px;">
			<div id="primarioContentContainer">
				<ul>
					<li>
						<div style="margin-top:20px;height:240px;">
							<br />
							<t:jscookMenu layout="hbr" theme="ThemeOffice" id="menuPrincipal">
								<t:navigationMenuItems value="#{loginBB.navItens}" />
							</t:jscookMenu>
						</div>
					</li>
				</ul>					
				<div class="box" style="height:225px;"></div>			
			</div>
			<div class="clear"></div>
			</div>
			<jsp:include page="/jsp/rodape.jsp"></jsp:include>

		</h:form>					
	</f:view>
</html>