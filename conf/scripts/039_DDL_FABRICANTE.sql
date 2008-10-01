CREATE TABLE [dbo].[FABRICANTE] (
  [ID] int NOT NULL,
  [NOME_FABRICANTE] varchar(100) COLLATE Latin1_General_CI_AS NOT NULL,
  [TIPO_PESSOA] char(1) COLLATE Latin1_General_CI_AS NOT NULL,
  [CPF_CNPJ] varchar(18) COLLATE Latin1_General_CI_AS NOT NULL,
  [RAZAO_SOCIAL] varchar(100) COLLATE Latin1_General_CI_AS NULL,
  [NOME_FANTASIA] varchar(100) COLLATE Latin1_General_CI_AS NULL,
  [INSCRICAO_ESTADUAL] varchar(50) COLLATE Latin1_General_CI_AS NULL,
  [INSCRICAO_MUNICIPAL] varchar(50) COLLATE Latin1_General_CI_AS NULL,
  [LOGRADOURO] varchar(200) COLLATE Latin1_General_CI_AS NULL,
  [NUMERO] varchar(20) COLLATE Latin1_General_CI_AS NULL,
  [COMPLEMENTO] varchar(50) COLLATE Latin1_General_CI_AS NULL,
  [BAIRRO] varchar(50) COLLATE Latin1_General_CI_AS NULL,
  [CIDADE] varchar(50) COLLATE Latin1_General_CI_AS NULL,
  [ESTADO] varchar(50) COLLATE Latin1_General_CI_AS NULL,
  [CEP] varchar(10) COLLATE Latin1_General_CI_AS NULL,
  [FONE_RESIDENCIAL] varchar(12) COLLATE Latin1_General_CI_AS NULL,
  [FONE_COMERCIAL] varchar(12) COLLATE Latin1_General_CI_AS NULL,
  [FONE_CELULAR] varchar(12) COLLATE Latin1_General_CI_AS NULL,
  [PESSOA_CONTATO] varchar(100) COLLATE Latin1_General_CI_AS NULL,
  [FONE_CONTATO] varchar(12) COLLATE Latin1_General_CI_AS NULL,
  [DATA_CADASTRO] datetime NOT NULL,
  CONSTRAINT [PK_FABRICANTE] PRIMARY KEY CLUSTERED ([ID])
)
ON [PRIMARY]
GO
alter table produto add constraint FK_PRODUTO_FABRICANTE foreign key(id_fabricante)
references fabricante(id)
GO