/*
   sábado, 1 de março de 200817:03:44
   User: sa
   Server: BLACKBOX
   Database: pdv
   Application: 
*/

/* To prevent any potential data loss issues, you should review this script in detail before running it outside the context of the database designer.*/
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
GO
ALTER TABLE dbo.COMPONENTE
	DROP CONSTRAINT FK_CP_LJ
GO
COMMIT
BEGIN TRANSACTION
GO
CREATE TABLE dbo.Tmp_COMPONENTE
	(
	ID numeric(18, 0) NOT NULL,
	DESCRICAO varchar(50) NULL,
	ID_LOJA numeric(18, 0) NULL,
	IP varchar(50) NULL,
	VERSAO varchar(50) NULL,
	PORTA varbinary(5) NULL
	)  ON [PRIMARY]
GO
IF EXISTS(SELECT * FROM dbo.COMPONENTE)
	 EXEC('INSERT INTO dbo.Tmp_COMPONENTE (ID, DESCRICAO, ID_LOJA, IP, VERSAO, PORTA)
		SELECT ID, DESCRICAO, ID_LOJA, IP, VERSAO, CONVERT(varbinary(5), PORTA) FROM dbo.COMPONENTE WITH (HOLDLOCK TABLOCKX)')
GO
DROP TABLE dbo.COMPONENTE
GO
EXECUTE sp_rename N'dbo.Tmp_COMPONENTE', N'COMPONENTE', 'OBJECT' 
GO
ALTER TABLE dbo.COMPONENTE ADD CONSTRAINT
	PK_COMPONENTE PRIMARY KEY CLUSTERED 
	(
	ID
	) WITH( STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

GO
ALTER TABLE dbo.COMPONENTE WITH NOCHECK ADD CONSTRAINT
	FK_CP_LJ FOREIGN KEY
	(
	ID_LOJA
	) REFERENCES dbo.LOJA
	(
	ID
	) ON UPDATE  NO ACTION 
	 ON DELETE  NO ACTION 
	
GO
COMMIT
