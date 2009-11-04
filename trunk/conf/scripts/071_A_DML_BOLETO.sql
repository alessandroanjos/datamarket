
/****** Object:  Table [dbo].[BOLETO]    Script Date: 11/03/2009 22:49:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[BOLETO](
	[ID] [numeric](19, 0) NOT NULL,
	[COMPONENTE] [int] NULL,
	[USUARIO] [int] NULL,
	[CLIENTE] [int] NULL,
	[LOJA] [int] NOT NULL,
	[STATUS] [int] NOT NULL,
	[CONTA_CORRENTE] [numeric](19, 0) NOT NULL,
	[CEDENTE] [varchar](35) COLLATE Latin1_General_CI_AI NOT NULL,
	[NOSSO_NUMERO] [varchar](8) COLLATE Latin1_General_CI_AI NOT NULL,
	[AGENCIA] [varchar](4) COLLATE Latin1_General_CI_AI NOT NULL,
	[CARTEIRA] [varchar](3) COLLATE Latin1_General_CI_AI NOT NULL,
	[NUMERO_CONTA_CORRENTE] [varchar](5) COLLATE Latin1_General_CI_AI NOT NULL,
	[DIGITO_CONTA_CORRENTE] [varchar](1) COLLATE Latin1_General_CI_AI NOT NULL,
	[VALOR] [numeric](19, 2) NOT NULL,
	[INSTRUCAO1] [varchar](100) COLLATE Latin1_General_CI_AI NULL,
	[INSTRUCAO2] [varchar](100) COLLATE Latin1_General_CI_AI NULL,
	[INSTRUCAO3] [varchar](100) COLLATE Latin1_General_CI_AI NULL,
	[INSTRUCAO4] [varchar](100) COLLATE Latin1_General_CI_AI NULL,
	[TIPO_BANCO] [int] NOT NULL,
	[DATA_vencimento] [datetime] NOT NULL,
	[DATA_PROCESSAMENTO] [datetime] NOT NULL,
	[NOME_CLIENTE] [varchar](100) COLLATE Latin1_General_CI_AI NULL,
	[ENDERECO_CLIENTE] [varchar](100) COLLATE Latin1_General_CI_AI NULL,
	[BAIRRO_CLIENTE] [varchar](100) COLLATE Latin1_General_CI_AI NULL,
	[CIDADE_CLIENTE] [varchar](100) COLLATE Latin1_General_CI_AI NULL,
	[UF_CLIENTE] [varchar](100) COLLATE Latin1_General_CI_AI NULL,
	[CPF_CNPJ] [varchar](100) COLLATE Latin1_General_CI_AI NULL,
	[CEP_CLIENTE] [varchar](100) COLLATE Latin1_General_CI_AI NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF