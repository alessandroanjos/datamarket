<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
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
		<script type="text/javascript" src="/EnterpriseServer/js/funcoes.js"></script>
		<t:stylesheet path="/EnterpriseServer/css/form.css"></t:stylesheet>
		<t:stylesheet path="/EnterpriseServer/css/default.css"></t:stylesheet>

	</head>
	<f:view>


		<h:form id="frmManterAjusteEstoque">
			<f:loadBundle basename="resources.mensagens" var="msgs" />
			<f:subview id="subTopo" rendered="true">
				<jsp:include
					page="/jsp/topo.jsp?tituloPagina=#{msgs.manterAjusteEstoque}&user=#{loginBB.usuarioLogado.nome}"></jsp:include>
			</f:subview>
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
								<h:outputLabel styleClass="desc" value="C�digo*"></h:outputLabel>
								<h:outputLabel styleClass="desc" value="#{ajusteEstoqueBB.id}"></h:outputLabel>
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Estoque"></h:outputLabel>
								<h:outputLabel styleClass="desc" value="#{ajusteEstoqueBB.ajusteEstoque.estoque.descricao}"></h:outputLabel>
							</div>
						</li>

						<li class="normal">
							<div>
								<h:outputLabel styleClass="desc" value="C�digo Produto"></h:outputLabel>
								<h:outputLabel styleClass="desc" value="#{ajusteEstoqueBB.idProduto}"></h:outputLabel>
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="C�digo Produto*"></h:outputLabel>
								<h:outputLabel styleClass="desc" value="#{ajusteEstoqueBB.produto.descricaoCompleta}"></h:outputLabel>
								
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Qtd.Antes"></h:outputLabel>
								<h:outputLabel styleClass="desc" value="#{ajusteEstoqueBB.quantidadeAntes}"></h:outputLabel>								
							</div>
							<div>
								<h:outputLabel styleClass="desc" value="Qtd.Depois"></h:outputLabel>
								<h:outputLabel styleClass="desc" value="#{ajusteEstoqueBB.quantidadeDepois}"></h:outputLabel>								
								
							</div>
						</li>	

						<li class="buttons">
							<h:commandButton styleClass="btTxt" id="botaoVoltar"
								action="#{ajusteEstoqueBB.voltarConsulta}" value="Voltar"></h:commandButton>
						</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<jsp:include page="/jsp/rodape.jsp"></jsp:include>
		</h:form>
				
	</f:view>
</html>
