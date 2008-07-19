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

		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />

		<script type="text/javascript" src="/EnterpriseServer/js/jquery.js"></script>
		<script type="text/javascript" src="/EnterpriseServer/js/global.js"></script>
		<script type="text/javascript" src="/EnterpriseServer/js/funcoes.js"></script>
		<t:stylesheet path="/css/form.css"></t:stylesheet>
		<t:stylesheet path="/css/default.css"></t:stylesheet>

		<script language="javascript">

		window.onload = function(){ inicializar() };
		
		function inicializar() {
			$("input.field, select.field").each(function(i){
				$(this).focus(function() {this.style.backgroundColor = "#eff6ff"});
				$(this).blur(function() {this.style.backgroundColor = ""});
			});
		}



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
            //
             function setAtributo(idProduto,descricao) {
                             var form = document.forms[formId];   
                             form[formId+":idProduto"].value=idProduto; 
                             form[formId+":descricao"].value=descricao;  
                             winId.close();
             }
      </script>

	</head>
	<body>
	<div id="outer">
		<div id="topoGeral">
			<div id="tituloPaginaGeral">
				<strong>
					<h:outputText value="#{msgs.manterEntradaProdutos}"></h:outputText>
				</strong>
			</div>				
		</div>	
		<h:form id="frmManterEntradaProdutos">

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
								<h:outputLabel styleClass="desc" value="Código"></h:outputLabel>
								<h:outputText value="#{entradaProdutoBB.id}" />
							</div>
							
							<div>
								<h:outputLabel styleClass="desc" value="N.Fiscal"></h:outputLabel>
								<h:outputText value="#{entradaProdutoBB.numeroNota}" />
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Data Emissão"></h:outputLabel>
								<h:outputText value="#{entradaProdutoBB.dataEmissaoNota}" />							
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Data Entrada"></h:outputLabel>
								<h:outputText value="#{entradaProdutoBB.dataEntrada}" />														
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Fornecedor"></h:outputLabel>
								<h:outputText value="#{entradaProdutoBB.fornecedor.nomeFantasia}" />							
							</div>


						</li>

						<li class="normal">
							<div>
								<h:outputLabel styleClass="desc" value="Valor da Nota"></h:outputLabel>
								<h:outputText value="#{entradaProdutoBB.valor}" />							
							</div>

							<div>
								<h:outputLabel styleClass="desc" value="Frete"></h:outputLabel>
								<h:outputText value="#{entradaProdutoBB.frete}" />							
							</div>

							<div>
								<h:outputLabel styleClass="desc" value="Desc"></h:outputLabel>
								<h:outputText value="#{entradaProdutoBB.desconto}" />							
							</div>

							<div>
								<h:outputLabel styleClass="desc" value="IPI"></h:outputLabel>
								<h:outputText value="#{entradaProdutoBB.ipi}" />							
							</div>

							<div>
								<h:outputLabel styleClass="desc" value="ICMS"></h:outputLabel>
								<h:outputText value="#{entradaProdutoBB.icms}" />							
							</div>

						</li>


						<div class="listagem">
							<t:dataTable value="#{entradaProdutoBB.arrayProduto}"
								var="produtoEntrada" rowClasses="rowA,rowB" width="95%">
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
										<h:outputText value="Estoque" />
									</f:facet>
									<h:outputText value="#{produtoEntrada.estoque.estoqueVenda}" />
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="Quant" />
									</f:facet>
									<h:outputText value="#{produtoEntrada.quantidade}" />
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="Preço Unit." />
									</f:facet>
									<h:outputText value="#{produtoEntrada.precoUnitario}" />
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="Desconto" />
									</f:facet>
									<h:outputText value="#{produtoEntrada.desconto}" />
								</h:column>	
								<h:column>
									<f:facet name="header">
										<h:outputText value="ICMS" />
									</f:facet>
									<h:outputText value="#{produtoEntrada.icms}" />
								</h:column>	
								<h:column>
									<f:facet name="header">
										<h:outputText value="IPI" />
									</f:facet>
									<h:outputText value="#{produtoEntrada.ipi}" />
								</h:column>	
								<h:column>
									<f:facet name="header">
										<h:outputText value="Total" />
									</f:facet>
									<h:outputText value="#{produtoEntrada.total}" />
								</h:column>																
							</t:dataTable>
						</div>



						<li class="buttons">
							<h:commandButton styleClass="btTxt" immediate="true" id="botaoVoltar"
								action="#{entradaProdutoBB.voltarConsulta}" value="Voltar"></h:commandButton>
						</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>

		</h:form>
	  </body>		
	</f:view>
</html>
