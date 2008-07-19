<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t" %>
<%@ taglib uri="https://ajax4jsf.dev.java.net/ajax" prefix="a4j" %>

<f:verbatim>		
<script type="text/javascript">

			window.onload = function(){ inicializar() };
			
			function inicializar() {
				$("input.field, select.field").each(function(i){
					$(this).focus(function() {this.style.backgroundColor = "#eff6ff"});
					$(this).blur(function() {this.style.backgroundColor = "white"});
				});
			}
			
			function returnDay(day){
				var day_of_week = "";
				switch (parseInt(day)){
					case parseInt("1"): day_of_week = "Domingo";break;
					case parseInt("2"): day_of_week = "Segunda-Feira";break;
					case parseInt("3"): day_of_week = "Terça-Feira";break;
					case parseInt("4"): day_of_week = "Quarta-Feira";break;
					case parseInt("5"): day_of_week = "Quinta-Feira";break;
					case parseInt("6"): day_of_week = "Sexta-Feira";break;
					case parseInt("7"): day_of_week = "Sábado";break;
				}
				return day_of_week;
			}
			var today_date= new Date();
			var month=today_date.getMonth()+1;
			var today=today_date.getDate();
			var year=today_date.getFullYear();
			var day=returnDay(today_date.getDay()+1);
			today = new String(today);
			if(today.length==1)
				today="0"+today;
			month = new String(month);
			if(month.length==1)
				month="0"+month;
 </script>

	 <div id="header">
		<div class="centro">
			<div id="loginUser">
				Usuário:
				<strong>
					</f:verbatim>	
						<% if(!request.getParameter("user").equals("Desconhecido")) { %>			
							<h:outputText value="#{loginBB.usuarioLogado.nome}"></h:outputText>
						<% } else { %>
							<f:verbatim>	
								<strong>Desconhecido</strong>
							</f:verbatim>	
						<% } %>
					<f:verbatim>
				</strong>				
				&nbsp;&nbsp;&nbsp;&#8212;&nbsp;&nbsp;&nbsp;
				<script type="text/javascript">document.write(day + ", "+today+"/"+month+"/"+year)</script>
			</div>
			<div id="breadcrumb"><strong>Infinity</strong> - DataMarket - Enterprise Server</div>
		</div>
	</div> 
</f:verbatim>	
<f:verbatim>
<div id="outer">
<div id="topo">
	<h1>&nbsp;</h1>
	<h2>&nbsp;</h2>
	<div id="logoCliente"><img src="/EnterpriseServer/images/logoCliente.gif" alt="" title="" /></div>
</div>
</f:verbatim>