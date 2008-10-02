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
		<script type="text/javascript" src="/EnterpriseServer/js/funcoes.js"></script>
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
						<strong> <h:outputText value="#{msgs.manterProduto}"></h:outputText>
						</strong>
					</div>
				</div>
				<div id="content">
					<div class="tabMenu">
						<ul>
							<li id="tabMenuDiv0" class="current"
								onclick="selecionaMenuTab(this.id)">
								<span><a href="#">Produto</a> </span>
							</li>
							<li id="tabMenuDiv1" onclick="selecionaMenuTab(this.id)">
								<span><a href="#">Lojas</a> </span>
							</li>
						</ul>
						<div class="clear"></div>
					</div>
					<div id="primarioContentContainerInternas">
						<ul>
							<li class="normal">
								<div>
									<h:messages errorClass="msgSistemaErro"
										infoClass="msgSistemaSucesso" globalOnly="true"
										showDetail="true" />
								</div>
							</li>
						</ul>
						<h:form id="frmAlterarProduto" binding="#{produtoBB.init}"
							onsubmit="javascript:getId('frmAlterarProduto:abaCorrente').value = strAbaCorrente;">
							<h:inputHidden id="abaCorrente" value="#{produtoBB.abaCorrente}">
							</h:inputHidden>
							<div id="tabDiv0">
								<ul>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Descrição Completa*"></h:outputLabel>
											<h:inputText styleClass="field text" id="descricaoCompleta"
												maxlength="50" size="50"
												value="#{produtoBB.descricaoCompleta}" required="true">
												<f:validateLength maximum="50" />
											</h:inputText>
										</div>
										<h:message for="descricaoCompleta" styleClass="msgErro" />
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Descrição Compacta*"></h:outputLabel>
											<h:inputText styleClass="field text" id="descricaoCompacta"
												maxlength="30" size="30"
												value="#{produtoBB.descricaoCompacta}" required="true">
												<f:validateLength maximum="30" />
											</h:inputText>

										</div>
										<h:message for="descricaoCompacta" styleClass="msgErro" />
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Preço Padrão*"></h:outputLabel>
											<h:inputText styleClass="field text" id="precoPadrao"
												maxlength="10" size="10" value="#{produtoBB.precoPadrao}"
												required="true" dir="rtl"
												onfocus="this.select();" onclick="this.select();"
												onkeypress="return(formataMoeda(this,'','.',2,event));">
												<f:validateDoubleRange minimum="0.00" maximum="999999.99" />
											</h:inputText>
											<h:message for="precoPadrao" styleClass="msgErro" />
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Preço Promocional"></h:outputLabel>
											<h:inputText styleClass="field text" id="precoPromocional"
												maxlength="10" size="10"
												value="#{produtoBB.precoPromocional}" required="false"
												dir="rtl" 
												onfocus="this.select();" onclick="this.select();"
												onkeypress="return(formataMoeda(this,'','.',2,event));">
												<f:validateDoubleRange minimum="0.00" maximum="999999.99" />
											</h:inputText>
											<h:message for="precoPromocional" styleClass="msgErro" />
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Preço de Compra"></h:outputLabel>
											<h:inputText styleClass="field text" id="precoCompra"
												maxlength="10" size="10"
												value="#{produtoBB.precoCompra}" required="false"
												dir="rtl" 
												onfocus="this.select();" onclick="this.select();"
												onkeypress="return(formataMoeda(this,'','.',2,event));">
												<f:validateDoubleRange minimum="0.01" maximum="999999.99" />
											</h:inputText>
											<h:message for="precoCompra" styleClass="msgErro" />
										</div>
										
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Código Externo*"></h:outputLabel>
											<h:inputText styleClass="field text" id="codigoExterno"
												maxlength="15" size="17" value="#{produtoBB.codigoExterno}"
												onfocus="this.select();" onclick="this.select();"
												onkeypress="return SoNumero(event);" 
												required="true">
												<f:validateLength maximum="15" />
											</h:inputText>
											<h:message for="codigoExterno" styleClass="msgErro" />
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Código Automação*"></h:outputLabel>
											<h:inputText styleClass="field text" id="codigoAutomacao"
												maxlength="15" size="17"
												value="#{produtoBB.codigoAutomacao}"
												onfocus="this.select();" onclick="this.select();"
												onkeypress="return SoNumero(event);" 
												required="true">
												<f:validateLength maximum="15" />
											</h:inputText>
											<h:message for="codigoAutomacao" styleClass="msgErro" />
										</div>
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Tipo de Produto*"></h:outputLabel>
											<h:selectOneMenu id="tipos" styleClass="field select"
												style="width: 200px;" value="#{produtoBB.idTipoProduto}"
												required="true">
												<f:selectItems id="tiposSelectItems"
													value="#{produtoBB.tipos}" />
											</h:selectOneMenu>
											<h:message for="idTipoProduto" styleClass="msgErro" />
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Grupo de Produto*"></h:outputLabel>
											<h:selectOneMenu id="grupos" styleClass="field select"
												style="width: 200px;" value="#{produtoBB.idGrupo}"
												required="true">
												<f:selectItems id="gruposSelectItems"
													value="#{produtoBB.grupos}" />
											</h:selectOneMenu>
											<h:message for="idGrupo" styleClass="msgErro" />
										</div>
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Unidade*"></h:outputLabel>
											<h:selectOneMenu id="unidades" styleClass="field select"
												style="width: 200px;" value="#{produtoBB.idUnidade}"
												required="true">
												<f:selectItems id="unidadeSelectItems"
													value="#{produtoBB.unidades}" />
											</h:selectOneMenu>
											<h:message for="idUnidade" styleClass="msgErro" />
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Imposto*"></h:outputLabel>
											<h:selectOneMenu id="impostos" styleClass="field select"
												style="width: 200px;" value="#{produtoBB.idImposto}"
												required="true">
												<f:selectItems id="impostosSelectItems"
													value="#{produtoBB.impostos}" />
											</h:selectOneMenu>
											<h:message for="idImposto" styleClass="msgErro" />
										</div>
									</li>
									<li class="normal">		
										<div>
											<h:outputLabel styleClass="desc" value="Fabricante*"></h:outputLabel>
											<h:selectOneMenu id="Fabricantes" styleClass="field select"
												style="width: 200px;" value="#{produtoBB.idFabricante}"
												required="true">
												<f:selectItems id="fabricantesSelectItems"
													value="#{produtoBB.fabricantes}" />
											</h:selectOneMenu>
											<h:message for="idFabricante" styleClass="msgErro" />
										</div>
									</li>
								</ul>
							</div>

							<div id="tabDiv1" style="display:none;">
								<ul>
									<li class="normal">
										<div class="div-auto-scroll"
											style="width:400px !important; height: 142px;">
											<h:selectManyCheckbox id="listaLojas" layout="pageDirection"
												required="false" styleClass="label"
												value="#{produtoBB.listaLojas}">
												<f:selectItems value="#{produtoBB.lojas}" />
											</h:selectManyCheckbox>
											<h:message for="listaLojas" styleClass="msgErro" />
										</div>

									</li>
								</ul>
							</div>
							<ul>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" immediate="true" id="botaoVoltar" action="#{produtoBB.voltarConsulta}" value="Voltar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoAlterar" action="#{produtoBB.alterar}" value="Alterar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoExcluir" action="#{produtoBB.excluir}" value="Excluir"></h:commandButton>
								</li>
							</ul>
						</h:form>
						<div class="clear"></div>
					</div>
				</div>
				
  	      </body>	
	</f:view>
</html>