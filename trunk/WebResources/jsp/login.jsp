<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="https://ajax4jsf.dev.java.net/ajax" prefix="a4j"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!--
	Design e Engenharia de Interface
	por Marco Antônio (marcaopimentel@gmail.com)
	Disponibilizado para Infinity DataMarket
-->
<html xmlns="http://www.w3.org/1999/xhtml">
<f:view>
	<head>
		<title>Login &nbsp;&nbsp;&nbsp;&#8212;&nbsp;&nbsp;&nbsp; Infinity | Data Market - Enterprise Server</title>
		<meta http-equiv="pragma" content="no-cache"/>
		<link rel="icon" xhref="favicon.ico" type="image/x-icon" />
		<link rel="shortcut icon" xhref="favicon.ico" type="image/x-icon" />
		<script type="text/javascript" src="/EnterpriseServer/js/jquery.js"></script>
		<script type="text/javascript" src="/EnterpriseServer/js/global.js"></script>
		<t:stylesheet path="/css/default.css" enabledOnUserRole="true"></t:stylesheet>
		<t:stylesheet path="/css/form.css" enabledOnUserRole="true"></t:stylesheet>
	</head>
			<jsp:include page="/jsp/topo.jsp?user=Desconhecido"></jsp:include>	
			<h:form>
			<div id="outer">				
				<div id="content" style="margin-top:20px;">
					<div id="primarioContentContainer" style="margin-left:none;">
						<div id="primarioContent" style="width:200px;margin: 0em 0em 0em 20em;">			
							<!-- <div class="box">
								<h2>Bem Vindo!</h2>
								<div class="boxContent">
									<p>Este sistema é parte de um conjunto de aplicações que foram desenvolvidas visando <strong>gerenciar</strong>, <strong>informar</strong> e <strong>ajudar na tomada de decis&atilde;o</strong> de sua empresa.</p>
									<p>Para usá-lo <strong>é necessário ter registrado</strong> junto ao administrador de sistema <strong>um usuário e senha</strong>.</p>
								</div>
							</div>			
							 -->
						<div class="box boxA">
							<div class="boxContent">
								
									<ul>
										<li>
											<div>										
												<label class="desc">ID*</label>					
												<h:inputText styleClass="text" id="id" maxlength="6"
													value="#{loginBB.id}" required="true">
													<f:validateLength maximum="6" />
													<f:validator validatorId="LongValidator"/>
												</h:inputText>
												<h:message for="id" styleClass="msgErro"/>
											</div>
										</li>
										<li>
											<div>
												<label class="desc">SENHA*</label>
												<h:inputSecret styleClass="text" id="senha" redisplay="true" maxlength="6"
													value="#{loginBB.senha}" required="true">
													<f:validateLength maximum="10" />
													<f:validator validatorId="LongValidator"/>
												</h:inputSecret>
												<h:message for="senha" styleClass="msgErro"/>
											</div>
										</li>
										<li class="buttons">
											<h:commandButton styleClass="btTxt" id="botaoLogin" action="#{loginBB.logar}" value="Login"></h:commandButton>
											<h:commandButton styleClass="btTxt" id="botaoLimpar" type="reset" value="Limpar"></h:commandButton>
										</li>
										<li>
											<div>
												<h:messages errorClass="msgSistemaErro"
														infoClass="msgSistemaSucesso" globalOnly="true"
														showDetail="true" />
											</div>
										</li>
									</ul>

							</div>
						</div>
			
						<div class="box" style="height:130px;"></div>


						</div>
					</div>
					<div id="secundarioContent">			
			
					</div>
					<div class="clear"></div>
					</div>
				<jsp:include page="/jsp/rodape.jsp"></jsp:include>
		</h:form>
	</f:view>
</html>