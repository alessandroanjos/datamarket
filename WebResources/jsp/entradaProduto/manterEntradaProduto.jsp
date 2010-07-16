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
			
			strAbaCorrente = getId("frmManterEntradaProdutos:abaCorrente").value;
			//alert(strAbaCorrente);
			if(strAbaCorrente != ""){							
				selecionaMenuTab(strAbaCorrente);
			}else{
				selecionaMenuTab("tabMenuDiv0");
			}
		}



            var formId; // referência ao formulário principal
            var winId;  // referência à janela popup
            // Esta função faz a chamada da janela popup.
            //
            function showPopUp(action, form, target) {
                  if(getId("frmManterEntradaProdutos:idProduto").value == ""){        		
        		     formId=form;
        			 if (winId != null) {
        			     winId.close();
        			 }
                     features="height=500,width=600,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes,dependent=yes";             
        			 winId=window.open('/EnterpriseServer/jsp/popup/PopUpProdutos.faces?acao=init&enquadramento=2','list',features);
        			 // Formulário escondido
                     hform=document.forms[form];      
                  }else{
                	document.forms["frmManterEntradaProdutos"].action = "/EnterpriseServer/jsp/entradaProduto/manterEntradaProduto.faces?acaoLocal=pesquisarProdutos&codigoProduto="+getId("frmManterEntradaProdutos:idProduto").value;
					document.forms["frmManterEntradaProdutos"].submit();
                }          
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
	<body onload="inicializar();exibirMensagemErro();">
	<div id="outer">
		<div id="topoGeral">
			<div id="tituloPaginaGeral">
				<strong>
					<h:outputText value="#{msgs.manterEntradaProdutos}"></h:outputText>
				</strong>
			</div>				
		</div>	
		<div id="content">
			<div class="tabMenu">
				<ul>
					<li id="tabMenuDiv0" class="current" onclick="selecionaMenuTab(this.id);strAbaCorrente = this.id;">
						<span><a href="#">Geral</a> </span>
					</li>
					<li id="tabMenuDiv1" onclick="selecionaMenuTab(this.id);strAbaCorrente = this.id;">
						<span><a href="#">Pagamentos</a> </span>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
			<div id="primarioContentContainerInternas" >
				<h:form id="frmManterEntradaProdutos"  onsubmit="javascript:getId('frmManterEntradaProdutos:abaCorrente').value = strAbaCorrente;">
					<h:inputHidden id="abaCorrente" value="#{entradaProdutoBB.abaCorrente}"></h:inputHidden>							
					<div id="tabDiv0" style="height: 530px;">
						<ul>
							<li class="normal">
								<div>
									<%@ include file="/jsp/mensagem_erro.jsp"%> <!--  h  messages errorClass="msgSistemaErro"
										infoClass="msgSistemaSucesso" globalOnly="true"
										showDetail="true" /> -->
								</div>
							</li>
							<li class="normal">
								<!-- <div>
									<h:outputLabel styleClass="desc" value="Código"></h:outputLabel>
									<h:outputText value="#{entradaProdutoBB.id}" />
								</div>							
								 -->
								 <div>
									<h:outputLabel styleClass="desc" value="Loja"></h:outputLabel>
									<h:outputText value="#{entradaProdutoBB.estoque.pk.loja.nome}" />
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
									<h:outputText value="#{entradaProdutoBB.nomeFornecedor}" />							
								</div>
								<br/>
								<br/>
								<div>
									<h:outputLabel styleClass="desc" value="Estoque"></h:outputLabel>
									<h:outputText value="#{entradaProdutoBB.estoque.descricao}" />							
								</div>
								<div>
									<h:outputLabel styleClass="desc" value="Status"></h:outputLabel>
									<h:outputText value="Ativa" rendered="#{entradaProdutoBB.status == 'A'}" />
									<h:outputText value="Cancelada" rendered="#{entradaProdutoBB.status == 'C'}" />
								</div>
							</li>
							<li>
								<fieldset>
									<legend>Produtos:</legend>
									<ul>						
										<div class="listagem">
											<t:dataTable value="#{entradaProdutoBB.arrayProduto}"
												var="produtoEntrada" rowClasses="rowA,rowB" width="100%">
												<h:column>
													<f:facet name="header">
														<h:outputText value="Cód." />
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
														<h:outputText value="Vl. Unitário" />
													</f:facet>
													<h:outputText value="#{produtoEntrada.precoUnitario}" > 																				
														<f:convertNumber currencySymbol="R$" locale="pt-BR" pattern="R$ ###,##0.00" type="currency"/>
													</h:outputText>
												</h:column>
												<h:column>
													<f:facet name="header">
														<h:outputText value="Quant" />
													</f:facet>
													<h:outputText value="#{produtoEntrada.quantidade}" />
												</h:column>
												<h:column>
													<f:facet name="header">
														<h:outputText value="ICMS" />
													</f:facet>
													<h:outputText value="#{produtoEntrada.icms}"> 																				
														<f:convertNumber currencySymbol="R$" locale="pt-BR" pattern="R$ ###,##0.00" type="currency"/>
													</h:outputText>
												</h:column>	
												<h:column>
													<f:facet name="header">
														<h:outputText value="IPI" />
													</f:facet>
													<h:outputText value="#{produtoEntrada.ipi}" > 																				
														<f:convertNumber currencySymbol="R$" locale="pt-BR" pattern="R$ ###,##0.00" type="currency"/>
													</h:outputText>
												</h:column>	
												<h:column>
													<f:facet name="header">
														<h:outputText value="Desconto" />
													</f:facet>
													<h:outputText value="#{produtoEntrada.desconto}" > 																				
														<f:convertNumber currencySymbol="R$" locale="pt-BR" pattern="R$ ###,##0.00" type="currency"/>
													</h:outputText>
												</h:column>	
												<h:column>
													<f:facet name="header">
														<h:outputText value="Total" />
													</f:facet>
													<h:outputText value="#{produtoEntrada.total}" > 																				
														<f:convertNumber currencySymbol="R$" locale="pt-BR" pattern="R$ ###,##0.00" type="currency"/>
													</h:outputText>
												</h:column>
												<h:column>
													<f:facet name="header">
														<h:outputText value="Vencimento" />
													</f:facet>
													<h:outputText value="#{produtoEntrada.vencimento}" />
												</h:column>
											</t:dataTable>
										</div>
									</ul>
								</fieldset>
							</li>	
							
							<li class="normal">
								<div>
									<h:outputLabel styleClass="desc" value="Quant. Total"></h:outputLabel>
									<h:outputText value="#{entradaProdutoBB.quantidadeTotal}" />							
								</div>
								<div>
									<h:outputLabel styleClass="desc" value="Frete"></h:outputLabel>
									<h:outputText value="#{entradaProdutoBB.frete}" > 																				
										<f:convertNumber currencySymbol="R$" locale="pt-BR" pattern="R$ ###,##0.00" type="currency"/>
									</h:outputText>							
								</div>
								<div>
									<h:outputLabel styleClass="desc" value="ICMS"></h:outputLabel>
									<h:outputText value="#{entradaProdutoBB.icms}" > 																				
										<f:convertNumber currencySymbol="R$" locale="pt-BR" pattern="R$ ###,##0.00" type="currency"/>
									</h:outputText>							
								</div>
								<div>
									<h:outputLabel styleClass="desc" value="IPI"></h:outputLabel>
									<h:outputText value="#{entradaProdutoBB.ipi}" > 																				
										<f:convertNumber currencySymbol="R$" locale="pt-BR" pattern="R$ ###,##0.00" type="currency"/>
									</h:outputText>							
								</div>
								<div>
									<h:outputLabel styleClass="desc" value="Desc"></h:outputLabel>
									<h:outputText value="#{entradaProdutoBB.desconto}" > 																				
										<f:convertNumber currencySymbol="R$" locale="pt-BR" pattern="R$ ###,##0.00" type="currency"/>
									</h:outputText>							
								</div>
								<div>
									<h:outputLabel styleClass="desc" value="Valor da Nota"></h:outputLabel>
									<h:outputText value="#{entradaProdutoBB.valor}" > 																				
										<f:convertNumber currencySymbol="R$" locale="pt-BR" pattern="R$ ###,##0.00" type="currency"/>
									</h:outputText>							
								</div>							
							</li>
						</ul>
					</div>
					<div id="tabDiv1" style="display:none;height: 530px;">
						<div class="listagemSimples" style="overflow:auto; height:235px;">
							<t:dataTable value="#{entradaProdutoBB.arrayParcela}"
								var="parcela" rowClasses="rowA,rowB" width="100%" renderedIfEmpty="false">
								<h:column>
									<f:facet name="header">
										<h:outputText value="Item" /> 
									</f:facet>
									<h:outputText style="align: center;" value="#{parcela.pk.id}" /> 
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="Vencimento" />
									</f:facet>
									<h:outputText style="align: left;" value="#{parcela.dataVencimento}" /> 
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="Valor" />
									</f:facet>
									<h:outputText style="align: right;" value="#{parcela.valor}" > 																				
										<f:convertNumber currencySymbol="R$" locale="pt-BR" pattern="R$ ###,##0.00" type="currency"/>
									</h:outputText> 
								</h:column>	
								<h:column>
									<f:facet name="header">
										<h:outputText style="align: center;" value="Ação" />
									</f:facet>										
									<h:commandButton image="/images/excluir.png" alt="Excluir Parcela" actionListener="#{entradaProdutoBB.excluirParcela}">
										<f:param name="idExcluirParcela" value="#{parcela.pk.id}" id="idExcluirParcela"/>
									</h:commandButton>
								</h:column>													
							</t:dataTable>																
						</div>
					</div >
					<ul>
						<li class="buttons">
							<h:commandButton styleClass="btTxt" immediate="true" id="botaoVoltar"
								action="#{entradaProdutoBB.voltarConsulta}" value="Voltar"></h:commandButton>
							<h:commandButton styleClass="btTxt" id="botaoImprimir" action="#{entradaProdutoBB.imprimirRecibo}" value="Imprimir"></h:commandButton>
							<h:commandButton styleClass="btTxt" id="botaoCancelar" value="Cancelar" alt="Cancelar" actionListener="#{entradaProdutoBB.cancelar}" >
								<f:param name="idCancelarEntradaProduto" id="idCancelarEntradaProduto" value="#{entradaProdutoBB.id}" />
							</h:commandButton>							
						</li>
					</ul>
					</h:form>
					<div class="clear"></div>
				</div>
			</div>		
	  </body>				
	</f:view>
</html>
