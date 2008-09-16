<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="https://ajax4jsf.dev.java.net/ajax" prefix="a4j"%>
<%@taglib uri="http://yui4jsf.sourceforge.net" prefix="yui"%>


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
		
		<script type="text/javascript">
			window.onload = function(){ inicializar() };
			function inicializar() {
				trocaDivFormaRecebimento();
				
				if(getId("frmInserirTransacao:valorTroco").value == 0){
					desabilita("frmInserirTransacao:idFormaTroco");
				}else{
					habilita("frmInserirTransacao:idFormaTroco");				
				}
				
				$("input.tipopessoa").each(function(i){
					$(this).click(function() {mostraCampos(this.value)});
				});
				if ($('[name=frmInserirTransacao:idTipoPessoaCadastro]:checked').val() != "undefined") {
					mostraCampos($('[name=frmInserirTransacao:idTipoPessoaCadastro]:checked').val());
				}
				//tipoPessoaChqPrz
				strAbaCorrente = getId("frmInserirTransacao:abaCorrente").value;
				if(strAbaCorrente != ""){							
					selecionaMenuTab(strAbaCorrente);
				}else{
					selecionaMenuTab("tabMenuDiv0");
				}
				
				strAbaCadastroClienteCorrente = getId("frmInserirTransacao:abaCadastroClienteCorrente").value;
				if(strAbaCorrente != ""){							
					selecionaMenuTabInterno(strAbaCadastroClienteCorrente);
				}else{
					selecionaMenuTabInterno("tabMenuDivInterno0");
				}									
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
             function setAtributo(idProduto,descricao,precoVenda) {
                             var form = document.forms[formId];   
                             form[formId+":codigoProduto"].value=idProduto; 
                             form[formId+":descricaoProduto"].value=descricao;  
                             form[formId+":precoVenda"].value=precoVenda; 
                             winId.close();
                             reCalculaPrecoItem();
                             form[formId+":precoVenda"].focus();
                             selecionaMenuTab("tabMenuDiv1");
                            // selecionaMenuTabInterno("tabMenuDivInterno0");
            }
            
            function reCalculaPrecoItem(){
            	var precoVenda = parseFloat(getId("frmInserirTransacao:precoVenda").value);
            	var quantidade = parseFloat(getId("frmInserirTransacao:quantidade").value);
            	var descontoItem = parseFloat(getId("frmInserirTransacao:descontoItem").value);
            	var valorItem = parseFloat(getId("frmInserirTransacao:valorItem").value);          	
            	//calculando o valor do item
            	valorItem = (precoVenda*quantidade)-descontoItem;
            	getId("frmInserirTransacao:valorItem").value = valorItem.toFixed(2);
            }
                        
            function reCalculaTotalCupom(){
            	var valorSubTotalCupom = parseFloat(getId("frmInserirTransacao:valorSubTotalCupom").value);
            	var valorTotalRecebido = parseFloat(getId("frmInserirTransacao:valorTotalRecebido").value);
            	var descontoCupom = parseFloat(getId("frmInserirTransacao:descontoCupom").value);
            	var valorTroco = parseFloat(getId("frmInserirTransacao:valorTroco").value);            	
            	var valorTotalCupom = parseFloat(getId("frmInserirTransacao:valorTotalCupom").value);            
				var valorTemp = parseFloat("0");
				var valorLiquido = parseFloat("0");
				var valorTroco = parseFloat("0");
				
				//calculo do valor total do cupom				
 				valorLiquido = valorSubTotalCupom - descontoCupom;
				
				//calculo do valor do troco
			    valorTroco = valorTotalRecebido - valorLiquido;
				
				getId("frmInserirTransacao:valorTotalCupom").value = valorLiquido.toFixed(2);
				
				getId("frmInserirTransacao:valorTroco").value = valorTroco.toFixed(2);
            }
            
            function reCalculaTotalRecebido(){
            	var valorTotalRecebido = parseFloat(getId("frmInserirTransacao:valorTotalRecebido").value);
            	var valorFormaPagamento = parseFloat(getId("frmInserirTransacao:valorFormaPagamento").value);
            
            	valorTotalRecebido = valorTotalRecebido + valorFormaPagamento;
				getId("frmInserirTransacao:valorTotalRecebido").value = valorTotalRecebido.toFixed(2);
				reCalculaTotalCupom();
            }
            
            //funcao que troca a visibilidade dos div das formas de pagamento a transacao
            function trocaDivFormaRecebimento(){
            	var formaSelecionada = getId("frmInserirTransacao:idFormaPagamento").value;
            	for(i = 1;i<=5;i++){ 
            		var obj = getId("divForma"+i);            		
            		if(i==formaSelecionada){
	            		obj.style.display = "block";
            		}else{
	            		obj.style.display = "none";        		
            		}            	
            	}
            }
            
			function mostraCampos(str) {
				var flag = new String(str);
				if (flag.toUpperCase() == "F") {
				    habilita("frmInserirTransacao:nomeClienteCadastro");
					desabilita("frmInserirTransacao:razaoSocialCadastro");
					desabilita("frmInserirTransacao:inscricaoEstadualCadastro");
					desabilita("frmInserirTransacao:inscricaoMunicipalCadastro");				
					$("input.tipocpfcnpj").each(function(i){
						$(this).unbind('blur');
						$(this).unbind('keydown');
						$(this).bind('blur',function(event){validaCPF(this);});
						$(this).bind('keydown',function(event){FormataCPF(this,event);});
						getId(this.id).maxLength = "14";
					});
				} else {
				   	desabilita("frmInserirTransacao:nomeClienteCadastro");
					habilita("frmInserirTransacao:razaoSocialCadastro");
					habilita("frmInserirTransacao:inscricaoEstadualCadastro");
					habilita("frmInserirTransacao:inscricaoMunicipalCadastro");
					$("input.tipocpfcnpj").each(function(i){
						$(this).unbind('blur');
						$(this).unbind('keydown');
						$(this).bind('blur',function(event){validaCNPJ(this);});
						$(this).bind('keydown',function(event){FormataCNPJ(this,event);});
						getId(this.id).maxLength = "18";
					});
				}
			}
		</script>
	</head>
	<body>

	<div id="outer">
		<div id="topoGeral">
			<div id="tituloPaginaGeral">
				<strong>
					<h:outputText value="#{msgs.inserirTransacao}"></h:outputText>
				</strong>
			</div>				
		</div>	
		<div id="content">
			<div class="tabMenu">
				<ul>
					<li id="tabMenuDiv0" class="current" onclick="selecionaMenuTab(this.id)"><span><a href="#">Transação</a></span></li>
					<li id="tabMenuDiv1" onclick="selecionaMenuTab(this.id)"><span><a href="#">Itens</a></span></li>
					<li id="tabMenuDiv2" onclick="selecionaMenuTab(this.id)"><span><a href="#">Pagamento</a></span></li>
					<li id="tabMenuDiv3" onclick="selecionaMenuTab(this.id)"><span><a href="#">Cliente</a></span></li>
				</ul>
				<div class="clear"></div>
			</div>
			<div id="primarioContentContainerInternas">
				<h:form id="frmInserirTransacao"  binding="#{transacaoBB.init}" onsubmit="javascript:getId('frmInserirTransacao:abaCorrente').value = strAbaCorrente;getId('frmInserirTransacao:abaCadastroClienteCorrente').value = strAbaCadastroClienteCorrente;">
					<div>
						<h:messages errorClass="msgSistemaErro" infoClass="msgSistemaSucesso" globalOnly="true" showDetail="true"/>
					</div>
					<h:inputHidden id="abaCadastroClienteCorrente" value="#{transacaoBB.abaCadastroClienteCorrente}"></h:inputHidden>
					<h:inputHidden id="abaCorrente" value="#{transacaoBB.abaCorrente}"></h:inputHidden>
					<div id="tabDiv0" style="height: 390px;">
						<ul>								
							<li class="normal">
								<div>
									<h:outputLabel styleClass="desc" value="Loja*"></h:outputLabel>
									<h:selectOneMenu id="idLoja" style="width: 200px;" value="#{transacaoBB.idLoja}" onchange="submit();" valueChangeListener="#{transacaoBB.carregarComponentesPorLoja}"> 
										<f:selectItems id="lojasSelectItems" value="#{transacaoBB.lojas}" />   
									</h:selectOneMenu>
								</div>
							</li>	
							<li class="normal">
								<div>
									<h:outputLabel styleClass="desc" value="Componente*"></h:outputLabel>
									<h:selectOneMenu id="idComponente" style="width: 200px;" value="#{transacaoBB.idComponente}"> 
										<f:selectItems id="componentesSelectItems" value="#{transacaoBB.componentes}" />   
									</h:selectOneMenu>
								</div>
							</li>	
							<li class="normal">
								<div>
									<h:outputLabel styleClass="desc" value="Data Transação*"></h:outputLabel>
									<h:inputText styleClass="field text" id="dataTransacao" maxlength="10" size="10" required="false"
										value="#{transacaoBB.dataTransacao}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }">
										<f:convertDateTime timeZone="GMT-3"/>
									</h:inputText>
								</div>
							</li>	
							<li class="normal">
								<div>
									<h:outputLabel styleClass="desc" value="Número Transação*"></h:outputLabel>
									<h:inputText styleClass="field text ativo" id="nsuTransacao" maxlength="6" onkeypress="return SoNumero(event);"
										value="#{transacaoBB.nsuTransacao}" size="6" required="false">
										<f:validateLength maximum="6" />
										<f:validator validatorId="LongValidator"/>
									</h:inputText>
								</div>
							</li>	
							<li class="normal">
								<div>
									<h:outputLabel styleClass="desc" value="Número Cupom*"></h:outputLabel>
									<h:inputText styleClass="field text ativo" id="numeroCupom" maxlength="6" onkeypress="return SoNumero(event);"
										value="#{transacaoBB.numeroCupom}" size="6" required="false">
										<f:validateLength maximum="6" />
										<f:validator validatorId="LongValidator"/>
									</h:inputText>
								</div>
							</li>							
							<li class="normal">
								<div>
									<h:outputLabel styleClass="desc" value="Vendedor"></h:outputLabel>
									<h:selectOneMenu id="idVendedor" style="width: 180px;" value="#{transacaoBB.idVendedor}"> 
										<f:selectItems id="vendedoresSelectItems" value="#{transacaoBB.usuariosVendedores}" />   
									</h:selectOneMenu>
								</div>
							</li>	
							<li class="normal">
								<div>
									<h:outputLabel styleClass="desc" value="Operador"></h:outputLabel>
									<h:selectOneMenu id="idOperador" style="width: 180px;" value="#{transacaoBB.idOperador}"> 
										<f:selectItems id="operadoresSelectItems" value="#{transacaoBB.usuariosOperadores}" />   
									</h:selectOneMenu>
								</div>
							</li>	
							<li class="normal">
								<div>	
									<h:outputLabel styleClass="desc" value="Situacao"></h:outputLabel>
									<h:selectOneMenu id="idSituacao" style="width: 200px;" value="#{transacaoBB.idSituacao}"> 
										<f:selectItems id="situacaoSelectItems" value="#{transacaoBB.listaSituacao}" />   
									</h:selectOneMenu>
								</div>
							</li>						
						</ul>
					</div>
					<div id="tabDiv1"  style="display:none;height: 390px;">
							<ul>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Código Produto*"></h:outputLabel>
										<h:inputText styleClass="field text" id="codigoProduto" maxlength="6" size="6"
											value="#{transacaoBB.codigoProduto}" dir="ltr" required="false" onkeypress="return SoNumero(event);">
											<f:validateLength maximum="6" />
										</h:inputText>
										<h:commandButton image="/images/pesquisa.png" alt="Pesquisar Produto" styleClass="btTxt" id="botaoConsultarProduto"
											onmousedown="showPopUp(this,'frmInserirTransacao','find')"
											onclick="return false" value="">
										</h:commandButton>
									</div>
									<br />
									<br />
									<div>
										<h:outputLabel styleClass="desc" value="Descrição Completa*"></h:outputLabel>
										<h:inputText styleClass="field text" id="descricaoProduto" size="39"
											value="#{transacaoBB.descricaoProduto}" readonly="true">
										</h:inputText>
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Valor Unit.*"></h:outputLabel>
										<h:inputText styleClass="field text" id="precoVenda" maxlength="10" size="10"
											value="#{transacaoBB.precoVenda}" dir="rtl" required="false" onkeypress="return SoNumero(event);" onkeydown="Formata('frmInserirTransacao:precoVenda',9,2,event);" onblur="reCalculaPrecoItem();">
											<f:validateLength maximum="10" />
											<f:validateDoubleRange  minimum="0.00" maximum="9999999.99"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Quantidade*"></h:outputLabel>
										<h:inputText styleClass="field text" id="quantidade" maxlength="9" size="9"
											value="#{transacaoBB.quantidade}" dir="rtl" required="false" onkeypress="return SoNumero(event);" onkeydown="FormataPeso('frmInserirTransacao:quantidade',7,3,event);" onblur="reCalculaPrecoItem();">
											<f:validateLength maximum="9" />
											<f:validateDoubleRange  minimum="0.000" maximum="999.999"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Desconto"></h:outputLabel>
										<h:inputText styleClass="field text" id="descontoItem" maxlength="10" size="10"
											value="#{transacaoBB.descontoItem}" dir="rtl" required="false" onkeypress="return SoNumero(event);" onkeydown="Formata('frmInserirTransacao:descontoItem',9,2,event);" onblur="reCalculaPrecoItem();">
											<f:validateLength maximum="10" />
											<f:validateDoubleRange  minimum="0.00" maximum="9999999.99"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Valor Item*"></h:outputLabel>
										<h:inputText styleClass="field text" id="valorItem" maxlength="10" size="10" readonly="true"
											value="#{transacaoBB.valorItem}" dir="rtl" required="false" onkeypress="return SoNumero(event);" onkeydown="Formata('frmInserirTransacao:valorItem',9,2,event);">
											<f:validateLength maximum="10" />
											<f:validateDoubleRange  minimum="0.00" maximum="9999999.99"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
										<h:commandButton image="/images/adicionar.png" alt="Inserir" onclick="reCalculaPrecoItem();" styleClass="btTxt" id="botaoInserirItemTransacao" action="#{transacaoBB.inserirItemTransacao}" value="Inserir"></h:commandButton>
									</div>
									<div style="position:relative; top:9px;">
										
									</div>
  								</li>
							</ul>
							<div class="listagemSimples" style="overflow:auto; height:235px;">
								<t:dataTable value="#{transacaoBB.itensTransacao}"
									var="itemTransacao" rowClasses="rowA,rowB" width="100%" renderedIfEmpty="false">
									<h:column>
										<f:facet name="header">
											<h:outputText value="Item" /> 
										</f:facet>
										<h:outputText style="align: center;" value="#{itemTransacao.pk.numeroEvento}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Produto" />
										</f:facet>
										<h:outputText style="align: left;" value="#{itemTransacao.produtoItemRegistrado.codigoExterno} - #{itemTransacao.produtoItemRegistrado.descricaoCompleta}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Pr. Unidade" />
										</f:facet>
										<h:outputText style="align: right;" value="#{itemTransacao.produtoItemRegistrado.precoPadrao}" /> 
									</h:column>	
									<h:column>
										<f:facet name="header">
											<h:outputText value="Pr. Venda" />
										</f:facet>
										<h:outputText style="align: right;" value="#{itemTransacao.produtoItemRegistrado.precoPraticado}" /> 
									</h:column>	
									<h:column>
										<f:facet name="header">
											<h:outputText value="Quantidade" />
										</f:facet>
										<h:outputText style="align: right;" value="#{itemTransacao.quantidade}" /> 
									</h:column>	
									<h:column>
										<f:facet name="header">
											<h:outputText value="Desconto" />
										</f:facet>
										<h:outputText style="align: right;" value="#{itemTransacao.desconto}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Pr. Item" />
										</f:facet>
										<h:outputText style="align: right;" value="#{itemTransacao.preco}" /> 
									</h:column>	
									<h:column>
										<f:facet name="header">
											<h:outputText style="align: center;" value="Ação" />
										</f:facet>										
										<h:commandButton image="/images/excluir.png" alt="Excluir" action="#{transacaoBB.removerItemTransacao}">
											<f:param name="idExcluirItemRegistrado" value="#{itemTransacao.pk.numeroEvento}" />
										</h:commandButton>
									</h:column>													
								</t:dataTable>																
							</div>
					
					</div>	
					<div id="tabDiv2" style="display:none;height: 390px;">
						<ul>
							<li class="normal">
								<div>
									<h:outputLabel styleClass="desc" value="Forma Recebimento"></h:outputLabel>
									<h:selectOneMenu id="idFormaPagamento" style="width: 200px;" 
										value="#{transacaoBB.idFormaPagamento}" onchange="trocaDivFormaRecebimento();"> 
											  <f:selectItems id="formasSelectItems" value="#{transacaoBB.formasPagamento}"  />   
									</h:selectOneMenu>
								</div>
								<div>
									<h:outputLabel styleClass="desc" value="Valor"></h:outputLabel>
									<h:inputText styleClass="field text" id="valorFormaPagamento" maxlength="10" size="10" readonly="false"
										value="#{transacaoBB.valorFormaPagamento}" dir="rtl" required="false" onkeypress="return SoNumero(event);" onkeydown="Formata('frmInserirTransacao:valorFormaPagamento',9,2,event);">
										<f:validateLength maximum="10" />
										<f:validateDoubleRange  minimum="0.00" maximum="9999999.99"/>
										<f:validator validatorId="BigDecimalValidator"/>
									</h:inputText>
								
									<h:commandButton image="/images/adicionar.png" alt="Inserir Forma de Recebimento" styleClass="btTxt" id="botaoInserirItemPagamento" action="#{transacaoBB.inserirItemPagamento}" value="Inserir"></h:commandButton>
								</div>
							</li>
							<li class="normal">
								<div id="divForma1">
									&nbsp;
								</div>
								<div id="divForma2" style="display:none;">									
									<ul>
										<li class="normal">
											<div>
												<h:outputLabel styleClass="desc" value="Banco"></h:outputLabel>
												<h:inputText styleClass="field text" id="codigoBancoChqAvt" maxlength="3" size="8"
													value="#{transacaoBB.codigoBancoChqAvt}" dir="ltr" required="false" onkeypress="return SoNumero(event);">
													<f:validateLength maximum="3" />
												</h:inputText>
											</div>
											<div>
												<h:outputLabel styleClass="desc" value="Agência"></h:outputLabel>
												<h:inputText styleClass="field text" id="codigoAgenciaChqAvt" maxlength="6" size="8"
													value="#{transacaoBB.codigoAgenciaChqAvt}" dir="ltr" required="false" onkeypress="return SoNumero(event);">
													<f:validateLength maximum="6" />
												</h:inputText>
											</div>
											<div>
												<h:outputLabel styleClass="desc" value="Num. Conta"></h:outputLabel>
												<h:inputText styleClass="field text" id="numeroContaChqAvt" maxlength="8" size="8"
													value="#{transacaoBB.numeroContaChqAvt}" dir="ltr" required="false" onkeypress="return SoNumero(event);">
													<f:validateLength maximum="8" />
												</h:inputText>
											</div>
											<div>
												<h:outputLabel styleClass="desc" value="Num. Cheque"></h:outputLabel>
												<h:inputText styleClass="field text" id="numeroChequeChqAvt" maxlength="6" size="8"
													value="#{transacaoBB.numeroChequeChqAvt}" dir="ltr" required="false" onkeypress="return SoNumero(event);">
													<f:validateLength maximum="6" />
												</h:inputText>
											</div>
											<br />
											<br />
											<div>
												<h:outputLabel styleClass="desc" value="CMC7"></h:outputLabel>
												<h:inputText styleClass="field text" id="cmc7ChqAvt" maxlength="30" size="30"
													value="#{transacaoBB.cmc7ChqAvt}" dir="ltr" required="false" onkeypress="return SoNumero(event);">
													<f:validateLength maximum="30" />
												</h:inputText>
											</div>											
											<div>
												<h:outputLabel styleClass="desc" value="Tipo Cliente"></h:outputLabel>
												<h:selectOneRadio  styleClass="field select tipopessoa" id="tipoPessoaChqAvt" 
													value="#{transacaoBB.idTipoPessoa}" layout="lineDirection" required="false">
													<f:selectItems id="tipoPessoaListaChqAvt" value="#{transacaoBB.listaTipoPessoa}"/>
												</h:selectOneRadio>
											</div>
											<div>
												<h:outputLabel styleClass="desc" value="CPF/CNPJ Cliente"></h:outputLabel>
												<h:inputText styleClass="field text tipocpfcnpj" id="cpfCnpjClienteChqAvt" maxlength="14" size="14"
													value="#{transacaoBB.cpfCnpjClienteChqAvt}" dir="ltr" required="false" onkeypress="return SoNumero(event);">
													<f:validateLength maximum="14" />
												</h:inputText>
											</div>											
										</li>
									</ul>
								</div>
								<div id="divForma3" style="display:none;">
									<ul>
										<li class="normal">
											<div>
												<h:outputLabel styleClass="desc" value="Banco"></h:outputLabel>
												<h:inputText styleClass="field text" id="codigoBancoChqPrz" maxlength="3" size="8"
													value="#{transacaoBB.codigoBancoChqPrz}" dir="ltr" required="false" onkeypress="return SoNumero(event);">
													<f:validateLength maximum="3" />
												</h:inputText>
											</div>
											<div>
												<h:outputLabel styleClass="desc" value="Agência"></h:outputLabel>
												<h:inputText styleClass="field text" id="codigoAgenciaChqPrz" maxlength="6" size="8"
													value="#{transacaoBB.codigoAgenciaChqPrz}" dir="ltr" required="false" onkeypress="return SoNumero(event);">
													<f:validateLength maximum="6" />
												</h:inputText>
											</div>
											<div>
												<h:outputLabel styleClass="desc" value="Num. Conta"></h:outputLabel>
												<h:inputText styleClass="field text" id="numeroContaChqPrz" maxlength="8" size="8"
													value="#{transacaoBB.numeroContaChqPrz}" dir="ltr" required="false" onkeypress="return SoNumero(event);">
													<f:validateLength maximum="8" />
												</h:inputText>
											</div>
											<div>
												<h:outputLabel styleClass="desc" value="Num. Cheque"></h:outputLabel>
												<h:inputText styleClass="field text" id="numeroChequeChqPrz" maxlength="6" size="8"
													value="#{transacaoBB.numeroChequeChqPrz}" dir="ltr" required="false" onkeypress="return SoNumero(event);">
													<f:validateLength maximum="6" />
												</h:inputText>
											</div>
											<div>
												<h:outputLabel styleClass="desc" value="Data Vencimento"></h:outputLabel>
												<h:inputText styleClass="field text" id="dataVencimento" maxlength="10" size="10" required="false"
													value="#{transacaoBB.dataVencimentoChqPrz}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }">
													<f:convertDateTime timeZone="GMT-3"/>
												</h:inputText>
											</div>
											<br />
											<br />
											<div>
												<h:outputLabel styleClass="desc" value="CMC7"></h:outputLabel>
												<h:inputText styleClass="field text" id="cmc7ChqPrz" maxlength="30" size="30"
													value="#{transacaoBB.cmc7ChqPrz}" dir="ltr" required="false" onkeypress="return SoNumero(event);">
													<f:validateLength maximum="30" />
												</h:inputText>
											</div>
											<div>
												<h:outputLabel styleClass="desc" value="Tipo Cliente"></h:outputLabel>
												<h:selectOneRadio  styleClass="field select tipopessoa" id="tipoPessoaChqPrz" 
													value="#{transacaoBB.idTipoPessoa}" layout="lineDirection" required="false">
													<f:selectItems id="tipoPessoaListaChqPrz" value="#{transacaoBB.listaTipoPessoa}"/>
												</h:selectOneRadio>
											</div>
											<div>
												<h:outputLabel styleClass="desc" value="CPF/CNPJ Cliente"></h:outputLabel>
												<h:inputText styleClass="field text tipocpfcnpj" id="cpfCnpjClienteChqPrz" maxlength="14" size="14"
													value="#{transacaoBB.cpfCnpjClienteChqPrz}" dir="ltr" required="false" onkeypress="return SoNumero(event);">
													<f:validateLength maximum="14" />
												</h:inputText>
											</div>										
										</li>
									</ul>
								</div>
								<div id="divForma4" style="display:none;">
									<ul>
										<li class="normal">
											<div>
												<h:outputLabel styleClass="desc" value="Número Cartão"></h:outputLabel>
												<h:inputText styleClass="field text" id="numeroCartao" maxlength="19" size="20"
													value="#{transacaoBB.numeroCartao}" dir="ltr" required="false" onkeypress="return SoNumero(event);">
													<f:validateLength maximum="19" />
												</h:inputText>
											</div>
											<div>
												<h:outputLabel styleClass="desc" value="Autorizadora"></h:outputLabel>
												<h:selectOneMenu id="codigoAutorizadora" style="width: 180px;" value="#{transacaoBB.codigoAutorizadora}"> 
													<f:selectItems id="autorizadoraSelectItems" value="#{transacaoBB.autorizadoras}" />   
												</h:selectOneMenu>
											</div>
											<div>
												<h:outputLabel styleClass="desc" value="Num. Autorização"></h:outputLabel>
												<h:inputText styleClass="field text" id="numeroAutorizacao" maxlength="6" size="8"
													value="#{transacaoBB.codigoAutorizacao}" dir="ltr" required="false" onkeypress="return SoNumero(event);">
													<f:validateLength maximum="6" />
												</h:inputText>
											</div>
											<div>
												<h:outputLabel styleClass="desc" value="Num. Parcelas"></h:outputLabel>
												<h:inputText styleClass="field text" id="numeroParcelas" maxlength="2" size="6"
													value="#{transacaoBB.quantidadeParcelasCartao}" dir="ltr" required="false" onkeypress="return SoNumero(event);">
													<f:validateLength maximum="2" />
												</h:inputText>
											</div>
										</li>
									</ul>
								</div>
								<div id="divForma5" style="display:none;">
									<ul>
										<li class="normal">
											<div>
												<h:outputLabel styleClass="desc" value="Tipo Cliente"></h:outputLabel>
												<h:selectOneRadio  styleClass="field select tipopessoa" id="tipoPessoa" 
													value="#{transacaoBB.idTipoPessoa}" layout="lineDirection" required="false">
													<f:selectItems id="tipoPessoaLista" value="#{transacaoBB.listaTipoPessoa}"/>
												</h:selectOneRadio>
											</div>
											<div>
												<h:outputLabel styleClass="desc" value="CPF/CNPJ Cliente"></h:outputLabel>
												<h:inputText styleClass="field text tipocpfcnpj" id="cpfCnpjClienteCartaoProprio" maxlength="18" size="18"
													value="#{transacaoBB.cpfCnpjCliente}" dir="ltr" required="false" onkeypress="return SoNumero(event);">
													<f:validateLength maximum="14" />
												</h:inputText>
											</div>
										</li>
									</ul>
								</div>
							</li>
							<li class="normal">
								<div class="listagemSimples" style="overflow:auto; height:235px;">
									<t:dataTable value="#{transacaoBB.itensPagamento}"
										var="itemPagamento" rowClasses="rowA,rowB" width="100%" renderedIfEmpty="false">
										<h:column>
											<f:facet name="header">
												<h:outputText value="Número" /> 
											</f:facet>
											<h:outputText style="align: center;" value="#{itemPagamento.pk.numeroEvento}" /> 
										</h:column>
										<h:column>
											<f:facet name="header">
												<h:outputText value="Forma Recebimento" />
											</f:facet>
											<h:outputText style="align: left;" value="#{itemPagamento.descricaoForma}" /> 
										</h:column>
										<h:column>
											<f:facet name="header">
												<h:outputText value="Valor" />
											</f:facet>
											<h:outputText style="align: right;" value="#{itemPagamento.valorBruto}" /> 
										</h:column>	
										<h:column>
											<f:facet name="header">
												<h:outputText style="align: center;" value="Ação" />
											</f:facet>
											<h:commandButton image="/images/excluir.png" alt="Excluir" action="#{transacaoBB.removerItemPagamento}">
												<f:param name="idExcluirItemPagamento" value="#{itemPagamento.pk.numeroEvento}" />
											</h:commandButton>										
										</h:column>													
									</t:dataTable>																
								</div>
							</li>
						</ul>
					</div>
					<div id="tabDiv3" style="display:none;height: 390px;">			
						<!-- 
						<div class="tabMenuDois">
							<ul>
								<li id="tabMenuIntDiv0" class="current" onclick="selecionaMenuIntTab(this.id)"><span><a href="#">Dados Cliente</a></span></li>
								<li id="tabMenuIntDiv1" onclick="selecionaMenuIntTab(this.id)"><span><a href="#">Endereço</a></span></li>
							</ul>
							<div class="clear"></div>							
						</div> -->
									<table class="tabMenuDois" cellspacing="0" cellpadding="0" border="0">
				<tr>
                    <td id="tabMenuIntDiv0" class="current" onclick="selecionaMenuIntTab(this.id)"><span><a href="#">Dados Cliente</a></span></td>
                    <td id="tabMenuIntDiv1" onclick="selecionaMenuIntTab(this.id)"><span><a href="#">Endereço</a></span></td>
				</tr>
			</table>
						
						<div id="primarioContentContainerInternas">
							<div id="tabIntDiv0">
								<ul>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Tipo Pessoa"></h:outputLabel>
											<h:selectOneRadio  styleClass="field select tipopessoa" id="idTipoPessoaCadastro" 
												value="#{transacaoBB.idTipoPessoaCadastro}" layout="lineDirection" required="false">
												<f:selectItems id="tipoPessoaLista" value="#{transacaoBB.listaTipoPessoa}"/>
											</h:selectOneRadio>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="CPF/CNPJ"></h:outputLabel>
											<h:inputText styleClass="field text tipocpfcnpj" id="cpfCnpjClienteCadastro" maxlength="18" size="18" 
											value="#{transacaoBB.cpfCnpjClienteCadastro}" required="false" onkeypress="return SoNumero(event);">
												<f:validateLength minimum="11" maximum="18" />
											</h:inputText>	
											<h:commandButton image="/images/pesquisa.png" alt="Pesquisar" styleClass="btTxt" id="botaoConsultar" action="#{transacaoBB.buscaClientePorCpfCnpj}" value=""></h:commandButton>							
										</div>

									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Nome Cliente"></h:outputLabel>
											<h:inputText styleClass="field text" id="nomeClienteCadastro" maxlength="50" size="50" value="#{transacaoBB.nomeClienteCadastro}" required="false">
												<f:validateLength maximum="50" />
											</h:inputText>								
										</div>									
									</li>										
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Razão Social"></h:outputLabel>
											<h:inputText styleClass="field text" id="razaoSocialCadastro" maxlength="50" size="50" value="#{transacaoBB.razaoSocialCadastro}" required="false">
												<f:validateLength maximum="50" />
											</h:inputText>								
										</div>
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Insc. Estadual"></h:outputLabel>
											<h:inputText styleClass="field text" id="inscricaoEstadualCadastro" maxlength="30" size="30" value="#{transacaoBB.inscricaoEstadualCadastro}" required="false">
												<f:validateLength maximum="30" />
											</h:inputText>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Insc. Municipal"></h:outputLabel>
											<h:inputText styleClass="field text" id="inscricaoMunicipalCadastro" maxlength="30" size="30" value="#{transacaoBB.inscricaoMunicipalCadastro}" required="false">
												<f:validateLength maximum="30" />
											</h:inputText>					
										</div>
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="E-mail"></h:outputLabel>
											<h:inputText styleClass="field text" id="emailCadastro" maxlength="50" size="50" value="#{transacaoBB.emailCadastro}" required="false">
												<f:validateLength maximum="50" />
											</h:inputText>
										</div>
									</li>
									
								</ul>
							</div>
							<div id="tabIntDiv1" style="display:none;">
								<ul>
									<li class="normal">
										<div style="width: 100%;">
											<h:outputLabel styleClass="desc" value="Logradouro"></h:outputLabel>
											<h:inputTextarea rows="3" id="logradouroCadastro" style="width: 90%;" styleClass="field text" value="#{transacaoBB.logradouroCadastro}" required="false">
												<f:validateLength maximum="200" />
											</h:inputTextarea>	
										</div>
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Número"></h:outputLabel>
											<h:inputText styleClass="field text" id="numeroCadastro" maxlength="10" size="10" value="#{transacaoBB.numeroCadastro}" required="false">
												<f:validateLength maximum="10" />
											</h:inputText>						
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Complemento"></h:outputLabel>
											<h:inputText styleClass="field text" id="complementoCadastro" maxlength="20" size="20" value="#{transacaoBB.complementoCadastro}" required="false">
												<f:validateLength maximum="20" />
												</h:inputText>							
										</div>
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Bairro"></h:outputLabel>
											<h:inputText styleClass="field text" id="bairroCadastro" maxlength="30" size="30" value="#{transacaoBB.bairroCadastro}" required="false">
												<f:validateLength maximum="30" />
											</h:inputText>					
										</div>								
										<div>
											<h:outputLabel styleClass="desc" value="Cidade"></h:outputLabel>
											<h:inputText styleClass="field text" id="cidadeCadastro" maxlength="30" size="30" value="#{transacaoBB.cidadeCadastro}" required="false">
												<f:validateLength maximum="30" />
											</h:inputText>
										</div>
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Estado"></h:outputLabel>
											<h:inputText styleClass="field text" id="estadoCadastro" maxlength="30" size="30" value="#{transacaoBB.estadoCadastro}" required="false">
												<f:validateLength maximum="30" />
											</h:inputText>					
										</div>								
										<div>
											<h:outputLabel styleClass="desc" value="CEP"></h:outputLabel>
											<h:inputText styleClass="field text" id="cepCadastro" maxlength="10" size="10" value="#{transacaoBB.cepCadastro}" required="false">
												<f:validateLength maximum="10" />
											</h:inputText>	
										</div>
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Fone"></h:outputLabel>
											<h:inputText styleClass="field text" id="foneResidencialCadastro" maxlength="13" size="13" value="#{transacaoBB.foneResidencialCadastro}" required="false">
												<f:validateLength maximum="13" />
											</h:inputText>
										</div>								
										<div>
											<h:outputLabel styleClass="desc" value="Fone Celular"></h:outputLabel>
											<h:inputText styleClass="field text" id="foneCelularCadastro" maxlength="13" size="13" value="#{transacaoBB.foneCelularCadastro}" required="false">
												<f:validateLength maximum="13" />
											</h:inputText>		
										</div>
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Nome Contato"></h:outputLabel>
											<h:inputText styleClass="field text" id="pessoaContatoCadastro" maxlength="50" size="50" value="#{transacaoBB.pessoaContatoCadastro}" required="false">
												<f:validateLength maximum="50" />
											</h:inputText>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Fone Contato"></h:outputLabel>
											<h:inputText styleClass="field text" id="foneContatoCadastro" maxlength="13" size="13" value="#{transacaoBB.foneContatoCadastro}" required="false">
												<f:validateLength maximum="13" />
											</h:inputText>
										</div>
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Referências Comerciais"></h:outputLabel>
											<h:inputTextarea rows="3" id="referenciaComercialCadastro" style="width: 90%;" styleClass="field text" value="#{transacaoBB.referenciaComercialCadastro}" required="false">
												<f:validateLength maximum="1000" />
											</h:inputTextarea>
										</div>
									</li>
								</ul>
							</div>	
							<div class="clear"></div>						
						</div>
					</div>
					<ul>		
						<li>
							<hr dir="ltr" class="linha" />				
						</li>
						<li class="normal">
							<div>
								<h:outputLabel styleClass="desc" value="Sub-Total"></h:outputLabel>
								<h:inputText styleClass="field text" id="valorSubTotalCupom" maxlength="10" size="10"
									value="#{transacaoBB.valorSubTotalCupom}" dir="rtl" required="false" readonly="true" onkeypress="return SoNumero(event);" onkeydown="Formata('frmInserirTransacao:valorSubTotalCupom',9,2,event);">
									<f:validateLength maximum="10" />
									<f:validateDoubleRange  minimum="0.00" maximum="9999999.99"/>
									<f:validator validatorId="BigDecimalValidator"/>
								</h:inputText>
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Total Recebido"></h:outputLabel>
								<h:inputText styleClass="field text" id="valorTotalRecebido" maxlength="10" size="10"
									value="#{transacaoBB.valorTotalRecebido}" dir="rtl" required="false" readonly="true" onkeypress="return SoNumero(event);" onkeydown="Formata('frmInserirTransacao:valorTotalRecebido',9,2,event);">
									<f:validateLength maximum="10" />
									<f:validateDoubleRange  minimum="0.00" maximum="9999999.99"/>
									<f:validator validatorId="BigDecimalValidator"/>
								</h:inputText>
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Desconto"></h:outputLabel>
								<h:inputText styleClass="field text" id="descontoCupom" maxlength="10" size="10" onblur="reCalculaTotalCupom();"
									value="#{transacaoBB.descontoCupom}" dir="rtl" required="false" onkeypress="return SoNumero(event);" onkeydown="Formata('frmInserirTransacao:descontoCupom',9,2,event);">
									<f:validateLength maximum="10" />
									<f:validateDoubleRange  minimum="0.00" maximum="9999999.99"/>
									<f:validator validatorId="BigDecimalValidator"/>
								</h:inputText>
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Troco"></h:outputLabel>
								<h:inputText styleClass="field text" id="valorTroco" maxlength="10" size="10" onblur="reCalculaTotalCupom();"
									value="#{transacaoBB.valorTroco}" dir="rtl" required="false" onkeypress="return SoNumero(event);" onkeydown="Formata('frmInserirTransacao:valorTroco',9,2,event);">
									<f:validateLength maximum="10" />
									<f:validateDoubleRange  minimum="0.00" maximum="9999999.99"/>
									<f:validator validatorId="BigDecimalValidator"/>
								</h:inputText>
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Forma Rec. Troco"></h:outputLabel>
								<h:selectOneMenu id="idFormaTroco" style="width: 180px;" 
									value="#{transacaoBB.idFormaTroco}"> 
										  <f:selectItems id="formasSelectItems" value="#{transacaoBB.formasTroco}"  />   
								</h:selectOneMenu>
							</div>							
							<div>
								<h:outputLabel styleClass="desc" value="Total Cupom"></h:outputLabel>
								<h:inputText styleClass="field text" id="valorTotalCupom" maxlength="10" size="10" readonly="false"
									value="#{transacaoBB.valorTotalCupom}" dir="rtl" required="false" onkeypress="return SoNumero(event);" onkeydown="Formata('frmInserirTransacao:valorTotalCupom',9,2,event);">
									<f:validateLength maximum="10" />
									<f:validateDoubleRange  minimum="0.00" maximum="999999.99"/>
									<f:validator validatorId="BigDecimalValidator"/>
								</h:inputText>
							</div>
						</li>
						<li class="buttons">
							<h:commandButton styleClass="btTxt" immediate="true" id="botaoLimpar" type="reset" value="Limpar"></h:commandButton>
							<h:commandButton styleClass="btTxt" id="botaoInserir" action="#{transacaoBB.inserir}" value="Inserir"></h:commandButton>
							<h:commandButton styleClass="btTxt" id="botaoImprimir" action="#{transacaoBB.imprimirRecibo}" value="Imprimir"></h:commandButton>
						</li>
					</ul>	
				</h:form>		
			</div>
			<div class="clear"></div>
		</div>
		</div>	
	 </body>						
	</f:view>
</html>