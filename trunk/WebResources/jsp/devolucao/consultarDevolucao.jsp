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
			window.onload = function(){ inicializar() };
			function inicializar() {
				$("input.tipopessoa").each(function(i){
					$(this).click(function() {mostraCampos(this.value)});
				});
				if ($('[name=frmConsultarDevolucao:idTipoPessoa]:checked').val() != "undefined") {
					mostraCampos($('[name=frmConsultarDevolucao:idTipoPessoa]:checked').val());
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
			
			var formId; // referência ao formulário principal
            var winId;  // referência à janela popup
            // Esta função faz a chamada da janela popup.
            //
            function showPopUp(action, form, target) {
            
             if(getId("frmConsultarDevolucao:cpfCnpj").value == ""){        		
                    formId=form;
        			if (winId != null) {
        			    winId.close();
        			}
                    features="height=500,width=600,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes,dependent=yes";             
        			winId=window.open('/EnterpriseServer/jsp/popup/PopUpClientes.faces','list',features);
				    // Formulário escondido
                    hform=document.forms[form];                
				}else{
				    var cpfCnpjTmp = getId("frmConsultarDevolucao:cpfCnpj").value;
					document.forms["frmConsultarDevolucao"].action = "/EnterpriseServer/jsp/devolucao/consultarDevolucao.faces?acaoLocal=pesquisarClientes&cpfCnpj="+cpfCnpjTmp;
					document.forms["frmConsultarDevolucao"].submit();                  	
                }
            }

            // Esta função é chamada pela janela popup 
            // quando um usuário clica em um item na listagem.
            // O item selecionado é copiado para um campo de texto
            // no formulário principal.
            //
             function setAtributoClientes(cpfCnpj,nomeCliente, razaoSocial, tipoPessoa) {
                 var form = document.forms[formId];                             
                  
                 form[formId+":cpfCnpj"].value = cpfCnpj; 
                 
                 if(tipoPessoa == "F"){
                     form[formId+":nomeCliente"].value = nomeCliente;                               
                 }else if(tipoPessoa == "J"){
                     form[formId+":nomeCliente"].value = razaoSocial;  
                 }
                 
                 form[formId+":idTipoPessoa"].value = tipoPessoa; 
               
                 winId.close();
             }
		</script>
	</head>
	<body>
	<div id="outer">
		<div id="topoGeral">
			<div id="tituloPaginaGeral">
				<strong>
					<h:outputText value="#{msgs.consultarDevolucao}"></h:outputText>
				</strong>
			</div>				
		</div>		
		<h:form id="frmConsultarDevolucao" binding="#{devolucaoBB.init}">				
				<div id="content">				
						<div id="primarioContentContainer">
							<fieldset>
								<legend>Opções de filtro:</legend>
								<ul>								
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Loja"></h:outputLabel>
											<h:selectOneMenu id="idLoja" style="width: 200px;" value="#{devolucaoBB.idLoja}"> 
												<f:selectItems id="lojasSelectItems" value="#{devolucaoBB.lojas}" />   
											</h:selectOneMenu>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Número Devolução"></h:outputLabel>
											<h:inputText styleClass="field text ativo" id="idOperacaoDevolucao" maxlength="9" onkeypress="return SoNumero(event);"
												value="#{devolucaoBB.idOperacaoDevolucao}" size="10" required="false">
												<f:validateLength maximum="9" />
												<f:validator validatorId="LongValidator"/>
											</h:inputText>
										</div>	
										<br />															
										<br />
										<div>
											<h:outputLabel styleClass="desc" value="Data Início"></h:outputLabel>
											<h:inputText styleClass="field text" id="dataInicial" maxlength="10" size="10" required="false"
												value="#{devolucaoBB.dataInicial}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }">
												<f:convertDateTime timeZone="GMT-3"/>
											</h:inputText>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Data Fim"></h:outputLabel>
											<h:inputText styleClass="field text" id="dataFinal" maxlength="10" size="10" required="false"
												value="#{devolucaoBB.dataFinal}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }">
												<f:convertDateTime timeZone="GMT-3"/>
											</h:inputText>										
										</div>
										<div>	
											<h:outputLabel styleClass="desc" value="Status"></h:outputLabel>
											<h:selectOneMenu id="idSituacao" style="width: 200px;" value="#{devolucaoBB.idSituacao}"> 
												<f:selectItems id="situacaoSelectItems" value="#{devolucaoBB.listaSituacao}" />   
											</h:selectOneMenu>
										</div>
										<br />
										<br />
										<div>
											<h:outputLabel styleClass="desc" value="Tipo Pessoa"></h:outputLabel>
											<h:selectOneRadio  styleClass="field select tipopessoa" id="idTipoPessoa" 
												value="#{devolucaoBB.idTipoPessoa}" layout="lineDirection" required="false">
												<f:selectItems id="tipoPessoaLista" value="#{devolucaoBB.listaTipoPessoa}"/>
											</h:selectOneRadio>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="CPF/CNPJ"></h:outputLabel>
											<h:inputText styleClass="field text tipocpfcnpj" id="cpfCnpj" maxlength="18" size="18" 
											value="#{devolucaoBB.cpfCnpj}" required="false" onkeypress="return SoNumero(event);">
												<f:validateLength minimum="11" maximum="18" />
											</h:inputText>	
										
											<h:commandButton image="/images/pesquisa.png" alt="Pesquisar Cliente" styleClass="btTxt" id="botaoConsultarCliente"
												onmousedown="showPopUp(this,'frmConsultarDevolucao','find')" value="">
											</h:commandButton>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Nome Cliente/Razão Social"></h:outputLabel>
											<h:inputText styleClass="field text" id="nomeCliente" maxlength="50" size="50" required="false"
												value="#{devolucaoBB.nomeCliente}" >
											</h:inputText>
										</div>
									</li>
								</ul>
							</fieldset>	
							<div class="listagem">
								<t:dataTable id="devolucoes" value="#{devolucaoBB.devolucoes}"
									var="devolucao" rowClasses="rowA,rowB" width="90%" renderedIfEmpty="false">
									<h:column>
										<f:facet name="header">
											<h:outputText value="Número Devolução" /> 
										</f:facet>
										<h:commandLink value="#{devolucao.pk.id}" action="#{devolucaoBB.consultar}">
											<f:param name="operacao_loja" value="#{devolucao.pk.loja}"/>						
											<f:param name="operacao_id" value="#{devolucao.pk.id}"/>
										</h:commandLink>										
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Loja" />
										</f:facet>
										<h:outputText value="#{devolucao.pk.loja}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Data Devolução" />
										</f:facet>
										<h:outputText value="#{devolucao.data}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Valor" />
										</f:facet>
										<h:outputText value="#{devolucao.valor}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Situação" />
										</f:facet>
										<h:outputText value="#{devolucao.descricaoStatus}" /> 
									</h:column>									
								</t:dataTable>	
								<div>
									<%@ include file="/jsp/mensagem_erro.jsp"%> <!--  h  messages rendered="#{not devolucaoBB.existeRegistros}" errorClass="msgSistemaErro" infoClass="msgSistemaSucesso" globalOnly="true" showDetail="true" /> -->
								</div>
							</div>
							<ul>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" action="#{devolucaoBB.resetBB}" id="botaoLimpar" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoConsultar" action="#{devolucaoBB.consultar}" value="Consultar"></h:commandButton>
								</li>						
							</ul>
						</div>
						<div class="clear"></div>
					</div>
		</h:form>
	  </body>	
	</f:view>
</html>