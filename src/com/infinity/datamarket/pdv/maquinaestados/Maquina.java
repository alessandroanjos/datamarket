package com.infinity.datamarket.pdv.maquinaestados;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.util.ConcentradorParametro;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.infocomponent.InfoComponent;
import com.infinity.datamarket.infocomponent.InfoComponentPK;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.gui.telas.Tela;
import com.infinity.datamarket.pdv.gui.telas.TelaMenssagem;
import com.infinity.datamarket.pdv.gui.telas.swing.TelaCargaDados;
import com.infinity.datamarket.pdv.infocomponent.ThreadEnviaInfoComponent;
import com.infinity.datamarket.pdv.lote.ThreadVerificaNovoLote;

public class Maquina implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3260987050727215663L;
	private Estado estadoAtual;
    private Date dataMov;
    private GerenciadorPerifericos gerenciadorPerifericos;
    private ThreadVerificaNovoLote threadVerificaNovoLote; 

    private static Maquina instancia;

    public static Maquina getInstancia(Estado estado, Date dataMov, GerenciadorPerifericos gerenciadorPerifericos){
        if(instancia == null){
            instancia = new Maquina(estado, dataMov, gerenciadorPerifericos);
        }
        return instancia;
    }
    private Maquina(Estado estado, Date dataMov, GerenciadorPerifericos gerenciadorPerifericos){
        this.estadoAtual = estado;
        this.dataMov = dataMov;
        this.gerenciadorPerifericos = gerenciadorPerifericos;
    }
    public void iniciar(){
        Tela tela = (Tela) gerenciadorPerifericos.getCmos().ler(CMOS.TELA_ATUAL);
        if (tela == null){
            TelaMenssagem t = (TelaMenssagem) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_MENSAGEM);
            t.setMenssagem("Caixa Fechado");
            gerenciadorPerifericos.getDisplay().setMensagem("Caixa Fechado");
            gerenciadorPerifericos.atualizaTela(t);
        }else{
            gerenciadorPerifericos.atualizaTela(tela);
            Usuario usu = (Usuario)gerenciadorPerifericos.getCmos().ler(CMOS.USUARIO_ATUAL);
            if (usu != null){
            	gerenciadorPerifericos.getDisplay().setUsuario(usu.getNome());
            }
        }

        Estado est = (Estado) gerenciadorPerifericos.getCmos().ler(CMOS.ESTADO_ATUAL);
        if (est != null){
            estadoAtual = est;
            gerenciadorPerifericos.getDisplay().setMensagem(est.getDescricao());
        }

        System.out.println("Maquina Iniciada");
        int lote = ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.LOTE).getValorInteiro();
        int loja = ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.LOJA).getValorInteiro();
        gerenciadorPerifericos.getWindow().atualizaLote(lote);
        threadVerificaNovoLote = new ThreadVerificaNovoLote(lote,loja);
        threadVerificaNovoLote.start();
        new ThreadProcessaMacro(estadoAtual);
    }
    private ConcentradorMaquina getConcentradorMaquina(){
        return ConcentradorMaquina.getInstancia();
    }

    private MicroOperacaoAssociada getProxMicro(int alternativa, Collection saidas){
        if (saidas == null){
            return null;
        }else{
            Iterator i = saidas.iterator();
            while(i.hasNext()){
                SaidaMicroOperacaoAssociada saida = (SaidaMicroOperacaoAssociada) i.next();
                if (saida.getSaida() == alternativa){
                    return saida.getMicroOperacaoAssociada();
                }
            }
            return null;
        }
    }

    private int processaMicroOperacao(MicroOperacaoAssociada mic, ParametroMacroOperacao param){
    	Mic microOperacao = ServiceLocator.getInstancia().getMic(mic.getMicroOperacao().getClasse());
        System.out.println("Maquina::Cod "+mic.getId()+" ::MicroOperacao ["+mic.getMicroOperacao().getClasse()+"]");
        int alternativa = 0;
        try{        	
        	alternativa = microOperacao.exec(gerenciadorPerifericos, param);        	
        }catch(Throwable t){
        	t.printStackTrace();
        	getMicroOperacaoExcessao().exec(gerenciadorPerifericos, param);
        	gerenciadorPerifericos.getDisplay().setMensagem(estadoAtual.getDescricao());
        	return Mic.ALTERNATIVA_2; 
        }
        MicroOperacaoAssociada proxMicro = getProxMicro(alternativa, mic.getSaidas());
        if (proxMicro != null){
            return processaMicroOperacao(proxMicro, param);
        }
        return alternativa;
    }

    private Mic getMicroOperacaoExcessao(){
    	return ServiceLocator.getInstancia().getMic("com.infinity.datamarket.pdv.mic.MicErroSistema");
    }

    private class ThreadProcessaMacro extends Thread implements Serializable{
        private Estado estAtual;
        public void run(){
            try{
            	RepositoryManagerHibernateUtil.currentSession().load(estadoAtual, estadoAtual.getId());
            	ParametroMacroOperacao param = null;
                EntradaDisplay entrada = null;
                if (estadoAtual.getFinalizadoras() != null && estadoAtual.getFinalizadoras().size() > 0){
                	Evento e = gerenciadorPerifericos.getEvento();
                    if (e == null){
	                	entrada = gerenciadorPerifericos.lerDados(estadoAtual.getFinalizadorasArray(),estadoAtual.getInputType(),estadoAtual.getInputSize());
	                    param = new ParametroMacroOperacao(entrada.getDado());
                    }else{
                    	entrada = new EntradaDisplay(e.getParam(),e.getTecla());
                    	param = new ParametroMacroOperacao(e.getParam());
                    }
                }else{
                    entrada = gerenciadorPerifericos.lerDados(null,Display.MASCARA_NUMERICA, 0);
                }
                PropertyFilter filtro = new PropertyFilter();
                filtro.setTheClass(MacroOperacao.class);
                filtro.addProperty("tecla.codigoASCI", new Integer(entrada.getTeclaFinalizadora()));
                filtro.addProperty("estadoAtual.id", this.estAtual.getId());
                MacroOperacao macroOperacao = getConcentradorMaquina().consultaMacroOperacao(filtro);
                if (macroOperacao != null){
                    gerenciadorPerifericos.getCmos().gravar(CMOS.MACRO_ATUAL, macroOperacao);
                    MicroOperacaoAssociada mic = macroOperacao.getMicroOperacaoInicial();
                    int alternativa = processaMicroOperacao(mic,param);
                    if (alternativa == Mic.ALTERNATIVA_1){
                        estAtual = macroOperacao.getProximoEstado();
                        estadoAtual = this.estAtual;
                        gerenciadorPerifericos.getCmos().gravar(CMOS.ESTADO_ATUAL, estAtual);
                    }
                }
                InfoComponentPK pk = new InfoComponentPK();
    			pk.setComponente(gerenciadorPerifericos.getCodigoComponente()+"");
    			pk.setLoja(gerenciadorPerifericos.getCodigoLoja()+"");
    			InfoComponent info = new InfoComponent();
    			info.setPk(pk);
    			info.setLote(ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.LOTE).getValor());
    			info.setVersao(ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.VERSAO).getValor());
    			info.setEstado(estadoAtual.getDescricao());
                ThreadEnviaInfoComponent t2 = new ThreadEnviaInfoComponent(info);
        		t2.start();
        		if (estadoAtual.getId().equals(Estado.DISPONIVEL)){
	        		//verifica se tem novo lote liberado
        			if (threadVerificaNovoLote.existeNovoLote()){
        				System.out.println("HÁ UM NOVO LOTE LIBERADO");
        				TelaCargaDados tela = new TelaCargaDados(gerenciadorPerifericos.getWindow().getFrame().getWidth(),gerenciadorPerifericos.getWindow().getFrame().getHeight());
        				Thread thread = new Thread(tela);
        				thread.start();
	        			threadVerificaNovoLote.atualizaLote();
	        			gerenciadorPerifericos.getWindow().atualizaLote(threadVerificaNovoLote.getNumeroLote());
	        			tela.stop();	        			
	        		}else{
	        			System.out.println("NÃO HÁ NOVO LOTE LIBERADO");
	        		}
        		}
                new ThreadProcessaMacro(estadoAtual);
            }catch(Exception ex){            	
            	ex.printStackTrace();
            }finally{
            	RepositoryManagerHibernateUtil.closeSession();
            }
        }

        public ThreadProcessaMacro(Estado estAtual) {
            this.estAtual = estAtual;
            this.start();
        }


    }

}
