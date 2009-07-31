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
					<h:outputText value="#{msgs.relatorioEstoqueAtual}"></h:outputText>
				</strong>
			</div>				
		</div>		
		<h:form id="frmRelatorioEstoqueAtual" binding="#{relatorioBB.init}">				
				<div id="content">				
						<div id="primarioContentContainer">
							<fieldset>
								<legend>Op��es de filtro:</legend>
								<ul>								
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Loja" rendered="true"></h:outputLabel>										
											<h:selectOneMenu id="idLoja" style="width: 200px;" value="#{relatorioBB.idLoja}" rendered="true" onchange="submit();" valueChangeListener="#{relatorioBB.carregarEstoquesPorLoja}">  
												<f:selectItems id="lojasSelectItems" value="#{relatorioBB.lojas}" />   
											</h:selectOneMenu>
										</div>																
										<div>
											<h:outputLabel styleClass="desc" value="Estoque" rendered="true"></h:outputLabel>										
											<h:selectOneMenu id="idEstoque" style="width: 200px;" value="#{relatorioBB.idEstoque}" rendered="true" onchange="submit();" valueChangeListener="#{relatorioBB.carregarEstoquesPorLoja}">  
												<f:selectItems id="estoquesSelectItems" value="#{relatorioBB.estoques}" />   
											</h:selectOneMenu>
										</div>																
									</li>
								</ul>
							</fieldset>	
							<ul>
								<li class="normal">
									<div>
										<%@ include file="/jsp/mensagem_erro.jsp"%>
									</div>
								</li>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" action="#{relatorioBB.limparRelatorioEstoqueAtual}" id="botaoLimpar" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoRelatorio" action="#{relatorioBB.executarRelatorioEstoqueAtual}" value="Relat�rio"></h:commandButton>
								</li>						
							</ul>
						</div>
						<div class="clear"></div>
					</div>
		</h:form>
	  </body>	
	</f:view>
</html>