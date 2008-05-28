package com.infinity.datamarket.pdv.gerenciadorperifericos;

import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ConcentradorParametro;
import com.infinity.datamarket.comum.util.ListaEventos;
import com.infinity.datamarket.comum.util.SistemaException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.FrameDisplay;
import com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.ImpressoraFiscal;
import com.infinity.datamarket.pdv.gerenciadorperifericos.window.Window;
import com.infinity.datamarket.pdv.gui.telas.Tela;
import com.infinity.datamarket.pdv.maquinaestados.Evento;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;

public class GerenciadorPerifericos implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5243286292600587391L;

	private static GerenciadorPerifericos instancia;

    private Window window;
    private CMOS cmos;
    private Display display;
    private ListaEventos eventos;
    private transient ImpressoraFiscal impressoraFiscal;

    private int loja;
    private int componente;
  
    private static final String WINDOW = "WINDOW";
    private static final String CMOS_VIRTUAL = "CMOS";
    private static final String DISPLAY_VIRTUAL = "DISPLAY";
    private static final String IMPRESSOSRA_FISCAL = "IMPRESSOSRA_FISCAL";

    private GerenciadorPerifericos(){
        carregaPerifericos();
        carregaConstantes();
        eventos = new ListaEventos();

    }

    public void addEvento(Evento e){
    	eventos.add(e);
    }

    public Evento getEvento(){
    	return eventos.getEvento();
    }

    public static GerenciadorPerifericos getInstancia(){
        if (instancia == null){
            instancia = new GerenciadorPerifericos();
        }
        return instancia;
    }

    private void carregaPerifericos(){
        ResourceBundle rb = ResourceBundle.getBundle("Perifericos");
        String strWindow = rb.getString(WINDOW);
        window = carregaWindow(strWindow);
        String strCmos = rb.getString(CMOS_VIRTUAL);
        cmos = CMOS.getInstancia(strCmos);
        String strDisplay = rb.getString(DISPLAY_VIRTUAL);
        display = (Display) getInstanciaClasse(strDisplay);
        String strImpFiscal = rb.getString(IMPRESSOSRA_FISCAL);
        try{
        	impressoraFiscal = (ImpressoraFiscal) getInstanciaClasse(strImpFiscal);
        }catch(Exception e){
        	JOptionPane.showMessageDialog(window.getFrame(), "Erro na inicialização da Impressora \n Verifique se a impressora está ligada","Atenção",JOptionPane.ERROR_MESSAGE);
        	System.exit(0);
        }
    }

    private Window carregaWindow(String parametros){
        StringTokenizer st = new StringTokenizer(parametros,",");
        String [] param = new String[6];
        for (int i=0;st.hasMoreElements();i++){
            param[i] = (String) st.nextElement();
        }
        String strClasse = param[0];
        String strAltura = param[1];
        String strLargura = param[2];
        String strRed = param[3];
        String strGrenn = param[4];
        String strBlue = param[5];
        int altura = Integer.parseInt(strAltura);
        int largura = Integer.parseInt(strLargura);
        int red = Integer.parseInt(strRed);
        int grenn = Integer.parseInt(strGrenn);
        int blue = Integer.parseInt(strBlue);
        Window win = null;
        try{
            win = (Window) getInstanciaClasse(strClasse);
            win.setTamanho(altura, largura);
            win.setCor(red, grenn, blue);
            win.iniciar();
        }catch(ClassCastException e){
            throw new SistemaException(e);
        }
        return win;
    }

    private Object getInstanciaClasse(String strClasse){
        Object retorno = null;
        try{
            Class c = Class.forName(strClasse);
            retorno = c.newInstance();
        }catch(ClassNotFoundException e){
            throw new SistemaException(e);
        }catch(IllegalAccessException e){
            throw new SistemaException(e);
        }catch(InstantiationException e){
            throw new SistemaException(e);
        }

        return retorno;
    }

    public void atualizaTela(Tela tela) {
    	//new ThreadAtualizaTela(tela);
    	if (display instanceof FrameDisplay){
        	FrameDisplay f = (FrameDisplay) display;
        	tela.getPainel().remove(f);
        	tela.getPainel().add(f);
        }
		window.atualizaTela(tela);
		cmos.gravar(CMOS.TELA_ATUAL, tela);
    }

    public Window getWindow(){
        return window;
    }

    public CMOS getCmos() {
        return cmos;
    }

	public Display getDisplay() {
		return display;
	}

//	private class ThreadAtualizaTela extends Thread{
//		private Tela tela;
//		public void run(){
//			if (display instanceof FrameDisplay){
//	        	FrameDisplay f = (FrameDisplay) display;
//	        	tela.getPainel().remove(f);
//	        	tela.getPainel().add(f);
//	        }
//			window.atualizaTela(tela);
//			cmos.gravar(CMOS.TELA_ATUAL, tela);
//
//		}
//
//		public ThreadAtualizaTela(Tela tela) {
//			this.tela = tela;
//			this.start();
//		}
//
//
//	}


	public EntradaDisplay lerDados(int[] finalizadoras,int tipo, int tamanho ) throws AppException{
		EntradaDisplay retorno = display.lerDados(finalizadoras,tipo, tamanho);
		return retorno;
	}

	public EntradaDisplay esperaVolta() throws AppException{
		EntradaDisplay retorno = display.lerDados(new int[]{Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA,0);
		return retorno;
	}

	public EntradaDisplay esperaEntra() throws AppException{
		EntradaDisplay retorno = display.lerDados(new int[]{Tecla.CODIGO_ENTER},Display.MASCARA_NUMERICA,0);
		return retorno;
	}

	public int incrementaNumeroCupom(){
		Integer num = (Integer) cmos.ler(CMOS.NUMERO_CUPOM);
		int novo = 0;
		if (num != null){
			novo = num.intValue() + 1;
		}
		cmos.gravar(CMOS.NUMERO_CUPOM, new Integer(novo));
		return novo;
	}

	public int incrementaNumeroTransacao(){
		Integer num = (Integer) cmos.ler(CMOS.NUMERO_TRANSACAO);
		int novo = 0;
		if (num != null){
			novo = num.intValue() + 1;
		}
		cmos.gravar(CMOS.NUMERO_TRANSACAO, new Integer(novo));
		return novo;
	}


	private void carregaConstantes(){
		try{
			loja = Integer.parseInt(ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.LOJA).getValor());
			componente =  Integer.parseInt(ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.COMPONENTE).getValor());
			Loja objLoja = Fachada.getInstancia().consultarLojaPorId(new Long(loja));
			getDisplay().setLoja(objLoja.getNome());
			getDisplay().setComponente(""+componente);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public int getCodigoLoja(){
		return this.loja;
	}

	public int getCodigoComponente(){
		return this.componente;
	}

	public ImpressoraFiscal getImpressoraFiscal() {
		return impressoraFiscal;
	}

}
