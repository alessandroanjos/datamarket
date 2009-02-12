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
 				if(getId("frmManterMovimentacaoEstoque:idProduto").value == ""){        		
                   formId=form;
				   if (winId != null) {
				       winId.close();
				   }
                   features="height=500,width=600,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes,dependent=yes";             
				   winId=window.open('/EnterpriseServer/jsp/popup/PopUpProdutos.faces?acao=init','list',features);
				   // Formulário escondido
                   hform=document.forms[form];                
                }else{
                	document.forms["frmManterMovimentacaoEstoque"].action = "/EnterpriseServer/jsp/movimentacaoEstoque/manterMovimentacaoEstoque.faces?acaoLocal=pesquisarProdutos&codigoProduto="+getId("frmManterMovimentacaoEstoque:idProduto").value;
					document.forms["frmManterMovimentacaoEstoque"].submit();
                }
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
	<body>
	<div id="outer">
		<div id="topoGeral">
			<div id="tituloPaginaGeral">
				<strong>
					<h:outputText value="#{msgs.manterMovimentacaoEstoque}"></h:outputText>
				</strong>
			</div>				
		</div>	
		<h:form id="frmManterMovimentacaoEstoque">

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
								<h:outputText value="#{movimentacaoEstoqueBB.id}" />
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Data Emissão"></h:outputLabel>
								<h:outputText value="#{movimentacaoEstoqueBB.dataMovimentacao}" />							
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Estoque Entrada"></h:outputLabel>
								<h:outputText value="#{movimentacaoEstoqueBB.estoqueEntrada.descricao}" />			
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Estoque Saida"></h:outputLabel>
								<h:outputText value="#{movimentacaoEstoqueBB.estoqueSaida.descricao}" />			
							</div>	
	


						</li>
						<li class="normal">
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
							</t:dataTable>
						</div>

						</li>

						<li class="buttons">
							<h:commandButton styleClass="btTxt" id="botaoVoltar" action="#{movimentacaoEstoqueBB.voltarConsulta}" value="Voltar"></h:commandButton>
							<h:commandButton styleClass="btTxt" id="botaoCancelar" disabled="#{movimentacaoEstoqueBB.status == 'C'}" action="#{movimentacaoEstoqueBB.cancelar}" value="Cancelar"></h:commandButton>
							<h:commandButton styleClass="btTxt" id="botaoImprimir" action="#{movimentacaoEstoqueBB.imprimirRecibo}" value="Imprimir"></h:commandButton>
						</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
		</h:form>
	 </body>			
	</f:view>
</html>
