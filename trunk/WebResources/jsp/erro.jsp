<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Login &nbsp;&nbsp;&nbsp;&#8212;&nbsp;&nbsp;&nbsp; Infinity | Data Market - Enterprise Server</title>
		<meta http-equiv="pragma" content="no-cache"/>
		<link rel="icon" xhref="favicon.ico" type="image/x-icon" />
		<link rel="shortcut icon" xhref="favicon.ico" type="image/x-icon" />
		<script type="text/javascript" src="/EnterpriseServer/js/jquery.js"></script>
		<script type="text/javascript" src="/EnterpriseServer/js/global.js"></script>
		<script type="text/javascript" src="/EnterpriseServer/js/funcoes.js"></script>
		<link rel="stylesheet" type="text/css" href="/EnterpriseServer/css/default.css" />
		<link rel="stylesheet" type="text/css" href="/EnterpriseServer/css/form.css" />
<script type="text/javascript">

 </script>

</head>
<body onload=" ">
					
<div id="outer">
<div id="topo">
	
	<div id="logoCliente"><img src="/EnterpriseServer/images/logoCliente.gif" alt="" title="" /></div>
</div>


<form id="frmLogin" name="frmLogin" method="post" action="/EnterpriseServer/jsp/login.faces" enctype="application/x-www-form-urlencoded">
<div id="outer">				
			<div id="primarioContent" style="width:100%;margin: 0em 0em 0em 00em;margin-top: 0em;color: #000;border-bottom: solid 1px #999;padding: 1em;">		
						<ul>
							<li>
								<div>										
									<BR>
									<BR>
									<BR>
									<label class="desc">Erro Desconecido*   
									<BR>
									<BR>
<% if (request.getAttribute("exception") != null){ 

Exception e = (Exception) request.getAttribute("exception");

%>

<%StackTraceElement[] trace = e.getStackTrace();
for (int i = 1;i<trace.length;i++){
 %><%=trace[i]%><br>
<%} %> 
<%
}else{
	%><H1>Erro desconhecido</H1><%
}
%>
									<BR>
									<BR>
<a href="javascript:history.go(-1)">Voltar</a>

							</label>
						</div>
					</li>
				</ul>
		</div>
	</div>
</div>
</form>	
</body>
</html>