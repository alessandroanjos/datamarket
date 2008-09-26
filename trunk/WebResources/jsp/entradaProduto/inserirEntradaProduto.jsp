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
		<script type="text/javascript" src="/EnterpriseServer/js/util.js"></script>
		<t:stylesheet path="/css/default.css"></t:stylesheet>
		<t:stylesheet path="/css/form.css"></t:stylesheet>		

	</head>

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
            	if(getId("frmInserirEntradaProdutos:idProduto").value == ""){        		
                   formId=form;
	        	   if (winId != null) {
	        		   winId.close();
	        	   }
	               features="height=500,width=600,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes,dependent=yes";             
	        	   winId=window.open('/EnterpriseServer/jsp/popup/PopUpProdutos.faces','list',features);
				   // Formulário escondido
	               hform=document.forms[form];                
				}else{
                	document.forms["frmInserirEntradaProdutos"].action = "/EnterpriseServer/jsp/entradaProduto/inserirEntradaProduto.faces?acaoLocal=pesquisarProdutos&codigoProduto="+getId("frmInserirEntradaProdutos:idProduto").value;
					document.forms["frmInserirEntradaProdutos"].submit();
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
                             form[formId+":quantidade"].focus();  
                             winId.close();
            }
      </script>


	<body>
	<div id="outer">
		<div id="topoGeral">
			<div id="tituloPaginaGeral">
				<strong>
					<h:outputText value="#{msgs.inserirEntradaProdutos}"></h:outputText>
				</strong>
			</div>				
		</div>	
		<h:form id="frmInserirEntradaProdutos" binding="#{entradaProdutoBB.init}">
			<div id="content">
				<div id="primarioContentContainerInternas">
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
									styleClass="field text data" forceId="dataEmissaoNota"
									value="#{entradaProdutoBB.dataEmissaoNota}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }"
									id="dataEmissaoNota" />
								<h:message for="dataEmissaoNota" styleClass="msgErro" />
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Data Entrada"></h:outputLabel>
								<t:inputText readonly="false" styleClass="field text data"
									maxlength="10" size="10" forceId="dataEntrada"
									value="#{entradaProdutoBB.dataEntrada}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }"
									id="dataEntrada" />
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
								<h:outputText value="#{entradaProdutoBB.valor}" /> 
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Frete"></h:outputLabel>
								<h:inputText styleClass="field monetario" id="frete" maxlength="7"
									size="7" value="#{entradaProdutoBB.frete}" dir="rtl"
									onfocus="this.select();" onclick="this.select();"
									onkeypress="return SoNumero(event);"
									onkeydown="Formata('frmInserirEntradaProdutos:frete',7,2,event);">
									<f:validateLength maximum="7"/>
									<f:validateDoubleRange minimum="0.00" maximum="9999.99" />
									<f:validator validatorId="BigDecimalValidator" />
								</h:inputText>
								<h:message for="frete" styleClass="msgErro" />
							</div>

							<div>
								<h:outputLabel styleClass="desc" value="Desc"></h:outputLabel>
								<h:inputText styleClass="field monetario" id="desconto" maxlength="7"
									size="7" value="#{entradaProdutoBB.desconto}" dir="rtl"
									onfocus="this.select();" onclick="this.select();"
									onkeypress="return SoNumero(event);" onkeydown="Formata('frmInserirEntradaProdutos:desconto',7,2,event);"
									required="false">
									<f:validateLength maximum="7" />
									<f:validateDoubleRange minimum="0.00" maximum="9999.99" />
									<f:validator validatorId="BigDecimalValidator" />
								</h:inputText>
								<h:message for="desconto" styleClass="msgErro" />
							</div>
							
							<div>
								<h:outputLabel styleClass="desc" value="ICMS"></h:outputLabel>
								<h:outputText value="#{entradaProdutoBB.icms}" /> 
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="IPI"></h:outputLabel>
								<h:outputText value="#{entradaProdutoBB.ipi}" /> 
							</div>
	
						</li>

						<li class="normal">
							<div>
								<h:outputLabel styleClass="desc" value="Código Produto*"></h:outputLabel>
								<h:inputText styleClass="field text ativo" id="idProduto" onfocus="this.select();" onclick="this.select();" onkeypress="return SoNumero(event);"
									maxlength="9" value="#{entradaProdutoBB.idProduto}" size="9" required="false">
									<f:validateLength maximum="9" />
									<f:validator validatorId="LongValidator" />
								</h:inputText>
								<h:message for="idProduto" styleClass="errors" />

									<h:commandButton  image="/images/pesquisa.png" alt="Pesquisar Produto" styleClass="btTxt" id="botaoConsultarProduto"
									onmousedown="showPopUp(this,'frmInserirEntradaProdutos','find')"
									onclick="return false" value="Consultar Produto "></h:commandButton>
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Descrição*"></h:outputLabel>
								<h:inputText styleClass="field text ativo" id="descricao" 
									maxlength="50" size="50" value="#{entradaProdutoBB.descricao}">
									<f:validateLength maximum="50" />
								</h:inputText>
								<h:message for="descricao" styleClass="errors" />
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
								<h:inputText styleClass="field monetario" id="quantidade" maxlength="9"
									size="7" value="#{entradaProdutoBB.quantidade}" dir="rtl"
									onfocus="this.select();" onclick="this.select();"
									onkeypress="return SoNumero(event);" onkeydown="Formata('frmInserirEntradaProdutos:quantidade',9,3,event);"
									required="false">
									<f:validateLength maximum="9" />
									<f:validateDoubleRange minimum="0.000" maximum="9999.999" />
									<f:validator validatorId="BigDecimalValidator" />
								</h:inputText>
								<h:message for="quantidade" styleClass="msgErro" />
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Unitário"></h:outputLabel>
								<h:inputText styleClass="field monetario" id="precoUnitario" maxlength="7"
									size="7" value="#{entradaProdutoBB.precoUnitario}" dir="rtl"
									onfocus="this.select();" onclick="this.select();"
									onkeypress="return SoNumero(event);" onkeydown="Formata('frmInserirEntradaProdutos:precoUnitario',7,2,event);"
									required="false">
									<f:validateLength maximum="7" />
									<f:validateDoubleRange minimum="0.00" maximum="9999.99" />
									<f:validator validatorId="BigDecimalValidator" />
								</h:inputText>
								<h:message for="precoUnitario" styleClass="msgErro" />
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Desconto"></h:outputLabel>
								<h:inputText styleClass="field monetario" id="descontoProduto" maxlength="7"
									size="7" value="#{entradaProdutoBB.descontoProduto}" dir="rtl"
									onfocus="this.select();" onclick="this.select();"
									onkeypress="return SoNumero(event);" onkeydown="Formata('frmInserirEntradaProdutos:descontoProduto',7,2,event);"
									required="false">
									<f:validateLength maximum="7" />
									<f:validateDoubleRange minimum="0.00" maximum="9999.99" />
									<f:validator validatorId="BigDecimalValidator" />
								</h:inputText>
								<h:message for="descontoProduto" styleClass="msgErro" />
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="ICMS"></h:outputLabel>
								<h:inputText styleClass="field monetario" id="icmsProduto" maxlength="7"
									size="7" value="#{entradaProdutoBB.icmsProduto}" dir="rtl"
									onfocus="this.select();" onclick="this.select();"
									onkeypress="return SoNumero(event);" onkeydown="Formata('frmInserirEntradaProdutos:icmsProduto',7,2,event);"
									required="false">
									<f:validateLength maximum="7" />
									<f:validateDoubleRange minimum="0.00" maximum="9999.99" />
									<f:validator validatorId="BigDecimalValidator" />
								</h:inputText>
								<h:message for="icmsProduto" styleClass="msgErro" />
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="IPI"></h:outputLabel>
								<h:inputText styleClass="field monetario" id="ipiProduto" maxlength="7"
									size="7" value="#{entradaProdutoBB.ipiProduto}" dir="rtl"
									onfocus="this.select();" onclick="this.select();"
									onkeypress="return SoNumero(event);" onkeydown="Formata('frmInserirEntradaProdutos:ipiProduto',7,2,event);"
									required="false">
									<f:validateLength maximum="7" />
									<f:validateDoubleRange minimum="0.00" maximum="9999.99" />
									<f:validator validatorId="BigDecimalValidator" />
								</h:inputText>
								<h:message for="ipiProduto" styleClass="msgErro" />
								<h:commandButton image="/images/adicionar.png" alt="Inserir Produto" id="botaoInserirProduto"
									action="#{entradaProdutoBB.inserirProdutoEntrada}">
								</h:commandButton>
							</div>	
						</li>	
						<li>
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
									<h:outputText value="#{produtoEntrada.estoque.descricao}" />
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="Quant" />
									</f:facet>
									<h:outputText value="#{produtoEntrada.quantidade}" />
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="P.Unit." />
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
								<h:column >
									<f:facet name="header">
										<h:outputText value="" />
									</f:facet>
									<h:commandLink value="" 
										action="#{entradaProdutoBB.excluirProdutoEntrada}"   >
	
 									   <h:commandButton style="" image="/images/excluir.png" alt="Excluir Produto" title="" />
										<f:param name="idExcluir" value="#{produtoEntrada.pk.produto.id}" />
									</h:commandLink>
								</h:column>
							</t:dataTable>
						
						</div>
						</li>
						<li class="buttons">
							<h:commandButton styleClass="btTxt" immediate="true"
								id="botaoLimpar" type="reset" value="Limpar"></h:commandButton>
							<h:commandButton styleClass="btTxt" id="botaoInserir"
								action="#{entradaProdutoBB.inserir}" value="Inserir"></h:commandButton>
						</li>

					</ul>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
		</h:form>
	  </body>				
	</f:view>
</html>
