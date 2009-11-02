ALTER TABLE LANCAMENTO
ADD [LOJA] [numeric](18, 0) NULL,
	[COMPONENTE] [numeric](18, 0) NULL,
	[NUM_TRANSACAO] [numeric](18, 0) NULL,
	[DATA_TRANSACAO] [datetime] NULL,
	[BOLETO] [numeric](18, 0) NULL;
