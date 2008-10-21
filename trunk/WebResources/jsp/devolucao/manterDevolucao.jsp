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
				$("input.tipopessoa").each(function(i){
					$(this).click(function() {mostraCampos(this.value)});
				});
				if ($('[name=frmManterDevolucao:idTipoPessoa]:checked').val() != "undefined") {
					mostraCampos($('[name=frmManterDevolucao:idTipoPessoa]:checked').val());
				}
			}
			
			var formId; // referência ao formulário principal
            var winId;  // referência à janela popup
            // Esta função faz a chamada da janela popup.
            //
            function showPopUpProdutos(action, form, target) {
             if(getId("frmManterDevolucao:codigoProduto").value == ""){        		
                    formId=form;
        			if (winId != null) {
        			    winId.close();
        			}
                    features="height=500,width=600,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes,dependent=yes";             
        			winId=window.open('/EnterpriseServer/jsp/popup/PopUpProdutos.faces','list',features);
				    // Formulário escondido
                    hform=document.forms[form];                
				}else{
					document.forms["frmManterDevolucao"].action = "/EnterpriseServer/jsp/devolucao/inserirDevolucao.faces?acaoLocal=pesquisarProdutos&codigoProduto="+getId("frmManterDevolucao:codigoProduto").value;
					document.forms["frmManterDevolucao"].submit();                  	
                }
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
						//getId(this.id).value = "";
					});
				} else {
					$("input.tipocpfcnpj").each(function(i){
						$(this).unbind('blur');
						$(this).unbind('keydown');
						$(this).bind('blur',function(event){validaCNPJ(this);});
						$(this).bind('keydown',function(event){FormataCNPJ(this,event);});
						getId(this.id).maxLength = "18";
						//getId(this.id).value = "";
					});
				}
			}
			
			var formIdClientes; // referência ao formulário principal
            var winIdClientes;  // referência à janela popup
            // Esta função faz a chamada da janela popup.
            //
            function showPopUpClientes(action, form, target) {
            
	            if(getId("frmManterDevolucao:cpfCnpj").value == ""){        		
	                   formIdClientes = form;
	       			if (winIdClientes != null) {
	       			    winIdClientes.close();
	       			}
	                features = "height=500,width=600,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes,dependent=yes";             
	       			winIdClientes = window.open('/EnterpriseServer/jsp/popup/PopUpClientes.faces','list',features);
				    // Formulário escondido
	                hform=document.forms[form];                
				}else{
				    var cpfCnpjTmp = getId("frmManterDevolucao:cpfCnpj").value;
					document.forms["frmManterDevolucao"].action = "/EnterpriseServer/jsp/devolucao/inserirDevolucao.faces?acaoLocal=pesquisarClientes&cpfCnpj="+cpfCnpjTmp;
					document.forms["frmManterDevolucao"].submit();                  	
               }
           }
           
           // Esta função é chamada pela janela popup 
           // quando um usuário clica em um item na listagem.
           // O item selecionado é copiado para um campo de texto
           // no formulário principal.
           //
            function setAtributoClientes(cpfCnpj,nomeCliente, razaoSocial, tipoPessoa) {
                var form = document.forms[formIdClientes];                             
                 
                form[formIdClientes+":cpfCnpj"].value = cpfCnpj; 
                
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
	<body>
		<div id="outer">
			<div id="topoGeral">
				<div id="tituloPaginaGeral">
					<strong>
						<h:outputText value="#{msgs.manterDevolucao}"></h:outputText>
					</strong>
				</div>				
			</div>	
			<div id="content">
				<div id="primarioContentContainerInternas">
					<h:form id="frmManterDevolucao"  binding="#{devolucaoBB.init}">
						<div>
							<h:messages errorClass="msgSistemaErro" infoClass="msgSistemaSucesso" globalOnly="true" showDetail="true"/>
						</div>
						<ul>								
							<li class="normal">
								<div>
									<h:outputLabel styleClass="desc" value="Loja"></h:outputLabel>
									<h:selectOneMenu id="idLoja" style="width: 200px;" value="#{devolucaoBB.idLoja}" readonly="true"> 
										<f:selectItems id="lojasSelectItems" value="#{devolucaoBB.lojas}" />   
									</h:selectOneMenu>
								</div>
								<div>
									<h:outputLabel styleClass="desc" value="Data"></h:outputLabel>
									<h:inputText styleClass="field text" id="dataDevolucao" maxlength="10" size="10"  readonly="true"
										value="#{devolucaoBB.dataDevolucao}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }">
										<f:convertDateTime timeZone="GMT-3"/>
									</h:inputText>
								</div>
								<div>
									<h:outputLabel styleClass="desc" value="Status"></h:outputLabel>
									<h:selectOneMenu id="idSituacao" style="width: 200px;" value="#{devolucaoBB.idSituacao}" readonly="true"> 
										<f:selectItems id="situacaoSelectItems" value="#{devolucaoBB.listaSituacao}" />   
									</h:selectOneMenu>
								</div>
							</li>	
							<li class="normal">
								<div>
									<h:outputLabel styleClass="desc" value="Tipo Pessoa"></h:outputLabel>
									<h:selectOneRadio  styleClass="field select tipopessoa" id="idTipoPessoa"   readonly="true"
										value="#{devolucaoBB.idTipoPessoa}" layout="lineDirection">
										<f:selectItems id="tipoPessoaLista" value="#{devolucaoBB.listaTipoPessoa}"/>
									</h:selectOneRadio>
								</div>
								<div>
									<h:outputLabel styleClass="desc" value="CPF/CNPJ"></h:outputLabel>
									<h:inputText styleClass="field text tipocpfcnpj" id="cpfCnpj" maxlength="18" size="18"   readonly="true"
									value="#{devolucaoBB.cpfCnpj}" onkeypress="return SoNumero(event);">
										<f:validateLength minimum="11" maximum="18" />
									</h:inputText>					
								</div>
								<div>
									<h:outputLabel styleClass="desc" value="Nome Cliente"></h:outputLabel>
									<h:inputText styleClass="field text" id="nomeCliente" maxlength="50" size="50" value="#{devolucaoBB.nomeCliente}"  readonly="true"/>			
								</div>									
							</li>							
							<li>
								<div class="listagemSimples" style="overflow:auto; height:235px;">
									<t:dataTable value="#{devolucaoBB.eventosOperacao}" 
										var="produtoDevolucao" rowClasses="rowA,rowB" width="100%" renderedIfEmpty="false">
										<h:column>
											<f:facet name="header">
												<h:outputText value="Código Produto" />
											</f:facet>
											<h:outputText style="align: left;" value="#{produtoDevolucao.produtoOperacaoItemRegistrado.codigoExterno}" /> 
										</h:column>
										<h:column>
											<f:facet name="header">
												<h:outputText value="Descrição" />
											</f:facet>
											<h:outputText style="align: left;" value="#{produtoDevolucao.produtoOperacaoItemRegistrado.descricaoCompleta}" /> 
										</h:column>
										<h:column>
											<f:facet name="header">
												<h:outputText value="Valor Unitário" />
											</f:facet>
											<h:outputText style="align: right;" value="#{produtoDevolucao.produtoOperacaoItemRegistrado.precoPadrao}" /> 
										</h:column>	
										<h:column>
											<f:facet name="header">
												<h:outputText value="Quantidade" />
											</f:facet>
											<h:outputText style="align: right;" value="#{produtoDevolucao.quantidade}" /> 
										</h:column>	
										<h:column>
											<f:facet name="header">
												<h:outputText value="Valor Item" />
											</f:facet>
											<h:outputText style="align: right;" value="#{produtoDevolucao.preco}" /> 
										</h:column>																						
									</t:dataTable>																
								</div>
							</li>
							<li class="direita">
								<div>
									<h:outputLabel styleClass="descBaixo" value="Valor Total"></h:outputLabel>
								</div>
								<div>
									<h:inputText styleClass="field text" id="valorTotalDevolucao" maxlength="15" size="15" 
										value="#{devolucaoBB.valorTotalDevolucao}" dir="rtl" onkeypress="return SoNumero(event);" readonly="true">
									</h:inputText>
								</div>
							</li>
							<li class="buttons">
								<h:commandButton immediate="true" styleClass="btTxt" id="botaoVoltar" action="#{devolucaoBB.voltarConsulta}" value="Voltar"></h:commandButton>
								<h:commandButton styleClass="btTxt" id="botaoImprimir" action="#{devolucaoBB.imprimirRecibo}" value="Imprimir"></h:commandButton>
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