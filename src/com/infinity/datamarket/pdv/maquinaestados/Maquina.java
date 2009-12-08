package com.infinity.datamarket.pdv.maquinaestados;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import com.infinity.datamarket.av.gui.telas.TelaAVInicial;
import com.infinity.datamarket.av.op.OpAVIniciaAV;
import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.componente.Componente;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ConcentradorParametro;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.infocomponent.InfoComponent;
import com.infinity.datamarket.infocomponent.InfoComponentPK;
import com.infinity.datamarket.pdv.cargabase.ThreadVerificaCargaBase;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOSArquivo;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.gui.telas.Tela;
import com.infinity.datamarket.pdv.infocomponent.ThreadEnviaInfoComponent;
import com.infinity.datamarket.pdv.lote.ThreadVerificaNovoLote;
import com.infinity.datamarket.pdv.op.OpSolicitaCargaBase;

public class Maquina implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3260987050727215663L;
	private Estado estadoAtual;
    private Date dataMov;
    private GerenciadorPerifericos gerenciadorPerifericos;
    private ControladorMaquinaEstado controladorMaquinaEstado;
    private ThreadVerificaNovoLote threadVerificaNovoLote; 
    private ThreadVerificaCargaBase threadVerificaCargaBase;
    private Tela telaInicial;
    private static Maquina instancia;
    private String mensagemInicial;

    public static Maquina getInstancia(Estado estado, Date dataMov, GerenciadorPerifericos gerenciadorPerifericos, ControladorMaquinaEstado controladorME,Tela t, String mensagemInicial ){
        if(instancia == null){
            instancia = new Maquina(estado, dataMov, gerenciadorPerifericos,controladorME,t, mensagemInicial);
            
        }
        return instancia;
    }
    private Maquina(Estado estado, Date dataMov, GerenciadorPerifericos gerenciadorPerifericos, ControladorMaquinaEstado controladorME,Tela t, String mensagemInicial ){
        this.estadoAtual = estado;
        this.dataMov = dataMov;
        this.gerenciadorPerifericos = gerenciadorPerifericos;
        this.controladorMaquinaEstado = controladorME;
        this.telaInicial = t; 
        this.mensagemInicial = mensagemInicial;
    }
    public void iniciar() throws AppException{
        Tela tela = (Tela) gerenciadorPerifericos.getCmos().ler(CMOSArquivo.TELA_ATUAL);
        System.out.println("Maquina.iniciar: tela: "+tela);
        if (tela == null){
        	gerenciadorPerifericos.getDisplay().setMensagem(mensagemInicial);
            gerenciadorPerifericos.atualizaTela(telaInicial);
        }else{
            gerenciadorPerifericos.atualizaTela(tela);
            System.out.println("Maquina.iniciar: atualizei a tela");
            Usuario usu = (Usuario)gerenciadorPerifericos.getCmos().ler(CMOSArquivo.USUARIO_ATUAL);
            if (usu != null){
            	gerenciadorPerifericos.getDisplay().setUsuario(usu.getNome());
            }
        }

        Estado est = (Estado) gerenciadorPerifericos.getCmos().ler(CMOSArquivo.ESTADO_ATUAL);
        if (est != null){
            estadoAtual = est;
        }
        gerenciadorPerifericos.getDisplay().setMensagem(estadoAtual.getDescricao());
        gerenciadorPerifericos.getCmos().gravar(CMOSArquivo.ESTADO_ATUAL,estadoAtual);

        System.out.println("Maquina Iniciada");
        
        int idComponente = ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.COMPONENTE).getValorInteiro();
        int lote = ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.LOTE).getValorInteiro();
        int idLoja = ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.LOJA).getValorInteiro();

        Loja loja = Fachada.getInstancia().consultarLojaPorId(new Long(idLoja));
        gerenciadorPerifericos.getCmos().gravar(CMOSArquivo.LOJA,loja);

        Componente componente = Fachada.getInstancia().consultarComponentePorId(new Long(idComponente));
        gerenciadorPerifericos.getCmos().gravar(CMOSArquivo.COMPONENTE,componente);

		if (componente.getTipoComponente() == Componente.TIPO_COMPONENTE_AV) {
	        if (tela == null) {
	    		tela = (TelaAVInicial) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_AV_INICIAL);
	        }
	        if (tela instanceof  TelaAVInicial) {
	        	OpAVIniciaAV.iniciaAv(this.gerenciadorPerifericos);
	        }
		}
		
        gerenciadorPerifericos.getWindow().atualizaLote(lote);
        threadVerificaNovoLote = new ThreadVerificaNovoLote(lote,idLoja);
        threadVerificaNovoLote.start();
        
        threadVerificaCargaBase = new ThreadVerificaCargaBase(new Long(idComponente),new Long(idLoja));
        threadVerificaCargaBase.start();
        new ThreadProcessaMacro(estadoAtual);
    }
    private ControladorMaquinaEstado getConcentradorMaquina(){
        return controladorMaquinaEstado;
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
        System.out.println("Maquina::Cod "+mic.getId()+" ::MicroOperacao ["+mic.getMicroOperacao().getClasse()+"]");
    	Mic microOperacao = ServiceLocator.getInstancia().getMic(mic.getMicroOperacao().getClasse());
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
    	return ServiceLocator.getInstancia().getMic("com.infinity.datamarket.pdv.op.OpErroSistema");
    }

    private class ThreadProcessaMacro extends Thread implements Serializable{
        private Estado estAtual;
        public void run(){
            try{
            	System.out.println("Maquina.ThreadProcessaMacro.run: id: " + estadoAtual.getId() + " - nome: " + estadoAtual.getDescricao());
//            	RepositoryManagerHibernateUtil.getInstancia().currentSession().load(estadoAtual, estadoAtual.getId());
            	ParametroMacroOperacao param = null;
                EntradaDisplay entrada = null;
                System.out.println("Maquina.ThreadProcessaMacro.run: qtd finalizadoras: "+estadoAtual.getFinalizadoras().size());
                if (estadoAtual.getFinalizadoras() != null && estadoAtual.getFinalizadoras().size() > 0){
                	Evento e = gerenciadorPerifericos.getEvento();
                	System.out.println("Maquina.ThreadProcessaMacro.run: evento: "+e);
                    if (e == null){
	                	entrada = gerenciadorPerifericos.lerDados(estadoAtual.getFinalizadorasArray(),estadoAtual.getInputType(),estadoAtual.getInputSize());
	                    param = new ParametroMacroOperacao(entrada.getDado());
                    }else{
                    	entrada = new EntradaDisplay(e.getParam(),e.getTecla());
                    	param = new ParametroMacroOperacao(e.getParam());
                    }
                }else{
//                    entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 0);
                    entrada = gerenciadorPerifericos.lerDados(null,Display.MASCARA_NUMERICA, 0);                    
                }
                synchronized (OpSolicitaCargaBase.SINCRONIZADOR) {
//                  System.out.println("Maquina.ThreadProcessaMacro.run: 1");
                    PropertyFilter filtro = new PropertyFilter();
//                    System.out.println("Maquina.ThreadProcessaMacro.run: 2");
                    filtro.setTheClass(MacroOperacao.class);
//                    System.out.println("Maquina.ThreadProcessaMacro.run: 3");
                    filtro.addProperty("tecla.codigoASCI", new Integer(entrada.getTeclaFinalizadora()));
//                    System.out.println("Maquina.ThreadProcessaMacro.run: 4");
                    filtro.addProperty("estadoAtual.id", this.estAtual.getId());
//                    System.out.println("Maquina.ThreadProcessaMacro.run: 5");
                    MacroOperacao macroOperacao = getConcentradorMaquina().consultaMacroOperacao(filtro);
//                    System.out.println("Maquina.ThreadProcessaMacro.run: 6");
                    System.out.println("Maquina.ThreadProcessaMacro.run: macroOperacao: "+macroOperacao);
                    if (macroOperacao != null){
                        gerenciadorPerifericos.getCmos().gravar(CMOSArquivo.MACRO_ATUAL, macroOperacao);
//                        System.out.println("Maquina.ThreadProcessaMacro.run: 7");
                        MicroOperacaoAssociada mic = macroOperacao.getMicroOperacaoInicial();
//                        System.out.println("Maquina.ThreadProcessaMacro.run: 8");
                        int alternativa = processaMicroOperacao(mic,param);
//                        System.out.println("Maquina.ThreadProcessaMacro.run: 9");
                        System.out.println("Maquina.ThreadProcessaMacro.run: alternativa: "+alternativa);
                        if (alternativa == Mic.ALTERNATIVA_1){
                            estAtual = macroOperacao.getProximoEstado();
                            estadoAtual = this.estAtual;
                            gerenciadorPerifericos.getCmos().gravar(CMOSArquivo.ESTADO_ATUAL, estAtual);
                        }
                    }
//                    System.out.println("Maquina.ThreadProcessaMacro.run: 10");
                    InfoComponentPK pk = new InfoComponentPK();
//                    System.out.println("Maquina.ThreadProcessaMacro.run: 11");
        			pk.setComponente(gerenciadorPerifericos.getCodigoComponente()+"");
//        			System.out.println("Maquina.ThreadProcessaMacro.run: 12");
        			pk.setLoja(gerenciadorPerifericos.getCodigoLoja()+"");
//        			System.out.println("Maquina.ThreadProcessaMacro.run: 13");
        			InfoComponent info = new InfoComponent();
//        			System.out.println("Maquina.ThreadProcessaMacro.run: 14");
        			info.setPk(pk);
//        			System.out.println("Maquina.ThreadProcessaMacro.run: 15");
        			info.setLote(ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.LOTE).getValor());
//        			System.out.println("Maquina.ThreadProcessaMacro.run: 16");
        			info.setVersao(ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.VERSAO).getValor());
//        			System.out.println("Maquina.ThreadProcessaMacro.run: 17");
        			info.setEstado(estadoAtual.getDescricao());
//        			System.out.println("Maquina.ThreadProcessaMacro.run: 18");
                    ThreadEnviaInfoComponent t2 = new ThreadEnviaInfoComponent(info);
//                    System.out.println("Maquina.ThreadProcessaMacro.run: 19");
            		t2.start();
//            		System.out.println("Maquina.ThreadProcessaMacro.run: 20");

//            		System.out.println("Maquina.ThreadProcessaMacro.run: estadoAtual: "+estadoAtual.getId());
//            		if (estadoAtual.getId().equals(Estado.DISPONIVEL)){
//    	        		//verifica se tem novo lote liberado
//            			System.out.println("Maquina.ThreadProcessaMacro.run: threadVerificaNovoLote.existeNovoLote(): "+threadVerificaNovoLote.existeNovoLote());
//            			if (threadVerificaNovoLote.existeNovoLote()){
//            				System.out.println("HÁ UM NOVO LOTE LIBERADO");
//            				TelaCargaDados tela = new TelaCargaDados(gerenciadorPerifericos.getWindow().getFrame().getWidth(),gerenciadorPerifericos.getWindow().getFrame().getHeight());
//            				Thread thread = new Thread(tela);
//            				thread.start();
//    	        			threadVerificaNovoLote.atualizaLote();
//    	        			gerenciadorPerifericos.getWindow().atualizaLote(threadVerificaNovoLote.getNumeroLote());
//    	        			tela.stop();	        			
//    	        		}else{
//    	        			System.out.println("NÃO HÁ NOVO LOTE LIBERADO");
//    	        		}
//            		}
                    new ThreadProcessaMacro(estadoAtual);
                }
            }catch(Exception ex){            	
            	ex.printStackTrace();
            }finally{
            	RepositoryManagerHibernateUtil.getInstancia().closeSession();
            }
        }

        public ThreadProcessaMacro(Estado estAtual) {
            this.estAtual = estAtual;
            this.start();
        }


    }

}
