

CREATE TABLE EVEN_ITEM_TRANS_CARTAO(
	LOJA int NOT NULL ,
	COMPONENTE int NOT NULL ,
	NUM_TRANSACAO int NOT NULL  ,
	DATA_TRANSACAO DATE NOT NULL  ,
	NUM_EVENTO int NOT NULL  ,
	IDENTIFICACAO bigint NOT NULL,
	DOC_FISCAL_COO bigint NULL,
	VALOR decimal NOT NULL,
	MOEDA int NOT NULL,
	STATUS int NOT NULL,
	REDE varchar(100)  NULL,
	TIPO_TRANSACAO int NOT NULL,
	NUMERO_TRANSACAO_NSU bigint NOT NULL,
	CODIGO_AUTORIZACAO int NULL,
	NUMERO_LOTE_TRANSACAO bigint NOT NULL,
	DATA_HORA_TRANSACAO_HOST datetime NULL,
	DATA_HORA_TRANSACAO_LOCAL datetime NULL,
	TIPO_PARCELAMENTO int NULL,
	QUANTIDADE_PARCELAMENTO int NULL,
	DATA_HORA_TRANSACAO datetime NULL,
	DATA_CARTAO_PRE_DATATADO datetime NULL,
	CHAVE_FINALIZACAO varchar(100) NULL,
	NOME_ADMINISTRADOR varchar(100) NULL,

PRIMARY KEY  
(
	LOJA ASC,
	COMPONENTE ASC,
	NUM_TRANSACAO ASC,
	DATA_TRANSACAO ASC,
	NUM_EVENTO ASC
));


ALTER TABLE  EVEN_ITEM_TRANS_CARTAO WITH CHECK ADD  CONSTRAINT FK_EVEN_ITEM_TRANS_CARTAO FOREIGN KEY(LOJA, COMPONENTE, NUM_TRANSACAO, DATA_TRANSACAO, NUM_EVENTO)
REFERENCES EVENTO_ITEM_PAGAMENTO (LOJA, COMPONENTE, NUM_TRANSACAO, DATA_TRANSACAO, NUM_EVENTO);



CREATE TABLE PARC_EVE_ITEM_PAG_CARTAO(
	LOJA int NOT NULL ,
	COMPONENTE int NOT NULL ,
	NUM_TRANSACAO int NOT NULL  ,
	DATA_TRANSACAO DATE NOT NULL  ,
	NUM_EVENTO int NOT NULL  ,
	NSU_PARCELA int NOT NULL,
	VALOR decimal NULL,
	DATA datetime NULL,

PRIMARY KEY  
(
	LOJA ASC,
	COMPONENTE ASC,
	NUM_TRANSACAO ASC,
	DATA_TRANSACAO ASC,
	NUM_EVENTO ASC,
	NSU_PARCELA ASC
))



ALTER TABLE EVEN_ITEM_TRANS_CARTAO  WITH CHECK ADD  CONSTRAINT FK_PARC_EVE_ITEM_PAG_CARTAO2 FOREIGN KEY(LOJA, COMPONENTE, NUM_TRANSACAO, DATA_TRANSACAO, NUM_EVENTO)
REFERENCES EVEN_ITEM_TRANS_CARTAO (LOJA, COMPONENTE, NUM_TRANSACAO, DATA_TRANSACAO, NUM_EVENTO);
