CREATE TABLE [dbo].[PARCELA_PLAN_PAGTO_CHEQUE_PRE] (
	[ID_PLANO] [numeric](18, 0) NOT NULL ,
	[NUM_ENTRADA] [int] NOT NULL ,
	[PERCENTAGEM_PARCELA] [numeric](18, 2) NULL ,
	[QTD_DIAS] [int] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[PLANO_PAGAMENTO_CHEQUE_PRE] (
	[ID_PLANO] [numeric](18, 0) NOT NULL ,
	[PERCENTAGEM_ENTRADA] [numeric](18, 2) NULL 
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[PARCELA_PLAN_PAGTO_CHEQUE_PRE] ADD 
	CONSTRAINT [PK_PARCELA_PLAN_PAGATO_CHEQUE_PRE] PRIMARY KEY  CLUSTERED 
	(
		[ID_PLANO],
		[NUM_ENTRADA]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[PLANO_PAGAMENTO_CHEQUE_PRE] ADD 
	CONSTRAINT [PK_FORMA] PRIMARY KEY  CLUSTERED 
	(
		[ID_PLANO]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[PARCELA_PLAN_PAGTO_CHEQUE_PRE] ADD 
	CONSTRAINT [FK_PLAN_PAG_CHEQ_PRE_PARC] FOREIGN KEY 
	(
		[ID_PLANO]
	) REFERENCES [dbo].[PLANO_PAGAMENTO_CHEQUE_PRE] (
		[ID_PLANO]
	)
GO

ALTER TABLE [dbo].[PLANO_PAGAMENTO_CHEQUE_PRE] ADD 
	CONSTRAINT [FK_PLAN_PAG_PLAN_PAG_CHEQ_PRE] FOREIGN KEY 
	(
		[ID_PLANO]
	) REFERENCES [dbo].[PLANO_PAGAMENTO] (
		[ID]
	)
GO
