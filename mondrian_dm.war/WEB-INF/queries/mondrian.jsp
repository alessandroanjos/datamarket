<%@ page session="true" contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="http://www.tonbeller.com/jpivot" prefix="jp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>


<jp:mondrianQuery queryName="ConsolidacaoVenda" dynLocale="pt"
	jdbcDriver="net.sourceforge.jtds.jdbc.Driver" jdbcUser="sa"
	jdbcPassword="001100"
	jdbcUrl="jdbc:jtds:sqlserver://localhost:1433/ENTERPRISE"
	catalogUri="/WEB-INF/queries/schemaGcomGerencial2.xml" id="query01">


select NON EMPTY {[Measures].[Quantidade],[Measures].[Valor], [Measures].[Lucro], [Measures].[Desconto]} ON COLUMNS,
  NON EMPTY  {[DESCRICAO_COMPLETA.DESCRICAO_COMPLETA].[Todos]} ON ROWS
from [ConsolidacaoVenda]

</jp:mondrianQuery>

<%--<c:set var="parametro" scope="request">ResumoPendencia</c:set>--%>


<%
String parametro = request.getParameter("parametro");

%>

<jp:chooseQuery id="query01" queryName="<%=parametro %>"/>

<c:set var="title01" scope="session">OLAP DataMarket</c:set>


