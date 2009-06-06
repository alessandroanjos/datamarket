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
		<script type="text/javascript" src="/EnterpriseServer/js/jquery-maskedinput.js"></script>
		<script type="text/javascript" src="/EnterpriseServer/js/global.js"></script>
		
		<t:stylesheet path="/css/default.css"></t:stylesheet>
		<t:stylesheet path="/css/form.css"></t:stylesheet>
      <script type="text/javascript">

      window.onload = function(){ inicializar() };

      function inicializar() {

      	$("input.field, select.field").each(function(i){
      		$(this).focus(function() {this.style.backgroundColor = "#eff6ff"});
      		$(this).blur(function() {this.style.backgroundColor = ""});
      	});

      }

      </script>
	</head>
	<body>
	<div id="outer">
		<div id="topoGeral">
			<div id="tituloPaginaGeral">
				<strong>
					<h:outputText value="#{msgs.consultarProduto}"></h:outputText>
				</strong>
			</div>				
		</div>	
		<h:form id="frmConsultarProduto" binding="#{produtoBB.init}">
				<div id="content">
						<div id="primarioContentContainerInternas" style="width:100%;">
							<fieldset>
								<legend>Opções de filtro:</legend>
								<ul>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Loja"></h:outputLabel>
											<h:selectOneMenu id="idLoja" style="width: 200px;" value="#{produtoBB.idLoja}"> 
												<f:selectItems id="lojasSelectItems" value="#{produtoBB.lojas}" />   
											</h:selectOneMenu>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Código"></h:outputLabel>
											<h:inputText styleClass="field text ativo" id="id" maxlength="4"
												value="#{produtoBB.id}" size="4" required="false">
												<f:validateLength maximum="4" />
												<f:validator validatorId="LongValidator"/>
											</h:inputText>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Descrição Completa"></h:outputLabel>
											<h:inputText styleClass="field text" id="descricaoCompleta" maxlength="50" size="55"
												value="#{produtoBB.descricaoCompleta}">
												<f:validateLength maximum="50" />
											</h:inputText>
										</div>
										<br />
										<br />
										<div>
											<h:outputLabel styleClass="desc" value="Tipo de Produto"></h:outputLabel>
											<h:selectOneMenu id="tiposProduto" styleClass="field select" style="width: 200px;"
												value="#{produtoBB.idTipoProduto}" > 
													<f:selectItem id="tiposSelectItemsBranco" itemValue="" itemLabel=""/>	  
													<f:selectItems id="perfilSelectItems" 
													  value="#{produtoBB.tiposConsulta}" />   
											</h:selectOneMenu> 							
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Grupo de Produto"></h:outputLabel>
											<h:selectOneMenu id="gruposProduto" styleClass="field select" style="width: 200px;"
												value="#{produtoBB.idGrupo}" > 
													<f:selectItem id="tiposSelectItemsBranco" itemValue="" itemLabel=""/>	  
													<f:selectItems id="perfilSelectItems" 
													  value="#{produtoBB.gruposConsulta}" />   
											</h:selectOneMenu> 									
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Unidade"></h:outputLabel>
											<h:selectOneMenu id="unidades" styleClass="field select" style="width: 200px;"
												value="#{produtoBB.idUnidade}" > 
													<f:selectItem id="tiposSelectItemsBranco" itemValue="" itemLabel=""/>	  
													<f:selectItems id="perfilSelectItems" 
													  value="#{produtoBB.unidadesConsulta}" />   
											</h:selectOneMenu> 							
										</div>
										<br />
										<br />
										<div>
											<h:outputLabel styleClass="desc" value="Imposto"></h:outputLabel>
											<h:selectOneMenu id="impostos" styleClass="field select" style="width: 200px;"
												value="#{produtoBB.idImposto}" > 
													<f:selectItem id="tiposSelectItemsBranco" itemValue="" itemLabel=""/>	  
													<f:selectItems id="perfilSelectItems" 
													  value="#{produtoBB.impostosConsulta}" />   
											</h:selectOneMenu> 							
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Fabricante"></h:outputLabel>
											<h:selectOneMenu id="fabricante"  styleClass="field select" style="width: 200px;"
												value="#{produtoBB.idFabricante}" > 
													<f:selectItems id="fabricanteSelectItems" 
													  value="#{produtoBB.fabricantesConsulta}" />   
											</h:selectOneMenu> 																						
										</div>										
									</li>
								</ul>
							</fieldset>
							<div class="listagem">
								<t:dataTable value="#{produtoBB.produtos}"
									var="produto" rowClasses="rowA,rowB" width="95%" renderedIfEmpty="false">
									<h:column>
										<f:facet name="header">
											<h:outputText value="Cód." /> 
										</f:facet>
										<h:outputText value="#{produto.id}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Desc. Completa" />
										</f:facet>
										<h:commandLink value="#{produto.descricaoCompleta}" action="#{produtoBB.consultar}">
											<f:param name="id" value="#{produto.id}"/>						
										</h:commandLink>
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Cód. Aut." /> 
										</f:facet>
										<h:outputText value="#{produto.codigoAutomacao}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Cód. Ext." /> 
										</f:facet>
										<h:outputText value="#{produto.codigoExterno}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Preço" /> 
										</f:facet>
										<h:outputText value="#{produto.precoPadrao}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Preço Promo" /> 
										</f:facet>
										<h:outputText value="#{produto.precoPromocional}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Preço Compra" /> 
										</f:facet>
										<h:outputText value="#{produto.precoCompra}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Tipo" /> 
										</f:facet>
										<h:outputText value="#{produto.tipo.descricao}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Grupo" /> 
										</f:facet>
										<h:outputText value="#{produto.grupo.descricao}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Unidade" /> 
										</f:facet>
										<h:outputText value="#{produto.unidade.descricao}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Imposto" /> 
										</f:facet>
										<h:outputText value="#{produto.imposto.descricao}" /> 
									</h:column>								
									<h:column>
										<f:facet name="header">
											<h:outputText value="Fornecedor" /> 
										</f:facet>
										<h:outputText value="#{produto.fabricante.nomeFabricante}" /> 
									</h:column>
								</t:dataTable>
								<ul>
									<li class="normal">
										<div>
											<h:messages rendered="#{not produtoBB.existeRegistros}" errorClass="msgSistemaErro" infoClass="msgSistemaSucesso" globalOnly="true" showDetail="true"/>
										</div>
									</li>
								</ul>
							</div>
							<ul>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" action="#{produtoBB.resetBB}" id="botaoLimpar" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoConsultar" action="#{produtoBB.consultar}" value="Consultar"></h:commandButton>
								</li>						
							</ul>
						</div>
						<div class="clear"></div>
					</div>
		</h:form>
	  </body>			
	</f:view>
</html>