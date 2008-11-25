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

	</head>
	<script language="javascript">

            var formId; // referência ao formulário principal

            var winId;  // referência à janela popup
            
            window.onload = function(){ inicializar() };
		
		      function inicializar() {
		
		      			strQuantidadeAntes = getId("frmInserirAjusteEstoque:quantidadeAntes").value;
						if(strQuantidadeAntes != ""){							
							getId("frmInserirAjusteEstoque:quantidadeDepois").focus();
						}
		
		      }

            // Esta função faz a chamada da janela popup.

            //

            function showPopUp(action, form, target) {
            	if(getId("frmInserirAjusteEstoque:idProduto").value == ""){        		
	                formId=form;
	      			if (winId != null) {
	      				winId.close();
	      			}
	                features="height=500,width=600,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes,dependent=yes";             
	      			winId=window.open('/EnterpriseServer/jsp/popup/PopUpProdutos.faces?acao=init','list',features);
	      			// Formulário escondido
	                hform=document.forms[form];   
                 }else{
					document.forms["frmInserirAjusteEstoque"].action = "/EnterpriseServer/jsp/ajusteEstoque/inserirAjusteEstoque.faces?acao=pesquisarProdutos&codigoProduto="+getId("frmInserirAjusteEstoque:idProduto").value;
					document.forms["frmInserirAjusteEstoque"].submit();
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
                             winId.close();
                             form.action = "/EnterpriseServer/jsp/ajusteEstoque/inserirAjusteEstoque.faces?acao=buscaQtdAntes&idEstoque"+form[formId+":idEstoque"].value;
                             form.submit();
                             form[formId+":quantidadeDepois"].focus();
            }
      </script>
	<body>
	<div id="outer">
		<div id="topoGeral">
			<div id="tituloPaginaGeral">
				<strong>
					<h:outputText value="#{msgs.inserirAjusteEstoque}"></h:outputText>
				</strong>
			</div>				
		</div>
		<h:form id="frmInserirAjusteEstoque" binding="#{ajusteEstoqueBB.init}">
			<div id="content">
				<div id="primarioContentContainerInternas" >
					<ul>
						<li class="normal">
							<div>
								<h:messages errorClass="msgSistemaErro"
									infoClass="msgSistemaSucesso" globalOnly="true"
									showDetail="true" />
							</div>
						</li>
						<li class="normal">
							<div>
								<h:outputLabel styleClass="desc" value="Estoque"></h:outputLabel>
								<h:selectOneMenu id="idEstoque" styleClass="field select" onchange="submit();" immediate="true"
									value="#{ajusteEstoqueBB.idEstoque}" valueChangeListener="#{ajusteEstoqueBB.recuperaQuantidadeAntes}">
									<f:selectItems id="estoqueSelectItems" 
										value="#{ajusteEstoqueBB.estoques}"  />
										
								</h:selectOneMenu>
								<h:message for="idEstoque" styleClass="errors" />
							</div>
						</li>

						<li class="normal">
							<div>
								<h:outputLabel styleClass="desc" value="Código Produto*"></h:outputLabel>
								<h:inputText styleClass="field text ativo" id="idProduto" 
									onfocus="this.select();" onclick="this.select();"
									onkeypress="return SoNumero(event);" 								
									maxlength="9" value="#{ajusteEstoqueBB.idProduto}" size="12">
								</h:inputText>
								<h:commandButton image="/images/pesquisa.png" alt="Pesquisar Produto"  styleClass="btTxt" id="botaoConsultarProduto"
								onmousedown="showPopUp(this,'frmInserirAjusteEstoque','find')"
								onclick="return false" value="Consultar Produto"></h:commandButton>
								
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Descrição*"></h:outputLabel>
								<h:inputText styleClass="field text ativo" id="descricao" 
									maxlength="50" size="50" readonly="true" value="#{ajusteEstoqueBB.descricao}" size="50">
									<f:validateLength maximum="50" />
									
 							    </h:inputText>
							</div>
						</li>

						<li class="normal">
							 <div>
								<h:outputLabel styleClass="desc" value="Qtd. Atual"></h:outputLabel>
								<h:inputText styleClass="field text" id="quantidadeAntes"  readonly="true" maxlength="7"
									size="10" value="#{ajusteEstoqueBB.quantidadeAntes}">
									<f:validateLength maximum="7" />
								</h:inputText>
								<h:message for="quantidadeAntes" styleClass="msgErro" />
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Qtd. Depois*"></h:outputLabel>
								<h:inputText styleClass="field text" id="quantidadeDepois" maxlength="7"
									size="10" value="#{ajusteEstoqueBB.quantidadeDepois}" 
									onkeydown="returnQTD(BackSpace(this,event));"  onkeypress="return(MascaraQTD(this,'','.',event));" >
									<f:validateLength maximum="7" />
									<f:validateDoubleRange minimum="0.00" maximum="9999.999" />
									<f:validator validatorId="BigDecimalValidator" />
								</h:inputText>
								<h:message for="quantidadeDepois" styleClass="msgErro" />
							</div>
						</li>	

						<li class="buttons">
							<h:commandButton styleClass="btTxt" action="#{ajusteEstoqueBB.resetBB}" id="botaoLimpar" value="Limpar"></h:commandButton>
							<h:commandButton styleClass="btTxt" id="botaoInserir"
								action="#{ajusteEstoqueBB.inserir}" value="Inserir"></h:commandButton>
						</li>
					</ul>

				</div>
				<div class="clear"></div>
			</div>
			
		</h:form>
	 </body>			
	</f:view>
</html>
