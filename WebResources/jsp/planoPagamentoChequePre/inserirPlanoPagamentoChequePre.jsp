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

		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
		
		<script type="text/javascript" src="/EnterpriseServer/js/jquery.js"></script>
		<script type="text/javascript" src="/EnterpriseServer/js/global.js"></script>
		<script type="text/javascript" src="/EnterpriseServer/js/funcoes.js"></script>
		<t:stylesheet path="/EnterpriseServer/css/default.css"></t:stylesheet>
		<t:stylesheet path="/EnterpriseServer/css/form.css"></t:stylesheet>	
	</head>
				<f:subview id="subTopo" rendered="true">
					<jsp:include page="/jsp/topo.jsp?tituloPagina=#{msgs.inserirPlanoPagamentoChequePre}&user=#{loginBB.usuarioLogado.nome}"></jsp:include>	
				</f:subview>	
				<div id="content">
					<div id="tabMenu">
						<ul>
							<li id="tabMenuDiv0" class="current" onclick="selecionaMenuTab(this.id)"><span><a href="#">Geral</a></span></li>
							<li id="tabMenuDiv1" onclick="selecionaMenuTab(this.id)"><span><a href="#">Parcelas</a></span></li>
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
<!-- xxxxxxxxxxxxxxx -->					
		<h:form id="frmInserirPlanoPagamentoChequePre">
<!-- xxxxxxxxxxxxxxx -->					
						<div id="tabDiv0">
							<ul>								
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Código*"></h:outputLabel>
										<h:inputText styleClass="field text ativo" id="id" maxlength="2"
											value="#{planoPagamentoChequePreBB.id}" size="3" required="false">
											<f:validateLength maximum="2" />
											<f:validator validatorId="LongValidator"/>
										</h:inputText>
										<h:message for="id" styleClass="msgErro"/>
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Descrição*"></h:outputLabel>
										<h:inputText styleClass="field text" id="descricao" maxlength="50" size="50" required="false"
											value="#{planoPagamentoChequePreBB.descricao}">
											<f:validateLength maximum="50" />
										</h:inputText>
										<h:message for="descricao" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Forma de Receb. Associada*"></h:outputLabel>
										<h:selectOneMenu id="idForma" style="width: 180px;" 
											value="#{planoPagamentoChequePreBB.idForma}"> 
												  <f:selectItems id="formaSelectItems" 
												  value="#{planoPagamentoChequePreBB.formas}"  />   
										</h:selectOneMenu>
										<h:message for="idForma" styleClass="msgErro"/>		
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Valor Mínimo*"></h:outputLabel>
										<h:inputText styleClass="field text" id="valorMinimo" maxlength="10" size="10"
											value="#{planoPagamentoChequePreBB.valorMinimo}" dir="rtl" required="false" onkeypress="Formata('frmInserirPlanoPagamentoChequePre:valorMinimo',9,2);">
											<f:validateLength maximum="10" />
											<f:validateDoubleRange  minimum="0.01" maximum="9999999.99"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
										<h:message for="valorMinimo" styleClass="msgErro"/>
									</div>								
									<div>
										<h:outputLabel styleClass="desc" value="Valor Máximo*"></h:outputLabel>
										<h:inputText styleClass="field text" id="valorMaximo" maxlength="10" size="10"
											value="#{planoPagamentoChequePreBB.valorMaximo}" dir="rtl" required="false" onkeypress="Formata('frmInserirPlanoPagamentoChequePre:valorMaximo',9,2);">
											<f:validateLength maximum="10" />
											<f:validateDoubleRange  minimum="0.01" maximum="9999999.99"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
										<h:message for="valorMaximo" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Perc. Desconto*"></h:outputLabel>
										<h:inputText styleClass="field text" id="percentualDesconto" maxlength="5" size="5"
											value="#{planoPagamentoChequePreBB.percDesconto}" dir="rtl" required="false" onkeypress="Formata('frmInserirPlanoPagamentoChequePre:percentualDesconto',5,2);">
											<f:validateLength maximum="5" />
											<f:validateDoubleRange  minimum="0.00" maximum="100.00"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
										<h:message for="percentualDesconto" styleClass="msgErro"/>
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Perc. Acréscimo*"></h:outputLabel>
										<h:inputText styleClass="field text" id="percentualAcrescimo" maxlength="5" size="5"
											value="#{planoPagamentoChequePreBB.percAcrescimo}" dir="rtl" required="false" onkeypress="Formata('frmInserirPlanoPagamentoChequePre:percentualAcrescimo',5,2);">
											<f:validateLength maximum="5" />
											<f:validateDoubleRange  minimum="0.00" maximum="100.00"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
										<h:message for="percentualAcrescimo" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Inicio Validade*"></h:outputLabel>
										<h:inputText styleClass="field text" id="dataInicioValidade" maxlength="10" size="10"
											value="#{planoPagamentoChequePreBB.dataInicioValidade}" onkeypress="return SoNumero();" onkeydown="FormataData('frmInserirPlanoPagamentoChequePre:dataInicioValidade');">
											
										</h:inputText>
										<h:message for="dataInicioValidade" styleClass="msgErro"/>
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Final Validade*"></h:outputLabel>
										<h:inputText styleClass="field text" id="dataFimValidade" maxlength="10" size="10"
											value="#{planoPagamentoChequePreBB.dataFimValidade}" onkeypress="return SoNumero();" onkeydown="FormataData('frmInserirPlanoPagamentoChequePre:dataFimValidade');">
										</h:inputText>
										<h:message for="dataFimValidade" styleClass="msgErro"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Situação*"></h:outputLabel>
										<h:selectOneRadio  styleClass="field select" id="status" 
											value="#{planoPagamentoChequePreBB.status}" layout="lineDirection">
										    <f:selectItems id="situacao" value="#{planoPagamentoChequePreBB.situacaoItens}" />
										</h:selectOneRadio>
										<h:message for="status" styleClass="msgErro"/>
									</div>								
								</li>
							</ul>
						</div>
						<div id="tabDiv1" style="display:none;">
							<ul>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Percentual de Entrada*"></h:outputLabel>
										<h:inputText styleClass="field text" id="percentualEntrada" maxlength="5" size="5"
											value="#{planoPagamentoChequePreBB.percentagemEntrada}" dir="rtl" required="false" onkeypress="Formata('frmInserirPlanoPagamentoChequePre:percentualEntrada',5,2);">
											<f:validateLength maximum="5" />
											<f:validateDoubleRange  minimum="0.00" maximum="100.00"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
										<h:message for="percentualEntrada" styleClass="msgErro"/>
									</div>
								</li>
							</ul>
							<ul>
								<li class="normal">							
									<div id="primarioContentContainerInternas">
										<fieldset>
											<legend>Parcelas</legend>
											<ul>
												<li class="normal">
													<div>
														<h:outputLabel styleClass="desc" value="Percentual da Parcela*"></h:outputLabel>
														<h:inputText styleClass="field text" id="percentualParcela" maxlength="6" size="6"
															value="#{planoPagamentoChequePreBB.percentagemParcela}" dir="rtl" required="false" onkeypress="return SoNumero();" onkeypress="Formata('frmInserirPlanoPagamentoChequePre:percentualParcela',5,2);">
															<f:validateLength maximum="6" />
															<f:validateDoubleRange  minimum="0.00" maximum="100.00"/>
															<f:validator validatorId="BigDecimalValidator"/>
														</h:inputText>
														<h:message for="percentualParcela" styleClass="msgErro"/>
													</div>
													<div>
														<h:outputLabel styleClass="desc" value="Quantidade de Dias*"></h:outputLabel>
														<h:inputText styleClass="field text ativo" id="quantidadeDiasParcela" dir="rtl" maxlength="3"
															value="#{planoPagamentoChequePreBB.quantidadeDias}" size="3" rendered="true" onkeypress="return SoNumero();">
															<f:validateLength maximum="3" />
															<f:validator validatorId="LongValidator"/>
														</h:inputText>
														<h:message for="quantidadeDiasParcela" styleClass="msgErro"/>
													</div>
													<div style="vertical-align: bottom;">
														<h:commandButton styleClass="btTxt" id="botaoInserirParcela" action="#{planoPagamentoChequePreBB.inserirParcela}" value="Inserir Parcela"></h:commandButton>
													</div>
													<br />

													<div class="listagemSimples">
														<t:dataTable value="#{planoPagamentoChequePreBB.parcelas}"
															var="parcela" rowClasses="rowA,rowB" width="100%">
															<h:column>
																<f:facet name="header">
																	<h:outputText value="Código" /> 
																</f:facet>
																<h:outputText value="#{parcela.pk.numeroEntrada}" /> 
															</h:column>
															<h:column>
																<f:facet name="header">
																	<h:outputText value="Percentual Parcela" />
																</f:facet>
																<h:outputText value="#{parcela.percentagemParcela}" /> 
															</h:column>
															<h:column>
																<f:facet name="header">
																	<h:outputText value="Quantidade Dias" />
																</f:facet>
																<h:outputText value="#{parcela.quantidadeDias}" /> 
															</h:column>	
															<h:column>
																<f:facet name="header">
																	<h:outputText value="Excluir" />
																</f:facet>
																<h:commandLink  value=""
																	action="#{planoPagamentoChequePreBB.excluirParcela}">
																	<h:commandButton type="button" image="/EnterpriseServer/images/tree/TortoiseDeleted.jpg"/>
																	
																	<f:param name="idExcluir" value="#{parcela.pk.numeroEntrada}" />
																</h:commandLink>
															</h:column>													
														</t:dataTable>																
													</div>
												</li>
											</ul>
										</fieldset>								
									</div>
								</li>								
							</ul>
						</div>						
						<ul>
							<li class="buttons">
								<h:commandButton styleClass="btTxt" immediate="true" id="botaoLimpar" type="reset" value="Limpar"></h:commandButton>
								<h:commandButton styleClass="btTxt" id="botaoInserir" action="#{planoPagamentoChequePreBB.inserir}" value="Inserir"></h:commandButton>
							</li>
						</ul>
<!-- xxxxxxxxxxxxxxx -->					
		</h:form>		
<!-- xxxxxxxxxxxxxxx -->					
          </div>
					<div class="clear"></div>
				</div>	
				<jsp:include page="/jsp/rodape.jsp"></jsp:include>			
	</f:view>
</html>
		
