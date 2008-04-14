<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="https://ajax4jsf.dev.java.net/ajax" prefix="a4j"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>

	<title>INFINITY - DataMarket - Enterprise Server</title>
	
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
	<meta http-equiv="description" content="This is my page" />
	
	<script type="text/javascript" src="/EnterpriseServer/js/jquery.js"></script>
	<script type="text/javascript" src="/EnterpriseServer/js/global.js"></script>
	
<script type="text/javascript">

window.onload = function(){ inicializar() };

function inicializar() {
	$("input.field, select.field").each(function(i){
		$(this).focus(function() {this.style.backgroundColor = "#eff6ff"});
		$(this).blur(function() {this.style.backgroundColor = "white"});
	});
}


</script>

</head>
<f:view>  

<t:stylesheet path="/css/form.css"></t:stylesheet>
<t:stylesheet path="/css/default.css"></t:stylesheet>

<body>

<div id="outer">
	<t:jscookMenu id="menuPrincipal" layout="hbr" theme="ThemeOffice">
		<t:navigationMenuItems value="#{loginBB.navItens}" />
	</t:jscookMenu>
	<h:form>
	<div id="topo">
		<h1>&nbsp;</h1>
		<h2>&nbsp;</h2>
		<div id="tituloPagina"><strong>Tipo de Produtos</strong></div>
		<div id="logoCliente"><img src="/EnterpriseServer/images/logoCliente.gif" alt="Magia dos Pães" title="Magia dos Pães" /></div>
	</div>
	<div id="header">
		<div class="centro">
			<div id="loginUser">Usuário: <strong><h:outputText id="usuario" value="#{loginBB.usuarioLogado.nome}"></h:outputText></strong> &nbsp;&nbsp;&nbsp;&#8212;&nbsp;&nbsp;&nbsp; Domingo, 23/03/2008</div>
			<div id="breadcrumb"><strong>Infinity</strong> - DataMarket - Enterprise Server</div>
		</div>
	</div>
	<div id="content">
	
		<div id="primarioContentContainerInternas">
				<ul>
					<li class="normal">
						<div>
							<label class="desc"><h:outputText value="Codigo*"></h:outputText></label>
							<span><h:inputText styleClass="field text ativo" id="id" maxlength="2"
								value="#{tipoProdutoBB.id}" size="2" required="true">
								<f:validateLength maximum="2" />
								<f:validator validatorId="LongValidator"/>
							</h:inputText>
							<h:message for="id" styleClass="msgErro"/></span>
						</div>
					</li>
					<li class="normal">
						<div>
							<label class="desc"><h:outputText value="Descrição*"></h:outputText></label>
							<span><h:inputText styleClass="field text" id="descricao" maxlength="50" size="50"
								value="#{tipoProdutoBB.descricao}" required="true">
								<f:validateLength maximum="50" />
							</h:inputText>			
							<h:message for="descricao" styleClass="msgErro"/></span>
						</div>
					</li>
					<li class="buttons">
				
						<h:commandButton styleClass="btTxt" immediate="true" id="botaoLimpar" type="reset" value="Limpar"></h:commandButton>
						<h:commandButton styleClass="btTxt" immediate="true" id="botaoInserir" action="#{tipoProdutoBB.inserir}" value="Inserir"></h:commandButton>
	
					</li>
				</ul>
		</div>
		<div class="clear"></div>
	</h:form>	
	</div>
	<div id="footer">
		<p>Todos os direitos reservados &copy; 2008 | <a href="http://www.infinity.com.br">www.infinity.com.br</a></p>
	</div>
</div>
</body>
</f:view>
</html>