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
				strAbaCorrente = getId("frmInserirTransacao:abaCorrente").value;
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
            	var descontoCupom = parseFloat(getId("frmInserirTransacao:descontoCupom").value);
            	var valorTroco = parseFloat(getId("frmInserirTransacao:valorTroco").value);            	
            	var valorTotalCupom = parseFloat(getId("frmInserirTransacao:valorTotalCupom").value);            
            
            	valorTotalCupom = valorSubTotalCupom - descontoCupom - valorTroco;
				getId("frmInserirTransacao:valorTotalCupom").value = valorTotalCupom.toFixed(2);
            }
            
            //funcao que troca a visibilidade dos div das formas de pagamento a transacao
            function trocaDivFormaRecebimento(){
            	var formaSelecionada = getId("frmInserirTransacao:idFormaPagamento").value;
				switch(formaSelecionada){
				   case "1": //dinheiro
					break;
				   case "2": //cheque a vista
					break;
				   case "3": //cheque-pre
					break;
				   case "4": //cartao off
					break;
				   case "5": //cartao proprio
					break;
					
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
			<div id="tabMenu">
				<ul>
					<li id="tabMenuDiv0" class="current" onclick="selecionaMenuTab(this.id)"><span><a href="#">Transação</a></span></li>
					<li id="tabMenuDiv1" onclick="selecionaMenuTab(this.id)"><span><a href="#">Itens</a></span></li>
					<li id="tabMenuDiv2" onclick="selecionaMenuTab(this.id)"><span><a href="#">Pagamento</a></span></li>
				</ul>
				<div class="clear"></div>
			</div>
			<div id="primarioContentContainerInternas">
				<h:form id="frmInserirTransacao" onsubmit="javascript:getId('frmInserirTransacao:abaCorrente').value = strAbaCorrente;">
					<div>
						<h:messages errorClass="msgSistemaErro" infoClass="msgSistemaSucesso" globalOnly="true" showDetail="true"/>
					</div>
					<h:inputHidden id="abaCorrente" value="#{transacaoBB.abaCorrente}"></h:inputHidden>
					<div id="tabDiv0" style="height: 430px;">
						<ul>								
							<li class="normal">
								<div>
									<h:outputLabel styleClass="desc" value="Loja*"></h:outputLabel>
									<h:selectOneMenu id="idLoja" style="width: 200px;" value="#{transacaoBB.idLoja}"> 
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
									<h:outputLabel styleClass="desc" value="Cliente"></h:outputLabel>
									<h:selectOneMenu id="idCliente" style="width: 180px;" value="#{transacaoBB.idCliente}"> 
										<f:selectItems id="clientesSelectItems" value="#{transacaoBB.clientes}" />   
									</h:selectOneMenu>
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
						</ul>
					</div>
					<div id="tabDiv1"  style="display:none;height: 430px;">
						<fieldset style="height: 380px;width: 100%;">
							<legend><b>Itens da Transação</b></legend>
							<ul>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Código Produto*"></h:outputLabel>
										<h:inputText styleClass="field text" id="codigoProduto" maxlength="6" size="6"
											value="#{transacaoBB.codigoProduto}" dir="ltr" required="false" onkeypress="return SoNumero(event);">
											<f:validateLength maximum="6" />
										</h:inputText>
										<h:commandButton styleClass="btTxt" id="botaoConsultarProduto"
											onmousedown="showPopUp(this,'frmInserirTransacao','find')"
											onclick="return false" value="Consultar">
										</h:commandButton>
									</div>
									<br />
									<br />
									<div>
										<h:outputLabel styleClass="desc" value="Descrição Completa*"></h:outputLabel>
										<h:inputText styleClass="field text" id="descricaoProduto" size="39"
											value="#{transacaoBB.descricaoProduto}" disabled="true">
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
										<h:inputText styleClass="field text" id="descontoItem" maxlength="5" size="10"
											value="#{transacaoBB.descontoItem}" dir="rtl" required="false" onkeypress="return SoNumero(event);" onkeydown="Formata('frmInserirTransacao:descontoItem',4,2,event);" onblur="reCalculaPrecoItem();">
											<f:validateLength maximum="5" />
											<f:validateDoubleRange  minimum="0.00" maximum="99.99"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Valor Item*"></h:outputLabel>
										<h:inputText styleClass="field text" id="valorItem" maxlength="10" size="10" disabled="true"
											value="#{transacaoBB.valorItem}" dir="rtl" required="false" onkeypress="return SoNumero(event);" onkeydown="Formata('frmInserirTransacao:valorItem',9,2,event);">
											<f:validateLength maximum="10" />
											<f:validateDoubleRange  minimum="0.00" maximum="9999999.99"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
									</div>
									<div style="position:relative; top:9px;">
										<h:commandButton onclick="reCalculaPrecoItem();" styleClass="btTxt" id="botaoInserirItemTransacao" action="#{transacaoBB.inserirItemTransacao}" value="Inserir"></h:commandButton>
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
										<h:commandLink  value="Excluir" 
											action="#{transacaoBB.removerItemTransacao}">																	
											<f:param name="idExcluirItemRegistrado" value="#{itemTransacao.pk.numeroEvento}" />
										</h:commandLink>
									</h:column>													
								</t:dataTable>																
							</div>
						</fieldset>								
					</div>	
					<div id="tabDiv2"  style="display:none;height: 430px;">
						<ul>
							<li class="normal">
								<div>
									<h:outputLabel styleClass="desc" value="Forma Recebimento"></h:outputLabel>
									<h:selectOneMenu id="idFormaPagamento" style="width: 200px;" 
										value="#{transacaoBB.idFormaPagamento}" onchange="javascript:trocaDivFormaRecebimento();"> 
											  <f:selectItems id="formasSelectItems" value="#{transacaoBB.formasPagamento}"  />   
									</h:selectOneMenu>
								</div>		
							</li>
							<li class="normal">
								<div id="divFormaDinheiro">
									D I N H E I R O
								</div>
								<div id="divFormaChequeAVista">
									C H E Q U E  À  V I S T A
								</div>
								<div id="divFormaChequePre">
									C H E Q U E  P R É - D A T A D O
								</div>
								<div id="divFormaCartaoOff">
								    C A R T Ã O  O F F
								</div>
								<div id="divFormaCartaoProprio">
								    C A R T Ã O  P R Ó P R I O
								</div>
							</li>
						</ul>
					</div>					
					<ul>
						<li class="normal">
							<div>
								<h:outputLabel styleClass="desc" value="Sub-Total"></h:outputLabel>
								<h:inputText styleClass="field text" id="valorSubTotalCupom" maxlength="10" size="10"
									value="#{transacaoBB.valorSubTotalCupom}" dir="rtl" required="false" disabled="true" onkeypress="return SoNumero(event);" onkeydown="Formata('frmInserirTransacao:valorSubTotalCupom',9,2,event);">
									<f:validateLength maximum="10" />
									<f:validateDoubleRange  minimum="0.00" maximum="9999999.99"/>
									<f:validator validatorId="BigDecimalValidator"/>
								</h:inputText>
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Desconto"></h:outputLabel>
								<h:inputText styleClass="field text" id="descontoCupom" maxlength="5" size="10"
									value="#{transacaoBB.descontoCupom}" dir="rtl" required="false" onkeypress="return SoNumero(event);" onkeydown="Formata('frmInserirTransacao:descontoCupom',4,2,event);">
									<f:validateLength maximum="5" />
									<f:validateDoubleRange  minimum="0.00" maximum="99.99"/>
									<f:validator validatorId="BigDecimalValidator"/>
								</h:inputText>
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Troco"></h:outputLabel>
								<h:inputText styleClass="field text" id="valorTroco" maxlength="10" size="10"
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
								<h:inputText styleClass="field text" id="valorTotalCupom" maxlength="10" size="10" disabled="true"
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