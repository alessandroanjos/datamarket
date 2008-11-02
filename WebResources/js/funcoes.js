
var ERRO_ENDERECO_IP   = "Endereço IP inválido!\nDigite novamente.";
var ERRO_DATA_INVALIDA = "Data inválida!\nDigite novamente.\n\nPara sair apague/limpe o campo.";


function Limpar(valor, validos) {
	// retira caracteres invalidos da string
	var result = "";
	var aux;
	for (var i = 0; i < valor.length; i++) {
		aux = validos.indexOf(valor.substring(i, i+1));
		if (aux>=0) {
			result += aux;
		}
	}
	return result;
}

function FormataPeso(campo,tammax,decimal,evt) {
	var objeto = document.getElementById(campo);
	
	//var tecla = event.keyCode;
	var tecla = evt.keyCode ? evt.keyCode :
	evt.charCode ? evt.charCode :
	evt.which ? evt.which : void 0;
	
	var vr = Limpar(objeto.value,"0123456789");
	var tam = vr.length;
	var dec = decimal;
	if ( tam > dec )
		{ objeto.value = vr.substr(0,tam-3) + "." + vr.substr(tam-3) ; }
}

function Formata(campo,tammax,decimal,evt) {
	var objeto = document.getElementById(campo);
	
	//var tecla = event.keyCode;
	var tecla = evt.keyCode ? evt.keyCode :
	evt.charCode ? evt.charCode :
	evt.which ? evt.which : void 0;
	
	var vr = Limpar(objeto.value,"0123456789");
	var tam = vr.length;
	var dec = decimal;
	
	if (tam < tammax && tecla != 8){ tam = vr.length + 1 ; }
		if (tecla == 8 )
		{ tam = tam - 1 ; }
		
		if ( tecla == 8 || tecla >= 48 && tecla <= 57 || tecla >= 96 && tecla <= 105 )
		{
		
		if ( tam <= dec )
		{ objeto.value = vr ; }
		
		if ( (tam > dec) && (tam <= 5) ){
			objeto.value = vr.substr( 0, tam - 2 ) + "." + vr.substr( tam - dec, tam ) ; 
		}
		if ( (tam >= 6) && (tam <= 8) ){
			objeto.value = vr.substr( 0, tam - 5 ) + "" + vr.substr( tam - 5, 3 ) + "." + vr.substr( tam - dec, tam ) ; 
		}
		if ( (tam >= 9) && (tam <= 11) ){
			objeto.value = vr.substr( 0, tam - 8 ) + "" + vr.substr( tam - 8, 3 ) + "" + vr.substr( tam - 5, 3 ) + "." + vr.substr( tam - dec, tam ) ; 
		}
		if ( (tam >= 12) && (tam <= 14) ){
			objeto.value = vr.substr( 0, tam - 11 ) + "" + vr.substr( tam - 11, 3 ) + "" + vr.substr( tam - 8, 3 ) + "" + vr.substr( tam - 5, 3 ) + "." + vr.substr( tam - dec, tam ) ; 
		}
		if ( (tam >= 15) && (tam <= 17) ){
			objeto.value = vr.substr( 0, tam - 14 ) + "" + vr.substr( tam - 14, 3 ) + "" + vr.substr( tam - 11, 3 ) + "" + vr.substr( tam - 8, 3 ) + "" + vr.substr( tam - 5, 3 ) + "." + vr.substr( tam - 2, tam ) ;
		}
	}
}

function FormataCNPJ(Campo, teclapres){

	var tecla = teclapres.keyCode;

	var vr = new String(Campo.value);
	vr = vr.replace(".", "");
	vr = vr.replace(".", "");
	vr = vr.replace("/", "");
	vr = vr.replace("-", "");

	tam = vr.length + 1 ;

	
	if (tecla != 9 && tecla != 8){
		if (tam > 2 && tam < 6)
			Campo.value = vr.substr(0, 2) + '.' + vr.substr(2, tam);
		if (tam >= 6 && tam < 9)
			Campo.value = vr.substr(0,2) + '.' + vr.substr(2,3) + '.' + vr.substr(5,tam-5);
		if (tam >= 9 && tam < 13)
			Campo.value = vr.substr(0,2) + '.' + vr.substr(2,3) + '.' + vr.substr(5,3) + '/' + vr.substr(8,tam-8);
		if (tam >= 13 && tam < 15)
			Campo.value = vr.substr(0,2) + '.' + vr.substr(2,3) + '.' + vr.substr(5,3) + '/' + vr.substr(8,4)+ '-' + vr.substr(12,tam-12);
		}
}

function FormataCPF(Campo, teclapres){
	var tecla;
	if(window.event){
		tecla = teclapres.keyCode;
	}else{
		tecla = teclapres.which;
	} 

	var vr = new String(Campo.value);
	vr = vr.replace(".", "");
	vr = vr.replace(".", "");
	vr = vr.replace("-", "");

	tam = vr.length + 1;
	
	if (tecla != 9 && tecla != 8){
		if (tam > 3 && tam < 7)
			Campo.value = vr.substr(0, 3) + '.' + vr.substr(3, tam);
		if (tam >= 7 && tam <10)
			Campo.value = vr.substr(0,3) + '.' + vr.substr(3,3) + '.' + vr.substr(6,tam-6);
		if (tam >= 10 && tam < 12)
			Campo.value = vr.substr(0,3) + '.' + vr.substr(3,3) + '.' + vr.substr(6,3) + '-' + vr.substr(9,tam-9);
		}
}

function trocaAba(nomeForm, x, n) {
//	trocaAba(NUMERO_ABA, QTD_ABAS)

//	deselecionaLinha();
	var n = n + 1;
	for (i=1; i<n; i++) {
		if ( i == x) {
			document.getElementById(nomeForm + ":Layer" + i).style.display = "block";
			document.getElementById(nomeForm + ":aba" + i).className = "aba-cinza-claro";
		} else {
			document.getElementById(nomeForm + ":Layer" + i).style.display = "none";
			document.getElementById(nomeForm + ":aba" + i).className = "aba-cinza-escuro";
		}
	}
}


function openReport(url){
	window.open(url,"","top=10,left=50,height=600,width=900, menubar=no, resizable=no, scrollbars=no, status=no, toolbar=no");
}

function SoNumero(evt) {
	var key_code = evt.keyCode ? evt.keyCode :
	evt.charCode ? evt.charCode :
	evt.which ? evt.which : void 0;

	// Habilita teclas <DEL>, <TAB>, <ENTER>, <ESC> e <BACKSPACE>
	if (key_code == 8 || key_code == 9 || key_code == 13 || key_code == 27 || key_code == 46){
	  return true;
	}
	// Habilita teclas <HOME>, <END>, mais as quatros setas de navegacao (cima, baixo, direta, esquerda)
	else if ((key_code >= 35) && (key_code <= 40)){
	  return false
	}
	// Habilita numeros de 0 a 9
	else if ((key_code >= 48) && (key_code <= 57)) {
	  return true
	}
	return false;
}


function formataCampo(campo, Mascara, evento) { 
  var boleanoMascara; 
  
  var Digitato = evento.keyCode;
  exp = /\-|\.|\/|\(|\)| /g
  campoSoNumeros = campo.value.toString().replace( exp, "" ); 
  
  var posicaoCampo = 0; 
  var NovoValorCampo="";
  var TamanhoMascara = campoSoNumeros.length;; 
  
  if (Digitato != 8) { // backspace 
  for(i=0; i<= TamanhoMascara; i++) { 
  boleanoMascara = ((Mascara.charAt(i) == "-") || (Mascara.charAt(i) == ".")
  || (Mascara.charAt(i) == "/")) 
  boleanoMascara = boleanoMascara || ((Mascara.charAt(i) == "(") 
  || (Mascara.charAt(i) == ")") || (Mascara.charAt(i) == " ")) 
  if (boleanoMascara) { 
  NovoValorCampo += Mascara.charAt(i); 
  TamanhoMascara++;
  }else { 
  NovoValorCampo += campoSoNumeros.charAt(posicaoCampo); 
  posicaoCampo++; 
  } 
  } 
  campo.value = NovoValorCampo;
  return true; 
  }else { 
  return true; 
  }
}

function MascaraData(data,event){
  if(SoNumero(event)==false){
    event.returnValue = false;
    return false;
  } 
  return formataCampo(data, '00/00/0000', event);
}



function FormataData(Campo) {

	var tecla = event.keyCode;

	var vr = new String(document.getElementById(Campo).value);

	while (vr.indexOf("/") > -1) {
		vr = vr.replace("/", "");
	}
	if (!(vr.charAt(0) in [0,1,2,3,4,5,6,7,8,9])) {
		document.getElementById(Campo).value = "";
	}

	tam = vr.length + 1;

	if (tecla != 9 && tecla != 8) {
		if (tam == 3) {
			document.getElementById(Campo).value = vr + "/";
		} else if (tam == 5) {
			document.getElementById(Campo).value = vr.substring(0,2) + "/" + vr.substring(2,4) + "/";
		}
	}
}

function isDate(strData){
	var dia, mes, ano, retorno;
	retorno = true;
	
	//critica valor de um campo de data
	if(strData.length < 10) retorno = false;

	mes = strData.substr(3,2);
	if(mes > "12" || mes == "00") retorno = false;

	dia = strData.substr(0,2);
	if(dia=="00") retorno = false;

	ano = strData.substr(6,4);
	if (ano < "1900") retorno = false;

	if (mes=="01"||mes=="03"||mes=="05"||mes=="07"||mes=="08"||mes=="10"||mes=="12"){
		if (dia > "31")
		retorno = false;
	}
	if(mes=="04"||mes=="06"||mes=="09"||mes=="11"){
		if (dia > "30")
		retorno = false;
	}
	if(mes=="02"){
		if((parseInt(ano) % 4) ==0){
			if (dia > "29")	retorno = false;
		} else {
			if (dia > "28") retorno = false;
		}
	}
	if(strData == "") retorno = true;
	return retorno;
}


function ValidaData(data){
	if (document.getElementById(data).length != 10) {
		return false;
	}

	if ( (document.getElementById(data).indexOf("/") != 2)  || (document.getElementById(data).lastIndexOf("/") != 5) ) {
		return false;
	}

	arrayData = document.getElementById(data).split("/");
	dia = parseInt(arrayData[0]);
	mes = parseInt(arrayData[1]);
	ano = parseInt(arrayData[2]);

	if ( (dia == Number.NaN) || (mes == Number.NAn) || (ano == Number.NaN) ) {
		return false;
	}

	if (ano < 1900){
		return false;
	}

	switch (mes){
		case 1:
			if  (dia > 31){
				return false;
			}
		break;

		case 2:
			if (ano % 4 == 0)
				FimDoMes = 29;
			else
				FimDoMes = 28;
			if  ( dia > FimDoMes ) {
				return false;
			}
		break;

		case 3:
			if  (dia > 31){
				return false;
			}
		break;

		case 4:
			if  (dia > 30){
				return false;
			}
		break;

		case 5:
			if  (dia > 31) {
				return false;
			}
		break;

		case 6:
			if  (dia > 30) {
				return false;
			}
		break;

		case 7:
			if  (dia > 31){
				return false;
			}
		break;

		case 8:
			if  (dia > 31){
				return false;
			}
		break;

		case 9:
			if  (dia > 30){
				return false;
			}
		break;

		case 10:
			if  (dia > 31){
				return false;
			}
		break;

		case 11:
			if  (dia > 30) {
				return false;
			}
		break;

		case 12:
			if  (dia > 31){
				return false;
			}
		break;
	}

	return true;
}

function showwindow(action) {
	alert(action);
    window.open(action,'_blank','');
}

function formataTel(Campo) {
	var evt = event.keyCode;
	var obj = document.getElementById(Campo);
//    if (navigator.appName.indexOf("Netscape") != -1)
//      obj = evt.target;
//    else 
//      obj = evt.srcElement;
    qtd = obj.value.length;
    if (qtd == 2) obj.value = "("+obj.value+")";
    if (qtd == 7) obj.value = obj.value+"-";
    if (qtd == 12 && evt.keyCode == 8) {
    character = tiraChar(obj.value, "-");
        obj.value = character.substring(0,7)+"-"+character.substring(7,12);
    }
    if (qtd == 13) {
    character = tiraChar(obj.value, "-");
    obj.value = character.substring(0,8)+"-"+character.substring(8,12);
}
}
function tiraChar(texto, caracter) {
var ret;
    for (i=0; i < texto.length; i++) {
    if (texto.substring(i, i+1) == caracter)
            ret = texto.substring(0, i)+texto.substring(i+1, texto.length);
    }
    return ret;
}

function tamanhoJanela(width,height) {
	if (window.outerWidth) {
		window.outerWidth = width;
		window.outerHeight = height;
	}
	else if (window.resizeTo) {
		window.resizeTo(width,height);
	}
	else {
		alert("Esse browser não suporta:\ntamanhoJanela(" + width + "," + height + ")");
	}
}


function verificaIP (IPvalor) {
    errorString = true;

    var ipPattern = /^(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})$/;
    var ipArray = IPvalor.match(ipPattern);

    if (IPvalor == "") {
        errorString = true;
	} else {
	    if (IPvalor == "0.0.0.0")
	        errorString = false;
	    else if (IPvalor == "255.255.255.255")
	        errorString = false;
	    if (ipArray == null)
	        errorString = false;
	    else {
	        for (i = 0; i < 4; i++) {
	            if (ipArray[i] > 255) {
	                errorString = false;
	                i = 4;
	            }
	            if ((i == 0) && (ipArray[i] > 255)) {
	                errorString = false;
	                i = 4;
	            }
	        }
	    }
	}
    return errorString;
}

function getId(strId) {
  return document.getElementById(strId);
}

function desabilita(strId) {
  	getId(strId).disabled = true;
  	getId(strId).style.backgroundColor = "#dedede";
  	getId(strId).value = "";
}

function habilita(strId) {
  	getId(strId).disabled = false;
  	getId(strId).style.backgroundColor = "white";
}

function validaCNPJ(objCNPJ) {
    erro = new String;
	CNPJ = new String(objCNPJ.value);
	
	if (CNPJ.length == 0) return true;
    if (CNPJ.length < 18) erro += "É necessario preencher corretamente o número do CNPJ! \n\n";
    if ((CNPJ.charAt(2) != ".") || (CNPJ.charAt(6) != ".") || (CNPJ.charAt(10) != "/") || (CNPJ.charAt(15) != "-")){
    	if (erro.length == 0) erro += "É necessario preencher corretamente o número do CNPJ! \n\n";
    }
    //substituir os caracteres que nï¿½o sï¿½o nï¿½meros
   if(document.layers && parseInt(navigator.appVersion) == 4){
       x = CNPJ.substring(0,2);
       x += CNPJ. substring (3,6);
       x += CNPJ. substring (7,10);
       x += CNPJ. substring (11,15);
       x += CNPJ. substring (16,18);
       CNPJ = x;
   } else {
       CNPJ = CNPJ. replace (".","");
       CNPJ = CNPJ. replace (".","");
       CNPJ = CNPJ. replace ("-","");
       CNPJ = CNPJ. replace ("/","");
   }
   var nonNumbers = /\D/;
   if (nonNumbers.test(CNPJ)) erro += "A verificação de CNPJ suporta apenas números! \n\n";
   var a = [];
   var b = new Number;
   var c = [6,5,4,3,2,9,8,7,6,5,4,3,2];
   for (i=0; i<12; i++){
		a[i] = CNPJ.charAt(i);
		b += a[i] * c[i+1];
   }
   if ((x = b % 11) < 2) { a[12] = 0 } else { a[12] = 11-x }
   b = 0;
   for (y=0; y<13; y++) {
	   b += (a[y] * c[y]);
   }
   if ((x = b % 11) < 2) { a[13] = 0; } else { a[13] = 11-x; }
   if ((CNPJ.charAt(12) != a[12]) || (CNPJ.charAt(13) != a[13])){
		erro +="Dígito verificador com problema!";
   }
   if (erro.length > 0){
	   alert(erro);
	   objCNPJ.select();
	   return false;
   }
   return true;
}

function validaCPF(objCPF) {
	// 'cpf' tem que ser uma string
    erro = new String;
	cpf = new String(objCPF.value);
	if (cpf.length == 0) return true;

	if(document.layers && parseInt(navigator.appVersion) == 4){
	   x = cpf.substring(0,3);
	   x += cpf. substring (4,7);
	   x += cpf. substring (8,11);
	   x += cpf. substring (12,14);
	   cpf = x;
	} else {
	   cpf = cpf. replace (".","");
	   cpf = cpf. replace (".","");
	   cpf = cpf. replace ("-","");
	}

    if (cpf.length < 11) erro += "Sao necessarios 11 digitos para verificacao do CPF! \n\n";
    var nonNumbers = /\D/;
    if (nonNumbers.test(cpf)) erro += "A verificacao de CPF suporta apenas numeros! \n\n";
    if (cpf == "00000000000" || cpf == "11111111111" || cpf == "22222222222" || cpf == "33333333333" || cpf == "44444444444" || cpf == "55555555555" || cpf == "66666666666" || cpf == "77777777777" || cpf == "88888888888" || cpf == "99999999999"){
            erro += "Numero de CPF invalido!"
   }
   var a = [];
   var b = new Number;
   var c = 11;
   for (i=0; i<11; i++){
           a[i] = cpf.charAt(i);
           if (i < 9) b += (a[i] * --c);
   }
   if ((x = b % 11) < 2) { a[9] = 0 } else { a[9] = 11-x }
   b = 0;
   c = 11;
   for (y=0; y<10; y++) b += (a[y] * c--);
   if ((x = b % 11) < 2) { a[10] = 0; } else { a[10] = 11-x; }
   if ((cpf.charAt(9) != a[9]) || (cpf.charAt(10) != a[10])){
           erro +="Digito verificador com problema!";
   }
   if (erro.length > 0){
		alert(erro);
		objCPF.select();
		return false;
   }
   return true;
}



checarTabulacao=true;
function exibeValor(nomeCampo, lenCampo) {
	if ((nomeCampo.value.length == lenCampo) && (checarTabulacao)) {
		var i=0;
		for (i=0; i < document.forms[0].elements.length; i++) {
			if (document.forms[0].elements[i].name == nomeCampo.name) {
				while ((i+1) < document.forms[0].elements.length) {		
					if (document.forms[0].elements[i+1].type != "hidden") {
						document.forms[0].elements[i+1].focus();
						break;
					}
					i++;
				}
				checarTabulacao=false;
				break;
			}
		}
	}
}

function Apenas_Numeros(e) {
		//- 48 a 57 (0,1,2,3,4,56,7,8,9)
		//- 8 (Backspace)
		//- 0 (null)
		var NS = (navigator.appName == "Netscape")
		var Digito = parseInt(eval( ( (NS)?"e.which":"e.keyCode" ) ))

//		if (!(Digito>47 && Digito<58 || Digito == 8 || Digito == 0)) return false;
		if (!(Digito>47 && Digito<58 || Digito == 8 || Digito == 0)) return false;
	}

function altera_campo(valor_campo,caracter,altera) {
	while(valor_campo.indexOf(caracter)>-1) {
		valor_campo = valor_campo.replace(caracter,altera);
	}
	return valor_campo;
}
function altera_campo(valor_campo,caracter,altera) {	
	while(valor_campo.indexOf(caracter)>-1) {
		valor_campo = valor_campo.replace(caracter,altera);
	}
	return valor_campo;
}

function Right(str, n) {
	// Pega quantidade de números
	// a direita desejado
	if (n <= 0)
	   return "";
	else if (n > String(str).length)
	   return str;
	else {
	   var iLen = String(str).length;
	   return String(str).substring(iLen, iLen - n);
	}
}

function formataDecimalTres(campo_formulario,maximo,press) {
	var codigo = press.keyCode;
	var caracter = ".";
	var fc = campo_formulario.value;

	fc = altera_campo(fc,caracter,"");
	fc = altera_campo(fc,",","");
	fc = altera_campo(fc,".","");

	var tamanho = fc.length;
	if (codigo > 0) {	
		if (tamanho < maximo && codigo != 8) tamanho = fc.length + 1 ;
		if (codigo == 8) tamanho = tamanho - 1 ; 
	}

	if ((codigo == -1) ||
		(codigo >= 48) && (codigo <= 57) || 
		(codigo >= 96) && (codigo <= 105)) {

		if (tamanho <= 2) campo_formulario.value = fc ;
			
		if ((tamanho > 2) && (tamanho <= 5))
		{campo_formulario.value = fc.substr( 0, tamanho - 3 ) + '.' + fc.substr( tamanho - 3, tamanho ) ;}
			
		if ((tamanho >= 6) && (tamanho <= 8))
		{campo_formulario.value = fc.substr( 0, tamanho - 6 ) + fc.substr( tamanho - 6, 3 ) + '.' + fc.substr( tamanho - 3, tamanho ) ; }
			
/*		if (tamanho == 9)
		{campo_formulario.value = fc.substr( 0, tamanho - 8 ) + fc.substr( tamanho - 8, 3 ) + fc.substr( tamanho - 5, 3 ) + '.' + fc.substr( tamanho - 3, tamanho ) ; }
*/		
	}
}

function formataDecimalDois(campo_formulario,maximo,press) {
	var codigo = press.keyCode;
	var caracter = ".";
	var fc = campo_formulario.value;

	fc = altera_campo(fc,caracter,"");
	fc = altera_campo(fc,",","");
	fc = altera_campo(fc,".","");

	var tamanho = fc.length;
	if(codigo > 0) {
		if (tamanho < maximo && codigo != 8) tamanho = fc.length + 1 ;
		if (codigo == 8) tamanho = tamanho - 1 ;
	}

	if ((codigo == -1) || 
		(codigo >= 48) && (codigo <= 57) || 
		(codigo >= 96) && (codigo <= 105))
	{		//alert(codigo);
		if (tamanho <= 2) campo_formulario.value = fc ;
			
		if ((tamanho > 2) && (tamanho <= 5))
		{campo_formulario.value = fc.substr( 0, tamanho - 2 ) + '.' + fc.substr( tamanho - 2, tamanho ) ;}
			
		if ((tamanho >= 6) && (tamanho <= 8))
		{campo_formulario.value = fc.substr( 0, tamanho - 5 ) + fc.substr( tamanho - 5, 3 ) + '.' + fc.substr( tamanho - 2, tamanho ) ; }
			
		if ((tamanho >= 9) && (tamanho <= 11))
		{campo_formulario.value = fc.substr( 0, tamanho - 8 ) + fc.substr( tamanho - 8, 3 ) + fc.substr( tamanho - 5, 3 ) + '.' + fc.substr( tamanho - 2, tamanho ) ; }		

		if (tamanho == 12)
		{campo_formulario.value = fc.substr( 0, tamanho - 11 ) + fc.substr( tamanho - 11, 3 ) + fc.substr( tamanho - 8, 3 ) + fc.substr( tamanho - 5, 3 ) + '.' + fc.substr( tamanho - 2, tamanho ) ; }		
	}
}

function FuncBlur(campo) {
	campo.style.backgroundColor = '#ffffff';
}	

function FuncFocus(campo) {
	campo.style.backgroundColor = '#F8F7F7'
}	

function formataMoeda(objTextBox, SeparadorMilesimo, SeparadorDecimal,QtdDecimal, e){
    var sep = 0;
    var key = '';
    var i = j = 0;
    var len = len2 = 0;
    var strCheck = '0123456789';
    var aux = aux2 = '';
    var whichCode = (window.Event) ? e.which : e.keyCode;    
    // 13=enter, 8=backspace as demais retornam 0(zero)
    // whichCode==0 faz com que seja possivel usar todas as teclas como delete, setas, etc    
    if ((whichCode == 13) || (whichCode == 0) || (whichCode == 8))
    	return true;
    key = String.fromCharCode(whichCode); // Valor para o código da Chave
 
 
    if (strCheck.indexOf(key) == -1) 
    	return false; // Chave inválida
    len = objTextBox.value.length;
    for(i = 0; i < len; i++)
        if ((objTextBox.value.charAt(i) != '0') && (objTextBox.value.charAt(i) != SeparadorDecimal)) 
        	break;
    aux = '';
    for(; i < len; i++)
        if (strCheck.indexOf(objTextBox.value.charAt(i))!=-1) 
        	aux += objTextBox.value.charAt(i);
    aux += key;
    len = aux.length;
    if (len == 0) 
    	objTextBox.value = '';
    if ((len == (QtdDecimal-1)) || (len == 1))
    	objTextBox.value = '0'+ SeparadorDecimal + '0' + aux;
    if (len == QtdDecimal) 
    	objTextBox.value = '0'+ SeparadorDecimal + aux;
    if (len > QtdDecimal) {
        aux2 = '';
        for (j = 0, i = len - (QtdDecimal+1); i >= 0; i--) {
            if (j == (QtdDecimal+1)) {
                aux2 += SeparadorMilesimo;
                j = 0;
            }
            aux2 += aux.charAt(i);
            j++;
        }
        objTextBox.value = '';
        len2 = aux2.length;
        for (i = len2 - 1; i >= 0; i--)
        	objTextBox.value += aux2.charAt(i);
        objTextBox.value += SeparadorDecimal + aux.substr(len - QtdDecimal, len);
    }
    return false;
}


function MascaraMoeda(objTextBox, SeparadorMilesimo, SeparadorDecimal, e){
    var sep = 0;
    var key = '';
    var i = j = 0;
    var len = len2 = 0;
    var strCheck = '0123456789';
    var aux = aux2 = '';	
    var whichCode = (window.Event) ? e.which : e.keyCode;
    if ((whichCode == 13))
    	return true;   
    key = String.fromCharCode(whichCode); // Valor para o código da Chave
    if (strCheck.indexOf(key) == -1) return false; // Chave inválida
    len = objTextBox.value.length;
    for(i = 0; i < len; i++)
        if ((objTextBox.value.charAt(i) != '0') && (objTextBox.value.charAt(i) != SeparadorDecimal)) break;
    aux = '';
    for(; i < len; i++)
        if (strCheck.indexOf(objTextBox.value.charAt(i))!=-1) aux += objTextBox.value.charAt(i);
    aux += key;
    len = aux.length;
    if (len == 0) objTextBox.value = '';
    if (len == 1) objTextBox.value = '0'+ SeparadorDecimal + '0' + aux;
    if (len == 2) objTextBox.value = '0'+ SeparadorDecimal + aux;
    if (len > 2) {
        aux2 = '';
        for (j = 0, i = len - 3; i >= 0; i--) {
            if (j == 3) {
                aux2 += SeparadorMilesimo;
                j = 0;
            }
            aux2 += aux.charAt(i);
            j++;
        }
        objTextBox.value = '';
        len2 = aux2.length;
        for (i = len2 - 1; i >= 0; i--)
        objTextBox.value += aux2.charAt(i);
        objTextBox.value += SeparadorDecimal + aux.substr(len - 2, len);
    }
    
    return false;
}


function MascaraQTD(objTextBox, SeparadorMilesimo, SeparadorDecimal, e){
    var sep = 0;
    var key = '';
    var i = j = 0;
    var len = len2 = 0;
    var strCheck = '0123456789';
    var aux = aux2 = '';
    var whichCode = (window.Event) ? e.which : e.keyCode;
    if ((whichCode == 13))
    	return true;
    if ((whichCode == 8)){
    	objTextBox.value = '0.00';
	return false;
    }
    key = String.fromCharCode(whichCode); // Valor para o código da Chave
    if (strCheck.indexOf(key) == -1) return false; // Chave inválida
    len = objTextBox.value.length;
    for(i = 0; i < len; i++)
        if ((objTextBox.value.charAt(i) != '0') && (objTextBox.value.charAt(i) != SeparadorDecimal)) break;
    aux = '';
    for(; i < len; i++)
        if (strCheck.indexOf(objTextBox.value.charAt(i))!=-1) aux += objTextBox.value.charAt(i);
    aux += key;
    len = aux.length;
    if (len == 0) objTextBox.value = '';
    if (len == 1) objTextBox.value = '0'+ SeparadorDecimal + '00' + aux;
    if (len == 2) objTextBox.value = '0'+ SeparadorDecimal + '0' + aux;
    if (len == 3) objTextBox.value = '0'+ SeparadorDecimal + aux;
    if (len > 3) {
        aux2 = '';
        for (j = 0, i = len - 4; i >= 0; i--) {
            if (j == 4) {
                aux2 += SeparadorMilesimo;
                j = 0;
            }
            aux2 += aux.charAt(i);
            j++;
        }
        objTextBox.value = '';
        len2 = aux2.length;
        for (i = len2 - 1; i >= 0; i--)
        objTextBox.value += aux2.charAt(i);
        objTextBox.value += SeparadorDecimal + aux.substr(len - 3, len);
    }
    return false;
}


function BackSpace(objTextBox,e){
    var whichCode = (window.Event) ? e.which : e.keyCode;
    if (whichCode == 8){    
    	objTextBox.value = '0.00';
    	return false;
    }
    if (whichCode == 9){    
    	return true;
    }
    if ((whichCode >= 48 && whichCode <= 57) || (whichCode >= 96 && whichCode <= 105)){
    
    	return true;
    }else{
    
    	return false;
    }
    
   
    
}

function BackSpaceQTD(objTextBox,e){
    var whichCode = (window.Event) ? e.which : e.keyCode;
    if (whichCode == 8){    
    	objTextBox.value = '0.000';
    	return false;
    }
    if (whichCode == 9){    
    	return true;
    }
    if ((whichCode >= 48 && whichCode <= 57) || (whichCode >= 96 && whichCode <= 105)){
    
    	return true;
    }else{
    
    	return false;
    }
    
   
    
}

