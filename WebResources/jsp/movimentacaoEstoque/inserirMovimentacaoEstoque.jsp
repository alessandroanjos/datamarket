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
				  var formaux = document.forms[form];
				 
                  features="height=500,width=600,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes,dependent=yes";             
				  winId=window.open('/EnterpriseServer/jsp/popup/PopUpProdutosEstoque.faces','list',features);

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
                             form[formId+":quantidade"].focus();
                             winId.close();
            }
      </script>

	</head>
	<body>
	<div id="outer">
		<div id="topoGeral">
			<div id="tituloPaginaGeral">
				<strong>
					<h:outputText value="#{msgs.inserirMovimentacaoEstoque}"></h:outputText>
				</strong>
			</div>				
		</div>
	
			<div id="content">
				<div id="primarioContentContainerInternas" >
<h:form id="frmInserirMovimentacaoEstoque">
<h:inputHidden id="codigoUsuario" value="#{loginBB.usuarioLogado.id}">
</h:inputHidden>

					<ul>
						<li class="normal">
							<div>
								<h:messages errorClass="msgSistemaErro"
									infoClass="msgSistemaSucesso" globalOnly="true"
									showDetail="true" />
							</div>
						</li>
						
						<li class="normal" style="border-bottom:0;">
					    <div>
								<h:outputLabel styleClass="desc" value="Código*"></h:outputLabel>
								<h:inputText styleClass="field text ativo" id="id" maxlength="4" onkeypress="return SoNumero(event);"
									value="#{movimentacaoEstoqueBB.id}" size="4" required="true">
									<f:validateLength maximum="4" />
									<f:validator validatorId="LongValidator" />
								</h:inputText>
							</div>
							<h:message for="id" styleClass="msgErro" />
						</li>
						
						<li class="normal">	
							<div>
								<h:outputLabel styleClass="desc" value="Estoque Saida"></h:outputLabel>
								<h:selectOneMenu id="idEstoqueSaida" styleClass="field text select"
									value="#{movimentacaoEstoqueBB.idEstoqueSaida}" style="width:100px;">
									<f:selectItems id="estoqueSelectItemsSaida"
										value="#{movimentacaoEstoqueBB.estoques}" />
								</h:selectOneMenu>
								<h:message for="idEstoqueSaida" styleClass="errors" />
							</div>	
							<div>	
								<h:outputLabel styleClass="desc" value="Estoque Entrada"></h:outputLabel>
								<h:selectOneMenu id="idEstoqueEntrada" styleClass="field text select"
									value="#{movimentacaoEstoqueBB.idEstoqueEntrada}" style="width:100px;">
									<f:selectItems id="estoqueSelectItemsEntrada"
										value="#{movimentacaoEstoqueBB.estoques}" />
								</h:selectOneMenu>
								<h:message for="idEstoqueEntrada" styleClass="errors" />
							</div>
						</li>

						<li class="normal">
							<div>
								<h:outputLabel styleClass="desc" value="Código Produto*"></h:outputLabel>
								<h:inputText styleClass="field text" id="idProduto"
									maxlength="9" value="#{movimentacaoEstoqueBB.idProduto}" size="12">
									<f:validateLength maximum="9" />
									<f:validator validatorId="LongValidator" />
								</h:inputText>
							</div>	
							<div>		
								<h:outputLabel styleClass="desc" value="Descrição*"></h:outputLabel>
								<h:inputText styleClass="field text" id="descricao" 
									maxlength="50" size="50" value="#{movimentacaoEstoqueBB.descricao}">
									<f:validateLength maximum="50" /> 
								</h:inputText>
							</div>	
							<div>
								<h:outputLabel styleClass="desc" value="Qtd."></h:outputLabel>
								<h:inputText styleClass="field text" id="quantidade" maxlength="7"
									size="7" value="#{movimentacaoEstoqueBB.quantidade}" required="false"
									onkeypress="return SoNumero(event);" onkeydown="Formata('frmInserirMovimentacaoEstoque:quantidade',7,3,event);">
									<f:validateLength maximum="7" />
									<f:validateDoubleRange minimum="0.00" maximum="9999.999" />
									<f:validator validatorId="BigDecimalValidator" />
								</h:inputText>
								<h:message for="quantidade" styleClass="msgErro" />
							</div>
						</li>	

						<li class="buttons" style="position:relative; top:-15px;">
							<h:commandButton styleClass="btTxt" id="botaoInserirProduto"
								action="#{movimentacaoEstoqueBB.inserirProduto}"
								value="Inserir Produto">
								</h:commandButton>
							<h:commandButton styleClass="btTxt" id="botaoConsultarProduto"
								onmousedown="showPopUp(this,'frmInserirMovimentacaoEstoque','find')"
								onclick="return false" value="Consultar Produto ">
								</h:commandButton>
						</li>
					</ul>

						<div class="listagem">
							<t:dataTable value="#{movimentacaoEstoqueBB.arrayProduto}"
								var="produtoMovimentacao" rowClasses="rowA,rowB" width="95%">
								<h:column>
									<f:facet name="header">
										<h:outputText value="Produto" />
									</f:facet>
									<h:outputText value="#{produtoMovimentacao.produto.id}" />
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="Descrição" />
									</f:facet>
									<h:outputText value="#{produtoMovimentacao.produto.descricaoCompleta}" />
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="Qtd." />
									</f:facet>
									<h:outputText value="#{produtoMovimentacao.quantidade}" />
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="Excluir" />
									</f:facet>
									<h:commandLink  value=""
										action="#{movimentacaoEstoqueBB.excluirProduto}">
										<h:commandButton type="button" image="images/tree/TortoiseDeleted.ico"/>
										<f:param name="idExcluir" value="#{produtoMovimentacao.produto.id}" />
									</h:commandLink>
								</h:column>
							</t:dataTable>
						</div>

					<ul>
						<li class="buttons">
							<h:commandButton styleClass="btTxt" immediate="true"
								id="botaoLimpar" type="reset" value="Limpar"></h:commandButton>
							<h:commandButton styleClass="btTxt" id="botaoInserir"
								action="#{movimentacaoEstoqueBB.inserir}" value="Inserir"></h:commandButton>
						</li>
					</ul>
</h:form>
				</div>
				<div class="clear"></div>
			</div>
	 </body>			
	</f:view>
</html>
