var strAbaCorrente = "";
function autofocus(field, limit, next, evt) {
	evt = (evt) ? evt : event;
	var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode : 
		((evt.which) ? evt.which : 0));
	if (charCode > 31 && field.value.length == limit) {
		field.form.elements[next].focus( );
	}
}

/*function selecionaMenuTab(alvo) {
	$("li#"+alvo).siblings().each(function(i){
		$("li#"+this.id).removeClass("current");
		var idObj = new String(this.id).replace("Menu","");
		$("div#"+idObj.toString()).hide();
	});
	$("li#"+alvo).addClass('current');
	$("div#"+alvo.replace("Menu","")).show();
}*/

function selecionaMenuTab(alvo) {
	$("li#"+alvo).siblings().each(function(i){
		$("li#"+this.id).removeClass("current");
		var idObj = new String(this.id).replace("Menu","");
		$("div#"+idObj.toString()).hide();
	});
	$("li#"+alvo).addClass('current');
	$("div#"+alvo.replace("Menu","")).show();
	strAbaCorrente = alvo;
}

