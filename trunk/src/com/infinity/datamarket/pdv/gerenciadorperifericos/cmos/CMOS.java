package com.infinity.datamarket.pdv.gerenciadorperifericos.cmos;

public interface CMOS {
	
	public static final String COMPONENTE = "COMPONENTE";
	public static final String LOJA = "LOJA";
	public static final String CLIENTE_AV = "CLIENTE_AV";
	public static final String TELA_ATUAL = "TELA_ATUAL";
	public static final String ESTADO_ATUAL = "ESTADO_ATUAL";
	public static final String OPCOES_TECLA_MACRO = "OPCOES_TECLA_MACRO";
	public static final String MACRO_ATUAL = "MACRO_ATUAL";
	public static final String USUARIO_ATUAL = "USUARIO_ATUAL";
	public static final String PRODUTO_ATUAL = "PRODUTO_ATUAL";
	public static final String SUB_TOTAL = "SUB_TOTAL";
	public static final String TOTAL = "TOTAL";
	public static final String QUANTIDADE_ITEM = "QUANTIDADE_ITEM";
	public static final String QUANTIDADE_ITEM_NECESSARIO_PARA_SEPARACAO = "QUANTIDADE_ITEM_NECESSARIO_PARA_SEPARACAO";
	public static final String OPERADOR_ATUAL = "OPERADOR_ATUAL";
	public static final String AUTORIZADOR_ATUAL = "AUTORIZADOR_ATUAL";
	public static final String FUNDO_TROCO = "FUNDO_TROCO";
	public static final String NUMERO_CUPOM = "NUMERO_CUPOM";
	public static final String NUMERO_TRANSACAO = "NUMERO_TRANSACAO";
	public static final String VENDEDOR_ATUAL = "VENDEDOR_ATUAL";
	public static final String TRANSACAO_VENDA_ATUAL = "TRANSACAO_VENDA_ATUAL";
	public static final String ITEM_REGISTRADO = "ITEM_REGISTRADO";
	public static final String ITEM_PAGAMENTO = "ITEM_PAGAMENTO";
	public static final String VALOR_SANGRIA = "VALOR_SANGRIA";
	public static final String VALOR_RESUPRIMENTO = "VALOR_RESUPRIMENTO";
	public static final String ITEM_CANCELADO = "ITEM_CANCELADO";
	public static final String FORMA_RECEBIMENTO_ATUAL = "FORMA_RECEBIMENTO_ATUAL";
	public static final String PLANO_PAGAMENTO_ATUAL = "PLANO_PAGAMENTO_ATUAL";
	public static final String VALOR_PAGAMENTO_ATUAL = "VALOR_PAGAMENTO_ATUAL";
	public static final String VALOR_ITEM_PEDIDO = "VALOR_ITEM_PEDIDO";
	public static final String VALOR_TOTAL_PEDIDO = "VALOR_TOTAL_PEDIDO";
	public static final String VALOR_TOTAL_DESCONTO_PEDIDO = "VALOR_TOTAL_DESCONTO_PEDIDO";
	public static final String COLL_EVENTO_OPERACAO_ITEM_REGISTRADO_PEDIDO = "COLL_EVENTO_OPERACAO_ITEM_REGISTRADO_PEDIDO";
	public static final String VALOR_TROCO_ATUAL = "VALOR_TROCO_ATUAL";
	public static final String CHAVE_ULTIMA_TRANSACAO = "CHAVE_ULTIMA_TRANSACAO";
	public static final String DADOS_CHEQUE = "DADOS_CHEQUE";
	public static final String DADOS_CHEQUE_PRE = "DADOS_CHEQUE_PRE";
	public static final String DADOS_CARTAO_OFF = "DADOS_CARTAO_OFF";
	public static final String DADOS_CARTAO_PROPRIO = "DADOS_CARTAO_PROPRIO";
	public static final String DADOS_CONSULTA_CARTAO_PROPRIO = "DADOS_CONSULTA_CARTAO_PROPRIO";
	public static final String AUTORIZADORA_CARTAO_OFF = "AUTORIZADORA_CARTAO_OFF";
	public static final String DADOS_AUTORIZACOES_CARTAO_PROPRIO = "DADOS_AUTORIZACOES_CARTAO_PROPRIO";
	public static final String DATA_ULTIMA_ABERTURA = "DATA_ULTIMA_ABERTURA";
	public static final String TIPO_TRANSACAO = "TIPO_TRANSACAO";
	public static final String DESCONTO = "DESCONTO";
	public static final String ACRESCIMO = "ACRESCIMO";
	public static final String TRANSACAO_PAGAMENTO_ATUAL = "TRANSACAO_PAGAMENTO_ATUAL";
	public static final String DATA_MOVIMENTO = "DATA_MOVIMENTO";
	
	public static final String OPERACAO_ATUAL = "OPERACAO_ATUAL";
	public static final String OPERACAO_DEVOLUCAO = "OPERACAO_DEVOLUCAO";
	public static final String OPERACAO_PEDIDO = "OPERACAO_PEDIDO";
	public static final String OPERACAO_SEPARACAO = "OPERACAO_SEPARACAO";
	public static final String OPERACAO_CANCELAMENTO_PEDIDO = "OPERACAO_CANCELAMENTO_PEDIDO";
	public static final String OPERACAO_ALTERACAO_PEDIDO = "OPERACAO_ALTERACAO_PEDIDO";
	public static final String PK_OPERACOES = "PK_OPERACOES";
	public static final String BOLETO = "BOLETO";
	
	public void gravar(String chave, Object valor);
	public Object ler(String chave);
	public void setArquivo(String arquivo);
	
}
