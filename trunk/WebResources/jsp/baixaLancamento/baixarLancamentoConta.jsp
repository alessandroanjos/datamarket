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
		<script type="text/javascript">
		
			window.onload = function(){ inicializar(); };
			
			function inicializar() {
				trocaDivFormaRecebimento();
				$("input.tipopessoa").each(function(i){
					$(this).click(function() {mostraCampos(this.value)});
				});
				if ($('[name=frmBaixarLancamento:idTipoPessoa]:checked').val() != "undefined") {
					mostraCampos($('[name=frmBaixarLancamento:idTipoPessoa]:checked').val());
				}
			}
			
			function mostraCampos(str) {
				var flag = new String(str);
				if (flag.toUpperCase() == "F") {			   
					$("input.tipocpfcnpj").each(function(i){
						$(this).unbind('blur');
						$(this).unbind('keydown');
						$(this).bind('blur',function(event){validaCPF(this);});
						$(this).bind('keydown',function(event){FormataCPF(this,event);});
						getId(this.id).maxLength = "14";
					});
				} else {
					$("input.tipocpfcnpj").each(function(i){
						$(this).unbind('blur');
						$(this).unbind('keydown');
						$(this).bind('blur',function(event){validaCNPJ(this);});
						$(this).bind('keydown',function(event){FormataCNPJ(this,event);});
						getId(this.id).maxLength = "18";
					});
				}
			}
			//funcao que troca a visibilidade dos div das formas de pagamento a transacao
            function trocaDivFormaRecebimento(){
            	var formaSelecionada = getId("frmBaixarLancamento:idFormaRecebimento").value;
            	var obj = getId("divForma1");            		
           		if(formaSelecionada == 2){
            		obj.style.display = "block";
           		}else{
            		obj.style.display = "none";        		
           		}            	
            }
            
            function atualizaValorTotalItem(){
            
            	var valorItem = parseFloat(getId("frmBaixarLancamento:valorItem").value);
            	var valorDesconto = parseFloat(getId("frmBaixarLancamento:valorDesconto").value);
            	var valorAcrescimo = parseFloat(getId("frmBaixarLancamento:valorAcrescimo").value);
            	var valorTotalItem = parseFloat(getId("frmBaixarLancamento:valorTotalItem").value);
            	
            	valorTotalItem = valorItem + valorAcrescimo - valorDesconto;
            	
            	getId("frmBaixarLancamento:valorTotalItem").value = valorTotalItem.toFixed(2);            
            }
		</script>		
	</head>
	<body>
	<div id="outer">
		<div id="topoGeral">
			<div id="tituloPaginaGeral">
				<strong>
					<h:outputText rendered="#{baixaLancamentoBB.tipoLancamento == 'D'}" value="#{msgs.baixarLancamentoContaAPagar}"></h:outputText>
					<h:outputText rendered="#{baixaLancamentoBB.tipoLancamento == 'C'}" value="#{msgs.baixarLancamentoContaAReceber}"></h:outputText>
				</strong>
			</div>				
		</div>
		<h:form id="frmBaixarLancamento" binding="#{baixaLancamentoBB.init}">
				<div id="content">
						<div id="primarioContentContainerInternas">
							<ul>
								<li class="normal">
									<div>
										<h:messages errorClass="msgSistemaErro" infoClass="msgSistemaSucesso" globalOnly="true" showDetail="true"/>
									</div>
								</li>
							</ul>
							<fieldset>
								<legend>Dados do Lançamento</legend>
								<ul>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Loja"></h:outputLabel>
											<h:outputText id="loja" value="#{baixaLancamentoBB.lancamento.loja.id} - #{baixaLancamentoBB.lancamento.loja.nome}"></h:outputText>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Data Lançamento"></h:outputLabel>
											<h:outputText id="dataLancamento" value="#{baixaLancamentoBB.lancamento.dataLancamento}"></h:outputText>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Fornecedor"></h:outputLabel>
											<h:outputText id="fornecedor1" rendered="#{not empty baixaLancamentoBB.lancamento.fornecedor}" value="#{baixaLancamentoBB.lancamento.fornecedor.id} - #{baixaLancamentoBB.lancamento.fornecedor.nomeFantasia}"></h:outputText> 
											<h:outputText id="fornecedor2" rendered="#{empty baixaLancamentoBB.lancamento.fornecedor}" value="SEM FORNECEDOR"></h:outputText> 
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Grupo de Lançamento"></h:outputLabel>
											<h:outputText id="grupoLancamento" value="#{baixaLancamentoBB.lancamento.grupo.id} - #{baixaLancamentoBB.lancamento.grupo.descricao}"></h:outputText>
										</div>								
										<div>
											<h:outputLabel styleClass="desc" value="Descrição"></h:outputLabel>
											<h:outputText id="descricao" value="#{baixaLancamentoBB.lancamento.descricao}"></h:outputText>
										</div>
										<br />
										<br />
										<div>
											<h:outputLabel styleClass="desc" value="Num. Docum." style="width: 86px;"></h:outputLabel>
											<h:outputText id="numeroDocumento" value="#{baixaLancamentoBB.lancamento.numeroDocumento}"></h:outputText>
										</div>								
										<div>
											<h:outputLabel styleClass="desc" value="Data Vencimento" style="width: 100px;"></h:outputLabel>
											<h:outputText id="dataVencimento" value="#{baixaLancamentoBB.lancamento.dataVencimento}"></h:outputText>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Valor Lançamento" style="width: 118px;"></h:outputLabel>
											<h:outputText id="valorLancamento" value="#{baixaLancamentoBB.lancamento.valor}">
												<f:convertNumber currencySymbol="R$" locale="pt-BR" pattern="R$ ###,##0.00"/>
											</h:outputText>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Observação" style="width: 228px;"></h:outputLabel>
											<h:outputText id="observacao" value="#{baixaLancamentoBB.lancamento.observacao}"></h:outputText>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Situação"></h:outputLabel>
											<h:outputText id="descricaoSituacao" style="font-size: 14px; font-weight: bold; color: red;" value="#{baixaLancamentoBB.descricaoSituacao}"></h:outputText>
										</div>
									</li>	
								</ul>
							</fieldset>
							<ul>
								<li class="normal">
									<ul>
										<li class="normal">
											<div>
												<h:outputLabel styleClass="desc" value="Conta Corrente"></h:outputLabel>
												<h:selectOneMenu id="idContaCorrente" style="width: 200px;" 
													value="#{baixaLancamentoBB.idContaCorrente}"> 
														  <f:selectItems id="contasSelectItems" value="#{baixaLancamentoBB.contasCorrentes}"  />   
												</h:selectOneMenu>
											</div>
											<div>
												<h:outputLabel styleClass="desc" value="Forma Recebimento"></h:outputLabel>
												<h:selectOneMenu id="idFormaRecebimento" style="width: 200px;" onchange="javascript:trocaDivFormaRecebimento();"
													value="#{baixaLancamentoBB.idFormaRecebimento}"> 
														  <f:selectItems id="formasSelectItems" value="#{baixaLancamentoBB.formas}"  />   
												</h:selectOneMenu>
											</div>
											<div>
												<h:outputLabel styleClass="desc" value="Número Documento"></h:outputLabel>
												<h:inputText styleClass="field text" id="numeroDocumentoItemBaixa" maxlength="30" size="30"
													value="#{baixaLancamentoBB.numeroDocumentoItemBaixa}" dir="ltr" onkeypress="return SoNumero(event);">
													<f:validateLength maximum="30" />
												</h:inputText>
											</div>	
										</li>
										<li class="normal">
											<div id="divForma1" style="display:none;">									
												<ul>
													<li class="normal">
														<div>
															<h:outputLabel styleClass="desc" value="Banco"></h:outputLabel>
															<h:selectOneMenu id="idBanco" style="width: 130px;" value="#{baixaLancamentoBB.idBanco}"> 
																  <f:selectItems id="bancosSelectItems" value="#{baixaLancamentoBB.bancos}"  />   
															</h:selectOneMenu>
														</div>
														<div>
															<h:outputLabel styleClass="desc" value="Agência"></h:outputLabel>
															<h:inputText styleClass="field text" id="agencia" maxlength="6" size="6"
																value="#{baixaLancamentoBB.agencia}" dir="ltr" onkeypress="return SoNumero(event);">
																<f:validateLength maximum="6" />
															</h:inputText>
														</div>
														<div>
															<h:outputLabel styleClass="desc" value="Num. Conta"></h:outputLabel>
															<h:inputText styleClass="field text" id="numeroConta" maxlength="8" size="8"
																value="#{baixaLancamentoBB.numeroConta}" dir="ltr" onkeypress="return SoNumero(event);">
																<f:validateLength maximum="8" />
															</h:inputText>
														</div>
														<div>
															<h:outputLabel styleClass="desc" value="Num. Cheque"></h:outputLabel>
															<h:inputText styleClass="field text" id="numeroCheque" maxlength="8" size="8"
																value="#{baixaLancamentoBB.numeroCheque}" dir="ltr" onkeypress="return SoNumero(event);">
																<f:validateLength maximum="8" />
															</h:inputText>
														</div>													
														<div>
															<h:outputLabel styleClass="desc" value="Data Cheque"></h:outputLabel>
															<h:inputText readonly="false" maxlength="10" size="10" styleClass="field text data"
																value="#{baixaLancamentoBB.dataCheque}" onkeypress="return MascaraData(this,event);" 
																onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }"
																id="dataCheque">
																<f:convertDateTime timeZone="GMT-3"/>
															</h:inputText>
														</div>	
														<div>
															<h:outputLabel styleClass="desc" value="Tipo Cliente"></h:outputLabel>
															<h:selectOneRadio  styleClass="field select tipopessoa" id="idTipoPessoa" 
																value="#{baixaLancamentoBB.idTipoPessoa}" layout="lineDirection" required="false">
																<f:selectItems id="tipoPessoaLista" value="#{baixaLancamentoBB.listaTipoPessoa}"/>
															</h:selectOneRadio>
														</div>
														<div>
															<h:outputLabel styleClass="desc" value="CPF/CNPJ"></h:outputLabel>
															<h:inputText styleClass="field text tipocpfcnpj" id="cpfCnpj" maxlength="18" size="18"
																value="#{baixaLancamentoBB.cpfCnpj}" dir="ltr" onkeypress="return SoNumero(event);">
																<f:validateLength maximum="18" />
															</h:inputText>
														</div>											
													</li>
												</ul>
											</div>	
										</li>
										<li class="normal">
											<div>
												<h:outputLabel styleClass="desc" value="Valor"></h:outputLabel>
												<h:inputText styleClass="field text" id="valorItem" maxlength="10" size="10" readonly="false" onblur="atualizaValorTotalItem();"
													value="#{baixaLancamentoBB.valorItem}" dir="rtl" onkeydown="return(BackSpace(this,event));"  onkeypress="return(MascaraMoeda(this,'','.',event));" >
													<f:validateLength maximum="10" />
													<f:validateDoubleRange  minimum="0.00" maximum="9999999.99"/>
													<f:validator validatorId="BigDecimalValidator"/>
												</h:inputText>
											</div>
											<div>
												<h:outputLabel styleClass="desc" value="Desconto"></h:outputLabel>
												<h:inputText styleClass="field text" id="valorDesconto" maxlength="10" size="10" readonly="false" onblur="atualizaValorTotalItem();"
													value="#{baixaLancamentoBB.valorDesconto}" dir="rtl" onkeydown="return(BackSpace(this,event));"  onkeypress="return(MascaraMoeda(this,'','.',event));" >
													<f:validateLength maximum="10" />
													<f:validateDoubleRange  minimum="0.00" maximum="9999999.99"/>
													<f:validator validatorId="BigDecimalValidator"/>
												</h:inputText>
											</div>
											<div>
												<h:outputLabel styleClass="desc" value="Acréscimo"></h:outputLabel>
												<h:inputText styleClass="field text" id="valorAcrescimo" maxlength="10" size="10" readonly="false" onblur="atualizaValorTotalItem();"
													value="#{baixaLancamentoBB.valorAcrescimo}" dir="rtl" onkeydown="return(BackSpace(this,event));"  onkeypress="return(MascaraMoeda(this,'','.',event));" >
													<f:validateLength maximum="10" />
													<f:validateDoubleRange  minimum="0.00" maximum="9999999.99"/>
													<f:validator validatorId="BigDecimalValidator"/>
												</h:inputText>
											</div>
											<div>
												<h:outputLabel styleClass="desc" value="Valor Item"></h:outputLabel>
												<h:inputText styleClass="field text" id="valorTotalItem" maxlength="10" size="10" disabled="true"
													value="#{baixaLancamentoBB.valorTotalItem}" dir="rtl" onkeydown="return(BackSpace(this,event));"  onkeypress="return(MascaraMoeda(this,'','.',event));" >
													<f:validateLength maximum="10" />
													<f:validateDoubleRange  minimum="0.00" maximum="9999999.99"/>
													<f:validator validatorId="BigDecimalValidator"/>
												</h:inputText>
											</div>
											<div style="vertical-align: middle;">
												<h:commandButton image="/images/adicionar.png" onfocus="atualizaValorTotalItem();" alt="Inserir" styleClass="btTxt" id="botaoInserirItem" action="#{baixaLancamentoBB.inserirItemBaixaLancamento}" value="Inserir"></h:commandButton>
											</div>
										</li>	
										<li class="normal">
											<div class="listagemSimples" style="overflow:auto; height:230px;">
												<t:dataTable value="#{baixaLancamentoBB.itensBaixaLancamento}"
													var="baixaLancamento" rowClasses="rowA,rowB" width="100%" renderedIfEmpty="false">
													<h:column>
														<f:facet name="header">
															<h:outputText value="Número" /> 
														</f:facet>
														<h:outputText style="align: center;" value="#{baixaLancamento.pk.id}" /> 
													</h:column>
													<h:column>
														<f:facet name="header">
															<h:outputText value="Cta Corrente" />
														</f:facet>
														<h:outputText style="align: left;" value="#{baixaLancamento.contaCorrente.id} - #{baixaLancamento.contaCorrente.nome}" /> 
													</h:column>
													<h:column>
														<f:facet name="header">
															<h:outputText value="Forma" />
														</f:facet>
														<h:outputText style="align: left;" value="#{baixaLancamento.formaRecebimento.id} - #{baixaLancamento.formaRecebimento.descricao}" /> 
													</h:column>
													<h:column>
														<f:facet name="header">
															<h:outputText value="Detalhe" />
														</f:facet>
														<h:outputText style="align: right;" value="#{baixaLancamento.detalheFormaRecebimento}" /> 
													</h:column>
													<h:column>
														<f:facet name="header">
															<h:outputText value="Valor" />
														</f:facet>
														<h:outputText style="align: left;" value="#{baixaLancamento.valor}"> 
															<f:convertNumber currencySymbol="R$" locale="pt-BR" pattern="R$ ###,##0.00"/>
														</h:outputText>
													</h:column>
													<h:column>
														<f:facet name="header">
															<h:outputText value="Vl Desc." />
														</f:facet>
														<h:outputText style="align: left;" value="#{baixaLancamento.valorDesconto}" > 
															<f:convertNumber currencySymbol="R$" locale="pt-BR" pattern="R$ ###,##0.00"/>
														</h:outputText>
													</h:column>
													<h:column>
														<f:facet name="header">
															<h:outputText value="Vl Acrésc." />
														</f:facet>
														<h:outputText style="align: left;" value="#{baixaLancamento.valorAcrescimo}" > 
															<f:convertNumber currencySymbol="R$" locale="pt-BR" pattern="R$ ###,##0.00"/>
														</h:outputText>
													</h:column>
													<h:column>
														<f:facet name="header">
															<h:outputText value="Vl Item" />
														</f:facet>
														<h:outputText style="align: left;" value="#{baixaLancamento.valorTotalItem}" > 
															<f:convertNumber currencySymbol="R$" locale="pt-BR" pattern="R$ ###,##0.00"/>
														</h:outputText>
													</h:column>
													<h:column rendered="#{baixaLancamentoBB.idSituacao == 'A' or baixaLancamentoBB.idSituacao == 'P'}">
														<f:facet name="header">
															<h:outputText style="align: center;" value="Ação" />
														</f:facet>										
														<h:commandButton image="/images/excluir.png" alt="Excluir" actionListener="#{baixaLancamentoBB.removerItemBaixaLancamento}">
															<f:param id="idExcluirItemBaixaLancamento" name="idExcluirItemBaixaLancamento" value="#{baixaLancamento.pk.id}" />
														</h:commandButton>
													</h:column>													
												</t:dataTable>																
											</div>
										</li>
										<li class="direita">
											<div>
												<h:outputLabel styleClass="desc" value="Valor Total Pago"></h:outputLabel>
												<h:outputText dir="rtl" style="font-size: 14px; font-weight: bold; color: red;" id="valorTotalPago" value="#{baixaLancamentoBB.valorTotalPago}">
													<f:convertNumber currencySymbol="R$" locale="pt-BR" pattern="R$ ###,##0.00"/>
												</h:outputText>
											</div>
										</li>
									</ul>
								</li>
								<li class="buttons">
									<h:commandButton immediate="true" styleClass="btTxt" id="botaoVoltar" action="#{baixaLancamentoBB.voltarConsulta}" value="Voltar"></h:commandButton>
									<h:commandButton disabled="#{baixaLancamentoBB.idSituacao == 'C' or baixaLancamentoBB.idSituacao == 'F'}" styleClass="btTxt" id="botaoBaixar" action="#{baixaLancamentoBB.baixarLancamento}" value="Baixar Lançamento"></h:commandButton>
									<h:commandButton disabled="#{baixaLancamentoBB.idSituacao == 'C'}" styleClass="btTxt" id="botaoCancelar" action="#{baixaLancamentoBB.cancelarBaixaLancamento}" value="Cancelar Baixa Lançamento"></h:commandButton>
								</li>
							</ul>
						</div>
						<div class="clear"></div>
					</div>
		</h:form>
	  </body>	
	</f:view>
</html>
		