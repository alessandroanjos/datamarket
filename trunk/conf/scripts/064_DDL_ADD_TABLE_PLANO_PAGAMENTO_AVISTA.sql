if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[PLANO_PAGAMENTO_AVISTA]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[PLANO_PAGAMENTO_AVISTA]
GO

CREATE TABLE [dbo].[PLANO_PAGAMENTO_AVISTA] (
	[ID_PLANO] [numeric](18, 0) NOT NULL 
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[PLANO_PAGAMENTO_AVISTA] WITH NOCHECK ADD 
	CONSTRAINT [PK_PLANO_PAGAMENTO_AVISTA] PRIMARY KEY  CLUSTERED 
	(
		[ID_PLANO]
	)  ON [PRIMARY] 
GO

