INSERT INTO [FUNCIONALIDADE]
           ([ID]
           ,[ID_FUNC_SUPERIOR]
           ,[DESCRICAO]
           ,[SITUACAO]
           ,[URL]
           ,[LARGURA]
           ,[ALTURA])
     VALUES(88,67,'Comiss�o Por Vendedor','S','reports/relatorioComissaoPorVendedor',800,350);


INSERT INTO [PERFIL_FUNCIONALIDADE]
           ([ID_PERFIL]
           ,[ID_FUNCIONALIDADE])
     VALUES
           (1,88);
