if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_PARCELA_PLAN_PAGTO_CHEQUE_PRE_PLANO_PAGAMENTO_APRAZO]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[PARCELA_PLAN_PAGTO_CHEQUE_PRE] DROP CONSTRAINT FK_PARCELA_PLAN_PAGTO_CHEQUE_PRE_PLANO_PAGAMENTO_APRAZO
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[PLANO_PAGAMENTO_APRAZO]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[PLANO_PAGAMENTO_APRAZO]
GO

CREATE TABLE [dbo].[PLANO_PAGAMENTO_APRAZO] (
	[ID_PLANO] [numeric](18, 0) NOT NULL ,
	[PERCENTAGEM_ENTRADA] [numeric](18, 2) NULL 
) ON [PRIMARY]
GO

