
/****** Object:  Table [dbo].[PAGAMENTO_BOLETO]    Script Date: 11/03/2009 22:54:15 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PAGAMENTO_BOLETO](
	[ID] [numeric](19, 0) NOT NULL,
	[ID_ARQUIVO_PROCESSADO] [numeric](19, 0) NOT NULL,
	[VALOR] [numeric](19, 2) NOT NULL,
	[VALOR_DESCONTO] [numeric](19, 2) NULL,
	[VALOR_MULTA] [numeric](19, 2) NULL,
	[VALOR_MORA] [numeric](19, 2) NULL,
	[DATA_PAGAMENTO] [datetime] NOT NULL,
	[ID_BOLETO] [numeric](19, 0) NOT NULL,
	[BOLETO] [numeric](19, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC,
	[ID_ARQUIVO_PROCESSADO] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

GO
USE [ENTERPRISE]
GO
ALTER TABLE [dbo].[PAGAMENTO_BOLETO]  WITH CHECK ADD  CONSTRAINT [FK2E043ADA991B006B] FOREIGN KEY([BOLETO])
REFERENCES [dbo].[BOLETO] ([ID])
GO
ALTER TABLE [dbo].[PAGAMENTO_BOLETO]  WITH CHECK ADD  CONSTRAINT [FK2E043ADAE04987B4] FOREIGN KEY([ID_ARQUIVO_PROCESSADO])
REFERENCES [dbo].[ARQUIVO_PROCESSADO] ([ID])