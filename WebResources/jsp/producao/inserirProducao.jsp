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
				 if(getId("frmInserirProducao:idProduto").value == ""){        		
  				    getId("frmInserirProducao:descricao").value = "";
	                formId=form;
					if (winId != null) {
					    winId.close();
					}
				    var formaux = document.forms[form];
				 
                    features="height=500,width=600,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes,dependent=yes";             
				    winId=window.open('/EnterpriseServer/jsp/popup/PopUpProdutos.faces?acaoPopUp=init&enquadramento=3','list',features);

				    // Formulário escondido
                    hform=document.forms[form];                
                 }else{
                	document.forms["frmInserirProducao"].action = "/EnterpriseServer/jsp/producao/inserirProducao.faces?acaoLocal=pesquisarProdutos&codigoProduto="+getId("frmInserirProducao:idProduto").value;
					document.forms["frmInserirProducao"].submit();
                }
            }

            // Esta função é chamada pela janela popup 

            // quando um usuário clica em um item na listagem.

            // O item selecionado é copiado para um campo de texto

            // no formulário principal.

             function setAtributo(idProduto,descricao) {
                             var form = document.forms[formId];   
                             form[formId+":idProduto"].value=idProduto; 
                             form[formId+":descricao"].value=descricao;
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
					<h:outputText value="#{msgs.inserirProducao}"></h:outputText>
				</strong>
			</div>				
		</div>	
		<div id="content">
			<div id="primarioContentContainerInternas" >
				<h:form id="frmInserirProducao" binding="#{producaoBB.init}">
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
								<h:outputLabel styleClass="desc" value="Lote*"></h:outputLabel>
								<h:inputText readonly="false" styleClass="field text"
									maxlength="9" size="10" 
									value="#{producaoBB.lote}" disabled="true" onfocus="this.select();" onclick="this.select();" onkeypress="return SoNumero(event);"
									maxlength="9" 
									id="lote">								    
								</h:inputText>	
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Data Fabricação*"></h:outputLabel>
								<h:inputText readonly="false" styleClass="field text data"
									maxlength="10" size="10" 
									value="#{producaoBB.fabricacao}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }"
									id="fabricacao">
								    <f:convertDateTime timeZone="GMT-3"/>	 
								</h:inputText>	
							</div>
						</li>		
						<li class="normal">
							<div>
								<h:outputLabel styleClass="desc" value="Loja"></h:outputLabel>
								<h:selectOneMenu id="idLoja" style="width: 200px;" value="#{producaoBB.idLoja}" onchange="submit();" valueChangeListener="#{entradaProdutoBB.carregarEstoquesPorLoja}"> 
									<f:selectItems id="lojasSelectItems" value="#{producaoBB.lojas}" />
								</h:selectOneMenu>
							</div>		
							<div>
								<h:outputLabel styleClass="desc" value="Estoque"></h:outputLabel>
								<h:selectOneMenu id="idEstoque" style="width: 200px;" value="#{producaoBB.idEstoque}"> 
									<f:selectItems id="estoquesSelectItems" value="#{producaoBB.estoques}" />   
								</h:selectOneMenu>
							</div>	
						</li>	
											
						<li class="normal">
							<div>
								<h:outputLabel styleClass="desc" value="Código Produto*"></h:outputLabel>
								<h:inputText styleClass="field text" id="idProduto" onfocus="this.select();" onclick="this.select();" onkeypress="return SoNumero(event);"
									maxlength="9" value="#{producaoBB.idProduto}" size="9" required="false">
									<f:validateLength maximum="9" />
									<f:validator validatorId="LongValidator" />
								</h:inputText>
								<h:commandButton  image="/images/pesquisa.png" alt="Pesquisar Produto" styleClass="btTxt" id="botaoConsultarProduto"
									onmousedown="showPopUp(this,'frmInserirProducao','find')"
									onclick="return false" value="Consultar Produto ">
								</h:commandButton>
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Descrição*"></h:outputLabel>
								<h:inputText styleClass="field text" id="descricao" 
									maxlength="50" size="50" value="#{producaoBB.descricao}">
									<f:validateLength maximum="50" />
								</h:inputText>
							</div>
							
							<div>
								<h:outputLabel styleClass="desc" value="Quantidade*"></h:outputLabel>
								<h:inputText styleClass="field text" id="quantidade" maxlength="7"
									size="7" value="#{producaoBB.quantidade}" required="false"
									onkeydown="return(BackSpaceQTD(this,event));"  onkeypress="return(MascaraQTD(this,'','.',event));" >
									<f:validateLength maximum="7" />
									<f:validateDoubleRange minimum="0.000" maximum="9999.999" />
									<f:validator validatorId="BigDecimalValidator" />
								</h:inputText>																
							</div>
						</li>
						</ul>
						<fieldset>	
						<legend>Precificação</legend>
						<ul>
						<li class="normal">
							<div>
								<h:outputLabel styleClass="desc" value="Preço Venda Atual"></h:outputLabel>
								<h:inputText styleClass="field text" id="precoVendaAtual" maxlength="7"
									size="7" value="#{producaoBB.precoVendaAtual}" disabled="true">
									<f:validator validatorId="BigDecimalValidator" />									
								</h:inputText>																
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Valor de Custo"></h:outputLabel>
								<h:inputText styleClass="field text" id="valorUnitario" maxlength="7"
									size="7" value="#{producaoBB.valorUnitario}" disabled="true">
									<f:validator validatorId="BigDecimalValidator" />									
								</h:inputText>																
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="MarkUp"></h:outputLabel>
								<h:inputText styleClass="field text" id="markUp"
									maxlength="10" size="10" value="#{producaoBB.markUp}"
									 dir="rtl" disabled="true">
									<f:validator validatorId="BigDecimalValidator"/>
								</h:inputText>
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Preço Venda"></h:outputLabel>
								<h:inputText styleClass="field text" id="precoVenda"
									maxlength="10" size="10" value="#{producaoBB.precoVenda}"
									 dir="rtl" disabled="true">
									<f:validator validatorId="BigDecimalValidator"/>
								</h:inputText>
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Ajusta Preço"></h:outputLabel>
								<h:selectOneRadio  styleClass="field radio ehvendedor" id="idAjustarPrecoVenda" 
									value="#{producaoBB.idAjustarPrecoVenda}" layout="lineDirection"  rendered="true">
								    <f:selectItems id="ajustarPrecoVenda" value="#{producaoBB.ajustarPrecoVenda}" />
								</h:selectOneRadio>											
							</div>
						</li>	
					</ul>
					</fieldset>	
					<fieldset>	
					<legend>Composição do produto</legend>
					<div>
						<t:dataTable id="composicao" value="#{producaoBB.produto.composicao}"
									var="comp" rowClasses="rowA,rowB" width="90%" renderedIfEmpty="false">
									<h:column>
										<f:facet name="header">
											<h:outputText value="Produto" /> 
										</f:facet>
										<h:outputText value="#{comp.pk.produto.descricaoCompleta}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Quantidade" />
										</f:facet>
										<h:outputText value="#{comp.quantidade}" /> 																				
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Custo" />
										</f:facet>
										<h:outputText value="#{comp.pk.produto.precoCompra}" /> 																				
									</h:column>
						</t:dataTable>	
					</div>
					
					
					</fieldset>
					
					
					<ul>
						<li class="buttons">
							<h:commandButton styleClass="btTxt" action="#{producaoBB.resetBB}" id="botaoLimpar" value="Limpar"></h:commandButton>
							<h:commandButton styleClass="btTxt" id="botaoInserir" action="#{producaoBB.inserir}" value="Inserir"></h:commandButton>
						</li>
					</ul>
				</h:form>
				</div>
				<div class="clear"></div>
			</div>
	 </body>			
	</f:view>
</html>
