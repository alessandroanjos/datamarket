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
				if ($('[name=frmInserirFabricante:tipoPessoa]:checked').val() != "undefined") {
					mostraCampos($('[name=frmInserirFabricante:tipoPessoa]:checked').val());
				}
												
			}

      

      function mostraCampos(str) {
        var flag = new String(str);
        if (flag.toUpperCase() == "F") {
	        habilita("frmInserirFabricante:nomeFabricante");
        	desabilita("frmInserirFabricante:razaoSocial");
        	desabilita("frmInserirFabricante:nomeFantasia");
        	desabilita("frmInserirFabricante:inscricaoEstadual");
        	desabilita("frmInserirFabricante:inscricaoMunicipal");
			$("input.tipocpfcnpj").each(function(i){
				$(this).unbind('blur');
				$(this).unbind('keydown');
				$(this).bind('blur',function(event){validaCPF(this);});
				$(this).bind('keydown',function(event){FormataCPF(this,event);});
				getId(this.id).maxLength = "14";
			});
        } else {
  	        desabilita("frmInserirFabricante:nomeFabricante");
        	habilita("frmInserirFabricante:razaoSocial");
        	habilita("frmInserirFabricante:nomeFantasia");
        	habilita("frmInserirFabricante:inscricaoEstadual");
        	habilita("frmInserirFabricante:inscricaoMunicipal");
			$("input.tipocpfcnpj").each(function(i){
				$(this).unbind('blur');
				$(this).unbind('keydown');
				$(this).bind('blur',function(event){validaCNPJ(this);});
				$(this).bind('keydown',function(event){FormataCNPJ(this,event);});
				getId(this.id).maxLength = "18";
			});
        }
      }		
      </script>
	</head>
	<body onload="exibirMensagemErro();inicializar();">
	<div id="outer">
		<div id="topoGeral">
			<div id="tituloPaginaGeral">
				<strong>
					<h:outputText value="#{msgs.inserirFabricante}"></h:outputText>
				</strong>
			</div>				
		</div>			
				<div id="content">
					<div class="tabMenu">
						<ul>
							<li id="tabMenuDiv0" class="current" onclick="selecionaMenuTab(this.id)"><span><a href="#">Dados Fabricante</a></span></li>
							<li id="tabMenuDiv1" onclick="selecionaMenuTab(this.id)"><span><a href="#">Endere�o</a></span></li>
						</ul>
						<div class="clear"></div>
					</div>
					<div id="primarioContentContainerInternas">
<!-- xxxxxxxxxxxxxxx -->
		<h:form id="frmInserirFabricante" binding="#{fabricanteBB.init}">
<!-- xxxxxxxxxxxxxxx -->
						<div id="tabDiv0">
							<ul>
								<li class="normal">
									<div>
										<%@ include file="/jsp/mensagem_erro.jsp"%>
									</div>
								</li>
								<li class="normal">																	
									<div>
										<h:outputLabel styleClass="desc" value="Data de Cadastro"></h:outputLabel>
										<h:inputText styleClass="field text" id="dataCadastro" maxlength="10" size="10" readonly="true"
											value="#{fabricanteBB.dataCadastro}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }">			
											<f:convertDateTime timeZone="GMT-3"/>
										</h:inputText>
										<div>
									</div>
									
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Tipo Pessoa*"></h:outputLabel>
										<h:selectOneRadio  styleClass="field select tipopessoa" id="tipoPessoa" 
											value="#{fabricanteBB.idTipoPessoa}" layout="lineDirection" >
											<f:selectItems id="tipoPessoaLista" value="#{fabricanteBB.listaTipoPessoa}"/>
										</h:selectOneRadio>
										
									</div>
									
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="CPF/CNPJ*"></h:outputLabel>
										<h:inputText styleClass="field text tipocpfcnpj" id="cpfCnpj" maxlength="18" size="18" value="#{fabricanteBB.cpfCnpj}" 
										onfocus="this.select();" onclick="this.select();"
										onkeypress="return SoNumero(event);">
											<f:validateLength minimum="11" maximum="18" />
										</h:inputText>
																		
									</div>
										
								</li>
						
								<!-- PESSOA FISICA -->																
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Nome Fabricante*"></h:outputLabel>
										<h:inputText styleClass="field text" id="nomeFabricante" maxlength="50" size="50" value="#{fabricanteBB.nomeFabricante}" required="false">
											<f:validateLength maximum="50" />
										</h:inputText>
																	
									</div>
											
								</li>
								<!-- PESSOA FISICA -->								
								
								<!-- PESSOA JURIDICA -->
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Raz�o Social*"></h:outputLabel>
										<h:inputText styleClass="field text" id="razaoSocial" maxlength="50" size="50" value="#{fabricanteBB.razaoSocial}" required="false">
											<f:validateLength maximum="50" />
										</h:inputText>
																			
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Nome Fantasia*"></h:outputLabel>
										<h:inputText styleClass="field text" id="nomeFantasia" maxlength="50" size="50" value="#{fabricanteBB.nomeFantasia}" required="false">
											<f:validateLength maximum="50" />
										</h:inputText>
																			
									</div>
									
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Insc. Estadual"></h:outputLabel>
										<h:inputText styleClass="field text" id="inscricaoEstadual" maxlength="30" size="30" value="#{fabricanteBB.inscricaoEstadual}" required="false">
											<f:validateLength maximum="30" />
										</h:inputText>
									</div>
																													
											</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Insc. Municipal"></h:outputLabel>
										<h:inputText styleClass="field text" id="inscricaoMunicipal" maxlength="30" size="30" value="#{fabricanteBB.inscricaoMunicipal}" required="false">
											<f:validateLength maximum="30" />
										</h:inputText>
																		
									</div>
										
								</li>
							</ul>
						</div>
						<div id="tabDiv1" style="display:none;">
							<ul>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Logradouro"></h:outputLabel>
										<h:inputTextarea rows="4" id="logradouro" style="width: 80%;" styleClass="field text" value="#{fabricanteBB.logradouro}" required="false">
											<f:validateLength maximum="200" />
										</h:inputTextarea>
																			
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="N�mero"></h:outputLabel>
										<h:inputText styleClass="field text" id="numero" maxlength="10" size="10" value="#{fabricanteBB.numero}" required="false" 
											onfocus="this.select();" onclick="this.select();"
											onkeypress="return SoNumero(event);">
											<f:validateLength maximum="10" />
										</h:inputText>
																			
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Complemento"></h:outputLabel>
										<h:inputText styleClass="field text" id="complemento" maxlength="20" size="20" value="#{fabricanteBB.complemento}" required="false">
											<f:validateLength maximum="20" />
										</h:inputText>
																			
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Bairro"></h:outputLabel>
										<h:inputText styleClass="field text" id="bairro" maxlength="30" size="30" value="#{fabricanteBB.bairro}" required="false">
											<f:validateLength maximum="30" />
										</h:inputText>
																			
									</div>								
									<div>
										<h:outputLabel styleClass="desc" value="Cidade"></h:outputLabel>
										<h:inputText styleClass="field text" id="cidade" maxlength="30" size="30" value="#{fabricanteBB.cidade}" required="false">
											<f:validateLength maximum="30" />
										</h:inputText>
																			
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Estado"></h:outputLabel>
										<h:selectOneMenu id="estado" styleClass="field text"
											value="#{fabricanteBB.estado}">
											<f:selectItems id="listaUfSelectItems"
												value="#{fabricanteBB.listaUf}" />
										</h:selectOneMenu>
																			
									</div>							
									<div>
										<h:outputLabel styleClass="desc" value="CEP"></h:outputLabel>
										<h:inputText styleClass="field text" id="cep" maxlength="10" size="10" value="#{fabricanteBB.cep}" required="false" 
											onfocus="this.select();" onclick="this.select();"
											onkeypress="return SoNumero(event);">
											<f:validateLength maximum="10" />
										</h:inputText>
																			
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Fone Residencial"></h:outputLabel>
										<h:inputText styleClass="field text" id="foneResidencial" maxlength="12" size="12" value="#{fabricanteBB.foneResidencial}" required="false" 
											onfocus="this.select();" onclick="this.select();"
											onkeypress="return SoNumero(event);">
											<f:validateLength maximum="12" />
										</h:inputText>
										
									</div>								
									<div>
										<h:outputLabel styleClass="desc" value="Fone Comercial"></h:outputLabel>
										<h:inputText styleClass="field text" id="foneComercial" maxlength="12" size="12" value="#{fabricanteBB.foneComercial}" required="false" 
											onfocus="this.select();" onclick="this.select();"
											onkeypress="return SoNumero(event);">
											<f:validateLength maximum="12" />
										</h:inputText>
																			
									</div>								
									<div>
										<h:outputLabel styleClass="desc" value="Fone Celular"></h:outputLabel>
										<h:inputText styleClass="field text" id="foneCelular" maxlength="12" size="12" value="#{fabricanteBB.foneCelular}" required="false" 
											onfocus="this.select();" onclick="this.select();"
											onkeypress="return SoNumero(event);">
											<f:validateLength maximum="12" />
										</h:inputText>
																			
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Nome Contato"></h:outputLabel>
										<h:inputText styleClass="field text" id="pessoaContato" maxlength="50" size="50" value="#{fabricanteBB.pessoaContato}" required="false">
											<f:validateLength maximum="50" />
										</h:inputText>
																			
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Fone Contato"></h:outputLabel>
										<h:inputText styleClass="field text" id="foneContato" maxlength="12" size="12" value="#{fabricanteBB.foneContato}" required="false" 
											onfocus="this.select();" onclick="this.select();"
											onkeypress="return SoNumero(event);">
											<f:validateLength maximum="12" />
										</h:inputText>
																			
									</div>
								</li>
							</ul>
						</div>	
						<ul>
							<li class="buttons">
								<h:commandButton styleClass="btTxt" action="#{fabricanteBB.resetBB}" id="botaoLimpar" value="Limpar"></h:commandButton>
								<h:commandButton styleClass="btTxt" id="botaoInserir" action="#{fabricanteBB.inserir}" value="Inserir"></h:commandButton>
							</li>
						</ul>
<!-- xxxxxxxxxxxxxxx -->					
		</h:form>		
<!-- xxxxxxxxxxxxxxx -->					
          </div>
					<div class="clear"></div>
				</div>

	 </body>
	</f:view>
</html>