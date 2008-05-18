/*
   quinta-feira, 15 de maio de 2008 21:40:47
   User: 
   Server: (local)
   Database: PDV
   Application: MS SQLEM - Data Tools
*/

BEGIN TRANSACTION
SET QUOTED_IDENTIFIER ON
SET ARITHABORT ON
SET NUMERIC_ROUNDABORT OFF
SET CONCAT_NULL_YIELDS_NULL ON
SET ANSI_NULLS ON
SET ANSI_PADDING ON
SET ANSI_WARNINGS ON
COMMIT
BEGIN TRANSACTION
ALTER TABLE dbo.LOJA ADD
	ID_ESTOQUE_ATIVO int NULL
GO
DECLARE @v sql_variant 
SET @v = N'CODIGO DO ESTOQUE ATUAL'
EXECUTE sp_addextendedproperty N'MS_Description', @v, N'user', N'dbo', N'table', N'LOJA', N'column', N'ID_ESTOQUE_ATIVO'
GO
COMMIT
