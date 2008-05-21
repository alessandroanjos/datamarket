if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[CLIENTE_PAGTO]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[CLIENTE_PAGTO]
GO

CREATE TABLE [dbo].[CLIENTE_PAGTO] (
	[ID] [int] NOT NULL ,
	[ID_CLIENTE] [int] NOT NULL ,
	[DATA_PAGTO] [datetime] NOT NULL ,
	[VALOR_PAGTO] [numeric](18, 2) NOT NULL ,
	[ID_FORMA_RECEBIMENTO] [int] NOT NULL 
) ON [PRIMARY]
GO

