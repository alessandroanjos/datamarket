<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="https://ajax4jsf.dev.java.net/ajax" prefix="a4j"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<f:view>
		<f:loadBundle basename="resources.mensagens" var="msgs" />
		<head>

			<title><h:outputText value="#{msgs.tituloPaginas}"></h:outputText>
			</title>

			<meta http-equiv="pragma" content="no-cache" />
			<link rel="icon" xhref="favicon.ico" type="image/x-icon" />
			<link rel="shortcut icon" xhref="favicon.ico" type="image/x-icon" />
			<meta http-equiv="cache-control" content="no-cache" />
			<meta http-equiv="expires" content="0" />
			<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
			<meta http-equiv="description" content="This is my page" />

			<script type="text/javascript" src="/EnterpriseServer/js/jquery.js"></script>
			<script type="text/javascript" src="/EnterpriseServer/js/global.js"></script>
			<script type="text/javascript" src="/EnterpriseServer/js/funcoes.js"></script>
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
		      	strAbaCorrente = getId("frmInserirEntradaProdutos:abaCorrente").value;
				if(strAbaCorrente != ""){							
					selecionaMenuTab(strAbaCorrente);
				}else{
					selecionaMenuTab("tabMenuDiv0");
				}
		
		      }

            var formId; // refer�ncia ao formul�rio principal
            var winId;  // refer�ncia � janela popup
            // Esta fun��o faz a chamada da janela popup.
            //
            function showPopUp(action, form, target) {
            	if(getId("frmInserirEntradaProdutos:idProduto").value == ""){        		
                   formId=form;
	        	   if (winId != null) {
	        		   winId.close();
	        	   }
	               features="height=500,width=600,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes,dependent=yes";             
	        	   winId=window.open('/EnterpriseServer/jsp/popup/PopUpProdutos.faces?acao=init','list',features);
				   // Formul�rio escondido
	               hform=document.forms[form];                
				}else{
                	document.forms["frmInserirEntradaProdutos"].action = "/EnterpriseServer/jsp/entradaProduto/inserirEntradaProduto.faces?acaoLocal=pesquisarProdutos&codigoProduto="+getId("frmInserirEntradaProdutos:idProduto").value;
					document.forms["frmInserirEntradaProdutos"].submit();
                }
            }

            // Esta fun��o � chamada pela janela popup 
            // quando um usu�rio clica em um item na listagem.
            // O item selecionado � copiado para um campo de texto
            // no formul�rio principal.
            //
             function setAtributo(idProduto,descricao) {
                             var form = document.forms[formId];   
                             form[formId+":idProduto"].value=idProduto; 
                             form[formId+":descricao"].value=descricao;
                             form[formId+":quantidade"].focus();  
                             winId.close();
            }

			 function reCalculaTotalEntradaProdutosDesconto(){
            	var valorTotalCupom = parseFloat(getId("frmInserirEntradaProdutos:valor").value);
            	var descontoCupom = parseFloat(getId("frmInserirEntradaProdutos:desconto").value);

			    valorTotalCupom = valorTotalCupom - descontoCupom;

				getId("frmInserirEntradaProdutos:valor").value = valorTotalCupom.toFixed(2);
            }
            
			 function reCalculaTotalEntradaProdutosFrete(){
            	var valorTotalCupom = parseFloat(getId("frmInserirEntradaProdutos:valor").value);
            	var valorFrete = parseFloat(getId("frmInserirEntradaProdutos:frete").value);

			    valorTotalCupom = valorTotalCupom + valorFrete;

				getId("frmInserirEntradaProdutos:valor").value = valorTotalCupom.toFixed(2);
            }
      </script>
		<body>
			<div id="outer">
				<div id="topoGeral">
					<div id="tituloPaginaGeral">
						<strong> <h:outputText
								value="#{msgs.inserirEntradaProdutos}"></h:outputText> </strong>
					</div>
				</div>

				<div id="content">
					<div class="tabMenu">
						<ul>
							<li id="tabMenuDiv0" class="current"
								onclick="selecionaMenuTab(this.id)">
								<span><a href="#">Entrada de Produto</a> </span>
							</li>
							<li id="tabMenuDiv1" onclick="selecionaMenuTab(this.id)">
								<span><a href="#">Pagamentos</a> </span>
							</li>
						</ul>
						<div class="clear"></div>
					</div>
					<div id="primarioContentContainerInternas">
						<h:form id="frmInserirEntradaProdutos"
							binding="#{entradaProdutoBB.init}">
							<h:inputHidden id="abaCorrente"
								value="#{entradaProdutoBB.abaCorrente}"></h:inputHidden>
							<div id="tabDiv0">
								<ul>
									<li class="normal">
										<div>
											<h:messages errorClass="msgSistemaErro"
												infoClass="msgSistemaSucesso" globalOnly="true" id="erroEntrada"
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
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Data Emiss�o"></h:outputLabel>
											<h:inputText readonly="false" maxlength="10" size="10"
												styleClass="field text data"
												value="#{entradaProdutoBB.dataEmissaoNota}"
												onkeypress="return MascaraData(this,event);"
												onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }"
												id="dataEmissaoNota">
												<f:convertDateTime timeZone="GMT-3" />
											</h:inputText>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Data Entrada"></h:outputLabel>
											<h:inputText readonly="false" styleClass="field text data"
												maxlength="10" size="10"
												value="#{entradaProdutoBB.dataEntrada}"
												onkeypress="return MascaraData(this,event);"
												onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }"
												id="dataEntrada">
												<f:convertDateTime timeZone="GMT-3" />
											</h:inputText>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Fornecedor"></h:outputLabel>
											<h:selectOneMenu id="idFornecedor" styleClass="field text"
												value="#{entradaProdutoBB.idFornecedor}"
												style="width: 250px;">
												<f:selectItems id="fornecedorSelectItems"
													value="#{entradaProdutoBB.fornecedores}" />
											</h:selectOneMenu>
										</div>
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="C�digo Produto*"></h:outputLabel>
											<h:inputText styleClass="field text ativo" id="idProduto"
												onfocus="this.select();" onclick="this.select();"
												onkeypress="return SoNumero(event);" maxlength="9"
												value="#{entradaProdutoBB.idProduto}" size="9"
												required="false">
												<f:validateLength maximum="9" />
												<f:validator validatorId="LongValidator" />
											</h:inputText>
											<h:commandButton image="/images/pesquisa.png"
												alt="Pesquisar Produto" styleClass="btTxt"
												id="botaoConsultarProduto"
												onmousedown="showPopUp(this,'frmInserirEntradaProdutos','find')"
												onclick="return false" value="Consultar Produto ">
											</h:commandButton>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Descri��o*"></h:outputLabel>
											<h:inputText styleClass="field text ativo" id="descricao"
												maxlength="50" size="50"
												value="#{entradaProdutoBB.descricao}">
												<f:validateLength maximum="50" />
											</h:inputText>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Estoque"></h:outputLabel>
											<h:selectOneMenu id="idEstoque" styleClass="field text"
												value="#{entradaProdutoBB.idEstoque}">
												<f:selectItems id="estoqueSelectItems"
													value="#{entradaProdutoBB.estoques}" />
											</h:selectOneMenu>
										</div>
									</li>
									<li class="normal">
										<h:inputHidden id="valorSubTotalNota"
											value="#{entradaProdutoBB.valorSubTotalNota}"></h:inputHidden>
										<div>
											<h:outputLabel styleClass="desc" value="Qtd."></h:outputLabel>
											<h:inputText styleClass="field monetario" id="quantidade"
												maxlength="9" size="7"
												value="#{entradaProdutoBB.quantidade}" dir="rtl"
												onkeydown="return(BackSpaceQTD(this,event));"
												onkeypress="return(MascaraQTD(this,'','.',event));"
												required="false">
												<f:validateLength maximum="9" />
												<f:validateDoubleRange minimum="0.000" maximum="9999.999" />
												<f:validator validatorId="BigDecimalValidator" />
											</h:inputText>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Valor Unit�rio"></h:outputLabel>
											<h:inputText styleClass="field monetario" id="precoUnitario"
												maxlength="10" size="11"
												value="#{entradaProdutoBB.precoUnitario}" dir="rtl"
												onkeydown="return(BackSpace(this,event));"
												onkeypress="return(MascaraMoeda(this,'','.',event));"
												required="false">
												<f:validateLength maximum="10" />
												<f:validateDoubleRange minimum="0.00" maximum="9999999.99" />
												<f:validator validatorId="BigDecimalValidator" />
											</h:inputText>


										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Desconto"></h:outputLabel>
											<h:inputText styleClass="field monetario"
												id="descontoProduto" maxlength="10" size="11"
												value="#{entradaProdutoBB.descontoProduto}" dir="rtl"
												onkeydown="return(BackSpace(this,event));"
												onkeypress="return(MascaraMoeda(this,'','.',event));"
												required="false">
												<f:validateLength maximum="10" />
												<f:validateDoubleRange minimum="0.00" maximum="9999999.99" />
												<f:validator validatorId="BigDecimalValidator" />
											</h:inputText>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="ICMS"></h:outputLabel>
											<h:inputText styleClass="field monetario" id="icmsProduto"
												maxlength="10" size="11"
												value="#{entradaProdutoBB.icmsProduto}" dir="rtl"
												onkeydown="return(BackSpace(this,event));"
												onkeypress="return(MascaraMoeda(this,'','.',event));"
												required="false">
												<f:validateLength maximum="10" />
												<f:validateDoubleRange minimum="0.00" maximum="9999999.99" />
												<f:validator validatorId="BigDecimalValidator" />
											</h:inputText>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="IPI"></h:outputLabel>
											<h:inputText styleClass="field monetario" id="ipiProduto"
												maxlength="10" size="11"
												value="#{entradaProdutoBB.ipiProduto}" dir="rtl"
												onkeydown="return(BackSpace(this,event));"
												onkeypress="return(MascaraMoeda(this,'','.',event));"
												required="false">
												<f:validateLength maximum="10" />
												<f:validateDoubleRange minimum="0.00" maximum="9999999.99" />
												<f:validator validatorId="BigDecimalValidator" />
											</h:inputText>
											<h:commandButton image="/images/adicionar.png"
												alt="Inserir Produto" id="botaoInserirProduto"
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
														<h:outputText value="C�d." />
													</f:facet>
													<h:outputText value="#{produtoEntrada.pk.produto.id}" />
												</h:column>
												<h:column>
													<f:facet name="header">
														<h:outputText value="Descri��o" />
													</f:facet>
													<h:outputText
														value="#{produtoEntrada.pk.produto.descricaoCompleta}" />
												</h:column>
												<h:column>
													<f:facet name="header">
														<h:outputText value="Estoque" />
													</f:facet>
													<h:outputText value="#{produtoEntrada.estoque.descricao}" />
												</h:column>
												<h:column>
													<f:facet name="header">
														<h:outputText value="Vl. Unit�rio" />
													</f:facet>
													<h:outputText value="#{produtoEntrada.precoUnitario}" />
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
														<h:outputText value="Desconto" />
													</f:facet>
													<h:outputText value="#{produtoEntrada.desconto}" />
												</h:column>
												<h:column>
													<f:facet name="header">
														<h:outputText value="Total" />
													</f:facet>
													<h:outputText value="#{produtoEntrada.total}" />
												</h:column>
												<h:column>
													<f:facet name="header">
														<h:outputText value="" />
													</f:facet>
													<h:commandLink value=""
														action="#{entradaProdutoBB.excluirProdutoEntrada}">

														<h:commandButton style="" image="/images/excluir.png"
															alt="Excluir Produto" title="" />
														<f:param name="idExcluir"
															value="#{produtoEntrada.pk.produto.id}" />
													</h:commandLink>
												</h:column>
											</t:dataTable>

										</div>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Quant. Total"></h:outputLabel>
											<h:inputText styleClass="field monetario"
												id="quantidadeTotal" maxlength="10" disabled="true"
												size="11" value="#{entradaProdutoBB.quantidadeTotal}"
												onkeydown="return(BackSpace(this,event));"
												onkeypress="return(MascaraMoeda(this,'','.',event));"
												required="false">
												<f:validator validatorId="BigDecimalValidator" />
											</h:inputText>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Frete"></h:outputLabel>
											<h:inputText styleClass="field monetario" id="frete"
												maxlength="10" size="11" value="#{entradaProdutoBB.frete}"
												onkeydown="return(BackSpace(this,event));"
												onkeypress="return(MascaraMoeda(this,'','.',event));"
												required="false"
												onblur="javascript:reCalculaTotalEntradaProdutosFrete();">
												<f:validator validatorId="BigDecimalValidator" />
											</h:inputText>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="ICMS"></h:outputLabel>
											<h:inputText styleClass="field monetario" id="icms"
												maxlength="10" disabled="true" size="11"
												value="#{entradaProdutoBB.icms}" dir="rtl"
												onkeydown="return(BackSpace(this,event));"
												onkeypress="return(MascaraMoeda(this,'','.',event));"
												required="false">
												<f:validateLength maximum="10" />
												<f:validateDoubleRange minimum="0.00" maximum="9999999.99" />
												<f:validator validatorId="BigDecimalValidator" />
											</h:inputText>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="IPI"></h:outputLabel>
											<h:inputText styleClass="field monetario" id="ipi"
												maxlength="10" disabled="true" size="11"
												value="#{entradaProdutoBB.ipi}" dir="rtl"
												onkeydown="return(BackSpace(this,event));"
												onkeypress="return(MascaraMoeda(this,'','.',event));"
												required="false">
												<f:validateLength maximum="10" />
												<f:validateDoubleRange minimum="0.00" maximum="9999999.99" />
												<f:validator validatorId="BigDecimalValidator" />
											</h:inputText>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Desconto"></h:outputLabel>
											<h:inputText styleClass="field monetario" id="desconto"
												maxlength="10" size="11"
												value="#{entradaProdutoBB.desconto}" dir="rtl"
												onkeydown="return(BackSpace(this,event));"
												onkeypress="return(MascaraMoeda(this,'','.',event));"
												required="false"
												onblur="javascript:reCalculaTotalEntradaProdutosDesconto();">
												<f:validateLength maximum="10" />
												<f:validateDoubleRange minimum="0.00" maximum="9999999.99" />
												<f:validator validatorId="BigDecimalValidator" />
											</h:inputText>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Valor da Nota"></h:outputLabel>
											<h:inputText styleClass="field monetario" id="valor"
												maxlength="10" disabled="true" size="11"
												value="#{entradaProdutoBB.valor}" dir="rtl"
												onkeydown="return(BackSpace(this,event));"
												onkeypress="return(MascaraMoeda(this,'','.',event));"
												required="false">
												<f:validateLength maximum="10" />
												<f:validateDoubleRange minimum="0.00" maximum="9999999.99" />
												<f:validator validatorId="BigDecimalValidator" />
											</h:inputText>
										</div>
									</li>
								</ul>
							</div>
							<div id="tabDiv1" style="display:none;">
								<ul>
									<li class="normal">
										<div>
											<h:messages errorClass="msgSistemaErro"
												infoClass="msgSistemaSucesso" globalOnly="true" id="erroLancamento"
												showDetail="true" />
										</div>
									</li>
									<li class="normal">

										<div>
											<h:outputLabel styleClass="desc" value="Descri��o*"></h:outputLabel>
											<h:inputText styleClass="field text" id="descricaoLancamento"
												maxlength="40" size="40" required="false"
												value="#{entradaProdutoBB.descricaoLancamento}">
												<f:validateLength maximum="40" />
											</h:inputText>
											<h:message for="descricaoLancamento" styleClass="msgErro" />
										</div>

										<div>
											<h:outputLabel styleClass="desc" value="Loja*"></h:outputLabel>
											<h:selectOneMenu id="idLojaLancamento" styleClass="field select"
												value="#{entradaProdutoBB.idLojaLancamento}" required="true">
												<f:selectItems id="lojaSelectItems"
													value="#{entradaProdutoBB.lojas}" />
											</h:selectOneMenu>
											<h:message for="idLoja" styleClass="msgErro" />
										</div>

										<div>
											<h:outputLabel styleClass="desc" value="Forma Pagto*"></h:outputLabel>
											<h:selectOneMenu id="idForma" styleClass="field select"
												value="#{entradaProdutoBB.idForma}" required="false">
												<f:selectItems id="formaSelectItems"
													value="#{entradaProdutoBB.formas}" />
											</h:selectOneMenu>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Vencimento"></h:outputLabel>
											<h:inputText readonly="false" maxlength="10" size="10"
												styleClass="field text data"
												value="#{entradaProdutoBB.dataVencimento}"
												onkeypress="return MascaraData(this,event);"
												onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }"
												id="dataVencimento">
												<f:convertDateTime timeZone="GMT-3" />
											</h:inputText>
											<h:message for="dataLancamento" styleClass="msgErro" />
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Valor*"></h:outputLabel>
											<h:inputText styleClass="field monetario"
												id="valorLancamento" maxlength="10" size="11"
												value="#{entradaProdutoBB.valorLancamento}" dir="rtl"
												onkeydown="return(BackSpace(this,event));"
												onkeypress="return(MascaraMoeda(this,'','.',event));"
												required="false">
												<f:validateLength maximum="10" />
												<f:validateDoubleRange minimum="0.00" maximum="9999999.99" />
												<f:validator validatorId="BigDecimalValidator" />
											</h:inputText>
											<h:commandButton image="/images/adicionar.png"
												alt="Inserir Lan�amento" id="botaoInserirLancamento"
												action="#{entradaProdutoBB.inserirLancamento}">
											</h:commandButton>
										</div>

									</li>
									<li>
										<div class="listagem">
											<t:dataTable value="#{entradaProdutoBB.lancamentos}"
												var="lancamento" rowClasses="rowA,rowB" width="95%">
												<h:column>
													<f:facet name="header">
														<h:outputText value="Descri��o" />
													</f:facet>
													<h:outputText value="#{lancamento.descricao}" />
												</h:column>
												<h:column>
													<f:facet name="header">
														<h:outputText value="Loja" />
													</f:facet>
													<h:outputText value="#{lancamento.loja.nome}" />
												</h:column>
												<h:column>
													<f:facet name="header">
														<h:outputText value="Forma Rec." />
													</f:facet>
													<h:outputText value="#{lancamento.forma.descricao}" />
												</h:column>
												<h:column>
													<f:facet name="header">
														<h:outputText value="Vencimento" />
													</f:facet>
													<h:outputText value="#{lancamento.dataVencimento}" />
												</h:column>
												<h:column>
													<f:facet name="header">
														<h:outputText value="Valor" />
													</f:facet>
													<h:outputText value="#{lancamento.valor}" />
												</h:column>
												<h:column>
													<f:facet name="header">
														<h:outputText value="" />
													</f:facet>
													<h:commandLink value=""
														action="#{entradaProdutoBB.excluirLancamento}">

														<h:commandButton style="" image="/images/excluir.png"
															alt="Excluir Produto" title="" />
														<f:param name="idExcluirLancamento"
															value="#{lancamento.id}" />
													</h:commandLink>
												</h:column>
											</t:dataTable>
										</div>
									</li>
									<li class="normal">

										<div style="text-align:right">
											<h:outputLabel styleClass="desc" value="Valor da Nota"></h:outputLabel>
											<h:inputText styleClass="field monetario" id="valornota"
												maxlength="10" disabled="true" size="11"
												value="#{entradaProdutoBB.valor}" dir="rtl"
												onkeydown="return(BackSpace(this,event));"
												onkeypress="return(MascaraMoeda(this,'','.',event));"
												required="false">
												<f:validateLength maximum="10" />
												<f:validateDoubleRange minimum="0.00" maximum="9999999.99" />
												<f:validator validatorId="BigDecimalValidator" />
											</h:inputText>
										</div>
										<div style="text-align:right">
											<h:outputLabel styleClass="desc" value="Total de Lancamento"></h:outputLabel>
											<h:inputText styleClass="field monetario" id="valorTotalLancamento"
												maxlength="10" disabled="true" size="11"
												value="#{entradaProdutoBB.valorTotalLancamento}" dir="rtl"
												onkeydown="return(BackSpace(this,event));"
												onkeypress="return(MascaraMoeda(this,'','.',event));"
												required="false">
												<f:validateLength maximum="10" />
												<f:validateDoubleRange minimum="0.00" maximum="9999999.99" />
												<f:validator validatorId="BigDecimalValidator" />
											</h:inputText>
										</div>
									</li>
								</ul>
							</div>
							<ul>
								<li class="buttons">
									<h:commandButton styleClass="btTxt"
										action="#{entradaProdutoBB.resetBB}" id="botaoLimpar"
										value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoInserir"
										action="#{entradaProdutoBB.inserir}" value="Inserir"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoImprimir"
										action="#{entradaProdutoBB.imprimirRecibo}" value="Imprimir"></h:commandButton>
								</li>

							</ul>

						</h:form>

					</div>
					<div class="clear"></div>
				</div>
		</body>
	</f:view>
</html>
