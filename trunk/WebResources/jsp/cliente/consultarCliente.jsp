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
	<script type="text/javascript">
		
		window.onload = function(){ inicializar() };
		
		function inicializar() {
			$("input.tipopessoa").each(function(i){
				$(this).click(function() {mostraCampos(this.value)});
			});
			if ($('[name=frmConsultarCliente:tipoPessoa]:checked').val() != "undefined") {
				mostraCampos($('[name=frmConsultarCliente:tipoPessoa]:checked').val());
			}
		}
		
		function mostraCampos(str) {
			//frmInserirCliente:comissao
			var flag = new String(str);
			if (flag.toUpperCase() == "F") {
			   
				$("input.tipocpfcnpj").each(function(i){
					$(this).unbind('blur');
					$(this).unbind('keydown');
					$(this).bind('blur',function(event){validaCPF(this);});
					$(this).bind('keydown',function(event){FormataCPF(this,event);});
					getId(this.id).maxLength = "14";
				});
			} else {
				$("input.tipocpfcnpj").each(function(i){
					$(this).unbind('blur');
					$(this).unbind('keydown');
					$(this).bind('blur',function(event){validaCNPJ(this);});
					$(this).bind('keydown',function(event){FormataCNPJ(this,event);});
					getId(this.id).maxLength = "18";
				});
			}
		}
		
		</script>
		</head>
		<body onload="exibirMensagemErro();inicializar();">
			<div id="outer">
				<div id="topoGeral">
					<div id="tituloPaginaGeral">
						<strong>
							<h:outputText value="#{msgs.consultarCliente}"></h:outputText>
						</strong>
					</div>				
				</div>
				<div id="content">
					<div id="primarioContentContainerInternas">
						<h:form id="frmConsultarCliente" binding="#{clienteBB.init}">
							<fieldset>
								<legend>Opções de filtro:</legend>
								<ul>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Código"></h:outputLabel>
											<h:inputText styleClass="field text" id="id" maxlength="4" onfocus="this.select();" onclick="this.select();" onkeypress="return SoNumero(event);"
												value="#{clienteBB.id}" size="4" required="false">
												<f:validateLength maximum="4" />
												<f:validator validatorId="LongValidator"/>
											</h:inputText>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Tipo Pessoa*"></h:outputLabel>
											<h:selectOneRadio  styleClass="field select tipopessoa" id="tipoPessoa" 
												value="#{clienteBB.idTipoPessoa}" layout="lineDirection" >
												<f:selectItems id="tipoPessoaLista" value="#{clienteBB.listaTipoPessoa}"/>
											</h:selectOneRadio>
										</div>
									<br />
									<br />
										<div>
											<h:outputLabel styleClass="desc" value="CPF/CNPJ"></h:outputLabel>
											<h:inputText styleClass="field text" id="cpfCnpj" maxlength="18"
												value="#{clienteBB.cpfCnpj}" size="18" required="false"
												onfocus="this.select();" onclick="this.select();"
												onkeypress="return SoNumero(event);">
												<f:validateLength minimum="11" maximum="18" />
											</h:inputText>
										</div>												
										<div>
											<h:outputLabel styleClass="desc" value="Nome Cliente/Razão Social"></h:outputLabel>
											<h:inputText styleClass="field text" id="nomeCliente" maxlength="50" size="50"
												value="#{clienteBB.nomeCliente}">
												<f:validateLength maximum="50" />
											</h:inputText>
										</div>						
									</li>
								</ul>
							</fieldset>	
							<div class="listagem">
								<t:dataTable value="#{clienteBB.clientes}"
									var="cliente" rowClasses="rowA,rowB" width="95%" renderedIfEmpty="false">
									<h:column>
										<f:facet name="header">
											<h:outputText value="Código" /> 
										</f:facet>
										<h:outputText value="#{cliente.id}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Nome Cliente" />
										</f:facet>
										<h:commandLink value="#{cliente.nomeCliente}" action="#{clienteBB.consultar}" rendered="#{cliente.tipoPessoa == 'F'}">
											<f:param name="id" value="#{cliente.id}"/>						
										</h:commandLink>
										<h:commandLink value="#{cliente.nomeFantasia}" action="#{clienteBB.consultar}" rendered="#{cliente.tipoPessoa == 'J'}">
											<f:param name="id" value="#{cliente.id}"/>						
										</h:commandLink>
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Tipo Pessoa" /> 
										</f:facet>
										<h:outputText rendered="#{cliente.tipoPessoa == 'F'}" value="Física" /> 
										<h:outputText rendered="#{cliente.tipoPessoa == 'J'}" value="Jurídica" /> 
									</h:column>	
									<h:column>
										<f:facet name="header">
											<h:outputText value="CPF/CNPJ" /> 
										</f:facet>
										<h:outputText value="#{cliente.cpfCnpj}" /> 
									</h:column>		
									<h:column>
										<f:facet name="header">
											<h:outputText value="Data Cadastro" /> 
										</f:facet>
										<h:outputText value="#{cliente.dataCadastro}" /> 
									</h:column>							
								</t:dataTable>
								<ul>
									<li class="normal">
										<div>
											<%@ include file="/jsp/mensagem_erro.jsp"%>
										</div>
									</li>
								</ul>	
							</div>
							<ul>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" action="#{clienteBB.resetBB}" id="botaoLimpar" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoConsultar" action="#{clienteBB.consultar}" value="Consultar"></h:commandButton>
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