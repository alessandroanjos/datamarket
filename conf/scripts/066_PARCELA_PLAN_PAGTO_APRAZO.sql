if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[PARCELA_PLAN_PAGTO_APRAZO]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[PARCELA_PLAN_PAGTO_APRAZO]
GO

CREATE TABLE [dbo].[PARCELA_PLAN_PAGTO_APRAZO] (
	[ID_PLANO] [numeric](18, 0) NOT NULL ,
	[NUM_ENTRADA] [int] NOT NULL ,
	[PERCENTAGEM_ENTRADA] [numeric](18, 0) NULL ,
	[QTD_DIAS] [int] NULL ,
	[DATA_PROGRAMADA] [datetime] NULL 
) ON [PRIMARY]
GO

