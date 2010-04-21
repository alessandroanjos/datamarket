package com.infinity.datamarket.pdv.maquinaestados;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

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
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOSArquivo;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.gui.telas.Tela;
import com.infinity.datamarket.pdv.gui.telas.TelaComMenu;
import com.infinity.datamarket.pdv.gui.telas.swing.ExibeMenuFrame;
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
//        threadVerificaNovoLote = new ThreadVerificaNovoLote(lote,idLoja);
//        threadVerificaNovoLote.start();
        
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
        System.out.println("Maquina::Cod "+mic.getId()+" ::MicroOperacao ["+mic.getMicroOperacao().getClasse()+"] ## " );
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
                    entrada = gerenciadorPerifericos.lerDados(null,Display.MASCARA_NUMERICA, 0);                    
                }
                synchronized (OpSolicitaCargaBase.SINCRONIZADOR) {
                    PropertyFilter filtro = new PropertyFilter();
                    filtro.setTheClass(MacroOperacao.class);
                    filtro.addProperty("tecla.codigoASCI", new Integer(entrada.getTeclaFinalizadora()));
                    filtro.addProperty("estadoAtual.id", this.estAtual.getId());
                    MacroOperacao macroOperacao = getConcentradorMaquina().consultaMacroOperacao(filtro);
                    if (macroOperacao != null) {
                    	System.out.println("Maquina.ThreadProcessaMacro.run: macroOperacao: "+macroOperacao.getId() + " ## " + macroOperacao.getDescricao()  );
                    
                    } else {
                    	System.out.println("Maquina.ThreadProcessaMacro.run: macroOperacao: null" );
                    }
                    
                    if (macroOperacao == null) {
                    	// se o estado atual tem teclas 
                    	// se a tecla apertada for do tipo menu
	                    if (this.estAtual != null && this.estAtual.getTeclaMenu() != null && this.estAtual.getTeclaMenu().getCodigoASCI() == entrada.getTeclaFinalizadora()) {
	
        					Map<Tecla, MacroOperacao> teclaMacro = getTeclaMacro(this.estAtual.getId());
	                    	if (teclaMacro != null) {
	        					
	        					ExibeMenuFrame c = new ExibeMenuFrame(gerenciadorPerifericos.getWindow().getFrame(),teclaMacro);
	        			
	        			    	c.setSize(800, 530);
	        			    	c.play();
	        			    	
	        			    	if (c.getRetornoTela() == c.BUTTON_OK){
	        			    		macroOperacao= c.getValor();
	        			    	}
	        				}
	                    }
                    }
                    
                    if (macroOperacao != null){
                        gerenciadorPerifericos.getCmos().gravar(CMOSArquivo.MACRO_ATUAL, macroOperacao);
                        MicroOperacaoAssociada mic = macroOperacao.getMicroOperacaoInicial();
                        int alternativa = processaMicroOperacao(mic,param);
                        System.out.println("Maquina.ThreadProcessaMacro.run: alternativa: "+alternativa);
                        if (alternativa == Mic.ALTERNATIVA_1){
                            estAtual = macroOperacao.getProximoEstado();
                            estadoAtual = this.estAtual;
                            gerenciadorPerifericos.getCmos().gravar(CMOSArquivo.ESTADO_ATUAL, estAtual);

                        }
                    } 
                    
                    // se a tela do periferico extends de TelaComMenu entao seta as opceos na tela
                    if (gerenciadorPerifericos.getCmos().ler(CMOSArquivo.TELA_ATUAL) instanceof TelaComMenu){
    					TelaComMenu telaComMenu = (TelaComMenu)gerenciadorPerifericos.getCmos().ler(CMOSArquivo.TELA_ATUAL);
    					telaComMenu.apagarLayoutMenu();
    					
                    	Map<Tecla, MacroOperacao> teclaMacro = getTeclaMacro(this.estAtual.getId());

                    	Iterator<Tecla> it = teclaMacro.keySet().iterator();
                    	while (it.hasNext()) {
                    		Tecla tecla = it.next();
                    		MacroOperacao macro = teclaMacro.get(tecla);
                    		telaComMenu.adicionarLayoutMenu(tecla.getDescricao() + " - " + macro);
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

        /**
         * Método qeu retorna as telca e os macros associados a ao um estado,
         * esse metodo remove as teclas que o usuario nao tem permissao
         * 
         * @param teclaMacro
         * @throws AppException
         */
		private Map<Tecla, MacroOperacao> getTeclaMacro(Long idEstadoAtual) throws AppException {
			
			Map<Tecla, MacroOperacao> teclaMacro = getConcentradorMaquina().getDescTeclasDescMacro(idEstadoAtual);
        	if (teclaMacro != null) {

	            int idComponente = ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.COMPONENTE).getValorInteiro();
				Componente componente = Fachada.getInstancia().consultarComponentePorId(new Long(idComponente));
	
				// se tem usuario logado
				// veja quais as macros que ele tem permissao
				Usuario usuario =(Usuario) gerenciadorPerifericos.getCmos().ler(CMOS.OPERADOR_ATUAL);
				if (usuario != null) {
					Iterator it = teclaMacro.keySet().iterator();
					while(it.hasNext()) {
						Tecla tecla = (Tecla)it.next();
						MacroOperacao mo = teclaMacro.get(tecla);
						
						try {
							Fachada.getInstancia().consultarUsuarioPorId_IdMacro(usuario.getId(), mo.getId(), componente.getTipoComponente());
						} catch (Exception e) {
							it.remove();
						}
					}
				}
        	}
			return teclaMacro;
		}

        public ThreadProcessaMacro(Estado estAtual) {
            this.estAtual = estAtual;
            this.start();
        }


    }

}
