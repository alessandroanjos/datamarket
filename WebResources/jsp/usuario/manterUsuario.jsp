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
      	$("input.ehvendedor").each(function(i){
      		$(this).click(function() {mostraComissao(this.value)});
      	});
		if ($('[name=frmManterUsuario:vendedor]:checked').val() != "undefined") {
			mostraComissao($('[name=frmManterUsuario:vendedor]:checked').val())
		}
      }

      function mostraComissao(str) {
        //frmManterUsuario:comissao
        var flag = new String(str);
        if (flag.toUpperCase() == "N") {
         	desabilita("frmManterUsuario:comissao");
        } else {
         	habilita("frmManterUsuario:comissao");
        }
      
      }

      </script>
		</head>
		<body>			
			<div id="outer">
				<div id="topoGeral">
					<div id="tituloPaginaGeral">
						<strong>
							<h:outputText value="#{msgs.manterUsuario}"></h:outputText>
						</strong>
					</div>				
				</div>
				<div id="content">
					<div id="tabMenu">
						<ul>
							<li id="tabMenuDiv0" class="current" onclick="selecionaMenuTab(this.id)"><span><a href="#">Usu�rio</a></span></li>
							<li id="tabMenuDiv1" onclick="selecionaMenuTab(this.id)"><span><a href="#">Lojas Associadas</a></span></li>
						</ul>
						<div class="clear"></div>
					</div>
					<div id="primarioContentContainerInternas">
					<h:form id="frmManterUsuario">
						<div id="tabDiv0">						
							<ul>
								<li class="normal">
									<div>
										<h:messages errorClass="msgSistemaErro" infoClass="msgSistemaSucesso" globalOnly="true" showDetail="true"/>
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="C�digo*"></h:outputLabel>
										<h:inputText styleClass="field text ativo" id="id" maxlength="2" onkeypress="return SoNumero(event);"
											value="#{usuarioBB.id}" size="3" required="true" disabled="true">
											<f:validateLength maximum="2" />
											<f:validator validatorId="LongValidator"/>
										</h:inputText>
									</div>
									<h:message for="id" styleClass="msgErro"/>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Nome*"></h:outputLabel>
										<h:inputText styleClass="field text" id="nome" maxlength="50" size="50" required="true"
											value="#{usuarioBB.nome}">
											<f:validateLength maximum="50" />
										</h:inputText>
									</div>
									<h:message for="nome" styleClass="msgErro"/>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Senha Num�rica*"></h:outputLabel>
										<h:inputSecret styleClass="field text" id="senha" redisplay="true" maxlength="20" size="25"
											value="#{usuarioBB.senha}" required="true" >
											<f:validateLength maximum="20" />
											<f:validator validatorId="LongValidator"/>
										</h:inputSecret>
									</div>
									<h:message for="senha" styleClass="msgErro"/>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Perfil*"></h:outputLabel>
										<h:selectOneMenu id="perfis" styleClass="field select"
												value="#{usuarioBB.idPerfil}" required="true" style="width: 200px;">   
													  <f:selectItems id="perfilSelectItems" 
													  value="#{usuarioBB.perfis}" />   
											</h:selectOneMenu>
									</div>
									<h:message for="perfis" styleClass="msgErro"/>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Vendedor*"></h:outputLabel>
										<h:selectOneRadio  styleClass="field radio ehvendedor" id="vendedor" 
											value="#{usuarioBB.idTipoUsuario}" layout="lineDirection" required="true" rendered="true">
										    <f:selectItems id="situacao" value="#{usuarioBB.tiposUsuario}" />
										</h:selectOneRadio>	
									</div>
									<h:message for="vendedor" styleClass="msgErro"/>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Perc. Comiss�o"></h:outputLabel>
										<h:inputText styleClass="field text" id="comissao" maxlength="5" size="5" onkeypress="return SoNumero(event);" onkeydown="Formata('frmManterUsuario:comissao',3,2,event);"
											value="#{usuarioBB.comissao}" required="false" dir="rtl">
											<f:validateLength maximum="5" />
											<f:validateDoubleRange  minimum="0.00" maximum="100.00"/>
											<f:validator validatorId="BigDecimalValidator"/>
										</h:inputText>
									</div>
									<h:message for="comissao" styleClass="msgErro"/>		
								</li>
							</ul>
						</div>
						<div id="tabDiv1" style="display:none;">
							<ul>
								<li class="normal">
									<div class="div-auto-scroll" style="width:400px !important; height: 242px;">
										<h:selectManyCheckbox id="idListaLojasAssociadas" layout="pageDirection" required="false" styleClass="field select"
											value="#{usuarioBB.listaLojasAssociadas}" >
												<f:selectItems value="#{usuarioBB.lojas}"/>
										</h:selectManyCheckbox>	
									</div>									
									<h:message for="idListaLojasAssociadas" styleClass="msgErro"/>
								</li>															
							</ul>
						</div>
						<ul>
							<li class="buttons">
								<h:commandButton immediate="true" styleClass="btTxt" id="botaoVoltar" action="#{usuarioBB.voltarConsulta}" value="Voltar"></h:commandButton>
								<h:commandButton styleClass="btTxt" id="botaoAlterar" action="#{usuarioBB.alterar}" value="Alterar"></h:commandButton>
								<h:commandButton styleClass="btTxt" id="botaoExcluir" action="#{usuarioBB.excluir}" value="Excluir"></h:commandButton>
							</li>
						</ul>
					</h:form>
				</div>
				<div class="clear"></div>
			</div>
		</body>
	</f:view>
</html>
