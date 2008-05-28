package com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.bematech;

import com.sun.jna.Library;



public interface IComunicacaoImpressoraFiscalBematechMP2000 extends Library{
	
	public int Bematech_FI_AberturaDoDia(String cValor, String cFormaPgto); 
	public int Bematech_FI_Suprimento(String cValor, String cFormaPgto);
	public int Bematech_FI_FechamentoDoDia();
	public int Bematech_FI_AbreComprovanteNaoFiscalVinculado(String cFormaPgto, String cValor, String cNumeroCupom);
	public int Bematech_FI_FechaComprovanteNaoFiscalVinculado();
	public int Bematech_FI_LeituraX();
	public int Bematech_FI_RecebimentoNaoFiscal(String cTotalizador, String cValor, String cForma);
	public int Bematech_FI_NomeiaTotalizadorNaoSujeitoIcms(int nIndice, String cDescricao);
	public int Bematech_FI_AbreCupom(String cpf_cnpj);
	public int Bematech_FI_VendeItem(String cCodigo , String cDescricao,String cAliquota, String cTipoQtde, String cQtde ,int iCasasDecimais, String cValor, String cTipoDesconto, String cValorDesc);
	public int Bematech_FI_CancelaItemGenerico(String cIndice);
	public int Bematech_FI_CancelaCupom();
	public int Bematech_FI_EfetuaFormaPagamento(String cFormaPgto,String cValor);
	public int Bematech_FI_FechaCupom(String cFormaPgto, String cAcresDesc,String cTipoAcresDesc,String cValorAcresDesc,String cValorPago, String cMsgPromocional);
	public int Bematech_FI_ProgramaAliquota(String cValor, int tipo);
	public int Bematech_FI_UsaUnidadeMedida(String cUnidade); 
	public int Bematech_FI_TerminaFechamentoCupom(String cMensagem);
	public int Bematech_FI_IniciaFechamentoCupom(String cTipoAcresDesc,String cValorAcresDesc,String cValorPago);
	public int Bematech_FI_RelatorioGerencial(String cTexto);
	public int Bematech_FI_FechaRelatorioGerencial();
	public int Bematech_FI_GrandeTotal(char[] gt);

}
