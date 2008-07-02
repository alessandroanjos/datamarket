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
	
			<meta http-equiv="pragma" content="no-cache"/>
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

      }

      function mostraCampos(str) {
        //frmInserirCliente:comissao
        var flag = new String(str);
        if (flag.toUpperCase() == "F") {
	        habilita("frmInserirCliente:nomeCliente");
        	habilita("frmInserirCliente:dataNascimento");
        	desabilita("frmInserirCliente:razaoSocial");
        	desabilita("frmInserirCliente:nomeFantasia");
        	desabilita("frmInserirCliente:inscricaoEstadual");
        	desabilita("frmInserirCliente:inscricaoMunicipal");
        } else {
  	        desabilita("frmInserirCliente:nomeCliente");
        	desabilita("frmInserirCliente:dataNascimento");
        	habilita("frmInserirCliente:razaoSocial");
        	habilita("frmInserirCliente:nomeFantasia");
        	habilita("frmInserirCliente:inscricaoEstadual");
        	habilita("frmInserirCliente:inscricaoMunicipal");
        }
      
      }

      </script>

		</head>
		<body>
			<div id="outer">
				<div id="topoGeral">
					<div id="tituloPaginaGeral">
						<strong>
							<h:outputText value="#{msgs.inserirCliente}"></h:outputText>
						</strong>
					</div>				
				</div>
				<div id="content">
					<div id="tabMenu">
						<ul>
							<li id="tabMenuDiv0" class="current" onclick="selecionaMenuTab(this.id)"><span><a href="#">Dados Cliente</a></span></li>
							<li id="tabMenuDiv1" onclick="selecionaMenuTab(this.id)"><span><a href="#">Endereço</a></span></li>
						</ul>
						<div class="clear"></div>
					</div>
					<div id="primarioContentContainerInternas">				
						<h:form id="frmInserirCliente">
							<div id="tabDiv0">
								<ul>
									<li class="normal">
										<div>
											<h:messages errorClass="msgSistemaErro" infoClass="msgSistemaSucesso" globalOnly="true" showDetail="true"/>
										</div>
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Código*"></h:outputLabel>
											<h:inputText styleClass="field text ativo" id="id" onkeypress="return SoNumero(event);"
												maxlength="4" value="#{clienteBB.id}" size="4"
												required="true">
												<f:validateLength maximum="4" />
												<f:validator validatorId="LongValidator" />
											</h:inputText>
											<h:message for="id" styleClass="msgErro" showDetail="true" showSummary="true" tooltip="true"/>										
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Data de Cadastro"></h:outputLabel>
											<h:inputText styleClass="field text" id="dataCadastro" maxlength="10" size="10" readonly="false"
												value="#{clienteBB.dataCadastro}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) this.value = ''">			
											</h:inputText>
											<h:message for="dataCadastro" styleClass="msgErro"/>
										</div>
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Tipo Pessoa*"></h:outputLabel>
											<h:selectOneRadio  styleClass="field select tipopessoa" id="tipoPessoa" 
												value="#{clienteBB.idTipoPessoa}" layout="lineDirection" required="true">
												<f:selectItems id="tipoPessoaLista" value="#{clienteBB.listaTipoPessoa}"/>
											</h:selectOneRadio>
											<h:message for="tipoPessoa" styleClass="msgErro"/>
										</div>
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="CPF/CNPJ*"></h:outputLabel>
											<h:inputText styleClass="field text" id="cpfCnpj" maxlength="18" size="18" value="#{clienteBB.cpfCnpj}" required="true">
												<f:validateLength minimum="11" maximum="18" />
											</h:inputText>
											<h:message for="cpfCnpj" styleClass="msgErro" />									
										</div>
									</li>
									<!-- PESSOA FISICA -->																
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Nome Cliente*"></h:outputLabel>
											<h:inputText styleClass="field text" id="nomeCliente" maxlength="50" size="50" value="#{clienteBB.nomeCliente}" required="true">
												<f:validateLength maximum="50" />
											</h:inputText>
											<h:message for="nomeCliente" styleClass="msgErro" />									
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Data de Nascimento"></h:outputLabel>
											<h:inputText styleClass="field text" id="dataNascimento" maxlength="10" size="10"
												value="#{clienteBB.dataNascimento}" onkeypress="return MascaraData(this,event);" onblur="if (!isDate(this.value)) this.value = ''">
												
											</h:inputText>
											<h:message for="dataNascimento" styleClass="msgErro"/>
										</div>
									</li>
									<!-- PESSOA FISICA -->								
									<!-- PESSOA JURIDICA -->
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Razão Social"></h:outputLabel>
											<h:inputText styleClass="field text" id="razaoSocial" maxlength="50" size="50" value="#{clienteBB.razaoSocial}" required="false">
												<f:validateLength maximum="50" />
											</h:inputText>
											<h:message for="razaoSocial" styleClass="msgErro" />									
										</div>
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Nome Fantasia"></h:outputLabel>
											<h:inputText styleClass="field text" id="nomeFantasia" maxlength="50" size="50" value="#{clienteBB.nomeFantasia}" required="false">
												<f:validateLength maximum="50" />
											</h:inputText>
											<h:message for="nomeFantasia" styleClass="msgErro" />									
										</div>
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Insc. Estadual"></h:outputLabel>
											<h:inputText styleClass="field text" id="inscricaoEstadual" maxlength="30" size="30" value="#{clienteBB.inscricaoEstadual}" required="false">
												<f:validateLength maximum="30" />
											</h:inputText>
											<h:message for="inscricaoEstadual" styleClass="msgErro" />									
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Insc. Municipal"></h:outputLabel>
											<h:inputText styleClass="field text" id="inscricaoMunicipal" maxlength="30" size="30" value="#{clienteBB.inscricaoMunicipal}" required="false">
												<f:validateLength maximum="30" />
											</h:inputText>
											<h:message for="inscricaoMunicipal" styleClass="msgErro" />									
										</div>
									</li>
									<!-- PESSOA JURIDICA -->
								</ul>
							</div>
							<div id="tabDiv1" style="display:none;">
								<ul>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Logradouro"></h:outputLabel>
											<h:inputTextarea rows="4" id="logradouro" style="width: 80%;" styleClass="field text" value="#{clienteBB.logradouro}" required="false">
												<f:validateLength maximum="200" />
											</h:inputTextarea>
											<h:message for="logradouro" styleClass="msgErro" />									
										</div>
									</li>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="Número"></h:outputLabel>
											<h:inputText styleClass="field text" id="numero" maxlength="10" size="10" value="#{clienteBB.numero}" required="false">
												<f:validateLength maximum="10" />
											</h:inputText>
											<h:message for="numero" styleClass="msgErro" />									
										</div>
										<div>
											<h:outputLabel styleClass="desc" value="Complemento"></h:outputLabel>
											<h:inputText styleClass="field text" id="complemento" maxlength="20" size="20" value="#{clienteBB.complemento}" required="false">
												<f:validateLength maximum="20" />
												</h:inputText>
												<h:message for="complemento" styleClass="msgErro" />									
											</div>
										</li>
										<li class="normal">
											<div>
												<h:outputLabel styleClass="desc" value="Bairro"></h:outputLabel>
												<h:inputText styleClass="field text" id="bairro" maxlength="30" size="30" value="#{clienteBB.bairro}" required="false">
													<f:validateLength maximum="30" />
												</h:inputText>
												<h:message for="bairro" styleClass="msgErro" />									
											</div>								
											<div>
												<h:outputLabel styleClass="desc" value="Cidade"></h:outputLabel>
												<h:inputText styleClass="field text" id="cidade" maxlength="30" size="30" value="#{clienteBB.cidade}" required="false">
													<f:validateLength maximum="30" />
												</h:inputText>
												<h:message for="cidade" styleClass="msgErro" />									
											</div>
										</li>
										<li class="normal">
											<div>
												<h:outputLabel styleClass="desc" value="Estado"></h:outputLabel>
												<h:inputText styleClass="field text" id="estado" maxlength="30" size="30" value="#{clienteBB.estado}" required="false">
													<f:validateLength maximum="30" />
												</h:inputText>
												<h:message for="estado" styleClass="msgErro" />									
											</div>								
											<div>
												<h:outputLabel styleClass="desc" value="CEP"></h:outputLabel>
												<h:inputText styleClass="field text" id="cep" maxlength="10" size="10" value="#{clienteBB.cep}" required="false">
													<f:validateLength maximum="10" />
												</h:inputText>
												<h:message for="cep" styleClass="msgErro" />									
											</div>
										</li>
										<li class="normal">
											<div>
												<h:outputLabel styleClass="desc" value="Fone Residencial"></h:outputLabel>
												<h:inputText styleClass="field text" id="foneResidencial" maxlength="13" size="13" value="#{clienteBB.foneResidencial}" required="false">
													<f:validateLength maximum="13" />
												</h:inputText>
												<h:message for="foneResidencial" styleClass="msgErro" />
											</div>								
											<div>
												<h:outputLabel styleClass="desc" value="Fone Comercial"></h:outputLabel>
												<h:inputText styleClass="field text" id="foneComercial" maxlength="13" size="13" value="#{clienteBB.foneComercial}" required="false">
													<f:validateLength maximum="13" />
												</h:inputText>
												<h:message for="foneComercial" styleClass="msgErro" />									
											</div>								
											<div>
												<h:outputLabel styleClass="desc" value="Fone Celular"></h:outputLabel>
												<h:inputText styleClass="field text" id="foneCelular" maxlength="13" size="13" value="#{clienteBB.foneCelular}" required="false">
													<f:validateLength maximum="13" />
												</h:inputText>
												<h:message for="foneCelular" styleClass="msgErro" />									
											</div>
										</li>
										<li class="normal">
											<div>
												<h:outputLabel styleClass="desc" value="Nome Contato"></h:outputLabel>
												<h:inputText styleClass="field text" id="pessoaContato" maxlength="50" size="50" value="#{clienteBB.pessoaContato}" required="false">
													<f:validateLength maximum="50" />
												</h:inputText>
												<h:message for="pessoaContato" styleClass="msgErro" />									
											</div>
											<div>
												<h:outputLabel styleClass="desc" value="Fone Contato"></h:outputLabel>
												<h:inputText styleClass="field text" id="foneContato" maxlength="13" size="13" value="#{clienteBB.foneContato}" required="false">
													<f:validateLength maximum="13" />
												</h:inputText>
												<h:message for="foneContato" styleClass="msgErro" />									
											</div>
										</li>
									</ul>
								</div>	
								<ul>
									<li class="buttons">
										<h:commandButton styleClass="btTxt" immediate="true" id="botaoLimpar" type="reset" value="Limpar"></h:commandButton>
										<h:commandButton styleClass="btTxt" id="botaoInserir" action="#{clienteBB.inserir}" value="Inserir"></h:commandButton>
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