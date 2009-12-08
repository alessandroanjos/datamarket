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
		
		<script type="text/javascript">
		
		window.onload = function(){ inicializar() };
		
		function inicializar() {
			$("input.field, select.field").each(function(i){
				$(this).focus(function() {this.style.backgroundColor = "#eff6ff"});
				$(this).blur(function() {this.style.backgroundColor = ""});
			});
		}
		
		</script>
	
	<body onload="exibirMensagemErro();inicializar();">
	<div id="outer">
		<div id="topoGeral">
			<div id="tituloPaginaGeral">
				<strong>
					<h:outputText value="#{msgs.consultarEntradaProdutos}"></h:outputText>
				</strong>
			</div>				
		</div>	
		<h:form id="frmConsultarEntradaProduto" binding="#{entradaProdutoBB.init}">
					
				<div id="content">
				
						<div id="primarioContentContainerInternas">
							<fieldset>
								<legend>Opções de filtro:</legend>
								<ul>
									<li class="normal">	
										<div>
											<h:outputLabel styleClass="desc" value="Loja"></h:outputLabel>
											<h:selectOneMenu id="idLoja" style="width: 200px;" value="#{entradaProdutoBB.idLoja}" onchange="submit();" valueChangeListener="#{entradaProdutoBB.carregarEstoquesPorLoja}"> 
												<f:selectItems id="lojasSelectItems" value="#{entradaProdutoBB.lojas}" />   
											</h:selectOneMenu>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Estoque"></h:outputLabel>
											<h:selectOneMenu id="idEstoque" style="width: 200px;" value="#{entradaProdutoBB.idEstoque}"> 
												<f:selectItems id="estoquesSelectItems" value="#{entradaProdutoBB.estoques}" />   
											</h:selectOneMenu>
										</div>									
										<br />
										<br />
										<div>
											<h:outputLabel styleClass="desc" value="N.Fiscal"></h:outputLabel>
											<h:inputText styleClass="field text" id="numeroNota"
												maxlength="15" size="15" rendered="true"
												value="#{entradaProdutoBB.numeroNota}">
												<f:validateLength maximum="15" />
											</h:inputText>
										</div>
										<div>	
											<h:outputLabel styleClass="desc" value="Status"></h:outputLabel>
											<h:selectOneMenu id="idStatus" style="width: 200px;" value="#{entradaProdutoBB.idStatus}"> 
												<f:selectItems id="statusSelectItems" value="#{entradaProdutoBB.listaStatus}" />   
											</h:selectOneMenu>
										</div>										
										<div>
											<h:outputLabel styleClass="desc" value="Data Inicial"></h:outputLabel>
											<t:inputText maxlength="10" size="10"
												styleClass="field text data" forceId="dataInicio"
												value="#{entradaProdutoBB.dataInicio}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }"
												id="dataInicio" />
										</div>	
										<div>	
											<h:outputLabel styleClass="desc" value=" Data Final "></h:outputLabel>
											<t:inputText styleClass="field text data"
												maxlength="10" size="10" forceId="dataFinal" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }"
												value="#{entradaProdutoBB.dataFinal}" id="dataFinal">										
												</t:inputText>
										</div>								
									</li>
								</ul>
							</fieldset>	
							<div class="listagem">
								<t:dataTable value="#{entradaProdutoBB.entradasProduto}"
									var="entradaProduto" rowClasses="rowA,rowB" width="95%" renderedIfEmpty="false">
									<h:column>
										<f:facet name="header">
											<h:outputText value="N.Fiscal" /> 
										</f:facet>
										<h:commandLink value="#{entradaProduto.numeroNota}" action="#{entradaProdutoBB.consultar}">
											<f:param name="id" value="#{entradaProduto.id}"/>						
										</h:commandLink>
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Dt.Entrada" />
										</f:facet>
										<h:outputText value="#{entradaProduto.dataEntrada}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Dt.Emissão" />
										</f:facet>
										<h:outputText value="#{entradaProduto.dataEmissaoNota}" /> 
									</h:column>	
									<h:column>
										<f:facet name="header">
											<h:outputText value="Fornecedor" /> 
										</f:facet>
										<h:outputText value="#{entradaProduto.fornecedor.nomeFornecedor}" rendered="#{entradaProduto.fornecedor.tipoPessoa == 'F'}" />
										<h:outputText value="#{entradaProduto.fornecedor.nomeFantasia}" rendered="#{entradaProduto.fornecedor.tipoPessoa == 'J'}" />
									</h:column>									
									<h:column>
										<f:facet name="header">
											<h:outputText value="Valor" /> 
										</f:facet>
										<h:outputText value="#{entradaProduto.valor}" /> 
									</h:column>		
									<h:column>
										<f:facet name="header">
											<h:outputText value="Desconto" /> 
										</f:facet>
										<h:outputText value="#{entradaProduto.desconto}" /> 
									</h:column>							
									<h:column>
										<f:facet name="header">
											<h:outputText value="Icms" /> 
										</f:facet>
										<h:outputText value="#{entradaProduto.icms}" /> 
									</h:column>							
									<h:column>
										<f:facet name="header">
											<h:outputText value="IPI" /> 
										</f:facet>
										<h:outputText value="#{entradaProduto.ipi}" /> 
									</h:column>	
									<h:column>
										<f:facet name="header">
											<h:outputText value="Status" /> 
										</f:facet>
										<h:outputText value="Ativa" rendered="#{entradaProduto.status == 'A'}" />
										<h:outputText value="Cancelada" rendered="#{entradaProduto.status == 'C'}" />
									</h:column>	
						
								</t:dataTable>
								<div>
										<%@ include file="/jsp/mensagem_erro.jsp"%> <!--  h  messages rendered="#{not entradaProdutoBB.existeRegistros}" errorClass="msgSistemaErro" infoClass="msgSistemaSucesso" globalOnly="true" showDetail="true" /> -->
								</div>								
							</div>
							<ul>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" action="#{entradaProdutoBB.resetBB}" id="botaoLimpar" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoConsultar" action="#{entradaProdutoBB.consultar}" value="Consultar"></h:commandButton>
								</li>						
							</ul>
						</div>
						<div class="clear"></div>
					</div>
		</h:form>
	  </body>	
	</f:view>
</html>