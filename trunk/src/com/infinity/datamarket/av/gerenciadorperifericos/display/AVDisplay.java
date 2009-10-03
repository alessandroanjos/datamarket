package com.infinity.datamarket.av.gerenciadorperifericos.display;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.Serializable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.components.DateTextField;
import com.infinity.datamarket.pdv.gerenciadorperifericos.components.LimitedPasswordField;
import com.infinity.datamarket.pdv.gerenciadorperifericos.components.LimitedTextField;
import com.infinity.datamarket.pdv.gerenciadorperifericos.components.MoneyTextField;
import com.infinity.datamarket.pdv.gerenciadorperifericos.components.NumberTextField;
import com.infinity.datamarket.pdv.gerenciadorperifericos.components.PercentTextField;
import com.infinity.datamarket.pdv.gerenciadorperifericos.components.QuantidadeTextField;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.gui.telas.Tela;

public class AVDisplay extends JPanel implements Display{
	private long id = 123456789;
	private JLabel usuario;
	private JLabel loja;
	private JLabel componente;
	private static final String MENS_USU = "Operador: ";
	private static final String MENS_LOJA = "Loja: ";
	private static final String MENS_COMPONENTE = "PDV: ";
	private JLabel campo1;
	private JTextField campo2;
	private int dadoRetorno = -1;
	private transient Thread threadDisplay;
	private EventosListener ev;
	private boolean sinal = false;
	private JLabel imgLogo = null;
	
	public AVDisplay(){
		initComponents();
	}

	public void setUsuario(String usuario){
		this.usuario.setText("");
		//	this.usuario.setText(MENS_USU+usuario);
	}
	
	public void setLoja(String loja){
		this.loja.setText("");
		//		this.loja.setText(MENS_LOJA+loja);
	}
	
	public void setComponente(String componente){
		this.componente.setText("");
//		this.componente.setText(MENS_COMPONENTE+componente);
	}


	private void initComponents(){

		ev = new EventosListener();

		 if(new File(Tela.LOGO_INFINITY).exists()) {
			 javax.swing.ImageIcon ii = new javax.swing.ImageIcon(Tela.LOGO_INFINITY);
			 
			 imgLogo = new JLabel(ii);
	         imgLogo.setBounds(0, 0, 340, 83);
	         add(imgLogo);
	     }
		 
		setLayout(null);
		campo1 = new javax.swing.JLabel();
        campo2 = new LimitedTextField(20);
        usuario = new javax.swing.JLabel();
        loja = new javax.swing.JLabel();
        componente = new javax.swing.JLabel();

        loja.setBackground(new java.awt.Color(0, 0, 100));
        loja.setFont(new java.awt.Font("Courier New", 1, 12));
        loja.setForeground(new java.awt.Color(255, 255, 255));
        loja.setText("");
        //loja.setText(MENS_LOJA);
        loja.setBounds(5, 5, 300, 20);
        add(loja);
        
        componente.setBackground(new java.awt.Color(0, 0, 100));
        componente.setFont(new java.awt.Font("Courier New", 1, 12));
        componente.setForeground(new java.awt.Color(255, 255, 255));
        //componente.setText(MENS_COMPONENTE);
        componente.setText("");
        componente.setBounds(5, 25, 300, 20);
        add(componente);
        
        usuario.setBackground(new java.awt.Color(0, 0, 100));
        usuario.setFont(new java.awt.Font("Courier New", 1, 12));
        usuario.setForeground(new java.awt.Color(255, 255, 255));
        usuario.setText("");
        //usuario.setText(MENS_USU);
        usuario.setBounds(5, 45, 300, 20);
        add(usuario);


        campo1.setFocusable(false);


        setBackground(new java.awt.Color(0, 0, 100));

        campo1.setBackground(new java.awt.Color(0, 0, 100));
        campo1.setFont(new java.awt.Font("Courier New", 1, 24));
        campo1.setForeground(new java.awt.Color(255, 255, 255));
        campo1.setText("");
        campo1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255)));
        campo1.setOpaque(true);
        campo1.setBounds(400, 5, 390, 30);
        add(campo1);

        campo2.setEditable(false);
        campo2.setBackground(new java.awt.Color(0, 0, 100));
        campo2.setFont(new java.awt.Font("Courier New", 1, 24));
        campo2.setForeground(new java.awt.Color(255, 255, 255));
        campo2.setText("");
        campo2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        campo2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255)));
        campo2.setBounds(400, 35, 390, 30);
        campo2.addKeyListener(ev);
        add(campo2);
        setBounds(0,500,800, 100);
	}
	public void setMensagem(String mens) {
		campo1.setText(mens);
	}

	public void limpaMensagem(){
		campo1.setText("");
		campo2.setText("");
	}
	public boolean equals(Object o){
		if (o instanceof AVDisplay){
			AVDisplay f = (AVDisplay) o;
			return (f.id == this.id);
		}else{
			return false;
		}
	}



	private int getDadoDigitado(){
		while (dadoRetorno == -1) {
			sinal = true;
			try{
				threadDisplay = Thread.currentThread();
				Thread.currentThread().sleep(10000);
			}catch(Exception e){

			}
		}
		sinal = false;
		int dadoRettornoAux = dadoRetorno;
		dadoRetorno = -1;
		return dadoRettornoAux;
	}

	private LimitedPasswordField getLimitedPasswordField(int tamanho){
		LimitedPasswordField retorno = new LimitedPasswordField(tamanho);
		retorno.setBackground(new java.awt.Color(0, 0, 100));
		retorno.setFont(new java.awt.Font("Courier New", 1, 24));
		retorno.setForeground(new java.awt.Color(255, 255, 255));
		retorno.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		retorno.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255)));
		retorno.setBounds(400, 35, 390, 30);
		retorno.addKeyListener(ev);
		return retorno;
	}

	private LimitedTextField getLimitedTextField(int tamanho){
		LimitedTextField retorno = new LimitedTextField(tamanho);
		retorno.setBackground(new java.awt.Color(0, 0, 100));
		retorno.setFont(new java.awt.Font("Courier New", 1, 24));
		retorno.setForeground(new java.awt.Color(255, 255, 255));
		retorno.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		retorno.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255)));
		retorno.setBounds(400, 35, 390, 30);
		retorno.addKeyListener(ev);
		return retorno;
	}

	private MoneyTextField getMoneyTextField(){
		MoneyTextField retorno = new MoneyTextField();
		retorno.setBackground(new java.awt.Color(0, 0, 100));
		retorno.setFont(new java.awt.Font("Courier New", 1, 24));
		retorno.setForeground(new java.awt.Color(255, 255, 255));
		retorno.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255)));
		retorno.setBounds(400, 35, 390, 30);
		retorno.addKeyListener(ev);
		return retorno;
	}

	private PercentTextField getPercentTextField(){
		PercentTextField retorno = new PercentTextField();
		retorno.setBackground(new java.awt.Color(0, 0, 100));
		retorno.setFont(new java.awt.Font("Courier New", 1, 24));
		retorno.setForeground(new java.awt.Color(255, 255, 255));
		retorno.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255)));
		retorno.setBounds(400, 35, 390, 30);
		retorno.addKeyListener(ev);
		return retorno;
	}
	
	private QuantidadeTextField getQuantidadeTextField(){
		QuantidadeTextField retorno = new QuantidadeTextField();
		retorno.setBackground(new java.awt.Color(0, 0, 100));
		retorno.setFont(new java.awt.Font("Courier New", 1, 24));
		retorno.setForeground(new java.awt.Color(255, 255, 255));
		retorno.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255)));
		retorno.setBounds(400, 35, 390, 30);
		retorno.addKeyListener(ev);
		return retorno;
	}

	private NumberTextField getNumberTextField(int tamanho){
		NumberTextField retorno = new NumberTextField(tamanho);
		retorno.setBackground(new java.awt.Color(0, 0, 100));
		retorno.setFont(new java.awt.Font("Courier New", 1, 24));
		retorno.setForeground(new java.awt.Color(255, 255, 255));
		retorno.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255)));
		retorno.setBounds(400, 35, 390, 30);
		retorno.addKeyListener(ev);
		return retorno;
	}
	
	private DateTextField getDateTextField(){
		DateTextField retorno = new DateTextField();
		retorno.setBackground(new java.awt.Color(0, 0, 100));
		retorno.setFont(new java.awt.Font("Courier New", 1, 24));
		retorno.setForeground(new java.awt.Color(255, 255, 255));
		retorno.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255)));
		retorno.setBounds(400, 35, 390, 30);
		retorno.addKeyListener(ev);
		return retorno;
	}
	
	public EntradaDisplay lerDados(int[] finalizadoras, int tipo, int tamanho) throws AppException{
		remove(campo2);
		switch(tipo){
			case MASCARA_MONETARIA : {
				campo2 = getMoneyTextField();
				break;
			}
			case MASCARA_PASSWORD : {
				campo2 = getLimitedPasswordField(tamanho);
				break;
			}
			case MASCARA_NUMERICA : {
				campo2 = getNumberTextField(tamanho);
				break;
			}
			case MASCARA_PERCENTUAL : {
				campo2 = getPercentTextField();
				break;
			}
			case MASCARA_QUANTIDADE : {
				campo2 = getQuantidadeTextField();
				break;
			}
			case MASCARA_DATA : {
				campo2 = getDateTextField();
				break;
			}
		}
		add(campo2);
		repaint();
		campo2.setEditable(true);
		campo2.requestFocus();
		if (finalizadoras != null && finalizadoras.length > 0 ){
			while(true){
				int dado = getDadoDigitado();
				for (int i = 0;i<finalizadoras.length;i++){
					int finalizadora = finalizadoras[i];
					if (dado == finalizadora){
						String retorno = null;
						if (campo2 instanceof MoneyTextField){
							MoneyTextField c = (MoneyTextField) campo2;
							retorno = new String(""+c.getValor());
						}else if (campo2 instanceof PercentTextField){
							PercentTextField c = (PercentTextField) campo2;
							retorno = new String(""+c.getValor());
						}else if (campo2 instanceof QuantidadeTextField){
							QuantidadeTextField c = (QuantidadeTextField) campo2;
							retorno = new String(""+c.getValor());
						}else if (campo2 instanceof DateTextField){
							DateTextField c = (DateTextField) campo2;
							retorno = new String(""+c.getValor());
						}
						else{
							retorno = new String(campo2.getText());
							campo2.setText("");
						}
						campo2.setEditable(false);
						return new EntradaDisplay(retorno,finalizadora);
					}
				}
			}
		}else{
			while(true){
				int dado = getDadoDigitado();
				return new EntradaDisplay(null,dado);
			}
		}
	}


	private class EventosListener implements KeyListener,Serializable{

		private int[] rangeAlfa = new int[]{0x41,0x42,0x43,0x44,0x45,0x46,0x47,0x48,0x49,0x4A,0x4B,0x4C,0x4D,0x4E,0x4F,0x50,0x51,0x52,0x53,0x54,0x55,0x56,0x57,0x58,0x59,0x5A};
		private int[] rangeNumber = new int[]{0x30,0x31,0x32,0x33,0x34,0x35,0x36,0x37,0x38,0x39,0x60,0x61,0x62,0x63,0x64,0x65,0x66,0x67,0x68,0x69};
		private boolean trava = false;

		public void keyPressed(KeyEvent e) {
			if (sinal){
				dadoRetorno = e.getKeyCode();
				if (dadoRetorno == KeyEvent.VK_TAB){
					e.consume();
					return;
				}
				threadDisplay.interrupt();
				trava = false;
				if (ehValorAlfaNumerico(e.getKeyCode())){
//					if (!(campo2 instanceof MoneyTextField) && !(campo2 instanceof PercentTextField)){
//						//campo2.setText(campo2.getText()+e.getKeyChar());
//						trava = true;
//					}
				}
			}
		}

		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		public void keyTyped(KeyEvent e) {
			if (trava){
				e.consume();
			}
		}

		private boolean ehValorNumerico(int key){
			for (int i = 0;i < rangeNumber.length;i++){
				if (rangeNumber[i] == key){
					return true;
				}
			}
			return false;
		}

		private boolean ehValorAlfa(int key){
			for (int i = 0;i < rangeAlfa.length;i++){
				if (rangeAlfa[i] == key){
					return true;
				}
			}
			return false;
		}

		private boolean ehValorAlfaNumerico(int key){
			return (ehValorAlfa(key) || ehValorNumerico(key));
		}
	}

	public void setValor(String valor){
		campo2.setText(valor);
	}

}
