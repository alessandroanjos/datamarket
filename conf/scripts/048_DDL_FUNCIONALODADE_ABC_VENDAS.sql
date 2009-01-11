INSERT INTO [FUNCIONALIDADE]
           ([ID]
           ,[ID_FUNC_SUPERIOR]
           ,[DESCRICAO]
           ,[SITUACAO]
           ,[URL]
           ,[LARGURA]
           ,[ALTURA])
     VALUES(81,67,'ABC Vendas','S','reports/relatorioABCVendas',800,350);

INSERT INTO [FUNCIONALIDADE]
           ([ID]
           ,[ID_FUNC_SUPERIOR]
           ,[DESCRICAO]
           ,[SITUACAO]
           ,[URL]
           ,[LARGURA]
           ,[ALTURA])
     VALUES(82,67,'Fechamento Caixa','S',null,0,0);

INSERT INTO [FUNCIONALIDADE]
           ([ID]
           ,[ID_FUNC_SUPERIOR]
           ,[DESCRICAO]
           ,[SITUACAO]
           ,[URL]
           ,[LARGURA]
           ,[ALTURA])
     VALUES(83,82,'Geral','S','reports/relatorioFechamentoCaixaGeral',800,300);

INSERT INTO [FUNCIONALIDADE]
           ([ID]
           ,[ID_FUNC_SUPERIOR]
           ,[DESCRICAO]
           ,[SITUACAO]
           ,[URL]
           ,[LARGURA]
           ,[ALTURA])
     VALUES(84,82,'Operador','S','reports/relatorioFechamentoCaixaOperador',800,350);

INSERT INTO [PERFIL_FUNCIONALIDADE]
           ([ID_PERFIL]
           ,[ID_FUNCIONALIDADE])
     VALUES
           (1,81);
INSERT INTO [PERFIL_FUNCIONALIDADE]
           ([ID_PERFIL]
           ,[ID_FUNCIONALIDADE])
     VALUES
           (1,82);
INSERT INTO [PERFIL_FUNCIONALIDADE]
           ([ID_PERFIL]
           ,[ID_FUNCIONALIDADE])
     VALUES
           (1,83);
INSERT INTO [PERFIL_FUNCIONALIDADE]
           ([ID_PERFIL]
           ,[ID_FUNCIONALIDADE])
     VALUES
           (1,84);