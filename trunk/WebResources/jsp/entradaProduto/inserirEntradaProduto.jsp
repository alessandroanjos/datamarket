<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="https://ajax4jsf.dev.java.net/ajax" prefix="a4j"%>






<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

	<head>

		<title>INFINITY - DataMarket - Enterprise Server</title>

		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />

		<script type="text/javascript" src="/EnterpriseServer/js/jquery.js"></script>
		<script type="text/javascript" src="/EnterpriseServer/js/global.js"></script>
		<t:stylesheet path="/EnterpriseServer/css/form.css"></t:stylesheet>
		<t:stylesheet path="/EnterpriseServer/css/default.css"></t:stylesheet>

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
                             winId.close();
            }
      </script>

	</head>
	<f:view>


		<h:form id="frmInserirEntradaProdutos">
			<f:loadBundle basename="resources.mensagens" var="msgs" />
			<f:subview id="subTopo" rendered="true">
				<jsp:include
					page="/jsp/topo.jsp?tituloPagina=#{msgs.inserirEntradaProduto}&user=#{loginBB.usuarioLogado.nome}"></jsp:include>
			</f:subview>
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
								<h:inputText styleClass="field text ativo" id="id" maxlength="2"
									value="#{entradaProdutoBB.id}" size="3" rendered="true">
									<f:validateLength maximum="2" />
									<f:validator validatorId="LongValidator" />
								</h:inputText>
								<h:message for="id" styleClass="msgErro" />
							</div>

							<div>
								<h:outputLabel styleClass="desc" value="N.Fiscal*"></h:outputLabel>
								<h:inputText styleClass="field text" id="numeroNota"
									maxlength="15" size="15" rendered="true"
									value="#{entradaProdutoBB.numeroNota}">
									<f:validateLength maximum="15" />
								</h:inputText>
								<h:message for="numeroNota" styleClass="msgErro" />
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Data Emissão"></h:outputLabel>
								<t:inputText readonly="false" maxlength="10" size="10"
									styleClass="field text" forceId="dataEmissaoNota"
									value="#{entradaProdutoBB.dataEmissaoNota}"
									id="dataEmissaoNota" />
								<h:message for="dataEmissaoNota" styleClass="msgErro" />
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Data Entrada"></h:outputLabel>
								<t:inputText readonly="false" styleClass="field text"
									maxlength="10" size="10" forceId="dataEntrada"
									value="#{entradaProdutoBB.dataEntrada}" id="dataEntrada" />
								<h:message for="dataEntrada" styleClass="msgErro" />
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Fornecedor"></h:outputLabel>
								<h:selectOneMenu id="idFornecedor" styleClass="field text"
									value="#{entradaProdutoBB.idFornecedor}">
									<f:selectItems id="fornecedorSelectItems"
										value="#{entradaProdutoBB.fornecedores}" />
								</h:selectOneMenu>
								<h:message for="idFornecedor" styleClass="errors" />
							</div>


						</li>

						<li class="normal">
							<div>
								<h:outputLabel styleClass="desc" value="Valor da Nota"></h:outputLabel>
								<h:inputText styleClass="field text" id="valor" maxlength="10"
									size="10" value="#{entradaProdutoBB.valor}" dir="rtl"
									required="true"
									onkeypress="Formata('frmInserirEntradaProdutos:valor',9,2);">
									<f:validateLength maximum="10" />
									<f:validateDoubleRange minimum="0.00" maximum="9999999.99" />
									<f:validator validatorId="BigDecimalValidator" />
								</h:inputText>
								<h:message for="valor" styleClass="msgErro" />
							</div>

							<div>
								<h:outputLabel styleClass="desc" value="Frete"></h:outputLabel>
								<h:inputText styleClass="field text" id="frete" maxlength="7"
									size="7" value="#{entradaProdutoBB.frete}" dir="rtl"
									required="true"
									onkeypress="Formata('frmInserirEntradaProdutos:frete',7,2);">
									<f:validateLength maximum="7" />
									<f:validateDoubleRange minimum="0.00" maximum="9999.99" />
									<f:validator validatorId="BigDecimalValidator" />
								</h:inputText>
								<h:message for="frete" styleClass="msgErro" />
							</div>

							<div>
								<h:outputLabel styleClass="desc" value="Desc"></h:outputLabel>
								<h:inputText styleClass="field text" id="desconto" maxlength="7"
									size="7" value="#{entradaProdutoBB.desconto}" dir="rtl"
									required="true"
									onkeypress="Formata('frmInserirEntradaProdutos:desconto',7,2);">
									<f:validateLength maximum="7" />
									<f:validateDoubleRange minimum="0.00" maximum="9999.99" />
									<f:validator validatorId="BigDecimalValidator" />
								</h:inputText>
								<h:message for="desconto" styleClass="msgErro" />
							</div>

							<div>
								<h:outputLabel styleClass="desc" value="IPI"></h:outputLabel>
								<h:inputText styleClass="field text" id="ipi" maxlength="7"
									size="7" value="#{entradaProdutoBB.ipi}" dir="rtl"
									required="true"
									onkeypress="Formata('frmInserirEntradaProdutos:ipi',9,2);">
									<f:validateLength maximum="7" />
									<f:validateDoubleRange minimum="0.00" maximum="9999.99" />
									<f:validator validatorId="BigDecimalValidator" />
								</h:inputText>
								<h:message for="ipi" styleClass="msgErro" />
							</div>

							<div>
								<h:outputLabel styleClass="desc" value="ICMS"></h:outputLabel>
								<h:inputText styleClass="field text" id="icms" maxlength="7"
									size="7" value="#{entradaProdutoBB.icms}" dir="rtl"
									required="true"
									onkeypress="Formata('frmInserirEntradaProdutos:icms',9,2);">
									<f:validateLength maximum="7" />
									<f:validateDoubleRange minimum="0.00" maximum="9999.99" />
									<f:validator validatorId="BigDecimalValidator" />
								</h:inputText>
								<h:message for="icms" styleClass="msgErro" />
							</div>

						</li>

						<li class="normal">
							<div>
								<h:outputLabel styleClass="desc" value="Código Produto*"></h:outputLabel>
								<h:inputText styleClass="field text ativo" id="idProduto"
									maxlength="9" value="#{entradaProdutoBB.idProduto}" size="9"
									rendered="true">
									<f:validateLength maximum="9" />
									<f:validator validatorId="LongValidator" />
								</h:inputText>
								<h:message for="idProduto" styleClass="msgErro" />
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Descrição*"></h:outputLabel>
								<h:inputText styleClass="field text ativo" id="descricao" 
									maxlength="50" size="50" rendered="true"
									value="#{entradaProdutoBB.descricao}">
									<f:validateLength maximum="50" />
								</h:inputText>
								<h:message for="descricao" styleClass="msgErro" />
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Estoque"></h:outputLabel>
								<h:selectOneMenu id="idEstoque" styleClass="field text"
									value="#{entradaProdutoBB.idEstoque}">
									<f:selectItems id="estoqueSelectItems"
										value="#{entradaProdutoBB.estoques}" />
								</h:selectOneMenu>
								<h:message for="idEstoque" styleClass="errors" />
							</div>
						</li>	
						<li class="normal">							
							<div>
								<h:outputLabel styleClass="desc" value="Qtd."></h:outputLabel>
								<h:inputText styleClass="field text" id="quantidade" maxlength="7"
									size="7" value="#{entradaProdutoBB.quantidade}" dir="rtl"
									required="true"
									onkeypress="Formata('frmInserirEntradaProdutos:quantidade',7,2);">
									<f:validateLength maximum="7" />
									<f:validateDoubleRange minimum="0.00" maximum="9999.99" />
									<f:validator validatorId="BigDecimalValidator" />
								</h:inputText>
								<h:message for="quantidade" styleClass="msgErro" />
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Unitário"></h:outputLabel>
								<h:inputText styleClass="field text" id="precoUnitario" maxlength="7"
									size="7" value="#{entradaProdutoBB.precoUnitario}" dir="rtl"
									required="true"
									onkeypress="Formata('frmInserirEntradaProdutos:precoUnitario',7,2);">
									<f:validateLength maximum="7" />
									<f:validateDoubleRange minimum="0.00" maximum="9999.99" />
									<f:validator validatorId="BigDecimalValidator" />
								</h:inputText>
								<h:message for="precoUnitario" styleClass="msgErro" />
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Desconto"></h:outputLabel>
								<h:inputText styleClass="field text" id="descontoProduto" maxlength="7"
									size="7" value="#{entradaProdutoBB.descontoProduto}" dir="rtl"
									required="true"
									onkeypress="Formata('frmInserirEntradaProdutos:descontoProduto',7,2);">
									<f:validateLength maximum="7" />
									<f:validateDoubleRange minimum="0.00" maximum="9999.99" />
									<f:validator validatorId="BigDecimalValidator" />
								</h:inputText>
								<h:message for="descontoProduto" styleClass="msgErro" />
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="ICMS"></h:outputLabel>
								<h:inputText styleClass="field text" id="icmsProduto" maxlength="7"
									size="7" value="#{entradaProdutoBB.icmsProduto}" dir="rtl"
									required="true"
									onkeypress="Formata('frmInserirEntradaProdutos:icmsProduto',7,2);">
									<f:validateLength maximum="7" />
									<f:validateDoubleRange minimum="0.00" maximum="9999.99" />
									<f:validator validatorId="BigDecimalValidator" />
								</h:inputText>
								<h:message for="icmsProduto" styleClass="msgErro" />
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="IPI"></h:outputLabel>
								<h:inputText styleClass="field text" id="ipiProduto" maxlength="7"
									size="7" value="#{entradaProdutoBB.ipiProduto}" dir="rtl"
									required="true"
									onkeypress="Formata('frmInserirEntradaProdutos:ipiProduto',7,2);">
									<f:validateLength maximum="7" />
									<f:validateDoubleRange minimum="0.00" maximum="9999.99" />
									<f:validator validatorId="BigDecimalValidator" />
								</h:inputText>
								<h:message for="ipiProduto" styleClass="msgErro" />
							</div>								
						</li>	
						<li class="buttons">
							<h:commandButton styleClass="btTxt" id="botaoInserirProduto"
								action="#{entradaProdutoBB.inserirProdutoEntrada}"
								value="Inserir Produto"></h:commandButton>
							<h:commandButton styleClass="btTxt" id="botaoConsultarProduto"
								onmousedown="showPopUp(this,'frmInserirEntradaProdutos','find')"
								onclick="return false" value="Consultar Produt "></h:commandButton>
						</li>

						<div class="listagem">
							<t:dataTable value="#{entradaProdutoBB.arrayProduto}"
								var="produtoEntrada" rowClasses="rowA,rowB" width="100%">
								<h:column>
									<f:facet name="header">
										<h:outputText value="Produto" />
									</f:facet>
									<h:outputText value="#{produtoEntrada.pk.produto.id}" />
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="Descrição" />
									</f:facet>
									<h:outputText value="#{produtoEntrada.pk.produto.descricaoCompleta}" />
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="Excluir" />
									</f:facet>
									<h:commandLink  value=""
										action="#{entradaProdutoBB.excluirProduto}">
										<h:commandButton type="button" image="images/tree/TortoiseDeleted.ico"/>
										<f:param name="id" value="#{produtoEntrada.pk.produto.id}" />
									</h:commandLink>
								</h:column>
							</t:dataTable>
						</div>



						<li class="buttons">
							<h:commandButton styleClass="btTxt" immediate="true"
								id="botaoLimpar" type="reset" value="Limpar"></h:commandButton>
							<h:commandButton styleClass="btTxt" id="botaoInserir"
								action="#{entradaProdutoBB.inserir}" value="Inserir"></h:commandButton>
						</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<jsp:include page="/jsp/rodape.jsp"></jsp:include>
		</h:form>
				
	</f:view>
</html>
