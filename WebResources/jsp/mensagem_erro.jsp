

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<t:stylesheet path="/css/estilo.css"></t:stylesheet>

<script type="text/javascript">
<!--
	
function alertaErro(mensagem){

	document.getElementById('content').style.display = 'none';
	document.getElementById('alerta').style.display = 'block';
	
	document.getElementById('jqmAlertContent').innerHTML= mensagem;

}
function fecharAlerta(){

	document.getElementById('alerta').style.display = 'none';

	document.getElementById('content').style.display = 'block';

}

	function exibirMensagemErro() {
	
		if ('<h:messages />' != '') {
		try{
			alertaErro('<h:messages />');
		} catch(e)	 {
			alert(e);
		}
		}
	}

	exibirMensagemErro();

	
// -->
</script>

