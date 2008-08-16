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
		if ($('[name=frmInserirFornecedor:tipoPessoa]:checked').val() != "undefined") {
			mostraCampos($('[name=frmInserirFornecedor:tipoPessoa]:checked').val());
		}
      }

      function mostraCampos(str) {
        var flag = new String(str);
        if (flag.toUpperCase() == "F") {
	        habilita("frmInserirFornecedor:nomeFornecedor");
        	desabilita("frmInserirFornecedor:razaoSocial");
        	desabilita("frmInserirFornecedor:nomeFantasia");
        	desabilita("frmInserirFornecedor:inscricaoEstadual");
        	desabilita("frmInserirFornecedor:inscricaoMunicipal");
			$("input.tipocpfcnpj").each(function(i){
				$(this).unbind('blur');
				$(this).unbind('keydown');
				$(this).bind('blur',function(event){validaCPF(this);});
				$(this).bind('keydown',function(event){FormataCPF(this,event);});
				getId(this.id).maxLength = "14";
			});
        } else {
  	        desabilita("frmInserirFornecedor:nomeFornecedor");
        	habilita("frmInserirFornecedor:razaoSocial");
        	habilita("frmInserirFornecedor:nomeFantasia");
        	habilita("frmInserirFornecedor:inscricaoEstadual");
        	habilita("frmInserirFornecedor:inscricaoMunicipal");
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
	<body>
	<div id="outer">
		<div id="topoGeral">
			<div id="tituloPaginaGeral">
				<strong>
					<h:outputText value="#{msgs.inserirFornecedor}"></h:outputText>
				</strong>
			</div>				
		</div>			
				<div id="content">
					<div id="tabMenu">
						<ul>
							<li id="tabMenuDiv0" class="current" onclick="selecionaMenuTab(this.id)"><span><a href="#">Dados Fornecedor</a></span></li>
							<li id="tabMenuDiv1" onclick="selecionaMenuTab(this.id)"><span><a href="#">Endereço</a></span></li>
						</ul>
						<div class="clear"></div>
					</div>
					<div id="primarioContentContainerInternas">
<!-- xxxxxxxxxxxxxxx -->
		<h:form id="frmInserirFornecedor" binding="#{fornecedorBB.init}">
<!-- xxxxxxxxxxxxxxx -->
						<div id="tabDiv0">
							<ul>
								<li class="normal">
									<div>
										<h:messages errorClass="msgSistemaErro" infoClass="msgSistemaSucesso" globalOnly="true" showDetail="true"/>
									</div>
								</li>
								<li class="normal">
									<!--  <div>
										<h:outputLabel styleClass="desc" value="Código*"></h:outputLabel>
										<h:inputText styleClass="field text ativo" id="id"
											maxlength="4" value="#{fornecedorBB.id}" size="4"
											required="true">
											<f:validateLength maximum="4" />
											<f:validator validatorId="LongValidator" />
										</h:inputText>
																				
									</div>
									<h:message for="id" styleClass="msgErro"/> -->
									
									<div>
										<h:outputLabel styleClass="desc" value="Data de Cadastro"></h:outputLabel>
										<h:inputText styleClass="field text" id="dataCadastro" maxlength="10" size="10" readonly="true"
											value="#{fornecedorBB.dataCadastro}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) { alert(ERRO_DATA_INVALIDA); this.select(); }">			
											
										</h:inputText>
										<div>
									</div>
									<h:message for="dataCadastro" styleClass="msgErro"/>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Tipo Pessoa*"></h:outputLabel>
										<h:selectOneRadio  styleClass="field select tipopessoa" id="tipoPessoa" 
											value="#{fornecedorBB.idTipoPessoa}" layout="lineDirection" required="true">
											<f:selectItems id="tipoPessoaLista" value="#{fornecedorBB.listaTipoPessoa}"/>
										</h:selectOneRadio>
										
									</div>
									<h:message for="tipoPessoa" styleClass="msgErro"/>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="CPF/CNPJ*"></h:outputLabel>
										<h:inputText styleClass="field text tipocpfcnpj" id="cpfCnpj" maxlength="18" size="18" value="#{fornecedorBB.cpfCnpj}" required="true"
										onkeypress="return SoNumero(event);">
											<f:validateLength minimum="11" maximum="18" />
										</h:inputText>
																		
									</div>
									<h:message for="cpfCnpj" styleClass="msgErro" />	
								</li>
						
								<!-- PESSOA FISICA -->																
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Nome Fornecedor*"></h:outputLabel>
										<h:inputText styleClass="field text" id="nomeFornecedor" maxlength="50" size="50" value="#{fornecedorBB.nomeFornecedor}" required="false">
											<f:validateLength maximum="50" />
										</h:inputText>
																	
									</div>
									<h:message for="nomeFornecedor" styleClass="msgErro" />		
								</li>
								<!-- PESSOA FISICA -->								
								
								<!-- PESSOA JURIDICA -->
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Razão Social*"></h:outputLabel>
										<h:inputText styleClass="field text" id="razaoSocial" maxlength="50" size="50" value="#{fornecedorBB.razaoSocial}" required="false">
											<f:validateLength maximum="50" />
										</h:inputText>
										<h:message for="razaoSocial" styleClass="msgErro" />									
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Nome Fantasia*"></h:outputLabel>
										<h:inputText styleClass="field text" id="nomeFantasia" maxlength="50" size="50" value="#{fornecedorBB.nomeFantasia}" required="false">
											<f:validateLength maximum="50" />
										</h:inputText>
																			
									</div>
									<h:message for="nomeFantasia" styleClass="msgErro" />
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Insc. Estadual*"></h:outputLabel>
										<h:inputText styleClass="field text" id="inscricaoEstadual" maxlength="30" size="30" value="#{fornecedorBB.inscricaoEstadual}" required="false">
											<f:validateLength maximum="30" />
										</h:inputText>
									</div>
										<h:message for="inscricaoEstadual" styleClass="msgErro" />																			
											</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Insc. Municipal*"></h:outputLabel>
										<h:inputText styleClass="field text" id="inscricaoMunicipal" maxlength="30" size="30" value="#{fornecedorBB.inscricaoMunicipal}" required="false">
											<f:validateLength maximum="30" />
										</h:inputText>
																		
									</div>
									<h:message for="inscricaoMunicipal" styleClass="msgErro" />	
								</li>
							</ul>
						</div>
						<div id="tabDiv1" style="display:none;">
							<ul>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Logradouro"></h:outputLabel>
										<h:inputTextarea rows="4" id="logradouro" style="width: 80%;" styleClass="field text" value="#{fornecedorBB.logradouro}" required="false">
											<f:validateLength maximum="200" />
										</h:inputTextarea>
										<h:message for="logradouro" styleClass="msgErro" />									
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Número"></h:outputLabel>
										<h:inputText styleClass="field text" id="numero" maxlength="10" size="10" value="#{fornecedorBB.numero}" required="false" onkeypress="return SoNumero(event);">
											<f:validateLength maximum="10" />
										</h:inputText>
										<h:message for="numero" styleClass="msgErro" />									
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Complemento"></h:outputLabel>
										<h:inputText styleClass="field text" id="complemento" maxlength="20" size="20" value="#{fornecedorBB.complemento}" required="false">
											<f:validateLength maximum="20" />
										</h:inputText>
										<h:message for="complemento" styleClass="msgErro" />									
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Bairro"></h:outputLabel>
										<h:inputText styleClass="field text" id="bairro" maxlength="30" size="30" value="#{fornecedorBB.bairro}" required="false">
											<f:validateLength maximum="30" />
										</h:inputText>
										<h:message for="bairro" styleClass="msgErro" />									
									</div>								
									<div>
										<h:outputLabel styleClass="desc" value="Cidade"></h:outputLabel>
										<h:inputText styleClass="field text" id="cidade" maxlength="30" size="30" value="#{fornecedorBB.cidade}" required="false">
											<f:validateLength maximum="30" />
										</h:inputText>
										<h:message for="cidade" styleClass="msgErro" />									
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Estado"></h:outputLabel>
										<h:selectOneMenu id="estado" styleClass="field text"
											value="#{fornecedorBB.estado}">
											<f:selectItems id="listaUfSelectItems"
												value="#{fornecedorBB.listaUf}" />
										</h:selectOneMenu>
										<h:message for="estado" styleClass="msgErro" />									
									</div>							
									<div>
										<h:outputLabel styleClass="desc" value="CEP"></h:outputLabel>
										<h:inputText styleClass="field text" id="cep" maxlength="10" size="10" value="#{fornecedorBB.cep}" required="false" onkeypress="return SoNumero(event);">
											<f:validateLength maximum="10" />
										</h:inputText>
										<h:message for="cep" styleClass="msgErro" />									
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Fone Residencial"></h:outputLabel>
										<h:inputText styleClass="field text" id="foneResidencial" maxlength="12" size="12" value="#{fornecedorBB.foneResidencial}" required="false" onkeypress="return SoNumero(event);">
											<f:validateLength maximum="12" />
										</h:inputText>
										<h:message for="foneResidencial" styleClass="msgErro" />
									</div>								
									<div>
										<h:outputLabel styleClass="desc" value="Fone Comercial"></h:outputLabel>
										<h:inputText styleClass="field text" id="foneComercial" maxlength="12" size="12" value="#{fornecedorBB.foneComercial}" required="false" onkeypress="return SoNumero(event);">
											<f:validateLength maximum="12" />
										</h:inputText>
										<h:message for="foneComercial" styleClass="msgErro" />									
									</div>								
									<div>
										<h:outputLabel styleClass="desc" value="Fone Celular"></h:outputLabel>
										<h:inputText styleClass="field text" id="foneCelular" maxlength="12" size="12" value="#{fornecedorBB.foneCelular}" required="false" onkeypress="return SoNumero(event);">
											<f:validateLength maximum="12" />
										</h:inputText>
										<h:message for="foneCelular" styleClass="msgErro" />									
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Nome Contato"></h:outputLabel>
										<h:inputText styleClass="field text" id="pessoaContato" maxlength="50" size="50" value="#{fornecedorBB.pessoaContato}" required="false">
											<f:validateLength maximum="50" />
										</h:inputText>
										<h:message for="pessoaContato" styleClass="msgErro" />									
									</div>
									<div>
										<h:outputLabel styleClass="desc" value="Fone Contato"></h:outputLabel>
										<h:inputText styleClass="field text" id="foneContato" maxlength="12" size="12" value="#{fornecedorBB.foneContato}" required="false" onkeypress="return SoNumero(event);">
											<f:validateLength maximum="12" />
										</h:inputText>
										<h:message for="foneContato" styleClass="msgErro" />									
									</div>
								</li>
							</ul>
						</div>	
						<ul>
							<li class="buttons">
								<h:commandButton styleClass="btTxt" immediate="true" id="botaoLimpar" type="reset" value="Limpar"></h:commandButton>
								<h:commandButton styleClass="btTxt" id="botaoInserir" action="#{fornecedorBB.inserir}" value="Inserir"></h:commandButton>
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