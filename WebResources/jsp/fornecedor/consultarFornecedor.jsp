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
	<body onload="exibirMensagemErro();inicializar();">
	<div id="outer">
		<div id="topoGeral">
			<div id="tituloPaginaGeral">
				<strong>
					<h:outputText value="#{msgs.manterFornecedor}"></h:outputText>
				</strong>
			</div>				
		</div>		
		<h:form id="frmConsultarFornecedor" binding="#{fornecedorBB.init}">
					
				<div id="content">
				
						<div id="primarioContentContainerInternas">
							<fieldset>
								<legend>Op��es de filtro:</legend>
								<ul>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="C�digo"></h:outputLabel>
											<h:inputText styleClass="field text" id="id" maxlength="4" 
												onfocus="this.select();" onclick="this.select();"
												onkeypress="return SoNumero(event);"
												value="#{fornecedorBB.id}" size="4" required="false">
												<f:validateLength maximum="4" />
												<f:validator validatorId="LongValidator"/>
											</h:inputText>
											
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Tipo Pessoa*"></h:outputLabel>
											<h:selectOneRadio  styleClass="field select" id="tipoPessoa" 
												value="#{fornecedorBB.idTipoPessoa}" layout="lineDirection" >
												<f:selectItems id="tipoPessoaLista" value="#{fornecedorBB.listaTipoPessoa}"/>
											</h:selectOneRadio>
											
										</div>
									<br />
									<br />
									<div>
										<h:outputLabel styleClass="desc" value="CPF/CNPJ*"></h:outputLabel>
										<h:inputText styleClass="field text tipocpfcnpj" id="cpfCnpj" maxlength="18" size="18" value="#{fornecedorBB.cpfCnpj}" required="false"
										onkeypress="return SoNumero(event);">
											<f:validateLength minimum="11" maximum="18" />
										</h:inputText>
									</div>
																				
										<div>
											<h:outputLabel styleClass="desc" value="Nome Fornecedor/Raz�o Social"></h:outputLabel>
											<h:inputText styleClass="field text" id="nomeFornecedor" maxlength="50" size="50"
												value="#{fornecedorBB.nomeFornecedor}">
												<f:validateLength maximum="50" />
											</h:inputText>
											
										</div>						
									</li>
								</ul>
							</fieldset>	
							<div class="listagem">
								<t:dataTable value="#{fornecedorBB.fornecedores}"
									var="fornecedor" rowClasses="rowA,rowB" width="95%" renderedIfEmpty="false">
									<h:column>
										<f:facet name="header">
											<h:outputText value="C�digo" /> 
										</f:facet>
										<h:outputText value="#{fornecedor.id}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Nome Fornecedor" />
										</f:facet>
										<h:commandLink value="#{fornecedor.nomeFornecedor}" action="#{fornecedorBB.consultar}">
											<f:param name="id" value="#{fornecedor.id}"/>						
										</h:commandLink>
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Tipo Pessoa" /> 
										</f:facet>
										<h:outputText value="#{fornecedor.tipoPessoa}" /> 
									</h:column>	
									<h:column>
										<f:facet name="header">
											<h:outputText value="CPF/CNPJ" /> 
										</f:facet>
										<h:outputText value="#{fornecedor.cpfCnpj}" /> 
									</h:column>		
									<h:column>
										<f:facet name="header">
											<h:outputText value="Data Cadastro" /> 
										</f:facet>
										<h:outputText value="#{fornecedor.dataCadastro}" /> 
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
									<h:commandButton styleClass="btTxt" action="#{fornecedorBB.resetBB}" id="botaoLimpar" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoConsultar" action="#{fornecedorBB.consultar}" value="Consultar"></h:commandButton>
								</li>						
							</ul>
						</div>
						<div class="clear"></div>
					</div>


		</h:form>
	 </body>	
	</f:view>
</html>