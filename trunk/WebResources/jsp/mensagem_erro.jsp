

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

	
	function exibirMensagemErro() {
	
		if ('<h:messages />' != '') {
		try{
			ModalPopupsAlert1('<h:messages />');
		} catch(e)	 {
			alert(e);
		}
		}
	}

//	exibirMensagemErro();

	
// -->
</script>

