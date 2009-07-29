<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="https://ajax4jsf.dev.java.net/ajax" prefix="a4j"%>
<%@taglib uri="http://yui4jsf.sourceforge.net" prefix="yui"%>


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
			<script type="text/javascript">
				window.onload = function(){ inicializar() };
				function inicializar() {
						$("input.dataProgramada").each(function(i){
							$(this).click(function() {mostraCampos(this.value)});
						});
						if ($('[name=frmInserirPlanoPagamentoChequePre:dataProgramada]:checked').val() != "undefined") {
							mostraCampos($('[name=frmInserirPlanoPagamentoChequePre:dataProgramada]:checked').val());
						}
						strAbaCorrente = getId("frmInserirPlanoPagamentoChequePre:abaCorrente").value;
						if(strAbaCorrente != ""){							
							selecionaMenuTab(strAbaCorrente);
						}

				}

		
		        function mostraCampos(str) {
					var flag = new String(str);
					if (flag.toUpperCase() == "S") {
					    habilita("frmInserirPlanoPagamentoChequePre:quantidadeDiasParcela");
					} else {
						desabilita("frmInserirPlanoPagamentoChequePre:quantidadeDiasParcela");
					    getId("frmInserirPlanoPagamentoChequePre:quantidadeDiasParcela").value = "0";
					}
		      }		
			</script>


		</head>
		<body>

			<div id="outer">
				<div id="topoGeral">
					<div id="tituloPaginaGeral">
						<strong> <h:outputText
								value="#{msgs.inserirPlanoPagamentoChequePre}"></h:outputText> </strong>
					</div>
				</div>
				<div id="content">
					<div class="tabMenu">
						<ul>
							<li id="tabMenuDiv0" class="current"
								onclick="selecionaMenuTab(this.id)">
								<span><a href="#">Geral</a>
								</span>
							</li>
							<li id="tabMenuDiv1" onclick="selecionaMenuTab(this.id)">
								<span><a href="#">Parcelas</a>
								</span>
							</li>
						</ul>
						<div class="clear"></div>
					</div>
					<div id="primarioContentContainerInternas">
						<h:form id="frmInserirPlanoPagamentoChequePre"
							binding="#{planoPagamentoChequePreBB.init}"
							onsubmit="javascript:getId('frmInserirPlanoPagamentoChequePre:abaCorrente').value = strAbaCorrente;">
							<h:inputHidden id="abaCorrente"
								value="#{planoPagamentoChequePreBB.abaCorrente}"></h:inputHidden>
							<div id="tabDiv0">
							<ul>
							<li class="normal">
								<div>
									<h:messages errorClass="msgSistemaErro"
										infoClass="msgSistemaSucesso" globalOnly="true"
										showDetail="true" />
								</div>
							</li>
						</ul>
								<ul>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Código*"></h:outputLabel>
											<h:inputText styleClass="field text ativo" id="id"
												maxlength="2" 
												onfocus="this.select();" onclick="this.select();"
												onkeypress="return SoNumero(event);"
												value="#{planoPagamentoChequePreBB.id}" size="3"
												required="false">
												<f:validateLength maximum="2" />
												<f:validator validatorId="LongValidator" />
											</h:inputText>
											
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Descrição*"></h:outputLabel>
											<h:inputText styleClass="field text" id="descricao"
												maxlength="50" size="50" required="false"
												value="#{planoPagamentoChequePreBB.descricao}">
												<f:validateLength maximum="50" />
											</h:inputText>
											
										</div>
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Valor Mínimo*"></h:outputLabel>
											<h:inputText styleClass="field text" id="valorMinimo"
												maxlength="10" size="10"
												value="#{planoPagamentoChequePreBB.valorMinimo}" dir="rtl"
												required="false" 
												onkeydown="return(BackSpace(this,event));"  onkeypress="return(MascaraMoeda(this,'','.',event));" >
												<f:validateLength maximum="10" />
												<f:validateDoubleRange minimum="0.01" maximum="9999999.99" />
												<f:validator validatorId="BigDecimalValidator" />
											</h:inputText>
											
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Valor Máximo*"></h:outputLabel>
											<h:inputText styleClass="field text" id="valorMaximo"
												maxlength="10" size="10"
												value="#{planoPagamentoChequePreBB.valorMaximo}" dir="rtl"
												required="false" 
												onkeydown="return(BackSpace(this,event));"  onkeypress="return(MascaraMoeda(this,'','.',event));" >
												<f:validateLength maximum="10" />
												<f:validateDoubleRange minimum="0.01" maximum="9999999.99" />
												<f:validator validatorId="BigDecimalValidator" />
											</h:inputText>
											
										</div>
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Perc. Desconto*"></h:outputLabel>
											<h:inputText styleClass="field text" id="percentualDesconto"
												maxlength="5" size="5"
												value="#{planoPagamentoChequePreBB.percDesconto}" dir="rtl"
												required="false" 
												onkeydown="return(BackSpace(this,event));"  onkeypress="return(MascaraMoeda(this,'','.',event));" >
												<f:validateLength maximum="5" />
												<f:validateDoubleRange minimum="0.00" maximum="100.00" />
												<f:validator validatorId="BigDecimalValidator" />
											</h:inputText>
											
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Perc. Acréscimo*"></h:outputLabel>
											<h:inputText styleClass="field text" id="percentualAcrescimo"
												maxlength="5" size="5"
												value="#{planoPagamentoChequePreBB.percAcrescimo}" dir="rtl"
												required="false" 
												onkeydown="return(BackSpace(this,event));"  onkeypress="return(MascaraMoeda(this,'','.',event));" >
												<f:validateLength maximum="5" />
												<f:validateDoubleRange minimum="0.00" maximum="100.00" />
												<f:validator validatorId="BigDecimalValidator" />
											</h:inputText>
											
										</div>
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Inicio Validade*"></h:outputLabel>
											<h:inputText styleClass="field text" id="dataInicioValidade"
												maxlength="10" size="10" required="false"
												value="#{planoPagamentoChequePreBB.dataInicioValidade}"
												onkeypress="return MascaraData(this,event);"
												onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }">

											</h:inputText>
											
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Final Validade*"></h:outputLabel>
											<h:inputText styleClass="field text" id="dataFimValidade"
												maxlength="10" size="10" required="false"
												value="#{planoPagamentoChequePreBB.dataFimValidade}"
												onkeypress="return MascaraData(this,event);"
												onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }">
											</h:inputText>
											
										</div>
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Situação*"></h:outputLabel>
											<h:selectOneRadio styleClass="field select" id="status"
												value="#{planoPagamentoChequePreBB.status}"
												layout="lineDirection">
												<f:selectItems id="situacao"
													value="#{planoPagamentoChequePreBB.situacaoItens}" />
											</h:selectOneRadio>
											
										</div>
									</li>
								</ul>
							</div>
							<div id="tabDiv1" style="display:none;">
								<ul>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Perc. Entrada*"></h:outputLabel>
											<h:inputText styleClass="field text" id="percentualEntrada"
												maxlength="5" size="5"
												value="#{planoPagamentoChequePreBB.percentagemEntrada}"
												dir="rtl" required="false"
												onkeydown="return(BackSpace(this,event));"  onkeypress="return(MascaraMoeda(this,'','.',event));" >
												<f:validateLength maximum="5" />
												<f:validateDoubleRange minimum="0.00" maximum="100.00" />
												<f:validator validatorId="BigDecimalValidator" />
											</h:inputText>
											
										</div>
									</li>
								</ul>
								<fieldset>
									<legend>
										<b>Parcelas</b>
									</legend>
									<ul>
										<li class="normal">
											<div>
												<h:outputLabel styleClass="desc" value="Data Programada*"></h:outputLabel>
												<h:selectOneRadio onclick="mostraCampos(this.value);" styleClass="field select tipopessoa"
													id="dataProgramada"
													value="#{planoPagamentoChequePreBB.dataProgramada}"
													layout="lineDirection" >
													<f:selectItems  id="radioDataProgramada"
														value="#{planoPagamentoChequePreBB.dataProgramadaSimNao}" />
												</h:selectOneRadio>
												
											</div>
											<div>
												<h:outputLabel styleClass="desc" value="Percentual*"></h:outputLabel>
												<h:inputText styleClass="field text" id="percentualParcela"
													maxlength="6" size="6"
													value="#{planoPagamentoChequePreBB.percentagemParcela}"
													dir="rtl" required="false" 
													onkeydown="return(BackSpace(this,event));"  onkeypress="return(MascaraMoeda(this,'','.',event));" >
													<f:validateLength maximum="6" />
													<f:validateDoubleRange minimum="0.00" maximum="100.00" />
													<f:validator validatorId="BigDecimalValidator" />
												</h:inputText>
												
											</div>
											<div>
												<h:outputLabel styleClass="desc" value="Qtd. de Dias*"></h:outputLabel>
												<h:inputText styleClass="field text ativo"
													id="quantidadeDiasParcela" dir="rtl" maxlength="3"
													value="#{planoPagamentoChequePreBB.quantidadeDias}"
													size="3" rendered="true"
													onfocus="this.select();" onclick="this.select();"
													onkeypress="return SoNumero(event);">
													<f:validateLength maximum="3" />
													<f:validator validatorId="LongValidator" />
												</h:inputText>
												
											</div>
											<div>
												<h:commandButton image="/images/adicionar.png" alt="Inserir Parcela" styleClass="btTxt" id="botaoInserirParcela"
													action="#{planoPagamentoChequePreBB.inserirParcela}"
													value="Inserir"></h:commandButton>
											</div>
										</li>
									</ul>
									<div class="listagemSimples">
										<t:dataTable value="#{planoPagamentoChequePreBB.parcelas}"
											var="parcela" rowClasses="rowA,rowB" width="95%"
											renderedIfEmpty="false">
											<h:column>
												<f:facet name="header">
													<h:outputText value="Código" />
												</f:facet>
												<h:outputText value="#{parcela.pk.numeroEntrada}" />
											</h:column>
											<h:column>
												<f:facet name="header">
													<h:outputText value="Percentual" />
												</f:facet>
												<h:outputText value="#{parcela.percentagemParcela}" />
											</h:column>
											<h:column>
												<f:facet name="header">
													<h:outputText value="Qtd. de dias" />
												</f:facet>
												<h:outputText value="#{parcela.quantidadeDias}" />
											</h:column>
											<h:column>
												<f:facet name="header">
													<h:outputText value="Ação" />
												</f:facet>
										<
										<h:commandLink value="Excluir"
													action="#{planoPagamentoChequePreBB.excluirParcela}">
													<f:param name="idExcluir"
														value="#{parcela.pk.numeroEntrada}" />
												</h:commandLink>
											</h:column>
										</t:dataTable>
									</div>
								</fieldset>
							</div>
							<ul>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" action="#{planoPagamentoChequePreBB.resetBB}" id="botaoLimpar" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoInserir"
										action="#{planoPagamentoChequePreBB.inserir}" value="Inserir"></h:commandButton>
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

