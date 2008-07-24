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
				<ul>
					<li class="normal">
						<div>
							<h:messages errorClass="msgSistemaErro" infoClass="msgSistemaSucesso" globalOnly="true" showDetail="true"/>
						</div>
					</li>
				</ul>
				<h:form id="frmInserirTransacao" onsubmit="javascript:getId('frmInserirTransacao:abaCorrente').value = strAbaCorrente;">
					<h:inputHidden id="abaCorrente" value="#{transacaoBB.abaCorrente}"></h:inputHidden>
					<div id="tabDiv0">
						<ul>								
							<li class="normal">
								<div>
									<h:outputLabel styleClass="desc" value="Loja*"></h:outputLabel>
									<h:selectOneMenu id="idLoja" style="width: 200px;" value="#{transacaoBB.idLoja}"> 
										<f:selectItems id="lojasSelectItems" value="#{transacaoBB.lojas}" />   
									</h:selectOneMenu>
								</div>
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
								<div>
									<h:outputLabel styleClass="desc" value="Número Transação*"></h:outputLabel>
									<h:inputText styleClass="field text ativo" id="nsuTransacao" maxlength="6" onkeypress="return SoNumero(event);"
										value="#{transacaoBB.nsuTransacao}" size="6" required="true" disabled="true">
										<f:validateLength maximum="6" />
										<f:validator validatorId="LongValidator"/>
									</h:inputText>
								</div>
								<div>
									<h:outputLabel styleClass="desc" value="Número Cupom*"></h:outputLabel>
									<h:inputText styleClass="field text ativo" id="numeroCupom" maxlength="6" onkeypress="return SoNumero(event);"
										value="#{transacaoBB.numeroCupom}" size="6" required="true" disabled="true">
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
								<div>
									<h:outputLabel styleClass="desc" value="Vendedor"></h:outputLabel>
									<h:selectOneMenu id="idComponente" style="width: 180px;" value="#{transacaoBB.idVendedor}"> 
										<f:selectItems id="vendedoresSelectItems" value="#{transacaoBB.usuarios}" />   
									</h:selectOneMenu>
								</div>
								<div>
									<h:outputLabel styleClass="desc" value="Operador"></h:outputLabel>
									<h:selectOneMenu id="idOperador" style="width: 180px;" value="#{transacaoBB.idOperador}"> 
										<f:selectItems id="operadoresSelectItems" value="#{transacaoBB.usuarios}" />   
									</h:selectOneMenu>
								</div>
							</li>							
						</ul>
					</div>
					<div id="tabDiv1"  style="display:none;">
						<fieldset>
							<legend><b>Item</b></legend>
							<ul>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Código Produto*"></h:outputLabel>
										<h:inputText styleClass="field text" id="codigoProduto" maxlength="6" size="6"
											value="#{transacaoBB.codigoProduto}" dir="ltr" required="false" onkeypress="return SoNumero();">
											<f:validateLength maximum="6" />
										</h:inputText>
									</div>
  								</li>
  								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Descrição Completa*"></h:outputLabel>
										<h:inputText styleClass="field text" id="descricaoProduto" size="50"
											value="#{transacaoBB.descricaoProduto}" disabled="true">
										</h:inputText>
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Valor Unit.*"></h:outputLabel>
										<h:inputText styleClass="field text" id="precoVenda" maxlength="9" size="9"
											value="#{transacaoBB.precoVenda}" dir="rtl" required="false" onkeypress="return SoNumero();" onkeydown="Formata('frmInserirTransacao:precoVenda',9,2,event);">
											<f:validateLength maximum="9" />
											<f:validateDoubleRange  minimum="0.00" maximum="999999.99"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Quantidade*"></h:outputLabel>
										<h:inputText styleClass="field text" id="quantidade" maxlength="7" size="7"
											value="#{transacaoBB.quantidade}" dir="rtl" required="false" onkeypress="return SoNumero();" onkeydown="Formata('frmInserirTransacao:quantidade',7,3,event);">
											<f:validateLength maximum="7" />
											<f:validateDoubleRange  minimum="0.000" maximum="999.999"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Desconto"></h:outputLabel>
										<h:inputText styleClass="field text" id="descontoItem" maxlength="5" size="5"
											value="#{transacaoBB.descontoItem}" dir="rtl" required="false" onkeypress="return SoNumero();" onkeydown="Formata('frmInserirTransacao:descontoItem',4,2,event);">
											<f:validateLength maximum="5" />
											<f:validateDoubleRange  minimum="0.00" maximum="99.99"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Valor Item*"></h:outputLabel>
										<h:inputText styleClass="field text" id="valorItem" maxlength="9" size="9"
											value="#{transacaoBB.valorItem}" dir="rtl" required="false" onkeypress="return SoNumero();" onkeydown="Formata('frmInserirTransacao:valorItem',9,2,event);">
											<f:validateLength maximum="9" />
											<f:validateDoubleRange  minimum="0.00" maximum="999999.99"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
									</div>
									<div>
										<h:commandButton styleClass="btTxt" id="botaoInserirItemTransacao" action="#{transacaoBB.inserirItemTransacao}" value="Inserir Item"></h:commandButton>
									</div>
  								</li>
							</ul>
							<div class="listagemSimples">
								<t:dataTable value="#{transacaoBB.itensTransacao}"
									var="itemTransacao" rowClasses="rowA,rowB" width="95%" renderedIfEmpty="false">
									<h:column>
										<f:facet name="header">
											<h:outputText value="Item" /> 
										</f:facet>
										<h:outputText value="#{itemTransacao.pk.numeroEvento}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Produto" />
										</f:facet>
										<h:outputText value="#{itemTransacao.produtoItemRegistrado.codigoExterno - itemTransacao.produtoItemRegistrado.descricaoCompleta}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Valor Unit." />
										</f:facet>
										<h:outputText value="#{itemTransacao.produtoItemRegistrado.precoPadrao}" /> 
									</h:column>	
									<h:column>
										<f:facet name="header">
											<h:outputText value="Valor Venda" />
										</f:facet>
										<h:outputText value="#{itemTransacao.produtoItemRegistrado.precoPraticado}" /> 
									</h:column>	
									<h:column>
										<f:facet name="header">
											<h:outputText value="Quantidade" />
										</f:facet>
										<h:outputText value="#{itemTransacao.quantidade}" /> 
									</h:column>	
									<h:column>
										<f:facet name="header">
											<h:outputText value="Desc. Item" />
										</f:facet>
										<h:outputText value="#{itemTransacao.descontoItem}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Valor Item" />
										</f:facet>
										<h:outputText value="#{itemTransacao.precoVenda}" /> 
									</h:column>	
									<h:column>
										<f:facet name="header">
											<h:outputText style="align: center;" value="Ação" />
										</f:facet>
										
										<h:commandLink  value="Excluir" 
											action="#{transacaoBB.excluirItemTransacao}">																	
											<f:param name="idExcluirItemRegistrado" value="#{itemTransacao.pk.numeroEvento}" />
										</h:commandLink>
									</h:column>													
								</t:dataTable>																
							</div>
						</fieldset>								
					</div>	
					<div id="tabDiv2"  style="display:none;">
						Em construção
					</div>					
					<ul>
						<li class="normal">
							<div>
								<h:outputLabel styleClass="desc" value="Sub-Total"></h:outputLabel>
								<h:inputText styleClass="field text" id="valorSubTotalCupom" maxlength="9" size="9"
									value="#{transacaoBB.valorSubTotalCupom}" dir="rtl" required="false" onkeypress="return SoNumero();" onkeydown="Formata('frmInserirTransacao:valorSubTotalCupom',9,2,event);">
									<f:validateLength maximum="9" />
									<f:validateDoubleRange  minimum="0.00" maximum="999999.99"/>
									<f:validator validatorId="BigDecimalValidator"/>
								</h:inputText>
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Desconto"></h:outputLabel>
								<h:inputText styleClass="field text" id="descontoCupom" maxlength="5" size="5"
									value="#{transacaoBB.descontoCupom}" dir="rtl" required="false" onkeypress="return SoNumero();" onkeydown="Formata('frmInserirTransacao:descontoCupom',4,2,event);">
									<f:validateLength maximum="5" />
									<f:validateDoubleRange  minimum="0.00" maximum="99.99"/>
									<f:validator validatorId="BigDecimalValidator"/>
								</h:inputText>
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Total Cupom"></h:outputLabel>
								<h:inputText styleClass="field text" id="valorTotalCupom" maxlength="9" size="9"
									value="#{transacaoBB.valorTotalCupom}" dir="rtl" required="false" onkeypress="return SoNumero();" onkeydown="Formata('frmInserirTransacao:valorTotalCupom',9,2,event);">
									<f:validateLength maximum="9" />
									<f:validateDoubleRange  minimum="0.00" maximum="999999.99"/>
									<f:validator validatorId="BigDecimalValidator"/>
								</h:inputText>
							</div>
						</li>
						<li class="normal">
							<div>
								<h:outputLabel styleClass="desc" value="Troco"></h:outputLabel>
								<h:inputText styleClass="field text" id="valorTroco" maxlength="9" size="9"
									value="#{transacaoBB.valorTroco}" dir="rtl" required="false" onkeypress="return SoNumero();" onkeydown="Formata('frmInserirTransacao:valorSubTotalCupom',9,2,event);">
									<f:validateLength maximum="9" />
									<f:validateDoubleRange  minimum="0.00" maximum="999999.99"/>
									<f:validator validatorId="BigDecimalValidator"/>
								</h:inputText>
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Forma Rec. Troco"></h:outputLabel>
								<h:selectOneMenu id="idFormaTroco" style="width: 180px;" 
									value="#{transacaoBB.idFormaTroco}"> 
										  <f:selectItems id="formasSelectItems" value="#{transacaoBB.formas}"  />   
								</h:selectOneMenu>
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