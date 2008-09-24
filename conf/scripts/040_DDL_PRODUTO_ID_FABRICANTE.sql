USE [ENTERPRISE];
GO
EXECUTE sp_rename N'[dbo].[PRODUTO].[ID_FORNECEDOR]', N'TemporaryColumnName1', 'COLUMN'
EXECUTE sp_rename N'[dbo].[PRODUTO].[TemporaryColumnName1]', N'ID_FABRICANTE', 'COLUMN'
GO