package com.infinity.datamarket.pdv.tef.paygo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import com.infinity.datamarket.comum.util.Util;
import com.infinity.datamarket.pdv.tef.ExcecaoTEF;
import com.infinity.datamarket.pdv.tef.RespostaOperacaoTEF;
import com.infinity.datamarket.pdv.tef.SolicitacaoOperacaoTEF;
import com.infinity.datamarket.pdv.tef.TEF;
import com.infinity.datamarket.pdv.tef.RespostaOperacaoTEF.ParcelaTEF;

public class TEFPayGo implements TEF {
	
	public final static String NOME_ARQUIVO = "IntPos";
	public final static String EXTENSAO_TEMPORARIA = ".tmp";
	public final static String EXTENSAO_PRINCIPAL = ".001";
	public final static String EXTENSAO_STATUS = ".Sts";
	public final static String QUEBRA_LINHA = "\r\n";
	public final static String EXTENSAO_MINE = ".mine";
	
	public final static String OPERACAO_VERIFICA_SERVICO = "ATV";
	//	ATV		Verifica se o Gerenciador Padrão está ativo
	public final static String OPERACAO_ADMINISTRACAO = "ADM";
	//ADM		Permite o acionamento da Solução TEF Discado para execução das funções administrativas
	public final static String OPERACAO_CHEQUE = "CHQ";
	//CHQ*		Pedido de autorização para transação por meio de cheque
	public final static String OPERACAO_CARTAO = "CRT";
	//CRT		Pedido de autorização para transação por meio de cartão
	public final static String OPERACAO_CANCELAMENTO = "CNC";
	//CNC		Cancelamento de venda efetuada por qualquer meio de pagamento
	public final static String OPERACAO_CONFIRMACAO = "CNF";
	//CNF		Confirmação da venda e impressão de cupom
	public final static String OPERACAO_DESFAZIMENTO = "NCN";
	//NCN		Não confirmação da venda e/ou da impressão.

	public final static long TIMEOUT_OPERACAO = 120000;
	public final static long TIMEOUT_RESPOSTA = 7000;
	
	public final static String FINALIZA_ARQUIVO = "999-999 = 0";
	
	public final static String DIRETORIO_REQUISICAO = "C:\\TEF_Dial\\Req\\";
	public final static String DIRETORIO_RESPOSTA = "C:\\TEF_Dial\\Resp\\";

	public final static String TIPO_TRANSACAO_00 ="00"; //   00-	Administrativas - Outras (Reimpressão, Iniciação de Terminal etc.)
	public final static String TIPO_TRANSACAO_01 ="01"; //   01-	Administrativa - Fechamento/Transmissão de Lote                   
	public final static String TIPO_TRANSACAO_10 ="10"; //   10- Cartão de Crédito à Vista                                             
	public final static String TIPO_TRANSACAO_11 ="11"; //   11- Cartão de Crédito Parcelado pelo Estabelecimento                      
	public final static String TIPO_TRANSACAO_12 ="12"; //   12- Cartão de Crédito Parcelado pela Administradora                       
	public final static String TIPO_TRANSACAO_13 ="13"; //   13- Pré-Autorização com Cartão de Crédito                                 
	public final static String TIPO_TRANSACAO_20 ="20"; //   20- Cartão de Débito à Vista                                              
	public final static String TIPO_TRANSACAO_21 ="21"; //   21- Cartão de Débito Pré-Datado                                           
	public final static String TIPO_TRANSACAO_22 ="22"; //   22- Cartão de Débito Parcelada                                            
	public final static String TIPO_TRANSACAO_23 ="23"; //   23- Cartão de Débito à Vista Forçada                                      
	public final static String TIPO_TRANSACAO_24 ="24"; //   24- Cartão de Débito Pré-Datado Forçada                                   
	public final static String TIPO_TRANSACAO_25 ="25"; //   25- Cartão de Débito Pré-Datado sem Garantia                              
	public final static String TIPO_TRANSACAO_30 ="30"; //   30- Outros Cartões                                                        
	public final static String TIPO_TRANSACAO_40 ="40"; //   40- CDC                                                                   
	public final static String TIPO_TRANSACAO_41 ="41"; //   41- Consulta CDC                                                          
	public final static String TIPO_TRANSACAO_50 ="50"; //   50- Convênio                                                              
	public final static String TIPO_TRANSACAO_60 ="60"; //   60- Voucher                                                               
	public final static String TIPO_TRANSACAO_70 ="70"; //   70- Consulta Cheque                                                       
	public final static String TIPO_TRANSACAO_71 ="71"; //   71- Garantia de Cheque                                                    
	public final static String TIPO_TRANSACAO_99 ="99"; //   99-Outras                                                                 

	//Constantes da solicitacao
	public final static Byte MOEDA_REAL = new Byte((byte)0);
	public final static Byte MOEDA_DOLAR = new Byte((byte)1);
	
	public final static String PESSOA_FISICA = "F";
	public final static String PESSOA_JURIDICA = "J";
	
	public final static String VISANET = "VISANET";
	public final static String REDECARD = "REDECARD";
	public final static String AMEX = "AMEX";
	//fim das constantes da solicitacao
	
	// constantes da resposta
	public final static int PARCELAMENTO_ESTABELECIMENTO = 0;
	public final static int PARCELAMENTO_ADMINISTRADORA = 1;
	// fim das constantes da resposta
	
	public static TEFPayGo instancia;
	
	public static TEFPayGo getInstancia(){
		if (instancia == null){
			instancia = new TEFPayGo();
		}
		return instancia;
	}
	
	
	private void validarSolicitacaoOperacaoTEF(SolicitacaoOperacaoTEF solicitacaoOperacaoTEF) throws ExcecaoTEF{
		if(solicitacaoOperacaoTEF.getIdentificacao() != null 
				&& (solicitacaoOperacaoTEF.getIdentificacao().toString().length() <= 0 || solicitacaoOperacaoTEF.getIdentificacao().toString().length() > 10)){
			throw new ExcecaoTEF("Campo Identificacao [001-000] invalido.");
		}
		
		if(solicitacaoOperacaoTEF.getNumeroCOO() != null 
				&& (solicitacaoOperacaoTEF.getNumeroCOO().toString().length() <= 0 || solicitacaoOperacaoTEF.getNumeroCOO().toString().length() > 12)){
			throw new ExcecaoTEF("Campo NumeroCOO [002-000] invalido.");
		}
		
		if(solicitacaoOperacaoTEF.getValorOperacao() != null 
				&& (solicitacaoOperacaoTEF.getValorOperacao().movePointRight(2).toString().length() <= 0 || solicitacaoOperacaoTEF.getValorOperacao().movePointRight(2).toString().length() > 12)){
			throw new ExcecaoTEF("Campo ValorOperacao [003-000] invalido.");
		}
		
		if(solicitacaoOperacaoTEF.getMoeda() != null 
				&& (solicitacaoOperacaoTEF.getMoeda().toString().length() <= 0 || solicitacaoOperacaoTEF.getMoeda().toString().length() > 1)
				&& (!solicitacaoOperacaoTEF.getMoeda().equals(MOEDA_REAL) || !solicitacaoOperacaoTEF.getMoeda().equals(MOEDA_DOLAR))){
			throw new ExcecaoTEF("Campo Moeda [004-000] invalido.");
		}
		
		if(solicitacaoOperacaoTEF.getCmc7() != null 
				&& (solicitacaoOperacaoTEF.getCmc7().length() <= 0 || solicitacaoOperacaoTEF.getCmc7().length() > 70)){
			throw new ExcecaoTEF("Campo CMC7 [005-000] invalido.");
		}
		
		if(solicitacaoOperacaoTEF.getTipoPessoa() != null 
				&& (solicitacaoOperacaoTEF.getTipoPessoa().length() <= 0 || solicitacaoOperacaoTEF.getTipoPessoa().length() > 1)
				&& (!solicitacaoOperacaoTEF.getTipoPessoa().equals(PESSOA_FISICA) || !solicitacaoOperacaoTEF.getTipoPessoa().equals(PESSOA_JURIDICA))){
			throw new ExcecaoTEF("Campo TipoPessoa [006-000] invalido.");
		}
		
		if(solicitacaoOperacaoTEF.getDocumentoPessoa() != null
				&& (solicitacaoOperacaoTEF.getDocumentoPessoa().toString().length() == 0 || solicitacaoOperacaoTEF.getDocumentoPessoa().toString().length() > 16 )){
			throw new ExcecaoTEF("Campo DocumentoPessoa [007-000] invalido.");
		}
		
		if(solicitacaoOperacaoTEF.getDataCheque() != null){
//			throw new ExcecaoTEF("Campo DataCheque [008-000] invalido.");
		}
		
		if(solicitacaoOperacaoTEF.getNomeRede() != null 
				&& (solicitacaoOperacaoTEF.getNomeRede().length() <= 0 || solicitacaoOperacaoTEF.getNomeRede().length() > 8)
				&& (!solicitacaoOperacaoTEF.getNomeRede().equals(AMEX) || !solicitacaoOperacaoTEF.getNomeRede().equals(VISANET) || !solicitacaoOperacaoTEF.getNomeRede().equals(REDECARD))){
			throw new ExcecaoTEF("Campo NomeRede [010-000] invalido.");
		}
		
		if(solicitacaoOperacaoTEF.getNsuTEF() != null 
				&& (solicitacaoOperacaoTEF.getNsuTEF().toString().length() <= 0 || solicitacaoOperacaoTEF.getNsuTEF().toString().length() > 12)){
			throw new ExcecaoTEF("Campo NsuTEF [012-000] invalido.");
		}
		
		if(solicitacaoOperacaoTEF.getDataHoraTransacao() != null){
//			throw new ExcecaoTEF("Campo DataHoraTransacao [022-000] invalido.");
		}
		
		if(solicitacaoOperacaoTEF.getDataHoraTransacao() != null){
//			throw new ExcecaoTEF("Campo DataHoraTransacao [023-000] invalido.");
		}

		if(solicitacaoOperacaoTEF.getChaveFinalizacao() != null 
				&& (solicitacaoOperacaoTEF.getChaveFinalizacao().length() <= 0 || solicitacaoOperacaoTEF.getChaveFinalizacao().length() > 30)){
			throw new ExcecaoTEF("Campo ChaveFinalizacao [027-000] invalido.");
		}
		
		if(solicitacaoOperacaoTEF.getBanco() != null 
				&& (solicitacaoOperacaoTEF.getBanco().length() <= 0 || solicitacaoOperacaoTEF.getBanco().length() > 4)){
			throw new ExcecaoTEF("Campo Banco [033-000] invalido.");
		}
		
		if(solicitacaoOperacaoTEF.getAgencia() != null 
				&& (solicitacaoOperacaoTEF.getAgencia().length() <= 0 || solicitacaoOperacaoTEF.getAgencia().length() > 20)){
			throw new ExcecaoTEF("Campo Agencia [034-000] invalido.");
		}
		
		if(solicitacaoOperacaoTEF.getDigitoAgencia() != null 
				&& (solicitacaoOperacaoTEF.getDigitoAgencia().length() <= 0 || solicitacaoOperacaoTEF.getDigitoAgencia().length() > 2)){
			throw new ExcecaoTEF("Campo DigitoAgencia [035-000] invalido.");
		}
		
		if(solicitacaoOperacaoTEF.getContaCorrente() != null 
				&& (solicitacaoOperacaoTEF.getContaCorrente().length() <= 0 || solicitacaoOperacaoTEF.getContaCorrente().length() > 20)){
			throw new ExcecaoTEF("Campo ContaCorrente [036-000] invalido.");
		}
		
		if(solicitacaoOperacaoTEF.getDigitoContaCorrente() != null 
				&& (solicitacaoOperacaoTEF.getDigitoContaCorrente().length() <= 0 || solicitacaoOperacaoTEF.getDigitoContaCorrente().length() > 2)){
			throw new ExcecaoTEF("Campo DigitoContaCorrente [037-000] invalido.");
		}
		
		if(solicitacaoOperacaoTEF.getNumeroCheque() != null 
				&& (solicitacaoOperacaoTEF.getNumeroCheque().length() <= 0 || solicitacaoOperacaoTEF.getNumeroCheque().length() > 20)){
			throw new ExcecaoTEF("Campo NumeroCheque [038-000] invalido.");
		}
		
		if(solicitacaoOperacaoTEF.getDigitoCheque() != null 
				&& (solicitacaoOperacaoTEF.getDigitoCheque().length() <= 0 || solicitacaoOperacaoTEF.getDigitoCheque().length() > 2)){
			throw new ExcecaoTEF("Campo DigitoCheque [039-000] invalido.");
		}
	}
	
	public RespostaOperacaoTEF cancelaOperacao(SolicitacaoOperacaoTEF solicitacaoOperacaoTEF, boolean operacaoCheque) throws ExcecaoTEF {
		RespostaOperacaoTEF resposta = null;
		
		validarSolicitacaoOperacaoTEF(solicitacaoOperacaoTEF);
		
		verificaStatusServico(solicitacaoOperacaoTEF.getIdentificacao());
		Util.apagarArquivos(new File(DIRETORIO_REQUISICAO), filtro);

		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			File arqTemp = new File(DIRETORIO_REQUISICAO + NOME_ARQUIVO + EXTENSAO_TEMPORARIA);
			fw = new FileWriter(arqTemp);
			bw = new BufferedWriter(fw);
			
			bw.write("000-000 = " + OPERACAO_CANCELAMENTO + QUEBRA_LINHA);
			bw.write("001-000 = " + String.valueOf(solicitacaoOperacaoTEF.getIdentificacao()) + QUEBRA_LINHA);
			
			if(solicitacaoOperacaoTEF.getNumeroCOO() != null){
				bw.write("002-000 = " + solicitacaoOperacaoTEF.getNumeroCOO().toString() + QUEBRA_LINHA);	
			}
			
			bw.write("003-000 = " + String.valueOf(solicitacaoOperacaoTEF.getValorOperacao().movePointRight(2)) + QUEBRA_LINHA);
			
			if(operacaoCheque){
				bw.write("005-000 = " + solicitacaoOperacaoTEF.getCmc7() + QUEBRA_LINHA);
				bw.write("006-000 = " + solicitacaoOperacaoTEF.getTipoPessoa() + QUEBRA_LINHA);
				bw.write("007-000 = " + Util.completaString(solicitacaoOperacaoTEF.getDocumentoPessoa().toString(), "0", 16, true) + QUEBRA_LINHA);
				bw.write("008-000 = " + Util.retornaDataFormatoDDMMYYYY(solicitacaoOperacaoTEF.getDataCheque()) + QUEBRA_LINHA);
			}
			
			bw.write("010-000 = " + solicitacaoOperacaoTEF.getNomeRede() + QUEBRA_LINHA);
			bw.write("012-000 = " + solicitacaoOperacaoTEF.getNsuTEF() + QUEBRA_LINHA);
			bw.write("022-000 = " + Util.retornaDataFormatoDDMMYYYY(solicitacaoOperacaoTEF.getDataHoraTransacao()) + QUEBRA_LINHA);
			bw.write("023-000 = " + Util.retornaHoraFormatoHHMMSS(solicitacaoOperacaoTEF.getDataHoraTransacao()) + QUEBRA_LINHA);
			
			if(operacaoCheque){
				bw.write("033-000 = " + solicitacaoOperacaoTEF.getBanco() + QUEBRA_LINHA);
				bw.write("034-000 = " + solicitacaoOperacaoTEF.getAgencia() + QUEBRA_LINHA);
				bw.write("035-000 = " + solicitacaoOperacaoTEF.getDigitoAgencia() + QUEBRA_LINHA);
				bw.write("036-000 = " + solicitacaoOperacaoTEF.getContaCorrente() + QUEBRA_LINHA);
				bw.write("037-000 = " + solicitacaoOperacaoTEF.getDigitoContaCorrente() + QUEBRA_LINHA);
				bw.write("038-000 = " + solicitacaoOperacaoTEF.getNumeroCheque() + QUEBRA_LINHA);
				bw.write("039-000 = " + solicitacaoOperacaoTEF.getDigitoCheque() + QUEBRA_LINHA);				
			}
			
			bw.write(FINALIZA_ARQUIVO + QUEBRA_LINHA);
			
			bw.flush();
			fw.flush();			
			
			if(fw != null){
				try {
					fw.close();} catch (IOException e) {
					e.printStackTrace();
					throw new ExcecaoTEF("TEF Indisponivel!", e);
				}
			}
			if(bw != null){
				try {
					bw.close();} catch (IOException e) {
					e.printStackTrace();
					throw new ExcecaoTEF("TEF Indisponivel!", e);
				}
			}
			
			File arqFinal = new File(DIRETORIO_REQUISICAO + NOME_ARQUIVO + EXTENSAO_PRINCIPAL);
			if(arqTemp.exists()){
				arqTemp.renameTo(arqFinal);
			}
			
			long start = System.currentTimeMillis();
			
			File arqResposta = new File(DIRETORIO_RESPOSTA + NOME_ARQUIVO + EXTENSAO_PRINCIPAL);
			
			while(true){
				if(System.currentTimeMillis() - start > TIMEOUT_OPERACAO && !arqResposta.exists()){
					//cancela operacao
					throw new ExcecaoTEF("Operacao Cancelada!");
				}else if(arqResposta.exists()){
					break;
				}
			}
			
			//criar metodo para preencher a resposta do arquivo.
			resposta = preencheRespostaSolicitacaoTEF(arqResposta);
			
			//deletar arquivos de resposta IntPos.Sts e IntPos.001 do diretorio de RESPOSTA
			Util.apagarArquivos(new File(DIRETORIO_RESPOSTA), filtro);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ExcecaoTEF("TEF Indisponivel!", e);
		} finally{
			if(fw != null){
				try {
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new ExcecaoTEF("TEF Indisponivel!", e);
				}
			}
			if(bw != null){
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new ExcecaoTEF("TEF Indisponivel!", e);
				}
			}
		}				

		return resposta;
	}

	public RespostaOperacaoTEF confirmaOperacao(SolicitacaoOperacaoTEF solicitacaoOperacaoTEF) throws ExcecaoTEF {
		RespostaOperacaoTEF resposta = null;
		
		validarSolicitacaoOperacaoTEF(solicitacaoOperacaoTEF);
		
		verificaStatusServico(solicitacaoOperacaoTEF.getIdentificacao());
		Util.apagarArquivos(new File(DIRETORIO_REQUISICAO), filtro);

		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			File arqTemp = new File(DIRETORIO_REQUISICAO + NOME_ARQUIVO + EXTENSAO_TEMPORARIA);
			fw = new FileWriter(arqTemp);
			bw = new BufferedWriter(fw);
			
			bw.write("000-000 = " + OPERACAO_CONFIRMACAO + QUEBRA_LINHA);
			bw.write("001-000 = " + String.valueOf(solicitacaoOperacaoTEF.getIdentificacao()) + QUEBRA_LINHA);
			
			if(solicitacaoOperacaoTEF.getNumeroCOO() != null){
				bw.write("002-000 = " + solicitacaoOperacaoTEF.getNumeroCOO().toString() + QUEBRA_LINHA);	
			}
			
			bw.write("010-000 = " + solicitacaoOperacaoTEF.getNomeRede() + QUEBRA_LINHA);
			bw.write("012-000 = " + solicitacaoOperacaoTEF.getNsuTEF() + QUEBRA_LINHA);
			bw.write("027-000 = " + solicitacaoOperacaoTEF.getChaveFinalizacao() + QUEBRA_LINHA);
			
			bw.write(FINALIZA_ARQUIVO + QUEBRA_LINHA);
			
			bw.flush();
			fw.flush();			
			
			if(fw != null){
				try {
					fw.close();} catch (IOException e) {
					e.printStackTrace();
					throw new ExcecaoTEF("TEF Indisponivel!", e);
				}
			}
			if(bw != null){
				try {
					bw.close();} catch (IOException e) {
					e.printStackTrace();
					throw new ExcecaoTEF("TEF Indisponivel!", e);
				}
			}
			
			File arqFinal = new File(DIRETORIO_REQUISICAO + NOME_ARQUIVO + EXTENSAO_PRINCIPAL);
			if(arqTemp.exists()){
				arqTemp.renameTo(arqFinal);
			}
			
			long start = System.currentTimeMillis();
			
			File arqResposta = new File(DIRETORIO_RESPOSTA + NOME_ARQUIVO + EXTENSAO_STATUS);
			
			while(true){
				if(System.currentTimeMillis() - start > TIMEOUT_OPERACAO && !arqResposta.exists()){
					//cancela operacao
					throw new ExcecaoTEF("Operacao Cancelada!");
				}else if(arqResposta.exists()){
					break;
				}
			}
			
			//criar metodo para preencher a resposta do arquivo.
			resposta = preencheRespostaSolicitacaoTEF(arqResposta);
			
			//deletar arquivos de resposta IntPos.Sts e IntPos.001 do diretorio de RESPOSTA
			Util.apagarArquivos(new File(DIRETORIO_RESPOSTA), filtro);			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ExcecaoTEF("TEF Indisponivel!", e);
		} finally{
			if(fw != null){
				try {
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new ExcecaoTEF("TEF Indisponivel!", e);
				}
			}
			if(bw != null){
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new ExcecaoTEF("TEF Indisponivel!", e);
				}
			}
		}				

		return resposta;
	}

	public RespostaOperacaoTEF desfazOperacao(SolicitacaoOperacaoTEF solicitacaoOperacaoTEF) throws ExcecaoTEF {		
		RespostaOperacaoTEF resposta = null;
		
		validarSolicitacaoOperacaoTEF(solicitacaoOperacaoTEF);
		
		verificaStatusServico(solicitacaoOperacaoTEF.getIdentificacao());
		Util.apagarArquivos(new File(DIRETORIO_REQUISICAO), filtro);
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			File arqTemp = new File(DIRETORIO_REQUISICAO + NOME_ARQUIVO + EXTENSAO_TEMPORARIA);
			fw = new FileWriter(arqTemp);
			bw = new BufferedWriter(fw);
			
			bw.write("000-000 = " + OPERACAO_DESFAZIMENTO + QUEBRA_LINHA);
			bw.write("001-000 = " + String.valueOf(solicitacaoOperacaoTEF.getIdentificacao()) + QUEBRA_LINHA);
			
			if(solicitacaoOperacaoTEF.getNumeroCOO() != null){
				bw.write("002-000 = " + solicitacaoOperacaoTEF.getNumeroCOO().toString() + QUEBRA_LINHA);	
			}
			
			bw.write("010-000 = " + solicitacaoOperacaoTEF.getNomeRede() + QUEBRA_LINHA);
			bw.write("012-000 = " + solicitacaoOperacaoTEF.getNsuTEF() + QUEBRA_LINHA);
			bw.write("027-000 = " + solicitacaoOperacaoTEF.getChaveFinalizacao() + QUEBRA_LINHA);
			
			bw.write(FINALIZA_ARQUIVO + QUEBRA_LINHA);
			
			bw.flush();
			fw.flush();			
			
			if(fw != null){
				try {
					fw.close();} catch (IOException e) {
					e.printStackTrace();
					throw new ExcecaoTEF("TEF Indisponivel!", e);
				}
			}
			if(bw != null){
				try {
					bw.close();} catch (IOException e) {
					e.printStackTrace();
					throw new ExcecaoTEF("TEF Indisponivel!", e);
				}
			}
			
			File arqFinal = new File(DIRETORIO_REQUISICAO + NOME_ARQUIVO + EXTENSAO_PRINCIPAL);
			if(arqTemp.exists()){
				arqTemp.renameTo(arqFinal);
			}
			
			long start = System.currentTimeMillis();
			
			File arqResposta = new File(DIRETORIO_RESPOSTA + NOME_ARQUIVO + EXTENSAO_STATUS);
			
			while(true){
				if(System.currentTimeMillis() - start > TIMEOUT_OPERACAO && !arqResposta.exists()){
					//cancela operacao
					throw new ExcecaoTEF("Operacao Cancelada!");
				}else if(arqResposta.exists()){
					break;
				}
			}
			
			//criar metodo para preencher a resposta do arquivo.
			resposta = preencheRespostaSolicitacaoTEF(arqResposta);
			
			//deletar arquivos de resposta IntPos.Sts e IntPos.001 do diretorio de RESPOSTA
			Util.apagarArquivos(new File(DIRETORIO_RESPOSTA), filtro);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ExcecaoTEF("TEF Indisponivel!", e);
		} finally{
			if(fw != null){
				try {
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new ExcecaoTEF("TEF Indisponivel!", e);
				}
			}
			if(bw != null){
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new ExcecaoTEF("TEF Indisponivel!", e);
				}
			}
		}				

		return resposta;
	}

	public RespostaOperacaoTEF solicitaOperacaoAdministracao(SolicitacaoOperacaoTEF solicitacaoOperacaoTEF) throws ExcecaoTEF {
		RespostaOperacaoTEF resposta = null;
		
		validarSolicitacaoOperacaoTEF(solicitacaoOperacaoTEF);
		
		verificaStatusServico(solicitacaoOperacaoTEF.getIdentificacao());
		Util.apagarArquivos(new File(DIRETORIO_REQUISICAO), filtro);
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			File arqTemp = new File(DIRETORIO_REQUISICAO + NOME_ARQUIVO + EXTENSAO_TEMPORARIA);
			fw = new FileWriter(arqTemp);
			bw = new BufferedWriter(fw);
			
			bw.write("000-000 = " + OPERACAO_ADMINISTRACAO + QUEBRA_LINHA);
			bw.write("001-000 = " + String.valueOf(solicitacaoOperacaoTEF.getIdentificacao()) + QUEBRA_LINHA);
			bw.write(FINALIZA_ARQUIVO + QUEBRA_LINHA);
			
			bw.flush();
			fw.flush();			
			
			if(fw != null){
				try {
					fw.close();} catch (IOException e) {
					e.printStackTrace();
					throw new ExcecaoTEF("TEF Indisponivel!", e);
				}
			}
			if(bw != null){
				try {
					bw.close();} catch (IOException e) {
					e.printStackTrace();
					throw new ExcecaoTEF("TEF Indisponivel!", e);
				}
			}
			
			File arqFinal = new File(DIRETORIO_REQUISICAO + NOME_ARQUIVO + EXTENSAO_PRINCIPAL);
			if(arqTemp.exists()){
				arqTemp.renameTo(arqFinal);
			}
			
			long start = System.currentTimeMillis();
			
			File arqResposta = new File(DIRETORIO_RESPOSTA + NOME_ARQUIVO + EXTENSAO_PRINCIPAL);
			
			while(true){
				if(System.currentTimeMillis() - start > TIMEOUT_OPERACAO && !arqResposta.exists()){
					//cancela operacao
					throw new ExcecaoTEF("Operacao Cancelada!");
				}else if(arqResposta.exists()){
					break;
				}
			}
			
			//criar metodo para preencher a resposta do arquivo.
			resposta = preencheRespostaSolicitacaoTEF(arqResposta);
			
			//deletar arquivos de resposta IntPos.Sts e IntPos.001 do diretorio de RESPOSTA
			Util.apagarArquivos(new File(DIRETORIO_RESPOSTA), filtro);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ExcecaoTEF("TEF Indisponivel!", e);
		} finally{
			if(fw != null){
				try {
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new ExcecaoTEF("TEF Indisponivel!", e);
				}
			}
			if(bw != null){
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new ExcecaoTEF("TEF Indisponivel!", e);
				}
			}
		}				

		return resposta;
	}

	public boolean validarServicoAtivo() throws ExcecaoTEF {

		try {
			verificaStatusServico(1l);	
		} catch (Exception e) {
			return false;
		}
		return true;

	}


	
	public RespostaOperacaoTEF solicitaOperacaoCartao(SolicitacaoOperacaoTEF solicitacaoOperacaoTEF) throws ExcecaoTEF {
		RespostaOperacaoTEF resposta = null;
		System.out.println("solicitaOperacaoCartao " + 01);
		validarSolicitacaoOperacaoTEF(solicitacaoOperacaoTEF);
		System.out.println("solicitaOperacaoCartao " + 02);
		
		verificaStatusServico(solicitacaoOperacaoTEF.getIdentificacao());
		Util.apagarArquivos(new File(DIRETORIO_REQUISICAO), filtro);
		
		System.out.println("solicitaOperacaoCartao " + 03);
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			File arqTemp = new File(DIRETORIO_REQUISICAO + NOME_ARQUIVO + EXTENSAO_TEMPORARIA);
			fw = new FileWriter(arqTemp);
			bw = new BufferedWriter(fw);
			System.out.println("solicitaOperacaoCartao " + 04);

			bw.write("000-000 = " + OPERACAO_CARTAO + QUEBRA_LINHA);
			bw.write("001-000 = " + String.valueOf(solicitacaoOperacaoTEF.getIdentificacao()) + QUEBRA_LINHA);
			if(solicitacaoOperacaoTEF.getNumeroCOO() != null){
				System.out.println("solicitaOperacaoCartao " + 05);
				bw.write("002-000 = " + solicitacaoOperacaoTEF.getNumeroCOO().toString() + QUEBRA_LINHA);				
			}
			bw.write("003-000 = " + String.valueOf(solicitacaoOperacaoTEF.getValorOperacao().movePointRight(2)) + QUEBRA_LINHA);
			if(solicitacaoOperacaoTEF.getMoeda() != null){
				System.out.println("solicitaOperacaoCartao " + 06);
				bw.write("004-000 = " + solicitacaoOperacaoTEF.getMoeda().toString() + QUEBRA_LINHA);				
			}
			bw.write(FINALIZA_ARQUIVO + QUEBRA_LINHA);
			
			bw.flush();
			fw.flush();			
			
			if(fw != null){
				try {
					fw.close();} catch (IOException e) {
					e.printStackTrace();
					throw new ExcecaoTEF("TEF Indisponivel!", e);
				}
			}
			if(bw != null){
				try {
					bw.close();} catch (IOException e) {
					e.printStackTrace();
					throw new ExcecaoTEF("TEF Indisponivel!", e);
				}
			}
			System.out.println("solicitaOperacaoCartao " + 07);
			
			File arqFinal = new File(DIRETORIO_REQUISICAO + NOME_ARQUIVO + EXTENSAO_PRINCIPAL);
			if(arqTemp.exists()){
				arqTemp.renameTo(arqFinal);
				System.out.println("solicitaOperacaoCartao " + 8);
			}
			
			long start = System.currentTimeMillis();
			
			File arqResposta = new File(DIRETORIO_RESPOSTA + NOME_ARQUIVO + EXTENSAO_PRINCIPAL);
			
			while(true){
				if(System.currentTimeMillis() - start > TIMEOUT_OPERACAO && !arqResposta.exists()){
					System.out.println("solicitaOperacaoCartao " + 9);
					//cancela operacao
					throw new ExcecaoTEF("Operacao Cancelada!");
				}else if(arqResposta.exists()){
					System.out.println("solicitaOperacaoCartao " + 10);
					
					//criar metodo para preencher a resposta do arquivo.
					resposta = preencheRespostaSolicitacaoTEF(arqResposta);
					if(solicitacaoOperacaoTEF.getIdentificacao().equals(resposta.getIdentificacao())){
						System.out.println("solicitaOperacaoCartao " + 11);
						break;	
					} else {
						System.out.println("solicitaOperacaoCartao " + 111);
					}
				}
			}
			
			
			//deletar arquivos de resposta IntPos.Sts e IntPos.001 do diretorio de RESPOSTA
			System.out.println("solicitaOperacaoCartao " + 12);

			Util.apagarArquivos(new File(DIRETORIO_RESPOSTA), filtro);
			System.out.println("solicitaOperacaoCartao " + 13);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ExcecaoTEF("TEF Indisponivel!", e);
		} finally{
			if(fw != null){
				try {
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new ExcecaoTEF("TEF Indisponivel!", e);
				}
			}
			if(bw != null){
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new ExcecaoTEF("TEF Indisponivel!", e);
				}
			}
		}				

		return resposta;
	}

public RespostaOperacaoTEF solicitaOperacaoCheque(SolicitacaoOperacaoTEF solicitacaoOperacaoTEF) throws ExcecaoTEF {
		RespostaOperacaoTEF resposta = null;
		
		validarSolicitacaoOperacaoTEF(solicitacaoOperacaoTEF);
		
		verificaStatusServico(solicitacaoOperacaoTEF.getIdentificacao());
		Util.apagarArquivos(new File(DIRETORIO_REQUISICAO), filtro);
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			File arqTemp = new File(DIRETORIO_REQUISICAO + NOME_ARQUIVO + EXTENSAO_TEMPORARIA);
			fw = new FileWriter(arqTemp);
			bw = new BufferedWriter(fw);
			
			bw.write("000-000 = " + OPERACAO_CHEQUE + QUEBRA_LINHA);
			
			bw.write("001-000 = " + String.valueOf(solicitacaoOperacaoTEF.getIdentificacao()) + QUEBRA_LINHA);
			
			if(solicitacaoOperacaoTEF.getNumeroCOO() != null){
				bw.write("002-000 = " + solicitacaoOperacaoTEF.getNumeroCOO().toString() + QUEBRA_LINHA);				
			}
			
			bw.write("003-000 = " + String.valueOf(solicitacaoOperacaoTEF.getValorOperacao().movePointRight(2)) + QUEBRA_LINHA);
			
			if(solicitacaoOperacaoTEF.getCmc7() != null && !solicitacaoOperacaoTEF.getCmc7().equals("")){
				bw.write("005-000 = " + solicitacaoOperacaoTEF.getCmc7() + QUEBRA_LINHA);
			}
			
			if(solicitacaoOperacaoTEF.getTipoPessoa() != null && !solicitacaoOperacaoTEF.getTipoPessoa().equals("")){
				bw.write("006-000 = " + solicitacaoOperacaoTEF.getTipoPessoa() + QUEBRA_LINHA);	
			}

			if(solicitacaoOperacaoTEF.getDocumentoPessoa() != null){
				bw.write("007-000 = " + Util.completaString(solicitacaoOperacaoTEF.getDocumentoPessoa().toString(), "0", 16, true) + QUEBRA_LINHA);
			}

			if(solicitacaoOperacaoTEF.getDataCheque() != null){
				bw.write("008-000 = " + Util.retornaDataFormatoDDMMYYYY(solicitacaoOperacaoTEF.getDataCheque()) + QUEBRA_LINHA);
			}
			
			if(solicitacaoOperacaoTEF.getBanco() != null && !solicitacaoOperacaoTEF.getBanco().equals("")){
				bw.write("033-000 = " + solicitacaoOperacaoTEF.getBanco() + QUEBRA_LINHA);
			}
			
			if(solicitacaoOperacaoTEF.getAgencia() != null && !solicitacaoOperacaoTEF.getAgencia().equals("")){
				bw.write("034-000 = " + solicitacaoOperacaoTEF.getAgencia() + QUEBRA_LINHA);
			}
			
			if(solicitacaoOperacaoTEF.getDigitoAgencia() != null && !solicitacaoOperacaoTEF.getDigitoAgencia().equals("")){
				bw.write("035-000 = " + solicitacaoOperacaoTEF.getDigitoAgencia() + QUEBRA_LINHA);
			}
			
			if(solicitacaoOperacaoTEF.getContaCorrente() != null && !solicitacaoOperacaoTEF.getContaCorrente().equals("")){
				bw.write("036-000 = " + solicitacaoOperacaoTEF.getContaCorrente() + QUEBRA_LINHA);
			}
			
			if(solicitacaoOperacaoTEF.getDigitoContaCorrente() != null && !solicitacaoOperacaoTEF.getDigitoContaCorrente().equals("")){
				bw.write("037-000 = " + solicitacaoOperacaoTEF.getDigitoContaCorrente() + QUEBRA_LINHA);
			}
			
			if(solicitacaoOperacaoTEF.getNumeroCheque() != null && !solicitacaoOperacaoTEF.getNumeroCheque().equals("")){
				bw.write("038-000 = " + solicitacaoOperacaoTEF.getNumeroCheque() + QUEBRA_LINHA);
			}
			
			if(solicitacaoOperacaoTEF.getDigitoCheque() != null && !solicitacaoOperacaoTEF.getDigitoCheque().equals("")){
				bw.write("039-000 = " + solicitacaoOperacaoTEF.getDigitoCheque() + QUEBRA_LINHA);
			}
			
			bw.write(FINALIZA_ARQUIVO + QUEBRA_LINHA);
			
			bw.flush();
			fw.flush();			
			
			if(fw != null){
				try {
					fw.close();} catch (IOException e) {
					e.printStackTrace();
					throw new ExcecaoTEF("TEF Indisponivel!", e);
				}
			}
			if(bw != null){
				try {
					bw.close();} catch (IOException e) {
					e.printStackTrace();
					throw new ExcecaoTEF("TEF Indisponivel!", e);
				}
			}
			
			File arqFinal = new File(DIRETORIO_REQUISICAO + NOME_ARQUIVO + EXTENSAO_PRINCIPAL);
			if(arqTemp.exists()){
				arqTemp.renameTo(arqFinal);
			}
			
			long start = System.currentTimeMillis();
			
			File arqResposta = new File(DIRETORIO_RESPOSTA + NOME_ARQUIVO + EXTENSAO_PRINCIPAL);
			
			while(true){
				if(System.currentTimeMillis() - start > TIMEOUT_OPERACAO && !arqResposta.exists()){
					//cancela operacao
					throw new ExcecaoTEF("Operacao Cancelada!");
				}else if(arqResposta.exists()){
					break;
				}
			}
			
			//criar metodo para preencher a resposta do arquivo.
			resposta = preencheRespostaSolicitacaoTEF(arqResposta);
			
			//deletar arquivos de resposta IntPos.Sts e IntPos.001 do diretorio de RESPOSTA
			Util.apagarArquivos(new File(DIRETORIO_RESPOSTA), filtro);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ExcecaoTEF("TEF Indisponivel!", e);
		} finally{
			if(fw != null){
				try {
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new ExcecaoTEF("TEF Indisponivel!", e);
				}
			}
			if(bw != null){
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new ExcecaoTEF("TEF Indisponivel!", e);
				}
			}
		}				

		return resposta;
	}
	
	private void verificaStatusServico(long identificacao) throws ExcecaoTEF {

		Util.apagarArquivos(new File(DIRETORIO_RESPOSTA), filtro);

		FileWriter fw = null;
		BufferedWriter bw = null;

		FileReader fr = null;
		BufferedReader br = null;
		try {
			File arqTemp = new File(DIRETORIO_REQUISICAO + NOME_ARQUIVO + EXTENSAO_TEMPORARIA);
			fw = new FileWriter(arqTemp);
			bw = new BufferedWriter(fw);
			
			bw.write("000-000 = " + OPERACAO_VERIFICA_SERVICO + QUEBRA_LINHA);
			bw.write("001-000 = " + String.valueOf(identificacao) + QUEBRA_LINHA);
			bw.write(FINALIZA_ARQUIVO + QUEBRA_LINHA);
			
			bw.flush();
			fw.flush();		
			
			if(fw != null){
				try {
					fw.close();} catch (IOException e) {
					e.printStackTrace();
					throw new ExcecaoTEF("TEF Indisponivel!", e);
				}
			}
			if(bw != null){
				try {
					bw.close();} catch (IOException e) {
					e.printStackTrace();
					throw new ExcecaoTEF("TEF Indisponivel!", e);
				}
			}
			
			File arqFinal = new File(DIRETORIO_REQUISICAO + NOME_ARQUIVO + EXTENSAO_PRINCIPAL);
			if(arqTemp.exists()){
				arqTemp.renameTo(arqFinal);
			}
			
			long start = System.currentTimeMillis();
			
			File arqResposta = new File(DIRETORIO_RESPOSTA + NOME_ARQUIVO + EXTENSAO_STATUS);
			
			while(true){
				if(System.currentTimeMillis() - start > TIMEOUT_RESPOSTA && !arqResposta.exists()){
					throw new ExcecaoTEF("TEF não está ativo.");
				}else if(arqResposta.exists()){
					break;
				}
			}

			// colocado pq o arquivo existia ai ele tentava ler o paygo ainda estava escrevendo no arquivo de saida
			try {Thread.sleep(100);} catch (Exception e) {}

			fr = new FileReader(arqResposta);
			br = new BufferedReader(fr);
			
			String linhaArquivo = "";
			linhaArquivo = br.readLine();
			
			if(linhaArquivo != null && !linhaArquivo.equals("")){
				if(!linhaArquivo.equals("000-000 = " + OPERACAO_VERIFICA_SERVICO)){
					throw new ExcecaoTEF("TEF não está ativo.");
				}
			}else{
				throw new ExcecaoTEF("TEF não está ativo.");
			}
		
			linhaArquivo = br.readLine();
			if(linhaArquivo != null && !linhaArquivo.equals("")){
				if(!linhaArquivo.equals("001-000 = " + String.valueOf(identificacao))){
					throw new ExcecaoTEF("TEF não está ativo.");
				}
			}else{
				throw new ExcecaoTEF("TEF não está ativo.");
			}
			
			if(fr != null){
				try {
					fr.close();} catch (IOException e) {
					e.printStackTrace();
					throw new ExcecaoTEF("TEF não está ativo.", e);
				}
			}
			if(br != null){
				try {
					br.close();} catch (IOException e) {
					e.printStackTrace();
					throw new ExcecaoTEF("TEF não está ativo.", e);
				}
			}
			
			//deletar arquivos de resposta IntPos.Sts e IntPos.001 do diretorio de RESPOSTA
			Util.apagarArquivos(new File(DIRETORIO_RESPOSTA), filtro);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ExcecaoTEF("TEF não está ativo.", e);
		}/* finally{
			if(fw != null){
				try {
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new ExcecaoTEF("TEF Indisponivel!", e);
				}
			}
			if(bw != null){
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new ExcecaoTEF("TEF Indisponivel!", e);
				}
			}
		}	*/			
	}
	
	private RespostaOperacaoTEF preencheRespostaSolicitacaoTEF(File arquivoResposta){

		// colocado pq o arquivo existia ai ele tentava ler o paygo ainda estava escrevendo no arquivo de saida
		try {Thread.sleep(100);} catch (Exception e) {}
		
		FileReader fr = null;
		BufferedReader br = null;
		RespostaOperacaoTEF resposta = new RespostaOperacaoTEF();
		try {
			fr = new FileReader(arquivoResposta);
			br = new BufferedReader(fr);
			
			Map<String, String> hashArquivoResposta = new Hashtable<String, String>();
			String linha = "";
			while((linha = br.readLine())!= null){
				if(linha != null && !linha.equals("")){
					String[] arrayLinha = linha.split("=");
					hashArquivoResposta.put(arrayLinha[0].trim(), arrayLinha[1].trim());
					System.out.println("CHAVE" + arrayLinha[0].trim() + " - VALOR: " + arrayLinha[1].trim());
				}
			}
			
			if(hashArquivoResposta.containsKey("001-000")){
				resposta.setIdentificacao(Long.parseLong(hashArquivoResposta.get("001-000")));	
			}			

			if(hashArquivoResposta.containsKey("002-000")){
				resposta.setNumeroCOO(Long.parseLong(hashArquivoResposta.get("002-000")));	
			}
			
			if(hashArquivoResposta.containsKey("003-000")){
				resposta.setValorOperacao(new BigDecimal(hashArquivoResposta.get("003-000")).setScale(2));	
			}
			
			if(hashArquivoResposta.containsKey("004-000")){
				resposta.setMoeda(Byte.parseByte(hashArquivoResposta.get("004-000")));	
			}
			
			if(hashArquivoResposta.containsKey("005-000")){
				resposta.setCmc7(hashArquivoResposta.get("005-000"));	
			}
			
			if(hashArquivoResposta.containsKey("006-000")){
				resposta.setTipoPessoa(hashArquivoResposta.get("006-000"));	
			}
			
			if(hashArquivoResposta.containsKey("007-000")){
				resposta.setDocumentoPessoa(Long.parseLong(hashArquivoResposta.get("007-000")));	
			}
			
			if(hashArquivoResposta.containsKey("008-000")){
				resposta.setDataCheque(Util.formatarStringDDMMYYYYParaDataPadrao(hashArquivoResposta.get("008-000")));	
			}
			
			if(hashArquivoResposta.containsKey("009-000")){
				resposta.setStatusTransacao(hashArquivoResposta.get("009-000"));	
			}			

			if(hashArquivoResposta.containsKey("010-000")){
				resposta.setNomeRede(hashArquivoResposta.get("010-000"));	
			}			

			if(hashArquivoResposta.containsKey("011-000")){
				resposta.setTipoTransacao(Integer.parseInt(hashArquivoResposta.get("011-000")));	
			}			

			if(hashArquivoResposta.containsKey("012-000")){
				resposta.setNsuTEF(Long.parseLong(hashArquivoResposta.get("012-000")));	
			}			

			if(hashArquivoResposta.containsKey("013-000")){
				resposta.setCodigoAutorizacao(Integer.parseInt(hashArquivoResposta.get("013-000")));	
			}			

			if(hashArquivoResposta.containsKey("014-000")){
				resposta.setNumeroLoteTransacao(Long.parseLong(hashArquivoResposta.get("014-000")));	
			}			

			if(hashArquivoResposta.containsKey("015-000")){
				resposta.setDataHoraTransacaoHost(Util.retornaDataTimestampFormatoDDMMHHMMSS(hashArquivoResposta.get("015-000")));	
			}			

			if(hashArquivoResposta.containsKey("016-000")){
				resposta.setDataHoraTransacaoLocal(Util.retornaDataTimestampFormatoDDMMHHMMSS(hashArquivoResposta.get("016-000")));	
			}			

			if(hashArquivoResposta.containsKey("017-000")){
				resposta.setTipoParcelamento(Byte.parseByte(hashArquivoResposta.get("017-000")));	
			}			

			if(hashArquivoResposta.containsKey("018-000")){
				resposta.setQuantidadeParcelas(Integer.parseInt(hashArquivoResposta.get("018-000")));	
			}
			
			//ALIMENTAR A COLECAO DE PARCELAS
			ParcelaTEF parcela = null;
			for (int i = 0; i < resposta.getQuantidadeParcelas(); i++) {
				parcela = resposta.newParcelaTEF();
				int j = 19;	
				String chave = Util.completaString(String.valueOf(j++), "0", 3, true).concat("-").concat(Util.completaString(String.valueOf(i+1), "0", 3, true));
				if(hashArquivoResposta.containsKey(chave)){
					parcela.setVencimentoParcela(Util.formatarStringDDMMYYYYParaDataPadrao(hashArquivoResposta.get(chave)));	
				}
				chave = Util.completaString(String.valueOf(j++), "0", 3, true).concat("-").concat(Util.completaString(String.valueOf(i+1), "0", 3, true));
				if(hashArquivoResposta.containsKey(chave)){
					parcela.setValorParcela(new BigDecimal(hashArquivoResposta.get(chave)).setScale(2));	
				}
				chave = Util.completaString(String.valueOf(j++), "0", 3, true).concat("-").concat(Util.completaString(String.valueOf(i+1), "0", 3, true));
				if(hashArquivoResposta.containsKey(chave)){
					parcela.setNsuParcela(Integer.parseInt(hashArquivoResposta.get(chave)));	
				}
				resposta.getConjuntoParcelas().add(parcela);
			}
			
			if(hashArquivoResposta.containsKey("022-000")){
				Date data = null;
				String dataTransacao = hashArquivoResposta.get("022-000");
				String horaTransacao = "";
				int dia = Integer.parseInt(dataTransacao.substring(0, 2));
				int mes = Integer.parseInt(dataTransacao.substring(2, 4));
				int ano = Integer.parseInt(dataTransacao.substring(4));
				if(hashArquivoResposta.containsKey("023-000")){
					horaTransacao = hashArquivoResposta.get("023-000");	
				}
				if(horaTransacao.length() == 6){
					int horas = Integer.parseInt(horaTransacao.substring(0, 2));
					int minutos = Integer.parseInt(horaTransacao.substring(2, 4));
					int segundos = Integer.parseInt(horaTransacao.substring(4));
					data = new Date(ano-1900, mes, dia, horas, minutos, segundos);
				}else{
					data = new Date(ano-1900, mes, dia, 0, 0, 0);	
				}
				resposta.setDataHoraTransacao(data);
			}			
			
			if(hashArquivoResposta.containsKey("024-000")){
				resposta.setDataCartaoPreDatado(Util.formatarStringDDMMYYYYParaDataPadrao(hashArquivoResposta.get("024-000")));	
			}
			
			if(hashArquivoResposta.containsKey("025-000")){
				resposta.setNsuTransacaoCancelada(Long.parseLong(hashArquivoResposta.get("025-000")));	
			}
			
			if(hashArquivoResposta.containsKey("026-000")){ // DDMMHHMMSS
//				String dataTransacao = hashArquivoResposta.get("026-000");
//				int ano = new Date(System.currentTimeMillis()).getYear();
//				int dia = Integer.parseInt(dataTransacao.substring(0, 2));
//				int mes = Integer.parseInt(dataTransacao.substring(2, 4));
//				int hora = Integer.parseInt(dataTransacao.substring(4, 6));
//				int minuto = Integer.parseInt(dataTransacao.substring(6, 8));
//				int segundo = Integer.parseInt(dataTransacao.substring(8));
				resposta.setDataHoraTransacaoCancelada(Util.retornaDataTimestampFormatoDDMMHHMMSS(hashArquivoResposta.get("026-000")));
//				resposta.setDataHoraTransacaoCancelada(new Date(ano, mes, dia, hora, minuto, segundo));	
			}
			
			if(hashArquivoResposta.containsKey("027-000")){
				resposta.setChaveFinalizacao(hashArquivoResposta.get("027-000"));	
			}
			
			int qtdLinhasComprovante = 0;
			
			if(hashArquivoResposta.containsKey("028-000")){
				qtdLinhasComprovante = Integer.parseInt(hashArquivoResposta.get("028-000"));	
			}			
			
			String[] linhasComprovante = new String[qtdLinhasComprovante];
			
			for (int i = 0; i < qtdLinhasComprovante; i++) {
				String chave = "029-".concat(Util.completaString(String.valueOf(i+1), "0", 3, true));
				linhasComprovante[i] = hashArquivoResposta.get(chave).replaceAll("\"", "");
			}
			resposta.setLinhasComprovantePrincipal(linhasComprovante);
			
			if(hashArquivoResposta.containsKey("030-000")){
				resposta.setTextoEspecialOperador(hashArquivoResposta.get("030-000"));	
			}

			if(hashArquivoResposta.containsKey("031-000")){
				resposta.setTextoEspecialCliente(hashArquivoResposta.get("031-000"));	
			}
			
			if(hashArquivoResposta.containsKey("032-000")){
				resposta.setNumeroAutenticacaoCheque(hashArquivoResposta.get("032-000"));	
			}
			
			if(hashArquivoResposta.containsKey("033-000")){
				resposta.setBanco(hashArquivoResposta.get("033-000"));	
			}
			
			if(hashArquivoResposta.containsKey("034-000")){
				resposta.setAgencia(hashArquivoResposta.get("034-000"));	
			}
			
			if(hashArquivoResposta.containsKey("035-000")){
				resposta.setDigitoAgencia(hashArquivoResposta.get("035-000"));	
			}
			
			if(hashArquivoResposta.containsKey("036-000")){
				resposta.setContaCorrente(hashArquivoResposta.get("036-000"));	
			}
			
			if(hashArquivoResposta.containsKey("037-000")){
				resposta.setDigitoContaCorrente(hashArquivoResposta.get("037-000"));	
			}
			
			if(hashArquivoResposta.containsKey("038-000")){
				resposta.setNumeroCheque(hashArquivoResposta.get("038-000"));	
			}
			
			if(hashArquivoResposta.containsKey("039-000")){
				resposta.setDigitoCheque(hashArquivoResposta.get("039-000"));	
			}
			
			if(hashArquivoResposta.containsKey("040-000")){
				resposta.setNomeAutorizadora(hashArquivoResposta.get("040-000"));	
			}
			int qtdLinhasCupomReduzido = 0;
			
			if(hashArquivoResposta.containsKey("710-000")){
				qtdLinhasCupomReduzido = Integer.parseInt(hashArquivoResposta.get("710-000"));	
			}
			
			String[] linhasCupomReduzidoCli = new String[qtdLinhasCupomReduzido];
			
			for (int i = 0; i < qtdLinhasCupomReduzido; i++) {
				String chave = "711-".concat(Util.completaString(String.valueOf(i+1), "0", 3, true));
				linhasCupomReduzidoCli[i] = hashArquivoResposta.get(chave).replaceAll("\"", "");
			}
			resposta.setLinhasCupomReduzidoCli(linhasCupomReduzidoCli);
			
			int qtdLinhasComprovanteCli = 0;
			
			if(hashArquivoResposta.containsKey("712-000")){
				qtdLinhasComprovanteCli = Integer.parseInt(hashArquivoResposta.get("712-000"));	
			}
			
			String[] linhasComprovanteCli = new String[qtdLinhasComprovanteCli];
			
			for (int i = 0; i < qtdLinhasComprovanteCli; i++) {
				String chave = "713-".concat(Util.completaString(String.valueOf(i+1), "0", 3, true));
				linhasComprovanteCli[i] = hashArquivoResposta.get(chave).replaceAll("\"", "");
			}
			resposta.setLinhasComprovanteCli(linhasComprovanteCli);
			
			int qtdLinhasComprovanteEstabl = 0;
			
			if(hashArquivoResposta.containsKey("714-000")){
				qtdLinhasComprovanteEstabl = Integer.parseInt(hashArquivoResposta.get("714-000"));	
			}
			
			String[] linhasComprovanteEstabl = new String[qtdLinhasComprovanteEstabl];
			
			for (int i = 0; i < qtdLinhasComprovanteEstabl; i++) {
				String chave = "715-".concat(Util.completaString(String.valueOf(i+1), "0", 3, true));
				linhasComprovanteEstabl[i] = hashArquivoResposta.get(chave).replaceAll("\"", "");
			}
			resposta.setLinhasComprovanteEstabl(linhasComprovanteEstabl);			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(fr != null){
				try {fr.close();} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(br != null){
				try {br.close();} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return resposta;
	}	
	
	private static FileFilter filtro = new FileFilter() {
		public boolean accept(File file) {
			return file.getName().endsWith(NOME_ARQUIVO.concat(EXTENSAO_PRINCIPAL)) ||
	           file.getName().endsWith(NOME_ARQUIVO.concat(EXTENSAO_STATUS))
	            ||
	           file.getName().endsWith(NOME_ARQUIVO.concat(EXTENSAO_MINE));
		}
	};
}
