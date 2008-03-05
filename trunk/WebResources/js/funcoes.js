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