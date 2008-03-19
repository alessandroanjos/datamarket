package com.infinity.datamarket.pdv.mic;


import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gui.telas.swing.ConsultaItemFrame;
import com.infinity.datamarket.pdv.maquinaestados.Evento;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class MicConsultaProdutoNome extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		ConsultaItemFrame c = new ConsultaItemFrame(gerenciadorPerifericos.getWindow().getFrame());
    	c.setSize(800, 530);
    	c.play();
    	if (c.getRetornoTela() == c.BUTTON_OK){
    		Evento e = new Evento(10,c.getValor().getCodigoExterno());
    		gerenciadorPerifericos.addEvento(e);
    		return ALTERNATIVA_1;
    	}else{
    		return ALTERNATIVA_2;
    	}
		
	}
}