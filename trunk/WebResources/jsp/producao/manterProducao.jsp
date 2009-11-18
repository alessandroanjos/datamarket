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

			<script language="javascript">

            var formId; // referência ao formulário principal

            var winId;  // referência à janela popup

            // Esta função faz a chamada da janela popup.

            //

            function showPopUp(action, form, target) {
				 if(getId("frmInserirProducao:idProduto").value == ""){        		
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
						<strong> <h:outputText value="#{msgs.manterProducao}"></h:outputText>
						</strong>
					</div>
				</div>
				<div id="content">
					<div id="primarioContentContainerInternas">
						<h:form id="frmInserirProducao" binding="#{producaoBB.init}">
							<ul>
								<li class="normal">
									<div>
										<%@ include file="/jsp/mensagem_erro.jsp"%>
										<!--  h  messages errorClass="msgSistemaErro"
									infoClass="msgSistemaSucesso" globalOnly="true"
									showDetail="true" /> -->
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Lote"></h:outputLabel>
										<h:outputLabel styleClass="textoGrande2"
											value="#{producaoBB.producao.lote}"></h:outputLabel>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Data Fabricação"></h:outputLabel>
										<h:outputLabel styleClass="textoGrande2"
											value="#{producaoBB.producao.dataFabricacao}"></h:outputLabel>

									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Loja"></h:outputLabel>
										<h:outputLabel styleClass="textoGrande2"
											value="#{producaoBB.producao.estoque.pk.loja.nome}"></h:outputLabel>
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Estoque"></h:outputLabel>
										<h:outputLabel styleClass="textoGrande2"
											value="#{producaoBB.producao.estoque.descricao}"></h:outputLabel>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Código Produto"></h:outputLabel>
										<h:outputLabel styleClass="textoGrande2"
											value="#{producaoBB.producao.produto.id}"></h:outputLabel>
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Descrição"></h:outputLabel>
										<h:outputLabel styleClass="textoGrande2"
											value="#{producaoBB.producao.produto.descricaoCompleta}"></h:outputLabel>
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Quantidade*"></h:outputLabel>
										<h:inputText styleClass="field text" id="quantidade"
											maxlength="7" size="7" value="#{producaoBB.quantidade}"
											required="false"
											onkeydown="return(BackSpaceQTD(this,event));"
											onkeypress="return(MascaraQTD(this,'','.',event));">
											<f:validateLength maximum="7" />
											<f:validateDoubleRange minimum="0.00" maximum="9999.999" />
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
										<h:outputLabel styleClass="desc" value="Preço Venda Anterior"></h:outputLabel>
										<h:outputLabel styleClass="textoGrande2"
											value="#{producaoBB.producao.precoVendaAnterior}"></h:outputLabel>										
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Valor de Custo"></h:outputLabel>
										<h:outputLabel styleClass="textoGrande2"
											value="#{producaoBB.producao.valorUnitario}"></h:outputLabel>										
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="MarkUp"></h:outputLabel>
										<h:outputLabel styleClass="textoGrande2"
											value="#{producaoBB.producao.markUp}"></h:outputLabel>										
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Preço Venda"></h:outputLabel>
										<h:outputLabel styleClass="textoGrande2"
											value="#{producaoBB.producao.precoVenda}"></h:outputLabel>										
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
								</t:dataTable>	
							</div>
							
							
							</fieldset>

							<ul>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" immediate="true" id="botaoVoltar" 
										action="#{producaoBB.voltarConsulta}" value="Voltar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoExcluir"
										action="#{producaoBB.excluir}" value="Excluir"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoAlterar"
										action="#{producaoBB.alterar}" value="Alterar"></h:commandButton>
								</li>
							</ul>
						</h:form>
					</div>
					<div class="clear"></div>
				</div>
		</body>
	</f:view>
</html>
