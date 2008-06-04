/*
   ter�a-feira, 3 de junho de 2008 20:10:22
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
ALTER TABLE dbo.PARCELA_PLAN_PAGTO_CHEQUE_PRE
	DROP CONSTRAINT FK_PLAN_PAG_CHEQ_PRE_PARC
GO
COMMIT
BEGIN TRANSACTION
ALTER TABLE dbo.PARCELA_PLAN_PAGTO_CHEQUE_PRE WITH NOCHECK ADD CONSTRAINT
	FK_PLAN_PAG_CHEQ_PRE_PARC FOREIGN KEY
	(
	ID_PLANO
	) REFERENCES dbo.PLANO_PAGAMENTO_CHEQUE_PRE
	(
	ID_PLANO
	) ON UPDATE CASCADE
	 ON DELETE CASCADE
	
GO
COMMIT
