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
				
				if ($('[name=frmManterPedido:idTipoPessoa]:checked').val() != "undefined") {
					mostraCampos($('[name=frmManterPedido:idTipoPessoa]:checked').val());
				};
				
				var valor = getId("frmManterPedido:usaEnderecoParaEntrega").value;
				if(valor != "" && valor == "true"){
					getId("frmManterPedido:usaEnderecoParaEntrega").checked = "true";
				}else{
					getId("frmManterPedido:usaEnderecoParaEntrega").checked = "false";
				}		
						
				strAbaCorrente = getId("frmManterPedido:abaCorrente").value;
				//alert(strAbaCorrente);
				if(strAbaCorrente != ""){							
					selecionaMenuTab(strAbaCorrente);
				}else{
					selecionaMenuTab("tabMenuDiv0");
				}	
				
				
			}
			
			var formId; // referência ao formulário principal
            var winId;  // referência à janela popup
            // Esta função faz a chamada da janela popup.
            //
            function showPopUp(action, form, target) {
				if(getId("frmManterPedido:codigoProduto").value == ""){        		
					formId=form;
					if (winId != null) {
						winId.close();
        		  	} 
        		    features="height=500,width=600,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes,dependent=yes";             
        		    winId=window.open('/EnterpriseServer/jsp/popup/PopUpProdutos.faces?acao=init&enquadramento=4','list',features);
				    // Formulário escondido
                    hform=document.forms[form];                
			   }else{
				    getId("frmManterPedido:abaCorrente").value = strAbaCorrente;
					document.forms["frmManterPedido"].action = "/EnterpriseServer/jsp/pedido/manterPedido.faces?acaoLocal=pesquisarProdutos&codigoProduto="+getId("frmManterPedido:codigoProduto").value;
					document.forms["frmManterPedido"].submit();                  	
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
               reCalculaPrecoItem();
               if(precoVenda == 0){
                  form[formId+":precoVenda"].focus();  
               }else{
                  form[formId+":descontoItem"].focus();
               }
               
            }
            
            function reCalculaPrecoItem(){
           	   var precoVenda = parseFloat(getId("frmManterPedido:precoVenda").value);
           	   var quantidade = parseFloat(getId("frmManterPedido:quantidade").value);
           	   var descontoItem = parseFloat(getId("frmManterPedido:descontoItem").value);
           	   var valorItem = parseFloat(getId("frmManterPedido:valorItem").value);  
           	   
           	   var valorTotalPedido = parseFloat(getId("frmManterPedido:valorTotalPedido").value);
           	              	           	
           	   //calculando o valor do item
           	   valorItem = (precoVenda-descontoItem)*quantidade;
           	   getId("frmManterPedido:valorItem").value = valorItem.toFixed(2);            	   
            }
                        
            function reCalculaTotalPedido(){
           	   var valorTotalPedido = parseFloat(getId("frmManterPedido:valorTotalPedido").value);
           	   var descontoPedido = parseFloat(getId("frmManterPedido:descontoPedido").value);
			
			   // subtraindo o valor do desconto no pé do pedido
			   getId("frmManterPedido:valorTotalPedido").value = valorTotalPedido.toFixed(2) - descontoPedido.toFixed(2);
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
	            if(getId("frmManterPedido:cpfCnpjCliente").value == ""){        		
	                   formIdClientes = form;
	       			if (winIdClientes != null) {
	       			    winIdClientes.close();
	       			}
	                features = "height=500,width=600,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes,dependent=yes";             
	       			winIdClientes = window.open('/EnterpriseServer/jsp/popup/PopUpClientes.faces','list',features);
				    // Formulário escondido
	                hform=document.forms[form];                
				}else{
				//alert(strAbaCorrente);
					getId("frmManterPedido:abaCorrente").value = strAbaCorrente;
				    var cpfCnpjTmp = getId("frmManterPedido:cpfCnpjCliente").value;
					document.forms["frmManterPedido"].action = "/EnterpriseServer/jsp/pedido/manterPedido.faces?acaoLocal=pesquisarClientes&cpfCnpj="+cpfCnpjTmp;
					document.forms["frmManterPedido"].submit();                  	
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
                
                if(endereco != ""){
                
                    var endTmp = endereco.split("|");
                
                	form[formIdClientes+":logradouroCliente"].value = endTmp[0];
                	form[formIdClientes+":numeroCliente"].value = endTmp[1];
                	form[formIdClientes+":complementoCliente"].value = endTmp[2];
                	form[formIdClientes+":bairroCliente"].value = endTmp[3];
                	form[formIdClientes+":cidadeCliente"].value = endTmp[4];
                	form[formIdClientes+":estadoCliente"].value = endTmp[5];
                	form[formIdClientes+":cepCliente"].value = endTmp[6];
                }
              
                winIdClientes.close();
            }
   		</script>
	</head>
	<body onload="exibirMensagemErro();inicializar();">
		<div id="outer">
			<div id="topoGeral">
				<div id="tituloPaginaGeral">
					<strong>
						<h:outputText value="#{msgs.manterPedido}"></h:outputText>
					</strong>
				</div>				
			</div>	
			<div id="content">		
				<div class="tabMenu">
					<ul>
						<li id="tabMenuDiv0" class="current" onclick="selecionaMenuTab(this.id)"><span><a href="#">Pedido</a></span></li>
						<li id="tabMenuDiv1" onclick="selecionaMenuTab(this.id)"><span><a href="#">Cliente</a></span></li>
					</ul>
					<div class="clear"></div>
				</div>	
				<div id="primarioContentContainerInternas">
					<h:form id="frmManterPedido" binding="#{pedidoBB.init}" onsubmit="javascript:getId('frmManterPedido:abaCorrente').value = strAbaCorrente;getId('frmManterPedido:enderecoEntrega').value = getId('frmManterPedido:usaEnderecoParaEntrega').checked?'S':'N'; ">
						<div>
							<%@ include file="/jsp/mensagem_erro.jsp"%>
						</div>
						<h:inputHidden id="abaCorrente" value="#{pedidoBB.abaCorrente}"></h:inputHidden>				
						<h:inputHidden id="enderecoEntrega" value="#{pedidoBB.enderecoEntrega}"></h:inputHidden>
						<div id="tabDiv0" style="height: 470px;">					
							<ul>								
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Loja*"></h:outputLabel>
										<h:selectOneMenu id="idLoja" style="width: 230px;" value="#{pedidoBB.idLoja}" valueChangeListener="#{pedidoBB.carregarProximoIdPedido}"> 
											<f:selectItems id="lojasSelectItems" value="#{pedidoBB.lojas}" />   
										</h:selectOneMenu>
									</div>
								
									<div>
										<h:outputLabel styleClass="desc" value="Número Pedido*"></h:outputLabel>
										<h:inputText styleClass="field text" id="idPedido" maxlength="6" onkeypress="return SoNumero(event);"
											value="#{pedidoBB.idPedido}" size="10" required="false" disabled="true">
											<f:validateLength maximum="6" />
											<f:validator validatorId="LongValidator"/>
										</h:inputText>
									</div>
								
									<div>
										<h:outputLabel styleClass="desc" value="Data Pedido*"></h:outputLabel>
										<h:inputText styleClass="field text" id="dataPedido" maxlength="10" size="10" required="false"
											value="#{pedidoBB.dataPedido}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }">
											<f:convertDateTime timeZone="GMT-3"/>
										</h:inputText>
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Vendedor*"></h:outputLabel>
										<h:selectOneMenu id="idVendedor" style="width: 230px;" value="#{pedidoBB.idVendedor}"> 
											<f:selectItems id="vendedoresSelectItems" value="#{pedidoBB.usuariosVendedores}" />   
										</h:selectOneMenu>
									</div>
								</li>
							</ul>
							<ul>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Código Produto*"></h:outputLabel>
										<h:inputText styleClass="field text" id="codigoProduto" maxlength="6" size="6"
											value="#{pedidoBB.codigoProduto}" dir="ltr" required="false" onkeypress="return SoNumero(event);">
											<f:validateLength maximum="6" />
										</h:inputText>
										<h:commandButton image="/images/pesquisa.png" alt="Pesquisar Produto" styleClass="btTxt" id="botaoConsultarProduto"
											onmousedown="showPopUp(this,'frmManterPedido','find')"
											onclick="return false" value="">
										</h:commandButton>
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Descrição Completa*"></h:outputLabel>
										<h:inputText styleClass="field text" id="descricaoProduto" size="30"
											value="#{pedidoBB.descricaoProduto}" readonly="true">
										</h:inputText>
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Valor Unit.*"></h:outputLabel>
										<h:inputText styleClass="field text" id="precoVenda" maxlength="10" size="10"
											value="#{pedidoBB.precoVenda}" disabled="#{pedidoBB.precoVenda > 0}" dir="rtl" required="false" onkeypress="return(formataMoeda(this,'','.',2,event));" onblur="reCalculaPrecoItem();">
											<f:validateLength maximum="10" />
											<f:validateDoubleRange  minimum="0.00" maximum="9999999.99"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Desconto"></h:outputLabel>
										<h:inputText styleClass="field text" id="descontoItem" maxlength="10" size="10"
											value="#{pedidoBB.descontoItem}" dir="rtl" required="false" onkeypress="return(formataMoeda(this,'','.',2,event));" onblur="reCalculaPrecoItem();">
											<f:validateLength maximum="10" />
											<f:validateDoubleRange  minimum="0.00" maximum="9999999.99"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Quantidade*"></h:outputLabel>
										<h:inputText styleClass="field text" id="quantidade" maxlength="7" size="9"
											value="#{pedidoBB.quantidade}" dir="rtl" required="false" onkeypress="return(MascaraQTD(this,'','.',event));" onblur="reCalculaPrecoItem();">
											<f:validateLength maximum="7" />
											<f:validateDoubleRange  minimum="0.000" maximum="999.999"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Valor Item*"></h:outputLabel>
										<h:inputText styleClass="field text" id="valorItem" maxlength="10" size="10" readonly="true"
											value="#{pedidoBB.valorItem}" dir="rtl" required="false" onkeypress="return(formataMoeda(this,'','.',2,event));">
											<f:validateLength maximum="10" />
											<f:validateDoubleRange  minimum="0.00" maximum="9999999.99"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
										<h:commandButton image="/images/adicionar.png" alt="Inserir" onclick="reCalculaPrecoItem();" styleClass="btTxt" id="botaoInserirItemPedido" action="#{pedidoBB.inserirItemPedido}" value="Inserir"></h:commandButton>
									</div>
		 						</li>										
							</ul>
							<div class="listagemSimples" style="overflow:auto; height: 300px;">
								<t:dataTable value="#{pedidoBB.itensPedido}"
									var="itemPedido" rowClasses="rowA,rowB" width="100%" renderedIfEmpty="false">
									<h:column>
										<f:facet name="header">
											<h:outputText value="Item" /> 
										</f:facet>
										<h:outputText style="align: center;" value="#{itemPedido.pk.numeroEvento}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Produto" />
										</f:facet>
										<h:outputText style="align: left;" value="#{itemPedido.produtoOperacaoItemRegistrado.codigoExterno} - #{itemPedido.produtoOperacaoItemRegistrado.descricaoCompleta}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Pr. Unidade" />
										</f:facet>
										<h:outputText style="align: right;" value="#{itemPedido.produtoOperacaoItemRegistrado.precoPadrao}" /> 
									</h:column>	
									<h:column>
										<f:facet name="header">
											<h:outputText value="Pr. Venda" />
										</f:facet>
										<h:outputText style="align: right;" value="#{itemPedido.produtoOperacaoItemRegistrado.precoPraticado}" /> 
									</h:column>	
									<h:column>
										<f:facet name="header">
											<h:outputText value="Quantidade" />
										</f:facet>
										<h:outputText style="align: right;" value="#{itemPedido.quantidade}" /> 
									</h:column>	
									<h:column>
										<f:facet name="header">
											<h:outputText value="Desconto" />
										</f:facet>
										<h:outputText style="align: right;" value="#{itemPedido.desconto}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Pr. Item" />
										</f:facet>
										<h:outputText style="align: right;" value="#{itemPedido.preco}" /> 
									</h:column>	
									<h:column>
										<f:facet name="header">
											<h:outputText style="align: center;" value="Ação" />
										</f:facet>										
										<h:commandButton image="/images/excluir.png" alt="Excluir" actionListener="#{pedidoBB.removerItemPedido}">
											<f:param name="idExcluirItemPedido" value="#{itemPedido.pk.numeroEvento}" id="idExcluirItemPedido"/>
										</h:commandButton>
									</h:column>													
								</t:dataTable>																
							</div>
							<ul>		
								<li>
									<hr dir="ltr" class="linha" />				
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Quant. Total"></h:outputLabel>
										<h:inputText styleClass="field text" id="quantidadeTotal" maxlength="10" size="10"
											value="#{pedidoBB.quantidadeTotal}" dir="rtl" required="false" readonly="true" onkeypress="return(formataMoeda(this,'','.',3,event));">
											<f:validateLength maximum="10" />
											<f:validateDoubleRange  minimum="0.00" maximum="9999999.999"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
									</div>							
									<div>
										<h:outputLabel styleClass="desc" value="Desconto"></h:outputLabel>
										<h:inputText styleClass="field text" id="descontoPedido" maxlength="5" size="10" onblur="reCalculaTotalPedido();"
											value="#{pedidoBB.descontoPedido}" dir="rtl" required="false" onkeypress="return(formataMoeda(this,'','.',2,event));">
											<f:validateLength maximum="5" />
											<f:validateDoubleRange  minimum="0.00" maximum="9999999.99"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
									</div>											
									<div>
										<h:outputLabel styleClass="desc" value="Total Pedido"></h:outputLabel>
										<h:inputText styleClass="field text" id="valorTotalPedido" maxlength="10" size="10" readonly="true"
											value="#{pedidoBB.valorTotalPedido}" dir="rtl" required="false" onkeypress="return(formataMoeda(this,'','.',2,event));">
											<f:validateLength maximum="10" />
											<f:validateDoubleRange  minimum="0.01" maximum="9999999.99"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
									</div>
								</li>
							</ul>
						</div>
						<div id="tabDiv1"  style="display:none;height: 470px;">
							<ul>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Tipo Pessoa*"></h:outputLabel>
										<h:selectOneRadio  styleClass="field select tipopessoa" id="idTipoPessoa" 
											value="#{pedidoBB.idTipoPessoa}" layout="lineDirection">
											<f:selectItems id="tipoPessoaLista" value="#{pedidoBB.listaTipoPessoa}"/>
										</h:selectOneRadio>
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="CPF/CNPJ Cliente*"></h:outputLabel>
										<h:inputText styleClass="field text tipocpfcnpj" id="cpfCnpjCliente" maxlength="18" size="18"
											value="#{pedidoBB.cpfCnpjCliente}" dir="ltr" onkeypress="return SoNumero(event);">
											<f:validateLength maximum="14" />
										</h:inputText>
										<h:commandButton image="/images/pesquisa.png" alt="Pesquisar Cliente" styleClass="btTxt" id="botaoConsultarCliente"
											onmousedown="showPopUpClientes(this,'frmManterPedido','find')" value="">
										</h:commandButton>
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Nome Cliente"></h:outputLabel>
										<h:inputText styleClass="field text" id="nomeCliente" maxlength="50" size="56" value="#{pedidoBB.nomeCliente}">
											<f:validateLength maximum="50" />
										</h:inputText>	
									</div>
								</li>												
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Razão Social"></h:outputLabel>
										<h:inputText styleClass="field text" id="razaoSocialCliente" maxlength="50" size="50" value="#{pedidoBB.razaoSocialCliente}">
											<f:validateLength maximum="50" />
										</h:inputText>								
									</div>								
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Insc. Estadual"></h:outputLabel>
										<h:inputText styleClass="field text" id="inscricaoEstadualCliente" maxlength="30" size="23" value="#{pedidoBB.inscricaoEstadualCliente}">
											<f:validateLength maximum="30" />
										</h:inputText>
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Insc. Municipal"></h:outputLabel>
										<h:inputText styleClass="field text" id="inscricaoMunicipalCliente" maxlength="30" size="22" value="#{pedidoBB.inscricaoMunicipalCliente}">
											<f:validateLength maximum="30" />
										</h:inputText>					
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="E-mail"></h:outputLabel>
										<h:inputText styleClass="field text" id="emailCliente" maxlength="50" size="52" value="#{pedidoBB.emailCliente}">
											<f:validateLength maximum="50" />
										</h:inputText>
									</div>
								</li>		
								<li class="normal">
									<div style="width: 100%;">
										<h:outputLabel styleClass="desc" value="Logradouro"></h:outputLabel>
										<h:inputTextarea rows="3" id="logradouroCliente" style="width: 94%;" styleClass="field text" value="#{pedidoBB.logradouroCliente}">
											<f:validateLength maximum="200" />
										</h:inputTextarea>	
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Número"></h:outputLabel>
										<h:inputText styleClass="field text" id="numeroCliente" maxlength="10" size="10" value="#{pedidoBB.numeroCliente}">
											<f:validateLength maximum="10" />
										</h:inputText>						
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Complemento"></h:outputLabel>
										<h:inputText styleClass="field text" id="complementoCliente" maxlength="20" size="15" value="#{pedidoBB.complementoCliente}">
											<f:validateLength maximum="20" />
											</h:inputText>							
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Bairro"></h:outputLabel>
										<h:inputText styleClass="field text" id="bairroCliente" maxlength="30" size="20" value="#{pedidoBB.bairroCliente}">
											<f:validateLength maximum="30" />
										</h:inputText>					
									</div>								
									<div>
										<h:outputLabel styleClass="desc" value="Cidade"></h:outputLabel>
										<h:inputText styleClass="field text" id="cidadeCliente" maxlength="30" size="20" value="#{pedidoBB.cidadeCliente}">
											<f:validateLength maximum="30" />
										</h:inputText>
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Estado"></h:outputLabel>									
										<h:selectOneMenu id="estadoCliente" style="width: 150px;" value="#{pedidoBB.estadoCliente}"> 
											<f:selectItems id="UFs1SelectItems" value="#{pedidoBB.listaUFs1}" />   
										</h:selectOneMenu>			
									</div>								
									<div>
										<h:outputLabel styleClass="desc" value="CEP"></h:outputLabel>
										<h:inputText styleClass="field text" id="cepCliente" maxlength="10" size="10" value="#{pedidoBB.cepCliente}">
											<f:validateLength maximum="10" />
										</h:inputText>	
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Fone"></h:outputLabel>
										<h:inputText styleClass="field text" id="foneResidencialCliente" maxlength="13" size="13" value="#{pedidoBB.foneCliente}">
											<f:validateLength maximum="13" />
										</h:inputText>
									</div>								
									<div>
										<h:outputLabel styleClass="desc" value="Fone Celular"></h:outputLabel>
										<h:inputText styleClass="field text" id="foneCelularCliente" maxlength="13" size="13" value="#{pedidoBB.celularCliente}">
											<f:validateLength maximum="13" />
										</h:inputText>		
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Nome Contato"></h:outputLabel>
										<h:inputText styleClass="field text" id="pessoaContatoCliente" maxlength="50" size="30" value="#{pedidoBB.pessoaContatoCliente}">
											<f:validateLength maximum="50" />
										</h:inputText>
									</div>
								</li>
							</ul>
							<ul>
								<li class="normal">								
									<div>
										<h:outputText styleClass="field select" value="Usar Endereço para Entrega"></h:outputText>
										<h:selectBooleanCheckbox dir="rtl" id="usaEnderecoParaEntrega"
											styleClass="field select" title="Usar Endereço para Entrega" 
											valueChangeListener="#{pedidoBB.setaUsaEnderecoEntrega}"
											onclick="document.forms['frmManterPedido'].submit();">
										</h:selectBooleanCheckbox>
									</div>
								</li>								
								<li class="normal">
									<div style="width: 100%;">
										<h:outputLabel styleClass="desc" value="Logradouro"></h:outputLabel>
										<h:inputTextarea rows="3" id="logradouroClienteEntrega" style="width: 94%;" styleClass="field text" value="#{pedidoBB.logradouroClienteEntrega}">
											<f:validateLength maximum="200" />
										</h:inputTextarea>	
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Número"></h:outputLabel>
										<h:inputText styleClass="field text" id="numeroClienteEntrega" maxlength="10" size="10" value="#{pedidoBB.numeroClienteEntrega}">
											<f:validateLength maximum="10" />
										</h:inputText>						
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Complemento"></h:outputLabel>
										<h:inputText styleClass="field text" id="complementoClienteEntrega" maxlength="20" size="15" value="#{pedidoBB.complementoClienteEntrega}">
											<f:validateLength maximum="20" />
											</h:inputText>							
									</div>																
									<div>
										<h:outputLabel styleClass="desc" value="CEP"></h:outputLabel>
										<h:inputText styleClass="field text" id="cepClienteEntrega" maxlength="10" size="10" value="#{pedidoBB.cepClienteEntrega}">
											<f:validateLength maximum="10" />
										</h:inputText>	
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Bairro"></h:outputLabel>
										<h:inputText styleClass="field text" id="bairroClienteEntrega" maxlength="30" size="20" value="#{pedidoBB.bairroClienteEntrega}">
											<f:validateLength maximum="30" />
										</h:inputText>					
									</div>								
									<div>
										<h:outputLabel styleClass="desc" value="Cidade"></h:outputLabel>
										<h:inputText styleClass="field text" id="cidadeClienteEntrega" maxlength="30" size="20" value="#{pedidoBB.cidadeClienteEntrega}">
											<f:validateLength maximum="30" />
										</h:inputText>
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Estado"></h:outputLabel>
										<h:selectOneMenu id="estadoClienteEntrega" style="width: 150px;" value="#{pedidoBB.estadoClienteEntrega}"> 
											<f:selectItems id="UFs2SelectItems" value="#{pedidoBB.listaUFs2}" />   
										</h:selectOneMenu>						
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Ponto de Referência"></h:outputLabel>
										<h:inputTextarea rows="3" id="pontoReferenciaEnderecoEntrega" style="width: 94%;" styleClass="field text" value="#{pedidoBB.pontoReferenciaEnderecoEntrega}">
											<f:validateLength maximum="1000" />
										</h:inputTextarea>
									</div>
								</li>
							</ul>
						</div>	
						<ul>
							<li class="buttons">
								<h:commandButton immediate="true" styleClass="btTxt" id="botaoVoltar" action="#{pedidoBB.voltarConsulta}" value="Voltar"></h:commandButton>
								<h:commandButton styleClass="btTxt" id="botaoAlterar" action="#{pedidoBB.alterar}" value="Alterar"></h:commandButton>
								<h:commandButton styleClass="btTxt" id="botaoCancelar" action="#{pedidoBB.cancelar}" value="Cancelar"></h:commandButton>
								<h:commandButton styleClass="btTxt" id="botaoImprimir" action="#{pedidoBB.imprimirRecibo}" value="Imprimir"></h:commandButton>								
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