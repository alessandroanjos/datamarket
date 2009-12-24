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
				if ($('[name=frmConsultarPedido:idTipoPessoa]:checked').val() != "undefined") {
					mostraCampos($('[name=frmConsultarPedido:idTipoPessoa]:checked').val());
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
			
			var formIdClientes; // referência ao formulário principal
            var winIdClientes;  // referência à janela popup
            // Esta função faz a chamada da janela popup.
            //
            function showPopUpClientes(action, form, target) {            
	            if(getId("frmConsultarPedido:cpfCnpjCliente").value == ""){        		
	                   formIdClientes = form;
	       			if (winIdClientes != null) {
	       			    winIdClientes.close();
	       			}
	                features = "height=500,width=600,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes,dependent=yes";             
	       			winIdClientes = window.open('/EnterpriseServer/jsp/popup/PopUpClientes.faces','list',features);
				    // Formulário escondido
	                hform=document.forms[form];                
				}else{
				    var cpfCnpjTmp = getId("frmConsultarPedido:cpfCnpjCliente").value;
					document.forms["frmConsultarPedido"].action = "/EnterpriseServer/jsp/pedido/consultarPedido.faces?acaoLocal=pesquisarClientes&cpfCnpj="+cpfCnpjTmp;
					document.forms["frmConsultarPedido"].submit();                  	
               }
           }
           
           // Esta função é chamada pela janela popup 
           // quando um usuário clica em um item na listagem.
           // O item selecionado é copiado para um campo de texto
           // no formulário principal.
           //
            function setAtributoClientes(cpfCnpj,nomeCliente, razaoSocial, tipoPessoa, endereco) {
                var form = document.forms[formIdClientes];                             
                 
                form[formIdClientes+":cpfCnpjCliente"].value = cpfCnpj; 
                
                if(tipoPessoa == "F"){
                    form[formIdClientes+":nomeCliente"].value = nomeCliente;                               
                }else if(tipoPessoa == "J"){
                    form[formIdClientes+":nomeCliente"].value = razaoSocial;  
                }
                                                
                form[formIdClientes+":idTipoPessoa"].value = tipoPessoa;               
              
                winIdClientes.close();
            }
		</script>
	</head>
	<body onload="exibirMensagemErro();inicializar();">
	<div id="outer">
		<div id="topoGeral">
			<div id="tituloPaginaGeral">
				<strong>
					<h:outputText value="#{msgs.consultarPedido}"></h:outputText>
				</strong>
			</div>				
		</div>		
		<h:form id="frmConsultarPedido" binding="#{pedidoBB.init}">			
			<div>
				<%@ include file="/jsp/mensagem_erro.jsp"%>
			</div>	
				<div id="content">				
						<div id="primarioContentContainer">
							<fieldset>
								<legend>Opções de filtro:</legend>
								<ul>								
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Loja"></h:outputLabel>
											<h:selectOneMenu id="idLoja" style="width: 200px;" value="#{pedidoBB.idLoja}"> 
												<f:selectItems id="lojasSelectItems" value="#{pedidoBB.lojas}" />   
											</h:selectOneMenu>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Número Pedido"></h:outputLabel>
											<h:inputText styleClass="field text" id="idPedido" maxlength="6" onkeypress="return SoNumero(event);"
												value="#{pedidoBB.idPedido}" size="10" required="false">
												<f:validateLength maximum="6" />
												<f:validator validatorId="LongValidator"/>
											</h:inputText>								
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Vendedor"></h:outputLabel>
											<h:selectOneMenu id="idVendedor" style="width: 185px;" value="#{pedidoBB.idVendedor}"> 
												<f:selectItems id="vendedoresSelectItems" value="#{pedidoBB.usuariosVendedores}" />   
											</h:selectOneMenu>
										</div>
										<div>	
											<h:outputLabel styleClass="desc" value="Status"></h:outputLabel>
											<h:selectOneMenu id="idSituacao" style="width: 185px;" value="#{pedidoBB.idSituacao}"> 
												<f:selectItems id="statusSelectItems" value="#{pedidoBB.listaSituacao}" />   
											</h:selectOneMenu>
										</div>
										<br />
										<br />
										<div>
											<h:outputLabel styleClass="desc" value="Tipo Pessoa"></h:outputLabel>
											<h:selectOneRadio  styleClass="field select tipopessoa" id="idTipoPessoa" 
												value="#{pedidoBB.idTipoPessoa}" layout="lineDirection" required="false">
												<f:selectItems id="tipoPessoaLista" value="#{pedidoBB.listaTipoPessoa}"/>
											</h:selectOneRadio>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="CPF/CNPJ"></h:outputLabel>
											<h:inputText styleClass="field text tipocpfcnpj" id="cpfCnpjCliente" maxlength="18" size="18" 
											value="#{pedidoBB.cpfCnpjCliente}" required="false" onkeypress="return SoNumero(event);">
												<f:validateLength minimum="11" maximum="18" />
											</h:inputText>	
										
											<h:commandButton image="/images/pesquisa.png" alt="Pesquisar Cliente" styleClass="btTxt" id="botaoConsultarCliente"
												onmousedown="showPopUpClientes(this,'frmConsultarPedido','find')" value="">
											</h:commandButton>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Nome Cliente/Razão Social"></h:outputLabel>
											<h:inputText styleClass="field text" id="nomeCliente" maxlength="50" size="60" required="false"
												value="#{pedidoBB.nomeCliente}" disabled="true">
											</h:inputText>
										</div>
										<br />															
										<br />
										<div>
											<h:outputLabel styleClass="desc" value="Data Início"></h:outputLabel>
											<h:inputText styleClass="field text" id="dataInicial" maxlength="10" size="10" required="false"
												value="#{pedidoBB.dataInicial}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }">
												<f:convertDateTime timeZone="GMT-3"/>
											</h:inputText>
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Data Fim"></h:outputLabel>
											<h:inputText styleClass="field text" id="dataFinal" maxlength="10" size="10" required="false"
												value="#{pedidoBB.dataFinal}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }">
												<f:convertDateTime timeZone="GMT-3"/>
											</h:inputText>										
										</div>									</li>
								</ul>
							</fieldset>	
							<div class="listagem">
								<t:dataTable id="pedidos" value="#{pedidoBB.pedidos}"
									var="pedido" rowClasses="rowA,rowB" width="90%" renderedIfEmpty="false">
									<h:column>
										<f:facet name="header">
											<h:outputText value="Número Pedido" /> 
										</f:facet>
										<h:commandLink value="#{pedido.pk.id}" action="#{pedidoBB.consultar}">
											<f:param name="pedido_loja" value="#{pedido.pk.loja}"/>						
											<f:param name="pedido_id" value="#{pedido.pk.id}"/>
										</h:commandLink>										
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Loja" />
										</f:facet>
										<h:outputText value="#{pedido.pk.loja}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Data Pedido" />
										</f:facet>
										<h:outputText value="#{pedido.data}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Data Transação" />
										</f:facet>
										<h:outputText value="#{pedido.data}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Vendedor" />
										</f:facet>
										<h:outputText value="#{pedido.codigoUsuarioVendedor} - #{pedido.codigoUsuarioVendedor}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Desconto" />
										</f:facet>
										<h:outputText value="#{pedido.desconto}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Valor" />
										</f:facet>
										<h:outputText value="#{pedido.valor}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Status" />
										</f:facet>
										<h:outputText value="Novo" rendered="#{pedido.status == 1}"/> 
										<h:outputText value="Em processamento" rendered="#{pedido.status == 2}"/>
										<h:outputText value="Concluído" rendered="#{pedido.status == 3}"/> 
										<h:outputText value="Cancelado" rendered="#{pedido.status == 4}"/>
									</h:column>									
								</t:dataTable>									
							</div>
							<ul>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" action="#{pedidoBB.resetBB}" id="botaoLimpar" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoConsultar" action="#{pedidoBB.consultar}" value="Consultar"></h:commandButton>
								</li>						
							</ul>
						</div>
						<div class="clear"></div>
					</div>
		</h:form>
	  </body>	
	</f:view>
</html>