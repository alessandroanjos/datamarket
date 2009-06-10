ALTER TABLE dbo.PRODUTO ADD
	STATUS varchar(1)

GO
ALTER TABLE dbo.LOJA ADD
	ID_CONTA_CORRENTE int

GO
INSERT INTO [ENTERPRISE].[dbo].[GRUPO_LANCAMENTO]
           ([ID]
           ,[DESCRICAO]
           ,[TIPO_REGISTRO])
     VALUES
           (1
           ,'GRUPO RECEBIMENTO'
           ,'S');

GO


INSERT INTO [ENTERPRISE].[dbo].[GRUPO_LANCAMENTO]
           ([ID]
           ,[DESCRICAO]
           ,[TIPO_REGISTRO])
     VALUES
           (2
           ,'GRUPO ENTRADA PRODUTO'
           ,'S');


