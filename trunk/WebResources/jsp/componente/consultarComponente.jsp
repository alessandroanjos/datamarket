<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="https://ajax4jsf.dev.java.net/ajax" prefix="a4j"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

	<head>

		<title>INFINITY - DataMarket - Enterprise Server</title>

		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
		
		<script type="text/javascript" src="/EnterpriseServer/js/jquery.js"></script>
		<script type="text/javascript" src="/EnterpriseServer/js/global.js"></script>
		<t:stylesheet path="/EnterpriseServer/css/form.css"></t:stylesheet>
		<t:stylesheet path="/EnterpriseServer/css/default.css"></t:stylesheet>
	</head>
	<f:view>
		<h:form id="frmConsultarComponente">
				<f:loadBundle basename="resources.mensagens" var="msgs"/>
				<f:subview id="subTopo" rendered="true">
					<jsp:include page="/jsp/topo.jsp?tituloPagina=#{msgs.consultarComponente}&user=#{loginBB.usuarioLogado.nome}"></jsp:include>	
				</f:subview>					
				<div id="content">
						<div id="primarioContentContainer">
							<fieldset>
								<legend>Op��es de filtro:</legend>
								<ul>
									<li class="normal">
										<div>
											<h:outputLabel styleClass="desc" value="C�digo"></h:outputLabel>
											<h:inputText styleClass="field text ativo" id="id" maxlength="4"
												value="#{componenteBB.id}" size="4" required="false">
												<f:validateLength maximum="4" />
												<f:validator validatorId="LongValidator"/>
											</h:inputText>
											<h:message for="id" styleClass="msgErro" />
										</div>
									</li>
									<li>
										<div>
											<h:outputLabel styleClass="desc" value="Descri��o"></h:outputLabel>
											<h:inputText styleClass="field text" id="descricao" maxlength="50" size="50"
												value="#{componenteBB.descricao}">
												<f:validateLength maximum="50" />
											</h:inputText>
											<h:message for="descricao" styleClass="msgErro" />
										</div>
									</li>
								</ul>
							</fieldset>	
							<div class="listagem">
								<t:dataTable id="componentes" value="#{componenteBB.componentes}"
									var="componente" rowClasses="rowA,rowB" rows="5" width="100%" preserveDataModel="false" renderedIfEmpty="false">
									<h:column>
										<f:facet name="header">
											<h:outputText value="C�digo" /> 
										</f:facet>
										<h:outputText value="#{componente.id}" /> 
									</h:column>
									<h:column>
										<f:facet name="header">
											<h:outputText value="Descri��o" />
										</f:facet>
										<h:commandLink value="#{componente.descricao}" action="#{componenteBB.consultar}">
											<f:param name="id" value="#{componente.id}"/>						
										</h:commandLink>
									</h:column>
								</t:dataTable>
								<t:dataScroller 
									for="componentes" 
									id="paginacao" 
									pageCountVar="pageCount" 
									pageIndexVar="pageIndex"
									fastStep="10" 
									paginator="true"
									paginatorActiveColumnStyle="font-weight;bold;" renderFacetsIfSinglePage="false">
									<f:facet name="first">
										<t:outputText value="Primeira"></t:outputText>
									</f:facet>
									<f:facet name="previous">
										<t:outputText value="Anterior"></t:outputText>
									</f:facet>
									<f:facet name="next">
										<t:outputText value="Pr�xima"></t:outputText>
									</f:facet>
									<f:facet name="last">
										<t:outputText value="�ltima"></t:outputText>
									</f:facet>
								</t:dataScroller>								
							</div>
							<ul>
								<li class="buttons">
									<h:commandButton styleClass="btTxt" immediate="true" id="botaoLimpar" type="reset" value="Limpar"></h:commandButton>
									<h:commandButton styleClass="btTxt" id="botaoConsultar" action="#{componenteBB.consultar}" value="Consultar"></h:commandButton>
								</li>						
							</ul>
						</div>
						<div class="clear"></div>
					</div>
					<jsp:include page="/jsp/rodape.jsp"></jsp:include>
				</div>
		</h:form>
	</f:view>
</html>