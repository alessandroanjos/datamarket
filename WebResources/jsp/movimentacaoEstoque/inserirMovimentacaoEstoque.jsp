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
				 if(getId("frmInserirMovimentacaoEstoque:idProduto").value == ""){        		
	                formId=form;
					if (winId != null) {
					    winId.close();
					}
				    var formaux = document.forms[form];
				 
                    features="height=500,width=600,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes,dependent=yes";             
				    winId=window.open('/EnterpriseServer/jsp/popup/PopUpProdutos.faces?acaoPopUp=init&enquadramento=4','list',features);

				    // Formulário escondido
                    hform=document.forms[form];                
                 }else{
                	document.forms["frmInserirMovimentacaoEstoque"].action = "/EnterpriseServer/jsp/movimentacaoEstoque/inserirMovimentacaoEstoque.faces?acaoLocal=pesquisarProdutos&codigoProduto="+getId("frmInserirMovimentacaoEstoque:idProduto").value;
					document.forms["frmInserirMovimentacaoEstoque"].submit();
                }
            }

            // Esta função é chamada pela janela popup 

            // quando um usuário clica em um item na listagem.

            // O item selecionado é copiado para um campo de texto

            // no formulário principal.

             function setAtributo(idProduto,descricao) {
                             var form = document.forms[formId];   
                             form[formId+":idProduto"].value=idProduto; 
                             form[formId+":descricaoCompletaEstoque"].value=descricao;
                             form[formId+":quantidade"].focus();
                             winId.close();
            }
      </script>

	</head>
	<body onload="exibirMensagemErro();">
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
				<h:form id="frmInserirMovimentacaoEstoque" binding="#{movimentacaoEstoqueBB.init}">
					<ul>
						<li class="normal">
							<div>
								<%@ include file="/jsp/mensagem_erro.jsp"%> <!--  h  messages errorClass="msgSistemaErro"
									infoClass="msgSistemaSucesso" globalOnly="true"
									showDetail="true" /> -->
							</div>
						</li>
						<li class="normal">	
							<div>
								<h:outputLabel styleClass="desc" value="Loja Saída"></h:outputLabel>
								<h:selectOneMenu id="idLojaSaida" style="width: 200px;" value="#{movimentacaoEstoqueBB.idLojaSaida}" onchange="submit();" valueChangeListener="#{movimentacaoEstoqueBB.carregarEstoquesPorLojaSaida}"> 
									<f:selectItems id="lojasSelectItems" value="#{movimentacaoEstoqueBB.lojasSaida}" />   
								</h:selectOneMenu>
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Estoque Saída"></h:outputLabel>
								<h:selectOneMenu id="idEstoqueSaida" style="width:250px;" 
									value="#{movimentacaoEstoqueBB.idEstoqueSaida}" >   
										  <f:selectItems id="estoqueSelectItems" 
										  value="#{movimentacaoEstoqueBB.estoquesSaida}" />   
								</h:selectOneMenu> 
							</div>
							<br/>
							<br/>
							<div>
								<h:outputLabel styleClass="desc" value="Loja Entrada"></h:outputLabel>
								<h:selectOneMenu id="idLojaEntrada" style="width: 200px;" value="#{movimentacaoEstoqueBB.idLojaEntrada}" onchange="submit();" valueChangeListener="#{movimentacaoEstoqueBB.carregarEstoquesPorLojaEntrada}"> 
									<f:selectItems id="lojasSelectItems" value="#{movimentacaoEstoqueBB.lojasEntrada}" />   
								</h:selectOneMenu>
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Estoque Entrada"></h:outputLabel>
								<h:selectOneMenu id="idEstoqueEntrada" style="width:250px;" 
									value="#{movimentacaoEstoqueBB.idEstoqueEntrada}" >   
										  <f:selectItems id="estoqueSelectItems" 
										  value="#{movimentacaoEstoqueBB.estoquesEntrada}" />   
								</h:selectOneMenu> 
							</div>
						</li>
					</ul>
					<fieldset>	
					<legend>Produtos</legend>
					<ul>						
						<li class="normal">	
							<div>
								<h:outputLabel styleClass="desc" value="Código Produto*"></h:outputLabel>
								<h:inputText styleClass="field text" id="idProduto"
									maxlength="9" value="#{movimentacaoEstoqueBB.idProduto}" size="12">
									<f:validateLength maximum="9" />
									<f:validator validatorId="LongValidator" />
								</h:inputText>								
								<h:commandButton image="/images/pesquisa.png" alt="Pesquisar Produto" styleClass="btTxt" id="botaoConsultarProduto"
									onmousedown="showPopUp(this,'frmInserirMovimentacaoEstoque','find')"
									onclick="return false" value="Consultar Produto ">
								</h:commandButton>
							</div>	
							<div>		
								<h:outputLabel styleClass="desc" value="Descrição*"></h:outputLabel>
								<h:inputText styleClass="field text" id="descricaoCompletaEstoque" 
									maxlength="50" size="50" readonly="true" value="#{movimentacaoEstoqueBB.descricaoCompletaEstoque}">
									<f:validateLength maximum="50" /> 
								</h:inputText>
							</div>	
							<div>
								<h:outputLabel styleClass="desc" value="Qtd."></h:outputLabel>
								<h:inputText styleClass="field text" id="quantidade" maxlength="7"
									size="7" value="#{movimentacaoEstoqueBB.quantidade}" required="false"
									onkeydown="return(BackSpaceQTD(this,event));"  onkeypress="return(MascaraQTD(this,'','.',event));" >
									<f:validateLength maximum="7" />
									<f:validateDoubleRange minimum="0.00" maximum="9999.999" />
									<f:validator validatorId="BigDecimalValidator" />
								</h:inputText>
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Vencimento"></h:outputLabel>
								<h:inputText styleClass="field text" id="vencimento" maxlength="10" size="10"
									value="#{movimentacaoEstoqueBB.vencimento}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }">
									<f:convertDateTime timeZone="GMT-3"/>
								</h:inputText>
								
								<h:commandButton image="/images/adicionar.png" alt="Inserir Produto" id="botaoInserirProduto"
									action="#{movimentacaoEstoqueBB.inserirProduto}">
								</h:commandButton>
							</div>							
						</li>	
					</ul>
					<div class="listagem">
						<t:dataTable value="#{movimentacaoEstoqueBB.arrayProduto}"
							var="produtoMovimentacao" rowClasses="rowA,rowB" width="95%" renderedIfEmpty="false">
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
									<h:commandButton type="button" image="/images/excluir.png"/>
									<f:param name="idExcluir" value="#{produtoMovimentacao.produto.id}" />
								</h:commandLink>
							</h:column>
						</t:dataTable>
					</div>
					</fieldset>
					<ul>
						<li class="buttons">
							<h:commandButton styleClass="btTxt" action="#{movimentacaoEstoqueBB.resetBB}" id="botaoLimpar" value="Limpar"></h:commandButton>
							<h:commandButton styleClass="btTxt" id="botaoInserir" action="#{movimentacaoEstoqueBB.inserir}" value="Inserir"></h:commandButton>
							<h:commandButton styleClass="btTxt" id="botaoImprimir" action="#{movimentacaoEstoqueBB.imprimirRecibo}" value="Imprimir"></h:commandButton>
						</li>
					</ul>
				</h:form>
				</div>
				<div class="clear"></div>
			</div>
	 </body>			
	</f:view>
</html>
