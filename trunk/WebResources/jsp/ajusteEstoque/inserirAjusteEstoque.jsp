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

		<meta http-equiv="pragma" content="no-cache"/>
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
	<script language="javascript">

            var formId; // referência ao formulário principal

            var winId;  // referência à janela popup

            // Esta função faz a chamada da janela popup.

            //

            function showPopUp(action, form, target) {

                  formId=form;
				  if (winId != null) {
				      winId.close();
				  }
                  features="height=500,width=600,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes,dependent=yes";             
				  winId=window.open('/EnterpriseServer/jsp/popup/PopUpProdutos.faces','list',features);
				  // Formulário escondido
                  hform=document.forms[form];                

            }

            // Esta função é chamada pela janela popup 

            // quando um usuário clica em um item na listagem.

            // O item selecionado é copiado para um campo de texto

            // no formulário principal.

             function setAtributo(idProduto,descricao) {
                             var form = document.forms[formId];   
                             form[formId+":idProduto"].value=idProduto; 
                             form[formId+":descricao"].value=descricao;  
                             form[formId+":quantidadeAntes"].focus();
                             winId.close();
            }
      </script>
	<body>
	<div id="outer">
		<div id="topoGeral">
			<div id="tituloPaginaGeral">
				<strong>
					<h:outputText value="#{msgs.inserirAjusteEstoque}"></h:outputText>
				</strong>
			</div>				
		</div>
		<h:form id="frmInserirAjusteEstoque">
			<div id="content">
				<div id="primarioContentContainerInternas" >
					<ul>
						<li class="normal">
							<div>
								<h:messages errorClass="msgSistemaErro"
									infoClass="msgSistemaSucesso" globalOnly="true"
									showDetail="true" />
							</div>
						</li>
						<li class="normal">
							<div>
								<h:outputLabel styleClass="desc" value="Código*"></h:outputLabel>
								<h:inputText styleClass="field text ativo" id="id" maxlength="5" onkeypress="return SoNumero(event);"
									value="#{ajusteEstoqueBB.id}" size="3" rendered="true">
									<f:validateLength maximum="5" />
									<f:validator validatorId="LongValidator" />
								</h:inputText>
								<h:message for="id" styleClass="msgErro" />
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Estoque"></h:outputLabel>
								<h:selectOneMenu id="idEstoque" styleClass="field text"
									value="#{ajusteEstoqueBB.idEstoque}">
									<f:selectItems id="estoqueSelectItems"
										value="#{ajusteEstoqueBB.estoques}" />
								</h:selectOneMenu>
								<h:message for="idEstoque" styleClass="errors" />
							</div>
						</li>

						<li class="normal">
							<div>
								<h:outputLabel styleClass="desc" value="Código Produto*"></h:outputLabel>
								<h:inputText styleClass="field text ativo" id="idProduto"  onkeypress="return SoNumero(event);"
									maxlength="9" value="#{ajusteEstoqueBB.idProduto}" size="9">
									<f:validateLength maximum="9" />
									<f:validator validatorId="LongValidator" />
								</h:inputText>
								
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Descrição*"></h:outputLabel>
								<h:inputText styleClass="field text ativo" id="descricao" 
									maxlength="50" size="50" value="#{ajusteEstoqueBB.descricao}">
									<f:validateLength maximum="50" />
								</h:inputText>
								
							</div>
							<div style="padding-top:9px;">
								<h:commandButton styleClass="btTxt" id="botaoConsultarProduto"
								onmousedown="showPopUp(this,'frmInserirAjusteEstoque','find')"
								onclick="return false" value="Consultar Produto "></h:commandButton>
							</div>
						</li>

						<li class="normal">
							<div>
								<h:outputLabel styleClass="desc" value="Qtd.Antes"></h:outputLabel>
								<h:inputText styleClass="field text" id="quantidadeAntes" maxlength="7"
									size="7" value="#{ajusteEstoqueBB.quantidadeAntes}" 
									required="true"
									onkeypress="Formata('frmInserirAjusteEstoque:quantidadeAntes',7,2);">
									<f:validateLength maximum="7" />
									<f:validateDoubleRange minimum="0.00" maximum="9999.99" />
									<f:validator validatorId="BigDecimalValidator" />
								</h:inputText>
								<h:message for="quantidadeAntes" styleClass="msgErro" />
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Qtd.Depois"></h:outputLabel>
								<h:inputText styleClass="field text" id="quantidadeDepois" maxlength="7"
									size="7" value="#{ajusteEstoqueBB.quantidadeDepois}" 
									required="true"
									onkeypress="Formata('frmInserirAjusteEstoque:quantidadeDepois',7,2);">
									<f:validateLength maximum="7" />
									<f:validateDoubleRange minimum="0.00" maximum="9999.99" />
									<f:validator validatorId="BigDecimalValidator" />
								</h:inputText>
								<h:message for="quantidadeDepois" styleClass="msgErro" />
							</div>
						</li>	

					<ul>
						<li class="buttons">
							<h:commandButton styleClass="btTxt" immediate="true"
								id="botaoLimpar" type="reset" value="Limpar"></h:commandButton>
							<h:commandButton styleClass="btTxt" id="botaoInserir"
								action="#{ajusteEstoqueBB.inserir}" value="Inserir"></h:commandButton>
						</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			
		</h:form>
	 </body>			
	</f:view>
</html>
