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
					<h:outputText value="#{msgs.consultarFabricante}"></h:outputText>
				</strong>
			</div>				
		</div>		
		<h:form id="frmConsultarFabricante" binding="#{fabricanteBB.init}">
					
				<div id="content">
				
						<div id="primarioContentContainerInternas">
							<fieldset>
								<legend>Opções de filtro:</legend>
								<ul>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Código"></h:outputLabel>
											<h:inputText styleClass="field text ativo" id="id" maxlength="4" 
												onfocus="this.select();" onclick="this.select();"
												onkeypress="return SoNumero(event);"
												value="#{fabricanteBB.id}" size="4" required="false">
												<f:validateLength maximum="4" />
												<f:validator validatorId="LongValidator"/>
											</h:inputText>
											
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Tipo Pessoa*"></h:outputLabel>
											<h:selectOneRadio  styleClass="field select" id="tipoPessoa" 
												value="#{fabricanteBB.idTipoPessoa}" layout="lineDirection" >
												<f:selectItems id="tipoPessoaLista" value="#{fabricanteBB.listaTipoPessoa}"/>
											</h:selectOneRadio>
											
										</div>
									<br />
									<br />
									<div>
										<h:outputLabel styleClass="desc" value="CPF/CNPJ*"></h:outputLabel>
										<h:inputText styleClass="field text tipocpfcnpj" id="cpfCnpj" maxlength="18" size="18" value="#{fabricanteBB.cpfCnpj}" required="false"
										onkeypress="return SoNumero(event);">
											<f:validateLength minimum="11" maximum="18" />
										</h:inputText>
									</div>
																				
										<div>
											<h:outputLabel styleClass="desc" value="Nome Fabricante/Razão Social"></h:outputLabel>
											<h:inputText styleClass="field text" id="nomeFabricante" maxlength="50" size="50"
												value="#{fabricanteBB.nomeFabricante}">
												<f:validateLength maximum="50" />
											</h:inputText>
											
										</div>						
									</li>
								</ul>
							</fieldset>	
							<div class="listagem">
								<t:dataTable value="#{fabricanteBB.fabricantes}"
									var="fabricante" rowClasses="rowA,rowB" width="95%" renderedIfEmpty="false">
									<h:column>
										<f:facet name="header">
											<h:outputText value="Código" /> 
										</f:facet>
										<h:outputText value="#{fabricante.id}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Nome Fabricante" />
										</f:facet>
										<h:commandLink value="#{fabricante.nomeFabricante}" action="#{fabricanteBB.consultar}">
											<f:param name="id" value="#{fabricante.id}"/>						
										</h:commandLink>
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Tipo Pessoa" /> 
										</f:facet>
										<h:outputText value="#{fabricante.tipoPessoa}" /> 
									</h:column>	
									<h:column>
										<f:facet name="header">
											<h:outputText value="CPF/CNPJ" /> 
										</f:facet>
										<h:outputText value="#{fabricante.cpfCnpj}" /> 
									</h:column>		
									<h:column>
										<f:facet name="header">
											<h:outputText value="Data Cadastro" /> 
										</f:facet>
										<h:outputText value="#{fabricante.dataCadastro}" /> 
									</h:column>							
								</t:dataTable>
								<ul>
									<li class="normal">
										<div>
											<%@ include file="/jsp/mensagem_erro.jsp"%> <!--  h  messages rendered="#{not fabricanteBB.existeRegistros}" errorClass="msgSistemaErro" infoClass="msgSistemaSucesso" globalOnly="true" showDetail="true" /> -->
										</div>
									</li>
								</ul>	
							</div>
							<ul>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" action="#{fabricanteBB.resetBB}" id="botaoLimpar" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoConsultar" action="#{fabricanteBB.consultar}" value="Consultar"></h:commandButton>
								</li>						
							</ul>
						</div>
						<div class="clear"></div>
					</div>


		</h:form>
	 </body>	
	</f:view>
</html>