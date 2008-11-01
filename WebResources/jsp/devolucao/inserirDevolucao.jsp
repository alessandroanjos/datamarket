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
				if ($('[name=frmInserirDevolucao:idTipoPessoa]:checked').val() != "undefined") {
					mostraCampos($('[name=frmInserirDevolucao:idTipoPessoa]:checked').val());
				}
			}
			
			var formId; // referência ao formulário principal
            var winId;  // referência à janela popup
            // Esta função faz a chamada da janela popup.
            //
            function showPopUpProdutos(action, form, target) {
             if(getId("frmInserirDevolucao:codigoProduto").value == ""){        		
                    formId=form;
        			if (winId != null) {
        			    winId.close();
        			}
                    features="height=500,width=600,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes,dependent=yes";             
        			winId=window.open('/EnterpriseServer/jsp/popup/PopUpProdutos.faces?acao=init','list',features);
				    // Formulário escondido
                    hform=document.forms[form];                
				}else{
					document.forms["frmInserirDevolucao"].action = "/EnterpriseServer/jsp/devolucao/inserirDevolucao.faces?acaoLocal=pesquisarProdutos&codigoProduto="+getId("frmInserirDevolucao:codigoProduto").value;
					document.forms["frmInserirDevolucao"].submit();                  	
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
            
	            if(getId("frmInserirDevolucao:cpfCnpj").value == ""){        		
	                   formIdClientes = form;
	       			if (winIdClientes != null) {
	       			    winIdClientes.close();
	       			}
	                features = "height=500,width=600,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes,dependent=yes";             
	       			winIdClientes = window.open('/EnterpriseServer/jsp/popup/PopUpClientes.faces','list',features);
				    // Formulário escondido
	                hform=document.forms[form];                
				}else{
				    var cpfCnpjTmp = getId("frmInserirDevolucao:cpfCnpj").value;
					document.forms["frmInserirDevolucao"].action = "/EnterpriseServer/jsp/devolucao/inserirDevolucao.faces?acaoLocal=pesquisarClientes&cpfCnpj="+cpfCnpjTmp;
					document.forms["frmInserirDevolucao"].submit();                  	
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
						<h:outputText value="#{msgs.inserirDevolucao}"></h:outputText>
					</strong>
				</div>				
			</div>	
			<div id="content">
				<div id="primarioContentContainerInternas">
					<h:form id="frmInserirDevolucao"  binding="#{devolucaoBB.init}">
						<div>
							<h:messages errorClass="msgSistemaErro" infoClass="msgSistemaSucesso" globalOnly="true" showDetail="true"/>
						</div>
						<ul>								
							<li class="normal">
								<div>
									<h:outputLabel styleClass="desc" value="Loja"></h:outputLabel>
									<h:selectOneMenu id="idLoja" style="width: 200px;" value="#{devolucaoBB.idLoja}"> 
										<f:selectItems id="lojasSelectItems" value="#{devolucaoBB.lojas}" />   
									</h:selectOneMenu>
								</div>
								<div>
									<h:outputLabel styleClass="desc" value="Data"></h:outputLabel>
									<h:inputText styleClass="field text" id="dataDevolucao" maxlength="10" size="10"
										value="#{devolucaoBB.dataDevolucao}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }">
										<f:convertDateTime timeZone="GMT-3"/>
									</h:inputText>
								</div>
							</li>	
							<li class="normal">
								<div>
									<h:outputLabel styleClass="desc" value="Tipo Pessoa"></h:outputLabel>
									<h:selectOneRadio  styleClass="field select tipopessoa" id="idTipoPessoa" 
										value="#{devolucaoBB.idTipoPessoa}" layout="lineDirection">
										<f:selectItems id="tipoPessoaLista" value="#{devolucaoBB.listaTipoPessoa}"/>
									</h:selectOneRadio>
								</div>
								<div>
									<h:outputLabel styleClass="desc" value="CPF/CNPJ"></h:outputLabel>
									<h:inputText styleClass="field text tipocpfcnpj" id="cpfCnpj" maxlength="18" size="18" 
									value="#{devolucaoBB.cpfCnpj}" onkeypress="return SoNumero(event);">
										<f:validateLength minimum="11" maximum="18" />
									</h:inputText>	
									<h:commandButton image="/images/pesquisa.png" alt="Pesquisar Cliente" styleClass="btTxt" id="botaoConsultarCliente"
										onmousedown="showPopUpClientes(this,'frmInserirDevolucao','find')" onclick="return false" value="">
									</h:commandButton>							
								</div>
								<div>
									<h:outputLabel styleClass="desc" value="Nome Cliente"></h:outputLabel>
									<h:inputText styleClass="field text" id="nomeCliente" maxlength="50" size="50" value="#{devolucaoBB.nomeCliente}" />			
								</div>									
							</li>										
							<li class="normal">
								<div>
									<h:outputLabel styleClass="desc" value="Código Produto*"></h:outputLabel>
									<h:inputText styleClass="field text" id="codigoProduto" maxlength="6" size="6"
										value="#{devolucaoBB.codigoProduto}" dir="ltr" onkeypress="return SoNumero(event);">
										<f:validateLength maximum="6" />
									</h:inputText>
									<h:commandButton image="/images/pesquisa.png" alt="Pesquisar Produto" styleClass="btTxt" id="botaoConsultarProduto"
										onmousedown="showPopUpProdutos(this,'frmInserirDevolucao','find')" onclick="return false" value="">
									</h:commandButton>
								</div>
								<div>
									<h:outputLabel styleClass="desc" value="Descrição Completa"></h:outputLabel>
									<h:inputText styleClass="field text" id="descricaoProduto" size="39"
										value="#{devolucaoBB.descricaoProduto}" readonly="true">
									</h:inputText>
								</div>
								<div>
									<h:outputLabel styleClass="desc" value="Preço Venda"></h:outputLabel>
									<h:inputText styleClass="field text" dir="rtl" id="precoVenda" size="12" value="#{devolucaoBB.precoVenda}" onkeypress="return(formataMoeda(this,'','.',2,event));">
										<f:validateLength maximum="12" />
										<f:validateDoubleRange  minimum="0.00" maximum="999999999.99"/>
										<f:validator validatorId="BigDecimalValidator"/>
									</h:inputText>
								</div>
								<div>
									<h:outputLabel styleClass="desc" value="Quantidade"></h:outputLabel>
									<h:inputText styleClass="field text" id="quantidade" maxlength="7" size="7"
										value="#{devolucaoBB.quantidade}" dir="rtl" onkeypress="return(formataMoeda(this,'','.',3,event));">
										<f:validateLength maximum="7" />
										<f:validateDoubleRange  minimum="0.000" maximum="999.999"/>
										<f:validator validatorId="BigDecimalValidator"/>
									</h:inputText>
									<h:commandButton image="/images/adicionar.png" alt="Inserir" styleClass="btTxt" id="botaoInserirProdutoDevolucao" action="#{devolucaoBB.inserirProdutoDevolucao}" value="Inserir"></h:commandButton>
								</div>
								<div style="position:relative; top:9px;">									
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
										<h:column>
											<f:facet name="header">
												<h:outputText style="align: center;" value="Ação" />
											</f:facet>										
											<h:commandButton image="/images/excluir.png" alt="Excluir" actionListener="#{devolucaoBB.removerProdutoDevolucao}">
												<f:param id="idExcluirProdutoDevolucao" name="idExcluirProdutoDevolucao" value="#{produtoDevolucao.pk.numeroEvento}" />
											</h:commandButton>
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
								<h:commandButton styleClass="btTxt" action="#{devolucaoBB.resetBB}" id="botaoLimpar" value="Limpar"></h:commandButton>
								<h:commandButton styleClass="btTxt" id="botaoInserir" action="#{devolucaoBB.inserir}" value="Inserir"></h:commandButton>
<!-- 							<h:commandButton styleClass="btTxt" id="botaoImprimir" action="#{devolucaoBB.imprimirRecibo}" value="Imprimir"></h:commandButton> -->
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