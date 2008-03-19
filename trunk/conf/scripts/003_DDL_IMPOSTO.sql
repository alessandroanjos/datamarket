CREATE TABLE [dbo].[GRUPO_PRODUTO] (
	[ID] [numeric](18, 0) NOT NULL ,
	[DESCRICAO] [varchar] (50) COLLATE Latin1_General_CI_AI NULL ,
	[ID_GRUPO_SUPERIOR] [numeric](18, 0) NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[IMPOSTO] (
	[ID] [numeric](18, 0) NOT NULL ,
	[DESCRICAO] [varchar] (50) COLLATE Latin1_General_CI_AI NOT NULL ,
	[IMP_IMPRESSORA] [varchar] (50) COLLATE Latin1_General_CI_AI NOT NULL ,
	[PERCENTUAL] [numeric](18, 2) NOT NULL 
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[GRUPO_PRODUTO] ADD 
	CONSTRAINT [PK_GRUPO_PRODUTO] PRIMARY KEY  CLUSTERED 
	(
		[ID]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[IMPOSTO] ADD 
	CONSTRAINT [PK_IMPOSTO] PRIMARY KEY  CLUSTERED 
	(
		[ID]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[GRUPO_PRODUTO] ADD 
	CONSTRAINT [GRUPO_PRODUTO_GRUPO_PRODUTO] FOREIGN KEY 
	(
		[ID_GRUPO_SUPERIOR]
	) REFERENCES [dbo].[GRUPO_PRODUTO] (
		[ID]
	)
GO

ALTER TABLE [dbo].[PRODUTO] ADD 
	CONSTRAINT [FK_PRODUTO_IMPOSTO] FOREIGN KEY 
	(
		[ID_IMPOSTO]
	) REFERENCES [dbo].[IMPOSTO] (
		[ID]
	),
	CONSTRAINT [PRODUTO_GRUPO_PRODUTO] FOREIGN KEY 
	(
		[ID_GRUPO_PRODUTO]
	) REFERENCES [dbo].[GRUPO_PRODUTO] (
		[ID]
	)
GO



