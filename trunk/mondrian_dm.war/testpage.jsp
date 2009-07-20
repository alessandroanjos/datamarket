<%@ page session="true" contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="http://www.tonbeller.com/jpivot" prefix="jp" %>
<%@ taglib uri="http://www.tonbeller.com/wcf" prefix="wcf" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<%--

  JPivot / WCF comes with its own "expression language", which simply
  is a path of properties. E.g. #{customer.address.name} is
  translated into:
    session.getAttribute("customer").getAddress().getName()
  WCF uses jakarta commons beanutils to do so, for an exact syntax
  see its documentation.

  With JSP 2.0 you should use <code>#{}</code> notation to define
  expressions for WCF attributes and <code>\${}</code> to define
  JSP EL expressions.

  JSP EL expressions can not be used with WCF tags currently, all
  tag attributes have their <code>rtexprvalue</code> set to false.
  There may be a twin library supporting JSP EL expressions in
  the future (similar to the twin libraries in JSTL, e.g. core
  and core_rt).

  Check out the WCF distribution which contains many examples on
  how to use the WCF tags (like tree, form, table etc).

--%>

<html>
<head>
  <title>GCOM OLAP</title>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <link rel="stylesheet" type="text/css" href="jpivot/table/mdxtable.css">
  <link rel="stylesheet" type="text/css" href="jpivot/navi/mdxnavi.css">
  <link rel="stylesheet" type="text/css" href="wcf/form/xform.css">
  <link rel="stylesheet" type="text/css" href="wcf/table/xtable.css">
  <link rel="stylesheet" type="text/css" href="wcf/tree/xtree.css">
  <link rel="stylesheet" href="css/EstilosCompesa.css" type="text/css">

</head>
<body bgcolor="#CBE5FE">


<form action="testpage.jsp" method="post">

<table width="100%" border="0"><tr><td>

<%@ include file="cabecalho.jsp"%>

<%-- include query and title, so this jsp may be used with different queries --%>

<%
String parametro = request.getParameter("parametro");
if (parametro != null) {
	session.setAttribute("parametro",parametro);
	//out.write("sessao");
}

//out.write((String)session.getAttribute("parametro"));
%>

<c:if test="${sessionScope.parametro == 'ConsolidacaoVenda'}">
	<h1>Consolida&ccedil;&atilde;o das vendas</h1>
</c:if>

<wcf:include id="include01" httpParam="query" prefix="/WEB-INF/queries/" suffix=".jsp"/>


<c:if test="${query01 == null}">
	
  <jsp:forward page="/testpage.jsp?query=mondrian&parametro=<%=parametro%>"/>;
   
</c:if>


<%-- define table, navigator and forms --%>
<jp:table id="table01" query="#{query01}"/>
<jp:navigator id="navi01" query="#{query01}" visible="false"/>
<%--<wcf:form id="mdxedit01" xmlUri="/WEB-INF/jpivot/table/mdxedit.xml" model="#{query01}" visible="false"/>--%>
<wcf:form id="sortform01" xmlUri="/WEB-INF/jpivot/table/sortform.xml" model="#{table01}" visible="false"/>


<jp:print id="print01"/>
<wcf:form id="printform01" xmlUri="/WEB-INF/jpivot/print/printpropertiesform.xml" model="#{print01}" visible="false"/>

<jp:chart id="chart01" query="#{query01}" visible="false"/>
<wcf:form id="chartform01" xmlUri="/WEB-INF/jpivot/chart/chartpropertiesform.xml" model="#{chart01}" visible="false"/>
<wcf:table id="query01.drillthroughtable" visible="false" selmode="none" editable="true"/>

<%--<h2><c:out value="${title01}"/></h2>--%>

<%-- define a toolbar --%>
<wcf:toolbar id="toolbar01" bundle="com.tonbeller.jpivot.toolbar.resources">
  <wcf:scriptbutton id="cubeNaviButton" tooltip="toolb.cube" img="cube" model="#{navi01.visible}"/>
<%--  <wcf:scriptbutton id="mdxEditButton" tooltip="toolb.mdx.edit" img="mdx-edit" model="#{mdxedit01.visible}"/>--%>
  <wcf:scriptbutton id="sortConfigButton" tooltip="toolb.table.config" img="sort-asc" model="#{sortform01.visible}"/>
  <wcf:separator/>
<%--  <wcf:scriptbutton id="levelStyle" tooltip="toolb.level.style" img="level-style" model="#{table01.extensions.axisStyle.levelStyle}"/>--%>
<%--  <wcf:scriptbutton id="hideSpans" tooltip="toolb.hide.spans" img="hide-spans" model="#{table01.extensions.axisStyle.hideSpans}"/>--%>
<%--  <wcf:scriptbutton id="propertiesButton" tooltip="toolb.properties"  img="properties" model="#{table01.rowAxisBuilder.axisConfig.propertyConfig.showProperties}"/>--%>
  <wcf:scriptbutton id="nonEmpty" tooltip="toolb.non.empty" img="non-empty" model="#{table01.extensions.nonEmpty.buttonPressed}"/>
  <wcf:scriptbutton id="swapAxes" tooltip="toolb.swap.axes"  img="swap-axes" model="#{table01.extensions.swapAxes.buttonPressed}"/>

  <wcf:separator/>

  <wcf:scriptbutton model="#{table01.extensions.drillMember.enabled}"	 tooltip="toolb.navi.member" radioGroup="navi" id="drillMember"   img="navi-member"/>
  <wcf:scriptbutton model="#{table01.extensions.drillPosition.enabled}" tooltip="toolb.navi.position" radioGroup="navi" id="drillPosition" img="navi-position"/>
  <wcf:scriptbutton model="#{table01.extensions.drillReplace.enabled}"	 tooltip="toolb.navi.replace" radioGroup="navi" id="drillReplace"  img="navi-replace"/>
  <wcf:scriptbutton model="#{table01.extensions.drillThrough.enabled}"  tooltip="toolb.navi.drillthru" id="drillThrough01"  img="navi-through"/>
  <wcf:separator/>
  <wcf:scriptbutton id="chartButton01" tooltip="toolb.chart" img="chart" model="#{chart01.visible}"/>
  <wcf:scriptbutton id="chartPropertiesButton01" tooltip="toolb.chart.config" img="chart-config" model="#{chartform01.visible}"/>
  <wcf:separator/>
  <wcf:scriptbutton id="printPropertiesButton01" tooltip="toolb.print.config" img="print-config" model="#{printform01.visible}"/>
  <wcf:imgbutton id="printpdf" tooltip="toolb.print" img="print" href="./Print?cube=01&type=1"/>
  <wcf:imgbutton id="printxls" tooltip="toolb.excel" img="excel" href="./Print?cube=01&type=0"/>
</wcf:toolbar>

<%-- render toolbar --%>
<wcf:render ref="toolbar01" xslUri="/WEB-INF/jpivot/toolbar/htoolbar.xsl" xslCache="true"/>

<p>

<%-- if there was an overflow, show error message --%>
<c:if test="${query01.result.overflowOccured}">
  <p>
  <strong style="color:red">Resultset overflow occured</strong>
  <p>
</c:if>

<%-- render navigator --%>
<wcf:render ref="navi01" xslUri="/WEB-INF/jpivot/navi/navigator.xsl" xslCache="true"/>

<%-- edit mdx --%>
<%--<c:if test="${mdxedit01.visible}">
  <h3>MDX Query Editor</h3>
  <wcf:render ref="mdxedit01" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
</c:if>
--%>

<%-- sort properties --%>
<wcf:render ref="sortform01" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>

<%-- chart properties --%>
<wcf:render ref="chartform01" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>

<%-- print properties --%>
<wcf:render ref="printform01" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>

<!-- render the table -->
<p>
<wcf:render ref="table01" xslUri="/WEB-INF/jpivot/table/mdxtable.xsl" xslCache="true"/>
<p>
Slicer:
<wcf:render ref="table01" xslUri="/WEB-INF/jpivot/table/mdxslicer.xsl" xslCache="true"/>

<p>
<!-- drill through table -->
<wcf:render ref="query01.drillthroughtable" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>

<p>
<!-- render chart -->
<wcf:render ref="chart01" xslUri="/WEB-INF/jpivot/chart/chart.xsl" xslCache="true"/>

<p>

<c:if test="${sessionScope.parametro == 'ResumoIndicadorDesempenhoMicromedicao'}">
<br><p><font size ="1"><strong>&Iacute;ndice de Medi&ccedil;&atilde;o de Consumo - Nominal: </strong>(Quantidade Liga&ccedil;&otilde;es com Hidr&ocirc;metro/Quantidade Liga&ccedil;&otilde;es Ativas) * 100<br />
<strong>&Iacute;ndice de Medi&ccedil;&atilde;o de Consumo - Efetivo:</strong>   (Quantidade de Liga&ccedil;&otilde;es com Medi&ccedil;&atilde;o Rea l/Quantidade de Liga&ccedil;&otilde;es Ativa) * 100<strong><br />
  Efici&ecirc;ncia da Medi&ccedil;&atilde;o de Consumo</strong>: (Quantidade de Liga&ccedil;&otilde;es com Medi&ccedil;&atilde;o Real/Quantidade de Liga&ccedil;&otilde;es com Hidr&ocirc;metro) * 100<br />
<strong>Efici&ecirc;ncia da Leitura</strong>: (Quantidade de Leituras Efetuadas/ Quantidade de Visitas Realizadas) * 100</font><br />
<font size ="1"><strong>Percentual de Hidr&ocirc;metros com Anormalidade</strong>: (Quantidade de Leituras de Hidr&ocirc;metros com Anormalidade/Quantidade de Liga&ccedil;&otilde;es com Hidr&ocirc;metro) * 100<br />
<strong>Volume M&eacute;dio das Liga&ccedil;&otilde;es com Hidr&ocirc;metro - Consumo Real</strong>: Consumo de &Aacute;gua com Medi&ccedil;&atilde;o Real/Quantidade de Liga&ccedil;&otilde;es com Medi&ccedil;&atilde;o Real<br />
<strong>Volume M&eacute;dio das Liga&ccedil;&otilde;es com Hidr&ocirc;metro - Consumo Estimado</strong>: Consumo de &Aacute;gua com Medi&ccedil;&atilde;o Estimada/Quantidade de Liga&ccedil;&otilde;es com Hidr&ocirc;metro e Medi&ccedil;&atilde;o Estimada<br />
<strong>Volume Consumido por Economia</strong>: Consumo Ativo de &Aacute;guas/ Quantidade de Economias Ativas<br />
<strong>Volume Consumido por Liga&ccedil;&atilde;o</strong>: Consumo Ativo de &Aacute;guas / Quantidade de Liga&ccedil;&otilde;es Ativas<br />
<strong>Percentual de Volume com Hidr&ocirc;metro - Consumo Real</strong>: (Consumo de &Aacute;gua com Medi&ccedil;&atilde;o Real* 100) /(Consumo de &Aacute;gua com Medi&ccedil;&atilde;o Real  +Consumo de &Aacute;gua com Medi&ccedil;&atilde;o Estimada)<br />
</font><font size ="1"><strong>Percentual de Volume com Hidr&ocirc;metro - Consumo Estimado:</strong> (Consumo de &Aacute;gua com Medi&ccedil;&atilde;o Estimada * 100) /(Consumo de &Aacute;gua com Medi&ccedil;&atilde;o Real  +Consumo de &Aacute;gua com Medi&ccedil;&atilde;o Estimada)</font></p> 


</c:if>

<c:if test="${sessionScope.parametro == 'ResumoIndicadorLigacaoEconomia'}">
	<br><font size ="1"><strong>&Iacute;ndice de Crescimento de Liga&ccedil;&otilde;es &Aacute;gua(%):</strong> (Quantidade Liga&ccedil;&otilde;es Ativas &Agrave;gua - Quantidade Liga&ccedil;&otilde;es Ativas &Agrave;gua M&ecirc;s Anterior) / (Quantidade Liga&ccedil;&otilde;es Ativas &Agrave;gua M&ecirc;s Anterior)*100 <br />
<strong>&Iacute;ndice de Crescimento de Liga&ccedil;&otilde;es Esgoto(%)</strong>: (Quantidade Liga&ccedil;&otilde;es Ativas Esgoto - Quantidade Liga&ccedil;&otilde;es Ativas Esgoto M&ecirc;s Anterior) / (Quantidade Liga&ccedil;&otilde;es Ativas Esgoto M&ecirc;s Anterior)*100 <br />
<strong>&Iacute;ndice de Crescimento de Economias &Agrave;gua(%):</strong> (Quantidade Economias Totais &Agrave;gua - Quantidade Economias Totais &Agrave;gua M&ecirc;s Anterior) / (Quantidade Economias Totais &Agrave;gua M&ecirc;s Anterior)*100 <br />
<strong>&Iacute;ndice de Crescimento de Economias Esgoto(%):</strong> (Quantidade Economias Totais Esgoto - Quantidade Economias Totais Esgoto M&ecirc;s Anterior) / (Quantidade Economias Totais Esgoto M&ecirc;s Anterior)*100 </font>
</c:if>


<%--<input type="button" class="bottonRightCol" value="Voltar" style="width: 80px" 
onclick="javascript:window.location.href = 'http://www.compesa.com.br/gcom/exibirSelecionarRelatorioGerencialAction.do'" />
<a href="http://www.compesa.com.br/gcom/exibirSelecionarRelatorioGerencialAction.do">Voltar</a>--%>

</td></tr></table>

</form>


</body>
</html>










