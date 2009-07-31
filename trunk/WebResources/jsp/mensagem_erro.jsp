

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>


<t:stylesheet path="/css/alert/SyntaxHighlighter.css"></t:stylesheet>

    <script type="text/javascript" src="/EnterpriseServer/js/alert/shCore.js" language="javascript"></script>
    <script type="text/javascript" src="/EnterpriseServer/js/alert/shBrushJScript.js" language="javascript"></script>
    <script type="text/javascript" src="/EnterpriseServer/js/alert/ModalPopups.js" language="javascript"></script>
    <script type="text/javascript" src="/EnterpriseServer/js/alert/shInit.js" language="javascript"></script>

<script type="text/javascript">
<!-- 

        function ModalPopupsAlert1(msn) {
            ModalPopups.Alert("jsAlert1",
                "Alerta",
                "<div style='padding:30px'>" + msn +  " " +
                "</div>", 
                {
                    okButtonText: "OK"
                }
            );
        }    

	
function alertaErro2(mensagem){
	ModalPopupsAlert1(mensagem);
}

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
			alertaErro2('<h:messages />');
		} catch(e)	 {
			alert(e);
		}
		}
	}

	exibirMensagemErro();

	
// -->
</script>

