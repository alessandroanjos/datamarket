CREATE TABLE [dbo].[EVENTO_OPERACAO] (
	[ID] [numeric](18, 0) NOT NULL ,
	[LOJA] [numeric](18, 0) NOT NULL ,
	[NUM_EVENTO] [numeric](18, 0) NOT NULL ,
	[TIPO_EVENTO] [numeric](10, 0) NOT NULL ,
	[DATA_HORA_EVENTO] [datetime] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[EVENTO_OPERACAO_ITEM_REGISTRADO] (
	[ID] [numeric](18, 0) NOT NULL ,
	[LOJA] [numeric](18, 0) NOT NULL ,
	[NUM_EVENTO] [numeric](18, 0) NOT NULL ,
	[PRECO] [numeric](18, 2) NOT NULL ,
	[QUANTIDADE] [numeric](18, 3) NOT NULL ,
	[DESCONTO] [numeric](18, 2) NULL ,
	[LUCRO] [numeric](18, 2) NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[OPERACAO] (
	[ID] [numeric](18, 0) NOT NULL ,
	[LOJA] [numeric](18, 0) NOT NULL ,
	[DATA] [datetime] NOT NULL ,
	[TIPO] [numeric](10, 0) NOT NULL ,
	[STATUS] [numeric](10, 0) NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[OPERACAO_DEVOLUCAO] (
	[ID] [numeric](18, 0) NOT NULL ,
	[LOJA] [numeric](18, 0) NOT NULL ,
	[VALOR] [numeric](18, 2) NOT NULL ,
	[COD_USU_OPERADOR] [varchar] (50) COLLATE Latin1_General_CI_AI NOT NULL ,
	[CPF_CNPJ_CLIENTE_TRANSACAO] [varchar] (18) COLLATE Latin1_General_CI_AI NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[PRODUTO_OPERAC_ITEM_REGISTRADO] (
	[ID] [numeric](18, 0) NOT NULL ,
	[LOJA] [numeric](18, 0) NOT NULL ,
	[NUM_EVENTO] [numeric](18, 0) NOT NULL ,
	[ID_PRODUTO] [numeric](18, 0) NULL ,
	[CODIGO_EXTERNO] [varchar] (50) COLLATE Latin1_General_CI_AI NULL ,
	[DESCRICAO_COMPLETA] [varchar] (50) COLLATE Latin1_General_CI_AI NULL ,
	[PRECO_PADRAO] [numeric](18, 2) NULL ,
	[PRECO_PRATICADO] [numeric](18, 2) NULL ,
	[IMP_IMPRESSORA] [varchar] (50) COLLATE Latin1_General_CI_AI NULL ,
	[PERCENTUAL] [numeric](18, 2) NULL 
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[EVENTO_OPERACAO] ADD 
	CONSTRAINT [PK_EVENTO_OPERACAO] PRIMARY KEY  CLUSTERED 
	(
		[ID],
		[LOJA],
		[NUM_EVENTO]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[EVENTO_OPERACAO_ITEM_REGISTRADO] ADD 
	CONSTRAINT [PK_EVENTO_OPERACAO_ITEM_DEVOLUCAO] PRIMARY KEY  CLUSTERED 
	(
		[ID],
		[LOJA],
		[NUM_EVENTO]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[OPERACAO] ADD 
	CONSTRAINT [PK_OPERACAO] PRIMARY KEY  CLUSTERED 
	(
		[ID],
		[LOJA]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[OPERACAO_DEVOLUCAO] ADD 
	CONSTRAINT [PK_OPERACAO_DEVOLUCAO] PRIMARY KEY  CLUSTERED 
	(
		[ID],
		[LOJA]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[PRODUTO_OPERAC_ITEM_REGISTRADO] ADD 
	CONSTRAINT [PK_PRODUTO_EVENTO_OPERCAO] PRIMARY KEY  CLUSTERED 
	(
		[ID],
		[LOJA],
		[NUM_EVENTO]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[EVENTO_OPERACAO] ADD 
	CONSTRAINT [FK_EVENTO_OPER_OPER] FOREIGN KEY 
	(
		[ID],
		[LOJA]
	) REFERENCES [dbo].[OPERACAO] (
		[ID],
		[LOJA]
	)
GO

ALTER TABLE [dbo].[EVENTO_OPERACAO_ITEM_REGISTRADO] ADD 
	CONSTRAINT [FK_EV_OPER_ITEM_REG_EV_OPER] FOREIGN KEY 
	(
		[ID],
		[LOJA],
		[NUM_EVENTO]
	) REFERENCES [dbo].[EVENTO_OPERACAO] (
		[ID],
		[LOJA],
		[NUM_EVENTO]
	)
GO

ALTER TABLE [dbo].[OPERACAO_DEVOLUCAO] ADD 
	CONSTRAINT [FK_OPER_DEVOLUCAO_OPER] FOREIGN KEY 
	(
		[ID],
		[LOJA]
	) REFERENCES [dbo].[OPERACAO] (
		[ID],
		[LOJA]
	)
GO

ALTER TABLE [dbo].[PRODUTO_OPERAC_ITEM_REGISTRADO] ADD 
	CONSTRAINT [FK_PROD_EV_OP_EV_OP_ITEM_REG] FOREIGN KEY 
	(
		[ID],
		[LOJA],
		[NUM_EVENTO]
	) REFERENCES [dbo].[EVENTO_OPERACAO_ITEM_REGISTRADO] (
		[ID],
		[LOJA],
		[NUM_EVENTO]
	)
GO