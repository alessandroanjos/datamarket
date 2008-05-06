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

function Formata(campo,tammax,decimal) {
	var objeto = document.getElementById(campo);
	
	var tecla = event.keyCode;

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

function SoNumero(evento) {
	var caracCode;
	if (evento == null) evento = event;

	caracCode = (navigator.appName == "Netscape") ? evento.which : evento.keyCode;
	if (caracCode == 45  || caracCode == 13 || caracCode == 8 || caracCode == 0) {
		return true;
	}
	if (caracCode < 48 || caracCode > 57) {
		if (navigator.appName != "Netscape") evento.keyCode = 0;
		return false;
	}
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
	
	if (vr.length == 7) {
		document.getElementById(Campo).onblur = 	
			function() {
				if (isDate(document.getElementById(Campo).value) == false && document.getElementById(Campo).value != "") {
					alert("Data Digitada inválida.");
					document.getElementById(Campo).focus();
					document.getElementById(Campo).select();
				}
			};
	}
}

function isDate(strData){
	var dia, mes, ano;
	//critica valor de um campo de data
	if(strData.length < 10) return false;

	mes = strData.substr(3,2);
	if(mes > "12" || mes == "00") return false;

	dia = strData.substr(0,2);
	if(dia=="00") return false;

	ano = strData.substr(6,4);
	if (ano < "1900") return false;

	if (mes=="01"||mes=="03"||mes=="05"||mes=="07"||mes=="08"||mes=="10"||mes=="12"){
		if (dia > "31")
		return false;
	}
	if(mes=="04"||mes=="06"||mes=="09"||mes=="11"){
		if (dia > "30")
		return false;
	}
	if(mes=="02"){
		if((parseInt(ano) % 4) ==0){
			if (dia > "29")	return false;
		} else {
			if (dia > "28") return false;
		}
	}
	return true;
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