<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="https://ajax4jsf.dev.java.net/ajax" prefix="a4j"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<f:view>
		<f:loadBundle basename="resources.mensagens" var="msgs" />
		<head>

			<title><h:outputText value="#{msgs.tituloPaginas}"></h:outputText>
			</title>

			<meta http-equiv="pragma" content="no-cache" />
			<meta http-equiv="cache-control" content="no-cache" />
			<meta http-equiv="expires" content="0" />
			<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
			<meta http-equiv="description" content="This is my page" />

			<script type="text/javascript" src="/js/jquery.js"></script>
			<script type="text/javascript" src="/js/global.js"></script>

			<t:stylesheet path="/css/default.css"></t:stylesheet>
			<t:stylesheet path="/css/form.css"></t:stylesheet>
			<script>
			   function send(idProduto,descricaoCompleta) {
				    window.opener.setAtributo(idProduto,descricaoCompleta);
			   }	
			</script>
		</head>

		<h:form id="frmPopUpProduto">
			<div id="content">

				<div id="primarioContentContainerInternas">
					<fieldset>
						<legend>
							Op��es de filtro:
						</legend>
						<ul>
							<li class="normal">
								<div>
									<h:outputLabel styleClass="desc" value="C�digo"></h:outputLabel>
									<h:inputText styleClass="field text ativo" id="id"
										maxlength="4" value="#{produtoBB.id}" size="4"
										required="false">
										<f:validateLength maximum="4" />
										<f:validator validatorId="LongValidator" />
									</h:inputText>
									<h:message for="id" styleClass="msgErro" />
								</div>
								<div>
									<h:outputLabel styleClass="desc" value="Descri��o"></h:outputLabel>
									<h:inputText styleClass="field text" id="descricaoCompleta"
										maxlength="50" size="50"
										value="#{produtoBB.descricaoCompleta}">
										<f:validateLength maximum="50" />
									</h:inputText>
									<h:message for="descricao" styleClass="msgErro" />
								</div>
							</li>
						</ul>
					</fieldset>
					<div class="listagem">
						<t:dataTable value="#{produtoBB.produtos}" var="produto"
							rowClasses="rowA,rowB" width="100%">
							<h:column>
								<f:facet name="header">
									<h:outputText value="C�digo" />
								</f:facet>
								<h:outputText value="#{produto.id}" />
							</h:column>
							<h:column>
								<f:facet name="header">
									<h:outputText value="Descri��o" />
								</f:facet>
								<h:outputText value="#{produto.descricaoCompleta}" />
							</h:column>
							<h:column>
								<h:commandButton type="button" image="/images/tree/drop-yes.gif"
									onclick="send('#{produto.id}','#{produto.descricaoCompleta}')"
									alt="selecionar produto"></h:commandButton>
							</h:column>
						</t:dataTable>
					</div>
					<ul>
						<li class="buttons">
							<h:commandButton styleClass="btTxt" id="botaoCancelar"
								onclick="window.close();" value="Cancelar"></h:commandButton>
							<h:commandButton styleClass="btTxt" id="botaoConsultar"
								action="#{produtoBB.consultar}" value="Consultar"></h:commandButton>
						</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			</div>
		</h:form>
	</f:view>
</html>