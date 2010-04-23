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
			
			<script type="text/javascript">

			      window.onload = function(){ inicializar() };
			
			      function inicializar() {	
			      		//alert("1->"+getId("frmInserirProduto:abaCorrente").value);		
				      	
				      	$("input.field, select.field").each(function(i){
				      		$(this).focus(function() {this.style.backgroundColor = "#eff6ff"});
				      		$(this).blur(function() {this.style.backgroundColor = ""});
				      	});
				        
						strAbaCorrente = getId("frmInserirProduto:abaCorrente").value;
						//alert(strAbaCorrente);
						if(strAbaCorrente != ""){							
							selecionaMenuTab(strAbaCorrente);
						}else{
							selecionaMenuTab("tabMenuDiv0");
						}
						//alert(getId("frmInserirProduto:enquadramento"));
						//alert(getId("frmInserirProduto:enquadramentoSelecionado").value);
						trataTipoEnquadramento(getId("frmInserirProduto:enquadramentoSelecionado"));
			      }
			
				  var formId; // referência ao formulário principal
                  var winId;  // referência à janela popup
                  // Esta função faz a chamada da janela popup.
	              function showPopUp(action, form, target) {
	 	                getId("frmInserirProduto:quantidadeProdutoComposicao").value = parseFloat("0").toFixed(3);	
                		if(getId("frmInserirProduto:idProdutoComposicao").value == ""){        		
		        		    getId("frmInserirProduto:descricaoProdutoComposicao").value = "";
			                formId=form;
					        if (winId != null) {
						       winId.close();
						    }
			                features="height=500,width=600,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes,dependent=yes";             
			     			winId=window.open('/EnterpriseServer/jsp/popup/PopUpProdutos.faces?acao=init&enquadramento=1','list',features);
				            // Formulário escondido
			                hform=document.forms[form];                
		                }else{
		                	getId("frmInserirProduto:abaCorrente").value = strAbaCorrente;
							document.forms["frmInserirProduto"].action = "/EnterpriseServer/jsp/produto/inserirProduto.faces?acaoLocal=pesquisarProdutos&codigoProduto="+getId("frmInserirProduto:idProdutoComposicao").value;
							document.forms["frmInserirProduto"].submit();                	
		                }
		          }
		
		          // Esta função é chamada pela janela popup 
		          // quando um usuário clica em um item na listagem.
		          // O item selecionado é copiado para um campo de texto
		          // no formulário principal.
		          //
		          function setAtributo(idProduto,descricao,precoVenda) {
		                var form = document.forms[formId];   
		                form[formId+":idProdutoComposicao"].value=idProduto; 
		                form[formId+":descricaoProdutoComposicao"].value=descricao;  
                        winId.close();
                        
                        getId("frmInserirProduto:quantidadeProdutoComposicao").focus();
                        getId("frmInserirProduto:quantidadeProdutoComposicao").select();
                        
                        selecionaMenuTab("tabMenuDiv2");
                  }
                  
                  //funcao responsavel por controlar a aba de composicao. esta so devera ser habilitada quando o enquadramento selecionado
				  // for para produto fabricado (tipo P)
				  function trataTipoEnquadramento(str){
				      var opcao = str.value;
 					 // alert(opcao);
 					  //alert((opcao.toUpperCase() == "P")); 					  
 					  if (opcao.toUpperCase() == "P") {
				          habilita("tabMenuDiv2");  			              
				      }else{
						  desabilita("tabMenuDiv2");
						  selecionaMenuTab("tabMenuDiv0");
					  }
					  getId("frmInserirProduto:enquadramentoSelecionado").value = opcao;
					 // getId("frmInserirProduto:idEnquadramento").value = opcao;
					  //alert(getId("frmInserirProduto:enquadramentoSelecionado").value);
					  //alert(getId("frmInserirProduto:idEnquadramento"));
				  }
      		 </script>
		</head>
		<body onload="exibirMensagemErro();inicializar();">
			<div id="outer">
				<div id="topoGeral">
					<div id="tituloPaginaGeral">
						<strong> <h:outputText value="#{msgs.inserirProduto}"></h:outputText>
						</strong>
					</div>
				</div>
				<div id="content">
					<div id="primarioContentContainerInternas">
						<h:form id="frmInserirProduto2">
							<ul>
								<li>
									<div style="vertical-align: middle">
									<h:selectOneRadio  styleClass="field radio" id="enquadramento"  
												value="#{produtoBB.idEnquadramento}" layout="lineDirection"  rendered="true"  onclick="javascript:trataTipoEnquadramento(this);">
											    <f:selectItems id="situacao" value="#{produtoBB.listaTiposEnquadramento}"/>
											</h:selectOneRadio>
									</div>
								</li>
							</ul>
						</h:form>
					</div>
					
					<div class="tabMenu">
						<ul>
							<li id="tabMenuDiv0" class="current" onclick="selecionaMenuTab(this.id);trataTipoEnquadramento(getId('frmInserirProduto:enquadramentoSelecionado'));">
								<span><a href="#">Produto</a> </span>
							</li>
							<li id="tabMenuDiv1" onclick="selecionaMenuTab(this.id);trataTipoEnquadramento(getId('frmInserirProduto:enquadramentoSelecionado'));">
								<span><a href="#">Lojas</a> </span>
							</li>
							<li id="tabMenuDiv2" onclick="selecionaMenuTab(this.id);trataTipoEnquadramento(getId('frmInserirProduto:enquadramentoSelecionado'));">
								<span><a href="#">Composição</a> </span>
							</li>
						</ul>
						<div class="clear"></div>
					</div>
					<div id="primarioContentContainerInternas">
					<h:form id="frmInserirProduto" binding="#{produtoBB.init}"
							onsubmit="javascript:getId('frmInserirProduto:abaCorrente').value = strAbaCorrente;">
						<ul>
							<li class="normal">
								<div>
									<%@ include file="/jsp/mensagem_erro.jsp"%> <!--  h  messages errorClass="msgSistemaErro" infoClass="msgSistemaSucesso" globalOnly="true" showDetail="true" /> -->
								</div>
							</li>
						</ul>
							<h:inputHidden id="enquadramentoSelecionado" value="#{produtoBB.enquadramentoSelecionado}"></h:inputHidden>
							<h:inputHidden id="abaCorrente" value="#{produtoBB.abaCorrente}"></h:inputHidden>
							<div id="tabDiv0" style="height: 350px;">
								<ul>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Descrição Completa*"></h:outputLabel>
											<h:inputText styleClass="field text" id="descricaoCompleta"
												maxlength="50" size="50"
												value="#{produtoBB.descricaoCompleta}" >
												<f:validateLength maximum="50" />
											</h:inputText>
										</div>
										
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Descrição Compacta*"></h:outputLabel>
											<h:inputText styleClass="field text" id="descricaoCompacta"
												maxlength="30" size="30"
												value="#{produtoBB.descricaoCompacta}" >
												<f:validateLength maximum="30" />
											</h:inputText>

										</div>
										
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Preço Padrão*"></h:outputLabel>
											<h:inputText styleClass="field text" id="precoPadrao"
												maxlength="10" size="10" value="#{produtoBB.precoPadrao}"
												 dir="rtl"
												onkeydown="return(BackSpace(this,event));"  onkeypress="return(MascaraMoeda(this,'','.',event));" >
												<f:validator validatorId="BigDecimalValidator"/>
											</h:inputText>
											
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Preço Promocional"></h:outputLabel>
											<h:inputText styleClass="field text" id="precoPromocional"
												maxlength="10" size="10"
												value="#{produtoBB.precoPromocional}" required="false"
												dir="rtl" 
												onkeydown="return(BackSpace(this,event));"  onkeypress="return(MascaraMoeda(this,'','.',event));" >
												<f:validator validatorId="BigDecimalValidator"/>
											</h:inputText>
											
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Preço de Compra"></h:outputLabel>
											<h:inputText styleClass="field text" id="precoCompra"
												maxlength="10" size="10"
												value="#{produtoBB.precoCompra}" required="false"
												dir="rtl" 
												onkeydown="return(BackSpace(this,event));"  onkeypress="return(MascaraMoeda(this,'','.',event));" >
												<f:validator validatorId="BigDecimalValidator"/>
											</h:inputText>
											
										</div>
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Código Externo*"></h:outputLabel>
											<h:inputText styleClass="field text" id="codigoExterno"
												maxlength="15" size="17" value="#{produtoBB.codigoExterno}"
												onfocus="this.select();" onclick="this.select();"
												onkeypress="return SoNumero(event);"  >
												<f:validateLength maximum="15" />
											</h:inputText>
											
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Código Automação*"></h:outputLabel>
											<h:inputText styleClass="field text" id="codigoAutomacao"
												maxlength="15" size="17"
												value="#{produtoBB.codigoAutomacao}"
												onfocus="this.select();" onclick="this.select();"
												onkeypress="return SoNumero(event);" 
												>
												<f:validateLength maximum="15" />
											</h:inputText>
											
										</div>
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Tipo de Produto*"></h:outputLabel>
											<h:selectOneMenu id="tipos" styleClass="field select"
												style="width: 200px;" value="#{produtoBB.idTipoProduto}"
												>
												<f:selectItems id="tiposSelectItems"
													value="#{produtoBB.tipos}" />
											</h:selectOneMenu>
											
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Grupo de Produto*"></h:outputLabel>
											<h:selectOneMenu id="grupos" styleClass="field select"
												style="width: 200px;" value="#{produtoBB.idGrupo}"
												>
												<f:selectItems id="gruposSelectItems"
													value="#{produtoBB.grupos}" />
											</h:selectOneMenu>
											
										</div>
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Unidade*"></h:outputLabel>
											<h:selectOneMenu id="unidades" styleClass="field select"
												style="width: 200px;" value="#{produtoBB.idUnidade}"
												>
												<f:selectItems id="unidadeSelectItems"
													value="#{produtoBB.unidades}" />
											</h:selectOneMenu>
											
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Imposto*"></h:outputLabel>
											<h:selectOneMenu id="impostos" styleClass="field select"
												style="width: 200px;" value="#{produtoBB.idImposto}"
												>
												<f:selectItems id="impostosSelectItems"
													value="#{produtoBB.impostos}" />
											</h:selectOneMenu>
											
										</div>
									</li>
									<li class="normal">	
										<div>
											<h:outputLabel styleClass="desc" value="Fabricante*"></h:outputLabel>
											<h:selectOneMenu id="Fabricantes" styleClass="field select"
												style="width: 200px;" value="#{produtoBB.idFabricante}"
												>
												<f:selectItems id="fabricantesSelectItems"
													value="#{produtoBB.fabricantes}" />
											</h:selectOneMenu>
											
										</div>
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Quant. Mínima"></h:outputLabel>
											<h:inputText styleClass="field text" id="quantidadeMinimaProduto" maxlength="8" size="9"
												value="#{produtoBB.quantidadeMinimaProduto}" dir="rtl" required="false" onkeydown="return(BackSpaceQTD(this,event));"  onkeypress="return(MascaraQTD(this,'','.',event));">
												<f:validateLength maximum="8" />
												<f:validateDoubleRange  minimum="0.000" maximum="999.999"/>
												<f:validator validatorId="BigDecimalValidator"/>
											</h:inputText>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Markup"></h:outputLabel>
											<h:inputText styleClass="field text" id="markup" maxlength="8" size="9"
												value="#{produtoBB.markup}" dir="rtl" required="false" onkeydown="return(BackSpace(this,event));"  onkeypress="return(MascaraMoeda(this,'','.',event));">
												<f:validateLength maximum="8" />
												<f:validateDoubleRange  minimum="0.01" maximum="999.99"/>
												<f:validator validatorId="BigDecimalValidator"/>
											</h:inputText>
										</div>
									</li>

								</ul>
							</div>

							<div id="tabDiv1" style="display:none;height: 350px;">
								<ul>
									<li class="normal">
										<div class="div-auto-scroll"
											style="width:400px !important; height: 210px;">
											<h:selectManyCheckbox id="listaLojas" layout="pageDirection"
												required="false" styleClass="label"
												value="#{produtoBB.listaLojas}">
												<f:selectItems value="#{produtoBB.lojas}" />
											</h:selectManyCheckbox>
											
										</div>

									</li>
								</ul>
							</div>
							<div id="tabDiv2" style="display:none;height: 350px;">
								<ul>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Código Produto*"></h:outputLabel>
											<h:inputText styleClass="field text" id="idProdutoComposicao" maxlength="6" size="6"
												value="#{produtoBB.idProdutoComposicao}" dir="ltr" required="false" onkeypress="return SoNumero(event);">
												<f:validateLength maximum="6" />
											</h:inputText>
											<h:commandButton immediate="true" image="/images/pesquisa.png" alt="Pesquisar Produto" styleClass="btTxt" id="botaoConsultarProduto"
												onmousedown="showPopUp(this,'frmInserirProduto','find')"
												onclick="return false" value="">
											</h:commandButton>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Descrição Completa*"></h:outputLabel>
											<h:inputText styleClass="field text" id="descricaoProdutoComposicao" size="39"
												value="#{produtoBB.descricaoProdutoComposicao}" readonly="true">
											</h:inputText>
										</div>									
										<div>
											<h:outputLabel styleClass="desc" value="Quantidade*"></h:outputLabel>
											<h:inputText styleClass="field text" id="quantidadeProdutoComposicao" maxlength="8" size="9"
												value="#{produtoBB.quantidadeProdutoComposicao}" dir="rtl" required="false" onkeydown="return(BackSpaceQTD(this,event));"  onkeypress="return(MascaraQTD(this,'','.',event));">
												<f:validateLength maximum="8" />
												<f:validateDoubleRange  minimum="0.000" maximum="999.999"/>
												<f:validator validatorId="BigDecimalValidator"/>
											</h:inputText>
											<h:commandButton image="/images/adicionar.png" alt="Inserir" onclick="calculaQuantidadeTotal();" styleClass="btTxt" id="botaoInserirItemComposicao" action="#{produtoBB.inserirItemComposicao}" value="Inserir"></h:commandButton>
										</div>
										<div style="position:relative; top:9px;">
											
										</div>
	  								</li>
								</ul>
								<div class="listagemSimples" style="overflow:auto; height:235px;">
									<t:dataTable value="#{produtoBB.itensComposicao}"
										var="itemComposicao" rowClasses="rowA,rowB" width="100%" renderedIfEmpty="false">
										<h:column>
											<f:facet name="header">
												<h:outputText value="Item" /> 
											</f:facet>
											<h:outputText style="align: center;" value="#{itemComposicao.pk.produto.id}" /> 
										</h:column>
										<h:column>
											<f:facet name="header">
												<h:outputText value="Produto" />
											</f:facet>
											<h:outputText style="align: left;" value="#{itemComposicao.pk.produto.descricaoCompleta}" /> 
										</h:column>
										<h:column>
											<f:facet name="header">
												<h:outputText value="Quantidade" />
											</f:facet>
											<h:outputText style="align: right;" value="#{itemComposicao.quantidade}" /> 
										</h:column>	
										<h:column>
											<f:facet name="header">
												<h:outputText style="align: center;" value="Ação" />
											</f:facet>										
											<h:commandButton image="/images/excluir.png" alt="Excluir" actionListener="#{produtoBB.removerItemComposicao}">
												<f:param name="idExcluirItemComposicao" value="#{itemComposicao.pk.produto.id}" id="idExcluirItemComposicao"/>
											</h:commandButton>
										</h:column>													
									</t:dataTable>																
								</div>	
								<ul>
									<li class="direita">
										<div>
											<h:outputLabel styleClass="desc" value="Quantidade Total"></h:outputLabel>
											<h:inputText styleClass="field text" id="quantidadeProdutoComposicaoTotal" maxlength="10" size="10"
												value="#{produtoBB.quantidadeProdutoComposicaoTotal}" dir="rtl" required="false" readonly="true">
												<f:validateLength maximum="10" />
												<f:validateDoubleRange  minimum="0.00" maximum="9999999.99"/>
												<f:validator validatorId="BigDecimalValidator"/>
											</h:inputText>
										</div>
									</li>
								</ul>
							</div>
							<ul>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" action="#{produtoBB.resetBB}" id="botaoLimpar" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoInserir"
										action="#{produtoBB.inserir}" value="Inserir"></h:commandButton>
								</li>
							</ul>
						</h:form>
						<div class="clear"></div>
					</div>
				</div>
	
		</body>
	</f:view>
</html>
