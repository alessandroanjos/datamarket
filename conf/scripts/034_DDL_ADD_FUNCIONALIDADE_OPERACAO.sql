/*
   quarta-feira, 13 de agosto de 2008 00:58:33
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
ALTER TABLE dbo.FUNCIONALIDADE ADD
	OPERACAO char(1) NULL
	update funcionalidade set operacao = 'I'
where url like '%inserir%'
update funcionalidade set operacao = 'A'
where url like '%consultar%'

GO
COMMIT
