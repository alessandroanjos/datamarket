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
		
			
<t:stylesheet path="/css/estilo.css"></t:stylesheet>
			<t:stylesheet path="/css/default.css"></t:stylesheet>
			<t:stylesheet path="/css/form.css"></t:stylesheet>
      	</head>
		<body>			
			<div id="outer">
				<div id="topoGeral">
					<div id="tituloPaginaGeral">
						<strong>
							<h:outputText value="#{msgs.inserirLoja}"></h:outputText>
						</strong>
					</div>				
				</div>
				
				
				<div class="jqmAlert" id="alerta"><div class="jqmAlertWindow"><div class="jqmAlertTitle clearfix"><a href="#" onclick="javascript:fecharAlerta();" class="jqmClose"><em>Fechar</em></a><h1>Alerta</h1></div><div class="jqmAlertContent" id="jqmAlertContent"></div><input type="button" value="Ok" onclick="javascript:fecharAlerta();" /></div></div><div id="content">
					<div id="primarioContentContainerInternas" >
						<h:form id="frmInserirLoja"  binding="#{lojaBB.init}" >
					        <ul>
								<div>
									<%@ include file="/jsp/mensagem_erro.jsp"%> <!--  h  messages rendered="#{not planoPagamentoChequePreBB.existeRegistros}" errorClass="msgSistemaErro" infoClass="msgSistemaSucesso" globalOnly="true" showDetail="true" /> -->

								</div>
							
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Código*"></h:outputLabel>
										<h:inputText styleClass="field text ativo" id="id" maxlength="2" 
											onfocus="this.select();" onclick="this.select();"
											onkeypress="return SoNumero(event);"
											value="#{lojaBB.id}" size="2" >
											<f:validateLength maximum="2" />
											<f:validator validatorId="LongValidator" />
										</h:inputText>
									</div>
									
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Nome*"></h:outputLabel>
										<h:inputText styleClass="field text" id="nome" maxlength="50"
											size="50" value="#{lojaBB.nome}" >
											<f:validateLength maximum="50" />
										</h:inputText>
									</div>
									
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Número IP*"></h:outputLabel>
										<h:inputText styleClass="field text" id="ip" maxlength="15"
											size="15" value="#{lojaBB.numeroIp}"  onblur="if (!verificaIP(this.value)) alert(ERRO_ENDERECO_IP);" 
												onfocus="this.select();" onclick="this.select();"
												onkeypress="return SoNumero(event);">
											<f:validateLength maximum="15" />
										</h:inputText>
									</div>		
									
								</li>
								<li class="normal">															
									<div>
										<h:outputLabel styleClass="desc" value="Número Porta*"></h:outputLabel>
										<h:inputText styleClass="field text inteiro" id="numeroPorta" maxlength="4" 
											onfocus="this.select();" onclick="this.select();"
											onkeypress="return SoNumero(event);"
											size="4" value="#{lojaBB.numeroPorta}" >
											<f:validateLength maximum="4" />
										</h:inputText>
									</div>
									
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Estoque Atual*"></h:outputLabel>
										<h:selectOneMenu id="idEstoque" styleClass="field select"
											value="#{lojaBB.idEstoqueAtual}" style="width: 200px;">		
											<f:selectItems id="estoqueSelectItems" value="#{lojaBB.estoques}" />
										</h:selectOneMenu>
										
									</div>
								</li>
								<li class="normal">
									<div>
										<h:outputLabel styleClass="desc" value="Conta Crédito*"></h:outputLabel>
										<h:selectOneMenu id="idConta" styleClass="field select"
											value="#{lojaBB.idContaAtual}" style="width: 200px;">		
											<f:selectItems id="contaSelectItems" value="#{lojaBB.contas}" />
										</h:selectOneMenu>
									</div>
								</li>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" action="#{lojaBB.resetBB}" id="botaoLimpar" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoInserir" action="#{lojaBB.inserir}" value="Inserir"></h:commandButton>
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
		