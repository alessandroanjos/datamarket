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
					<h:outputText value="#{msgs.consultarProducao}"></h:outputText>
				</strong>
			</div>				
		</div>	
		<div id="content">
			<div id="primarioContentContainerInternas" >
				<h:form id="frmInserirProducao" binding="#{producaoBB.init}">
					<fieldset>
					<ul>
						<li class="normal">
							<div>
								<%@ include file="/jsp/mensagem_erro.jsp"%> <!--  h  messages errorClass="msgSistemaErro"
									infoClass="msgSistemaSucesso" globalOnly="true"
									showDetail="true" /> -->
							</div>
						</li>

						<legend>Opções de filtro:</legend>
						<li class="normal">	
							<div>
								<h:outputLabel styleClass="desc" value="Lote"></h:outputLabel>
								<h:inputText readonly="false" styleClass="field text"
									maxlength="9" size="10" 
									value="#{producaoBB.loteConsulta}" onfocus="this.select();" onclick="this.select();" onkeypress="return SoNumero(event);"
									maxlength="9" 
									id="loteConsulta">
								</h:inputText>	
							</div>
						</li>
						<li class="normal">	
							<div>
								<h:outputLabel styleClass="desc" value="Fabricação Inicial*"></h:outputLabel>
								<h:inputText readonly="false" styleClass="field text data"
									maxlength="10" size="10" 
									value="#{producaoBB.fabricacaoIni}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }"
									id="fabricacaoIni">
								    <f:convertDateTime timeZone="GMT-3"/>	 
								</h:inputText>	
							</div>
							<div>
								<h:outputLabel styleClass="desc" value=" Final*"></h:outputLabel>
								<h:inputText readonly="false" styleClass="field text data"
									maxlength="10" size="10" 
									value="#{producaoBB.fabricacaoFim}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }"
									id="fabricacaoFim">
								    <f:convertDateTime timeZone="GMT-3"/>	 
								</h:inputText>								
							</div>
						</li>								
						<li class="normal">
							<div>
								<h:outputLabel styleClass="desc" value="Código Produto"></h:outputLabel>
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
								<h:outputLabel styleClass="desc" value="Descrição"></h:outputLabel>
								<h:inputText styleClass="field text" id="descricao" 
									maxlength="50" size="50" value="#{producaoBB.descricao}">
									<f:validateLength maximum="50" />
								</h:inputText>
							</div>
							
						</li>
					</ul>
					</fieldset>
					<div class="listagem">
								<t:dataTable id="producoes" value="#{producaoBB.producoes}"
									var="producao" rowClasses="rowA,rowB" width="90%" renderedIfEmpty="false">
									<h:column>
										<f:facet name="header">
											<h:outputText value="lote" /> 
										</f:facet>
										<h:outputText value="#{producao.lote}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Fabricação" />
										</f:facet>
										<h:outputText value="#{producao.dataFabricacao}" /> 
										
										
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Produto" /> 
										</f:facet>
										<h:commandLink value="#{producao.produto.descricaoCompleta}" action="#{producaoBB.manter}">
											<f:param name="producao" value="#{producao.id}"/>						
										</h:commandLink>
										
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Loja" /> 
										</f:facet>
										<h:outputText value="#{producao.estoque.pk.loja.nome}"/> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Estoque" /> 
										</f:facet>
										<h:outputText value="#{producao.estoque.descricao}"/> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="quantidade" /> 
										</f:facet>
										<h:outputText value="#{producao.quantidade}"/> 
									</h:column>
								</t:dataTable>	
										<div>
											<%@ include file="/jsp/mensagem_erro.jsp"%>
										</div>
							</div>
					<ul>
						<li class="buttons">
							<h:commandButton styleClass="btTxt" action="#{producaoBB.resetBB}" id="botaoLimpar" value="Limpar"></h:commandButton>
							<h:commandButton styleClass="btTxt" id="botaoConsultar" action="#{producaoBB.consultar}" value="Consultar"></h:commandButton>
						</li>
					</ul>
				</h:form>
				</div>
				<div class="clear"></div>
			</div>
	 </body>			
	</f:view>
</html>
